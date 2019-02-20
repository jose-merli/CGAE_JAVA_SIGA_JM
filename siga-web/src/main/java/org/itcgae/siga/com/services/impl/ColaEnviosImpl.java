package org.itcgae.siga.com.services.impl;

import java.io.File;
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
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IColaEnvios;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.FORMATO_SALIDA;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvDocumentosExample;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosgrupocliente;
import org.itcgae.siga.db.entities.EnvEnviosgrupoclienteExample;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosgrupoclienteMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvConsultasEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.aspose.words.Document;

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
	private IGeneracionDocumentosService _generacionDocService;
	
	@Autowired
	private EnvConsultasEnvioExtendsMapper _envConsultasEnvioExtendsMapper;
	
	@Autowired
	private ModPlantilladocumentoMapper _modPlantilladocumentoMapper;
	
	@Autowired
	private ModModeloPlantillaDocumentoExtendsMapper _modModeloPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper _modPlantillaDocumentoConsultaExtendsMapper;
	
	@Autowired
	private EnvEnviosgrupoclienteMapper _envEnviosgrupoclienteMapper;
	
	@Autowired
	private CenGruposclienteClienteMapper _cenGruposclienteClienteMapper;
	

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
						case SigaConstants.ID_ENVIO_CORREO_ORDINARIO:
							preparaCorreo(envio);
							LOGGER.info("Correo ordinario generado con éxito");
							break;
						case SigaConstants.ID_ENVIO_SMS:
							//TEST PARA INTEGRACION
//							CenDirecciones remitente = new CenDirecciones();
//							remitente.setCorreoelectronico("bherrero@deloitte.es");
//							String[] numerosDestinatarios = new String[2];
//							numerosDestinatarios[0] = "691038553";
//							numerosDestinatarios[1] = "622300543";
//							_enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), "ASUNTO TEST", "CUERPO TEST", false);
							preparaEnvioSMS(envio, false);
							LOGGER.info("SMS enviado con éxito");
							break;
						case SigaConstants.ID_ENVIO_BURO_SMS:
							//TEST PARA INTEGRACION
