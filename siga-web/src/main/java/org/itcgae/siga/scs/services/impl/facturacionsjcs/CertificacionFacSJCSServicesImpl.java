package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.DTOs.scs.CertificacionesDTO;
import org.itcgae.siga.DTOs.scs.CertificacionesItem;
import org.itcgae.siga.DTOs.scs.GestionEconomicaCatalunyaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsCertificacionesExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactEstadosfacturacionExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public InsertResponseDTO tramitarCertificacion(String idFacturacion, HttpServletRequest request) {

        LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Entrada al servicio para generar el informe de la certificacion");

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
                LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() / admUsuariosExtendsMapper.selectByExample() -> " +
                        "Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {
                    listaParaConsejo(idInstitucion, idFacturacion, usuarios.get(0));
                }

            }

        } catch (Exception e) {
            LOGGER.error("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Se ha producido un error al intentar generar el informe de la certificacion", e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        if (error.getDescription() == null) {
            insertResponseDTO.setStatus(SigaConstants.OK);
        } else {
            insertResponseDTO.setStatus(SigaConstants.KO);
        }

        LOGGER.info("CertificacionFacSJCSServicesImpl.generaInformeCertificacion() -> Salida del servicio para generar el informe de la certificacion");

        return insertResponseDTO;
    }

    private void listaParaConsejo(Short idInstitucion, String idFacturacion, AdmUsuarios usuario) throws Exception {

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
        }

        //TODO CAMBIAR LA OPERACIÓN
        if (SigaConstants.TIPO_CAJG_CATALANES == tipoCAJG) {
            envioWS(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.ECOM2_CAT_VALIDA_JUSTIFICACION.getId(), usuario);
            estadoFuturo = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_EN_PROCESO.getCodigo();
        }

        if (esCAM(idInstitucion)) {
            /* en el caso de la CAM el fichero ya se ha generado previamente al ejecutar informe
             *  averiguar qué implicaciones tiene en el estado en el caso de la CAM
             */

        }

        if (!esCAM(idInstitucion)) {
            actualizarEstadoFacturacion(usuario, idInstitucion, idFacturacion, estadoFuturo);
        }

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
                if (!isEjecutandoFacturacion(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.PCAJG_ALCALA_JE_FICHERO_ERROR.getId())) {
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


    private boolean isEjecutandoFacturacion(Short idinstitucion, String idFacturacion, int operacionCompruebaEnEjecucion) {
        boolean ejecutandose = false;

        EcomColaParametrosExample ecomColaParametrosExample = new EcomColaParametrosExample();

        ecomColaParametrosExample.createCriteria().andClaveEqualTo(IDFACTURACION).andValorEqualTo(idFacturacion.toString());
        List<EcomColaParametros> listaEcomColaParametros = ecomColaParametrosMapper.selectByExample(ecomColaParametrosExample);

        if (listaEcomColaParametros != null && listaEcomColaParametros.size() > 0) {
            LOGGER.debug("Posibles candidatos para ver si la facturación ha sido ejecutada o se está ejecutando");
            List<Long> ids = new ArrayList<Long>();
            for (EcomColaParametros ecomColaParametros : listaEcomColaParametros) {
                ids.add(ecomColaParametros.getIdecomcola());
            }

            List<Short> listaEstados = new ArrayList<Short>();
            listaEstados.add(ECOM_ESTADOSCOLA.INICIAL.getId());
            listaEstados.add(ECOM_ESTADOSCOLA.EJECUTANDOSE.getId());
            listaEstados.add(ECOM_ESTADOSCOLA.REINTENTANDO.getId());

            EcomColaExample ecomColaExample = new EcomColaExample();
            ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaIn(listaEstados).andIdecomcolaIn(ids);
            long count = ecomColaMapper.countByExample(ecomColaExample);
            if (count > 0) {
                ejecutandose = true;
            } else {
                EcomOperacion operacion = ecomOperacionMapper.selectByPrimaryKey(operacionCompruebaEnEjecucion);
                ecomColaExample = new EcomColaExample();
                ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaEqualTo(ECOM_ESTADOSCOLA.ERROR.getId()).andReintentoLessThan(operacion.getMaxreintentos()).andIdecomcolaIn(ids);
                count = ecomColaMapper.countByExample(ecomColaExample);
                if (count > 0)
                    ejecutandose = true;

            }
        }

        LOGGER.debug("¿La facturación del colegio " + idinstitucion + " con idFacturacion " + idFacturacion + " está en ejecución? = '" + ejecutandose + "'");

        return ejecutandose;
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
		 AdmUsuarios admUsr =  facturacionHelper.getUsuario(idInstitucion, dni);
		 
		 try {
			 cataHelper.valida(gestEcom, admUsr);
			 updateResponseDTO.setStatus(SigaConstants.OK);
		 } catch(Exception e) {
			 LOGGER.error(e);
			 error.setDescription(e.toString());
			 updateResponseDTO.setStatus(SigaConstants.KO);
		 }
		 
		 return updateResponseDTO;
	}
	
	
	
}
