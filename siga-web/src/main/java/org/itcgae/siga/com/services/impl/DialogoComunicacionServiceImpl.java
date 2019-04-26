package org.itcgae.siga.com.services.impl;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.ByteResponseDto;
import org.itcgae.siga.DTOs.com.CampoDinamicoItem;
import org.itcgae.siga.DTOs.com.ClaseComunicacionItem;
import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.ConsultaEnvioItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DatosEnvioDTO;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.GenerarComunicacionItem;
import org.itcgae.siga.DTOs.com.KeyItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
import org.itcgae.siga.DTOs.com.ModeloDialogoItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.com.ModelosEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDateDTO;
import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.DTOs.com.TipoEnvioDTO;
import org.itcgae.siga.DTOs.com.TipoEnvioItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.FORMATO_SALIDA;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosExample;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicacionRuta;
import org.itcgae.siga.db.entities.ModClasecomunicacionRutaExample;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvio;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioKey;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionRutaMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantillaenvioMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvConsultasEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModClasecomunicacionRutaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModClasecomunicacionesExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModKeyclasecomunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaEnvioConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModRelPlantillaSufijoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspose.words.Document;

@Service
public class DialogoComunicacionServiceImpl implements IDialogoComunicacionService{
	
	private Logger LOGGER = Logger.getLogger(DialogoComunicacionServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private IConsultasService _consultasService;
	
	@Autowired
	private IGeneracionDocumentosService _generacionDocService;
	
	@Autowired
	private GenParametrosExtendsMapper _genParametrosExtendsMapper;
	
	@Autowired
	private ModClasecomunicacionRutaExtendsMapper _modClasecomunicacionRutaExtendsMapper;
	
	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;
	
	@Autowired
	private ModModeloPlantillaDocumentoExtendsMapper _modModeloPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper _modPlantillaDocumentoConsultaExtendsMapper;
	
	@Autowired
	private ModModeloPlantillaenvioMapper _modModeloPlantillaenvioMapper;
	
	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;
	
	@Autowired
	private ModKeyclasecomunicacionExtendsMapper _modKeyclasecomunicacionExtendsMapper;
	
	@Autowired
	private ModRelPlantillaSufijoExtendsMapper _modRelPlantillaSufijoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocumentoExtendsMapper _modPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ModPlantilladocumentoMapper _modPlantilladocumentoMapper;
	
	@Autowired
	private EnvPlantillaEnviosExtendsMapper _envPlantillaEnviosExtendsMapper;
	
	@Autowired
	private ConConsultaMapper _conConsultaMapper;
	
	@Autowired
	private ModPlantillaEnvioConsultaExtendsMapper _modPlantillaEnvioConsultaExtendsMapper;
	
	@Autowired
	private EnvConsultasenvioMapper _envConsultasenvioMapper;
	
	@Autowired
	private EnvConsultasEnvioExtendsMapper _envConsultasEnvioExtendsMapper;
	
	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private EnvEnvioprogramadoMapper _envEnvioprogramadoMapper;
	
	@Autowired
	private EnvHistoricoestadoenvioMapper _envHistoricoestadoenvioMapper;
	
	@Autowired
	private CenInstitucionMapper _cenInstitucion;
	
	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;
	
	@Autowired
	private ModClasecomunicacionesMapper _modClasecomunicacionesMapper;
	
	@Autowired
	private EnvDocumentosMapper _envDocumentosMapper;
	
	@Autowired
	private CenPersonaMapper _cenPersonaMapper;
	
	@Autowired
	private EnvDestinatariosMapper _envDestinatariosMapper;		

	@Autowired
	private ModClasecomunicacionesExtendsMapper _modClasecomunicacionesExtendsMapper;
	
	@Override
	public ComboDTO obtenerClaseComunicaciones(HttpServletRequest request, String rutaClaseComunicacion) {
		LOGGER.info("claseComunicacion() -> Entrada al servicio para obtener combo clases comunicacion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _modClasecomunicacionRutaExtendsMapper.getClaseComunicaciones(rutaClaseComunicacion);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo clases comunicacion");
		
		return comboDTO;
	}



	@Override
	public ModelosComunicacionSearch obtenerModelos(HttpServletRequest request, ModeloDialogoItem modeloDTO) {
		
		LOGGER.info("obtenerModelos() -> Entrada al servicio para obtener los modelos de comunicacion");
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		
		ModelosComunicacionSearch respuesta = new ModelosComunicacionSearch();
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (usuarios != null && usuarios.size() > 0) {
				try{
					AdmUsuarios usuario = usuarios.get(0);
					List<ModelosComunicacionItem> modelos = _modModeloComunicacionExtendsMapper.selectModelosComunicacionDialogo(String.valueOf(idInstitucion), modeloDTO.getIdClaseComunicacion(), modeloDTO.getIdModulo(), usuario.getIdlenguaje(), modeloDTO.getIdConsulta(), perfiles);
					
					/*for (ModelosComunicacionItem modelosComunicacionItem : modelos) {
						ComboDTO comboDTO = new ComboDTO();
						List<ComboItem> comboItems = _modModeloComunicacionExtendsMapper.selectPlantillasModelos(modelosComunicacionItem.getIdModeloComunicacion(), usuario.getIdinstitucion());
						if(null != comboItems && comboItems.size() > 0) {
							ComboItem element = new ComboItem();
							element.setLabel("");
							element.setValue("");
							comboItems.add(0, element);
						}		
						comboDTO.setCombooItems(comboItems);
						modelosComunicacionItem.setPlantillas(comboItems);
					}*/
					respuesta.setModelosComunicacionItems(modelos);
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener los modelos");
					error.setMessage(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		LOGGER.info("obtenerModelos() -> Salida del servicio para obtener los modelos de comunicacion");
		
		return respuesta;
	}



	@Override
	public TipoEnvioDTO obtenertipoEnvioPlantilla(HttpServletRequest request, String idPlantilla) {
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Entrada al servicio para obtener el tipo de envio de la plantilla");
		
		TipoEnvioDTO response = new TipoEnvioDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				
				try{
					AdmUsuarios usuario = usuarios.get(0);
					TipoEnvioItem tipoEnvio = _envPlantillaEnviosExtendsMapper.selectTipoEnvioPlantilla(idInstitucion, usuario.getIdlenguaje(), idPlantilla);	
					response.setTipoEnvio(tipoEnvio);
				}catch(Exception e){
					LOGGER.error("Error en obtenertipoEnvioPlantilla :: ", e);
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener los modelos");
					error.setMessage(e.getMessage());
					response.setError(error);
				}				
			}
		}
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Salida del servicio para obtener el tipo de envio de la plantilla");
		
		return response;
	}



	@Override
	public ByteResponseDto descargarComunicacion(HttpServletRequest request, DialogoComunicacionItem dialogo) {
		LOGGER.info("descargarComunicacion() -> Entrada al servicio para descargar la documentación de la comunicación");
		
		byte [] zip = null;
		ByteResponseDto response = new ByteResponseDto();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		List<DatosDocumentoItem> listaFicheros = null;
		GenerarComunicacionItem generarComunicacion = new GenerarComunicacionItem();
		Error error = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				try{
					generarComunicacion = generarComunicacion(dialogo,usuario, false);
					
					listaFicheros = generarComunicacion.getListaDocumentos();
					
					if(listaFicheros != null){
//						if(listaFicheros.size() > 1) {
							zip = WSCommons.zipBytes(listaFicheros);
							response.setData(zip);
//						}else {
//							response.setNombre(listaFicheros.get(0).getFileName().toString());
//							response.setData(listaFicheros.get(0).getDatos());
//						}
					}else{
						LOGGER.debug("No se han obtenido documentos");
						error.setCode(500);
						error.setDescription("No se han obtenido documentos");
						error.setMessage("No se han obtenido documentos");
						response.setError(error);
					}				
					
				}catch(Exception e){
					LOGGER.error("Error al guardar los documentos",e);
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al guardar los documentos");
					response.setError(error);
				}			
			}
		}
		
		LOGGER.info("descargarComunicacion() -> Salida del servicio para descargar la documentación de la comunicación");
		return response;
	}

	
	public GenerarComunicacionItem generarComunicacion(DialogoComunicacionItem dialogo, AdmUsuarios usuario, boolean esEnvio) throws Exception{
		LOGGER.info("generarComunicacion() -> Entrada al servicio para generar los documentos a comunicar");
		boolean continua = true;
		
		GenerarComunicacionItem generarComunicacion = new GenerarComunicacionItem();
		List<Document> listaDocumentos = new ArrayList<Document>();
		List<DatosDocumentoItem> listaFicheros = new ArrayList<DatosDocumentoItem>();
		List<DatosEnvioDTO> listaConsultasYDestinatario = new ArrayList<DatosEnvioDTO>();
		List<ModelosEnvioItem> listaModelosEnvio = new ArrayList<ModelosEnvioItem>();
		DestinatarioItem destinatario = new DestinatarioItem();
		
		HashMap<String,Object> hDatosGenerales = new HashMap<String, Object>();		
		HashMap<String,Object> hDatosFinal = new HashMap<String, Object>();	
		
		List<List<Map<String,Object>>> listaDatosExcel = new ArrayList<List<Map<String,Object>>>();
		
		int numFicheros = 0;
		
		String rutaPlantillaClase = "";
		ModClasecomunicaciones modClase = null;

		try{
			if(dialogo != null && dialogo.getModelos() != null && dialogo.getModelos().size() > 0){
				
				//Obtenemos el campo del Sufijo asociado a la clase de la comunicación
				
				if(dialogo.getIdClaseComunicacion() != null) {
					modClase = _modClasecomunicacionesMapper.selectByPrimaryKey(Short.parseShort(dialogo.getIdClaseComunicacion()));
				}
				
				String campoSufijo = "";
				
				if(modClase != null){
					campoSufijo = modClase.getSufijo();
					rutaPlantillaClase = modClase.getRutaplantilla();
				}
				
				if(rutaPlantillaClase == null || "".equals(rutaPlantillaClase)) {
					rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
				}else {
					rutaPlantillaClase = rutaPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, String.valueOf(usuario.getIdinstitucion()));
				}
				
				generarComunicacion.setFechaProgramada(dialogo.getFechaProgramada());
				
				for(ModelosComunicacionItem modelo :dialogo.getModelos()){
					ModelosEnvioItem modeloEnvioItem = new ModelosEnvioItem();
					
					LOGGER.debug("Obtenemos las key de la clase de comunicación");
					String idClaseComunicacion = dialogo.getIdClaseComunicacion();
					
					List<KeyItem> listaKey = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Short.parseShort(idClaseComunicacion));
					
					LOGGER.debug("Obtenemos las plantillas de documento asociadas al modelo");
					List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectInformesGenerar(Long.parseLong(modelo.getIdModeloComunicacion()));
					
					// Obtenemos la plantilla de envio seleccionada en el modelo
					List<ConsultaItem> listaConsultasPlantillaEnvio = null;
					if(modelo.getIdPlantillaEnvio() != null && modelo.getIdTipoEnvio()!=null){
						listaConsultasPlantillaEnvio = _modPlantillaEnvioConsultaExtendsMapper.selectPlantillaEnvioConsultas(usuario.getIdinstitucion(), Integer.parseInt(modelo.getIdPlantillaEnvio()), Short.parseShort(modelo.getIdTipoEnvio()));
					}
					
					String consultaEjecutarCondicional = "";
					Long idConsultaEjecutarCondicional = null;
					// Por cada conjunto de valores key se generan los documentos
					
					List<List<String>> listaKeyFiltros = dialogo.getSelectedDatos();					
						
					boolean ejecutarConsulta = false;
					
					if(dialogo.getIdClaseComunicacion().equals(SigaConstants.ID_CLASE_CONSULTA_GENERICA)  && (listaKeyFiltros == null || listaKeyFiltros.size() == 0)) {
						listaKeyFiltros = new ArrayList<List<String>>();
						List<String> listaVacia = new ArrayList<String>();
						listaKeyFiltros.add(listaVacia);
						ejecutarConsulta = true;
					}
					
					if((listaKeyFiltros != null && listaKeyFiltros.size() > 0) || ejecutarConsulta){
						for(int i=0; i< listaKeyFiltros.size(); i ++){	
							List<String> listaStringKey = listaKeyFiltros.get(i);							
							HashMap<String, String> mapaClave = new HashMap<String, String>();
							if(!ejecutarConsulta) {
								for(int j = 0; j < listaKey.size(); j++){
									KeyItem key = listaKey.get(j);
									mapaClave.put(key.getNombre(), listaStringKey.get(j));
								}
							}
												
							
							// Nuevo envio = nueva lista de consultas
							
							List<ConsultaEnvioItem> listaConsultasEnvio = new ArrayList<ConsultaEnvioItem>();
							
							for(PlantillaModeloDocumentoDTO plantilla:plantillas){								
								String nombrePlantilla = "";
								Long idPlantillaGenerar = null;
								boolean esExcel = false;
								//Obtenemos el nombre de la plantilla
								if(plantilla.getIdPlantillas() != null){
									ModPlantilladocumentoExample example = new ModPlantilladocumentoExample();
									List<Long> idValues = new ArrayList<Long>();
									String [] idPlantillas = plantilla.getIdPlantillas().split(",");
									
									if(idPlantillas != null && idPlantillas.length > 0){																			
										for(String idPlantilla :idPlantillas){
											idValues.add(Long.parseLong(idPlantilla));
										}
									}
									
									if(plantilla.getFormatoSalida() != null && Short.parseShort(plantilla.getFormatoSalida()) == SigaConstants.FORMATO_SALIDA.XLS.getCodigo()) {
										esExcel = true;
									}
									
									example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(usuario.getIdlenguaje());
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
								
								LOGGER.debug("Obtenemos las consultas condicional: " + plantilla.getIdPlantillas());
								List<ConsultaItem> consultasItemCondicional = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

								if(consultasItemCondicional != null && consultasItemCondicional.size() > 0){
									for(ConsultaItem consulta:consultasItemCondicional){
										
										consultaEjecutarCondicional = reemplazarConsultaConClaves(usuario, dialogo, consulta, mapaClave, esEnvio);
										
										idConsultaEjecutarCondicional = Long.parseLong(consulta.getIdConsulta());
										
										List<Map<String,Object>> result = _consultasService.ejecutarConsultaConClaves(consultaEjecutarCondicional);
										
										if(result != null && result.size() > 0){
											LOGGER.debug("Se cumple la consulta condicional del informe: " + plantilla.getIdInforme());
											continua = true;								
										}else{
											continua = false;
											LOGGER.info("La consulta condicional no ha devuelto resultados");
										}
									}						
								}else{
									LOGGER.debug("No hay consulta condicional para el informe: " + plantilla.getIdInforme());
									continua = true;
								}
								
								if(continua){									
									LOGGER.debug("Obtenemos la consulta de destinatario: " + plantilla.getIdInforme());
									List<ConsultaItem> consultasItemDest = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
									
									if(consultasItemDest != null && consultasItemDest.size() > 0){
										for(ConsultaItem consulta:consultasItemDest){	
																						
											String consultaEjecutarDestinatarios = reemplazarConsultaConClaves(usuario, dialogo, consulta, mapaClave, esEnvio);

											List<Map<String,Object>> result = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDestinatarios);
											
											if(result != null && result.size() > 0){
												LOGGER.debug("Se han obtenido " + result.size() + " destinatarios");
												
												for(Map<String,Object> dest: result){
													LOGGER.debug("Guardamos el resultado de la query para cada destinatario");
													if(esEnvio){
														LOGGER.debug("Guardamos las consultas para la generación del envio");
														
														//Guardamos la consulta condicional
														ConsultaEnvioItem consultaEnvio= new ConsultaEnvioItem();
														
														if(idConsultaEjecutarCondicional != null) {
															consultaEnvio.setIdConsulta(idConsultaEjecutarCondicional);
															consultaEnvio.setConsulta(consultaEjecutarCondicional);
															consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
															consultaEnvio.setIdObjetivo(SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());
															consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
															consultaEnvio.setIdPlantillaDoc(idPlantillaGenerar);
															consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
															consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
															listaConsultasEnvio.add(consultaEnvio);
														}														
														
														//Guardamos la consulta de destinatarios
														
														consultaEnvio = new ConsultaEnvioItem();
														consultaEnvio.setConsulta(consultaEjecutarDestinatarios);
														consultaEnvio.setIdConsulta(Long.parseLong(consulta.getIdConsulta()));
														consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
														consultaEnvio.setIdObjetivo(SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
														consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
														consultaEnvio.setIdPlantillaDoc(idPlantillaGenerar);
														consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
														consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
														
														listaConsultasEnvio.add(consultaEnvio);
														
														// Reemplazamos los campos para las consultas de la plantilla de envio

														if(listaConsultasPlantillaEnvio != null && listaConsultasPlantillaEnvio.size() > 0){
															for(ConsultaItem consultaPlantilla:listaConsultasPlantillaEnvio){
																String consultaPlantillaEnvio = reemplazarConsultaConClaves(usuario, dialogo, consultaPlantilla, mapaClave, esEnvio);
																
																consultaEnvio= new ConsultaEnvioItem();
																
																consultaEnvio.setIdConsulta(Long.parseLong(consultaPlantilla.getIdConsulta()));
																consultaEnvio.setConsulta(consultaPlantillaEnvio);
																consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
																consultaEnvio.setIdObjetivo(Long.parseLong(consultaPlantilla.getIdObjetivo()));
																consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
																consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
																consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
																consultaEnvio.setIdPlantillaDoc(null);
																listaConsultasEnvio.add(consultaEnvio);
															}
														}
														
													}
													numFicheros = 0;
													
													hDatosGenerales = new HashMap<String, Object>();
													hDatosGenerales.putAll(dest);
													
													hDatosFinal = new HashMap<String, Object>();
													
													
													LOGGER.debug("Obtenemos la consulta de multidocumento para la plantilla: " + plantilla.getIdInforme());
													List<ConsultaItem> consultasItemMulti = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
													if(consultasItemMulti != null && consultasItemMulti.size() > 0){
														
														for(ConsultaItem consultaMulti:consultasItemMulti){	
															String consultaEjecutarMulti = reemplazarConsultaConClaves(usuario, dialogo, consultaMulti, mapaClave, esEnvio);
															
															if(esEnvio){
																//Guardamos la consulta multidocumento																
																ConsultaEnvioItem consultaEnvio = new ConsultaEnvioItem();
																consultaEnvio.setConsulta(consultaEjecutarMulti);
																consultaEnvio.setIdConsulta(Long.parseLong(consultaMulti.getIdConsulta()));
																consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
																consultaEnvio.setIdObjetivo(SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
																consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
																consultaEnvio.setIdPlantillaDoc(idPlantillaGenerar);
																consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
																consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
																
																listaConsultasEnvio.add(consultaEnvio);
															}
															
															List<Map<String,Object>> resultMulti = _consultasService.ejecutarConsultaConClaves(consultaEjecutarMulti);
															
															if(resultMulti != null && resultMulti.size() > 0){
																for(int k = 0;k<resultMulti.size();k++){
																	
																	// Por cada registro generamos un documento
																	numFicheros++;
																	
																	//Otenemos el nombre del fichero de salida
																	
																	//Obtenemos la ruta temporal
																	GenPropertiesKey key = new GenPropertiesKey();
																	key.setFichero(SigaConstants.FICHERO_SIGA);
																	key.setParametro(SigaConstants.parametroRutaSalidaInformes);
																	
																	GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
																	
																	String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + dialogo.getIdInstitucion() + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator;
																	
																	key = new GenPropertiesKey();
																	key.setFichero(SigaConstants.FICHERO_SIGA);
																	key.setParametro(SigaConstants.parametroRutaPlantillas);
																	
																	GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
																	
																	String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + rutaPlantillaClase + SigaConstants.pathSeparator;
																	
																	
																	String pathFicheroSalida = rutaTmp;
																	String pathPlantilla = rutaPlantilla;
																	
																	//Si no existe el directorio temporal lo creamos
																	File dir = new File(pathFicheroSalida);
																	if(!dir.exists()){
																		dir.mkdirs();
																	}	
																	
																	
																	File filePlantilla = new File(pathPlantilla + nombrePlantilla);
																	if(!filePlantilla.exists()){
																		throw new SigaExceptions("No existe la plantilla de documento");
																	}
																																		
																	Document doc = null;

																	hDatosGenerales.putAll(resultMulti.get(k));
																	
																	// Por cada resultado ejecutamos las consultas de datos
																	LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
																	List<ConsultaItem> consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
																	
																	for(ConsultaItem consultaDatos:consultasItemDatos){	
																		
																		String consultaEjecutarDatos = reemplazarConsultaConClaves(usuario, dialogo, consultaDatos, mapaClave, esEnvio);
																		
																		if(esEnvio){
																			//Guardamos la consulta datos																			
																			ConsultaEnvioItem consultaEnvio = new ConsultaEnvioItem();
																			consultaEnvio.setConsulta(consultaEjecutarDatos);
																			consultaEnvio.setIdConsulta(Long.parseLong(consultaDatos.getIdConsulta()));
																			consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
																			consultaEnvio.setIdObjetivo(SigaConstants.OBJETIVO.DATOS.getCodigo());
																			consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
																			consultaEnvio.setIdPlantillaDoc(idPlantillaGenerar);
																			consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
																			consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
																			
																			listaConsultasEnvio.add(consultaEnvio);
																		}
																		
																		List<Map<String,Object>> resultDatos = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDatos);
																		
																		if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
																			hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
																		}else{
																			if(resultDatos != null && resultDatos.size() > 0) {
																				hDatosGenerales.putAll(resultDatos.get(0));
																			}
																		}
																		
																		if(esExcel) {
																			listaDatosExcel.add(resultDatos);
																		}
																	}
																	
																	hDatosFinal.put("row", hDatosGenerales);
																	
																	//Obtenemos el sufijo del fichero para el caso de que se haya seleccionado el sufijo de entidad
																	String campoSufijoReplaced = "";
																	if(campoSufijo != null && !"".equals(campoSufijo)) {
																		campoSufijoReplaced = reemplazarSufijo(hDatosGenerales, mapaClave, campoSufijo);
																	}
																	
																	String nombreFicheroSalida = obtenerNombreFicheroSalida(modelo.getIdModeloComunicacion(), plantilla, hDatosGenerales, usuario.getIdlenguaje(), numFicheros, pathFicheroSalida, campoSufijoReplaced);
																																																		
																	if(!esEnvio){
																		LOGGER.debug("Generamos el documento");																
																		DatosDocumentoItem docGenerado = null;
																		
																		if(esExcel) {
																			docGenerado = _generacionDocService.generarExcel(pathPlantilla + nombrePlantilla, pathFicheroSalida, nombreFicheroSalida, listaDatosExcel);
																		}else {
																			doc = new Document(pathPlantilla + nombrePlantilla);
																			
																			doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);																			
																			
																			boolean firmado = false;
																			
																			if(plantilla.getFormatoSalida() != null){
																				FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
																				if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
																					firmado = true;
																				}
																			}
																			
																			docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
																		}
																		
																		
																		listaFicheros.add(docGenerado);
																		listaDocumentos.add(doc);
																	}else{
																		//Cogemos todas las consultas y le metemos el nombre del fichero
																		if(listaConsultasEnvio != null && listaConsultasEnvio.size() > 0){
																			for(ConsultaEnvioItem consultaEnvio : listaConsultasEnvio){
																				consultaEnvio.setSufijo(campoSufijoReplaced);
																				consultaEnvio.setPathFichero(pathFicheroSalida);
																				consultaEnvio.setNombreFichero(nombreFicheroSalida);
																			}
																		}																		
																	}																															
																}														
															}
															
														}
													}else{
														LOGGER.debug("No hay consulta de multidocumento");
														
														GenPropertiesKey key = new GenPropertiesKey();
														key.setFichero(SigaConstants.FICHERO_SIGA);
														key.setParametro(SigaConstants.parametroRutaSalidaInformes);
														
														GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
														
														String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + dialogo.getIdInstitucion() + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator;
														
														key = new GenPropertiesKey();
														key.setFichero(SigaConstants.FICHERO_SIGA);
														key.setParametro(SigaConstants.parametroRutaPlantillas);
														
														GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
														
														String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + rutaPlantillaClase + SigaConstants.pathSeparator;
														
														
														String pathFicheroSalida = rutaTmp;
														String pathPlantilla = rutaPlantilla;
														
														//Si no existe el directorio temporal lo creamos
														File dir = new File(pathFicheroSalida);
														if(!dir.exists()){
															dir.mkdirs();
														}
														
														File filePlantilla = new File(pathPlantilla + nombrePlantilla);
														if(!filePlantilla.exists()){
															throw new SigaExceptions("No existe la plantilla de documento");
														}		
														
														Document doc = null;
														
														// Por cada resultado ejecutamos las consultas de datos
														LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
														List<ConsultaItem> consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
														
														for(ConsultaItem consultaDatos:consultasItemDatos){															
															String consultaEjecutarDatos = reemplazarConsultaConClaves(usuario, dialogo, consultaDatos, mapaClave, esEnvio);
															
															if(esEnvio){
																//Guardamos la consulta de datos																		
																ConsultaEnvioItem consultaEnvio = new ConsultaEnvioItem();
																consultaEnvio.setConsulta(consultaEjecutarDatos);
																consultaEnvio.setIdConsulta(Long.parseLong(consultaDatos.getIdConsulta()));
																consultaEnvio.setIdInstitucion(usuario.getIdinstitucion());
																consultaEnvio.setIdObjetivo(SigaConstants.OBJETIVO.DATOS.getCodigo());
																consultaEnvio.setUsuModificacion(Short.parseShort(String.valueOf(usuario.getIdusuario())));
																consultaEnvio.setIdPlantillaDoc(idPlantillaGenerar);
																consultaEnvio.setIdInforme(Long.parseLong(plantilla.getIdInforme()));
																consultaEnvio.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
																
																listaConsultasEnvio.add(consultaEnvio);
															}
															
															List<Map<String,Object>> resultDatos = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDatos);
															
															if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
																hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
															}else{
																if(resultDatos != null && resultDatos.size() > 0) {
																	hDatosGenerales.putAll(resultDatos.get(0));
																}
															}
															
															if(esExcel) {
																listaDatosExcel.add(resultDatos);
															}
														}
														
														hDatosFinal.put("row", hDatosGenerales);
														
														//Obtenemos el sufijo del fichero para el caso de que se haya seleccionado el sufijo de entidad
														String campoSufijoReplaced = "";
														if(campoSufijo != null && !"".equals(campoSufijo)) {
															campoSufijoReplaced = reemplazarSufijo(hDatosGenerales, mapaClave, campoSufijo);
														}
														
														
														String nombreFicheroSalida = obtenerNombreFicheroSalida(modelo.getIdModeloComunicacion(), plantilla, hDatosGenerales, usuario.getIdlenguaje(), numFicheros, pathFicheroSalida, campoSufijoReplaced);
														
														
														if(!esEnvio){
															LOGGER.debug("Generamos el documento");
															DatosDocumentoItem docGenerado = null;
															
															if(esExcel) {
																docGenerado = _generacionDocService.generarExcel(pathPlantilla + nombrePlantilla, pathFicheroSalida, nombreFicheroSalida, listaDatosExcel);
															}else {
																doc = new Document(pathPlantilla + nombrePlantilla);
																
																doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
																
																boolean firmado = false;
																
																if(plantilla.getFormatoSalida() != null){
																	FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
																	if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
																		firmado = true;
																	}
																}
																
																docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
															}															
															
															listaFicheros.add(docGenerado);
															listaDocumentos.add(doc);
														}else{
															//Cogemos todas las consultas y le metemos el nombre del fichero
															if(listaConsultasEnvio != null && listaConsultasEnvio.size() > 0){
																for(ConsultaEnvioItem consultaEnvio : listaConsultasEnvio){
																	consultaEnvio.setSufijo(campoSufijoReplaced);
																	consultaEnvio.setPathFichero(pathFicheroSalida);
																	consultaEnvio.setNombreFichero(nombreFicheroSalida);
																}
															}
														}													
													}
													
													//Obtenemos los campos del destinatario
													Object idPersona = dest.get(SigaConstants.ALIASIDPERSONA.trim());
													Object correo = dest.get(SigaConstants.ALIASCORREO.trim());
													Object movil = dest.get(SigaConstants.ALIASMOVIL.trim());
													Object domicilio = dest.get(SigaConstants.ALIASDOMICILIO.trim());
													
													CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona.toString()));
													destinatario = new DestinatarioItem();
													destinatario.setIdPersona(idPersona.toString());
													destinatario.setNombre(persona.getNombre());
													destinatario.setApellidos1(persona.getApellidos1());
													destinatario.setApellidos2(persona.getApellidos2());
													destinatario.setNIFCIF(persona.getNifcif());
													if(domicilio != null){
														destinatario.setDomicilio(domicilio.toString());
													}
													if(correo != null){
														destinatario.setCorreoElectronico(correo.toString());
													}
													if(movil != null){
														destinatario.setMovil(movil.toString());
													}
													
												}	
											}else{
												LOGGER.info("La consulta de destinatarios no ha devuelto resultados");
											}											
										}										
									}else{										
										if(ejecutarConsulta) {
											// Por cada resultado ejecutamos las consultas de datos
											LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
											List<ConsultaItem> consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
											
											for(ConsultaItem consultaDatos:consultasItemDatos){															
												String consultaEjecutarDatos = reemplazarConsultaConClaves(usuario, dialogo, consultaDatos, mapaClave, esEnvio);
												
												List<Map<String,Object>> resultDatos = _consultasService.ejecutarConsultaConClaves(consultaEjecutarDatos);
												
												if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
													hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
												}else{
													if(resultDatos != null && resultDatos.size() > 0) {
														hDatosGenerales.putAll(resultDatos.get(0));
													}													
												}
												
												listaDatosExcel.add(resultDatos);
											}
											
											hDatosFinal.put("row", hDatosGenerales);
											
											String campoSufijoReplaced = "";
											if(campoSufijo != null && !"".equals(campoSufijo)) {
												campoSufijoReplaced = reemplazarSufijo(hDatosGenerales, mapaClave, campoSufijo);
											}											
											
											//Obtenemos la ruta temporal
											GenPropertiesKey key = new GenPropertiesKey();
											key.setFichero(SigaConstants.FICHERO_SIGA);
											key.setParametro(SigaConstants.parametroRutaSalidaInformes);
											
											GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
											
											String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + dialogo.getIdInstitucion() + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator;
											
											key = new GenPropertiesKey();
											key.setFichero(SigaConstants.FICHERO_SIGA);
											key.setParametro(SigaConstants.parametroRutaPlantillas);
											
											GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
											
											String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + rutaPlantillaClase + SigaConstants.pathSeparator;
											
											
											String pathFicheroSalida = rutaTmp;
											String pathPlantilla = rutaPlantilla;
											
											//Si no existe el directorio temporal lo creamos
											File dir = new File(pathFicheroSalida);
											if(!dir.exists()){
												dir.mkdirs();
											}	
											
											
											/*File filePlantilla = new File(pathPlantilla + nombrePlantilla);
											if(!filePlantilla.exists()){
												throw new SigaExceptions("No existe la plantilla de documento");
											}*/
											

											String nombreFicheroSalida = obtenerNombreFicheroSalida(modelo.getIdModeloComunicacion(), plantilla, hDatosGenerales, usuario.getIdlenguaje(), numFicheros, pathFicheroSalida, campoSufijoReplaced);
											
											DatosDocumentoItem docGenerado = null;
											
											if(esExcel) {
												 docGenerado = _generacionDocService.generarExcel(pathPlantilla + nombrePlantilla, pathFicheroSalida, nombreFicheroSalida, listaDatosExcel);
											}else {
												Document doc = new Document(pathPlantilla + nombrePlantilla);
												
												doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
												
												boolean firmado = false;
												
												if(plantilla.getFormatoSalida() != null){
													FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
													if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
														firmado = true;
													}
												}
												
												docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
											}	
											
											
											listaFicheros.add(docGenerado);
											
										}else {
											LOGGER.error("No hay consulta de destinatario para el informe: " + plantilla.getIdInforme());
										}
									}
								}else{
									LOGGER.debug("No se ejecuta la generación del informe: " + plantilla.getIdInforme());
								}								
								
								//Por cada plantilla							
							}
							
							// Por cada key seleccionada
							if(esEnvio){
								DatosEnvioDTO dato = new DatosEnvioDTO();
								dato.setConsultas(listaConsultasEnvio);
								dato.setDestinatario(destinatario);
								listaConsultasYDestinatario.add(dato);
							}
						}
					}	
					
					// Por cada modelo
					
					modeloEnvioItem.setListaDatosEnvio(listaConsultasYDestinatario);
					if(modelo.getIdPlantillaEnvio() != null){
						modeloEnvioItem.setIdPlantillaEnvio(Integer.parseInt(modelo.getIdPlantillaEnvio()));
					}
					if(modelo.getIdTipoEnvio() != null){
						modeloEnvioItem.setIdTipoEnvio(Short.parseShort(modelo.getIdTipoEnvio()));
					}	
					modeloEnvioItem.setIdModeloComunicacion(Long.parseLong(modelo.getIdModeloComunicacion()));
					
					listaModelosEnvio.add(modeloEnvioItem);
				}	
				
				generarComunicacion.setListaModelosEnvio(listaModelosEnvio);
				generarComunicacion.setListaDocumentos(listaFicheros);
				
			}else{
				LOGGER.error("No hay modelos seleccionados");
			}
		}catch(Exception e){
			LOGGER.error("Error al generar la documentación",e);
			throw e;
		}
		
		LOGGER.info("generarComunicacion() -> Entrada al servicio para generar los documentos a comunicar");
		
		return generarComunicacion;
	}
	
	private String reemplazarSufijo(HashMap<String, Object> hDatosGenerales, HashMap<String, String> mapaClave, String campoSufijo) {
		HashMap<String,Object> hashMapRow = null;
		
		if(hDatosGenerales.get("row") != null){
			hashMapRow = (HashMap<String, Object>) hDatosGenerales.get("row");
		}
		
		String sufijo = "";
		if(campoSufijo != null) {
			String campos[] = campoSufijo.split(",");
			if(campos.length > 0) {
				for(String campo: campos) {
					Object identificacion = hDatosGenerales.get(campo);
					if(identificacion == null){
						if(hashMapRow != null && hashMapRow.get(campo) != null){
							identificacion = hashMapRow.get(campo);
						}else{
							if(mapaClave != null && mapaClave.get(campo) != null) {
								identificacion = mapaClave.get(campo);
							}else {
								identificacion = SigaConstants.CAMPO_IDENTIFICACION;
							}
						}
					}
					if(!sufijo.equalsIgnoreCase(""))sufijo = sufijo + "_";				
					sufijo = sufijo + String.valueOf(identificacion);
				}
			}else {
				sufijo = SigaConstants.CAMPO_IDENTIFICACION;
			}
			
		}
		return sufijo;
	}



	private String reemplazarConsultaConClaves(AdmUsuarios usuario, DialogoComunicacionItem dialogo, ConsultaItem consulta, HashMap<String, String> mapaClave, boolean esEnvio) throws ParseException, SigaExceptions{
		
		String sentencia = null;
		//Buscamos la consulta con sus parametros dinamicos
		
		consulta = findConsultaItem(dialogo.getConsultas(), consulta);
		
		if(consulta != null){
			// Reemplazamos los datos insertados desde pantalla		
			sentencia = _consultasService.procesarEjecutarConsulta(usuario, consulta.getSentencia(), consulta.getCamposDinamicos(), true);
			
			// Remplazamos las claves de la query
			if(mapaClave != null && mapaClave.size() > 0) {
				for (Map.Entry<String, String> entry : mapaClave.entrySet()) {
					sentencia = sentencia.replace(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + entry.getKey().toUpperCase() + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, entry.getValue());
				}
			}			
			
		}else{
			LOGGER.error("No se ha encontrado la consulta");
			throw new SigaExceptions("No se ha encontrado la consulta");
		}		
		
		return sentencia;
	}

	@Override
	public KeysDTO obtenerKeysClaseComunicacion(HttpServletRequest request, String idClaseComunicacion) {
		LOGGER.info("obtenerKeysClaseComunicacion() -> Entrada al servicio para obtener las keys asociadas a una clase de comunicación");
		
		KeysDTO keysDTO = new KeysDTO();
		List<KeyItem> listaKeys = new ArrayList<KeyItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				listaKeys = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Short.parseShort(idClaseComunicacion));
		
				keysDTO.setKeysItem(listaKeys);
				
			}
		}
		
		LOGGER.info("obtenerKeysClaseComunicacion() -> Salida del servicio para obtener las keys asociadas a una clase de comunicación");
		
		return keysDTO;
	}
	
	@Override
	public String obtenerNombreFicheroSalida(String idModeloComunicacion, PlantillaModeloDocumentoDTO plantilla, HashMap<String,Object> hDatosGenerales, String idLenguaje, int numFichero, String pathFicheroSalida, String campoSufijo){
		HashMap<String,Object> hashMapRow = null;
		String extension = "";
		String nombreFicheroSalida = plantilla.getNombreFicheroSalida();
		
		if(plantilla.getFormatoSalida() != null){
			FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));
			if(extensionObject != null){
				extension = extensionObject.getDescripcion();
			}									
		}
		
		// Obtenemos los sufijos del documento
		String sufijo = "";
		List<SufijoItem> sufijos = _modRelPlantillaSufijoExtendsMapper.selectSufijosPlantilla(Long.parseLong(idModeloComunicacion), Long.parseLong(plantilla.getIdInforme()), idLenguaje);
		
		if(hDatosGenerales.get("row") != null){
			hashMapRow = (HashMap<String, Object>) hDatosGenerales.get("row");
		}
		
		for(SufijoItem sufijoItem:sufijos){
			if(Short.valueOf(sufijoItem.getIdSufijo()).shortValue() == SigaConstants.SUFIJOS.NOMBRE_COLEGIADO.getCodigo().shortValue()){
				Object nombre = hDatosGenerales.get(SigaConstants.CAMPO_NOMBRE);
				Object apellidos = hDatosGenerales.get(SigaConstants.CAMPO_APELLIDOS);
				
				if(nombre == null){
					if(hashMapRow != null && hashMapRow.get(SigaConstants.CAMPO_NOMBRE) != null){
						nombre = hashMapRow.get(SigaConstants.CAMPO_NOMBRE);
					}else{
						nombre = SigaConstants.CAMPO_NOMBRE;
					}					
				}
				
				if(apellidos == null){
					if(hashMapRow != null && hashMapRow.get(SigaConstants.CAMPO_APELLIDOS) != null){
						apellidos = hashMapRow.get(SigaConstants.CAMPO_APELLIDOS);
					}else{
						apellidos = SigaConstants.CAMPO_APELLIDOS;
					}
				}
				
				if(!sufijo.equalsIgnoreCase(""))sufijo = sufijo + "_";				
				sufijo = sufijo + String.valueOf(nombre) + "_" + String.valueOf(apellidos);
				
			}else if(Short.valueOf(sufijoItem.getIdSufijo()).shortValue() == SigaConstants.SUFIJOS.NUMERO_COLEGIADO.getCodigo().shortValue()){
				Object numColegiado = hDatosGenerales.get(SigaConstants.CAMPO_NUM_COLEGIADO);
				if(numColegiado == null){
					if(hashMapRow != null && hashMapRow.get(SigaConstants.CAMPO_NUM_COLEGIADO) != null){
						numColegiado = hashMapRow.get(SigaConstants.CAMPO_NUM_COLEGIADO);
					}else{
						numColegiado = SigaConstants.CAMPO_NUM_COLEGIADO;
					}
				}
				if(!sufijo.equalsIgnoreCase(""))sufijo = sufijo + "_";				
				sufijo = sufijo + String.valueOf(numColegiado);
				
			}else if(Short.valueOf(sufijoItem.getIdSufijo()).shortValue() == SigaConstants.SUFIJOS.IDENTIFICACION.getCodigo().shortValue()){

				if(!sufijo.equalsIgnoreCase(""))sufijo = sufijo + "_";				
				sufijo = sufijo + String.valueOf(campoSufijo);
			}
		}
		
		String numero = "";
		if(numFichero > 0){
			numero = "_" + String.valueOf(numFichero);
		}
		
		String nombreFichero = nombreFicheroSalida + sufijo + numero + "." + extension;
		
		return nombreFichero;
	}



	@Override
	public ClaseComunicacionesDTO obtenerClaseComunicacionesUnica(HttpServletRequest request, String rutaClaseComunicacion) {
		LOGGER.info("obtenerClaseComunicacionesUnica() -> Entrada al servicio para obtener la clase de comunicacion asociada la ruta indicada");
		
		ClaseComunicacionesDTO response = new ClaseComunicacionesDTO();
		Error error = new Error();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					List<ClaseComunicacionItem> clasesComunicaciones = _modClasecomunicacionRutaExtendsMapper.getClaseComunicacionesUnica(rutaClaseComunicacion);
					response.setClasesComunicaciones(clasesComunicaciones);
				}catch(Exception e){
					LOGGER.error("Error al obtener la clase de comunicaciones", e);
					error.setCode(500);
					error.setDescription("Error al obtener la clase de comunicaciones");
					error.setMessage(e.getMessage());
					response.setError(error);
				}					
			}
		}
		
		LOGGER.info("obtenerClaseComunicacionesUnica() -> Salida del servicio para obtener la clase de comunicacion asociada la ruta indicada");
		return response;
	}



	@Override
	public ConsultasDTO obtenerCamposModelo(HttpServletRequest request, DialogoComunicacionItem dialogo) {
		LOGGER.info("obtenerCamposModelo() -> Entrada al servicio para obtener todos los campos dinamicos de las consultas asociadas a modelo");
		
		ConsultasDTO response = new ConsultasDTO();
		Error error = new Error();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				List<ConsultaItem> listaConsultasEjecutar = new ArrayList<ConsultaItem>();
				try{	
					if(dialogo.getModelos() != null && dialogo.getModelos().size() > 0){
						for(ModelosComunicacionItem modelo : dialogo.getModelos()){
							
							//Obtenemos las plantillas de documento del modelo
							
							List<DocumentoPlantillaItem> listaPlantillas = _modPlantillaDocumentoExtendsMapper.selectPlantillasByModelo(Long.parseLong(modelo.getIdModeloComunicacion()),usuario.getIdlenguaje());
							if(listaPlantillas != null && listaPlantillas.size() > 0){
								for(DocumentoPlantillaItem plantilla: listaPlantillas){
									//Obtenemos las consultas asociadas a las plantillas
									List<ConsultaItem> listaConsultas = _modPlantillaDocumentoConsultaExtendsMapper.selectPlantillaDocConsultas(idInstitucion, Long.parseLong(modelo.getIdModeloComunicacion()), Long.parseLong(plantilla.getIdPlantillaDocumento()), false);
									if(listaConsultas != null && listaConsultas.size() > 0){
										for(ConsultaItem consultaItem: listaConsultas){
											ConConsultaKey key = new ConConsultaKey();
											key.setIdconsulta(Long.parseLong(consultaItem.getIdConsulta()));
											key.setIdinstitucion(Short.parseShort(consultaItem.getIdInstitucion()));
											ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
											if(consulta != null){
												ArrayList<CampoDinamicoItem> campos = _consultasService.obtenerCamposDinamicos(usuario, consulta.getSentencia());
												consultaItem.setCamposDinamicos(campos);
												consultaItem.setSentencia(consulta.getSentencia());
												listaConsultasEjecutar.add(consultaItem);
											}
										}
									}else{
										LOGGER.info("La plantilla " + plantilla.getIdPlantillaDocumento() + " de documento no tiene consultas");
									}
								}
							}else{
								LOGGER.info("El modelo " + modelo.getIdModeloComunicacion() + " no tiene plantillas de documento asignadas");
							}
							
							//Obtenemos las consultas de la plantilla de envio seleccionada
							
							if(modelo.getIdPlantillaEnvio() != null && modelo.getIdTipoEnvio() != null){
								//Obtenemos las consultas asociadas a la plantilla
								List<ConsultaItem> listaEnvioConsultas = _modPlantillaEnvioConsultaExtendsMapper.selectPlantillaEnvioConsultas(idInstitucion, Integer.parseInt(modelo.getIdPlantillaEnvio()), Short.parseShort(modelo.getIdTipoEnvio()));
								if(listaEnvioConsultas != null && listaEnvioConsultas.size() > 0){
									for(ConsultaItem consultaEnvioItem : listaEnvioConsultas){			
										if(consultaEnvioItem != null){
											ArrayList<CampoDinamicoItem> campos = _consultasService.obtenerCamposDinamicos(usuario, consultaEnvioItem.getSentencia());
											consultaEnvioItem.setCamposDinamicos(campos);
											listaConsultasEjecutar.add(consultaEnvioItem);
										}
									}
								}else{
									LOGGER.info("La plantila de envio " + modelo.getIdPlantillaEnvio() + " no tiene consultas asociadas");
								}
							}else{
								LOGGER.error("Plantilla de envio no encontrada");
							}
							
							response.setConsultaItem(listaConsultasEjecutar);
						}
					}else{
						LOGGER.error("No se ha seleccionado ningún modelo de comunicaicones");
						error.setCode(500);
						error.setDescription("No se ha seleccionado ningún modelo de comunicaicones");
						error.setMessage("No se ha seleccionado ningún modelo de comunicaicones");
						response.setError(error);
					}
					
				}catch(Exception e){
					LOGGER.error("Error al obtener la clase de comunicaciones", e);
					error.setCode(500);
					error.setDescription("Error al obtener la clase de comunicaciones");
					error.setMessage(e.getMessage());
					response.setError(error);
				}					
			}
		}
		
		LOGGER.info("obtenerCamposModelo() -> Salida del servicio para obtener todos los campos dinamicos de las consultas asociadas a modelo");
		return response;
	}
	
	public ConsultaItem findConsultaItem(List<ConsultaItem> listaConsultas, ConsultaItem consulta){
		ConsultaItem consultaEncontrada = null;
		for(ConsultaItem consultaItem: listaConsultas){
			if(consultaEncontrada == null && consultaItem.getIdConsulta().equalsIgnoreCase(consulta.getIdConsulta())){
				consultaEncontrada = consultaItem;
			}
		}
		return consultaEncontrada;		
	}

	@Override
	public ResponseDateDTO obtenerFechaProgramada(HttpServletRequest request) {
		LOGGER.info("obtenerFechaProgramada() -> Entrada al servicio para obtener la fecha por defecto del envio programado");
		
		ResponseDateDTO response = new ResponseDateDTO();
		Error error = new Error();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					StringDTO desfaseMinutos = _genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.DESFASE_PROGRAMACION_ENVIO_MINUTOS, String.valueOf(idInstitucion));
					
					Date fecha = new Date();
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(fecha);
					calendar.add(Calendar.MINUTE, Integer.parseInt(desfaseMinutos.getValor()));

					Date fechaSalida = calendar.getTime();					
					response.setFecha(fechaSalida);
					
				}catch(Exception e){
					LOGGER.error("Error al obtener la fecha de programación del envio", e);
					error.setCode(500);
					error.setDescription("Error al obtener la fecha de programación del envio");
					error.setMessage(e.getMessage());
					response.setError(error);
				}					
			}
		}
		
		LOGGER.info("obtenerFechaProgramada() -> Salida del servicio para obtener la fecha por defecto del envio programado");
		return response;
	}



	@Override
	public ResponseDataDTO obtenerNumMaximoModelos(HttpServletRequest request) {
		LOGGER.info("obtenerNumMaximoModelos() -> Entrada al servicio para obtener el numero maximo de modelos seleccionados");
		
		ResponseDataDTO response = new ResponseDataDTO();
		Error error = new Error();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					GenPropertiesKey key = new GenPropertiesKey();
					key.setFichero(SigaConstants.FICHERO_SIGA);
					key.setParametro(SigaConstants.NUM_MAXIMO_MODELOS_SELECCIONADOS);
					
					GenProperties numMaximo = _genPropertiesMapper.selectByPrimaryKey(key);
					response.setData(numMaximo.getValor());
					
				}catch(Exception e){
					LOGGER.error("Error al obtener el número máximo de modelos", e);
					error.setCode(500);
					error.setDescription("Error al obtener el número máximo de modelos");
					error.setMessage(e.getMessage());
					response.setError(error);
				}					
			}
		}
		
		LOGGER.info("obtenerNumMaximoModelos() -> Salida del servicio para obtener el numero maximo de modelos seleccionados");
		return response;
	}


	@Override
	public Error generarEnvios(HttpServletRequest request, DialogoComunicacionItem dialogo) {
		LOGGER.info("generarEnvios() -> Entrada al servicio para generar los envíos");
		
		Error error = new Error();
		GenerarComunicacionItem generarComunicacion = new GenerarComunicacionItem();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				try{
					generarComunicacion = generarComunicacion(dialogo,usuario, true);					
					
					// Insertamos las consultas para los envios
					insertarConsultasEnvio(usuario, idInstitucion, generarComunicacion);
					
					error.setCode(200);
					error.setDescription("Envios generados");
				}catch(Exception e){
					LOGGER.error("Error al guardar los documentos",e);
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al guardar los documentos");
				}			
			}
		}
		
		LOGGER.info("generarEnvios() -> Salida del servicio  para generar los envíos");
		return error;
	}
	
	@Transactional
	private void insertarConsultasEnvio(AdmUsuarios usuario, Short idInstitucion, GenerarComunicacionItem generarComunicacion){
		// Hay que generar un envio por cada modelo y cada destinatario
		
		try {
			if(generarComunicacion != null && generarComunicacion.getListaModelosEnvio() != null && generarComunicacion.getListaModelosEnvio().size() > 0){
				// Por cada modelo y por cada destinatario se genera un envio
				for(ModelosEnvioItem modeloEnvio : generarComunicacion.getListaModelosEnvio()){
					if(modeloEnvio != null && modeloEnvio.getListaDatosEnvio() != null){
						for(DatosEnvioDTO listaConsultasEnvio : modeloEnvio.getListaDatosEnvio()){
							
							// Insertamos nuevo envio
							EnvEnvios envio = new EnvEnvios();
							envio.setIdinstitucion(idInstitucion);
							envio.setDescripcion("TMP");
							envio.setFecha(new Date());
							envio.setGenerardocumento("N");
							envio.setImprimiretiquetas("N");
							envio.setIdplantillaenvios(modeloEnvio.getIdPlantillaEnvio());
							
							Short estadoNuevo = 4;
							envio.setIdestado(estadoNuevo);
							envio.setIdtipoenvios(modeloEnvio.getIdTipoEnvio());
							envio.setFechamodificacion(new Date());
							envio.setUsumodificacion(usuario.getIdusuario());
							envio.setEnvio("A");
							envio.setFechaprogramada(generarComunicacion.getFechaProgramada());
							envio.setIdmodelocomunicacion(modeloEnvio.getIdModeloComunicacion());
							int insert = _envEnviosMapper.insert(envio);
							
							// Actualizamos el envio para ponerle la descripcion
							CenInstitucion institucion = _cenInstitucion.selectByPrimaryKey(idInstitucion);
							String descripcion = envio.getIdenvio() + "--" +institucion.getAbreviatura();
							envio.setDescripcion(descripcion);
							_envEnviosMapper.updateByPrimaryKey(envio);					
							
							if(insert >0){						
								
								EnvHistoricoestadoenvio historico = new EnvHistoricoestadoenvio();
								historico.setIdenvio(envio.getIdenvio());
								historico.setIdinstitucion(usuario.getIdinstitucion());
								historico.setFechamodificacion(new Date());
								historico.setFechaestado(new Date());
								historico.setUsumodificacion(usuario.getIdusuario());
								Short idEstado = 4;
								historico.setIdestado(idEstado);
								_envHistoricoestadoenvioMapper.insert(historico);
								
								//Insertamos el envio programado
								EnvEnvioprogramado envioProgramado = new EnvEnvioprogramado();
								envioProgramado.setIdenvio(envio.getIdenvio());
								envioProgramado.setIdinstitucion(idInstitucion);
								envioProgramado.setEstado("0");
								envioProgramado.setIdplantillaenvios(modeloEnvio.getIdPlantillaEnvio());
								envioProgramado.setIdtipoenvios(modeloEnvio.getIdTipoEnvio());
								envioProgramado.setNombre(descripcion);
								envioProgramado.setFechaprogramada(generarComunicacion.getFechaProgramada());
								envioProgramado.setFechamodificacion(new Date());
								envioProgramado.setUsumodificacion(usuario.getIdusuario());								
								_envEnvioprogramadoMapper.insert(envioProgramado);
								
								if (null != listaConsultasEnvio.getDestinatario().getIdPersona()) {
									//INSERTAMOS  DESTINATARIO
									EnvDestinatarios destinatario = new EnvDestinatarios();
									DestinatarioItem dest = listaConsultasEnvio.getDestinatario();
									destinatario.setIdinstitucion(idInstitucion);
									destinatario.setIdenvio(envio.getIdenvio());
									destinatario.setIdpersona(Long.valueOf(dest.getIdPersona()));
									destinatario.setNombre(dest.getNombre());
									destinatario.setApellidos1(dest.getApellidos1());
									destinatario.setApellidos2(dest.getApellidos2());
									destinatario.setCorreoelectronico(dest.getCorreoElectronico());
									destinatario.setNifcif(dest.getNIFCIF());
									destinatario.setMovil(dest.getMovil());
									destinatario.setFechamodificacion(new Date());
									destinatario.setUsumodificacion(usuario.getIdusuario());
									destinatario.setTipodestinatario(SigaConstants.TIPO_CEN_PERSONA);
									_envDestinatariosMapper.insert(destinatario);
								}
							}
							
							List<String> nombresFicheros = new ArrayList<String>();
							
							for(ConsultaEnvioItem consultaEnvio: listaConsultasEnvio.getConsultas()){
								// Insertamos las consultas del envio
								EnvConsultasenvio consultaEnvioEntity = new EnvConsultasenvio();
								consultaEnvioEntity.setConsulta(consultaEnvio.getConsulta());
								consultaEnvioEntity.setFechamodificacion(new Date());
								consultaEnvioEntity.setIdconsulta(consultaEnvio.getIdConsulta());
								consultaEnvioEntity.setIdenvio(envio.getIdenvio());
								consultaEnvioEntity.setIdinstitucion(consultaEnvio.getIdInstitucion());
								consultaEnvioEntity.setIdobjetivo(consultaEnvio.getIdObjetivo());
								consultaEnvioEntity.setUsumodificacion(consultaEnvio.getUsuModificacion());
								consultaEnvioEntity.setIdplantilladocumento(consultaEnvio.getIdPlantillaDoc());
								consultaEnvioEntity.setIdinforme(consultaEnvio.getIdInforme());
								consultaEnvioEntity.setIdmodelocomunicacion(consultaEnvio.getIdModeloComunicacion());
								consultaEnvioEntity.setSufijo(consultaEnvio.getSufijo());
								_envConsultasenvioMapper.insert(consultaEnvioEntity);
								
								
								//Insertamos los documentos asociados al envio
								
								// Si ya se ha insertado lo omitimos
								if(!nombresFicheros.contains(consultaEnvio.getNombreFichero())) {
									EnvDocumentos documento = new EnvDocumentos();
									documento.setIdenvio(envio.getIdenvio());
									documento.setIdinstitucion(usuario.getIdinstitucion());
									documento.setFechamodificacion(new Date());
									documento.setUsumodificacion(usuario.getIdusuario());
									documento.setDescripcion(consultaEnvio.getNombreFichero());
									documento.setPathdocumento(consultaEnvio.getPathFichero());
									_envDocumentosMapper.insert(documento);
									nombresFicheros.add(consultaEnvio.getNombreFichero());
								}							
							}
						}
					}							
				}						
			}	
		}catch(Exception e) {
			LOGGER.error("Error al generar el envío", e);
			throw e;
		}
	}	
	
	@Override
	public List<DatosDocumentoItem> generarDocumentosEnvio(String idInstitucion, String idEnvio) throws Exception{	
		List<String> listaIdPlantilla = _envConsultasEnvioExtendsMapper.selectPlantillasByEnvio(idInstitucion, idEnvio);
		List<DatosDocumentoItem> listaFicheros = new ArrayList<DatosDocumentoItem>();
		Long idModeloComunicacion = null;
		String directorioPlantillaClase = "";
		boolean esExcel = false;
		List<List<Map<String,Object>>> listaDatosExcel = new ArrayList<List<Map<String,Object>>>();
		
		//Obtenemos la clase de comunicacion del modelo (si tiene)	
		EnvEnviosExample envioExample = new EnvEnviosExample();
		envioExample.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		
		List<EnvEnvios> envios = _envEnviosMapper.selectByExample(envioExample);
		
		if(envios!=null && envios.size()>0) {
			EnvEnvios envio = envios.get(0);
			Long idModeloComunicacionEnvio = envio.getIdmodelocomunicacion();
			if(idModeloComunicacionEnvio != null) {
				List<ClaseComunicacionItem> modClaseItem = _modClasecomunicacionesExtendsMapper.selectClaseComunicacionModulo(String.valueOf(idModeloComunicacionEnvio));
				if(modClaseItem != null && modClaseItem.size() > 0) {
					ClaseComunicacionItem claseItem = modClaseItem.get(0);
					directorioPlantillaClase = claseItem.getRutaPlantilla();					
				}
			}
		}
		
		if(directorioPlantillaClase == null || "".equals(directorioPlantillaClase)) {
			directorioPlantillaClase = SigaConstants.rutaPlantillaSinClase;
		}
		
		directorioPlantillaClase = directorioPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, idInstitucion);
		
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
						List<Map<String, Object>> result = null;
						String sentencia = consulta.getConsulta();
						
						sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
						
						if(sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER) || sentencia.contains(SigaConstants.SENTENCIA_CREATE)
								|| sentencia.contains(SigaConstants.SENTENCIA_DELETE) || sentencia.contains(SigaConstants.SENTENCIA_DROP)
								|| sentencia.contains(SigaConstants.SENTENCIA_INSERT) || sentencia.contains(SigaConstants.SENTENCIA_UPDATE))){
							
							LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
						}else {
							result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
						}
						
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
						
						String sentencia = consultaDest.getConsulta();
						
						sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
						
						if(sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER) || sentencia.contains(SigaConstants.SENTENCIA_CREATE)
								|| sentencia.contains(SigaConstants.SENTENCIA_DELETE) || sentencia.contains(SigaConstants.SENTENCIA_DROP)
								|| sentencia.contains(SigaConstants.SENTENCIA_INSERT) || sentencia.contains(SigaConstants.SENTENCIA_UPDATE))){
							
							LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
						}else {
							List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
							if(result != null && result.size() > 0){
								for(Map<String,Object> dest : result){
									hDatosGenerales.putAll(dest);
								}							
							}
						}
						
					}
					
					LOGGER.debug("Obtenemos la consulta de multidocumento para el envio " + idEnvio + " y plantilla: " + idPlantilla);
					example = new EnvConsultasenvioExample();
					example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
					List<EnvConsultasenvio> listaConsultasMulti = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
					if(listaConsultasMulti != null && listaConsultasMulti.size() > 0){
						for(EnvConsultasenvio consultaMulti: listaConsultasMulti){
							List<Map<String,Object>> resultMulti = null;
							
							// Ejecutamos la consulta
							
							if(campoSufijo == null || !"".equalsIgnoreCase(campoSufijo)){
								campoSufijo = consultaMulti.getSufijo();
							}
							
							String sentencia = consultaMulti.getConsulta();
							
							sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
							
							if(sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER) || sentencia.contains(SigaConstants.SENTENCIA_CREATE)
									|| sentencia.contains(SigaConstants.SENTENCIA_DELETE) || sentencia.contains(SigaConstants.SENTENCIA_DROP)
									|| sentencia.contains(SigaConstants.SENTENCIA_INSERT) || sentencia.contains(SigaConstants.SENTENCIA_UPDATE))){
								
								LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
							}else {
								resultMulti = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
							}
														
							int numFicheros = 0;
							if(resultMulti != null && resultMulti.size() > 0){
								for(int k = 0;k<resultMulti.size();k++){
									
									hDatosGenerales.putAll(resultMulti.get(k));
									
									// Por cada registro generamos un documento
									numFicheros++;
									
									List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectPlantillaGenerar(consultaMulti.getIdmodelocomunicacion(), consultaMulti.getIdplantilladocumento());
									PlantillaModeloDocumentoDTO plantilla = plantillas.get(0);
									
									if(plantilla.getFormatoSalida() != null && Short.parseShort(plantilla.getFormatoSalida()) == SigaConstants.FORMATO_SALIDA.XLS.getCodigo()) {
										esExcel = true;
									}
									
									//Otenemos el nombre del fichero de salida
									GenPropertiesKey key = new GenPropertiesKey();
									key.setFichero(SigaConstants.FICHERO_SIGA);
									key.setParametro(SigaConstants.parametroRutaSalidaInformes);
									
									GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
									
									String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator;
									
									key = new GenPropertiesKey();
									key.setFichero(SigaConstants.FICHERO_SIGA);
									key.setParametro(SigaConstants.parametroRutaPlantillas);
									
									GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
									
									String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + directorioPlantillaClase + SigaConstants.pathSeparator;
									
									
									String pathFicheroSalida = rutaTmp;
									String pathPlantilla = rutaPlantilla;
									
									//Si no existe el directorio temporal lo creamos
									File dir = new File(pathFicheroSalida);
									if(!dir.exists()){
										dir.mkdirs();
									}	
									
									
									File filePlantilla = new File(pathPlantilla + nombrePlantilla);
									if(!filePlantilla.exists()){
										throw new SigaExceptions("No existe la plantilla de documento");
									}								

									
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
											if(resultDatos != null && resultDatos.size() > 0) {
												hDatosGenerales.putAll(resultDatos.get(0));
											}
										}
										
										if(esExcel) {
											listaDatosExcel.add(resultDatos);
										}
									}
									
									hDatosFinal.put("row", hDatosGenerales);								

									
									String nombreFicheroSalida = obtenerNombreFicheroSalida(String.valueOf(consultaMulti.getIdmodelocomunicacion()), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, numFicheros, pathFicheroSalida, campoSufijo);
																
									DatosDocumentoItem docGenerado = null;
									
									
									if(esExcel) {
										 docGenerado = _generacionDocService.generarExcel(pathPlantilla + nombrePlantilla, pathFicheroSalida, nombreFicheroSalida, listaDatosExcel);
									}else {
										Document doc = new Document(pathPlantilla + nombrePlantilla);
										
										doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
										
										boolean firmado = false;
										
										if(plantilla.getFormatoSalida() != null){
											FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
											if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
												firmado = true;
											}
										}
										
										docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
									}
									
									docGenerado.setPathDocumento(pathFicheroSalida + nombreFicheroSalida);
									
									listaFicheros.add(docGenerado);
																															
								}
							}
						}
					}else{
						LOGGER.debug("No hay consulta multidocumento para el envio " + idEnvio + " y plantilla: " + idPlantilla);
						
						List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectPlantillaGenerar(idModeloComunicacion, Long.parseLong(idPlantilla));
						PlantillaModeloDocumentoDTO plantilla = plantillas.get(0);
						
						if(plantilla.getFormatoSalida() != null && Short.parseShort(plantilla.getFormatoSalida()) == SigaConstants.FORMATO_SALIDA.XLS.getCodigo()) {
							esExcel = true;
						}
						
						GenPropertiesKey key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaSalidaInformes);
						
						GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
						
						String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator;
						
						key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaPlantillas);
						
						GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
						
						String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + directorioPlantillaClase + SigaConstants.pathSeparator;
						
						
						String pathFicheroSalida = rutaTmp;
						String pathPlantilla = rutaPlantilla;
						
						//Si no existe el directorio temporal lo creamos
						File dir = new File(pathFicheroSalida);
						if(!dir.exists()){
							dir.mkdirs();
						}	
						
						
						File filePlantilla = new File(pathPlantilla + nombrePlantilla);
						if(!filePlantilla.exists()){
							throw new SigaExceptions("No existe la plantilla de documento");
						}

						
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
								if(resultDatos != null && resultDatos.size() > 0) {
									hDatosGenerales.putAll(resultDatos.get(0));
								}
							}
							
							if(esExcel) {
								listaDatosExcel.add(resultDatos);
							}
						}
						
						hDatosFinal.put("row", hDatosGenerales);									
						
						String nombreFicheroSalida = obtenerNombreFicheroSalida(String.valueOf(idModeloComunicacion), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, 0, pathFicheroSalida, campoSufijo);
						
						DatosDocumentoItem docGenerado = null;
						
						if(esExcel) {
							 docGenerado = _generacionDocService.generarExcel(pathPlantilla + nombrePlantilla, pathFicheroSalida, nombreFicheroSalida, listaDatosExcel);
						}else {
							Document doc = new Document(pathPlantilla + nombrePlantilla);
							
							doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
							
							boolean firmado = false;
							
							if(plantilla.getFormatoSalida() != null){
								FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
								if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
									firmado = true;
								}
							}
							
							docGenerado = _generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida, firmado);
						}
						
						
						
						
						docGenerado.setPathDocumento(pathFicheroSalida + nombreFicheroSalida);
						
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
