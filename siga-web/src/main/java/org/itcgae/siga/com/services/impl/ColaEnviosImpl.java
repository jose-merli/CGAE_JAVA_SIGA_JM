package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IColaEnvios;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.FORMATO_SALIDA;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvCamposenviosKey;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDestinatariosBurosms;
import org.itcgae.siga.db.entities.EnvDestinatariosExample;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvDocumentosExample;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosgrupocliente;
import org.itcgae.siga.db.entities.EnvEnviosgrupoclienteExample;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.EnvCamposenviosMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosBurosmsMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosgrupoclienteMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestConsultaEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ColaEnviosImpl implements IColaEnvios {
	
	private Logger LOGGER = Logger.getLogger(ColaEnviosImpl.class);


	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;

	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;

	@Autowired
	private CenDireccionesMapper _cenDireccionesMapper;

	@Autowired
	private IEnviosService _enviosService;
	
	@Autowired
	private EnvDocumentosMapper _envDocumentosMapper;

	@Autowired
	private EnvConsultasenvioMapper _envConsultasenvioMapper;

	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;

	@Autowired
	private CenPersonaMapper _cenPersonaMapper;
	
	@Autowired
	private IConsultasService _consultasService;
	
	@Autowired
	private IDialogoComunicacionService _dialogoComunicacionService;
	
	@Autowired
	private EnvEnviosgrupoclienteMapper _envEnviosgrupoclienteMapper;
	
	@Autowired
	private CenGruposclienteClienteMapper _cenGruposclienteClienteMapper;
	
	@Autowired
	private EnvDestinatariosMapper _envDestinatariosMapper;
	
	@Autowired
	private EnvDestConsultaEnvioExtendsMapper _envDestConsultaEnvioExtendsMapper;
	
	@Autowired
	private EnvCamposenviosMapper _envCamposenviosMapper;
	
	@Autowired
	private EnvDestinatariosBurosmsMapper envDestinatariosBurosmsMapper;
	
	@Autowired
	private EnvDocumentosMapper envDocumentosMapper;
	
	@Autowired
	private IEnviosMasivosService _enviosMasivosService;
	
	List<Exception> listaErrores = new ArrayList<Exception>();
	
	
	//@Transactional
	@Scheduled(cron = "${cron.pattern.scheduled.Envios: 0 * * ? * *}")
	@Override
	public void execute() {
		
		EnvEnvios envio = null;
		try{
			
			List<EnvEnvios> enviosProgramados = _envEnviosExtendsMapper.obtenerEnviosProgramados();
			if (enviosProgramados != null && enviosProgramados.size() > 0) {

				for (EnvEnvios envEnvioprogramado : enviosProgramados) {
					envio = envEnvioprogramado;
					LOGGER.info("Listener envios => Se ha encontrado envio programado con ID: " + envio.getIdenvio());
					envio.setIdestado(SigaConstants.ENVIO_PROCESANDO);
					envio.setFechamodificacion(new Date());
					_envEnviosMapper.updateByPrimaryKey(envio);
					
					switch (envio.getIdtipoenvios().toString()) {

						case SigaConstants.ID_ENVIO_MAIL:
							preparaCorreo(envio);					
							LOGGER.info("Correo electrónico enviado con éxito");							
							break;
						case SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO:
							preparaCorreo(envio);
							LOGGER.info("Documentación letrado enviado con éxito");
							break;
						case SigaConstants.ID_ENVIO_CORREO_ORDINARIO:
							preparaCorreo(envio);
							LOGGER.info("Correo ordinario generado con éxito");
							break;
						case SigaConstants.ID_ENVIO_SMS:
							preparaEnvioSMS(envio, false);
							LOGGER.info("SMS enviado con éxito");
							break;
						case SigaConstants.ID_ENVIO_BURO_SMS:
							preparaEnvioSMS(envio, true);
							LOGGER.info("BURO SMS enviado con éxito");
							break;
						}
					
					if(this.listaErrores.size() == 0) {
						LOGGER.info("Envio programado realizado con exito");
					}else if(this.listaErrores.size() > 0){
						LOGGER.info("El envio programado se ha procesado con errores");
						
						envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
						envio.setFechamodificacion(new Date());
						_envEnviosMapper.updateByPrimaryKey(envio);
						generaLogGenerico(envio.getIdinstitucion(), envio, "");	
					}
					
					this.listaErrores.clear();
				}

			}
			List<EnvEnvios> enviosBasura = _envEnviosExtendsMapper.obtenerEnviosMalCreados();
			if (enviosBasura != null && enviosBasura.size() > 0) {
				for (EnvEnvios envioBasura : enviosBasura) {
					envio = envioBasura;
					LOGGER.info("Listener envios => Se ha encontrado envio mal creado y se procederá a su cambio de estado a Error con ID: " + envio.getIdenvio());
					envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
					envio.setFechamodificacion(new Date());
					_envEnviosMapper.updateByPrimaryKey(envio);
				}
			}
			
		}catch(Exception e){
			LOGGER.error("Error al procesar el envío", e);
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
			envio.setFechamodificacion(new Date());
			_envEnviosMapper.updateByPrimaryKey(envio);
			e.printStackTrace();
			// Se genera un log con los errores ocurridos
			generaLogGenerico(envio.getIdinstitucion(), envio, e.getMessage());
		}
		
	}

	@Override
	public void preparaCorreo(EnvEnvios envio) throws Exception {
		
		// Obtenemos la plantilla de envio
		EnvPlantillasenviosKey keyPlantilla = new EnvPlantillasenviosKey();
		keyPlantilla.setIdinstitucion(envio.getIdinstitucion());
		keyPlantilla.setIdplantillaenvios(envio.getIdplantillaenvios());
		keyPlantilla.setIdtipoenvios(envio.getIdtipoenvios());
		EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(keyPlantilla);
		
		if (plantilla == null || plantilla.getIdplantillaenvios() == null) {
			String mensaje = "No se ha encontrado la plantilla de envío en el sistema";
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje);
		}
		
		if (plantilla.getIdpersona() == null) {
			String mensaje = "La plantilla de envío no tiene un remitente asociado";
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje);
		}
		
		if (plantilla.getIddireccion() == null) {
			String mensaje = "La plantilla de envío no tiene una dirección de envío asociada";
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje);
		}

		// obtenemos la direccion del remitente de la plantilla
		CenDireccionesKey keyDireccion = new CenDireccionesKey();
		keyDireccion.setIddireccion(plantilla.getIddireccion());
		keyDireccion.setIdpersona(plantilla.getIdpersona());
		keyDireccion.setIdinstitucion(envio.getIdinstitucion());
		CenDirecciones remitente = _cenDireccionesMapper.selectByPrimaryKey(keyDireccion);
		
		if (remitente == null) {
			String mensaje = "No se ha encontrado la dirección del remitente";
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje);
		}
		
		if (remitente.getFechabaja() != null) {
			String mensaje = "La dirección del remitente se encuentra de baja en el sistema.";
			LOGGER.error(mensaje);
			throw new BusinessException(mensaje);
		}
		
		
		CenPersona personaRemitente = _cenPersonaMapper.selectByPrimaryKey(plantilla.getIdpersona());
		RemitenteDTO remitentedto = new RemitenteDTO();

		if(plantilla.getDescripcionRemitente() != null && !plantilla.getDescripcionRemitente().trim().equals("")) {
			remitentedto.setNombre(plantilla.getDescripcionRemitente());
			remitentedto.setApellido1("");
			
			if(remitente != null) {
				remitentedto.setCorreoElectronico(remitente.getCorreoelectronico());
			}
		}else {
			remitentedto.setNombre(personaRemitente.getNombre());
			remitentedto.setApellido1(personaRemitente.getApellidos1());
			
			if(personaRemitente.getApellidos2() != null) {
				remitentedto.setApellido2(personaRemitente.getApellidos2());
			}
			if(remitente != null) {
				remitentedto.setCorreoElectronico(remitente.getCorreoelectronico());
			}
		}

		
		//Obtenemos los destinatarios dependendiendo del tipo de envio.
		boolean envioMasivo = envio.getEnvio().contains("M") ? true : false;
		
		Short idEstadoEnvio = SigaConstants.ENVIO_PROCESADO;

		if(envioMasivo){
			
			List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
			//Obtenemos destinatarios de las etiquetas de envio
			addDestintatariosEtiquetas(envio, destinatarios, false, null);
			//Obtenemos destinatarios individuales.
			addDestinatariosIndividuales(envio.getIdinstitucion(), envio.getIdenvio(), destinatarios, false, null);
			//obtenemos los destinatarios por consultas de destinatarios
			addDestinatariosConsultas(envio, destinatarios, false, null);
			
			
			List<Map<String, Object>> resultadosConsultas = new ArrayList<Map<String, Object>>();
			addResultadoConsultaDatosPlantillaEnvio(envio, resultadosConsultas);
			
			String asuntoFinal = getAsuntoFinal(envio, resultadosConsultas, plantilla.getAsunto());
			String cuerpoFinal = getCuerpoFinal(envio, resultadosConsultas, plantilla.getCuerpo());
			
			//Generamos los informes para adjuntarlos al envio
			List<DatosDocumentoItem> documentosEnvio = new ArrayList<DatosDocumentoItem>();
			addDocumentosAdjuntos(envio, documentosEnvio);			

			
			if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL)){
				idEstadoEnvio = _enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
			}else{
				
				if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO)){					
					idEstadoEnvio = _enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, null, envioMasivo);
				}
				//Añadimos los informes al envio para que puedan ser descargados.
				for (DatosDocumentoItem datosDocumentoItem : documentosEnvio) {
					EnvDocumentos documento = new EnvDocumentos();
					documento.setIdinstitucion(envio.getIdinstitucion());
					documento.setIdenvio(envio.getIdenvio());
					documento.setPathdocumento(datosDocumentoItem.getPathDocumento());
					documento.setDescripcion(datosDocumentoItem.getFileName());
					documento.setFechamodificacion(new Date());
					documento.setUsumodificacion(1);
					_envDocumentosMapper.insert(documento);		
				}
				
				for (DestinatarioItem destinatario : destinatarios) {
					destinatario.getNombre();
					destinatario.getApellidos1();
					destinatario.getApellidos2();
					destinatario.getDomicilio();
				}
			}
			
			envio.setIdestado(idEstadoEnvio);
			envio.setFechamodificacion(new Date());
			_envEnviosMapper.updateByPrimaryKey(envio);

		}else{
			
			EnvDestinatariosExample exampleDest = new EnvDestinatariosExample();
			exampleDest.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvDestinatarios> destinatariosEntities = _envDestinatariosMapper.selectByExample(exampleDest);
			if(destinatariosEntities != null && destinatariosEntities.size() > 0){
				List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
				for (EnvDestinatarios dest : destinatariosEntities) {
					DestinatarioItem destinatario = new DestinatarioItem();
					destinatario.setIdPersona(dest.getIdpersona().toString());
					destinatario.setNombre(dest.getNombre());
					destinatario.setApellidos1(dest.getApellidos1());
					destinatario.setApellidos2(dest.getApellidos2());
					destinatario.setCorreoElectronico(dest.getCorreoelectronico());
					destinatario.setMovil(dest.getMovil());
					destinatarios.add(destinatario);
				}
				
				
				LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
				
				List<Map<String, Object>> resultadosConsultas = new ArrayList<Map<String, Object>>();
				// Obtenemos las consultas asociadas a la plantilla de envio.
				addResultadoConsultaDatosPlantillaEnvio(envio, resultadosConsultas);				
				
				
				// Obtenemos el asunto y el cuerpo del envio
				String asuntoFinal = getAsuntoFinal(envio, resultadosConsultas, plantilla.getAsunto());
				String cuerpoFinal = getCuerpoFinal(envio, resultadosConsultas, plantilla.getCuerpo());
				
				//Generamos los informes para adjuntarlos al envio
				List<DatosDocumentoItem> documentosEnvio = _dialogoComunicacionService.generarDocumentosEnvio(envio.getIdinstitucion().toString(), envio.getIdenvio().toString());
				
				LOGGER.debug("Procedemos al envio del email: tipo " + envio.getIdtipoenvios() + "--" + envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL));
				
				
				if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL)){
					idEstadoEnvio = _enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
				}else{
					if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO)){
						idEstadoEnvio = _enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, null, envioMasivo);
					}
					//Añadimos los informes al envio para que puedan ser descargados.
