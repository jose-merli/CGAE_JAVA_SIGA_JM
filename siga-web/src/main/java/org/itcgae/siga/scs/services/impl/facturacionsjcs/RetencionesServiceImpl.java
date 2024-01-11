package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
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
                    LOGGER.warn("RetencionesServiceImpl.searchRetenciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("RetencionesServiceImpl.searchRetenciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.searchRetenciones() -> Se ha producido un error en la busqueda de retenciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        retencionesDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.searchRetenciones() -> Salida del servicio de busqueda de retenciones");

        return retencionesDTO;
    }

    @Override
    public RetencionDTO searchRetencion(RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.searchRetencion() -> Entrada al servicio de busqueda de retencion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        RetencionDTO retencionDTO = new RetencionDTO();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetencion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetencion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info("RetencionesServiceImpl.searchRetencion() ->  fcsRetencionesJudicialesExtendsMapper.selectByPrimaryKey() -> Inicio de consulta para buscar la retencion");
                    FcsRetencionesJudicialesKey fcsRetencionesJudicialesKey = new FcsRetencionesJudicialesKey();
                    RetencionJudicialItem retencionJudicialItem = fcsRetencionesJudicialesExtendsMapper.searchRetencionConRestante(idInstitucion, retencionesRequestDTO.getIdRetenciones());
                    retencionDTO.setRetencion(retencionJudicialItem);
                    LOGGER.info("RetencionesServiceImpl.searchRetencion() ->  fcsRetencionesJudicialesExtendsMapper.selectByPrimaryKey() -> Fin de consulta para buscar la retencion");
                } else {
                    LOGGER.warn("RetencionesServiceImpl.searchRetencion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("RetencionesServiceImpl.searchRetencion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.searchRetencion() -> Se ha producido un error en la busqueda de la retencion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        retencionDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.searchRetencion() -> Salida del servicio de busqueda de retencion");

        return retencionDTO;
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

                LOGGER.info("RetencionesServiceImpl.deleteRetenciones() -> fcsRetencionesJudicialesExtendsMapper.deleteByExample() -> " +
                        "Eliminamos las retenciones no aplicadas");
                fcsRetencionesJudicialesExtendsMapper.deleteByExample(fcsRetencionesJudicialesExample);
            }

            if (!retencionesAplicadas.isEmpty()) {
                FcsRetencionesJudicialesExample fcsRetencionesJudicialesExample = new FcsRetencionesJudicialesExample();
                fcsRetencionesJudicialesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                        .andIdretencionIn(retencionesAplicadas);

                FcsRetencionesJudiciales fcsRetencionesJudiciales = new FcsRetencionesJudiciales();
                fcsRetencionesJudiciales.setFechafin(new Date());

                LOGGER.info("RetencionesServiceImpl.deleteRetenciones() -> fcsRetencionesJudicialesExtendsMapper.updateByExampleSelective() -> " +
                        "Establecemos fecha de fin a las retenciones aplicadas");
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

    @Override
    public InsertResponseDTO saveOrUpdateRetencion(FcsRetencionesJudiciales retencion, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> Entrada al servicio para guardar o actualizar una retencion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "RetencionesServiceImpl.saveOrUpdateRetencion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "RetencionesServiceImpl.saveOrUpdateRetencion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    retencion.setFechamodificacion(new Date());
                    retencion.setUsumodificacion(usuarios.get(0).getIdusuario());
                    retencion.setIdinstitucion(idInstitucion);
                    if (retencion.getIdretencion() == null) {
                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.getNewId() -> Inicio consulta para obtener nuevo id de retencion");
                        Long newId = Long.valueOf(fcsRetencionesJudicialesExtendsMapper.getNewId(idInstitucion).getIdMax());
                        retencion.setIdretencion(newId);
                        insertResponseDTO.setId(newId.toString());
                        retencion.setFechaalta(new Date());
                        if(retencion.getIdpersona() == null) {
                        	retencion.setEsdeturno("1");
                        }
                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.getNewId() -> Fin consulta para obtener nuevo id de retencion");

                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.insertSelective() -> Inicio inserccion de la retencion");
                        response = fcsRetencionesJudicialesExtendsMapper.insertSelective(retencion);
                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.insertSelective() -> Fin inserccion de la retencion");
                    } else {
                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.updateByPrimaryKeySelective() -> Inicio actualizacion de la retencion");
                        response = fcsRetencionesJudicialesExtendsMapper.updateByPrimaryKeySelective(retencion);
                        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> fcsRetencionesJudicialesExtendsMapper.updateByPrimaryKeySelective() -> Fin actualizacion de la retencion");
                    }

                    if (response == 1) {
                        insertResponseDTO.setStatus(SigaConstants.OK);
                    } else {
                        LOGGER.error("RetencionesServiceImpl.saveOrUpdateRetencion() -> Se ha producido un error al intentar guardar o actualizar la retencion");
                        error.setCode(500);
                        error.setDescription("general.message.error.realiza.accion");
                        insertResponseDTO.setStatus(SigaConstants.KO);
                    }

                } else {
                    LOGGER.warn("RetencionesServiceImpl.saveOrUpdateRetencion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("RetencionesServiceImpl.saveOrUpdateRetencion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.saveOrUpdateRetencion() -> Se ha producido un error al intentar guardar o actualizar la retencion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
            insertResponseDTO.setStatus(SigaConstants.KO);
        }

        insertResponseDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.saveOrUpdateRetencion() -> Salida del servicio para guardar o actualizar una retencion");

        return insertResponseDTO;
    }

    @Override
    public RetencionesAplicadasDTO searchRetencionesAplicadas(RetencionesRequestDTO retencionesRequestDTO, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() -> Entrada al servicio de busqueda de retenciones aplicadas");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        List<GenParametros> tamMax = null;
        Integer tamMaximo = null;
        Error error = new Error();
        RetencionesAplicadasDTO retencionesAplicadasDTO = new RetencionesAplicadasDTO();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetencionesAplicadas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "RetencionesServiceImpl.searchRetencionesAplicadas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper " +
                            "para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para " +
                            "obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() -> fcsRetencionesJudicialesExtendsMapper.searchRetencionesAplicadas() -> Inicio de consulta de retenciones aplicadas");
                    List<RetencionesAplicadasItem> retencionesAplicadasItemList = fcsRetencionesJudicialesExtendsMapper.searchRetencionesAplicadas(idInstitucion, retencionesRequestDTO, tamMaximo);
                    LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() -> fcsRetencionesJudicialesExtendsMapper.searchRetencionesAplicadas()-> Fin de consulta de retenciones aplicadas");

                    if (null != retencionesAplicadasItemList && retencionesAplicadasItemList.size() > tamMaximo) {
                        retencionesAplicadasItemList.remove(retencionesAplicadasItemList.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    retencionesAplicadasDTO.setRetencionesAplicadasItemList(retencionesAplicadasItemList);

                } else {
                    LOGGER.warn("RetencionesServiceImpl.searchRetencionesAplicadas() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("RetencionesServiceImpl.searchRetencionesAplicadas() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.searchRetencionesAplicadas() -> Se ha producido un error en la busqueda de retenciones aplicadas", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        retencionesAplicadasDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.searchRetencionesAplicadas() -> Salida del servicio de busqueda de retenciones aplicadas");

        return retencionesAplicadasDTO;
    }

    @Override
    public AplicacionRetencionDTO getAplicacionesRetenciones(AplicacionRetencionRequestDTO aplicacionRetencionRequestDTO, HttpServletRequest request) {

        LOGGER.info("RetencionesServiceImpl.getAplicacionesRetenciones() -> Entrada al servicio para obtener las aplicaciones de la retencion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        AplicacionRetencionDTO aplicacionRetencionDTO = new AplicacionRetencionDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "RetencionesServiceImpl.getAplicacionesRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "RetencionesServiceImpl.getAplicacionesRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (!UtilidadesString.esCadenaVacia(aplicacionRetencionRequestDTO.getIdPersona()) && !UtilidadesString.esCadenaVacia(aplicacionRetencionRequestDTO.getFechaPagoDesde())
                            && !UtilidadesString.esCadenaVacia(aplicacionRetencionRequestDTO.getFechaPagoHasta())) {

                        LOGGER.info("RetencionesServiceImpl.getAplicacionesRetenciones() / fcsRetencionesJudicialesExtendsMapper.getAplicacionesRetenciones() -> Inicio de la consulta para la busqueda de las aplicaciones de la retencion");
                        List<AplicacionRetencionItem> aplicacionRetencionItemList = fcsRetencionesJudicialesExtendsMapper.getAplicacionesRetenciones(idInstitucion, aplicacionRetencionRequestDTO);
                        LOGGER.info("RetencionesServiceImpl.getAplicacionesRetenciones() / fcsRetencionesJudicialesExtendsMapper.getAplicacionesRetenciones() -> Fin de la consulta para la busqueda de las aplicaciones de la retencion");
                        aplicacionRetencionDTO.setAplicacionRetencionItemList(aplicacionRetencionItemList);

                    } else {
                        LOGGER.error("RetencionesServiceImpl.getAplicacionesRetenciones() -> Alguno de los parámetro de entrada no encontrados");
                    }

                } else {
                    LOGGER.warn("RetencionesServiceImpl.getAplicacionesRetenciones() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                            + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("RetencionesServiceImpl.getAplicacionesRetenciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("RetencionesServiceImpl.getAplicacionesRetenciones() -> Se ha producido un error en obtencion de las aplicaciones de la retencion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        aplicacionRetencionDTO.setError(error);

        LOGGER.info("RetencionesServiceImpl.getAplicacionesRetenciones() -> Salida del servicio para obtener las aplicaciones de la retencion");

        return aplicacionRetencionDTO;
    }

}
