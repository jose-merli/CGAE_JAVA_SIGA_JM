package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ReadProperties;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.OPERACION;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsCertificacionesExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactCertificacionesExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactEstadosfacturacionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CertificacionFacSJCSServicesImpl implements ICertificacionFacSJCSService {

    private static final String IDFACTURACION = "IDFACTURACION";

    private static Map<Short, String> mapaInstituciones = null;

    private Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesImpl.class);

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private FcsFactEstadosfacturacionExtendsMapper fcsFactEstadosfacturacionExtendsMapper;

    @Autowired
    private GenParametrosMapper genParametrosMapper;

    @Autowired
    private EcomColaMapper ecomColaMapper;

    @Autowired
    private EcomColaParametrosMapper ecomColaParametrosMapper;

    @Autowired
    private CenInstitucionExtendsMapper institucionesMapper;

    @Autowired
    private CertificacionFacSJCSServicesCAMHelper camHelper;

    @Autowired
    private CertificacionFacSJCSServicesCatalunyaHelper cataHelper;

    @Autowired
    private FcsCertificacionesExtendsMapper fcsCertificacionesExtendsMapper;

    @Autowired
    private EcomOperacionMapper ecomOperacionMapper;

    @Autowired
    private FacturacionSJCSHelper facturacionHelper;

    @Autowired
    private FcsFactCertificacionesMapper fcsFactCertificacionesMapper;

    @Autowired
    private FcsMvariosCertificacionesMapper fcsMvariosCertificacionesMapper;

    @Autowired
    private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

    @Autowired
    private FcsCertificacionesHistoricoEstadoMapper fcsCertificacionesHistoricoEstadoMapper;

    @Autowired
    private CertificacionFacSJCSServicesXuntaHelper xuntaHelper;

    @Autowired
    private FcsFactEstadosfacturacionMapper fcsFactEstadosfacturacionMapper;

    @Autowired
    private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

    @Autowired
    private FcsPagosjgMapper fcsPagosjgMapper;

    @Autowired
    private FcsFactCertificacionesExtendsMapper fcsFactCertificacionesExtendsMapper;


    @Override
    public InsertResponseDTO tramitarCertificacion(TramitarCerttificacionRequestDTO tramitarCerttificacionRequestDTO, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.tramitarCertificacion() -> Entrada al servicio para tramitar la certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.tramitarCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.tramitarCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (tramitarCerttificacionRequestDTO.getFacturacionItemList() != null && !tramitarCerttificacionRequestDTO.getFacturacionItemList().isEmpty()) {

                        for (FacturacionItem f : tramitarCerttificacionRequestDTO.getFacturacionItemList()) {
                            listaParaConsejo(tramitarCerttificacionRequestDTO.getIdCertificacion(), idInstitucion, f.getIdFacturacion(), usuarios.get(0));
                        }

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.tramitarCertificacion() -> Se ha producido un error al intentar tramitar la certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);
        insertResponseDTO.setId(tramitarCerttificacionRequestDTO.getIdCertificacion());

        if (error.getDescription() == null) {
            insertResponseDTO.setStatus(SigaConstants.OK);
        } else {
            insertResponseDTO.setStatus(SigaConstants.KO);
        }

        LOGGER.info("CertificacionFacSJCSServicesImpl.tramitarCertificacion() -> Salida del servicio para tramitar la certificacion");

        return insertResponseDTO;
    }

    private void listaParaConsejo(String idCertificacion, Short idInstitucion, String idFacturacion, AdmUsuarios usuario) throws Exception {

        String estadoActualFacturacion = fcsFactEstadosfacturacionExtendsMapper.getIdEstadoFacturacion(idInstitucion, idFacturacion);

        //comprobamos que la facturacion se encuentra ejecutada o no validada o rechazada
        int estadoActualFac = Integer.parseInt(estadoActualFacturacion);

        if (estadoActualFac != SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_EJECUTADA.getCodigo()
                && estadoActualFac != SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_VALIDACION_NO_CORRECTA.getCodigo()
                && estadoActualFac != SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_NO_DISPONIBLE.getCodigo()
                && estadoActualFac != SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_NO_ACEPTADO.getCodigo()) {
            throw new Exception("Ha ocurrido un error al cerrar la facturación. No se puede cerrar la facturación porque el estado actual no es correcto.");
        }

        int estadoFuturo = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_LISTA_CONSEJO.getCodigo();
        //SI TIENE CONFIGURADO EL WEBSERVICE HACEMOS LA LLAMADA
        int tipoCAJG = getTipoCAJG(idInstitucion);

        if (SigaConstants.TIPO_CAJG_XML_SANTIAGO == tipoCAJG) {
            envioWS(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.ECOM2_XUNTA_JE.getId(), usuario);
            estadoFuturo = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_EN_PROCESO.getCodigo();
            actualizaEstadoCertificacion(idCertificacion, idInstitucion, Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_VALIDANDO.getCodigo()), usuario.getIdusuario());
        }

        //TODO CAMBIAR LA OPERACIÓN