//					for (DatosDocumentoItem datosDocumentoItem : documentosEnvio) {
//						EnvDocumentos documento = new EnvDocumentos();
//						documento.setIdinstitucion(envio.getIdinstitucion());
//						documento.setIdenvio(envio.getIdenvio());
//						documento.setPathdocumento(datosDocumentoItem.getPathDocumento());
//						documento.setDescripcion(datosDocumentoItem.getFileName());
//						documento.setFechamodificacion(new Date());
//						documento.setUsumodificacion(1);
//						_envDocumentosMapper.insert(documento);		
//					}
//					
//					for (DestinatarioItem destinatario : destinatarios) {
//						destinatario.getNombre();
//						destinatario.getApellidos1();
//						destinatario.getApellidos2();
//						destinatario.getDomicilio();
//					}
				}
				
			}else{
				LOGGER.error("No se han encontrado consultas de destinatarios asociadas a la plantilla de envío");
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
				envio.setFechamodificacion(new Date());
				_envEnviosMapper.updateByPrimaryKey(envio);
			}
			
		}
		envio.setIdestado(idEstadoEnvio);
		envio.setFechamodificacion(new Date());
		_envEnviosMapper.updateByPrimaryKey(envio);
	}

	private String getAsuntoFinal(EnvEnvios envio, List<Map<String,Object>> resultadosConsultas, String asunto) {
		return getAsuntoCuerpoFinal(envio, resultadosConsultas, asunto, SigaConstants.ID_CAMPO_ASUNTO);
	}

	private String getAsuntoCuerpoFinal(EnvEnvios envio, List<Map<String, Object>> resultadosConsultas, String asunto,	String idCampoAsunto) {
		if (asunto == null) {
			asunto = "";
		}

		EnvCamposenviosKey key = new EnvCamposenviosKey();
		key.setIdcampo(Short.parseShort(idCampoAsunto));
		key.setIdenvio(envio.getIdenvio());
		key.setIdinstitucion(envio.getIdinstitucion());
		key.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);

		EnvCamposenvios envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);

		if (envCampo != null && envCampo.getValor() != null) {
			asunto = envCampo.getValor();
		}

		asunto = remplazarCamposAsunto(asunto, resultadosConsultas);
		return asunto;
	}

	private String getCuerpoFinal(EnvEnvios envio, List<Map<String, Object>> resultadosConsultas, String cuerpo) {
		return getAsuntoCuerpoFinal(envio, resultadosConsultas, cuerpo, SigaConstants.ID_CAMPO_CUERPO);

	}

	private void addDocumentosAdjuntos(EnvEnvios envio, List<DatosDocumentoItem> documentosEnvio) {
		EnvDocumentosExample exampleDoc = new EnvDocumentosExample();
		exampleDoc.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
		List<EnvDocumentos> documentos = _envDocumentosMapper.selectByExample(exampleDoc);
		
		if (documentos != null) {
			for (EnvDocumentos documento : documentos) {
				DatosDocumentoItem doc = new DatosDocumentoItem();
				doc.setFileName(documento.getDescripcion());
				String path = _enviosMasivosService.getPathFicheroEnvioMasivo(envio.getIdinstitucion(), envio.getIdenvio(),null);
				File file = new File(path, documento.getPathdocumento());
				doc.setDocumentoFile(file);
				doc.setPathDocumento(documento.getPathdocumento());
				documentosEnvio.add(doc);
			}
		}
	}

	private void addResultadoConsultaDatosPlantillaEnvio(EnvEnvios envio, List<Map<String, Object>> resultadosConsultas) {
		
		// Obtenemos las consultas asociadas de datos a la plantilla de envio.
		EnvConsultasenvioExample exampleConsulta = new EnvConsultasenvioExample();
		Long objetivoDatos = new Long(4);
		exampleConsulta.createCriteria().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdenvioEqualTo(envio.getIdenvio()).andFechabajaIsNull().andIdobjetivoEqualTo(objetivoDatos);

		List<EnvConsultasenvio> consultasDatosPlantilla = _envConsultasenvioMapper.selectByExampleWithBLOBs(exampleConsulta);

		// Las ejecutamos y obtenemos los resultados
		
		for (EnvConsultasenvio consultaDatos : consultasDatosPlantilla) {
			String sentencia = consultaDatos.getConsulta();
			
			sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
			
			if(sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER) || sentencia.contains(SigaConstants.SENTENCIA_CREATE)
					|| sentencia.contains(SigaConstants.SENTENCIA_DELETE) || sentencia.contains(SigaConstants.SENTENCIA_DROP)
					|| sentencia.contains(SigaConstants.SENTENCIA_INSERT) || sentencia.contains(SigaConstants.SENTENCIA_UPDATE))){
				
				LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
			}else {
				List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
				resultadosConsultas.addAll(result);
			}
		}
		
	}

	private void addDestintatariosEtiquetas(EnvEnvios envio, List<DestinatarioItem> destinatarios, boolean isBuroSMS, List<EnvDestinatariosBurosms> listEnvDestinatariosBurosms) {
		EnvEnviosgrupoclienteExample exampleEtiquetas = new EnvEnviosgrupoclienteExample();
		exampleEtiquetas.createCriteria().andIdenvioEqualTo(envio.getIdenvio());
		List<EnvEnviosgrupocliente> etiquetasEnvio = _envEnviosgrupoclienteMapper.selectByExample(exampleEtiquetas);
		
		
		for (EnvEnviosgrupocliente envEnviosgrupocliente : etiquetasEnvio) {
			CenGruposclienteClienteExample etiqueta = new CenGruposclienteClienteExample();
			etiqueta.createCriteria().andIdgrupoEqualTo(envEnviosgrupocliente.getIdgrupo()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<CenGruposclienteCliente> personas = _cenGruposclienteClienteMapper.selectByExample(etiqueta);
			for (CenGruposclienteCliente persona : personas) {

				//Obtenemos la persona
				CenPersona cenPersona = _cenPersonaMapper.selectByPrimaryKey(persona.getIdpersona());
				LOGGER.info("PRUEBA ENVIOS ETIQUETAS : " + cenPersona.toString());

				//Buscamos las direcciones de esa persona
				CenDireccionesExample exampleDir = new CenDireccionesExample();
				
				String preferente = SigaConstants.TIPO_PREFERENTE_CORREOELECTRONICO;
				//Obtenemos la direccion preferente de la persona
				if (SigaConstants.ID_ENVIO_SMS.equals(envio.getIdtipoenvios().toString()) || SigaConstants.ID_ENVIO_BURO_SMS.equals(envio.getIdtipoenvios().toString())) {
					preferente = SigaConstants.TIPO_PREFERENTE_SMS;
				}
				exampleDir.createCriteria().andIdpersonaEqualTo(persona.getIdpersona()).andIdinstitucionEqualTo(persona.getIdinstitucion()).andFechabajaIsNull().andPreferenteLike("%" + preferente + "%");
				
				List<CenDirecciones> direcciones =  _cenDireccionesMapper.selectByExample(exampleDir);
				
				if(direcciones == null || direcciones.size() == 0){
					exampleDir = new CenDireccionesExample();
					exampleDir.createCriteria().andIdpersonaEqualTo(persona.getIdpersona()).andIdinstitucionEqualTo(persona.getIdinstitucion()).andFechabajaIsNull();
					direcciones =  _cenDireccionesMapper.selectByExample(exampleDir);
				}
				
				if(direcciones != null && direcciones.size() > 0){
					boolean añadido = false;
					for (CenDirecciones dir : direcciones) {
						DestinatarioItem destinatario = null;
						if((SigaConstants.ID_ENVIO_MAIL.equals(envio.getIdtipoenvios().toString()) || SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO.equals(envio.getIdtipoenvios().toString())) && dir.getCorreoelectronico() != null) {
							if(!añadido){
								destinatario = new DestinatarioItem();
								añadido = true;
//								LOGGER.info("PRUEBA ENVIOS ETIQUETAS : " + destinatarios.toString());
							}
						}

						if((SigaConstants.ID_ENVIO_SMS.equals(envio.getIdtipoenvios().toString()) || SigaConstants.ID_ENVIO_BURO_SMS.equals(envio.getIdtipoenvios().toString())) && dir.getMovil() != null) {
							if(!añadido){
								destinatario = new DestinatarioItem();
								añadido = true;
								addDestBuroSMS(isBuroSMS, listEnvDestinatariosBurosms, envio.getIdenvio(), dir.getIdinstitucion(), dir.getIdpersona(), dir.getMovil());
							}
						}
						
						if (destinatario != null) {
							destinatario.setCorreoElectronico(dir.getCorreoelectronico());
							destinatario.setNombre(cenPersona.getNombre());
							destinatario.setApellidos1(cenPersona.getApellidos1());
							destinatario.setApellidos2(cenPersona.getApellidos2());
							destinatario.setNIFCIF(cenPersona.getNifcif());
							destinatario.setMovil(dir.getMovil());
							destinatario.setIdPersona(String.valueOf(persona.getIdpersona()));
							destinatarios.add(destinatario);
						}
						
						insertaEnvDestinatarios(envio, dir, persona);
					}
				}
			}
		}
	}
	
	protected void insertaEnvDestinatarios(EnvEnvios etiquetasDTO, CenDirecciones direccion, CenGruposclienteCliente cenPersona) {

		try {

			EnvDestinatariosExample ejemplo = new EnvDestinatariosExample();
				
			if(direccion.getCorreoelectronico() == null) {
				ejemplo.createCriteria().andIdinstitucionEqualTo(cenPersona.getIdinstitucion())
				.andIdenvioEqualTo(etiquetasDTO.getIdenvio())
				.andIdpersonaEqualTo(cenPersona.getIdpersona());
			}else {
				ejemplo.createCriteria().andIdinstitucionEqualTo(cenPersona.getIdinstitucion())
	            .andIdenvioEqualTo(etiquetasDTO.getIdenvio())
	            .andIdpersonaEqualTo(cenPersona.getIdpersona())
		        .andCorreoelectronicoEqualTo(direccion.getCorreoelectronico());
			}

			List<EnvDestinatarios> destinatariosExistentes = _envDestinatariosMapper
					.selectByExample(ejemplo);
	
	//		LOGGER.info("PRUEBA ENVIOS ETIQUETAS : " + cenPersona.getNifcif() + "  IDGRUPO = " + etiquetasDTO.getEtiquetasSeleccionadas()[i].getValue());
			if (destinatariosExistentes.size() > 0) {
				
					CenPersona personaTag = _cenPersonaMapper
							.selectByPrimaryKey(cenPersona.getIdpersona());
					CenDireccionesKey key = new CenDireccionesKey();
					key.setIddireccion(direccion.getIddireccion());
					key.setIdpersona(cenPersona.getIdpersona());
					key.setIdinstitucion(cenPersona.getIdinstitucion());
					CenDirecciones dir = _cenDireccionesMapper.selectByPrimaryKey(key);
					EnvDestinatarios dest = new EnvDestinatarios();
					dest.setApellidos1(personaTag.getApellidos1());
					dest.setApellidos2(personaTag.getApellidos2());
					if(dir != null) {
					if(dir.getCodigopostal() != null) dest.setCodigopostal(dir.getCodigopostal());
					dest.setCorreoelectronico(dir.getCorreoelectronico());
					dest.setDomicilio(dir.getDomicilio());
					dest.setFax1(dir.getFax1());
					dest.setFax2(dir.getFax2());
					dest.setFechamodificacion(new Date());
					dest.setIdenvio(etiquetasDTO.getIdenvio());
					dest.setIdinstitucion(cenPersona.getIdinstitucion());
					dest.setOrigendestinatario(Short.parseShort("1"));
					dest.setIdpais(dir.getIdpais());
					dest.setIdpersona(personaTag.getIdpersona());
					dest.setIdpoblacion(dir.getIdpoblacion());
					dest.setIdprovincia(dir.getIdprovincia());
					dest.setMovil(dir.getMovil());
					dest.setNifcif(personaTag.getNifcif());
					dest.setNombre(personaTag.getNombre());
					dest.setPoblacionextranjera(dir.getPoblacionextranjera());
					dest.setUsumodificacion(2);
					dest.setTipodestinatario("CEN_PERSONA");
					_envDestinatariosMapper.updateByExample(dest, ejemplo);
	//				LOGGER.info("PRUEBA ENVIOS ETIQUETAS UPDATE");
	//				respuesta.setCode(200);
	//				respuesta.setDescription("Destinatario asociado con éxito");
					}
				
			} else {
			
					CenPersona personaTag = _cenPersonaMapper
							.selectByPrimaryKey(cenPersona.getIdpersona());
					CenDireccionesKey key = new CenDireccionesKey();
					key.setIddireccion(direccion.getIddireccion());
					key.setIdpersona(cenPersona.getIdpersona());
					key.setIdinstitucion(cenPersona.getIdinstitucion());
					CenDirecciones dir = _cenDireccionesMapper.selectByPrimaryKey(key);
					EnvDestinatarios dest = new EnvDestinatarios();
					dest.setApellidos1(personaTag.getApellidos1());
					dest.setApellidos2(personaTag.getApellidos2());
					if(dir != null) {
					if(dir.getCodigopostal() != null) dest.setCodigopostal(dir.getCodigopostal());
					dest.setCorreoelectronico(dir.getCorreoelectronico());
					dest.setDomicilio(dir.getDomicilio());
					dest.setFax1(dir.getFax1());
					dest.setFax2(dir.getFax2());
					dest.setFechamodificacion(new Date());
					dest.setIdenvio(etiquetasDTO.getIdenvio());
					dest.setIdinstitucion(cenPersona.getIdinstitucion());
					dest.setOrigendestinatario(Short.parseShort("1"));
					dest.setIdpais(dir.getIdpais());
					dest.setIdpersona(personaTag.getIdpersona());
					dest.setIdpoblacion(dir.getIdpoblacion());
					dest.setIdprovincia(dir.getIdprovincia());
					dest.setMovil(dir.getMovil());
					dest.setNifcif(personaTag.getNifcif());
					dest.setNombre(personaTag.getNombre());
					dest.setPoblacionextranjera(dir.getPoblacionextranjera());
					dest.setUsumodificacion(2);
					dest.setTipodestinatario("CEN_PERSONA");
	//				LOGGER.info("PRUEBA ENVIOS ETIQUETAS INSERT");
					_envDestinatariosMapper.insert(dest);
	//				respuesta.setDescription("Destinatario asociado con éxito");
					}
				}
		}catch(Exception e) {
			this.listaErrores.add(e);
		}
	}

	private void addDestinatariosConsultas(EnvEnvios envio, List<DestinatarioItem> destinatarios, boolean isBuroSMS, List<EnvDestinatariosBurosms> listEnvDestinatariosBurosms) {
				
		List<ConsultaItem> consultaItem = _envDestConsultaEnvioExtendsMapper.selectConsultasDestEnvio(envio.getIdinstitucion(), envio.getIdenvio().toString());
		if (consultaItem != null) {
			for (ConsultaItem consulta : consultaItem) {
				String sentenciaFinal = prepararConsulta(consulta.getSentencia(), envio.getIdtipoenvios(), envio.getIdinstitucion());
				List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentenciaFinal);
				if(result != null && result.size() > 0){
					
					for (Map<String, Object> map : result) {
						
            			DestinatarioItem destinatario = new DestinatarioItem();
            			Object campo = map.get("CORREOELECTRONICO");
            			if (campo != null) {
            				destinatario.setCorreoElectronico(campo.toString());
            			}
            			
            			campo = map.get("MOVIL");
            			if (campo != null) {
            				destinatario.setMovil(campo.toString());	
            			}
            			
            			campo = map.get("NOMBRE");
            			destinatario.setNombre(campo!=null? String.valueOf(campo):"");
            			
            			campo = map.get("APELLIDOS1");
            			destinatario.setApellidos1(campo!=null? String.valueOf(campo):"");
            			
            			campo = map.get("APELLIDOS2");
            			destinatario.setApellidos2(campo!=null? String.valueOf(campo):"");
            			
            			campo = map.get("NIFCIF");
            			destinatario.setNIFCIF(campo!=null? String.valueOf(campo):"");
            			
            			campo = map.get("IDPERSONA");
            			destinatario.setIdPersona(campo!=null? String.valueOf(campo):"");
            			
            			Long idPersona = null;
            			if (destinatario.getIdPersona() != null && !destinatario.getIdPersona().trim().equals("")) {
            				idPersona = Long.parseLong(destinatario.getIdPersona());
            				if (destinatario.getNIFCIF() == null || destinatario.getNIFCIF().trim().equals("")) {
            					CenPersona cenPersona = _cenPersonaMapper.selectByPrimaryKey(idPersona);
            					if (cenPersona != null) {
            						destinatario.setNIFCIF(cenPersona.getNifcif());
            						destinatario.setNombre(cenPersona.getNombre());
            						destinatario.setApellidos1(cenPersona.getApellidos1());
            						destinatario.setApellidos2(cenPersona.getApellidos2());
            					}
            				}
            			}
            			

            			destinatarios.add(destinatario);
            			
            			//si la consulta tiene movil idpersona e idinstitucion se añade		            			
            			addDestBuroSMS(isBuroSMS, listEnvDestinatariosBurosms, envio.getIdenvio(), envio.getIdinstitucion(), idPersona, destinatario.getMovil());

	    				//Obtenemos la persona
	    				CenPersona cenPersona = _cenPersonaMapper.selectByPrimaryKey(Long.parseLong(String.valueOf(map.get("IDPERSONA"))));
	    				
	    					//Creamos la persona con CenGruposclienteCliente que es como lo va a uar insertaEnvDestinatarios().
	    				CenGruposclienteCliente persona = new CenGruposclienteCliente();
	    				persona.setIdpersona(cenPersona.getIdpersona());
	    				persona.setIdinstitucion(Short.parseShort(String.valueOf(map.get("IDINSTITUCION"))));
	    				
	    				//Buscamos las direcciones de esa persona
	    				CenDireccionesExample exampleDir = new CenDireccionesExample();
	    				
	    				//Obtenemos la direccion preferente de la persona
	    				exampleDir.createCriteria().andIdpersonaEqualTo(Long.parseLong(String.valueOf(map.get("IDPERSONA")))).andIdinstitucionEqualTo(Short.parseShort(String.valueOf(map.get("IDINSTITUCION")))).andFechabajaIsNull().andPreferenteLike("%" + SigaConstants.TIPO_PREFERENTE_CORREOELECTRONICO + "%");
	    				List<CenDirecciones> direcciones =  _cenDireccionesMapper.selectByExample(exampleDir);
	    				if(direcciones.size() > 0) insertaEnvDestinatarios(envio, direcciones.get(0), persona);
					}
				}
			}
		}
		
		
		LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
		
		
		
	}

	private void addDestinatariosIndividuales(Short idinstitucion, Long idenvio, List<DestinatarioItem> destinatarios, boolean isBuroSMS, List<EnvDestinatariosBurosms> listEnvDestinatariosBurosms) {
		if (idenvio != null && idinstitucion != null) {
			LOGGER.debug("Buscamos los destinatarios individuales para el idinstitucion " + idinstitucion + " e idenvio = " + idenvio);
			EnvDestinatariosExample example = new EnvDestinatariosExample();
			example.createCriteria().andIdenvioEqualTo(idenvio).andIdinstitucionEqualTo(idinstitucion);
			List<EnvDestinatarios> destIndv = _envDestinatariosMapper.selectByExample(example);
			if (destIndv != null) {
				for (EnvDestinatarios destinatario : destIndv) {
					DestinatarioItem dest = new DestinatarioItem();
					dest.setCorreoElectronico(destinatario.getCorreoelectronico());
					dest.setNombre(destinatario.getNombre());
					dest.setApellidos1(destinatario.getApellidos1());
					dest.setApellidos2(destinatario.getApellidos2());
					dest.setNIFCIF(destinatario.getNifcif());
					dest.setMovil(destinatario.getMovil());
					dest.setIdPersona(String.valueOf(destinatario.getIdpersona()));
					destinatarios.add(dest);
					addDestBuroSMS(isBuroSMS, listEnvDestinatariosBurosms, idenvio, idinstitucion, destinatario.getIdpersona(), destinatario.getMovil());
				}
			}
		}
	}

	

	public void preparaEnvioSMS(EnvEnvios envio, boolean isBuroSMS) {

		String idSolicitudEcos ="";
		// Obtenemos la plantilla de envio
		EnvPlantillasenviosKey keyPlantilla = new EnvPlantillasenviosKey();
		keyPlantilla.setIdinstitucion(envio.getIdinstitucion());
		keyPlantilla.setIdplantillaenvios(envio.getIdplantillaenvios());
		keyPlantilla.setIdtipoenvios(envio.getIdtipoenvios());
		EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(keyPlantilla);

		// obtenemos la direccion del remitente de la plantilla
		CenDireccionesKey keyDireccion = new CenDireccionesKey();
		keyDireccion.setIddireccion(plantilla.getIddireccion());
		keyDireccion.setIdpersona(plantilla.getIdpersona());
		keyDireccion.setIdinstitucion(envio.getIdinstitucion());
		CenDirecciones remitente = _cenDireccionesMapper.selectByPrimaryKey(keyDireccion);

		//Obtenemos los destinatarios dependendiendo del tipo de envio.
		boolean envioMasivo = envio.getEnvio().contains("M") ? true : false;

		List<EnvDestinatariosBurosms> listEnvDestinatariosBurosms = new ArrayList<EnvDestinatariosBurosms>();
		
		if(envioMasivo){
			List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
			//Obtenemos destinatarios de las etiquetas de envio
			addDestintatariosEtiquetas(envio, destinatarios, isBuroSMS, listEnvDestinatariosBurosms);
			//Obtenemos destinatarios individuales.
			addDestinatariosIndividuales(envio.getIdinstitucion(), envio.getIdenvio(), destinatarios, isBuroSMS, listEnvDestinatariosBurosms);
			//obtenemos los destinatarios por consultas de destinatarios
			addDestinatariosConsultas(envio, destinatarios, isBuroSMS, listEnvDestinatariosBurosms);			
			
			LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
			
		} else {
			EnvDestinatariosExample exampleDest = new EnvDestinatariosExample();
			exampleDest.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvDestinatarios> destinatariosEntities = _envDestinatariosMapper.selectByExample(exampleDest);
			if(destinatariosEntities != null && destinatariosEntities.size() > 0){
				
				List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
				for (EnvDestinatarios dest : destinatariosEntities) {
					DestinatarioItem destinatario = new DestinatarioItem();
					destinatario.setIdPersona(dest.getIdpersona().toString());
					destinatario.setNombre(dest.getNombre());
					destinatario.setApellidos1(dest.getApellidos1());
					destinatario.setApellidos2(dest.getApellidos2());
					destinatario.setCorreoElectronico(dest.getCorreoelectronico());
					destinatario.setMovil(dest.getMovil());
					destinatarios.add(destinatario);
					
					addDestBuroSMS(isBuroSMS, listEnvDestinatariosBurosms, envio.getIdenvio(), envio.getIdinstitucion(), dest.getIdpersona(), dest.getMovil());
				}
				
				LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
			}else{
				LOGGER.error("No se han encontrado destinatarios asociados a la plantilla de envío");
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
				envio.setFechamodificacion(new Date());
				_envEnviosMapper.updateByPrimaryKey(envio);
			}
		}
			
		
		// Obtenemos las consultas asociadas a la plantilla de envio.
		EnvConsultasenvioExample exampleConsulta = new EnvConsultasenvioExample();
		Long objetivoDatos = new Long(4);
		exampleConsulta.createCriteria().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdenvioEqualTo(envio.getIdenvio()).andFechabajaIsNull().andIdobjetivoEqualTo(objetivoDatos);

		List<EnvConsultasenvio> consultasDatosPlantilla = _envConsultasenvioMapper.selectByExampleWithBLOBs(exampleConsulta);

		// Las ejecutamos y obtenemos los resultados
		List<Map<String, Object>> resultadosConsultas = new ArrayList<Map<String, Object>>();
		for (EnvConsultasenvio consultaDatos : consultasDatosPlantilla) {				
			String sentencia = consultaDatos.getConsulta();				
			sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
			
			if(sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER) || sentencia.contains(SigaConstants.SENTENCIA_CREATE)
					|| sentencia.contains(SigaConstants.SENTENCIA_DELETE) || sentencia.contains(SigaConstants.SENTENCIA_DROP)
					|| sentencia.contains(SigaConstants.SENTENCIA_INSERT) || sentencia.contains(SigaConstants.SENTENCIA_UPDATE))){
				
				LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
			}else {
				List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
				resultadosConsultas.addAll(result);
			}				
		}
