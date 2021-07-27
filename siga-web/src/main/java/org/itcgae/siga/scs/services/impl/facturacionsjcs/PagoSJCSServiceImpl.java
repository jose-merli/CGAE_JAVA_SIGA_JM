package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.FcsPagoGrupofactHitoMapper;
import org.itcgae.siga.db.mappers.FcsPagosEstadospagosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.*;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.itcgae.siga.scs.services.facturacionsjcs.IPagoSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class PagoSJCSServiceImpl implements IPagoSJCSService {

    private final Logger LOGGER = Logger.getLogger(PagoSJCSServiceImpl.class);

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosMapper;

    @Autowired
    private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

    @Autowired
    private FcsPagosEstadospagosMapper fcsPagosEstadospagosMapper;

    @Autowired
    private FcsFactGrupofactHitoExtendsMapper fcsFactGrupofactHitoExtendsMapper;

    @Autowired
    private FcsPagoGrupofactHitoMapper fcsPagoGrupofactHitoMapper;

    @Autowired
    private FacPropositosExtendsMapper facPropositosExtendsMapper;

    @Autowired
    private FacSufijoExtendsMapper facSufijoExtendsMapper;

    @Autowired
    private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

    @Override
    public PagosjgDTO buscarPagos(PagosjgItem pagosItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagos = new PagosjgDTO();
        String idLenguaje = "";
        List<GenParametros> tamMax = null;
        Integer tamMaximo = null;
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {
                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);
                    idLenguaje = usuario.getIdlenguaje();

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS")
                            .andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los pagos");
                    List<PagosjgItem> pagosItems = fcsPagosjgExtendsMapper.buscarPagos(pagosItem,
                            idInstitucion.toString(), idLenguaje, tamMaximo);

                    if (null != pagosItems && pagosItems.size() > tamMaximo) {
                        pagosItems.remove(pagosItems.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    pagos.setPagosjgItem(pagosItems);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los pagos");
                } else {
                    LOGGER.warn(
                            "FacturacionSJCSServicesImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("FacturacionSJCSServicesImpl.buscarPagos() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("FacturacionSJCSServicesImpl.buscarPagos() -> Se ha producido un error al buscar los pagos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagos.setError(error);
        LOGGER.info("FacturacionSJCSServicesImpl.buscarPagos() -> Salida del servicio para obtener los pagos");

        return pagos;
    }

    @Override
    public PagosjgDTO datosGeneralesPagos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> Entrada para obtener los datos generales del pago: "
                        + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        if (null != idInstitucion) {

            try {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Entrada para obtener los datos del pago");
                    List<PagosjgItem> pago = fcsPagosjgExtendsMapper.datosGeneralesPagos(idPago,
                            idInstitucion.toString());
                    pagosjgDTO.setPagosjgItem(pago);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Salida para obtener los datos del pago");
                } else {
                    LOGGER.warn(
                            "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } catch (Exception e) {
                LOGGER.error(
                        "FacturacionSJCSServicesImpl.datosGeneralesPagos() -> Se ha producido un error al buscar los datos generales del pago: "
                                + idPago,
                        e);
                error.setCode(500);
                error.setDescription("general.mensaje.error.bbdd");
            }

        } else {
            LOGGER.warn("FacturacionSJCSServicesImpl.datosGeneralesPagos() -> idInstitucion del token nula");
        }

        pagosjgDTO.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.datosGeneralesPagos() -> Salida con los datos generales del pago: "
                + idPago);

        return pagosjgDTO;
    }

    @Override
    public PagosjgDTO historicoPagos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.historicoPagos() -> Entrada al servicio para obtener el histórico de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    String idLenguaje = usuarios.get(0).getIdlenguaje();

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.historicoPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
                    List<PagosjgItem> listaEstados = fcsPagosjgExtendsMapper.historicoPagos(idPago, idLenguaje,
                            idInstitucion);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.historicoPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
                    pagosjgDTO.setPagosjgItem(listaEstados);
                }
            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.historicoPagos() -> Se ha producido un error al buscar el histórico de estados del pago: "
                            + idPago,
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagosjgDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.historicoPagos() -> Salida del servicio para obtener el histórico de un pago");

        return pagosjgDTO;
    }

    @Override
    public InsertResponseDTO savePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info("FacturacionSJCSServicesImpl.savePago() -> Entrada al servicio para la creación de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Entrada al método para obtener un nuevo identificador de pago");
                    NewIdDTO newId = fcsPagosjgExtendsMapper.getNewIdPago(idInstitucion);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Salida del método para obtener un nuevo identificador de pago: "
                                    + newId.getNewId());

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(newId.getNewId()));
                    record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
                    record.setNombre(pagosjgItem.getNombre());
                    record.setAbreviatura(pagosjgItem.getAbreviatura());
                    record.setFechadesde(pagosjgItem.getFechaDesde());
                    record.setFechahasta(pagosjgItem.getFechaHasta());
                    record.setCriteriopagoturno("F");
                    record.setImportepagado(BigDecimal.ZERO);
                    record.setImporterepartir(BigDecimal.ZERO);
                    record.setImporteoficio(BigDecimal.ZERO);
                    record.setImporteguardia(BigDecimal.ZERO);
                    record.setImportesoj(BigDecimal.ZERO);
                    record.setImporteejg(BigDecimal.ZERO);
                    record.setImporteminimo(BigDecimal.ZERO);
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setFechamodificacion(new Date());

                    // Buscamos el propósito sepa por defecto de la institución y lo seteamos en el pago
                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_TRANSFERENCIA_SEPA)
                            .andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito SEPA por defecto");
                    List<GenParametros> parametrosPropSepa = genParametrosMapper.selectByExample(genParametrosExample);

                    if (null != parametrosPropSepa && !parametrosPropSepa.isEmpty()) {

                        FacPropositosExample facPropositosExample = new FacPropositosExample();
                        facPropositosExample.createCriteria().andCodigoEqualTo(parametrosPropSepa.get(0).getValor());
                        List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(facPropositosExample);

                        if (null != propositos && !propositos.isEmpty()) {
                            record.setIdpropsepa(propositos.get(0).getIdproposito());
                        }

                    }

                    // Buscamos el propósito para otras transferencias por defecto de la institución y lo seteamos en el pago
                    genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_OTRA_TRANFERENCIA)
                            .andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));


                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito para otras transferencias por defecto");
                    List<GenParametros> parametrosPropOtraTrans = genParametrosMapper.selectByExample(genParametrosExample);

                    if (null != parametrosPropOtraTrans && !parametrosPropOtraTrans.isEmpty()) {

                        FacPropositosExample facPropositosExample = new FacPropositosExample();
                        facPropositosExample.createCriteria().andCodigoEqualTo(parametrosPropSepa.get(0).getValor());
                        List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(facPropositosExample);

                        if (null != propositos && !propositos.isEmpty()) {
                            record.setIdpropotros(propositos.get(0).getIdproposito());
                        }
                    }

                    // Si sólo encontramos un sufijo lo ponemos por defecto en el pago
                    FacSufijoExample facSufijoExample = new FacSufijoExample();
                    facSufijoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Obtenenos la lista de sufijos");
                    List<FacSufijo> listaSufijos = facSufijoExtendsMapper.selectByExample(facSufijoExample);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Lista de sufijos obtenida: " + listaSufijos.toString());

                    if (listaSufijos.size() == 1) {
                        record.setIdsufijo(listaSufijos.get(0).getIdsufijo());
                    }

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Entrada al método para insertar el nuevo pago: "
                                    + newId.getNewId());
                    response = fcsPagosjgExtendsMapper.insertSelective(record);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Salida del método para insertar el nuevo pago: "
                                    + newId.getNewId());

                    FcsPagosEstadospagos record2 = new FcsPagosEstadospagos();
                    record2.setIdinstitucion(idInstitucion);
                    record2.setIdpagosjg(Integer.valueOf(newId.getNewId()));
                    record2.setIdestadopagosjg(Short.valueOf("10"));
                    record2.setFechaestado(new Date());
                    record2.setFechamodificacion(new Date());
                    record2.setUsumodificacion(usuarios.get(0).getIdusuario());

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Entrada al método para insertar el nuevo estado al pago: "
                                    + newId.getNewId());
                    fcsPagosEstadospagosMapper.insertSelective(record2);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Salida del método para insertar el nuevo estado al pago: "
                                    + newId.getNewId());

                    if (response == 0) {
                        insertResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(500);
                        error.setDescription("general.message.error.realiza.accion");
                    } else {
                        insertResponseDTO.setStatus(SigaConstants.OK);
                        insertResponseDTO.setId(newId.getNewId());
                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error("FacturacionSJCSServicesImpl.savePago() -> Se ha producido un error en la creación del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.savePago() -> Salida del servicio para la creación de un pago");

        return insertResponseDTO;
    }

    @Override
    public UpdateResponseDTO updatePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info("FacturacionSJCSServicesImpl.updatePago() -> Entrada al servicio para la actualización de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
                    record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
                    record.setNombre(pagosjgItem.getNombre());
                    record.setAbreviatura(pagosjgItem.getAbreviatura());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setFechamodificacion(new Date());

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Entrada al método para actualizar el pago: "
                                    + pagosjgItem.getIdPagosjg());
                    response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Salida del método para actualizar el pago: "
                                    + pagosjgItem.getIdPagosjg());

                    if (response == 0) {
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(500);
                        error.setDescription("general.message.error.realiza.accion");
                    } else {
                        updateResponseDTO.setStatus(SigaConstants.OK);
                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.updatePago() -> Se ha producido un error en la actualización del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        updateResponseDTO.setError(error);

        LOGGER.info("FacturacionSJCSServicesImpl.updatePago() -> Salida del servicio para la actualización de un pago");

        return updateResponseDTO;
    }

    @Override
    public ConceptoPagoDTO comboConceptosPago(String idFacturacion, String idPago, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboConceptosPago() -> Entrada al servicio para obtener el combo de los conceptos del pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Entrada para obtener el combo de los conceptos del pago según su facturación");

                    List<ConceptoPagoItem> listaConceptosFac = fcsPagosjgExtendsMapper.comboConceptosPago(idInstitucion, idFacturacion, idPago, usuarios.get(0).getIdlenguaje());
                    conceptoPagoDTO.setListaConceptos(listaConceptosFac);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Salida de obtener el combo de los conceptos del pago según su facturación");

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.comboConceptosPago() -> Se ha producido un error en la obtención del combo de conceptos del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        conceptoPagoDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboConceptosPago() -> Salida del servicio para obtener el combo de los conceptos del pago");

        return conceptoPagoDTO;
    }

