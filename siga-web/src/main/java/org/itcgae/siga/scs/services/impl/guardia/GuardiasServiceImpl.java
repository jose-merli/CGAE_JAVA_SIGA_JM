package org.itcgae.siga.scs.services.impl.guardia;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.commons.utils.Puntero;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.EcomGuardiacolegiadoMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasMapper;
import org.itcgae.siga.db.mappers.ScsCvGuardiacolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionasiMapper;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsGuardiascolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsHcoConfProgCalendariosMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraMapper;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.mappers.ScsProgCalendariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EcomColaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.guardia.GuardiasColegiadoService;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GuardiasServiceImpl implements GuardiasService {

	private Logger LOGGER = Logger.getLogger(GuardiasServiceImpl.class);
	private boolean resetGrupos = false;
	private static final String ESTADO_PROGRAMADO = "0";
	private static final String EN_PROCESO = "1";
	private static final String PROCESADO_CON_ERRORES = "2";
	private static final String FINALIZADO = "3"; // FINALIZADO
	private static final String PENDIENTE = "4";
	private static final String REPROGRAMADO = "5";
	public static final int INSTITUCION_CGAE = 2000;
	public static final String DATE_FORMAT_JAVA = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_FORMAT_SHORT_SPANISH = "dd/MM/yyyy";
	public static final String TIPO_COD_VACACION = "V";
	public static final String TIPO_COD_MATERNIDAD = "M";
	public static final String TIPO_COD_BAJA = "B";
	public static final String TIPO_DESC_VACACION = "censo.bajastemporales.tipo.vacaciones";
	public static final String TIPO_DESC_BAJA = "censo.bajastemporales.tipo.baja";
	public static final String TIPO_DESC_MATERNIDAD = "censo.bajastemporales.tipo.maternidad";
	private static int controlError = 0;
	private static boolean controlVacioSC = false;
	private static boolean controlGrupoConSalto = false;

	public static final String GUARDIAS_DIRECTORIO_FISICO_LOG_CALENDARIOS_PROGRAMADOS = "guardias.directorioFisicoLogCalendariosProgramados";
	private static final int EXCEL_ROW_FLUSH = 1000;
	
	private static String USU_MODIFICACION = "0";
	
	private static final String DATE_SHORT_SLASH = "dd/MM/yyyy";
	private static final String DATE_SHORT_HYPHEN = "yyyy-MM-dd";
	private static final String DATE_LONG = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_LONG_MSEC = "yyyy-MM-dd HH:mm:ss.S";
	

	// private Map<String, Boolean> calendariosGenerandose = new HashMap<String,
	// Boolean>();
//	private Boolean generacionCalEnProceso = false;
	private Integer idInstitucion1;// GuardiasCalendarioItem
	private Integer idTurno1;// GuardiasCalendarioItem
	private Integer idGuardia1;// GuardiasCalendarioItem
	private Integer idCalendarioGuardias1;// GuardiasCalendarioItem
	private String fechaInicio1;// GuardiasCalendarioItem
	private String fechaFin1;// GuardiasCalendarioItem
	private GuardiasTurnoItem beanGuardiasTurno1 = null;
	private List<GuardiasCalendarioItem> calendariosVinculados1;
	private Integer usuModificacion1;

	// Datos para usar posteriormente propios de SJCS
	/**
	 * ArrayList de periodos con las fechas en formato corto como String
	 */
	private ArrayList<ArrayList<String>> arrayPeriodosDiasGuardiaSJCS1;
	/**
	 * ArrayList con los periodos, cada uno con sus letrados para hacer las guardias
	 * SJCS
	 */
	private ArrayList arrayPeriodosLetradosSJCS1;

	// Datos a calcular previamente
	private List<String> vDiasFestivos1;

	private List<Map<String, Object>> listaDatosExcelGeneracionCalendarios = new ArrayList<>();

	private String errorGeneracionCalendario;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private ScsIncompatibilidadguardiasExtendsMapper scsIncompatibilidadguardiasExtendsMapper;

	@Autowired
	private ScsHitofacturableguardiaExtendsMapper scsHitofacturableguardiaExtendsMapper;

	@Autowired
	private ScsOrdenacionColasExtendsMapper scsOrdenacionColasExtendsMapper;

	@Autowired
	private ScsInscripcionguardiaExtendsMapper scsInscripcionguardiaExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsSubzonapartidoExtendsMapper scsSubzonapartidoExtendsMapper;

	@Autowired
	private ScsGrupoguardiaExtendsMapper scsGrupoguardiaExtendsMapper;

	@Autowired
	private ScsGrupoguardiacolegiadoExtendsMapper scsGrupoguardiacolegiadoExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private GenFicheroMapper genFicheroMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private ScsDocumentacionasiMapper scsDocumentacionasiMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private ScsSaltoscompensacionesExtendsMapper scsSaltoscompensacionesExtendsMapper;

	@Autowired
	private ScsGrupoguardiacolegiadoMapper scsGrupoguardiacolegiadoMapper;

	@Autowired
	private ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;

	@Autowired
	private ScsGuardiascolegiadoExtendsMapper scsGuardiascolegiadoExtendsMapper;

	@Autowired
	private ScsCabeceraguardiasMapper scsCabeceraguardiasMapper;

	@Autowired
	private IGeneracionDocumentosService _generacionDocService;

	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	private ScsHcoConfProgCalendariosMapper scsHcoConfProgCalendariosMapper;

	@Autowired
	private ScsProgCalendariosMapper scsProgCalendariosMapper;

	@Autowired
	private ScsCabeceraguardiasExtendsMapper scsCabeceraguardiasExtendsMapper;

	@Autowired
	private ScsPermutaCabeceraMapper scsPermutaCabeceraMapper;

	@Autowired
	private ScsPermutaguardiasMapper scsPermutaguardiasMapper;

	@Autowired
	private ScsPermutaguardiasExtendsMapper scsPermutaguardiasExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

	@Autowired
	private ScsConfConjuntoGuardiasExtendsMapper scsConfConjuntoGuardiasExtendsMapper;

	@Autowired
	private ScsCalendarioguardiasExtendsMapper scsCalendarioguardiasMapper;

	@Autowired
	private ScsGuardiasturnoMapper scsGuardiasTurnoMapper;

	@Autowired
	private GuardiasColegiadoService guardiasColegiadoService;

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private PlatformTransactionManager transactionManagerProgramaciones;

	@Autowired
	private PlatformTransactionManager transactionManagerCalendarios;
	
	@Autowired
	private EcomColaExtendsMapper ecomColaExtendsMapper;
	
	@Autowired
	private EcomGuardiacolegiadoMapper ecomGuardiacolegiadoMapper;
	
	@Autowired
	private CenDireccionesMapper cenDireccionesMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private ScsCvGuardiacolegiadoMapper scsCvGuardiacolegiadoMapper;
	

	@Override
	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiaDTO = new GuardiasDTO();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchGuardias() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS)
						.andParametroEqualTo(SigaConstants.TAM_MAX_CONSULTA_JG)
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause(SigaConstants.C_IDINSTITUCION + " DESC");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchGuardias() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info("searchGuardias() -> Entrada para obtener las guardias");

				List<GuardiasItem> guardias = scsGuardiasturnoExtendsMapper.searchGuardias2(guardiasItem,
						idInstitucion.toString(), usuarios.get(0).getIdlenguaje(), tamMaximo);

				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				if ((guardias != null) && tamMaximo != null && (guardias.size()) > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					guardiaDTO.setError(error);
					guardias.remove(guardias.size() - 1);
				}

				guardiaDTO.setGuardiaItems(guardias);

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}
		return guardiaDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("deleteGuardias() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"deleteGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(new Date());
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una guardia");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"deleteGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una guardia");
							LOGGER.info(
									"deleteGuardias() / scsInscripcionguardiaExtendsMapper.updateByPrimaryKey() -> Entrada de scsInscripcionguardiaExtendsMapper para dar de baja las inscripciones de una guardia");
							// Tambien damos de baja las inscripciones a la guardia
							ScsInscripcionguardiaExample scsInscripcionguardiaExample = new ScsInscripcionguardiaExample();
							scsInscripcionguardiaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaEqualTo(guardia.getIdguardia()).andIdturnoEqualTo(guardia.getIdturno())
									.andFechabajaIsNull().andFechavalidacionIsNotNull().andFechadenegacionIsNull();
							List<ScsInscripcionguardia> inscripcionesActivas = scsInscripcionguardiaExtendsMapper
									.selectByExample(scsInscripcionguardiaExample);
							if (inscripcionesActivas != null && !inscripcionesActivas.isEmpty()) {

								inscripcionesActivas.forEach(inscripcion -> {
									inscripcion.setFechasolicitudbaja(new Date());
									inscripcion.setFechabaja(new Date());
									inscripcion.setFechamodificacion(new Date());
									inscripcion.setUsumodificacion(usuario.getIdusuario());
									scsInscripcionguardiaExtendsMapper.updateByPrimaryKey(inscripcion);
								});

							}

							LOGGER.info(
									"deleteGuardias() / scsInscripcionguardiaExtendsMapper.updateByPrimaryKey() -> Salida de scsInscripcionguardiaExtendsMapper para dar de baja las inscripciones de una guardia");

						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteGuardias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;

	}

	@Override
	@Transactional
	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request) {

		LOGGER.info("activateGuardias() ->  Entrada al servicio para dar de alta a prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//List<List<ScsInscripcionguardia>> inscripcionesGuardiasTurno = new ArrayList<>();
		//List<String> guardiasTrabajadas = new ArrayList<>(); 
		
		/*List<InscripcionTurnoItem> inscripcionTurnos = new ArrayList<>();
		List<List<InscripcionTurnoItem>> listaInscripcionesTurno = new ArrayList<>();
		
		List<org.itcgae.siga.DTO.scs.GuardiasItem> guardiasConfiguradas = new ArrayList<>();
		List<List<org.itcgae.siga.DTO.scs.GuardiasItem>> listaGuardiasConfiguradas = new ArrayList<>();
		
		List<ScsInscripcionguardia> inscripcionGuardia = new ArrayList<>();
		*/
		

		
		
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					// Guardias desde FRONT , pueden ser mas Guardias por Configuracion //
					// Obligatorias, Todas o ninguna...
					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {
						//if (!guardiasTrabajadas.contains(guardiaItem.getIdGuardia())) { // Si no está en guardias trabajadas empezamos a con esa guardia.
							
							// Comprobamos Configuracion de turnos para de alta
							ScsTurnoKey turnoPrincipalBusqueda = new ScsTurnoKey();
							turnoPrincipalBusqueda.setIdinstitucion(idInstitucion);
							turnoPrincipalBusqueda.setIdturno(Integer.parseInt(guardiaItem.getIdTurno()));

							ScsTurno turnoPrincipal = scsTurnosExtendsMapper.selectByPrimaryKey(turnoPrincipalBusqueda);
							
							//Codigo viejo:
							if (turnoPrincipal.getFechabaja() != null) {
								throw new Exception("justiciaGratuita.guardias.mensaje.turnos.error");
							}
							

							//Short tipoGuardias = turnoPrincipal.getGuardias();
							// 0 Obligatorias //1 - Todas o ninguna // 2- A elegir

							// Damos de alta el turno si esta de baja
							/*if (turnoPrincipal.getFechabaja() != null) {
								turnoPrincipal.setFechabaja(null);
								turnoPrincipal.setUsumodificacion(usuario.getIdusuario());
								turnoPrincipal.setFechamodificacion(new Date());
								scsTurnosExtendsMapper.updateByPrimaryKey(turnoPrincipal);
							}*/
							
							//CODIGO PARA FUTURA ACTIVACION DE GUARDIAS CON SUS INSCRIPCIONES Y TIPO DE TURNO.

							/*if (tipoGuardias != 2) { // Si es 	// 0 Obligatorias //1 - Todas o ninguna 

								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

								String fechaComoCadena = sdf.format(guardiaItem.getFechabaja());

								// Obtenemos las inscripciones al turno en estado de BAJA con fecha informada
								// fechabaja
								inscripcionTurnos = scsTurnosExtendsMapper.selectInscripcionTurnoBajasByTurnoFechaBaja(
										idInstitucion, turnoPrincipal.getIdturno().toString(), fechaComoCadena);
								if (inscripcionTurnos.size() != 0) {
									listaInscripcionesTurno.add(inscripcionTurnos);
								}

								// Obtenemos las inscripcions de guardia del turno en Baja by fecha.
								inscripcionGuardia = scsGuardiasturnoExtendsMapper.selectGuardiaTurnoBajasByTurnoFecha(
										idInstitucion, turnoPrincipal.getIdturno().toString(), fechaComoCadena);
								if (inscripcionGuardia.size() != 0) {
									inscripcionesGuardiasTurno.add(inscripcionGuardia);
								}

								// Obtenemos las guardiasConfiguradasTurno.
								guardiasConfiguradas = scsGuardiasturnoExtendsMapper
										.selectGuardiaConfiguradasBajaByTurnoFechaBaja(idInstitucion,
												turnoPrincipal.getIdturno().toString(), fechaComoCadena);
								if (guardiasConfiguradas.size() != 0) {
									listaGuardiasConfiguradas.add(guardiasConfiguradas);
								}

								//Guardamos los idGuardia de las guardias ya trabajadas.
								guardiasTrabajadas.addAll(guardiasConfiguradas.stream()
										.map(guardiaItemA -> String.valueOf(guardiaItemA.getIdGuardia()))
										.collect(Collectors.toList()));

							} */ 
							/*else {
								//2- A elegir / Solo damos de alta la guardia seleccionada, y si viene informada


								ScsGuardiasturnoKey guardiaKey = new ScsGuardiasturnoKey();
								guardiaKey.setIdguardia(Integer.parseInt(guardiaItem.getIdGuardia()));
								guardiaKey.setIdinstitucion(guardiaItem.getIdInstitucion());
								guardiaKey.setIdturno(Integer.parseInt(guardiaItem.getIdTurno()));

								ScsGuardiasturno guardiaUpdate = scsGuardiasturnoExtendsMapper
										.selectByPrimaryKey(guardiaKey);
								guardiaUpdate.setFechamodificacion(new Date());
								guardiaUpdate.setFechabaja(null);
								guardiaUpdate.setUsumodificacion(usuario.getIdusuario());

								scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardiaUpdate);

								if (guardiaItem.getFechabaja() != null) { // Si viene con fecha baja es que la fecha fue
																			// informada para dar de alta esas fechas,
																			// sino, no se activaras sus suscripciones,
																			// y solo se dara de alta la guardia.
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

									String fechaComoCadena = sdf.format(guardiaItem.getFechabaja());

									// Obtenemos las guardias inscritas al turno en alta.
									inscripcionGuardia = scsGuardiasturnoExtendsMapper
											.selectGuardiaTurnoGuardiaBajasByTurnoFecha(idInstitucion,
													guardiaUpdate.getIdturno().toString(),
													guardiaUpdate.getIdguardia().toString(), fechaComoCadena);
									if (inscripcionGuardia.size() != 0) {
										inscripcionesGuardiasTurno.add(inscripcionGuardia);
									}
								}

								guardiasTrabajadas.add(guardiaItem.getIdGuardia());
							}*/

						//}

					}
					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {
						ScsGuardiasturnoKey guardiaKey = new ScsGuardiasturnoKey();
						guardiaKey.setIdguardia(Integer.parseInt(guardiaItem.getIdGuardia()));
						guardiaKey.setIdinstitucion(idInstitucion);
						guardiaKey.setIdturno(Integer.parseInt(guardiaItem.getIdTurno()));

						ScsGuardiasturno guardiaUpdate = scsGuardiasturnoExtendsMapper
								.selectByPrimaryKey(guardiaKey);
						guardiaUpdate.setFechamodificacion(new Date());
						guardiaUpdate.setFechabaja(null);
						guardiaUpdate.setUsumodificacion(usuario.getIdusuario());

						response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardiaUpdate);
					}

					// ELIMINAMOS LAS INSCRIPCIONES A LAS GUARDIAS DEL TURNO
					/*for (List<ScsInscripcionguardia> listaInscripcionesGuardia : inscripcionesGuardiasTurno) {
						for (ScsInscripcionguardia inscripcionGuardiaAAnular : listaInscripcionesGuardia) {
							ScsInscripcionguardia guardiaupdate = new ScsInscripcionguardia();
							guardiaupdate.setIdinstitucion(inscripcionGuardiaAAnular.getIdinstitucion());
							guardiaupdate.setIdturno(inscripcionGuardiaAAnular.getIdturno());
							guardiaupdate.setIdpersona(inscripcionGuardiaAAnular.getIdpersona());
							guardiaupdate.setFechasuscripcion(inscripcionGuardiaAAnular.getFechasuscripcion());
							guardiaupdate.setIdguardia(inscripcionGuardiaAAnular.getIdguardia());

							ScsInscripcionguardia guardiaActualUpdate = scsInscripcionguardiaMapper
									.selectByPrimaryKey(guardiaupdate);

							if (guardiaActualUpdate.getFechasolicitudbaja().equals(guardiaActualUpdate.getFechabaja()))
								guardiaActualUpdate.setFechasolicitudbaja(null);

							guardiaActualUpdate.setFechabaja(null);
							guardiaActualUpdate.setFechamodificacion(new Date());
							guardiaActualUpdate.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsInscripcionguardiaMapper.updateByPrimaryKey(guardiaActualUpdate);

						}
					}*/

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					if(e.getMessage() != null ) error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateGuardias() -> Salida del servicio para dar de alta las guardias");

		return updateResponseDTO;

	}

	@Transactional
	@Override
	public GuardiasItem detalleGuardia(GuardiasItem guardiaTurno, HttpServletRequest request) {

		LOGGER.info("detalleGuardia() ->  Entrada al servicio para obtener una guardia");

		GuardiasItem guardiaItem = new GuardiasItem();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"detalleGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"detalleGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a la busqueda de la guardia");

					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiaTurno.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiaTurno.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);
					if (guardiasList != null && guardiasList.size() > 0) {

						LOGGER.info(
								"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Mapeamos lo recogido");
						// Mapeo para Datos Generales
						ScsGuardiasturno guardia = guardiasList.get(0);
						guardiaItem.setNombre(guardia.getNombre());
						guardiaItem.setDescripcion(guardia.getDescripcion());
						if (guardia.getIdtipoguardia() != null)
							guardiaItem.setTipoGuardia(guardia.getIdtipoguardia() + "");
						if (guardia.getEnviocentralita() != null)
							guardiaItem.setEnvioCentralita(guardia.getEnviocentralita().equals(Short.valueOf("1")));
						guardiaItem.setDescripcionFacturacion(guardia.getDescripcionfacturacion());
						guardiaItem.setDescripcionPago(guardia.getDescripcionpago());
						guardiaItem.setIdTurno(guardia.getIdturno() + "");
						if (guardia.getIdtipoguardia() != null) {
							guardiaItem.setIdTipoGuardia(guardia.getIdtipoguardia().toString());
						} else
							guardiaItem.setIdTipoGuardia("");
						guardiaItem.setIdGuardia(guardia.getIdguardia() + "");

						// AQUI VEMOS SI ALGUNA GUARDIA ESTA VINCULADA Y SI ESTÁ SE LA AÑADIMOS AL
						// OBJETO

						List<GuardiasItem> hilfe = scsGuardiasturnoExtendsMapper.getGuardiasVinculadas(
								guardia.getIdguardia().toString(), guardia.getIdturno().toString(),
								idInstitucion.toString());

						if (hilfe != null && hilfe.size() > 0) {
							guardiaItem.setIdGuardiaVinculada("");
							guardiaItem.setIdTurnoVinculada("");
							hilfe.forEach(it -> {
								guardiaItem.setIdGuardiaVinculada(
										guardiaItem.getIdGuardiaVinculada() + it.getNombre() + ",");
								guardiaItem
										.setIdTurnoVinculada(guardiaItem.getIdTurnoVinculada() + it.getTurno() + ",");
							});

						}

						if (guardia.getIdguardiaprincipal() != null && guardia.getIdturnoprincipal() != null) {
							hilfe = scsGuardiasturnoExtendsMapper.getGuardiaPrincipal(
									guardia.getIdguardiaprincipal().toString(),
									guardia.getIdturnoprincipal().toString(), idInstitucion.toString());

							guardiaItem.setIdGuardiaPrincipal(hilfe.get(0).getNombre());
							guardiaItem.setIdTurnoPrincipal(hilfe.get(0).getTurno());
						}

						// Configuracion de cola
						guardiaItem.setPorGrupos(guardia.getPorgrupos());
						if (guardia.getIdordenacioncolas() != null)
							guardiaItem.setIdOrdenacionColas(guardia.getIdordenacioncolas() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setRotarComponentes(guardia.getRotarcomponentes());
						if (guardia.getNumeroletradosguardia() != null) {
							guardiaItem.setLetradosGuardia(guardia.getNumeroletradosguardia().toString());
						} else {
							guardiaItem.setLetradosGuardia("0");
						}
						// Configuracion calendarios
						if (guardia.getDiasguardia() != null)
							guardiaItem.setDiasGuardia(guardia.getDiasguardia() + "");
						if (guardia.getDiasperiodo() != null)
							guardiaItem.setDiasPeriodo(guardia.getDiasperiodo() + "");
						guardiaItem.setTipoDiasGuardia(guardia.getTipodiasguardia());
						guardiaItem.setTipoDiasPeriodo(guardia.getTipodiasperiodo());
						guardiaItem.setDiasSeparacionGuardias(guardia.getDiasseparacionguardias() != null
								? guardia.getDiasseparacionguardias().toString()
								: "0");
						guardiaItem.setRequeridaValidacion("S".equals(guardia.getRequeridavalidacion()) ? true : false);

						List<GuardiasItem> checkeado = scsHitofacturableguardiaExtendsMapper.getCheckSeparacionGuardias(
								guardia.getIdturno().toString(), guardia.getIdguardia().toString(),
								idInstitucion.toString());
						if (checkeado.size() > 0)
							guardiaItem.setSepararGuardia(checkeado.get(0).getSepararGuardia());

						guardiaItem.setSeleccionLaborables(guardia.getSeleccionlaborables());
						guardiaItem.setSeleccionFestivos(guardia.getSeleccionfestivos());

						if (guardia.getIdgrupoguardiaUltimo() != null)
							guardiaItem.setIdGrupoUltimo(guardia.getIdgrupoguardiaUltimo().toString());
						// Cola de guardia
						if (guardia.getIdpersonaUltimo() != null)
							guardiaItem.setIdPersonaUltimo(guardia.getIdpersonaUltimo() + "");
						LOGGER.info(
								"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Acabamos de mapear");

					}
					LOGGER.info(
							"detalleGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de la busqueda de guardia");

				} catch (Exception e) {
					LOGGER.error(e);
				}
			}

		}

		LOGGER.info("detalleGuardia() -> Salida del servicio para recoger una guardia");

		return guardiaItem;

	}

	@Override
	public UpdateResponseDTO updateColaGuardia(InscripcionGuardiaDTO guardiaBody, HttpServletRequest request) {
		UpdateResponseDTO res = guardarColaGuardias(guardiaBody.getInscripcionesItem(), request);
		return res;

	}

	@Override
	public UpdateResponseDTO updateGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("updateGuardia() ->  Entrada al servicio para editar guardia");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					guardia.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
					guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
					guardia.setIdinstitucion(idInstitucion);

					// Se entrara en cada if dependiendo de que tarjeta se haya editado.
					// Se define donde entrara segun los datos que lleguen.
					if (guardiasItem.getDescripcion() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Datos Generales de guardias");

						guardia.setDescripcion(guardiasItem.getDescripcion());
						guardia.setNombre(guardiasItem.getNombre());
						if (guardiasItem.getIdTipoGuardia() != null && !StringUtils.isEmpty(guardiasItem.getIdTipoGuardia())) {
							guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						} else {
							guardia.setIdtipoguardia(null);
						}
						if (guardiasItem.getEnvioCentralita() != null)
							guardia.setEnviocentralita(
									Short.valueOf((short) (guardiasItem.getEnvioCentralita() ? 1 : 0)));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						// guardia.setIdturnoprincipal(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()));
						// guardia.setIdguardiaprincipal(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()));

					} else if (guardiasItem.getLetradosGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de Cola de guardias");
						ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
						example.createCriteria().andIdturnoEqualTo(guardia.getIdturno())
								.andIdguardiaEqualTo(guardia.getIdguardia()).andIdinstitucionEqualTo(idInstitucion);
						List<ScsGuardiasturno> item = scsGuardiasturnoExtendsMapper.selectByExample(example);

						ScsOrdenacioncolasExample ordExample = new ScsOrdenacioncolasExample();
						ordExample.createCriteria().andIdordenacioncolasEqualTo(item.get(0).getIdordenacioncolas());

						List<ScsOrdenacioncolas> colas = scsOrdenacionColasExtendsMapper.selectByExample(ordExample);

						// Esto sirve para ver si habrá que generar grupos automaticamente despues de
						// updatear
						resetGrupos = false;
						if ((item.get(0).getPorgrupos().equals("1") || colas.get(0).getOrdenacionmanual() == 0)
								&& Short.valueOf(guardiasItem.getFiltros().split(",")[4]) != 0) {
							resetGrupos = true;
						}
						guardia.setPorgrupos((Boolean.valueOf(guardiasItem.getPorGrupos()) ? "1" : "0"));
						guardia.setIdordenacioncolas(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
						// ROTAR COMPONENTES?
						guardia.setNumeroletradosguardia(Integer.valueOf(guardiasItem.getLetradosGuardia()));

						ordExample = new ScsOrdenacioncolasExample();
						ordExample.createCriteria()
								.andAlfabeticoapellidosEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[0]))
								.andFechanacimientoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[1]))
								.andNumerocolegiadoEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[2]))
								.andAntiguedadcolaEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[3]))
								.andOrdenacionmanualEqualTo(Short.valueOf(guardiasItem.getFiltros().split(",")[4]));

						colas = scsOrdenacionColasExtendsMapper.selectByExample(ordExample);

						if (colas.isEmpty()) { // Setteo de cola
							ScsOrdenacioncolas ordenacion = new ScsOrdenacioncolas();
							ordenacion.setAlfabeticoapellidos(Short.valueOf(guardiasItem.getFiltros().split(",")[0]));
							ordenacion.setFechanacimiento(Short.valueOf(guardiasItem.getFiltros().split(",")[1]));
							ordenacion.setNumerocolegiado(Short.valueOf(guardiasItem.getFiltros().split(",")[2]));
							ordenacion.setAntiguedadcola(Short.valueOf(guardiasItem.getFiltros().split(",")[3]));
							ordenacion.setOrdenacionmanual(Short.valueOf(guardiasItem.getFiltros().split(",")[4]));
							ordenacion.setFechamodificacion(new Date());
							ordenacion.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());

							NewIdDTO idP = scsOrdenacionColasExtendsMapper.getIdOrdenacion();

							if (idP == null) {
								ordenacion.setIdordenacioncolas((int) 1);
							} else {
								ordenacion.setIdordenacioncolas(Integer.parseInt(idP.getNewId()) + 1);
							}
							scsOrdenacionColasExtendsMapper.insert(ordenacion);
							guardia.setIdordenacioncolas(ordenacion.getIdordenacioncolas());

						} else {
							Short dS = null;
							if (guardiasItem.getDiasSeparacionGuardias() != null) {
								dS = Short.parseShort(guardiasItem.getDiasSeparacionGuardias());
							}
							guardia.setDiasseparacionguardias(dS);
							guardia.setRotarcomponentes(guardiasItem.getRotarComponentes()=="false" ? "0" : "1");
							guardia.setIdordenacioncolas(colas.get(0).getIdordenacioncolas());
							guardia.setRequeridavalidacion(Boolean.toString(guardiasItem.isRequeridaValidacion()));
							scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

						}

					} else if (guardiasItem.getDiasGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de calendario de guardias");
						if (!UtilidadesString.esCadenaVacia(guardiasItem.getDiasGuardia()))
							guardia.setDiasguardia(Short.valueOf(guardiasItem.getDiasGuardia()));
						guardia.setTipodiasguardia(guardiasItem.getTipoDiasGuardia());
						if (!UtilidadesString.esCadenaVacia(guardiasItem.getDiasPeriodo())) {
							guardia.setDiasperiodo(Short.valueOf(guardiasItem.getDiasPeriodo()));
						}else{
							guardia.setDiasperiodo((short)0);

						}
						
						
						if (!UtilidadesString.esCadenaVacia(guardiasItem.getTipoDiasPeriodo()) && guardiasItem.getTipoDiasPeriodo() != null) {
							guardia.setTipodiasperiodo(guardiasItem.getTipoDiasPeriodo());
						}else if(guardiasItem.getTipoDiasPeriodo() == null) {
							guardiasItem.setTipoDiasPeriodo("");
							guardia.setTipodiasperiodo(guardiasItem.getTipoDiasPeriodo());

						}
						
						
						guardia.setSeleccionfestivos(guardiasItem.getSeleccionFestivos());
						guardia.setSeleccionlaborables(guardiasItem.getSeleccionLaborables());
						guardia.setRequeridavalidacion(guardiasItem.isRequeridaValidacion() ? "S" : "N");

					}

					guardia.setFechamodificacion(new Date());
					guardia.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
					if (!UtilidadesString.esCadenaVacia(guardiasItem.getDiasSeparacionGuardias()))
						guardia.setDiasseparacionguardias(Short.parseShort(guardiasItem.getDiasSeparacionGuardias()));
					guardia.setRequeridavalidacion(Boolean.toString(guardiasItem.isRequeridaValidacion()));
					response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

					if (response > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(guardia.getIdguardia().toString());
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateGuardia() -> Salida del servicio para editar guardia");

		return updateResponseDTO;

	}

	@Override
	public InsertResponseDTO createGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("createPrision() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		long idGuardia = 0;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsGuardiasturnoExample scsGuardiasturnoExample = null;
					LOGGER.info(
							"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Setteo de los campos que se han introducido y el resto de datos heredados de la guardia vinculada");

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					if (!UtilidadesString.esCadenaVacia(guardiasItem.getIdGuardiaPrincipal())
							&& !UtilidadesString.esCadenaVacia(guardiasItem.getIdTurnoPrincipal())) {
						scsGuardiasturnoExample = new ScsGuardiasturnoExample();
						scsGuardiasturnoExample.createCriteria()
								.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()))
								.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()))
								.andIdinstitucionEqualTo(idInstitucion);
						guardia = scsGuardiasturnoExtendsMapper.selectByExample(scsGuardiasturnoExample).get(0);

						guardia.setFechabaja(null);
						guardia.setFechamodificacion(new Date());
						guardia.setUsumodificacion(usuario.getIdusuario().intValue());
						guardia.setIdinstitucion(idInstitucion);
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setDescripcion(guardiasItem.getDescripcion());
						if (guardiasItem.getIdTipoGuardia() != null)
							guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
						guardia.setEnviocentralita((short) (guardiasItem.getEnvioCentralita() ? 1 : 0));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						guardia.setIdguardiaprincipal(Integer.valueOf(guardiasItem.getIdGuardiaPrincipal()));
						guardia.setIdturnoprincipal(Integer.valueOf(guardiasItem.getIdTurnoPrincipal()));
						guardia.setFechasuscripcionUltimo(null);
						guardia.setIdpersonaUltimo(null);
					} else {
						LOGGER.info(
								"createGuardia() / admUsuariosExtendsMapper.selectByExample() -> Setteo de los campos que se han introducido y el resto de datos por defecto");

						// Extraemos el último ID de la tabla OrdenacionColas y le sumamos 1 para
						// generar el próximo ID
						Integer nuevoIdOrdenacionCola = Integer
								.valueOf(this.scsGuardiasturnoExtendsMapper.getIdUltimaGuardiaTurno()) + 1;

						// Insertamos el nuevo registro en la tabla OrdenacionColas sin configuración
						ScsOrdenacioncolas ordenacionColas = new ScsOrdenacioncolas();
						ordenacionColas.setIdordenacioncolas(nuevoIdOrdenacionCola);
						ordenacionColas.setAlfabeticoapellidos(Short.valueOf("0"));
						ordenacionColas.setFechanacimiento(Short.valueOf("0"));
						ordenacionColas.setNumerocolegiado(Short.valueOf("0"));
						ordenacionColas.setAntiguedadcola(Short.valueOf("0"));
						ordenacionColas.setFechamodificacion(new Date());
						ordenacionColas.setUsumodificacion(0);
						ordenacionColas.setOrdenacionmanual(Short.valueOf("0"));

						this.scsOrdenacionColasExtendsMapper.insert(ordenacionColas);

						guardia.setFechabaja(null);
						guardia.setEnviocentralita((short) (guardiasItem.getEnvioCentralita() ? 1 : 0));
						guardia.setFechamodificacion(new Date());
						guardia.setUsumodificacion(usuario.getIdusuario().intValue());
						guardia.setIdinstitucion(idInstitucion);
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setDescripcion(guardiasItem.getDescripcion());
						if (guardiasItem.getIdTipoGuardia() != null)
							guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
						guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
						guardia.setDescripcionfacturacion(guardiasItem.getDescripcionFacturacion());
						guardia.setDescripcionpago(guardiasItem.getDescripcionPago());
						// VALORES POR DEFECTO ESTABLECIDOS
						guardia.setTipodiasguardia("D");
						guardia.setNumeroletradosguardia(1);
						guardia.setNumerosustitutosguardia(1);
						guardia.setDiasguardia((short) 1);
						guardia.setDiaspagados((short) 1);
						guardia.setDiasseparacionguardias((short) 0);
						guardia.setSeleccionfestivos("LMXJVSD");
						guardia.setSeleccionlaborables("LMXJVSD");
						guardia.setValidarjustificaciones("N");
						guardia.setIdordenacioncolas(nuevoIdOrdenacionCola); // Se extrae el ultimo ID y se le suma 1
																				// para obtener el nuevo ID
					}
					NewIdDTO idP = scsGuardiasturnoExtendsMapper.getIdGuardia();

					if (idP == null) {
						guardia.setIdguardia((int) 1);
						idGuardia = 1;
					} else {
						idGuardia = (Integer.parseInt(idP.getNewId()) + 1);
						guardia.setIdguardia((int) idGuardia);
					}

					LOGGER.info(
							"createGuardia() / scsGuardiasturnoExtendsMapper.insert() -> Entrada a scsGuardiasturnoExtendsMapper para insertar la nueva guardia");

					response = scsGuardiasturnoExtendsMapper.insertSelective(guardia);
					
					LOGGER.info(
							"createGuardia() / scsGuardiasturnoExtendsMapper.insert() -> Salida de scsGuardiasturnoExtendsMapper para insertar la nueva guardia");

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idGuardia));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createGuardia() -> Salida del servicio para crear una nueva guardia");

		return insertResponseDTO;

	}

	@Override
	public GuardiasItem resumenGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		boolean tipoGuardiaVacia = false;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenGuardia() -> Entrada para obtener datos resumen");
				
				if(guardiasItem.getTipoGuardia() == null) {
					tipoGuardiaVacia = true;
				}
				
				guardias = scsGuardiasturnoExtendsMapper.getResumen(guardiasItem.getIdGuardia(),
						guardiasItem.getIdTurno(), idInstitucion.toString(), usuarios.get(0).getIdlenguaje(), tipoGuardiaVacia);

				LOGGER.info("resumenGuardia() -> Salida ya con los datos recogidos");
			}
		}
		return !guardias.isEmpty() ? guardias.get(0) : null;
	}

	@Override
	public GuardiasItem resumenConfiguracionCola(GuardiasItem guardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenConfiguracionCola() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenConfiguracionCola() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenConfiguracionCola() -> Entrada para obtener los resumen Conf. Cola");
				guardias = scsGuardiasturnoExtendsMapper.resumenConfCola(guardia.getIdGuardia(), guardia.getIdTurno(),
						idInstitucion.toString());
				LOGGER.info("resumenConfiguracionCola() -> Salida ya con los datos recogidos");
			}
		}
		return guardias.get(0);
	}

	@Override
	public GuardiasDTO tarjetaIncompatibilidades(String idGuardia, String idTurno, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		GuardiasDTO guardia = new GuardiasDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"tarjetaIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				// guardias =
				// scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia,
				// idInstitucion.toString());
				guardias = scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia, idTurno,
						idInstitucion.toString());
				guardias = guardias.stream().map(it -> {
					it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
							+ it.getSeleccionFestivos()).replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				LOGGER.info("tarjetaIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		guardia.setGuardiaItems(guardias);
		return guardia;
	}

	@Override
	public ComboDTO getBaremos(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ComboItem> datos = new ArrayList<ComboItem>();
		ComboDTO baremos = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getBaremos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getBaremos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getBaremos() -> Entrada para obtener los datos de baremos");

				datos = scsHitofacturableguardiaExtendsMapper.getBaremos(idGuardia, usuarios.get(0).getIdlenguaje());

				LOGGER.info("getBaremos() -> Salida ya con los datos recogidos");
			}
		}
		baremos.setCombooItems(datos);
		return baremos;
	}

	@Override
	public DatosCalendarioItem getCalendario(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioItem> datos = new ArrayList<DatosCalendarioItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCalendario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendario() -> Entrada para obtener los datos del calendario");

				datos = scsGuardiasturnoExtendsMapper.getCalendario(idGuardia);

				LOGGER.info("getCalendario() -> Salida ya con los datos recogidos");
			}
		}

		return datos.size() > 0 ? datos.get(0) : null;
	}

	@Transactional
	@Override
	public InscripcionGuardiaDTO searchColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request, String from) {
		Short idInstitucion =null ;
		String dni = "";
		boolean isOrdenacionManual = false;
		
		if(!"Calendarios".equals(from)) {
			String token = request.getHeader("Authorization");
			 dni = UserTokenUtils.getDniFromJWTToken(token);
			 idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		}
	
		
		String ordenaciones = "";
		InscripcionGuardiaDTO inscritos = new InscripcionGuardiaDTO();
		
		if(from != null && from.equals("Calendarios"))
			idInstitucion = guardiasItem.getIdInstitucion();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try {

				if (usuarios != null && usuarios.size() > 0 || ("Calendarios").equals(from)) {
					LOGGER.info("searchGuardias() -> Entrada para obtener las colas de guardia");
					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()));

					List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper.selectByExample(example);

					// Actualizar cola guardia para evitar que el ultimo grupo quede a caballo
					actualizarColaGuardiaConUltimoColegiadoPorGrupo(idInstitucion,
							Integer.valueOf(guardiasItem.getIdTurno()), Integer.valueOf(guardiasItem.getIdGuardia()));

					// Copia el valor de porGrupos para ser utilizado en la busqueda
					List<GuardiasItem> confCola = scsGuardiasturnoExtendsMapper.resumenConfCola(
							guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), idInstitucion.toString());

					if (confCola != null && !confCola.isEmpty() && confCola.get(0).getIdOrdenacionColas() != null) {
						guardiasItem.setPorGrupos(
								confCola.get(0).getIdOrdenacionColas().contains("Por grupos") ? "1" : "0");
					}

					String ultimo = "";

					if (guardias != null && !guardias.isEmpty())
						ultimo = guardias.get(0).getIdpersonaUltimo() == null ? ""
								: guardias.get(0).getIdpersonaUltimo().toString();


					ScsOrdenacioncolasExample example2 = new ScsOrdenacioncolasExample();
					example2.createCriteria()
							.andIdordenacioncolasEqualTo(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
					// Segun lo que nos llega del front preparamos el ORDER BY que habra en la
					// query.
					List<ScsOrdenacioncolas> cola = scsOrdenacionColasExtendsMapper.selectByExample(example2);
					Map<Short, String> mapilla = new HashMap();
					Map<Short, String> mapa = new TreeMap<Short, String>(Collections.reverseOrder());
					if (cola != null && !cola.isEmpty()) {
						if (cola.get(0).getAntiguedadcola() > 0 && cola.get(0).getOrdenacionmanual() <= 0)
							mapilla.put(cola.get(0).getAntiguedadcola(), " ANTIGUEDADCOLA asc, ");
						else if (cola.get(0).getAntiguedadcola() < 0 && cola.get(0).getOrdenacionmanual() <= 0) {
							cola.get(0).setAntiguedadcola((short) -cola.get(0).getAntiguedadcola());
							mapilla.put(cola.get(0).getAntiguedadcola(), " ANTIGUEDADCOLA desc, ");
						}
						if (cola.get(0).getAlfabeticoapellidos() > 0 && cola.get(0).getOrdenacionmanual() <= 0)
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS asc, ");
						else if (cola.get(0).getAlfabeticoapellidos() < 0 && cola.get(0).getOrdenacionmanual() <= 0) {
							cola.get(0).setAlfabeticoapellidos((short) -cola.get(0).getAlfabeticoapellidos());
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS desc, ");
						}
						if (cola.get(0).getFechanacimiento() > 0 && cola.get(0).getOrdenacionmanual() <= 0)
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO asc, ");
						else if (cola.get(0).getFechanacimiento() < 0 && cola.get(0).getOrdenacionmanual() <= 0) {
							cola.get(0).setFechanacimiento((short) -cola.get(0).getFechanacimiento());
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO desc, ");

						}
						if (cola.get(0).getOrdenacionmanual() > 0) {
							mapilla.put(cola.get(0).getOrdenacionmanual(), "NUMEROGRUPO, ORDENGRUPO, ");
							isOrdenacionManual = true;}

						if (cola.get(0).getNumerocolegiado() > 0 && cola.get(0).getOrdenacionmanual() <= 0)
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO asc, ");
						else if (cola.get(0).getNumerocolegiado() < 0 && cola.get(0).getOrdenacionmanual() <= 0) {
							cola.get(0).setNumerocolegiado((short) -cola.get(0).getNumerocolegiado());
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO desc, ");
						}
						mapa.putAll(mapilla);
						if (mapa.size() > 0)
							for (String orden : mapa.values()) {
								ordenaciones += orden;
							}
						if (!ordenaciones.isEmpty()) {
							ordenaciones.substring(0, ordenaciones.length() - 1);
						} else {
							ordenaciones = " ANTIGUEDADCOLA, "; // por defecto
						}
					}
					// Si hay ultimo se prepara su WHERE correspondiente
					if (!UtilidadesString.esCadenaVacia(ultimo))
						ultimo = "WHERE\r\n" + "		idpersona =" + ultimo;
					String grupoUltimo = "";

					if (guardias.get(0).getIdgrupoguardiaUltimo() != null)
						grupoUltimo = "and idgrupoguardiacolegiado = " + guardias.get(0).getIdgrupoguardiaUltimo();

					// Se comprueba si existe letrado último antes de realizar la búsqueda
					if (guardias.get(0).getIdpersonaUltimo() != null
							&& guardias.get(0).getFechasuscripcionUltimo() != null) {
						ScsInscripcionguardiaKey inscripcionguardiaKey = new ScsInscripcionguardiaKey();
						inscripcionguardiaKey.setIdinstitucion(guardias.get(0).getIdinstitucion());
						inscripcionguardiaKey.setIdturno(guardias.get(0).getIdturno());
						inscripcionguardiaKey.setIdguardia(guardias.get(0).getIdguardia());
						inscripcionguardiaKey.setIdpersona(guardias.get(0).getIdpersonaUltimo());
						inscripcionguardiaKey.setFechasuscripcion(guardias.get(0).getFechasuscripcionUltimo());

						ScsInscripcionguardia inscripcionguardia = scsInscripcionguardiaExtendsMapper
								.selectByPrimaryKey(inscripcionguardiaKey);

						if (inscripcionguardia == null) {
							ultimo = "";
							grupoUltimo = "";

							//
						} else if (guardias.get(0).getIdgrupoguardiaUltimo() != null) {
							ScsGrupoguardiacolegiadoExample grupoExample = new ScsGrupoguardiacolegiadoExample();
							grupoExample.createCriteria().andIdinstitucionEqualTo(guardias.get(0).getIdinstitucion())
									.andIdturnoEqualTo(guardias.get(0).getIdturno())
									.andIdguardiaEqualTo(guardias.get(0).getIdguardia())
									.andIdpersonaEqualTo(guardias.get(0).getIdpersonaUltimo())
									.andFechasuscripcionEqualTo(guardias.get(0).getFechasuscripcionUltimo())
									.andIdgrupoguardiacolegiadoEqualTo(guardias.get(0).getIdgrupoguardiaUltimo());

							if (scsGrupoguardiacolegiadoMapper.countByExample(grupoExample) == 0) {
								ultimo = "";
								grupoUltimo = "";
							}
						}
					}

					String porGrupos = guardiasItem.getPorGrupos();
					List<InscripcionGuardiaItem> colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(
							guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(),
							ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo, porGrupos == "1");
					// cuando marcamos orden = manual por primera vez
					if (ordenaciones.contains("NUMEROGRUPO, ORDENGRUPO,") && porGrupos == "1") {
						int j = 1;
						for (int x = 0; x < colaGuardia.size(); x++) {
							// rellenar todos los numero grupo y orden
							if (colaGuardia.get(x).getNumeroGrupo() == null
									|| colaGuardia.get(x).getNumeroGrupo().equals("null")) {

								colaGuardia.get(x).setNumeroGrupo(String.valueOf(x + 1));
								j++;
								colaGuardia.get(x).setOrden("1");
							}
						}

					} else if (ordenaciones.contains("NUMEROGRUPO, ORDENGRUPO,") && porGrupos != "1") {
						int j = colaGuardia.size();
						for (int x = 0; x < colaGuardia.size(); x++) {
							// rellenar todos los numero grupo y orden
							if (colaGuardia.get(x).getNumeroGrupo() == null
									|| colaGuardia.get(x).getNumeroGrupo().equals("null")) {
								
								colaGuardia.get(x).setNumeroGrupo(String.valueOf(j));
								colaGuardia.get(x).setOrden("1");
								j++;
							}
						}
					}
					List<InscripcionGuardiaItem> colaGuardiaNulos = new ArrayList<InscripcionGuardiaItem>();
					List<InscripcionGuardiaItem> needToRemove = new ArrayList<InscripcionGuardiaItem>();
					List<InscripcionGuardiaItem> colaGuardiaUltimos = new ArrayList<InscripcionGuardiaItem>();
					List<InscripcionGuardiaItem> colaGuardiaAux = new ArrayList<InscripcionGuardiaItem>();

					//Provisionalmente quitamos esta ordenacion porque sospechamos que no la hace bien en todos los casos
					// y que ahora viene bien ordenado desde la query
					/*if (ordenaciones.contains("NUMEROGRUPO, ORDENGRUPO,")) {
						Collections.sort(colaGuardia);
					}*/
					
					for (int y = 0; y < colaGuardia.size(); y++) {
						System.out.println("i: " + String.valueOf(y));

						if (colaGuardia.get(y).getIdPersona().equals(String.valueOf(guardias.get(0).getIdpersonaUltimo()))) {
							
							colaGuardia.get(y).setUltimoCola(1);
							
							for (int g = 0; g <= y; g++) {
								needToRemove.add(colaGuardia.get(g)); // remove after
								colaGuardiaUltimos.add(colaGuardia.get(g));
							}
							
						}
						if (porGrupos == "1" && (colaGuardia.get(y).getNumeroGrupo() == null
								|| "null".equals(colaGuardia.get(y).getNumeroGrupo()))) {
							needToRemove.add(colaGuardia.get(y)); // remove after
							colaGuardiaNulos.add(colaGuardia.get(y));
						}
					}
					
					colaGuardia.removeAll(needToRemove);
					
					colaGuardia.addAll(colaGuardiaUltimos);
					
					// no se permiten repetidos
					Set<InscripcionGuardiaItem> colaGuardiaHS2 = new LinkedHashSet<InscripcionGuardiaItem>(colaGuardia);

					colaGuardia.clear();
					colaGuardia.addAll(colaGuardiaHS2);
					
					colaGuardia.addAll(colaGuardiaNulos);
					// reordenamos
					
					//Vamos a modificar los número de orden
					Integer orden = 1;
					for (InscripcionGuardiaItem cG : colaGuardia) {
						if(porGrupos != "1") {
							cG.setOrdenCola(orden.toString());
							orden++;
						}
						if (!colaGuardiaAux.contains(cG)) {
							colaGuardiaAux.add(cG);
						}
					}

					colaGuardia.clear();
					colaGuardia.addAll(colaGuardiaAux);
					

					inscritos.setInscripcionesItem(colaGuardia);

					// Si se ha pasado de por grupos a sin grupos o que estuviese sin grupos y se
					// añada la ord. manual
					// hay que updatear todos los grupos
					// y generarlos con el mismo valor de la posicion que tienen en la lista.
					if (colaGuardia != null && colaGuardia.size() > 0 && resetGrupos) {
						resetGrupos = false;

						ScsGrupoguardia grupo = null;
						ScsGrupoguardiacolegiado grupoColegiado = null;

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						String strDate = formatter.format(new Date());

						List<InscripcionGuardiaItem> todaColaGuardia = scsInscripcionguardiaExtendsMapper
								.getColaGuardias(guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), strDate,
										ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo, porGrupos == "1");
						// Obtenemos el ultimo id generado en los grupos
						ScsGrupoguardiaExample grupoGuardiaExample = new ScsGrupoguardiaExample();
						grupoGuardiaExample.createCriteria()
								.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
								.andIdinstitucionEqualTo(idInstitucion);
						List<ScsGrupoguardia> gruposGuardia = scsGrupoguardiaExtendsMapper
								.selectByExample(grupoGuardiaExample);

						ScsGrupoguardiacolegiadoExample scsGrupoguardiacolegiadoExample = null;

						for (int i = 0; i < todaColaGuardia.size(); i++) {
							grupoColegiado = new ScsGrupoguardiacolegiado();
							// Aqui se buscan de los grupos existentes uno cuyo numero sea adecuado para el
							// orden cola. Si encuentra uno le settea el campo FK.
							for (ScsGrupoguardia g : gruposGuardia)
								if ((i + 1) == g.getNumerogrupo())
									grupoColegiado.setIdgrupoguardia(g.getIdgrupoguardia());
							// Aqui creamos un Grupoguardiacolegiado en caso de que no tenga uno asignado.
							if (todaColaGuardia.get(i).getIdGrupoGuardiaColegiado() == null) {
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setFechacreacion(new Date());
								grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(1);
								grupoColegiado.setFechasuscripcion(todaColaGuardia.get(i).getFechaSuscripcion());
								grupoColegiado.setIdinstitucion(idInstitucion);
								grupoColegiado.setIdpersona(Long.valueOf(todaColaGuardia.get(i).getIdPersona()));
								grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
								// NewIdDTO idP = scsGrupoguardiacolegiadoExtendsMapper.getLastId();

								/*
								 * if (idP == null) grupoColegiado.setIdgrupoguardiacolegiado((long) 1); else
								 * grupoColegiado.setIdgrupoguardiacolegiado(Long.parseLong(idP.getNewId()) +
								 * 1);
								 */

								// Si se encontro un grupo se inserta sino se crea uno nuevo y se asigna.
								if (grupoColegiado.getIdgrupoguardia() != null)
									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);
								else {
									grupo = new ScsGrupoguardia();

									// grupo.setIdgrupoguardia((long) idGrupo);
									grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupo.setFechamodificacion(new Date());
									grupo.setFechacreacion(new Date());
									grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setNumerogrupo(i + 1);
									grupo.setIdinstitucion(idInstitucion);

									scsGrupoguardiaExtendsMapper.insertSelective(grupo);

									grupoColegiado.setIdgrupoguardia(grupo.getIdgrupoguardia());

									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

								}

							} else {
								// En este caso el Grupoguardiacolegiado existe y solo setteamos
								// lo necesario.
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(1);
								
								scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
								scsGrupoguardiacolegiadoExample.createCriteria()
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdgrupoguardiacolegiadoEqualTo(
												Long.valueOf(todaColaGuardia.get(i).getIdGrupoGuardiaColegiado()))
										.andIdinstitucionEqualTo(idInstitucion);

								// Si previamente se le encontro grupo se inserta directamente
								if (grupoColegiado.getIdgrupoguardia() != null)
									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
								// Sino se crea uno, se asigna y entonces insertamos
								else {
									grupo = new ScsGrupoguardia();

									// grupo.setIdgrupoguardia((long) idGrupo);
									grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupo.setFechamodificacion(new Date());
									grupo.setFechacreacion(new Date());
									grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupo.setNumerogrupo(i + 1);
									grupo.setIdinstitucion(idInstitucion);

									scsGrupoguardiaExtendsMapper.insertSelective(grupo);

									grupoColegiado.setIdgrupoguardia(grupo.getIdgrupoguardia());

									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
								}
							}

						}if(!isOrdenacionManual){ 
						colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(guardiasItem.getIdGuardia(),
								guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(), ultimo, ordenaciones,
								idInstitucion.toString(), grupoUltimo, porGrupos == "1");
						for (int i = 0; i < colaGuardia.size(); i++) {
							if (colaGuardia.get(i).getNumeroGrupo() == null
									|| colaGuardia.get(i).getNumeroGrupo() == "null") {
								colaGuardia.remove(i);
							}
						}
					}
						inscritos.setInscripcionesItem(colaGuardia);
					}

				}

				LOGGER.info("searchColaGuardia() -> Salida ya con los datos recogidos");
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}

		return inscritos;
	}

	@Override
	public UpdateResponseDTO updateUltimoCola(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("updateUltimoCola() ->  Entrada al servicio para editar guardia");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateUltimoCola() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateUltimoCola() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsGuardiasturno guardia = new ScsGuardiasturno();
					guardia.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
					guardia.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
					guardia.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"updateUltimoCola() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear DatosGenerales de guardias");
					// Se obtiene de la tabla de inscripciones la fecha de suscripcion y se asigna
					// el idpersona.
					// Hacen falta ambos para definir el ultimo de la cola.
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(Long.valueOf(guardiasItem.getIdPersonaUltimo()))
							.andFechasuscripcionIsNotNull().andFechabajaIsNull().andFechavalidacionIsNotNull()
							.andFechadenegacionIsNull();
					List<ScsInscripcionguardia> inscripciones = scsInscripcionguardiaExtendsMapper
							.selectByExample(example);
					if (!inscripciones.isEmpty()) {
						guardia.setFechasuscripcionUltimo(inscripciones.get(0).getFechasuscripcion());
						guardia.setIdpersonaUltimo(Long.valueOf(guardiasItem.getIdPersonaUltimo()));
						// if(!UtilidadesString.esCadenaVacia(guardiasItem.getIdGrupoUltimo()))
						guardia.setIdgrupoguardiaUltimo(guardiasItem.getIdGrupoUltimo() == null ? null
								: Long.valueOf(guardiasItem.getIdGrupoUltimo()));

						response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);
					} else
						throw new Exception("No se encontró la inscripcion a la que asignarle último");
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateUltimoCola() -> Salida del servicio para editar guardia");

		return updateResponseDTO;

	}

	@Override
	public GuardiasDTO resumenIncompatibilidades(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasItem> guardias = new ArrayList<GuardiasItem>();
		List<GuardiasItem> guardias2 = new ArrayList<GuardiasItem>();
		GuardiasDTO guardiaDTO = new GuardiasDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem,
						idInstitucion.toString());
//				guardiasItem.setIdGuardia("358");//borrar
//				guardiasItem.setIdTurno("177");//borrar
//				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem,
//						"2003"); //borrar
				String numIcompatibilidades = guardias.get(0).getIncompatibilidades();
				guardias2 = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades2(guardiasItem,
						idInstitucion.toString());
				guardias2.forEach(guardia -> {
					guardia.setIncompatibilidades(numIcompatibilidades);
					guardia.setTipoDia(("Selección: Labor. " + guardia.getSeleccionLaborables() + ", Fest. "
							+ guardia.getSeleccionFestivos()).replace("null", ""));
				});
				guardiaDTO.setGuardiaItems(guardias);
				LOGGER.info("resumenIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}

		return guardiaDTO;
	}

	@Override
	public TurnosDTO resumenTurno(String idTurno, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<TurnosItem> turnos = new ArrayList<TurnosItem>();
		TurnosDTO turnoDTO = new TurnosDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"resumenTurno() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"resumenTurno() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("resumenTurno() -> Entrada para obtener los resumen turno");

				turnos = scsTurnosExtendsMapper.resumenTurnoColaGuardia(idTurno, idInstitucion.toString());
				List<TurnosItem> partidoJudicial = scsSubzonapartidoExtendsMapper.getPartidoJudicialTurno(
						turnos.get(0).getIdzona(), turnos.get(0).getIdzubzona(), idInstitucion.toString());
				if (partidoJudicial.size() > 0)
					turnos.get(0).setPartidoJudicial(partidoJudicial.get(0).getPartidoJudicial());
				turnoDTO.setTurnosItems(turnos);
				LOGGER.info("resumenTurno() -> Salida ya con los datos recogidos");
			}
		}

		return turnoDTO;
	}

	// @Override
	// Antigua función de guardado
	public UpdateResponseDTO guardarColaGuardias2(List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request) {
		LOGGER.info("guardarColaGuardias() ->  Entrada al servicio para editar guardia");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					GuardiasItem guardiasItem = new GuardiasItem();
					if (inscripciones != null && inscripciones.size() > 0) {
						guardiasItem.setIdTurno(inscripciones.get(0).getIdturno());
						guardiasItem.setIdGuardia(inscripciones.get(0).getIdGuardia());
					}
					List<InscripcionGuardiaItem> inscripcionesGrupoNuevo = new ArrayList<InscripcionGuardiaItem>();
					// Obtenemos el ultimo id generado en los grupos
					// NewIdDTO idGrupoDTO = scsGrupoguardiaExtendsMapper.getLastId();
					Integer idGrupo = 0;

					ScsGrupoguardia grupo = null;
					ScsGrupoguardiacolegiado grupoColegiado = null;
					ScsGrupoguardiacolegiadoExample scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();

					/*
					 * if (idGrupoDTO != null) idGrupo = Integer.valueOf(idGrupoDTO.getNewId());
					 */

					// Comprobamos si hay algun grupo nuevo
					ScsGrupoguardiaExample grupoGuardiaExample = new ScsGrupoguardiaExample();
					if (guardiasItem.getIdGuardia() != null && guardiasItem.getIdTurno() != null)
						grupoGuardiaExample.createCriteria()
								.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
								.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGrupoguardia> gruposExistentes = scsGrupoguardiaExtendsMapper
							.selectByExample(grupoGuardiaExample);
					List<ScsGrupoguardia> grupoPerteneciente = null;
					for (int i = 0; i < inscripciones.size(); i++) {

						InscripcionGuardiaItem element = inscripciones.get(i);

						// int updateOrdenSinGrupo =
						// scsInscripcionguardiaExtendsMapper.updateOrdenInscripciones(element.getIdturno(),
						// element.getIdGuardia(), element.getIdPersona(), idInstitucion.toString(),
						// element.getOrdenBD());
						// Vemos si la inscripcion tiene grupo y si existe
						if (element.getNumeroGrupo() != null && !"".equals(element.getNumeroGrupo()))
							grupoPerteneciente = gruposExistentes.stream()
									.filter(it -> Integer.valueOf(element.getNumeroGrupo()).equals(it.getNumerogrupo()))
									.collect(Collectors.toList());
						// Si no pertenece a ninguno se añade a la lista encargada de los nuevos grupos.
						if (grupoPerteneciente == null || grupoPerteneciente.size() == 0) {
							inscripcionesGrupoNuevo.add(element); // Aqui vemos si hay alguno nuevo y si lo hay
						} else {
							grupoColegiado = new ScsGrupoguardiacolegiado();
							// Si tenia Grupoguardiacolegiado, es decir, no tenia ni grupo ni orden
							// creamos uno. Para crear es importante la Fechasuscripcion que se obtiene
							// con mybatis
							if (element.getIdGrupoGuardiaColegiado() == null
									|| element.getIdGrupoGuardiaColegiado().equals("")) {
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setFechacreacion(new Date());
								grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(Integer.valueOf(element.getOrden()));
								grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());
								grupoColegiado.setIdinstitucion(idInstitucion);
								grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
								grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupoColegiado.setIdpersona(Long.valueOf(element.getIdPersona()));

								ScsInscripcionguardiaExample inscripExample = new ScsInscripcionguardiaExample();
								inscripExample.createCriteria()
										.andIdpersonaEqualTo(Long.valueOf(element.getIdPersona()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdinstitucionEqualTo(idInstitucion).andFechasuscripcionIsNotNull()
										.andFechabajaIsNull().andFechavalidacionIsNotNull().andFechadenegacionIsNull();
								ScsInscripcionguardia inscripcionFechSus = scsInscripcionguardiaExtendsMapper
										.selectByExample(inscripExample).get(0);

								grupoColegiado.setFechasuscripcion(inscripcionFechSus.getFechasuscripcion());

								response = scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

							} else {

								// Aqui cambiamos los datos necesarios del grupo guardia en caso de que
								// haya que actualizar.
								grupoColegiado.setFechamodificacion(new Date());
								grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupoColegiado.setOrden(Integer.valueOf(element.getOrden()));

								scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
								scsGrupoguardiacolegiadoExample.createCriteria()
										.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
										.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
										.andIdgrupoguardiacolegiadoEqualTo(
												Long.valueOf(element.getIdGrupoGuardiaColegiado()))
										.andIdinstitucionEqualTo(idInstitucion);

								grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());

								response = scsGrupoguardiacolegiadoExtendsMapper
										.updateByExampleSelective(grupoColegiado, scsGrupoguardiacolegiadoExample);
							}
						}

						String fS = null;
						String fSoK = null;
						if (element.getUltimoCola() != null && element.getUltimoCola() != 0) {
							if (element.getFechaSuscripcion() != null) {
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								fSoK = formatter.format(element.getFechaSuscripcion());
							}

							try {
								int resultimo = scsGuardiasturnoExtendsMapper.cambiarUltimoCola4(
										idInstitucion.toString(), element.getIdTurno(), element.getIdGuardia(),
										element.getIdPersona(), element.getIdGrupoGuardiaColegiado(), fSoK,
										usuarios.get(0).getIdusuario().toString());
							} catch (Exception e) {

							}
						}
					}

					// Si hay grupos nuevos los creamos
					if (inscripcionesGrupoNuevo != null && inscripcionesGrupoNuevo.size() > 0) {
						Integer ultimoGrupo = 1;

						for (ScsGrupoguardia it : gruposExistentes)
							if (it.getNumerogrupo() > ultimoGrupo)
								ultimoGrupo = (int) it.getNumerogrupo();

						for (int i = 0; i < inscripcionesGrupoNuevo.size(); i++) {
							if (inscripcionesGrupoNuevo.get(i).getNumeroGrupo() == null
									|| inscripcionesGrupoNuevo.get(i).getNumeroGrupo().equals("")) {
								Long idGGC = null;
								if (inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado() != null) {
									idGGC = Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado());
									ScsGrupoguardiacolegiadoExample grupoGuardiaColegiadoExample = new ScsGrupoguardiacolegiadoExample();
									grupoGuardiaColegiadoExample.createCriteria()
											.andIdgrupoguardiacolegiadoEqualTo(idGGC);
									scsGrupoguardiacolegiadoExtendsMapper.deleteByExample(grupoGuardiaColegiadoExample);
								}

							} else {
								idGrupo += 1;
								ultimoGrupo += 1;
								grupo = new ScsGrupoguardia();

								grupo.setIdgrupoguardia((long) idGrupo);
								grupo.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
								grupo.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
								grupo.setFechamodificacion(new Date());
								grupo.setFechacreacion(new Date());
								grupo.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
								grupo.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
								grupo.setNumerogrupo(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getNumeroGrupo()));
								grupo.setIdinstitucion(idInstitucion);

								response = scsGrupoguardiaExtendsMapper.insert(grupo);

								grupoColegiado = new ScsGrupoguardiacolegiado();
								// Aqui se crea un nuevo grupo colegiado en caso de que el recibido no tenga
								// idgrupoguardiacolegiado
								if (inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado() == null) {
									grupoColegiado.setFechamodificacion(new Date());
									grupoColegiado.setFechacreacion(new Date());
									grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									Integer orden = null;
									if (inscripcionesGrupoNuevo.get(i).getOrden() != null) {
										orden = Integer.valueOf(inscripcionesGrupoNuevo.get(i).getOrden());
									}
									grupoColegiado.setOrden(orden);
									grupoColegiado.setIdgrupoguardia(grupoPerteneciente.get(0).getIdgrupoguardia());
									grupoColegiado.setIdinstitucion(idInstitucion);
									grupoColegiado.setIdguardia(Integer.valueOf(guardiasItem.getIdGuardia()));
									grupoColegiado.setIdturno(Integer.valueOf(guardiasItem.getIdTurno()));
									grupoColegiado
											.setIdpersona(Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdPersona()));

									ScsInscripcionguardiaExample inscripExample = new ScsInscripcionguardiaExample();
									inscripExample.createCriteria()
											.andIdpersonaEqualTo(
													Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdPersona()))
											.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
											.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
											.andIdinstitucionEqualTo(idInstitucion).andFechasuscripcionIsNotNull()
											.andFechabajaIsNull().andFechavalidacionIsNotNull()
											.andFechadenegacionIsNull();
									ScsInscripcionguardia inscripcionFechSus = scsInscripcionguardiaExtendsMapper
											.selectByExample(inscripExample).get(0);

									grupoColegiado.setFechasuscripcion(inscripcionFechSus.getFechasuscripcion());

									response = scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

								} else {
									// Aqui cambiamos los datos necesarios del grupo guardia y lo actualizamos.
									grupoColegiado.setFechamodificacion(new Date());
									grupoColegiado.setFechacreacion(new Date());
									grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());

									scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();
									scsGrupoguardiacolegiadoExample.createCriteria()
											.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
											.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
											.andIdgrupoguardiacolegiadoEqualTo(Long.valueOf(
													inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado()))
											.andIdinstitucionEqualTo(idInstitucion);

									grupoColegiado.setIdgrupoguardia((long) idGrupo);
									grupoColegiado.setOrden(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getOrden()));

									response = scsGrupoguardiacolegiadoExtendsMapper
											.updateByExampleSelective(grupoColegiado, scsGrupoguardiacolegiadoExample);
								}
							}
						}
					}
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("guardarColaGuardia() -> Salida del servicio para guardar la cola de guardia");

		return updateResponseDTO;
	}
	
	public ComboDTO getComboDiasDisponiblesGC(HttpServletRequest request, GuardiasItem guardiaItem) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getComboDiasDisponiblesGC() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getComboDiasDisponiblesGC() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					List<ComboItem> combosItems = new ArrayList<ComboItem>();
					
					ArrayList<ArrayList<String>> fechasDisponibles = this.obtenerPeriodosGuardiaFijo(guardiaItem, idInstitucion.toString());
					for(ArrayList<String> listaPe : fechasDisponibles) {
						ComboItem comboItem = new ComboItem();
						String listString = String.join(", ", listaPe);
						comboItem.setLabel(listString);
						comboItem.setValue(listString);
						combosItems.add(comboItem);
					}
					comboDTO.setCombooItems(combosItems);
					

					if (combosItems == null || combosItems.isEmpty()) {

						error.setCode(200);
						error.setMessage("sjcs.guardia.asistencia.nohayguardia");
						error.description("sjcs.guardia.asistencia.nohayguardia");
						comboDTO.setError(error);

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getComboDiasDisponiblesGC() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al obtener combo fechas disponibles GC " + e);
			error.description("Error al obtener combo fechas disponibles GC " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones,
			HttpServletRequest request) {
		LOGGER.info(
				"guardarColaGuardias() / guardiasServiceImpl.guardarColaGuardias() -> Entrada al servicio que guarda la cola de guardias");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

		LOGGER.info(
				"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		LOGGER.info(
				"guardarColaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			try {
				// Comprobamos cual es el último grupo para cambiar el último letrado
				OptionalInt maxGrupo = inscripciones.stream()
						.filter(item -> !UtilidadesString.esCadenaVacia(item.getNumeroGrupo()))
						.mapToInt(item -> Integer.parseInt(item.getNumeroGrupo())).max();
				OptionalInt maxOrden;
				try{
					maxOrden = inscripciones.stream()
						.filter(item -> !UtilidadesString.esCadenaVacia(item.getNumeroGrupo()) && maxGrupo.isPresent()
								&& Integer.parseInt(item.getNumeroGrupo()) == maxGrupo.getAsInt())
						.mapToInt(item -> Integer.parseInt(item.getOrden())).max();
				}catch(Exception e) {
					maxOrden = maxGrupo;
				}
				for (InscripcionGuardiaItem item : inscripciones) {
					if (UtilidadesString.esCadenaVacia(item.getOrden())
							&& UtilidadesString.esCadenaVacia(item.getNumeroGrupo())
							&& !UtilidadesString.esCadenaVacia(item.getIdGrupoGuardiaColegiado())) {
						// Elimina grupo guardia colegiado
						scsGrupoguardiacolegiadoExtendsMapper
								.deleteByPrimaryKey(Long.parseLong(item.getIdGrupoGuardiaColegiado()));
					} else if (!UtilidadesString.esCadenaVacia(item.getOrden())
							&& !UtilidadesString.esCadenaVacia(item.getNumeroGrupo())) {
						// Inserta grupo guardia
						try {
							// Se guarda el último letrado en caso de que coincida el letrado con el letrado
							// del ultimo grupo de la guardia
							if (item.getFechaSuscripcion() != null && maxGrupo.isPresent() && maxOrden.isPresent()
									&& item.getUltimoCola() != null && item.getUltimoCola() == 1) {
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String fSoK = formatter.format(item.getFechaSuscripcion());

								scsGuardiasturnoExtendsMapper.cambiarUltimoCola4(idInstitucion.toString(),
										item.getIdTurno(), item.getIdGuardia(), item.getIdPersona(),
										item.getIdGrupoGuardiaColegiado(), fSoK,
										usuarios.get(0).getIdusuario().toString());
							}

							insertaGrupoGuardiaColegiado(idInstitucion, Integer.parseInt(item.getIdturno()),
									Integer.parseInt(item.getIdGuardia()), Long.parseLong(item.getIdPersona()),
									item.getFechaSuscripcion(), Integer.parseInt(item.getNumeroGrupo()),
									Integer.parseInt(item.getOrden()),
									item.getIdGrupoGuardiaColegiado(), usuarios.get(0).getIdusuario());
						} catch (Exception e) {
							LOGGER.warn("Error al insertar el grupo guardia colegiado", e);
						}
					}else {//No es guardia por grupos
						if(item.getFechaSuscripcion() != null && item.getUltimoCola() != null && item.getUltimoCola().equals(1)) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String fSoK = formatter.format(item.getFechaSuscripcion());

							ScsGuardiasturnoKey key = new ScsGuardiasturnoKey();
							key.setIdinstitucion(idInstitucion);
							key.setIdturno(Integer.valueOf(item.getIdTurno()));
							key.setIdguardia(Integer.valueOf(item.getIdGuardia()));
							ScsGuardiasturno regGuardia = scsGuardiasturnoExtendsMapper.selectByPrimaryKey(key );
							
							regGuardia.setIdpersonaUltimo(Long.valueOf(item.getIdPersona()));
							regGuardia.setFechasuscripcionUltimo(item.getFechaSuscripcion());
							regGuardia.setUsumodificacion(usuarios.get(0).getIdusuario());
							regGuardia.setFechamodificacion(new Date());
							
							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(regGuardia);
						}
					}
				}

				LOGGER.info(
						"guardarColaGuardias() / guardiasServiceImpl.guardarColaGuardias() -> Entrando a busqueda de grupos sin letrados");

				for (InscripcionGuardiaItem item : inscripciones) {
					Integer idTurno = Integer.parseInt(item.getIdturno());
					Integer idGuardia = Integer.parseInt(item.getIdGuardia());
					Long idGrupoGuardia = null;
					if(item.getIdGrupoGuardia()!=null) {
						idGrupoGuardia = Long.parseLong(item.getIdGrupoGuardia());
					}
					
					if(idGrupoGuardia != null) {
						// Cuando se haya recorrido la lista de cambios hay que comprobar que los grupos
						// se sigan usando porque igual
						// se pueden eliminar los que ya no se usen
	
						ScsGrupoguardiaExample grupoExample = new ScsGrupoguardiaExample();
						grupoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(idTurno)
								.andIdguardiaEqualTo(idGuardia).andIdgrupoguardiaEqualTo(idGrupoGuardia);
	
						List<ScsGrupoguardia> grupos = scsGrupoguardiaExtendsMapper.selectByExample(grupoExample);
						for (ScsGrupoguardia grupo : grupos) {
							ScsGrupoguardiacolegiadoExample grupoColegiadoExample = new ScsGrupoguardiacolegiadoExample();
							grupoColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(idTurno).andIdguardiaEqualTo(idGuardia)
									.andIdgrupoguardiaEqualTo(grupo.getIdgrupoguardia());
	
							long numGrupoGuardiaColegiados = scsGrupoguardiacolegiadoExtendsMapper
									.countByExample(grupoColegiadoExample);
							if (numGrupoGuardiaColegiados == 0) {
								scsGrupoguardiaExtendsMapper.deleteByPrimaryKey(grupo.getIdgrupoguardia());
							}
						}
					}
				}

				LOGGER.info(
						"guardarColaGuardias() / guardiasServiceImpl.guardarColaGuardias() <- Saliendo de busqueda de grupos sin letrados");
			} catch (Exception e) {
				LOGGER.error(e);
				response = 0;
				error.setCode(400);
				error.setDescription("general.mensaje.error.bbdd");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (response == 1) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		LOGGER.info(
				"guardarColaGuardias() / guardiasServiceImpl.guardarColaGuardias() <- Saliendo del servicio que guarda la cola de guardias");
		return updateResponseDTO;
	}

	private void insertaGrupoGuardiaColegiado(Short idInstitucion, Integer idTurno, Integer idGuardia, Long idPersona,
			Date fechaValidacion, Integer numeroGrupo, Integer ordenGrupo, String idGrupoGuardiaColegiado,
			Integer idUsuario) {
		Long idGrupoGuardia = null;

		// Buscamos el idGrupo que corresponde con el numero del grupo
		ScsGrupoguardiaExample grupoExample = new ScsGrupoguardiaExample();
		grupoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(idTurno)
				.andIdguardiaEqualTo(idGuardia).andNumerogrupoEqualTo(numeroGrupo);

		List<ScsGrupoguardia> gruposGuardia = scsGrupoguardiaExtendsMapper.selectByExample(grupoExample);

		if (gruposGuardia != null && !gruposGuardia.isEmpty()) {
			idGrupoGuardia = gruposGuardia.get(0).getIdgrupoguardia();
		} else {
			// Si el grupo no existe lo creamos y lo usamos para meter la guardia
			idGrupoGuardia = Long.parseLong(scsGrupoguardiaExtendsMapper.getLastId().getNewId()) + 1;

			ScsGrupoguardia recordGrupoGuardia = new ScsGrupoguardia();
			recordGrupoGuardia.setIdgrupoguardia(idGrupoGuardia);
			recordGrupoGuardia.setIdinstitucion(idInstitucion);
			recordGrupoGuardia.setIdturno(idTurno);
			recordGrupoGuardia.setIdguardia(idGuardia);
			recordGrupoGuardia.setNumerogrupo(numeroGrupo);
			recordGrupoGuardia.setFechacreacion(new Date());
			recordGrupoGuardia.setUsucreacion(idUsuario);
			recordGrupoGuardia.setFechamodificacion(new Date());
			recordGrupoGuardia.setUsumodificacion(idUsuario);

			scsGrupoguardiaExtendsMapper.insert(recordGrupoGuardia);
		}

		boolean nuevoGrupoGuardiaColegiado = false;
		if (idGrupoGuardiaColegiado == null || idGrupoGuardiaColegiado.isEmpty()) {
			nuevoGrupoGuardiaColegiado = true;
			idGrupoGuardiaColegiado = scsGrupoguardiacolegiadoExtendsMapper.getLastId().getNewId();
		}

		ScsGrupoguardiacolegiado recordGrupoGuardiaColegiado = new ScsGrupoguardiacolegiado();
		
		if (nuevoGrupoGuardiaColegiado) {
			recordGrupoGuardiaColegiado.setIdgrupoguardiacolegiado(Long.parseLong(idGrupoGuardiaColegiado) + 1);
		} else {
			recordGrupoGuardiaColegiado.setIdgrupoguardiacolegiado(Long.parseLong(idGrupoGuardiaColegiado));
		}
		
		recordGrupoGuardiaColegiado.setIdpersona(idPersona);
		recordGrupoGuardiaColegiado.setOrden(ordenGrupo);
		recordGrupoGuardiaColegiado.setIdgrupoguardia(idGrupoGuardia);
		recordGrupoGuardiaColegiado.setIdinstitucion(idInstitucion);
		recordGrupoGuardiaColegiado.setIdturno(idTurno);
		recordGrupoGuardiaColegiado.setIdguardia(idGuardia);
		recordGrupoGuardiaColegiado.setFechacreacion(new Date());
		recordGrupoGuardiaColegiado.setUsucreacion(idUsuario);
		recordGrupoGuardiaColegiado.setFechasuscripcion(fechaValidacion);
		recordGrupoGuardiaColegiado.setFechamodificacion(new Date());
		recordGrupoGuardiaColegiado.setUsumodificacion(idUsuario);

		if (nuevoGrupoGuardiaColegiado) {
			scsGrupoguardiacolegiadoMapper.insert(recordGrupoGuardiaColegiado);
		} else {
			scsGrupoguardiacolegiadoMapper.updateByPrimaryKey(recordGrupoGuardiaColegiado);
		}

		// Actualizar cola guardia para evitar que el ultimo grupo quede a caballo
		actualizarColaGuardiaConUltimoColegiadoPorGrupo(idInstitucion, idTurno, idGuardia);
	}

	@Override
	public LetradosGuardiaDTO letradosGuardia(String idTurno, String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		LetradosGuardiaDTO letradosGuardiaDTO = new LetradosGuardiaDTO();
		Error error = new Error();

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"letradosGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"letradosGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					List<LetradoGuardiaItem> letradosGuardiaItem = scsGuardiasturnoExtendsMapper
							.searchLetradosGuardia(Short.toString(idInstitucion), idTurno, idGuardia);

					letradosGuardiaDTO.setLetradosGuardiaItem(letradosGuardiaItem);

				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"GuardiasServiceImpl.letradosGuardia() -> Se ha producido un error en consultando los letrados", e);

			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());

			letradosGuardiaDTO.setError(error);
		}

		return letradosGuardiaDTO;
	}

	@Override
	public IncompatibilidadesDTO getIncompatibilidades(IncompatibilidadesDatosEntradaItem incompBody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<IncompatibilidadesItem> incompatibilidades = new ArrayList<IncompatibilidadesItem>();
		List<IncompatibilidadesItem> incompatibilidadesAux = new ArrayList<IncompatibilidadesItem>();
		IncompatibilidadesDTO inc = new IncompatibilidadesDTO();
		String idGuardia = "";
		Integer tamMaximo;
		List<GenParametros> tamMax = new ArrayList<>();
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getIncompatibilidades() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getIncompatibilidades() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}
//				List<String> idTurnoIncpList = Arrays.asList(incompBody.getIdTurno().split("\\s*,\\s*"));
//				List<String> idGuardiaIncpList = new ArrayList<>();
//				if (incompBody.getIdGuardia() != null) {
//					 idGuardiaIncpList = Arrays.asList(incompBody.getIdGuardia().split("\\s*,\\s*"));
//				}else {
//					idGuardiaIncpList = null;
//				}
//				for (int j = 0; j < idTurnoIncpList.size(); j++) {
//					IncompatibilidadesDatosEntradaItem incompBodyAux = incompBody;
//					if(idGuardiaIncpList != null)
//					incompBodyAux.setIdGuardia(idGuardiaIncpList.get(j));
//					incompBodyAux.setIdTurno(idTurnoIncpList.get(j));
//					incompatibilidadesAux = scsIncompatibilidadguardiasExtendsMapper.getListadoIncompatibilidades(incompBodyAux,
//							idInstitucion.toString(), tamMaximo);
//					incompatibilidadesAux.forEach(iA -> {
//						incompatibilidades.add(iA);
//					});
//				}
				incompatibilidades = scsIncompatibilidadguardiasExtendsMapper.getListadoIncompatibilidades(incompBody,
						idInstitucion.toString(), tamMaximo);
				if (tamMaximo != null && incompatibilidades != null && !incompatibilidades.isEmpty()
						&& incompatibilidades.size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					inc.setError(error);
					incompatibilidades.remove(incompatibilidades.size() - 1);
				}
				LOGGER.info("getIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		inc.setIncompatibilidadesItem(incompatibilidades);
		return inc;
	}

	@Override
	public ComboIncompatibilidadesResponse getCombo(
			ComboIncompatibilidadesDatosEntradaItem comboIncompatibilidadesDatosEntradaItem,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		List<String> comboGuardiasIncLabels = new ArrayList<String>();
		List<String> comboGuardiasIncValues = new ArrayList<String>();
		ComboIncompatibilidadesResponse response = new ComboIncompatibilidadesResponse();

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni)
				.andIdinstitucionEqualTo(Short.valueOf(comboIncompatibilidadesDatosEntradaItem.getIdInstitucion()));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		comboGuardiasIncLabels = scsIncompatibilidadguardiasExtendsMapper.getListaLabelsGuardiasInc(
				comboIncompatibilidadesDatosEntradaItem.getIdInstitucion(),
				comboIncompatibilidadesDatosEntradaItem.getIdTipoGuardia(),
				comboIncompatibilidadesDatosEntradaItem.getIdTurno(), usuarios.get(0).getIdusuario(),
				comboIncompatibilidadesDatosEntradaItem.getIdPartidaPresupuestaria());
		comboGuardiasIncValues = scsIncompatibilidadguardiasExtendsMapper.getListaValueGuardiasInc(
				comboIncompatibilidadesDatosEntradaItem.getIdInstitucion(),
				comboIncompatibilidadesDatosEntradaItem.getIdTipoGuardia(),
				comboIncompatibilidadesDatosEntradaItem.getIdTurno(), usuarios.get(0).getIdusuario(),
				comboIncompatibilidadesDatosEntradaItem.getIdPartidaPresupuestaria());

		response.setLabels(comboGuardiasIncLabels);
		response.setValues(comboGuardiasIncValues);
		return response;
	}

	@Transactional
	@Override
	public DeleteResponseDTO deleteIncompatibilidades(
			List<DeleteIncompatibilidadesDatosEntradaItem> deleteIncompatibilidadesBody, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 1;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("deleteIncompatibilidades() -> Entrada para borrar las incompatibilidades");
				for (int i = 0; i < deleteIncompatibilidadesBody.size(); i++) {
					DeleteIncompatibilidadesDatosEntradaItem incompatibilidad = deleteIncompatibilidadesBody.get(i);
					if (!UtilidadesString.esCadenaVacia(incompatibilidad.getIdTurno())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdInstitucion())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardia())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdTurnoIncompatible())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardiaIncompatible())) {
						// Doble borrado
						scsIncompatibilidadguardiasExtendsMapper.deleteIncompatibilidades(incompatibilidad.getIdTurno(),
								incompatibilidad.getIdInstitucion(), incompatibilidad.getIdGuardia(),
								incompatibilidad.getIdTurnoIncompatible(), incompatibilidad.getIdGuardiaIncompatible());
					} else if (!UtilidadesString.esCadenaVacia(incompatibilidad.getIdTurno())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdInstitucion())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardia())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardiaIncompatible())) {
						
						List<String> turnoIncByIdGuardiaInc = scsIncompatibilidadguardiasExtendsMapper.getIdTurnoIncByIdGuardiaInc(incompatibilidad.getIdGuardiaIncompatible(), incompatibilidad.getIdInstitucion());
						
						scsIncompatibilidadguardiasExtendsMapper.deleteIncompatibilidades(incompatibilidad.getIdTurno(),
								incompatibilidad.getIdInstitucion(), incompatibilidad.getIdGuardia(),
								turnoIncByIdGuardiaInc.get(0), incompatibilidad.getIdGuardiaIncompatible());
					}
				}
				LOGGER.info("deleteIncompatibilidades() -> Salida ya con los datos recogidos");
			} else {
				response = 0;
			}
		}

		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteIncompatibilidades() -> OK. Delete para incompatibilidades realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteIncompatibilidades() -> KO. Delete para incompatibilidades NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteIncompatibilidades() -> Salida del servicio para eliminar incompatibilidades");
		return deleteResponseDTO;
	}

	@Transactional
	@Override
	public DeleteResponseDTO saveIncompatibilidades(List<SaveIncompatibilidadesDatosEntradaItem> incompatibilidadesBody,
			HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String idInstitucion = UserTokenUtils.getInstitucionFromJWTTokenAsString(token);
		String idGuardia = "";
		String idTurno = "";
		String idTurnoIncompatible = "";
		String idGuardiaIncompatible = "";
		List<String> idGuardiaIncompatibleList = new ArrayList<>();

		if (idInstitucion != null) {
			try {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"saveIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (usuarios != null && usuarios.size() > 0 && !incompatibilidadesBody.isEmpty()) {
					LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");
					for (int i = 0; i < incompatibilidadesBody.size(); i++) {
						SaveIncompatibilidadesDatosEntradaItem incompatibilidad = incompatibilidadesBody.get(i);
						idInstitucion = incompatibilidad.getIdInstitucion();
						idTurno = incompatibilidad.getIdTurno();
						if (idTurno == null || idTurno.isEmpty() && incompatibilidad.getNombreTurno() != null) {
							idTurno = scsIncompatibilidadguardiasExtendsMapper
									.getIdTurnoFromNombreTurno(incompatibilidad.getNombreTurno());
						}

						idGuardia = incompatibilidad.getIdGuardia();
						if (idGuardia == null || idGuardia.isEmpty() && incompatibilidad.getNombreGuardia() != null) {
							idGuardia = scsIncompatibilidadguardiasExtendsMapper
									.getIdGuardiaFromNombreGuardia(incompatibilidad.getNombreGuardia());
						}

						idGuardiaIncompatible = incompatibilidad.getIdGuardiaIncompatible();
						if (idGuardiaIncompatible == null || idGuardiaIncompatible.isEmpty()) {
							incompatibilidad.getNombreGuardiaIncompatible().forEach(gi -> {
								String idGuardiaIncompatibleAux = scsIncompatibilidadguardiasExtendsMapper
										.getIdGuardiaIncompatibleFromNombreGuardia(gi);
								idGuardiaIncompatibleList.add(idGuardiaIncompatibleAux);
							});
							if (idGuardiaIncompatibleList.size() > 0)
								idGuardiaIncompatible = idGuardiaIncompatibleList.get(0);

						}

						idTurnoIncompatible = incompatibilidad.getIdTurnoIncompatible();
						if (idTurnoIncompatible == null || idTurnoIncompatible.isEmpty()) {
							if (incompatibilidad.getNombreTurnoIncompatible() != null
									&& !incompatibilidad.getNombreTurnoIncompatible().isEmpty()) {
								idTurnoIncompatible = scsIncompatibilidadguardiasExtendsMapper
										.getIdTurnoIncompatibleFromNombreTurno(
												incompatibilidad.getNombreTurnoIncompatible());
							} else {
								String idInstitucionTurno = idInstitucion;
								idTurnoIncompatible = Stream.of(idGuardiaIncompatible.split("\\s*,\\s*"))
										.flatMap(idGuardiaItem -> scsIncompatibilidadguardiasExtendsMapper
												.getIdTurnoIncByIdGuardiaInc(idGuardiaItem, idInstitucionTurno)
												.stream())
										.collect(Collectors.joining(","));
							}
						}
						
						List<String> idTurnoIncpList = Arrays.asList(idTurnoIncompatible.split("\\s*,\\s*"));
						List<String> idGuardiaIncpList = Arrays.asList(idGuardiaIncompatible.split("\\s*,\\s*"));
						if(idGuardiaIncpList.size() > idTurnoIncpList.size()) {
							String idInstitucionTurno = idInstitucion;
							idTurnoIncompatible = Stream.of(idGuardiaIncompatible.split("\\s*,\\s*"))
									.flatMap(idGuardiaItem -> scsIncompatibilidadguardiasExtendsMapper
											.getIdTurnoIncByIdGuardiaInc(idGuardiaItem, idInstitucionTurno)
											.stream())
									.collect(Collectors.joining(","));
						}
						idTurnoIncpList = Arrays.asList(idTurnoIncompatible.split("\\s*,\\s*"));

						String pattern = "dd/MM/YY";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						String fechaModificacion = simpleDateFormat.format(new Date());
						if (idTurno != null && !idTurno.isEmpty() && incompatibilidad.getIdInstitucion() != null
								&& !incompatibilidad.getIdInstitucion().isEmpty() && idGuardia != null
								&& !idGuardia.isEmpty() && idGuardiaIncompatible != null
								&& !idGuardiaIncompatible.isEmpty()) {
							for (int j = 0; j < idGuardiaIncpList.size(); j++) {
								int existe = scsIncompatibilidadguardiasExtendsMapper.checkIncompatibilidadesExists(
										idTurno, incompatibilidad.getIdInstitucion(), idGuardia, idTurnoIncpList.get(j),
										idGuardiaIncpList.get(j).toString());
								if (existe == 2) {
									// existe en ambas direcciones - la actualizamos
									ScsIncompatibilidadguardias ig = new ScsIncompatibilidadguardias();
									ig.setIdturno(Integer.parseInt(idTurno));
									ig.setIdguardia(Integer.parseInt(idGuardia));
									ig.setDiasseparacionguardias(
											Short.parseShort(incompatibilidad.getDiasSeparacionGuardias()));
									ig.setIdturnoIncompatible(Integer.parseInt(idTurnoIncpList.get(j)));
									ig.setIdguardiaIncompatible(Integer.parseInt(idGuardiaIncpList.get(j).toString()));
									ig.setMotivos(incompatibilidad.getMotivos());
									ig.setFechamodificacion(
											new SimpleDateFormat("dd/MM/yyyy").parse((fechaModificacion)));
									ig.setIdinstitucion(Short.parseShort(incompatibilidad.getIdInstitucion()));
									scsIncompatibilidadguardiasExtendsMapper.updateByPrimaryKeySelective(ig);

									// Lógica para el registro en la otra dirección.
									ig.setIdturno(Integer.parseInt(idTurnoIncpList.get(j)));
									ig.setIdguardia(Integer.parseInt(idGuardiaIncpList.get(j).toString()));
									ig.setIdturnoIncompatible(Integer.parseInt(idTurno));
									ig.setIdguardiaIncompatible(Integer.parseInt(idGuardia));
									scsIncompatibilidadguardiasExtendsMapper.updateByPrimaryKeySelective(ig);
									// scsIncompatibilidadguardiasExtendsMapper.updateIfExists(idTurno,
									// incompatibilidad.getIdInstitucion(), idGuardia, idTurnoIncompatible,
									// idGuardiaIncompatible, incompatibilidad.getMotivos(),
									// incompatibilidad.getDiasSeparacionGuardias(), fechaModificacion);
								}
								if (existe > 2) {
									// existe en ambas direcciones - la actualizamos

									ScsIncompatibilidadguardias ig = new ScsIncompatibilidadguardias();
									ig.setIdturno(Integer.parseInt(idTurno));
									ig.setIdguardia(Integer.parseInt(idGuardia));
									ig.setDiasseparacionguardias(
											Short.parseShort(incompatibilidad.getDiasSeparacionGuardias()));
									ig.setIdturnoIncompatible(Integer.parseInt(idTurnoIncpList.get(j)));
									ig.setIdguardiaIncompatible(Integer.parseInt(idGuardiaIncpList.get(j).toString()));
									ig.setMotivos(incompatibilidad.getMotivos());
									ig.setFechamodificacion(
											new SimpleDateFormat("dd/MM/yyyy").parse((fechaModificacion)));
									ig.setIdinstitucion(Short.parseShort(incompatibilidad.getIdInstitucion()));
									scsIncompatibilidadguardiasExtendsMapper.updateByPrimaryKeySelective(ig);

								}
								if (existe == 0) {
									// no existe - llamamos dos veces para guardar en ambas direcciones
									scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(
											Integer.parseInt(idTurno),
											Integer.parseInt(incompatibilidad.getIdInstitucion()),
											Integer.parseInt(idGuardia), Integer.parseInt(idTurnoIncpList.get(j)),
											Integer.parseInt(idGuardiaIncpList.get(j)), usuarios.get(0).getIdusuario(),
											incompatibilidad.getMotivos(),
											Integer.parseInt(incompatibilidad.getDiasSeparacionGuardias()),
											fechaModificacion);
									scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(
											Integer.parseInt(idTurnoIncpList.get(j)),
											Integer.parseInt(incompatibilidad.getIdInstitucion()),
											Integer.parseInt(idGuardiaIncpList.get(j)), Integer.parseInt(idTurno),
											Integer.parseInt(idGuardia), usuarios.get(0).getIdusuario(),
											incompatibilidad.getMotivos(),
											Integer.parseInt(incompatibilidad.getDiasSeparacionGuardias()),
											fechaModificacion);

								}
							}
						} else {
							response = 0;
						}
					}
					LOGGER.info("saveIncompatibilidades() -> Salida ya con los datos recogidos");
				}
			} catch (Exception e) {
				deleteResponseDTO.setStatus(SigaConstants.KO);
			}
		}

		// comprobacion actualización
		if (response >= 0) {
			LOGGER.info("saveIncompatibilidades() -> OK. Save para incompatibilidades realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("saveIncompatibilidades() -> KO. Save para incompatibilidades NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteSubtipoCurricular() -> Salida del servicio para guardar incompatibilidades");
		return deleteResponseDTO;
	}

	@Override
	public List<CalendariosProgDatosSalidaItem> getCalendariosProg(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CalendariosProgDatosSalidaItem calendarios = new CalendariosProgDatosSalidaItem();
		List<CalendariosProgDatosSalidaItem> calendariosList = new ArrayList<CalendariosProgDatosSalidaItem>();
		String idGuardia = "";

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getIncompatibilidades() -> Entrada para obtener las incompatibilidades");

				calendariosList = scsGuardiasturnoExtendsMapper.searchCalendarios(calendarioProgBody,
						idInstitucion.toString());

				LOGGER.info("getIncompatibilidades() -> Salida ya con los datos recogidos");

			}
		}

		return calendariosList;
	}

	@Override
	public List<GuardiaCalendarioItem> getGuardiasFromCalendar(String idCalendar, String fechaDesde, String fechaHasta,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int orden = 0;
		List<GuardiaCalendarioItem> datos = new ArrayList<GuardiaCalendarioItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getGuardiasFromCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getGuardiasFromCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendarioProgramado() -> Entrada para obtener los datos del calendario");

				datos = scsGuardiasturnoExtendsMapper.getGuardiasFromCalendar(idCalendar, idInstitucion.toString(),
						fechaDesde, fechaHasta);
				for (int i = 0; i < datos.size(); i++) {
					List<String> idCalendarioGuardia = scsGuardiasturnoExtendsMapper
							.getIdCalendarioGuardiasFromTurnosGuardiasList(datos.get(i).getIdTurno(),
									datos.get(i).getIdGuardia(), idInstitucion.toString(), fechaDesde, fechaHasta);
					if (idCalendarioGuardia.size() != 0) {
						datos.get(i).setIdCalendarioGuardia(idCalendarioGuardia.get(0));
					}
					//datos.get(i).setOrden(Integer.toString(i + 1));
				}
				LOGGER.info("getGuardiasFromCalendar() -> Salida ya con los datos recogidos");
			}
		}

		return datos;
	}

	@Override
	public List<DatosCalendarioProgramadoItem> getCalendarioProgramado(
			CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioProgramadoItem> datos = new ArrayList<DatosCalendarioProgramadoItem>();
		List<DatosCalendarioProgramadoItem> datosFull = new ArrayList<DatosCalendarioProgramadoItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendarioProgramado() -> Entrada para obtener los datos del calendario");

				// datos =
				// scsGuardiasturnoExtendsMapper.getCalendarioProgramado(calendarioProgBody,
				// idInstitucion.toString());
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				String NEW_FORMAT1 = "dd/MM/yyyy HH:mm:ss";
				// 2017-02-06 00:00:00.0
				if (calendarioProgBody.getFechaCalendarioDesde() != null) {
					String fecha = null;
					String format = NEW_FORMAT;
					if(calendarioProgBody.getFechaCalendarioDesde().length() == 29 ) 
						format = OLD_FORMAT;
					else if (calendarioProgBody.getFechaCalendarioDesde().length() == 10 )
						format = NEW_FORMAT;
					else
						format = OLD_FORMAT;
					fecha = changeDateFormat(format, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
					calendarioProgBody.setFechaCalendarioDesde(fecha);
				}
				if (calendarioProgBody.getFechaCalendarioHasta() != null) {
					String fecha = null;
					String format = NEW_FORMAT;
					if(calendarioProgBody.getFechaCalendarioHasta().length() == 29 ) 
						format = OLD_FORMAT;
					else if (calendarioProgBody.getFechaCalendarioHasta().length() == 10 )
						format = NEW_FORMAT;
					else
						format = OLD_FORMAT;
					fecha = changeDateFormat(format, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
					calendarioProgBody.setFechaCalendarioHasta(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaDesde() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaDesde());
					calendarioProgBody.setFechaProgramadaDesde(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaHasta() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaHasta());
					calendarioProgBody.setFechaProgramadaHasta(fecha);
				}
				datos = scsGuardiasturnoExtendsMapper.getCalendariosProgramadosSigaClassique(calendarioProgBody,
						idInstitucion.toString());

//				datos.forEach(d -> {
//					d.setFacturado(false);
//					String numGuardias = scsGuardiasturnoExtendsMapper.getNumGuardiasCalProg(d.getIdCalG(), d.getIdCalendarioProgramado(), d.getIdInstitucion());
//					//String numGuardias = scsGuardiasturnoExtendsMapper.getNumGuardiasCalProg2(idInstitucion.toString(), d.getIdTurno(), calendarioProgBody.getIdGuardia(), d.getIdCalendarioProgramado());
//
//					String turno = scsGuardiasturnoExtendsMapper.getTurnoCalProg( d.getIdTurno(), d.getIdCalG(), d.getIdInstitucion());
//					String guardia = scsGuardiasturnoExtendsMapper.getGuardiaCalProg( d.getIdTurno(), d.getIdGuardia(), d.getIdCalG(), d.getIdInstitucion());
//					//existen guardias de colegiado facturadas en esos calendarios
//					List<String> guardiasAsociadas =  scsGuardiasturnoExtendsMapper.getGuardiasAsociadasCalProg(d.getIdCalG(), d.getIdCalendarioProgramado(), d.getIdInstitucion());
//					if (guardiasAsociadas != null && !guardiasAsociadas.isEmpty()) {
//					guardiasAsociadas.forEach(idGuardia -> {
//						List<String> facturado = scsGuardiasturnoExtendsMapper.getFacturada(idGuardia);
//						if (facturado != null && !facturado.isEmpty()) {
//							if (facturado.get(0).equals("1")) {
//								d.setFacturado(true);
//							}
//						}
//						Integer asistenciasAsociadas = scsGuardiasturnoExtendsMapper.getAsistencias(idGuardia);
//						if (asistenciasAsociadas != null && asistenciasAsociadas != 0) {
//							d.setAsistenciasAsociadas(true);
//						}
//					});
//					}
//
//					String OLD_FORMAT2 = "yyyy-MM-dd HH:mm:ss.S";
//					String NEW_FORMAT2 = "yyyy-MM-dd";
//
//
//					if(numGuardias != null){
//						d.setNumGuardias(numGuardias);
//					}
//					if(turno != null){
//					d.setTurno(turno);
//					}
//					if(guardia != null){
//					d.setGuardia(guardia);
//					}
//					datosFull.add(d);
//				});

				LOGGER.info("getCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return datos;
	}
	
	@Override
	public List<DatosCalendarioProgramadoItem> getCalendarioProgramadoTarjeta(
			CalendariosProgDatosEntradaItem calendarioProgBody, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioProgramadoItem> datos = new ArrayList<DatosCalendarioProgramadoItem>();
		List<DatosCalendarioProgramadoItem> datosFull = new ArrayList<DatosCalendarioProgramadoItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendarioProgramado() -> Entrada para obtener los datos del calendario");

				// datos =
				// scsGuardiasturnoExtendsMapper.getCalendarioProgramado(calendarioProgBody,
				// idInstitucion.toString());
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				String NEW_FORMAT1 = "dd/MM/yyyy HH:mm:ss";
				// 2017-02-06 00:00:00.0
				if (calendarioProgBody.getFechaCalendarioDesde() != null) {
					String fecha = null;
					String format = NEW_FORMAT;
					if(calendarioProgBody.getFechaCalendarioDesde().length() == 29 ) 
						format = OLD_FORMAT;
					else if (calendarioProgBody.getFechaCalendarioDesde().length() == 10 )
						format = NEW_FORMAT;
					else
						format = OLD_FORMAT;
					fecha = changeDateFormat(format, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
					calendarioProgBody.setFechaCalendarioDesde(fecha);
				}
				if (calendarioProgBody.getFechaCalendarioHasta() != null) {
					String fecha = null;
					String format = NEW_FORMAT;
					if(calendarioProgBody.getFechaCalendarioHasta().length() == 29 ) 
						format = OLD_FORMAT;
					else if (calendarioProgBody.getFechaCalendarioHasta().length() == 10 )
						format = NEW_FORMAT;
					else
						format = OLD_FORMAT;
					fecha = changeDateFormat(format, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
					calendarioProgBody.setFechaCalendarioHasta(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaDesde() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaDesde());
					calendarioProgBody.setFechaProgramadaDesde(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaHasta() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaHasta());
					calendarioProgBody.setFechaProgramadaHasta(fecha);
				}
				datos = scsGuardiasturnoExtendsMapper.getCalendariosProgramadosSigaClassiqueTarjeta(calendarioProgBody,
						idInstitucion.toString());

//				datos.forEach(d -> {
//					d.setFacturado(false);
//					String numGuardias = scsGuardiasturnoExtendsMapper.getNumGuardiasCalProg(d.getIdCalG(), d.getIdCalendarioProgramado(), d.getIdInstitucion());
//					//String numGuardias = scsGuardiasturnoExtendsMapper.getNumGuardiasCalProg2(idInstitucion.toString(), d.getIdTurno(), calendarioProgBody.getIdGuardia(), d.getIdCalendarioProgramado());
//
//					String turno = scsGuardiasturnoExtendsMapper.getTurnoCalProg( d.getIdTurno(), d.getIdCalG(), d.getIdInstitucion());
//					String guardia = scsGuardiasturnoExtendsMapper.getGuardiaCalProg( d.getIdTurno(), d.getIdGuardia(), d.getIdCalG(), d.getIdInstitucion());
//					//existen guardias de colegiado facturadas en esos calendarios
//					List<String> guardiasAsociadas =  scsGuardiasturnoExtendsMapper.getGuardiasAsociadasCalProg(d.getIdCalG(), d.getIdCalendarioProgramado(), d.getIdInstitucion());
//					if (guardiasAsociadas != null && !guardiasAsociadas.isEmpty()) {
//					guardiasAsociadas.forEach(idGuardia -> {
//						List<String> facturado = scsGuardiasturnoExtendsMapper.getFacturada(idGuardia);
//						if (facturado != null && !facturado.isEmpty()) {
//							if (facturado.get(0).equals("1")) {
//								d.setFacturado(true);
//							}
//						}
//						Integer asistenciasAsociadas = scsGuardiasturnoExtendsMapper.getAsistencias(idGuardia);
//						if (asistenciasAsociadas != null && asistenciasAsociadas != 0) {
//							d.setAsistenciasAsociadas(true);
//						}
//					});
//					}
//
//					String OLD_FORMAT2 = "yyyy-MM-dd HH:mm:ss.S";
//					String NEW_FORMAT2 = "yyyy-MM-dd";
//
//
//					if(numGuardias != null){
//						d.setNumGuardias(numGuardias);
//					}
//					if(turno != null){
//					d.setTurno(turno);
//					}
//					if(guardia != null){
//					d.setGuardia(guardia);
//					}
//					datosFull.add(d);
//				});

				LOGGER.info("getCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return datos;
	}

	@Override
	public DatosCalendarioProgramadoItem getLastCalendarioProgramado(CalendariosProgDatosEntradaItem calendarioProgBody,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<DatosCalendarioProgramadoItem> datos = new ArrayList<DatosCalendarioProgramadoItem>();
		List<DatosCalendarioProgramadoItem> datosFull = new ArrayList<DatosCalendarioProgramadoItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getLastCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getLastCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getLastCalendarioProgramado() -> Entrada para obtener los datos del calendario");

				// datos =
				// scsGuardiasturnoExtendsMapper.getCalendarioProgramado(calendarioProgBody,
				// idInstitucion.toString());
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				String NEW_FORMAT1 = "dd/MM/yyyy HH:mm:ss";
				// 2017-02-06 00:00:00.0
				if (calendarioProgBody.getFechaCalendarioDesde() != null) {
					String fecha = null;
					if (!calendarioProgBody.getFechaCalendarioDesde().contains("T")) {
						fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
					} else {
						fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
					}

					calendarioProgBody.setFechaCalendarioDesde(fecha);
				}
				if (calendarioProgBody.getFechaCalendarioHasta() != null) {
					String fecha = null;
					if (!calendarioProgBody.getFechaCalendarioHasta().contains("T")) {
						fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
					} else {
						fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
					}
					calendarioProgBody.setFechaCalendarioHasta(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaDesde() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaDesde());
					calendarioProgBody.setFechaProgramadaDesde(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaHasta() != null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1,
							calendarioProgBody.getFechaProgramadaHasta());
					calendarioProgBody.setFechaProgramadaHasta(fecha);
				}
				datos = scsGuardiasturnoExtendsMapper.getLastCalendariosProgramadosSigaClassique(calendarioProgBody,
						idInstitucion.toString());

				LOGGER.info("getLastCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		if (datos == null || datos.size() == 0) {
			return null;
		} else {
			return datos.get(0);
		}
	}

	@Override
	public List<RangoFechasItem> getFechasProgramacionGuardia(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<RangoFechasItem> fechas = new ArrayList<RangoFechasItem>();
		List<String> idConjuntoGuardias = new ArrayList<String>();
		List<String> prueba = new ArrayList<String>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getFechasProgramacionGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getFechasProgramacionGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getFechasProgramacionGuardia() -> Entrada para obtener los datos del calendario");

				idConjuntoGuardias = scsGuardiasturnoExtendsMapper.getConjuntoGuardiasIdFromGuardiaId(idGuardia,
						idInstitucion.toString());
				if (!idConjuntoGuardias.isEmpty() && idConjuntoGuardias != null) {
					idConjuntoGuardias.forEach(idC -> {
						List<RangoFechasItem> fechaList = scsGuardiasturnoExtendsMapper
								.getFechasProgramacionFromIdConjuntoGuardia(idC, idInstitucion.toString());
						fechaList.forEach(fL -> {
							RangoFechasItem rfI = new RangoFechasItem();
							rfI.setFechaDesde(fL.getFechaDesde());
							rfI.setFechaHasta(fL.getFechaHasta());
							fechas.add(rfI);
						});

					});

				}

				LOGGER.info("getCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return fechas;
	}

	public TransactionStatus getNeTransactionProgramaciones() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setTimeout(Integer.parseInt("60"));
		def.setName("transProgramaciones");
		return transactionManagerProgramaciones.getTransaction(def);
	}

	private void rollBackProgramaciones(TransactionStatus transactionStatus) {
		if (transactionStatus != null && !transactionStatus.isCompleted()) {
			transactionManagerProgramaciones.rollback(transactionStatus);
		}
	}

	private void commitProgramaciones(TransactionStatus transactionStatus) {
		if (transactionStatus != null && !transactionStatus.isCompleted()) {
			transactionManagerProgramaciones.commit(transactionStatus);
		}
	}

	public TransactionStatus getNeTransactionCalendarios() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setTimeout(Integer.parseInt("600"));
		def.setName("transCalendarios");
		return transactionManagerCalendarios.getTransaction(def);
	}

	private void rollBackCalendarios(TransactionStatus transactionStatus) {
		if (transactionStatus != null && !transactionStatus.isCompleted()) {
			transactionManagerCalendarios.rollback(transactionStatus);
		}
	}

	private void commitCalendarios(TransactionStatus transactionStatus) {
		if (transactionStatus != null && !transactionStatus.isCompleted()) {
			transactionManagerCalendarios.commit(transactionStatus);
		}
	}

	/**
	 *
	 */
	@Override
	public DeleteResponseDTO deleteCalendariosProgramados(List<DeleteCalendariosProgDatosEntradaItem> listDeleteCalBody,
			HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0; // Variable para guardar el contador de NO ELIMINADOS
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int tamListaNum = 0;
		Error err = new  Error();
		List<String> erroresRespuesta = new ArrayList<String>();
		
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"deleteCalendariosProgramados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"deleteCalendariosProgramados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				
				if (usuarios != null && usuarios.size() > 0) {
					Stream<Integer> listaIdCalendariosProgramados = listDeleteCalBody.stream()
							.map(x -> Integer.parseInt(x.getIdCalendarioProgramado())).distinct();
					List<Integer> listaNum = listaIdCalendariosProgramados.collect(Collectors.toList());
					tamListaNum = listaNum.size();
					for (int i = 0; i < listaNum.size(); i++) {
						try {
							String respuestaAlEliminar = this.deleteByIdProgramacionCalendarios(listaNum.get(i), listDeleteCalBody, idInstitucion,
									usuarios);
							if ( respuestaAlEliminar == "") // si es vacio la respuesta, fue todo bien
								response++;
							else
								erroresRespuesta.add(respuestaAlEliminar);
						} catch (NoTransactionException e) {
							response++;
						}
	
					}
	
					LOGGER.info("deleteCalendariosProgramados() -> Salida ya con los datos recogidos");
				}
			}
	
			// comprobacion actualización
			if(erroresRespuesta.size() > 0) {
				err.setDescription(String.join(", ", erroresRespuesta));
				deleteResponseDTO.setError(err);
			}
		
			deleteResponseDTO.setStatus(SigaConstants.OK);
		
			deleteResponseDTO.setId(String.valueOf((response)) + "/" + String.valueOf(tamListaNum));
			
			deleteResponseDTO.setError(err);
		} catch (Exception e) {
			LOGGER.error(
					"deleteCalendariosProgramados() -> Se ha producido un error al eliminar un calendario programado",
					e);
			err.setCode(500);
			err.setDescription("general.mensaje.error.bbdd");
			err.setMessage(e.getMessage());
			deleteResponseDTO.setError(err);
		}

		LOGGER.info("deleteCalendariosProgramados() -> Salida del servicio para eliminar incompatibilidades");
		return deleteResponseDTO;
	}

	public String deleteByIdProgramacionCalendarios(int idProgramacionCalendarios,
			List<DeleteCalendariosProgDatosEntradaItem> listDeleteCalBody, Short idInstitucion,
			List<AdmUsuarios> usuarios) {

		TransactionStatus tx = getNeTransactionCalendarios();
		String respuesta = "errorDefecto";
		LOGGER.info("deleteCalendariosProgramados() -> Entra a validar las guardias del calendario");
		String idGuardias = "";
		// SCS_HCO_CONF_PROG_CALENDARIOS INFORMACION DE 
		ScsHcoConfProgCalendariosExample calendariosProgramadosExample = new ScsHcoConfProgCalendariosExample();
		calendariosProgramadosExample.createCriteria().andIdprogcalendarioEqualTo((long) idProgramacionCalendarios)
				.andIdinstitucionEqualTo(idInstitucion);
		ScsProgCalendariosExample calendarioExample = new ScsProgCalendariosExample();
		calendarioExample.createCriteria().andIdprogcalendarioEqualTo((long)idProgramacionCalendarios).andIdinstitucionEqualTo(idInstitucion);
		
		ScsProgCalendarios calendarioInfo = scsProgCalendariosMapper.selectByExample(calendarioExample).get(0);
		
		List<ScsHcoConfProgCalendarios> listaCalendarios = scsHcoConfProgCalendariosMapper
				.selectByExample(calendariosProgramadosExample);
		listaCalendarios.sort(Comparator.comparing(ScsHcoConfProgCalendarios::getOrden));
		int controlEsUltimo= 0, controlGuardia= 0, controlFacturados = 0, controlAsistencias = 0;
		LOGGER.info(" ORDEN A ELIINNAR");
		listaCalendarios.forEach(item -> {
			LOGGER.info(item.getOrden() + " " + item.getIdguardia());
		});
		DateFormat dateFormatFin = new SimpleDateFormat("dd/MM/yyyy");  
		for (int j = 0; j < listaCalendarios.size(); j++) {
			
		
			List<GuardiasCalendarioItem> guardiaIDList = scsGuardiasturnoExtendsMapper.getOneCalGuardia(listaCalendarios.get(j).getIdturno().toString(),
					listaCalendarios.get(j).getIdguardia().toString(), dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString(), 
					dateFormatFin.format( calendarioInfo.getFechacalfin()).toString(), idInstitucion.toString());
			if(!guardiaIDList.isEmpty() && guardiaIDList != null) {
				idGuardias =guardiaIDList.get(0).getIdcalendarioguardias();
				if (!esUltimoCalendario(Integer.valueOf(listaCalendarios.get(j).getIdinstitucion()),
						Integer.parseInt(guardiaIDList.get(0).getIdcalendarioguardias()),
						listaCalendarios.get(j).getIdturno(), listaCalendarios.get(j).getIdguardia()))
					controlEsUltimo++;

				if (!validarBorradoGuardias(Integer.valueOf(listaCalendarios.get(j).getIdinstitucion()),
						Integer.parseInt(guardiaIDList.get(0).getIdcalendarioguardias()),
						listaCalendarios.get(j).getIdturno(), listaCalendarios.get(j).getIdguardia(),dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString()))
					controlGuardia++;

				if(!validarColegiadosFacturados(idInstitucion, Integer.parseInt(guardiaIDList.get(0).getIdcalendarioguardias()),
						listaCalendarios.get(j).getIdturno(), listaCalendarios.get(j).getIdguardia(), 
						dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString(), 
						dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString()))
					controlFacturados++;

				if(!validarTieneAsistencias(idInstitucion,listaCalendarios.get(j).getIdturno(), listaCalendarios.get(j).getIdguardia(), 
						dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString(), 
						dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString()))
					controlAsistencias++;
				
				
				
			}
			
			
		}
		  
		 
		LOGGER.info("deleteCalendariosProgramados() -> Se ejecuta el borrado de todo lo relacionado con el calendario");

		if ((controlEsUltimo + controlGuardia + controlFacturados + controlAsistencias) == 0) {

			try {
				ScsProgCalendariosExample progExample = new ScsProgCalendariosExample();
				progExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdprogcalendarioEqualTo((long) idProgramacionCalendarios);
				List<ScsProgCalendarios> listaAux = scsProgCalendariosMapper.selectByExample(progExample);
				try {
					if (listaAux != null) {
						String nameLog = scsProgCalendariosMapper.selectByExample(progExample).get(0)
								.getLogProgramacionName() != null
										? scsProgCalendariosMapper.selectByExample(progExample).get(0)
												.getLogProgramacionName()
										: "NO";
						if (nameLog != "NO") {
							String pathFichero = getPathCalProByInstitucion(idInstitucion.toString());
							File file = new File(pathFichero, nameLog);

							if (file.exists())
								file.delete();

						}
					}

				} catch (Exception e) {
					LOGGER.info("deleteByIdProgramacionCalendarios() -> Error al eliminar Log de la programacion ID "
							+ idProgramacionCalendarios);

				}

				// Si es valido, borramos todos los calendarios y despues borramos la
				// programacion
				for (int k = 0; k < listaCalendarios.size(); k++) {
					// Identificamos el deleteBody para obtener la fechaInicio
					String idCal = listaCalendarios.get(k).getIdprogcalendario().toString();
					Optional<DeleteCalendariosProgDatosEntradaItem> itemDeleteOption = listDeleteCalBody.stream()
							.filter(x -> x.getIdCalendarioProgramado().equals(idCal)).findFirst();
					
					DeleteCalendariosProgDatosEntradaItem itemDeleteOptionCalendario = new DeleteCalendariosProgDatosEntradaItem();
					itemDeleteOptionCalendario.setFechaDesde(itemDeleteOption.get().getFechaDesde());
					itemDeleteOptionCalendario.setFechaHasta(itemDeleteOption.get().getFechaHasta());
					itemDeleteOptionCalendario.setIdCalendarioProgramado(itemDeleteOption.get().getIdCalendarioProgramado());
					itemDeleteOptionCalendario.setIdInstitucion(listaCalendarios.get(k).getIdinstitucion().toString());
					itemDeleteOptionCalendario.setIdTurno(listaCalendarios.get(k).getIdturno().toString());
					itemDeleteOptionCalendario.setIdGuardia(listaCalendarios.get(k).getIdguardia().toString());

					List<ScsCabeceraguardias> listaCabeceras = this.scsCabeceraguardiasExtendsMapper.getCabeceraGuardia(
							listaCalendarios.get(k).getIdinstitucion().toString(),
							listaCalendarios.get(k).getIdturno().toString(),
							listaCalendarios.get(k).getIdguardia().toString(), itemDeleteOption.get().getFechaDesde(),
							itemDeleteOption.get().getFechaHasta());
					
					if (!listaCabeceras.isEmpty()) {
						LOGGER.info("Tiene cabeceras");
						ScsCabeceraguardias cabeceraguardias = listaCabeceras.get(0);
						this.borrarGeneracionCalendario(itemDeleteOptionCalendario, usuarios,
								cabeceraguardias.getIdcalendarioguardias());
						//this.borrarRegistrosCalendario(itemDeleteOption.get(),
						//		cabeceraguardias.getIdcalendarioguardias());
					}else if(!idGuardias.isEmpty() && listaCabeceras.isEmpty()) {
						this.borrarGeneracionCalendario(itemDeleteOptionCalendario, usuarios,
								Integer.parseInt(idGuardias));
					}
					
					
//					//Eliminación de tablaa hcof
					LOGGER.info("Eliminación de tablaa hcof");
					this.scsIncompatibilidadguardiasExtendsMapper.deleteCalendarioProgramado1(
							listaCalendarios.get(k).getIdturno().toString(),
							listaCalendarios.get(k).getIdinstitucion().toString(),
							listaCalendarios.get(k).getIdguardia().toString(),
							listaCalendarios.get(k).getIdprogcalendario().toString());
				}

				// Eliminar de tabla prog_clandario
				LOGGER.info("Eliminación de tabla prog_clandario");
				this.scsIncompatibilidadguardiasExtendsMapper
						.deleteCalendarioProgramado2(String.valueOf(idProgramacionCalendarios));
				respuesta = "";

				commitCalendarios(tx);
			} catch (Exception e) {
				LOGGER.error(Arrays.toString(e.getStackTrace()).replaceAll(", ", "\n"));
				respuesta = "Error al eliminar calendarios programados";
				// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				rollBackCalendarios(tx);
			}

		} else {
			//CONSTRUCCION MENSAJE A MOSTRAR EN FRONT
			String mensajeProgramacionError ="Calendario Desde" + dateFormatFin.format( calendarioInfo.getFechacalinicio()).toString() +" Hasta "  + dateFormatFin.format( calendarioInfo.getFechacalfin()).toString();
			if(controlEsUltimo > 0) {
				mensajeProgramacionError = mensajeProgramacionError + " No es el ultimo ";
			}else if(controlGuardia > 0) {
				mensajeProgramacionError = mensajeProgramacionError + " Tiene Guardias de Colegiado realizadas.";
			}else if(controlFacturados > 0) {
				mensajeProgramacionError = mensajeProgramacionError + " Tiene Guardias de Colegiado en estado Facturado.";
			}else if(controlAsistencias > 0) {
				mensajeProgramacionError = mensajeProgramacionError + " Tiene Guardia de Colegiado con Asistencias.";
			}
			
			respuesta = mensajeProgramacionError;
		}
		return respuesta;
	}

	@Override
	public InsertResponseDTO subirDocumentoActDesigna(MultipartHttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Iterator<String> itr = request.getFileNames();

				while (itr.hasNext()) {

					MultipartFile file = request.getFile(itr.next());
					String nombreFichero = file.getOriginalFilename().split(";")[0];
					String json = file.getOriginalFilename().split(";")[1].replaceAll("%22", "\"");
					DocumentoActDesignaItem documentoActDesignaItem = objectMapper.readValue(json,
							DocumentoActDesignaItem.class);
					String extension = FilenameUtils.getExtension(nombreFichero);

					Long idFile = uploadFileActDesigna(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion,
							nombreFichero, extension);

					MaxIdDto nuevoId = scsDesignacionesExtendsMapper.getNewIdDocumentacionAsi(idInstitucion);

					ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();

					scsDocumentacionasi.setAnio(Short.valueOf(documentoActDesignaItem.getAnio()));
					scsDocumentacionasi.setNumero(Long.valueOf(documentoActDesignaItem.getNumero()));
					scsDocumentacionasi.setIdactuacion(Long.valueOf(documentoActDesignaItem.getIdActuacion()));
					if (!UtilidadesString.esCadenaVacia(documentoActDesignaItem.getObservaciones())) {
						scsDocumentacionasi.setObservaciones(documentoActDesignaItem.getObservaciones());
					}
					scsDocumentacionasi.setNombrefichero(nombreFichero);
					scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(nuevoId.getIdMax().toString()));
					scsDocumentacionasi.setIdtipodocumento(Short.valueOf("2"));
					scsDocumentacionasi.setIdfichero(idFile);
					scsDocumentacionasi.setIdinstitucion(idInstitucion);
					scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
					scsDocumentacionasi.setFechamodificacion(new Date());
					scsDocumentacionasi.setFechaentrada(new Date());

					int response = scsDocumentacionasiMapper.insertSelective(scsDocumentacionasi);

					if (response == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.subirDocumentoActDesigna() -> Se ha producido un error al subir un fichero perteneciente a la actuación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						insertResponseDTO.setError(error);
					}

				}

				String documentos = request.getParameter("documentosActualizar");
				List<DocumentoActDesignaItem> listaDocumentos = objectMapper.readValue(documentos,
						new TypeReference<List<DocumentoActDesignaItem>>() {
						});

				if (listaDocumentos != null && !listaDocumentos.isEmpty()) {

					for (DocumentoActDesignaItem doc : listaDocumentos) {

						ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();

						if (!UtilidadesString.esCadenaVacia(doc.getObservaciones())) {
							scsDocumentacionasi.setObservaciones(doc.getObservaciones());
						}
						scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsDocumentacionasi.setFechamodificacion(new Date());
						scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(doc.getIdDocumentacionasi()));
						scsDocumentacionasi.setIdinstitucion(idInstitucion);

						int response2 = scsDocumentacionasiMapper.updateByPrimaryKeySelective(scsDocumentacionasi);

						if (response2 == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

						if (response2 == 0) {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.error(
									"DesignacionesServiceImpl.subirDocumentoActDesigna() -> Se ha producido un error al actualizar la información relacionada al documento de la actuación");
							error.setCode(500);
							error.setDescription("general.mensaje.error.bbdd");
							insertResponseDTO.setError(error);
						}

					}

				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.subirDocumentoActDesigna2() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public DocumentoActDesignaDTO getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DocumentoActDesignaDTO documentoActDesignaDTO = new DocumentoActDesignaDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<DocumentoActDesignaItem> listaDocumentoActDesignaItem = scsDesignacionesExtendsMapper
						.getDocumentosPorActDesigna(documentoActDesignaItem, idInstitucion);

				documentoActDesignaDTO.setListaDocumentoActDesignaItem(listaDocumentoActDesignaItem);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() -> Se ha producido un error en la búsqueda de documentos asociados a la actuacion",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			documentoActDesignaDTO.setError(error);
		}

		return documentoActDesignaDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listaDocumentoActDesignaItem.isEmpty()) {

				if (listaDocumentoActDesignaItem.size() == 1) {

					String path = getDirectorioFichero(idInstitucion);
					path += File.separator + idInstitucion + "_" + listaDocumentoActDesignaItem.get(0).getIdFichero()
							+ listaDocumentoActDesignaItem.get(0).getExtension().toLowerCase();

					File file = new File(path);
					fileStream = new FileInputStream(file);

					String tipoMime = getMimeType(listaDocumentoActDesignaItem.get(0).getExtension());

					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition",
							"attachment; filename=\"" + listaDocumentoActDesignaItem.get(0).getNombreFichero() + "\"");
					headers.setContentLength(file.length());

				} else {
					fileStream = getZipFileDocumentosActDesigna(listaDocumentoActDesignaItem, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentos.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.descargarDocumentosActDesigna() -> Se ha producido un error al descargar un archivo asociado a la actuacion",
					e);
		}

		return res;

	}

	@Override
	public DeleteResponseDTO eliminarDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				for (DocumentoActDesignaItem doc : listaDocumentoActDesignaItem) {

					String path = getDirectorioFichero(idInstitucion);
					path += File.separator + idInstitucion + "_" + doc.getIdFichero()
							+ doc.getExtension().toLowerCase();

					File file = new File(path);

					if (file.exists()) {
						file.delete();
					}

					ScsDocumentacionasiKey scsDocumentacionasiKey = new ScsDocumentacionasiKey();

					scsDocumentacionasiKey.setIddocumentacionasi(Integer.valueOf(doc.getIdDocumentacionasi()));
					scsDocumentacionasiKey.setIdinstitucion(idInstitucion);

					int response = scsDocumentacionasiMapper.deleteByPrimaryKey(scsDocumentacionasiKey);

					if (response == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

					GenFicheroKey genFicheroKey = new GenFicheroKey();

					genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
					genFicheroKey.setIdinstitucion(idInstitucion);

					int response2 = genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

					if (response2 == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response2 == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	private String getMimeType(String extension) {

		String mime = "";

		switch (extension.toLowerCase()) {

		case ".doc":
			mime = "application/msword";
			break;
		case ".docx":
			mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			break;
		case ".pdf":
			mime = "application/pdf";
			break;
		case ".jpg":
			mime = "image/jpeg";
			break;
		case ".png":
			mime = "image/png";
			break;
		case ".rtf":
			mime = "application/rtf";
			break;
		case ".txt":
			mime = "text/plain";
			break;
		}

		return mime;
	}

	private Long uploadFileActDesigna(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
			String extension) {

		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(idInstitucion);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado a la actuación " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());

		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());

		return ficheroVo.getIdfichero();
	}

	private String getDirectorioFichero(Short idInstitucion) {

		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(path);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraemos el path concreto para actuaciones
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.actuaciones");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		return directorioFichero.toString();
	}

	private InputStream getZipFileDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			Short idInstitucion) {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (DocumentoActDesignaItem doc : listaDocumentoActDesignaItem) {

				zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
				String path = getDirectorioFichero(idInstitucion);
				path += File.separator + idInstitucion + "_" + doc.getIdFichero() + doc.getExtension().toLowerCase();
				File file = new File(path);
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	@Override
	public List<GuardiaCalendarioItem> guardiasFromCojunto(HttpServletRequest request, String idConjuntoGuardia) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiaCalendarioItem> datos = new ArrayList<GuardiaCalendarioItem>();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"guardiasFromCojunto() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");

				datos = scsGuardiasturnoExtendsMapper.getguardiasFromConjuntoGuardiasId(idConjuntoGuardia,
						idInstitucion.toString());
				for (int i = 0; i < datos.size(); i++) {
					datos.get(i).setOrden(Integer.toString(i + 1));
				}

				LOGGER.info("guardiasFromCojunto() -> Entrada para obtener los datos del calendario");
			}
		}

		return datos;
	}

	@Override
	public InsertResponseDTO insertGuardiaToConjunto(HttpServletRequest request, String idConjuntoGuardia,
			String idTurno, String idGuardia, String fechaDesde, String fechaHasta,
			List<GuardiaCalendarioItem> itemList) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (usuarios != null && usuarios.size() > 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"insertGuardiaToConjunto() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					DatosCalendarioProgramadoItem calendarioItem = new DatosCalendarioProgramadoItem();
					calendarioItem.setIdInstitucion(idInstitucion.toString());
					calendarioItem.setIdTurno(idTurno);
					calendarioItem.setIdGuardia(idGuardia);
					calendarioItem.setFechaDesde(fechaDesde);
					calendarioItem.setFechaHasta(fechaHasta);
					int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProg(calendarioItem,
							idInstitucion.toString());
					if (numGuards != 0) {
						error.setCode(204);
						error.setDescription(
								"Ya existen calendarios generados para las fechas seleccionadas o futuras");
						error.setMessage("Ya existen calendarios generados para las fechas seleccionadas o futuras");
						insertResponseDTO.setStatus("ERRORASOCIADAS");
					} else {
						itemList.forEach(item -> {
							String response = scsGuardiasturnoExtendsMapper.setguardiaInConjuntoGuardias(
									idConjuntoGuardia, idInstitucion.toString(), today, item,
									usuarios.get(0).getUsumodificacion().toString());
							if (response == null && error.getDescription() == null) {
								error.setCode(400);
								insertResponseDTO.setStatus(SigaConstants.KO);
							} else if (error.getCode() == null) {
								error.setCode(200);
								insertResponseDTO.setStatus(SigaConstants.OK);
							}

						});
					}

					LOGGER.info("insertGuardiaToConjunto() -> Entrada para obtener los datos del calendario");
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"insertGuardiaToConjunto() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO insertGuardiaToCalendar(Boolean update, HttpServletRequest request, String idCalendar,
			List<GuardiaCalendarioItem> itemList) throws Exception {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		boolean solapamiento = false;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				ScsProgCalendariosExample example = new ScsProgCalendariosExample();
				example.createCriteria().andIdprogcalendarioEqualTo(new Long(idCalendar))
						.andIdinstitucionEqualTo(idInstitucion);

				List<ScsProgCalendarios> calendarioItemList = scsProgCalendariosMapper.selectByExample(example);

				LOGGER.info(
						"insertGuardiaToCalendar() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
				String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(idCalendar,
						idInstitucion.toString());

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fechaIni = dateFormat.format(calendarioItemList.get(0).getFechacalinicio());
				String fechaFin = dateFormat.format(calendarioItemList.get(0).getFechacalfin());
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getNuevo() != null && itemList.get(i).getNuevo()) {
						long comprobacionA = scsGuardiasturnoExtendsMapper.compruebaSolapamientoProgramamcionesA(
								itemList.get(i).getTurno(), itemList.get(i).getGuardia(), fechaIni, fechaFin,
								idInstitucion);
						if (comprobacionA > 0) {
							solapamiento = true;
							throw new Exception("messages.factSJCS.error.solapamientoRango");
						}
					}

				}

				itemList.forEach(item -> {
					if (item.getNuevo() != null && item.getNuevo()) {
						item.setEstado(PENDIENTE);
						int res = 0;
						res = scsGuardiasturnoExtendsMapper.getGuardiasAsociadasCalProgByOrder(item.getGuardia(), item.getIdTurno(),
								idCalendar, idInstitucion.toString(), item.getOrden());
						
						if(res != 0) {
							throw new BusinessException("order");
						}
						String response2 = scsGuardiasturnoExtendsMapper.setGuardiaInCalendario(idCalendar,
								idConjuntoGuardia, idInstitucion.toString(), today, item);
						
						
						//Si venia informado el campo idConjuntoGuardia se hacia el insert en SCS_CONF_CONJUNTO_GUARDIAS -- SIGARNV-2618
						/*if (idConjuntoGuardia != null) {
							response = scsGuardiasturnoExtendsMapper.setguardiaInConjuntoGuardias(idConjuntoGuardia,
									idInstitucion.toString(), today, item,
									usuarios.get(0).getUsumodificacion().toString());
						}*/
						
					
						if (response2 == null && error.getDescription() == null) {
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
					} else {
						scsGuardiasturnoExtendsMapper.updateGuardiaInCalendario(idCalendar, idConjuntoGuardia,
								idInstitucion.toString(), today, item);
						error.setCode(200);
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
				});

				LOGGER.info("insertGuardiaToCalendar() -> Entrada para obtener los datos del calendario");
			}
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public InsertResponseDTO deleteGuardiaFromCalendar(HttpServletRequest request, String idCalendar,
			List<GuardiaCalendarioItem> itemList) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (usuarios != null && usuarios.size() > 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"insertGuardiaToCalendar() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(idCalendar,
							idInstitucion.toString());
					itemList.forEach(item -> {
//						String response3 = scsGuardiasturnoExtendsMapper.deleteguardiaFromLog(idConjuntoGuardia, idInstitucion.toString(), today, item);
						String response = scsGuardiasturnoExtendsMapper.deleteguardiaFromConjuntoGuardias(
								idConjuntoGuardia, idInstitucion.toString(), today, item);
						String response2 = scsGuardiasturnoExtendsMapper.deleteGuardiaFromCalendario(idCalendar,
								idConjuntoGuardia, idInstitucion.toString(), today, item);
						if (response2 == null || response == null && error.getDescription() == null) {
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

					});

					LOGGER.info("insertGuardiaToCalendar() -> Entrada para obtener los datos del calendario");
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"insertGuardiaToCalendar() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public InsertResponseDTO updateCalendarioProgramado(HttpServletRequest request,
			DatosCalendarioProgramadoItem calendarioItem) {
		LOGGER.info("updateCalendarioProgramado() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String hola = "";
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				if (usuarios != null && usuarios.size() > 0) {
					LOGGER.info(
							"updateCalendarioProgramado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProg(calendarioItem,
							idInstitucion.toString());
					if (numGuards != 0) {
						error.setCode(204);
						error.setDescription(
								"Ya existen calendarios generados para las fechas seleccionadas o futuras");
						error.setMessage("Ya existen calendarios generados para las fechas seleccionadas o futuras");
						insertResponseDTO.setStatus("ERRORASOCIADAS");
					} else {
						// String response =
						// scsGuardiasturnoExtendsMapper.updateCalendarioProgramado1(calendarioItem,
						// idInstitucion.toString());
						// int response2 =
						// scsGuardiasturnoExtendsMapper.updateCalendarioProgramado2(calendarioItem,
						// idInstitucion.toString());
						ScsProgCalendarios pc = new ScsProgCalendarios();
						if (calendarioItem.getEstado() != null) {
							pc.setEstado(new Integer(calendarioItem.getEstado()).shortValue());
						}
						if (calendarioItem.getFechaHasta() != null) {
							pc.setFechacalfin(new SimpleDateFormat("dd/MM/yyyy").parse(calendarioItem.getFechaHasta()));
						}
						if (calendarioItem.getFechaDesde() != null) {
							pc.setFechacalinicio(
									new SimpleDateFormat("dd/MM/yyyy").parse(calendarioItem.getFechaDesde()));
						}
						if (calendarioItem.getFechaProgramacion() != null) {
							pc.setFechaprogramacion(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
									.parse(calendarioItem.getFechaProgramacion()));
						}
						if (calendarioItem.getIdCalG() != null) {
							pc.setIdconjuntoguardia(Short.valueOf(calendarioItem.getIdCalG()));
						}
						if (calendarioItem.getIdCalendarioProgramado() != null) {
							pc.setIdprogcalendario(Long.valueOf(calendarioItem.getIdCalendarioProgramado()));
						}
						if (calendarioItem.getFechaProgramacion() != null) {
							pc.setEstado(new Short("0"));
							calendarioItem.setEstado(ESTADO_PROGRAMADO);
						}

						pc.setUsumodificacion(usuarios.get(0).getUsumodificacion());
						pc.setFechamodificacion(new Date());
						pc.setIdinstitucion(idInstitucion);
						// int response2 = scsProgCalendariosMapper.updateByPrimaryKeySelective(pc);
						int res = scsProgCalendariosMapper.updateProgCalendario(pc.getIdconjuntoguardia(),
								idInstitucion.toString(), calendarioItem.getFechaProgramacion(),
								calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta(),
								pc.getEstado().toString(), pc.getFechamodificacion().toString(),
								pc.getUsumodificacion().toString(), null, pc.getIdprogcalendario().toString(),
								calendarioItem.getSoloGenerarVacio());
						// String response3 =
						// scsGuardiasturnoExtendsMapper.updateConfCalendarioProgramado2(calendarioItem,
						// idInstitucion.toString());

						ScsHcoConfProgCalendarios hco = new ScsHcoConfProgCalendarios();
						if (calendarioItem.getIdTurno() != null) {
							hco.setIdturno(new Integer(calendarioItem.getIdTurno()));
						}
						if (calendarioItem.getIdGuardia() != null) {
							hco.setIdguardia(new Integer(calendarioItem.getIdGuardia()));
						}
						if (calendarioItem.getEstado() != null) {
							hco.setEstado(new Integer(calendarioItem.getEstado()).shortValue());
						}
						hco.setIdinstitucion(idInstitucion);
						if (calendarioItem.getIdCalendarioProgramado() != null) {
							hco.setIdprogcalendario(Long.valueOf(calendarioItem.getIdCalendarioProgramado()));
						}
						if (calendarioItem.getIdCalG() != null) {
							hco.setIdconjuntoguardia(Long.valueOf(calendarioItem.getIdCalG()));
						}
						if (calendarioItem.getFechaProgramacion() != null)
							hco.setEstado(new Short("0"));
						if (calendarioItem.getObservaciones() != null && !calendarioItem.getObservaciones().isEmpty()) {
							scsGuardiasturnoExtendsMapper.setObservaciones(idInstitucion.toString(),
									hco.getIdprogcalendario().toString(), calendarioItem.getObservaciones(),
									hco.getIdguardia().toString(), hco.getIdturno().toString(),
									calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta());
							scsGuardiasturnoExtendsMapper.setObservaciones2(idInstitucion.toString(),
									hco.getIdprogcalendario().toString(), calendarioItem.getObservaciones(),
									calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta());
						}
						hco.setUsumodificacion(usuarios.get(0).getUsumodificacion());
						hco.setFechamodificacion(new Date());
						scsHcoConfProgCalendariosMapper.updateByPrimaryKeySelective(hco);
						//scsGuardiasturnoExtendsMapper.updateCalendarioProgramado3(calendarioItem,
						//		idInstitucion.toString());

						if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

						LOGGER.info("updateCalendarioProgramado() -> salida para obtener los datos del calendario");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("updateCalendarioProgramado() -> Se ha producido un error", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO newCalendarioProgramado(HttpServletRequest request,
			DatosCalendarioProgramadoItem calendarioItem) {
		LOGGER.info("updateCalendarioProgramado() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		Boolean errorGuardiaAsociadas = false;
		boolean solapamiento = false;
		boolean listaVacia = false;
		int response = 0;

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (usuarios != null && usuarios.size() > 0) {
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
					this.LOGGER.info(
							"ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					List<GenParametros> tamMax = this.genParametrosExtendsMapper.selectByExample(genParametrosExample);
					this.LOGGER.info(
							"ListaGuardiaServiceImpl.getGuardiasFromLista() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
					Integer tamMaximo;
					if (tamMax != null) {
						tamMaximo = Integer.valueOf(((GenParametros) tamMax.get(0)).getValor());
					} else {
						tamMaximo = null;
					}
					AdmUsuarios usuario = usuarios.get(0);
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"newCalendarioProgramado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					// check si hay guardias asociadas a esta programacion
					if (calendarioItem.getListaGuardias() == null || calendarioItem.getListaGuardias().isEmpty()
							&& calendarioItem.getGuardias() != null && calendarioItem.getGuardias().isEmpty()) {
						// programación sin lista de guardias

						// En una programación sin lista de guardias, no debería existir ningún control
						// al guardar la tarjeta de Datos generales,
						// porque todavía no se sabe qué guardias se quieren generar. Luego, se deberían
						// poder añadir las guardias y en el Guardar de
						// esas guardias es donde sí debería ir el control anterior.

					} else {
						// programación con lista de guardias
						// la comprobación debería ser que NO existieran calendarios generados en esas
						// fechas o futuras; es decir, SELECT COUNT(*) GUARDIAS: si devuelve 0 entonces
						// es correcto.
						// Si hay != 0 entonces el error sería “Ya existen calendarios generados para
						// las fechas seleccionadas o futuras”.
						if(!calendarioItem.getGuardias().isEmpty()) {
							for(GuardiaCalendarioItem guardiaItem :calendarioItem.getGuardias() ) {
								DatosCalendarioProgramadoItem comprobacionItem = calendarioItem;
								comprobacionItem.setIdGuardia(guardiaItem.getGuardia());
								comprobacionItem.setIdTurno(guardiaItem.getTurno());
								int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProg(calendarioItem,
										idInstitucion.toString());
								if (numGuards != 0) {
									errorGuardiaAsociadas = true;
									error.setCode(204);
									error.setDescription(
											"Ya existen calendarios generados para las fechas seleccionadas o futuras");
									error.setMessage(
											"Ya existen calendarios generados para las fechas seleccionadas o futuras");
									insertResponseDTO.setStatus("ERRORASOCIADAS");
									break;
								}
							}
		
							
							
						}
						
					}
					if (!errorGuardiaAsociadas) {
						if (calendarioItem.getIdCalG() == null
								&& !UtilidadesString.esCadenaVacia(calendarioItem.getIdCalendarioProgramado())) {
							String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(
									calendarioItem.getIdCalendarioProgramado(), idInstitucion.toString());
							// idConjunto:
							calendarioItem.setIdCalG(idConjuntoGuardia);
						}
						String nextIdCalendarioProgramado = getNuevoIdCalProg();
						calendarioItem.setIdCalendarioProgramado(nextIdCalendarioProgramado);
						calendarioItem.setIdInstitucion(idInstitucion.toString());
						if(calendarioItem.getEstado() == null || calendarioItem.getEstado().isEmpty()  ) calendarioItem.setEstado(PENDIENTE);
						calendarioItem.setNombreLogProgramacion(
								"LOG_" + calendarioItem.getIdCalendarioProgramado() + ".xlsx");
						// Validacion de Solapamiento.
						compruebaSolapamientoProgramamciones(calendarioItem, idInstitucion, solapamiento, tamMaximo);
						if (calendarioItem.getFechaProgramacion() != null)
							calendarioItem.setEstado(ESTADO_PROGRAMADO);
						if (calendarioItem.getIdCalG() != null) {
							List<GuardiasItem> listaGuardiasItems = scsConfConjuntoGuardiasExtendsMapper
									.searchGuardiasFromLista(calendarioItem.getIdCalG(), idInstitucion, tamMaximo);
							if (!listaGuardiasItems.isEmpty()) {
								response = scsGuardiasturnoExtendsMapper.generateCalendarioProgramado(
										nextIdCalendarioProgramado, calendarioItem, idInstitucion.toString(), today,
										usuario.getIdusuario().toString());
								int ordenDefault = 0;
								for (int i = 0; i < listaGuardiasItems.size(); i++) {
									ScsHcoConfProgCalendarios historico = new ScsHcoConfProgCalendarios();
									historico.setEstado(new Short(calendarioItem.getEstado()));
									historico.setFechamodificacion(new Date());
									historico.setIdconjuntoguardia(new Long(calendarioItem.getIdCalG()));
									historico.setIdguardia(Integer.parseInt(listaGuardiasItems.get(i).getIdGuardia()));
									historico.setIdinstitucion(idInstitucion);
									historico.setIdprogcalendario(new Long(nextIdCalendarioProgramado));
									historico.setIdturno(Integer.parseInt(listaGuardiasItems.get(i).getIdTurno()));
									historico.setUsumodificacion(usuario.getIdusuario());
									historico.setOrden(Integer.parseInt(listaGuardiasItems.get(i).getOrden()));
									ordenDefault++;
									scsHcoConfProgCalendariosMapper.insertSelective(historico);
								}
							} else {
								listaVacia = true;
								throw new Exception("messages.factSJCS.error.listaVacia");
							}
						} else if (calendarioItem.getGuardias() != null && !calendarioItem.getGuardias().isEmpty()) {
							response = scsGuardiasturnoExtendsMapper.generateCalendarioProgramado(
									nextIdCalendarioProgramado, calendarioItem, idInstitucion.toString(), today,
									usuario.getIdusuario().toString());
							for (GuardiaCalendarioItem item : calendarioItem.getGuardias()) {
								if (!UtilidadesString.esCadenaVacia(item.getOrden())
										&& !UtilidadesString.esCadenaVacia(item.getGuardia())
										&& !UtilidadesString.esCadenaVacia(item.getTurno())) {
									ScsHcoConfProgCalendarios historico = new ScsHcoConfProgCalendarios();
									historico.setEstado(new Short(calendarioItem.getEstado()));
									historico.setFechamodificacion(new Date());
									historico.setIdconjuntoguardia(null);
									historico.setIdguardia(Integer.parseInt(item.getGuardia()));
									historico.setIdinstitucion(idInstitucion);
									historico.setIdprogcalendario(new Long(nextIdCalendarioProgramado));
									historico.setIdturno(Integer.parseInt(item.getTurno()));
									historico.setUsumodificacion(usuario.getIdusuario());
									historico.setOrden(Integer.parseInt(item.getOrden()));
									scsHcoConfProgCalendariosMapper.insertSelective(historico);
								}
							}
						} else {
							listaVacia = true;
							throw new Exception("messages.factSJCS.error.listaVacia");
						}

						if (response == 0) {
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							crearLog(calendarioItem);
							error.setCode(200);
							insertResponseDTO.setId(nextIdCalendarioProgramado);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

						LOGGER.info("newCalendarioProgramado() -> Entrada para obtener los datos del calendario");
					}
				}
			}
		} catch (Exception e) {
			if (solapamiento) {
				LOGGER.error("newCalendarioProgramado() -> Solapamiento de Fechas.");
				error.setCode(400);
				error.setDescription("messages.factSJCS.error.solapamientoRango");
				error.setMessage("messages.factSJCS.error.solapamientoRango");
			} else if (listaVacia) {
				LOGGER.info("newCalendarioProgramado() -> Lista de guardias Vacia.");
				error.setCode(400);
				error.setDescription("messages.factSJCS.error.listaVacia");
				error.setMessage("messages.factSJCS.error.listaVacia");
			} else {
				LOGGER.error(
						"newCalendarioProgramado() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
						e);
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
				error.setMessage(e.getMessage());
			}
			insertResponseDTO.setError(error);
			insertResponseDTO.setStatus(SigaConstants.KO);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	private void crearLog(DatosCalendarioProgramadoItem calendarioItem) {
		try {
			LOGGER.info("crearLog()- Se va a crear el excel informando");

			String sheetName = "LOG";
			String pathFichero = getPathCalPro(calendarioItem);
			Workbook workBook = crearExcel(calendarioItem);
			FileOutputStream fileOut;
			String nombreFichero = calendarioItem.getNombreLogProgramacion();
			SIGAHelper.mkdirs(pathFichero);
			File file = new File(pathFichero, nombreFichero);
			fileOut = new FileOutputStream(file);
			workBook.write(fileOut);
			fileOut.close();
			workBook.close();
			LOGGER.info(" crearLog()- Creado fichero:  " + nombreFichero);
		} catch (Exception e) {
			LOGGER.error("crearLog() - Error a la hora de crear Excel", e);

		}

	}

	private Workbook crearExcel(DatosCalendarioProgramadoItem calendarioItem) {

		try {

			Workbook workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
			Sheet sheet = workbook.createSheet("LOG");

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			// Fecha
			Row rowFecha = sheet.createRow(0);
			rowFecha.createCell(0).setCellValue("FECHA");
			rowFecha.createCell(1).setCellValue("ESTADO");
			rowFecha.createCell(2).setCellValue("DETALLE");

			Date date = new Date();
			String fecha = dateFormat.format(date);
			// Fecha
			Row Accion = sheet.createRow(1);
			Accion.createCell(0).setCellValue(fecha);
			Accion.createCell(1).setCellValue("Pendiente");
			Accion.createCell(2).setCellValue("");

			sheet.setColumnWidth(0, 7000);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 4000);
			return workbook;

		} catch (Exception e) {
			throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
		}
	}

	private void compruebaSolapamientoProgramamciones(DatosCalendarioProgramadoItem calendarioItem, Short idInstitucion,
			boolean solapamiento, int tamMaximo) throws Exception {
		// --Se comprueba si hay alguna programación creada solapamiento de fechas en el
		// turno-guardia

		if (calendarioItem.getIdCalG() != null) {
			List<GuardiasItem> listaGuardiasItems = scsConfConjuntoGuardiasExtendsMapper
					.searchGuardiasFromLista(calendarioItem.getIdCalG(), idInstitucion, tamMaximo);

			for (int i = 0; i < listaGuardiasItems.size(); i++) {
				long comprobacionA = scsGuardiasturnoExtendsMapper.compruebaSolapamientoProgramamcionesA(
						listaGuardiasItems.get(i).getIdTurno(), listaGuardiasItems.get(i).getIdGuardia(),
						calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta(), idInstitucion);
				if (comprobacionA > 0) {
					solapamiento = true;
					throw new Exception("messages.factSJCS.error.solapamientoRango");
				}
			}
		} else {
			List<GuardiaCalendarioItem> listaGuardiasItems = calendarioItem.getGuardias();
			for (int i = 0; i < listaGuardiasItems.size(); i++) {
				long comprobacionA = scsGuardiasturnoExtendsMapper.compruebaSolapamientoProgramamcionesA(
						listaGuardiasItems.get(i).getTurno(), listaGuardiasItems.get(i).getGuardia(),
						calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta(), idInstitucion);
				if (comprobacionA > 0) {
					solapamiento = true;
					throw new Exception("messages.factSJCS.error.solapamientoRango");
				}
			}
		}

	}

	private List<DatosCalendarioProgramadoItem> listaNoRepetida(
			List<DatosCalendarioProgramadoItem> programacionItemList) {
		return programacionItemList.stream()
				.filter(distinctByIdCalProg(DatosCalendarioProgramadoItem::getIdCalendarioProgramado))
				.collect(Collectors.toList());
	}

	private int updateHcoConfigProgCal(HcoConfProgCalendariosItem item, String estado) {
		ScsHcoConfProgCalendariosExample example = new ScsHcoConfProgCalendariosExample();
		example.createCriteria().andIdprogcalendarioEqualTo(Long.parseLong(item.getIdprogcalendario()))
				.andIdinstitucionEqualTo(Short.parseShort(item.getIdinstitucion()))
				.andIdturnoEqualTo(Integer.parseInt(item.getIdturno()))
				.andIdguardiaEqualTo(Integer.parseInt(item.getIdguardia()));

		ScsHcoConfProgCalendarios record = new ScsHcoConfProgCalendarios();
		record.setEstado(Short.valueOf(estado));
		record.setUsumodificacion(Integer.valueOf(item.getUsumodificacion()));
		record.setFechamodificacion(new Date());

		int res = scsHcoConfProgCalendariosMapper.updateByExampleSelective(record, example);
		return res;

	}

	@Scheduled(cron = "${cron.pattern.scheduled.guardias.generarCalendario}")
	@Override
	public void generarCalendarioAsync() {

		LOGGER.info("generarCalendarioAsync() -> Entrada al servicio para búsqueda de las guardias");
		// TransactionStatus txProgramacion = getNeTransactionProgramaciones();
		List<String> idProgCalGenerandose = scsCalendarioguardiasMapper.getGeneracionEnProceso();
//		setGeneracionEnProceso("39568", "0");
		
		// if (!generacionCalEnProceso) {
		if (idProgCalGenerandose == null || idProgCalGenerandose.size() == 0) {

			try {
				LOGGER.info(
						"generarCalendarioAsync()  -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
				// commitProgramaciones(txProgramacion);
				List<DatosCalendarioProgramadoItem> programacionItemList = scsGuardiasturnoExtendsMapper.getAllCalendariosProgramadosSigaClassiquePendiente();
				// commitProgramaciones(txProgramacion);
				
				System.out.print("programacionItemList : ");
				System.out.print(programacionItemList);
				if (programacionItemList != null && !programacionItemList.isEmpty()) {
					// List <DatosCalendarioProgramadoItem> listaIdCalendariosProgramados
					// =programacionItemList.stream().
					// filter(distinctByIdCalProg(DatosCalendarioProgramadoItem::getIdCalendarioProgramado)).collect(Collectors.toList());
					List<DatosCalendarioProgramadoItem> listaLimpia = listaNoRepetida(programacionItemList);
					listaLimpia.forEach(d -> {
						// commitProgramaciones(txProgramacion);
						List<String> idProgCalGenerandose2 = scsCalendarioguardiasMapper.getGeneracionEnProceso();
						String generado = scsCalendarioguardiasMapper.getGenerado(d.getIdCalendarioProgramado(),
								Short.valueOf(d.getIdInstitucion()));
						boolean soloVacio = d.getSoloGenerarVacio() == 'S' ? true : false;
						LOGGER.info("generarCalendarioAsync() -> Empieza generacion idCalendario"
								+ d.getIdCalendarioProgramado() + "Generado: "+generado + " Estado: " + d.getEstado());
//						if (generado.equals("Si")
//								|| (idProgCalGenerandose2 != null && idProgCalGenerandose2.size() != 0)) {
						if ((idProgCalGenerandose2 != null && idProgCalGenerandose2.size() != 0)) {
							// generacionCalEnProceso = false;
							int res = setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
							idProgCalGenerandose2 = scsCalendarioguardiasMapper.getGeneracionEnProceso();
							// LOGGER.error("generarCalendarioAsync: Error, el calendario " +
							// d.getIdCalendarioProgramado() + " ya está generado o en proceso de
							// generación");

						} else {
							LOGGER.info("generarCalendarioAsync: Comienza la generación asincrona del calendario "
									+ d.getIdCalendarioProgramado());
							try {
								// calendariosGenerandose.put(d.getIdCalendarioProgramado(), true);
								setGeneracionEnProceso(d.getIdCalendarioProgramado(), "1");
								idProgCalGenerandose2 = scsCalendarioguardiasMapper.getGeneracionEnProceso();

								try {
									if (d.getEstado().equals(ESTADO_PROGRAMADO)) {
										// Insertamos en el historico
										String res3 = scsGuardiasturnoExtendsMapper.checkHistorico(d,
												d.getIdInstitucion());
										if (res3 == null) {
											scsGuardiasturnoExtendsMapper.insertarHistorico(d, d.getIdInstitucion());
										}

										d.setEstado(EN_PROCESO);
										updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);

									} else if (d.getEstado().equals(REPROGRAMADO)) {
										d.setEstado(EN_PROCESO);
										updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
									}

									// PROGRAMACION EN ESTADO EN PROCESO
									// commitProgramaciones(txProgramacion);

									// Obtenemos la siguiente guardia programada
									List<HcoConfProgCalendariosItem> hcoConfProgCalendariosItemList = scsGuardiasturnoExtendsMapper
											.getNextGuardiaProgramadaNoGenerada(d, d.getIdInstitucion());
									if (hcoConfProgCalendariosItemList != null
											&& !hcoConfProgCalendariosItemList.isEmpty()) {
										hcoConfProgCalendariosItemList.forEach(hcoConfProgCalendariosItem -> {
											LOGGER.info("generarCalendarioAsync() -> hcoConfProgCalendario estado: "
													+ hcoConfProgCalendariosItem.getEstado());
											if (hcoConfProgCalendariosItem != null) {
												if (cantidadHcoEstado(hcoConfProgCalendariosItem, PROCESADO_CON_ERRORES) == 0) { // Buscamos que no tenga HCO en estado ERROR, sino, pasamos los demas a pendiente
													editarLog(d, "En proceso", "Turno "+hcoConfProgCalendariosItem.getIdturno()+" Guardia "+hcoConfProgCalendariosItem.getIdguardia());
													LOGGER.info(
															"generarCalendarioAsync() -> INICIO generacion base HCO "
																	+ hcoConfProgCalendariosItem.getNombre());
													
													String control = d.getFechaDesde().length() == 21 ? DATE_LONG_MSEC : DATE_LONG;

													String fechaDesde = changeDateFormat(control, DATE_SHORT_SLASH, d.getFechaDesde());
													String fechaHasta = changeDateFormat(control, DATE_SHORT_SLASH, d.getFechaHasta());
													
													try {

														controlError = 0;
														// updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
														// realizado antes, estado en proceso
																//+ hcoConfProgCalendariosItem.getNombre());
														// El metodo crear calerndario nos creara los calendarios. Hay
														// mas de
														// uno ya
														// que pueden tener guardias vincualdas
														String textoAutomatico = "Calendario generado automáticamente desde la programación de calendarios";
														int idCalendario;
														int idCalendarioGuardias3 = 0;

//														idCalendario = crearCalendario(
//																hcoConfProgCalendariosItem.getIdinstitucion(),
//																hcoConfProgCalendariosItem.getIdturno(),
//																hcoConfProgCalendariosItem.getIdguardia(),
//																d.getFechaDesde(), d.getFechaHasta(), textoAutomatico,
//																null, null, null);
														
//														//Nuevo
														GuardiasCalendarioItem guardiasCalendarioItem3 = new GuardiasCalendarioItem(
																hcoConfProgCalendariosItem.getIdinstitucion(),
																hcoConfProgCalendariosItem.getIdturno(),
																hcoConfProgCalendariosItem.getIdguardia(),
																Integer.toString(idCalendarioGuardias3), fechaDesde,
																fechaHasta, textoAutomatico);
														guardiasCalendarioItem3.setUsumodificacion(USU_MODIFICACION);
														
														guardiasCalendarioItem3 = crearCalendario3(guardiasCalendarioItem3,false);														
														idCalendarioGuardias3 = ("0".equals(guardiasCalendarioItem3.getIdcalendarioguardias())) ? idCalendarioGuardias3 : Integer.parseInt(guardiasCalendarioItem3.getIdcalendarioguardias());

														TransactionStatus txCalendario = getNeTransactionCalendarios();
														try {
//															inicializaParaGenerarCalendario(
//																	new Integer(
//																			hcoConfProgCalendariosItem.getIdinstitucion()),
//																	new Integer(hcoConfProgCalendariosItem.getIdturno()),
//																	new Integer(hcoConfProgCalendariosItem.getIdguardia()),
//																	new Integer(idCalendario), d.getFechaDesde(),
//																	d.getFechaHasta());
////
//															generarCalendario2(soloVacio);															
															
															//Nuevo
															generarCalendario3(guardiasCalendarioItem3, soloVacio, null, false);
															
															if (controlError == 0) {
																LOGGER.info(
																		"generarCalendarioAsync: HCO SIN ERRORES - FINALIZADO ");
																commitCalendarios(txCalendario);
															} else {
																LOGGER.info(
																		"generarCalendarioAsync: HCO CON ERRORES - FINALIZADO ");
																rollBackCalendarios(txCalendario);
															}
															
														} catch (Exception exp) {
															rollBackCalendarios(txCalendario);
															editarLog(d, "Con errores", "Error en la programación: "+exp.getMessage());
														}
														
														
														
														String nombreFicheroSalida = hcoConfProgCalendariosItem.getIdturno() + "." + hcoConfProgCalendariosItem.getIdguardia()+ "."
																+ /*idCalendario*/ idCalendarioGuardias3+ "-"
																+ fechaDesde.replace('/', '.') + "-"
																+ fechaHasta.replace('/', '.') + "-log";
														LOGGER.info("generarCalendarioAsync() -> NOMBRE LOG DE "
																+ hcoConfProgCalendariosItem.getNombre() + " : "
																+ nombreFicheroSalida);

														String updateName = scsCalendarioguardiasMapper.setLogName(d.getIdInstitucion(),
																Integer.toString(/*idCalendario*/idCalendarioGuardias3), d.getObservaciones(),
																fechaDesde, fechaHasta, nombreFicheroSalida,
																hcoConfProgCalendariosItem.getIdturno(),
																hcoConfProgCalendariosItem.getIdguardia());
														
//														generarExcelLog(nombreFicheroSalida);
														generarExcelLog3(nombreFicheroSalida, guardiasCalendarioItem3.getIdinstitucion());
														
														LOGGER.info("LOGNAME UPDATE : " + updateName);

														LOGGER.info(
																"generarCalendarioAsync() -> FIN generacion base HCO "
																		+ hcoConfProgCalendariosItem.getNombre());
														if (!soloVacio) {
															int guardiasInsertadas = comprobarGuardiasInsert(d,
																	hcoConfProgCalendariosItem);
															LOGGER.info("Guardias Insertadas para  "
																	+ guardiasInsertadas + " - "
																	+ hcoConfProgCalendariosItem.getNombre());
															if (guardiasInsertadas > 0) {
																LOGGER.info(
																		"generarCalendarioAsync: HCO con REGISTROS - FINALIZADO ");
																updateHcoConfigProgCal(hcoConfProgCalendariosItem,
																		FINALIZADO);
															} else {
																LOGGER.info(
																		"generarCalendarioAsync: HCO SIN REGISTROS - Procesado con errores ");
																updateHcoConfigProgCal(hcoConfProgCalendariosItem,
																		PROCESADO_CON_ERRORES);
																editarLog(d, "Procesado con errores", "");
															}

														} else {// Si viene con check solo generar vacio
															updateHcoConfigProgCal(hcoConfProgCalendariosItem,
																	FINALIZADO);
														}

													} catch (Exception e) {
														LOGGER.error("ERROR catch - hco - " + e.getMessage());
												//		rollBackCalendarios(txCalendario);
														updateHcoConfigProgCal(hcoConfProgCalendariosItem,
																PROCESADO_CON_ERRORES);
														editarLog(d, "Con errores", "Error en la programación: "+e.getMessage());
														setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
														d.setEstado(PROCESADO_CON_ERRORES);
														updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
													}
												} else {
													updateHcoConfigProgCal(hcoConfProgCalendariosItem, PROCESADO_CON_ERRORES);
													editarLog(d, "Con errores", "Errores en la generación");
												}
											} // null

										});
									}

									// SI TIENE LAS GUARIDAS GENERADAS, MARCAMOS COMO GENERADO.
									// List<HcoConfProgCalendariosItem> listaControl =
									// scsGuardiasturnoExtendsMapper.getNextGuardiaConfigurada(null,
									// d.getIdCalendarioProgramado());
									boolean todoGenerado = false;
									if (cantidadHcoEstado(hcoConfProgCalendariosItemList.get(0),
											FINALIZADO) == hcoConfProgCalendariosItemList.size()) {
										todoGenerado = true;
									}
									if (todoGenerado || soloVacio) {
										d.setEstado(FINALIZADO);// FINALIZADO
										updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
										editarLog(d, "Finalizada", "");
									} else {
										d.setEstado(PROCESADO_CON_ERRORES);// FINALIZADO
										updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
										editarLog(d, "Procesado con errores", "");
									}
									setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
									// commitProgramaciones(txProgramacion);

								} catch (Exception e) {
									// rollBackProgramaciones(txProgramacion);
									// generacionCalEnProceso = false;
									// calendariosGenerandose.put(programacionItem.getIdCalendarioProgramado(),
									// false);
									int res3 = setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
									LOGGER.error(
											"generarCalendarioAsync() -> Se ha producido un error al trabajar con el histórico",
											e);
									if (d != null) {
										editarLog(d, "Con errores", "Error en la programación: "+e.getMessage());
										d.setEstado(PROCESADO_CON_ERRORES);
										updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
									}
									// generacionCalEnProceso = false;
									int res4 = setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
									// calendariosGenerandose.put(programacionItem.getIdCalendarioProgramado(),
									// false);

								}

							} catch (Exception e) {
								// rollBackProgramaciones(txProgramacion);
								// generacionCalEnProceso = false;
								int res = setGeneracionEnProceso(d.getIdCalendarioProgramado(), "0");
								// calendariosGenerandose.put(d.getIdCalendarioProgramado(), false);
								LOGGER.error(
										"generarCalendarioAsync() -> Se ha producido un error al trabajar con el histórico",
										e);

								if (d != null) {
									editarLog(d, "Con errores", "Error en la programación: "+e.getMessage());
									d.setEstado(PROCESADO_CON_ERRORES);
									updateEstado(d, Short.valueOf(d.getIdInstitucion()), 0);
								}
								// generacionCalEnProceso = false;

							}
						}
					});

				} else {
					LOGGER.info("generarCalendarioAsync() -> LISTA DE PROGRAMACION DE CALENDARIOS VACIA");
				}

			} catch (Exception e) {
				// rollBackProgramaciones(txProgramacion);
				// calendariosGenerandose.put(programacionItem.getIdCalendarioProgramado(),
				// false);
				// generacionCalEnProceso = false;
				// int res3 =
				// scsCalendarioguardiasMapper.setGeneracionEnProceso(programacionItem.getIdCalendarioProgramado(),
				// "0");
				LOGGER.error(
						"generarCalendarioAsync() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
						e);
			}
			// generacionCalEnProceso = false;
			// int res3 =
			// scsCalendarioguardiasMapper.setGeneracionEnProceso(programacionItem.getIdCalendarioProgramado(),
			// "0");
//		return insertResponseDTO;
		} else {
			// calendariosGenerandose.put(programacionItem.getIdCalendarioProgramado(),
			// false);
			LOGGER.error(
					"generarCalendarioAsync() -> No puede generarse un calendario hasta que no finalice la generación del anterior.");
		}
		// commitProgramaciones(txProgramacion);
	}

	private int cantidadHcoEstado(HcoConfProgCalendariosItem hcoConfProgCalendariosItem, String estado) {

		ScsHcoConfProgCalendariosExample hcoExample = new ScsHcoConfProgCalendariosExample();
		hcoExample.createCriteria()
				.andIdinstitucionEqualTo(Short.parseShort(hcoConfProgCalendariosItem.getIdinstitucion()))
				.andIdprogcalendarioEqualTo(Long.parseLong(hcoConfProgCalendariosItem.getIdprogcalendario()))
				.andEstadoEqualTo(Short.parseShort(estado));
		List<ScsHcoConfProgCalendarios> listaHco = scsHcoConfProgCalendariosMapper.selectByExample(hcoExample);

		if (listaHco.isEmpty() || listaHco == null) {
			return 0;
		} else {
			return listaHco.size();
		}

	}

	private int comprobarGuardiasInsert(DatosCalendarioProgramadoItem programacion,
			HcoConfProgCalendariosItem calendario) {
		String OLD_FORMATGlb = "yyyy-MM-dd HH:mm:ss.S";
		String OLD_FORMATGlb2 = "yyyy-MM-dd HH:mm:ss";
		String NEW_FORMATGlb = "dd-MM-yyyy";

		String control = programacion.getFechaDesde().length() == 21 ? OLD_FORMATGlb : OLD_FORMATGlb2;

		String fechaDesdeAux = changeDateFormat(control, NEW_FORMATGlb, programacion.getFechaDesde());
		String fechaHastaAux = changeDateFormat(control, NEW_FORMATGlb, programacion.getFechaHasta());

		int res = scsCalendarioguardiasMapper.getTotalGuardiasColegiadoInsertados(programacion.getIdInstitucion(),
				calendario.getIdturno(), calendario.getIdguardia(), fechaDesdeAux, fechaHastaAux);
		return res;
	}

	public static <T> Predicate<T> distinctByIdCalProg(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	@Override
	public InsertResponseDTO generarCalendario(HttpServletRequest request,
			DatosCalendarioProgramadoItem programacionItem) throws Exception {
		LOGGER.info("generarCalendario() -> Entrada al servicio para cambiar un programacion a programada");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		List<HcoConfProgCalendariosItem> hcoConfProgCalendariosItemList = new ArrayList<HcoConfProgCalendariosItem>();
		GuardiasTurnoItem guardiaBean = new GuardiasTurnoItem();

		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());

				ScsProgCalendariosExample exampleProg = new ScsProgCalendariosExample();
				exampleProg.createCriteria()
						.andIdprogcalendarioEqualTo(Long.parseLong(programacionItem.getIdCalendarioProgramado()))
						.andIdinstitucionEqualTo(idInstitucion);
				List<ScsProgCalendarios> progToUpdate = scsProgCalendariosMapper.selectByExample(exampleProg);

				if (progToUpdate != null && progToUpdate.size() == 1) {
					// Estados para actualizar a PROGRAMADO--PENDIENTE 4
					ScsProgCalendarios record = progToUpdate.get(0);
					if (record.getEstado() == 4 || record.getEstado() == 2) {
						record.setFechaprogramacion(new Date());
						record.setEstado(new Short("0"));
						int res = scsProgCalendariosMapper.updateByExample(record, exampleProg);
						if (res == 1)
							insertResponseDTO.setStatus("OK");

						// añadir si el generado pasa a reprogramado.
						Date fecha = new Date();
						editarLog(programacionItem, "Programado", fecha.toString());
						List<Short> listaCompatibles = new ArrayList<Short>();
						listaCompatibles.add((short) 2);
						listaCompatibles.add((short) 4);

						ScsHcoConfProgCalendariosExample listaCalendariosExample = new ScsHcoConfProgCalendariosExample();

						listaCalendariosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdprogcalendarioEqualTo(record.getIdprogcalendario()).andEstadoIn(listaCompatibles);
						List<ScsHcoConfProgCalendarios> listaResult = scsHcoConfProgCalendariosMapper
								.selectByExample(listaCalendariosExample);
						ScsHcoConfProgCalendarios recordHco = new ScsHcoConfProgCalendarios();
						recordHco.setEstado((short) 0);
						scsHcoConfProgCalendariosMapper.updateByExampleSelective(recordHco, listaCalendariosExample);
					}

				}
			}
		}
		return insertResponseDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarLogCalendarioProgramado(DatosCalendarioProgramadoItem item,
			HttpServletRequest request) throws Exception {
		ResponseEntity<InputStreamResource> res = null;
		AdmUsuarios usuario = null;

		LOGGER.info(
				"descargarFichaFacturacion() -> Entrada al servicio para recuperar el archivo de LOG de la facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		String pathFichero = getPathCalPro(item);

		// Lista de ficheros de facturaciones programadas
		List<File> listaFicheros = new ArrayList<File>();
		File file = null;
		String nombreFichero = getNombreFichero(item);
		file = new File(pathFichero + File.separator + nombreFichero);
		listaFicheros.add(file);

		// Construcción de la respuesta para uno o más archivos
		res = SIGAServicesHelper.descargarFicheros(listaFicheros, MediaType.parseMediaType("application/vnd.ms-excel"),
				MediaType.parseMediaType("application/zip"), "LOG_FACTURACION");

		LOGGER.info(
				"descargarFichaFacturacion() -> Salida del servicio para obtener el archivo de LOG de la facturación");

		return res;
	}

	private int updateEstado(DatosCalendarioProgramadoItem programacionItem, Short idInstitucion,
			Integer usuModificacion) {

		ScsProgCalendariosExample example = new ScsProgCalendariosExample();
		example.createCriteria().andIdprogcalendarioEqualTo(Long.valueOf(programacionItem.getIdCalendarioProgramado()))
				.andIdinstitucionEqualTo(idInstitucion);

		ScsProgCalendarios record = new ScsProgCalendarios();
		if (programacionItem.getEstado() != null) {
			record.setEstado(Short.valueOf(programacionItem.getEstado()));
		}
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuModificacion);

		int res = scsProgCalendariosMapper.updateByExampleSelective(record, example);
		return res;

	}

	private void updateEstadoHco(HcoConfProgCalendariosItem item, String estado) {

		ScsHcoConfProgCalendariosExample example = new ScsHcoConfProgCalendariosExample();
		example.createCriteria().andIdprogcalendarioEqualTo(Long.valueOf(item.getIdprogcalendario()))
				.andIdinstitucionEqualTo(Short.valueOf(item.getIdinstitucion()))
				.andIdturnoEqualTo(Integer.valueOf(item.getIdturno()))
				.andIdguardiaEqualTo(Integer.valueOf(item.getIdguardia()));

		List<ScsHcoConfProgCalendarios> dbItems = this.scsHcoConfProgCalendariosMapper.selectByExample(example);
		ScsHcoConfProgCalendarios dbItem = dbItems.get(0);
		dbItem.setEstado(Short.valueOf(estado));

		this.scsHcoConfProgCalendariosMapper.updateByExampleSelective(dbItem, example);

	}

	public int setGeneracionEnProceso(String idProgCal, String procesando) {
		ScsProgCalendariosExample example = new ScsProgCalendariosExample();
		example.createCriteria().andIdprogcalendarioEqualTo(Long.valueOf(idProgCal));

		ScsProgCalendarios record = new ScsProgCalendarios();
		record.setProcesandogeneracion(Short.valueOf(procesando));
		int res = scsProgCalendariosMapper.updateByExampleSelective(record, example);
		return res;

	}

	int crearCalendario(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String fechaHasta,
			String observaciones, Integer idTurnoPrincipal, Integer idGuardiaPrincipal, Integer idCalendarioPrincipal)
			throws Exception {
		List<CabeceraGuardiasCalendarioItem> cabGuardiaList = new ArrayList<CabeceraGuardiasCalendarioItem>();
		String idPersonaUltimoAnterior, fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior;
		GuardiasTurnoItem guardiaBean = new GuardiasTurnoItem();
		// Comprobamos que no haya ninguna cabecera de calendario para el turno ,
		// guardia,
		// fecha desde y fecha hasta. Si esta generada damos error. si no lo esta la
		// cogemos
		// y le concatenamos las observaciones

		String OLD_FORMATGlb = "yyyy-MM-dd HH:mm:ss.S";
		String OLD_FORMATGlb2 = "yyyy-MM-dd HH:mm:ss";
		String NEW_FORMATGlb = "dd/MM/yyyy";

		String control = fechaDesde.length() == 21 ? OLD_FORMATGlb : OLD_FORMATGlb2;

		String fechaDesdeAux = changeDateFormat(control, NEW_FORMATGlb, fechaDesde);
		String fechaHastaAux = changeDateFormat(control, NEW_FORMATGlb, fechaHasta);

		List<GuardiasCalendarioItem> calGuardiaList = scsGuardiasturnoExtendsMapper.getCalGuardiavVector(idTurno,
				idGuardia, fechaDesdeAux, fechaHastaAux, idInstitucion);

		if (calGuardiaList != null && calGuardiaList.size() > 0) {
//				calGuardiaList.forEach(calGuardia -> {
			GuardiasCalendarioItem calGuardia = calGuardiaList.get(0);

			String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
			String NEW_FORMAT = "dd/MM/yyyy";
			String fechainicio2 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calGuardia.getFechainicio());
			calGuardia.setFechainicio(fechainicio2);
			String fechafin2 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calGuardia.getFechafin());
			calGuardia.setFechafin(fechafin2);
			CabeceraGuardiasCalendarioItem cabGuardia = scsGuardiasturnoExtendsMapper.cabGuardiavVector(calGuardia,
					idInstitucion);
			if (cabGuardia != null) {
				cabGuardiaList.add(cabGuardia);
			}

			if (cabGuardiaList != null && cabGuardiaList.size() > 0) {
				errorGeneracionCalendario = "El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta
						+ "] ya está generado";
				throw new Exception(
						"El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta + "] ya está generado");
			}
			// modificamos las observaciones
			if (calGuardia.getObservaciones() != null && !calGuardia.getObservaciones().equals("")
					&& observaciones != null && !calGuardia.getObservaciones().equals(observaciones)) {
				calGuardia.setObservaciones(calGuardia.getObservaciones() + ". " + observaciones);
				// String[] claves = { ScsCalendarioGuardiasBean.C_IDINSTITUCION,
				// ScsCalendarioGuardiasBean.C_IDGUARDIA, ScsCalendarioGuardiasBean.C_IDTURNO,
				// ScsCalendarioGuardiasBean.C_IDCALENDARIOGUARDIAS };
				// String[] campos = { ScsCalendarioGuardiasBean.C_OBSERVACIONES };
				ScsCalendarioguardias record = new ScsCalendarioguardias();
				record.setIdturno(Integer.valueOf(calGuardia.getIdturno()));
				record.setIdguardia(Integer.valueOf(calGuardia.getIdguardia()));
				record.setIdcalendarioguardias(Integer.valueOf(calGuardia.getIdcalendarioguardias()));
				record.setIdinstitucion(Short.valueOf(calGuardia.getIdinstitucion()));
				record.setObservaciones(calGuardia.getObservaciones());
				scsCalendarioguardiasMapper.updateByPrimaryKeySelective(record);
			}
//				});
			return Integer.parseInt(calGuardia.getIdcalendarioguardias());
		}

		// Calculando idPersonaUltimoAnterior de guardias turno
		List<GuardiasTurnoItem> guardiasTurnoList = scsGuardiasturnoExtendsMapper.getIdPersonaUltimoAnterior(idTurno,
				idGuardia, idInstitucion);
		if (guardiasTurnoList != null && guardiasTurnoList.size() > 0) {
			guardiaBean = guardiasTurnoList.get(0);
			if (guardiaBean.getIdPersona_Ultimo() == null) {
				idPersonaUltimoAnterior = "";
				fechaSuscUltimoAnterior = "";
			} else {
				idPersonaUltimoAnterior = guardiaBean.getIdPersona_Ultimo().toString();
				fechaSuscUltimoAnterior = guardiaBean.getFechaSuscripcion_Ultimo();
			}
			if (guardiaBean.getIdGrupoGuardiaColegiado_Ultimo() == null) {
				idGrupoGuardiaColegiadoAnterior = "";
			} else {
				idGrupoGuardiaColegiadoAnterior = guardiaBean.getIdGrupoGuardiaColegiado_Ultimo().toString();
			}

			// calculando nuevo idcalendarioguardias

			String idcalendarioguardias = scsGuardiasturnoExtendsMapper.getIdCalendarioGuardias(idTurno, idGuardia,
					idInstitucion);
			if (idcalendarioguardias == null || (idcalendarioguardias != null && idcalendarioguardias.equals(""))) {
				idcalendarioguardias = "1";
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(new Date());
			String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
			String OLD_FORMAT2 = "yyyy-MM-dd HH:mm:ss.S";
			String OLD_FORMAT = "dd/MM/yyyy";
			// if (!fechaSuscUltimoAnterior.isEmpty()) {
			// fechaSuscUltimoAnterior = changeDateFormat(OLD_FORMAT, NEW_FORMAT,
			// fechaSuscUltimoAnterior);
			// }
			String auxFormat;
			if (fechaHasta.length() == 10) {
				auxFormat = OLD_FORMAT;
			} else {
				auxFormat = OLD_FORMAT2;
			}
			try {
				String fechaHastaOK = null;
				if (fechaHasta != null && !fechaHasta.isEmpty()) {
					fechaHastaOK = changeDateFormat(OLD_FORMAT2, NEW_FORMAT, fechaHasta);
				}
				String fechaDesdeOK = null;
				if (fechaDesde != null && !fechaDesde.isEmpty()) {
					fechaDesdeOK = changeDateFormat(OLD_FORMAT2, NEW_FORMAT, fechaDesde);
				}
				String todayOK = null;
				if (today != null && !today.isEmpty()) {
					todayOK = changeDateFormat(OLD_FORMAT, NEW_FORMAT, today);
				}
				String fechaSuscUltimoAnteriorOK = null;
				if (fechaSuscUltimoAnterior != null && !fechaSuscUltimoAnterior.isEmpty()) {
					fechaSuscUltimoAnteriorOK = changeDateFormat(OLD_FORMAT2, NEW_FORMAT, fechaSuscUltimoAnterior);
				}

				formatter = new SimpleDateFormat(NEW_FORMAT);
				ScsCalendarioguardias registroCalendario = new ScsCalendarioguardias();
				registroCalendario.setIdturnoprincipal(idTurnoPrincipal);
				registroCalendario.setIdguardiaprincipal(idGuardiaPrincipal);
				registroCalendario.setIdcalendarioguardiasprincipal(idCalendarioPrincipal);
				registroCalendario.setObservaciones(observaciones);
				registroCalendario.setIdturno(Integer.valueOf(idTurno));
				registroCalendario.setIdguardia(Integer.valueOf(idGuardia));
				if (fechaHastaOK != null && !fechaHastaOK.isEmpty()) {
					registroCalendario.setFechafin(formatter.parse(fechaHastaOK));
				}
				if (fechaDesdeOK != null && !fechaDesdeOK.isEmpty()) {
					registroCalendario.setFechainicio(formatter.parse(fechaDesdeOK));
				}
				registroCalendario.setIdcalendarioguardias(Integer.valueOf(idcalendarioguardias));
				registroCalendario.setIdinstitucion(Short.valueOf(idInstitucion));
				if (idPersonaUltimoAnterior != null && !idPersonaUltimoAnterior.isEmpty()) {
					registroCalendario.setIdpersonaUltimoanterior(Long.valueOf(idPersonaUltimoAnterior));
				}
				registroCalendario.setFechamodificacion(new Date());
				if (fechaSuscUltimoAnteriorOK != null && !fechaSuscUltimoAnteriorOK.isEmpty()) {
					registroCalendario.setFechasuscUltimoanterior(formatter.parse(fechaSuscUltimoAnteriorOK));
				}
				if (idGrupoGuardiaColegiadoAnterior != null && !idGrupoGuardiaColegiadoAnterior.isEmpty()) {
					registroCalendario.setIdgrupoguardiaUltimoanterior(Long.valueOf(idGrupoGuardiaColegiadoAnterior));
				}
				registroCalendario.setUsumodificacion(usuModificacion1);
				int res = scsCalendarioguardiasMapper.insert(registroCalendario);
				/*
				 * scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(
				 * idTurnoPrincipal, idGuardiaPrincipal, idCalendarioPrincipal, observaciones,
				 * idTurno, idGuardia, fechaHastaOK, fechaDesdeOK, idcalendarioguardias,
				 * idInstitucion, idPersonaUltimoAnterior, todayOK, fechaSuscUltimoAnteriorOK,
				 * idGrupoGuardiaColegiadoAnterior, usuModificacion1.toString());
				 */
				return Integer.parseInt(idcalendarioguardias);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
	
	public GuardiasCalendarioItem crearCalendario3(GuardiasCalendarioItem guardiasCalendarioItem, boolean cargaMasiva)
			throws Exception {
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String fechaDesde = guardiasCalendarioItem.getFechainicio();
		String fechaHasta = guardiasCalendarioItem.getFechafin();
		String observaciones = guardiasCalendarioItem.getObservaciones();
		
		List<CabeceraGuardiasCalendarioItem> cabGuardiasCalendarioList = new ArrayList<CabeceraGuardiasCalendarioItem>();
		GuardiasTurnoItem guardiasTurnoItem = new GuardiasTurnoItem();
		
		// Comprobamos que no haya ninguna cabecera de calendario para el turno ,
		// guardia,
		// fecha desde y fecha hasta. Si esta generada damos error. si no lo esta la
		// cogemos
		// y le concatenamos las observaciones

		//SELECT FROM SCS_CALENDARIOGUARDIAS
		List<GuardiasCalendarioItem> calGuardiaList = scsGuardiasturnoExtendsMapper.getCalGuardiavVector(idTurno,
				idGuardia, fechaDesde, fechaHasta, idInstitucion);

		if (calGuardiaList != null && calGuardiaList.size() > 0) { //Hay un calendario creado
			GuardiasCalendarioItem calGuardia = calGuardiaList.get(0);
			
			calGuardia.setFechainicio(changeDateFormat(DATE_LONG_MSEC, DATE_SHORT_SLASH, calGuardia.getFechainicio()));
			calGuardia.setFechafin(changeDateFormat(DATE_LONG_MSEC, DATE_SHORT_SLASH, calGuardia.getFechafin()));
			if(cargaMasiva) return calGuardia;
			
			//SELECT FROM SCS_CABECERAGUARDIAS
			CabeceraGuardiasCalendarioItem cabGuardia = scsGuardiasturnoExtendsMapper.cabGuardiavVector(calGuardia,
					idInstitucion);
			if (cabGuardia != null) {
				cabGuardiasCalendarioList.add(cabGuardia);
			}

			if (cabGuardiasCalendarioList != null && cabGuardiasCalendarioList.size() > 0) {
				errorGeneracionCalendario = "El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta
						+ "] ya está generado";
				throw new Exception(
						"El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta + "] ya está generado");
			}
			
			// modificamos las observaciones
			if (observaciones != null) {
				
				if(calGuardia.getObservaciones() != null && !calGuardia.getObservaciones().equals("")&& !calGuardia.getObservaciones().equals(observaciones)) {
					calGuardia.setObservaciones(calGuardia.getObservaciones() + ". " + observaciones);
				}else {
					calGuardia.setObservaciones(observaciones);	
				}
					
				ScsCalendarioguardias record = new ScsCalendarioguardias();
				record.setIdturno(Integer.valueOf(calGuardia.getIdturno()));
				record.setIdguardia(Integer.valueOf(calGuardia.getIdguardia()));
				record.setIdcalendarioguardias(Integer.valueOf(calGuardia.getIdcalendarioguardias()));
				record.setIdinstitucion(Short.valueOf(calGuardia.getIdinstitucion()));
				record.setObservaciones(calGuardia.getObservaciones());
				scsCalendarioguardiasMapper.updateByPrimaryKeySelective(record);
			}
			return calGuardia;
		}
		
		//No hay un calendario creado (SELECT FROM SCS_CALENDARIOGUARDIAS), creamos un calendario para la guardia

		// Calculando idPersonaUltimoAnterior de guardias turno (SELECT FROM SCS_GUARDIASTURNO)
		List<GuardiasTurnoItem> guardiasTurnoList = scsGuardiasturnoExtendsMapper.getIdPersonaUltimoAnterior(idTurno,
				idGuardia, idInstitucion);
		if (guardiasTurnoList != null && guardiasTurnoList.size() > 0) {
			guardiasTurnoItem = guardiasTurnoList.get(0);

			// calculando nuevo idcalendarioguardias
			String idcalendarioguardias = scsGuardiasturnoExtendsMapper.getIdCalendarioGuardias(idTurno, idGuardia,
					idInstitucion);
			if (idcalendarioguardias == null || (idcalendarioguardias != null && idcalendarioguardias.equals(""))) {
				idcalendarioguardias = "1";
			}
			
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_LONG); //"yyyy-MM-dd HH:mm:ss"
				ScsCalendarioguardias registroCalendario = new ScsCalendarioguardias();
				
				registroCalendario.setIdinstitucion(Short.valueOf(idInstitucion));
				registroCalendario.setIdturno(Integer.valueOf(idTurno));
				registroCalendario.setIdguardia(Integer.valueOf(idGuardia));
				registroCalendario.setIdcalendarioguardias(Integer.valueOf(idcalendarioguardias));
				registroCalendario.setObservaciones(observaciones);

				registroCalendario.setFechainicio(formatter.parse(changeDateFormat(DATE_SHORT_SLASH, DATE_LONG, fechaDesde)));
				registroCalendario.setFechafin(formatter.parse(changeDateFormat(DATE_SHORT_SLASH, DATE_LONG, fechaHasta)));

				registroCalendario.setUsumodificacion(Integer.valueOf(guardiasCalendarioItem.getUsumodificacion()));
				registroCalendario.setFechamodificacion(new Date());
				
				if(guardiasCalendarioItem.getIdturnoprincipal()!=null) {
					registroCalendario.setIdturnoprincipal(Integer.parseInt(guardiasCalendarioItem.getIdturnoprincipal()));
				}
				if(guardiasCalendarioItem.getIdguardiaprincipal()!=null) {
					registroCalendario.setIdguardiaprincipal(Integer.parseInt(guardiasCalendarioItem.getIdguardiaprincipal()));
				}
				if(guardiasCalendarioItem.getIdcalendarioguardiasprincipal()!=null) {
					registroCalendario.setIdcalendarioguardiasprincipal(Integer.parseInt(guardiasCalendarioItem.getIdcalendarioguardiasprincipal()));
				}
				if (guardiasTurnoItem.getFechaSuscripcion_Ultimo() != null && !guardiasTurnoItem.getFechaSuscripcion_Ultimo().isEmpty()) {
					registroCalendario.setFechasuscUltimoanterior(formatter.parse(changeDateFormat(DATE_LONG_MSEC, DATE_LONG, guardiasTurnoItem.getFechaSuscripcion_Ultimo())));
				}
				if (guardiasTurnoItem.getIdPersona_Ultimo() != null ) {
					registroCalendario.setIdpersonaUltimoanterior(guardiasTurnoItem.getIdPersona_Ultimo());
				}
				if (guardiasTurnoItem.getIdGrupoGuardiaColegiado_Ultimo() != null) {
					registroCalendario.setIdgrupoguardiaUltimoanterior(guardiasTurnoItem.getIdGrupoGuardiaColegiado_Ultimo());
				}
				
				//insert into SCS_CALENDARIOGUARDIAS
				int res = scsCalendarioguardiasMapper.insert(registroCalendario);
				/*
				 * scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(
				 * idTurnoPrincipal, idGuardiaPrincipal, idCalendarioPrincipal, observaciones,
				 * idTurno, idGuardia, fechaHastaOK, fechaDesdeOK, idcalendarioguardias,
				 * idInstitucion, idPersonaUltimoAnterior, todayOK, fechaSuscUltimoAnteriorOK,
				 * idGrupoGuardiaColegiadoAnterior, usuModificacion1.toString());
				 */
				
				guardiasCalendarioItem.setIdcalendarioguardias(idcalendarioguardias);
			} catch (Exception e) {
				return guardiasCalendarioItem;
			}
		}
		
		return guardiasCalendarioItem;
	}

	private String changeDateFormat(String OLD_FORMAT, String NEW_FORMAT, String oldDateString) {
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(oldDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		return newDateString;
	}

	public void inicializaParaGenerarCalendario(Integer idInstitucion, Integer idTurno, Integer idGuardia,
			Integer idCalendarioGuardias, String fechaInicio, String fechaFin) {
		idInstitucion1 = idInstitucion;
		idTurno1 = idTurno;
		idGuardia1 = idGuardia;
		idCalendarioGuardias1 = idCalendarioGuardias;
		fechaInicio1 = fechaInicio;
		fechaFin1 = fechaFin;
	}

	public String getProperty(final String parametro) {

		String valor = null;

		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		GenPropertiesExample.Criteria criteria = genPropertiesExample.createCriteria().andParametroEqualTo(parametro);

		List<GenProperties> genPropertiesList = genPropertiesMapper.selectByExample(genPropertiesExample);

		if (genPropertiesList != null && !genPropertiesList.isEmpty()) {
			valor = genPropertiesList.get(0).getValor();
		}

		return valor;
	}

	private String getPathCalPro(DatosCalendarioProgramadoItem item) {
		String pathFichero = getProperty(GUARDIAS_DIRECTORIO_FISICO_LOG_CALENDARIOS_PROGRAMADOS);
		Path pLog = Paths.get(pathFichero).resolve(item.getIdInstitucion());
		return pLog.toString();
	}

	private String getPathCalProByInstitucion(String idInstitucion) {
		String pathFichero = getProperty(GUARDIAS_DIRECTORIO_FISICO_LOG_CALENDARIOS_PROGRAMADOS);
		Path pLog = Paths.get(pathFichero).resolve(idInstitucion);
		return pLog.toString();
	}

	private void editarLog(DatosCalendarioProgramadoItem itemCalendarioProgramado, String accion, String descripcion) {
		try {
			LOGGER.info("CalendarioProgramado()- Se va a editar el excel informando");

			if (!descripcion.contains("No transaction aspect-managed TransactionStatus in scope")) { // Distinto del
																										// Error
																										// Generado para
																										// el RollBack
				String pathFichero = getPathCalPro(itemCalendarioProgramado);
				String nombreFichero = itemCalendarioProgramado.getNombreLogProgramacion();
				if (nombreFichero == null || nombreFichero.isEmpty()) {
					nombreFichero = getNombreFichero(itemCalendarioProgramado);
				}

				if (!pathFichero.isEmpty() && !nombreFichero.isEmpty() && pathFichero != null
						&& nombreFichero != null) {
					File file = new File(pathFichero, nombreFichero);
					if (file.exists()) {
						Workbook workBook = editarExcel(file, itemCalendarioProgramado, accion, descripcion);
						FileOutputStream fileOut;
						fileOut = new FileOutputStream(file);
						workBook.write(fileOut);
						fileOut.close();
						workBook.close();
						LOGGER.info(" CalendarioProgramado()- editar fichero:  " + nombreFichero);
					}
				} else {
					LOGGER.info(" CalendarioProgramado()- No se encuentra fichero para modificar" + pathFichero
							+ nombreFichero + " con id" + itemCalendarioProgramado.getIdCalendarioProgramado());
				}

			}

		} catch (Exception e) {
			LOGGER.error("CalendarioProgramado() - Error a la hora de editar Excel", e);

		}
	}

	private String getNombreFichero(DatosCalendarioProgramadoItem itemCalendarioProgramado) {

		ScsProgCalendariosExample exampleUsuarios = new ScsProgCalendariosExample();
		exampleUsuarios.createCriteria()
				.andIdinstitucionEqualTo(Short.valueOf(itemCalendarioProgramado.getIdInstitucion()))
				.andIdprogcalendarioEqualTo(Long.parseLong(itemCalendarioProgramado.getIdCalendarioProgramado()));

		List<ScsProgCalendarios> calendariosProgramados = scsProgCalendariosMapper.selectByExample(exampleUsuarios);
		return calendariosProgramados.get(0).getLogProgramacionName();
	}

	private Workbook editarExcel(File file, DatosCalendarioProgramadoItem facPro, String accion, String descripcion) {

		try {
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			//int rowCount = sheet.getLastRowNum();
			//sheet.shiftRows(1, rowCount, 1);
			Date date = new Date();
			String fecha = dateFormat.format(date);
			// Fecha
			Row Accion = sheet.createRow(sheet.getLastRowNum()+1);
			Accion.createCell(0).setCellValue(fecha);
			Accion.createCell(1).setCellValue(accion);
			Accion.createCell(2).setCellValue(descripcion);
			if(descripcion.equalsIgnoreCase("nueva fecha")) {
				Accion.createCell(2).setCellValue(fecha);
			}

			sheet.setColumnWidth(0, 6500);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 10000);
			fis.close();
			return workbook;

		} catch (Exception e) {
			throw new RuntimeException("Error al editar el archivo Excel: " + e.getMessage());
		}
	}

	public void generarCalendario2(boolean soloVacio) throws Exception {
		String porGrupos = "false";
		boolean rotacion = false;

		LOGGER.info("generarCalendarioAsync.generarCalendario2: idGuardia: "
				+ idGuardia1.toString() + " idTurno: " + idTurno1.toString() + " idInstitucion: " + idInstitucion1.toString());
		
		GuardiasTurnoItem guardia = scsGuardiasturnoExtendsMapper.getGuardia(idGuardia1.toString(), idTurno1.toString(),
				idInstitucion1.toString());
		porGrupos = guardia.getPorGrupos();
		rotacion = "1".equals(guardia.getRotarComponentes()) ? true : false;
		String nombreGuardia = guardia.getNombre();

		// validando que no haya ninguna guardia realizada

		if (!validarBorradoGuardias(idInstitucion1, idCalendarioGuardias1, idTurno1, idGuardia1,fechaInicio1)) {
			errorGeneracionCalendario = "Error en la validación del borrado de guardias";
			LOGGER.error("No se puede borrar un CALENDARIO de guardias porque existen guardias realizadas");
//			throw new Exception(
//					"No se puede borrar un CALENDARIO de guardias porque existen guardias realizadas");
		}

		// obteniendo las Guardias vinculadas
		List<GuardiasTurnoItem> guardiasVinculadas = scsGuardiasturnoExtendsMapper
				.getGuardiasVnculadas(idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString());

		// si hay guardias vinculadas, hay que crear los calendarios antes que nada
		String textoAutomatico = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());
		List<GuardiasCalendarioItem> calendariosVinculados = new ArrayList<GuardiasCalendarioItem>();
		if (guardiasVinculadas.size() != 0) {
			for (GuardiasTurnoItem guardiaV : guardiasVinculadas) {

				// Si la guardia A está vinculada con la guardia B (B es la principal) y a su
				// vez la guardia B está vinculado con la guardia C (C es la principal), el
				// sistema no debe permitir la generación de un calendario para las guardias A
				// ni B
				// Sólo se procederá a generar calendarios de la guardia C, lo cual
				// automáticamente lanzará primero la generación de B y de A

				GuardiasCalendarioItem calendarioGuardiasBean = new GuardiasCalendarioItem(
						guardiaV.getIdInstitucion().toString(), guardiaV.getIdTurno().toString(),
						guardiaV.getIdGuardia().toString(), null, fechaFin1.toString(), fechaInicio1.toString(),
						textoAutomatico, today, "0", null, null, null, new Integer(idTurno1).toString(),
						new Integer(idGuardia1).toString(), new Integer(idCalendarioGuardias1).toString());

				int idCalendario = crearCalendario2(calendarioGuardiasBean.getIdinstitucion(),
						calendarioGuardiasBean.getIdturno(), calendarioGuardiasBean.getIdguardia(),
						calendarioGuardiasBean.getFechainicio(), calendarioGuardiasBean.getFechafin(),
						calendarioGuardiasBean.getObservaciones(),
						new Integer(calendarioGuardiasBean.getIdturnoprincipal()),
						new Integer(calendarioGuardiasBean.getIdguardiaprincipal()),
						new Integer(calendarioGuardiasBean.getIdcalendarioguardiasprincipal()));

				LOGGER.info("generarCalendarioAsync.generarCalendario2: idCalendario: " + idCalendario);
				if (idCalendario > 0) {
					calendarioGuardiasBean.setIdcalendarioguardias(Integer.toString(idCalendario));

					// lineaLog.add("OK");
					calendariosVinculados.add(calendarioGuardiasBean);
				} else {
					// lineaLog.add("Fallo. Puede que ya exista el calendario");
					// log.addLog(lineaLog);
					Map<String, Object> mapLog = new HashMap();
					mapLog.put("*Fallo. Puede que ya exista el calendario", "");
					errorGeneracionCalendario = "*Fallo. Puede que ya exista el calendario";
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*Fallo. Puede que ya exista el calendario");
				}

			}
		}
//check false
		if (!soloVacio) {
			// inicializando calendario
			inicializaParaMatriz(new Integer(idInstitucion1), new Integer(idTurno1), new Integer(idGuardia1),
					new Integer(idCalendarioGuardias1), calendariosVinculados, null); // enviar log en lugar de null en el ultimo param

			// obteniendo los periodos
			try {
				LOGGER.info(beanGuardiasTurno1);
				calcularMatrizPeriodosDiasGuardiaAutomatico(); //(L) lo guarda en arrayPeriodosDiasGuardiaSJCS1, lista de listas<String> si la duracion es 5 dias genera paquete de 5 fechas
				List<Integer> lDiasASeparar = getDiasASeparar(new Integer(idInstitucion1), new Integer(idTurno1),
						new Integer(idGuardia1)); //(L) Si es null caso 1, si tiene valo caso 0

				// obteniendo la matriz de letrados de guardia
				listaDatosExcelGeneracionCalendarios = new ArrayList<>();//(L) Excel logs asignaciones
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*INICIO generacion", guardia.getNombre() + " (" + fechaInicio1 + " - " + fechaFin1 + ")");
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info(new String[] { "*INICIO generacion",
						guardia.getNombre() + " (" + fechaInicio1 + " - " + fechaFin1 + ")" });
				//TODO(L) Podria hacer aqui la distinción entre calendarios programados y carga masiva?
				if (porGrupos.equals("1")) {
					calcularMatrizLetradosGuardiaPorGrupos(lDiasASeparar, rotacion);
				} else {
					calcularMatrizLetradosGuardia(lDiasASeparar);//(L) arrayPeriodosLetradosSJCS1
				}

				// log.addLog(new String[] { "FIN generacion" });
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*FIN generacion", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*FIN generacion" + "");

			} catch (Exception e) {
				if (e.getMessage().equals("periodoSinDias")) {
					// log.addLog(new String[] { " ", "Sin periodos" } );

					Map<String, Object> mapLog = new HashMap();
					mapLog.put("* ", "Sin periodos");
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("* " + "Sin periodos");
				} else {
					throw e;
				}

			}
		}

	}
	
	public void generarCalendario3(GuardiasCalendarioItem guardiasCalendarioItem, boolean soloVacio, InscripcionGuardiaItem inscripcion, boolean esCargaMasiva) throws Exception {
		String usuModificacion = guardiasCalendarioItem.getUsumodificacion();
		
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		
		String fechaDesde = guardiasCalendarioItem.getFechainicio();
		String fechaHasta = guardiasCalendarioItem.getFechafin();
		
		String textoAutomatico = guardiasCalendarioItem.getObservaciones();

		LOGGER.info("generarCalendarioAsync.generarCalendario2: idGuardia: "
				+ idGuardia + " idTurno: " + idTurno+ " idInstitucion: " + idInstitucion);
		
		GuardiasTurnoItem guardia = scsGuardiasturnoExtendsMapper.getGuardia(idGuardia, idTurno, idInstitucion);
		boolean porGrupos = "1".equals(guardia.getPorGrupos()) ? true : false;
		boolean rotacion = "1".equals(guardia.getRotarComponentes()) ? true : false;

		// obteniendo las Guardias vinculadas
		List<GuardiasTurnoItem> guardiasVinculadas = scsGuardiasturnoExtendsMapper.getGuardiasVnculadas(idInstitucion, idTurno, idGuardia);

		// si hay guardias vinculadas, hay que crear los calendarios antes que nada
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_SHORT_SLASH);
		String today = formatter.format(new Date());
		List<GuardiasCalendarioItem> calendariosVinculados = new ArrayList<GuardiasCalendarioItem>();
		if (guardiasVinculadas.size() != 0) {
			for (GuardiasTurnoItem guardiaVinc : guardiasVinculadas) {

				// Si la guardia A está vinculada con la guardia B (B es la principal) y a su
				// vez la guardia B está vinculado con la guardia C (C es la principal), el
				// sistema no debe permitir la generación de un calendario para las guardias A
				// ni B
				// Sólo se procederá a generar calendarios de la guardia C, lo cual
				// automáticamente lanzará primero la generación de B y de A

				GuardiasCalendarioItem calendarioGuardiasVinc = new GuardiasCalendarioItem(
						idInstitucion, guardiaVinc.getIdTurno().toString(),
						guardiaVinc.getIdGuardia().toString(), "0", fechaDesde, fechaHasta,
						textoAutomatico, today, usuModificacion, idTurno,
						idGuardia,idCalendarioGuardias);
				
				calendarioGuardiasVinc = crearCalendario3(calendarioGuardiasVinc,false);
				String idCalendarioVinculado = calendarioGuardiasVinc.getIdcalendarioguardias();
//				int idCalendario = crearCalendario2(calendarioGuardiasVinc.getIdinstitucion(),
//						calendarioGuardiasVinc.getIdturno(), calendarioGuardiasVinc.getIdguardia(),
//						calendarioGuardiasVinc.getFechainicio(), calendarioGuardiasVinc.getFechafin(),
//						calendarioGuardiasVinc.getObservaciones(),
//						new Integer(calendarioGuardiasVinc.getIdturnoprincipal()),
//						new Integer(calendarioGuardiasVinc.getIdguardiaprincipal()),
//						new Integer(calendarioGuardiasVinc.getIdcalendarioguardiasprincipal()));

				LOGGER.info("generarCalendarioAsync.generarCalendario2: idCalendarioVinculado: " + idCalendarioVinculado);
				if (Integer.valueOf(idCalendarioVinculado) > 0) {
					calendarioGuardiasVinc.setIdcalendarioguardias(idCalendarioVinculado);

					calendariosVinculados.add(calendarioGuardiasVinc);
				} else {
					Map<String, Object> mapLog = new HashMap();
					mapLog.put("*Fallo. Puede que ya exista el calendario", "");
					errorGeneracionCalendario = "*Fallo. Puede que ya exista el calendario";
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*Fallo. Puede que ya exista el calendario");
				}

			}
		}

		if (!soloVacio) {
			// inicializando calendario: De aqui solo necesito mantener el calculo de los festivos
//			inicializaParaMatriz(new Integer(idInstitucion), new Integer(idTurno), new Integer(idGuardia),
//					new Integer(idCalendarioGuardias), calendariosVinculados, null); // enviar log en lugar de null en el ultimo param
//			
			// obteniendo los periodos
			try {
				LOGGER.info(guardia);
				boolean hasGuardiasVinculadas = (calendariosVinculados!=null && !calendariosVinculados.isEmpty()) ? true : false;
				ArrayList<ArrayList<String>> arrayPeriodosDiasGuardia = calcularMatrizPeriodosDiasGuardiaAutomatico3( guardia, fechaDesde, fechaHasta, hasGuardiasVinculadas); //(L) lo guarda en arrayPeriodosDiasGuardiaSJCS1, lista de listas<String> si la duracion es 5 dias genera paquete de 5 fechas
				List<Integer> lDiasASeparar = getDiasASeparar3(idInstitucion, idTurno, idGuardia); //(L) Si es null caso 1, si tiene valo caso 0

				// obteniendo la matriz de letrados de guardia
				listaDatosExcelGeneracionCalendarios = new ArrayList<>();//(L) Excel logs asignaciones
				
				Map<String, Object> mapLog = new HashMap<String, Object>();
				mapLog.put("*INICIO generacion", guardia.getNombre() + " (" + fechaDesde + " - " + fechaHasta + ")");
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info(new String[] { "*INICIO generacion",
						guardia.getNombre() + " (" + fechaDesde + " - " + fechaHasta + ")" });
				
				//TODO(L) Podria hacer aqui la distinción entre calendarios programados y carga masiva?
				
				if(!esCargaMasiva) {
					if (porGrupos) {
						calcularMatrizLetradosGuardiaPorGrupos3(guardiasCalendarioItem, calendariosVinculados, guardia, arrayPeriodosDiasGuardia, lDiasASeparar, rotacion, inscripcion);
					} else {
						//GuardiasCalendarioItem guardiasCalendarioItem, List<GuardiasCalendarioItem> calendariosVinculados, GuardiasTurnoItem guardiasTurnoItem ,ArrayList<ArrayList<String>> arrayPeriodosDiasGuardia, List lDiasASeparar
						calcularMatrizLetradosGuardia3(guardiasCalendarioItem, calendariosVinculados, guardia, arrayPeriodosDiasGuardia, lDiasASeparar,  inscripcion);//(L) arrayPeriodosLetradosSJCS1,calendariosVinculados
					}
				}else {
						insertarGuardiasColegiadoCM(guardiasCalendarioItem, calendariosVinculados, guardia, arrayPeriodosDiasGuardia, lDiasASeparar,  inscripcion);
				}
				
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*FIN generacion", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*FIN generacion" + "");

			} catch (Exception e) {
				if (e.getMessage().equals("periodoSinDias")) {
					Map<String, Object> mapLog = new HashMap();
					mapLog.put("* ", "Sin periodos");
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("* " + "Sin periodos");
				} else {
					throw e;
				}
			}
		}
	}

	

	private void insertarGuardiasColegiadoCM(GuardiasCalendarioItem guardiasCalendarioItem,
			List<GuardiasCalendarioItem> calendariosVinculados, GuardiasTurnoItem guardia,
			ArrayList<ArrayList<String>> arrayPeriodosDiasGuardia, List<Integer> lDiasASeparar,
			InscripcionGuardiaItem inscripcion) {
		// Variables generales
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con
		// letrado
		diasGuardia = new ArrayList<String>();
		int posicion = 1;
		int inicial = 0;

		primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(0);
		segundoPeriodo = null;
		if (calendariosVinculados == null || calendariosVinculados.isEmpty())
			inicial = 0;
		else {
			Map<String, Object> mapLog2 = new HashMap();
			// log.addLog(new String[] {"Guardias vinculadas",
			// calendariosVinculados1.toString()});
			mapLog2.put("*Guardias vinculadas ", calendariosVinculados.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Guardias vinculadas " + calendariosVinculados.toString());
			inicial = 1;
		}
		// Para cada dia o conjunto de dias:
		for (int i = inicial; i < arrayPeriodosDiasGuardia.size(); i++) {

			// obteniendo conjunto de dias
			// Nota: cada periodo es un arraylist de fechas (String en formato de fecha
			// corto DD/MM/YYYY)
			if (calendariosVinculados == null || calendariosVinculados.isEmpty()) {
				diasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
			} // Si hay guardias vinculadas, hay que mirar dos periodos a la vez
			else {
				segundoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
				diasGuardia = new ArrayList<String>();
				diasGuardia.addAll(primerPeriodo);
				diasGuardia.addAll(segundoPeriodo);
			}

		}

		List<LetradoInscripcionItem> alLetradosOrdenados = new ArrayList<LetradoInscripcionItem>();
		alLetradosOrdenados = getInscritoInfo(inscripcion);

		ArrayList<LetradoInscripcionItem> alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
		LetradoInscripcionItem letradoGuardia = alLetradosOrdenados.get(0);

		letradoGuardia.setPeriodoGuardias(diasGuardia); // (L) Se supone que aqui esta el letrado seleccionado
		LetradoInscripcionItem letradoGuardiaClone = (LetradoInscripcionItem) letradoGuardia;
		if (inscripcion.getPosicion() != 0) {
			letradoGuardiaClone.setPosicion(inscripcion.getPosicion());
		} else {
			letradoGuardiaClone.setPosicion(posicion);
			posicion++;
		}

		alLetradosInsertar.add(letradoGuardiaClone);

		try {
			if (calendariosVinculados == null || calendariosVinculados.isEmpty()) {// TODO: (L) Insertas en
																					// guardiacolegiado y cabecera
				almacenarAsignacionGuardia3(guardiasCalendarioItem, guardia, alLetradosInsertar, diasGuardia,
						lDiasASeparar, "gratuita.literal.comentario.sustitucion");
			} else {
				// guardando la principal
				almacenarAsignacionGuardia3(guardiasCalendarioItem, guardia, alLetradosInsertar, primerPeriodo,
						lDiasASeparar, "gratuita.literal.comentario.sustitucion");

				// guardando para cada una de las vinculadas
				for (GuardiasCalendarioItem calendario : calendariosVinculados) {
					// modificando la guardia y calendario en el que se insertaran las guardias
					for (LetradoInscripcionItem lg : alLetradosInsertar) {
						lg.setIdinstitucion(new Short(calendario.getIdinstitucion()));
						lg.setIdTurno(Integer.parseInt(calendario.getIdturno()));
						lg.setIdGuardia(Integer.parseInt(calendario.getIdguardia()));
					}

					// guardando en BD
					almacenarAsignacionGuardia3(guardiasCalendarioItem, guardia, alLetradosInsertar, segundoPeriodo,
							lDiasASeparar, "gratuita.literal.comentario.sustitucion");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getStackTrace());
		}

	}

	public boolean esUltimoCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno,
			Integer idGuardia) {

		// VALIDA SI ES EL ÚLTIMO CALENDARIO CREADO

		int totalGuardiasCalendarios = 0;
		boolean correcto = false;
		try {
			totalGuardiasCalendarios = scsGuardiasturnoExtendsMapper.getTotalGuardias(idInstitucion.toString(),
					idCalendarioGuardias.toString(), idTurno.toString(), idGuardia.toString());

			if (totalGuardiasCalendarios == 0)
				correcto = true;
		} catch (Exception e) {
			correcto = false;
		}
		return correcto;

	}

	// Comprueba antes de borrar un CALENDARIO de guardias que no exista ninguna
	// guardia realizada.
	public boolean validarBorradoGuardias(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno,
			Integer idGuardia, String fechaIni) {
		int totalLetrados = 0;
		boolean correcto = false;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(new Date());
			totalLetrados = scsGuardiasturnoExtendsMapper.getTotalLetrados(idInstitucion.toString(),
					idCalendarioGuardias.toString(), idTurno.toString(), idGuardia.toString(), today, fechaIni);

			if (totalLetrados == 0)
				correcto = true;
		} catch (Exception e) {
			correcto = false;
		}
		return correcto;
	}
	
	public boolean validarColegiadosFacturados(Short idInstitucion, Integer idCalendarioGuardias, Integer idTurno,
			Integer idGuardia, String fechaIni, String fechaFin) {
		int totalLetrados = 0;
		boolean correcto = false;
		try {
			totalLetrados = scsGuardiasturnoExtendsMapper.getTotalColegiadosFacturados(idInstitucion.toString(), idCalendarioGuardias.toString(), 
					idTurno.toString(), idGuardia.toString(), fechaIni, fechaFin);
			if (totalLetrados == 0)
				correcto = true;
		} catch (Exception e) {
			correcto = false;
		}
		return correcto;
	}
	
	public boolean validarTieneAsistencias(Short idInstitucion, Integer idTurno,
			Integer idGuardia, String fechaIni, String fechaFin) {
		int totalLetrados = 0;
		boolean correcto = false;
		try {
			totalLetrados = scsGuardiasturnoExtendsMapper.getTotalColegiadosConAsistencias(idInstitucion.toString(),idTurno.toString(), idGuardia.toString(), fechaIni, fechaFin);
			if (totalLetrados == 0)
				correcto = true;
		} catch (Exception e) {
			correcto = false;
		}
		return correcto;
	}


	public void inicializaParaMatriz(Integer idInstitucion, Integer idTurno, Integer idGuardia,
			Integer idCalendarioGuardias, List<GuardiasCalendarioItem> calendariosVinculados, LogFile log)
			throws Exception {
		List<GuardiasTurnoItem> vGuardias;
		// 1. GUARDIATURNO: Todos los datos de la guardia

		try {
			vGuardias = scsGuardiasturnoExtendsMapper.getAllDatosGuardia(idInstitucion1.toString(), idTurno1.toString(),
					idGuardia1.toString());
			if (!vGuardias.isEmpty())
				beanGuardiasTurno1 = vGuardias.get(0);
		} catch (Exception e) {
			beanGuardiasTurno1 = null;
			errorGeneracionCalendario = "Error obteniendo todos los datos de la guardia";
		}
		// Calendarios vinculados (guardias vinculadas)
		if (calendariosVinculados == null || calendariosVinculados.isEmpty())
			calendariosVinculados1 = null;
		else
			calendariosVinculados1 = calendariosVinculados;

		// 2. CALENDARIO: Fecha de inicio y fin
		try {
			List<GuardiasCalendarioItem> vCalendarioGuardias = scsGuardiasturnoExtendsMapper.getCalGuardias(
					idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(),
					idCalendarioGuardias1.toString());

			if (!vCalendarioGuardias.isEmpty()) {
				GuardiasCalendarioItem beanCalendarioGuardias = (GuardiasCalendarioItem) vCalendarioGuardias.get(0);

				String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
				String NEW_FORMAT = "dd/MM/yyyy";
				fechaInicio1 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, beanCalendarioGuardias.getFechainicio());
				fechaFin1 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, beanCalendarioGuardias.getFechafin());
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo la fecha inicio y fin del calendario";
			fechaInicio1 = "";
			fechaFin1 = "";
		}

		// 3. CALENDARIO FESTIVOS:
		Calendar calFechaFin = StringToCalendar(fechaFin1);
		calFechaFin.add(Calendar.MONTH, 1); // sumamos 1 mes
		// esto se hace por si la generacion llega a otros dias posteriores a la fecha
		// de fin; esto es,
		// cuando la configuracion es por semanas/quincenas/meses
		// pero el calendario termina antes del ultimo dia de la semana/quincena/mes
		try {
			vDiasFestivos1 = obtenerFestivosTurno(idInstitucion, idTurno, fechaInicio1, calFechaFin.getTime());
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo los festivos del turno";
		}

		// 4. INICIALIZACION DE LOS 2 ARRAYS:
		arrayPeriodosDiasGuardiaSJCS1 = new ArrayList();
		arrayPeriodosLetradosSJCS1 = new ArrayList();
		// this.log = log;
	}

	List<String> obtenerFestivosTurno(Integer idInstitucion, Integer idTurno, String fechaInicio, Date fechaFin)
			throws Exception {
		LocalDate date4 = ZonedDateTime
				.parse(fechaFin.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
				.toLocalDate();
		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd/MM/yyyy";
		String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		List <RangoFechasItem> rangosFechasFestivos  = scsGuardiasturnoExtendsMapper.getFestivosAgenda(fechaInicio, fechaFinOk, idInstitucion.toString());
		List<String> fechasFestivas = new ArrayList<String>();
		if(rangosFechasFestivos == null || rangosFechasFestivos.isEmpty()) return fechasFestivas;
		
		for(RangoFechasItem itemFecha : rangosFechasFestivos) {
			if(itemFecha.getFechaDesde().equals(itemFecha.getFechaHasta())) {
				fechasFestivas.add(itemFecha.getFechaDesde());
			}else {
				long fechasBT = daysBetween(itemFecha.getFechaDesde(),itemFecha.getFechaHasta());
				
				if(fechasBT == 1) {
					fechasFestivas.add(itemFecha.getFechaDesde());
					fechasFestivas.add(itemFecha.getFechaHasta());
				}else {
					fechasFestivas.add(itemFecha.getFechaDesde());
					String fechaAuxIni = itemFecha.getFechaDesde();
					for (int i = 0; i < fechasBT; i++) {
						String fechaNext = this.diaSiguienteDate(fechaAuxIni);
						fechasFestivas.add(fechaNext);
						fechaAuxIni = fechaNext;
					}
				}
			}	
		}
		quitarRepetidos(fechasFestivas);
		Collections.sort(fechasFestivas);//ORDENAR
		return fechasFestivas;
		//return scsGuardiasturnoExtendsMapper.getFestivosTurno(fechaInicio, fechaFinOk.toString(),
		//		idInstitucion1.toString(), Integer.toString(INSTITUCION_CGAE), idTurno.toString());
	}
	List<String> obtenerFestivosTurno3(String idInstitucion,String fechaInicio, String fechaFin)
			throws Exception {
		// esto se hace por si la generacion llega a otros dias posteriores a la fecha de fin; esto es,
		// cuando la configuracion es por semanas/quincenas/meses
		// pero el calendario termina antes del ultimo dia de la semana/quincena/mes
		Calendar calFechaFin = StringToCalendar(fechaFin);
		calFechaFin.add(Calendar.MONTH, 1); // sumamos 1 mes
		String fechaFinOk = new SimpleDateFormat("dd/MM/yyyy").format(calFechaFin.getTime());	
//		LocalDate date4 = ZonedDateTime
//				.parse(calFechaFin.getTime().toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
//				.toLocalDate();
//		String OLD_FORMAT = "yyyy-MM-dd";
//		String NEW_FORMAT = "dd/MM/yyyy";
//		String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		List <RangoFechasItem> rangosFechasFestivos  = scsGuardiasturnoExtendsMapper.getFestivosAgenda(fechaInicio, fechaFinOk, idInstitucion);
		List<String> fechasFestivas = new ArrayList<String>();
		if(rangosFechasFestivos == null || rangosFechasFestivos.isEmpty()) return fechasFestivas;
		
		for(RangoFechasItem itemFecha : rangosFechasFestivos) {
			if(itemFecha.getFechaDesde().equals(itemFecha.getFechaHasta())) {
				fechasFestivas.add(itemFecha.getFechaDesde());
			}else {
				long fechasBT = daysBetween(itemFecha.getFechaDesde(),itemFecha.getFechaHasta());
				
				if(fechasBT == 1) {
					fechasFestivas.add(itemFecha.getFechaDesde());
					fechasFestivas.add(itemFecha.getFechaHasta());
				}else {
					fechasFestivas.add(itemFecha.getFechaDesde());
					String fechaAuxIni = itemFecha.getFechaDesde();
					for (int i = 0; i < fechasBT; i++) {
						String fechaNext = this.diaSiguienteDate(fechaAuxIni);
						fechasFestivas.add(fechaNext);
						fechaAuxIni = fechaNext;
					}
				}
			}	
		}
		quitarRepetidos(fechasFestivas);
		Collections.sort(fechasFestivas);//ORDENAR
		return fechasFestivas;
		//return scsGuardiasturnoExtendsMapper.getFestivosTurno(fechaInicio, fechaFinOk.toString(),
		//		idInstitucion1.toString(), Integer.toString(INSTITUCION_CGAE), idTurno.toString());
	}
	private List<String> quitarRepetidos(List<String> fechasFestivas){
		Set<String> hashSet = new HashSet<String>(fechasFestivas);
		fechasFestivas.clear();
		fechasFestivas.addAll(hashSet);
        return fechasFestivas;
	}
	private String diaSiguienteDate(String fechaIn) throws ParseException {
		 Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(fechaIn);  

		DateFormat dateFormatFin = new SimpleDateFormat("dd/MM/yyyy");  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		cal.add(Calendar.DAY_OF_YEAR, 1); 
		return dateFormatFin.format(cal.getTime());
	}
	
	private long daysBetween(String fechaIni, String fechaFin) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String strDateDesde = dateFormat.format(	 new SimpleDateFormat("dd/MM/yyyy").parse(fechaIni));  
		String strDateHasta = dateFormat.format( new SimpleDateFormat("dd/MM/yyyy").parse(fechaFin));
		LocalDate dateBefore = LocalDate.parse(strDateDesde);
		LocalDate dateAfter = LocalDate.parse(strDateHasta);
		return ChronoUnit.DAYS.between(dateBefore, dateAfter);
	}
	List<String> obtenerFestivosTurnoFijo(Integer idInstitucion, Integer idTurno, String fechaInicio, Date fechaFin)
			throws Exception {
		LocalDate date4 = ZonedDateTime
				.parse(fechaFin.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
				.toLocalDate();
		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd/MM/yyyy";
		String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		List <RangoFechasItem> rangosFechasFestivos  = scsGuardiasturnoExtendsMapper.getFestivosAgenda(fechaInicio, fechaFinOk, idInstitucion.toString());
		List<String> fechasFestivas = new ArrayList<String>();
		if(rangosFechasFestivos == null || rangosFechasFestivos.isEmpty()) return fechasFestivas;
		
		for(RangoFechasItem itemFecha : rangosFechasFestivos) {
			if(itemFecha.getFechaDesde().equals(itemFecha.getFechaHasta())) {
				fechasFestivas.add(itemFecha.getFechaDesde());
			}else {
				long fechasBT = daysBetween(itemFecha.getFechaDesde(),itemFecha.getFechaHasta());
				
				if(fechasBT == 1) {
					fechasFestivas.add(itemFecha.getFechaDesde());
					fechasFestivas.add(itemFecha.getFechaHasta());
				}else {
					fechasFestivas.add(itemFecha.getFechaDesde());
					String fechaAuxIni = itemFecha.getFechaDesde();
					for (int i = 0; i < fechasBT; i++) {
						String fechaNext = this.diaSiguienteDate(fechaAuxIni);
						fechasFestivas.add(fechaNext);
						fechaAuxIni = fechaNext;
					}
				}
			}	
		}
		quitarRepetidos(fechasFestivas);
		Collections.sort(fechasFestivas);//ORDENAR
		return fechasFestivas;
	}

	Calendar StringToCalendar(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH); //new SimpleDateFormat("dd/MM/YY", Locale.ENGLISH); 
		cal.setTime(sdf.parse(date));
		return cal;
	}

	public void calcularMatrizPeriodosDiasGuardiaAutomatico() throws Exception {
		// generando calendario normal
		arrayPeriodosDiasGuardiaSJCS1 = new ArrayList();
		ArrayList<ArrayList<String>> listaDiasPeriodos = this.obtenerPeriodosGuardia();//(L) pasar variables por parametro
		if (listaDiasPeriodos != null && !listaDiasPeriodos.isEmpty())
			arrayPeriodosDiasGuardiaSJCS1.addAll(listaDiasPeriodos);
		else
			throw new Exception("periodoSinDias");

		// En el caso de que existan guardias vinculadas, se ha de generar un periodo
		// mas:
		// Pero no sirve generar un calendario ampliado (que termine mas tarde),
		// porque no hay forma de saber cual es el ultimo dia del calendario real
		// Asi que la unica forma es:
		// generar el calendario real (antes) y (ahora) uno ampliado para obtener el
		// periodo de mas
		if (calendariosVinculados1 != null) {
			// obteniendo una fechaFin suficientemente posterior
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_JAVA);
				SimpleDateFormat miFormatoFecha = new SimpleDateFormat(DATE_FORMAT_SHORT_SPANISH);
				Date dFechaInicio = sdf.parse(fechaInicio1);
				ArrayList<String> ultimoPeriodo = listaDiasPeriodos.get(listaDiasPeriodos.size() - 1);
				Date dFechaFin = miFormatoFecha.parse(ultimoPeriodo.get(ultimoPeriodo.size() - 1));

				Date nuevaFechaFin = new Date(dFechaFin.getTime() + dFechaFin.getTime() - dFechaInicio.getTime());
				Calendar cal = Calendar.getInstance();
				cal.setTime(nuevaFechaFin);
				cal.add(Calendar.DATE, 1);
				fechaFin1 = sdf.format(cal.getTime());
			} catch (ParseException e) {
				throw new Exception(e + ": Error al parsear las fechas del calendario");
			}

			// generando el calendario ampliado
			ArrayList<ArrayList<String>> listaDiasPeriodosAmpliado = obtenerPeriodosGuardia();

			// anyadiendo el primer periodo del calendario ampliado
			arrayPeriodosDiasGuardiaSJCS1.add(listaDiasPeriodosAmpliado.get(listaDiasPeriodos.size()));
		}
	}
	
	public ArrayList<ArrayList<String>> calcularMatrizPeriodosDiasGuardiaAutomatico3(GuardiasTurnoItem guardiasTurnoItem , String fechaDesde, String fechaHasta, boolean hasGuardiasVinculadas ) throws Exception {
		// generando calendario normal
		ArrayList<ArrayList<String>> periodosDiasGuardiaSJCS = new ArrayList<ArrayList<String>> ();

		List<String> diasFestivos = null;
		try {
			diasFestivos = obtenerFestivosTurno3(guardiasTurnoItem.getIdInstitucion(), fechaDesde, fechaHasta);
		} catch (Exception e) {
			LOGGER.info("Error obteniendo los festivos del turno");
		}
		
		CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(guardiasTurnoItem, fechaDesde, fechaHasta,
				diasFestivos);
		ArrayList<ArrayList<String>> listaDiasPeriodos = this.obtenerPeriodosGuardia3(calendarioAutomatico);//(L) pasar variables por parametro
		
		if (listaDiasPeriodos != null && !listaDiasPeriodos.isEmpty())
			periodosDiasGuardiaSJCS.addAll(listaDiasPeriodos);
		else
			throw new Exception("periodoSinDias");
		
		//Las guaridas vinculadas no tienen configuración, si se añade una guardia vinculada, solo se hace el insert en el dia despues de la insercion en la guardia normal.

		/*
		 * En el caso de que existan guardias vinculadas, se ha de generar un periodo
		 * mas: Pero no sirve generar un calendario ampliado (que termine mas tarde),
		 * porque no hay forma de saber cual es el ultimo dia del calendario real. Asi
		 * que la unica forma es: generar el calendario real (antes) y (ahora) uno
		 * ampliado para obtener el periodo de mas
		 */
		/*if (hasGuardiasVinculadas) {
			// obteniendo una fechaFin suficientemente posterior
			try {
//				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_JAVA);
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT_SLASH);
				Date dFechaInicio = sdf.parse(fechaDesde);
				ArrayList<String> ultimoPeriodo = listaDiasPeriodos.get(listaDiasPeriodos.size() - 1);
				Date dFechaFin = sdf.parse(ultimoPeriodo.get(ultimoPeriodo.size() - 1));

				Date nuevaFechaFin = new Date(dFechaFin.getTime() + dFechaFin.getTime() - dFechaInicio.getTime());
				Calendar cal = Calendar.getInstance();
				cal.setTime(nuevaFechaFin);
				cal.add(Calendar.DATE, 1);
				fechaHasta = sdf.format(cal.getTime()); //Revisar antes se actualizaba fechafin1
			} catch (ParseException e) {
				throw new Exception(e + ": Error al parsear las fechas del calendario");
			}
			CalendarioAutomatico calendarioAutomaticoAmpliado = new CalendarioAutomatico(guardiasTurnoItem, fechaDesde, fechaHasta,
					diasFestivos);
			// generando el calendario ampliado
			ArrayList<ArrayList<String>> listaDiasPeriodosAmpliado = obtenerPeriodosGuardia3(calendarioAutomaticoAmpliado);

			// anyadiendo el primer periodo del calendario ampliado
			periodosDiasGuardiaSJCS.add(listaDiasPeriodosAmpliado.get(listaDiasPeriodos.size()));
		}*/
		
		return periodosDiasGuardiaSJCS;
	}
	
	private ArrayList<ArrayList<String>> obtenerPeriodosGuardia() throws Exception {
		// Variables
		ArrayList arrayDiasGuardiaCGAE;
		PeriodoEfectivoItem periodoEfectivo;
		ArrayList<String> arrayDias;
		Iterator iter;
		Date fecha;
		String sFecha;
		SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<ArrayList<String>> listaFinal = new ArrayList<ArrayList<String>>();

		try {
			// generando la matriz de periodos
			CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(beanGuardiasTurno1, fechaInicio1,
					fechaFin1, vDiasFestivos1);
			arrayDiasGuardiaCGAE = calendarioAutomatico.obtenerMatrizDiasGuardia();

			// recorriendo lista de periodos para anhadir en cada periodo
			// las fechas de dias de guardia en formato corto
			for (int i = 0; i < arrayDiasGuardiaCGAE.size(); i++) {
				periodoEfectivo = (PeriodoEfectivoItem) arrayDiasGuardiaCGAE.get(i);

				// obteniendo los dis por cada periodo
				arrayDias = new ArrayList<String>();
				iter = periodoEfectivo.iterator();
				while (iter.hasNext()) {
					fecha = (Date) iter.next();
					sFecha = datef.format(fecha);
					arrayDias.add(sFecha);
				}

				// insertando lista de dias (en formato corto) para el generador posterior
				// Nota: no se insertan los arrays de periodos vacios que si guarda el CGAE
				if (!arrayDias.isEmpty())
					listaFinal.add(arrayDias);
			}

			return listaFinal;

		} catch (Exception e) {
			throw new Exception(e + ": Excepcion al calcular la matriz de periodos de dias de guardias.");
		}
	}
	
	private ArrayList<ArrayList<String>> obtenerPeriodosGuardia3(CalendarioAutomatico calendarioAutomatico) throws Exception {
		// Variables
		ArrayList arrayDiasGuardiaCGAE;
		PeriodoEfectivoItem periodoEfectivo;
		ArrayList<String> arrayDias;
		Iterator iter;
		Date fecha;
		String sFecha;
		SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<ArrayList<String>> listaFinal = new ArrayList<ArrayList<String>>();

		try {
			// generando la matriz de periodos
//			CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(beanGuardiasTurno1, fechaInicio1,
//					fechaFin1, vDiasFestivos1);
			arrayDiasGuardiaCGAE = calendarioAutomatico.obtenerMatrizDiasGuardia();

			// recorriendo lista de periodos para anhadir en cada periodo
			// las fechas de dias de guardia en formato corto
			for (int i = 0; i < arrayDiasGuardiaCGAE.size(); i++) {
				periodoEfectivo = (PeriodoEfectivoItem) arrayDiasGuardiaCGAE.get(i);

				// obteniendo los dis por cada periodo
				arrayDias = new ArrayList<String>();
				iter = periodoEfectivo.iterator();
				while (iter.hasNext()) {
					fecha = (Date) iter.next();
					sFecha = datef.format(fecha);
					arrayDias.add(sFecha);
				}

				// insertando lista de dias (en formato corto) para el generador posterior
				// Nota: no se insertan los arrays de periodos vacios que si guarda el CGAE
				if (!arrayDias.isEmpty())
					listaFinal.add(arrayDias);
			}

			return listaFinal;

		} catch (Exception e) {
			throw new Exception(e + ": Excepcion al calcular la matriz de periodos de dias de guardias.");
		}
	}

	private ArrayList<ArrayList<String>> obtenerPeriodosGuardiaFijo(GuardiasItem guardiaItem, String idInstitucion) throws Exception {
		// Variables
		ArrayList arrayDiasGuardiaCGAE;
		PeriodoEfectivoItem periodoEfectivo;
		ArrayList<String> arrayDias;
		Iterator iter;
		Date fecha;
		String sFecha;
		SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
		GuardiasTurnoItem guardiaConf = null;
		ArrayList<ArrayList<String>> listaFinal = new ArrayList<ArrayList<String>>();

		try {

			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			String fechaIni = dateFormat.format(guardiaItem.getFechadesde());  
			String fechaFin = dateFormat.format(guardiaItem.getFechahasta()); 
			
			
				List<GuardiasTurnoItem> vGuardias = scsGuardiasturnoExtendsMapper.getAllDatosGuardia(idInstitucion, guardiaItem.getIdTurno(),
						guardiaItem.getIdGuardia());
				if (!vGuardias.isEmpty())
					guardiaConf = vGuardias.get(0);
				
				// 3. CALENDARIO FESTIVOS:
				Calendar calFechaFin = StringToCalendar(fechaFin);
				calFechaFin.add(Calendar.MONTH, 1); // sumamos 1 mes

				List<String> diasFestivos = this.obtenerFestivosTurnoFijo(Integer.parseInt(idInstitucion), Integer.parseInt(guardiaItem.getIdTurno()), fechaIni, calFechaFin.getTime());
			

			// generando la matriz de periodos
			CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(guardiaConf, fechaIni,
					fechaFin, diasFestivos);
			
			arrayDiasGuardiaCGAE = calendarioAutomatico.obtenerMatrizDiasGuardia();
			
			

			// recorriendo lista de periodos para anhadir en cada periodo
			// las fechas de dias de guardia en formato corto
			for (int i = 0; i < arrayDiasGuardiaCGAE.size(); i++) {
				periodoEfectivo = (PeriodoEfectivoItem) arrayDiasGuardiaCGAE.get(i);

				// obteniendo los dis por cada periodo
				arrayDias = new ArrayList<String>();
				iter = periodoEfectivo.iterator();
				while (iter.hasNext()) {
					fecha = (Date) iter.next();
					sFecha = datef.format(fecha);
					arrayDias.add(sFecha);
				}

				// insertando lista de dias (en formato corto) para el generador posterior
				// Nota: no se insertan los arrays de periodos vacios que si guarda el CGAE
				if (!arrayDias.isEmpty())
					listaFinal.add(arrayDias);
			}

			return listaFinal;

		} catch (Exception e) {
			throw new Exception(e + ": Excepcion al calcular la matriz de periodos de dias de guardias.");
		}
	}
	public List<Integer> getDiasASeparar(Integer idInstitucion, Integer idTurno, Integer idGuardia) throws Exception {
		List<String> alDiasASeparar = getDiasASeparar2(idInstitucion, idTurno, idGuardia);
		List<Integer> alDiasASepararEnUnidadesDiarias = null;
		if (alDiasASeparar != null && alDiasASeparar.size() > 0) {
			alDiasASepararEnUnidadesDiarias = new ArrayList();
			for (int i = 0; i < alDiasASeparar.size(); i++) {
				String diasSemana = alDiasASeparar.get(i);
				for (int j = 0; j < diasSemana.length(); j++) {
					char diaSemana = diasSemana.charAt(j);

					alDiasASepararEnUnidadesDiarias
							.add(new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(diaSemana)));
				}
			}
		}

		return alDiasASepararEnUnidadesDiarias;
	}

	public List<String> getDiasASeparar2(Integer idInstitucion, Integer idTurno, Integer idGuardia) throws Exception {
		String agrupar = "0";
		return scsGuardiasturnoExtendsMapper.getDiasASeparar(idGuardia1.toString(), idTurno1.toString(),
				idInstitucion1.toString(), agrupar);
	}
	
	public List<Integer> getDiasASeparar3(String idInstitucion, String idTurno, String idGuardia) throws Exception {
		String agrupar = "0";
		List<String> alDiasASeparar = scsGuardiasturnoExtendsMapper.getDiasASeparar(idGuardia, idTurno, idInstitucion, agrupar);
		List<Integer> alDiasASepararEnUnidadesDiarias = null;
		if (alDiasASeparar != null && alDiasASeparar.size() > 0) {
			alDiasASepararEnUnidadesDiarias = new ArrayList<Integer>();
			for (int i = 0; i < alDiasASeparar.size(); i++) {
				String diasSemana = alDiasASeparar.get(i);
				for (int j = 0; j < diasSemana.length(); j++) {
					char diaSemana = diasSemana.charAt(j);

					alDiasASepararEnUnidadesDiarias
							.add(new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(diaSemana)));
				}
			}
		}

		return alDiasASepararEnUnidadesDiarias;
	}



	public int crearCalendario2(String idInstitucion, String idTurno, String idGuardia, String fechaDesde,
			String fechaHasta, String observaciones, Integer idTurnoPrincipal, Integer idGuardiaPrincipal,
			Integer idCalendarioPrincipal) throws Exception {

		String idPersonaUltimoAnterior, fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior;

		// Comprobamos que no haya ninguna cabecera de calendario para el turno ,
		// guardia,
		// fecha desde y fecha hasta. Si esta generada damos error. si no lo esta la
		// cogemos
		// y le concatenamos las observaciones

		List<GuardiasCalendarioItem> calGuardiavVector = scsGuardiasturnoExtendsMapper.getCalGuardiavVector2(
				idTurno, idGuardia, fechaDesde, fechaHasta,
				idInstitucion);

		if (calGuardiavVector != null && calGuardiavVector.size() > 0) {
			GuardiasCalendarioItem calendarioGuardiasBean = (GuardiasCalendarioItem) calGuardiavVector.get(0);
			List<CabeceraGuardiasCalendarioItem> cabGuardiavVector = scsGuardiasturnoExtendsMapper
					.cabGuardiavVector2(calendarioGuardiasBean, idInstitucion);

			if (cabGuardiavVector != null && cabGuardiavVector.size() > 0) {
				throw new Exception(
						"El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta + "] ya está generado");
			}
			// modificamos las observaciones
			if (calendarioGuardiasBean.getObservaciones() != null
					&& !calendarioGuardiasBean.getObservaciones().equals("") && observaciones != null
					&& !calendarioGuardiasBean.getObservaciones().equals(observaciones)) {
				calendarioGuardiasBean
						.setObservaciones(calendarioGuardiasBean.getObservaciones() + ". " + observaciones);
				// String[] claves = { ScsCalendarioGuardiasBean.C_IDINSTITUCION,
				// ScsCalendarioGuardiasBean.C_IDGUARDIA, ScsCalendarioGuardiasBean.C_IDTURNO,
				// ScsCalendarioGuardiasBean.C_IDCALENDARIOGUARDIAS };
				// String[] campos = { ScsCalendarioGuardiasBean.C_OBSERVACIONES };
				// admCalendarioGuardia.updateDirect(admCalendarioGuardia.beanToHashTable(calendarioGuardiasBean),
				// claves, campos);
			}
//		});
			return Integer.parseInt(calendarioGuardiasBean.getIdcalendarioguardias());
		}

		// Calculando idPersonaUltimoAnterior de guardias turno
		List<GuardiasTurnoItem> guardiasTurnoList = scsGuardiasturnoExtendsMapper
				.getIdPersonaUltimoAnterior(idTurno, idGuardia, idInstitucion);
		GuardiasTurnoItem guardiaBean = guardiasTurnoList.get(0);
		if (guardiaBean.getIdPersona_Ultimo() == null) {
			idPersonaUltimoAnterior = "";
			fechaSuscUltimoAnterior = "";
		} else {
			idPersonaUltimoAnterior = guardiaBean.getIdPersona_Ultimo().toString();
			fechaSuscUltimoAnterior = guardiaBean.getFechaSuscripcion_Ultimo();
		}
		if (guardiaBean.getIdGrupoGuardiaColegiado_Ultimo() == null) {
			idGrupoGuardiaColegiadoAnterior = "";
		} else {
			idGrupoGuardiaColegiadoAnterior = guardiaBean.getIdGrupoGuardiaColegiado_Ultimo().toString();
		}

		// calculando nuevo idcalendarioguardias

		String idcalendarioguardias2 = scsGuardiasturnoExtendsMapper.getIdCalendarioGuardias(idTurno, idGuardia, idInstitucion);
		if (idcalendarioguardias2.equals("")) {
			idcalendarioguardias2 = "1";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());

		try {
			scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(idTurnoPrincipal, idGuardiaPrincipal,
					idCalendarioPrincipal, observaciones, idTurno, idGuardia, fechaHasta,
					fechaDesde, idcalendarioguardias2, idInstitucion, idPersonaUltimoAnterior, today,
					fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior, usuModificacion1.toString());
			return Integer.parseInt(idcalendarioguardias2);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Genera las cabeceras de guardias del calendario, a partir de la lista de
	 * dias/periodos Cuando la guardia es por grupos Nota: previamente se ha
	 * calculado mediante el metodo "calcularMatrizPeriodosDiasGuardia()"
	 */
	public void calcularMatrizLetradosGuardiaPorGrupos(List<Integer> lDiasASeparar, boolean rotacion) throws Exception {

		LOGGER.info("INICIO generarCalendarioAsync.calcularMatrizLetradosGuardiaPorGrupos:");
		
		// Variables
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con
		// letrado
		int numeroLetradosGuardia; // Numero de letrados necesarios para cada periodo

		HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales = null;
		ArrayList<SaltoCompGuardiaGrupoItem> alSaltos = null; // Lista de saltos
		ArrayList<SaltoCompGuardiaGrupoItem> alCompensaciones = null; // Lista de compensaciones

		List<LetradoInscripcionItem> alLetradosOrdenados = new ArrayList<LetradoInscripcionItem>(); // Cola de letrados
		// en la guardia

		int posicion; // Orden de cada componente en la cola y en la lista de guardias generada
		ArrayList<LetradoInscripcionItem> grupoLetrados = null; // Lista de letrados en el grupo
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		Puntero punteroListaLetrados;
		LetradoInscripcionItem unLetrado = null;

		ScsGrupoguardiacolegiado beanGrupoLetrado; // Bean para guardar cambios en los grupos
		Hashtable hashGrupoLetrado; // Hash para guardar cambios en los grupos

		ArrayList<String> lineaLog; // Linea para escribir en LOG

		Long idPersona = null;
		String fechaSubs = "";

		// try {

		// Crea una copia de la cola de guardias de grupos de un calendario
		try {
			crearRegistroGrupoGuardiaColegiadoCalendario();
		} catch (Exception e) {
			errorGeneracionCalendario = "Error al crear una copia de la cola de guardias de grupos de un calendario";
		}

		// obteniendo bajas temporales por letrado
		String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0)).get(0);
		ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1
				.get(arrayPeriodosDiasGuardiaSJCS1.size() - 1);
		String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size() - 1);
		try {
			hmBajasTemporales = getLetradosDiasBajaTemporal(idInstitucion1, idTurno1, idGuardia1, primerDia, ultimoDia);
		} catch (Exception e) {
			controlError++;
			errorGeneracionCalendario = "Error obteniendo las bajas temporales";
		}

		

		// obteniendo numero de letrados necesarios para cada periodo
		numeroLetradosGuardia = beanGuardiasTurno1.getNumeroLetradosGuardia().intValue();
		// log.addLog(new String[] {"Minimo Letrados por Grupo",
		// Integer.toString(numeroLetradosGuardia)});
		Map<String, Object> mapLog = new HashMap();
		mapLog.put("*Minimo Letrados por Grupo", Integer.toString(numeroLetradosGuardia));
		listaDatosExcelGeneracionCalendarios.add(mapLog);
		LOGGER.info("*Minimo Letrados por Grupo" + Integer.toString(numeroLetradosGuardia));
		// Si hay guardias vinculadas, hay que mirar dos periodos a la vez,
		// por lo que se comienza con uno ya en memoria
		int inicial;
		primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0);
		segundoPeriodo = null;
		if (calendariosVinculados1 == null)
			inicial = 0;
		else {
			// log.addLog(new String[] {"Guardias vinculadas",
			// this.calendariosVinculados.toString()});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Guardias vinculadas", calendariosVinculados1.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Guardias vinculadas" + calendariosVinculados1.toString());
			inicial = 1;
		}

		// Para cada dia o conjunto de dias:
		for (int i = inicial; i < arrayPeriodosDiasGuardiaSJCS1.size(); i++) {
			// obteniendo conjunto de dias
			// Nota: cada periodo es un arraylist de fechas (String en formato de fecha
			// corto DD/MM/YYYY)
			if (calendariosVinculados1 == null) {
				diasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(i);
			} // Si hay guardias vinculadas, hay que mirar dos periodos a la vez
			else {
				segundoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(i);
				diasGuardia = new ArrayList<String>();
				diasGuardia.addAll(primerPeriodo);
				diasGuardia.addAll(segundoPeriodo);
			}
//				log.addLog(new String[] {" ", " "});
//				log.addLog(new String[] {"Dias", diasGuardia.toString()});
			Map<String, Object> mapLog3 = new HashMap();
			mapLog3.put("*Dias", diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog3);
			LOGGER.info("*Dias " + diasGuardia.toString());

			// obteniendo cola de letrados
			punteroListaLetrados = new Puntero();
			try {
				alLetradosOrdenados = getColaGuardia(idInstitucion1, idTurno1, idGuardia1, (String) diasGuardia.get(0),
						(String) diasGuardia.get(diasGuardia.size() - 1));
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo la cola de guardia";
			}

			// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});

			// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
			Map<String, Object> mapLog2 = new HashMap();
			if (alLetradosOrdenados != null && !alLetradosOrdenados.isEmpty()) {
				int auxIndex = 0;
				//alLetradosOrdenados.forEach(letrado -> {
					for(LetradoInscripcionItem letrado : alLetradosOrdenados) {
					String nombre = "";
					String ap1 = "";
					String ap2 = "";
					String numCol = "";
					String numGrupo = "";
					if (letrado.getInscripcionGuardia() != null) {
						if (letrado.getInscripcionGuardia().getApellido2() != null)
							ap2 = letrado.getInscripcionGuardia().getApellido2().toString();
						if (letrado.getInscripcionGuardia().getApellido1() != null)
							ap1 = letrado.getInscripcionGuardia().getApellido1().toString();
						if (letrado.getInscripcionGuardia().getNombre() != null)
							nombre = letrado.getInscripcionGuardia().getNombre().toString();
						if(letrado.getInscripcionGuardia().getnColegiado() != null)
							numCol = letrado.getInscripcionGuardia().getnColegiado().toString();
						if(letrado.getInscripcionGuardia().getNumeroGrupo() != null)
							numGrupo = letrado.getInscripcionGuardia().getNumeroGrupo();
						Map<String, Object> mapLog3a = new HashMap();
						mapLog3a.put("*Cola-" + auxIndex,"Grupo: "+numGrupo+ " ("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3a);
						LOGGER.info("*Colae "+numGrupo +" "+ ap1 + " " + ap2 + ", " + nombre);
					} else if (letrado.getInscripcionTurno() != null) {
						if (letrado.getInscripcionTurno().getApellidos2() != null)
							ap2 = letrado.getInscripcionTurno().getApellidos2().toString();
						if (letrado.getInscripcionTurno().getApellidos1() != null)
							ap1 = letrado.getInscripcionTurno().getApellidos1().toString();
						if (letrado.getInscripcionTurno().getNombre() != null)
							nombre = letrado.getInscripcionTurno().getNombre().toString();
						if (letrado.getInscripcionTurno().getNumerocolegiado() != null)
							numCol = letrado.getInscripcionTurno().getNumerocolegiado().toString();
						mapLog2.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Colaia " + ap1 + " " + ap2 + ", " + nombre);

					}
					auxIndex++;
				};
				
			} else {
				mapLog2.put("*Cola vacía", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Cola vacía");
				controlError++;
			}
			
			if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//					throw new Exception("No existe cola de letrados de guardia");
				LOGGER.error("No existe cola de letrados de guardia");
			
			// obteniendo saltos
			try {
				alSaltos = getSaltosCompensacionesPendientesGuardia(idInstitucion1, idTurno1, idGuardia1, null, "S");
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error obteniendo los saltos pendientes";
			}

			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmGruposConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
			ArrayList<LetradoInscripcionItem> grupoConSaltos;
			List<String> hmGruposConSaltosToLog = new ArrayList<>();
			if(alSaltos != null && !alSaltos.isEmpty()) {
				for (SaltoCompGuardiaGrupoItem bean : alSaltos) {
					if ((grupoConSaltos = (ArrayList<LetradoInscripcionItem>) hmGruposConSaltos
							.get(bean.getIdGrupoGuardia())) == null) {
						grupoConSaltos = new ArrayList<LetradoInscripcionItem>();
						grupoConSaltos.add(bean.getLetrados().get(0)); // se inserta uno de los letrados, que lleva ya el
						// idSaltoCompensacionGrupo
						hmGruposConSaltos.put(bean.getIdGrupoGuardia(), grupoConSaltos);
						hmGruposConSaltosToLog.add(bean.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
					} else {
						grupoConSaltos.add(bean.getLetrados().get(0));
						hmGruposConSaltosToLog.add(bean.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
						// se inserta uno de los letrados, que lleva ya el
						// idSaltoCompensacionGrupo
					}
						//mapLog3.put("*Probando Grupo", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				}
			}
			if(!hmGruposConSaltosToLog.isEmpty() && hmGruposConSaltosToLog != null ) {
				Map<String, Object> mapLog6 = new HashMap();
				hmGruposConSaltosToLog = (ArrayList<String>) hmGruposConSaltosToLog.stream().distinct().collect(Collectors.toList());
				mapLog6.put("*Grupos con Saltos : ",  String.join(", ", hmGruposConSaltosToLog));
				listaDatosExcelGeneracionCalendarios.add(mapLog6);
				LOGGER.info("*Grupos con Saltos :" +  String.join(", ", hmGruposConSaltosToLog));

			}
				

			// obteniendo las compensaciones. Se obtienen dentro de este
			// bucle, ya que si hay incompatibilidades se añade una compensacion
			try {
				alCompensaciones = getSaltosCompensacionesPendientesGuardia(idInstitucion1, idTurno1, idGuardia1,
						(String) diasGuardia.get(diasGuardia.size() - 1), "C");
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo las compensaciones pendientes";
			}
//				log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
			if(alCompensaciones != null && !alCompensaciones.isEmpty()) {
				Map<String, Object> mapLog5 = new HashMap();
				//String alCompensacionesSt = alCompensaciones.stream()
				//		.map(SaltoCompGuardiaGrupoItem::getIdSaltoCompensacionGrupo).collect(Collectors.joining(","));
				List <ArrayList<LetradoInscripcionItem>> letradosConCompensaciones = alCompensaciones.stream()
						.map(SaltoCompGuardiaGrupoItem::getLetrados).collect(Collectors.toList());
				List<String> gruposConCompensacionString = new ArrayList<>();
				
				for( ArrayList<LetradoInscripcionItem> lista : letradosConCompensaciones) {
					gruposConCompensacionString.add(lista.get(0).getInscripcionGuardia().getNumeroGrupo());
				}
				mapLog5.put("*Grupos con Compensaciones : ",String.join(", ", gruposConCompensacionString));
				listaDatosExcelGeneracionCalendarios.add(mapLog5);
				LOGGER.info("*Grupos con Compensaciones : " + String.join(", ", gruposConCompensacionString));
//					log.addLog(new String[] {"Saltos", hmGruposConSaltos.toString()});
			}
				
	
			// buscando grupo que no tenga restricciones (incompatibilidades, bajas
			// temporales, saltos)
			try {//TODO(L) INCOMPATIBILIDADES
				grupoLetrados = getSiguienteGrupo(alCompensaciones, alLetradosOrdenados, punteroListaLetrados,
						diasGuardia, hmGruposConSaltos, hmBajasTemporales);
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error uscando grupo que no tenga restricciones (incompatibilidades, bajas temporales, saltos)";
			}

			if (grupoLetrados == null) {
//					log.addLog(new String[] {"FIN generacion", "Todos los grupos tienen restricciones que no permiten generar el calendario"});
				Map<String, Object> mapLog7 = new HashMap();
				mapLog7.put("*FIN generacion",
						" Todos los grupos tienen restricciones que no permiten generar el calendario");
				listaDatosExcelGeneracionCalendarios.add(mapLog7);
				LOGGER.info("*FIN generacion");
//					throw new Exception("Todos los grupos tienen restricciones que no permiten generar el calendario");
				LOGGER.error("Todos los grupos tienen restricciones que no permiten generar el calendario");
			} else {
//					log.addLog(new String[] {"Grupo seleccionado", grupoLetrados.toString()});
				Map<String, Object> mapLog8 = new HashMap();
				mapLog8.put("*Grupo seleccionado ", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				listaDatosExcelGeneracionCalendarios.add(mapLog8);
				LOGGER.info("*Grupo seleccionado " + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				// comprobando minimo de letrados en la guardia
				if (grupoLetrados.size() < numeroLetradosGuardia) {
//					log.addLog(new String[] {"¡¡ AVISO !!", "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: " + grupoLetrados.size() + " < " + numeroLetradosGuardia});
					Map<String, Object> mapLog9 = new HashMap();
					mapLog9.put("*¡¡ AVISO !!",
							"El numero de letrados en el grupo es menor que el minimo configurado para la guardia: "
									+ grupoLetrados.size() + " < " + numeroLetradosGuardia);
					listaDatosExcelGeneracionCalendarios.add(mapLog9);
					LOGGER.info("*¡¡ AVISO !!"
							+ "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: "
							+ grupoLetrados.size() + " < " + numeroLetradosGuardia);
				}
				// guardando ultimo de cola
				if (rotacion)
					unLetrado = grupoLetrados.get(0);
				else
					unLetrado = grupoLetrados.get(grupoLetrados.size() - 1);
				if (unLetrado.getSaltoocompensacion() == null) {
					String idGGC = null;
					if (unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado() != null)
						idGGC = unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado();
					try {
						String fSU = ( unLetrado.getInscripcionGuardia().getFechaSuscripcion() == null ) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(unLetrado.getInscripcionGuardia().getFechaSuscripcion());
						cambiarUltimoCola(unLetrado.getInscripcionGuardia().getIdInstitucion().toString(),
								unLetrado.getInscripcionGuardia().getIdturno().toString(),
								unLetrado.getInscripcionGuardia().getIdGuardia().toString(),
								unLetrado.getInscripcionGuardia().getIdPersona().toString(), fSU, idGGC);
					} catch (Exception e) {
						controlError++;
						errorGeneracionCalendario = "Error cambiando el último de la cola";
					}
				}

				// metiendo grupo en el periodo de guardia
				alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
				posicion = 1;
				for (LetradoInscripcionItem letrado : grupoLetrados) {
					// insertando letrados
					letrado.setPeriodoGuardias(diasGuardia);
					alLetradosInsertar.add(letrado);

					// colocando componentes del grupo (mejorando ordenes)
					letrado.setPosicion(posicion);

					if (rotacion) {
						if (posicion == 1) {
							letrado.setOrdenGrupo(grupoLetrados.size());
						} else {
							letrado.setOrdenGrupo(posicion - 1);
						}
					} else {
						letrado.setOrdenGrupo(posicion);
					}

					hashGrupoLetrado = new Hashtable();
					hashGrupoLetrado.put("IDGRUPOGUARDIACOLEGIADO", letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado());
					beanGrupoLetrado = new ScsGrupoguardiacolegiado();
					beanGrupoLetrado.setIdgrupoguardia(Long.parseLong(letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado()));
					beanGrupoLetrado = (ScsGrupoguardiacolegiado) scsGrupoguardiacolegiadoMapper
							.selectByPrimaryKey(Long.parseLong(letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado()));
					beanGrupoLetrado.setOrden(letrado.getOrdenGrupo());
					beanGrupoLetrado.setFechamodificacion(new Date());
					beanGrupoLetrado.setUsumodificacion(usuModificacion1);
					scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective2(beanGrupoLetrado);
					posicion++;
				}

				// Asignamos valores superiores al tamaño de la lista a los letrados no activos
				try {
					String ident = grupoLetrados.get(0).getInscripcionGuardia().getIdGrupoGuardia();
					if(ident == null || ident.isEmpty()) ident = grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo();
					
					reordenarRestoGrupoLetrados(Integer.parseInt(ident), grupoLetrados.size());
				} catch (Exception e) {
					LOGGER.info("ERROR EN reordenarRestoGrupoLetrados");
					errorGeneracionCalendario = "Error asignando valores superiores al tamaño de la lista a los letrados no activos";
				}

				arrayPeriodosLetradosSJCS1.add(alLetradosInsertar);

				// guardando las guardias en BD
				if (calendariosVinculados1 == null) {

					almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, diasGuardia, lDiasASeparar,
							"gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
					);
				} else {
					// guardando la principal
					almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, primerPeriodo, lDiasASeparar,
							"gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
					);

					// guardando para cada una de las vinculadas
					for (GuardiasCalendarioItem calendario : calendariosVinculados1) {
						// modificando la guardia y calendario en el que se insertaran las guardias
						for (LetradoInscripcionItem lg : alLetradosInsertar) {
							lg.setIdinstitucion(new Integer(calendario.getIdinstitucion()).shortValue());
							lg.setIdTurno(new Integer(calendario.getIdturno()));
							lg.setIdGuardia(new Integer(calendario.getIdguardia()));
						}

						// guardando en BD
						almacenarAsignacionGuardia(new Integer(calendario.getIdcalendarioguardias()),
								alLetradosInsertar, segundoPeriodo, lDiasASeparar,
								"gratuita.literal.comentario.sustitucion"
//								UtilidadesString.getMensajeIdioma(usuModificacion1,
//										"gratuita.literal.comentario.sustitucion")
						);
					}
				}

			} // FIN Para cada dia o conjunto de dias
		}
		// avanzando el puntero de dia en el caso de guardias vinculadas
		if (calendariosVinculados1 != null)
			primerPeriodo = segundoPeriodo;

//		} catch (Exception e) {
//			arrayPeriodosLetradosSJCS1 = null;
//			throw new Exception(e + "");
//		}
		
		LOGGER.info("FIN generarCalendarioAsync.calcularMatrizLetradosGuardiaPorGrupos:");
		
	} // calcularMatrizLetradosGuardiaPorGrupos()
	
	public void calcularMatrizLetradosGuardiaPorGrupos3(GuardiasCalendarioItem guardiasCalendarioItem, List<GuardiasCalendarioItem> calendariosVinculados, GuardiasTurnoItem guardiasTurnoItem ,ArrayList<ArrayList<String>> arrayPeriodosDiasGuardia, List<Integer> lDiasASeparar, boolean rotacion, InscripcionGuardiaItem inscripcion) throws Exception {

		LOGGER.info("INICIO generarCalendarioAsync.calcularMatrizLetradosGuardiaPorGrupos:");
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		Integer usuModificacion = Integer.valueOf(guardiasCalendarioItem.getUsumodificacion());
		
		// Variables
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con
		// letrado
		int numeroLetradosGuardia; // Numero de letrados necesarios para cada periodo

		HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales = null;
		ArrayList<SaltoCompGuardiaGrupoItem> alSaltos = null; // Lista de saltos
		ArrayList<SaltoCompGuardiaGrupoItem> alCompensaciones = null; // Lista de compensaciones

		List<LetradoInscripcionItem> alLetradosOrdenados = new ArrayList<LetradoInscripcionItem>(); // Cola de letrados
		// en la guardia

		int posicion; // Orden de cada componente en la cola y en la lista de guardias generada
		ArrayList<LetradoInscripcionItem> grupoLetrados = null; // Lista de letrados en el grupo
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		Puntero punteroListaLetrados;
		LetradoInscripcionItem unLetrado = null;

		ScsGrupoguardiacolegiado beanGrupoLetrado; // Bean para guardar cambios en los grupos
		Hashtable hashGrupoLetrado; // Hash para guardar cambios en los grupos

		ArrayList<String> lineaLog; // Linea para escribir en LOG

		Long idPersona = null;
		String fechaSubs = "";

		// try {

		// Crea una copia de la cola de guardias de grupos de un calendario
		try {
			crearRegistroGrupoGuardiaColegiadoCalendario3(idInstitucion, idGuardia, idTurno, idCalendarioGuardias);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error al crear una copia de la cola de guardias de grupos de un calendario";
		}

		// obteniendo bajas temporales por letrado
		String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardia.get(0)).get(0);
		ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia
				.get(arrayPeriodosDiasGuardia.size() - 1);
		String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size() - 1);
		try {
			hmBajasTemporales = getLetradosDiasBajaTemporal(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia), primerDia, ultimoDia);
		} catch (Exception e) {
			controlError++;
			errorGeneracionCalendario = "Error obteniendo las bajas temporales";
		}

		

		// obteniendo numero de letrados necesarios para cada periodo
		numeroLetradosGuardia =  guardiasTurnoItem.getNumeroLetradosGuardia().intValue();
		// log.addLog(new String[] {"Minimo Letrados por Grupo",
		// Integer.toString(numeroLetradosGuardia)});
		Map<String, Object> mapLog = new HashMap();
		mapLog.put("*Minimo Letrados por Grupo", Integer.toString(numeroLetradosGuardia));
		listaDatosExcelGeneracionCalendarios.add(mapLog);
		LOGGER.info("*Minimo Letrados por Grupo" + Integer.toString(numeroLetradosGuardia));
		// Si hay guardias vinculadas, hay que mirar dos periodos a la vez,
		// por lo que se comienza con uno ya en memoria
		int inicial;
		primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(0);
		segundoPeriodo = null;
		if (calendariosVinculados == null || calendariosVinculados.isEmpty())
			inicial = 0;
		else {
			// log.addLog(new String[] {"Guardias vinculadas",
			// this.calendariosVinculados.toString()});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Guardias vinculadas", calendariosVinculados.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Guardias vinculadas" + calendariosVinculados.toString());
			inicial = 1;
		}

		// Para cada dia o conjunto de dias:
		for (int i = inicial; i < arrayPeriodosDiasGuardia.size(); i++) {
			// obteniendo conjunto de dias
			// Nota: cada periodo es un arraylist de fechas (String en formato de fecha
			// corto DD/MM/YYYY)
			if (calendariosVinculados == null || calendariosVinculados.isEmpty()) {
				diasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
			} // Si hay guardias vinculadas, hay que mirar dos periodos a la vez
			else {
				segundoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
				diasGuardia = new ArrayList<String>();
				diasGuardia.addAll(primerPeriodo);
				diasGuardia.addAll(segundoPeriodo);
			}
//				log.addLog(new String[] {" ", " "});
//				log.addLog(new String[] {"Dias", diasGuardia.toString()});
			Map<String, Object> mapLog3 = new HashMap();
			mapLog3.put("*Dias", diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog3);
			LOGGER.info("*Dias " + diasGuardia.toString());

			// obteniendo cola de letrados
			punteroListaLetrados = new Puntero();
			try {
				if(inscripcion != null) {
					alLetradosOrdenados = getInscritoInfo(inscripcion);
				}else {
					alLetradosOrdenados = getColaGuardia(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia), (String) diasGuardia.get(0),
							(String) diasGuardia.get(diasGuardia.size() - 1));
				}
				
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo la cola de guardia";
			}

			// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});

			// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
			Map<String, Object> mapLog2 = new HashMap();
			if (alLetradosOrdenados != null && !alLetradosOrdenados.isEmpty()) {
				int auxIndex = 0;
				//alLetradosOrdenados.forEach(letrado -> {
					for(LetradoInscripcionItem letrado : alLetradosOrdenados) {
					String nombre = "";
					String ap1 = "";
					String ap2 = "";
					String numCol = "";
					String numGrupo = "";
					if (letrado.getInscripcionGuardia() != null) {
						if (letrado.getInscripcionGuardia().getApellido2() != null)
							ap2 = letrado.getInscripcionGuardia().getApellido2().toString();
						if (letrado.getInscripcionGuardia().getApellido1() != null)
							ap1 = letrado.getInscripcionGuardia().getApellido1().toString();
						if (letrado.getInscripcionGuardia().getNombre() != null)
							nombre = letrado.getInscripcionGuardia().getNombre().toString();
						if(letrado.getInscripcionGuardia().getnColegiado() != null)
							numCol = letrado.getInscripcionGuardia().getnColegiado().toString();
						if(letrado.getInscripcionGuardia().getNumeroGrupo() != null)
							numGrupo = letrado.getInscripcionGuardia().getNumeroGrupo();
						Map<String, Object> mapLog3a = new HashMap();
						mapLog3a.put("*Cola-" + auxIndex,"Grupo: "+numGrupo+ " ("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3a);
						LOGGER.info("*Colae "+numGrupo +" "+ ap1 + " " + ap2 + ", " + nombre);
					} else if (letrado.getInscripcionTurno() != null) {
						if (letrado.getInscripcionTurno().getApellidos2() != null)
							ap2 = letrado.getInscripcionTurno().getApellidos2().toString();
						if (letrado.getInscripcionTurno().getApellidos1() != null)
							ap1 = letrado.getInscripcionTurno().getApellidos1().toString();
						if (letrado.getInscripcionTurno().getNombre() != null)
							nombre = letrado.getInscripcionTurno().getNombre().toString();
						if (letrado.getInscripcionTurno().getNumerocolegiado() != null)
							numCol = letrado.getInscripcionTurno().getNumerocolegiado().toString();
						mapLog2.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Colaia " + ap1 + " " + ap2 + ", " + nombre);

					}
					auxIndex++;
				};
				
			} else {
				mapLog2.put("*Cola vacía", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Cola vacía");
				controlError++;
			}
			
			if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//					throw new Exception("No existe cola de letrados de guardia");
				LOGGER.error("No existe cola de letrados de guardia");
			
			// obteniendo saltos
			try {
				alSaltos = getSaltosCompensacionesPendientesGuardia(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia), null, "S");
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error obteniendo los saltos pendientes";
			}

			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmGruposConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
			ArrayList<LetradoInscripcionItem> grupoConSaltos;
			List<String> hmGruposConSaltosToLog = new ArrayList<>();
			if(alSaltos != null && !alSaltos.isEmpty()) {
				for (SaltoCompGuardiaGrupoItem bean : alSaltos) {
					if ((grupoConSaltos = (ArrayList<LetradoInscripcionItem>) hmGruposConSaltos
							.get(bean.getIdGrupoGuardia())) == null) {
						grupoConSaltos = new ArrayList<LetradoInscripcionItem>();
						grupoConSaltos.add(bean.getLetrados().get(0)); // se inserta uno de los letrados, que lleva ya el
						// idSaltoCompensacionGrupo
						hmGruposConSaltos.put(bean.getIdGrupoGuardia(), grupoConSaltos);
						hmGruposConSaltosToLog.add(bean.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
					} else {
						grupoConSaltos.add(bean.getLetrados().get(0));
						hmGruposConSaltosToLog.add(bean.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
						// se inserta uno de los letrados, que lleva ya el
						// idSaltoCompensacionGrupo
					}
						//mapLog3.put("*Probando Grupo", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				}
			}
			if(!hmGruposConSaltosToLog.isEmpty() && hmGruposConSaltosToLog != null ) {
				Map<String, Object> mapLog6 = new HashMap();
				hmGruposConSaltosToLog = (ArrayList<String>) hmGruposConSaltosToLog.stream().distinct().collect(Collectors.toList());
				mapLog6.put("*Grupos con Saltos : ",  String.join(", ", hmGruposConSaltosToLog));
				listaDatosExcelGeneracionCalendarios.add(mapLog6);
				LOGGER.info("*Grupos con Saltos :" +  String.join(", ", hmGruposConSaltosToLog));

			}
				

			// obteniendo las compensaciones. Se obtienen dentro de este
			// bucle, ya que si hay incompatibilidades se añade una compensacion
			try {
				alCompensaciones = getSaltosCompensacionesPendientesGuardia(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia),
						(String) diasGuardia.get(diasGuardia.size() - 1), "C");
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo las compensaciones pendientes";
			}
//				log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
			if(alCompensaciones != null && !alCompensaciones.isEmpty()) {
				Map<String, Object> mapLog5 = new HashMap();
				//String alCompensacionesSt = alCompensaciones.stream()
				//		.map(SaltoCompGuardiaGrupoItem::getIdSaltoCompensacionGrupo).collect(Collectors.joining(","));
				List <ArrayList<LetradoInscripcionItem>> letradosConCompensaciones = alCompensaciones.stream()
						.map(SaltoCompGuardiaGrupoItem::getLetrados).collect(Collectors.toList());
				List<String> gruposConCompensacionString = new ArrayList<>();
				
				for( ArrayList<LetradoInscripcionItem> lista : letradosConCompensaciones) {
					gruposConCompensacionString.add(lista.get(0).getInscripcionGuardia().getNumeroGrupo());
				}
				mapLog5.put("*Grupos con Compensaciones : ",String.join(", ", gruposConCompensacionString));
				listaDatosExcelGeneracionCalendarios.add(mapLog5);
				LOGGER.info("*Grupos con Compensaciones : " + String.join(", ", gruposConCompensacionString));
//					log.addLog(new String[] {"Saltos", hmGruposConSaltos.toString()});
			}
				
	
			// buscando grupo que no tenga restricciones (incompatibilidades, bajas
			// temporales, saltos)
			try {//TODO(L) INCOMPATIBILIDADES
				grupoLetrados = getSiguienteGrupo3(guardiasCalendarioItem, alCompensaciones, alLetradosOrdenados, punteroListaLetrados,
						diasGuardia, hmGruposConSaltos, hmBajasTemporales);
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error uscando grupo que no tenga restricciones (incompatibilidades, bajas temporales, saltos)";
			}

			if (grupoLetrados == null) {
//					log.addLog(new String[] {"FIN generacion", "Todos los grupos tienen restricciones que no permiten generar el calendario"});
				Map<String, Object> mapLog7 = new HashMap();
				mapLog7.put("*FIN generacion",
						" Todos los grupos tienen restricciones que no permiten generar el calendario");
				listaDatosExcelGeneracionCalendarios.add(mapLog7);
				LOGGER.info("*FIN generacion");
//					throw new Exception("Todos los grupos tienen restricciones que no permiten generar el calendario");
				LOGGER.error("Todos los grupos tienen restricciones que no permiten generar el calendario");
			} else {
//					log.addLog(new String[] {"Grupo seleccionado", grupoLetrados.toString()});
				Map<String, Object> mapLog8 = new HashMap();
				mapLog8.put("*Grupo seleccionado ", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				listaDatosExcelGeneracionCalendarios.add(mapLog8);
				LOGGER.info("*Grupo seleccionado " + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				// comprobando minimo de letrados en la guardia
				if (grupoLetrados.size() < numeroLetradosGuardia) {
//					log.addLog(new String[] {"¡¡ AVISO !!", "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: " + grupoLetrados.size() + " < " + numeroLetradosGuardia});
					Map<String, Object> mapLog9 = new HashMap();
					mapLog9.put("*¡¡ AVISO !!",
							"El numero de letrados en el grupo es menor que el minimo configurado para la guardia: "
									+ grupoLetrados.size() + " < " + numeroLetradosGuardia);
					listaDatosExcelGeneracionCalendarios.add(mapLog9);
					LOGGER.info("*¡¡ AVISO !!"
							+ "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: "
							+ grupoLetrados.size() + " < " + numeroLetradosGuardia);
				}
				// guardando ultimo de cola
				if (rotacion)
					unLetrado = grupoLetrados.get(0);
				else
					unLetrado = grupoLetrados.get(grupoLetrados.size() - 1);
				if (unLetrado.getSaltoocompensacion() == null) {
					String idGGC = null;
					if (unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado() != null)
						idGGC = unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado();
					try {
						String fSU = ( unLetrado.getInscripcionGuardia().getFechaSuscripcion() == null ) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(unLetrado.getInscripcionGuardia().getFechaSuscripcion());
								cambiarUltimoCola(unLetrado.getInscripcionGuardia().getIdInstitucion().toString(),
								unLetrado.getInscripcionGuardia().getIdturno().toString(),
								unLetrado.getInscripcionGuardia().getIdGuardia().toString(),
								unLetrado.getInscripcionGuardia().getIdPersona().toString(), fSU, idGGC);
					} catch (Exception e) {
						controlError++;
						errorGeneracionCalendario = "Error cambiando el último de la cola";
					}
				}

				// metiendo grupo en el periodo de guardia
				alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
				posicion = 1;
				for (LetradoInscripcionItem letrado : grupoLetrados) {
					// insertando letrados
					letrado.setPeriodoGuardias(diasGuardia);
					alLetradosInsertar.add(letrado);

					// colocando componentes del grupo (mejorando ordenes)
					letrado.setPosicion(posicion);

					if (rotacion) {
						if (posicion == 1) {
							letrado.setOrdenGrupo(grupoLetrados.size());
						} else {
							letrado.setOrdenGrupo(posicion - 1);
						}
					} else {
						letrado.setOrdenGrupo(posicion);
					}

					hashGrupoLetrado = new Hashtable();
					hashGrupoLetrado.put("IDGRUPOGUARDIACOLEGIADO", letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado()); //(L)IdGrupoGuardiaColegiado =null
					beanGrupoLetrado = new ScsGrupoguardiacolegiado();
					beanGrupoLetrado.setIdgrupoguardia(Long.parseLong(letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado()));
					beanGrupoLetrado = (ScsGrupoguardiacolegiado) scsGrupoguardiacolegiadoMapper
							.selectByPrimaryKey(Long.parseLong(letrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado()));
					beanGrupoLetrado.setOrden(letrado.getOrdenGrupo());
					beanGrupoLetrado.setFechamodificacion(new Date());
					beanGrupoLetrado.setUsumodificacion(usuModificacion);
					scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective2(beanGrupoLetrado);
					posicion++;
				}

				// Asignamos valores superiores al tamaño de la lista a los letrados no activos
				try {
					String ident = grupoLetrados.get(0).getInscripcionGuardia().getIdGrupoGuardia();
					if(ident == null || ident.isEmpty()) ident = grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo();
					
					reordenarRestoGrupoLetrados(Integer.parseInt(ident), grupoLetrados.size());
				} catch (Exception e) {
					LOGGER.info("ERROR EN reordenarRestoGrupoLetrados");
					errorGeneracionCalendario = "Error asignando valores superiores al tamaño de la lista a los letrados no activos";
				}

				// guardando las guardias en BD
				if (calendariosVinculados == null || calendariosVinculados.isEmpty()) {

					almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem, alLetradosInsertar, diasGuardia, lDiasASeparar,
							"gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
					);
				} else {
					// guardando la principal
					almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem, alLetradosInsertar, primerPeriodo, lDiasASeparar,
							"gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
					);

					// guardando para cada una de las vinculadas
					for (GuardiasCalendarioItem calendario : calendariosVinculados) {
						// modificando la guardia y calendario en el que se insertaran las guardias
						for (LetradoInscripcionItem lg : alLetradosInsertar) {
							lg.setIdinstitucion(new Integer(calendario.getIdinstitucion()).shortValue());
							lg.setIdTurno(new Integer(calendario.getIdturno()));
							lg.setIdGuardia(new Integer(calendario.getIdguardia()));
						}

						// guardando en BD
						almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem,
								alLetradosInsertar, segundoPeriodo, lDiasASeparar,
								"gratuita.literal.comentario.sustitucion"
//								UtilidadesString.getMensajeIdioma(usuModificacion1,
//										"gratuita.literal.comentario.sustitucion")
						);
					}
				}

			} // FIN Para cada dia o conjunto de dias
		}
		// avanzando el puntero de dia en el caso de guardias vinculadas
		if (calendariosVinculados != null && !calendariosVinculados.isEmpty())
			primerPeriodo = segundoPeriodo;

//		} catch (Exception e) {
//			arrayPeriodosLetradosSJCS1 = null;
//			throw new Exception(e + "");
//		}
		
		LOGGER.info("FIN generarCalendarioAsync.calcularMatrizLetradosGuardiaPorGrupos:");
		
	} // calcularMatrizLetradosGuardiaPorGrupos()

	public HashMap<Long, List< BajasTemporalesItem>> getLetradosDiasBajaTemporal(Integer idInstitucion,
			Integer idTurno, Integer idGuardia, String fechaDesde, String fechaHasta) throws Exception {
		BajasTemporalesItem bajasBean;
		String idPersona;
		Date fechaBT;
		List<BajasTemporalesItem> bajasDePersona = null;

		HashMap<Long, List< BajasTemporalesItem>> mSalida = null;

		try {

			List<BajasTemporalesItem> datos = scsGuardiasturnoExtendsMapper.getBajasTemporalesGuardias(
					idInstitucion.toString(), idTurno.toString(), idGuardia.toString(),null, fechaDesde, fechaHasta);

			mSalida = new HashMap<Long, List< BajasTemporalesItem>>();
			
			List<String> lista = new ArrayList<>();
			lista = datos.stream().map(BajasTemporalesItem::getIdpersona).distinct().collect(Collectors.toList());
			
			for(String idPer : lista) {
				mSalida.put(new Long(idPer),  datos.stream().filter(bajaItem -> bajaItem.getIdpersona().equals(idPer)).collect(Collectors.toList()));	
			}
			/*for (int i = 0; i < datos.size(); i++) {
				bajasBean = (BajasTemporalesItem) datos.get(i);
				idPersona = bajasBean.getIdpersona();
				fechaBT = bajasBean.getFechabt();

				bajasDePersona = (TreeMap<String, BajasTemporalesItem>) mSalida.get(idPersona);
				if (bajasDePersona != null)
					bajasDePersona.put(fechaBT.toString(), bajasBean);
				else {
					bajasDePersona = new TreeMap<String, BajasTemporalesItem>();
					bajasDePersona.put(fechaBT.toString(), bajasBean);
					mSalida.put(new Long(idPersona), bajasDePersona);
				}
			}*/

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener los getDiasBajaTemporal: " + e.toString();
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al obtener los getDiasBajaTemporal: " + e.toString());

		}

		return mSalida;
	}
	
	public HashMap<Long, List< BajasTemporalesItem>> getLetradosDiasBajaTemporal3(String idInstitucion,
			String idTurno, String idGuardia, String fechaDesde, String fechaHasta) throws Exception {
		BajasTemporalesItem bajasBean;
		String idPersona;
		Date fechaBT;
		List<BajasTemporalesItem> bajasDePersona = null;

		HashMap<Long, List< BajasTemporalesItem>> mSalida = null;

		try {

			List<BajasTemporalesItem> datos = scsGuardiasturnoExtendsMapper.getBajasTemporalesGuardias(
					idInstitucion, idTurno, idGuardia, null, fechaDesde, fechaHasta);

			mSalida = new HashMap<Long, List< BajasTemporalesItem>>();
			
			List<String> lista = new ArrayList<>();
			lista = datos.stream().map(BajasTemporalesItem::getIdpersona).distinct().collect(Collectors.toList());
			
			for(String idPer : lista) {
				mSalida.put(new Long(idPer),  datos.stream().filter(bajaItem -> bajaItem.getIdpersona().equals(idPer)).collect(Collectors.toList()));	
			}
			/*for (int i = 0; i < datos.size(); i++) {
				bajasBean = (BajasTemporalesItem) datos.get(i);
				idPersona = bajasBean.getIdpersona();
				fechaBT = bajasBean.getFechabt();

				bajasDePersona = (TreeMap<String, BajasTemporalesItem>) mSalida.get(idPersona);
				if (bajasDePersona != null)
					bajasDePersona.put(fechaBT.toString(), bajasBean);
				else {
					bajasDePersona = new TreeMap<String, BajasTemporalesItem>();
					bajasDePersona.put(fechaBT.toString(), bajasBean);
					mSalida.put(new Long(idPersona), bajasDePersona);
				}
			}*/

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener los getDiasBajaTemporal: " + e.toString();
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al obtener los getDiasBajaTemporal: " + e.toString());

		}

		return mSalida;
	}
	
	public HashMap<Long, List< BajasTemporalesItem>> getLetradosDiasBajaTemporal3(Integer idInstitucion,
			Integer idTurno, Integer idGuardia, String fechaDesde, String fechaHasta) throws Exception {
		BajasTemporalesItem bajasBean;
		String idPersona;
		Date fechaBT;
		List<BajasTemporalesItem> bajasDePersona = null;

		HashMap<Long, List< BajasTemporalesItem>> mSalida = null;

		try {

			List<BajasTemporalesItem> datos = scsGuardiasturnoExtendsMapper.getBajasTemporalesGuardias(
					idInstitucion.toString(), idTurno.toString(), idGuardia.toString(), "idPersona", fechaDesde, fechaHasta);

			mSalida = new HashMap<Long, List< BajasTemporalesItem>>();
			
			List<String> lista = new ArrayList<>();
			lista = datos.stream().map(BajasTemporalesItem::getIdpersona).distinct().collect(Collectors.toList());
			
			for(String idPer : lista) {
				mSalida.put(new Long(idPer),  datos.stream().filter(bajaItem -> bajaItem.getIdpersona().equals(idPer)).collect(Collectors.toList()));	
			}

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener los getDiasBajaTemporal: " + e.toString();
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al obtener los getDiasBajaTemporal: " + e.toString());

		}

		return mSalida;
	}

	private void crearRegistroGrupoGuardiaColegiadoCalendario() throws Exception {
		try {
			scsGuardiasturnoExtendsMapper.insertGrupoGuardiaColegiadoCalendario(idCalendarioGuardias1.toString(),
					idTurno1.toString(), idInstitucion1.toString(), idGuardia1.toString());

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al crear la copia de la cola de guardias de grupos de un calendario: "
					+ e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception("Error al crear la copia de la cola de guardias de grupos de un calendario: " + e);
		}
	}
	
	private void crearRegistroGrupoGuardiaColegiadoCalendario3(String idInstitucion, String idGuardia, String idTurno, String idCalendarioGuardias) throws Exception {
		try {
			scsGuardiasturnoExtendsMapper.insertGrupoGuardiaColegiadoCalendario(idCalendarioGuardias,
					idTurno, idInstitucion, idGuardia);

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al crear la copia de la cola de guardias de grupos de un calendario: "
					+ e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception("Error al crear la copia de la cola de guardias de grupos de un calendario: " + e);
		}
	}

	/**
	 * Obtiene los saltos o las compensaciones pendientes dada una guardia. Para
	 * cada salto o compensacion, anyade al bean los letrados del grupo
	 * correspondiente.
	 *
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @param saltoOcompensacion
	 * @param compensaciones2
	 * @return
	 * @throws ClsExceptions
	 */
	public ArrayList<SaltoCompGuardiaGrupoItem> getSaltosCompensacionesPendientesGuardia(Integer idInstitucion,
			Integer idTurno, Integer idGuardia, String fechaGuardia, String saltoOcompensacion) throws Exception {
		// Variables
		List<SaltoCompGuardiaGrupoItem> compensaciones;
		ArrayList<SaltoCompGuardiaGrupoItem> resultado = null;
		SaltoCompGuardiaGrupoItem compensacion;
		try {
			// obteniendo las compensaciones
			compensaciones = scsGuardiasturnoExtendsMapper.getSaltosCompensacionesGrupo(saltoOcompensacion,
					idTurno.toString(), idInstitucion.toString(), idGuardia.toString());

			// obteniendo los letrados de cada grupo que tiene compensacion
			resultado = new ArrayList<SaltoCompGuardiaGrupoItem>();
			for (int i = 0; i < compensaciones.size(); i++) {
				compensacion = compensaciones.get(i);
				ArrayList<LetradoInscripcionItem> letradoInscripciones = getLetradosGrupo(idInstitucion, idTurno,
						idGuardia, compensacion.getIdGrupoGuardia().toString(), saltoOcompensacion,
						compensacion.getIdSaltoCompensacionGrupo().toString(), fechaGuardia);
				// Si no hay ningun letrado inscrito del grupo que hay que compensar continuamos
				if (letradoInscripciones == null)
					continue;
				compensacion.setLetrados(letradoInscripciones);
				resultado.add(compensacion);
			}

		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo los saltos o las compensaciones pendientes dada una guardia: "
					+ e;
			LOGGER.info(errorGeneracionCalendario);
		}
		return resultado;
	}

	public ArrayList<LetradoInscripcionItem> getLetradosGrupo(Integer idInstitucion, Integer idTurno, Integer idGuardia,
			String idGrupoGuardia, String saltoCompensacion, String idSaltoCompensacionGrupo, String fechaGuardia)
			throws Exception {
		try {

			// Variables
			List<InscripcionGuardiaItem> vectorLetrados;
			ArrayList<LetradoInscripcionItem> listaLetrados = new ArrayList<LetradoInscripcionItem>();
			LetradoInscripcionItem letradoGuardia;
			InscripcionGuardiaItem inscripcionGuardia;

			// obteniendo lista de letrados
			vectorLetrados = getLetradosGrupo(idInstitucion.toString(), idTurno.toString(), idGuardia.toString(),
					idGrupoGuardia.toString(), fechaGuardia);
			if (vectorLetrados == null || vectorLetrados.size() == 0)
				return null;

			// obteniendo LetradoGuardia's
			listaLetrados = new ArrayList<LetradoInscripcionItem>();
			for (int i = 0; i < vectorLetrados.size(); i++) {
				inscripcionGuardia = (InscripcionGuardiaItem) vectorLetrados.get(i);
				letradoGuardia = new LetradoInscripcionItem();
				letradoGuardia.setInscripcionGuardia(inscripcionGuardia);
				if (saltoCompensacion != null) {
					letradoGuardia.setSaltoocompensacion(saltoCompensacion);
					letradoGuardia.setIdSaltoCompensacionGrupo(idSaltoCompensacionGrupo);
				}
				listaLetrados.add(letradoGuardia);
			}

			return listaLetrados;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo los letrados grupos: " + e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al ejecutar getLetradosGrupo()");

		}
	} // getLetradosGrupo()

	/**
	 * Obtiene los letrados dado un grupo
	 */
	public List<InscripcionGuardiaItem> getLetradosGrupo(String idInstitucion, String idTurno, String idGuardia,
			String idGrupoGuardia, String fechaGuardia) throws Exception {
		try {
			if (idGrupoGuardia == null || idGrupoGuardia.equals(""))
				return null;

			List<InscripcionGuardiaItem> datos = null;

			datos = scsGuardiasturnoExtendsMapper.getLetradosGrupos(fechaGuardia, idTurno, idInstitucion, idGuardia,
					idGrupoGuardia);
//				for (int i = 0; i < rc.size(); i++) {
//					Row fila = (Row) rc.get(i);
//					Hashtable<String, Object> htFila = fila.getRow();
//					if(((String)htFila.get("ACTIVO"))!=null && ((String)htFila.get("ACTIVO")).equals("1") )
//						datos.add(beanToHashCola(htFila));
//				}

			return datos;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo los letrados grupos de BD: " + e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al ejecutar getLetradosGrupo()");
		}
	}

	/**
	 * Obtiene la cola de una guardia
	 *
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @param fechaInicio
	 * @param fechaFin
	 * @param usr
	 * @return
	 * @throws ClsExceptions
	 */
	public List<LetradoInscripcionItem> getColaGuardia(Integer idInstitucion, Integer idTurno, Integer idGuardia,
			String fechaInicio, String fechaFin) throws Exception {
		try {
			// Variables para navegacion por la cola
			InscripcionGuardiaItem ultimoAnterior;
			InscripcionGuardiaItem punteroInscripciones;
			boolean foundUltimo;

			// Variables de la guardia
			GuardiasTurnoItem beanGuardia;
			Integer idOrdenacionColas;
			String orden;
			boolean porGrupos;
			Long idPersonaUltimo, idGrupoGuardiaUltimo;
			Date fechaSuscripcionUltimo;
			String idGrupoGuardiaUltimo2 = null;

			// Controles
			List<LetradoInscripcionItem> colaLetrados = new ArrayList<LetradoInscripcionItem>();

			// Actualizar cola guardia para evitar que el ultimo grupo quede a caballo
			actualizarColaGuardiaConUltimoColegiadoPorGrupo(idInstitucion.shortValue(), idTurno, idGuardia); //TODO: OK (L)

			// obteniendo la guardia

			GuardiasTurnoItem vGuardia = scsGuardiasturnoExtendsMapper.getGuardia(idGuardia.toString(),
					idTurno.toString(), idInstitucion.toString());
			beanGuardia = (GuardiasTurnoItem) vGuardia;
			porGrupos = beanGuardia.getPorGrupos().equals("1");// true
			idOrdenacionColas = beanGuardia.getIdOrdenacionColas();
			idPersonaUltimo = beanGuardia.getIdPersona_Ultimo();
			idGrupoGuardiaUltimo = beanGuardia.getIdGrupoGuardiaColegiado_Ultimo();
			if (idGrupoGuardiaUltimo == null) {
				idGrupoGuardiaUltimo2 = "";
			} else {
				idGrupoGuardiaUltimo2 = idGrupoGuardiaUltimo.toString();
			}
			if (beanGuardia.getFechaSuscripcion_Ultimo() != null
					&& !beanGuardia.getFechaSuscripcion_Ultimo().isEmpty()) {
				fechaSuscripcionUltimo = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.S")
						.parse(beanGuardia.getFechaSuscripcion_Ultimo());
			} else {
				fechaSuscripcionUltimo = null;
			}

			// obteniendo ordenacion de la guardia
			if (idOrdenacionColas == null)
				throw new Exception("messages.general.error");
			orden = porGrupos ? " numeroGrupo, ordengrupo" : getOrderBy(idOrdenacionColas.toString());

			// obteniendo ultimo apuntado de la guardias
			if (idPersonaUltimo == null)
				ultimoAnterior = null;
			else
				ultimoAnterior = new InscripcionGuardiaItem(idInstitucion.toString(), idTurno.toString(),
						idGuardia.toString(), idPersonaUltimo.toString(), fechaSuscripcionUltimo,
						idGrupoGuardiaUltimo2);
			
			GuardiasItem guardiaBuscador = new GuardiasItem();
			guardiaBuscador.setIdTurno(idTurno.toString());
			guardiaBuscador.setIdGuardia(vGuardia.getIdGuardia());
			guardiaBuscador.setIdOrdenacionColas(vGuardia.getIdOrdenacionColas().toString());
			guardiaBuscador.setPorGrupos(vGuardia.getPorGrupos());
			guardiaBuscador.setIdInstitucion(Short.valueOf(vGuardia.getIdInstitucion()));
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String fecha = sdf.format(new Date());
		    
		    guardiaBuscador.setLetradosIns(fecha);
		    
		    
		    InscripcionGuardiaDTO respuesta = searchColaGuardia(guardiaBuscador, null, "Calendarios");//TODO:(L) 
		    List<InscripcionGuardiaItem> listaLetrados = respuesta.getInscripcionesItem();

		    /*
			// obteniendo lista de letrados (ordenada)
			List<InscripcionGuardiaItem> listaLetrados = null;
			if (idGuardia != null) {
				listaLetrados = getColaGuardia2(idInstitucion.toString(), idTurno.toString(), idGuardia.toString(),
						fechaInicio, fechaFin, porGrupos, orden);
			} else {
				listaLetrados = getColaGuardia2(idInstitucion.toString(), idTurno.toString(), null, fechaInicio,
						fechaFin, porGrupos, orden);
			}
			
			if (listaLetrados == null || listaLetrados.size() == 0)
				return colaLetrados;
			
			if (ultimoAnterior == null) {
				// si no existe ultimo colegiado, se empieza la cola desde el primero en la
				// lista
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = (InscripcionGuardiaItem) listaLetrados.get(i);
					if (punteroInscripciones.getEstado() != null && punteroInscripciones.getEstado().equals("1")) // true
					{
						LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
						letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
						colaLetrados.add(letradoInscripcionItem);
					}

				}
			} else {
				// ordenando la cola en funcion del idPersonaUltimo guardado
				List<LetradoInscripcionItem> colaAuxiliar = new ArrayList<LetradoInscripcionItem>();
				foundUltimo = false;
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = listaLetrados.get(i);

					// insertando en la cola si la inscripcion esta activa
					if (punteroInscripciones.getEstado() != null && punteroInscripciones.getEstado().equals("1")) { // true

						LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
						letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
						// El primero que se anyade es el siguiente al ultimo
						if (foundUltimo) {
							colaLetrados.add(letradoInscripcionItem);
						} else {
							colaAuxiliar.add(letradoInscripcionItem);
						}
					}

					// revisando si se encontro ya al ultimo
					if (!foundUltimo && punteroInscripciones.getIdPersona().equals(ultimoAnterior.getIdPersona()))
						foundUltimo = true;
				}
				colaLetrados.addAll(colaAuxiliar);
			}*/
			if (listaLetrados == null || listaLetrados.size() == 0)
				return colaLetrados;
			for (int i = 0; i < listaLetrados.size(); i++) {
				punteroInscripciones = listaLetrados.get(i);

				LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
				letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
				colaLetrados.add(letradoInscripcionItem);

			}
			
			
			// usando saltos si es necesario (en guardias no)

			return colaLetrados;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo la cola de guardia: " + e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al ejecutar getColaGuardia()");
		}
	} // getColaGuardia()

	public void actualizarColaGuardiaConUltimoColegiadoPorGrupo(Short idInstitucion, Integer idTurno,
			Integer idGuardia) {
		// actualizando el ultimo colegiado de la guardia al ultimo colegiado del grupo
		// (que era ultimo de la guardia)
		try {
			// obteniendo el ultimo colegiado del grupo tal que es el ultimo asignado en la
			// cola:
			GrupoGuardiaRowItem registro = scsGuardiasturnoExtendsMapper.getUltimoColegiadoGrupo(idTurno.toString(),
					idInstitucion.toString(), idGuardia.toString());
			if (registro != null) {
				ScsGuardiasturnoKey guardiaKey = new ScsGuardiasturnoKey();
				guardiaKey.setIdinstitucion(idInstitucion);
				guardiaKey.setIdturno(idTurno);
				guardiaKey.setIdguardia(idGuardia);

				ScsGuardiasturno guardia = scsGuardiasTurnoMapper.selectByPrimaryKey(guardiaKey);

				if (guardia != null) {
					ScsInscripcionguardiaExample insc = new ScsInscripcionguardiaExample();
					insc.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.parseLong(registro.getIdPersona()))
					.andIdguardiaEqualTo(idGuardia).andIdturnoEqualTo(idTurno);
					insc.setOrderByClause("fechasuscripcion desc");
					
					List<ScsInscripcionguardia> inscripcionList = scsInscripcionguardiaExtendsMapper.selectByExample(insc);
					if(inscripcionList != null && !inscripcionList.isEmpty()) {
						ScsInscripcionguardia inscripcionUpdate = inscripcionList.get(0);
						guardia.setIdpersonaUltimo(inscripcionUpdate.getIdpersona());
						guardia.setIdgrupoguardiaUltimo((long)registro.getIdGrupoGuardiaColegiado());
						guardia.setFechasuscripcionUltimo(inscripcionUpdate.getFechasuscripcion()); //fecha inscripcionguardia
						scsGuardiasTurnoMapper.updateByPrimaryKeySelective(guardia);
					}
					//select inscripcionguardiasbyexample
					
					//scsGuardiasturnoExtendsMapper.actualizarUltimoColegiado(guardia);
				}
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "actualizando el ultimo colegiado de la guardia al ultimo colegiado del grupo: "
					+ e;
			LOGGER.info(errorGeneracionCalendario);
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene las inscripciones ordenadas para formar la cola de una guardia dada
	 * una fecha. Importante: obtiene todas las inscripciones, no solo las que estan
	 * de alta, ya que luego hay que ordenarlas segun el puntero, que puede estar
	 * apuntando a una inscripcion de baja
	 */
	public List<InscripcionGuardiaItem> getColaGuardia2(String idinstitucion, String idturno, String idguardia,
			String fechaInicio, String fechaFin, boolean porGrupos, String order) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))
				return null;
			if (idturno == null || idturno.equals(""))
				return null;
			if (idguardia == null || idguardia.equals(""))
				return null;
			if (fechaInicio == null || fechaInicio.equals(""))
				fechaInicio = "null";
			else if (!fechaInicio.trim().equalsIgnoreCase("sysdate"))
				fechaInicio = "'" + fechaInicio.trim() + "'";
			if (fechaFin == null || fechaFin.equals(""))
				fechaFin = "null";
			else if (!fechaFin.trim().equalsIgnoreCase("sysdate"))
				fechaFin = "'" + fechaFin.trim() + "'";

			List<InscripcionGuardiaItem> datos = null;

			datos = scsGuardiasturnoExtendsMapper.getColaGuardia(fechaInicio, fechaFin, idinstitucion, idturno,
					idguardia, order);
//				for (int i = 0; i < rc.size(); i++) {
//					Row fila = (Row) rc.get(i);
//					Hashtable<String, Object> htFila = fila.getRow();
//					datos.add(beanToHashCola(htFila));
//				}
			return datos;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo las inscripciones ordenadas para formar la cola de una guardia dada\r\n"
					+ "	 * una fecha: " + e;
			LOGGER.info(errorGeneracionCalendario);
			throw new Exception(e + ": Error al ejecutar getColaGuardia()");
		}
	} // getColaGuardia()

	/**
	 * Obtiene la clausula order by para el select de inscripciones
	 *
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @param usr
	 * @return Order by sin el "order by"
	 * @throws ClsExceptions
	 */
	public String getOrderBy(String idOrdenacionColas) throws Exception {
		// obteniendo el orden
		List<ScsOrdenacioncolas> vOrden = scsGuardiasturnoExtendsMapper.getOrdenacionColas(idOrdenacionColas);
		ScsOrdenacioncolas ordenBean = (ScsOrdenacioncolas) vOrden.get(0);
		Short apellidos = ordenBean.getAlfabeticoapellidos();
		Short antiguedad = ordenBean.getAntiguedadcola();
		Short fechanacimiento = ordenBean.getFechanacimiento();
		Short numerocolegiado = ordenBean.getNumerocolegiado();

		// calculando order by
		String orden = "";
		for (int i = 4; i > 0; i--) {
			if (Math.abs(apellidos) == i) {
				orden += "ALFABETICOAPELLIDOS";
				if (Math.abs(apellidos) != apellidos)
					orden += " desc";
				orden += "," + "NOMBRE";
				if (Math.abs(apellidos) != apellidos)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(antiguedad) == i) {
				orden += "ANTIGUEDADCOLA";
				if (Math.abs(antiguedad) != antiguedad)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(fechanacimiento) == i) {
				orden += "FECHANACIMIENTO";
				if (Math.abs(fechanacimiento) != fechanacimiento)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(numerocolegiado) == i) {
				// orden += "to_number("+ScsOrdenacionColasBean.C_NUMEROCOLEGIADO+")"; esta
				// linea queda comentada para que se vea que el to_number es peligroso
				orden += "lpad(NUMEROCOLEGIADO,20,'0')";
				if (Math.abs(numerocolegiado) != numerocolegiado)
					orden += " desc";
				orden += ", ";
			}
		}

		if (orden.equals("")) {
			// En el caso de que no se haya establecido orden, se ordena por antiguedad en
			// la cola
			orden = "ANTIGUEDADCOLA";
			orden += ", ";
		}
		orden = orden.substring(0, orden.length() - 2); // quitando ultima coma

		return orden;
	} // getOrderBy()

	/**
	 * Obtiene el siguiente grupo de letrados valido para asignar en una guardia
	 *
	 * @param alCompensaciones
	 * @param alLetradosOrdenados
	 * @param punteroLetrado
	 * @param diasGuardia
	 * @param alSaltos
	 * @return
	 * @throws ClsExceptions
	 * @throws SIGAException
	 */
	private ArrayList<LetradoInscripcionItem> getSiguienteGrupo(ArrayList<SaltoCompGuardiaGrupoItem> alCompensaciones,
			List<LetradoInscripcionItem> alLetradosOrdenados, Puntero punteroLetrado, ArrayList<String> diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List<BajasTemporalesItem>> hmBajasTemporales) throws Exception {
		// Variables
		ArrayList<LetradoInscripcionItem> grupoLetrados;
		SaltoCompGuardiaGrupoItem compensacion = null;
		boolean grupoValido;
		int restricciones;

		// obteniendo grupo de entre los compensados
		grupoValido = false;
		if(alCompensaciones != null) {
			Iterator<SaltoCompGuardiaGrupoItem> iterador = alCompensaciones.iterator();
			while (iterador.hasNext() && !grupoValido) {
				compensacion = iterador.next();

				// comprobando cada letrado del grupo
				// log.addLog(new String[] {"Probando Grupo Compensado",
				// compensacion.getLetrados().toString()});

				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Probando Grupo Compensado", compensacion.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Probando Grupo Compensado - "+ compensacion.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
				grupoValido = true;
				for (LetradoInscripcionItem lg : compensacion.getLetrados()) {
					if (!comprobarRestriccionesLetradoCompensado(lg, diasGuardia, null,
							compensacion.getIdSaltoCompensacionGrupo().toString(), hmBajasTemporales,hmPersonasConSaltos))
						grupoValido = false;
					if (!grupoValido)
						break; // salir de las comprobaciones por letrado si uno de ellos no es valido
				}
				if (!grupoValido) {
					// log.addLog(new String[] {"Grupo Compensado no valido",
					// compensacion.getLetrados().toString()});
					Map<String, Object> mapLog1 = new HashMap();
					mapLog1.put("*Grupo Compensado no valido", compensacion.getLetrados().toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog1);
					LOGGER.info("*Grupo Compensado no valido" + compensacion.getLetrados().toString());
				}
			}
		}
		
		

		// si bien, cumplir la compensacion de grupo:
		if (grupoValido) {
			// log.addLog(new String[] {"Compensacion de grupo cumplida"});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Compensacion de grupo cumplida", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Compensacion de grupo cumplida");
			// String motivoCumplimiento = "Compensacion de grupo cumplida";
			String motivoCumplimiento = "(Registro Automático) Utilizando compensación en día de Guardia: "
					+ diasGuardia.get(0) + "por generación calendario";
			try {
				cumplirSaltoCompensacion2(compensacion.getIdSaltoCompensacionGrupo().toString(), diasGuardia.get(0),
						motivoCumplimiento, idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(),
						idCalendarioGuardias1.toString());
			} catch (Exception e) {
				errorGeneracionCalendario = "Error cumpliendo compensaciones: " + e;
				LOGGER.info(errorGeneracionCalendario);
			}
			grupoLetrados = compensacion.getLetrados();

		} else {
			// obteniendo grupo de la cola
			grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
			if (grupoLetrados != null) {
				int idgrupoinicial = Integer.parseInt(grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				while (grupoLetrados != null && !grupoValido) {

					// comprobando cada letrado del grupo
					// log.addLog(new String[] {"Probando Grupo", grupoLetrados.toString()});

					Map<String, Object> mapLog3 = new HashMap();
					mapLog3.put("*Probando Grupo", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
					listaDatosExcelGeneracionCalendarios.add(mapLog3);
					LOGGER.info("*Probando Grupo" + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
					grupoValido = true;
					for (LetradoInscripcionItem lg : grupoLetrados) {
						if (!comprobarRestriccionesLetradoCola(lg, diasGuardia, hmPersonasConSaltos, hmBajasTemporales,
								false)) {
							grupoValido = false;
							break; // salir de las comprobaciones por letrado si uno de ellos no es valido
						}
					}
					if (!grupoValido) {
						Map<String, Object> mapLog4 = new HashMap();
						// log.addLog(new String[] {"Grupo no valido", grupoLetrados.toString()});
						mapLog4.put("*Grupo no valido",grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
						listaDatosExcelGeneracionCalendarios.add(mapLog4);
						LOGGER.info("*Grupo no valido" + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
						grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
						if (idgrupoinicial == Integer.parseInt(grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo()))
							break;
					}
				}
			}
		}
		if (grupoValido) {
			try {
				String ident = grupoLetrados.get(0).getInscripcionGuardia().getIdGrupoGuardia();
				if( ident == null || ident.isEmpty() ) {
					ident = grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo();
				}
			
				modifyOrderGruposLetrados(Integer.parseInt(ident));
				return grupoLetrados;
			}catch (Exception e) {
				LOGGER.info("ERROR EN reordenarRestoGrupoLetrados");
				return null;
			}
			
		} else {
			return null;
		}
	} // getSiguienteGrupo()
	
	private ArrayList<LetradoInscripcionItem> getSiguienteGrupo3(GuardiasCalendarioItem guardiasCalendarioItem, ArrayList<SaltoCompGuardiaGrupoItem> alCompensaciones,
			List<LetradoInscripcionItem> alLetradosOrdenados, Puntero punteroLetrado, ArrayList<String> diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List<BajasTemporalesItem>> hmBajasTemporales) throws Exception {
		
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		
		// Variables
		ArrayList<LetradoInscripcionItem> grupoLetrados;
		SaltoCompGuardiaGrupoItem compensacion = null;
		boolean grupoValido;
		int restricciones;
		boolean esGrupo = true;
		
		// obteniendo grupo de entre los compensados
		grupoValido = false;
		if(alCompensaciones != null) {
			Iterator<SaltoCompGuardiaGrupoItem> iterador = alCompensaciones.iterator();
			while (iterador.hasNext() && !grupoValido) {
				compensacion = iterador.next();

				// comprobando cada letrado del grupo
				// log.addLog(new String[] {"Probando Grupo Compensado",
				// compensacion.getLetrados().toString()});

				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Probando Grupo Compensado", compensacion.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Probando Grupo Compensado - "+ compensacion.getLetrados().get(0).getInscripcionGuardia().getNumeroGrupo());
				grupoValido = true;
				for (LetradoInscripcionItem lg : compensacion.getLetrados()) {
					if (!comprobarRestriccionesLetradoCompensado3(guardiasCalendarioItem, lg, diasGuardia, null,
							compensacion.getIdSaltoCompensacionGrupo().toString(), hmBajasTemporales,hmPersonasConSaltos, esGrupo))
						grupoValido = false;
					if (!grupoValido)
						break; // salir de las comprobaciones por letrado si uno de ellos no es valido
				}
				if (!grupoValido) {
					// log.addLog(new String[] {"Grupo Compensado no valido",
					// compensacion.getLetrados().toString()});
					Map<String, Object> mapLog1 = new HashMap();
					mapLog1.put("*Grupo Compensado no valido", compensacion.getLetrados().toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog1);
					LOGGER.info("*Grupo Compensado no valido" + compensacion.getLetrados().toString());
				}
			}
		}
		
		

		// si bien, cumplir la compensacion de grupo:
		if (grupoValido) {
			// log.addLog(new String[] {"Compensacion de grupo cumplida"});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Compensacion de grupo cumplida", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Compensacion de grupo cumplida");
			// String motivoCumplimiento = "Compensacion de grupo cumplida";
			String motivoCumplimiento = "(Registro Automático) Utilizando compensación en día de Guardia: "
					+ diasGuardia.get(0) + "por generación calendario";
			try {
				cumplirSaltoCompensacion23(compensacion.getIdSaltoCompensacionGrupo().toString(), diasGuardia.get(0),
						motivoCumplimiento, guardiasCalendarioItem);
			} catch (Exception e) {
				errorGeneracionCalendario = "Error cumpliendo compensaciones: " + e;
				LOGGER.info(errorGeneracionCalendario);
			}
			grupoLetrados = compensacion.getLetrados();

		} else {
			// obteniendo grupo de la cola
			grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
			if (grupoLetrados != null) {
				int idgrupoinicial = Integer.parseInt(grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
				while (grupoLetrados != null && !grupoValido) {

					// comprobando cada letrado del grupo
					// log.addLog(new String[] {"Probando Grupo", grupoLetrados.toString()});

					Map<String, Object> mapLog3 = new HashMap();
					mapLog3.put("*Probando Grupo", grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
					listaDatosExcelGeneracionCalendarios.add(mapLog3);
					LOGGER.info("*Probando Grupo" + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
					grupoValido = true;
					for (LetradoInscripcionItem lg : grupoLetrados) {
						if (!comprobarRestriccionesLetradoCola3(guardiasCalendarioItem, lg, diasGuardia, hmPersonasConSaltos, hmBajasTemporales,
								false,esGrupo)) {
							grupoValido = false;
							break; // salir de las comprobaciones por letrado si uno de ellos no es valido
						}
					}
					if (!grupoValido) {
						Map<String, Object> mapLog4 = new HashMap();
						// log.addLog(new String[] {"Grupo no valido", grupoLetrados.toString()});
						mapLog4.put("*Grupo no valido",grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
						listaDatosExcelGeneracionCalendarios.add(mapLog4);
						LOGGER.info("*Grupo no valido" + grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo());
						grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
						if (idgrupoinicial == Integer.parseInt(grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo()))
							break;
					}
				}
			}
		}
		if (grupoValido) {
			try {
				String ident = grupoLetrados.get(0).getInscripcionGuardia().getIdGrupoGuardia();
				if( ident == null || ident.isEmpty() ) {
					ident = grupoLetrados.get(0).getInscripcionGuardia().getNumeroGrupo();
				}
			
				modifyOrderGruposLetrados(Integer.parseInt(ident));
				return grupoLetrados;
			}catch (Exception e) {
				LOGGER.info("ERROR EN reordenarRestoGrupoLetrados");
				return null;
			}
			
		} else {
			return null;
		}
	} // getSiguienteGrupo()

	private boolean comprobarRestriccionesLetradoCompensado(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo, HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales,HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos)
			throws Exception {
		// Controles

		// si esta de vacaciones, ...
		try {
			if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia,
					letradoGuardia)) {
				// log.addLog(new String[] {"Encontrado Baja temporal",
				// letradoGuardia.toString(), diasGuardia.toString()});
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' + diasGuardia.toString());
				if (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() == null || letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals(""))
					// ... crear un salto cumplido (como si fuera un log)
					insertarNuevoSaltoBT(letradoGuardia, diasGuardia,
							"Cumplido en dia de guardia " + diasGuardia.get(0));
				else
					// ... crear un salto cumplido (como si fuera un log)
					crearSaltoBT(letradoGuardia.getInscripcionGuardia().getNumeroGrupo(),
							"Cumplido en dia de guardia " + diasGuardia.get(0), "", idInstitucion1.toString(),
							idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString(),
							idCalendarioGuardias1.toString(), letradoGuardia.getBajaTemporal());
				return false; // y no seleccionar
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si el letrado tiene bajas temporales: " + e;
		}

		// si hay incompatibilidad
		try {
			if (isIncompatible(letradoGuardia, diasGuardia)) {
				String nombre = "";
				String ap1 = "";
				String ap2 = "";
				// log.addLog(new String[] {"Encontrado Incompatibilidad",
				// letradoGuardia.toString(), diasGuardia.toString()});
				if (letradoGuardia.getInscripcionGuardia() != null) {
					Map<String, Object> mapLog = new HashMap();
					if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
						ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
					if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
						ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
					if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
						nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
					mapLog.put("*EncontradEo Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*EncontradEo Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
				} else if (letradoGuardia.getInscripcionTurno() != null) {
					Map<String, Object> mapLog = new HashMap();
					if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
						ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
					if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
						ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
					if (letradoGuardia.getInscripcionTurno().getNombre() != null)
						nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
					mapLog.put("*EncontraEdo Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*EncontraEdo Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);

				}
				return false; // no seleccionar
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si el letrado es incompatible: " + e;
		}
		List<LetradoInscripcionItem> alSaltos = new ArrayList();
		if( letradoGuardia.getInscripcionGuardia().getNumeroGrupo() != null) {
			alSaltos = hmPersonasConSaltos.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getNumeroGrupo()));
		}else {
			 alSaltos =  hmPersonasConSaltos.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getIdPersona()));
				
		}
		
		// cumpliendo compensacion
		if ((letradoGuardia.getInscripcionGuardia().getNumeroGrupo() == null || letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals(""))  ) {
			if(alSaltos != null && !alSaltos.isEmpty()) {
				// log.addLog(new String[] { "Encontrado Salto", letradoGuardia.toString() });
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Salto y Compensacion encontrado", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				// ... compensar uno
				String motivoS = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				
				String motivoC = "(Registro Automático) Utilizando compensación en día de Guardia: "
						+ diasGuardia.get(0) + "por generación calendario";
				controlVacioSC = true;
				try {
					cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "S", motivoS);
					cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", motivoC);
					iteCompensaciones.remove();
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo salto: " + e;
				}

				return false; // y no seleccionar
			}else {
				// log.addLog(new String[] {"Compensacion cumplida",
				// letradoGuardia.toString()});
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Compensacion cumplida", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*Compensacion cumplida" + letradoGuardia.toString());
//				String motivo = ":id="+idCalendarioGuardias1.toString()+":Cumplido en fecha ("+diasGuardia.get(0)+"):finid="+idCalendarioGuardias1.toString()+":";
				String motivo = "(Registro Automático) Utilizando compensación en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				try {
					cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", motivo);
					iteCompensaciones.remove();
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo saltos y compensaciones: " + e;
				}
				return false;
			}
			
		} else {
			// nada, hay que cumplir la compensacion cuando todos los letrados esten
			// comprobados
			//AÑADIR AQUI CONDICION SI ES GRUPO Y TIENE ASALTO
			if(alSaltos != null && !alSaltos.isEmpty() && (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() != null && !letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals(""))  ) {
					controlGrupoConSalto = true;	
			}else {
				controlGrupoConSalto = false;
			}
			// una vez comprobado todo, se selecciona a este letrado
			// log.addLog(new String[] {"Letrado ok", letradoGuardia.toString()});
			String nombre = "";
			String ap1 = "";
			String ap2 = "";
			if (letradoGuardia.getInscripcionGuardia() != null) {

				Map<String, Object> mapLog12 = new HashMap();

				if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
					ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
				if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
					ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
				if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
					nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
				mapLog12.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog12);
				LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
			} else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog13 = new HashMap();
				if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
					ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
				if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
					ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
				if (letradoGuardia.getInscripcionTurno().getNombre() != null)
					nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
				mapLog13.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog13);
				LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
			}
			return true;
		}
	} // comprobarRestriccionesLetradoCompensado()
	
	private boolean comprobarRestriccionesLetradoCompensado3(GuardiasCalendarioItem guardiasCalendarioItem, LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo, HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales,HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos, boolean esGrupo)
			throws Exception {
		
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		// Controles

		// si esta de vacaciones, ...
		try {
			if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia,
					letradoGuardia)) {
				// log.addLog(new String[] {"Encontrado Baja temporal",
				// letradoGuardia.toString(), diasGuardia.toString()});
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' + diasGuardia.toString());
				// ... crear un salto cumplido (como si fuera un log)
				if(esGrupo && (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() != null && !letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals("")) ) {
					crearSaltoBT3(letradoGuardia.getInscripcionGuardia().getNumeroGrupo(),
							"Cumplido en dia de guardia " + diasGuardia.get(0), "", guardiasCalendarioItem,
							idCalendarioGuardias, letradoGuardia.getBajaTemporal());
				}else{
						insertarNuevoSaltoBT(letradoGuardia, diasGuardia,
								"Cumplido en dia de guardia " + diasGuardia.get(0));
				}
				return false; // y no seleccionar
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si el letrado tiene bajas temporales: " + e;
		}

		// si hay incompatibilidad
		try {
			if (isIncompatible(letradoGuardia, diasGuardia)) {
				String nombre = "";
				String ap1 = "";
				String ap2 = "";
				// log.addLog(new String[] {"Encontrado Incompatibilidad",
				// letradoGuardia.toString(), diasGuardia.toString()});
				if (letradoGuardia.getInscripcionGuardia() != null) {
					Map<String, Object> mapLog = new HashMap();
					if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
						ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
					if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
						ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
					if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
						nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
					mapLog.put("*EncontradEo Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*EncontradEo Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
				} else if (letradoGuardia.getInscripcionTurno() != null) {
					Map<String, Object> mapLog = new HashMap();
					if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
						ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
					if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
						ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
					if (letradoGuardia.getInscripcionTurno().getNombre() != null)
						nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
					mapLog.put("*EncontraEdo Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*EncontraEdo Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);

				}
				return false; // no seleccionar
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si el letrado es incompatible: " + e;
		}
		List<LetradoInscripcionItem> alSaltos = new ArrayList();
		if(esGrupo) {
			alSaltos = hmPersonasConSaltos.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getNumeroGrupo()));
		}else {
			 alSaltos =  hmPersonasConSaltos.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getIdPersona()));
				
		}
		
		// cumpliendo compensacion
		if (!esGrupo) {
			if(alSaltos != null && !alSaltos.isEmpty()) {
				// log.addLog(new String[] { "Encontrado Salto", letradoGuardia.toString() });
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Salto y Compensacion encontrado", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				// ... compensar uno
				String motivoS = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				
				String motivoC = "(Registro Automático) Utilizando compensación en día de Guardia: "
						+ diasGuardia.get(0) + "por generación calendario";
				controlVacioSC = true;
				try {
					cumplirSaltoCompensacion3(guardiasCalendarioItem, letradoGuardia, diasGuardia, "S", motivoS);
					cumplirSaltoCompensacion3(guardiasCalendarioItem,letradoGuardia, diasGuardia, "C", motivoC);
					iteCompensaciones.remove();
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo salto: " + e;
				}

				return false; // y no seleccionar, porque tendría un salto y una compensación, se borran ambas.
			}else {
				// log.addLog(new String[] {"Compensacion cumplida",
				// letradoGuardia.toString()});
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Compensacion cumplida", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*Compensacion cumplida" + letradoGuardia.toString());
//				String motivo = ":id="+idCalendarioGuardias1.toString()+":Cumplido en fecha ("+diasGuardia.get(0)+"):finid="+idCalendarioGuardias1.toString()+":";
				String motivo = "(Registro Automático) Utilizando compensación en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				try {
					cumplirSaltoCompensacion3(guardiasCalendarioItem, letradoGuardia, diasGuardia, "C", motivo);
					iteCompensaciones.remove();
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo saltos y compensaciones: " + e;
				}
				return true;// si Cumplimos su compensacion o salto, seleccionamos el letrado
			}
			
		} else { //Si es GRUPO:
			// nada, hay que cumplir la compensacion cuando todos los letrados esten
			// comprobados
			//AÑADIR AQUI CONDICION SI ES GRUPO Y TIENE ASALTO
			if(alSaltos != null && !alSaltos.isEmpty() && (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() != null && !letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals(""))  ) {
					controlGrupoConSalto = true;	
			}else {
				controlGrupoConSalto = false;
			}
			// una vez comprobado todo, se selecciona a este letrado
			// log.addLog(new String[] {"Letrado ok", letradoGuardia.toString()});
			String nombre = "";
			String ap1 = "";
			String ap2 = "";
			if (letradoGuardia.getInscripcionGuardia() != null) {

				Map<String, Object> mapLog12 = new HashMap();

				if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
					ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
				if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
					ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
				if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
					nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
				mapLog12.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog12);
				LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
			} else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog13 = new HashMap();
				if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
					ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
				if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
					ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
				if (letradoGuardia.getInscripcionTurno().getNombre() != null)
					nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
				mapLog13.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog13);
				LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
			}
			return true;
		}
	} // comprobarRestriccionesLetradoCompensado()

	private void cumplirSaltoCompensacion(LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia,
			String saltoOCompensacion, String motivo) throws Exception {
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
		saltoCompensacion.setIdinstitucion(idInstitucion1.shortValue());
		saltoCompensacion.setIdturno(idTurno1);
		saltoCompensacion.setIdguardia(idGuardia1);
		saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
		saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);

		// saltoCompensacion.setIdCalendarioGuardiasCreacion(this.idCalendarioGuardias);

		saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
		// saltoCompensacion.setFechaCumplimiento(GstDate.getApplicationFormatDate("",
		// (String) diasGuardia.get(0)) );
		if (idGuardia1 != null)
			saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);
		saltoCompensacion.setMotivos(motivo);
		marcarSaltoCompensacion(saltoCompensacion, diasGuardia.get(0).toString());
	}
	
	private void cumplirSaltoCompensacion3(GuardiasCalendarioItem guardiasCalendarioItem, LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia,
			String saltoOCompensacion, String motivo) throws Exception {
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
		saltoCompensacion.setIdinstitucion(Short.valueOf(guardiasCalendarioItem.getIdinstitucion()));
		saltoCompensacion.setIdturno(Integer.valueOf(guardiasCalendarioItem.getIdturno()));
		saltoCompensacion.setIdguardia(Integer.valueOf(guardiasCalendarioItem.getIdguardia()));
		saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
		saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);

		// saltoCompensacion.setIdCalendarioGuardiasCreacion(this.idCalendarioGuardias);

		saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
		// saltoCompensacion.setFechaCumplimiento(GstDate.getApplicationFormatDate("",
		// (String) diasGuardia.get(0)) );
		if (guardiasCalendarioItem.getIdguardia() != null)
			saltoCompensacion.setIdcalendarioguardias(Integer.valueOf(guardiasCalendarioItem.getIdcalendarioguardias()));
		saltoCompensacion.setMotivos(motivo);
		saltoCompensacion.setUsumodificacion(Integer.valueOf(guardiasCalendarioItem.getUsumodificacion()));
		marcarSaltoCompensacion3(saltoCompensacion, diasGuardia.get(0).toString());
	}

	/**
	 * Cumple un salto/compensacion dado. Cuidado: si no se indica turno/guardia, se
	 * pueden cumplir muchos a la vez.
	 *
	 * @param saltoCompensacion
	 * @throws ClsExceptions
	 */
	public void marcarSaltoCompensacion(ScsSaltoscompensaciones saltoCompensacion, String fechaCumplimiento)
			throws Exception {
		try {
			String s_idinstitucion = saltoCompensacion.getIdinstitucion().toString();
			String s_idturno = null;
			if (saltoCompensacion.getIdturno() != null) {
				s_idturno = saltoCompensacion.getIdturno().toString();
			}
			String s_idguardia = null;
			if (saltoCompensacion.getIdguardia() != null) {
				s_idguardia = saltoCompensacion.getIdguardia().toString();
			}
			String s_idpersona = null;
			if (saltoCompensacion.getIdpersona() != null) {
				s_idpersona = saltoCompensacion.getIdpersona().toString();
			}
			String s_saltocompensacion = null;
			if (saltoCompensacion.getSaltoocompensacion() != null) {
				s_saltocompensacion = saltoCompensacion.getSaltoocompensacion();
				if (s_saltocompensacion.equals(""))
					s_saltocompensacion = " ";
			}
//			String OLD_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
//			String NEW_FORMAT = "dd/MM/YY";
//
//			saltoCompensacion.setFechacumplimiento(changeDateFormat(OLD_FORMAT, NEW_FORMAT, saltoCompensacion.getFechacumplimiento().toString()));
			scsGuardiasturnoExtendsMapper.marcarSaltoCompensacion(usuModificacion1, s_idturno,
					saltoCompensacion, s_idpersona, s_idinstitucion, s_idturno, s_idguardia, s_saltocompensacion,
					fechaCumplimiento);

		} catch (Exception e) {
			errorGeneracionCalendario = "Error marcando saltos y compensaciones: " + e;
			throw new Exception(e + "Excepcion en marcarSaltoCompensacionBBDD." + e.toString());
		}
	} // marcarSaltoCompensacion()
	
	public void marcarSaltoCompensacion3(ScsSaltoscompensaciones saltoCompensacion, String fechaCumplimiento)
			throws Exception {
		try {
			String s_idinstitucion = saltoCompensacion.getIdinstitucion().toString();
			String s_idturno = null;
			if (saltoCompensacion.getIdturno() != null) {
				s_idturno = saltoCompensacion.getIdturno().toString();
			}
			String s_idguardia = null;
			if (saltoCompensacion.getIdguardia() != null) {
				s_idguardia = saltoCompensacion.getIdguardia().toString();
			}
			String s_idpersona = null;
			if (saltoCompensacion.getIdpersona() != null) {
				s_idpersona = saltoCompensacion.getIdpersona().toString();
			}
			String s_saltocompensacion = null;
			if (saltoCompensacion.getSaltoocompensacion() != null) {
				s_saltocompensacion = saltoCompensacion.getSaltoocompensacion();
				if (s_saltocompensacion.equals(""))
					s_saltocompensacion = " ";
			}
			Integer s_usuModificacion = saltoCompensacion.getUsumodificacion();
//			String OLD_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
//			String NEW_FORMAT = "dd/MM/YY";
//
//			saltoCompensacion.setFechacumplimiento(changeDateFormat(OLD_FORMAT, NEW_FORMAT, saltoCompensacion.getFechacumplimiento().toString()));
			scsGuardiasturnoExtendsMapper.marcarSaltoCompensacion(s_usuModificacion, s_idturno,
					saltoCompensacion, s_idpersona, s_idinstitucion, s_idturno, s_idguardia, s_saltocompensacion,
					fechaCumplimiento);

		} catch (Exception e) {
			errorGeneracionCalendario = "Error marcando saltos y compensaciones: " + e;
			throw new Exception(e + "Excepcion en marcarSaltoCompensacionBBDD." + e.toString());
		}
	} // marcarSaltoCompensacion()

	public void cumplirSaltoCompensacion2(String idSaltoCompensacionGrupo, String fechaCumplimiento,
			String motivoCumplimiento, String idInstitucion_Cumpli, String idTurno_Cumpli, String idGuardia_Cumpli,
			String idCalendarioGuardias_Cumpli) throws Exception {
		try {
			SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();

			scg.setIdSaltoCompensacionGrupo(idSaltoCompensacionGrupo);
			scg.setFechaCumplimiento(fechaCumplimiento);
			scg.setMotivoCumplimiento(motivoCumplimiento);
			scg.setIdInstitucion_Cumpli(idInstitucion_Cumpli);
			scg.setIdTurno_Cumpli(idTurno_Cumpli);
			scg.setIdGuardia_Cumpli(idGuardia_Cumpli);
			scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias_Cumpli);

			scsGuardiasturnoExtendsMapper.updateSaltosCompensacionesGrupo(scg, idInstitucion_Cumpli,
					usuModificacion1);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error actualizando SaltosCompensacionesGrupo: " + e;
		}
	}

	public void cumplirSaltoCompensacion23(String idSaltoCompensacionGrupo, String fechaCumplimiento,
			String motivoCumplimiento, GuardiasCalendarioItem guardiasCalendarioItem) throws Exception {
		try {
			String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
			String idTurno = guardiasCalendarioItem.getIdturno();
			String idGuardia = guardiasCalendarioItem.getIdguardia();
			String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
			
			Integer usuModificacion = Integer.valueOf(guardiasCalendarioItem.getUsumodificacion());
			
			SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();

			scg.setIdSaltoCompensacionGrupo(idSaltoCompensacionGrupo);
			scg.setFechaCumplimiento(fechaCumplimiento);
			scg.setMotivoCumplimiento(motivoCumplimiento);
			scg.setIdInstitucion_Cumpli(idInstitucion);
			scg.setIdTurno_Cumpli(idTurno);
			scg.setIdGuardia_Cumpli(idGuardia);
			scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias);

			scsGuardiasturnoExtendsMapper.updateSaltosCompensacionesGrupo(scg, idInstitucion,
					usuModificacion);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error actualizando SaltosCompensacionesGrupo: " + e;
		}
	}
	
	/**
	 * Si el letrado esta de baja seteamos la baja temporarl en el objeto
	 * LetradoGuardia, para luego insertar un salto
	 */
	private boolean isLetradoBajaTemporal(List< BajasTemporalesItem> hmBajasTemporales, ArrayList diasGuardia,
			LetradoInscripcionItem letradoGuardia) {
		boolean isLetradoBaja = false;
		BajasTemporalesItem bajaTemporal;
		try {
			if (hmBajasTemporales == null)
				return isLetradoBaja;

			for (int j = 0; j < diasGuardia.size(); j++) {
				String fechaPeriodo = (String) diasGuardia.get(j);
				List<Date> listaFechas = new ArrayList<>();

				listaFechas = hmBajasTemporales.stream().map(BajasTemporalesItem::getFechabt).collect(Collectors.toList());
				//String strDate =  new SimpleDateFormat("yyyy-MM-dd").format(date);  
				//List<BajasTemporalesItem> listaT= hmBajasTemporales.stream().filter(item -> item.getFechabt().toString().equals(fechaPeriodo)).collect(Collectors.toList());
				 listaFechas = listaFechas.stream().filter(item ->new SimpleDateFormat("dd/MM/yyyy").format(item).toString().equals(fechaPeriodo)).collect(Collectors.toList());
				if(!listaFechas.isEmpty()) {
					Date dateA = listaFechas.get(0);
					bajaTemporal =  hmBajasTemporales.stream().filter(item -> item.getFechabt().equals(dateA)).collect(Collectors.toList()).get(0);
					letradoGuardia.setBajaTemporal(bajaTemporal);
					isLetradoBaja = true;
					break;
				}
				/*if (hmBajasTemporales.containsKey(fechaPeriodo)) {
					bajaTemporal = hmBajasTemporales.get(fechaPeriodo);
					letradoGuardia.setBajaTemporal(bajaTemporal);
					isLetradoBaja = true;
					break;
				}*/
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comporbando si el letrado esta de baja seteamos la baja temporal: " + e;
		}

		return isLetradoBaja;
	}

	private void insertarNuevoSaltoBT(LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia, String motivo)
			throws Exception {
		try {
			String saltoOCompensacion = "S";
			ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
//		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones(
//		idInstitucion1, idTurno1, idGuardia1, idCalendarioGuardias1,
//		letradoGuardia.getIdpersona(), saltoOCompensacion, "sysdate");
			saltoCompensacion.setIdturno(idTurno1);
			saltoCompensacion.setIdguardia(idGuardia1);
			saltoCompensacion.setIdinstitucion(Short.parseShort(idInstitucion1.toString()));
			saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);
			saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
			saltoCompensacion.setIdcalendarioguardiascreacion(idCalendarioGuardias1);
			saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
			saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);

			// obteniendo motivo
			StringBuffer descripcion = new StringBuffer();
			BajasTemporalesItem bajaTemporalBean = letradoGuardia.getBajaTemporal();
			if (bajaTemporalBean.getTipo().equals(TIPO_COD_VACACION)) {
				// descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_VACACION));
			} else if (bajaTemporalBean.getTipo().equals(TIPO_COD_BAJA))
				// descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_BAJA));
				descripcion.append(" ");
			descripcion.append(bajaTemporalBean.getDescripcion());
			saltoCompensacion.setMotivos(descripcion + ": " + motivo);

			insertarSaltoCompensacion(saltoCompensacion);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error insertando nuevo salto BT: " + e;
		}
	}

	private void insertarNuevoSaltoBT3(GuardiasCalendarioItem guardiasCalendarioItem, LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia, String motivo)
			throws Exception {
		try {
			String saltoOCompensacion = "S";
			ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
//		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones(
//		idInstitucion1, idTurno1, idGuardia1, idCalendarioGuardias1,
//		letradoGuardia.getIdpersona(), saltoOCompensacion, "sysdate");
			saltoCompensacion.setIdturno(Integer.parseInt(guardiasCalendarioItem.getIdturno()));
			saltoCompensacion.setIdguardia(Integer.parseInt(guardiasCalendarioItem.getIdguardia()));
			saltoCompensacion.setIdinstitucion(Short.parseShort(guardiasCalendarioItem.getIdinstitucion()));
			saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);
			saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
			saltoCompensacion.setIdcalendarioguardiascreacion(Integer.parseInt(guardiasCalendarioItem.getIdcalendarioguardias()));
			saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
			saltoCompensacion.setIdcalendarioguardias(Integer.parseInt(guardiasCalendarioItem.getIdcalendarioguardias()));

			// obteniendo motivo
			StringBuffer descripcion = new StringBuffer();
			BajasTemporalesItem bajaTemporalBean = letradoGuardia.getBajaTemporal();
			if (bajaTemporalBean.getTipo().equals(TIPO_COD_VACACION)) {
				// descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_VACACION));
			} else if (bajaTemporalBean.getTipo().equals(TIPO_COD_BAJA))
				// descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_BAJA));
				descripcion.append(" ");
			descripcion.append(bajaTemporalBean.getDescripcion());
			saltoCompensacion.setMotivos(descripcion + ": " + motivo);
			saltoCompensacion.setUsumodificacion(Integer.valueOf(guardiasCalendarioItem.getUsumodificacion()));

			insertarSaltoCompensacion3(saltoCompensacion);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error insertando nuevo salto BT: " + e;
		}
	}
	
	@Override
	public void insertarSaltoCompensacion(ScsSaltoscompensaciones salto) throws Exception {
		try {
			salto.setUsumodificacion(usuModificacion1);
			salto.setFechamodificacion(new Date());
			java.util.Date date = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String fechaFormat = sdf.format(salto.getFecha());
			Long idSaltosTurno = Long
					.valueOf(getNuevoIdSaltosTurno(salto.getIdinstitucion().toString(), salto.getIdturno().toString()));
			salto.setIdsaltosturno(idSaltosTurno);
			scsSaltoscompensacionesExtendsMapper.insertManual(salto, fechaFormat);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error insertando nuevo salto/compensacion: " + e;
		}

	}
	
	//@Override
	public void insertarSaltoCompensacion3(ScsSaltoscompensaciones salto) throws Exception {
		try {
			salto.setFechamodificacion(new Date());
			java.util.Date date = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String fechaFormat = sdf.format(salto.getFecha());
			Long idSaltosTurno = Long
					.valueOf(getNuevoIdSaltosTurno(salto.getIdinstitucion().toString(), salto.getIdturno().toString()));
			salto.setIdsaltosturno(idSaltosTurno);
			scsSaltoscompensacionesExtendsMapper.insertManual(salto, fechaFormat);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error insertando nuevo salto/compensacion: " + e;
		}

	}

	/**
	 * Calcula un nuevo id IDSALTOSTURNOS para la tabla.ida
	 *
	 * @param String idInstitucionlidar
	 * @param String idTurno
	 * @return String con el nuevo identificador.
	 * @throws ClsExceptions
	 */
	public String getNuevoIdSaltosTurno(String idInstitucion, String idTurno) throws Exception {
		String nuevoId = "";
		try {
			nuevoId = scsGuardiasturnoExtendsMapper.getNuevoId(idInstitucion, idTurno);
			if (nuevoId == null)
				nuevoId = "1";
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo nuevo identificador de saltoscompensaciones: " + e;
			throw new Exception(e + ": Excepcion en ScsSaltosCompensacionesAdm.getNuevoIdSaltosTurno().");
		}
		return nuevoId;
	}

	public void crearSaltoBT(String idGrupoGuardia, String fecha, String motivo, String idInstitucion, String idTurno,
			String idGuardia, String idCalendarioGuardias, String idCalendarioGuardiasCreacion,
			BajasTemporalesItem btBean) throws Exception {
		try {
			// obtencion de la descripcion de la Baja temporal
			StringBuffer motivoBT = new StringBuffer();
			if (btBean.getTipo().equals(TIPO_COD_VACACION))
				motivoBT.append(TIPO_DESC_VACACION);
			else if (btBean.getTipo().equals(TIPO_COD_BAJA))
				motivoBT.append(TIPO_DESC_BAJA);
			motivoBT.append(" ");
			motivoBT.append(btBean.getDescripcion());
			motivo = motivoBT + ": " + motivo;
			Hashtable<String, String> hash = new Hashtable<String, String>();
			SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();

			scg.setIdSaltoCompensacionGrupo(getNuevoIdSaltoCompensacionGrupo());
			scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
			scg.setSaltoCompensacion("S");
			scg.setFecha(fecha);
			scg.setMotivo(motivo);
			scg.setIdInstitucion(idInstitucion);
			scg.setIdTurno(idTurno);
			scg.setIdGuardia(idGuardia);
			scg.setIdCalendarioGuardias(idCalendarioGuardias);
			scg.setFechaCumplimiento(fecha);
			scg.setMotivoCumplimiento(motivo);
			scg.setIdInstitucion_Cumpli(idInstitucion);
			scg.setIdTurno_Cumpli(idTurno);
			scg.setIdGuardia_Cumpli(idGuardia);
			scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias);

			if (idCalendarioGuardiasCreacion != null) {
				scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
			}

			scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion, usuModificacion1);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error guardando saltoscompensaciones: " + e;

		}

	}

	public void crearSaltoBT3(String idGrupoGuardia, String fecha, String motivo, GuardiasCalendarioItem guardiasCalendarioItem, String idCalendarioGuardiasCreacion,
			BajasTemporalesItem btBean) throws Exception {
		try {
			String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
			String idTurno = guardiasCalendarioItem.getIdturno();
			String idGuardia = guardiasCalendarioItem.getIdguardia();
			String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
			Integer usuModificacion = Integer.valueOf(guardiasCalendarioItem.getUsumodificacion());
			
			// obtencion de la descripcion de la Baja temporal
			StringBuffer motivoBT = new StringBuffer();
			if (btBean.getTipo().equals(TIPO_COD_VACACION))
				motivoBT.append(TIPO_DESC_VACACION);
			else if (btBean.getTipo().equals(TIPO_COD_BAJA))
				motivoBT.append(TIPO_DESC_BAJA);
			motivoBT.append(" ");
			motivoBT.append(btBean.getDescripcion());
			motivo = motivoBT + ": " + motivo;
			Hashtable<String, String> hash = new Hashtable<String, String>();
			SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();

			scg.setIdSaltoCompensacionGrupo(getNuevoIdSaltoCompensacionGrupo());
			scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
			scg.setSaltoCompensacion("S");
			scg.setFecha(fecha);
			scg.setMotivo(motivo);
			scg.setIdInstitucion(idInstitucion);
			scg.setIdTurno(idTurno);
			scg.setIdGuardia(idGuardia);
			scg.setIdCalendarioGuardias(idCalendarioGuardias);
			scg.setFechaCumplimiento(fecha);
			scg.setMotivoCumplimiento(motivo);
			scg.setIdInstitucion_Cumpli(idInstitucion);
			scg.setIdTurno_Cumpli(idTurno);
			scg.setIdGuardia_Cumpli(idGuardia);
			scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias);

			if (idCalendarioGuardiasCreacion != null) {
				scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
			}

			scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion, usuModificacion);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error guardando saltoscompensaciones: " + e;

		}

	}
	/**
	 * Calcula un nuevo idSaltoCompensacionGrupo para la tabla
	 *
	 * @return String con el nuevo identificador.
	 * @throws ClsExceptions
	 */
	public String getNuevoIdCalProg() throws Exception {
		String nuevoId = "";

		try {
			nuevoId = scsGuardiasturnoExtendsMapper.nextIdCalprog();
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo nuevo identificador calendarios programados: " + e;
			throw new Exception(e + ": Error al obtener nuevo id calendarios programados");
		}

		return nuevoId;
	} // getNuevoIdSaltoCompensacionGrupo()

	public String getNuevoIdSaltoCompensacionGrupo() throws Exception {
		String nuevoId = "";

		try {
			nuevoId = scsGuardiasturnoExtendsMapper.nextIdSaltoOComp();
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo nuevo identificador saltosCompensaciones: " + e;
			throw new Exception(e + ": Error al obtener nuevo idsaltocompensaciongrupo");
		}

		return nuevoId;
	} // getNuevoIdSaltoCompensacionGrupo()

	private boolean isIncompatible(LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia) throws Exception {
		if (hayIncompatibilidadGuardias(letradoGuardia, diasGuardia))
			return true;
		if (!haySeparacionDias(letradoGuardia, diasGuardia))
			return true;

		return false;
	}

	/**
	 * @param letrado
	 * @param periodoDiasGuardia
	 * @return true si NO hay incompatibilidad de guardias
	 * @throws ClsExceptions
	 */
	private boolean hayIncompatibilidadGuardias(LetradoInscripcionItem letrado, ArrayList periodoDiasGuardia)
			throws Exception {
		boolean salida = false;
		String idInstitucion = null;
		String idGuardia = null;
		String idTurno = null;
		String idPersona = null;

		try {
			if (letrado.getIdinstitucion() != null) {
				idInstitucion = letrado.getIdinstitucion().toString();
			}
			if (letrado.getIdGuardia() != null) {
				idGuardia = letrado.getIdGuardia().toString();
			}
			if (letrado.getIdTurno() != null) {
				idTurno = letrado.getIdTurno().toString();
			}
			if (letrado.getIdpersona() != null) {
				idPersona = letrado.getIdpersona().toString();
			}
			if (letrado.getInscripcionGuardia() != null) {
				idInstitucion = letrado.getInscripcionGuardia().getIdInstitucion().toString();
				idGuardia = letrado.getInscripcionGuardia().getIdGuardia().toString();
				idTurno = letrado.getInscripcionGuardia().getIdturno().toString();
				idPersona = letrado.getInscripcionGuardia().getIdPersona().toString();
			} else if (letrado.getInscripcionTurno() != null) {
				idInstitucion = letrado.getInscripcionTurno().getIdinstitucion().toString();
				idGuardia = null;
				idTurno = letrado.getInscripcionTurno().getIdturno().toString();
				idPersona = letrado.getInscripcionTurno().getIdpersona().toString();
			}

			salida = validarIncompatibilidadGuardia(idInstitucion, idTurno, idGuardia, periodoDiasGuardia, idPersona);
			
			if(salida) {
				String nombreLog = scsGuardiasturnoExtendsMapper.searchNombresGuardiaByIdturnoIdguardia(idInstitucion, idGuardia, idTurno);
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Encontrado Incompatibilidad en Guardias",nombreLog+" "
						+ letrado.getInscripcionGuardia().getApellido1() + ' ' + letrado.getInscripcionGuardia().getNombre() 
				+ " Fechas :"+ periodoDiasGuardia.get(0).toString() +" - " + periodoDiasGuardia.get(periodoDiasGuardia.size() - 1).toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si hay incompatibilidad: " + e;
			throw new Exception(e + ": Excepcion en noHayIncompatibilidadGuardias.");
		}
		return salida;
	}

	/**
	 * @param letrado
	 * @param periodoDiasGuardia
	 * @return true si hay suficiente separacion de dias de guardia
	 * @throws ClsExceptions
	 */
	private boolean haySeparacionDias(LetradoInscripcionItem letrado, ArrayList periodoDiasGuardia) throws Exception {
		boolean salida = false;

		try {

			Hashtable miHash = new Hashtable();
			String idInstitucion = null;
			String idGuardia = null;
			String idTurno = null;
			String idPersona = null;

			if (letrado.getIdinstitucion() != null) {
				idInstitucion = letrado.getIdinstitucion().toString();
			}
			if (letrado.getIdGuardia() != null) {
				idGuardia = letrado.getIdGuardia().toString();

			}
			if (letrado.getIdTurno() != null) {
				idTurno = letrado.getIdTurno().toString();

			}
			if (letrado.getIdpersona() != null) {
				idPersona = letrado.getIdpersona().toString();

			}
			if (letrado.getInscripcionGuardia() != null) {
				idInstitucion = letrado.getInscripcionGuardia().getIdInstitucion().toString();
				idGuardia = letrado.getInscripcionGuardia().getIdGuardia().toString();
				idTurno = letrado.getInscripcionGuardia().getIdturno().toString();
				idPersona = letrado.getInscripcionGuardia().getIdPersona().toString();
			} else if (letrado.getInscripcionTurno() != null) {
				idInstitucion = letrado.getInscripcionTurno().getIdinstitucion().toString();
				idGuardia = null;
				idTurno = letrado.getInscripcionTurno().getIdturno().toString();
				idPersona = letrado.getInscripcionTurno().getIdpersona().toString();
			}

			miHash.put("IDINSTITUCION", idInstitucion);
			miHash.put("IDGUARDIA", idGuardia);
			miHash.put("IDTURNO", idTurno);
			miHash.put("IDPERSONA", idPersona);

			if (periodoDiasGuardia.get(0) != null) {
				miHash.put("FECHAINICIO", (String) periodoDiasGuardia.get(0));
			}
			if (periodoDiasGuardia.get(periodoDiasGuardia.size() - 1) != null) {
				miHash.put("FECHAFIN", (String) periodoDiasGuardia.get(periodoDiasGuardia.size() - 1));
			}

			salida = validarSeparacionGuardias(miHash);
			if(!salida) {
				String nombreLog = scsGuardiasturnoExtendsMapper.searchNombresGuardiaByIdturnoIdguardia(idInstitucion, idGuardia, idTurno);
				//nombreLog = nombre del turno y guardia.
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Encontrado Incompatibilidad en DIAS", (String) periodoDiasGuardia.get(0) + " - " + (String) periodoDiasGuardia.get(periodoDiasGuardia.size() - 1)
				+ " En " + nombreLog);
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error comprobando si hay separación días: " + e;
			throw new Exception(e + ": Excepcion en haySeparacionDias.");
		}
		return salida;
	}

	public boolean validarSeparacionGuardias(Hashtable miHash) throws Exception {
		int numeroSepacionesIncorrectas = 0;
		boolean salida = false;

		try {
			String vsg = validacionSeparacionGuardias(miHash);
			if (vsg != null) {
				numeroSepacionesIncorrectas = Integer.parseInt(vsg);
				if (numeroSepacionesIncorrectas == 0)
					salida = true;
				else
					salida = false;
			} else {
				salida = false;
			}

		} catch (Exception e) {
			errorGeneracionCalendario = "Error validando la separación de guardias: " + e;
			throw e;
		}
		return salida;
	}

	/**
	 * Validacion: Consulta para buscar los dias de separacion entre guardias y
	 * validarlos Devuelve 0 si no encuentra separaciones incorrectas
	 * <p>
	 * La Hash debe tener los siguientes datos: -String "IDPERSONA" -String
	 * "IDINSTITUCION" -String "IDTURNO" -String "IDGUARDIA" -String "FECHAINICIO"
	 * del periodo de esta guardia -String "FECHAFIN" del periodo de esta guardia
	 * Parametros opcionales (claves opcionales): -String "FECHAINICIO_ORIGINAL" del
	 * periodo original para esta guardia. Puede no venir en la hash (solo se
	 * rellena en las permutas) -String "FECHAFIN_ORIGINAL" del periodo original
	 * para esta guardia. Puede no venir en la hash (solo se rellena en las
	 * permutas)
	 *
	 * @return String con la consulta SQL.
	 * @throws ClsExceptions
	 */
	public String validacionSeparacionGuardias(Hashtable miHash) throws Exception {
		String consulta = "";
		String idinstitucion = "", idguardia = "", idturno = "", idpersona = "";
		String fechaPeriodoUltimoDia = "", fechaPeriodoPrimerDia = "", fechaPeriodoPrimerDiaOriginal = "";
		StringBuffer sBuffer;
		String fechaMIN = "", fechaMAX = "";
		String total = "";

		try {
			idinstitucion = (String) miHash.get("IDINSTITUCION");
			idguardia = (String) miHash.get("IDGUARDIA");
			idturno = (String) miHash.get("IDTURNO");
			idpersona = (String) miHash.get("IDPERSONA");
			fechaPeriodoPrimerDia = (String) miHash.get("FECHAINICIO"); // Fecha del periodo de la primera guardia
			fechaPeriodoUltimoDia = (String) miHash.get("FECHAFIN"); // Fecha del periodo de la ultima guardia
			fechaPeriodoPrimerDiaOriginal = (String) miHash.get("FECHAINICIO_ORIGINAL"); // Fecha del periodo de la
			// primera guardia

			// Si tenemos fechas originales (venimos de permutas) es true.
			boolean esPermuta = miHash.containsKey("FECHAFIN_ORIGINAL") && miHash.containsKey("FECHAINICIO_ORIGINAL");

			// Consulto la maxima fecha inicio para el periodo en la cabecera de guardias:

//			fechaMAX =  scsGuardiasturnoExtendsMapper.maxFechaInicioPeriodoCabGuardia( idpersona, idinstitucion,  idturno,  idguardia,  esPermuta,  fechaPeriodoPrimerDiaOriginal,  fechaPeriodoPrimerDia);
//			String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
//			String NEW_FORMAT = "dd/MM/yyyy";
//			fechaMAX = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMAX);
			// Consulto la minima fecha inicio para el periodo en la cabecera de guardias:

//			fechaMIN = scsGuardiasturnoExtendsMapper.minFechaInicioPeriodoCabGuardia(idpersona, idinstitucion,  idturno,  idguardia,  esPermuta,  fechaPeriodoPrimerDiaOriginal,  fechaPeriodoUltimoDia);
//			fechaMIN = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMIN);
			total = scsGuardiasturnoExtendsMapper.diasSeparacionEntreGuardias(idpersona, idinstitucion, idturno,
					idguardia, esPermuta, fechaPeriodoPrimerDiaOriginal, fechaPeriodoPrimerDia, fechaPeriodoUltimoDia);

		} catch (Exception e) {
			errorGeneracionCalendario = "Error validando la separación de guardias: " + e;
			throw new Exception(e + ": Excepcion en ScsGuardiasColegiadoAdm.validacionIncompatibilidad(). Consulta SQL:"
					+ consulta);
		}
		return total;
	}

	// Comprueba si hay incompatibilidades de guardia en el calendario
	@Override
	public boolean validarIncompatibilidadGuardia(String idInstitucion, String idTurno, String idGuardia,
			ArrayList arrayDiasGuardia, String idPersona) {

		String vFechaFin;
		boolean encontrado = false;
		StringBuffer sql = null;
		int finderror = 0;
		try {
			Iterator iterDiasGuardia = arrayDiasGuardia.iterator();

			List<FechaSeparacionItem> fsList = new ArrayList<>();
			fsList = scsGuardiasturnoExtendsMapper.checkIncompatibilidadesCalendarioSinBucle(idPersona, idInstitucion,
					idTurno, idGuardia);
			
			if (fsList.size() != 0) {
				while (iterDiasGuardia.hasNext() && !encontrado) {
					for (int i = 0; i < fsList.size(); i++) {

						String fechaGuardia = (String) iterDiasGuardia.next();
						String NEW_FORMAT = "yyyy-MM-dd";
						String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss.S";
						String OLD_FORMAT2 = "dd/MM/yyyy";
						String fFin = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, fsList.get(i).getFechaFin());
						String fG = changeDateFormat(OLD_FORMAT2, NEW_FORMAT, fechaGuardia);
						long resta = substractDates(fFin, fG);
//					consulta.append (" and abs (gc.FECHAFIN - to_date('"+fechaGuardia+"', 'DD/MM/YYYY')) ");
//					consulta.append (" <= g_incompatibles.DIASSEPARACIONGUARDIAS ");

						if (!fsList.get(i).getFechaFin().isEmpty()
								&& Math.abs(resta) <= Long.parseLong(fsList.get(i).getDiasSeparacion())) {
							encontrado = true;
							break;
						}

					}

//				vFechaFin = scsGuardiasturnoExtendsMapper.checkIncompatibilidadesCalendario( idPersona, idInstitucion,  idTurno,  idGuardia,  fechaGuardia);

				}
			} else {
				encontrado = false;
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error validando la incompatibilidad de guardias: " + e;
			// encontrado = true;
		}
		return encontrado;
	}

	long substractDates(String date1, String date2) {
		// yyyy-MM-dd format
		LocalDate d1 = LocalDate.parse(date1, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate d2 = LocalDate.parse(date2, DateTimeFormatter.ISO_LOCAL_DATE);
		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		return diffDays;
	}

	/**
	 * A partir de una lista de letrados, obtiene un grupo
	 *
	 * @param alLetradosOrdenados
	 * @param punteroLetrado
	 * @return
	 * @throws ClsExceptions
	 */
	private ArrayList<LetradoInscripcionItem> getGrupoLetrados(List<LetradoInscripcionItem> alLetradosOrdenados,
			Puntero punteroLetrado) throws Exception {
		// Variables
		LetradoInscripcionItem letrado;
		ArrayList<LetradoInscripcionItem> grupoLetrados;
		int numeroGrupo;
		boolean nuevoGrupo;
		int fin;

		// controlando que la lista este rellena
		if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
			return null;

		// obteniendo primer componente del grupo
		letrado = alLetradosOrdenados.get(punteroLetrado.getValor());

		// avanzando hasta encontrar alguien que pertenezca a un grupo
		fin = punteroLetrado.getValor();
		while (letrado != null && letrado.getInscripcionGuardia() == null || letrado.getInscripcionGuardia().getNumeroGrupo() == null) {
			// obteniendo siguiente en la cola
			if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
				punteroLetrado.incValor();
			else
				punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio
			letrado = alLetradosOrdenados.get(punteroLetrado.getValor());

			if (fin == punteroLetrado.getValor())
				break;
		}
		if (letrado == null) // no se encontro a nadie perteneciente a un grupo
			return null;
		else if (letrado.getInscripcionGuardia().getNumeroGrupo() == null)
			return null;
		else {
			numeroGrupo = Integer.parseInt(letrado.getInscripcionGuardia().getNumeroGrupo());
		}
		grupoLetrados = new ArrayList<LetradoInscripcionItem>();
		nuevoGrupo = false;
		fin = punteroLetrado.getValor();
		do {
			// anyadiendo componente al grupo
			grupoLetrados.add(letrado);

			// obteniendo siguiente en la cola
			if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
				punteroLetrado.incValor();
			else
				punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio
			letrado = alLetradosOrdenados.get(punteroLetrado.getValor());

			if (letrado == null)
				nuevoGrupo = true;
			else if (letrado.getInscripcionGuardia().getNumeroGrupo() == null || Integer.parseInt(letrado.getInscripcionGuardia().getNumeroGrupo()) != numeroGrupo)
				nuevoGrupo = true;
		} while (!nuevoGrupo && fin != punteroLetrado.getValor());

		if (grupoLetrados.size() == 0)
			return null;
		else
			return grupoLetrados;
	} // getGrupoLetrados()

	private boolean comprobarRestriccionesLetradoCola(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales, boolean ficheroCarga)
			throws Exception {

		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getIdPersona())), diasGuardia, letradoGuardia)) {
			// log.addLog(new String[] { "Encontrado Baja temporal",
			// letradoGuardia.toString(), diasGuardia.toString() });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' + diasGuardia.toString());
			if (!ficheroCarga) {
				if (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() == null || letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals("")) {
					// ... crear un salto cumplido (como si fuera un log)
					try {
						insertarNuevoSaltoBT(letradoGuardia, diasGuardia,
								"Cumplido en dia de guardia " + diasGuardia.get(0));
					} catch (Exception e) {
						errorGeneracionCalendario = "Error insertando nuevo salto BT: " + e;
					}
				} else {
					// ... crear un salto cumplido (como si fuera un log)
					try {
						crearSaltoBT(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia(), diasGuardia.get(0),
								"Cumplido en dia de guardia " + diasGuardia.get(0), idInstitucion1.toString(),
								idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString(),
								idCalendarioGuardias1.toString(), letradoGuardia.getBajaTemporal());
					} catch (Exception e) {
						errorGeneracionCalendario = "Error creando nuevo salto BT: " + e;
					}
				}
			}
			return false; // y no seleccionar
		}

		// si tiene saltos, ...
		List<LetradoInscripcionItem> alSaltos =  hmPersonasConSaltos.get(Long.valueOf( letradoGuardia.getInscripcionGuardia().getIdPersona()));
		if (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() == null || letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals("")) {
			if (alSaltos != null && !alSaltos.isEmpty()) {
				// log.addLog(new String[] { "Encontrado Salto", letradoGuardia.toString() });
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Salto", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Salto" + letradoGuardia.toString());
				if (!controlVacioSC) {
					// ... compensar uno
//					String motivo = ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":";
					String motivo = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
							+ "por generación calendario";
					try {
						cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "S", motivo);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error cumpliendo salto: " + e;
					}
					alSaltos.remove(0);
					if (alSaltos.size() == 0)
						hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());

				}else {
					controlVacioSC = false;
					alSaltos.remove(0);
					if (alSaltos.size() == 0)
						hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());
				}
				return false; // y no seleccionar
			}

		} else if ((alSaltos = hmPersonasConSaltos.get(new Long(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia()))) != null) {
			// log.addLog(new String[] { "Encontrado Salto de grupo" });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Salto de grupo", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Salto de grupo");
			if (!ficheroCarga) {
				// ... compensar uno
//				String motivo = ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":";
				String motivo = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				try {
					cumplirSaltoCompensacion2(alSaltos.get(0).getIdSaltoCompensacionGrupo(), diasGuardia.get(0), motivo,
							idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(),
							idCalendarioGuardias1.toString());
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo salto: " + e;
				}
				alSaltos.remove(0);
				if (alSaltos.size() == 0)
					hmPersonasConSaltos.remove(new Long(letradoGuardia.getInscripcionGuardia().getNumeroGrupo()));
			}
			return false; // y no seleccionar
		}

		// si hay incompatibilidad, ...
		if (isIncompatible(letradoGuardia, diasGuardia)) {
			// log.addLog(new String[] { "Encontrado Incompatibilidad",
			// letradoGuardia.toString(), diasGuardia.toString() });
			String nombre = "";
			String ap1 = "";
			String ap2 = "";
			if (letradoGuardia.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog = new HashMap();
				if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
					ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
				if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
					ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
				if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
					nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
				mapLog.put("*Encontrado Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
			} else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog = new HashMap();
				if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
					ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
				if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
					ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
				if (letradoGuardia.getInscripcionTurno().getNombre() != null)
					nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
				mapLog.put("*Encontrado Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
			}
			if (!ficheroCarga) {
				java.util.Date date = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
				String motivo = "Registro automático (" + sdf.format(new Date()) + ") por incompatibilidad en ";
				if (diasGuardia.size() > 1) {
					motivo += "días de guardia: ";
					for (String diaGuardia : diasGuardia) {
						motivo += diaGuardia + ", ";
					}
					motivo = motivo.substring(0, motivo.length() - 2);
				} else {
					motivo += "día de guardia: " + diasGuardia.get(0);
				}

				// Generación automática de calendario de guardia: se crea una compensación
				// cuando el colegiado no puede seleccionarse por existir una incompatibilidad
				// (no se crea por estar de baja temporal).
				// Indicar en el MOTIVO: (Registro automático) dd/mm/yyyy (fecha de la
				// operación) compensación por incompatibilidad en día de guardia: dd/mm/yyyy
				// (fecha de la guardia).
				// Indicar en la fecha de compensación por cambio de letrado la fecha de
				// inserción del cambio (horas, minutos y segundos).
				motivo = "(Registro automático) " + sdf.format(new Date())
						+ "compensación por incompatibilidad en día de guardia: " + diasGuardia.get(0);
				if (letradoGuardia.getInscripcionGuardia().getNumeroGrupo() == null || letradoGuardia.getInscripcionGuardia().getNumeroGrupo().equals("")) {
					// ... crear compensacion
					// BNS INC_07349_SIGA
					try {
						
						insertarNuevoSaltoCompensacion(letradoGuardia, diasGuardia, "C", motivo);
						Map<String, Object> mapLog3 = new HashMap();
						mapLog3.put("*Insertado Nueva Compensación", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error insertando nueva compensación: " + e;
					}
				} else {
					// ... crear compensacion
					try {
						LOGGER.info("Se creara una compensacion");
						crearSaltoCompensacion(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia(), diasGuardia.get(0), motivo,
								idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(),
								idCalendarioGuardias1.toString(), "C", null);
						Map<String, Object> mapLog3 = new HashMap();
						mapLog3.put("*Creado Nueva Compensación", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
					} catch (Exception e) {
						LOGGER.info("ERROR AL CREAR COMPENSACION");
						errorGeneracionCalendario = "Error creando nueva compensación: " + e;
					}
				}
			}

			return false; // y no seleccionar
		}

		// una vez comprobado todo, se selecciona a este letrado
		// log.addLog(new String[] { "Letrado ok", letradoGuardia.toString() });
		String nombre = "";
		String ap1 = "";
		String ap2 = "";
		if (letradoGuardia.getInscripcionGuardia() != null) {

			Map<String, Object> mapLog = new HashMap();

			if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
				ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
			if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
				ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
			if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
				nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
			mapLog.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
		} else if (letradoGuardia.getInscripcionTurno() != null) {
			Map<String, Object> mapLog = new HashMap();
			if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
				ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
			if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
				ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
			if (letradoGuardia.getInscripcionTurno().getNombre() != null)
				nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
			mapLog.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
		}
		return true;
	} // comprobarRestriccionesLetradoCola()
	
	private boolean comprobarRestriccionesLetradoCola3(GuardiasCalendarioItem guardiasCalendarioItem, LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales, boolean ficheroCarga, boolean esGrupo)
			throws Exception {
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(Long.parseLong( letradoGuardia.getInscripcionGuardia().getIdPersona())), diasGuardia, letradoGuardia)) {
			// log.addLog(new String[] { "Encontrado Baja temporal",
			// letradoGuardia.toString(), diasGuardia.toString() });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' + diasGuardia.toString());
			if (!ficheroCarga) {
				if (!esGrupo) {
					// ... crear un salto cumplido (como si fuera un log)
					try {
						insertarNuevoSaltoBT3(guardiasCalendarioItem, letradoGuardia, diasGuardia,
								"Cumplido en dia de guardia " + diasGuardia.get(0));
					} catch (Exception e) {
						errorGeneracionCalendario = "Error insertando nuevo salto BT: " + e;
					}
				} else {
					// ... crear un salto cumplido (como si fuera un log)
					try {
						crearSaltoBT3(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia(), diasGuardia.get(0),
								"Cumplido en dia de guardia " + diasGuardia.get(0), guardiasCalendarioItem,
								idCalendarioGuardias, letradoGuardia.getBajaTemporal());
					} catch (Exception e) {
						errorGeneracionCalendario = "Error creando nuevo salto BT: " + e;
					}
				}
			}
			return false; // y no seleccionar
		}

		// si tiene saltos, ...
		List<LetradoInscripcionItem> alSaltos =  hmPersonasConSaltos.get(Long.valueOf( letradoGuardia.getInscripcionGuardia().getIdPersona()));
		if (!esGrupo) {//Si es distinto de grupo, el campo numeroGrupo no se tiene en cuenta.
			if (alSaltos != null && !alSaltos.isEmpty()) {
				// log.addLog(new String[] { "Encontrado Salto", letradoGuardia.toString() });
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Salto", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Salto" + letradoGuardia.toString());
				if (!controlVacioSC) {
					// ... compensar uno
//					String motivo = ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":";
					String motivo = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
							+ "por generación calendario";
					try {
						cumplirSaltoCompensacion3(guardiasCalendarioItem, letradoGuardia, diasGuardia, "S", motivo);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error cumpliendo salto: " + e;
					}
					alSaltos.remove(0);
					if (alSaltos.size() == 0)
						hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());

				}else {
					controlVacioSC = false;
					alSaltos.remove(0);
					if (alSaltos.size() == 0)
						hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());
				}
				return false; // y no seleccionar
			}

		} else if (letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia() != null && (alSaltos = hmPersonasConSaltos.get(new Long(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia()))) != null) {
			// log.addLog(new String[] { "Encontrado Salto de grupo" });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Salto de grupo", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Salto de grupo");
			if (!ficheroCarga) {
				// ... compensar uno
//				String motivo = ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":";
				String motivo = "(Registro Automático) Utilizando salto en día de Guardia: " + diasGuardia.get(0)
						+ "por generación calendario";
				try {
					cumplirSaltoCompensacion23(alSaltos.get(0).getIdSaltoCompensacionGrupo(), diasGuardia.get(0), motivo,
							guardiasCalendarioItem);
				} catch (Exception e) {
					errorGeneracionCalendario = "Error cumpliendo salto: " + e;
				}
				alSaltos.remove(0);
				if (alSaltos.size() == 0)
					hmPersonasConSaltos.remove(new Long(letradoGuardia.getInscripcionGuardia().getNumeroGrupo()));
			}
			return false; // y no seleccionar
		}

		// si hay incompatibilidad, ...
		if (isIncompatible(letradoGuardia, diasGuardia)) {
			// log.addLog(new String[] { "Encontrado Incompatibilidad",
			// letradoGuardia.toString(), diasGuardia.toString() });
			String nombre = "";
			String ap1 = "";
			String ap2 = "";
			if (letradoGuardia.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog = new HashMap();
				if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
					ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
				if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
					ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
				if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
					nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
				mapLog.put("*Encontrado Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
			} else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog = new HashMap();
				if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
					ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
				if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
					ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
				if (letradoGuardia.getInscripcionTurno().getNombre() != null)
					nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
				mapLog.put("*Encontrado Incompatibilidad ", ap1 + " " + ap2 + ", " + nombre);
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + ap1 + " " + ap2 + ", " + nombre);
			}
			if (!ficheroCarga) {
				java.util.Date date = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
				String motivo = "Registro automático (" + sdf.format(new Date()) + ") por incompatibilidad en ";
				if (diasGuardia.size() > 1) {
					motivo += "días de guardia: ";
					for (String diaGuardia : diasGuardia) {
						motivo += diaGuardia + ", ";
					}
					motivo = motivo.substring(0, motivo.length() - 2);
				} else {
					motivo += "día de guardia: " + diasGuardia.get(0);
				}

				// Generación automática de calendario de guardia: se crea una compensación
				// cuando el colegiado no puede seleccionarse por existir una incompatibilidad
				// (no se crea por estar de baja temporal).
				// Indicar en el MOTIVO: (Registro automático) dd/mm/yyyy (fecha de la
				// operación) compensación por incompatibilidad en día de guardia: dd/mm/yyyy
				// (fecha de la guardia).
				// Indicar en la fecha de compensación por cambio de letrado la fecha de
				// inserción del cambio (horas, minutos y segundos).
				motivo = "(Registro automático) " + sdf.format(new Date())
						+ "compensación por incompatibilidad en día de guardia: " + diasGuardia.get(0);
				if (!esGrupo) {
					// ... crear compensacion
					// BNS INC_07349_SIGA
					try {
						
						insertarNuevoSaltoCompensacion3(guardiasCalendarioItem, letradoGuardia, diasGuardia, "C", motivo);
						Map<String, Object> mapLog3 = new HashMap();
						mapLog3.put("*Insertado Nueva Compensación", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error insertando nueva compensación: " + e;
					}
				} else {
					// ... crear compensacion
					try {
						LOGGER.info("Se creara una compensacion");
						crearSaltoCompensacion3(letradoGuardia.getInscripcionGuardia().getIdGrupoGuardia(), diasGuardia.get(0), motivo,
								guardiasCalendarioItem, "C", null);
						Map<String, Object> mapLog3 = new HashMap();
						mapLog3.put("*Creado Nueva Compensación", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
					} catch (Exception e) {
						LOGGER.info("ERROR AL CREAR COMPENSACION");
						errorGeneracionCalendario = "Error creando nueva compensación: " + e;
					}
				}
			}

			return false; // y no seleccionar
		}

		// una vez comprobado todo, se selecciona a este letrado
		// log.addLog(new String[] { "Letrado ok", letradoGuardia.toString() });
		String nombre = "";
		String ap1 = "";
		String ap2 = "";
		if (letradoGuardia.getInscripcionGuardia() != null) {

			Map<String, Object> mapLog = new HashMap();

			if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
				ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
			if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
				ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
			if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
				nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
			mapLog.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
		} else if (letradoGuardia.getInscripcionTurno() != null) {
			Map<String, Object> mapLog = new HashMap();
			if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
				ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
			if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
				ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
			if (letradoGuardia.getInscripcionTurno().getNombre() != null)
				nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
			mapLog.put("*Letrado ok ", ap1 + " " + ap2 + ", " + nombre);
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " + ap1 + " " + ap2 + ", " + nombre);
		}
		return true;
	} // comprobarRestriccionesLetradoCola()

	private void insertarNuevoSaltoCompensacion(LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia,
			String saltoOCompensacion, String motivo) throws Exception {
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
		saltoCompensacion.setIdinstitucion(idInstitucion1.shortValue());
		saltoCompensacion.setIdturno(idTurno1);
		saltoCompensacion.setIdguardia(idGuardia1);
		saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);
		// saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
		if (letradoGuardia.getInscripcionGuardia() != null)
			saltoCompensacion.setIdpersona(new Long(letradoGuardia.getInscripcionGuardia().getIdPersona()));
		if (letradoGuardia.getInscripcionTurno() != null)
			saltoCompensacion.setIdpersona(new Long(letradoGuardia.getInscripcionTurno().getIdpersona()));
		saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);
		saltoCompensacion.setFecha(new Date());
		saltoCompensacion.setIdcalendarioguardiascreacion(idCalendarioGuardias1);
		saltoCompensacion.setMotivos(motivo);
		insertarSaltoCompensacion(saltoCompensacion);
	}
	
	private void insertarNuevoSaltoCompensacion3(GuardiasCalendarioItem guardiasCalendarioItem, LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia,
			String saltoOCompensacion, String motivo) throws Exception {
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
		saltoCompensacion.setIdinstitucion(Short.parseShort(guardiasCalendarioItem.getIdinstitucion()));
		saltoCompensacion.setIdturno(Integer.parseInt(guardiasCalendarioItem.getIdturno()));
		saltoCompensacion.setIdguardia(Integer.parseInt(guardiasCalendarioItem.getIdguardia()));
		saltoCompensacion.setIdcalendarioguardias(Integer.parseInt(guardiasCalendarioItem.getIdcalendarioguardias()));
		// saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
		if (letradoGuardia.getInscripcionGuardia() != null)
			saltoCompensacion.setIdpersona(new Long(letradoGuardia.getInscripcionGuardia().getIdPersona()));
		if (letradoGuardia.getInscripcionTurno() != null)
			saltoCompensacion.setIdpersona(new Long(letradoGuardia.getInscripcionTurno().getIdpersona()));
		saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);
		saltoCompensacion.setFecha(new Date());
		saltoCompensacion.setIdcalendarioguardiascreacion(Integer.parseInt(guardiasCalendarioItem.getIdcalendarioguardias()));
		saltoCompensacion.setMotivos(motivo);
		saltoCompensacion.setUsumodificacion(Integer.valueOf(guardiasCalendarioItem.getUsumodificacion()));
		insertarSaltoCompensacion3(saltoCompensacion);
	}

	public void crearSaltoCompensacion(String idGrupoGuardia, String fecha, String motivo, String idInstitucion,
			String idTurno, String idGuardia, String idCalendarioGuardias, String saltoCompensacion,
			String idCalendarioGuardiasCreacion) throws Exception {
		SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();
		
		if(idGrupoGuardia!=null) {
			scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
		}
		scg.setIdSaltoCompensacionGrupo(getNuevoIdSaltoCompensacionGrupo());
		scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
		scg.setSaltoCompensacion(saltoCompensacion);
		scg.setFecha(fecha);
		scg.setMotivo(motivo);
		scg.setIdInstitucion(idInstitucion);
		scg.setIdTurno(idTurno);
		scg.setIdGuardia(idGuardia);
		scg.setIdCalendarioGuardias(idCalendarioGuardias);
		scg.setFechaCumplimiento(null);
		scg.setMotivoCumplimiento(motivo);
		scg.setIdInstitucion_Cumpli(idInstitucion);
		scg.setIdTurno_Cumpli(idTurno);
		scg.setIdGuardia_Cumpli(idGuardia);
		scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias);

		if (idCalendarioGuardiasCreacion != null) {
			scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
		}

		scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion, usuModificacion1);
	}
	
	public void crearSaltoCompensacion3(String idGrupoGuardia, String fecha, String motivo, GuardiasCalendarioItem guardiasCalendarioItem, String saltoCompensacion,
			String idCalendarioGuardiasCreacion) throws Exception {
		SaltoCompGuardiaGrupoItem scg = new SaltoCompGuardiaGrupoItem();
		
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		Integer usuModificacion = Integer.valueOf(guardiasCalendarioItem.getUsumodificacion());
		
		if(idGrupoGuardia!=null) {
			scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
		}
		scg.setIdSaltoCompensacionGrupo(getNuevoIdSaltoCompensacionGrupo());
		if(idGrupoGuardia != null )scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
		scg.setSaltoCompensacion(saltoCompensacion);
		scg.setFecha(fecha);
		scg.setMotivo(motivo);
		scg.setIdInstitucion(idInstitucion);
		scg.setIdTurno(idTurno);
		scg.setIdGuardia(idGuardia);
		scg.setIdCalendarioGuardias(idCalendarioGuardias);
		scg.setFechaCumplimiento(null);
		scg.setMotivoCumplimiento(motivo);
		scg.setIdInstitucion_Cumpli(idInstitucion);
		scg.setIdTurno_Cumpli(idTurno);
		scg.setIdGuardia_Cumpli(idGuardia);
		scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias);

		if (idCalendarioGuardiasCreacion != null) {
			scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
		}
		try {
			String res = scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion, usuModificacion);
		
		} catch (Exception e) {
			LOGGER.info(e.getCause());
		}
		//scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion, usuModificacion1);
	}

	/**
	 * Modifica el orden de la cola de guardia de letrados incrementando su orden en
	 * una constante (3000). Este metodo es necesario para no producir un error de
	 * restricción unica de orden en bbdd
	 */
	public void modifyOrderGruposLetrados(int idGrupoGuardia) {
		boolean repetido = false;
		Hashtable hash = new Hashtable();
		StringBuffer sql = new StringBuffer();
		int orden = 0;
		ArrayList<Hashtable<String, String>> grupo = new ArrayList<Hashtable<String, String>>();
		List<GrupoGuardiaRowItem>  registro = scsGuardiasturnoExtendsMapper.getGrupoData(String.valueOf(idGrupoGuardia));

		try {
			if (registro != null) {
				for(GrupoGuardiaRowItem item : registro) {
					orden = item.getOrden() + new Integer(30000);
					updateOrderBBDD(item.getIdGrupoGuardia(), orden, item.getIdGrupoGuardiaColegiado());
				}
			}

		} catch (Exception e) {
			errorGeneracionCalendario = "Error actualizando orden de la cola de guardia de letrados incrementando su orden en\r\n"
					+ "	 * una constante (3000): " + e;
			// throw new ClsExceptions (e,
			// "Error al recuperar la posicion del ultimo letrado");
		}
	}

	/**
	 * Actualiza en bbdd el orden modificado necesario para el algoritmo de rotación
	 * de guardias
	 */
	public void updateOrderBBDD(int idGrupoGuardia, int orden, int idGrupoColegiado) {
		Hashtable<String, String> hash = new Hashtable<String, String>();
		Hashtable<String, String> hashDataOld;
		try {
			ScsGrupoguardiacolegiado scsGrupoguardiacolegiado = new ScsGrupoguardiacolegiado();
			scsGrupoguardiacolegiado.setIdgrupoguardia((long)idGrupoGuardia);
			scsGrupoguardiacolegiado.setIdgrupoguardiacolegiado(Long.valueOf(idGrupoColegiado));
			scsGrupoguardiacolegiado.setOrden(orden);

			scsGrupoguardiacolegiadoExtendsMapper.updateGrupoGuardiaOrden(scsGrupoguardiacolegiado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Genera las cabeceras de guardias del calendario, a partir de la lista de
	 * dias/periodos Cuando la guardia no es por grupos Nota: previamente se ha
	 * calculado mediante el metodo "calcularMatrizPeriodosDiasGuardia()"
	 */
	public void calcularMatrizLetradosGuardia(List lDiasASeparar) throws Exception {
		
		LOGGER.info("INICIO generarCalendarioAsync.calcularMatrizLetradosGuardia:");
		
		// Variables generales
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con
		// letrado
		int numeroLetradosGuardia = 0; // Numero de letrados necesarios para cada periodo
		HashMap<Long, List<BajasTemporalesItem>> hmBajasTemporales = null;
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos = null; // Lista de saltos
		List<LetradoInscripcionItem> alCompensaciones = null; // Lista de compensaciones
		List<LetradoInscripcionItem> alLetradosOrdenados = null; // Cola de letrados en la guardia
		Puntero punteroListaLetrados;
		int posicion;
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		LetradoInscripcionItem unLetrado;

		try {
			// obteniendo bajas temporales por letrado
			String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0)).get(0);
			ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1
					.get(arrayPeriodosDiasGuardiaSJCS1.size() - 1);
			String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size() - 1);
			try {
				hmBajasTemporales = getLetradosDiasBajaTemporal(idInstitucion1, idTurno1, idGuardia1, primerDia,
						ultimoDia);
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error cobteniendo bajas temporales: " + e;
			}

			// obteniendo numero de letrados necesarios para cada periodo
			try {
				numeroLetradosGuardia = beanGuardiasTurno1.getNumeroLetradosGuardia().intValue();
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo numero de letrados necesarios para cada periodo: " + e;
			}

			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrados por dia ", Integer.toString(numeroLetradosGuardia));
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrados por dia " + Integer.toString(numeroLetradosGuardia));
			// log.addLog(new String[] {"Letrados por dia",
			// Integer.toString(numeroLetradosGuardia)});
			if (numeroLetradosGuardia > 0) {

				// Si hay guardias vinculadas, hay que mirar dos periodos a la vez,
				// por lo que se comienza con uno ya en memoria
				int inicial;
				primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0);
				segundoPeriodo = null;
				if (calendariosVinculados1 == null)
					inicial = 0;
				else {
					Map<String, Object> mapLog2 = new HashMap();
					// log.addLog(new String[] {"Guardias vinculadas",
					// calendariosVinculados1.toString()});
					mapLog2.put("*Guardias vinculadas ", calendariosVinculados1.toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog2);
					LOGGER.info("*Guardias vinculadas " + calendariosVinculados1.toString());
					inicial = 1;
				}
				// Para cada dia o conjunto de dias:
				for (int i = inicial; i < arrayPeriodosDiasGuardiaSJCS1.size(); i++) {
					posicion = 1;
					// obteniendo conjunto de dias
					// Nota: cada periodo es un arraylist de fechas (String en formato de fecha
					// corto DD/MM/YYYY)
					if (calendariosVinculados1 == null) {
						diasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(i);
					} // Si hay guardias vinculadas, hay que mirar dos periodos a la vez
					else {
						segundoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(i);
						diasGuardia = new ArrayList<String>();
						diasGuardia.addAll(primerPeriodo);
						diasGuardia.addAll(segundoPeriodo);
					}
//					log.addLog(new String[] {" ", " "});
//					log.addLog(new String[] {"Dias", diasGuardia.toString()});

					Map<String, Object> mapLog1 = new HashMap();
					mapLog1.put("*Dias", diasGuardia.toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog1);
					LOGGER.info("*Dias " + diasGuardia.toString());

					// obteniendo cola de letrados
					punteroListaLetrados = new Puntero();
					try {//TODO: (L) Aqui podría insertar letrado de Cargas Masivas(?) - No se analizan incompatibilidades, obtiene la cola de guardias para new Date
						alLetradosOrdenados = getColaGuardia(idInstitucion1, idTurno1, idGuardia1,
								(String) diasGuardia.get(0), (String) diasGuardia.get(diasGuardia.size() - 1));
					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo la cola de letrados ordenados: " + e;
						controlError++;
					}
					// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
					Map<String, Object> mapLog2 = new HashMap();
					if (alLetradosOrdenados != null && !alLetradosOrdenados.isEmpty()) {
						int auxIndex = 0;
						//alLetradosOrdenados.forEach(letrado -> {
						for(LetradoInscripcionItem letrado : alLetradosOrdenados) { //(L) Solo imprime
							String nombre = "";
							String ap1 = "";
							String ap2 = "";
							String numCol = "";
							if (letrado.getInscripcionGuardia() != null) {
								if (letrado.getInscripcionGuardia().getApellido2() != null)
									ap2 = letrado.getInscripcionGuardia().getApellido2().toString();
								if (letrado.getInscripcionGuardia().getApellido1() != null)
									ap1 = letrado.getInscripcionGuardia().getApellido1().toString();
								if (letrado.getInscripcionGuardia().getNombre() != null)
									nombre = letrado.getInscripcionGuardia().getNombre().toString();
								if(letrado.getInscripcionGuardia().getnColegiado() != null)
									numCol = letrado.getInscripcionGuardia().getnColegiado().toString();
								Map<String, Object> mapLog3 = new HashMap();
								mapLog3.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("*Colae " + ap1 + " " + ap2 + ", " + nombre);
							} else if (letrado.getInscripcionTurno() != null) {
								if (letrado.getInscripcionTurno().getApellidos2() != null)
									ap2 = letrado.getInscripcionTurno().getApellidos2().toString();
								if (letrado.getInscripcionTurno().getApellidos1() != null)
									ap1 = letrado.getInscripcionTurno().getApellidos1().toString();
								if (letrado.getInscripcionTurno().getNombre() != null)
									nombre = letrado.getInscripcionTurno().getNombre().toString();
								if (letrado.getInscripcionTurno().getNumerocolegiado() != null)
									numCol = letrado.getInscripcionTurno().getNumerocolegiado().toString();
								mapLog2.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog2);
								LOGGER.info("*Colaia " + ap1 + " " + ap2 + ", " + nombre);

							}
							auxIndex++;
						};
						
					} else {
						mapLog2.put("*Cola vacía", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Cola vacía");
						controlError++;
					}

					if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//						throw new Exception("No existe cola de letrados de guardia");
						LOGGER.error("No existe cola de letrados de guardia");

					// obteniendo las compensaciones. Se obtienen dentro de este
					// bucle, ya que si hay incompatibilidades se añade una compensacion
					try {
						alCompensaciones = getCompensaciones(idInstitucion1, idTurno1, idGuardia1,
								(String) diasGuardia.get(0));
					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo las compensaciones: " + e;
						controlError++;
					}
//					log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
					Map<String, Object> mapLog3 = new HashMap();
					if (alCompensaciones != null && !alCompensaciones.isEmpty()) {
						//alCompensaciones.forEach(compensacion -> {
							int indexCompensacion = 0;
							for(LetradoInscripcionItem compensacion : alCompensaciones) {
							Map<String, Object> mapLog3A = new HashMap();
							String nombre = "";
							String ap1 = "";
							String ap2 = "";
							if (compensacion.getInscripcionGuardia() != null) {
								if (compensacion.getInscripcionGuardia().getApellido2() != null)
									ap2 = compensacion.getInscripcionGuardia().getApellido2().toString();
								if (compensacion.getInscripcionGuardia().getApellido1() != null)
									ap1 = compensacion.getInscripcionGuardia().getApellido1().toString();
								if (compensacion.getInscripcionGuardia().getNombre() != null)
									nombre = compensacion.getInscripcionGuardia().getNombre().toString();
								mapLog3A.put("Lista Compensaciones-" + indexCompensacion, ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3A);
								LOGGER.info("Lista Compensaciones-" + ap1 + " " + ap2 + ", " + nombre);
							} else if (compensacion.getInscripcionTurno() != null) {
								mapLog3A.put("*Compensaciones ",
										compensacion.getInscripcionTurno().getApellidos1() + " "
												+ compensacion.getInscripcionTurno().getApellidos2() + ", "
												+ compensacion.getInscripcionTurno().getNombre());
								if (compensacion.getInscripcionTurno().getApellidos2() != null)
									ap2 = compensacion.getInscripcionTurno().getApellidos2().toString();
								if (compensacion.getInscripcionTurno().getApellidos1() != null)
									ap1 = compensacion.getInscripcionTurno().getApellidos1().toString();
								if (compensacion.getInscripcionTurno().getNombre() != null)
									nombre = compensacion.getInscripcionTurno().getNombre().toString();
								mapLog3A.put("Lista Compensaciones-" +indexCompensacion, ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3A);
								LOGGER.info("Lista Compensaciones-" + ap1 + " " + ap2 + ", " + nombre);
							}
							indexCompensacion++;
						};
					} else {
						mapLog3.put("*No existen compensaciones-", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
						LOGGER.info("*No existen compensaciones");
						//controlError++;
					}

					// obteniendo saltos
					try {
						hmPersonasConSaltos = getSaltos(idInstitucion1, idTurno1, idGuardia1);
					} catch (Exception e) {
						controlError++;
						errorGeneracionCalendario = "Error obteniendo saltos: " + e;
					}

//					log.addLog(new String[] {"Saltos", hmPersonasConSaltos.toString()});
					Map<String, Object> mapLog4 = new HashMap();
					if (hmPersonasConSaltos != null && !hmPersonasConSaltos.isEmpty()) {
						int indexAux = 0;
						for (Map.Entry<Long, ArrayList<LetradoInscripcionItem>> personasConSaltos : hmPersonasConSaltos.entrySet()) {
							for(LetradoInscripcionItem pSalto : personasConSaltos.getValue()) {
							//personasConSaltos.getValue().forEach(pSalto -> {
								
								String nombre = "";
								String ap1 = "";
								String ap2 = "";
								if (pSalto.getInscripcionGuardia() != null) {
									if (pSalto.getInscripcionGuardia().getApellido2() != null)
										ap2 = pSalto.getInscripcionGuardia().getApellido2().toString();
									if (pSalto.getInscripcionGuardia().getApellido1() != null)
										ap1 = pSalto.getInscripcionGuardia().getApellido1().toString();
									if (pSalto.getInscripcionGuardia().getNombre() != null)
										nombre = pSalto.getInscripcionGuardia().getNombre().toString();
									mapLog3.put("*Saltos ", ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + ap1 + " " + ap2 + ", " + nombre);
								} else if (pSalto.getInscripcionTurno() != null) {
									if (pSalto.getInscripcionTurno().getApellidos2() != null)
										ap2 = pSalto.getInscripcionTurno().getApellidos2().toString();
									if (pSalto.getInscripcionTurno().getApellidos1() != null)
										ap1 = pSalto.getInscripcionTurno().getApellidos1().toString();
									if (pSalto.getInscripcionTurno().getNombre() != null)
										nombre = pSalto.getInscripcionTurno().getNombre().toString();
									mapLog3.put("*Saltos ", ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + ap1 + " " + ap2 + ", " + nombre);
								}else if(pSalto.getPersona() != null) {
									if (pSalto.getPersona().getApellidos2() != null)
										ap2 = pSalto.getPersona().getApellidos2().toString();
									if (pSalto.getPersona().getApellidos1() != null)
										ap1 =pSalto.getPersona().getApellidos1().toString();
									if (pSalto.getPersona().getNombre() != null)
										nombre = pSalto.getPersona().getNombre().toString();
									mapLog3.put("*Lista Saltos - "+indexAux, ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Lista Saltos - " + ap1 + " " + ap2 + ", " + nombre);
								}
								indexAux++;
							};

						}
					} else {
						mapLog4.put("*No existen saltos", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog4);
						LOGGER.info("*No existen saltos");
					}
					// Para cada plaza que hay que ocupar en dia/conjunto de dias:
					int letradosInsertados = 0;
					for (int k = 0; k < numeroLetradosGuardia; k++) {
						// obteniendo el letrado a asignar.
						// ATENCION: este metodo es el nucleo del proceso
						//TODO: (L) Aqui puede ser otro punto para meter el letrado cargas masivas
						LetradoInscripcionItem letradoGuardia = getSiguienteLetrado(alCompensaciones,
								alLetradosOrdenados, punteroListaLetrados, diasGuardia, hmPersonasConSaltos,
								hmBajasTemporales);

						// guardando la asignacion de guardia si se encontro letrado
						// hay que hacerlo aqui para no repetir letrado el mismo dia
						if (letradoGuardia != null) {
							String nombre = "";
							String ap1 = "";
							String ap2 = "";

							// log.addLog(new String[] {"Letrado seleccionado", letradoGuardia.toString()});
							if (letradoGuardia.getInscripcionGuardia() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
									ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
								if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
									ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
								if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
									nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
								mapLog5.put("*Letrado seleccionado ", ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " + ap1 + " " + ap2 + ", " + nombre);
							} else if (letradoGuardia.getInscripcionTurno() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
									ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
								if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
									ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
								if (letradoGuardia.getInscripcionTurno().getNombre() != null)
									nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
								mapLog5.put("*Letrado seleccionado ", ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " + ap1 + " " + ap2 + ", " + nombre);
							}
							alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
							letradoGuardia.setPeriodoGuardias(diasGuardia); //(L) Se supone que aqui esta el letrado seleccionado
							LetradoInscripcionItem letradoGuardiaClone = (LetradoInscripcionItem) letradoGuardia;
							letradoGuardiaClone.setPosicion(posicion);
							posicion++;
							alLetradosInsertar.add(letradoGuardiaClone);
							arrayPeriodosLetradosSJCS1.add(alLetradosInsertar);

							// guardando las guardias en BD
							if (calendariosVinculados1 == null) {//TODO: (L) Insertas en guardiacolegiado y cabecera
								almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, diasGuardia,
										lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
								);
							} else {
								// guardando la principal
								almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, primerPeriodo,
										lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
								);

								// guardando para cada una de las vinculadas
								for (GuardiasCalendarioItem calendario : calendariosVinculados1) {
									// modificando la guardia y calendario en el que se insertaran las guardias
									for (LetradoInscripcionItem lg : alLetradosInsertar) {
										lg.setIdinstitucion(new Short(calendario.getIdinstitucion()));
										lg.setIdTurno(Integer.parseInt(calendario.getIdturno()));
										lg.setIdGuardia(Integer.parseInt(calendario.getIdguardia()));
									}
									LOGGER.info("Vinculacion: IDTURNO: " + calendario.getIdturno() + "/ IDGUARDIA: "+calendario.getIdguardia() );
									// guardando en BD
									almacenarAsignacionGuardia(new Integer(calendario.getIdcalendarioguardias()),
											alLetradosInsertar, segundoPeriodo, lDiasASeparar,
											"gratuita.literal.comentario.sustitucion"
//											UtilidadesString.getMensajeIdioma(usuModificacion1,
//													"gratuita.literal.comentario.sustitucion")
									);
								}
							}

							letradosInsertados++;
						} else {
//							log.addLog(new String[] {"FIN generacion", UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes")});
//							throw new SIGAException(
//									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
							Map<String, Object> mapLog5 = new HashMap();
							mapLog5.put("*FIN generacion ",
									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
							listaDatosExcelGeneracionCalendarios.add(mapLog5);
							controlError++;
							LOGGER.info("*FIN generacion "
									+ "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
						}
					} // FIN Para cada plaza que hay que ocupar en dia/conjunto de dias

					// controlando que se insertaron tantos letrados como hacian falta
					if (letradosInsertados != numeroLetradosGuardia) {
//						log.addLog(new String[] {"FIN generacion", UtilidadesString.getMensajeIdioma(this.usrBean,
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes")});
						Map<String, Object> mapLog5 = new HashMap();
						mapLog5.put("*FIN generacion ", "errorLetradosSuficientes");
						listaDatosExcelGeneracionCalendarios.add(mapLog5);
						LOGGER.info("*FIN generacion " + "errorLetradosSuficientes");
//						throw new Exception(
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
					}

					// actualizando el ultimo letrado en la guardia solo si no es de la lista de
					// compensaciones
					int punteroUltimo = 0;
					if (punteroListaLetrados.getValor() == 0)
						punteroUltimo = alLetradosOrdenados.size() - 1;
					else
						punteroUltimo = punteroListaLetrados.getValor() - 1;

					if (alLetradosOrdenados.size() != 0) {
						unLetrado = alLetradosOrdenados.get(punteroUltimo);

						if (unLetrado.getSaltoocompensacion() == null) {
							String idGGC = null;
							if (unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado() != null)
								idGGC = unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado();
							try {
								String fSU = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(unLetrado.getInscripcionGuardia().getFechaSuscripcion());
								cambiarUltimoCola(unLetrado.getInscripcionGuardia().getIdInstitucion().toString(),
										unLetrado.getInscripcionGuardia().getIdturno().toString(),
										unLetrado.getInscripcionGuardia().getIdGuardia().toString(),
										unLetrado.getInscripcionGuardia().getIdPersona().toString(), fSU, idGGC);
							} catch (Exception e) {
								controlError++;
								errorGeneracionCalendario = "Error actualizando el ultimo letrado en la guardia solo si no es de la lista de compensaciones: "
										+ e;
							}
						}
					}
					// avanzando el puntero de dia en el caso de guardias vinculadas
					if (calendariosVinculados1 != null)
						primerPeriodo = segundoPeriodo;
				} // FIN Para cada dia o conjunto de dias
			} else {
				// log.addLog(new String[] {"Calendario sin letrados generado correctamente"});
				Map<String, Object> mapLog2 = new HashMap();
				mapLog2.put("*Calendario sin letrados generado correctamente", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Calendario sin letrados generado correctamente");
			}

		} catch (Exception e) {
			arrayPeriodosLetradosSJCS1 = null;
			throw new Exception(e + "");
		}
		
		LOGGER.info("FIN generarCalendarioAsync.calcularMatrizLetradosGuardia:");
		
	} // calcularMatrizLetradosGuardia()
	

	public void calcularMatrizLetradosGuardia3(GuardiasCalendarioItem guardiasCalendarioItem, List<GuardiasCalendarioItem> calendariosVinculados, GuardiasTurnoItem guardiasTurnoItem ,ArrayList<ArrayList<String>> arrayPeriodosDiasGuardia, List lDiasASeparar, InscripcionGuardiaItem inscripcion) throws Exception {
		
		String idInstitucion = guardiasCalendarioItem.getIdinstitucion();
		String idTurno = guardiasCalendarioItem.getIdturno();
		String idGuardia = guardiasCalendarioItem.getIdguardia();
		String idCalendarioGuardias = guardiasCalendarioItem.getIdcalendarioguardias();
		
		String fechaDesde = guardiasCalendarioItem.getFechainicio();
		String fechaHasta = guardiasCalendarioItem.getFechafin();
		
		LOGGER.info("INICIO generarCalendarioAsync.calcularMatrizLetradosGuardia:");
		
		// Variables generales
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con
		// letrado
		int numeroLetradosGuardia = 0; // Numero de letrados necesarios para cada periodo
		HashMap<Long, List<BajasTemporalesItem>> hmBajasTemporales = null;
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos = null; // Lista de saltos
		List<LetradoInscripcionItem> alCompensaciones = null; // Lista de compensaciones
		List<LetradoInscripcionItem> alLetradosOrdenados = null; // Cola de letrados en la guardia
		Puntero punteroListaLetrados;
		int posicion;
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		LetradoInscripcionItem unLetrado;

		try {
			// obteniendo bajas temporales por letrado
			String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardia.get(0)).get(0);
			ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia
					.get(arrayPeriodosDiasGuardia.size() - 1);
			String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size() - 1);
			try {
				hmBajasTemporales = getLetradosDiasBajaTemporal3(idInstitucion, idTurno, idGuardia, primerDia,
						ultimoDia);
			} catch (Exception e) {
				controlError++;
				errorGeneracionCalendario = "Error cobteniendo bajas temporales: " + e;
			}

			// obteniendo numero de letrados necesarios para cada periodo
			try {
				numeroLetradosGuardia = guardiasTurnoItem.getNumeroLetradosGuardia().intValue();
			} catch (Exception e) {
				errorGeneracionCalendario = "Error obteniendo numero de letrados necesarios para cada periodo: " + e;
			}

			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrados por dia ", Integer.toString(numeroLetradosGuardia));
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrados por dia " + Integer.toString(numeroLetradosGuardia));
			// log.addLog(new String[] {"Letrados por dia",
			// Integer.toString(numeroLetradosGuardia)});
			if (numeroLetradosGuardia > 0) {

				// Si hay guardias vinculadas, hay que mirar dos periodos a la vez,
				// por lo que se comienza con uno ya en memoria
				int inicial;
				primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(0);
				segundoPeriodo = null;
				if (calendariosVinculados ==null || calendariosVinculados.isEmpty())
					inicial = 0;
				else {
					Map<String, Object> mapLog2 = new HashMap();
					// log.addLog(new String[] {"Guardias vinculadas",
					// calendariosVinculados1.toString()});
					mapLog2.put("*Guardias vinculadas ", "");
					listaDatosExcelGeneracionCalendarios.add(mapLog2);
					LOGGER.info("*Guardias vinculadas " + calendariosVinculados.toString());
					String idGuardiaVin = "";
					String idTurnoVin = "";
					for(GuardiasCalendarioItem item : calendariosVinculados) {
						idGuardiaVin = item.getIdguardia();
						idTurnoVin = item.getIdturno();
						String nombreLog = scsGuardiasturnoExtendsMapper.searchNombresGuardiaByIdturnoIdguardia(idInstitucion, idGuardiaVin, idTurnoVin);
						mapLog2.put("*Guardias vinculadas ", nombreLog);
					}
					inicial = 1;
				}
				// Para cada dia o conjunto de dias:
				for (int i = inicial; i < arrayPeriodosDiasGuardia.size(); i++) {
					posicion = 1;
					// obteniendo conjunto de dias
					// Nota: cada periodo es un arraylist de fechas (String en formato de fecha
					// corto DD/MM/YYYY)
					if (calendariosVinculados ==null || calendariosVinculados.isEmpty()) {
						diasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
					} // Si hay guardias vinculadas, hay que mirar dos periodos a la vez
					else {
						segundoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardia.get(i);
						diasGuardia = new ArrayList<String>();
						diasGuardia.addAll(primerPeriodo);
						diasGuardia.addAll(segundoPeriodo);
					}
//					log.addLog(new String[] {" ", " "});
//					log.addLog(new String[] {"Dias", diasGuardia.toString()});

					Map<String, Object> mapLog1 = new HashMap();
					mapLog1.put("*Dias", diasGuardia.toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog1);
					LOGGER.info("*Dias " + diasGuardia.toString());

					// obteniendo cola de letrados
					punteroListaLetrados = new Puntero();
					try {

						alLetradosOrdenados = getColaGuardia(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno),
								Integer.valueOf(idGuardia), (String) diasGuardia.get(0),
								(String) diasGuardia.get(diasGuardia.size() - 1));

					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo la cola de letrados ordenados: " + e;
						controlError++;
					}
					// log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
					Map<String, Object> mapLog2 = new HashMap();
					if (alLetradosOrdenados != null && !alLetradosOrdenados.isEmpty()) {
						int auxIndex = 0;
						//alLetradosOrdenados.forEach(letrado -> {
						for(LetradoInscripcionItem letrado : alLetradosOrdenados) { //(L) Solo imprime
							String nombre = "";
							String ap1 = "";
							String ap2 = "";
							String numCol = "";
							if (letrado.getInscripcionGuardia() != null) {
								if (letrado.getInscripcionGuardia().getApellido2() != null)
									ap2 = letrado.getInscripcionGuardia().getApellido2().toString();
								if (letrado.getInscripcionGuardia().getApellido1() != null)
									ap1 = letrado.getInscripcionGuardia().getApellido1().toString();
								if (letrado.getInscripcionGuardia().getNombre() != null)
									nombre = letrado.getInscripcionGuardia().getNombre().toString();
								if(letrado.getInscripcionGuardia().getnColegiado() != null)
									numCol = letrado.getInscripcionGuardia().getnColegiado().toString();
								Map<String, Object> mapLog3 = new HashMap();
								mapLog3.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("*Colae " + ap1 + " " + ap2 + ", " + nombre);
							} else if (letrado.getInscripcionTurno() != null) {
								if (letrado.getInscripcionTurno().getApellidos2() != null)
									ap2 = letrado.getInscripcionTurno().getApellidos2().toString();
								if (letrado.getInscripcionTurno().getApellidos1() != null)
									ap1 = letrado.getInscripcionTurno().getApellidos1().toString();
								if (letrado.getInscripcionTurno().getNombre() != null)
									nombre = letrado.getInscripcionTurno().getNombre().toString();
								if (letrado.getInscripcionTurno().getNumerocolegiado() != null)
									numCol = letrado.getInscripcionTurno().getNumerocolegiado().toString();
								mapLog2.put("*Cola-" + auxIndex,"("+numCol+") "+ ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog2);
								LOGGER.info("*Colaia " + ap1 + " " + ap2 + ", " + nombre);

							}
							auxIndex++;
						};
						
					} else {
						mapLog2.put("*Cola vacía", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Cola vacía");
						controlError++;
					}

					if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//						throw new Exception("No existe cola de letrados de guardia");
						LOGGER.error("No existe cola de letrados de guardia");

					// obteniendo las compensaciones. Se obtienen dentro de este
					// bucle, ya que si hay incompatibilidades se añade una compensacion
					try {
						alCompensaciones = getCompensaciones(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia),
								(String) diasGuardia.get(0));
					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo las compensaciones: " + e;
						controlError++;
					}
//					log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
					Map<String, Object> mapLog3 = new HashMap();
					if (alCompensaciones != null && !alCompensaciones.isEmpty()) {
						//alCompensaciones.forEach(compensacion -> {
							int indexCompensacion = 0;
							for(LetradoInscripcionItem compensacion : alCompensaciones) {
							String nombre = "";
							String ap1 = "";
							String ap2 = "";
							if (compensacion.getInscripcionGuardia() != null) {
								if (compensacion.getInscripcionGuardia().getApellido2() != null)
									ap2 = compensacion.getInscripcionGuardia().getApellido2().toString();
								if (compensacion.getInscripcionGuardia().getApellido1() != null)
									ap1 = compensacion.getInscripcionGuardia().getApellido1().toString();
								if (compensacion.getInscripcionGuardia().getNombre() != null)
									nombre = compensacion.getInscripcionGuardia().getNombre().toString();
								mapLog3.put("Lista Compensaciones-" + indexCompensacion, ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("Lista Compensaciones-" + ap1 + " " + ap2 + ", " + nombre);
							} else if (compensacion.getInscripcionTurno() != null) {
								mapLog3.put("*Compensaciones ",
										compensacion.getInscripcionTurno().getApellidos1() + " "
												+ compensacion.getInscripcionTurno().getApellidos2() + ", "
												+ compensacion.getInscripcionTurno().getNombre());
								if (compensacion.getInscripcionTurno().getApellidos2() != null)
									ap2 = compensacion.getInscripcionTurno().getApellidos2().toString();
								if (compensacion.getInscripcionTurno().getApellidos1() != null)
									ap1 = compensacion.getInscripcionTurno().getApellidos1().toString();
								if (compensacion.getInscripcionTurno().getNombre() != null)
									nombre = compensacion.getInscripcionTurno().getNombre().toString();
								mapLog3.put("Lista Compensaciones-" +indexCompensacion, ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("Lista Compensaciones-" + ap1 + " " + ap2 + ", " + nombre);
							}
							indexCompensacion++;
						};
					} else {
						mapLog3.put("*No existen compensaciones-", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
						LOGGER.info("*No existen compensaciones");
						//controlError++;
					}

					// obteniendo saltos
					try {
						hmPersonasConSaltos = getSaltos(Integer.valueOf(idInstitucion), Integer.valueOf(idTurno), Integer.valueOf(idGuardia));
					} catch (Exception e) {
						controlError++;
						errorGeneracionCalendario = "Error obteniendo saltos: " + e;
					}

//					log.addLog(new String[] {"Saltos", hmPersonasConSaltos.toString()});
					Map<String, Object> mapLog4 = new HashMap();
					if (hmPersonasConSaltos != null && !hmPersonasConSaltos.isEmpty()) {
						int indexAux = 0;
						for (Map.Entry<Long, ArrayList<LetradoInscripcionItem>> personasConSaltos : hmPersonasConSaltos.entrySet()) {
							for(LetradoInscripcionItem pSalto : personasConSaltos.getValue()) {
							//personasConSaltos.getValue().forEach(pSalto -> {
								
								String nombre = "";
								String ap1 = "";
								String ap2 = "";
								if (pSalto.getInscripcionGuardia() != null) {
									if (pSalto.getInscripcionGuardia().getApellido2() != null)
										ap2 = pSalto.getInscripcionGuardia().getApellido2().toString();
									if (pSalto.getInscripcionGuardia().getApellido1() != null)
										ap1 = pSalto.getInscripcionGuardia().getApellido1().toString();
									if (pSalto.getInscripcionGuardia().getNombre() != null)
										nombre = pSalto.getInscripcionGuardia().getNombre().toString();
									mapLog3.put("*Saltos ", ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + ap1 + " " + ap2 + ", " + nombre);
								} else if (pSalto.getInscripcionTurno() != null) {
									if (pSalto.getInscripcionTurno().getApellidos2() != null)
										ap2 = pSalto.getInscripcionTurno().getApellidos2().toString();
									if (pSalto.getInscripcionTurno().getApellidos1() != null)
										ap1 = pSalto.getInscripcionTurno().getApellidos1().toString();
									if (pSalto.getInscripcionTurno().getNombre() != null)
										nombre = pSalto.getInscripcionTurno().getNombre().toString();
									mapLog3.put("*Saltos ", ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + ap1 + " " + ap2 + ", " + nombre);
								}else if(pSalto.getPersona() != null) {
									if (pSalto.getPersona().getApellidos2() != null)
										ap2 = pSalto.getPersona().getApellidos2().toString();
									if (pSalto.getPersona().getApellidos1() != null)
										ap1 =pSalto.getPersona().getApellidos1().toString();
									if (pSalto.getPersona().getNombre() != null)
										nombre = pSalto.getPersona().getNombre().toString();
									mapLog3.put("*Lista Saltos - "+indexAux, ap1 + " " + ap2 + ", " + nombre);
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Lista Saltos - " + ap1 + " " + ap2 + ", " + nombre);
								}
								indexAux++;
							};

						}
					} else {
						mapLog4.put("*No existen saltos", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog4);
						LOGGER.info("*No existen saltos");
					}
					// Para cada plaza que hay que ocupar en dia/conjunto de dias:
					int letradosInsertados = 0;
					for (int k = 0; k < numeroLetradosGuardia; k++) {
						// obteniendo el letrado a asignar.
						// ATENCION: este metodo es el nucleo del proceso
						//TODO: (L) Aqui puede ser otro punto para meter el letrado cargas masivas
						LetradoInscripcionItem letradoGuardia = getSiguienteLetrado3(guardiasCalendarioItem, alCompensaciones,
								alLetradosOrdenados, punteroListaLetrados, diasGuardia, hmPersonasConSaltos,
								hmBajasTemporales);

						// guardando la asignacion de guardia si se encontro letrado
						// hay que hacerlo aqui para no repetir letrado el mismo dia
						if (letradoGuardia != null) {
							String nombre = "";
							String ap1 = "";
							String ap2 = "";

							// log.addLog(new String[] {"Letrado seleccionado", letradoGuardia.toString()});
							if (letradoGuardia.getInscripcionGuardia() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								if (letradoGuardia.getInscripcionGuardia().getApellido2() != null)
									ap2 = letradoGuardia.getInscripcionGuardia().getApellido2().toString();
								if (letradoGuardia.getInscripcionGuardia().getApellido1() != null)
									ap1 = letradoGuardia.getInscripcionGuardia().getApellido1().toString();
								if (letradoGuardia.getInscripcionGuardia().getNombre() != null)
									nombre = letradoGuardia.getInscripcionGuardia().getNombre().toString();
								mapLog5.put("*Letrado seleccionado ", ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " + ap1 + " " + ap2 + ", " + nombre);
							} else if (letradoGuardia.getInscripcionTurno() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								if (letradoGuardia.getInscripcionTurno().getApellidos2() != null)
									ap2 = letradoGuardia.getInscripcionTurno().getApellidos2().toString();
								if (letradoGuardia.getInscripcionTurno().getApellidos1() != null)
									ap1 = letradoGuardia.getInscripcionTurno().getApellidos1().toString();
								if (letradoGuardia.getInscripcionTurno().getNombre() != null)
									nombre = letradoGuardia.getInscripcionTurno().getNombre().toString();
								mapLog5.put("*Letrado seleccionado ", ap1 + " " + ap2 + ", " + nombre);
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " + ap1 + " " + ap2 + ", " + nombre);
							}
							alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
							letradoGuardia.setPeriodoGuardias(diasGuardia); //(L) Se supone que aqui esta el letrado seleccionado
							LetradoInscripcionItem letradoGuardiaClone = (LetradoInscripcionItem) letradoGuardia;
							letradoGuardiaClone.setPosicion(posicion);
							posicion++;
							alLetradosInsertar.add(letradoGuardiaClone);
							
							
							// guardando las guardias en BD
							if (calendariosVinculados == null || calendariosVinculados.isEmpty()) {//TODO: (L) Insertas en guardiacolegiado y cabecera
								almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem, alLetradosInsertar, diasGuardia,
										lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
								);
							} else {
								// guardando la principal
								almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem, alLetradosInsertar, primerPeriodo,
										lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
								);

								// guardando para cada una de las vinculadas
								for (GuardiasCalendarioItem calendario : calendariosVinculados) {
									// modificando la guardia y calendario en el que se insertaran las guardias
									for (LetradoInscripcionItem lg : alLetradosInsertar) {
										lg.setIdinstitucion(new Short(calendario.getIdinstitucion()));
										lg.setIdTurno(Integer.parseInt(calendario.getIdturno()));
										lg.setIdGuardia(Integer.parseInt(calendario.getIdguardia()));
									}

									// guardando en BD
									almacenarAsignacionGuardia3(guardiasCalendarioItem, guardiasTurnoItem,
											alLetradosInsertar, segundoPeriodo, lDiasASeparar,
											"gratuita.literal.comentario.sustitucion"
//											UtilidadesString.getMensajeIdioma(usuModificacion1,
//													"gratuita.literal.comentario.sustitucion")
									);
								}
							}

							letradosInsertados++;
						} else {
//							log.addLog(new String[] {"FIN generacion", UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes")});
//							throw new SIGAException(
//									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
							Map<String, Object> mapLog5 = new HashMap();
							mapLog5.put("*FIN generacion ",
									"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
							listaDatosExcelGeneracionCalendarios.add(mapLog5);
							controlError++;
							LOGGER.info("*FIN generacion "
									+ "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
						}
					} // FIN Para cada plaza que hay que ocupar en dia/conjunto de dias

					// controlando que se insertaron tantos letrados como hacian falta
					if (letradosInsertados != numeroLetradosGuardia) {
//						log.addLog(new String[] {"FIN generacion", UtilidadesString.getMensajeIdioma(this.usrBean,
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes")});
						Map<String, Object> mapLog5 = new HashMap();
						mapLog5.put("*FIN generacion ", "errorLetradosSuficientes");
						listaDatosExcelGeneracionCalendarios.add(mapLog5);
						LOGGER.info("*FIN generacion " + "errorLetradosSuficientes");
//						throw new Exception(
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
					}

					// actualizando el ultimo letrado en la guardia solo si no es de la lista de
					// compensaciones
					int punteroUltimo = 0;
					if (punteroListaLetrados.getValor() == 0)
						punteroUltimo = alLetradosOrdenados.size() - 1;
					else
						punteroUltimo = punteroListaLetrados.getValor() - 1;

					if (alLetradosOrdenados.size() != 0) {
						unLetrado = alLetradosOrdenados.get(punteroUltimo);

						if (unLetrado.getSaltoocompensacion() == null) {
							String idGGC = null;
							if (unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado() != null)
								idGGC = unLetrado.getInscripcionGuardia().getIdGrupoGuardiaColegiado();
							try {
								String fSU = ( unLetrado.getInscripcionGuardia().getFechaSuscripcion() == null ) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(unLetrado.getInscripcionGuardia().getFechaSuscripcion());
								cambiarUltimoCola(unLetrado.getInscripcionGuardia().getIdInstitucion().toString(),
										unLetrado.getInscripcionGuardia().getIdturno().toString(),
										unLetrado.getInscripcionGuardia().getIdGuardia().toString(),
										unLetrado.getInscripcionGuardia().getIdPersona().toString(), fSU, idGGC);
							} catch (Exception e) {
								controlError++;
								errorGeneracionCalendario = "Error actualizando el ultimo letrado en la guardia solo si no es de la lista de compensaciones: "
										+ e;
							}
						}
					}
					// avanzando el puntero de dia en el caso de guardias vinculadas
					if (calendariosVinculados != null && !calendariosVinculados.isEmpty())
						primerPeriodo = segundoPeriodo;
				} // FIN Para cada dia o conjunto de dias
			} else {
				// log.addLog(new String[] {"Calendario sin letrados generado correctamente"});
				Map<String, Object> mapLog2 = new HashMap();
				mapLog2.put("*Calendario sin letrados generado correctamente", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Calendario sin letrados generado correctamente");
			}

		} catch (Exception e) {
			throw new Exception(e + "");
		}
		
		LOGGER.info("FIN generarCalendarioAsync.calcularMatrizLetradosGuardia:");
		
	} // calcularMatrizLetradosGuardia()
	
	private List<LetradoInscripcionItem> getInscritoInfo(InscripcionGuardiaItem inscripcion) {
		// Controles
		List<LetradoInscripcionItem> colaLetrados = new ArrayList<LetradoInscripcionItem>();
		InscripcionGuardiaItem punteroInscripciones;
		
		
		List<InscripcionGuardiaItem> listaLetrados = scsInscripcionguardiaExtendsMapper.getColaGuardiasByNumColegiado(inscripcion.getIdGuardia(), inscripcion.getIdTurno(), 
				inscripcion.getFechaIni(), inscripcion.getFechaFin(), inscripcion.getIdInstitucion(), inscripcion.getnColegiado());

		if (listaLetrados == null || listaLetrados.size() == 0)
			return colaLetrados;
		for (int i = 0; i < listaLetrados.size(); i++) {
			punteroInscripciones = listaLetrados.get(i);

			LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
			letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
			colaLetrados.add(letradoInscripcionItem);

		}

		// usando saltos si es necesario (en guardias no)

		return colaLetrados;

	}

	/**
	 * Obtiene todos los saltos asociados a una guardia
	 *
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @return
	 * @throws ClsExceptions
	 */
	public HashMap<Long, ArrayList<LetradoInscripcionItem>> getSaltos(Integer idInstitucion, Integer idTurno,
			Integer idGuardia) throws Exception {

		// Variables
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos;
		Hashtable<String, Object> htFila;

		try {

			hmPersonasConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
			List<String> idPersona2List = getQuerySaltosCompensacionesActivos("S", idInstitucion, idTurno, idGuardia);
			List<CenPersonaItem> personas = new ArrayList<>();
//				idPersona2List.forEach(idPersona2 -> {

			String idPersonasSeparatedByComma = idPersona2List.stream().collect(Collectors.joining(","));
			if (!idPersonasSeparatedByComma.isEmpty())
				personas = getPersonaPorId(idPersonasSeparatedByComma);
			if (personas.size() != 0) {
				personas.forEach(persona -> {
					ArrayList<LetradoInscripcionItem> alLetradosSaltados;
					LetradoInscripcionItem letradoSeleccionado = null;

					try {
						letradoSeleccionado = new LetradoInscripcionItem(persona, idInstitucion, idTurno, idGuardia,
								"S");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (hmPersonasConSaltos.containsKey(persona.getIdpersona()))
						alLetradosSaltados = hmPersonasConSaltos.get(persona.getIdpersona());
					else
						alLetradosSaltados = new ArrayList<LetradoInscripcionItem>();

					alLetradosSaltados.add(letradoSeleccionado);
					hmPersonasConSaltos.put(persona.getIdpersona(), alLetradosSaltados);
				});
			}
//				});

		} catch (Exception e) {
			throw new Exception(e + "Error al comprobar si hay salto en BD.");
		}

		return hmPersonasConSaltos;
	}

	private List<String> getQuerySaltosCompensacionesActivos(String tipo, Integer idInstitucion, Integer idTurno,
			Integer idGuardia) {
		List<String> idPersona = null;
		try {
			String s_idpersona = null;
			String s_idinstitucion = idInstitucion.toString();
			String s_idturno = null;
			if (idTurno != null) {
				s_idturno = idTurno.toString();
			}
			String s_idguardia = null;
			if (idGuardia != null) {
				s_idguardia = idGuardia.toString();
			}
			String s_saltocompensacion = null;
			if (tipo != null) {
				s_saltocompensacion = tipo;
				if (s_saltocompensacion.equals(""))
					s_saltocompensacion = " ";
			}

			idPersona = scsGuardiasturnoExtendsMapper.getSaltoCompensacionesActivo(s_idinstitucion, s_idturno,
					s_idguardia, null, s_saltocompensacion);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo saltos/compensaciones activos: " + e;
		}
		return idPersona;

	} // getQuerySaltosCompensacionesActivos()

	/**
	 * Devuelve una persona a partir de su idPersona
	 *
	 * @param nifcif de la persona a buscar
	 * @version 1
	 */
	public List<CenPersonaItem> getPersonaPorId(String idPersona) throws Exception {

		try {
			CenPersonaItem perBean = null;

			Hashtable codigos = new Hashtable();
			codigos.put(new Integer(1), idPersona);
			List<CenPersonaItem> personas = scsGuardiasturnoExtendsMapper.getPersonaById(idPersona);

//			if ((personas != null) && personas.size() == 1) {
//				perBean = (CenPersonaItem)personas.get(0);
//			}
//
//			return perBean;
			return personas;
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo una persona a partir de su idPersona: " + e;
			throw new Exception(e + ": Error al recuperar los datos");
		}
	}

	/**
	 * Obtiene todas las compensaciones asociadas a una guardia
	 *
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @return
	 * @throws ClsExceptions
	 */
	public ArrayList<LetradoInscripcionItem> getCompensaciones(Integer idInstitucion, Integer idTurno,
			Integer idGuardia, String fecha) throws Exception {
		// Controles
		// Variables
		ArrayList<LetradoInscripcionItem> alLetradosCompensados;
		Hashtable<String, Object> htFila;
		List<String> idPersonaList;

		try {
			// obtiene las compesaciones de letrados que no esten de baja en la guardia
			alLetradosCompensados = new ArrayList<LetradoInscripcionItem>();
			idPersonaList = getQuerySaltosCompensacionesActivos("C", idInstitucion, idTurno, idGuardia);
//				idPersonaList.forEach(idPersona -> {
			if (idPersonaList.size() != 0) {
				String idPersonasSeparatedByComma = idPersonaList.stream().collect(Collectors.joining(","));
				if (idGuardia != null) {
					List<InscripcionGuardiaItem> inscripcionGuardiaList = new ArrayList<InscripcionGuardiaItem>();
					try {
						inscripcionGuardiaList = getInscripcionGuardiaActiva(idInstitucion.toString(),
								idTurno.toString(), idGuardia.toString(), idPersonasSeparatedByComma, fecha);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo InscripcionGuardiaActiva: " + e;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//							if(inscripcionGuardia == null)
//								continue;
					inscripcionGuardiaList.forEach(inscripcionGuardia -> {

						LetradoInscripcionItem letradoSeleccionado = new LetradoInscripcionItem();
						letradoSeleccionado.setInscripcionGuardia(inscripcionGuardia);
						letradoSeleccionado.setSaltoocompensacion("C");
						alLetradosCompensados.add(letradoSeleccionado);
					});

				} else {
					List<InscripcionTurnoItem> inscripcionTurnoList = new ArrayList<InscripcionTurnoItem>();
					try {
						inscripcionTurnoList = getInscripcionTurnoActiva(idInstitucion.toString(), idTurno.toString(),
								idPersonasSeparatedByComma, fecha);
					} catch (Exception e) {
						errorGeneracionCalendario = "Error obteniendo InscripcionTurnoActiva: " + e;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//							if(inscripcionTurno == null)
//								continue;
					inscripcionTurnoList.forEach(inscripcionTurno -> {
						LetradoInscripcionItem letradoSeleccionado = new LetradoInscripcionItem();
						letradoSeleccionado.setSaltoocompensacion("C");
						letradoSeleccionado.setInscripcionTurno(inscripcionTurno);
						alLetradosCompensados.add(letradoSeleccionado);
					});
				}
			}
//				});

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener letrados compensados  en BD: " + e;
			throw new Exception(e + ": Error al obtener letrados compensados  en BD.");
		}

		return alLetradosCompensados;
	}

	/**
	 * Obtiene las inscripcion activa de la persona en la guardia en la fecha dada
	 */
	public List<InscripcionTurnoItem> getInscripcionTurnoActiva(String idinstitucion, String idturno, String idpersona,
			String fecha) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))
				return null;
			if (idturno == null || idturno.equals(""))
				return null;
			if (idpersona == null || idpersona.equals(""))
				return null;
			if (fecha == null || fecha.equals(""))
				return null;

			if (!fecha.equals("sysdate"))
				fecha = "'" + fecha + "'";

			List<InscripcionTurnoItem> datos = null;
			datos = scsGuardiasturnoExtendsMapper.getInscripcionesTurnoActiva(idpersona, idinstitucion, idturno, fecha);
//				for (int i = 0; i < datos.size(); i++) {
//
//					InscripcionTurnoItem inscripcionBean = datos.get(i);
//					CenPersonaItem personaBean = new CenPersonaItem(inscripcionBean.getIdpersona(),
//							inscripcionBean.getNombre(),
//							inscripcionBean.getApellidos1(),
//							inscripcionBean.getApellidos2(),
//							inscripcionBean.getNumerocolegiado());
//					//DUDA
//
//					inscripcionBean.setPersona(personaBean);
//					datos.add(inscripcionBean);
//				}
//
//			return datos.get(0);
			return datos;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener inscripciones de turno activas: " + e;
			throw new Exception(e + ": Error al ejecutar getInscripcionActiva()");
		}
	} // getInscripcionActiva()

	/**
	 * Obtiene las inscripcion activa de la persona en la guardia en la fecha dada
	 */
	public List<InscripcionGuardiaItem> getInscripcionGuardiaActiva(String idinstitucion, String idturno,
			String idguardia, String idpersona, String fecha) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))
				return null;
			if (idturno == null || idturno.equals(""))
				return null;
			if (idguardia == null || idguardia.equals(""))
				return null;
			if (idpersona == null || idpersona.equals(""))
				return null;
			if (fecha == null || fecha.equals(""))
				return null;
			if (!fecha.equals("sysdate"))
				fecha = "'" + fecha + "'";

			List<InscripcionGuardiaItem> datos = null;
			datos = scsGuardiasturnoExtendsMapper.getInscripcionesGuardiaActiva(idpersona, idinstitucion, idguardia,
					fecha, idturno);
//			if (datos.size() != 0) {
//				return datos.get(0);
//			}else {
//				return null;
//			}
			return datos;

		} catch (Exception e) {
			errorGeneracionCalendario = "Error al obtener inscripciones de guardia activas: " + e;
			throw new Exception(e + ": Error al ejecutar getInscripcionActiva()");
		}
	}

	/**
	 * Cambia el ultimo letrado de la cola de la guardia indicada por el nuevo que
	 * se ha solicitado
	 */
	@Transactional
	public void cambiarUltimoCola(String idInstitucion, String idTurno, String idGuardia, String idPersona_Ultimo,
			String fechaSuscripcion_Ultimo, String idGrupoGuardiaColegiado_Ultimo) throws Exception {

		String sIdinstitucion = null;
		String sIdTurno = null;
		String sIdGuardia = null;
		if (idInstitucion != null) {
			sIdinstitucion = idInstitucion.toString();
		}
		if (idTurno != null) {
			sIdTurno = idTurno.toString();
		}
		if (idGuardia != null) {
			sIdGuardia = idGuardia.toString();
		}
		String sIdpersona = (idPersona_Ultimo == null) ? "null" : idPersona_Ultimo.toString();
		String sFechaSusc = (fechaSuscripcion_Ultimo == null || fechaSuscripcion_Ultimo.equals("")) ? "null"
				: fechaSuscripcion_Ultimo.toString();
		String sIdGrupoGuardiaColegiado_Ultimo = (idGrupoGuardiaColegiado_Ultimo == null) ? "null"
				: idGrupoGuardiaColegiado_Ultimo.toString();

//		LocalDate date4 = ZonedDateTime
//	            .parse(sFechaSusc.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
//	            .toLocalDate();
//	 	String OLD_FORMAT = "yyyy-MM-dd";
//		String NEW_FORMAT = "dd/MM/yyyy";
//
//		sFechaSusc = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		ScsGrupoguardiaExample example = new ScsGrupoguardiaExample();

		ScsGuardiasturnoKey guardiaKey = new ScsGuardiasturnoKey();
		guardiaKey.setIdinstitucion(Short.parseShort(sIdinstitucion));
		guardiaKey.setIdturno(Integer.parseInt(sIdTurno));
		guardiaKey.setIdguardia(Integer.parseInt(sIdGuardia));

		ScsGuardiasturno guardia = scsGuardiasTurnoMapper.selectByPrimaryKey(guardiaKey);

		if (guardia != null) {
			ScsInscripcionguardiaExample insc = new ScsInscripcionguardiaExample();
			insc.createCriteria().andIdinstitucionEqualTo(Short.parseShort(sIdinstitucion))
			.andIdpersonaEqualTo(Long.parseLong(idPersona_Ultimo))
			.andIdguardiaEqualTo(Integer.parseInt(sIdGuardia)).andIdturnoEqualTo(Integer.parseInt(sIdTurno));
			insc.setOrderByClause("fechasuscripcion desc");
			
			List<ScsInscripcionguardia> inscripcionList = scsInscripcionguardiaExtendsMapper.selectByExample(insc);
			if(inscripcionList != null && !inscripcionList.isEmpty()) {
				ScsInscripcionguardia inscripcionUpdate = inscripcionList.get(0);
				guardia.setIdpersonaUltimo(inscripcionUpdate.getIdpersona());
				if(!sIdGrupoGuardiaColegiado_Ultimo.isEmpty() && !sIdGrupoGuardiaColegiado_Ultimo.equals("null") && sIdGrupoGuardiaColegiado_Ultimo != null)
					guardia.setIdgrupoguardiaUltimo(Long.parseLong(sIdGrupoGuardiaColegiado_Ultimo));
				
				guardia.setFechasuscripcionUltimo(inscripcionUpdate.getFechasuscripcion()); //fecha inscripcionguardia
				int actualizado = scsGuardiasTurnoMapper.updateByPrimaryKeySelective(guardia);
				if(actualizado != 1)
					LOGGER.info("ERROR AL ACTUALIZAR ULTIMO EN LA COLA");
			}
			//select inscripcionguardiasbyexample
			
			//scsGuardiasturnoExtendsMapper.actualizarUltimoColegiado(guardia);
		}
		
		
		//int res4 = scsGuardiasturnoExtendsMapper.cambiarUltimoCola4(sIdinstitucion, sIdTurno, sIdGuardia, sIdpersona,
		//		sIdGrupoGuardiaColegiado_Ultimo, sFechaSusc, usuModificacion1.toString());
	
	} // cambiarUltimoCola()

	/**
	 * Almacena para una lista de letrados las guardias con los registros
	 * correspondientes a sus dias de guardia.
	 *
	 * @param arrayLetrados
	 * @param periodoDiasGuardia
	 * @param lDiasASeparar
	 * @param mensaje
	 * @throws ClsExceptions
	 */
	private void almacenarAsignacionGuardia(Integer idCalendarioGuardias, ArrayList arrayLetrados, //(L) El letrado y el periodo(getPeriodoGuardias o periodoDiasGuardia) van en arrayLetrados
			ArrayList periodoDiasGuardia, List lDiasASeparar, String mensaje) throws Exception {
		Iterator iter;
		Iterator iterLetrados;
		String fechaInicioPeriodo = null, fechaFinPeriodo = null, fechaPeriodo = null;
		ScsCabeceraguardias beanCabeceraGuardias;
		ScsGuardiascolegiado beanGuardiasColegiado;
		LetradoInscripcionItem letrado = null;

		List alPeriodosSinAgrupar = getPeriodos(periodoDiasGuardia, lDiasASeparar);
		for (int j = 0; j < alPeriodosSinAgrupar.size(); j++) {
			ArrayList alDiasPeriodo = (ArrayList) alPeriodosSinAgrupar.get(j);

			fechaInicioPeriodo = (String) alDiasPeriodo.get(0);
			fechaFinPeriodo = (String) alDiasPeriodo.get(alDiasPeriodo.size() - 1);

			iterLetrados = arrayLetrados.iterator();
			while (iterLetrados.hasNext()) {
				letrado = (LetradoInscripcionItem) iterLetrados.next();
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				// Paso1: inserto un registro cada guardia:
				beanCabeceraGuardias = new ScsCabeceraguardias();
				// beanCabeceraGuardias.setIdinstitucion(letrado.getIdinstitucion());

				if (letrado.getInscripcionGuardia() != null) {
					beanCabeceraGuardias
							.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
					// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
					beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
					// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
					beanCabeceraGuardias.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
					// beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
					beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
				} else if (letrado.getInscripcionTurno() != null) {
					beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
					// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
					beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
					// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
					beanCabeceraGuardias.setIdguardia(null);
					// beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
					beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionTurno().getIdpersona()));
				}

				beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
				// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
				beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
				// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
				beanCabeceraGuardias.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
				beanCabeceraGuardias.setIdcalendarioguardias(idCalendarioGuardias);
				beanCabeceraGuardias.setFechainicio(new Date(fechaInicioPeriodo));
				String fechaInicioPSt = fechaInicioPeriodo;
				beanCabeceraGuardias.setFechaFin(new Date(fechaFinPeriodo));
				String fechaFinPSt = fechaFinPeriodo;
				beanCabeceraGuardias.setFechamodificacion(new Date());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				beanCabeceraGuardias.setComensustitucion(mensaje);
				beanCabeceraGuardias.setSustituto("0");
				beanCabeceraGuardias.setUsumodificacion(usuModificacion1);
				beanCabeceraGuardias.setFacturado("");
				beanCabeceraGuardias.setPagado("");
				String fechaAlta = null;
				if (beanCabeceraGuardias.getFechaalta() != null) {
					fechaAlta = formatter.format(beanCabeceraGuardias.getFechaalta());
				}

				// RGG cambio para cabeceras de guardia validadas
				// String valorValidar =
				// getValor(idInstitucion1.toString(),"SCS","VALIDAR_VOLANTE","N");
				ScsGuardiasturnoKey key = new ScsGuardiasturnoKey();
				if (letrado.getInscripcionGuardia() != null) {
					key.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
					key.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
					key.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
				} else if (letrado.getInscripcionTurno() != null) {
					key.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
					key.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
					key.setIdguardia(null);
				}
				ScsGuardiasturno gt = scsGuardiasTurnoMapper.selectByPrimaryKey(key);
				String valorValidar = gt.getRequeridavalidacion();
//					if (valorValidar.equals("N")) {
				if (valorValidar != null && valorValidar.equals("0")) {
					// directamente quedan validados
					beanCabeceraGuardias.setValidado("1");// true
					beanCabeceraGuardias.setFechaalta(new Date());
				} else {
					// quedan pendientes de validacion
					beanCabeceraGuardias.setValidado("0");// false
				}

				beanCabeceraGuardias.setFechaalta(new Date());
				beanCabeceraGuardias.setUsualta(new Integer(usuModificacion1));
				beanCabeceraGuardias.setPosicion(letrado.getPosicion().shortValue());
				if (letrado.getNumeroGrupo() != null && !letrado.getNumeroGrupo().equals("")) {
					beanCabeceraGuardias.setNumerogrupo(Integer.parseInt(letrado.getNumeroGrupo()));
				}

				// Se comprueba si para ese periodo existe una guardia para el letrado
				if (validaGuardiaLetradoPeriodo(beanCabeceraGuardias.getIdinstitucion(),
						beanCabeceraGuardias.getIdturno(), beanCabeceraGuardias.getIdguardia(),
						beanCabeceraGuardias.getIdpersona(), fechaInicioPeriodo, fechaFinPeriodo))
					throw new Exception("gratuita.calendarios.guardias.mensaje.existe");

				if (letrado.getInscripcionGuardia() != null) {
					scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
							letrado.getInscripcionGuardia().getIdInstitucion(),
							letrado.getInscripcionGuardia().getIdturno(),
							letrado.getInscripcionGuardia().getIdGuardia(),
							letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
				} else if (letrado.getInscripcionTurno() != null) {
					scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
							letrado.getInscripcionTurno().getIdinstitucion().toString(),
							letrado.getInscripcionTurno().getIdturno().toString(), null,
							letrado.getInscripcionTurno().getIdpersona().toString(), fechaAlta);
				} else {
					scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
							letrado.getInscripcionGuardia().getIdInstitucion(),
							letrado.getInscripcionGuardia().getIdturno(),
							letrado.getInscripcionGuardia().getIdGuardia(),
							letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
				}
				// Paso2: inserto un registro por dia de guardia en cada guardia:
				iter = alDiasPeriodo.iterator();
				while (iter.hasNext()) {
					fechaPeriodo = (String) iter.next();

					beanGuardiasColegiado = new ScsGuardiascolegiado();
					if (letrado.getInscripcionGuardia() != null) {
						beanGuardiasColegiado
								.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
						beanGuardiasColegiado.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
						beanGuardiasColegiado.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
						beanGuardiasColegiado.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
					} else if (letrado.getInscripcionTurno() != null) {
						beanGuardiasColegiado
								.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
						beanGuardiasColegiado.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
						beanGuardiasColegiado.setIdguardia(null);
						beanGuardiasColegiado.setIdpersona(new Long(letrado.getInscripcionTurno().getIdpersona()));
					}

					String NEW_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
					String OLD_FORMAT2 = "dd/MM/yyyy";
//						beanGuardiasColegiado.setIdinstitucion(letrado.getIdinstitucion());
//						beanGuardiasColegiado.setIdturno(letrado.getIdturno());
//						beanGuardiasColegiado.setIdguardia(letrado.getIdguardia());
//						beanGuardiasColegiado.setIdpersona(letrado.getIdpersona());
					beanGuardiasColegiado.setFechainicio(new Date(fechaInicioPeriodo));
					fechaInicioPSt = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2, fechaInicioPeriodo);
					beanGuardiasColegiado.setFechafin(new Date(fechaPeriodo));
					fechaFinPSt = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2, fechaPeriodo);
					// Duracion segun tipoDias MES, SEMANA, QUINCENA, DIAS
					int duracion = convertirUnidadesDuracion(beanGuardiasTurno1.getTipodiasGuardia());
					// beanGuardiasColegiado.setDiasguardia(new
					// Long(beanGuardiasTurno1.getDiasGuardia().longValue()));
					beanGuardiasColegiado.setDiasguardia(new Long(duracion));
					beanGuardiasColegiado.setDiasacobrar(new Long(beanGuardiasTurno1.getDiasPagados().longValue()));
					beanGuardiasColegiado.setFechamodificacion(new Date());
					today = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2, formatter.format(new Date()));
					beanGuardiasColegiado.setUsumodificacion(usuModificacion1);
					beanGuardiasColegiado.setReserva("N");
					beanGuardiasColegiado.setObservaciones("-");
					beanGuardiasColegiado.setFacturado("N");
					beanGuardiasColegiado.setPagado("N");
					beanGuardiasColegiado.setIdfacturacion(null);
					scsGuardiascolegiadoMapper.insertSelective2(beanGuardiasColegiado, fechaInicioPSt, fechaFinPSt,
							today); // generado					
					try {
						beanGuardiasColegiado.setFechainicio(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioPSt));
						beanGuardiasColegiado.setFechafin(new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinPSt));
						this.triggerGuardiaColegiadoAID(beanGuardiasColegiado, 1);
					} catch (Exception e) {
						LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
					}					
				}
			}
		}

		Map<String, Object> mapLog10 = new HashMap();
		String g = "";
		String t = "";
		if (letrado.getInscripcionGuardia() != null) {
			g = "con guadia " + new Integer(letrado.getInscripcionGuardia().getIdGuardia());
			t = "con turno " + new Integer(letrado.getInscripcionGuardia().getIdturno());
		} else if (letrado.getInscripcionTurno() != null) {
			g = "con guadia null";
			t = "con turno " + new Integer(letrado.getInscripcionTurno().getIdturno());
		}
		mapLog10.put("*Almacenando guardia ", g + " y " + t);
		listaDatosExcelGeneracionCalendarios.add(mapLog10);
		LOGGER.info("*Almacenando guardia " + g + " y " + t);
		Map<String, Object> mapLog11 = new HashMap();
		mapLog11.put("","");
		listaDatosExcelGeneracionCalendarios.add(mapLog11);

	}
	
	private void almacenarAsignacionGuardia3(GuardiasCalendarioItem guardiasCalendarioItem,  GuardiasTurnoItem guardiasTurnoItem, ArrayList arrayLetrados, //(L) El letrado y el periodo(getPeriodoGuardias o periodoDiasGuardia) van en arrayLetrados
			ArrayList periodoDiasGuardia, List lDiasASeparar, String mensaje) throws Exception {
		
		Integer idCalendarioGuardias = Integer.valueOf(guardiasCalendarioItem.getIdcalendarioguardias());
		Integer usuModificacion = Integer.valueOf(guardiasCalendarioItem.getUsumodificacion());
		
		Iterator iter;
		Iterator iterLetrados;
		String fechaInicioPeriodo = null, fechaFinPeriodo = null, fechaPeriodo = null;
		ScsCabeceraguardias beanCabeceraGuardias;
		LetradoInscripcionItem letrado = null;
		Short idInstitucion = 0;
		List alPeriodosSinAgrupar = getPeriodos(periodoDiasGuardia, lDiasASeparar);
		for (int j = 0; j < alPeriodosSinAgrupar.size(); j++) {
			ArrayList alDiasPeriodo = (ArrayList) alPeriodosSinAgrupar.get(j);

			fechaInicioPeriodo = (String) alDiasPeriodo.get(0);
			fechaFinPeriodo = (String) alDiasPeriodo.get(alDiasPeriodo.size() - 1);

			iterLetrados = arrayLetrados.iterator();
			while (iterLetrados.hasNext()) {
				letrado = (LetradoInscripcionItem) iterLetrados.next();
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				// Paso1: inserto un registro cada guardia:
				beanCabeceraGuardias = new ScsCabeceraguardias();
				// beanCabeceraGuardias.setIdinstitucion(letrado.getIdinstitucion());

				if (letrado.getInscripcionGuardia() != null) {
					beanCabeceraGuardias
							.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
					idInstitucion = new Short(letrado.getInscripcionGuardia().getIdInstitucion());
					// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
					beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
					// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
					beanCabeceraGuardias.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
					// beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
					beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
				} else if (letrado.getInscripcionTurno() != null) {
					beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
					idInstitucion = new Short(letrado.getInscripcionTurno().getIdinstitucion());
					// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
					beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
					// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
					beanCabeceraGuardias.setIdguardia(null);
					// beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
					beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionTurno().getIdpersona()));
				}

				beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
				idInstitucion = new Short(letrado.getInscripcionGuardia().getIdInstitucion());
				// beanCabeceraGuardias.setIdturno(letrado.getIdturno());
				beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
				// beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
				beanCabeceraGuardias.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
				beanCabeceraGuardias.setIdcalendarioguardias(idCalendarioGuardias);
				beanCabeceraGuardias.setFechainicio(new Date(fechaInicioPeriodo));
				String fechaInicioPSt = fechaInicioPeriodo;
				beanCabeceraGuardias.setFechaFin(new Date(fechaFinPeriodo));
				String fechaFinPSt = fechaFinPeriodo;
				beanCabeceraGuardias.setFechamodificacion(new Date());
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_SHORT_SLASH);
				String today = formatter.format(new Date());
				beanCabeceraGuardias.setComensustitucion(mensaje);
				beanCabeceraGuardias.setSustituto("0");
				beanCabeceraGuardias.setUsumodificacion(usuModificacion);
				beanCabeceraGuardias.setFacturado("");
				beanCabeceraGuardias.setPagado("");
				String fechaAlta = null;
				if (beanCabeceraGuardias.getFechaalta() != null) {
					fechaAlta = formatter.format(beanCabeceraGuardias.getFechaalta());
				}

				// RGG cambio para cabeceras de guardia validadas
				// String valorValidar =
				// getValor(idInstitucion1.toString(),"SCS","VALIDAR_VOLANTE","N");
				ScsGuardiasturnoKey key = new ScsGuardiasturnoKey();
				if (letrado.getInscripcionGuardia() != null) {
					key.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
					key.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
					key.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
				} else if (letrado.getInscripcionTurno() != null) {
					key.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
					key.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
					key.setIdguardia(null);
				}
				ScsGuardiasturno gt = scsGuardiasTurnoMapper.selectByPrimaryKey(key);
				String valorValidar = gt.getRequeridavalidacion();
//					if (valorValidar.equals("N")) {
				if (valorValidar != null && valorValidar.equals("0")) {
					// directamente quedan validados
					beanCabeceraGuardias.setValidado("1");// true
				} else {
					// quedan pendientes de validacion
					beanCabeceraGuardias.setValidado("0");// false
				}

				beanCabeceraGuardias.setFechaalta(new Date());
				beanCabeceraGuardias.setUsualta(new Integer(usuModificacion));
				beanCabeceraGuardias.setPosicion(letrado.getPosicion().shortValue());
				if (letrado.getNumeroGrupo() != null && !letrado.getNumeroGrupo().equals("")) {
					beanCabeceraGuardias.setNumerogrupo(Integer.parseInt(letrado.getNumeroGrupo()));
				}

				// Se comprueba si para ese periodo existe una guardia para el letrado
				try {
					if (validaGuardiaLetradoPeriodo(beanCabeceraGuardias.getIdinstitucion(),
							beanCabeceraGuardias.getIdturno(), beanCabeceraGuardias.getIdguardia(),
							beanCabeceraGuardias.getIdpersona(), fechaInicioPeriodo, fechaFinPeriodo)) // TODO: (L) 
						throw new Exception("gratuita.calendarios.guardias.mensaje.existe");

					if (letrado.getInscripcionGuardia() != null) {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
								letrado.getInscripcionGuardia().getIdInstitucion(),
								letrado.getInscripcionGuardia().getIdturno(),
								letrado.getInscripcionGuardia().getIdGuardia(),
								letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
					} else if (letrado.getInscripcionTurno() != null) {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
								letrado.getInscripcionTurno().getIdinstitucion().toString(),
								letrado.getInscripcionTurno().getIdturno().toString(), null,
								letrado.getInscripcionTurno().getIdpersona().toString(), fechaAlta);
					} else {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias, fechaInicioPSt, fechaFinPSt, today,
								letrado.getInscripcionGuardia().getIdInstitucion(),
								letrado.getInscripcionGuardia().getIdturno(),
								letrado.getInscripcionGuardia().getIdGuardia(),
								letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
					}
					
					Boolean separarGuardia = separarGuardias(guardiasTurnoItem);
					
					if(separarGuardia) {
						// Paso2: inserto un registro por dia de guardia en cada guardia:
						iter = alDiasPeriodo.iterator();
						while (iter.hasNext()) {
							fechaPeriodo = (String) iter.next();
							ScsGuardiascolegiado beanGuardiasColegiado = createBeanGuardiasColegiado(letrado, fechaInicioPeriodo, fechaPeriodo, guardiasTurnoItem, usuModificacion);
							insertGuardiaColegiado(fechaInicioPeriodo, fechaPeriodo, formatter, beanGuardiasColegiado);
						}
					} else {

						//Calcular la fecha del fin del grupo de guardías
						fechaPeriodo = "30/09/2025";				
						ScsGuardiascolegiado beanGuardiasColegiado = createBeanGuardiasColegiado(letrado, fechaInicioPeriodo, fechaPeriodo, guardiasTurnoItem, usuModificacion);
						insertGuardiaColegiado(fechaInicioPeriodo, fechaPeriodo, formatter, beanGuardiasColegiado);
					}
					
					
					// (L) Refactorizado
					Map<String, Object> mapLog10 = new HashMap();
					String g = "";
					String t = "";
					String idGuardiaVin = "";
					String idTurnoVin = "";
					String nombreLog = "";
					
					if(letrado.getIdGuardia()!=null) {
						g = "con guadia " + new Integer(letrado.getIdGuardia());
						idGuardiaVin = letrado.getIdGuardia().toString();
						t = "con turno " + new Integer(letrado.getIdTurno());
						idTurnoVin = letrado.getIdTurno().toString();
					}
					else if (letrado.getInscripcionGuardia() != null) {
						g = "con guadia " + new Integer(letrado.getInscripcionGuardia().getIdGuardia());
						idGuardiaVin = letrado.getInscripcionGuardia().getIdGuardia().toString();
						t = "con turno " + new Integer(letrado.getInscripcionGuardia().getIdTurno());
						idTurnoVin = letrado.getInscripcionGuardia().getIdTurno();
					} else if (letrado.getInscripcionTurno() != null) {
						g = "con guadia null";
						t = "con turno " + new Integer(letrado.getInscripcionTurno().getIdturno());
					}
					nombreLog = scsGuardiasturnoExtendsMapper.searchNombresGuardiaByIdturnoIdguardia(beanCabeceraGuardias.getIdinstitucion().toString(), idGuardiaVin, idTurnoVin);
					
					mapLog10.put("*Almacenando guardia ", nombreLog);
					listaDatosExcelGeneracionCalendarios.add(mapLog10);
					LOGGER.info("*Almacenando guardia " + g + " y " + t + " " + nombreLog );
					Map<String, Object> mapLog11 = new HashMap();
					mapLog11.put("","");
					listaDatosExcelGeneracionCalendarios.add(mapLog11);
				}catch(Exception e) {
					LOGGER.error("Error: " + e.getMessage());
					if(e.getMessage().equals("gratuita.calendarios.guardias.mensaje.existe")) {
						String ap2 = null;
						String ap1 = null;
						String nombre = null;
						if (letrado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog3 = new HashMap();
							if (letrado.getInscripcionGuardia().getApellido2() != null)
								ap2 = letrado.getInscripcionGuardia().getApellido2().toString();
							if (letrado.getInscripcionGuardia().getApellido1() != null)
								ap1 = letrado.getInscripcionGuardia().getApellido1().toString();
							if (letrado.getInscripcionGuardia().getNombre() != null)
								nombre = letrado.getInscripcionGuardia().getNombre().toString();
							String letradoName = ap1 + " " + ap2 + ", " + nombre; 
							
							mapLog3.put("*Letrado "+letradoName+" no valido, asignación guardia ", letrado.getIdGuardia() + " y turno " + letrado.getIdTurno());
							listaDatosExcelGeneracionCalendarios.add(mapLog3);
							LOGGER.info("*Letrado "+letradoName+" no valido, asignación guardia "+ letrado.getIdGuardia() + " y turno " + letrado.getIdTurno());
						} else if (letrado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();

							if (letrado.getInscripcionTurno().getApellidos2() != null)
								ap2 = letrado.getInscripcionTurno().getApellidos2().toString();
							if (letrado.getInscripcionTurno().getApellidos1() != null)
								ap1 = letrado.getInscripcionTurno().getApellidos1().toString();
							if (letrado.getInscripcionTurno().getNombre() != null)
								nombre = letrado.getInscripcionTurno().getNombre().toString();
							String letradoName = ap1 + " " + ap2 + ", " + nombre;
							mapLog2.put("*Letrado "+letradoName+" no valido, asignación guardia ", letrado.getIdGuardia() + " y turno " + letrado.getIdTurno());
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Letrado "+letradoName+" no valido, asignación guardia "+ letrado.getIdGuardia() + " y turno " + letrado.getIdTurno());
						}
					}else {
						throw e;
					}
					
				}
				
			}
		}

//		Map<String, Object> mapLog10 = new HashMap();
//		String g = "";
//		String t = "";
//		if (letrado.getInscripcionGuardia() != null) {
//			g = "con guadia " + new Integer(letrado.getInscripcionGuardia().getIdGuardia());
//			t = "con turno " + new Integer(letrado.getInscripcionGuardia().getIdturno());
//		} else if (letrado.getInscripcionTurno() != null) {
//			g = "con guadia null";
//			t = "con turno " + new Integer(letrado.getInscripcionTurno().getIdturno());
//		}
//		mapLog10.put("*Almacenando guardia ", g + " y " + t);
//		listaDatosExcelGeneracionCalendarios.add(mapLog10);
//		LOGGER.info("*Almacenando guardia " + g + " y " + t);
//		Map<String, Object> mapLog11 = new HashMap();
//		mapLog11.put("","");
//		listaDatosExcelGeneracionCalendarios.add(mapLog11);

	}
	
	private Boolean separarGuardias(GuardiasTurnoItem guardiasTurnoItem) {
		
		//Se comprueba si el check de agrupar guardias está marcado.
		try {
			//Aquí habría que comprobar el check, y si no hay que separar se coge la primera fecha y la última y se hace solo un insert
			String agruparGuardia = scsHitofacturableguardiaExtendsMapper.getAgruparGuardia(guardiasTurnoItem.getIdInstitucion().toString(),
					guardiasTurnoItem.getIdTurno().toString(), guardiasTurnoItem.getIdGuardia().toString());
			LOGGER.info("*Agrupar guardia: " + agruparGuardia);
			if(agruparGuardia.equals("0")){
				LOGGER.info("*Separar guardia: activado");
				return true;
			}
		} catch(Exception e) {
			LOGGER.info("Error en la consulta separarGuadia");
			LOGGER.info(e.getStackTrace().toString());
		}
		LOGGER.info("*Separar guardia: desactivado");
		return false;
	}
	
	private void insertGuardiaColegiado(String fechaInicioPeriodo, String fechaPeriodo, SimpleDateFormat formatter, ScsGuardiascolegiado beanGuardiasColegiado) {
		
		String fechaInicioPSt = changeDateFormat(DATE_SHORT_SLASH, DATE_LONG, fechaInicioPeriodo);
		String fechaFinPSt = changeDateFormat(DATE_SHORT_SLASH, DATE_LONG, fechaPeriodo);
		String today = changeDateFormat(DATE_SHORT_SLASH, DATE_LONG, formatter.format(new Date()));
		scsGuardiascolegiadoMapper.insertSelective2(beanGuardiasColegiado, fechaInicioPSt, fechaFinPSt,
				today); // generado
		try {
			beanGuardiasColegiado.setFechainicio(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioPSt));
			beanGuardiasColegiado.setFechafin(new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinPSt));
			this.triggerGuardiaColegiadoAID(beanGuardiasColegiado, 1);
		} catch (Exception e) {
			LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 1 (insert)");
		}
	}
	
	private ScsGuardiascolegiado createBeanGuardiasColegiado(LetradoInscripcionItem letrado, String fechaInicioPeriodo, String fechaPeriodo, 
			GuardiasTurnoItem guardiasTurnoItem, Integer usuModificacion) {
		
		ScsGuardiascolegiado beanGuardiasColegiado = new ScsGuardiascolegiado();
		if (letrado.getInscripcionGuardia() != null) {
			beanGuardiasColegiado
					.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
			beanGuardiasColegiado.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
			beanGuardiasColegiado.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
			beanGuardiasColegiado.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
		} else if (letrado.getInscripcionTurno() != null) {
			beanGuardiasColegiado
					.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
			beanGuardiasColegiado.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
			beanGuardiasColegiado.setIdguardia(null);
			beanGuardiasColegiado.setIdpersona(new Long(letrado.getInscripcionTurno().getIdpersona()));
		}

//			beanGuardiasColegiado.setIdinstitucion(letrado.getIdinstitucion());
//			beanGuardiasColegiado.setIdturno(letrado.getIdturno());
//			beanGuardiasColegiado.setIdguardia(letrado.getIdguardia());
//			beanGuardiasColegiado.setIdpersona(letrado.getIdpersona());
		beanGuardiasColegiado.setFechainicio(new Date(fechaInicioPeriodo));
		beanGuardiasColegiado.setFechafin(new Date(fechaPeriodo));
		// Duracion segun tipoDias MES, SEMANA, QUINCENA, DIAS
		int duracion = convertirUnidadesDuracion(guardiasTurnoItem.getTipodiasGuardia());
		// beanGuardiasColegiado.setDiasguardia(new
		// Long(beanGuardiasTurno1.getDiasGuardia().longValue()));
		beanGuardiasColegiado.setDiasguardia(new Long(duracion));
		beanGuardiasColegiado.setDiasacobrar(new Long(guardiasTurnoItem.getDiasPagados().longValue()));
		beanGuardiasColegiado.setFechamodificacion(new Date());

		beanGuardiasColegiado.setUsumodificacion(usuModificacion);
		beanGuardiasColegiado.setReserva("N");
		beanGuardiasColegiado.setObservaciones("-");
		beanGuardiasColegiado.setFacturado("N");
		beanGuardiasColegiado.setPagado("N");
		beanGuardiasColegiado.setIdfacturacion(null);
		
		return beanGuardiasColegiado;
	}

	private int convertirUnidadesDuracion(String tipoDiasGuardia) {
		int unidades = 0;

		try {
			switch (tipoDiasGuardia.charAt(0)) {
			case 'D':
				unidades = Calendar.DAY_OF_YEAR;
				break;// 6
			case 'S':
				unidades = Calendar.WEEK_OF_YEAR;
				break;// 3
			case 'Q':
				unidades = CalendarioEfectivo.QUINCENA;
				break;// 1524
			case 'M':
				unidades = Calendar.MONTH;
				break;// 2
			}
		} catch (Exception e) {
			unidades = 0;
		}
		return unidades;
	}

	/**
	 * Metodo que genera una array de periodos de de fecha a partir de otro. La
	 * restriccion que tenemos para partir el array inicial es que contenga dias que
	 * se agrupan(que vienen en alDiasAgrupar)
	 *
	 * @param alPeriodo
	 * @param alDiasAgrupar
	 * @return
	 * @throws Exception
	 */
	private List getPeriodos(ArrayList alPeriodo, List alDiasAgrupar) throws Exception {
		List lPeriodosSinAgrupar = new ArrayList();
		try {

			if (alDiasAgrupar == null || alDiasAgrupar.size() < 1) {
				lPeriodosSinAgrupar.add(alPeriodo);
				return lPeriodosSinAgrupar;
			}
			List alDias = new ArrayList();
			for (int i = 0; i < alPeriodo.size(); i++) {
				String date = (String) alPeriodo.get(i);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date(date));
				if (alDiasAgrupar.contains(new Integer(c.get(Calendar.DAY_OF_WEEK)))) {
					// añadimos el acuemulado anterios si no esta vacio
					if (alDias.size() > 0) {
						lPeriodosSinAgrupar.add(alDias);

					}
					// Creamos un nuevo almacen para el dia
					alDias = new ArrayList();
					alDias.add(date);
					// lo añadimos al que devolveremos
					lPeriodosSinAgrupar.add(alDias);
					// inicializamos de nuevo el acumulador de dias
					alDias = new ArrayList();

				} else {
					alDias.add(date);
				}
			}
			// añadimos el último
			if (alDias.size() > 0)
				lPeriodosSinAgrupar.add(alDias);
		} catch (Exception e) {
			errorGeneracionCalendario = "Error al generar una array de periodos de de fecha a partir de otro: " + e;
		}

		return lPeriodosSinAgrupar;
	}

	public boolean validaGuardiaLetradoPeriodo(Short idInstitucion, Integer idTurno, Integer idGuardia, Long idPersona,
			String fechaInicio, String fechaFin) throws Exception {

		boolean existeGuardiaLetrado = false;
		String consulta = "";

		try {
			int cabecera = scsGuardiasturnoExtendsMapper.validaGuardiaLetradoPeriodo(idPersona.toString(),
					idInstitucion.toString(), idTurno.toString(), idGuardia.toString(), fechaInicio, fechaFin);

			if (cabecera == 0)
				existeGuardiaLetrado = false;
			else
				existeGuardiaLetrado = true;
		} catch (Exception e) {
			errorGeneracionCalendario = "Error al validar GuardiaLetradoPeriodo: " + e;
			throw new Exception(e + ": Excepcion en ScsCabeceraGuardiasAdm.validaGuardiaLetradoPeriodo().");
		}
		return existeGuardiaLetrado;
	}

	public String getValor(String idInstitucion, String idModulo, String idParametro, String valorDefecto)
			throws Exception {
		Hashtable htData = new Hashtable();
		List<String> instituciones = new ArrayList<>();
		String salida = valorDefecto;
		try {
//			htData = getValores(idModulo, idParametro);
			// ya tengo todos los path de las instituciones
			// miro si existe para la mia
//			if (htData.containsKey(idInstitucion)) {
//				salida = (String) htData.get(idInstitucion);
//			} else {
//				salida = (String) htData.get("0");
//			}
			// DUDA

			instituciones = scsGuardiasturnoExtendsMapper.getInstitucionParam(idModulo, idParametro);
			if (instituciones.contains(idInstitucion)) {
				salida = "S";
			} else {
				salida = "N";
			}
		} catch (Exception e) {
			throw new Exception(e + "Error al obtener el valor del parámetro " + idParametro);
		}
		if (salida == null) {
			return valorDefecto;
		} else {
			return salida;
		}
	}

	private LetradoInscripcionItem getSiguienteLetrado(List<LetradoInscripcionItem> alCompensaciones,
			List alLetradosOrdenados, Puntero punteroLetrado, ArrayList diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales) throws Exception {
		LetradoInscripcionItem letradoGuardia, auxLetradoSeleccionado;

		letradoGuardia = null;
		try {
			// recorriendo compensaciones
			if (alCompensaciones != null && alCompensaciones.size() > 0) {

				Iterator<LetradoInscripcionItem> iterador = alCompensaciones.iterator();
				while (iterador.hasNext()) {
					auxLetradoSeleccionado = (LetradoInscripcionItem) iterador.next();
					// log.addLog(new String[] {"Probando Letrado Compensado",
					// auxLetradoSeleccionado.toString()});
					Map<String, Object> mapLog = new HashMap();
//				if (auxLetradoSeleccionado.getPersona() != null) {
//				Map<String, Object> mapLog2 = new HashMap();
//				mapLog2.put("*Probando Letrado Compensado ", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				listaDatosExcelGeneracionCalendarios.add(mapLog2);
//				LOGGER.info("*Probando Letrado Compensado " + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				}
					String nombre = "";
					String ap1 = "";
					String ap2 = "";
					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
						mapLog2.put("*Probando Letrado Compensado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						 LOGGER.info("*Probando Letrado Compensado " + ap1 + " " + ap2 + ", " +
						 nombre);
					} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
						mapLog2.put("*Probando Letrado Compensado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Probando Letrado Compensado " + ap1 + " " + ap2 + ", " + nombre);
					}
					//TODO(L) INCOMPATIBILIDADES
					if (comprobarRestriccionesLetradoCompensado(auxLetradoSeleccionado, diasGuardia, iterador, null,
							hmBajasTemporales,hmPersonasConSaltos)) {
						letradoGuardia = auxLetradoSeleccionado;
						break;
					} else {
						if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
							mapLog2.put("*Letrado Compensado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Letrado Compensado no valido " + ap1 + " " + ap2 + ", " + nombre);

						} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
							mapLog2.put("*Letrado Compensado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
//							LOGGER.info("*Letrado Compensado no valido " + ap2 + " " + ap1 + ", " + nombre);
						}
						// log.addLog(new String[] {"Letrado Compensado no valido",
						// auxLetradoSeleccionado.toString()});
//					Map<String, Object> mapLog3 = new HashMap();
//					mapLog3.put("*Letrado Compensado no valido", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//					listaDatosExcelGeneracionCalendarios.add(mapLog3);
//					LOGGER.info("*Letrado Compensado no valido" + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
					}

				}
			}
			//Actualizamos saltos
			if (letradoGuardia != null)
				return letradoGuardia;
			LOGGER.info("Recorriendo la cola INICIO");
			// recorriendo la cola
			if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {

				int fin = punteroLetrado.getValor();
				do {
					auxLetradoSeleccionado = (LetradoInscripcionItem) alLetradosOrdenados
							.get(punteroLetrado.getValor());
					// log.addLog(new String[] {"Probando Letrado",
					// auxLetradoSeleccionado.toString()});
					String nombre = "";
					String ap1 = "";
					String ap2 = "";

					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
						Map<String, Object> mapLog = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
						mapLog.put("*Probando Letrado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog);
//						LOGGER.info("*Probando Letrado " + ap2 + " " + ap1 + ", " + nombre);
					} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
						mapLog2.put("*Probando Letrado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
//						LOGGER.info("*Probando Letrado " + ap2 + " " + ap1 + ", " + nombre);
					}
					// vale
					if (comprobarRestriccionesLetradoCola(auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos,
							hmBajasTemporales, false))
						letradoGuardia = auxLetradoSeleccionado;
					else {
						if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog3 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
							mapLog3.put("*Letrado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog3);
//							LOGGER.info("*Letrado no valido " + ap2 + " " + ap1 + ", " + nombre);
						} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();

							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
							mapLog2.put("*Letrado no valido  ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
//							LOGGER.info("*Letrado no valido  " + ap2 + " " + ap1 + ", " + nombre);
						}
						// log.addLog(new String[] {"Letrado no valido",
						// auxLetradoSeleccionado.toString()});
					}

					// obteniendo siguiente en la cola
					if (punteroLetrado != null && punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
						punteroLetrado.setValor(punteroLetrado.getValor() + 1);
					else
						punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio

				} while (letradoGuardia == null && fin != punteroLetrado.getValor());
			}
			LOGGER.info("Recorriendo la cola FIN");
		} catch (Exception e) {// TODO ERROR java.lang.NumberFormatException: null
			errorGeneracionCalendario = "Error obteniendo el siguiente letrado a asignar: " + e;
		}
		if (letradoGuardia != null) {
			return letradoGuardia;
		} else {
			return null;
		}

	} // getSiguienteLetrado()
	
	private LetradoInscripcionItem getSiguienteLetrado3(GuardiasCalendarioItem guardiasCalendarioItem, List<LetradoInscripcionItem> alCompensaciones,
			List alLetradosOrdenados, Puntero punteroLetrado, ArrayList diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, List< BajasTemporalesItem>> hmBajasTemporales) throws Exception {
				
		LetradoInscripcionItem letradoGuardia, auxLetradoSeleccionado;
		
		boolean esGrupo = false;
		letradoGuardia = null;
		try {
			// recorriendo compensaciones
			if (alCompensaciones != null && alCompensaciones.size() > 0) {

				Iterator<LetradoInscripcionItem> iterador = alCompensaciones.iterator();
				while (iterador.hasNext()) {
					auxLetradoSeleccionado = (LetradoInscripcionItem) iterador.next();
					// log.addLog(new String[] {"Probando Letrado Compensado",
					// auxLetradoSeleccionado.toString()});
					Map<String, Object> mapLog = new HashMap();
//				if (auxLetradoSeleccionado.getPersona() != null) {
//				Map<String, Object> mapLog2 = new HashMap();
//				mapLog2.put("*Probando Letrado Compensado ", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				listaDatosExcelGeneracionCalendarios.add(mapLog2);
//				LOGGER.info("*Probando Letrado Compensado " + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				}
					String nombre = "";
					String ap1 = "";
					String ap2 = "";
					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
						mapLog2.put("*Probando Letrado Compensado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						 LOGGER.info("*Probando Letrado Compensado " + ap1 + " " + ap2 + ", " +
						 nombre);
					} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
						mapLog2.put("*Probando Letrado Compensado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Probando Letrado Compensado " + ap1 + " " + ap2 + ", " + nombre);
					}
					//TODO(L) INCOMPATIBILIDADES
					if (comprobarRestriccionesLetradoCompensado3(guardiasCalendarioItem, auxLetradoSeleccionado, diasGuardia, iterador, null,
							hmBajasTemporales,hmPersonasConSaltos,esGrupo)) {
						letradoGuardia = auxLetradoSeleccionado;
						break;
					} else {
						if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
							mapLog2.put("*Letrado Compensado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Letrado Compensado no valido " + ap1 + " " + ap2 + ", " + nombre);

						} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
							mapLog2.put("*Letrado Compensado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
//							LOGGER.info("*Letrado Compensado no valido " + ap2 + " " + ap1 + ", " + nombre);
						}
						// log.addLog(new String[] {"Letrado Compensado no valido",
						// auxLetradoSeleccionado.toString()});
//					Map<String, Object> mapLog3 = new HashMap();
//					mapLog3.put("*Letrado Compensado no valido", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//					listaDatosExcelGeneracionCalendarios.add(mapLog3);
//					LOGGER.info("*Letrado Compensado no valido" + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
					}

				}
			}
			//Actualizamos saltos
			if (letradoGuardia != null)
				return letradoGuardia;
			LOGGER.info("Recorriendo la cola INICIO");
			// recorriendo la cola
			if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {

				int fin = punteroLetrado.getValor();
				do {
					auxLetradoSeleccionado = (LetradoInscripcionItem) alLetradosOrdenados
							.get(punteroLetrado.getValor());
					// log.addLog(new String[] {"Probando Letrado",
					// auxLetradoSeleccionado.toString()});
					String nombre = "";
					String ap1 = "";
					String ap2 = "";

					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
						Map<String, Object> mapLog = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
						if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
						mapLog.put("*Probando Letrado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog);
						LOGGER.info("*Probando Letrado " + ap2 + " " + ap1 + ", " + nombre);
					} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
							ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
							ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
						if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
							nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
						mapLog2.put("*Probando Letrado ", ap1 + " " + ap2 + ", " + nombre);
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
//						LOGGER.info("*Probando Letrado " + ap2 + " " + ap1 + ", " + nombre);
					}
					// vale
					if (comprobarRestriccionesLetradoCola3(guardiasCalendarioItem, auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos,
							hmBajasTemporales, false, esGrupo))
						letradoGuardia = auxLetradoSeleccionado;
					else {
						if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog3 = new HashMap();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getApellido1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString();
							if (auxLetradoSeleccionado.getInscripcionGuardia().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString();
							mapLog3.put("*Letrado no valido ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog3);
//							LOGGER.info("*Letrado no valido " + ap2 + " " + ap1 + ", " + nombre);
						} else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();

							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos2() != null)
								ap2 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getApellidos1() != null)
								ap1 = auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString();
							if (auxLetradoSeleccionado.getInscripcionTurno().getNombre() != null)
								nombre = auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString();
							mapLog2.put("*Letrado no valido  ", ap1 + " " + ap2 + ", " + nombre);
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
//							LOGGER.info("*Letrado no valido  " + ap2 + " " + ap1 + ", " + nombre);
						}
						// log.addLog(new String[] {"Letrado no valido",
						// auxLetradoSeleccionado.toString()});
					}

					// obteniendo siguiente en la cola
					if (punteroLetrado != null && punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
						punteroLetrado.setValor(punteroLetrado.getValor() + 1);
					else
						punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio

				} while (letradoGuardia == null && fin != punteroLetrado.getValor());
			}
			LOGGER.info("Recorriendo la cola FIN");
		} catch (Exception e) {
			errorGeneracionCalendario = "Error obteniendo el siguiente letrado a asignar: " + e;
		}
		if (letradoGuardia != null) {
			return letradoGuardia;
		} else {
			return null;
		}

	} // getSiguienteLetrado()


	/**
	 * Asigna un orden a los letrados inscritos en un grupo de guardia pero si
	 * apuntados en él, en orden consecutivo a partir del ultimo letrado apuntado en
	 * el grupo de guardia.
	 */
	public void reordenarRestoGrupoLetrados(int idGrupoGuardia, int sizeGrupo) {
		boolean repetido = false;
		Hashtable hash = new Hashtable();
		StringBuffer sql = new StringBuffer();
		int orden = 0;
		ArrayList<Hashtable<String, String>> grupo = new ArrayList<Hashtable<String, String>>();
		sql.append(" SELECT gg.idgrupoguardia, ggc.idgrupoguardiacolegiado, orden ");
		sql.append(" FROM scs_grupoguardiacolegiado ggc, scs_grupoguardia gg ");
		sql.append(" WHERE ggc.idgrupoguardia = gg.idgrupoguardia ");
		sql.append(" AND gg.idgrupoguardia =" + idGrupoGuardia + " ");
		sql.append(" AND ggc.orden > " + 30000 + " ");
		sql.append(" ORDER BY gg.idgrupoguardia, orden");
		try {
			List<GrupoGuardiaRowItem> registro = scsGuardiasturnoExtendsMapper
					.reordenarRestoGrupoLetrados(String.valueOf(idGrupoGuardia));
			if (registro != null) {
				for(GrupoGuardiaRowItem item : registro) {
					orden = sizeGrupo + 1;
					updateOrderBBDD(item.getIdGrupoGuardia(), orden,
							item.getIdGrupoGuardiaColegiado());
				}
				
			}
		} catch (Exception e) {
			errorGeneracionCalendario = "Error reordenando los letrados inscritos en un grupo de guardia: " + e;
			// throw new ClsExceptions (e,
			// "Error al recuperar la posicion del ultimo letrado");
		}
	}

	private List<String> getCalendariosByIdCalendario(DatosCalendarioyProgramacionItem item, String idInstitucion) {

		List<GuardiaCalendarioItem> datos = new ArrayList<GuardiaCalendarioItem>();
		List<String> lista = new ArrayList<>();

		datos = scsGuardiasturnoExtendsMapper.getGuardiasFromCalendar(item.getIdCalendarioProgramado(), idInstitucion,
				item.getFechaDesde(), item.getFechaHasta());
		for (int i = 0; i < datos.size(); i++) {
			List<String> idCalendarioGuardia = scsGuardiasturnoExtendsMapper
					.getIdCalendarioGuardiasFromTurnosGuardiasList(datos.get(i).getIdTurno(),
							datos.get(i).getIdGuardia(), idInstitucion, item.getFechaDesde(), item.getFechaHasta());
			if (idCalendarioGuardia.size() != 0) {
				datos.get(i).setIdCalendarioGuardia(idCalendarioGuardia.get(0));
			}
			//datos.get(i).setOrden(Integer.toString(i + 1));
		}

		datos.forEach(dato -> {
			lista.add(dato.getIdCalendarioGuardia());
		});

		return lista;
	}

	@Override
	public ByteArrayInputStream descargarZIPExcelLog(HttpServletRequest request,
			List<DatosCalendarioyProgramacionItem> calyprogItemList) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> nombresFicherosList = new ArrayList<>();
		List<String> listaIdCalendarioGuardia = new ArrayList<>();
		;
		String nombreLog = null;

		for (int i = 0; i < calyprogItemList.size(); i++) {

			listaIdCalendarioGuardia = getCalendariosByIdCalendario(calyprogItemList.get(i), idInstitucion.toString());

			for (int j = 0; j < listaIdCalendarioGuardia.size(); j++) {
				nombreLog = scsCalendarioguardiasMapper.getLogName(idInstitucion.toString(),
						calyprogItemList.get(i).getIdTurno(), calyprogItemList.get(i).getIdGuardia(),
						listaIdCalendarioGuardia.get(j).toString());
				if (nombreLog != null) {
					nombresFicherosList.add(nombreLog);
				}
			}
		}

		if (!nombresFicherosList.isEmpty()) {
			ByteArrayOutputStream byteArrayOutputStream = null;

			try {

				byteArrayOutputStream = new ByteArrayOutputStream();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
				ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

				for (String nombreFichero : nombresFicherosList) {
					String pathFicheroSalida = getRutaFicheroSalida(idInstitucion.toString());
					String nombreFicheroSalida = nombreFichero + ".xlsx";
					zipOutputStream.putNextEntry(new ZipEntry(nombreFicheroSalida));
					String path = pathFicheroSalida + File.separator + nombreFicheroSalida;
					File file = new File(path);
					FileInputStream fileInputStream = new FileInputStream(file);
					IOUtils.copy(fileInputStream, zipOutputStream);
					fileInputStream.close();
				}

				zipOutputStream.closeEntry();

				if (zipOutputStream != null) {
					zipOutputStream.finish();
					zipOutputStream.flush();
					IOUtils.closeQuietly(zipOutputStream);
				}

				IOUtils.closeQuietly(bufferedOutputStream);
				IOUtils.closeQuietly(byteArrayOutputStream);

			} catch (IOException e) {
				e.printStackTrace();
			}

			return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} else {
			return null;
		}
	}

	@Override
	public DatosDocumentoItem descargarExcelLog(HttpServletRequest request,
			DatosCalendarioyProgramacionItem calyprogItem) {
		String directorioPlantillaClase = "";
		DatosDocumentoItem docGenerado = new DatosDocumentoItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ArrayList<String> nombresConsultasDatos = null;
		String pathPlantilla = null;
		String nombreLog = scsCalendarioguardiasMapper.getLogName(idInstitucion.toString(), calyprogItem.getIdTurno(),
				calyprogItem.getIdGuardia(), calyprogItem.getIdCalendarioGuardias());
		LOGGER.info("descargarExcelLog() -> NOMBRE LOG A DESCARGAR : " + nombreLog);
		LOGGER.info("descargarExcelLog() - > INFO CAL A DESCARGAR - > Turno:" + calyprogItem.getIdTurno() +  " / Guardia: " + calyprogItem.getIdGuardia() + "/ idCalG: " + calyprogItem.getIdCalendarioGuardias());
		try {
			String pathFicheroSalida = getRutaFicheroSalida(idInstitucion.toString());
			String nombreFicheroSalida = nombreLog + ".xlsx";
			String path = pathFicheroSalida + nombreFicheroSalida;
			LOGGER.info("descargarExcelLog() -> NOMBRE PATH : " + path);
			
			File file = new File(path);
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.close();
			docGenerado.setDocumentoFile(file);
			docGenerado.setFileName(nombreFicheroSalida);
			docGenerado.setDatos(Files.readAllBytes(file.toPath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docGenerado;
	}

	public DatosDocumentoItem generarExcelLog(String nombreLog) {
		String directorioPlantillaClase = "";
		DatosDocumentoItem docGenerado = null;
		ArrayList<String> nombresConsultasDatos = null;
		String pathPlantilla = null;
		//String pathFicheroSalida = "sjcs.directorioFisicoGeneracionCalendarios.{" + idInstitucion1.toString() + "}";
		List<List<Map<String, Object>>> listaDatosExcelGeneracionCalendarios2 = new ArrayList<>();
		List<String> nombresFicherosList = new ArrayList<>();

		try {
			listaDatosExcelGeneracionCalendarios2.add(listaDatosExcelGeneracionCalendarios);
			String pathFicheroSalida = getRutaFicheroSalida(idInstitucion1.toString());
			docGenerado = _generacionDocService.generarExcelGeneracionCalendario(pathFicheroSalida, nombreLog + ".xlsx",
					listaDatosExcelGeneracionCalendarios2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docGenerado;
	}
	
	public DatosDocumentoItem generarExcelLog3(String nombreLog, String idInstitucion) {
		String directorioPlantillaClase = "";
		DatosDocumentoItem docGenerado = null;
		ArrayList<String> nombresConsultasDatos = null;
		String pathPlantilla = null;
		//String pathFicheroSalida = "sjcs.directorioFisicoGeneracionCalendarios.{" + idInstitucion1.toString() + "}";
		List<List<Map<String, Object>>> listaDatosExcelGeneracionCalendarios2 = new ArrayList<>();
		List<String> nombresFicherosList = new ArrayList<>();

		try {
			listaDatosExcelGeneracionCalendarios2.add(listaDatosExcelGeneracionCalendarios);
			String pathFicheroSalida = getRutaFicheroSalida(idInstitucion);
			docGenerado = _generacionDocService.generarExcelGeneracionCalendario(pathFicheroSalida, nombreLog + ".xlsx",
					listaDatosExcelGeneracionCalendarios2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docGenerado;
	}

	private String getRutaFicheroSalida(String idInstitucion) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.parametroRutaCalendarios);

		GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);

		String rutaCal = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion
				+ SigaConstants.pathSeparator ;

		return rutaCal;
	}

	@Override
	public InscripcionesResponseDTO getInscripciones(InscripcionDatosEntradaDTO inscripcionesBody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		InscripcionesResponseDTO ins = new InscripcionesResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getInscripciones() -> Entrada para obtener las inscripciones");

				inscripciones = scsInscripcionguardiaExtendsMapper.getListadoInscripciones(inscripcionesBody,
						idInstitucion.toString());

				for (BusquedaInscripcionItem inscrip : inscripciones) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date fechaSolicitud;
					try {
						fechaSolicitud = sdf.parse(inscrip.getFechaSol());
						inscrip.setFechasolicitud(fechaSolicitud);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				LOGGER.info("getInscripciones() -> Salida ya con los datos recogidos");
			}
		}

		ins.setInscripcionesItem(inscripciones);
		return ins;
	}

	@Override
	public UpdateResponseDTO validarInscripciones(BusquedaInscripcionItem validarBody, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<BusquedaInscripcionItem> inscripciones = new
		// ArrayList<BusquedaInscripcionItem>();
		String inscripciones = null;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"validarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("validarInscripciones() -> Entrada para obtener las inscripciones");

				// DESCOMENTAR CUANDO SUBA CRISTINA
				// inscripciones =
				// scsInscripcionguardiaExtendsMapper.getValidarInscripciones(validarBody,
				// idInstitucion.toString());

				LOGGER.info("validarInscripciones() -> Salida ya con los datos recogidos");
			}
		}

		upd.setStatus(inscripciones);
		return upd;
	}

	public StringDTO getTipoDiaGuardia(HttpServletRequest request, String idTurno, String idGuardia) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO stringDTO = new StringDTO();
		Error error = new Error();

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				this.LOGGER.info(
						"getTipoDiaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info(
						"getTipoDiaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					org.itcgae.siga.DTO.scs.GuardiasItem guardia = this.scsGuardiasturnoExtendsMapper
							.getTipoDiaGuardia(idTurno, idGuardia, idInstitucion);
					if (guardia != null) {
						guardia.setTipoDia("Labor. " + guardia.getSeleccionLaborables() + ", Fest. "
								+ guardia.getSeleccionFestivos());
						String tipoDiaNoNull = guardia.getTipoDia().replace("null", "");
						stringDTO.setValor(tipoDiaNoNull);
					}
				}
			}
		} catch (Exception var13) {
			this.LOGGER.error("getTipoDia() / ERROR: " + var13.getMessage(), var13);
			error.setCode(500);
			error.setMessage("Error al buscar los dias laborales y festivos de la guardia: " + var13);
			error.description("Error al buscar los dias laborales y festivos de la guardia: " + var13);
		}

		return stringDTO;
	}

	@Override
	public UpdateResponseDTO denegarInscripciones(BusquedaInscripcionItem denegarBody, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<BusquedaInscripcionItem> inscripciones = new
		// ArrayList<BusquedaInscripcionItem>();
		String inscripciones = null;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"denegarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("denegarInscripciones() -> Entrada para obtener las inscripciones");

				// DESCOMENTAR CUANDO SUBA CRISTINA
				// inscripciones =
				// scsInscripcionguardiaExtendsMapper.getDenegarInscripciones(denegarBody,
				// idInstitucion.toString());

				LOGGER.info("denegarInscripciones() -> Salida ya con los datos recogidos");
			}
		}

		upd.setStatus(inscripciones);
		return upd;
	}

	@Override
	public GuardiasDTO busquedaGuardiasColegiado(GuardiasItem guardiaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<BusquedaInscripcionItem> inscripciones = new
		// ArrayList<BusquedaInscripcionItem>();
		String inscripciones = null;
		GuardiasDTO guardiasDTO = new GuardiasDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS)
						.andParametroEqualTo(SigaConstants.TAM_MAX_CONSULTA_JG)
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause(SigaConstants.C_IDINSTITUCION + " DESC");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"searchGuardias() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				List<GuardiasItem> guardiasColegiado = scsCabeceraguardiasExtendsMapper
						.busquedaGuardiasColegiado(guardiaItem, idInstitucion.toString(), tamMaximo);

				Date fechaActural = new Date();

				for (GuardiasItem guardiaCol : guardiasColegiado) {

					// obtenemos el tipo de dias por guardia.
					GuardiasItem diasGuardias = new GuardiasItem();

					diasGuardias.setIdTurno(guardiaCol.getIdTurno());
					diasGuardias.setIdGuardia(guardiaCol.getIdGuardia());

					List<GuardiasItem> guardiasTurno = scsGuardiasturnoExtendsMapper.searchGuardias2(diasGuardias,
							idInstitucion.toString(), usuarios.get(0).getIdlenguaje(), tamMaximo);

					if (guardiasTurno != null && guardiasTurno.size() > 0) {
						guardiasTurno = guardiasTurno.stream().map(it -> {
							it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
									+ it.getSeleccionFestivos()).replace("null", ""));
							return it;
						}).collect(Collectors.toList());
						guardiaCol.setTipoDiasGuardia(guardiasTurno.get(0).getTipoDia());
					} else {
						guardiaCol.setTipoDiasGuardia("Sin dias en la Guardia.");
					}

					// obtenemos la posicion de un colegiado para un grupo en caso de que esta
					// guardia este en un grupo.
					ScsGrupoguardiacolegiadoExample example = new ScsGrupoguardiacolegiadoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(guardiaCol.getIdTurno()))
							.andIdguardiaEqualTo(Integer.parseInt(guardiaCol.getIdGuardia()))
							.andIdpersonaEqualTo(Long.parseLong(guardiaCol.getIdPersona()));
					List<ScsGrupoguardiacolegiado> grupos = scsGrupoguardiacolegiadoExtendsMapper
							.selectByExample(example);
					if (grupos == null || grupos.isEmpty()) {
						guardiaCol.setOrdenGrupo("Sin Grupo");
					} else {
						guardiaCol.setOrdenGrupo(grupos.get(0).getOrden().toString());
					}

					// Obtencion de la guardia con permuta. Como no se sabe a que puntero hace
					// referencia en SCS_PERMUTAGUARDIA se hace la busqueda en ambos casos
					ScsPermutaCabeceraExample permutaExample = new ScsPermutaCabeceraExample();
					permutaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(guardiaCol.getIdTurno()))
							.andIdguardiaEqualTo(Integer.parseInt(guardiaCol.getIdGuardia()))
							.andIdpersonaEqualTo(Long.parseLong(guardiaCol.getIdPersona()))
							.andIdcalendarioguardiasEqualTo(Integer.parseInt(guardiaCol.getIdCalendarioGuardias()));

					List<ScsPermutaCabecera> tienePermutaCabecera = scsPermutaCabeceraMapper
							.selectByExample(permutaExample);

					// Establecemos el estado
					setEstadoGuardiaCol(idInstitucion, guardiaCol);
					// Ahora comprobamos si tiene permutas
					if (!tienePermutaCabecera.isEmpty()) {
						ScsPermutaguardiasExample permutaGuardiaSolExample = new ScsPermutaguardiasExample();
						permutaGuardiaSolExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdPerCabSolicitanteEqualTo(tienePermutaCabecera.get(0).getIdPermutaCabecera());

						List<ScsPermutaguardias> tienePermutaGuardiaSol = scsPermutaguardiasMapper
								.selectByExample(permutaGuardiaSolExample);

						if (!tienePermutaGuardiaSol.isEmpty()) {
							if (tienePermutaGuardiaSol.get(0).getFechaconfirmacion() == null) {
								guardiaCol.setEstadoGuardia("Permuta Solicitada.");
							}
						} else {
							ScsPermutaguardiasExample permutaGuardiaConfExample = new ScsPermutaguardiasExample();
							permutaGuardiaConfExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdPerCabConfirmadorEqualTo(tienePermutaCabecera.get(0).getIdPermutaCabecera());

							List<ScsPermutaguardias> tienePermutaGuardiaConf = scsPermutaguardiasMapper
									.selectByExample(permutaGuardiaConfExample);
							if (guardiaCol.getEstadoGuardia() == null || guardiaCol.getEstadoGuardia().isEmpty()) {
								guardiaCol.setEstadoGuardia("Sin permuta asociada.");
							}
							if (!tienePermutaGuardiaConf.isEmpty()) {
								if (tienePermutaGuardiaConf.get(0).getFechaconfirmacion() == null) {
									guardiaCol.setEstadoGuardia("Permuta Solicitada.");
								}
							} else {
								if (guardiaCol.getEstadoGuardia() == null || guardiaCol.getEstadoGuardia().isEmpty()) {
									guardiaCol.setEstadoGuardia("Sin permuta asociada.");
								}
							}
						}
					} else {
						if (guardiaCol.getEstadoGuardia() == null || guardiaCol.getEstadoGuardia().isEmpty()) {
							guardiaCol.setEstadoGuardia("Sin permuta asociada.");
						}
					}

				}

				guardiasDTO.setGuardiaItems(guardiasColegiado);
			}
		}

		return guardiasDTO;
	}

	private void setEstadoGuardiaCol(Short idInstitucion, GuardiasItem guardiaCol) {
		if (guardiaCol.getFechadesde() != null && guardiaCol.getFechadesde().after(new Date())) {
			guardiaCol.setEstadoGuardia("dato.jgr.guardiacolegiado.estadoguardia.pendienterealizar");
		}

		if (guardiaCol.getFechadesde() != null && (guardiaCol.getFechadesde().before(new Date()) || guardiaCol.getFechadesde().equals(new Date()))
				/*&& (guardiaCol.getValidada().equals("0") || guardiaCol.getValidada()==null)*/) {
			guardiaCol.setEstadoGuardia("dato.jgr.guardiacolegiado.estadoguardia.realizadanovalidada");
		}

		if (guardiaCol.getFechadesde() != null && guardiaCol.getValidada()!=null && (guardiaCol.getFechadesde().before(new Date()) || guardiaCol.getFechadesde().equals(new Date())) && guardiaCol.getValidada().equals("1")) {
			guardiaCol.setEstadoGuardia("dato.jgr.guardiacolegiado.estadoguardia.realizadavalidada");
		}
		if (guardiaCol.getFechadesde() != null && (guardiaCol.getFechadesde().before(new Date()) ||
				guardiaCol.getFechadesde().equals(new Date())) &&
				(guardiaCol.getValidada() != null && guardiaCol.getValidada().equals("1")) &&
				(guardiaCol.getFacturado() != null && guardiaCol.getFacturado().equals("1"))) {

			if(guardiaCol.getIdFacturacion()!=null) {
				FcsFacturacionjgExample facturacionExample = new FcsFacturacionjgExample();
				facturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdfacturacionEqualTo(guardiaCol.getIdFacturacion());
	
				List<FcsFacturacionjg> facturas = fcsFacturacionJGExtendsMapper.selectByExample(facturacionExample);
				if (facturas!=null && !facturas.isEmpty()) {
					guardiaCol.setEstadoGuardia("dato.jgr.guardiacolegiado.estadoguardia.facturada*" + facturas.get(0).getNombre());
				}
			}

		}
		if(guardiaCol.getEstadoGuardia() == null) {
			guardiaCol.setEstadoGuardia(" ");
		}

	}

	@Override
	@Transactional
	public UpdateResponseDTO validarSolicitudGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("deleteGuardias() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsCabeceraguardias guardia = new ScsCabeceraguardias();
					guardia.setIdinstitucion(idInstitucion);
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());
					guardia.setFechavalidacion(guardiasItem.getFechaValidacion());

					guardiasColegiadoService.validarGuardiaColegiado(guardia);
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteGuardias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO desvalidarGuardiaColegiado(GuardiasItem guardiasItem, HttpServletRequest request) {
		LOGGER.info("deleteGuardias() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsCabeceraguardias guardia = new ScsCabeceraguardias();
					guardia.setIdinstitucion(idInstitucion);
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());
					if (guardiasItem.getFechaValidacion() == null) {
						response = 0;
					} else {
						response = scsCabeceraguardiasExtendsMapper.desvalidarGuardiaColegiado(guardia);
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteGuardias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarGuardiaColegiado(GuardiasItem guardiasItem, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 1;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"eliminarGuardiaColegiados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"eliminarGuardiaColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("eliminarGuardiaColegiado() -> Entrada para borrar las incompatibilidades");

				FcsFacturacionjgExample facturacionExample = new FcsFacturacionjgExample();
				facturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdfacturacionEqualTo(guardiasItem.getIdFacturacion());

				List<FcsFacturacionjg> facturas = fcsFacturacionJGExtendsMapper
						.selectByExample(facturacionExample);
				if (facturas.isEmpty()) {
					ScsCabeceraguardiasKey keyGuardia = new ScsCabeceraguardiasKey();

					keyGuardia.setIdinstitucion(idInstitucion);
					keyGuardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()));
					keyGuardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					keyGuardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					keyGuardia.setFechainicio(guardiasItem.getFechadesde());
					
					response = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keyGuardia);
				}else {
					response = 0;
				}

				LOGGER.info("eliminarGuardiaColegiados() -> Salida ya con los datos recogidos");
			} else {
				response = 0;
			}
		}

		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("eliminarGuardiaColegiado() -> OK. Delete para incompatibilidades realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("eliminarGuardiaColegiado() -> KO. Delete para incompatibilidades NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("eliminarGuardiaColegiado() -> Salida del servicio para eliminar incompatibilidades");
		return deleteResponseDTO;
	}

	private void borrarRegistrosCalendario(DeleteCalendariosProgDatosEntradaItem deleteCalBody,
			Integer idCalendarioGuardias) {
		this.scsGrupoguardiacolegiadoExtendsMapper.deleteRegistrosGrupoGuardiaCol(idCalendarioGuardias);
		this.scsGrupoguardiacolegiadoExtendsMapper.deleteGuardiConcreto(Short.valueOf(deleteCalBody.getIdInstitucion()),Integer.parseInt(deleteCalBody.getIdGuardia()),
				Integer.parseInt(deleteCalBody.getIdTurno()),idCalendarioGuardias,
				deleteCalBody.getFechaDesde(), deleteCalBody.getFechaHasta());
		// Eliminamos el calendario guardia en concreto
		/*ScsCalendarioguardiasKey calKey = new ScsCalendarioguardias();
		calKey.setIdinstitucion(Short.valueOf(deleteCalBody.getIdInstitucion()));
		calKey.setIdguardia(Integer.valueOf(deleteCalBody.getIdGuardia()));
		calKey.setIdturno(Integer.valueOf(deleteCalBody.getIdTurno()));
		calKey.setIdcalendarioguardias(idCalendarioGuardias);
		ScsCalendarioguardias itemToDel = scsCalendarioguardiasMapper.selectByPrimaryKey(calKey);*/
		//if(itemToDel != null)
		//scsCalendarioguardiasMapper.deleteByPrimaryKey(itemToDel);
		//this.scsGrupoguardiacolegiadoExtendsMapper (Short.valueOf(deleteCalBody.getIdInstitucion()
			//	,Integer.valueOf(deleteCalBody.getIdGuardia(),Integer.valueOf(deleteCalBody.getIdTurno(),idCalendarioGuardias);
		
	}

	/*
	 * public boolean actualizarOrdenGrupoColegiado(String idInstitucion, String
	 * idCalendarioProgramado, String idTurno, String idGuardia) {
	 * 
	 * boolean correcto = false;
	 * 
	 * sql2 = " FROM SCS_GRUPOGUARDIACOLEGIADO_HIST AUX " +
	 * " WHERE AUX.IDINSTITUCION = SCS_GRUPOGUARDIACOLEGIADO.IDINSTITUCION " +
	 * " AND AUX.IDTURNO = SCS_GRUPOGUARDIACOLEGIADO.IDTURNO " +
	 * " AND AUX.IDGUARDIA = SCS_GRUPOGUARDIACOLEGIADO.IDGUARDIA " +
	 * " AND AUX.IDCALENDARIOGUARDIAS = " + idCalendarioGuardias +
	 * " AND AUX.IDGRUPOGUARDIACOLEGIADO = SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIACOLEGIADO "
	 * ;
	 * 
	 * ScsGrupoguardiacolegiadoHistExample grupoGuardiaColegiadoHistExample = new
	 * ScsGrupoguardiacolegiadoHistExample();
	 * grupoGuardiaColegiadoHistExample.createCriteria()
	 * .andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
	 * .andIdcalendarioguardiasEqualTo(Integer.valueOf(idCalendarioProgramado))
	 * .andIdturnoEqualTo(Integer.valueOf(idTurno))
	 * .andIdguardiaEqualTo(Integer.valueOf(idGuardia));
	 * 
	 * List<ScsGrupoguardiacolegiadoHist> listaSubquery =
	 * this.scsGrupoGuardiaColegiadoHistExtendsMapper.selectByExample(
	 * grupoGuardiaColegiadoHistExample); ScsGrupoguardiacolegiadoHist
	 * resultadoSubquery = listaSubquery.get(0);
	 * 
	 * 
	 * ScsGrupoguardiacolegiado grupoGuardiaColegiado =
	 * this.scsGrupoguardiacolegiadoExtendsMapper.selectByExample();
	 * grupoGuardiaColegiado.setOrden(resultadoSubquery.getOrden());
	 * grupoGuardiaColegiado.setIdgrupoguardia(resultadoSubquery.getIdgrupoguardia()
	 * );
	 * grupoGuardiaColegiado.setFechamodificacion(resultadoSubquery.getFechacreacion
	 * ());
	 * grupoGuardiaColegiado.setUsumodificacion(resultadoSubquery.getUsucreacion());
	 * 
	 * sql = "UPDATE SCS_GRUPOGUARDIACOLEGIADO " + " SET ORDEN = (SELECT AUX.ORDEN "
	 * + sql2 + "), " + " IDGRUPOGUARDIA = (SELECT AUX.IDGRUPOGUARDIA " + sql2 +
	 * "), " + " FECHAMODIFICACION = (SELECT AUX.FECHACREACION " + sql2 + "), " + //
	 * Recupero la fecha de modificacion de la insercion de la tabla
	 * " USUMODIFICACION = (SELECT AUX.USUCREACION " + sql2 + ") " + // Recupero el
	 * usuario de modificacion de la insercion de la tabla " WHERE IDINSTITUCION = "
	 * + idInstitucion + " AND IDTURNO = " + idTurno + " AND IDGUARDIA = " +
	 * idGuardia + " AND EXISTS ( SELECT * " + sql2 + " ) ";
	 * 
	 * }
	 */

	private void borrarGeneracionCalendario(DeleteCalendariosProgDatosEntradaItem deleteCalBody,
			List<AdmUsuarios> usuarios, Integer idCalendarioGuardias) {

		this.scsGrupoguardiacolegiadoExtendsMapper.updateOrdenGrupoColegiadoPrimero(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina OrdenGrupoColegiado OK");
		this.scsGrupoguardiacolegiadoExtendsMapper.updateOrdenGrupoColegiadoSegundo(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()),
				usuarios.get(0).getUsumodificacion());
		LOGGER.info("Elimina updateOrdenGrupoColegiadoSegundo OK");
		this.scsSaltoscompensacionesExtendsMapper.updateSaltosCompensacionesCumplidos(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()),
				usuarios.get(0).getIdusuario());
		LOGGER.info("Elimina updateSaltosCompensacionesCumplidos OK");
		this.scsSaltoscompensacionesExtendsMapper.deleteSaltosCompensacionesCreadosEnCalendario(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deleteSaltosCompensacionesCreadosEnCalendario OK");
		this.scsSaltoscompensacionesExtendsMapper.deleteSaltosCompensacionesGrupoCreadosEnCalendario(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deleteSaltosCompensacionesGrupoCreadosEnCalendario OK");
		this.scsSaltoscompensacionesExtendsMapper.deleteSaltosCompensacionesCalendariosInexistentes(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(deleteCalBody.getIdTurno()),
				Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deleteSaltosCompensacionesCalendariosInexistentes OK");
		this.scsGuardiasturnoExtendsMapper.updateSaltosCompensacionesCumplidos(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()),
				usuarios.get(0).getIdusuario());
		LOGGER.info("Elimina updateSaltosCompensacionesCumplidos OK");
		this.scsGuardiasturnoExtendsMapper.deleteSaltosCompensacionesCreadosEnCalendario(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deleteSaltosCompensacionesCreadosEnCalendario OK");
		this.scsGuardiasturnoExtendsMapper.deleteSaltosCompensacionesCalendariosInexistentes(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(deleteCalBody.getIdTurno()),
				Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deleteSaltosCompensacionesCalendariosInexistentes OK");
		this.scsPermutaguardiasExtendsMapper.deletePermutasCalendarioSolicitante(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deletePermutasCalendarioSolicitante OK");
		this.scsPermutaguardiasExtendsMapper.deletePermutasCalendarioConfirmador(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()));
		LOGGER.info("Elimina deletePermutasCalendarioConfirmador OK");		
		try {
			//Conseguir el listado de registros a borrar
			ScsGuardiascolegiadoExample filter = new ScsGuardiascolegiadoExample();
			String NEW_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
			String OLD_FORMAT2 = "dd/MM/yyyy";
			String fechaIni = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2, deleteCalBody.getFechaDesde());
			String fechaFin = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2, deleteCalBody.getFechaHasta());
			filter.createCriteria().andIdinstitucionEqualTo(Short.valueOf(deleteCalBody.getIdInstitucion()))
				.andIdturnoEqualTo(Integer.valueOf(deleteCalBody.getIdTurno()))
				.andIdguardiaEqualTo(Integer.valueOf(deleteCalBody.getIdGuardia()))
				.andFechainicioGreaterThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(fechaIni))
				.andFechafinLessThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin));
			List<ScsGuardiascolegiado> rowsToDelete = this.scsGuardiascolegiadoExtendsMapper.selectByExample(filter);
			
			this.scsGuardiascolegiadoExtendsMapper.deleteGuardiasCalendario(
					Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
					Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()),
					deleteCalBody.getFechaDesde(), deleteCalBody.getFechaHasta());
			
			LOGGER.info("Elimina deleteGuardiasCalendario OK");
			
			//For para llamar al trigger con esos registros
			for(ScsGuardiascolegiado row : rowsToDelete) {
				this.triggerGuardiaColegiadoAID(row, 2);
			}			
			LOGGER.info("triggerGuardiaColegiadoAID - accion 2 (delete) - OK");
			
		} catch (ParseException e) {
			LOGGER.info("Elimina deleteGuardiasCalendario KO - fallo al buscar registros a eliminar (trigger)");
		} catch (Exception e) {
			LOGGER.info("No se ha podido ejecutar el triggerGuardiaColegiadoAID - accion 2 (delete)");
		}
		this.scsCabeceraguardiasExtendsMapper.deleteCabecerasGuardiasCalendario(
				Integer.valueOf(deleteCalBody.getIdInstitucion()), Integer.valueOf(idCalendarioGuardias),
				Integer.valueOf(deleteCalBody.getIdTurno()), Integer.valueOf(deleteCalBody.getIdGuardia()),
						deleteCalBody.getFechaDesde(), deleteCalBody.getFechaHasta());
		LOGGER.info("Elimina deleteCabecerasGuardiasCalendario OK");
		// Antiguo metodo error por encontrar varios elementos a eliminar, se pasa a
		// hacer metodo para eliminar uno a uno.

		// ScsCabeceraguardiasExample cabecerasExample = new
		// ScsCabeceraguardiasExample();
		// cabecerasExample.createCriteria().andIdinstitucionEqualTo(delete)

		// Actualizar ultimo en la cola
		ScsCalendarioguardiasKey key = new ScsCalendarioguardias();
		key.setIdinstitucion(Short.valueOf(deleteCalBody.getIdInstitucion()));
		key.setIdguardia(Integer.valueOf(deleteCalBody.getIdGuardia()));
		key.setIdturno(Integer.valueOf(deleteCalBody.getIdTurno()));
		key.setIdcalendarioguardias(idCalendarioGuardias);

		ScsCalendarioguardias calendario = scsCalendarioguardiasMapper.selectByPrimaryKey(key);
	
		if (calendario != null) {
			ScsGuardiasturno registro = new ScsGuardiasturno();
			registro.setIdguardia(Integer.valueOf(deleteCalBody.getIdGuardia()));
			registro.setIdinstitucion(Short.valueOf(deleteCalBody.getIdInstitucion()));
			registro.setIdturno(Integer.valueOf(deleteCalBody.getIdTurno()));
			registro.setIdpersonaUltimo(calendario.getIdpersonaUltimoanterior());
			registro.setIdgrupoguardiaUltimo(calendario.getIdgrupoguardiaUltimoanterior());
			registro.setFechasuscripcionUltimo(calendario.getFechasuscUltimoanterior());
			registro.setUsumodificacion(usuarios.get(0).getIdusuario());
			registro.setFechamodificacion(new Date());

			scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(registro);
			LOGGER.info("EliminaCalendario() Actualizar ultimo en la cola OK");
		}
		
		this.scsGrupoguardiacolegiadoExtendsMapper.deleteRegistrosGrupoGuardiaCol(idCalendarioGuardias);
		LOGGER.info("EliminaCalendario() deleteRegistrosGrupoGuardiaCol OK");
		
		this.scsGrupoguardiacolegiadoExtendsMapper.deleteGuardiConcreto(Short.valueOf(deleteCalBody.getIdInstitucion()),Integer.parseInt(deleteCalBody.getIdGuardia()),
				Integer.parseInt(deleteCalBody.getIdTurno()),idCalendarioGuardias,
				deleteCalBody.getFechaDesde(), deleteCalBody.getFechaHasta());
		LOGGER.info("EliminaCalendario() deleteGuardiConcreto OK");
	}
	
	/*
	 * SIGARNV-2918 :: este metodo forma parte de la solucion final (deshabilitar trigger + mejora generacion guardias colegiado)
	 * El comportamiento aqui descrito es identico al del DB TRIGGER SCS_GUARDIASCOLEGIADO_AID
	 * A fecha de 12/09/2023 se deja comentado hasta obtener la solucion final
	 */
	public int triggerGuardiaColegiadoAID(ScsGuardiascolegiado registro, Integer accion) throws Exception {
		// Si la accion es 1 es un insert, si la accion es 2 es un delete
		int response = 0;
		/*String activocolegio = "";
		
		//Revisa si la institucion tiene habilitado envios a centralita
		activocolegio = this.genParametrosExtendsMapper
				.selectParametroPorInstitucion("CENTRALITAVIRTUAL_ACTIVO", String.valueOf(registro.getIdinstitucion())).getValor();
		
		if(activocolegio != "0") {
			//Revisa si la guardia debe enviarse a centralita
			ScsGuardiasturnoKey guardiaKey = new ScsGuardiasturnoKey();
			guardiaKey.setIdinstitucion(registro.getIdinstitucion());
			guardiaKey.setIdturno(registro.getIdturno());
			guardiaKey.setIdguardia(registro.getIdguardia());
			
			ScsGuardiasturno guardia = scsGuardiasTurnoMapper.selectByPrimaryKey(guardiaKey);
			
			if(guardia != null && guardia.getEnviocentralita() == Short.valueOf("1")) {
				// 1. Insert en Ecom_Cola
				EcomCola nuevoEcomCola = new EcomCola();
				Integer lastIdEcomCola = ecomColaExtendsMapper.selectLastIdEcomCola();
				nuevoEcomCola.setIdinstitucion(registro.getIdinstitucion());
				nuevoEcomCola.setIdestadocola(ECOM_ESTADOSCOLA.INICIAL.getId());
				nuevoEcomCola.setIdoperacion(35);
				nuevoEcomCola.setReintento(0);
				nuevoEcomCola.setFechacreacion(new Date());
				nuevoEcomCola.setFechamodificacion(new Date());
				nuevoEcomCola.setUsumodificacion(SigaConstants.USUMODIFICACION_0);
				response = response + ecomColaExtendsMapper.insert(nuevoEcomCola);
				
				LOGGER.info("Despues insercion Ecom_Cola :: " +  response);
				
				//2. Insert en Ecom_Guardiacolegiado
				EcomGuardiacolegiado nuevoEcomGuardiaCol = new EcomGuardiacolegiado();
				Integer lastIdEcomGuardiaCol = ecomGuardiacolegiadoMapper.selectLastIdEcomGuardiaColegiado();
				CenDireccionesKey direccionABuscar = new CenDirecciones();
				direccionABuscar.setIdinstitucion(registro.getIdinstitucion());
				direccionABuscar.setIdpersona(registro.getIdpersona());
				direccionABuscar.setIddireccion(new Long(6));
				CenDirecciones direccionAInsertar = cenDireccionesMapper.selectByPrimaryKey(direccionABuscar);			
				CenPersona datosPersona = cenPersonaMapper.selectByPrimaryKey(registro.getIdpersona());
				String nombreColegiado = "";
				if(datosPersona != null) {
					nombreColegiado = (datosPersona.getNombre() == null ? "" : datosPersona.getNombre() + " ") 
							+ (datosPersona.getApellidos1() == null ? "" : datosPersona.getApellidos1() + " ") 
							+ (datosPersona.getApellidos2() == null ? "" : datosPersona.getApellidos2());
				}
				else {
					nombreColegiado = registro.getIdpersona().toString();
				}			
				CenColegiadoKey colegiadoABuscar = new CenColegiadoKey();
				colegiadoABuscar.setIdinstitucion(registro.getIdinstitucion());
				colegiadoABuscar.setIdpersona(registro.getIdpersona());
				String numColegiado = cenColegiadoExtendsMapper.selectCalculoNColegiado(colegiadoABuscar);
				ScsCabeceraguardiasKey cabeceraABuscar = new ScsCabeceraguardiasKey();
				cabeceraABuscar.setIdinstitucion(registro.getIdinstitucion());
				cabeceraABuscar.setIdturno(registro.getIdturno());			
				cabeceraABuscar.setIdguardia(registro.getIdguardia());
				cabeceraABuscar.setIdpersona(registro.getIdpersona());
				cabeceraABuscar.setFechainicio(registro.getFechainicio());
				ScsCabeceraguardias datosCabecera = scsCabeceraguardiasMapper.selectByPrimaryKey(cabeceraABuscar);
				
				//ScsGuardiasturno selectByPrimaryKey(ScsGuardiasturnoKey key);
				
				nuevoEcomGuardiaCol.setIdecomguardiacolegiado(lastIdEcomGuardiaCol + 1); // +1 ya que el id no se aumenta automaticamente
				nuevoEcomGuardiaCol.setIdinstitucion(registro.getIdinstitucion());
				nuevoEcomGuardiaCol.setIdguardia(registro.getIdguardia());
				nuevoEcomGuardiaCol.setFechaguardia(registro.getFechafin());
				nuevoEcomGuardiaCol.setNombreguardia(guardia.getNombre());
				nuevoEcomGuardiaCol.setOrdenguardia(datosCabecera != null ? datosCabecera.getPosicion() : null);			
				nuevoEcomGuardiaCol.setNumerocolegiado(numColegiado);			
				nuevoEcomGuardiaCol.setNombrecolegiado(nombreColegiado);			
				nuevoEcomGuardiaCol.setTelefono1(direccionAInsertar != null && direccionAInsertar.getTelefono1() != null ?
						direccionAInsertar.getTelefono1() : "000000000");
				nuevoEcomGuardiaCol.setTelefono2(direccionAInsertar != null ? direccionAInsertar.getTelefono2() : "");
				nuevoEcomGuardiaCol.setTelefonomovil(direccionAInsertar != null ? direccionAInsertar.getMovil() : "");
				nuevoEcomGuardiaCol.setEmail(direccionAInsertar != null ? direccionAInsertar.getCorreoelectronico() : "");
				nuevoEcomGuardiaCol.setAccion(accion.shortValue());
				nuevoEcomGuardiaCol.setIdecomcola(new Long(lastIdEcomCola + 1)); // +1 por la ultima insercion de arriba
				response = response + ecomGuardiacolegiadoMapper.insert(nuevoEcomGuardiaCol);
				
				LOGGER.info("Despues insercion Ecom_Guardiacolegiado :: " + nombreColegiado + " :: " +  response);
				
				//3. Insert en Scs_Cv_Guardiacolegiado
				ScsCvGuardiacolegiado nuevoScsCvGuardiaCol = new ScsCvGuardiacolegiado();
				Integer lastIdScsCvGuardiaCol = scsCvGuardiacolegiadoMapper.selectLastIdScsCvGuardiacolegiado();
				nuevoScsCvGuardiaCol.setIdscsguardiacolegiado(lastIdScsCvGuardiaCol + 1); // +1 ya que el id no se aumenta automaticamente
				nuevoScsCvGuardiaCol.setIdinstitucion(registro.getIdinstitucion());
				nuevoScsCvGuardiaCol.setIdguardia(registro.getIdguardia());
				nuevoScsCvGuardiaCol.setFechaguardia(registro.getFechafin());
				nuevoScsCvGuardiaCol.setNombreguardia(guardia.getNombre());
				nuevoScsCvGuardiaCol.setOrdenguardia(nuevoEcomGuardiaCol.getOrdenguardia());
				nuevoScsCvGuardiaCol.setFecharecepcion(new Date());
				nuevoScsCvGuardiaCol.setNumerocolegiado(numColegiado);
				nuevoScsCvGuardiaCol.setNombrecolegiado(nombreColegiado);
				nuevoScsCvGuardiaCol.setTelefono1(nuevoEcomGuardiaCol.getTelefono1());
				nuevoScsCvGuardiaCol.setTelefono2(nuevoEcomGuardiaCol.getTelefono2());
				nuevoScsCvGuardiaCol.setTelefonomovil(nuevoEcomGuardiaCol.getTelefonomovil());
				nuevoScsCvGuardiaCol.setEmail(nuevoEcomGuardiaCol.getEmail());
				nuevoScsCvGuardiaCol.setAccion(accion.shortValue());
				nuevoScsCvGuardiaCol.setIdturno(registro.getIdturno());
				nuevoScsCvGuardiaCol.setIdpersona(registro.getIdpersona());
				response = response + scsCvGuardiacolegiadoMapper.insert(nuevoScsCvGuardiaCol);
				
				LOGGER.info("Despues insercion Scs_Cv_Guardiacolegiado :: " + nombreColegiado + " :: " +  response);
				
			}
		}
		*/
		return response;
	}

}