//		String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo(), resultadosConsultas);

		String cuerpoEnvio = "";
		
		EnvCamposenviosKey key = new EnvCamposenviosKey();
		key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_TEXTO_SMS));

		key.setIdenvio(envio.getIdenvio());
		key.setIdinstitucion(envio.getIdinstitucion());
		key.setTipocampo(SigaConstants.ID_TIPO_CAMPO_SMS);
		
		EnvCamposenvios envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
		if(envCampo != null && envCampo.getValor() != null) {
			cuerpoEnvio = envCampo.getValor();
		}else{
			cuerpoEnvio = plantilla.getCuerpo();
		}
				
		String cuerpoFinal = remplazarCamposCuerpo(cuerpoEnvio, resultadosConsultas); 
		
		// Realizamos el envio por SMS para cada destiantario
		boolean hayError = false;
		int buroSMSenviados = 0;
		String mensajeError = "Error al enviar el sms";
		
		for(EnvDestinatariosBurosms envDestinatariosBurosms: listEnvDestinatariosBurosms) {
			String[] dest = new String[1];
			dest[0] = envDestinatariosBurosms.getMovil();
			
			try {
				DestinatarioItem destinatarioItem = envDestinatariosBurosms2DestinatarioItem(envDestinatariosBurosms);
				List<DestinatarioItem> listDestinatarioItems = new ArrayList<DestinatarioItem>();
				listDestinatarioItems.add(destinatarioItem);
				idSolicitudEcos = _enviosService.envioSMS(remitente, listDestinatarioItems, envio, cuerpoFinal, isBuroSMS);
				LOGGER.debug("El idSolicitudEcos para el número " + envDestinatariosBurosms.getMovil() + " es " + idSolicitudEcos);
				if (idSolicitudEcos != null && !idSolicitudEcos.trim().equals("")) {
					if (isBuroSMS) {
						
					
					//añadimos un documento vacío para que al descargar desde la web vayamos a buscar el pdf a la pfd
					EnvDocumentos envDocumentos = addEnvDocument(envio.getIdenvio(), envio.getIdinstitucion(), envDestinatariosBurosms.getMovil());
					if (envDocumentos != null) {
						LOGGER.debug("El identificador del documento insertado es " + envDocumentos.getIddocumento());
					}	
					
					envDestinatariosBurosms.setIdsolicitudecos(idSolicitudEcos);
					envDestinatariosBurosms.setIddocumento(envDocumentos.getIddocumento());
					
					buroSMSenviados += envDestinatariosBurosmsMapper.insert(envDestinatariosBurosms);	
					}
				}else{
					hayError = true;
					mensajeError = "Error al enviar el sms al destinatario: " + envDestinatariosBurosms.getMovil();
					LOGGER.error(mensajeError);
					
				}
			}catch(Exception e) {
				hayError = true;
				LOGGER.error("Error al enviar el sms al destinatario: " + envDestinatariosBurosms.getMovil(), e);
				throw new BusinessException("Error al enviar el sms al destinatario: " + envDestinatariosBurosms.getMovil(), e);
			}
		}
		
		if(hayError) {
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
			generaLogGenerico(envio.getIdinstitucion(), envio, mensajeError);
		}else{
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
			if (buroSMSenviados == 0) {
				generaLogGenerico(envio.getIdinstitucion(), envio, "No se ha realizado el envío");
			} else {
				generaLogGenerico(envio.getIdinstitucion(), envio, SigaConstants.OK);
			}
			
		}
		
		envio.setFechamodificacion(new Date());
		envio.setIdsolicitudecos(idSolicitudEcos);
		_envEnviosMapper.updateByPrimaryKey(envio);
		
	}
	
	private DestinatarioItem envDestinatariosBurosms2DestinatarioItem(EnvDestinatariosBurosms envDestinatariosBurosms) {
		DestinatarioItem returnDestinatario = new DestinatarioItem();
		CenPersona cenPersona = _cenPersonaMapper.selectByPrimaryKey(envDestinatariosBurosms.getIdpersona());
		
		if (cenPersona != null) {
			returnDestinatario.setNIFCIF(cenPersona.getNifcif());
			returnDestinatario.setNombre(cenPersona.getNombre());
			returnDestinatario.setApellidos1(cenPersona.getApellidos1());
			returnDestinatario.setApellidos2(cenPersona.getApellidos2());
		}

		returnDestinatario.setIdPersona(envDestinatariosBurosms.getIdpersona().toString());
//		returnDestinatario.setCorreoElectronico(envDestinatariosBurosms.getCorreoelectronico());
		returnDestinatario.setMovil(envDestinatariosBurosms.getMovil());
		
		return returnDestinatario;
	}

	private EnvDocumentos addEnvDocument(Long idenvio, Short idinstitucion, String movil) {
		EnvDocumentos envDocumentos = new EnvDocumentos();
		envDocumentos.setIdenvio(idenvio);
		envDocumentos.setIdinstitucion(idinstitucion);
		envDocumentos.setDescripcion(movil + "." + FORMATO_SALIDA.PDF.getDescripcion());
		envDocumentos.setPathdocumento(movil + "." + FORMATO_SALIDA.PDF.getDescripcion());
		envDocumentos.setUsumodificacion(1);
		envDocumentos.setFechamodificacion(new Date());
		envDocumentosMapper.insert(envDocumentos);
		return envDocumentos;		
	}
	
	/**
	 * Genera un fichero excel de log genérico.
	 */
	private void generaLogGenerico(Short idInstitucion, EnvEnvios envio, String error) {
		Sheet sheet = null;
		error = "No se ha podido realizar el envio";
		
		try {
			sheet = _enviosService.creaLogGenericoExcel(envio);
			
			if(this.listaErrores.size() > 0) {
				for (Exception e : this.listaErrores) {
					
					if(e.getMessage().equals("Value for correoelectronico cannot be null")) {
						error = "No se ha realizado el envio ya que el destinatario no tiene correo electronico";
					}
					
					_enviosService.insertaExcelRowLogGenerico(envio, sheet, error);
				}
			}else {
				_enviosService.insertaExcelRowLogGenerico(envio, sheet, error);
			}
			
		} catch (Exception e) {
			LOGGER.error("ColaEnviosImpl -- > generaLogGenerico: " + e);
		} finally {
			_enviosService.writeCloseLogFileGenerico(Short.valueOf(idInstitucion), envio.getIdenvio(), sheet);
		}
	}

	private void addDestBuroSMS(boolean isBuroSMS, List<EnvDestinatariosBurosms> listEnvDestinatariosBurosms, Long idenvio,
			Short idinstitucion, Long idpersona, String numMovil) {
		
		if (listEnvDestinatariosBurosms != null) {
			EnvDestinatariosBurosms envDestinatariosBurosms = new EnvDestinatariosBurosms();
			envDestinatariosBurosms.setIdenvio(idenvio);
			envDestinatariosBurosms.setIdpersona(idpersona);
			envDestinatariosBurosms.setIdinstitucion(idinstitucion);
			envDestinatariosBurosms.setMovil(numMovil);									
			envDestinatariosBurosms.setFechamodificacion(new Date());
			envDestinatariosBurosms.setUsumodificacion(1);
			listEnvDestinatariosBurosms.add(envDestinatariosBurosms);
		}
	}

	private String remplazarCamposAsunto(String asunto, List<Map<String, Object>> resultadosConsultas) {
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		for (Map<String, Object> map : resultadosConsultas) {
			mapa.putAll(map);
		}
		
		for (Entry<String, Object> object : mapa.entrySet()) {
			if(object.getValue() != null){
				asunto = asunto.replaceAll("%%" + object.getKey()+"%%", object.getValue().toString());
			}else{
				asunto = asunto.replaceAll("%%" + object.getKey()+"%%", " ");
			}
		}

		return asunto;
	}

	private String remplazarCamposCuerpo(String cuerpo, List<Map<String, Object>> resultadosConsultas) {
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		for (Map<String, Object> map : resultadosConsultas) {
			mapa.putAll(map);
		}
		
		for (Entry<String, Object> object : mapa.entrySet()) {
			if(object.getValue() != null){
				cuerpo = cuerpo.replaceAll("%%" + object.getKey()+"%%", object.getValue().toString());
			}else{
				cuerpo = cuerpo.replaceAll("%%" + object.getKey()+"%%", " ");
			}
		}

		return cuerpo;
	}


	private DestinatarioItem obtenerDestinatario(Map<String, Object> registro, String idPersonaAlias, String correoElectronicoAlias, String movilAlias, String domicilioAlias) {

		DestinatarioItem destinatario = new DestinatarioItem();

		// Recojemos los campos
		Object campoIdPersona = registro.get(idPersonaAlias.trim());
		Object campoCorreo = registro.get(correoElectronicoAlias.trim());
		Object movil = registro.get(movilAlias.trim());
		Object domicilio = registro.get(domicilioAlias.trim());

		CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(campoIdPersona.toString()));
		destinatario.setNombre(persona.getNombre());
		destinatario.setApellidos1(persona.getApellidos1());
		destinatario.setApellidos2(persona.getApellidos2());
		if(domicilio != null){
			destinatario.setDomicilio(domicilio.toString());
		}
		if(campoCorreo != null){
			destinatario.setCorreoElectronico(campoCorreo.toString());
		}
		if(movil != null){
			destinatario.setMovil(movil.toString());
		}
		return destinatario;

	}
	
	private String prepararConsulta(String sentencia, Short idtipoEnvios, Short idInstitucion){
		
		sentencia = SIGAHelper.quitarEtiquetas(sentencia);
		
		if(sentencia.toUpperCase().contains("%%IDTIPOENVIOS%%")){
			sentencia = sentencia.toUpperCase().replaceAll("%%IDTIPOENVIOS%%", idtipoEnvios.toString());
		}
		
		if(sentencia.toUpperCase().contains("%%TIPOENVIO%%")){
			sentencia = sentencia.toUpperCase().replaceAll("%%TIPOENVIO%%", idtipoEnvios.toString());
		}
		
		
		if(sentencia.toUpperCase().contains("%%IDINSTITUCION%%")){
			sentencia = sentencia.toUpperCase().replaceAll("%%IDINSTITUCION%%", idInstitucion.toString());
		}
		
		return sentencia;
		
		
	}
	

}
