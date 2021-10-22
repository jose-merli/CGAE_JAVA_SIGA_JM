package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.RetencionesDTO;
import org.itcgae.siga.DTOs.scs.RetencionesItem;
import org.itcgae.siga.DTOs.scs.RetencionesRequestDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsRetencionesJudicialesExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IRetencionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RetencionesServiceImpl implements IRetencionesService {

    private final Logger LOGGER = Logger.getLogger(RetencionesServiceImpl.class);

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private GenParametrosMapper genParametrosMapper;

    @Autowired
    private FcsRetencionesJudicialesExtendsMapper fcsRetencionesJudicialesExtendsMapper;

    @Override
    public RetencionesDTO searchRetenciones(RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.searchRetenciones() -> Entrada al servicio de busqueda de retenciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        List<GenParametros> tamMax = null;
        Integer tamMaximo = null;
        Error error = new Error();
        RetencionesDTO retencionesDTO = new RetencionesDTO();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info("RetencionesServiceImpl.searchRetenciones() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper " +
                            "para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info("RetencionesServiceImpl.searchRetenciones() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para " +
                            "obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info("RetencionesServiceImpl.searchRetenciones() -> fcsRetencionesJudicialesExtendsMapper.searchRetenciones() -> Inicio de consulta de retenciones");
                    List<RetencionesItem> retencionesItems = fcsRetencionesJudicialesExtendsMapper.searchRetenciones(idInstitucion, retencionesRequestDTO, usuarios.get(0).getIdlenguaje(), tamMaximo);
                    LOGGER.info("RetencionesServiceImpl.searchRetenciones() -> fcsRetencionesJudicialesExtendsMapper.searchRetenciones() -> Fin de consulta de retenciones");

                    if (null != retencionesItems && retencionesItems.size() > tamMaximo) {
                        retencionesItems.remove(retencionesItems.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    retencionesDTO.setRetencionesItemList(retencionesItems);

                } else {
                    LOGGER.warn("PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("RetencionesServiceImpl.searchRetenciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.searchRetenciones() -> Se ha producido un error en la busqueda de rtenciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        retencionesDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.searchRetenciones() -> Salida del servicio de busqueda de retenciones");

        return retencionesDTO;
    }

    public DeleteResponseDTO deleteRetenciones(List<RetencionesItem> retencionesItemList, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.deleteRetenciones() -> Entrada al servicio de eliminacion de retenciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

        try {
            deleteResponseDTO.setStatus(SigaConstants.OK);

            List<Long> retencionesAplicadas = new ArrayList<>();
            List<Long> retencionesNoAplicadas = new ArrayList<>();

            for (RetencionesItem r : retencionesItemList) {

                if (r.getRetencionAplicada().equals("0")) {
                    retencionesNoAplicadas.add(Long.valueOf(r.getIdRetencion()));
                } else {
                    retencionesAplicadas.add(Long.valueOf(r.getIdRetencion()));
                }

            }

            if (!retencionesNoAplicadas.isEmpty()) {
                FcsRetencionesJudicialesExample fcsRetencionesJudicialesExample = new FcsRetencionesJudicialesExample();
                fcsRetencionesJudicialesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                        .andIdretencionIn(retencionesNoAplicadas);

                fcsRetencionesJudicialesExtendsMapper.deleteByExample(fcsRetencionesJudicialesExample);
            }

            if (!retencionesAplicadas.isEmpty()) {
                FcsRetencionesJudicialesExample fcsRetencionesJudicialesExample = new FcsRetencionesJudicialesExample();
                fcsRetencionesJudicialesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                        .andIdretencionIn(retencionesAplicadas);

                FcsRetencionesJudiciales fcsRetencionesJudiciales = new FcsRetencionesJudiciales();
                fcsRetencionesJudiciales.setFechafin(new Date());

                fcsRetencionesJudicialesExtendsMapper.updateByExampleSelective(fcsRetencionesJudiciales, fcsRetencionesJudicialesExample);
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.deleteRetenciones() -> Se ha producido un error en el servicio de eliminacion de retenciones", e);
            error.setCode(500);
            error.setDescription("general.message.error.realiza.accion");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.deleteRetenciones() -> Salida del servicio de eliminacion de retenciones");

        return deleteResponseDTO;
    }

}
