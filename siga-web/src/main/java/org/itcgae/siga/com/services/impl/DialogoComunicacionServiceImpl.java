package org.itcgae.siga.com.services.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.model.types.HRESIAbstractType;
import org.itcgae.siga.DTOs.com.ClaseComunicacionItem;
import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.KeyItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.com.PlantillaModeloDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.SufijoItem;
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
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModClasecomunicacionRutaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModKeyclasecomunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModRelPlantillaSufijoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspose.words.DataSet;
import com.aspose.words.Document;
import com.aspose.words.MailMergeCleanupOptions;

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
	private ModClasecomunicacionRutaExtendsMapper _modClasecomunicacionRutaExtendsMapper;
	
	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;
	
	@Autowired
	private ModModeloPlantillaDocumentoExtendsMapper _modModeloPlantillaDocumentoExtendsMapper;
	
	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper _modPlantillaDocumentoConsultaExtendsMapper;
	
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
	public ModelosComunicacionSearch obtenerModelos(HttpServletRequest request, String idClaseComunicacion) {
		
		LOGGER.info("obtenerModelos() -> Entrada al servicio para obtener los modelos de comunicacion");
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		ModelosComunicacionSearch respuesta = new ModelosComunicacionSearch();
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (usuarios != null && usuarios.size() > 0) {
				try{
					List<ModelosComunicacionItem> modelos = _modModeloComunicacionExtendsMapper.selectModelosComunicacionDialogo(idClaseComunicacion);
					
					for (ModelosComunicacionItem modelosComunicacionItem : modelos) {
						ComboDTO comboDTO = new ComboDTO();
						List<ComboItem> comboItems = _modModeloComunicacionExtendsMapper.selectPlantillasModelos(modelosComunicacionItem.getIdModeloComunicacion());
						if(null != comboItems && comboItems.size() > 0) {
							ComboItem element = new ComboItem();
							element.setLabel("");
							element.setValue("");
							comboItems.add(0, element);
						}		
						comboDTO.setCombooItems(comboItems);
						modelosComunicacionItem.setPlantillas(comboDTO);
					}
					respuesta.setModelosComunicacionItems(modelos);
				}catch(Exception e){
					Error error = new Error();
					error.setCode(500);
					error.setDescription("Error al obtener los modelos");
					error.setMessage(e.getMessage());
				}
			}
		}
		
		LOGGER.info("obtenerModelos() -> Salida del servicio para obtener los modelos de comunicacion");
		
		return respuesta;
	}



	@Override
	public ComboDTO obtenertipoEnvioPlantilla(HttpServletRequest request, String idPlantilla) {
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Entrada al servicio para obtener tipos de envio de la plantilla ");
		
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
				AdmUsuarios usuario = usuarios.get(0);
				comboItems = _modModeloComunicacionExtendsMapper.selectTipoEnvioPlantilla(usuario.getIdlenguaje(), idPlantilla);
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("obtenertipoEnvioPlantilla() -> Salida del servicio para obtener tipos de envio de la plantilla ");
		
		return comboDTO;
	}



	@Override
	public Error descargarComunicacion(HttpServletRequest request, DialogoComunicacionItem dialogo) {
		LOGGER.info("descargarComunicacion() -> Entrada al servicio para descargar la documentación de la comunicación");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		List<Document> listaDocumentos = null;
		Error error = new Error();
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				try{
					listaDocumentos = generarDocumentos(dialogo,usuario);
					error.setCode(200);
					error.setDescription("Ficheros generados correctamente");
					error.setMessage("Ficheros generados correctamente");
				}catch(Exception e){
					LOGGER.error("Error al guardar los documentos",e);
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al guardar los documentos");
				}			
			}
		}
		
		LOGGER.info("descargarComunicacion() -> Salida del servicio para descargar la documentación de la comunicación");
		return error;
	}
	
	public List<Document> generarDocumentos(DialogoComunicacionItem dialogo, AdmUsuarios usuario) throws Exception{
		LOGGER.info("generarDocumentos() -> Entrada al servicio para generar los documentos a comunicar");
		boolean continua = true;
		List<Document> listaDocumentos = new ArrayList<Document>();
		HashMap<String,Object> hDatosGenerales = new HashMap<String, Object>();
		HashMap<String,Object> hDatosFinal = new HashMap<String, Object>();	
		int numFicheros = 0;

		try{
			if(dialogo != null && dialogo.getModelos() != null && dialogo.getModelos().size() > 0){
				for(ModelosComunicacionItem modelo :dialogo.getModelos()){
					
					LOGGER.debug("Obtenemos las key de la clase de comunicación");
					String idClaseComunicacion = dialogo.getIdClaseComunicacion();
					
					List<KeyItem> listaKey = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Long.parseLong(idClaseComunicacion));
					
					LOGGER.debug("Obtenemos las plantillas de documento asociadas al modelo");
					List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectInformesGenerar(Long.parseLong(modelo.getIdModeloComunicacion()));
					
					// Por cada conjunto de valores key se generan los documentos
					
					if(dialogo.getSelectedDatos() != null && dialogo.getSelectedDatos().size() > 0){
						List<List<String>> listaKeyFiltros = dialogo.getSelectedDatos();
						for(int i=0; i< listaKeyFiltros.size(); i ++){	
							
							String claves = null;
							List<String> listaStringKey = listaKeyFiltros.get(i);
							if(listaStringKey != null && listaStringKey.size() > 0){
								claves = "(";
								for(int j = 0; j<listaStringKey.size(); j++){
									String keyFiltro = listaStringKey.get(j);
									claves = claves + keyFiltro;
									if(j!=listaStringKey.size()-1){
										claves = claves + ", ";
									}
								}
								claves = claves + ")";
							}
							
							for(PlantillaModeloDocumentoDTO plantilla:plantillas){	
								
								LOGGER.debug("Obtenemos la consultas condicional: " + plantilla.getIdPlantillas());
								List<ConsultaItem> consultasItemCondicional = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

								if(consultasItemCondicional != null && consultasItemCondicional.size() > 0){
									for(ConsultaItem consulta:consultasItemCondicional){
										String sentencia = consulta.getSentencia();									
										List<Map<String,Object>> result = ejecutarConsultaConClaves(sentencia, claves);
										
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
											String sentencia = consulta.getSentencia();		
											
											List<Map<String,Object>> result = ejecutarConsultaConClaves(sentencia, claves);
											
											if(result != null && result.size() > 0){
												LOGGER.debug("Se han obtenido " + result.size() + " destinatarios");
												
												for(Map<String,Object> dest: result){
													LOGGER.debug("Guardamos el resultado de la query para cada destinatario");
													numFicheros = 0;
													
													hDatosGenerales = new HashMap<String, Object>();
													hDatosGenerales.putAll(dest);
													
													hDatosFinal = new HashMap<String, Object>();
													
													
													LOGGER.debug("Obtenemos la consulta de multidocumento para la plantilla: " + plantilla.getIdInforme());
													List<ConsultaItem> consultasItemMulti = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
													if(consultasItemMulti != null && consultasItemMulti.size() > 0){
														
														for(ConsultaItem consultaMulti:consultasItemMulti){
															sentencia = consultaMulti.getSentencia();		
															
															List<Map<String,Object>> resultMulti = ejecutarConsultaConClaves(sentencia, claves);
															if(resultMulti != null && resultMulti.size() > 0){
																for(int k = 0;k<resultMulti.size();k++){
																	
																	// Por cada registro generamos un documento
																	numFicheros++;
																	
																	//Otenemos el nombre del fichero de salida
																	String nombrePlantilla = "";
																	String pathFicheroSalida = SigaConstants.rutaficherosInformesYcomunicaciones + dialogo.getIdInstitucion() + SigaConstants.carpetaPlantillasDocumento;
																	String pathPlantilla = SigaConstants.rutaficherosInformesYcomunicaciones + dialogo.getIdInstitucion() + SigaConstants.carpetaPlantillasDocumento;
																	String nombreFicheroSalida = obtenerNombreFicheroSalida(modelo, plantilla, hDatosGenerales, usuario, numFicheros);
																	
																	
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
																		example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(usuario.getIdlenguaje());
																		List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
																		
																		if(listaPlantilla != null && listaPlantilla.size() == 1){
																			ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
																			nombrePlantilla = plantillaDoc.getPlantilla();
																		}else if(listaPlantilla != null && listaPlantilla.size() > 1){
																			LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
																			throw new SigaExceptions("Exiten multiples plantillas asociada al informe en el idioma del usuario");
																		}else{
																			LOGGER.error("No hay plantilla asociada para el informe en el idioma del usuario");
																			throw new SigaExceptions("No hay plantilla asociada para el informe en el idioma del usuario");
																		}
																	}
																	
																	File filePlantilla = new File(pathPlantilla + nombrePlantilla);
																	if(!filePlantilla.exists()){
																		throw new SigaExceptions("No existe la plantilla de documento");
																	}
																	
																	Document doc = new Document(pathPlantilla + nombrePlantilla);

																	hDatosGenerales.putAll(resultMulti.get(k));
																	
																	// Por cada resultado ejecutamos las consultas de datos
																	LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
																	List<ConsultaItem> consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
																	
																	for(ConsultaItem consultaDatos:consultasItemDatos){
																		sentencia = consultaDatos.getSentencia();		
																		
																		List<Map<String,Object>> resultDatos = ejecutarConsultaConClaves(sentencia, claves);
																		
																		if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
																			hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
																		}else{
																			hDatosGenerales.putAll(resultDatos.get(0));
																		}
																	}
																	
																	hDatosFinal.put("row", hDatosGenerales);
																	LOGGER.debug("Generamos el documento");																
																	
																	doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);
																	
																	_generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida);
																	
																	listaDocumentos.add(doc);
																
																}														
															}
															
														}
													}else{
														LOGGER.debug("No hay consulta de multidocumento");
														
														//Otenemos el nombre del fichero de salida
														String nombrePlantilla = "";
														String pathFicheroSalida = SigaConstants.rutaficherosInformesYcomunicaciones + dialogo.getIdInstitucion() + SigaConstants.carpetaPlantillasDocumento;
														String pathPlantilla = SigaConstants.rutaficherosInformesYcomunicaciones + dialogo.getIdInstitucion() + SigaConstants.carpetaPlantillasDocumento;
														String nombreFicheroSalida = obtenerNombreFicheroSalida(modelo, plantilla, hDatosGenerales, usuario, numFicheros);
														
														
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
															example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(usuario.getIdlenguaje());
															List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
															
															if(listaPlantilla != null && listaPlantilla.size() == 1){
																ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
																nombrePlantilla = plantillaDoc.getPlantilla();
															}else if(listaPlantilla != null && listaPlantilla.size() > 1){
																LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
																throw new SigaExceptions("Exiten multiples plantillas asociada al informe en el idioma del usuario");
															}else{
																LOGGER.error("No hay plantilla asociada para el informe en el idioma del usuario");
																throw new SigaExceptions("No hay plantilla asociada para el informe en el idioma del usuario");
															}
														}
														
														File filePlantilla = new File(pathPlantilla + nombrePlantilla);
														if(!filePlantilla.exists()){
															throw new SigaExceptions("No existe la plantilla de documento");
														}
														
														Document doc = new Document(pathPlantilla + nombrePlantilla);
														
														// Por cada resultado ejecutamos las consultas de datos
														LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
														List<ConsultaItem> consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(usuario.getIdinstitucion(), Long.parseLong(modelo.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
														
														for(ConsultaItem consultaDatos:consultasItemDatos){
															sentencia = consultaDatos.getSentencia();		
															
															List<Map<String,Object>> resultDatos = ejecutarConsultaConClaves(sentencia, claves);
															
															if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
																hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
															}else{
																hDatosGenerales.putAll(resultDatos.get(0));
															}
														}
														
														hDatosFinal.put("row", hDatosGenerales);
														
														LOGGER.debug("Generamos el documento");
														doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);
														
														_generacionDocService.grabaDocumento(doc, pathFicheroSalida, nombreFicheroSalida);
														
														listaDocumentos.add(doc);
													}
													
												}	
											}else{
												LOGGER.info("La consulta de destinatarios no ha devuelto resultados");
											}
										}
									}else{
										LOGGER.error("No hay consulta de destinatario para el informe: " + plantilla.getIdInforme());
									}
								}else{
									LOGGER.debug("No se ejecuta la generación del informe: " + plantilla.getIdInforme());
								}
								
							}
						}
					}				
				}
			}else{
				LOGGER.error("No hay modelos seleccionados");
			}
		}catch(Exception e){
			LOGGER.error("Error al generar la documentación",e);
			throw e;
		}
		
		LOGGER.info("generarDocumentos() -> Entrada al servicio para generar los documentos a comunicar");
		
		return listaDocumentos;
	}
	
	private List<Map<String, Object>> ejecutarConsultaConClaves(String sentencia, String claves){
		Map<String, String> mapConsulta = _consultasService.obtenerMapaConsulta(sentencia);		
		// Reemplazamos el where
		String where = mapConsulta.get(SigaConstants.WHERE_VALUE);
		
		// Reemplazamos los datos insertados desde pantalla		
		
		// Remplazamos el where de la query con las keys
		where = where.replace(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CLAVES_QUERY + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, claves);

		mapConsulta.put(SigaConstants.WHERE_VALUE, where);
		List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(mapConsulta);
		
		return result;
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
				listaKeys = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Long.parseLong(idClaseComunicacion));
		
				keysDTO.setKeysItem(listaKeys);
				
			}
		}
		
		LOGGER.info("obtenerKeysClaseComunicacion() -> Salida del servicio para obtener las keys asociadas a una clase de comunicación");
		
		return keysDTO;
	}
	
	private String obtenerNombreFicheroSalida(ModelosComunicacionItem modelo, PlantillaModeloDocumentoDTO plantilla, HashMap<String,Object> hDatosGenerales, AdmUsuarios usuario, int numFichero){
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
		List<SufijoItem> sufijos = _modRelPlantillaSufijoExtendsMapper.selectSufijosPlantilla(Long.parseLong(modelo.getIdModeloComunicacion()), Long.parseLong(plantilla.getIdInforme()), usuario.getIdlenguaje());
		
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
				Object identificacion = hDatosGenerales.get(SigaConstants.CAMPO_IDENTIFICACION);
				if(identificacion == null){
					if(hashMapRow != null && hashMapRow.get(SigaConstants.CAMPO_IDENTIFICACION) != null){
						identificacion = hashMapRow.get(SigaConstants.CAMPO_IDENTIFICACION);
					}else{
						identificacion = SigaConstants.CAMPO_IDENTIFICACION;
					}
				}
				if(!sufijo.equalsIgnoreCase(""))sufijo = sufijo + "_";
				sufijo = sufijo + String.valueOf(identificacion);
			}
		}
		
		String numero = "";
		if(numFichero > 0){
			numero = "_" + String.valueOf(numFichero) + "_";
		}
		
		nombreFicheroSalida = nombreFicheroSalida + sufijo + numero + "." + extension;
		
		return nombreFicheroSalida;
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
					LOGGER.error("Error al obtener la clase de comunicaciones");
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
	
}