//	@Override
//	public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {
//
//		LOGGER.info(
//				"FacturacionSJCSServicesImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
//		Error error = new Error();
//		List<Integer> responses = new ArrayList<Integer>();
//		List<FcsFactGrupofactHito> listaConceptosFac = new ArrayList<FcsFactGrupofactHito>();
//		List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();
//
//		try {
//
//			if (null != idInstitucion) {
//
//				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//				exampleUsuarios.createCriteria().andNifEqualTo(dni)
//						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//				LOGGER.info(
//						"FacturacionSJCSServicesImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//				LOGGER.info(
//						"FacturacionSJCSServicesImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				if (null != usuarios && !usuarios.isEmpty()) {
//
//					FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
//					fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdfacturacionEqualTo(Integer.valueOf(idFacturacion));
//
//					LOGGER.info(
//							"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Entrada para la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//					listaConceptosFac = fcsFactGrupofactHitoMapper.selectByExample(fcsFactGrupofactHitoExample);
//					LOGGER.info(
//							"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Salida de la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//
//					FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
//					fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdpagosjgEqualTo(Integer.valueOf(idPago));
//
//					LOGGER.info(
//							"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Entrada para la eliminación de los conceptos del pago: "
//									+ idPago);
//					fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
//					LOGGER.info(
//							"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Salida de la eliminación de los conceptos del pago: "
//									+ idPago);
//
//					if (!listaConceptosFac.isEmpty()) {
//
//						for (FcsFactGrupofactHito fcsFactGrupofactHito : listaConceptosFac) {
//
//							FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
//							fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
//							fcsPagoGrupofactHito.setIdpagosjg(Integer.valueOf(idPago));
//							fcsPagoGrupofactHito.setIdhitogeneral(fcsFactGrupofactHito.getIdhitogeneral());
//							fcsPagoGrupofactHito.setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
//							fcsPagoGrupofactHito.setFechamodificacion(new Date());
//							fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//							LOGGER.info(
//									"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Entrada para la inserción de los conceptos del pago: "
//											+ idPago);
//							int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
//							LOGGER.info(
//									"FacturacionSJCSServicesImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Salida de la inserción de los conceptos del pago: "
//											+ idPago);
//
//							responses.add(response);
//
//						}
//
//						LOGGER.info(
//								"FacturacionSJCSServicesImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
//										+ idPago);
//						listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
//								usuarios.get(0).getIdlenguaje());
//						LOGGER.info(
//								"FacturacionSJCSServicesImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
//										+ idPago);
//
//						conceptoPagoDTO.setListaConceptos(listaConceptosPago);
//					}
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			LOGGER.error(
//					"FacturacionSJCSServicesImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago,
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//		}
//
//		if (!listaConceptosFac.isEmpty() && !responses.isEmpty() && responses.contains(0)) {
//			LOGGER.error(
//					"FacturacionSJCSServicesImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago);
//			error.setCode(400);
//			error.setDescription("general.message.error.realiza.accion");
//		}
//
//		conceptoPagoDTO.setError(error);
//
//		LOGGER.info(
//				"FacturacionSJCSServicesImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		return conceptoPagoDTO;
//	}

    @Override
    public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
                        + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
        Error error = new Error();
        List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
                                    + idPago);
                    listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
                            usuarios.get(0).getIdlenguaje());
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
                                    + idPago);

                    listaConceptosPago = listaConceptosPago.stream()
                            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(ConceptoPagoItem::getIdConcepto))),
                                    ArrayList::new));

                    conceptoPagoDTO.setListaConceptos(listaConceptosPago);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
                            + idPago,
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        conceptoPagoDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
                        + idPago);

        return conceptoPagoDTO;
    }

    @Override
    public UpdateResponseDTO saveConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                              HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.saveConceptoPago() -> Entrada al servicio para actualizar conceptos asociados al pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        List<Integer> responses = null;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

                        responses = new ArrayList<Integer>();

                        // Obtenemos los datos del pago sobre el que actuaremos
                        FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                        fcsPagosjgKey.setIdinstitucion(idInstitucion);
                        fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
                        FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

                        // Dependiendo de el tipo de concepto actualizaremos el importe correspondiente
                        // y el importe pagado
                        for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

                            if (null != concepto.getCantidadApagar()) {

                                if (concepto.getCantidadApagar() == 0 && !hayMovimientosVariosPositivosAaplicar(idInstitucion, concepto.getIdFacturacion())) {
                                    error.setCode(400);
                                    error.setDescription("facturacionSJCS.facturacionesYPagos.errorImporteCriterio");
                                    throw new Exception("No se puede introducir un importe igual a 0 si no hay movimientos varios positivos a aplicar");
                                }

                                switch (concepto.getIdConcepto()) {
                                    case "10":
                                        pago.setImporteoficio(BigDecimal.valueOf(
                                                pago.getImporteoficio().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteoficio().doubleValue()));
                                        break;
                                    case "20":
                                        pago.setImporteguardia(BigDecimal.valueOf(
                                                pago.getImporteguardia().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteguardia().doubleValue()));
                                        break;
                                    case "30":
                                        pago.setImportesoj(BigDecimal.valueOf(
                                                pago.getImportesoj().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImportesoj().doubleValue()));
                                        break;
                                    case "40":
                                        pago.setImporteejg(BigDecimal.valueOf(
                                                pago.getImporteejg().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteejg().doubleValue()));
                                        break;
                                }

                                LOGGER.info("FacturacionSJCSServicesImpl.saveConceptoPago() -> fcsFactGrupofactHitoExtendsMapper.selectByExample() -> Buscamos los conceptos de facturación con IDHITOGENERAL = " + concepto.getIdConcepto());
                                FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
                                fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                        .andIdfacturacionEqualTo(Integer.valueOf(concepto.getIdFacturacion()))
                                        .andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()));

                                List<FcsFactGrupofactHito> listaFcsFactGrupofactHito = fcsFactGrupofactHitoExtendsMapper
                                        .selectByExample(fcsFactGrupofactHitoExample);

                                // Recorremos los conceptos de la facturación con el idHitogeneral buscado y por cada línea insertamos una en los conceptos del pago
                                for (FcsFactGrupofactHito fcsFactGrupofactHito : listaFcsFactGrupofactHito) {

                                    FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
                                    fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
                                    fcsPagoGrupofactHito.setIdpagosjg(fcsPagosjgKey.getIdpagosjg());
                                    fcsPagoGrupofactHito.setIdhitogeneral(Short.valueOf(concepto.getIdConcepto()));
                                    fcsPagoGrupofactHito
                                            .setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
                                    fcsPagoGrupofactHito.setFechamodificacion(new Date());
                                    fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());

                                    LOGGER.info("FacturacionSJCSServicesImpl.saveConceptoPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Insertamos el concepto del pago");
                                    int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
                                    responses.add(response);
                                }

                            }

                        }

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
                        pago.setImporterepartir(pago.getImportepagado());
                        pago.setFechamodificacion(new Date());
                        pago.setUsumodificacion(usuarios.get(0).getIdusuario());
                        fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago",
                    e);
            if (null == error.getCode() || error.getCode() != 400) {
                error.setCode(500);
                error.setDescription("general.mensaje.error.bbdd");
            }
        }

        if (null != responses && responses.contains(0)) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago");
            error.setCode(400);
            error.setDescription("general.message.error.realiza.accion");
            updateResponseDTO.setStatus(SigaConstants.KO);
        } else {
            updateResponseDTO.setStatus(SigaConstants.OK);
        }

        updateResponseDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.saveConceptoPago() -> Salida del servicio para actualizar conceptos asociados al pago");

        return updateResponseDTO;
    }

    private boolean hayMovimientosVariosPositivosAaplicar(Short idInstitucion, String idFacturacion) {
        int numero = fcsPagosjgExtendsMapper.hayMovimientosVariosPositivosAaplicar(idInstitucion, idFacturacion);
        return (numero > 0);
    }

    @Override
    public DeleteResponseDTO deleteConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                                HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.deleteConceptoPago() -> Entrada al servicio para desasociar cenceptos del pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        List<Integer> responses = null;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

                        responses = new ArrayList<Integer>();

                        // Obtenemos el pago
                        FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                        fcsPagosjgKey.setIdinstitucion(idInstitucion);
                        fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
                        FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

                        // Dependiendo del concepto de pago, restaremos la cantidad al importe pagado y
                        // además actualizaremos a 0 el campo correspondiente
                        for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

                            switch (concepto.getIdConcepto()) {
                                case "10":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImporteoficio().doubleValue()));
                                    pago.setImporteoficio(BigDecimal.ZERO);
                                    break;
                                case "20":
                                    pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                            - pago.getImporteguardia().doubleValue()));
                                    pago.setImporteguardia(BigDecimal.ZERO);
                                    break;
                                case "30":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImportesoj().doubleValue()));
                                    pago.setImportesoj(BigDecimal.ZERO);
                                    break;
                                case "40":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImporteejg().doubleValue()));
                                    pago.setImporteejg(BigDecimal.ZERO);
                                    break;
                            }

                            // Eliminamos el concepto del pago
                            FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
                            fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                    .andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()))
                                    .andIdpagosjgEqualTo(Integer.valueOf(concepto.getIdPagosjg()));

                            LOGGER.info(
                                    "FacturacionSJCSServicesImpl.deleteConceptoPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Eliminamos el concepto del pago");
                            int response = fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
                            responses.add(response);

                        }

                        LOGGER.info(
                                "FacturacionSJCSServicesImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
                        pago.setImporterepartir(pago.getImportepagado());
                        pago.setFechamodificacion(new Date());
                        pago.setUsumodificacion(usuarios.get(0).getIdusuario());
                        fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        if (null != responses && responses.contains(0)) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago");
            error.setCode(400);
            error.setDescription("general.message.error.realiza.accion");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        } else {
            deleteResponseDTO.setStatus(SigaConstants.OK);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.deleteConceptoPago() -> Salida del servicio para desasociar cenceptos del pago");

        return deleteResponseDTO;
    }

    @Override
    public ComboDTO comboPropTranferenciaSepa(HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> Entrada al servicio para obtener el combo de propósitos para transferencias SEPA");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
                    List<ComboItem> combo = facPropositosExtendsMapper.
                            comboPropTranferencia(usuarios.get(0).getIdlenguaje(), false);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo: " + combo.toString());
                    comboDTO.setCombooItems(combo);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> Se ha producido un error al intentar obtener el combo de propósitos para transferencias SEPA",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboPropTranferenciaSepa() -> Salida del servicio para obtener el combo de propósitos para transferencias SEPA");

        return comboDTO;
    }

    @Override
    public ComboDTO comboPropOtrasTranferencias(HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> Entrada al servicio para obtener el combo de propósitos para otras transferencias");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
                    List<ComboItem> combo = facPropositosExtendsMapper.
                            comboPropTranferencia(usuarios.get(0).getIdlenguaje(), true);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo: " + combo.toString());
                    comboDTO.setCombooItems(combo);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> Se ha producido un error al intentar obtener el combo de propósitos para otras transferencias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboPropOtrasTranferencias() -> Salida del servicio para obtener el combo de propósitos para otras transferencias");

        return comboDTO;
    }

    @Override
    public ComboDTO comboSufijos(HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboSufijos() -> Entrada al servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Entrada al servicio para obtener el combo");
                    List<ComboItem> comboSufijos = facSufijoExtendsMapper.comboSufijos(idInstitucion);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Salida del servicio con el combo obtenido: " + comboSufijos.toString());
                    comboDTO.setCombooItems(comboSufijos);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.comboSufijos() -> Se ha producido un error al intentar obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboSufijos() -> Salida del servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

        return comboDTO;
    }

    @Override
    public ComboDTO comboCuentasBanc(HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboCuentasBanc() -> Entrada al servicio para obtener el combo de cuentas bancarias");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Entrada al servicio para obtener el combo");
                    List<ComboItem> comboCuentasBancarias = facBancoinstitucionExtendsMapper.comboCuentasBanc(idInstitucion);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Salida del servicio con el combo obtenido: " + comboCuentasBancarias.toString());
                    comboDTO.setCombooItems(comboCuentasBancarias);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.comboCuentasBanc() -> Se ha producido un error al intentar obtener el combo de cuentas bancarias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.comboCuentasBanc() -> Salida del servicio para obtener el combo de cuentas bancarias");

        return comboDTO;
    }

    @Override
    public UpdateResponseDTO saveConfigFichAbonos(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> Entrada al servicio para guardar la configuración de ficheros de abonos");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setBancosCodigo(pagosjgItem.getCodBanco());
                    record.setIdsufijo(Short.valueOf(pagosjgItem.getIdSufijo()));
                    record.setIdpropsepa(Short.valueOf(pagosjgItem.getIdPropSepa()));
                    record.setIdpropotros(Short.valueOf(pagosjgItem.getIdPropOtros()));

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos en el pago");
                    response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Pago actualizado: " + response);
                }

            }

            if (response == 0) {
                updateResponseDTO.setStatus(SigaConstants.KO);
                error.setCode(500);
                error.setDescription("general.message.error.realiza.accion");

            } else {
                updateResponseDTO.setStatus(SigaConstants.OK);
                updateResponseDTO.setId(pagosjgItem.getIdPagosjg());
            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> Se ha producido un error al intentar guardar la configuración de ficheros de abonos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        updateResponseDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.saveConfigFichAbonos() -> Salida del servicio para guardar la configuración de ficheros de abonos");

        return updateResponseDTO;
    }

    @Override
    public PagosjgDTO getConfigFichAbonos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> Entrada al servicio para obtener la configuración de ficheros de abonos");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Entrada a la obtención de los datos");
                    List<PagosjgItem> datosConfigFichAbonos = fcsPagosjgExtendsMapper.getConfigFichAbonos(idPago, idInstitucion);
                    LOGGER.info(
                            "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Salida con los datos obtenidos: " + datosConfigFichAbonos.toString());
                    pagosjgDTO.setPagosjgItem(datosConfigFichAbonos);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> Se ha producido un error al intentar obtener la configuración de ficheros de abonos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagosjgDTO.setError(error);

        LOGGER.info(
                "FacturacionSJCSServicesImpl.getConfigFichAbonos() -> Salida del servicio para obtener la configuración de ficheros de abonos");

        return pagosjgDTO;
    }

    @Override
    public InsertResponseDTO ejecutarPagoSJCS(String idPago, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    // Antes de ejecutar el pago comprobamos si tiene banco asociado
                    FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                    fcsPagosjgKey.setIdinstitucion(idInstitucion);
                    fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

                    LOGGER.info(
                            "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
                                    + idPago);
                    FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
                                    + idPago);

                    if (UtilidadesString.esCadenaVacia(pago.getBancosCodigo())) {
                        throw new FacturacionSJCSException("");
                    }

                    // CR7 - Antes de ejecutar simulamos el guardado
