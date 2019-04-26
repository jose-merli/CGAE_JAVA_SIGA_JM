package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IColaEnvios;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvCamposenviosKey;
import org.itcgae.siga.db.entities.EnvCamposenviosExample;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
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
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosgrupoclienteMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestConsultaEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
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
				}

			}
		}catch(Exception e){
			LOGGER.error("Error al procesar el envío", e);
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
			envio.setFechamodificacion(new Date());
			_envEnviosMapper.updateByPrimaryKey(envio);
			e.printStackTrace();
		}
		
	}

	public void preparaCorreo(EnvEnvios envio) throws Exception {
		
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
		
		CenPersona personaRemitente = _cenPersonaMapper.selectByPrimaryKey(plantilla.getIdpersona());
		
		RemitenteDTO remitentedto = new RemitenteDTO();
		remitentedto.setNombre(personaRemitente.getNombre());
		remitentedto.setApellido1(personaRemitente.getApellidos1());
		
		if(personaRemitente.getApellidos2() != null) {
			remitentedto.setApellido2(personaRemitente.getApellidos2());
		}
		if(remitente != null) {
			remitentedto.setCorreoElectronico(remitente.getCorreoelectronico());
		}else {
			remitentedto.setCorreoElectronico("");
		}
		
		//Obtenemos los destinatarios dependendiendo del tipo de envio.
		boolean envioMasivo = envio.getEnvio().contains("M") ? true : false;
		
		if(envioMasivo){
			
			//Obtenemos destinatarios de las etiquetas de envio
			EnvEnviosgrupoclienteExample exampleEtiquetas = new EnvEnviosgrupoclienteExample();
			exampleEtiquetas.createCriteria().andIdenvioEqualTo(envio.getIdenvio());
			List<EnvEnviosgrupocliente> etiquetasEnvio = _envEnviosgrupoclienteMapper.selectByExample(exampleEtiquetas);
			
			List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
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
					exampleDir.createCriteria().andIdpersonaEqualTo(persona.getIdpersona()).andIdinstitucionEqualTo(persona.getIdinstitucion());
					List<CenDirecciones> direcciones =  _cenDireccionesMapper.selectByExample(exampleDir);
					//Si la persona tiene mas de una direccion obtenemos todas hasta que encontremos una con correo electrónico
					if(direcciones != null && direcciones.size() > 0){
						boolean añadido = false;
						for (CenDirecciones dir : direcciones) {
							if(dir.getCorreoelectronico() != null){
								if(!añadido){
									DestinatarioItem destinatario = new DestinatarioItem();
									destinatario.setCorreoElectronico(dir.getCorreoelectronico());
									destinatario.setNombre(cenPersona.getNombre());
									destinatario.setApellidos1(cenPersona.getApellidos1());
									destinatario.setApellidos2(cenPersona.getApellidos2());
									destinatario.setNIFCIF(cenPersona.getNifcif());
									destinatario.setIdPersona(String.valueOf(persona.getIdpersona()));
									destinatarios.add(destinatario);
									añadido = true;
									LOGGER.info("PRUEBA ENVIOS ETIQUETAS : " + destinatarios.toString());
								}
							}
						}
					}
				}
			}
			
			//Obtenemos destinatarios individuales.
			EnvDestinatariosExample example = new EnvDestinatariosExample();
			example.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvDestinatarios> destIndv = _envDestinatariosMapper.selectByExample(example);
			for (EnvDestinatarios destinatario : destIndv) {
				DestinatarioItem dest = new DestinatarioItem();
				dest.setCorreoElectronico(destinatario.getCorreoelectronico());
				dest.setNombre(destinatario.getNombre());
				dest.setApellidos1(destinatario.getApellidos1());
				dest.setApellidos2(destinatario.getApellidos2());
				dest.setNIFCIF(destinatario.getNifcif());
				dest.setIdPersona(String.valueOf(destinatario.getIdpersona()));
				destinatarios.add(dest);
			}
			
			//obtenemos los destinatarios por consultas de destinatarios
			List<ConsultaItem> consultaItem = _envDestConsultaEnvioExtendsMapper.selectConsultasDestEnvio(envio.getIdinstitucion(), envio.getIdenvio().toString());
			for (ConsultaItem consulta : consultaItem) {
				String sentenciaFinal = prepararConsulta(consulta.getSentencia(), envio.getIdtipoenvios(), envio.getIdinstitucion());
				List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentenciaFinal);
				if(result != null && result.size() > 0){
					
					for (Map<String, Object> map : result) {
						Object campo = map.get("CORREOELECTRONICO");
	            		if(campo != null){
	            			DestinatarioItem destinatario = new DestinatarioItem();
	            			destinatario.setCorreoElectronico(campo.toString());
	            			
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

	            			destinatarios.add(destinatario);
	            		}
					}
				}
			}
			
			
			LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
			
			// Obtenemos las consultas asociadas de datos a la plantilla de envio.
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
			
			// Obtenemos el asunto y el cuerpo del envio
			
			String asuntoEnvio = plantilla.getAsunto() != null ? plantilla.getAsunto():"";
			String cuerpoEnvio = plantilla.getCuerpo() != null ? plantilla.getCuerpo():"";
					
			EnvCamposenviosKey key = new EnvCamposenviosKey();
			key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_ASUNTO));
			key.setIdenvio(envio.getIdenvio());
			key.setIdinstitucion(envio.getIdinstitucion());
			key.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);		
			
			EnvCamposenvios envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
			
			if(envCampo != null && envCampo.getValor() != null) {
				asuntoEnvio = envCampo.getValor();
			}			

			key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_CUERPO));		
			
			envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
			
			if(envCampo != null && envCampo.getValor() != null) {
				cuerpoEnvio = envCampo.getValor();
			}
			
			String asuntoFinal = remplazarCamposAsunto(asuntoEnvio, resultadosConsultas);
			String cuerpoFinal = remplazarCamposCuerpo(cuerpoEnvio, resultadosConsultas);
			
			//Generamos los informes para adjuntarlos al envio
			List<DatosDocumentoItem> documentosEnvio = new ArrayList<DatosDocumentoItem>();
			EnvDocumentosExample exampleDoc = new EnvDocumentosExample();
			exampleDoc.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvDocumentos> documentos = _envDocumentosMapper.selectByExample(exampleDoc);
			for (EnvDocumentos documento : documentos) {
				DatosDocumentoItem doc = new DatosDocumentoItem();
				doc.setFileName(documento.getDescripcion());
				doc.setPathDocumento(documento.getPathdocumento());
				documentosEnvio.add(doc);
			}
			
			if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL)){
				_enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
			}else{
				if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO)){
					_enviosService.envioMailLetrado(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, envioMasivo);
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
			
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
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
				
				// Obtenemos el asunto y el cuerpo del envio
				
				String asuntoEnvio = plantilla.getAsunto() != null ? plantilla.getAsunto():"";
				String cuerpoEnvio = plantilla.getCuerpo() != null ? plantilla.getCuerpo():"";
						
				EnvCamposenviosKey key = new EnvCamposenviosKey();
				key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_ASUNTO));
				key.setIdenvio(envio.getIdenvio());
				key.setIdinstitucion(envio.getIdinstitucion());
				key.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);		
				
				EnvCamposenvios envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
				
				if(envCampo != null && envCampo.getValor() != null) {
					asuntoEnvio = envCampo.getValor();
				}			

				key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_CUERPO));		
				
				envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
				
				if(envCampo != null && envCampo.getValor() != null) {
					cuerpoEnvio = envCampo.getValor();
				}
				
				String asuntoFinal = remplazarCamposAsunto(asuntoEnvio, resultadosConsultas);
				String cuerpoFinal = remplazarCamposCuerpo(cuerpoEnvio, resultadosConsultas);
				
				//Generamos los informes para adjuntarlos al envio
				List<DatosDocumentoItem> documentosEnvio = _dialogoComunicacionService.generarDocumentosEnvio(envio.getIdinstitucion().toString(), envio.getIdenvio().toString());
				
				LOGGER.debug("Procedemos al envio del email: tipo " + envio.getIdtipoenvios() + "--" + envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL));
				
				if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL)){
					_enviosService.envioMail(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
				}else{
					if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_DOCUMENTACION_LETRADO)){
						_enviosService.envioMailLetrado(String.valueOf(envio.getIdinstitucion()), String.valueOf(envio.getIdenvio()), remitentedto, destinatarios, asuntoFinal, cuerpoFinal, envioMasivo);
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
			}else{
				LOGGER.error("No se han encontrado consultas de destinatarios asociadas a la plantilla de envío");
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
				envio.setFechamodificacion(new Date());
				_envEnviosMapper.updateByPrimaryKey(envio);
			}
		}
		envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
		envio.setFechamodificacion(new Date());
		_envEnviosMapper.updateByPrimaryKey(envio);
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
		String[] numerosDestinatarios = null;
		
		if(envioMasivo){
			//Obtenemos destinatarios de las etiquetas de envio
			EnvEnviosgrupoclienteExample exampleEtiquetas = new EnvEnviosgrupoclienteExample();
			exampleEtiquetas.createCriteria().andIdenvioEqualTo(envio.getIdenvio());
			List<EnvEnviosgrupocliente> etiquetasEnvio = _envEnviosgrupoclienteMapper.selectByExample(exampleEtiquetas);
			
			List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
			for (EnvEnviosgrupocliente envEnviosgrupocliente : etiquetasEnvio) {
				CenGruposclienteClienteExample etiqueta = new CenGruposclienteClienteExample();
				etiqueta.createCriteria().andIdgrupoEqualTo(envEnviosgrupocliente.getIdgrupo()).andIdinstitucionEqualTo(envio.getIdinstitucion());
				List<CenGruposclienteCliente> personas = _cenGruposclienteClienteMapper.selectByExample(etiqueta);
				for (CenGruposclienteCliente persona : personas) {
					//Buscamos las direcciones de esa persona
					CenDireccionesExample exampleDir = new CenDireccionesExample();
					exampleDir.createCriteria().andIdpersonaEqualTo(persona.getIdpersona()).andIdinstitucionEqualTo(persona.getIdinstitucion());
					List<CenDirecciones> direcciones =  _cenDireccionesMapper.selectByExample(exampleDir);
					//Si la persona tiene mas de una direccion obtenemos todas hasta que encontremos una con correo electrónico
					if(direcciones != null && direcciones.size() > 0){
						boolean añadido = false;
						for (CenDirecciones dir : direcciones) {
							if(dir.getMovil() != null){
								if(!añadido){
									DestinatarioItem destinatario = new DestinatarioItem();
									destinatario.setMovil(dir.getMovil());
									destinatarios.add(destinatario);
									añadido = true;
								}
							}
						}
					}
				}
			}
			
			//Obtenemos destinatarios individuales.
			EnvDestinatariosExample example = new EnvDestinatariosExample();
			example.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvDestinatarios> destIndv = _envDestinatariosMapper.selectByExample(example);
			for (EnvDestinatarios destinatario : destIndv) {
				if(destinatario.getMovil()!=null){
					DestinatarioItem dest = new DestinatarioItem();
					dest.setMovil(destinatario.getMovil());
					destinatarios.add(dest);
				}
			}
			
			//obtenemos los destinatarios por consultas de destinatarios
			List<ConsultaItem> consultaItem = _envDestConsultaEnvioExtendsMapper.selectConsultasDestEnvio(envio.getIdinstitucion(), envio.getIdenvio().toString());
			for (ConsultaItem consulta : consultaItem) {
				String sentenciaFinal = prepararConsulta(consulta.getSentencia(), envio.getIdtipoenvios(), envio.getIdinstitucion());
				List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentenciaFinal);
				if(result != null && result.size() > 0){
					
					for (Map<String, Object> map : result) {
						Object campo = map.get("MOVIL");
	            		if(campo != null){
	            			DestinatarioItem destinatario = new DestinatarioItem();
	            			destinatario.setMovil(campo.toString());
	            			destinatarios.add(destinatario);
	            		}
					}
				}
			}
			
			LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
			numerosDestinatarios = new String[destinatarios.size()];
			
			// Obtenemos los numeros de los destinatarios.
			for (int x = 0; x < numerosDestinatarios.length; x++) {
				numerosDestinatarios[x] = destinatarios.get(x).getMovil();
			}
			
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
				numerosDestinatarios = new String[destinatarios.size()];
				
				// Obtenemos los numeros de los destinatarios.
				for (int x = 0; x < numerosDestinatarios.length; x++) {
					numerosDestinatarios[x] = destinatarios.get(x).getMovil();
				}
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

		String cuerpoEnvio = plantilla.getCuerpo() != null ? plantilla.getCuerpo():"";
		
		EnvCamposenviosKey key = new EnvCamposenviosKey();
			key.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_CUERPO)); // No estoy seguro si es 1 o 2 para sms comprobar en tabla

			key.setIdenvio(envio.getIdenvio());
			key.setIdinstitucion(envio.getIdinstitucion());
			key.setTipocampo(SigaConstants.ID_TIPO_CAMPO_SMS);
			//Crear constante con valor 'S'
		
			EnvCamposenvios envCampo = _envCamposenviosMapper.selectByPrimaryKey(key);
				if(envCampo != null && envCampo.getValor() != null) {
					cuerpoEnvio = envCampo.getValor();
				}else{
					cuerpoEnvio = plantilla.getCuerpo();
				}
				
		String cuerpoFinal = remplazarCamposCuerpo(cuerpoEnvio, resultadosConsultas); 
		
		// Realizamos el envio por SMS
		idSolicitudEcos = _enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), cuerpoFinal, isBuroSMS);
		envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
		envio.setFechamodificacion(new Date());
		envio.setIdsolicitudecos(idSolicitudEcos);
		_envEnviosMapper.updateByPrimaryKey(envio);
		
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
		
		sentencia = sentencia.replaceAll("<SELECT>", " ");
		sentencia = sentencia.replaceAll("</SELECT>", " ");
		sentencia = sentencia.replaceAll("<FROM>", " ");
		sentencia = sentencia.replaceAll("</FROM>", " ");
		sentencia = sentencia.replaceAll("<JOIN>", " ");
		sentencia = sentencia.replaceAll("</JOIN>", " ");
		sentencia = sentencia.replaceAll("<OUTERJOIN>", " ");
		sentencia = sentencia.replaceAll("</OUTERJOIN>", " ");
		sentencia = sentencia.replaceAll("<INNERJOIN>", " ");
		sentencia = sentencia.replaceAll("</INNERJOIN>", " ");
		sentencia = sentencia.replaceAll("<LEFTJOIN>", " ");
		sentencia = sentencia.replaceAll("</LEFTJOIN>", " ");
		sentencia = sentencia.replaceAll("<WHERE>", " ");
		sentencia = sentencia.replaceAll("</WHERE>", " ");
		sentencia = sentencia.replaceAll("<ORDERBY>", " ");
		sentencia = sentencia.replaceAll("</ORDERBY>", " ");
		sentencia = sentencia.replaceAll("<ORDER BY>", " ");
		sentencia = sentencia.replaceAll("</ORDER BY>", " ");
		sentencia = sentencia.replaceAll("<GROUPBY>", " ");
		sentencia = sentencia.replaceAll("</GROUPBY>", " ");
		sentencia = sentencia.replaceAll("<HAVING>", " ");
		sentencia = sentencia.replaceAll("</HAVING>", " ");
		sentencia = sentencia.replaceAll("<UNION>", " ");
		sentencia = sentencia.replaceAll("<UNION>", " ");
		sentencia = sentencia.replaceAll("<UNIONALL>", " ");
		sentencia = sentencia.replaceAll("</UNIONALL>", " ");
		
		if(sentencia.toUpperCase().contains("%%IDTIPOENVIOS%%")){
			sentencia = sentencia.toUpperCase().replaceAll("%%IDTIPOENVIOS%%", idtipoEnvios.toString());
		}
		
		if(sentencia.toUpperCase().contains("%%IDINSTITUCION%%")){
			sentencia = sentencia.toUpperCase().replaceAll("%%IDINSTITUCION%%", idInstitucion.toString());
		}
		
		return sentencia;
		
		
	}
	

}
