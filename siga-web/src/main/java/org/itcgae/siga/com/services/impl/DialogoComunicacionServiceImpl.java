package org.itcgae.siga.com.services.impl;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
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
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosExample;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.ConEjecucionMapper;
import org.itcgae.siga.db.mappers.EnvCamposenviosMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantillaenvioMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
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
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.BusinessSQLException;
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
	private ModModelocomunicacionMapper _modModeloComunicacionMapper;
	
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
	private EnvCamposenviosMapper _envCamposenviosMapper;

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
	
	@Autowired
	private ConEjecucionMapper _conEjecucionMapper;
	
	
	static int numeroFicheros = 1; 
	
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
					List<ModelosComunicacionItem> modelos = _modModeloComunicacionExtendsMapper.selectModelosComunicacionDialogo(String.valueOf(idInstitucion), modeloDTO.getIdInstitucion(), modeloDTO.getIdClaseComunicacion(), modeloDTO.getIdModulo(), usuario.getIdlenguaje(), modeloDTO.getIdConsulta(), perfiles);
					
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
					LOGGER.error(e);
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
	public File obtenerNombre(HttpServletRequest request, DialogoComunicacionItem dialogo, HttpServletResponse resp) {
		LOGGER.info("descargarComunicacion() -> Entrada al servicio para descargar la documentación de la comunicación");
		
		File file = null;

		try {
			
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
					LOGGER.info("Rendimiento Inicio método generar comunicacion");
					AdmUsuarios usuario = usuarios.get(0);
					generarComunicacion = generarComunicacion(request, dialogo,usuario, false);
					listaFicheros = generarComunicacion.getListaDocumentos();
					LOGGER.info("Rendimiento Inicio obtener fichero o zip descarga");
					file = getFicheroDescarga(dialogo.getIdInstitucion(), listaFicheros);
					LOGGER.info("Rendimiento fin obtener fichero o zip descarga");
					LOGGER.info("Rendimiento Fin método generar comunicacion");
				}
			}
		} catch (BusinessException e) {
			LOGGER.error(e);
			throw e;
		} catch (Exception e) {
			LOGGER.error(e);
			throw new BusinessException("Error interno de la aplicación", e);
		}
		LOGGER.info("descargarComunicacion() -> Salida al servicio para descargar la documentación de la comunicación");
		return file;
		
	}


	private File getFicheroDescarga(String idInstitucion, List<DatosDocumentoItem> listaFicheros) throws IOException {
		File file = null;
		
		if(listaFicheros != null && listaFicheros.size() > 0){
			if(listaFicheros.size() == 1) {
				
				String path = listaFicheros.get(0).getPathDocumento() + listaFicheros.get(0).getFileName();
				file = WSCommons.fileBytes(listaFicheros, path);
			
			}else {
				file = new File(getRutaFicheroSalida(idInstitucion));
				file.mkdirs();
				file = new File(file, "Documentos.zip");
				file = WSCommons.zipBytes(listaFicheros, file);
			}
		}

		return file;
	}



	
	
	private GenerarComunicacionItem generarComunicacion(HttpServletRequest request, DialogoComunicacionItem dialogo, AdmUsuarios usuario, boolean esEnvio) throws Exception{
		LOGGER.info("generarComunicacion() -> Entrada al servicio para generar los documentos a comunicar");
		
		GenerarComunicacionItem generarComunicacion = new GenerarComunicacionItem();
		numeroFicheros = 1;
		List<DatosDocumentoItem> listaFicheros = new ArrayList<DatosDocumentoItem>();
		List<DatosEnvioDTO> listaConsultasYDestinatario = new ArrayList<DatosEnvioDTO>();
		List<ModelosEnvioItem> listaModelosEnvio = new ArrayList<ModelosEnvioItem>();
		DestinatarioItem destinatario = null;
		int ficherogenerado = 0;
		String rutaPlantillaClase = "";
		ModClasecomunicaciones modClasecomunicacion = null;

		try{
			
			if (dialogo == null || dialogo.getModelos() == null || dialogo.getModelos().size() < 1) {
				String mensaje = "No se ha seleccionada ningún modelo"; 
				LOGGER.warn(mensaje);
				throw new BusinessException(mensaje);
			}
			
			LOGGER.debug("El número de modelos seleccionado es " + dialogo.getModelos().size());
			
			//Obtenemos el campo del Sufijo asociado a la clase de la comunicación			
			if(dialogo.getIdClaseComunicacion() != null) {
				modClasecomunicacion = _modClasecomunicacionesMapper.selectByPrimaryKey(Short.parseShort(dialogo.getIdClaseComunicacion()));
			} else {
				String mensaje = "No se ha obtenido la clase de comunicación"; 
				LOGGER.warn(mensaje);
				throw new BusinessException(mensaje);
			}
			
			String campoSufijo = "";
			
			if(modClasecomunicacion != null){
				campoSufijo = modClasecomunicacion.getSufijo();
				LOGGER.debug("Campo campoSufijo = " + campoSufijo);
				rutaPlantillaClase = modClasecomunicacion.getRutaplantilla();
				LOGGER.debug("Campo rutaPlantillaClase = " + rutaPlantillaClase);
			}
			
			if(rutaPlantillaClase == null || "".equals(rutaPlantillaClase)) {
				rutaPlantillaClase = SigaConstants.rutaPlantillaSinClase;
				LOGGER.debug("Campo rutaPlantillaClase = " + rutaPlantillaClase);
			}else {
				rutaPlantillaClase = rutaPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, String.valueOf(usuario.getIdinstitucion()));
				LOGGER.debug("Campo rutaPlantillaClase = " + rutaPlantillaClase);
			}
			
			generarComunicacion.setFechaProgramada(dialogo.getFechaProgramada());
			int r = 1;
			for(ModelosComunicacionItem modelosComunicacionItem :dialogo.getModelos()){
				LOGGER.info("Rendimiento inicio tratamiento modelo de comunicacion: " + r);
				ModelosEnvioItem modeloEnvioItem = new ModelosEnvioItem();		
				
				LOGGER.debug("Generación de documentación del modelo " + modelosComunicacionItem.getIdModeloComunicacion());
				
				LOGGER.debug("Obtenemos las key de la clase de comunicación");
				String idClaseComunicacion = dialogo.getIdClaseComunicacion();
				
				List<KeyItem> listaKey = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Short.parseShort(idClaseComunicacion));
				
				// Comprobamos que se ha especificado una plantilla de envío
				if (modelosComunicacionItem.getIdPlantillaEnvio() != null) {
					EnvPlantillasenviosKey keyPlantilla = new EnvPlantillasenviosKey();
					keyPlantilla.setIdplantillaenvios(Integer.parseInt(modelosComunicacionItem.getIdPlantillaEnvio()));
					keyPlantilla.setIdtipoenvios(Short.parseShort(modelosComunicacionItem.getIdTipoEnvio()));
					keyPlantilla.setIdinstitucion(Short.valueOf(dialogo.getIdInstitucion()));
					EnvPlantillasenviosWithBLOBs plantilla = _envPlantillaEnviosExtendsMapper.selectByPrimaryKey(keyPlantilla);
					
					// Comprobamos si la plantilla especificada contiene remitente	
					if(plantilla.getIdpersona() == null) {
						String mensaje = "La plantilla especificada no tiene remitente"; 
						LOGGER.warn(mensaje);
						throw new BusinessException(mensaje);
					}
				}
				
				// Obtenemos la plantilla de envio seleccionada en el modelo
				List<ConsultaItem> listaConsultasPlantillaEnvio = null;
				if(modelosComunicacionItem.getIdPlantillaEnvio() != null && modelosComunicacionItem.getIdTipoEnvio()!=null){
					listaConsultasPlantillaEnvio = _modPlantillaEnvioConsultaExtendsMapper.selectPlantillaEnvioConsultas(Short.valueOf(dialogo.getIdInstitucion()), Integer.parseInt(modelosComunicacionItem.getIdPlantillaEnvio()), Short.parseShort(modelosComunicacionItem.getIdTipoEnvio()));
				}
				
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
							if (dialogo.isComunicar() && modelosComunicacionItem.getIdTipoEnvio() != null) {
								mapaClave.put(SigaConstants.ETIQUETATIPOENVIO.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, ""), modelosComunicacionItem.getIdTipoEnvio());
							}
						}
						
						

						List<ConsultaEnvioItem> listaConsultasEnvio = new ArrayList<ConsultaEnvioItem>();
						destinatario = new DestinatarioItem();	

						int ficherogeneradoOK = ejecutaPlantillas(request ,modelosComunicacionItem, dialogo, usuario, mapaClave, esEnvio, listaConsultasEnvio, listaConsultasPlantillaEnvio, rutaPlantillaClase, campoSufijo, listaFicheros, ejecutarConsulta, destinatario,listaKeyFiltros.size(),ficherogenerado,i);
											
						if (ficherogeneradoOK > 0) {
							ficherogenerado++;
							
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
				if(modelosComunicacionItem.getIdPlantillaEnvio() != null){
					modeloEnvioItem.setIdPlantillaEnvio(Integer.parseInt(modelosComunicacionItem.getIdPlantillaEnvio()));
				}
				if(modelosComunicacionItem.getIdTipoEnvio() != null){
					modeloEnvioItem.setIdTipoEnvio(Short.parseShort(modelosComunicacionItem.getIdTipoEnvio()));
				}	
				modeloEnvioItem.setIdModeloComunicacion(Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()));
				
				listaModelosEnvio.add(modeloEnvioItem);
				LOGGER.info("Rendimiento fin tratamiento modelo de comunicacion: " + r);
				r++;
				
			}	
			
			generarComunicacion.setListaModelosEnvio(listaModelosEnvio);
			if(listaFicheros != null && listaFicheros.size() > 0 && listaFicheros.get(0) != null) {
				generarComunicacion.setListaDocumentos(listaFicheros);
			}
			
		} catch (BusinessException e) {
			LOGGER.warn(e);
			throw e;
		} catch(Exception e){
			LOGGER.error("Error al generar la documentación",e);
			throw e;
		}
		
		LOGGER.info("generarComunicacion() -> Entrada al servicio para generar los documentos a comunicar");
		
		return generarComunicacion;
	}
	
	private int ejecutaPlantillas(HttpServletRequest request, ModelosComunicacionItem modelosComunicacionItem, DialogoComunicacionItem dialogo,
			AdmUsuarios usuario, HashMap<String, String> mapaClave, boolean esEnvio, List<ConsultaEnvioItem> listaConsultasEnvio, List<ConsultaItem> listaConsultasPlantillaEnvio, 
			String rutaPlantillaClase, String campoSufijo, List<DatosDocumentoItem> listaFicheros, boolean ejecutarConsulta, DestinatarioItem destinatario, int numeroSeleccionados, int ficherogenerado, int numeroSeleccionado) throws Exception {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		Boolean existenConsultas = Boolean.FALSE;
		
		ModelosComunicacionSearch respuesta = new ModelosComunicacionSearch();
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
		boolean continua = true;
		boolean esDestinatario = false;
		Long idConsultaEjecutarCondicional = null;
		String consultaEjecutarCondicional = "";
		int numFicheros = 0;
		List<PlantillaModeloDocumentoDTO> plantillas = new ArrayList<PlantillaModeloDocumentoDTO>();
		HashMap<String,Object> hDatosGenerales = new HashMap<String, Object>();		
		HashMap<String,Object> hDatosFinal = new HashMap<String, Object>();	
		
		
		
		List<ConsultaItem> consultasItemDest = _modPlantillaDocumentoConsultaExtendsMapper
				.selectConsultasDestinatario(usuario.getIdinstitucion(),
						Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
						 SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
		
		
		if (consultasItemDest != null && consultasItemDest.size() > 0 && !(null != modelosComunicacionItem.getIdClaseComunicacion() &&  modelosComunicacionItem.getIdClaseComunicacion().equals("5"))) {

			LOGGER.debug("Número de consultas de destintarios " + consultasItemDest.size());
			for (ConsultaItem consulta : consultasItemDest) {
				String consultaEjecutarDestinatarios = reemplazarConsultaConClaves(usuario, dialogo, consulta,
						mapaClave, esEnvio);

				List<Map<String, Object>> result;
				try {

					result = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDestinatarios, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()), Long.valueOf(consulta.getIdConsulta()),Short.valueOf(consulta.getIdInstitucion()),consulta.getDescripcion());	
					
					LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se ejecuta la consulta de destinatarios: " + consultaEjecutarDestinatarios);
					LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se envía a la dirección: " + result);

				} catch (BusinessSQLException e) {
					LOGGER.error(e);
					throw new BusinessException(
							"Error al ejecutar la consulta " + consulta.getDescripcion() + " " + e.getMessage(),
							e);
				} catch (Exception e) {
					LOGGER.warn("Error al ejejcutar la consulta" + e);
					throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(),
							e);
				}

				if (result != null && result.size() > 0) {
					LOGGER.debug("Se han obtenido " + result.size() + " destinatarios");

					for (Map<String, Object> dest : result) {
						String idioma = "";
						if (null != dest.get("IDIOMA")) {
							for (ConsultaItem consultaIdioma : consultasItemDest) {
								if (consultaIdioma.getIdioma().equals(dest.get("IDIOMA").toString())) {
									idioma= dest.get("IDIOMA").toString();
								}
							}
							if (idioma.equals("")) {
								idioma = usuario.getIdlenguaje();
							}
							
						}else{
							
							idioma = usuario.getIdlenguaje();
						}
						List<Document> listaDocumentos = new ArrayList<Document>();	
						LOGGER.info("el idioma del destinatario es: " + idioma);
						if (idioma.equals(consulta.getIdioma())) {
						
						existenConsultas = Boolean.TRUE;
						ficherogenerado++;
							plantillas= _modModeloPlantillaDocumentoExtendsMapper.selectInformesGenerar(Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), idioma);
						
						
						
						if (plantillas == null || plantillas.size() == 0) {
							String mensaje = "El modelo " + modelosComunicacionItem.getNombre() + " debe tener al menos una plantilla asociada"; 
							LOGGER.warn(mensaje);
							throw new BusinessException(mensaje);
						}
						int r =1;
						for(PlantillaModeloDocumentoDTO plantilla:plantillas){
						
							LOGGER.info("Rendimiento inicio tratamiento modelo de comunicacion: " + r);
							List<List<Map<String,Object>>> listaDatosExcel = new ArrayList<List<Map<String,Object>>>();
							String nombrePlantilla = "";
							Long idPlantillaGenerar = null;
							boolean esExcel = false;
							
							LOGGER.info("Rendimiento inicio ejecucion consultas " );
							
							LOGGER.info("Rendimiento inicio ejecucion consultas condicionales" );
							LOGGER.debug("Obtenemos las consultas condicional: " + plantilla.getIdPlantillas());
							List<ConsultaItem> consultasItemCondicional = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(Short.valueOf(dialogo.getIdInstitucion()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

							if(consultasItemCondicional != null && consultasItemCondicional.size() > 0){
								
								LOGGER.debug("Número de consultas condicionales: " + consultasItemCondicional.size());
								
								for(ConsultaItem consultaCondicional:consultasItemCondicional){
									
									consultaEjecutarCondicional = reemplazarConsultaConClaves(usuario, dialogo, consultaCondicional, mapaClave, esEnvio);
									
									idConsultaEjecutarCondicional = Long.parseLong(consultaCondicional.getIdConsulta());
									
									List<Map<String, Object>> resultCondicional;
									try {
				 
										LOGGER.info("SIGARNV-1232 generarComunicacion() -> Ejecutamos la consulta "+consultaEjecutarCondicional);
										resultCondicional = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarCondicional, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()), Long.valueOf(consultaCondicional.getIdConsulta()),Short.valueOf(consultaCondicional.getIdInstitucion()),consultaCondicional.getDescripcion());
										LOGGER.info("SIGARNV-1232 generarComunicacion() -> Ha sido ejecutada la consulta "+consultaEjecutarCondicional);
				 
									} catch (BusinessSQLException e) {
										LOGGER.error(e);
										throw new BusinessException("Error al ejecutar la consulta " + consultaCondicional.getDescripcion() + " " + e.getMessage(), e);
									} catch (Exception e) {
										LOGGER.error(e);
										throw new BusinessException("Error al ejecutar la consulta " + consultaCondicional.getDescripcion(), e);
									}
									
									if(resultCondicional != null && resultCondicional.size() > 0){
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

							LOGGER.info("Rendimiento fin ejecucion consultas condicionales" );
							if (continua) {
						
						
							//Obtenemos el nombre de la plantilla por cada destinatario
							if(plantilla.getIdPlantillas() != null){
								
								LOGGER.debug("Generación del documento para la plantilla " + plantilla.getIdPlantillas());
								
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
									LOGGER.debug("El formato de salida es Excel");
								}
									example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(idioma);
								
								List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
								
								if(listaPlantilla != null && listaPlantilla.size() == 1){
									ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
									nombrePlantilla = plantillaDoc.getPlantilla();
									idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
								}else if(listaPlantilla != null && listaPlantilla.size() > 1){
									LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
									throw new BusinessException("Exiten multiples plantillas asociada al informe en el idioma del usuario");
								}else{
									LOGGER.error("No hay plantilla asociada para el informe en el idioma del destinatario");
									throw new BusinessException("No hay plantilla asociada para el informe en el idioma del destinatario");
								}
							}
							

							
							//Obtenemos el nombre de la plantilla por cada destinatario
							if(plantilla.getIdPlantillas() != null){
								
								LOGGER.debug("Generación del documento para la plantilla " + plantilla.getIdPlantillas());
								
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
									LOGGER.debug("El formato de salida es Excel");
								}
									example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(idioma);
								
								
								List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
								
								if(listaPlantilla != null && listaPlantilla.size() == 1){
									ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
									nombrePlantilla = plantillaDoc.getPlantilla();
									idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
								}else if(listaPlantilla != null && listaPlantilla.size() > 1){
									LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
									throw new BusinessException("Exiten multiples plantillas asociada al informe en el idioma del usuario");
								}else{
									LOGGER.error("No hay plantilla asociada para el informe en el idioma del destinatario");
									throw new BusinessException("No hay plantilla asociada para el informe en el idioma del destinatario");
								}
							}
							LOGGER.debug("Guardamos el resultado de la query para cada destinatario");
							if (esEnvio) {
								LOGGER.debug("Guardamos las consultas para la generación del envio");

								// Guardamos la consulta condicional

								if (idConsultaEjecutarCondicional != null) {
									listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
											idConsultaEjecutarCondicional, consultaEjecutarCondicional,
											usuario.getIdinstitucion(),
											SigaConstants.OBJETIVO.CONDICIONAL.getCodigo(),
											Short.parseShort(String.valueOf(usuario.getIdusuario())),
											idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()),
											Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
											Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
								}

								// Guardamos la consulta de destinatarios

								listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
										Long.parseLong(consulta.getIdConsulta()), consultaEjecutarDestinatarios,
										usuario.getIdinstitucion(),
										SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo(),
										Short.parseShort(String.valueOf(usuario.getIdusuario())),
										idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()),
										Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
										Short.parseShort(String.valueOf(consulta.getIdInstitucion())));

								// Reemplazamos los campos para las consultas de la plantilla de envio

								if (listaConsultasPlantillaEnvio != null
										&& listaConsultasPlantillaEnvio.size() > 0) {
									for (ConsultaItem consultaPlantilla : listaConsultasPlantillaEnvio) {
										String consultaPlantillaEnvio = reemplazarConsultaConClaves(usuario,
												dialogo, consultaPlantilla, mapaClave, esEnvio);
										listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
												Long.parseLong(consultaPlantilla.getIdConsulta()),
												consultaPlantillaEnvio, usuario.getIdinstitucion(),
												Long.parseLong(consultaPlantilla.getIdObjetivo()),
												Short.parseShort(String.valueOf(usuario.getIdusuario())), null,
												Long.parseLong(plantilla.getIdInforme()),
												Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
												Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
									}
								}
								
								obtenerDatosDestinatario(destinatario,dest);


							} else {
								esDestinatario = true;
							}

							numFicheros = 0;

							hDatosGenerales = new HashMap<String, Object>();
							hDatosGenerales.putAll(dest);

							hDatosFinal = new HashMap<String, Object>();
							
							LOGGER.debug("Obtenemos la consulta de multidocumento para la plantilla: " + plantilla.getIdInforme());
							List<ConsultaItem> consultasItemMulti = _modPlantillaDocumentoConsultaExtendsMapper
									.selectConsultaPorObjetivo(usuario.getIdinstitucion(),
											Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
											plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
							boolean consultasDestinatarioEjecutadas = consultasItemDest.size() > 0;
							if (consultasItemMulti != null && consultasItemMulti.size() > 0) {
								LOGGER.info("Rendimiento inicio ejecucion consultas multidocumento" );
								for (ConsultaItem consultaMulti : consultasItemMulti) {
									String consultaEjecutarMulti = reemplazarConsultaConClaves(usuario, dialogo, consultaMulti,
											mapaClave, esEnvio);

									if (esEnvio) {
										// Guardamos la consulta multidocumento
										listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
												Long.parseLong(consultaMulti.getIdConsulta()), consultaEjecutarMulti,
												usuario.getIdinstitucion(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo(),
												Short.parseShort(String.valueOf(usuario.getIdusuario())), idPlantillaGenerar,
												Long.parseLong(plantilla.getIdInforme()),
												Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
												Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
									}

									List<Map<String, Object>> resultMulti;
									try {
										resultMulti = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarMulti, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
												Long.valueOf(consultaMulti.getIdConsulta()),Short.valueOf(consultaMulti.getIdInstitucion()),consultaMulti.getDescripcion());
										LOGGER.info("Se ejecuta la consulta MULTI");
										if(resultMulti != null && resultMulti.size() > 0){
											for(int k = 0;k<resultMulti.size();k++){
												// Por cada registro generamos un documento
												numFicheros++;
												generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla, idPlantillaGenerar,
														listaConsultasEnvio, listaFicheros, listaDocumentos, listaDatosExcel, hDatosFinal,
														hDatosGenerales, null, mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
														nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas);
											}														
										}
											
									} catch (BusinessSQLException e) {
										LOGGER.error(e);
										throw new BusinessException("Error al ejecutar la consulta "
												+ consultaMulti.getDescripcion() + " " + e.getMessage(), e);
									} catch (Exception e) {
										LOGGER.error(e);
										throw new BusinessException(
												"Error al ejecutar la consulta " + consultaMulti.getDescripcion(), e);
									}
									
								}
								LOGGER.info("Rendimiento fin ejecucion consultas multidocumento" );
							}else {
								generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla, idPlantillaGenerar,
										listaConsultasEnvio, listaFicheros, listaDocumentos, listaDatosExcel, hDatosFinal,
										hDatosGenerales, null, mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
										nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas);
							}
						
							
							
							
					}
				
				}
		
		
				LOGGER.info("Rendimiento fin tratamiento modelo de comunicacion: " + r);
				r++;
			}
		
		}
	} else {
		LOGGER.info("La consulta de destinatarios no ha devuelto resultados");
		if (ficherogenerado == 0 && (numeroSeleccionado +1 == numeroSeleccionados)) {
			throw new BusinessException("La consulta de destinatarios no ha devuelto resultados");
		}
		
		}
	}
	}else {
		
					LOGGER.error("No hay consulta de destinatario para el modelo de comunicacion: " + modelosComunicacionItem.getIdModeloComunicacion());
					ejecutaPlantillas(request ,modelosComunicacionItem, dialogo, usuario, mapaClave, esEnvio, listaConsultasEnvio, listaConsultasPlantillaEnvio, rutaPlantillaClase, campoSufijo, listaFicheros, ejecutarConsulta, destinatario);
					existenConsultas = Boolean.TRUE;
	}
	LOGGER.info("Rendimiento fin ejecucion consultas destinatarios" );
				
	if (!existenConsultas) {
		LOGGER.error("No hay plantilla asociada para el informe en el idioma del destinatario");
		if (ficherogenerado == 0 && (numeroSeleccionado +1 == numeroSeleccionados)) {
		throw new BusinessException("No hay plantilla asociada para el informe en el idioma del destinatario");
		}
	}			
						
	return ficherogenerado;						
	}
	
	private void ejecutaPlantillas(HttpServletRequest request, ModelosComunicacionItem modelosComunicacionItem, DialogoComunicacionItem dialogo,
			AdmUsuarios usuario, HashMap<String, String> mapaClave, boolean esEnvio, List<ConsultaEnvioItem> listaConsultasEnvio, List<ConsultaItem> listaConsultasPlantillaEnvio, String rutaPlantillaClase, String campoSufijo, List<DatosDocumentoItem> listaFicheros, boolean ejecutarConsulta, DestinatarioItem destinatario) throws Exception {

	
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);

		
		ModelosComunicacionSearch respuesta = new ModelosComunicacionSearch();
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
		boolean continua = true;
		boolean esDestinatario = false;
		Long idConsultaEjecutarCondicional = null;
		String consultaEjecutarCondicional = "";
		int numFicheros = 0;
		
		HashMap<String,Object> hDatosGenerales = new HashMap<String, Object>();		
		HashMap<String,Object> hDatosFinal = new HashMap<String, Object>();	
		
		List<Document> listaDocumentos = new ArrayList<Document>();	
		
		List<PlantillaModeloDocumentoDTO> plantillas = _modModeloPlantillaDocumentoExtendsMapper.selectInformesGenerar(Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), usuario.getIdlenguaje());
		
		
		if (plantillas == null || plantillas.size() == 0) {
			String mensaje = "El modelo " + modelosComunicacionItem.getNombre() + " debe tener al menos una plantilla asociada"; 
			LOGGER.warn(mensaje);
			throw new BusinessException(mensaje);
		}
		int r =1;
		for(PlantillaModeloDocumentoDTO plantilla:plantillas){
			LOGGER.info("Rendimiento inicio tratamiento modelo de comunicacion: " + r);
			List<List<Map<String,Object>>> listaDatosExcel = new ArrayList<List<Map<String,Object>>>();
			String nombrePlantilla = "";
			Long idPlantillaGenerar = null;
			boolean esExcel = false;
			List<ModPlantilladocumento> listaPlantilla = null;
			//Obtenemos el nombre de la plantilla
			if(plantilla.getIdPlantillas() != null){
				
				LOGGER.debug("Generación del documento para la plantilla " + plantilla.getIdPlantillas());
				
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
					LOGGER.debug("El formato de salida es Excel");
				}
				
				LOGGER.info("SIGARNV-631 ejecutaPlantillas() -> Consulta a la tabla MOD_PLANTILLADOCUMENTO - INICIO");
				example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(usuario.getIdlenguaje());
				listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
				LOGGER.info("SIGARNV-631 ejecutaPlantillas() -> Consulta a la tabla MOD_PLANTILLADOCUMENTO - FIN");
				
				
				LOGGER.info("SIGARNV-631 ejecutaPlantillas() -> Comprobamos si existe una plantilla asociada al informe  - INICIO");
				if(listaPlantilla == null || listaPlantilla.size() < 1){
					LOGGER.error("No hay plantilla asociada para el informe en el idioma del usuario");
					throw new BusinessException("No hay plantilla asociada para el informe en el idioma del usuario");
				}
				LOGGER.info("SIGARNV-631 ejecutaPlantillas() -> Comprobamos si existe una plantilla asociada al informe  - FIN");
			}
			
			for (ModPlantilladocumento plantillaDoc: listaPlantilla) {
				nombrePlantilla = plantillaDoc.getPlantilla();
				idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
				LOGGER.info("Rendimiento inicio ejecucion consultas " );
				
				LOGGER.info("Rendimiento inicio ejecucion consultas condicionales" );
				LOGGER.debug("Obtenemos las consultas condicional: " + plantilla.getIdPlantillas());
				List<ConsultaItem> consultasItemCondicional = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(Short.valueOf(dialogo.getIdInstitucion()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());
	
				if(consultasItemCondicional != null && consultasItemCondicional.size() > 0){
					
					LOGGER.debug("Número de consultas condicionales: " + consultasItemCondicional.size());
					
					for(ConsultaItem consulta:consultasItemCondicional){
						
						consultaEjecutarCondicional = reemplazarConsultaConClaves(usuario, dialogo, consulta, mapaClave, esEnvio);
						
						idConsultaEjecutarCondicional = Long.parseLong(consulta.getIdConsulta());
						
						List<Map<String, Object>> result;
						try {
	 
							LOGGER.info("SIGARNV-1232 generarComunicacion() -> Ejecutamos la consulta "+consultaEjecutarCondicional);
							result = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarCondicional, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()), Long.valueOf(consulta.getIdConsulta()),Short.valueOf(consulta.getIdInstitucion()),consulta.getDescripcion());
							LOGGER.info("SIGARNV-1232 generarComunicacion() -> Ha sido ejecutada la consulta "+consultaEjecutarCondicional);
	 
						} catch (BusinessSQLException e) {
							LOGGER.error(e);
							throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion() + " " + e.getMessage(), e);
						} catch (Exception e) {
							LOGGER.error(e);
							throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(), e);
						}
						
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
				LOGGER.info("Rendimiento fin ejecucion consultas condicionales" );
	//							
				LOGGER.info("Rendimiento inicio ejecucion consultas destinatarios" );
				if (continua) {
					LOGGER.debug("Obtenemos la consulta de destinatario: " + plantilla.getIdInforme());
					List<ConsultaItem> consultasItemDest = _modPlantillaDocumentoConsultaExtendsMapper
							.selectConsultaPorObjetivo(usuario.getIdinstitucion(),
									Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
									plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
	
					if (consultasItemDest != null && consultasItemDest.size() > 0) {
	
						LOGGER.debug("Número de consultas de destintarios " + consultasItemDest.size());
						for (ConsultaItem consulta : consultasItemDest) {
							String consultaEjecutarDestinatarios = reemplazarConsultaConClaves(usuario, dialogo, consulta,
									mapaClave, esEnvio);
	
							List<Map<String, Object>> result;
							try {
	 
								result = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDestinatarios, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()), Long.valueOf(consulta.getIdConsulta()),Short.valueOf(consulta.getIdInstitucion()),consulta.getDescripcion());	
								
								LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se ejecuta la consulta de destinatarios: " + consultaEjecutarDestinatarios);
								LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se envía a la dirección: " + result);
	 
							} catch (BusinessSQLException e) {
								LOGGER.error(e);
								throw new BusinessException(
										"Error al ejecutar la consulta " + consulta.getDescripcion() + " " + e.getMessage(),
										e);
							} catch (Exception e) {
								LOGGER.warn("Error al ejejcutar la consulta" + e);
								throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(),
										e);
							}
	
							if (result != null && result.size() > 0) {
								LOGGER.debug("Se han obtenido " + result.size() + " destinatarios");
	
								for (Map<String, Object> dest : result) {
									LOGGER.debug("Guardamos el resultado de la query para cada destinatario");
									if (esEnvio) {
										LOGGER.debug("Guardamos las consultas para la generación del envio");
	
										// Guardamos la consulta condicional
	
										if (idConsultaEjecutarCondicional != null) {
											listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
													idConsultaEjecutarCondicional, consultaEjecutarCondicional,
													usuario.getIdinstitucion(),
													SigaConstants.OBJETIVO.CONDICIONAL.getCodigo(),
													Short.parseShort(String.valueOf(usuario.getIdusuario())),
													idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()),
													Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
													Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
										}
	
										// Guardamos la consulta de destinatarios
	
										listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
												Long.parseLong(consulta.getIdConsulta()), consultaEjecutarDestinatarios,
												usuario.getIdinstitucion(),
												SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo(),
												Short.parseShort(String.valueOf(usuario.getIdusuario())),
												idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()),
												Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
												Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
	
										// Reemplazamos los campos para las consultas de la plantilla de envio
	
										if (listaConsultasPlantillaEnvio != null
												&& listaConsultasPlantillaEnvio.size() > 0) {
											for (ConsultaItem consultaPlantilla : listaConsultasPlantillaEnvio) {
												String consultaPlantillaEnvio = reemplazarConsultaConClaves(usuario,
														dialogo, consultaPlantilla, mapaClave, esEnvio);
												listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
														Long.parseLong(consultaPlantilla.getIdConsulta()),
														consultaPlantillaEnvio, usuario.getIdinstitucion(),
														Long.parseLong(consultaPlantilla.getIdObjetivo()),
														Short.parseShort(String.valueOf(usuario.getIdusuario())), null,
														Long.parseLong(plantilla.getIdInforme()),
														Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
														Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
											}
										}
										
										obtenerDatosDestinatario(destinatario,dest);
	
	
									} else {
										esDestinatario = true;
									}
	
									numFicheros = 0;
	
									hDatosGenerales = new HashMap<String, Object>();
									hDatosGenerales.putAll(dest);
	
									hDatosFinal = new HashMap<String, Object>();
								}
							} else {
								LOGGER.info("La consulta de destinatarios no ha devuelto resultados");
							}
						}
					}else {
						LOGGER.error("No hay consulta de destinatario para el informe: " + plantilla.getIdInforme());
					}
					LOGGER.info("Rendimiento fin ejecucion consultas destinatarios" );
					
					
					
					LOGGER.debug("Obtenemos la consulta de multidocumento para la plantilla: " + plantilla.getIdInforme());
					List<ConsultaItem> consultasItemMulti = _modPlantillaDocumentoConsultaExtendsMapper
							.selectConsultaPorObjetivo(usuario.getIdinstitucion(),
									Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
									plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
					boolean consultasDestinatarioEjecutadas = Boolean.FALSE;
					if (!(null != modelosComunicacionItem.getIdClaseComunicacion() &&  modelosComunicacionItem.getIdClaseComunicacion().equals("5"))) {
						 consultasDestinatarioEjecutadas = consultasItemDest.size() > 0;
					}
					
					if (consultasItemMulti != null && consultasItemMulti.size() > 0) {
						LOGGER.info("Rendimiento inicio ejecucion consultas multidocumento" );
						for (ConsultaItem consultaMulti : consultasItemMulti) {
							String consultaEjecutarMulti = reemplazarConsultaConClaves(usuario, dialogo, consultaMulti,
									mapaClave, esEnvio);
	
							if (esEnvio) {
								// Guardamos la consulta multidocumento
								listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
										Long.parseLong(consultaMulti.getIdConsulta()), consultaEjecutarMulti,
										usuario.getIdinstitucion(), SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo(),
										Short.parseShort(String.valueOf(usuario.getIdusuario())), idPlantillaGenerar,
										Long.parseLong(plantilla.getIdInforme()),
										Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
										Short.parseShort(String.valueOf(consultaMulti.getIdInstitucion())));
							}
	
							List<Map<String, Object>> resultMulti;
							try {
								resultMulti = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarMulti, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
										Long.valueOf(consultaMulti.getIdConsulta()),Short.valueOf(consultaMulti.getIdInstitucion()),consultaMulti.getDescripcion());
								LOGGER.info("Se ejecuta la consulta MULTI");
								if(resultMulti != null && resultMulti.size() > 0){
									for(int k = 0;k<resultMulti.size();k++){
										// Por cada registro generamos un documento
										numFicheros++;
										generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla, idPlantillaGenerar,
												listaConsultasEnvio, listaFicheros, listaDocumentos, listaDatosExcel, hDatosFinal,
												hDatosGenerales, null, mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
												nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas);
									}														
								}
									
							} catch (BusinessSQLException e) {
								LOGGER.error(e);
								throw new BusinessException("Error al ejecutar la consulta "
										+ consultaMulti.getDescripcion() + " " + e.getMessage(), e);
							} catch (Exception e) {
								LOGGER.error(e);
								throw new BusinessException(
										"Error al ejecutar la consulta " + consultaMulti.getDescripcion(), e);
							}
							
						}
						LOGGER.info("Rendimiento fin ejecucion consultas multidocumento" );
					}else {
						generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla, idPlantillaGenerar,
								listaConsultasEnvio, listaFicheros, listaDocumentos, listaDatosExcel, hDatosFinal,
								hDatosGenerales, null, mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
								nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas);
					}
				
	//				if (ejecutarConsulta) {
	//					// Por cada resultado ejecutamos las consultas de datos
	//					generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla, idPlantillaGenerar,
	//							listaConsultasEnvio, listaFicheros, listaDocumentos, listaDatosExcel, hDatosFinal,
	//							hDatosGenerales, null, mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
	//							nombrePlantilla, esEnvio, esExcel, esDestinatario);
	//				} 
				}
				
				LOGGER.info("Rendimiento fin tratamiento modelo de comunicacion: " + r);
				r++;
			}
		}			
	//	return existeConsulta;
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



	private String reemplazarConsultaConClaves(AdmUsuarios usuario, DialogoComunicacionItem dialogo, ConsultaItem consulta, HashMap<String, String> mapaClave, boolean esEnvio) {
		
		String sentencia = null;
		//Buscamos la consulta con sus parametros dinamicos
		
		consulta = findConsultaItem(dialogo.getConsultas(), consulta);
		
		if(consulta != null){
			// Reemplazamos los datos insertados desde pantalla		
			try {
				sentencia = _consultasService.procesarEjecutarConsulta(usuario, consulta.getSentencia(), consulta.getCamposDinamicos(), true);

			} catch (ParseException e) {
				LOGGER.error("Error al ejecutar la consulta con id " + consulta.getIdConsulta(), e);
				throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(), e);
			}
			
			// Remplazamos las claves de la query
			if(mapaClave != null && mapaClave.size() > 0) {
				for (Map.Entry<String, String> entry : mapaClave.entrySet()) {
					sentencia = sentencia.replace(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + entry.getKey().toUpperCase() + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, entry.getValue());
				}
			}			
			
		}else{
			LOGGER.error("No se ha encontrado la consulta");
			throw new BusinessException("No se ha encontrado la consulta");
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
		LOGGER.info("Obteniendo el nombre del fichero: " + plantilla.getNombreFicheroSalida());
		String nombreFichero = null;
		try {
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
			
			if(!(sufijos != null && sufijos.size() > 0)) {
				nombreFicheroSalida = nombreFicheroSalida + "_" + numeroFicheros;
				numeroFicheros++;
			} 
			
			String numero = "";
			if(numFichero > 0){
				numero = "_" + String.valueOf(numFichero);
			}
			
			nombreFichero = nombreFicheroSalida + sufijo + numero + "." + extension;
		
		}catch(Exception e) {
			LOGGER.error("Error al obtener el nombre del fichero", e);
			throw new BusinessException("Error al obtener el nombre del fichero", e);
		}
		
		
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
									List<ConsultaItem> listaConsultas = _modPlantillaDocumentoConsultaExtendsMapper.selectPlantillaDocConsultas(Short.valueOf(dialogo.getIdInstitucion()), Long.parseLong(modelo.getIdModeloComunicacion()), Long.parseLong(plantilla.getIdPlantillaDocumento()), false);
									if(listaConsultas != null && listaConsultas.size() > 0){
										for(ConsultaItem consultaItem: listaConsultas){
											ConConsultaKey key = new ConConsultaKey();
											key.setIdconsulta(Long.parseLong(consultaItem.getIdConsulta()));
											key.setIdinstitucion(Short.parseShort(consultaItem.getIdInstitucion()));
											ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
											if(consulta != null){
												ArrayList<CampoDinamicoItem> campos = _consultasService.obtenerCamposDinamicos(usuario, dialogo.isComunicar(), consulta.getSentencia());
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
							
							if(modelo.getIdPlantillaEnvio() != "" && modelo.getIdPlantillaEnvio() != null && modelo.getIdTipoEnvio() != null){
								//Obtenemos las consultas asociadas a la plantilla
								List<ConsultaItem> listaEnvioConsultas = _modPlantillaEnvioConsultaExtendsMapper.selectPlantillaEnvioConsultas(idInstitucion, Integer.parseInt(modelo.getIdPlantillaEnvio()), Short.parseShort(modelo.getIdTipoEnvio()));
								if(listaEnvioConsultas != null && listaEnvioConsultas.size() > 0){
									for(ConsultaItem consultaEnvioItem : listaEnvioConsultas){			
										if(consultaEnvioItem != null){
											ArrayList<CampoDinamicoItem> campos = _consultasService.obtenerCamposDinamicos(usuario, dialogo.isComunicar(), consultaEnvioItem.getSentencia());
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
							
							if (listaConsultasEjecutar != null && listaConsultasEjecutar.size() > 0) {
								response.setConsultaItem(listaConsultasEjecutar);
							} else {
								LOGGER.error("No se ha encontrado ninguna consulta para ejecutar. Ver los modelos selecionados si tienen plantillas y si las plantillas tienen documentos asociados");
								error.setCode(500);
								String mensaje = "No se ha encontrado ninguna consulta para ejecutar. Compruebe que los modelos seleccionados tienen plantillas y que las plantillas tienen documentos asociados.";
								error.setDescription(mensaje);
								error.setMessage(mensaje);
								response.setError(error);
							}
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
	@Transactional(timeout=2400)
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
					generarComunicacion = generarComunicacion(request, dialogo,usuario, true);					
					
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
							ModModelocomunicacion modelo =  _modModeloComunicacionMapper.selectByPrimaryKey(modeloEnvio.getIdModeloComunicacion());
							String descripcion = envio.getIdenvio() + "--" + modelo.getNombre();
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
								
								
								//Insertamos el nuevo asunto y cuerpo del envio
								EnvCamposenvios envCamposEnvio = new EnvCamposenvios();
								envCamposEnvio.setFechamodificacion(new Date());
								envCamposEnvio.setUsumodificacion(usuario.getIdusuario());
								envCamposEnvio.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_ASUNTO));
								envCamposEnvio.setIdenvio(envio.getIdenvio());
								envCamposEnvio.setIdinstitucion(usuario.getIdinstitucion());
								envCamposEnvio.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);
//								envCamposEnvio.setValor(datosTarjeta.getAsunto());							
								
								_envCamposenviosMapper.insert(envCamposEnvio);
								
								envCamposEnvio = new EnvCamposenvios();
								envCamposEnvio.setFechamodificacion(new Date());
								envCamposEnvio.setUsumodificacion(usuario.getIdusuario());
								envCamposEnvio.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_CUERPO));
								envCamposEnvio.setIdenvio(envio.getIdenvio());
								envCamposEnvio.setIdinstitucion(usuario.getIdinstitucion());
								envCamposEnvio.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);
//								envCamposEnvio.setValor(datosTarjeta.getCuerpo());	
								
								_envCamposenviosMapper.insert(envCamposEnvio);
								
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
									destinatario.setDomicilio(dest.getDomicilio());
									destinatario.setCodigopostal(dest.getCodigoPostal());
									destinatario.setIdpais(dest.getIdPais());
									destinatario.setIdpoblacion(dest.getIdPoblacion());
									destinatario.setIdprovincia(dest.getIdProvincia());
									destinatario.setPoblacionextranjera(dest.getPoblacionExtranjera());
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
								if (null != consultaEnvio.getUsuModificacion()) {
									consultaEnvioEntity.setUsumodificacion(Integer.valueOf((consultaEnvio.getUsuModificacion().toString())));
								}
								consultaEnvioEntity.setIdplantilladocumento(consultaEnvio.getIdPlantillaDoc());
								consultaEnvioEntity.setIdinforme(consultaEnvio.getIdInforme());
								consultaEnvioEntity.setIdmodelocomunicacion(consultaEnvio.getIdModeloComunicacion());
								consultaEnvioEntity.setSufijo(consultaEnvio.getSufijo());
								consultaEnvioEntity.setIdinstitucionconsulta(consultaEnvio.getIdInstitucionConsulta());
								_envConsultasenvioMapper.insert(consultaEnvioEntity);
								
								
								//Insertamos los documentos asociados al envio
								
								// Si ya se ha insertado lo omitimos
								if(consultaEnvio.getNombreFichero() != null && !nombresFicheros.contains(consultaEnvio.getNombreFichero())) {
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
		numeroFicheros = 1;  
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
		}else {
			LOGGER.error("No se ha encontrado el envío:" + idEnvio);
			throw new BusinessException("No se ha encontrado el envío:" + idEnvio);
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
							try {
								result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
							}catch(Exception e) {
								LOGGER.error("Error al ejecutar la consulta: " + sentencia);
								throw e;
							}							
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
							List<Map<String, Object>> result = null;
							try {
								result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
							}catch(Exception e) {
								LOGGER.error("Error al ejecutar la consulta: " + sentencia);
								throw e;
							}
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
								try {
									resultMulti = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
								}catch(Exception e) {
									LOGGER.error("Error al ejecutar la consulta: " + sentencia);
									throw e;
								}								
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
									String rutaTmp = getRutaFicheroSalida(idInstitucion);
									
									String rutaPlantilla = getRutaPlantilla(directorioPlantillaClase);
									
									//Si no existe el directorio temporal lo creamos
									File dir = new File(rutaTmp);
									if(!dir.exists()){
										dir.mkdirs();
									}	
									
									
									File filePlantilla = new File(rutaTmp + nombrePlantilla);
									
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
										List<Map<String,Object>> resultDatos = null;
																	
										//try {
//											ConConsultaKey key = new ConConsultaKey();
//											key.setIdconsulta(consultaDatos.getIdconsulta());
//											key.setIdinstitucion(consultaDatos.getIdinstitucion());
//											ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key );
											//resultDatos = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDatos,null, consultaDatos.getIdmodelocomunicacion(), consultaDatos.getIdconsulta(),consultaDatos.getIdinstitucion(),"");
											
											
											
//											
//											resultDatos = _conConsultasExtendsMapper.ejecutarConsultaString(consultaEjecutarDatos);
//										}catch (BusinessSQLException e) {
//											LOGGER.error(e);
//											throw new BusinessException("Error al ejecutar la consulta " + consultaDatos.getIdconsulta() + " " + e.getMessage(), e);
//										}catch (Exception e) {
//											LOGGER.error(e);
//											throw new BusinessException("Error al ejecutar la consulta " + consultaDatos.getIdconsulta() + " para el envío: " + idEnvio, e);
//										}
										consultaEjecutarDatos = _consultasService.quitarEtiquetas(consultaEjecutarDatos.toUpperCase());
										if(consultaEjecutarDatos != null && (consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_ALTER) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_CREATE)
												|| consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_DELETE) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_DROP)
												|| consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_INSERT) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_UPDATE))){
											
											LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + consultaEjecutarDatos);
										}else {								
											try {
												resultDatos = _conConsultasExtendsMapper.ejecutarConsultaString(consultaEjecutarDatos);
											}catch(Exception e) {
												LOGGER.error("Error al ejecutar la consulta: " + consultaEjecutarDatos);
												throw e;
											}								
										}
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

									
									String nombreFicheroSalida = obtenerNombreFicheroSalida(String.valueOf(consultaMulti.getIdmodelocomunicacion()), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, numFicheros, rutaTmp, campoSufijo);
																
									DatosDocumentoItem docGenerado = null;
									
									
									if(esExcel) {
										 docGenerado = _generacionDocService.generarExcel(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, listaDatosExcel, null);
										 
									}else {
										existePlantilla(filePlantilla);	
										Document doc = new Document(rutaPlantilla + nombrePlantilla);
										
										doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
//										EN CASO DE DEVOLVER FALSO ESTE DOC NO HAY DATOS, A TENER EN CUENTA PARA MENSAJE DE ERROR
										boolean firmado = false;
										
										if(plantilla.getFormatoSalida() != null){
											FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
											if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
												firmado = true;
											}
										}
										
										docGenerado = _generacionDocService.grabaDocumento(doc, rutaTmp, nombreFicheroSalida, firmado);
									}
									
									docGenerado.setPathDocumento(rutaTmp + nombreFicheroSalida);
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
						
						LOGGER.debug("Obtenemos la ruta temporal del fichero de salida");
						String rutaTmp = getRutaFicheroSalida(idInstitucion);
						
						LOGGER.debug("Obtenemos la ruta de la plantilla");
						String rutaPlantilla = getRutaPlantilla(directorioPlantillaClase);												
						
						//Si no existe el directorio temporal lo creamos
						File dir = new File(rutaTmp);
						if(!dir.exists()){
							dir.mkdirs();
						}
						
						
						File filePlantilla = new File(rutaPlantilla + nombrePlantilla);
						
						// Por cada resultado ejecutamos las consultas de datos
						LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
						example = new EnvConsultasenvioExample();
						example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.DATOS.getCodigo());
						List<EnvConsultasenvio> listaConsultasDatos = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
						ArrayList<String> nombresConsultasDatos = new ArrayList<String>();	
						for(EnvConsultasenvio consultaDatos:listaConsultasDatos){	
							
							String consultaEjecutarDatos = consultaDatos.getConsulta();
							
							if(campoSufijo == null || !"".equalsIgnoreCase(campoSufijo)){
								campoSufijo = consultaDatos.getSufijo();
							}
							
							List<Map<String,Object>> resultDatos = null;	
							
//							try {
//								ConConsultaKey key = new ConConsultaKey();
////								key.setIdconsulta(consultaDatos.getIdconsulta());
////								key.setIdinstitucion(consultaDatos.getIdinstitucion());
////								ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key );
//								//resultDatos = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDatos,null, consultaDatos.getIdmodelocomunicacion(), consultaDatos.getIdconsulta(),consultaDatos.getIdinstitucion(),"");
//								resultDatos = _conConsultasExtendsMapper.ejecutarConsultaString(consultaEjecutarDatos);
//							}catch (BusinessSQLException e) {
//								LOGGER.error(e);
//								throw new BusinessException("Error al ejecutar la consulta " + consultaDatos.getIdconsulta() + " " + e.getMessage(), e);
//							}catch(Exception e) {
//								String mensaje = "Error al ejecutar la consulta: " + consultaDatos.getIdconsulta() + " para el envío: " + idEnvio;
//								LOGGER.error(mensaje);
//								throw new BusinessException(mensaje,e);
//							}
							consultaEjecutarDatos = _consultasService.quitarEtiquetas(consultaEjecutarDatos.toUpperCase());
							if(consultaEjecutarDatos != null && (consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_ALTER) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_CREATE)
									|| consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_DELETE) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_DROP)
									|| consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_INSERT) || consultaEjecutarDatos.contains(SigaConstants.SENTENCIA_UPDATE))){
								
								LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + consultaEjecutarDatos);
							}else {								
								try {
									resultDatos = _conConsultasExtendsMapper.ejecutarConsultaString(consultaEjecutarDatos);
								}catch(Exception e) {
									LOGGER.error("Error al ejecutar la consulta: " + consultaEjecutarDatos);
									throw e;
								}								
							}
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
						
						String nombreFicheroSalida = obtenerNombreFicheroSalida(String.valueOf(idModeloComunicacion), plantilla, hDatosGenerales, SigaConstants.LENGUAJE_DEFECTO, 0, rutaTmp, campoSufijo);
						
						DatosDocumentoItem docGenerado = null;
						
						if(esExcel) {
							 docGenerado = _generacionDocService.generarExcel(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, listaDatosExcel, null);
						}else {
							existePlantilla(filePlantilla);
							
							Document doc = new Document(rutaPlantilla + nombrePlantilla);
							
							doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);														
							
							boolean firmado = false;
							
							if(plantilla.getFormatoSalida() != null){
								FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
								if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
									firmado = true;
								}
							}
							
							docGenerado = _generacionDocService.grabaDocumento(doc, rutaTmp, nombreFicheroSalida, firmado);
						}
						
						docGenerado.setPathDocumento(rutaTmp + nombreFicheroSalida);

						listaFicheros.add(docGenerado);
					}					
				}				
				
			}		
		}else{
			LOGGER.info("No hay plantillas asociadas al envio: " + idEnvio);
		}
				
		return listaFicheros;
	}
	
	private List<ConsultaEnvioItem> guardarDatosConsultas(List<ConsultaEnvioItem> listaConsultasEnvio, Long idConsulta, String consulta, Short idInstitucion, Long idObjetivo, Short idUsuario, Long idPlantilla, Long idInforme, Long idModelo, Short idInstitucionConsulta) {
		
		if(listaConsultasEnvio == null) {
			listaConsultasEnvio = new ArrayList<ConsultaEnvioItem>();
		}
		
		ConsultaEnvioItem consultaEnvio = new ConsultaEnvioItem();
		consultaEnvio.setIdConsulta(idConsulta);
		consultaEnvio.setConsulta(consulta);
		consultaEnvio.setIdInstitucion(idInstitucion);
		consultaEnvio.setIdObjetivo(idObjetivo);
		consultaEnvio.setUsuModificacion(idUsuario);
		consultaEnvio.setIdPlantillaDoc(idPlantilla);
		consultaEnvio.setIdInforme(idInforme);
		consultaEnvio.setIdModeloComunicacion(idModelo);
		consultaEnvio.setIdInstitucionConsulta(idInstitucionConsulta);
		listaConsultasEnvio.add(consultaEnvio);
		
		return listaConsultasEnvio;
	}
	
	private String getRutaFicheroSalida(String idInstitucion) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.parametroRutaSalidaInformes);
		
		GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
		
		String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion + SigaConstants.pathSeparator + SigaConstants.carpetaTmp + SigaConstants.pathSeparator + System.currentTimeMillis() + SigaConstants.pathSeparator;
		
		return rutaTmp;
	}
	
	private String getRutaFicheroSalidaTemp(String idInstitucion) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.pathAbsolutoFiler);
		
		// Obtenemos la ruta del filer
		GenProperties rutaFiler = _genPropertiesMapper.selectByPrimaryKey(key);
		
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.pathRelativoTemp);
		
		// Obtenemos la ruta de la carpeta de ficheros temporales
		GenProperties carpetaTemp = _genPropertiesMapper.selectByPrimaryKey(key);
		
		String rutaTmp = rutaFiler.getValor() + carpetaTemp.getValor() + idInstitucion + SigaConstants.pathSeparator + System.currentTimeMillis() + SigaConstants.pathSeparator;
		
		return rutaTmp;
	}
	
	private String getRutaPlantilla(String rutaPlantillaClase) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.parametroRutaPlantillas);
		
		GenProperties rutaFicherosPlantilla = _genPropertiesMapper.selectByPrimaryKey(key);
		
		String rutaPlantilla = rutaFicherosPlantilla.getValor() + SigaConstants.pathSeparator + rutaPlantillaClase + SigaConstants.pathSeparator;
		
		return rutaPlantilla;
	}
	
	private DestinatarioItem obtenerDatosDestinatario(DestinatarioItem destinatario, Map<String,Object> dest) {
		
		
		Object idPersona = dest.get(SigaConstants.ALIASIDPERSONA.trim());
		Object correo = dest.get(SigaConstants.ALIASCORREO.trim());
		Object movil = dest.get(SigaConstants.ALIASMOVIL.trim());
		Object domicilio = dest.get(SigaConstants.ALIASDOMICILIO.trim());
		Object codigoPostal = dest.get(SigaConstants.ALIASCODIGOPOSTAL.trim());
		Object idPais = dest.get(SigaConstants.ALIASIDPAIS.trim());
		Object idPoblacion = dest.get(SigaConstants.ALIASIDPOBLACION.trim());
		Object idProvincia= dest.get(SigaConstants.ALIASIDPROVINCIA.trim());
		Object poblacionExtranjera= dest.get(SigaConstants.ALIASPOBLACIONEXTRANJERA.trim());
		
		CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona.toString()));
		
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
		if(domicilio != null){
			destinatario.setDomicilio(domicilio.toString());
		}
		if(codigoPostal != null){
			destinatario.setCodigoPostal(codigoPostal.toString());
		}
		if(idPais != null){
			destinatario.setIdPais(idPais.toString());
		}
		if(idPoblacion != null){
			destinatario.setIdPoblacion(idPoblacion.toString());
		}
		if(idProvincia != null){
			destinatario.setIdProvincia(idProvincia.toString());
		}
		if(poblacionExtranjera != null){
			destinatario.setPoblacionExtranjera(poblacionExtranjera.toString());
		}
		return destinatario;
	}
	
	private void existePlantilla(File filePlantilla) {
		if(filePlantilla != null) {
			if(!filePlantilla.exists()){
				LOGGER.error("Fichero no existe " + filePlantilla.getAbsolutePath());
				throw new BusinessException("No existe la plantilla de documento " + filePlantilla.getAbsolutePath());
			}
		}else {
			LOGGER.error("Fichero nulo");
			throw new BusinessException("Fichero nulo");
		}
		
	}
	
	private void generarDocumentoConDatos(AdmUsuarios usuario, DialogoComunicacionItem dialogo, ModelosComunicacionItem modelosComunicacionItem, PlantillaModeloDocumentoDTO plantilla, Long idPlantillaGenerar, List<ConsultaEnvioItem> listaConsultasEnvio, List<DatosDocumentoItem> listaFicheros, List<Document> listaDocumentos, List<List<Map<String,Object>>> listaDatosExcel, HashMap<String,Object> hDatosFinal, HashMap<String,Object> hDatosGenerales, Map<String, Object> resultMulti, HashMap<String, String> mapaClave, String campoSufijo, int numFicheros, String rutaPlantillaClase, String nombrePlantilla, boolean esEnvio, boolean esExcel, boolean esDestinatario, boolean consultasDestinatarioEjecutadas) {
		
		LOGGER.debug("Obtenemos la ruta temporal del fichero de salida");
		String rutaTmp = getRutaFicheroSalidaTemp(dialogo.getIdInstitucion());
		
		LOGGER.debug("Obtenemos la ruta de la plantilla");
		String rutaPlantilla = getRutaPlantilla(rutaPlantillaClase);												
		List<Map<String,Object>> listaDatosDoc = new ArrayList<Map<String,Object>>();
		
		//Si no existe el directorio temporal lo creamos
		File dir = new File(rutaTmp);
		if(!dir.exists()){
			dir.mkdirs();
		}	
		
		
		File filePlantilla = new File(rutaPlantilla + nombrePlantilla);
																			
		Document doc = null;

		if(resultMulti != null) {
			hDatosGenerales.putAll(resultMulti);
		}
		
		LOGGER.info("Rendimiento inicio ejecucion consultas datos" );
		// Por cada resultado ejecutamos las consultas de datos
		LOGGER.debug("Obtenemos las consultas de datos para la plantilla: " + plantilla.getIdInforme());
		List<ConsultaItem> consultasItemFinal = new ArrayList<ConsultaItem>();
		List<ConsultaItem> consultasItemDatos = new ArrayList<ConsultaItem>();
		List<ConsultaItem> consultasItemDestinatario = new ArrayList<ConsultaItem>();	
		if(!consultasDestinatarioEjecutadas) {
			consultasItemDestinatario = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(Short.valueOf(dialogo.getIdInstitucion()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
			for(ConsultaItem consultaDatosDestinatario:consultasItemDestinatario){
				consultasItemFinal.add(consultaDatosDestinatario);
			}
		}

		consultasItemDatos = _modPlantillaDocumentoConsultaExtendsMapper.selectConsultaPorObjetivo(Short.valueOf(dialogo.getIdInstitucion()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), plantilla.getIdPlantillas(), SigaConstants.OBJETIVO.DATOS.getCodigo());
		
		for(ConsultaItem consultaDatosDatos:consultasItemDatos){
			consultasItemFinal.add(consultaDatosDatos);
		}		
		ArrayList<String> nombresConsultasDatos = new ArrayList<String>();	
		
		for(ConsultaItem consultaDatos:consultasItemFinal){																			
			
			String consultaEjecutarDatos = reemplazarConsultaConClaves(usuario, dialogo, consultaDatos, mapaClave, esEnvio);
			String nombreConsulta = consultaDatos.getDescripcion();
			if(esEnvio){
				//Guardamos la consulta datos															
				listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio, Long.parseLong(consultaDatos.getIdConsulta()),consultaEjecutarDatos,usuario.getIdinstitucion(), SigaConstants.OBJETIVO.DATOS.getCodigo(), Short.parseShort(String.valueOf(usuario.getIdusuario())), idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), Short.parseShort(String.valueOf(consultaDatos.getIdInstitucion())));
			}
			
			List<Map<String, Object>> resultDatos;
			try {
								
				resultDatos = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDatos, usuario, Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()), Long.valueOf(consultaDatos.getIdConsulta()),Short.valueOf(consultaDatos.getIdInstitucion()),consultaDatos.getDescripcion());		
				LOGGER.info("Se ejecuta la consulta de DATOS");
				
			} catch (BusinessSQLException e) {
				LOGGER.error(e);
				
				throw new BusinessException("Error al ejecutar la consulta " + consultaDatos.getDescripcion() + " " + e.getMessage(), e);
			} catch (Exception e) {
				LOGGER.error(e);
				throw new BusinessException("Error al ejecutar la consulta " + consultaDatos.getDescripcion(), e);
			}
			
			//Si la region no viene definida el documento no tiene region
			if(consultaDatos.getRegion()!= null && !consultaDatos.getRegion().equalsIgnoreCase("")){
				hDatosFinal.put(consultaDatos.getRegion(), resultDatos);
			}else{
				
				if(resultDatos != null && resultDatos.size() > 0) {
					hDatosGenerales.putAll(resultDatos.get(0));
				}
				hDatosFinal.put("row", hDatosGenerales);

//				listaDatosDoc = resultDatos;
//				if(resultDatos != null && resultDatos.size() > 0) {
//					hDatosFinal.put("region", resultDatos);
//				}
			}
			
			
			//Si hay error porque la region que viene de consultaDatos es erronea, se elimina de arriba y se hace en este else que esta comentado
			if(esExcel) {
				listaDatosExcel.add(resultDatos);
				nombresConsultasDatos.add(nombreConsulta);
			}
