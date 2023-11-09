package org.itcgae.siga.com.services.impl;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.management.IntrospectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosDireccionLetradoOficio;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.CampoDinamicoItem;
import org.itcgae.siga.DTOs.com.CamposPlantillaEnvio;
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
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearchConNombreConsultaDestinatarios;
import org.itcgae.siga.DTOs.com.ModelosComunicacionesItemConNombreConsultaDestinatarios;
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
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DatosCartaAcreditacionItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.FORMATO_SALIDA;
import org.itcgae.siga.commons.constants.SigaConstants.GEN_PARAMETROS;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDestinatariosExample;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosExample;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantilladocumentoExample;
import org.itcgae.siga.db.entities.ScsComunicaciones;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvCamposenviosMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvHistoricoestadoenvioMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ScsComunicacionesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
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
import org.itcgae.siga.db.services.com.mappers.ScsComunicacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDefendidosdesignasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.BusinessSQLException;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import com.aspose.words.Document;
import com.google.common.io.Files;

import oracle.security.crypto.core.DES;

@Service
@EnableAsync
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
	private CenDireccionesExtendsMapper cenDireccionesMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoMapper;
	
	@Autowired
	private ScsDesignacionesExtendsMapper designacionesMapper;
	
	@Autowired
	private ScsDefendidosdesignasExtendsMapper defendidosMapper;
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	@Autowired
	private IEnviosMasivosService _enviosMasivosService;
	
    @Autowired    
    CenClienteExtendsMapper _cenClienteExtendsMapper;
    
    @Autowired
    private ScsComunicacionesExtendsMapper scsComunicacionesExtendsMapper;
	
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
	public ModelosComunicacionSearchConNombreConsultaDestinatarios obtenerModelosConConsultaDestinatarios(HttpServletRequest request, ModeloDialogoItem modeloDTO) {
		
		LOGGER.info("obtenerModelosConConsultaDestinatarios() -> Entrada al servicio para obtener los modelos de comunicacion");
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		
		ModelosComunicacionSearchConNombreConsultaDestinatarios respuesta = new ModelosComunicacionSearchConNombreConsultaDestinatarios();
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (usuarios != null && usuarios.size() > 0) {
				try{
					AdmUsuarios usuario = usuarios.get(0);
					List<ModelosComunicacionesItemConNombreConsultaDestinatarios> modelos = _modModeloComunicacionExtendsMapper.selectModelosComunicacionDialogoConConsultaDestinatarios(String.valueOf(idInstitucion), modeloDTO.getIdInstitucion(), modeloDTO.getIdClaseComunicacion(), modeloDTO.getIdModulo(), usuario.getIdlenguaje(), modeloDTO.getIdConsulta(), perfiles);
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
		
		LOGGER.info("obtenerModelosConConsultaDestinatarios() -> Salida del servicio para obtener los modelos de comunicacion");
		
		return respuesta;
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

	@Async
	@Transactional(timeout=600000)
	public  ListenableFuture<File>  obtenerNombre(HttpServletRequest request, DialogoComunicacionItem dialogo, HttpServletResponse resp) {
		LOGGER.info("descargarComunicacion() -> Entrada al servicio para descargar la documentación de la comunicación");
		
		File file = null;

		try {
			
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			
			List<DatosDocumentoItem> listaFicheros = null;
			GenerarComunicacionItem generarComunicacion = new GenerarComunicacionItem();
			
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
		return  AsyncResult.forValue(file);
		
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
		List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
		int ficherogenerado = 0;
		ModClasecomunicaciones modClasecomunicacion = null;
		String cuerpoEnvio = null;
		CamposPlantillaEnvio camposEnvio = new CamposPlantillaEnvio();

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
					if(plantilla == null || plantilla.getIdpersona() == null) {
						String mensaje = "La plantilla especificada no tiene remitente"; 
						LOGGER.warn(mensaje);
						throw new BusinessException(mensaje);
					}
					if(plantilla.getCuerpo() != null)
						camposEnvio.setCuerpo(plantilla.getCuerpo());
					if(plantilla.getAsunto() != null)
						camposEnvio.setAsunto(plantilla.getAsunto());
			
				}
				
				// Obtenemos la plantilla de envio seleccionada en el modelo
				List<ConsultaItem> listaConsultasPlantillaEnvio = null;
				if(modelosComunicacionItem.getIdPlantillaEnvio() != null && modelosComunicacionItem.getIdTipoEnvio()!=null){
					listaConsultasPlantillaEnvio = _modPlantillaEnvioConsultaExtendsMapper.selectPlantillaEnvioConsultas(Short.valueOf(dialogo.getIdInstitucion()), Integer.parseInt(modelosComunicacionItem.getIdPlantillaEnvio()), Short.parseShort(modelosComunicacionItem.getIdTipoEnvio()));
				}
				
				// Por cada conjunto de valores key se generan los documentos
				
				List<List<String>> listaKeyFiltros = dialogo.getSelectedDatos();					
					
				boolean ejecutarConsulta = false;
				
				if((dialogo.getIdClaseComunicacion().equals(SigaConstants.ID_CLASE_CONSULTA_GENERICA)  && (listaKeyFiltros == null || listaKeyFiltros.size() == 0) )|| dialogo.getSentenciaImprimir() != null) {
					listaKeyFiltros = new ArrayList<List<String>>();
					List<String> listaVacia = new ArrayList<String>();
					listaKeyFiltros.add(listaVacia);
					ejecutarConsulta = true;
				}
				
				if((listaKeyFiltros != null && listaKeyFiltros.size() > 0) || ejecutarConsulta){
					if(modelosComunicacionItem.getInformeUnico() != null && modelosComunicacionItem.getInformeUnico().equals("1")) {
						List<ConsultaEnvioItem> listaConsultasEnvio = new ArrayList<ConsultaEnvioItem>();
						destinatarios =new ArrayList<DestinatarioItem>();
						
						String rutaPlantillaModelo = getRutaModeloByClaseModelo(modelosComunicacionItem.getIdModeloComunicacion(), modelosComunicacionItem.getIdClaseComunicacion());
						int ficherogeneradoOK = ejecutaPlantillas(request ,modelosComunicacionItem, dialogo, usuario, null, esEnvio, listaConsultasEnvio, listaConsultasPlantillaEnvio, rutaPlantillaModelo, campoSufijo, listaFicheros, ejecutarConsulta, destinatarios,listaKeyFiltros.size(),ficherogenerado,1,listaKeyFiltros, camposEnvio);
											
						if (ficherogeneradoOK > 0) {
							ficherogenerado++;
							
						}
						// Por cada key seleccionada
						if(esEnvio){
							DatosEnvioDTO dato = new DatosEnvioDTO();
							dato.setConsultas(listaConsultasEnvio);
							dato.setDestinatarios(destinatarios);
							listaConsultasYDestinatario.add(dato);
						}
					}else {
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
							destinatarios = new ArrayList<DestinatarioItem>();
							
							String rutaPlantillaModelo = getRutaModeloByClaseModelo(modelosComunicacionItem.getIdModeloComunicacion(), modelosComunicacionItem.getIdClaseComunicacion());
							int ficherogeneradoOK = ejecutaPlantillas(request ,modelosComunicacionItem, dialogo, usuario, mapaClave, esEnvio, listaConsultasEnvio, listaConsultasPlantillaEnvio, rutaPlantillaModelo, campoSufijo, listaFicheros, ejecutarConsulta, destinatarios,listaKeyFiltros.size(),ficherogenerado,i,null, camposEnvio);
												
							if (ficherogeneradoOK > 0) {
								ficherogenerado++;
								
							}
								
							// Por cada key seleccionada
							if(esEnvio){
								DatosEnvioDTO dato = new DatosEnvioDTO();
								dato.setConsultas(listaConsultasEnvio);
								dato.setDestinatarios(destinatarios);
								listaConsultasYDestinatario.add(dato);
							}
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
	
	private String getRutaModeloByClaseModelo(String idModeloComunicacion, String idClaseComunicacion) {
		String rutaPlantillaClase  = "";
		String rutaPlantillaModelo = "";
		ModClasecomunicacionesExample claseExample = new ModClasecomunicacionesExample();
		claseExample.createCriteria().andIdclasecomunicacionEqualTo(Short.valueOf(idClaseComunicacion));
		List <ModClasecomunicaciones> listaClaseRuta = _modClasecomunicacionesMapper.selectByExample(claseExample);
		if(!listaClaseRuta.isEmpty() && listaClaseRuta != null) {
			rutaPlantillaClase = listaClaseRuta.get(0).getRutaplantilla();
			
			ModModelocomunicacionExample modeloExample = new ModModelocomunicacionExample();
			modeloExample.createCriteria().andIdmodelocomunicacionEqualTo(Long.parseLong(idModeloComunicacion)).andIdclasecomunicacionEqualTo(Short.valueOf(idClaseComunicacion));
			List<ModModelocomunicacion> listaModeloComunicacion = _modModeloComunicacionMapper.selectByExample(modeloExample);

			if(!listaModeloComunicacion.isEmpty() && listaClaseRuta != null) {
				String idInstitucionModelo = String.valueOf( listaModeloComunicacion.get(0).getIdinstitucion());
				rutaPlantillaModelo = rutaPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, idInstitucionModelo);
				LOGGER.info("Campo rutaPlantillaClase = " + rutaPlantillaClase);
			
			}else {
				rutaPlantillaModelo = SigaConstants.rutaPlantillaSinClase;
				LOGGER.info("Campo rutaPlantillaClase = " + rutaPlantillaClase);
			}
		}
		return rutaPlantillaModelo;
	}



	private int ejecutaPlantillas(HttpServletRequest request, ModelosComunicacionItem modelosComunicacionItem,
			DialogoComunicacionItem dialogo, AdmUsuarios usuario, HashMap<String, String> mapaClave, boolean esEnvio,
			List<ConsultaEnvioItem> listaConsultasEnvio, List<ConsultaItem> listaConsultasPlantillaEnvio,
			String rutaPlantillaClase, String campoSufijo, List<DatosDocumentoItem> listaFicheros,
			boolean ejecutarConsulta, List<DestinatarioItem> destinatarios, int numeroSeleccionados,
			int ficherogenerado, int numeroSeleccionado, List<List<String>> listaKeyFiltros,
			CamposPlantillaEnvio camposEnvio) throws Exception {

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
		String idiomaUsu = usuarios.get(0).getIdlenguaje();

		HashMap<String, Object> hDatosGenerales = new HashMap<String, Object>();
		HashMap<String, Object> hDatosFinal = new HashMap<String, Object>();
		DestinatarioItem desti = null;

		List<PlantillaModeloDocumentoDTO>  plantillasConDest = _modModeloPlantillaDocumentoExtendsMapper
				.selectInformesGenerarConDest(Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), idiomaUsu);
		
		List<PlantillaModeloDocumentoDTO> plantillasSinFiltro = _modModeloPlantillaDocumentoExtendsMapper.selectInformesGenerar(Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), usuario.getIdlenguaje());
		
		Set<String> idRepetido = plantillasConDest.stream().map(PlantillaModeloDocumentoDTO::getIdPlantillaDocumento).collect(Collectors.toSet());
		
		plantillasSinFiltro.removeIf(planti ->idRepetido.contains(planti.getIdPlantillaDocumento()));

		if(!plantillasConDest.isEmpty() && plantillasConDest != null) {
			int r = 1;


			for (PlantillaModeloDocumentoDTO plantilla : plantillasConDest) {
				LOGGER.info("Rendimiento inicio tratamiento modelo de comunicacion: " + r);
				List<ConsultaItem> consultasItemDest = _modPlantillaDocumentoConsultaExtendsMapper
						.selectConsultasDestinatarioByModelo(usuario.getIdinstitucion(),
								Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
								SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo(), idiomaUsu,
								plantilla.getIdPlantillaDocumento());
				if (consultasItemDest != null && consultasItemDest.size() > 0) {

					LOGGER.debug("Número de consultas de destintarios " + consultasItemDest.size());
					for (ConsultaItem consulta : consultasItemDest) {
						String consultaEjecutarDestinatarios = reemplazarConsultaConClaves(usuario, dialogo, consulta,
								mapaClave, esEnvio, modelosComunicacionItem, listaKeyFiltros,false);

						List<Map<String, Object>> result;
						try {

							result = _consultasService.ejecutarConsultaConClavesLog(consultaEjecutarDestinatarios, usuario,
									Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
									Long.valueOf(consulta.getIdConsulta()), Short.valueOf(consulta.getIdInstitucion()),
									consulta.getDescripcion());

							LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se ejecuta la consulta de destinatarios: "
									+ consultaEjecutarDestinatarios);
							LOGGER.info("SIGARNV-1232 GenerarDocumentosEnvio() -> Se envía a la dirección: " + result);

						} catch (BusinessSQLException e) {
							LOGGER.error(e);
							throw new BusinessException(
									"Error al ejecutar la consulta " + consulta.getDescripcion() + " " + e.getMessage(), e);
						} catch (Exception e) {
							LOGGER.warn("Error al ejejcutar la consulta" + e);
							throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(), e);
						}

						if (result != null && result.size() > 0 && result.get(0) != null) {
							LOGGER.info("Se han obtenido " + result.size() + " destinatarios");

							for (Map<String, Object> dest : result) {
								Set<String> keyList = dest.keySet();

								for (String key : keyList) {
									
									if (mapaClave == null) {
										mapaClave = new HashMap<String, String>();
									}
									
									if (dest.get(key) != null && mapaClave != null)
										mapaClave.put(key, dest.get(key).toString()); // Añadimos informacion adicional del
																						// destinatario.
								
								}

								String idioma = "";
								if (null != dest.get("IDIOMA")) {
									for (ConsultaItem consultaIdioma : consultasItemDest) {
										if (consultaIdioma.getIdioma().equals(dest.get("IDIOMA").toString())) {
											idioma = dest.get("IDIOMA").toString();
										}
									}
									if (idioma.equals("")) {
										idioma = usuario.getIdlenguaje();
									}

								} else {

									idioma = usuario.getIdlenguaje();
								}
								List<Document> listaDocumentos = new ArrayList<Document>();
								LOGGER.info("el idioma del destinatario es: " + idioma);
								if (idioma.equals(consulta.getIdioma())) {

									existenConsultas = Boolean.TRUE;
									ficherogenerado++;

									
									List<List<Map<String, Object>>> listaDatosExcel = new ArrayList<List<Map<String, Object>>>();
									String nombrePlantilla = "";
									Long idPlantillaGenerar = null;
									boolean esExcel = false;
									boolean esFO = false;

									LOGGER.info("Rendimiento inicio ejecucion consultas ");

									LOGGER.info("Rendimiento inicio ejecucion consultas condicionales");
									LOGGER.debug("Obtenemos las consultas condicional: " + plantilla.getIdPlantillas());
									List<ConsultaItem> consultasItemCondicional = _modPlantillaDocumentoConsultaExtendsMapper
											.selectConsultaPorObjetivo(Short.valueOf(dialogo.getIdInstitucion()),
													Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
													plantilla.getIdPlantillas(),
													SigaConstants.OBJETIVO.CONDICIONAL.getCodigo());

									if (consultasItemCondicional != null && consultasItemCondicional.size() > 0) {

										LOGGER.debug(
												"Número de consultas condicionales: " + consultasItemCondicional.size());

										for (ConsultaItem consultaCondicional : consultasItemCondicional) {

											consultaEjecutarCondicional = reemplazarConsultaConClaves(usuario, dialogo,
													consultaCondicional, mapaClave, esEnvio, null, null,false);

											idConsultaEjecutarCondicional = Long
													.parseLong(consultaCondicional.getIdConsulta());

											List<Map<String, Object>> resultCondicional;
											try {

												LOGGER.info("SIGARNV-1232 generarComunicacion() -> Ejecutamos la consulta "
														+ consultaEjecutarCondicional);
												resultCondicional = _consultasService.ejecutarConsultaConClavesLog(
														consultaEjecutarCondicional, usuario,
														Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
														Long.valueOf(consultaCondicional.getIdConsulta()),
														Short.valueOf(consultaCondicional.getIdInstitucion()),
														consultaCondicional.getDescripcion());
												LOGGER.info(
														"SIGARNV-1232 generarComunicacion() -> Ha sido ejecutada la consulta "
																+ consultaEjecutarCondicional);

											} catch (BusinessSQLException e) {
												LOGGER.error(e);
												throw new BusinessException("Error al ejecutar la consulta "
														+ consultaCondicional.getDescripcion() + " " + e.getMessage(), e);
											} catch (Exception e) {
												LOGGER.error(e);
												throw new BusinessException("Error al ejecutar la consulta "
														+ consultaCondicional.getDescripcion(), e);
											}

											if (resultCondicional != null && resultCondicional.size() > 0) {
												LOGGER.debug("Se cumple la consulta condicional del informe: "
														+ plantilla.getIdInforme());
												continua = true;
											} else {
												continua = false;
												LOGGER.info("La consulta condicional no ha devuelto resultados");
											}
										}
									} else {
										LOGGER.debug(
												"No hay consulta condicional para el informe: " + plantilla.getIdInforme());
										continua = true;
									}

									LOGGER.info("Rendimiento fin ejecucion consultas condicionales");
									if (continua) {

										// Obtenemos el nombre de la plantilla por cada destinatario
										if (plantilla.getIdPlantillas() != null) {

											LOGGER.debug("Generación del documento para la plantilla "
													+ plantilla.getIdPlantillas());

											ModPlantilladocumentoExample example = new ModPlantilladocumentoExample();
											List<Long> idValues = new ArrayList<Long>();
											String[] idPlantillas = plantilla.getIdPlantillas().split(",");

											if (idPlantillas != null && idPlantillas.length > 0) {
												for (String idPlantilla : idPlantillas) {
													idValues.add(Long.parseLong(idPlantilla));
												}
											}

											if (plantilla.getFormatoSalida() != null && Short.parseShort(plantilla
													.getFormatoSalida()) == SigaConstants.FORMATO_SALIDA.XLS.getCodigo()) {
												esExcel = true;
												LOGGER.debug("El formato de salida es Excel");
											}
											example.createCriteria().andIdplantilladocumentoIn(idValues)
													.andIdiomaEqualTo(idioma);

											List<ModPlantilladocumento> listaPlantilla = _modPlantilladocumentoMapper
													.selectByExample(example);

											if (listaPlantilla != null && listaPlantilla.size() == 1) {
												ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
												nombrePlantilla = plantillaDoc.getPlantilla();
												idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
												if (nombrePlantilla.lastIndexOf(".") > -1 && nombrePlantilla
														.substring(nombrePlantilla.lastIndexOf(".")).equals(".fo")) {
													esFO = true;
												}
											} else if (listaPlantilla != null && listaPlantilla.size() > 1) {
												LOGGER.error(
														"Exiten multiples plantillas asociada al informe en el idioma del usuario");
												throw new BusinessException(
														"Exiten multiples plantillas asociada al informe en el idioma del usuario");
											} else {
												LOGGER.error(
														"No hay plantilla asociada para el informe en el idioma del destinatario");
												throw new BusinessException(
														"No hay plantilla asociada para el informe en el idioma del destinatario");
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
															dialogo, consultaPlantilla, mapaClave, esEnvio, null, null,false);
													listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
															Long.parseLong(consultaPlantilla.getIdConsulta()),
															consultaPlantillaEnvio, usuario.getIdinstitucion(),
															Long.parseLong(consultaPlantilla.getIdObjetivo()),
															Short.parseShort(String.valueOf(usuario.getIdusuario())), null,
															Long.parseLong(plantilla.getIdInforme()),
															Long.parseLong(
																	modelosComunicacionItem.getIdModeloComunicacion()),
															Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
												}
											}

											desti = obtenerDatosDestinatario(dest);

										} else {
											esDestinatario = true;
										}

										numFicheros = 0;

										hDatosGenerales = new HashMap<String, Object>();
										hDatosGenerales.putAll(dest);

										hDatosFinal = new HashMap<String, Object>();

										LOGGER.debug("Obtenemos la consulta de multidocumento para la plantilla: "
												+ plantilla.getIdInforme());
										List<ConsultaItem> consultasItemMulti = _modPlantillaDocumentoConsultaExtendsMapper
												.selectConsultaPorObjetivo(usuario.getIdinstitucion(),
														Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
														plantilla.getIdPlantillas(),
														SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo());
										boolean consultasDestinatarioEjecutadas = consultasItemDest.size() > 0;
										if (consultasItemMulti != null && consultasItemMulti.size() > 0) {
											LOGGER.info("Rendimiento inicio ejecucion consultas multidocumento");
											for (ConsultaItem consultaMulti : consultasItemMulti) {
												String consultaEjecutarMulti = reemplazarConsultaConClaves(usuario, dialogo,
														consultaMulti, mapaClave, esEnvio, null, null,false);

												if (esEnvio) {
													// Guardamos la consulta multidocumento
													listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio,
															Long.parseLong(consultaMulti.getIdConsulta()),
															consultaEjecutarMulti, usuario.getIdinstitucion(),
															SigaConstants.OBJETIVO.MULTIDOCUMENTO.getCodigo(),
															Short.parseShort(String.valueOf(usuario.getIdusuario())),
															idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()),
															Long.parseLong(
																	modelosComunicacionItem.getIdModeloComunicacion()),
															Short.parseShort(String.valueOf(consulta.getIdInstitucion())));
												}

												List<Map<String, Object>> resultMulti;
												try {
													resultMulti = _consultasService.ejecutarConsultaConClavesLog(
															consultaEjecutarMulti, usuario,
															Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
															Long.valueOf(consultaMulti.getIdConsulta()),
															Short.valueOf(consultaMulti.getIdInstitucion()),
															consultaMulti.getDescripcion());
													LOGGER.info("Se ejecuta la consulta MULTI");
													if (resultMulti != null && resultMulti.size() > 0) {
														for (int k = 0; k < resultMulti.size(); k++) {
															// Por cada registro generamos un documento
															numFicheros++;
															generarDocumentoConDatos(usuario, dialogo,
																	modelosComunicacionItem, plantilla, idPlantillaGenerar,
																	listaConsultasEnvio, listaFicheros, listaDocumentos,
																	listaDatosExcel, hDatosFinal, hDatosGenerales,
																	resultMulti.get(k), mapaClave, campoSufijo, numFicheros,
																	rutaPlantillaClase, nombrePlantilla, esEnvio, esExcel,
																	esDestinatario, consultasDestinatarioEjecutadas, esFO,
																	null, desti, destinatarios, camposEnvio);
														}
													}

												} catch (BusinessSQLException e) {
													LOGGER.error(e);
													throw new BusinessException(
															"Error al ejecutar la consulta "
																	+ consultaMulti.getDescripcion() + " " + e.getMessage(),
															e);
												} catch (Exception e) {
													LOGGER.error(e);
													if (e instanceof BusinessException)
														throw new BusinessException(e.getMessage(), e);

													throw new BusinessException("Error al ejecutar la consulta "
															+ consultaMulti.getDescripcion(), e);
												}

											}
											LOGGER.info("Rendimiento fin ejecucion consultas multidocumento");
										} else {
											generarDocumentoConDatos(usuario, dialogo, modelosComunicacionItem, plantilla,
													idPlantillaGenerar, listaConsultasEnvio, listaFicheros, listaDocumentos,
													listaDatosExcel, hDatosFinal, hDatosGenerales, null, mapaClave,
													campoSufijo, numFicheros, rutaPlantillaClase, nombrePlantilla, esEnvio,
													esExcel, esDestinatario, consultasDestinatarioEjecutadas, esFO,
													listaKeyFiltros, desti, destinatarios, camposEnvio);
										}

									}

									LOGGER.info("Rendimiento fin tratamiento modelo de comunicacion: " + r);
									r++;
								}

							}
						} else {
							LOGGER.info("La consulta de destinatarios no ha devuelto resultados");
							if (ficherogenerado == 0 && (numeroSeleccionado + 1 == numeroSeleccionados)) {
								throw new BusinessException("La consulta de destinatarios no ha devuelto resultados");
							}

						}
					}
				} 

			}
		}
		if(plantillasSinFiltro != null && !plantillasSinFiltro.isEmpty() && !esEnvio){
			LOGGER.error("No hay consulta de destinatario para el modelo de comunicacion: "
					+ modelosComunicacionItem.getIdModeloComunicacion());

			if (esEnvio) // Si es envio, generamos error ya que es necesario el destinatario,
				throw new BusinessException("La consulta de destinatarios no ha devuelto resultados");

			ejecutaPlantillas(request,plantillasSinFiltro, modelosComunicacionItem, dialogo, usuario, mapaClave, esEnvio,
					listaConsultasEnvio, listaConsultasPlantillaEnvio, rutaPlantillaClase, campoSufijo,
					listaFicheros, ejecutarConsulta, destinatarios, listaKeyFiltros, camposEnvio);
			existenConsultas = Boolean.TRUE;
		}
	
		// FIN
		// List<ConsultaItem> consultasItemDest =
		// _modPlantillaDocumentoConsultaExtendsMapper
		// .selectConsultasDestinatarioByModelo(usuario.getIdinstitucion(),
		// Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()),
		// SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo(), idiomaUsu);

		LOGGER.info("Rendimiento fin ejecucion consultas destinatarios");

		if (!existenConsultas) {
			LOGGER.error("No hay plantilla asociada para el informe en el idioma del destinatario");
			if (ficherogenerado == 0 && (numeroSeleccionado + 1 == numeroSeleccionados)) {
				throw new BusinessException("No hay plantilla asociada para el informe en el idioma del destinatario");
			}
		}

		return ficherogenerado;
	}

	private void ejecutaPlantillas(HttpServletRequest request, List<PlantillaModeloDocumentoDTO> plantillas ,ModelosComunicacionItem modelosComunicacionItem, DialogoComunicacionItem dialogo,
			AdmUsuarios usuario, HashMap<String, String> mapaClave, boolean esEnvio, List<ConsultaEnvioItem> listaConsultasEnvio, List<ConsultaItem> listaConsultasPlantillaEnvio, String rutaPlantillaClase, String campoSufijo, List<DatosDocumentoItem> listaFicheros, boolean ejecutarConsulta, List<DestinatarioItem> destinatarios, List<List<String>> listaKeyFiltros, CamposPlantillaEnvio camposEnvio) throws Exception {

	
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		DestinatarioItem desti = null;
		
		
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
			boolean esFO = false;
			List<ModPlantilladocumento> listaPlantilla = null;
			//List<ModPlantilladocumento> listaPlantilla = new ArrayList<ModPlantilladocumento>();
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
				
				example.createCriteria().andIdplantilladocumentoIn(idValues).andIdiomaEqualTo(usuario.getIdlenguaje());
				listaPlantilla = _modPlantilladocumentoMapper.selectByExample(example);
				
				if(listaPlantilla != null && listaPlantilla.size() == 1){
					ModPlantilladocumento plantillaDoc = listaPlantilla.get(0);
					nombrePlantilla = plantillaDoc.getPlantilla();
					idPlantillaGenerar = plantillaDoc.getIdplantilladocumento();
					if (nombrePlantilla.lastIndexOf(".") != -1) {
						String auxNombre = nombrePlantilla.substring(nombrePlantilla.lastIndexOf("."));
						if(auxNombre.toLowerCase().equals(".fo")) {
							esFO = true;
						}
					}
				}else if(listaPlantilla != null && listaPlantilla.size() > 1){
					LOGGER.error("Exiten multiples plantillas asociada al informe en el idioma del usuario");
					throw new BusinessException("Exiten multiples plantillas asociada al informe en el idioma del usuario");
				}else{
					LOGGER.error("No hay plantilla asociada para el informe en el idioma del usuario");
					throw new BusinessException("No hay plantilla asociada para el informe en el idioma del usuario");
				}
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
						
						consultaEjecutarCondicional = reemplazarConsultaConClaves(usuario, dialogo, consulta, mapaClave, esEnvio, null,null,esExcel);
						
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
									mapaClave, esEnvio, null, null,esExcel);
	
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
														dialogo, consultaPlantilla, mapaClave, esEnvio, null, null,esExcel);
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
										
										desti =	obtenerDatosDestinatario(dest);
	
	
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
								throw new BusinessException("La consulta de destinatarios no ha devuelto resultados");
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
								mapaClave, esEnvio, null,null,esExcel);

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
												hDatosGenerales, resultMulti.get(k), mapaClave, campoSufijo, numFicheros, rutaPlantillaClase,
												nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas, esFO,null, desti, destinatarios, camposEnvio);
									}														
								}
									
							} catch (BusinessSQLException e) {
								LOGGER.error(e);
								throw new BusinessException("Error al ejecutar la consulta "
										+ consultaMulti.getDescripcion() + " " + e.getMessage(), e);
							} catch (BusinessException e) {
								LOGGER.error(e);
								throw new BusinessException(
										e.getMessage(), e);
							}catch (Exception e) {
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
								nombrePlantilla, esEnvio, esExcel, esDestinatario,consultasDestinatarioEjecutadas,esFO,listaKeyFiltros, desti, destinatarios, camposEnvio);
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
							if(mapaClave != null && (mapaClave.get(campo) != null || mapaClave.get(campo.toLowerCase()) != null)) {
								if(mapaClave.get(campo) != null)
									identificacion = mapaClave.get(campo);
								else
									identificacion = mapaClave.get(campo.toLowerCase());
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
	
	private String formatearConsulta(String consulta) {
	    // Eliminar espacios en blanco al inicio y al final
	    consulta = consulta.trim();
	    
	    // Verificar si la consulta termina con un comentario
	    if (consulta.endsWith("*/")) {
	        // Eliminar cualquier comentario al final de la consulta
	        int comentarioInicio = consulta.lastIndexOf("/*");
	        consulta = consulta.substring(0, comentarioInicio).trim();
	    } else if (consulta.endsWith(";")) {
	        // Eliminar el punto y coma final, si lo hay
	        consulta = consulta.substring(0, consulta.length() - 1).trim();
	    } else if (consulta.endsWith(")") && consulta.indexOf('(') == -1) {
	        // Si la consulta termina con un paréntesis pero no tiene uno de apertura correspondiente,
	        // elimina el paréntesis final
	        consulta = consulta.substring(0, consulta.length() - 1).trim();
	    }
	    
	    return consulta;
	}



	private String reemplazarConsultaConClaves(AdmUsuarios usuario, DialogoComunicacionItem dialogo, ConsultaItem consulta, HashMap<String, String> mapaClave, boolean esEnvio, ModelosComunicacionItem modelosComunicacionItem,List<List<String>> listaKeyFiltros, boolean esExcel) {
				String sentencia = null;
		//Buscamos la consulta con sus parametros dinamicos
		
		String idObjetivoRepuesto = consulta.getIdObjetivo();
		String regionRepuesto = consulta.getRegion();
		consulta = findConsultaItem(dialogo.getConsultas(), consulta);
		consulta.setIdObjetivo(idObjetivoRepuesto);
		consulta.setRegion(regionRepuesto);
		
		if(consulta != null){
			// Reemplazamos los datos insertados desde pantalla		
			try {
				sentencia = _consultasService.procesarEjecutarConsulta(usuario, consulta.getSentencia(), consulta.getCamposDinamicos(), true);

			// Remplazamos las claves de la query
			if(mapaClave != null && mapaClave.size() > 0) {
				for (Map.Entry<String, String> entry : mapaClave.entrySet()) {
					sentencia = sentencia.replace(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + entry.getKey().toUpperCase() + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, entry.getValue());
					LOGGER.info("Sentencia antes de meter máximo" + sentencia);
				}
			}	
			
//			 Formateamos la consulta antes de su ejecución
	        sentencia = formatearConsulta(sentencia);
	        
			LOGGER.info("*******QUERY ANTES DE FILTRO MAXIMO***********" + sentencia);
			if (consulta.getIdObjetivo() != null && !esExcel) {
				// añadirmos maximo por depende del idObjetivo
				if (consulta.getIdObjetivo().equals("1") || consulta.getIdObjetivo().equals("2")
						|| consulta.getIdObjetivo().equals("3")) {

					sentencia = addMaxQuery(sentencia, 10000);

				} else if (consulta.getRegion() != null && !consulta.getRegion().isEmpty()) {

					sentencia = addMaxQuery(sentencia, 10000);
				} else {

					sentencia = addMaxQuery(sentencia, 1);
				}

			}			
			
			LOGGER.info("-----QUERY CON FILTRO MAXIMO-----------" + sentencia);	
			
			if(modelosComunicacionItem != null && modelosComunicacionItem.getInformeUnico() != null && modelosComunicacionItem.getInformeUnico().equals("1")) {
				sentencia = _consultasService.procesarEjecutarConsultaImprimir(usuario, sentencia, dialogo.getSentenciaImprimir(), listaKeyFiltros);
			}
				
			} catch (ParseException e) {
				LOGGER.error("Error al ejecutar la consulta con id " + consulta.getIdConsulta(), e);
				throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(), e);
			}
		}else{
			LOGGER.error("No se ha encontrado la consulta");
			throw new BusinessException("No se ha encontrado la consulta");
		}		
		
		return sentencia;
	}
	
	private String addMaxQuery(String consulta, int maximo) {
		
		String consultaModificada =  consulta + " OFFSET 0 ROWS FETCH NEXT "+ maximo +" ROWS ONLY";
 		
		return consultaModificada;
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
					generarComunicacion.setDescripcionDefecto(dialogo.getDescripcionDefecto());
					
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
	

	private void insertarConsultasEnvio(AdmUsuarios usuario, Short idInstitucion,
			GenerarComunicacionItem generarComunicacion) {
		// Hay que generar un envio por cada modelo y cada destinatario

		try {
			if (generarComunicacion != null && generarComunicacion.getListaModelosEnvio() != null
					&& generarComunicacion.getListaModelosEnvio().size() > 0) {
				// Por cada modelo y por cada destinatario se genera un envio
				for (ModelosEnvioItem modeloEnvio : generarComunicacion.getListaModelosEnvio()) {
					if (modeloEnvio != null && modeloEnvio.getListaDatosEnvio() != null) {

						for (DatosEnvioDTO listaConsultasEnvio : modeloEnvio.getListaDatosEnvio()) {

							List<DestinatarioItem> listDestinatarios =  listaConsultasEnvio.getDestinatarios().stream().distinct().collect(Collectors.toList());
									

							for (DestinatarioItem dest : listDestinatarios) {

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
								ModModelocomunicacion modelo = _modModeloComunicacionMapper
										.selectByPrimaryKey(modeloEnvio.getIdModeloComunicacion());
								String descripcion = envio.getIdenvio() + "--" + modelo.getNombre();
								
								if(dest.getCamposEnvio()!= null && dest.getCamposEnvio().getIdentificador() != null && !dest.getCamposEnvio().getIdentificador().isEmpty() ) {
									descripcion = dest.getCamposEnvio().getIdentificador();
								}
								if(generarComunicacion.getDescripcionDefecto() != null && !generarComunicacion.getDescripcionDefecto().isEmpty())
									descripcion =  generarComunicacion.getDescripcionDefecto() + " " + descripcion;
								
								envio.setDescripcion(descripcion);

								_envEnviosMapper.updateByPrimaryKey(envio);
								
								//Insertamos en SCS_Comunicaciones
								insertComunicaciones(envio.getIdenvio(),dest.getCamposEnvio());

								if (insert > 0) {

									EnvHistoricoestadoenvio historico = new EnvHistoricoestadoenvio();
									historico.setIdenvio(envio.getIdenvio());
									historico.setIdinstitucion(usuario.getIdinstitucion());
									historico.setFechamodificacion(new Date());
									historico.setFechaestado(new Date());
									historico.setUsumodificacion(usuario.getIdusuario());
									Short idEstado = 4;
									historico.setIdestado(idEstado);
									_envHistoricoestadoenvioMapper.insert(historico);

									// Insertamos el envio programado
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

									// Insertamos el nuevo asunto y cuerpo del envio
									String strAsunto = null;
									String strCuerpo = null;
									
									if(dest.getCamposEnvio()!= null) {
										if(dest.getCamposEnvio().getAsunto() != null)
											strAsunto = dest.getCamposEnvio().getAsunto();
										if(dest.getCamposEnvio().getCuerpo() != null)
											strCuerpo = dest.getCamposEnvio().getCuerpo();
									}
									
									EnvCamposenvios envCamposEnvio = new EnvCamposenvios();
									envCamposEnvio.setFechamodificacion(new Date());
									envCamposEnvio.setUsumodificacion(usuario.getIdusuario());
									envCamposEnvio.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_ASUNTO));
									envCamposEnvio.setIdenvio(envio.getIdenvio());
									envCamposEnvio.setIdinstitucion(usuario.getIdinstitucion());
									envCamposEnvio.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);
									envCamposEnvio.setValor(strAsunto);							

									_envCamposenviosMapper.insert(envCamposEnvio);

									envCamposEnvio = new EnvCamposenvios();
									envCamposEnvio.setFechamodificacion(new Date());
									envCamposEnvio.setUsumodificacion(usuario.getIdusuario());
									envCamposEnvio.setIdcampo(Short.parseShort(SigaConstants.ID_CAMPO_CUERPO));
									envCamposEnvio.setIdenvio(envio.getIdenvio());
									envCamposEnvio.setIdinstitucion(usuario.getIdinstitucion());
									envCamposEnvio.setTipocampo(SigaConstants.ID_TIPO_CAMPO_EMAIL);
									envCamposEnvio.setValor(strCuerpo);	

									_envCamposenviosMapper.insert(envCamposEnvio);

									// INSERTAMOS DESTINATARIO
									EnvDestinatarios destinatario = new EnvDestinatarios();
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

								List<String> nombresFicheros = new ArrayList<String>();

								for (ConsultaEnvioItem consultaEnvio : listaConsultasEnvio.getConsultas()) {

									if (consultaEnvio.getDestinatario().equals(dest)) {
										// Insertamos las consultas del envio
										EnvConsultasenvio consultaEnvioEntity = new EnvConsultasenvio();
										consultaEnvioEntity.setConsulta(consultaEnvio.getConsulta());
										consultaEnvioEntity.setFechamodificacion(new Date());
										consultaEnvioEntity.setIdconsulta(consultaEnvio.getIdConsulta());
										consultaEnvioEntity.setIdenvio(envio.getIdenvio());
										consultaEnvioEntity.setIdinstitucion(consultaEnvio.getIdInstitucion());
										consultaEnvioEntity.setIdobjetivo(consultaEnvio.getIdObjetivo());
										if (null != consultaEnvio.getUsuModificacion()) {
											consultaEnvioEntity.setUsumodificacion(
													Integer.valueOf((consultaEnvio.getUsuModificacion().toString())));
										}
										consultaEnvioEntity.setIdplantilladocumento(consultaEnvio.getIdPlantillaDoc());
										consultaEnvioEntity.setIdinforme(consultaEnvio.getIdInforme());
										consultaEnvioEntity
												.setIdmodelocomunicacion(consultaEnvio.getIdModeloComunicacion());
										consultaEnvioEntity.setSufijo(consultaEnvio.getSufijo());
										consultaEnvioEntity
												.setIdinstitucionconsulta(consultaEnvio.getIdInstitucionConsulta());
										_envConsultasenvioMapper.insert(consultaEnvioEntity);

										// Insertamos los documentos asociados al envio

										// Si ya se ha insertado lo omitimos
										if (consultaEnvio.getNombreFichero() != null
												&& !nombresFicheros.contains(consultaEnvio.getNombreFichero())) {
											
											String pathNuevo = _enviosMasivosService.getPathFicheroEnvioMasivo(envio.getIdinstitucion(), envio.getIdenvio(),envio);
											moverFichero(consultaEnvio.getPathFichero(),pathNuevo,consultaEnvio.getNombreFichero());
											
											EnvDocumentos documento = new EnvDocumentos();
											documento.setIdenvio(envio.getIdenvio());
											documento.setIdinstitucion(usuario.getIdinstitucion());
											documento.setFechamodificacion(new Date());
											documento.setUsumodificacion(usuario.getIdusuario());
											documento.setDescripcion(consultaEnvio.getNombreFichero());
											documento.setPathdocumento(consultaEnvio.getNombreFichero());
											_envDocumentosMapper.insert(documento);
											nombresFicheros.add(consultaEnvio.getNombreFichero());
										}
										
										
										
									} // Fin con dest
								} // for(ConsultaEnvioItem consultaEnvio:

							}

						} // FIN DatosEnvioDTO listaConsultasEnvio :

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error al generar el envío", e);
			throw e;
		}
	}
	
	private void insertComunicaciones(Long idenvio, CamposPlantillaEnvio camposEnvio) {
		// TODO Auto-generated method stub
		EjgItem ejg = camposEnvio.getEjg();
		DesignaItem des = camposEnvio.getDesigna();
		if(ejg != null || des != null ) {
			ScsComunicaciones comunicacionInsert = new ScsComunicaciones();
			NewIdDTO idNew = scsComunicacionesExtendsMapper.selectMaxIdLinea();
			
			comunicacionInsert.setIdcomunicacion(Long.parseLong(idNew.getNewId()));
			comunicacionInsert.setIdenviosalida(idenvio);
			if(des != null) {
				comunicacionInsert.setDesignaanio( (short)des.getAno() ); 
				comunicacionInsert.setDesignanumero((long)des.getNumero()); 
				comunicacionInsert.setDesignaidturno(des.getIdTurno());
			}else {
				comunicacionInsert.setEjganio(Short.parseShort(ejg.getAnnio()));
				comunicacionInsert.setEjgnumero(Long.parseLong(ejg.getNumero()));
				comunicacionInsert.setEjgidtipo(Short.parseShort(ejg.getTipoEJG()));	
			}
			comunicacionInsert.setIdinstitucion(Short.parseShort(camposEnvio.getIdInstitucion()));
			
			//comunicacionInsert
			int insertB = scsComunicacionesExtendsMapper.insert(comunicacionInsert);
			LOGGER.info("insertComunicaciones() --> Filas Insertadas: " + insertB);
		}
	
		
	}



	private void moverFichero(String rutaVieja, String nuevaRuta, String nombreFichero) {
		File archivoActual = new File(rutaVieja,nombreFichero);
		File archivoNuevo = new File(nuevaRuta,nombreFichero);
		
		if(!archivoNuevo.getParentFile().exists())
			archivoNuevo.getParentFile().mkdirs();
		
		
		try {
			archivoActual.renameTo(archivoNuevo);
			LOGGER.info("moverFichero() --> Desde: " + archivoActual.getAbsolutePath() + " - hasta : " + archivoNuevo.getAbsolutePath());
			
		}catch(Exception e) {
			LOGGER.error(e.getStackTrace());
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
				idModeloComunicacion = idModeloComunicacionEnvio;
				List<ClaseComunicacionItem> modClaseItem = _modClasecomunicacionesExtendsMapper.selectClaseComunicacionModulo(String.valueOf(idModeloComunicacionEnvio));
				if(modClaseItem != null && modClaseItem.size() > 0) {
					ClaseComunicacionItem claseItem = modClaseItem.get(0);
					directorioPlantillaClase = getRutaModeloByClaseModelo(idModeloComunicacionEnvio.toString(), claseItem.getIdClaseComunicacion());
				}
			}
		}else {
			LOGGER.error("No se ha encontrado el envío:" + idEnvio);
			throw new BusinessException("No se ha encontrado el envío:" + idEnvio);
		}
		
		
		//if(directorioPlantillaClase == null || "".equals(directorioPlantillaClase)) {
		//	directorioPlantillaClase = SigaConstants.rutaPlantillaSinClase;
		//}
		
		//directorioPlantillaClase = directorioPlantillaClase.replaceAll(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION + SigaConstants.REPLACECHAR_PREFIJO_SUFIJO, idInstitucion);
		
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
					
					//Destinatarios obtenidos por query, pero se pasa a obtenerlos desde BD
					example = new EnvConsultasenvioExample();
					example.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdplantilladocumentoEqualTo(Long.parseLong(idPlantilla)).andIdobjetivoEqualTo(SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo());
					List<EnvConsultasenvio> listaConsultasDest = _envConsultasenvioMapper.selectByExampleWithBLOBs(example);
					for(EnvConsultasenvio consultaDest: listaConsultasDest){
						idModeloComunicacion = consultaDest.getIdmodelocomunicacion();
						// Ejecutamos la consulta
						
						String sentencia = consultaDest.getConsulta();
						
						sentencia = _consultasService.quitarEtiquetas(sentencia.toUpperCase());
						
						EnvDestinatariosExample exampleDests = new EnvDestinatariosExample();
						exampleDests.createCriteria().andIdenvioEqualTo(Long.parseLong(idEnvio)).andIdinstitucionEqualTo(Short.parseShort(idInstitucion));
						
						List<EnvDestinatarios> destIndv = _envDestinatariosMapper.selectByExample(exampleDests);

						
						
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
									if(dest.get("IDPERSONA").toString().equals(destIndv.get(0).getIdpersona().toString()))
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
									
									
									File filePlantilla = new File(rutaPlantilla + nombrePlantilla);
									
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
						
						if(plantillas == null || plantillas.size() == 0) {
							throw new BusinessException("No hay plantillas para el envio " + idEnvio + " y plantilla: " + idPlantilla );
							
						}
					
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
	
	private String getPathFicheroEnvioMasivo(Short idInstitucion, Long idEnvio) {
		String pathFichero = null;

		GenParametrosKey genParametrosKey = new GenParametrosKey();
		genParametrosKey.setIdinstitucion(SigaConstants.IDINSTITUCION_0_SHORT);
		genParametrosKey.setModulo(SigaConstants.MODULO_ENV);
		genParametrosKey.setParametro(GEN_PARAMETROS.PATH_DOCUMENTOSADJUNTOS.name());

		GenParametros genParametros = _genParametrosMapper.selectByPrimaryKey(genParametrosKey);

		if (genParametros == null || genParametros.getValor() == null || genParametros.getValor().trim().equals("")) {
			String error = "La ruta de ficheros de plantilla no está configurada correctamente";
			LOGGER.error(error);
			throw new BusinessException(error);
		}

		Calendar cal = Calendar.getInstance();
		// seteamos la fecha de creación del envío
		//cal.setTime(envEnvios.getFecha());

		pathFichero = genParametros.getValor().trim() + SigaConstants.pathSeparator + String.valueOf(idInstitucion)
				+ SigaConstants.pathSeparator + cal.get(Calendar.YEAR) + SigaConstants.pathSeparator
				+ (cal.get(Calendar.MONTH) + 1) + SigaConstants.pathSeparator + idEnvio;

		return pathFichero;
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
	
	private DestinatarioItem obtenerDatosDestinatario(Map<String,Object> dest) {
		
		DestinatarioItem destinatario = new DestinatarioItem();
		
		Object idPersona = dest.get(SigaConstants.ALIASIDPERSONA.trim());
		Object correo = dest.get(SigaConstants.ALIASCORREO.trim());
		Object movil = dest.get(SigaConstants.ALIASMOVIL.trim());
		Object domicilio = dest.get(SigaConstants.ALIASDOMICILIO.trim());
		Object codigoPostal = dest.get(SigaConstants.ALIASCODIGOPOSTAL.trim());
		Object idPais = dest.get(SigaConstants.ALIASIDPAIS.trim());
		Object idPoblacion = dest.get(SigaConstants.ALIASIDPOBLACION.trim());
		Object idProvincia= dest.get(SigaConstants.ALIASIDPROVINCIA.trim());
		Object poblacionExtranjera= dest.get(SigaConstants.ALIASPOBLACIONEXTRANJERA.trim());
		Object nombre = dest.get(SigaConstants.ALIASNOMBRE.trim());
		Object apellidos1 = dest.get(SigaConstants.ALIASAPELLIDOS1.trim());
		Object apellidos2 = dest.get(SigaConstants.ALIASAPELLIDOS2.trim());
		Object nifCif = dest.get(SigaConstants.ALIASNIFCIF.trim());
		
		if(idPersona != null)
			destinatario.setIdPersona(idPersona.toString());
		
		if(nombre != null)
			destinatario.setNombre(nombre.toString());
		
		if(apellidos1 != null)
			destinatario.setApellidos1(apellidos1.toString());
		
		if(apellidos2 != null)
			destinatario.setApellidos2(apellidos2.toString());
		
		if(nifCif != null)
			destinatario.setNIFCIF(nifCif.toString());
		
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
		LOGGER.info("LOG Destinatario: " + dest.toString());
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
	
	private void generarDocumentoConDatos(AdmUsuarios usuario, DialogoComunicacionItem dialogo, ModelosComunicacionItem modelosComunicacionItem, PlantillaModeloDocumentoDTO plantilla, Long idPlantillaGenerar, List<ConsultaEnvioItem> listaConsultasEnvio, List<DatosDocumentoItem> listaFicheros, List<Document> listaDocumentos, List<List<Map<String,Object>>> listaDatosExcel, HashMap<String,Object> hDatosFinal, HashMap<String,Object> hDatosGenerales, 
			Map<String, Object> resultMulti, HashMap<String, String> mapaClave, String campoSufijo, int numFicheros, String rutaPlantillaClase, String nombrePlantilla, boolean esEnvio, boolean esExcel, boolean esDestinatario, boolean consultasDestinatarioEjecutadas, boolean esFO, List<List<String>> listaKeyFiltros, DestinatarioItem destinatario , List<DestinatarioItem> destinatarios, CamposPlantillaEnvio camposEnvio) {
		
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
		List<Map<String, Object>> resultDatos = null;
		for(ConsultaItem consultaDatosDatos:consultasItemDatos){
			consultasItemFinal.add(consultaDatosDatos);
		}		
		ArrayList<String> nombresConsultasDatos = new ArrayList<String>();	
		
		for(ConsultaItem consultaDatos:consultasItemFinal){																			
			
			String consultaEjecutarDatos = reemplazarConsultaConClaves(usuario, dialogo, consultaDatos, mapaClave, esEnvio, modelosComunicacionItem, listaKeyFiltros, esExcel);
			String nombreConsulta = consultaDatos.getDescripcion();
			if(esEnvio){
				//Guardamos la consulta datos															
				listaConsultasEnvio = guardarDatosConsultas(listaConsultasEnvio, Long.parseLong(consultaDatos.getIdConsulta()),consultaEjecutarDatos,usuario.getIdinstitucion(), SigaConstants.OBJETIVO.DATOS.getCodigo(), Short.parseShort(String.valueOf(usuario.getIdusuario())), idPlantillaGenerar, Long.parseLong(plantilla.getIdInforme()), Long.parseLong(modelosComunicacionItem.getIdModeloComunicacion()), Short.parseShort(String.valueOf(consultaDatos.getIdInstitucion())));
			}
			
			resultDatos = null;
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
		

		
			LOGGER.info("Rendimiento inicio generacion del documento con datos" );
			LOGGER.info("Generamos el documento");																
			DatosDocumentoItem docGenerado = null;
			
			if(esExcel) {
				try {
					docGenerado = _generacionDocService.generarExcel(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, listaDatosExcel, nombresConsultasDatos);
				} catch (Exception e) {
					LOGGER.error("Error al generar el fichero excel ", e);
					throw new BusinessException("No se ha podido generar el fichero Excel", e);
				}
			}else if(esFO){
				try {
					//if(modelosComunicacionItem.getIdClaseComunicacion().equals("6")) { //Carta de Oficio
						//docGenerado = _generacionDocService.generarFOTurnos(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, resultDatos);
					//}else {
						docGenerado = _generacionDocService.generarFO(rutaPlantilla + nombrePlantilla, rutaTmp, nombreFicheroSalida, hDatosFinal);
					//}
				} catch (Exception e) {
					LOGGER.error("Error al generar el fichero excel ", e);
					throw new BusinessException("No se ha podido generar el fichero Excel", e);
				}
			}else{
				existePlantilla(filePlantilla);
				
				try {
					doc = new Document(rutaPlantilla + nombrePlantilla);
				
					if(modelosComunicacionItem.getIdClaseComunicacion().equals("9")) { //Carta de Acreditación de Oficio
						hDatosFinal = completarDatosAcreditación(hDatosFinal,mapaClave);
						
					}
					
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
					
					if(e instanceof BusinessException) {
						throw new BusinessException(e.getMessage(), e);
					}
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
			
			if(esEnvio) {
				String envioCuerpo = null;
				String envioAsunto = null;
				String valorIdentificador = null;
				EjgItem ejg = null;
				DesignaItem des = null;
				
				String idInstitucion = dialogo.getIdInstitucion().toString();
				  
				if(camposEnvio.getAsunto() != null) {
					envioAsunto = sustituirEtiquetas(idInstitucion, camposEnvio.getAsunto(), destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, hDatosFinal);
					envioAsunto = sustituirEtiquetas(idInstitucion, envioAsunto, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, hDatosFinal);
				}
				if(camposEnvio.getCuerpo() != null) {
					envioCuerpo = sustituirEtiquetas(idInstitucion, camposEnvio.getCuerpo(), destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, hDatosFinal);
					envioCuerpo = sustituirEtiquetas(idInstitucion, envioCuerpo, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, hDatosFinal);
				}
				
				if (mapaClave.containsKey("identificador")) {
			        valorIdentificador = mapaClave.get("identificador");
			        switch (valorIdentificador.charAt(0)) {
			            case 'E':
			                ejg = new EjgItem();
			                ejg.setAnnio(mapaClave.get("anio"));
			                ejg.setNumero(mapaClave.get("num"));
			                ejg.setTipoEJG(mapaClave.get("idtipoejg"));
			                break;
			            case 'D':
			                des = new DesignaItem();
			                des.setAno(Integer.parseInt(mapaClave.get("anio")));
			                des.setNumero(Integer.parseInt(mapaClave.get("num")));
			                des.setIdTurno(Integer.parseInt(mapaClave.get("idturno")));
			                break;
			        }
			    }
				

				//Cogemos todas las consultas y le metemos el nombre del fichero
				if(listaConsultasEnvio != null && listaConsultasEnvio.size() > 0){
					CamposPlantillaEnvio envCampos = new CamposPlantillaEnvio();
					DestinatarioItem dest = new DestinatarioItem(destinatario);
					
					if(des != null) envCampos.setDesigna(des);
						else envCampos.setEjg(ejg);
					
					envCampos.setIdentificador(valorIdentificador);
					envCampos.setIdInstitucion(idInstitucion);
					envCampos.setAsunto(envioAsunto);
					envCampos.setCuerpo(envioCuerpo);
					dest.setCamposEnvio(envCampos);
					destinatarios.add(dest);
					for(ConsultaEnvioItem consultaEnvio : listaConsultasEnvio){
						if(consultaEnvio.getDestinatario() == null) {
							consultaEnvio.setSufijo(campoSufijoReplaced);
							consultaEnvio.setPathFichero(rutaTmp);
							consultaEnvio.setNombreFichero(nombreFicheroSalida);
							LOGGER.info(destinatario.getNombre());
							consultaEnvio.setDestinatario(dest);
						}

					}
				}	
		//		rutaPlantilla = getPathFicheroEnvioMasivo(dialogo.getIdInstitucion());
			}
			
			LOGGER.info("Rendimiento fin generacion del documento con datos" );
																																	
	}

	private String sustituirEtiquetas(String idInstitucion, String cuerpoEnvio, DestinatarioItem destinatario, String marcaInicioFin, HashMap<String, Object> hDatosFinal ){
		  StringBuilder cuerpoEnvioBuilder = new StringBuilder(cuerpoEnvio);

		    for(Map.Entry<String, Object> entry : hDatosFinal.entrySet()) {
		        Object value = entry.getValue();

		        if(value instanceof HashMap) {
		            HashMap<String, Object> keyyVal = (HashMap<String, Object>) value;

		            for(Map.Entry<String, Object> entryVal : keyyVal.entrySet()) {
		                if(entryVal == null)
		                    continue;

		                String etiqueta = marcaInicioFin + entryVal.getKey() + marcaInicioFin;
		                String replacement = (entryVal.getValue() == null) ? "" : entryVal.getValue().toString();
		                replaceAll(cuerpoEnvioBuilder, etiqueta, replacement);
		            }
		        }
		    }
     
		    //Obtenemos el tratamiento del destinatario
		    String etiqueta = SigaConstants.ETIQUETA_DEST_TRATAMIENTO;
		    replaceAll(cuerpoEnvioBuilder, marcaInicioFin + etiqueta + marcaInicioFin, getTratamientoDestinatario(idInstitucion, destinatario));

		    etiqueta = SigaConstants.ETIQUETA_FECHAACTUAL;
		    Date date = Calendar.getInstance().getTime();
		    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = dateFormat.format(date);
		    replaceAll(cuerpoEnvioBuilder, marcaInicioFin + etiqueta + marcaInicioFin, strDate);

		    return cuerpoEnvioBuilder.toString();

    }
	
	private static void replaceAll(StringBuilder sb, String from, String to) {
	    int index = sb.indexOf(from);
	    if(to == null) {
	    	to = "";
	    }
	    while(index != -1) {
	    	sb.replace(index, index + from.length(), to);
	    	index += to.length(); 
	        index = sb.indexOf(from, index);
	    }
	}
	
	  private String getTratamientoDestinatario(String idInstitucion, DestinatarioItem destinatario) {
	        String tratamiento = "";
	        int idIdioma = 1;
	        
	        if(destinatario.getTratamiento() == null){
	            List<StringDTO> result = _cenClienteExtendsMapper.getTratamiento(idInstitucion, destinatario.getIdPersona(), idIdioma);            
	            if(result != null && result.size() > 0 && result.get(0) != null) {
	                tratamiento = result.get(0).getValor();
	                destinatario.setTratamiento(tratamiento);
	            }
	        }        
	        
	        return destinatario.getTratamiento();
	    }

	  
	private HashMap<String, Object> completarDatosAcreditación(HashMap<String, Object> hDatosFinal, HashMap<String, String> mapaClave) throws Exception {
		HashMap<String, Object> total = new HashMap<String, Object>();
		String longitudNumEjg = "5";
		String idInstitucion = mapaClave.get("idInstitucion");
		String idTurno = mapaClave.get("idTurno");
		String numero = mapaClave.get("num");
		String anio = mapaClave.get("anio");
		//String numeroAsunto = mapaClave.get("numeroAsunto");
		
		if (hDatosFinal.size() > 0) {
			HashMap<String, Object> registroGeneral =  (HashMap<String, Object>) hDatosFinal.get("row");
			//Parseamos los ejg
			String expedientes= (String)registroGeneral.get("DESIGNA_LISTA_EXPEDIENTES");
			if(expedientes != null && !"".equalsIgnoreCase(expedientes)){
				if (expedientes != null && expedientes.indexOf("##") > -1) {
					String[] ejgs = expedientes.split(",");
					String salida = "";
					Boolean favorable;
					String idTipoRatificacion="";
					String fechaResolucionCAJG ="";
					for (String ejg:ejgs) {
						favorable = Boolean.FALSE;
						String[] ejgDoc = ejg.split("##");	
						//Obtenemos los datos del ejg para comprobar si tiene resolución favorable y sólo sacar aquellos que así lo son.
						 idTipoRatificacion = ejgDoc[6];
						 fechaResolucionCAJG = ejgDoc[7];
						if((fechaResolucionCAJG != null && !"".equalsIgnoreCase(fechaResolucionCAJG) && idTipoRatificacion!=null && !"".equalsIgnoreCase(idTipoRatificacion) 
								&& (idTipoRatificacion.equalsIgnoreCase("1") || idTipoRatificacion.equalsIgnoreCase("2")|| idTipoRatificacion.equalsIgnoreCase("8")
										|| idTipoRatificacion.equalsIgnoreCase("10")|| idTipoRatificacion.equalsIgnoreCase("9")|| idTipoRatificacion.equalsIgnoreCase("11"))) 
										|| (" ".equalsIgnoreCase(fechaResolucionCAJG) && " ".equalsIgnoreCase(idTipoRatificacion))){
							//Comprobamos el tipo de resolución
							
								favorable=Boolean.TRUE;
							
						}
						if(favorable)
							salida+=", " + ejgDoc[0].trim();				
					}
					expedientes=salida;
					if (expedientes.length() > 2){
						expedientes = expedientes.substring(1);
					}
					registroGeneral.put("DESIGNA_LISTA_EXPEDIENTES", expedientes);
				}
			}
			total.putAll(registroGeneral);
			
			BigDecimal idPersonaAux  = (BigDecimal) registroGeneral.get("DESIGNA_LETRADO");
			String idPersona = idPersonaAux.toString();
			String nombre_designa_letrado = obtenerNombreApellidos(idPersona);
			
			idPersonaAux  = (BigDecimal) registroGeneral.get("ACTUACION_ID_LETRADO");
			String idPersonaActuacion  = idPersonaAux.toString();
			String nombre_dest = obtenerNombreApellidos(idPersonaActuacion);
			
			
			List<DatosDireccionLetradoOficio> designaLetrado = cenDireccionesMapper.getDireccionLetradoSalidaOficio(idPersona, idInstitucion);
			DatosDireccionLetradoOficio registro2=  designaLetrado.get(0);
			total.put("DOMICILIO_DESPACHO_LETRADO", registro2.getDomicilio_letrado());
			total.put("CP_DESPACHO_LETRADO", registro2.getCp_letrado());
			total.put("IDPOBLACION_DESPACHO_LETRADO", registro2.getIdpoblacion_letrado());
			total.put("POBLACION_DESPACHO_LETRADO", registro2.getPoblacion_letrado());
			total.put("IDPROVINCIA_DESPACHO_LETRADO", registro2.getIdprovincia_letrado());
			total.put("PROVINCIA_DESPACHO_LETRADO", registro2.getProvincia_letrado());
			total.put("TELEFONO1_DESPACHO_LETRADO", registro2.getTelefono1_letrado());
			total.put("TELEFONO2_DESPACHO_LETRADO", registro2.getTelefono2_letrado());
			total.put("FAX1_DESPACHO_LETRADO", registro2.getFax1_letrado());
			total.put("FAX2_DESPACHO_LETRADO", registro2.getFax2_letrado());
			total.put("EMAIL_DESPACHO_LETRADO", registro2.getEmail_letrado());
			total.put("MOVIL_DESPACHO_LETRADO", registro2.getMovil_letrado());
			
			List<DatosDireccionLetradoOficio>  designaLetradoGuardia = cenDireccionesMapper.getDireccionPersonalSalidaOficio(idPersona, idInstitucion);
			DatosDireccionLetradoOficio  registro3=  designaLetradoGuardia.get(0);
			total.put("DOMICILIO_GUARDIA_LETRADO", registro3.getDomicilio_letrado());
			total.put("CP_GUARDIA_LETRADO", registro3.getCp_letrado());
			total.put("IDPOBLACION_GUARDIA_LETRADO", registro3.getIdpoblacion_letrado());
			total.put("POBLACION_GUARDIA_LETRADO", registro3.getPoblacion_letrado());
			total.put("IDPROVINCIA_GUARDIA_LETRADO", registro3.getIdprovincia_letrado());
			total.put("PROVINCIA_GUARDIA_LETRADO", registro3.getProvincia_letrado());
			total.put("TELEFONO1_GUARDIA_LETRADO", registro3.getTelefono1_letrado());
			total.put("TELEFONO2_GUARDIA_LETRADO", registro3.getTelefono2_letrado());
			total.put("FAX1_GUARDIA_LETRADO", registro3.getFax1_letrado());
			total.put("FAX2_GUARDIA_LETRADO", registro3.getFax2_letrado());
			total.put("EMAIL_GUARDIA_LETRADO", registro3.getEmail_letrado());
			total.put("MOVIL_GUARDIA_LETRADO", registro3.getMovil_letrado());
			
			HashMap<String, Object> letrado =  obtenerLetrado(registro2, registro3);
			letrado.put("NOMBRE_LETRADO", nombre_designa_letrado);
			
			// Obtenemos el numero de colegiado
			CenColegiadoKey colegiadoKey = new CenColegiadoKey();
			colegiadoKey.setIdinstitucion(Short.valueOf(idInstitucion));
			colegiadoKey.setIdpersona(Long.valueOf(idPersona));
			CenColegiado cenColegiado = cenColegiadoMapper.selectByPrimaryKey(colegiadoKey);
			
			if (cenColegiado!=null) {
				if(cenColegiado.getComunitario()!= null && cenColegiado.getComunitario().equals("1")) {
					letrado.put("NCOLEGIADO_LETRADO", cenColegiado.getNcomunitario());
				}else if(cenColegiado.getNcolegiado()!= null)
					letrado.put("NCOLEGIADO_LETRADO", cenColegiado.getNcolegiado());
			}			    
			total.putAll(letrado);
			
			
			//Destinatario
			List<DatosDireccionLetradoOficio> destinatario = cenDireccionesMapper.getDireccionLetradoSalidaOficio(idPersonaActuacion, idInstitucion);
			DatosDireccionLetradoOficio registroDestinatario=  destinatario.get(0);
			
			List<DatosDireccionLetradoOficio>  destinatarioGuardia = cenDireccionesMapper.getDireccionPersonalSalidaOficio(idPersonaActuacion, idInstitucion);
			DatosDireccionLetradoOficio  registroDestinatarioGuardia=  destinatarioGuardia.get(0);
			
			HashMap<String, Object> infoDestinatario = obtenerDestinatario(registroDestinatario, registroDestinatarioGuardia);
			infoDestinatario.put("NOMBRE_DEST", nombre_dest);
			total.putAll(infoDestinatario);
			
			
			List<DatosCartaAcreditacionItem> regionDefendido = designacionesMapper.getDefendidosDesigna(idInstitucion,numero,idTurno, anio,"","", longitudNumEjg);
			hDatosFinal = new HashMap<String,Object>();
			hDatosFinal.put("row", total);
			if (regionDefendido != null && !regionDefendido.isEmpty()) {
				HashMap<String,Object> mRegionDefendido = new HashMap<String,Object>();
				mRegionDefendido.put("IDINSTITUCION",regionDefendido.get(0).getIdinstitucion());
				mRegionDefendido.put("IDTURNO",regionDefendido.get(0).getIdturno());
				mRegionDefendido.put("ANIO",regionDefendido.get(0).getAnio());
				mRegionDefendido.put("NUMERO",regionDefendido.get(0).getNumero());
				mRegionDefendido.put("IDPERSONAINTERESADO",regionDefendido.get(0).getIdpersonainteresado());
				mRegionDefendido.put("NOMBRE_DEFENDIDO",regionDefendido.get(0).getNombre_defendido());
				mRegionDefendido.put("DOMICILIO_DEFENDIDO",regionDefendido.get(0).getDomicilio_defendido());
				mRegionDefendido.put("CP_DEFENDIDO",regionDefendido.get(0).getCp_defendido());
				mRegionDefendido.put("POBLACION_DEFENDIDO",regionDefendido.get(0).getPoblacion_defendido());
				mRegionDefendido.put("PROVINCIA_DEFENDIDO",regionDefendido.get(0).getProvincia_defendido());
				mRegionDefendido.put("NOMBRE_PAIS",regionDefendido.get(0).getNombre_pais());
				mRegionDefendido.put("OBS_DEFENDIDO",regionDefendido.get(0).getObs_defendido());
				mRegionDefendido.put("TELEFONO1_DEFENDIDO",regionDefendido.get(0).getTelefono1_defendido());
				mRegionDefendido.put("LISTA_TELEFONOS_INTERESADO",regionDefendido.get(0).getLista_telefonos_interesado());
				mRegionDefendido.put("NIF_DEFENDIDO",regionDefendido.get(0).getNif_defendido());
				mRegionDefendido.put("SEXO_DEFENDIDO",regionDefendido.get(0).getSexo_defendido());
				mRegionDefendido.put("SEXO_DEFENDIDO_DESCRIPCION",regionDefendido.get(0).getSexo_defendido_descripcion());
				mRegionDefendido.put("O_A_DEFENDIDO",regionDefendido.get(0).getO_a_defendido());
				mRegionDefendido.put("EL_LA_DEFENDIDO",regionDefendido.get(0).getEl_la_defendido());
				mRegionDefendido.put("IDLENGUAJE_DEFENDIDO",regionDefendido.get(0).getIdlenguaje_defendido());
				mRegionDefendido.put("ANIO_EJG",regionDefendido.get(0).getAnio_ejg());
				mRegionDefendido.put("NUMERO_EJG",regionDefendido.get(0).getNumero_ejg());
				mRegionDefendido.put("FECHARESOLUCIONCAJG",regionDefendido.get(0).getFecharesolucioncajg());
				mRegionDefendido.put("COUNT_EJG",regionDefendido.get(0).getCount_ejg());
				mRegionDefendido.put("CALIDAD_DEFENDIDO",regionDefendido.get(0).getCalidad_defendido());
				mRegionDefendido.put("IDTIPOENCALIDAD",regionDefendido.get(0).getIdtipoencalidad());
				mRegionDefendido.put("IDREPRESENTANTEJG",regionDefendido.get(0).getIdrepresentantejg());
				hDatosFinal.put("defendido", mRegionDefendido); //region del defendido
			}
			
			
			List<DatosCartaAcreditacionItem> listaInteresadosFavorablesEJG = getDatosEjgResolucionFavorable(idInstitucion, anio, numero, idTurno);
			HashMap<String,Object> listadoEjgFavorableHashMap = new HashMap<String,Object>();
			//Si existen interesados los ponemos en forma de lista
			if(listaInteresadosFavorablesEJG != null && listaInteresadosFavorablesEJG.size()>0){
				String listaInteresadosFavorablesEJGString="";
				//Si sólo hay uno lo mostramos en la lista.
				if(listaInteresadosFavorablesEJG.size() == 1){
					listaInteresadosFavorablesEJGString = listaInteresadosFavorablesEJG.get(0).getNombre() + " "+
							listaInteresadosFavorablesEJG.get(0).getApellido1() + " " + listaInteresadosFavorablesEJG.get(0).getApellido2();
					
					
				}else{
					//Si hay más de uno construimos la lista
					int tam = listaInteresadosFavorablesEJG.size();
					
					for(int i=0;i<listaInteresadosFavorablesEJG.size();i++){
						if((tam-1)-i ==0){
							//Es el úlimo elemento no se pone coma
							listaInteresadosFavorablesEJGString = listaInteresadosFavorablesEJG.get(i).getNombre() + " "+
									listaInteresadosFavorablesEJG.get(i).getApellido1() + " " + listaInteresadosFavorablesEJG.get(i).getApellido2();
						}else{
							//No es el último elemento se pone coma
							listaInteresadosFavorablesEJGString = listaInteresadosFavorablesEJG.get(i).getNombre() + " "+
									listaInteresadosFavorablesEJG.get(i).getApellido1() + " " + listaInteresadosFavorablesEJG.get(i).getApellido2() + ", ";
						}
					}
				}
				listadoEjgFavorableHashMap.put("DESIGNA_LISTA_INTERESADOS", listaInteresadosFavorablesEJGString);
				total.putAll(listadoEjgFavorableHashMap);
			}else{
				listadoEjgFavorableHashMap.put("DESIGNA_LISTA_INTERESADOS", "");
				total.putAll(listadoEjgFavorableHashMap);
			}
		
		}
		return hDatosFinal;
		
	}
	
	

private List<DatosCartaAcreditacionItem> getDatosEjgResolucionFavorable(String idInstitucion, String anio, String numero,
			String idTurno) {
		try {
			List<DatosCartaAcreditacionItem> v = null;
			
			ScsDefendidosdesignaExample example = new ScsDefendidosdesignaExample();
			example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).
				andIdturnoEqualTo(Integer.valueOf(idTurno)).andAnioEqualTo(Short.valueOf(anio)).
				andNumeroEqualTo(Long.valueOf(numero));
			List<ScsDefendidosdesigna> defendidos = defendidosMapper.selectByExample(example);
			
			if(defendidos != null && !defendidos.isEmpty()) {
				
				v = designacionesMapper.getDatosEjgResolucionFavorable(idInstitucion,idTurno,anio,numero);
							 
			}		
			
			if(v != null && v.size()>0){
				return v;
			}else{
				return null;
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	return new ArrayList<DatosCartaAcreditacionItem>();
	}



public HashMap<String, Object> obtenerLetrado(DatosDireccionLetradoOficio despacho, DatosDireccionLetradoOficio Guardia){
		
		HashMap<String, Object> letrado = new HashMap<String, Object>();
	
		if (despacho.getDomicilio_letrado()!=null) {
			letrado.put("DOMICILIO_LETRADO", despacho.getDomicilio_letrado());
		}
		if (despacho.getCp_letrado()!=null) {
			letrado.put("CP_LETRADO", despacho.getCp_letrado());
		}
		if (despacho.getIdpoblacion_letrado()!=null) {
			letrado.put("ID_POBLACION_LETRADO", despacho.getIdpoblacion_letrado());
		}
		if (despacho.getPoblacion_letrado()!=null) {
			letrado.put("POBLACION_LETRADO", despacho.getPoblacion_letrado());
		}
		if (despacho.getIdprovincia_letrado()!=null) {
			letrado.put("ID_PROVINCIA_LETRADO", despacho.getIdprovincia_letrado());
		}
		if (despacho.getProvincia_letrado()!=null) {
			letrado.put("PROVINCIA_LETRADO", despacho.getProvincia_letrado());
		}
		if (despacho.getTelefono1_letrado()!=null) {
			letrado.put("TELEFONODESPACHO_LET", despacho.getTelefono1_letrado());
		}
		if (despacho.getFax1_letrado()!=null) {
			letrado.put("FAX_LETRADO", despacho.getFax1_letrado());
		}
		if (despacho.getEmail_letrado()!=null) {
			letrado.put("EMAIL_LETRADO", despacho.getEmail_letrado());
		}
		if (despacho.getMovil_letrado()!=null) {
			letrado.put("MOVILDESPACHO_LET", despacho.getMovil_letrado());
		}
		if (Guardia.getTelefono1_letrado()!=null) {
			letrado.put("TELEFONO1_LETRADO", Guardia.getTelefono1_letrado());
		}
		if (Guardia.getTelefono2_letrado()!=null) {
			letrado.put("TELEFONO2_LETRADO", Guardia.getTelefono2_letrado());
		}
		if (Guardia.getMovil_letrado()!=null) {
			letrado.put("MOVIL_LETRADO", Guardia.getMovil_letrado());
		}
		return letrado;

	}

public HashMap<String, Object> obtenerDestinatario(DatosDireccionLetradoOficio despacho, DatosDireccionLetradoOficio Guardia){
	
	HashMap<String, Object> destinatario = new HashMap<String, Object>();

	if (despacho.getDomicilio_letrado()!=null) {
		destinatario.put("DOMICILIO_DEST", despacho.getDomicilio_letrado());
	}
	if (despacho.getCp_letrado()!=null) {
		destinatario.put("CP_DEST", despacho.getCp_letrado());
	}
	if (despacho.getIdpoblacion_letrado()!=null) {
		destinatario.put("ID_POBLACION_DEST", despacho.getIdpoblacion_letrado());
	}
	if (despacho.getPoblacion_letrado()!=null) {
		destinatario.put("POBLACION_DEST", despacho.getPoblacion_letrado());
	}
	if (despacho.getIdprovincia_letrado()!=null) {
		destinatario.put("ID_PROVINCIA_DEST", despacho.getIdprovincia_letrado());
	}
	if (despacho.getProvincia_letrado()!=null) {
		destinatario.put("PROVINCIA_DEST", despacho.getProvincia_letrado());
	}
	if (despacho.getTelefono1_letrado()!=null) {
		destinatario.put("TELEFONO_DEST", despacho.getTelefono1_letrado());
	}
	if (despacho.getFax1_letrado()!=null) {
		destinatario.put("FAX_DEST", despacho.getFax1_letrado());
	}
	if (despacho.getEmail_letrado()!=null) {
		destinatario.put("EMAIL_DEST", despacho.getEmail_letrado());
	}
	if (despacho.getMovil_letrado()!=null) {
		destinatario.put("MOVIL_DEST", despacho.getMovil_letrado());
	}
	if (Guardia.getTelefono1_letrado()!=null) {
		destinatario.put("TELEFONO1_DEST", Guardia.getTelefono1_letrado());
	}
	if (Guardia.getTelefono2_letrado()!=null) {
		destinatario.put("TELEFONO2_DEST", Guardia.getTelefono2_letrado());
	}
	
	return destinatario;

}

	private String obtenerNombreApellidos(String idPersona) throws Exception {
		String nombre = "";		
		
		try {
			// Obtiene la persona
			CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersona));
						
			if (persona!=null) {

				nombre = persona.getNombre();
				if (persona.getApellidos1()!=null && !persona.getApellidos1().equals("#NA")){
					nombre += " " + persona.getApellidos1();
				}
				
				if (persona.getApellidos2()!=null && !persona.getApellidos2().equals("#NA")){
					nombre += " " + persona.getApellidos2();
				}
			}
			
		} catch(Exception e) {
			throw new Exception ("Error al obtener el nombre y apellidos",e);
		}
		
		return nombre;
	}




}