//        if (SigaConstants.TIPO_CAJG_CATALANES == tipoCAJG) {
//            envioWS(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.ECOM2_CAT_VALIDA_JUSTIFICACION.getId(), usuario);
//            estadoFuturo = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_EN_PROCESO.getCodigo();
//            actualizaEstadoCertificacion(idCertificacion, idInstitucion, Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_VALIDANDO.getCodigo()), usuario.getIdusuario());
//        }

        if (esCAM(idInstitucion)) {
            /* en el caso de la CAM el fichero ya se ha generado previamente al ejecutar informe
             *  averiguar qué implicaciones tiene en el estado en el caso de la CAM
             */

        }

        if (!esCAM(idInstitucion)) {
            actualizarEstadoFacturacion(usuario, idInstitucion, idFacturacion, estadoFuturo);
        }

    }

    private void actualizaEstadoCertificacion(String idCertificacion, Short idInstitucion, Short idEstado, Integer idUsuario) {
        FcsCertificacionesHistoricoEstado estado = new FcsCertificacionesHistoricoEstado();
        estado.setIdcertificacion(Short.valueOf(idCertificacion));
        estado.setIdinstitucion(idInstitucion);
        estado.setFechaestado(new Date());
        estado.setIdestado(idEstado);
        estado.setFechamodificacion(new Date());
        estado.setUsumodificacion(idUsuario);
        LOGGER.info("CertificacionFacSJCSServicesImpl.actualizaEstadoCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> INICIO de la query para insertar el estado de la certificacion");
        fcsCertificacionesHistoricoEstadoMapper.insertSelective(estado);
        LOGGER.info("CertificacionFacSJCSServicesImpl.actualizaEstadoCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> FIN de la query para insertar el estado de la certificacion");

        FcsCertificaciones fcsCertificaciones = new FcsCertificaciones();
        fcsCertificaciones.setIdcertificacion(Short.valueOf(idCertificacion));
        fcsCertificaciones.setIdinstitucion(idInstitucion);
        fcsCertificaciones.setIdestadocertificacion(idEstado);
        fcsCertificaciones.setFechamodificacion(new Date());
        fcsCertificaciones.setUsumodificacion(idUsuario);
        LOGGER.info("CertificacionFacSJCSServicesImpl.actualizaEstadoCertificacion() -> fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective() -> INICIO de la query para aztualizar la certificacion");
        fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective(fcsCertificaciones);
        LOGGER.info("CertificacionFacSJCSServicesImpl.actualizaEstadoCertificacion() -> fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective() -> FIN de la query para aztualizar la certificacion");
    }

    private void actualizarEstadoFacturacion(AdmUsuarios usuario, Short idInstitucion, String idFacturacion, int estadoFuturo) {
        String idOrdenEstado = fcsFactEstadosfacturacionExtendsMapper.getIdordenestadoMaximo(idInstitucion, idFacturacion);
        FcsFactEstadosfacturacion fcsFactEstadosfacturacion = new FcsFactEstadosfacturacion();
        fcsFactEstadosfacturacion.setIdinstitucion(idInstitucion);
        fcsFactEstadosfacturacion.setIdfacturacion(Integer.valueOf(idFacturacion));
        fcsFactEstadosfacturacion.setIdestadofacturacion(Short.valueOf(String.valueOf(estadoFuturo)));
        fcsFactEstadosfacturacion.setIdordenestado(Short.valueOf(idOrdenEstado));
        fcsFactEstadosfacturacion.setFechaestado(new Date());
        fcsFactEstadosfacturacion.setFechamodificacion(new Date());
        fcsFactEstadosfacturacion.setUsumodificacion(usuario.getIdusuario());
        fcsFactEstadosfacturacionExtendsMapper.insert(fcsFactEstadosfacturacion);
    }

    private int getTipoCAJG(Short idInstitucion) {

        int response = 1;

        GenParametrosKey genParametrosKey = new GenParametrosKey();
        genParametrosKey.setIdinstitucion(idInstitucion);
        genParametrosKey.setModulo(SigaConstants.MODULO_SCS);
        genParametrosKey.setParametro(SigaConstants.PARAMETRO_PCAJG_TIPO);

        GenParametros parametro = genParametrosMapper.selectByPrimaryKey(genParametrosKey);

        if (parametro != null) {
            response = Integer.parseInt(parametro.getValor());
        }

        return response;
    }

    private void envioWS(Short idInstitucion, String idFacturacion, Integer idOperacion, AdmUsuarios usuario) throws Exception {

        FcsFacturacionjgKey fcsFacturacionjgKey = new FcsFacturacionjgKey();
        fcsFacturacionjgKey.setIdinstitucion(idInstitucion);
        fcsFacturacionjgKey.setIdfacturacion(Integer.valueOf(idFacturacion));

        EcomCola ecomCola = new EcomCola();
        ecomCola.setIdinstitucion(idInstitucion);
        ecomCola.setIdoperacion(idOperacion);

        insertaColaFcsFacturacionJG(ecomCola, fcsFacturacionjgKey, usuario);
    }

    private void insertaColaFcsFacturacionJG(EcomCola ecomCola, FcsFacturacionjgKey fcsFacturacionjgKey, AdmUsuarios usuario) throws Exception {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put(IDFACTURACION, fcsFacturacionjgKey.getIdfacturacion().toString());
        facturacionHelper.insertaColaConParametros(ecomCola, parametros, usuario);
    }

    private Boolean esGalicia(Short idInstitucion) {
        return SigaConstants.Consejos.C_GALEGA.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }

    private Boolean esCAM(Short idInstitucion) {
        return SigaConstants.Consejos.C_MADRID.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }


    private Boolean esCatalunya(Short idInstitucion) {
        return SigaConstants.Consejos.C_CATALUNYA.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }

    @Override
    public Resource getInformeCAM(String idFacturacion, String tipoFichero, HttpServletRequest request) throws Exception {
        Resource resource = null;
        File file = getFileInformeCAM(idFacturacion, tipoFichero, request);

        if (file != null) {
            resource = new ByteArrayResource(Files.readAllBytes(file.toPath())) {
                public String getFilename() {
                    return file.getName();
                }
            };

        }
        return resource;
    }


    private File getFileInformeCAM(String idFacturacion, String tipoFichero, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);


        File fichero = null;

        LOGGER.info("CertificacionFacSJCSServicesImpl.getFileInformeCAM() -> Entrada al servicio para obtener el fichero de informe CAM");

        fichero = camHelper.getInformeCAM(idInstitucion, idFacturacion, tipoFichero, request);

        LOGGER.info("CertificacionFacSJCSServicesImpl.getFileInformeCAM() -> Salida del servicio para obtener el fichero de informe CAM");

        return fichero;

    }


    public String getCodExtColegio(Short idInstitucion) {
        String colegio = null;

        if (mapaInstituciones == null) {
            mapaInstituciones = institucionesMapper.getInstitucionesConColegios().stream().collect(
                    Collectors.toMap(CenInstitucionExt::getIdinstitucion, CenInstitucionExt::getCodigoExtColegio));
        }

        colegio = mapaInstituciones.get(idInstitucion);

        return colegio;
    }

    @Override
    public UpdateResponseDTO subirFicheroCAM(String idFacturacion, MultipartFile fichero, MultipartHttpServletRequest request) {
        LOGGER.info("CertificacionFacSJCSServicesImpl.subirFicheroCAM() -> Entrada al servicio para subir el Fichero CAM");

        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();

        if (fichero.getSize() == 0l) {
            updateResponseDTO.setStatus(SigaConstants.KO);
            error.setDescription("message.cajg.ficheroValido");
            updateResponseDTO.setError(error);
        } else {
            try {
                Path pFile = camHelper.subirFicheroCAM(idInstitucion, idFacturacion, fichero, request);
                if (!facturacionHelper.isEjecutandoOperacionFacturacion(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.PCAJG_ALCALA_JE_FICHERO_ERROR.getId())) {
                    insertaEstadoFacturacion(facturacionHelper.getUsuario(idInstitucion, token), idInstitucion, idFacturacion);
                    envioWS(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.PCAJG_ALCALA_JE_FICHERO_ERROR.getId(), facturacionHelper.getUsuario(idInstitucion, token));
                    updateResponseDTO.setStatus(SigaConstants.OK);
                } else {
                    error.setCode(200);
                    error.setDescription("La operación ya se está ejecutando para la facturación");
                    updateResponseDTO.setStatus(SigaConstants.OK);
                    updateResponseDTO.setError(error);
                    LOGGER.error("La operación ya se está ejecutando para la facturación.");
                }
            } catch (Exception e) {
                LOGGER.error("error en subirFicheroCAM:" + e);
                updateResponseDTO.setStatus(SigaConstants.KO);
                error.setDescription("messages.general.error");
                updateResponseDTO.setError(error);
            }
        }

        return updateResponseDTO;
    }

    private void insertaEstadoFacturacion(AdmUsuarios usuario, Short idInstitucion, String idFacturacion) {
        String estadoActualFacturacion = fcsFactEstadosfacturacionExtendsMapper.getIdEstadoFacturacion(idInstitucion, idFacturacion);
        Integer estadoActualFac = Integer.valueOf(estadoActualFacturacion);

        if (estadoActualFac.equals(SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_EJECUTADA.getCodigo())) {
            String idOrdenEstado = fcsFactEstadosfacturacionExtendsMapper.getIdordenestadoMaximo(idInstitucion, idFacturacion);
            Integer estadoFuturo = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_VALIDACION_NO_CORRECTA.getCodigo();
            FcsFactEstadosfacturacion fcsFactEstadosfacturacion = new FcsFactEstadosfacturacion();
            fcsFactEstadosfacturacion.setIdinstitucion(idInstitucion);
            fcsFactEstadosfacturacion.setIdfacturacion(Integer.valueOf(idFacturacion));
            fcsFactEstadosfacturacion.setIdestadofacturacion(estadoFuturo.shortValue());
            fcsFactEstadosfacturacion.setIdordenestado(Short.valueOf(idOrdenEstado));
            fcsFactEstadosfacturacion.setFechaestado(new Date());
            fcsFactEstadosfacturacion.setFechamodificacion(new Date());
            fcsFactEstadosfacturacion.setUsumodificacion(usuario.getIdusuario());
            fcsFactEstadosfacturacionExtendsMapper.insert(fcsFactEstadosfacturacion);
        }
    }


    @Override
    public ComboDTO getComboEstadosCertificaciones(HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> Entrada al servicio para la obtencion del combo de estados de certificacion");

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
                        "CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info("CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> fcsCertificacionesExtendsMapper.getComboEstadosCertificaciones() -> INICIO consulta para obtener el combo");
                    List<ComboItem> comboItemList = fcsCertificacionesExtendsMapper.getComboEstadosCertificaciones(usuarios.get(0).getIdlenguaje());
                    LOGGER.info("CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> fcsCertificacionesExtendsMapper.getComboEstadosCertificaciones() -> FIN consulta para obtener el combo");
                    comboDTO.setCombooItems(comboItemList);

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error(
                    "CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones()) -> Se ha producido un error al intentar obtener el combo de estados de certificacion",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.getComboEstadosCertificaciones() -> Salida del servicio para la obtencion del combo de estados de certificacion");

        return comboDTO;
    }

    @Override
    public CertificacionesDTO buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> Entrada al servicio para la busqueda de certificaciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        CertificacionesDTO certificacionesDTO = new CertificacionesDTO();
        Error error = new Error();
        List<GenParametros> tamMax;
        Integer tamMaximo;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS")
                            .andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "CertificacionFacSJCSServicesImpl.buscarCertificaciones() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info(
                            "CertificacionFacSJCSServicesImpl.buscarCertificaciones() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> fcsCertificacionesExtendsMapper.buscarCertificaciones() -> INICIO consulta de la busqueda de certificaciones");
                    List<CertificacionesItem> certificacionesItemList = fcsCertificacionesExtendsMapper.buscarCertificaciones(busquedaRetencionesRequestDTO, tamMaximo, usuarios.get(0).getIdlenguaje());
                    LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> fcsCertificacionesExtendsMapper.buscarCertificaciones() -> FIN consulta de la busqueda de certificaciones");

                    if (null != certificacionesItemList && certificacionesItemList.size() > tamMaximo) {
                        certificacionesItemList.remove(certificacionesItemList.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    certificacionesDTO.setCertificacionesItemList(certificacionesItemList);
                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> Se ha producido un error al intentar buscar las certificaciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        certificacionesDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.buscarCertificaciones() -> Salida del servicio para la busqueda de certificaciones");

        return certificacionesDTO;
    }

    @Override
    public UpdateResponseDTO validaCatalunya(GestionEconomicaCatalunyaItem gestEcom, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        updateResponseDTO.setError(error)
        ;
        AdmUsuarios admUsr = facturacionHelper.getUsuario(idInstitucion, dni);

        try {
            cataHelper.valida(gestEcom, admUsr);
            updateResponseDTO.setStatus(SigaConstants.OK);
        } catch (Exception e) {
            LOGGER.error(e);
            error.setDescription(e.toString());
            updateResponseDTO.setStatus(SigaConstants.KO);
        }

        return updateResponseDTO;
    }


    @Override
    public DeleteResponseDTO eliminarCertificaciones(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Entrada al servicio para la eliminacion de certificaciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        deleteResponseDTO.setStatus(SigaConstants.OK);
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    List<CertificacionesItem> certisNoEliminar = new ArrayList<>();
                    List<Short> idCertisEliminar = new ArrayList<>();

                    // Si la certificación se encuentra en estado "ENVIANDO" no se puede eliminar
                    certificacionesItemList.forEach(el -> {
                        if (!el.getIdEstadoCertificacion().equalsIgnoreCase(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_ENVIANDO.getCodigo())) {
                            idCertisEliminar.add(Short.valueOf(el.getIdCertificacion()));
                        } else {
                            certisNoEliminar.add(el);
                        }
                    });

                    // Eliminamos los movimientos varios
                    FcsMvariosCertificacionesExample fcsMvariosCertificacionesExample = new FcsMvariosCertificacionesExample();
                    fcsMvariosCertificacionesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdcertificacionIn(idCertisEliminar);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsMvariosCertificacionesMapper.deleteByExample() -> INICIO eliminamos los movimientos varios");
                    fcsMvariosCertificacionesMapper.deleteByExample(fcsMvariosCertificacionesExample);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsMvariosCertificacionesMapper.deleteByExample() -> FIN eliminamos los movimientos varios");

                    // Eliminamos las facturaciones
                    FcsFactCertificacionesExample fcsFactCertificacionesExample = new FcsFactCertificacionesExample();
                    fcsFactCertificacionesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdcertificacionIn(idCertisEliminar);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsFactCertificacionesMapper.deleteByExample() -> INICIO eliminamos las facturaciones");
                    fcsFactCertificacionesMapper.deleteByExample(fcsFactCertificacionesExample);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsFactCertificacionesMapper.deleteByExample() -> FIN eliminamos las facturaciones");

                    // Eliminamos los estados de las certificaciones
                    FcsCertificacionesHistoricoEstadoExample fcsCertificacionesHistoricoEstadoExample = new FcsCertificacionesHistoricoEstadoExample();
                    fcsCertificacionesHistoricoEstadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdcertificacionIn(idCertisEliminar);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsCertificacionesHistoricoEstadoMapper.deleteByExample() -> INICIO eliminamos los estados de la certificacion");
                    fcsCertificacionesHistoricoEstadoMapper.deleteByExample(fcsCertificacionesHistoricoEstadoExample);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsCertificacionesHistoricoEstadoMapper.deleteByExample() -> FIN eliminamos los estados de la certificacion");

                    // Eliminamos las certificaciones
                    FcsCertificacionesExample fcsCertificacionesExample = new FcsCertificacionesExample();
                    fcsCertificacionesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdcertificacionIn(idCertisEliminar);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsCertificacionesExtendsMapper.deleteByExample() -> INICIO eliminamos las certificaciones");
                    fcsCertificacionesExtendsMapper.deleteByExample(fcsCertificacionesExample);
                    LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> fcsCertificacionesExtendsMapper.deleteByExample() -> FIN eliminamos las certificaciones");

                    if (!certisNoEliminar.isEmpty()) {
                        error.setCode(200);
                        String cadena = utilidadesFacturacionSJCS.getMensajeIdioma(usuarios.get(0).getIdlenguaje(), "factSJCS.certificaciones.eliminar.error");
                        cadena.replaceFirst("#1", String.valueOf(idCertisEliminar.size()));
                        cadena.replaceFirst("#2", String.valueOf(certificacionesItemList.size()));
                        error.setDescription(cadena);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Se ha producido un error al intentar eliminar las certificaciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Salida del servicio para la eliminacion de certificaciones");

        return deleteResponseDTO;
    }

    @Override
    public EstadoCertificacionDTO getEstadosCertificacion(String idCertificacion, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> Entrada al servicio para la obtencion de los estados de una certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        EstadoCertificacionDTO estadoCertificacionDTO = new EstadoCertificacionDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (!UtilidadesString.esCadenaVacia(idCertificacion)) {

                        LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> fcsCertificacionesExtendsMapper.getEstadosCertificacion() -> INICIO consulta para obtener los estados de la certificacion");
                        List<EstadoCertificacionItem> listaEstados = fcsCertificacionesExtendsMapper.getEstadosCertificacion(idCertificacion, idInstitucion, usuarios.get(0).getIdlenguaje());
                        LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> fcsCertificacionesExtendsMapper.getEstadosCertificacion() -> FIN consulta para obtener los estados de la certificacion");

                        estadoCertificacionDTO.setEstadoCertificacionItemList(listaEstados);

                    } else {
                        LOGGER.error("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> idCertificacion vacio");
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> Se ha producido un error al intentar obtener los estados de la certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        estadoCertificacionDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.getEstadosCertificacion() -> Salida del servicio para la obtencion de los estados de una certificacion");

        return estadoCertificacionDTO;
    }

    @Override
    public FacturacionDTO getFactCertificaciones(String idCertificacion, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.getFactCertificaciones() -> Entrada al servicio para la busqueda de facturaciones de certificaciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        FacturacionDTO facturacionDTO = new FacturacionDTO();
        Error error = new Error();
        List<GenParametros> tamMax;
        Integer tamMaximo;

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.getFactCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.getFactCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
                if (null != usuarios && !usuarios.isEmpty()) {

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS")
                            .andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "CertificacionFacSJCSServicesImpl.getFactCertificaciones() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info(
                            "CertificacionFacSJCSServicesImpl.getFactCertificaciones() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info(
                            "CertificacionFacSJCSServicesImpl.getFactCertificaciones() / fcsCertificacionesExtendsMapper.getFactCertificaciones() -> Entrada a fcsCertificacionesExtendsMapper para obtener las facturaciones");
                    List<FacturacionItem> facturacionItems = fcsCertificacionesExtendsMapper
                            .getFactCertificaciones(idCertificacion, idInstitucion.toString(), tamMaximo);

                    if (null != facturacionItems && facturacionItems.size() > tamMaximo) {
                        facturacionItems.remove(facturacionItems.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    facturacionDTO.setFacturacionItem(facturacionItems);


                }
            }
        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.getFactCertificaciones() -> Se ha producido un error al intentar obtener las facturaciones de certificaciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }
        return facturacionDTO;
    }

    @Override
    public InsertResponseDTO saveFactCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request) {
        LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Entrada al servicio para generar el informe de la certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        int response = 0;
        String idFacts = "";

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {
                    FcsFactCertificacionesKey fck = new FcsFactCertificacionesKey();
                    fck.setIdinstitucion(idInstitucion);
                    fck.setIdfacturacion(Integer.parseInt(certificacionesItem.getIdFacturacion()));
                    fck.setIdcertificacion(Short.parseShort(certificacionesItem.getIdCertificacion()));

                    FcsFactCertificaciones factCert = fcsFactCertificacionesMapper.selectByPrimaryKey(fck);

                    if (factCert != null) {
                        error.setCode(400);
                        error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.errorFactAsociada");
                        insertResponseDTO.setStatus(SigaConstants.KO);
                    } else {

                        //obtiene las facturaciones de la certificacion
                        FcsFactCertificacionesExample facturaciones = new FcsFactCertificacionesExample();
                        facturaciones.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                .andIdcertificacionEqualTo(Short.parseShort(certificacionesItem.getIdCertificacion()));
                        List<FcsFactCertificaciones> facts = fcsFactCertificacionesExtendsMapper.selectByExample(facturaciones);
                        if (facts != null) {
                            int cont = 0;
                            for (FcsFactCertificaciones f : facts) {
                                idFacts += f.getIdfacturacion();
                                cont++;
                                if (cont < facts.size()) {
                                    idFacts += ",";
                                }

                            }
                        }
                        //obtiene las fechas max y min de las facturaciones.
                        FcsCertificaciones fechasCert = null;
                        if(facts != null && !facts.isEmpty()) {
                            fechasCert = fcsFactCertificacionesExtendsMapper.getFechaMaxMinFact(idInstitucion, idFacts);
                        } else {
                            FcsFacturacionjgKey fcsFacturacionjgKey = new FcsFacturacionjgKey();
                            fcsFacturacionjgKey.setIdinstitucion(idInstitucion);
                            fcsFacturacionjgKey.setIdfacturacion(Integer.valueOf(certificacionesItem.getIdFacturacion()));
                            FcsFacturacionjg facturacion = fcsFacturacionJGExtendsMapper.selectByPrimaryKey(fcsFacturacionjgKey);

                            fechasCert = new FcsCertificaciones();
                            fechasCert.setFechadesde(facturacion.getFechadesde());
                            fechasCert.setFechahasta(facturacion.getFechahasta());
                        }

                        //actualiza la certificacion con las nuevas fechas.
                        FcsCertificaciones cert = new FcsCertificaciones();
                        cert.setIdinstitucion(idInstitucion);
                        cert.setIdcertificacion(Short.parseShort(certificacionesItem.getIdCertificacion()));
                        cert.setFechadesde(fechasCert.getFechadesde());
                        cert.setFechahasta(fechasCert.getFechahasta());
                        cert.setFechamodificacion(new Date());
                        cert.setUsumodificacion(usuarios.get(0).getIdusuario());
                        int updateCert = fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective(cert);
                        if (updateCert != 0) {
                            FcsFactCertificaciones fc = new FcsFactCertificaciones();
                            fc.setIdinstitucion(idInstitucion);
                            fc.setIdfacturacion(Integer.parseInt(certificacionesItem.getIdFacturacion()));
                            fc.setIdcertificacion(Short.parseShort(certificacionesItem.getIdCertificacion()));
                            fc.setUsumodificacion(usuarios.get(0).getIdusuario());
                            fc.setFechamodificacion(new Date());

                            response = fcsFactCertificacionesMapper.insert(fc);

                            if (response != 0) {
                                error.setCode(200);
                                error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.factAsociada");
                                insertResponseDTO.setStatus(SigaConstants.OK);
                            } else {
                                error.setCode(400);
                                error.setDescription("general.mensaje.error.bbdd");
                                insertResponseDTO.setStatus(SigaConstants.KO);
                            }
                        }

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Se ha producido un error al intentar generar el informe de la certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Salida del servicio para generar el informe de la certificacion");

        return insertResponseDTO;
    }

    @Override
    public DeleteResponseDTO delFactCertificacion(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request) {
        LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Entrada al servicio para la eliminacion de certificaciones");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        deleteResponseDTO.setStatus(SigaConstants.OK);
        Error error = new Error();
        int respose = 0;
        int enviado = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {
                    String idCertifacion = certificacionesItemList.get(0).getIdCertificacion();
                    FcsCertificacionesHistoricoEstadoExample cer = new FcsCertificacionesHistoricoEstadoExample();
                    cer.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdcertificacionEqualTo(Short.parseShort(idCertifacion));
                    List<FcsCertificacionesHistoricoEstado> listEstados = fcsCertificacionesHistoricoEstadoMapper.selectByExample(cer);
                    for (FcsCertificacionesHistoricoEstado histCert : listEstados) {
                        if (histCert.getIdestado().equals(5)) {
                            enviado++;
                        }
                    }

                    if (enviado == 0) {
                        for (CertificacionesItem cert : certificacionesItemList) {

                            // Eliminamos las facturaciones
                            FcsFactCertificacionesExample fcsFactCertificacionesExample = new FcsFactCertificacionesExample();
                            fcsFactCertificacionesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                    .andIdcertificacionEqualTo(Short.parseShort(cert.getIdCertificacion()))
                                    .andIdfacturacionEqualTo(Integer.parseInt(cert.getIdFacturacion()));

                            respose += fcsFactCertificacionesMapper.deleteByExample(fcsFactCertificacionesExample);

                        }

                        if (respose != 0) {
                            error.setCode(200);
                            error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.eliminado");
                            deleteResponseDTO.setStatus(SigaConstants.OK);
                        } else {
                            error.setCode(400);
                            error.setDescription("general.mensaje.error.bbdd");
                            deleteResponseDTO.setStatus(SigaConstants.KO);
                        }
                    } else {
                        error.setCode(400);
                        error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.errorEliminado");
                        deleteResponseDTO.setStatus(SigaConstants.KO);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Se ha producido un error al intentar eliminar las certificaciones", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.eliminarCertificaciones() -> Salida del servicio para la eliminacion de certificaciones");

        return deleteResponseDTO;
    }

    @Override
    public Resource descargaErrorValidacion(GestionEconomicaCatalunyaItem gestionVo, HttpServletRequest request)
            throws IOException {
        Resource resource = null;

        LOGGER.info("CertificacionFacSJCSServicesImpl.descargaErrorValidacion() -> Entrada al servicio para la descarga de errores de validacion");

        File file = cataHelper.descargaErrorValidacion(gestionVo);

        if (file != null) {
            resource = new ByteArrayResource(Files.readAllBytes(file.toPath())) {
                public String getFilename() {
                    return file.getName();
                }
            };

        }

        LOGGER.info("CertificacionFacSJCSServicesImpl.descargaErrorValidacion() -> Salida del servicio para la descarga de errores de validacion");

        return resource;
    }

    @Override
    public UpdateResponseDTO enviaRespuestaCICAC_ICA(GestionEconomicaCatalunyaItem gestEcom,
                                                     HttpServletRequest request) {
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        updateResponseDTO.setError(error)
        ;

        try {
            cataHelper.enviaRespuestaCICAC_ICA(gestEcom);
            updateResponseDTO.setStatus(SigaConstants.OK);
        } catch (Exception e) {
            LOGGER.error(e);
            error.setDescription(e.toString());
            updateResponseDTO.setStatus(SigaConstants.KO);
        }

        return updateResponseDTO;
    }

    @Override
    public Resource descargarCertificacionesXunta(DescargaCertificacionesXuntaItem descargaItem,
                                                  HttpServletRequest request) throws Exception {
        Resource resource = null;

        LOGGER.info("CertificacionFacSJCSServicesImpl.descargarCertificacionesXunta() -> Entrada al servicio para la descarga de certificaciones de la Xunta");

        File file = xuntaHelper.generaFicheroCertificacionesXunta(descargaItem.getIdInstitucion(), descargaItem.getLIdFacturaciones());

        if (file != null) {
            resource = new ByteArrayResource(Files.readAllBytes(file.toPath())) {
                public String getFilename() {
                    return file.getName();
                }
            };

        }

        LOGGER.info("CertificacionFacSJCSServicesImpl.descargarCertificacionesXunta() -> Salida del servicio para la descarga de certificaciones de la Xunta");

        return resource;
    }

    @Override
    public InsertResponseDTO reabrirFacturacion(List<CertificacionesItem> certificacionesItemList, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponse = new InsertResponseDTO();
        org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
        insertResponse.setError(error);
        int response = 0;
        int numReopenDone = 0;
        String factNoReabierta = "";

        if (null != idInstitucion) {
            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
            LOGGER.info(
                    "getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (null != usuarios && usuarios.size() > 0) {
                AdmUsuarios usuario = usuarios.get(0);
                usuario.setIdinstitucion(idInstitucion);

                LOGGER.info("reabrirFacturacion() -> Entrada para reabrir la facturacion");

                // GUARDAR DATOAS DE LA FACTURACION
                try {
                    // HACEMOS INSERT DEL ESTADO ABIERTA
                    LOGGER.info("reabrirFacturacion() -> Guardar datos para reabrir en fcsFactEstadosfacturacion");

                    for (CertificacionesItem cert : certificacionesItemList) {
                        //comprobacion de si la facturacion tiene pagos asociados
                        FcsPagosjgExample pagosAso = new FcsPagosjgExample();
                        pagosAso.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdfacturacionEqualTo(Integer.parseInt(cert.getIdFacturacion()));
                        List<FcsPagosjg> pagos = fcsPagosjgMapper.selectByExample(pagosAso);

                        if (pagos.isEmpty()) {
                            response += insertarEstado(SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ABIERTA.getCodigo(), idInstitucion,
                                    Integer.valueOf(cert.getIdFacturacion()), usuario.getIdusuario());
                        } else {
                            factNoReabierta += cert.getNombre() + "/";
                        }
                    }

                    if (response == certificacionesItemList.size() && response != 0) {
                        error.setCode(200);
                        error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.reabrirFact");
                        insertResponse.setStatus(SigaConstants.OK);
                    } else if (response != certificacionesItemList.size() && response != 0) {
                        error.setCode(200);
                        error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.reabrirAlgunas" + " " + factNoReabierta);
                        insertResponse.setStatus(SigaConstants.OK);
                    } else if (response == 0) {
                        error.setCode(400);
                        error.setDescription("fichaCertificacionSJCS.tarjetaFacturacion.reabrirConPagos");
                        insertResponse.setStatus(SigaConstants.KO);
                    }

                    LOGGER.info(
                            "reabrirFacturacion() -> Salida guardar datos para reabrir en fcsFactEstadosfacturacion");
                } catch (Exception e) {
                    LOGGER.error("ERROR: FacturacionServicesImpl.reabrirFacturacion() >  Al reabrir la facturacion.",
                            e);
                    error.setCode(400);
                    error.setDescription("general.mensaje.error.bbdd");
                    insertResponse.setStatus(SigaConstants.KO);
                }

                LOGGER.info("reabrirFacturacion() -> Salida para reabrir la facturacion");
            } else {
                LOGGER.warn(
                        "getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                + dni + " e idInstitucion = " + idInstitucion);
            }
        } else {
            LOGGER.warn("getLabel() -> idInstitucion del token nula");
        }

        LOGGER.info("getLabel() -> Salida del servicio para rabrir las facturaciones");

        insertResponse.setError(error);

        return insertResponse;
    }

    @Transactional
    private int insertarEstado(Integer codigoEstado, Short idInstitucion, Integer idFacturacion, Integer usuario) {
        NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(Short.valueOf(idInstitucion),
                String.valueOf(idFacturacion));
        Integer idOrdenEstado = (Integer.parseInt(idP.getNewId()) + 1);
        Short idEstado = codigoEstado.shortValue();

        FcsFactEstadosfacturacion record = new FcsFactEstadosfacturacion();
        record.setIdinstitucion(Short.valueOf(idInstitucion));
        record.setIdestadofacturacion(idEstado);
        record.setIdfacturacion(Integer.valueOf(idFacturacion));
        record.setFechaestado(new Date());
        record.setFechamodificacion(new Date());
        record.setUsumodificacion(usuario);
        record.setIdordenestado(idOrdenEstado.shortValue());

        return fcsFactEstadosfacturacionMapper.insert(record);
    }

    @Override
    public UpdateResponseDTO accionXuntaEnvios(EnvioXuntaItem envioItem, HttpServletRequest request) {
        Short idInstitucion = envioItem.getIdInstitucion();
        String idFacturacion = envioItem.getIdFacturacion();
        UpdateResponseDTO response = new UpdateResponseDTO();
        Error error = new Error();
        response.setError(error);

        try {
            OPERACION op = OPERACION.getEnum(envioItem.getCodigoOperacion());
            switch (op) {
                case XUNTA_ENVIO_JUSTIFICACION:
                    xuntaHelper.envioJustificacion(idInstitucion, idFacturacion);
                    response.setStatus(SigaConstants.OK);
                    break;

                case XUNTA_ENVIO_REINTEGROS:
                    xuntaHelper.envioReintegros(idInstitucion, idFacturacion);
                    response.setStatus(SigaConstants.OK);
                    break;

                default:
                    error.setDescription("operación no soportada");
                    response.setStatus(SigaConstants.KO);
                    break;
            }
        } catch (Exception e) {
            response.setStatus(SigaConstants.KO);
            error.setDescription("messages.general.error");

        }
        return response;
    }

    @Override
    public InsertResponseDTO createOrUpdateCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> Entrada al servicio para crear una certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (certificacionesItem != null && !UtilidadesString.esCadenaVacia(certificacionesItem.getNombre())) {

                        if (certificacionesItem.getIdCertificacion() == null) {

                            FcsCertificaciones certificacion = new FcsCertificaciones();
                            certificacion.setIdinstitucion(idInstitucion);
                            certificacion.setIdestadocertificacion(Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_ABIERTA.getCodigo()));
                            certificacion.setNombre(certificacionesItem.getNombre());
                            certificacion.setUsumodificacion(usuarios.get(0).getIdusuario());
                            certificacion.setFechamodificacion(new Date());
                            LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> INICIO de la query para insertar la certificacion");
                            int response = fcsCertificacionesExtendsMapper.insertSelective(certificacion);
                            LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> FIN de la query para insertar la certificacion");

                            if (response == 1) {
                                insertResponseDTO.setStatus(SigaConstants.OK);
                                LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.getCurrentValueSequence() -> INICIO de la query para obtener el idcertificacion");
                                Short idCertificacion = fcsCertificacionesExtendsMapper.getCurrentValueSequence(SigaConstants.SEQUENCE_CERTIFICACIONES);
                                LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.getCurrentValueSequence() -> FIN de la query para obtener el idcertificacion");
                                insertResponseDTO.setId(idCertificacion.toString());

                                if (idCertificacion != null) {

                                    FcsCertificacionesHistoricoEstado estado = new FcsCertificacionesHistoricoEstado();
                                    estado.setIdcertificacion(idCertificacion);
                                    estado.setIdinstitucion(idInstitucion);
                                    estado.setFechaestado(new Date());
                                    estado.setIdestado(Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_ABIERTA.getCodigo()));
                                    estado.setFechamodificacion(new Date());
                                    estado.setUsumodificacion(usuarios.get(0).getIdusuario());
                                    LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> INICIO de la query para insertar el estado de la certificacion");
                                    fcsCertificacionesHistoricoEstadoMapper.insertSelective(estado);
                                    LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> FIN de la query para insertar el estado de la certificacion");
                                }
                            } else {
                                insertResponseDTO.setStatus(SigaConstants.KO);
                                error.setDescription("general.message.error.realiza.accion");
                                error.setCode(500);
                            }

                        } else {
                            FcsCertificaciones certificacion = new FcsCertificaciones();
                            certificacion.setIdinstitucion(idInstitucion);
                            certificacion.setIdcertificacion(Short.valueOf(certificacionesItem.getIdCertificacion()));
                            certificacion.setNombre(certificacionesItem.getNombre());
                            certificacion.setFechamodificacion(new Date());
                            certificacion.setUsumodificacion(usuarios.get(0).getIdusuario());
                            int response = fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective(certificacion);

                            if (response == 1) {
                                insertResponseDTO.setStatus(SigaConstants.OK);
                                insertResponseDTO.setId(certificacionesItem.getIdCertificacion());
                            } else {
                                insertResponseDTO.setStatus(SigaConstants.KO);
                                error.setDescription("general.message.error.realiza.accion");
                                error.setCode(500);
                            }

                        }


                    } else {
                        insertResponseDTO.setStatus(SigaConstants.KO);
                        error.setDescription("general.message.camposObligatorios");
                        error.setCode(400);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> Se ha producido un error al intentar crear una nueva certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> Salida del servicio para crear una certificacion");

        return insertResponseDTO;
    }

    @Override
    public UpdateResponseDTO reabrirCertificacion(CertificacionesItem certificacionesItem, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> Entrada al servicio para reabrir una certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (certificacionesItem != null && !UtilidadesString.esCadenaVacia(certificacionesItem.getIdCertificacion())) {

                        FcsCertificacionesKey fcsCertificacionesKey = new FcsCertificacionesKey();
                        fcsCertificacionesKey.setIdinstitucion(idInstitucion);
                        fcsCertificacionesKey.setIdcertificacion(Short.valueOf(certificacionesItem.getIdCertificacion()));

                        FcsCertificaciones fcsCertificaciones = fcsCertificacionesExtendsMapper.selectByPrimaryKey(fcsCertificacionesKey);

                        if (fcsCertificaciones != null) {

                            if (fcsCertificaciones.getIdestadocertificacion().toString().equalsIgnoreCase(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_CERRADA.getCodigo())) {

                                FcsCertificacionesHistoricoEstado estado = new FcsCertificacionesHistoricoEstado();
                                estado.setIdcertificacion(fcsCertificaciones.getIdcertificacion());
                                estado.setIdinstitucion(fcsCertificaciones.getIdinstitucion());
                                estado.setFechaestado(new Date());
                                estado.setIdestado(Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_ENVIO_CON_ERRORES.getCodigo()));
                                estado.setFechamodificacion(new Date());
                                estado.setUsumodificacion(usuarios.get(0).getIdusuario());
                                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> INICIO de la query para insertar el estado de la certificacion");
                                fcsCertificacionesHistoricoEstadoMapper.insertSelective(estado);
                                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> fcsCertificacionesExtendsMapper.insertSelective() -> FIN de la query para insertar el estado de la certificacion");

                                fcsCertificaciones.setIdestadocertificacion(Short.valueOf(SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_ENVIO_CON_ERRORES.getCodigo()));
                                fcsCertificaciones.setFechamodificacion(new Date());
                                fcsCertificaciones.setUsumodificacion(usuarios.get(0).getIdusuario());
                                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective() -> INICIO de la query para aztualizar la certificacion");
                                fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective(fcsCertificaciones);
                                LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> fcsCertificacionesExtendsMapper.updateByPrimaryKeySelective() -> FIN de la query para aztualizar la certificacion");

                                updateResponseDTO.setStatus(SigaConstants.OK);
                                updateResponseDTO.setId(certificacionesItem.getIdCertificacion());

                            } else {
                                updateResponseDTO.setStatus(SigaConstants.KO);
                                error.setDescription("general.message.error.realiza.accion");
                                error.setCode(500);
                            }

                        }

                    } else {
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        error.setDescription("general.message.camposObligatorios");
                        error.setCode(400);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.nuevaCertificacion() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> Se ha producido un error al intentar reabrir una certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        updateResponseDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.reabrirCertificacion() -> Salida del servicio para reabrir una certificacion");

        return updateResponseDTO;
    }

    @Override
    public MovimientosVariosAsoCerDTO getMvariosAsociadosCertificacion(String idCertificacion, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() -> Entrada al servicio para obtener los movimientos varios asociados a una certificacion");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        MovimientosVariosAsoCerDTO movimientosVariosAsoCerDTO = new MovimientosVariosAsoCerDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (!UtilidadesString.esCadenaVacia(idCertificacion)) {

                        List<MovimientosVariosAsoCerItem> movimientosVariosAsoCerItemList = fcsCertificacionesExtendsMapper.getMvariosAsociadosCertificacion(idCertificacion, idInstitucion);
                        List<Long> listaIdMovimientos = movimientosVariosAsoCerItemList.stream().map(el -> Long.valueOf(el.getIdMovimiento())).collect(Collectors.toList());
                        List<AsuntoPorMovimientoItem> asuntoPorMovimientoItemList = new ArrayList<>();

                        if (!listaIdMovimientos.isEmpty()) {
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoActuacionDesignaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoActuacionAsistenciaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoAsistenciaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoGuardiaPorMovimientos(idInstitucion, listaIdMovimientos));
                        }

                        if (!asuntoPorMovimientoItemList.isEmpty()) {
                            asuntoPorMovimientoItemList.forEach(el -> {
                                MovimientosVariosAsoCerItem movimiento = movimientosVariosAsoCerItemList.stream().filter(m -> m.getIdMovimiento().equals(el.getIdMovimiento().toString())).findFirst().get();
                                movimiento.setAsunto(el.getAsunto());
                            });
                        }

                        movimientosVariosAsoCerDTO.setMovimientosVariosAsoCerItemList(movimientosVariosAsoCerItemList);
                    } else {
                        error.setDescription("general.message.error.realiza.accion");
                        error.setCode(400);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() -> Se ha producido un error al intentar obtener los movimientos varios asociados a una certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        movimientosVariosAsoCerDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAsociadosCertificacion() -> Salida del servicio para obtener los movimientos varios asociados a una certificacion");

        return movimientosVariosAsoCerDTO;
    }

    @Override
    public MovimientosVariosApliCerDTO getMvariosAplicadosEnPagosEjecutadosPorPeriodo(MovimientosVariosApliCerRequestDTO movimientosVariosApliCerRequestDTO, HttpServletRequest request) {
        LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() -> Entrada al servicio para obtener los movimientos varios aplicados en pagos ejecutados durante un periodo");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        MovimientosVariosApliCerDTO movimientosVariosApliCerDTO = new MovimientosVariosApliCerDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (movimientosVariosApliCerRequestDTO.getFechaDesde() != null && movimientosVariosApliCerRequestDTO.getFechaHasta() != null) {

                        List<MovimientosVariosApliCerItem> movimientosVariosApliCerItemList = fcsCertificacionesExtendsMapper.getMvariosAplicadosEnPagosEjecutadosPorPeriodo(idInstitucion, movimientosVariosApliCerRequestDTO.getFechaDesde(),
                                movimientosVariosApliCerRequestDTO.getFechaHasta());
                        List<Long> listaIdMovimientos = movimientosVariosApliCerItemList.stream().map(el -> Long.valueOf(el.getIdMovimiento())).collect(Collectors.toList());
                        List<AsuntoPorMovimientoItem> asuntoPorMovimientoItemList = new ArrayList<>();

                        if (!listaIdMovimientos.isEmpty()) {
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoActuacionDesignaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoActuacionAsistenciaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoAsistenciaPorMovimientos(idInstitucion, listaIdMovimientos));
                            asuntoPorMovimientoItemList.addAll(fcsCertificacionesExtendsMapper.getAsuntoGuardiaPorMovimientos(idInstitucion, listaIdMovimientos));
                        }

                        if (!asuntoPorMovimientoItemList.isEmpty()) {
                            asuntoPorMovimientoItemList.forEach(el -> {
                                MovimientosVariosApliCerItem movimiento = movimientosVariosApliCerItemList.stream().filter(m -> m.getIdMovimiento().equals(el.getIdMovimiento().toString())).findFirst().get();
                                movimiento.setAsunto(el.getAsunto());
                            });
                        }

                        movimientosVariosApliCerDTO.setMovimientosVariosApliCerItemList(movimientosVariosApliCerItemList);

                    } else {
                        error.setDescription("general.message.error.realiza.accion");
                        error.setCode(400);
                    }

                } else {
                    LOGGER.warn(
                            "CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } else {
                LOGGER.warn("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() -> Se ha producido un error al intentar obtener obtener los movimientos varios aplicados en pagos ejecutados durante un periodo", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        movimientosVariosApliCerDTO.setError(error);

        LOGGER.info("CertificacionFacSJCSServicesImpl.getMvariosAplicadosEnPagosEjecutadosPorPeriodo() -> Salida del servicio para obtener los movimientos varios aplicados en pagos ejecutados durante un periodo");

        return movimientosVariosApliCerDTO;
    }

    @Override
    public void marcaVisiblesFacturacionesCerradas() {

        CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
        exampleInstitucion.setDistinct(true);
        exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull().andCenInstIdinstitucionEqualTo(Short.valueOf("3005"));

        List<CenInstitucion> listaInstituciones = institucionesMapper.selectByExample(exampleInstitucion);

        for (CenInstitucion institucion : listaInstituciones) {

            List<Integer> facturaciones = fcsFacturacionJGExtendsMapper.getFacturacionesCerradasPorInstitucion(institucion.getIdinstitucion());

            FcsFacturacionjgExample fcsFacturacionjgExample = new FcsFacturacionjgExample();
            fcsFacturacionjgExample.createCriteria().andIdinstitucionEqualTo(institucion.getIdinstitucion()).andIdfacturacionIn(facturaciones);

            FcsFacturacionjg fcsFacturacionjg = new FcsFacturacionjg();
            fcsFacturacionjg.setFechamodificacion(new Date());
            fcsFacturacionjg.setUsumodificacion(0);
            fcsFacturacionjg.setVisible("1");

            fcsFacturacionJGExtendsMapper.updateByExampleSelective(fcsFacturacionjg, fcsFacturacionjgExample);

        }

    }

    @Override
    public ResponseEntity<InputStreamResource> descargarLogReintegrosXunta(List<String> idFactsList, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream fileStream = null;
        ResponseEntity<InputStreamResource> res = null;
        HttpHeaders headers = new HttpHeaders();
        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            if(!idFactsList.isEmpty() || idFactsList.size() !=0 || idFactsList != null){
                for (int i = 0; i < idFactsList.size();i ++) {


                    String path = getDirectorioFichero("FCS", SigaConstants.PATH_PREVISIONES_BD, idInstitucion);
                    path += File.separator + "LOG_ERROR_" + idInstitucion + "_" + idFactsList.get(i) + ".log";
                    File file = new File(path);
                    if(file.exists()) {
                        zipOutputStream.putNextEntry(new ZipEntry(file.getName() + ".txt"));
                        FileInputStream fileInputStream = new FileInputStream(file);
                        IOUtils.copy(fileInputStream, zipOutputStream);
                        fileInputStream.close();
                    }
                }

                fileStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                headers.setContentType(MediaType.parseMediaType("application/zip"));
                headers.set("Content-Disposition", "attachment; filename=\"Reintegros_Xunta_Error_Log\"");
                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.OK);

                zipOutputStream.closeEntry();
            }else{
                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.FORBIDDEN);
            }

            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.flush();
                IOUtils.closeQuietly(zipOutputStream);
            }

            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public ResponseEntity<InputStreamResource> descargarInformeIncidencias(List<String> idFactsList, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream fileStream = null;
        ResponseEntity<InputStreamResource> res = null;
        HttpHeaders headers = new HttpHeaders();
        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            if(!idFactsList.isEmpty() || idFactsList.size() !=0 || idFactsList != null){
                for (int i = 0; i < idFactsList.size();i ++) {

                    File file = getFileInformeIncidencias(idInstitucion,idFactsList.get(i));
                    if(file.exists()) {
                        zipOutputStream.putNextEntry(new ZipEntry(file.getName() + ".txt"));
                        FileInputStream fileInputStream = new FileInputStream(file);
                        IOUtils.copy(fileInputStream, zipOutputStream);
                        fileInputStream.close();
                    }
                }

                fileStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                headers.setContentType(MediaType.parseMediaType("application/zip"));
                headers.set("Content-Disposition", "attachment; filename=\"Informe_Incidencias\"");
                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.OK);

                zipOutputStream.closeEntry();
            }else{
                res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
                        HttpStatus.FORBIDDEN);
            }

            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.flush();
                IOUtils.closeQuietly(zipOutputStream);
            }

            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    private String getDirectorioFichero(String modulo, String parametro, Short idinstitucion) {
        GenParametrosKey path = new GenParametrosKey();

        String valor = "";
        path.setIdinstitucion(idinstitucion);
        path.setModulo(modulo);
        path.setParametro(parametro);

        valor = genParametrosMapper.selectByPrimaryKey(path).getValor();
        return valor;
    }

    public File getFileInformeIncidencias(Short idInstitucion, String idFacturacion) {
        ReadProperties rp = new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
        File file = new File(rp.returnProperty("informes.directorioFisicoSalidaInformesJava")
                + SigaConstants.FILE_SEP + "informeIncidenciasWS"
                + SigaConstants.FILE_SEP + idInstitucion
                + SigaConstants.FILE_SEP + idFacturacion);

        new File(file.getAbsolutePath()).mkdirs();
        file = new File (file, "InformeIncididencias.csv");

        return file;
    }
}
