package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
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
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosgrupoclienteMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
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
			
			
			//Obtenemos alias del Select para recuperar valores mas tarde
//			int inicioSelect = consulta.getConsulta().indexOf("<SELECT>") + 8;
//			int finSelect = consulta.getConsulta().indexOf("</SELECT>");
//			String selectConEtiquetas = consulta.getConsulta().substring(inicioSelect, finSelect);
//			String aliasIdPersona = obtenerAliasIdPersona(selectConEtiquetas.trim());
//			String aliasIdInstitucion = obtenerAliasIdInstitucion(selectConEtiquetas.trim());
//			String aliasCorreo = obtenerAliasCorreoElectronico(selectConEtiquetas.trim());
//			String aliasMovil = obtenerAliasMovil(selectConEtiquetas.trim());
//			String aliasDomicilio = obtenerAliasDomicilio(selectConEtiquetas.trim());
//			String aliasIdPersona = SigaConstants.ALIASIDPERSONA;
//			String aliasCorreo = SigaConstants.ALIASCORREO;
//			String aliasMovil = SigaConstants.ALIASMOVIL;
//			String aliasDomicilio = SigaConstants.ALIASDOMICILIO;
//			
//			Map<String,String> camposQuery =_consultasService.obtenerMapaConsulta(consulta.getConsulta());
//			List<Map<String, Object>> resultDestinatarios = _conConsultasExtendsMapper.ejecutarConsulta(camposQuery);
//	
//			// Recorremos todos los destinatarios de los resultados de la consulta.
//			for (Map<String, Object> map : resultDestinatarios) {
//				destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasCorreo, aliasMovil, aliasDomicilio));
//			}
			
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
				String asuntoFinal = remplazarCamposAsunto(plantilla.getAsunto() != null ? plantilla.getAsunto():"", resultadosConsultas);
				String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo() != null ? plantilla.getCuerpo():"", resultadosConsultas);
				
				//Generamos los informes para adjuntarlos al envio
				List<DatosDocumentoItem> documentosEnvio = _dialogoComunicacionService.generarDocumentosEnvio(envio.getIdinstitucion().toString(), envio.getIdenvio().toString());
				
				LOGGER.debug("Procedemos al envio del email: tipo " + envio.getIdtipoenvios() + "--" + envio.getIdtipoenvios().toString().equals(SigaConstants.ID_ENVIO_MAIL));
				
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

		// obtenemos las consultas de destinatarios asociadas a la plantilla de documento
//		EnvConsultasenvioExample consultaPlantillaExample = new EnvConsultasenvioExample();
//		consultaPlantillaExample.createCriteria().andIdenvioEqualTo(envio.getIdenvio()).andIdobjetivoEqualTo(SigaConstants.ID_OBJETIVO_DESTINATARIOS)
//		.andIdinstitucionEqualTo(envio.getIdinstitucion());
//		List<EnvConsultasenvio> consultasAsociadasDestinatarios = _envConsultasenvioMapper.selectByExampleWithBLOBs(consultaPlantillaExample);
//
//		List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
//		if (consultasAsociadasDestinatarios != null && consultasAsociadasDestinatarios.size() > 0) {
			
				
//				int inicioSelect = consulta.getConsulta().indexOf("<SELECT>") + 8;
//				int finSelect = consulta.getConsulta().indexOf("</SELECT>");
//				
//				//Obtenemos alias del Select para recuperar valores mas tarde
//				String selectConEtiquetas = consulta.getConsulta().substring(inicioSelect, finSelect);
//				String aliasIdPersona = SigaConstants.ALIASIDPERSONA;
//				String aliasCorreo = SigaConstants.ALIASCORREO;
//				String aliasMovil = SigaConstants.ALIASMOVIL;
//				String aliasDomicilio = SigaConstants.ALIASDOMICILIO;
//				
//				Map<String,String> camposQuery =_consultasService.obtenerMapaConsulta(consulta.getConsulta());
//				List<Map<String, Object>> resultDestinatarios = _conConsultasExtendsMapper.ejecutarConsulta(camposQuery);
//		
//				// Recorremos todos los destinatarios de los resultados de la consulta.
//				for (Map<String, Object> map : resultDestinatarios) {
//					destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasCorreo, aliasMovil, aliasDomicilio));
//				}
		
		
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
			String cuerpoFinal = remplazarCamposCuerpo(plantilla.getCuerpo(), resultadosConsultas);
			// Realizamos el envio por SMS
			idSolicitudEcos = _enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), cuerpoFinal, isBuroSMS);
			envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
			envio.setFechamodificacion(new Date());
			envio.setIdsolicitudecos(idSolicitudEcos);
			_envEnviosMapper.updateByPrimaryKey(envio);
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
	

}