//							remitente = new CenDirecciones();
//							remitente.setCorreoelectronico("bherrero@deloitte.es");
//							numerosDestinatarios = new String[2];
//							numerosDestinatarios[0] = "691038553";
//							numerosDestinatarios[1] = "622300543";
//							_enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), "ASUNTO TEST", "CUERPO TEST", true);
							preparaEnvioSMS(envio, true);
							LOGGER.info("BURO SMS enviado con éxito");
							break;
						}
				}

			}
		}catch(Exception e){
			LOGGER.error("Error al procesar el envío: " + e.getMessage());
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
		remitentedto.setApellido2(personaRemitente.getApellidos2());
		remitentedto.setCorreoElectronico(remitente.getCorreoelectronico());
		
		//Obtenemos los destinatarios dependendiendo del tipo de envio.
		boolean envioMasivo = envio.getEnvio().contains("M") ? true : false;
		
		if(envioMasivo){
			
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
							if(dir.getCorreoelectronico() != null){
								if(!añadido){
									DestinatarioItem destinatario = new DestinatarioItem();
									destinatario.setCorreoElectronico(dir.getCorreoelectronico());
									destinatarios.add(destinatario);
									añadido = true;
								}
							}
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
				Map<String,String> query =_consultasService.obtenerMapaConsulta(consultaDatos.getConsulta());
				List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(query);
				resultadosConsultas.addAll(result);
			}
			String asuntoFinal = remplazarCamposAsunto(plantilla.getAsunto() != null ? plantilla.getAsunto():"", resultadosConsultas);
			String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo() != null ? plantilla.getCuerpo():"", resultadosConsultas);
			
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
				_enviosService.envioMail(remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
			}else{
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
				
				//TODO: GENERAR LA ETIQUETA CON EL DESTINATARIO ???????
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
			EnvConsultasenvioExample consultaPlantillaExample = new EnvConsultasenvioExample();
			consultaPlantillaExample.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdobjetivoEqualTo(Long.valueOf(SigaConstants.ID_OBJETIVO_DESTINATARIOS))
			.andIdinstitucionEqualTo(envio.getIdinstitucion());
			List<EnvConsultasenvio> consultasAsociadasDestinatarios = _envConsultasenvioMapper.selectByExampleWithBLOBs(consultaPlantillaExample);
			
			List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
			if (consultasAsociadasDestinatarios != null && consultasAsociadasDestinatarios.size() > 0) {
				for (EnvConsultasenvio consulta : consultasAsociadasDestinatarios) {
					
					int inicioSelect = consulta.getConsulta().indexOf("<SELECT>") + 8;
					int finSelect = consulta.getConsulta().indexOf("</SELECT>");
					
					//Obtenemos alias del Select para recuperar valores mas tarde
//					String selectConEtiquetas = consulta.getConsulta().substring(inicioSelect, finSelect);
//					String aliasIdPersona = obtenerAliasIdPersona(selectConEtiquetas.trim());
					//String aliasIdInstitucion = obtenerAliasIdInstitucion(selectConEtiquetas.trim());
//					String aliasCorreo = obtenerAliasCorreoElectronico(selectConEtiquetas.trim());
//					String aliasMovil = obtenerAliasMovil(selectConEtiquetas.trim());
//					String aliasDomicilio = obtenerAliasDomicilio(selectConEtiquetas.trim());
					String aliasIdPersona = SigaConstants.ALIASIDPERSONA;
					String aliasCorreo = SigaConstants.ALIASCORREO;
					String aliasMovil = SigaConstants.ALIASMOVIL;
					String aliasDomicilio = SigaConstants.ALIASDOMICILIO;
					
					Map<String,String> camposQuery =_consultasService.obtenerMapaConsulta(consulta.getConsulta());
					List<Map<String, Object>> resultDestinatarios = _conConsultasExtendsMapper.ejecutarConsulta(camposQuery);
			
					// Recorremos todos los destinatarios de los resultados de la consulta.
					for (Map<String, Object> map : resultDestinatarios) {
						destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasCorreo, aliasMovil, aliasDomicilio));
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
						Map<String,String> query =_consultasService.obtenerMapaConsulta(consultaDatos.getConsulta());
						List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(query);
						resultadosConsultas.addAll(result);
					}
					String asuntoFinal = remplazarCamposAsunto(plantilla.getAsunto() != null ? plantilla.getAsunto():"", resultadosConsultas);
					String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo() != null ? plantilla.getCuerpo():"", resultadosConsultas);
					
					//Generamos los informes para adjuntarlos al envio
					List<DatosDocumentoItem> documentosEnvio = generarDocumentosEnvio(envio.getIdinstitucion().toString(), envio.getIdenvio().toString());
					
					if(envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL)){
						_enviosService.envioMail(remitentedto, destinatarios, asuntoFinal, cuerpoFinal, documentosEnvio, envioMasivo);
					}else{
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
						
						//TODO: GENERAR LA ETIQUETA CON EL DESTINATARIO ???????
						for (DestinatarioItem destinatario : destinatarios) {
							destinatario.getNombre();
							destinatario.getApellidos1();
							destinatario.getApellidos2();
							destinatario.getDomicilio();
						}
					}
				}
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
				envio.setFechamodificacion(new Date());
				_envEnviosMapper.updateByPrimaryKey(envio);
			}else{
				LOGGER.error("No se han encontrado consultas de destinatarios asociadas a la plantilla de envío");
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
				envio.setFechamodificacion(new Date());
				_envEnviosMapper.updateByPrimaryKey(envio);
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

		// obtenemos las consultas de destinatarios asociadas a la plantilla de documento
		EnvConsultasenvioExample consultaPlantillaExample = new EnvConsultasenvioExample();
		consultaPlantillaExample.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdobjetivoEqualTo(SigaConstants.ID_OBJETIVO_DESTINATARIOS)
		.andIdinstitucionEqualTo(envio.getIdinstitucion());
		List<EnvConsultasenvio> consultasAsociadasDestinatarios = _envConsultasenvioMapper.selectByExampleWithBLOBs(consultaPlantillaExample);

		List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
		if (consultasAsociadasDestinatarios != null && consultasAsociadasDestinatarios.size() > 0) {
			for (EnvConsultasenvio consulta : consultasAsociadasDestinatarios) {
				
				int inicioSelect = consulta.getConsulta().indexOf("<SELECT>") + 8;
				int finSelect = consulta.getConsulta().indexOf("</SELECT>");
				
				//Obtenemos alias del Select para recuperar valores mas tarde
				String selectConEtiquetas = consulta.getConsulta().substring(inicioSelect, finSelect);
				String aliasIdPersona = obtenerAliasIdPersona(selectConEtiquetas.trim());
				//String aliasIdInstitucion = obtenerAliasIdInstitucion(selectConEtiquetas.trim());
				String aliasCorreo = obtenerAliasCorreoElectronico(selectConEtiquetas.trim());
				String aliasMovil = obtenerAliasMovil(selectConEtiquetas.trim());
				String aliasDomicilio = obtenerAliasDomicilio(selectConEtiquetas.trim());
				
				Map<String,String> camposQuery =_consultasService.obtenerMapaConsulta(consulta.getConsulta());
				List<Map<String, Object>> resultDestinatarios = _conConsultasExtendsMapper.ejecutarConsulta(camposQuery);
		
				// Recorremos todos los destinatarios de los resultados de la consulta.
				for (Map<String, Object> map : resultDestinatarios) {
					destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasCorreo, aliasMovil, aliasDomicilio));
				}
				
				LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
				String[] numerosDestinatarios = new String[destinatarios.size()];
				
				// Obtenemos los numeros de los destinatarios.
				for (int x = 0; x < numerosDestinatarios.length; x++) {
					numerosDestinatarios[x] = destinatarios.get(x).getMovil();
				}
		
				// Obtenemos las consultas asociadas a la plantilla de envio.
				EnvConsultasenvioExample exampleConsulta = new EnvConsultasenvioExample();
				Long objetivoDatos = new Long(4);
				exampleConsulta.createCriteria().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdenvioEqualTo(envio.getIdenvio()).andFechabajaIsNull().andIdobjetivoEqualTo(objetivoDatos);

				List<EnvConsultasenvio> consultasDatosPlantilla = _envConsultasenvioMapper.selectByExampleWithBLOBs(exampleConsulta);
		
				// Las ejecutamos y obtenemos los resultados
				List<Map<String, Object>> resultadosConsultas = new ArrayList<Map<String, Object>>();
				for (EnvConsultasenvio consultaDatos : consultasDatosPlantilla) {
					Map<String,String> query =_consultasService.obtenerMapaConsulta(consultaDatos.getConsulta());
					List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(query);
					resultadosConsultas.addAll(result);
				}
				String asuntoFinal = remplazarCamposAsunto(plantilla.getAsunto(), resultadosConsultas);
				String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo(), resultadosConsultas);
				// Realizamos el envio por SMS
				idSolicitudEcos = _enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), asuntoFinal, cuerpoFinal, isBuroSMS);
				envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
				envio.setFechamodificacion(new Date());
				envio.setIdsolicitudecos(idSolicitudEcos);
				_envEnviosMapper.updateByPrimaryKey(envio);
			}
			
		}else{
			LOGGER.error("No se han encontrado consultas de destinatarios asociadas a la plantilla de envío");
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO_CON_ERRORES);
			envio.setFechamodificacion(new Date());
			_envEnviosMapper.updateByPrimaryKey(envio);
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

	private String obtenerAliasIdInstitucion(String select) {

		String idInstitucion = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");
		String alias = "CEN_CLIENTE.IDINSTITUCION";
		for (String string : selects) {
			string = string.trim();
			if (string.indexOf(alias) > -1) {
				int inicio = string.indexOf(alias) + alias.length();
				idInstitucion = string.substring(inicio);
				idInstitucion = idInstitucion.replace("\"", "");
				idInstitucion = idInstitucion.replace("\"", "");
				idInstitucion = idInstitucion.replace("AS", "");
				idInstitucion = idInstitucion.trim();
			}
		}

		return idInstitucion;
	}

	private String obtenerAliasIdPersona(String select) {

		String idPersona = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");
		String alias = "CEN_CLIENTE.IDPERSONA";
		for (String string : selects) {
			string = string.trim();
			if (string.indexOf(alias) > -1) {
				int inicio = string.indexOf(alias) + alias.length();
				idPersona = string.substring(inicio);
				idPersona = idPersona.replace("AS", "");
				idPersona = idPersona.replace("\"", "");
				idPersona = idPersona.replace("\"", "");
				idPersona = idPersona.trim();
			}
		}

		return idPersona;
	}

	private String obtenerAliasCorreoElectronico(String select) {

		String correo = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");
		String alias = "CEN_DIRECCIONES.CORREOELECTRONICO";
		for (String string : selects) {
			string = string.trim();
			if (string.indexOf(alias) > -1) {
				int inicio = string.indexOf(alias) + alias.length();
				correo = string.substring(inicio);
				correo = correo.replace("\"", "");
				correo = correo.replace("\"", "");
				correo = correo.replace("AS", "");
				correo = correo.replace("AS", "");
			}
		}

		return correo;
	}

	private String obtenerAliasMovil(String select) {

		String movil = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");
		String alias = "CEN_DIRECCIONES.MOVIL";
		for (String string : selects) {
			string = string.trim();
			if (string.indexOf(alias) > -1) {
				int inicio = string.indexOf(alias) + alias.length();
				movil = string.substring(inicio);
				movil = movil.replace("\"", "");
				movil = movil.replace("\"", "");
				movil = movil.replace("AS", "");
				movil = movil.trim();
			}
		}

		return movil;
	}
	
	private String obtenerAliasDomicilio(String select) {

		String domicilio = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");
		String alias = "CEN_DIRECCIONES.DOMICILIO";
		for (String string : selects) {
			string = string.trim();
			if (string.indexOf(alias) > -1) {
				int inicio = string.indexOf(alias) + alias.length();
				domicilio = string.substring(inicio);
				domicilio = domicilio.replace("\"", "");
				domicilio = domicilio.replace("\"", "");
				domicilio = domicilio.replace("AS", "");
				domicilio = domicilio.trim();
			}
		}

		return domicilio;
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
	
	public List<DatosDocumentoItem> generarDocumentosEnvio(String idInstitucion, String idEnvio) throws Exception{	
		List<String> listaIdPlantilla = _envConsultasEnvioExtendsMapper.selectPlantillasByEnvio(idInstitucion, idEnvio);
		List<DatosDocumentoItem> listaFicheros = new ArrayList<DatosDocumentoItem>();
		Long idModeloComunicacion = null;
		
		if(listaIdPlantilla != null && listaIdPlantilla.size() > 0){
			for(String idPlantilla : listaIdPlantilla){
				// Generamos el documento
				
				String nombrePlantilla = "";
				Long idPlantillaGenerar = null;
				//Obtenemos el nombre de la plantilla
				if(idPlantilla != null){
					ModPlantilladocumentoExample example = new ModPlantilladocumentoExample();
					List<Long> idValues = new ArrayList<Long>();
					idValues.add(Long.parseLong(idPlantilla));
					
					example.createCriteria().andIdplantilladocumentoIn(idValues);
					List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
					
					if(listaPlantilla != null && listaPlantilla.size() == 1){
						ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
						nombrePlantilla = plantillaDoc.getPlantilla();
						idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
					}else if(listaPlantilla != null && listaPlantilla.size() > 1){
						LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
						throw new SigaExceptions("Exiten multiples plantillas asociada al informe en el idioma del usuario");
					}else{
						LOGGER.error("No hay plantilla asociada para el informe en el idioma del usuario");
						throw new SigaExceptions("No hay plantilla asociada para el informe en el idioma del usuario");
					}
				}
				
				LOGGER.debug("Obtenemos la consulta condicional");			
				
				EnvConsultasenvioExample example = new EnvConsultasenvioExample();
				example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());
				List<EnvConsultasenvio> listaConsultas = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
				boolean continua = false;
				if(listaConsultas != null && listaConsultas.size() > 0){
					for(EnvConsultasenvio consulta: listaConsultas){
						Map<String,String> mapa = new HashMap<String,String>();
						mapa = _consultasService.obtenerMapaConsulta(consulta.getConsulta());
						List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(mapa);
						
						if(result != null && result.size() > 0){
							continua = true;
						}else{
							continua = false;
						}
					}
				}else{
					LOGGER.debug("No hay consulta condicional para el envio " + idEnvio + " y plantilla: " + idPlantilla);
					continua = true;
				}	
				
				if(continua){
					HashMap<String,Object> hDatosGenerales = new HashMap<String, Object>();
					HashMap<String,Object> hDatosFinal = new HashMap<String, Object>();
					
					String campoSufijo = null;
					
					LOGGER.debug("Obtenemos la consulta de destinatario para el envio " + idEnvio + " y plantilla: " + idPlantilla);
					example = new EnvConsultasenvioExample();
					example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
					List<EnvConsultasenvio> listaConsultasDest = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
					for(EnvConsultasenvio consultaDest: listaConsultasDest){
						idModeloComunicacion = consultaDest.getIdmodelocomunicacion();
						// Ejecutamos la consulta
						Map<String,String> mapa = new HashMap<String,String>();
						mapa = _consultasService.obtenerMapaConsulta(consultaDest.getConsulta());
						List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(mapa);
						if(result != null && result.size() > 0){
							for(Map<String,Object> dest : result){
								hDatosGenerales.putAll(dest);
							}							
						}
						
					}
					
					LOGGER.debug("Obtenemos la consulta de multidocumento para el envio " + idEnvio + " y plantilla: " + idPlantilla);
					example = new EnvConsultasenvioExample();
					example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
					List<EnvConsultasenvio> listaConsultasMulti = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
					if(listaConsultasMulti != null && listaConsultasMulti.size() > 0){
						for(EnvConsultasenvio consultaMulti: listaConsultasMulti){
							// Ejecutamos la consulta
							Map<String,String> mapa = new HashMap<String,String>();
							mapa = _consultasService.obtenerMapaConsulta(consultaMulti.getConsulta());
							
							if(campoSufijo == null || !"".equalsIgnoreCase(campoSufijo)){
								campoSufijo = consultaMulti.getSufijo();
							}
							
							List<Map<String,Object>> resultMulti = _conConsultasExtendsMapper.ejecutarConsulta(mapa);
							
							int numFicheros = 0;
							if(resultMulti != null && resultMulti.size() > 0){
								for(int k = 0;k<resultMulti.size();k++){
									
									hDatosGenerales.putAll(resultMulti.get(k));
									
									// Por cada registro generamos un documento
									numFicheros++;
									
									List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectPlantillaGenerar(consultaMulti.getIdmodelocomunicacion(), consultaMulti.getIdplantilladocumento());
									PlantillaModeloDocumentoDTO plantilla = plantillas.get(0);
									
									//Otenemos el nombre del fichero de salida
									String pathFicheroSalida = SigaConstants.rutaficherosInformesYcomunicaciones + idInstitucion + SigaConstants.carpetaTmp;
									String pathPlantilla = SigaConstants.rutaficherosInformesYcomunicaciones + idInstitucion + SigaConstants.carpetaPlantillasDocumento;
									
									//Si no existe el directorio temporal lo creamos
									File dir = new File(pathFicheroSalida);
									if(!dir.exists()){
										dir.mkdir();
									}	
									
									
									File filePlantilla = new File(pathPlantilla + nombrePlantilla);
									if(!filePlantilla.exists()){
										throw new SigaExceptions("No existe la plantilla de documento");
									}
									
									Document doc = new Document(pathPlantilla + nombrePlantilla);

									
									// Por cada resultado ejecutamos las consultas de datos
									LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
									example = new EnvConsultasenvioExample();
									example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.DATOS.getCodigo());
									List<EnvConsultasenvio> listaConsultasDatos = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
									
									for(EnvConsultasenvio consultaDatos:listaConsultasDatos){	
										
										if(campoSufijo == null || !"".equalsIgnoreCase(campoSufijo)){
											campoSufijo = consultaDatos.getSufijo();
										}
										
										String consultaEjecutarDatos = consultaDatos.getConsulta();
										
										List<Map<String,Object>> resultDatos = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDatos);										
										
										//Miramos si la consulta tiene region
										List<ConsultaItem> listaPlantillaDocConsulta = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaByIdConsulta(Short.valueOf(idInstitucion), consultaDatos.getIdmodelocomunicacion(), consultaDatos.getIdinforme(), consultaDatos.getIdconsulta(), consultaDatos.getIdplantilladocumento());
										
										ConsultaItem consultaConRegion = listaPlantillaDocConsulta.get(0);
										
										if(consultaConRegion.getRegion()!= null && !consultaConRegion.getRegion().equalsIgnoreCase("")){
											hDatosFinal.put(consultaConRegion.getRegion(), resultDatos);
										}else{
											hDatosGenerales.putAll(resultDatos.get(0));
										}
									}
									
									hDatosFinal.put("row", hDatosGenerales);									

									LOGGER.debug("Generamos el documento");																
									
									doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);
									
									String nombreFicheroSalida = _dialogoComunicacionService.obtenerNombreFicheroSalida(String.valueOf(consultaMulti.getIdmodelocomunicacion()), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, numFicheros, pathFicheroSalida, campoSufijo);
																
									boolean firmado = false;
									
									if(plantilla.getFormatoSalida() != null){
										FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
										if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
											firmado = true;
										}
									}
									
									DatosDocumentoItem docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
									docGenerado.setPathDocumento(pathFicheroSalida);
									
									listaFicheros.add(docGenerado);
																															
								}
							}
						}
					}else{
						LOGGER.debug("No hay consulta multidocumento para el envio " + idEnvio + " y plantilla: " + idPlantilla);
						
						List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectPlantillaGenerar(idModeloComunicacion, Long.parseLong(idPlantilla));
						PlantillaModeloDocumentoDTO plantilla = plantillas.get(0);
						
						//Otenemos el nombre del fichero de salida
						String pathFicheroSalida = SigaConstants.rutaficherosInformesYcomunicaciones + idInstitucion + SigaConstants.carpetaTmp;
						String pathPlantilla = SigaConstants.rutaficherosInformesYcomunicaciones + idInstitucion + SigaConstants.carpetaPlantillasDocumento;
						
						//Si no existe el directorio temporal lo creamos
						File dir = new File(pathFicheroSalida);
						if(!dir.exists()){
							dir.mkdir();
						}	
						
						
						File filePlantilla = new File(pathPlantilla + nombrePlantilla);
						if(!filePlantilla.exists()){
							throw new SigaExceptions("No existe la plantilla de documento");
						}
						
						Document doc = new Document(pathPlantilla + nombrePlantilla);

						
						// Por cada resultado ejecutamos las consultas de datos
						LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
						example = new EnvConsultasenvioExample();
						example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.DATOS.getCodigo());
						List<EnvConsultasenvio> listaConsultasDatos = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
						
						for(EnvConsultasenvio consultaDatos:listaConsultasDatos){	
							
							String consultaEjecutarDatos = consultaDatos.getConsulta();
							
							if(campoSufijo == null || !"".equalsIgnoreCase(campoSufijo)){
								campoSufijo = consultaDatos.getSufijo();
							}
							
							List<Map<String,Object>> resultDatos = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDatos);										
							
							//Miramos si la consulta tiene region
							List<ConsultaItem> listaPlantillaDocConsulta = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaByIdConsulta(Short.valueOf(idInstitucion), consultaDatos.getIdmodelocomunicacion(), consultaDatos.getIdinforme(), consultaDatos.getIdconsulta(), consultaDatos.getIdplantilladocumento());
							
							ConsultaItem consultaConRegion = listaPlantillaDocConsulta.get(0);
							
							if(consultaConRegion.getRegion()!= null && !consultaConRegion.getRegion().equalsIgnoreCase("")){
								hDatosFinal.put(consultaConRegion.getRegion(), resultDatos);
							}else{
								hDatosGenerales.putAll(resultDatos.get(0));
							}
						}
						
						hDatosFinal.put("row", hDatosGenerales);									

						LOGGER.debug("Generamos el documento");																
						
						doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);
						
						String nombreFicheroSalida = _dialogoComunicacionService.obtenerNombreFicheroSalida(String.valueOf(idModeloComunicacion), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, 0, pathFicheroSalida, campoSufijo);
						
						boolean firmado = false;
						
						if(plantilla.getFormatoSalida() != null){
							FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
							if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
								firmado = true;
							}
						}
						
						DatosDocumentoItem docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida,firmado);
						
						listaFicheros.add(docGenerado);
					}					
				}				
				
			}		
		}else{
			LOGGER.info("No hay asociadas al envio:" + idEnvio);
		}
				
		return listaFicheros;
	}

}
