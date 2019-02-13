package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaExample;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaExample;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvConsultasEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	private EnvConsultasenvioMapper _envConsultasenvioMapper;

	@Autowired
	private ModPlantillaenvioConsultaMapper _modPlantillaenvioConsultaMapper;

	@Autowired
	private ConConsultaMapper _conConsultaMapper;

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
	

	//@Transactional
	@Scheduled(cron = "${cron.pattern.scheduled.Envios: 0 * * ? * *}")
	@Override
	public void execute() {
		
		LOGGER.info("Entrando en listener de cola de envios");
		EnvEnvios envio = null;
		try{
			List<EnvEnvios> enviosProgramados = _envEnviosExtendsMapper.obtenerEnviosProgramados();
			if (enviosProgramados != null && enviosProgramados.size() > 0) {

				for (EnvEnvios envEnvioprogramado : enviosProgramados) {
					envio = envEnvioprogramado;
					LOGGER.info("Se ha encontrado envio programado con ID: " + envio.getIdenvio());
					String idSolicitudEcos;
					envio.setIdestado(SigaConstants.ENVIO_PROCESANDO);
					envio.setFechamodificacion(new Date());
					_envEnviosMapper.updateByPrimaryKey(envio);
					
					switch (envio.getIdtipoenvios().toString()) {

						case SigaConstants.TIPO_ENVIO_CORREOELECTRONICO:
							_enviosService.envioMail(envio);
							LOGGER.info("SMS enviado con éxito");
							envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
							envio.setFechamodificacion(new Date());
							_envEnviosMapper.updateByPrimaryKey(envio);
							LOGGER.info("mail enviado con éxito");
							break;
						case SigaConstants.TIPO_ENVIO_CORREO_ORDINARIO:
							_enviosService.envioCorreoOrdinario();
							LOGGER.info("SMS enviado con éxito");
							envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
							envio.setFechamodificacion(new Date());
							_envEnviosMapper.updateByPrimaryKey(envio);
							break;
						case SigaConstants.TIPO_ENVIO_SMS:
							//TEST PARA INTEGRACION
//							CenDirecciones remitente = new CenDirecciones();
//							remitente.setCorreoelectronico("bherrero@deloitte.es");
//							String[] numerosDestinatarios = new String[2];
//							numerosDestinatarios[0] = "691038553";
//							numerosDestinatarios[1] = "622300543";
//							_enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), "ASUNTO TEST", "CUERPO TEST", false);
							
							
							idSolicitudEcos = preparaEnvioSMS(envio, false);
							LOGGER.info("SMS enviado con éxito");
							envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
							envio.setFechamodificacion(new Date());
							_envEnviosMapper.updateByPrimaryKey(envio);
							break;
						case SigaConstants.TIPO_ENVIO_BUROSMS:
							//TEST PARA INTEGRACION
//							remitente = new CenDirecciones();
//							remitente.setCorreoelectronico("bherrero@deloitte.es");
//							numerosDestinatarios = new String[2];
//							numerosDestinatarios[0] = "691038553";
//							numerosDestinatarios[1] = "622300543";
//							_enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), "ASUNTO TEST", "CUERPO TEST", true);
							
							
							idSolicitudEcos = preparaEnvioSMS(envio, true);
							LOGGER.info("BURO SMS enviado con éxito");
							envio.setIdestado(SigaConstants.ENVIO_PROCESADO);
							envio.setFechamodificacion(new Date());
							_envEnviosMapper.updateByPrimaryKey(envio);
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

	private String preparaEnvioSMS(EnvEnvios envio, boolean isBuroSMS) {

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

		// obtenemos las consultas de destinatarios asociadas a la plantilla de
		// envio
		ModPlantillaenvioConsultaExample consultaPlantillaExample = new ModPlantillaenvioConsultaExample();
		consultaPlantillaExample.createCriteria().andIdplantillaenviosEqualTo(plantilla.getIdplantillaenvios())
				.andIdtipoenviosEqualTo(plantilla.getIdtipoenvios());
		List<ModPlantillaenvioConsulta> consultasAsociadas = _modPlantillaenvioConsultaMapper
				.selectByExample(consultaPlantillaExample);

		List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();

		for (int i = 0; i < consultasAsociadas.size(); i++) {
			ConConsultaExample consultaExample = new ConConsultaExample();
			consultaExample.createCriteria().andIdconsultaEqualTo(consultasAsociadas.get(i).getIdconsulta()).andIdinstitucionEqualTo(consultasAsociadas.get(i).getIdinstitucion())
					.andIdobjetivoEqualTo(Long.valueOf(SigaConstants.ID_OBJETIVO_DESTINATARIOS));
			List<ConConsulta> consultaSelected = _conConsultaMapper.selectByExampleWithBLOBs(consultaExample);

			if (consultaSelected != null && consultaSelected.size() > 0) {
				ConConsulta consulta = consultaSelected.get(0);
				int inicioSelect = consulta.getSentencia().indexOf("<SELECT>") + 8;
				int finSelect = consulta.getSentencia().indexOf("</SELECT>");
				String selectConEtiquetas = consulta.getSentencia().substring(inicioSelect, finSelect);
				String aliasIdPersona = obtenerAliasIdPersona(selectConEtiquetas.trim());
				String aliasIdInstitucion = obtenerAliasIdInstitucion(selectConEtiquetas.trim());
				String aliasCorreo = obtenerAliasCorreoElectronico(selectConEtiquetas.trim());
				String aliasMovil = obtenerAliasMovil(selectConEtiquetas.trim());

				String query = obtenerCamposConsulta(consulta.getSentencia());
				List<Map<String, Object>> resultDestinatarios = _conConsultasExtendsMapper.ejecutarConsultaString(query);

				// Recorremos todos los destinatarios de los resultados de la
				// consulta.
				for (Map<String, Object> map : resultDestinatarios) {
					destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasIdInstitucion, aliasCorreo, aliasMovil));
				}
				
				LOGGER.info("Destinatarios encontrados: " + destinatarios.size());
				String[] numerosDestinatarios = new String[destinatarios.size()];
				
				// Obtenemos los numeros de los destinatarios.
				for (int x = 0; x < numerosDestinatarios.length; x++) {
					numerosDestinatarios[x] = destinatarios.get(x).getMovil();
				}

				// Obtenemos las consultas asociadas a la plantilla de envio.
				EnvConsultasenvioExample exampleConsulta = new EnvConsultasenvioExample();
				Long objetivoDestinatarios = new Long(1);
				Long objetivoDatos = new Long(4);
				exampleConsulta.createCriteria().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdenvioEqualTo(envio.getIdenvio()).andFechabajaIsNotNull().andIdobjetivoEqualTo(objetivoDestinatarios);
				
				exampleConsulta.or().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdenvioEqualTo(envio.getIdenvio()).andFechabajaIsNotNull().andIdobjetivoEqualTo(objetivoDatos);
				// TODO: CAMBIAR A SELECTWITHCLOB CUANDO SE CAMBIE EN BDD
				List<EnvConsultasenvio> consultasPlantilla = _envConsultasenvioMapper.selectByExample(exampleConsulta);

				// Las ejecutamos y obtenemos los resultados
				List<Map<String, Object>> resultadosConsultas = new ArrayList<Map<String, Object>>();
				for (EnvConsultasenvio envConsultasenvio : consultasPlantilla) {
					List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(envConsultasenvio.getConsulta());
					resultadosConsultas.addAll(result);
				}
				String asuntoFinal = remplazarCamposAsunto(plantilla.getAsunto(), resultadosConsultas);
				String cuerpoFinal = remplazarCamposCuerpo(plantilla.getAsunto(), resultadosConsultas);
				// Realizamos el envio por SMS
				idSolicitudEcos = _enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), asuntoFinal, cuerpoFinal, isBuroSMS);
				
			}else{
				LOGGER.error("No se han encontrado consultas de destinatarios asociadas a la plantilla de envío");
			}
		}
		return idSolicitudEcos;
	}

	private String remplazarCamposAsunto(String asunto, List<Map<String, Object>> resultadosConsultas) {
		List<String> columnsKey = new ArrayList<String>();
		
		//Obtenemos todos los selects de las consultas
		for (Map<String, Object> map : resultadosConsultas) {
			for (String value : map.keySet()) {
				for (String columna : columnsKey) {
					if(!columna.contains(value)){
						columnsKey.add(value);
					}
				}
			}
		}
		
		//reemplazamos en la plantilla
//		for (Map<String, Object> map : resultadosConsultas) {
//			asunto = asunto.replace("%%"+columnsKey.get, newChar)
//		}
		
		return null;
	}

	private String remplazarCamposCuerpo(String asunto, List<Map<String, Object>> resultadosConsultas) {
		// TODO Auto-generated method stub
		return null;
	}

	private String obtenerCamposConsulta(String consulta) {

		consulta = consulta.replace("<SELECT>", "");
		consulta = consulta.replace("</SELECT>", "");
		consulta = consulta.replace("<FROM>", "");
		consulta = consulta.replace("</FROM>", "");
		consulta = consulta.replace("<WHERE>", "");
		consulta = consulta.replace("</WHERE>", "");
		consulta = consulta.replace("<UNION>", "");
		consulta = consulta.replace("</UNION>", "");
		consulta = consulta.replace("<UNIONALL>", "");
		consulta = consulta.replace("</UNIONALL>", "");
		consulta = consulta.replace("<GROUPBY>", "");
		consulta = consulta.replace("</GROUPBY>", "");
		consulta = consulta.replace("<ORDERBY>", "");
		consulta = consulta.replace("</ORDERBY>", "");

		return consulta;
	}

	private String obtenerAliasIdInstitucion(String select) {

		String idInstitucion = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");

		for (String string : selects) {
			if (string.indexOf("CEN_CLIENTE.IDINSTITUCION AS") > 0) {
				int inicio = string.indexOf("CEN_CLIENTE.IDINSTITUCION AS") + string.length();
				idInstitucion = string.substring(inicio);
			}
		}

		return idInstitucion;
	}

	private String obtenerAliasIdPersona(String select) {

		String idPersona = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");

		for (String string : selects) {
			string = string.trim();
			if (string.indexOf("CEN_CLIENTE.IDPERSONA AS") > 0) {
				int inicio = string.indexOf("CEN_CLIENTE.IDPERSONA AS") + string.length();
				idPersona = string.substring(inicio);
				idPersona = idPersona.replace("\"", "");
				idPersona = idPersona.replace("\"", "");
			}
		}

		return idPersona;
	}

	private String obtenerAliasCorreoElectronico(String select) {

		String correo = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");

		for (String string : selects) {
			if (string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS") > 0) {
				int inicio = string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS") + string.length();
				correo = string.substring(inicio);
				correo = correo.replace("\"", "");
				correo = correo.replace("\"", "");
			}
		}

		return correo;
	}

	private String obtenerAliasMovil(String select) {

		String correo = "";

		select = select.toUpperCase();
		String[] selects = select.split(",");

		for (String string : selects) {
			if (select.indexOf("CEN_DIRECCIONES.MOVIL AS") > 0) {
				int inicio = string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS") + string.length();
				correo = string.substring(inicio);
				correo = correo.replace("\"", "");
				correo = correo.replace("\"", "");
			}
		}

		return correo;
	}

	private DestinatarioItem obtenerDestinatario(Map<String, Object> registro, String idPersona, String idInstitucion,
			String correoElectronico, String movil) {

		DestinatarioItem destinatario = new DestinatarioItem();

		// Recojemos los campos
		Object campoIdPersona = registro.get(idPersona.trim());
		Object campoidInstitucion = registro.get(idInstitucion.trim());
		Object campoCorreo = registro.get(correoElectronico.trim());

		CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(campoIdPersona.toString()));
		destinatario.setNombre(persona.getNombre());
		destinatario.setApellidos1(persona.getApellidos1());
		destinatario.setApellidos2(persona.getApellidos2());
		destinatario.setCorreoElectronico(campoCorreo.toString());
		destinatario.setMovil(movil);

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
									String nombreFicheroSalida = _dialogoComunicacionService.obtenerNombreFicheroSalida(String.valueOf(consultaMulti.getIdmodelocomunicacion()), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, numFicheros, pathFicheroSalida);
									
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
									
									DatosDocumentoItem docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida);
									
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
						String nombreFicheroSalida = _dialogoComunicacionService.obtenerNombreFicheroSalida(String.valueOf(idModeloComunicacion), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, 0, pathFicheroSalida);
						
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
						
						DatosDocumentoItem docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida);
						
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