//					guardarBloquePago(miform, usr);

//					estadoPago = miform.getIdEstadoPagosJG();
//					criterioTurno = miform.getCriterioPagoTurno();

                    // Validacion de los datos antes de ejecutar el pago:
                    // 1. El estado del pago debe ser abierto:
//					if (!estadoPago.equals(ClsConstants.ESTADO_PAGO_ABIERTO))
//						return exito("messages.factSJCS.error.EstadoPagoNoCorrecto",
//								request);
//
//					// 2. Criterios correctos del Turno:
//					if (!criterioTurno.equals(ClsConstants.CRITERIOS_PAGO_FACTURACION))
//						return exito("messages.factSJCS.error.criterioPagoTurno",
//								request);
//					
//					//3. Si no se ha introducido importe a pagar el importe a facturar ser� cero
//					if (Double.valueOf(miform.getImporteRepartir())==0.00)
//						throw new SIGAException("messages.facturacionSJCS.abono.sin.importe.pago");

                }

            }

        } catch (FacturacionSJCSException fe) {

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> Se ha producido en la ejecución del pago: " + idPago, e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        return insertResponseDTO;
    }

//	protected String ejecutarPago(ActionMapping mapping, MasterForm formulario,
//			HttpServletRequest request, HttpServletResponse response)
//			throws SIGAException {
//		FcsPagosEstadosPagosAdm estadoPagosAdm = new FcsPagosEstadosPagosAdm(
//				this.getUserBean(request));
//		FcsPagosJGAdm pagosJGAdm = new FcsPagosJGAdm(this.getUserBean(request));
//		UsrBean usr;
//		DatosGeneralesPagoForm miform = (DatosGeneralesPagoForm) formulario;
//		String forward = "";
//		Hashtable registroSesion;
//		String estadoPago = null, criterioTurno = null;
//		UserTransaction tx = null;
//
//		try {
//			usr = (UsrBean) request.getSession().getAttribute("USRBEAN");
//			
//			
//			//Antes de ejecutar el pago comprobamos si tiene banco asociado
//			Hashtable claves = new Hashtable ();
//			UtilidadesHash.set (claves,FcsPagosJGBean.C_IDINSTITUCION,miform.getIdInstitucion());
//			UtilidadesHash.set (claves,FcsPagosJGBean.C_IDPAGOSJG,miform.getIdPagosJG());
//			Vector vdatosPago = pagosJGAdm.select(claves);
//			FcsPagosJGBean datosP = (FcsPagosJGBean) vdatosPago.get(0);
//			//Si no se ha asociado ninguna cuenta no se permite continuar
//			if((datosP.getBancosCodigo()==null)||(datosP.getBancosCodigo().isEmpty()))
//				throw new SIGAException(UtilidadesString.getMensajeIdioma(usr,"factSJCS.abonos.configuracion.literal.cuentaObligatoria"));
//
//			//CR7 - Antes de ejecutar simulamos el guardado
//			this.guardarBloquePago(miform, usr);
//			
//			
//			//AQUI EMPIEZA EL PROCESO DE EJCECUCION
//			tx = usr.getTransactionPesada();
//
//			// Datos del pago:
//			estadoPago = miform.getIdEstadoPagosJG();
//			criterioTurno = miform.getCriterioPagoTurno();
//
//			// Validacion de los datos antes de ejecutar el pago:
//			// 1. El estado del pago debe ser abierto:
//			if (!estadoPago.equals(ClsConstants.ESTADO_PAGO_ABIERTO))
//				return exito("messages.factSJCS.error.EstadoPagoNoCorrecto",
//						request);
//
//			// 2. Criterios correctos del Turno:
//			if (!criterioTurno.equals(ClsConstants.CRITERIOS_PAGO_FACTURACION))
//				return exito("messages.factSJCS.error.criterioPagoTurno",
//						request);
//			
//			//3. Si no se ha introducido importe a pagar el importe a facturar ser� cero
//			if (Double.valueOf(miform.getImporteRepartir())==0.00)
//				throw new SIGAException("messages.facturacionSJCS.abono.sin.importe.pago");
//			
//			// INICIO TRANSACCION
//			tx.begin();
//
//			// Obtenemos el Pago modificado del JSP:
//			Hashtable datosEntrada = (Hashtable) miform.getDatos();
//			datosEntrada
//					.put(FcsEstadosPagosBean.C_FECHAMODIFICACION, "SYSDATE");
//			datosEntrada.put(FcsEstadosPagosBean.C_USUMODIFICACION,
//					usr.getUserName());
//			FcsPagosEstadosPagosBean pagosEstadosBean = (FcsPagosEstadosPagosBean) estadoPagosAdm
//					.hashTableToBean(datosEntrada);
//			pagosEstadosBean.setIdEstadoPagosJG(new Integer(
//					ClsConstants.ESTADO_PAGO_EJECUTADO));
//			pagosEstadosBean.setFechaEstado("SYSDATE");
//
//			// Insertamos el estado del pago:
//			estadoPagosAdm.insert(pagosEstadosBean);
//
//			// Recuperamos de sesion el registro editado:
//			registroSesion = (Hashtable) request.getSession().getAttribute(
//					"DATABACKUP");
//
//			// Proceso de facturacion
//			Integer idInstitucion = UtilidadesHash.getInteger(registroSesion,
//					FcsPagosJGBean.C_IDINSTITUCION), idFacturacion = UtilidadesHash
//					.getInteger(registroSesion, FcsPagosJGBean.C_IDFACTURACION);
//
//			// crea el bean de pago colegiado e inicializa los datos comunes
//			Integer idPagoJG = new Integer(miform.getIdPagosJG());
//			Integer idPersona = new Integer(usr.getUserName());
//
//			// Se llama a los paquetes que ejecutan los pagos para cada concepto
//			// Estas funciones s�lo actualizan los importes de los conceptos
//			// del registro creado anteriormente
//			EjecucionPLs.ejecutarPL_PagoTurnosOficio(idInstitucion, idPagoJG,
//					idPersona);
//			EjecucionPLs.ejecutarPL_PagoGuardias(idInstitucion, idPagoJG,
//					idPersona);
//			EjecucionPLs.ejecutarPL_PagoSOJ(idInstitucion, idPagoJG, idPersona);
//			EjecucionPLs.ejecutarPL_PagoEJG(idInstitucion, idPagoJG, idPersona);
//
//			// a�adido cerrar abono
//			request.setAttribute("modo", "modificarPago");
//
//			// Calculo de todos los importes totales, importes de movimientos,
//			// importes de irpf, importe bruto, importe neto ......
//			// as� como la forma de pago, si el pago es por banco, obtenci�n del
//			// nombre del banco y la cuenta corriente.
//			String idInstitucionStr = usr.getLocation();
//			String idPagoStr = miform.getIdPagosJG();
//
//			this.obtencionImportes(idInstitucionStr, idPagoStr, request, null);
//
//			tx.commit();
//
//			// Exportacion de datos a EXCEL
//			UtilidadesFacturacionSJCS.exportarDatosPagos(idInstitucion,
//					idFacturacion, idPagoJG, null, usr);
//
//			// Consultamos el registro modificado tal cual esta en base de datos
//			// y lo almacenamos en sesion:
//			String where = " where " + FcsPagosJGBean.C_IDINSTITUCION + " = "
//					+ miform.getIdInstitucion() + " and "
//					+ FcsPagosJGBean.C_IDPAGOSJG + " = "
//					+ miform.getIdPagosJG() + " ";
//			Hashtable registroModificado = new Hashtable();
//			registroModificado = ((FcsPagosJGBean) pagosJGAdm.select(where)
//					.elementAt(0)).getOriginalHash();
//			request.getSession().setAttribute("DATABACKUP", registroModificado);
//
//			// Terminamos:
//			// Paso los parametros al jsp del refresco especifico para este caso
//			// de uso:
//			request.setAttribute("mensaje", "messages.updated.success");
//			request.setAttribute("modo", "abrirAvanzada");
//			request.setAttribute("idPagosJG", miform.getIdPagosJG());
//			request.setAttribute("idInstitucion", miform.getIdInstitucion());
//			forward = "exitoInsertarPago";
//		} catch (Exception e) {
//			throwExcp("messages.general.error",
//					new String[] { "modulo.facturacionSJCS" }, e, tx);
//		}
//		return forward;
//	}

