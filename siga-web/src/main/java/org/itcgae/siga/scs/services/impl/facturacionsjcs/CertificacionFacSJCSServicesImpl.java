package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucionExt;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.EcomColaParametrosExample;
import org.itcgae.siga.db.entities.EcomOperacion;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacion;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactEstadosfacturacionExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICertificacionFacSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class CertificacionFacSJCSServicesImpl implements ICertificacionFacSJCSService {
	
	private static final String IDFACTURACION = "IDFACTURACION";

	private static Map<Short,String> mapaInstituciones=null;

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
    private EcomOperacionMapper ecomOperacionMapper;

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
        
        if(!esCAM(idInstitucion)) {
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
        insertaColaConParametros(ecomCola, parametros, usuario);
    }

    private void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros, AdmUsuarios usuario) throws Exception {

        insertartCola(ecomCola, usuario);

        if (parametros != null) {
            Iterator<String> it = parametros.keySet().iterator();

            while (it.hasNext()) {
                EcomColaParametros ecomColaParametros = new EcomColaParametros();
                String clave = it.next();
                String valor = parametros.get(clave);
                ecomColaParametros.setIdecomcola(ecomCola.getIdecomcola());
                ecomColaParametros.setClave(clave);
                ecomColaParametros.setValor(valor);
                if (ecomColaParametrosMapper.insert(ecomColaParametros) != 1) {
                    throw new Exception("Error al insertar los parámetros de la cola.");
                }
            }
        }
    }

    private int insertartCola(EcomCola ecomCola, AdmUsuarios usuario) {

        try {
            ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.INICIAL.getId());
            ecomCola.setReintento(0);
            ecomCola.setFechacreacion(new Date());
            ecomCola.setUsumodificacion(usuario.getIdusuario());

            return ecomColaMapper.insert(ecomCola);
        } catch (Exception e) {
            LOGGER.error(String.format("Se ha producido un error al insertar en la cola %s", ecomCola));
            throw e;
        }
    }
    
    
    private Boolean esGalicia(Short idInstitucion) {
    	return SigaConstants.Consejos.C_GALEGA.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }
    
    private Boolean esCAM(Short idInstitucion) {
    	return  SigaConstants.Consejos.C_MADRID.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }
    
    
    private Boolean esCatalunya(Short idInstitucion) {
    	return  SigaConstants.Consejos.C_CATALUNYA.getCodigoExt().equals(getCodExtColegio(idInstitucion));
    }

	@Override
	public Resource getInformeCAM(String idFacturacion, String tipoFichero, HttpServletRequest request)	throws Exception {
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
		
		if(mapaInstituciones==null) {
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
        
		if(fichero.getSize()==0l) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			error.setDescription("message.cajg.ficheroValido");
			updateResponseDTO.setError(error);
		} else {
			try {
				Path pFile = camHelper.subirFicheroCAM(idInstitucion,idFacturacion, fichero, request);
				if(!isEjecutandoFacturacion(idInstitucion, idFacturacion,  SigaConstants.ECOM_OPERACION.PCAJG_ALCALA_JE_FICHERO_ERROR.getId())) {	
					insertaEstadoFacturacion(camHelper.getUsuario(idInstitucion, token),idInstitucion,idFacturacion);
					envioWS(idInstitucion, idFacturacion, SigaConstants.ECOM_OPERACION.PCAJG_ALCALA_JE_FICHERO_ERROR.getId(), camHelper.getUsuario(idInstitucion, token));
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
	        	Integer estadoFuturo  = SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_VALIDACION_NO_CORRECTA.getCodigo(); 
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
			}else {
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

	
}