//			else {
//				listaDatosDoc = resultDatos;
//				hDatosFinal.put("region", resultDatos);
//			}
		}
		LOGGER.info("Rendimiento fin ejecucion consultas datos" );
//		hDatosFinal.put("row", hDatosGenerales);
		
		//Obtenemos el sufijo del fichero para el caso de que se haya seleccionado el sufijo de entidad
		String campoSufijoReplaced = "";
		if(campoSufijo != null && !"".equals(campoSufijo)) {
			campoSufijoReplaced = reemplazarSufijo(hDatosGenerales, mapaClave, campoSufijo);
		}
		
		//plantilla.setNombreFicheroSalida(nombrePlantilla);
		String nombreFicheroSalida = obtenerNombreFicheroSalida(modelosComunicacionItem.getIdModeloComunicacion(), plantilla, hDatosGenerales, usuario.getIdlenguaje(), numFicheros, rutaTmp, campoSufijoReplaced);
		

		
		if(!esEnvio){
			LOGGER.info("Rendimiento inicio generacion del documento con datos" );
			LOGGER.debug("Generamos el documento");																
			DatosDocumentoItem docGenerado = null;
			
			if(esExcel) {
				try {
					docGenerado = _generacionDocService.generarExcel(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, listaDatosExcel, nombresConsultasDatos);
				} catch (Exception e) {
					LOGGER.error("Error al generar el fichero excel ", e);
					throw new BusinessException("No se ha podido generar el fichero Excel", e);
				}
			}else {
				existePlantilla(filePlantilla);
				
				try {
					doc = new Document(rutaPlantilla + nombrePlantilla);
				
					
					doc = _generacionDocService.sustituyeDocumento(doc, hDatosFinal);																			
					
					boolean firmado = false;
					
					if(plantilla.getFormatoSalida() != null){
						FORMATO_SALIDA extensionObject = SigaConstants.FORMATO_SALIDA.getEnum(Short.parseShort(plantilla.getFormatoSalida()));			
						if(extensionObject.getCodigo().shortValue() == FORMATO_SALIDA.PDF_FIRMADO.getCodigo().shortValue()){
							firmado = true;
						}
					}
					
					docGenerado = _generacionDocService.grabaDocumento(doc, rutaTmp, nombreFicheroSalida, firmado);

				} catch (Exception e) {
					LOGGER.error(e);
					throw new BusinessException("Error al generar el fichero", e);
				}
			}
			
			if (docGenerado != null) {
				listaFicheros.add(docGenerado);
			}
			if (doc != null) {
				listaDocumentos.add(doc);
			}
			
			LOGGER.info("Rendimiento fin generacion del documento con datos" );
		}else{
			//Cogemos todas las consultas y le metemos el nombre del fichero
			if(listaConsultasEnvio != null && listaConsultasEnvio.size() > 0){
				for(ConsultaEnvioItem consultaEnvio : listaConsultasEnvio){
					consultaEnvio.setSufijo(campoSufijoReplaced);
					consultaEnvio.setPathFichero(rutaTmp);
					consultaEnvio.setNombreFichero(nombreFicheroSalida);
				}
			}																		
		}																															
	}
}