//	private void guardarBloquePago(DatosGeneralesPagoForm miform, UsrBean usr) throws SIGAException {
//
//		FcsPagosJGAdm pagosAdm = new FcsPagosJGAdm(usr);
//		UserTransaction tx = null;
//
//		try {
//			tx = usr.getTransaction();
//			// Si no se ha introducido importe a pagar el importe a facturar ser� cero
//			if (Double.valueOf(miform.getImporteRepartir()) == 0.00)
//				throw new SIGAException("messages.facturacionSJCS.abono.sin.importe.pago");
//
//			// obtiene el bean a actualizar de BD
//			String where = " WHERE " + FcsPagosJGBean.C_IDINSTITUCION + "=" + miform.getIdInstitucion() + " AND "
//					+ FcsPagosJGBean.C_IDPAGOSJG + "=" + miform.getIdPagosJG() + " ";
//			FcsPagosJGBean pagosBean = (FcsPagosJGBean) pagosAdm.select(where).elementAt(0);
//			pagosBean.setNombre(miform.getNombre());
//			pagosBean.setAbreviatura(miform.getAbreviatura());
//			pagosBean.setImporteEJG(Double.valueOf(miform.getImporteEJG()));
//			pagosBean.setImporteSOJ(Double.valueOf(miform.getImporteSOJ()));
//			pagosBean.setImporteOficio(Double.valueOf(miform.getImporteOficio()));
//			pagosBean.setImporteGuardia(Double.valueOf(miform.getImporteGuardias()));
//			pagosBean.setImporteGuardia(Double.valueOf(miform.getImporteGuardias()));
//			pagosBean.setImporteRepartir(Double.valueOf(miform.getImporteRepartir()));
//			pagosBean.setImportePagado(Double.valueOf(miform.getImportePagado()));
//
//			/*
//			 * JPT: Calculo del concepto y el codigo del banco
//			 */
//
//			String sCuenta = "";
//			Integer idpropSEPA = null, idpropOtros = null, idsufijo = null;
//			Hashtable hash = new Hashtable();
//			hash.put(FcsPagosJGBean.C_IDINSTITUCION, pagosBean.getIdInstitucion());
//			hash.put(FcsPagosJGBean.C_IDPAGOSJG, pagosBean.getIdPagosJG());
//
//			Vector v = pagosAdm.selectByPK(hash);
//			if (v != null && v.size() > 0) {
//				FcsPagosJGBean bean = (FcsPagosJGBean) v.firstElement();
//				sCuenta = bean.getBancosCodigo();
//				idpropSEPA = bean.getIdpropSEPA();
//				idpropOtros = bean.getIdpropOtros();
//				idsufijo = bean.getIdsufijo();
//
//			}
//
//			pagosBean.setBancosCodigo(sCuenta);
//
//			if (idpropOtros != null)
//				pagosBean.setIdpropOtros(idpropOtros);
//			if (idpropSEPA != null)
//				pagosBean.setIdpropSEPA(idpropSEPA);
//
//			pagosBean.setIdsufijo(idsufijo);
//
//			// actualiza la BD
//			tx.begin();
//			pagosAdm.updateDirect(pagosBean);
//			tx.commit();
//
//		} catch (Exception e) {
//			throwExcp("messages.general.error", new String[] { "modulo.facturacionSJCS" }, e, null);
//		}
//	}

//	/**
//	 * 
//	 * Por cada colegiado aplicamos el proceso de obtenci�n de importes 1.
//	 * Obtener el total SJCS 2. Aplicar los movimientos varios 3. Obtener
//	 * importe bruto como la suma de los movimientos varios y el total SJCS 4.
//	 * Obtener el importe de irpf 5. Obtener el importe neto aplicando el
//	 * importe de irpf obtendio anteriormente 6. Aplicar retenciones judiciales
//	 * y no judiciales 7. Aplicar el importe total aplicando el importe de
//	 * retenciones obtenido previamente
//	 * 
//	 * 
//	 * @param idInstitucion
//	 * @param idPago
//	 * @param request
//	 * @param colegiadosMarcados
//	 * @return
//	 * @throws ClsExceptions
//	 * @throws SIGAException
//	 */
//	protected void obtencionImportes(String idInstitucion, String idPago,
//			HttpServletRequest request, Vector colegiadosMarcados)
//			throws ClsExceptions, SIGAException {
//
//		// Controles
//		UsrBean usr = (UsrBean) request.getSession().getAttribute("USRBEAN");
//
//		FcsPagosJGAdm pagoAdm = new FcsPagosJGAdm(usr);
//		
//
//		// variables para hacer el calculo del importe final a pagar
//		String idPersonaDestino = "";
//		double importeSJCS = 0.0d;
//		double importeTurnos = 0.0d, importeGuardias = 0.0d, importeSoj = 0.0d, importeEjg = 0.0d;
//		double importeMovimientos = 0.0d, importeRetenciones = 0.0d;
//		Double porcentajeIRPF;
//		double importeIrpfTotal = 0.0d;
//		String idCuenta;
//
//		FcsMovimientosVariosBean movimientosBean = new FcsMovimientosVariosBean();
//
//		// Recuperamos los colegiados a los que tenemos que pagar
//		// aquellos incluidos en el pago o con movimientos varios pendientes
//		Vector colegiados = (Vector) pagoAdm.getColegiadosAPagar(idInstitucion,
//				idPago,FcsPagosJGAdm.listaPagoTodos);
//
//		for (Iterator iter = colegiados.iterator(); iter.hasNext();) {
//			// recupera el colegiado
//			String idPersona = UtilidadesHash.getString(
//					(Hashtable) iter.next(), "IDPERSONA_SJCS");
//
//			// obtiene el pago del colegiado
//			FcsPagoColegiadoAdm pcAdm = new FcsPagoColegiadoAdm(usr);
//			Hashtable hash = new Hashtable();
//			hash.put(FcsPagoColegiadoBean.C_IDINSTITUCION, idInstitucion);
//			hash.put(FcsPagoColegiadoBean.C_IDPAGOSJG, idPago);
//			hash.put(FcsPagoColegiadoBean.C_IDPERORIGEN, idPersona);
//			Vector vector = pcAdm.selectByPK(hash);
//			// Si no existe un pago para el colegiado debe existir al menos un
//			// MV
//			// por lo que pasa a tratar los movimientos varios
//			if (!vector.isEmpty()) {
//				// Obtenemos el idcuenta con el fin de actualizar el registro de
//				// la persona de la tabla fcs_pago_colegiado
//
//				FcsPagoColegiadoBean pcBean = (FcsPagoColegiadoBean) vector
//						.get(0);
//				idPersonaDestino = pcBean.getIdPerDestino().toString();
//
//				CenClienteAdm clienteAdm = new CenClienteAdm(usr);
//				ArrayList cuenta = clienteAdm.getCuentaAbonoSJCS(idInstitucion, idPersonaDestino);
//
//				idCuenta = cuenta.get(2).toString().equals("-1") ? "null"
//						: cuenta.get(2).toString();
//
//				pagoAdm.updatePagoIdCuenta(idInstitucion, idCuenta, idPago,
//						idPersona);
//
//				// pagoAdm.updatePagoIdIrpf(idInstitucion, idPago, idPersona);
//
//				importeTurnos = pcBean.getImpOficio().doubleValue();
//				importeGuardias = pcBean.getImpAsistencia().doubleValue();
//				importeSoj = pcBean.getImpSOJ().doubleValue();
//				importeEjg = pcBean.getImpEJG().doubleValue();
//
//				// 1. Calcula el IMPORTE SJCS BRUTO
//				importeSJCS = importeTurnos + importeGuardias + importeSoj
//						+ importeEjg;
//			}
//
//			// obtiene el porcentajeIRPF del colegiado para utilizarlo al
//			// aplicar
//			// los movimientos varios y calcular el IRPF del importe bruto.
//			porcentajeIRPF = obtenerIrpf(idInstitucion, idPersonaDestino,
//					!idPersonaDestino.equals(idPersona));
//
//			// 2. Aplicar los movimientos varios
//			// Asocia todos los movimientos sin idpago al pago actual.
//			// Actualiza el porcentaje e importe IRPF para cada movimiento.
//			// FcsMovimientosVariosAdm movimientosAdm = new
//			// FcsMovimientosVariosAdm(usr);
//			// movimientosAdm.updatePago(idInstitucion, idPago, idPersona,
//			// porcentajeIRPF.toString());
//
//			movimientosBean.setIdInstitucion(Integer.valueOf(idInstitucion));
//			movimientosBean.setIdPersona(Integer.valueOf(idPersona));
//
//			importeMovimientos = aplicarMovimientosVarios(movimientosBean,
//					idPago, importeSJCS, usr);
//
//			// 3. Obtener importe bruto como la suma de los movimientos varios y
//			// el total SJCS
//			double importeBruto = importeSJCS + importeMovimientos;
//
//			// 4. Obtener el importe neto aplicando el IRPF
//			// (hay que redondear el importeIrpf porque es un importe que se ha
//			// de presentar)		
//			importeIrpfTotal =(-1)*UtilidadesNumero.redondea(importeBruto * porcentajeIRPF / 100,2);
//			
//			double importeNeto = importeBruto + importeIrpfTotal;
//
//			// 5. Aplicar retenciones judiciales y no judiciales
//			//aalg Incidencia del 28-sep-2011. Se modifica el usuario de modificacion que se estaba
//			// cogiendo el idPersona en vez del userName
//			aplicarRetencionesJudiciales(idInstitucion, idPago, idPersona,
//					Double.toString(importeNeto), 
//					usr.getUserName(), usr.getLanguage());
//			// obtener el importe de las retenciones judiciales
//			FcsCobrosRetencionJudicialAdm crjAdm = new FcsCobrosRetencionJudicialAdm(
//					usr);
//			importeRetenciones = crjAdm.getSumaRetenciones(idInstitucion,
//					idPago, idPersona);
//
//			// Actualizar el irpf, movimientos varios y retenciones en
//			// fcs_pago_colegiado
//			pcAdm.updateCierrePago(idInstitucion, idPago, idPersona,importeIrpfTotal,
//					porcentajeIRPF, importeMovimientos, importeRetenciones,
//					vector.isEmpty());
//
//		} // fin del for de colegiados
//
//	}

}
