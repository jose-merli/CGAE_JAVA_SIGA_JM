package org.itcgae.siga.scs.services.impl.guardia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.CabeceraGuardiasCalendarioItem;
import org.itcgae.siga.DTOs.scs.CalendarioAutomatico;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.ComboIncompatibilidadesResponse;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.DeleteCalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.GuardiasTurnoItem;
import org.itcgae.siga.DTOs.scs.HcoConfProgCalendariosItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.InscripcionesResponseDTO;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.LetradoInscripcionItem;
import org.itcgae.siga.DTOs.scs.LetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.PeriodoEfectivoItem;
import org.itcgae.siga.DTOs.scs.RangoFechasItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaGrupoItem;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.com.services.IGeneracionDocumentosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.Puntero;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsCabeceraguardiasKey;
import org.itcgae.siga.db.entities.ScsConfConjuntoGuardias;
import org.itcgae.siga.db.entities.ScsDocumentacionasi;
import org.itcgae.siga.db.entities.ScsDocumentacionasiKey;
import org.itcgae.siga.db.entities.ScsGrupoguardia;
import org.itcgae.siga.db.entities.ScsGrupoguardiaExample;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiado;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiadoExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsHcoConfProgCalendarios;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample;
import org.itcgae.siga.db.entities.ScsPermutaCabecera;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraExample;
import org.itcgae.siga.db.entities.ScsPermutaguardias;
import org.itcgae.siga.db.entities.ScsPermutaguardiasExample;
import org.itcgae.siga.db.entities.ScsProgCalendarios;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasMapper;
import org.itcgae.siga.db.mappers.ScsConfConjuntoGuardiasMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionasiMapper;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoSqlProvider;
import org.itcgae.siga.db.mappers.ScsGuardiascolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsHcoConfProgCalendariosMapper;
import org.itcgae.siga.db.mappers.ScsHcoConfProgCalendariosSqlProvider;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraMapper;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.mappers.ScsProgCalendariosMapper;
import org.itcgae.siga.db.mappers.ScsSaltoscompensacionesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCabeceraguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupoguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGrupoguardiacolegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsHitofacturableguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsIncompatibilidadguardiasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSubzonapartidoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;

@Service
public class GuardiasServiceImpl implements GuardiasService {

	private Logger LOGGER = Logger.getLogger(GuardiasServiceImpl.class);
	private boolean resetGrupos = false;
	private static final String ESTADO_PROGRAMADO = "1";
	private static final String EN_PROCESO = "2";
	private static final String PROCESADO_CON_ERRORES = "3";
	private static final String GENERADO = "4"; //FINALIZADO
	private static final String REPROGRAMADO = "5"; //REPROGRAMADO = PENDIENTE
	public static final int INSTITUCION_CGAE = 2000;
	public static final String DATE_FORMAT_JAVA = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_FORMAT_SHORT_SPANISH = "dd/MM/yyyy";
	public static final String TIPO_COD_VACACION = "V";
	public static final String TIPO_COD_MATERNIDAD = "M";
	public static final String TIPO_COD_BAJA = "B";
	public static final String TIPO_DESC_VACACION = "censo.bajastemporales.tipo.vacaciones";
	public static final String TIPO_DESC_BAJA = "censo.bajastemporales.tipo.baja";
	public static final String TIPO_DESC_MATERNIDAD = "censo.bajastemporales.tipo.maternidad";
	
	private Integer idInstitucion1;
	private Integer idTurno1;
	private Integer idGuardia1;
	private Integer idCalendarioGuardias1;
	private String fechaInicio1;
	private String fechaFin1;
	private GuardiasTurnoItem beanGuardiasTurno1 = null;
	private List<GuardiasCalendarioItem> calendariosVinculados1;
	private Integer usuModificacion1;
	
    //Datos para usar posteriormente propios de SJCS
	/**ArrayList de periodos con las fechas en formato corto como String*/
	private ArrayList<ArrayList<String>> arrayPeriodosDiasGuardiaSJCS1;	
	/**ArrayList con los periodos, cada uno con sus letrados para hacer las guardias SJCS*/
	private ArrayList arrayPeriodosLetradosSJCS1;
 
	//Datos a calcular previamente
	private List<String> vDiasFestivos1;
	
	private List<Map<String, Object>> listaDatosExcelGeneracionCalendarios = new ArrayList<>();

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
	private ScsSaltoscompensacionesMapper scsSaltoscompensacionesMapper;
	
	@Autowired
	private ScsGrupoguardiacolegiadoMapper scsGrupoguardiacolegiadoMapper;
	
	
	@Autowired
	private ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;
	
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
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;
	
	@Autowired
	private ScsConfConjuntoGuardiasMapper scsConfConjuntoGuardiasMapper;

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
							//Tambien damos de baja las inscripciones a la guardia
							ScsInscripcionguardiaExample scsInscripcionguardiaExample = new ScsInscripcionguardiaExample();
							scsInscripcionguardiaExample.createCriteria()
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdguardiaEqualTo(guardia.getIdguardia())
									.andIdturnoEqualTo(guardia.getIdturno())
									.andFechabajaIsNull().andFechavalidacionIsNotNull().andFechadenegacionIsNull();
							List<ScsInscripcionguardia> inscripcionesActivas = scsInscripcionguardiaExtendsMapper.selectByExample(scsInscripcionguardiaExample);
							if(inscripcionesActivas != null && !inscripcionesActivas.isEmpty()){

								inscripcionesActivas.forEach(inscripcion -> {
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

					for (GuardiasItem guardiaItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample scsGuardiasExample = new ScsGuardiasturnoExample();
						scsGuardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdguardiaEqualTo(Integer.valueOf(guardiaItem.getIdGuardia()))
								.andIdturnoEqualTo(Integer.valueOf(guardiaItem.getIdTurno()));

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a scsGuardiasturnoExtendsMapper para buscar la guardia");

						List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper
								.selectByExample(scsGuardiasExample);

						LOGGER.info(
								"activateGuardias() / scsGuardiasturnoExtendsMapper.selectByExample() -> Salida de scsGuardiasturnoExtendsMapper para buscar la guardia");

						if (null != guardiasList && guardiasList.size() > 0) {

							ScsGuardiasturno guardia = guardiasList.get(0);

							guardia.setFechabaja(null);
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Entrada a scsGuardiasturnoExtendsMapper para dar de baja a una prision");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardia);

							LOGGER.info(
									"activateGuardias() / scsGuardiasturnoExtendsMapper.updateByPrimaryKey() -> Salida de scsGuardiasturnoExtendsMapper para dar de baja a una prision");
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
						guardiaItem.setIdTipoGuardia(guardia.getIdtipoguardia().toString());
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
						guardiaItem.setLetradosGuardia(guardia.getNumeroletradosguardia().toString());
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
						guardia.setIdtipoguardia(Short.valueOf(guardiasItem.getIdTipoGuardia()));
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
								&& !Boolean.valueOf(guardiasItem.getPorGrupos())
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
							guardia.setDiasseparacionguardias(Short.parseShort(guardiasItem.getDiasSeparacionGuardias()));
							guardia.setIdordenacioncolas(colas.get(0).getIdordenacioncolas());
							guardia.setRequeridavalidacion(Boolean.toString(guardiasItem.isRequeridaValidacion()));
							scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

						}

					} else if (guardiasItem.getDiasGuardia() != null) {

						LOGGER.info(
								"updateGuardia() / scsGuardiasturnoExtendsMapper.selectByExample() -> Entrada a updatear Configuracion de calendario de guardias");
						guardia.setDiasguardia(Short.valueOf(guardiasItem.getDiasGuardia()));
						guardia.setTipodiasguardia(guardiasItem.getTipoDiasGuardia());
						if (!UtilidadesString.esCadenaVacia(guardiasItem.getDiasPeriodo()))
							guardia.setDiasperiodo(Short.valueOf(guardiasItem.getDiasPeriodo()));
						guardia.setTipodiasperiodo(guardiasItem.getTipoDiasPeriodo());
						guardia.setSeleccionfestivos(guardiasItem.getSeleccionFestivos());
						guardia.setSeleccionlaborables(guardiasItem.getSeleccionLaborables());
						guardia.setRequeridavalidacion(guardiasItem.isRequeridaValidacion() ? "S" : "N");

					}

					guardia.setFechamodificacion(new Date());
					guardia.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
					guardia.setDiasseparacionguardias(Short.parseShort(guardiasItem.getDiasSeparacionGuardias()));
					guardia.setRequeridavalidacion(Boolean.toString(guardiasItem.isRequeridaValidacion()));
					response = scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective(guardia);

					if(response > 0){
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

						guardia.setFechabaja(null);
						guardia.setEnviocentralita((short) (guardiasItem.getEnvioCentralita() ? 1 : 0));
						guardia.setFechamodificacion(new Date());
						guardia.setUsumodificacion(usuario.getIdusuario().intValue());
						guardia.setIdinstitucion(idInstitucion);
						guardia.setNombre(guardiasItem.getNombre());
						guardia.setDescripcion(guardiasItem.getDescripcion());
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
						guardia.setIdordenacioncolas(18033); // Esta puesto este id porque es el que tiene la conf por
																// defecto.
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

		if (response == 0 && error.getDescription() == null)

		{
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

				guardias = scsGuardiasturnoExtendsMapper.getResumen(guardiasItem.getIdGuardia(),
						guardiasItem.getIdTurno(), idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				LOGGER.info("resumenGuardia() -> Salida ya con los datos recogidos");
			}
		}
		return guardias.get(0);
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

				//guardias = scsIncompatibilidadguardiasExtendsMapper.tarjetaIncompatibilidades(idGuardia,
						//idInstitucion.toString());
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
	public InscripcionGuardiaDTO searchColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//idInstitucion = (short) 2005; // borrar
		String ordenaciones = "";
		InscripcionGuardiaDTO inscritos = new InscripcionGuardiaDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchColaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			try {

				if (usuarios != null && usuarios.size() > 0) {
					LOGGER.info("searchGuardias() -> Entrada para obtener las colas de guardia");
					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()));

					List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper.selectByExample(example);
					String ultimo = "";

					if (guardias != null && !guardias.isEmpty())
						ultimo = guardias.get(0).getIdpersonaUltimo() == null ? ""
								: guardias.get(0).getIdpersonaUltimo().toString();

					String fecha = "";

					ScsOrdenacioncolasExample example2 = new ScsOrdenacioncolasExample();
					example2.createCriteria()
							.andIdordenacioncolasEqualTo(Integer.valueOf(guardiasItem.getIdOrdenacionColas()));
					// Segun lo que nos llega del front preparamos el ORDER BY que habra en la
					// query.
					List<ScsOrdenacioncolas> cola = scsOrdenacionColasExtendsMapper.selectByExample(example2);
					Map<Short, String> mapilla = new HashMap();
					Map<Short, String> mapa = new TreeMap<Short, String>(Collections.reverseOrder());
					if (cola != null && !cola.isEmpty()) {
						if (cola.get(0).getAntiguedadcola() > 0)
							mapilla.put(cola.get(0).getAntiguedadcola(), "ANTIGUEDADCOLA,");
						else if (cola.get(0).getAntiguedadcola() < 0) {
							cola.get(0).setAntiguedadcola((short) -cola.get(0).getAntiguedadcola());
							mapilla.put(cola.get(0).getAntiguedadcola(), "ANTIGUEDADCOLA desc,");
						}
						if (cola.get(0).getAlfabeticoapellidos() > 0)
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS,");
						else if (cola.get(0).getAlfabeticoapellidos() < 0) {
							cola.get(0).setAlfabeticoapellidos((short) -cola.get(0).getAlfabeticoapellidos());
							mapilla.put(cola.get(0).getAlfabeticoapellidos(), "ALFABETICOAPELLIDOS desc,");
						}
						if (cola.get(0).getFechanacimiento() > 0)
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO,");
						else if (cola.get(0).getFechanacimiento() < 0) {
							cola.get(0).setFechanacimiento((short) -cola.get(0).getFechanacimiento());
							mapilla.put(cola.get(0).getFechanacimiento(), "FECHANACIMIENTO desc,");

						}
						if (cola.get(0).getOrdenacionmanual() > 0)
							mapilla.put(cola.get(0).getOrdenacionmanual(), "NUMEROGRUPO, ORDENGRUPO,");

						if (cola.get(0).getNumerocolegiado() > 0)
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO,");
						else if (cola.get(0).getNumerocolegiado() < 0) {
							cola.get(0).setNumerocolegiado((short) -cola.get(0).getNumerocolegiado());
							mapilla.put(cola.get(0).getNumerocolegiado(), "NUMEROCOLEGIADO desc,");
						}
						mapa.putAll(mapilla);
						if (mapa.size() > 0)
							for (String orden : mapa.values()) {
								ordenaciones += orden;
							}
						if (!ordenaciones.isEmpty())
							ordenaciones.substring(0, ordenaciones.length() - 1);
					}
					// Si hay ultimo se prepara su WHERE correspondiente
					if (!UtilidadesString.esCadenaVacia(ultimo))
						ultimo = "WHERE\r\n" + "		idpersona =" + ultimo;
					String grupoUltimo = "";

					if (guardias.get(0).getIdgrupoguardiaUltimo() != null)
						grupoUltimo = "and idgrupoguardia = " + guardias.get(0).getIdgrupoguardiaUltimo();

					List<InscripcionGuardiaItem> colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(
							guardiasItem.getIdGuardia(), guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(),
							ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo);
					
					for (int i = 0; i < colaGuardia.size(); i++) {
						if (colaGuardia.get(i).getNumeroGrupo() == null || colaGuardia.get(i).getNumeroGrupo().equals("null")) {
							colaGuardia.remove(i);
						}
					}
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
										ultimo, ordenaciones, idInstitucion.toString(), grupoUltimo);
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

								//NewIdDTO idP = scsGrupoguardiacolegiadoExtendsMapper.getLastId();

								/*if (idP == null)
									grupoColegiado.setIdgrupoguardiacolegiado((long) 1);
								else
									grupoColegiado.setIdgrupoguardiacolegiado(Long.parseLong(idP.getNewId()) + 1);*/

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

						}
						colaGuardia = scsInscripcionguardiaExtendsMapper.getColaGuardias(guardiasItem.getIdGuardia(),
								guardiasItem.getIdTurno(), guardiasItem.getLetradosIns(), ultimo, ordenaciones,
								idInstitucion.toString(), grupoUltimo);
						for (int i = 0; i < colaGuardia.size(); i++) {
							if(colaGuardia.get(i).getNombre() == "CARLA") {
								String duda = colaGuardia.get(i).getNumeroGrupo();
							}
							if (colaGuardia.get(i).getNumeroGrupo() == null || colaGuardia.get(i).getNumeroGrupo() == "null") {
								colaGuardia.remove(i);
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

				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem, idInstitucion.toString());
//				guardiasItem.setIdGuardia("358");//borrar
//				guardiasItem.setIdTurno("177");//borrar
//				guardias = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades(guardiasItem,
//						"2003"); //borrar
				String numIcompatibilidades = guardias.get(0).getIncompatibilidades();
				guardias2 = scsIncompatibilidadguardiasExtendsMapper.resumenIncompatibilidades2(guardiasItem, idInstitucion.toString());
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

	@Transactional
	@Override
	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones,
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
					//NewIdDTO idGrupoDTO = scsGrupoguardiaExtendsMapper.getLastId();
					Integer idGrupo = 0;

					ScsGrupoguardia grupo = null;
					ScsGrupoguardiacolegiado grupoColegiado = null;
					ScsGrupoguardiacolegiadoExample scsGrupoguardiacolegiadoExample = new ScsGrupoguardiacolegiadoExample();

					/*if (idGrupoDTO != null)
						idGrupo = Integer.valueOf(idGrupoDTO.getNewId());*/

					// Comprobamos si hay algun grupo nuevo
					ScsGrupoguardiaExample grupoGuardiaExample = new ScsGrupoguardiaExample();
					grupoGuardiaExample.createCriteria()
							.andIdguardiaEqualTo(Integer.valueOf(guardiasItem.getIdGuardia()))
							.andIdturnoEqualTo(Integer.valueOf(guardiasItem.getIdTurno()))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGrupoguardia> gruposExistentes = scsGrupoguardiaExtendsMapper
							.selectByExample(grupoGuardiaExample);
					List<ScsGrupoguardia> grupoPerteneciente = null;
					for (int i = 0; i < inscripciones.size(); i++) {
						InscripcionGuardiaItem element = inscripciones.get(i);
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

								scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

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

								scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
										scsGrupoguardiacolegiadoExample);
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

								ScsGrupoguardiacolegiadoExample grupoGuardiaColegiadoExample = new ScsGrupoguardiacolegiadoExample();
								grupoGuardiaColegiadoExample.createCriteria().andIdgrupoguardiacolegiadoEqualTo(
										Long.valueOf(inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado()));
								scsGrupoguardiacolegiadoExtendsMapper.deleteByExample(grupoGuardiaColegiadoExample);

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

								scsGrupoguardiaExtendsMapper.insert(grupo);

								grupoColegiado = new ScsGrupoguardiacolegiado();
								// Aqui se crea un nuevo grupo colegiado en caso de que el recibido no tenga
								// idgrupoguardiacolegiado
								if (inscripcionesGrupoNuevo.get(i).getIdGrupoGuardiaColegiado() == null) {
									grupoColegiado.setFechamodificacion(new Date());
									grupoColegiado.setFechacreacion(new Date());
									grupoColegiado.setUsucreacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setUsumodificacion(usuarios.get(0).getIdusuario().intValue());
									grupoColegiado.setOrden(Integer.valueOf(inscripcionesGrupoNuevo.get(i).getOrden()));
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

									scsGrupoguardiacolegiadoExtendsMapper.insert(grupoColegiado);

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

									scsGrupoguardiacolegiadoExtendsMapper.updateByExampleSelective(grupoColegiado,
											scsGrupoguardiacolegiadoExample);
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
	public IncompatibilidadesDTO getIncompatibilidades( IncompatibilidadesDatosEntradaItem incompBody, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<IncompatibilidadesItem> incompatibilidades = new ArrayList<IncompatibilidadesItem>();
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

				genParametrosExample.createCriteria().andModuloEqualTo("SCS")
						.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
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

				incompatibilidades = scsIncompatibilidadguardiasExtendsMapper.getListadoIncompatibilidades(incompBody, idInstitucion.toString(), idGuardia, tamMaximo);

				if (tamMaximo != null && incompatibilidades != null && !incompatibilidades.isEmpty() && incompatibilidades.size() > tamMaximo) {
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
	public ComboIncompatibilidadesResponse getCombo(ComboIncompatibilidadesDatosEntradaItem comboIncompatibilidadesDatosEntradaItem, HttpServletRequest request){
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		List<String> comboGuardiasIncLabels = new ArrayList<String>();
		List<String> comboGuardiasIncValues = new ArrayList<String>();
		ComboIncompatibilidadesResponse response = new ComboIncompatibilidadesResponse();
		
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(comboIncompatibilidadesDatosEntradaItem.getIdInstitucion()));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		comboGuardiasIncLabels = scsIncompatibilidadguardiasExtendsMapper.getListaLabelsGuardiasInc(comboIncompatibilidadesDatosEntradaItem.getIdInstitucion(), comboIncompatibilidadesDatosEntradaItem.getIdTipoGuardia(), comboIncompatibilidadesDatosEntradaItem.getIdTurno(), usuarios.get(0).getIdusuario(), comboIncompatibilidadesDatosEntradaItem.getIdPartidaPresupuestaria());
		comboGuardiasIncValues = scsIncompatibilidadguardiasExtendsMapper.getListaValueGuardiasInc(comboIncompatibilidadesDatosEntradaItem.getIdInstitucion(), comboIncompatibilidadesDatosEntradaItem.getIdTipoGuardia(), comboIncompatibilidadesDatosEntradaItem.getIdTurno(), usuarios.get(0).getIdusuario(), comboIncompatibilidadesDatosEntradaItem.getIdPartidaPresupuestaria());
		
		response.setLabels(comboGuardiasIncLabels);
		response.setValues(comboGuardiasIncValues);
		return response;
	}

	@Transactional
	@Override
	public DeleteResponseDTO deleteIncompatibilidades(List<DeleteIncompatibilidadesDatosEntradaItem> deleteIncompatibilidadesBody, HttpServletRequest request) {
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

			if (usuarios != null && usuarios.size() > 0 ) {
				LOGGER.info("deleteIncompatibilidades() -> Entrada para borrar las incompatibilidades");
				for (int i = 0; i < deleteIncompatibilidadesBody.size(); i++) {
					DeleteIncompatibilidadesDatosEntradaItem incompatibilidad = deleteIncompatibilidadesBody.get(i);
					if(!UtilidadesString.esCadenaVacia(incompatibilidad.getIdTurno())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdInstitucion())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardia())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdTurnoIncompatible())
							&& !UtilidadesString.esCadenaVacia(incompatibilidad.getIdGuardiaIncompatible())){
						//Doble borrado
						scsIncompatibilidadguardiasExtendsMapper.deleteIncompatibilidades(incompatibilidad.getIdTurno(), incompatibilidad.getIdInstitucion(), incompatibilidad.getIdGuardia(), incompatibilidad.getIdTurnoIncompatible(), incompatibilidad.getIdGuardiaIncompatible());
					}
				}
				LOGGER.info("deleteIncompatibilidades() -> Salida ya con los datos recogidos");
			}else {
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
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"saveIncompatibilidades() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0 && !incompatibilidadesBody.isEmpty()) {
                LOGGER.info("tarjetaIncompatibilidades() -> Entrada para obtener las incompatibilidades");
                for (int i = 0; i < incompatibilidadesBody.size(); i++) {
                    SaveIncompatibilidadesDatosEntradaItem incompatibilidad = incompatibilidadesBody.get(i);
                    idInstitucion = incompatibilidad.getIdInstitucion();
                    idTurno = incompatibilidad.getIdTurno();
                    if ( idTurno == null || idTurno.isEmpty() && incompatibilidad.getNombreTurno() != null) {
                        idTurno = scsIncompatibilidadguardiasExtendsMapper.getIdTurnoFromNombreTurno(incompatibilidad.getNombreTurno());
                    }

                    idGuardia = incompatibilidad.getIdGuardia();
                    if ( idGuardia == null || idGuardia.isEmpty() && incompatibilidad.getNombreGuardia() != null) {
                        idGuardia = scsIncompatibilidadguardiasExtendsMapper.getIdGuardiaFromNombreGuardia(incompatibilidad.getNombreGuardia());
                    }

                    idGuardiaIncompatible = incompatibilidad.getIdGuardiaIncompatible();
                    if ( idGuardiaIncompatible == null || idGuardiaIncompatible.isEmpty()) {
                        incompatibilidad.getNombreGuardiaIncompatible().forEach(gi -> {
                            String idGuardiaIncompatibleAux = scsIncompatibilidadguardiasExtendsMapper.getIdGuardiaIncompatibleFromNombreGuardia(gi);
                            idGuardiaIncompatibleList.add(idGuardiaIncompatibleAux);
                        });
                        idGuardiaIncompatible = idGuardiaIncompatibleList.get(0);

                    }

                    idTurnoIncompatible = incompatibilidad.getIdTurnoIncompatible();
                    if ( idTurnoIncompatible == null || idTurnoIncompatible.isEmpty()) {
                        if(incompatibilidad.getNombreTurnoIncompatible() != null && !incompatibilidad.getNombreTurnoIncompatible().isEmpty()) {
                            idTurnoIncompatible = scsIncompatibilidadguardiasExtendsMapper.getIdTurnoIncompatibleFromNombreTurno(incompatibilidad.getNombreTurnoIncompatible());
                        }else {
                            idTurnoIncompatible = scsIncompatibilidadguardiasExtendsMapper.getIdTurnoIncByIdGuardiaInc(idGuardiaIncompatible);
                        }
                    }

                    String pattern = "dd/MM/YY";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String fechaModificacion = simpleDateFormat.format(new Date());
                    if(idTurno != null && !idTurno.isEmpty() &&  incompatibilidad.getIdInstitucion() != null && ! incompatibilidad.getIdInstitucion().isEmpty() && idGuardia != null && !idGuardia.isEmpty() && idTurnoIncompatible != null && !idTurnoIncompatible.isEmpty() && idGuardiaIncompatible != null && !idGuardiaIncompatible.isEmpty()) {
                        int existe = scsIncompatibilidadguardiasExtendsMapper.checkIncompatibilidadesExists(idTurno, incompatibilidad.getIdInstitucion(), idGuardia, idTurnoIncompatible, idGuardiaIncompatible);
                        if (existe == 2) {
                            //existe en ambas direcciones - la actualizamos
                            scsIncompatibilidadguardiasExtendsMapper.updateIfExists(idTurno, incompatibilidad.getIdInstitucion(), idGuardia, idTurnoIncompatible, idGuardiaIncompatible, incompatibilidad.getMotivos(), incompatibilidad.getDiasSeparacionGuardias(), fechaModificacion);
                        }
                        if (existe == 0) {
                            //no existe - llamamos dos veces para guardar en ambas direcciones
                            scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(Integer.parseInt(idTurno), Integer.parseInt(incompatibilidad.getIdInstitucion()), Integer.parseInt(idGuardia), Integer.parseInt(idTurnoIncompatible), Integer.parseInt(idGuardiaIncompatible), usuarios.get(0).getIdusuario(), incompatibilidad.getMotivos(), Integer.parseInt(incompatibilidad.getDiasSeparacionGuardias()), fechaModificacion);
                            scsIncompatibilidadguardiasExtendsMapper.saveListadoIncompatibilidades(Integer.parseInt(idTurno), Integer.parseInt(incompatibilidad.getIdInstitucion()), Integer.parseInt(idGuardiaIncompatible), Integer.parseInt(idTurnoIncompatible), Integer.parseInt(idGuardia), usuarios.get(0).getIdusuario(), incompatibilidad.getMotivos(), Integer.parseInt(incompatibilidad.getDiasSeparacionGuardias()), fechaModificacion);

                        }
                    }else {
                        response = 0;
                    }
                }
				LOGGER.info("saveIncompatibilidades() -> Salida ya con los datos recogidos");
			}
		}
		
		// comprobacion actualización
		if (response >= 1) {
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

				calendariosList = scsGuardiasturnoExtendsMapper.searchCalendarios(calendarioProgBody, idInstitucion.toString());

				
				LOGGER.info("getIncompatibilidades() -> Salida ya con los datos recogidos");
				
			}
		}
		
		return calendariosList;
	}
	@Override
	public List<GuardiaCalendarioItem> getGuardiasFromCalendar(String idCalendar,
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

				datos = scsGuardiasturnoExtendsMapper.getGuardiasFromCalendar(idCalendar, idInstitucion.toString());
				for (int i = 0; i < datos.size(); i++){
				datos.get(i).setOrden(Integer.toString(i + 1));
				}

				LOGGER.info("getGuardiasFromCalendar() -> Salida ya con los datos recogidos");
			}
		}

		return datos;
	}
	
	@Override
	public List<DatosCalendarioProgramadoItem> getCalendarioProgramado(CalendariosProgDatosEntradaItem calendarioProgBody,
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
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCalendarioProgramado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getCalendarioProgramado() -> Entrada para obtener los datos del calendario");

				//datos = scsGuardiasturnoExtendsMapper.getCalendarioProgramado(calendarioProgBody, idInstitucion.toString());
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				String NEW_FORMAT1 = "dd/MM/yyyy HH:mm:ss";
				//2017-02-06 00:00:00.0
				if (calendarioProgBody.getFechaCalendarioDesde() != null) {
                    String fecha = null;
				    if(!calendarioProgBody.getFechaCalendarioDesde().contains("T")){
                        fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
                    }else{
                        fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
                    }

					calendarioProgBody.setFechaCalendarioDesde(fecha);
				}
				if (calendarioProgBody.getFechaCalendarioHasta()!= null) {
                    String fecha = null;
                    if(!calendarioProgBody.getFechaCalendarioHasta().contains("T")){
                        fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
                    }else{
                        fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
                    }
					calendarioProgBody.setFechaCalendarioHasta(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaDesde()!= null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1, calendarioProgBody.getFechaProgramadaDesde());
					calendarioProgBody.setFechaProgramadaDesde(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaHasta()!= null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1, calendarioProgBody.getFechaProgramadaHasta());
					calendarioProgBody.setFechaProgramadaHasta(fecha);
				}
				datos = scsGuardiasturnoExtendsMapper.getCalendariosProgramadosSigaClassique(calendarioProgBody, idInstitucion.toString());
				

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

				//datos = scsGuardiasturnoExtendsMapper.getCalendarioProgramado(calendarioProgBody, idInstitucion.toString());
				String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
				String OLD_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
				String NEW_FORMAT = "dd/MM/yyyy";
				String NEW_FORMAT1 = "dd/MM/yyyy HH:mm:ss";
				//2017-02-06 00:00:00.0
				if (calendarioProgBody.getFechaCalendarioDesde() != null) {
                    String fecha = null;
				    if(!calendarioProgBody.getFechaCalendarioDesde().contains("T")){
                        fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
                    }else{
                        fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioDesde());
                    }

					calendarioProgBody.setFechaCalendarioDesde(fecha);
				}
				if (calendarioProgBody.getFechaCalendarioHasta()!= null) {
                    String fecha = null;
                    if(!calendarioProgBody.getFechaCalendarioHasta().contains("T")){
                        fecha = changeDateFormat(OLD_FORMAT1, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
                    }else{
                        fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calendarioProgBody.getFechaCalendarioHasta());
                    }
					calendarioProgBody.setFechaCalendarioHasta(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaDesde()!= null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1, calendarioProgBody.getFechaProgramadaDesde());
					calendarioProgBody.setFechaProgramadaDesde(fecha);
				}
				if (calendarioProgBody.getFechaProgramadaHasta()!= null) {
					String fecha = changeDateFormat(OLD_FORMAT, NEW_FORMAT1, calendarioProgBody.getFechaProgramadaHasta());
					calendarioProgBody.setFechaProgramadaHasta(fecha);
				}
				datos = scsGuardiasturnoExtendsMapper.getLastCalendariosProgramadosSigaClassique(calendarioProgBody, idInstitucion.toString());


				LOGGER.info("getLastCalendarioProgramado() -> Salida ya con los datos recogidos");
			}
		}

		return datos.get(0);
	}
	@Override
	public List<RangoFechasItem> getFechasProgramacionGuardia(String idGuardia,
			HttpServletRequest request) {

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
				
				idConjuntoGuardias =  scsGuardiasturnoExtendsMapper.getConjuntoGuardiasIdFromGuardiaId(idGuardia, idInstitucion.toString());
				if (!idConjuntoGuardias.isEmpty() && idConjuntoGuardias != null) {
					idConjuntoGuardias.forEach(idC -> {
						List<RangoFechasItem> fechaList = scsGuardiasturnoExtendsMapper.getFechasProgramacionFromIdConjuntoGuardia(idC, idInstitucion.toString());
						fechaList.forEach(fL ->{
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

	@Override
	public DeleteResponseDTO deleteCalendariosProgramados(DeleteCalendariosProgDatosEntradaItem deleteCalBody,
			HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 1;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteCalendariosProgramados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteCalendariosProgramados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0 && deleteCalBody.getIdTurno() != null && !deleteCalBody.getIdTurno().isEmpty() && deleteCalBody.getIdInstitucion() != null && 
					!deleteCalBody.getIdInstitucion().isEmpty() && deleteCalBody.getIdGuardia() != null && !deleteCalBody.getIdGuardia().isEmpty() && 
							deleteCalBody.getIdCalendarioProgramado() != null && !deleteCalBody.getIdCalendarioProgramado().isEmpty()) {
				LOGGER.info("deleteCalendariosProgramados() -> Entrada para borrar las incompatibilidades");
				//Doble borrado
				scsIncompatibilidadguardiasExtendsMapper.deleteCalendarioProgramado1(deleteCalBody.getIdTurno(), deleteCalBody.getIdInstitucion(), deleteCalBody.getIdGuardia(), deleteCalBody.getIdCalendarioProgramado());
				scsIncompatibilidadguardiasExtendsMapper.deleteCalendarioProgramado2(deleteCalBody.getIdTurno(), deleteCalBody.getIdInstitucion(), deleteCalBody.getIdGuardia(), deleteCalBody.getIdCalendarioProgramado());
				
				LOGGER.info("deleteCalendariosProgramados() -> Salida ya con los datos recogidos");
			}else {
				response = 0;
			}
		}
		
		// comprobacion actualización
		if (response >= 1) {
			LOGGER.info("deleteCalendariosProgramados() -> OK. Delete para incompatibilidades realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		} else {
			LOGGER.info("deleteCalendariosProgramados() -> KO. Delete para incompatibilidades NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		LOGGER.info("deleteCalendariosProgramados() -> Salida del servicio para eliminar incompatibilidades");
		return deleteResponseDTO;
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
	public List<GuardiaCalendarioItem> guardiasFromCojunto (HttpServletRequest request, String idConjuntoGuardia) {
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

				datos = scsGuardiasturnoExtendsMapper.getguardiasFromConjuntoGuardiasId(idConjuntoGuardia, idInstitucion.toString());
				for (int i = 0; i < datos.size(); i++){
					datos.get(i).setOrden(Integer.toString(i + 1));
					}

					LOGGER.info("guardiasFromCojunto() -> Entrada para obtener los datos del calendario");
				}
		}

			return datos;
		}
	
	@Override
	public InsertResponseDTO insertGuardiaToConjunto (HttpServletRequest request, String idConjuntoGuardia, List<GuardiaCalendarioItem> itemList) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				if (usuarios != null && usuarios.size() > 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"insertGuardiaToConjunto() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					itemList.forEach(item -> {
						String response = scsGuardiasturnoExtendsMapper.setguardiaInConjuntoGuardias(idConjuntoGuardia, idInstitucion.toString(), today, item);
						if (response == null && error.getDescription() == null)
						{
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

					});
					
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
	public InsertResponseDTO insertGuardiaToCalendar (HttpServletRequest request, String idCalendar, List<GuardiaCalendarioItem> itemList) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				if (usuarios != null && usuarios.size() > 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"insertGuardiaToCalendar() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(idCalendar, idInstitucion.toString());
					itemList.forEach(item -> {
						try {
						String response = scsGuardiasturnoExtendsMapper.setguardiaInConjuntoGuardias(idConjuntoGuardia, idInstitucion.toString(), today, item);
						String response2 = scsGuardiasturnoExtendsMapper.setGuardiaInCalendario(idCalendar, idConjuntoGuardia, idInstitucion.toString(), today, item);
						if ((response == null || response2 == null)  && error.getDescription() == null)
						{
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
						}catch(Exception e) {
							error.setCode(500);
							error.setDescription("general.mensaje.error.bbdd");
							error.setMessage(e.getMessage());
							insertResponseDTO.setError(error);
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
	public InsertResponseDTO deleteGuardiaFromCalendar (HttpServletRequest request, String idCalendar, List<GuardiaCalendarioItem> itemList) {
		LOGGER.info("comboGuardias() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				if (usuarios != null && usuarios.size() > 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"insertGuardiaToCalendar() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(idCalendar, idInstitucion.toString());
					itemList.forEach(item -> {
//						String response3 = scsGuardiasturnoExtendsMapper.deleteguardiaFromLog(idConjuntoGuardia, idInstitucion.toString(), today, item);
						String response = scsGuardiasturnoExtendsMapper.deleteguardiaFromConjuntoGuardias(idConjuntoGuardia, idInstitucion.toString(), today, item);
						String response2 = scsGuardiasturnoExtendsMapper.deleteGuardiaFromCalendario(idCalendar, idConjuntoGuardia, idInstitucion.toString(), today, item);
						if (response2 == null || response == null  && error.getDescription() == null)
						{
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
	public InsertResponseDTO updateCalendarioProgramado (HttpServletRequest request, DatosCalendarioProgramadoItem calendarioItem) {
		LOGGER.info("updateCalendarioProgramado() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String hola ="";
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				if (usuarios != null && usuarios.size() > 0) {
					LOGGER.info(
							"updateCalendarioProgramado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					
						//String response = scsGuardiasturnoExtendsMapper.updateCalendarioProgramado1(calendarioItem,  idInstitucion.toString());
						//int response2 = scsGuardiasturnoExtendsMapper.updateCalendarioProgramado2(calendarioItem,  idInstitucion.toString());
					ScsProgCalendarios pc = new ScsProgCalendarios();
					if (calendarioItem.getEstado() != null) {
						pc.setEstado(new Integer(calendarioItem.getEstado()).shortValue());
					}
					if (calendarioItem.getFechaHasta() != null) {
						pc.setFechacalfin(new SimpleDateFormat("dd/MM/yyyy").parse(calendarioItem.getFechaHasta()));
					}
					if (calendarioItem.getFechaDesde() != null) {
						pc.setFechacalinicio(new SimpleDateFormat("dd/MM/yyyy").parse(calendarioItem.getFechaDesde()));
					}
					if (calendarioItem.getFechaProgramacion() != null) {
						pc.setFechaprogramacion(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(calendarioItem.getFechaProgramacion()));
					}
					if (calendarioItem.getIdCalG() != null) {
						pc.setIdconjuntoguardia(Short.valueOf(calendarioItem.getIdCalG()));
					}
					if (calendarioItem.getIdCalendarioProgramado() != null) {
						pc.setIdprogcalendario(Long.valueOf(calendarioItem.getIdCalendarioProgramado()));
					}
					
					pc.setUsumodificacion(usuarios.get(0).getUsumodificacion());
					pc.setFechamodificacion(new Date());
					pc.setIdinstitucion(idInstitucion);
					//int response2 = scsProgCalendariosMapper.updateByPrimaryKeySelective(pc);
					int res = scsProgCalendariosMapper.updateProgCalendario(pc.getIdconjuntoguardia().toString(), idInstitucion.toString(), calendarioItem.getFechaProgramacion(), calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta(), pc.getEstado().toString(), pc.getFechamodificacion().toString(), 
							pc.getUsumodificacion().toString(), null, pc.getIdprogcalendario().toString());
						 //String response3 = scsGuardiasturnoExtendsMapper.updateConfCalendarioProgramado2(calendarioItem,  idInstitucion.toString());
						 
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
						if (calendarioItem.getObservaciones() != null && !calendarioItem.getObservaciones().isEmpty()) {
							scsGuardiasturnoExtendsMapper.setObservaciones(idInstitucion.toString(), hco.getIdprogcalendario().toString(), calendarioItem.getObservaciones(), hco.getIdguardia().toString(), hco.getIdturno().toString(), calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta());
							scsGuardiasturnoExtendsMapper.setObservaciones2(idInstitucion.toString(), hco.getIdprogcalendario().toString(), calendarioItem.getObservaciones(), calendarioItem.getFechaDesde(), calendarioItem.getFechaHasta());
						}
						 hco.setUsumodificacion(usuarios.get(0).getUsumodificacion());
						 hco.setFechamodificacion(new Date());
						 int response3 = scsHcoConfProgCalendariosMapper.updateByPrimaryKeySelective(hco);
					String response4 = scsGuardiasturnoExtendsMapper.updateCalendarioProgramado3(calendarioItem,  idInstitucion.toString());
						if (response3 == 0 && error.getDescription() == null)
						{
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
					
						LOGGER.info("updateCalendarioProgramado() -> Entrada para obtener los datos del calendario");
					}
			}
		} catch (Exception e) {
			LOGGER.error(
					"updateCalendarioProgramado() -> Se ha producido un error",
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
	public InsertResponseDTO newCalendarioProgramado (HttpServletRequest request, DatosCalendarioProgramadoItem calendarioItem) {
		LOGGER.info("updateCalendarioProgramado() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		Boolean errorGuardiaAsociadas = false;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				if (usuarios != null && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"newCalendarioProgramado() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					//check si hay guardias asociadas a esta programacion
					if (!calendarioItem.getFechaDesde().isEmpty() && calendarioItem.getFechaDesde() != null && !calendarioItem.getFechaHasta().isEmpty() && calendarioItem.getFechaHasta() != null) {
						int numGuards = scsGuardiasturnoExtendsMapper.getGuardiasToProg(calendarioItem, idInstitucion.toString());
						if (numGuards <= 0) {
							errorGuardiaAsociadas = true;
							error.setCode(204);
							error.setDescription("No existen guardias asociadas a esta programación");
							error.setMessage("No existen guardias asociadas a esta programación");
							insertResponseDTO.setStatus("ERRORASOCIADAS");
						}
					}
					if ( !errorGuardiaAsociadas) {
					if (calendarioItem.getIdCalG() == null) {
						String idConjuntoGuardia = scsGuardiasturnoExtendsMapper.getConjuntoFromCalendarId(calendarioItem.getIdCalendarioProgramado(), idInstitucion.toString());
					//idConjunto: 
						calendarioItem.setIdCalG(idConjuntoGuardia);
					}
					String nextIdCalendarioProgramado = getNuevoIdCalProg();
					calendarioItem.setIdCalendarioProgramado(nextIdCalendarioProgramado);
					int response = scsGuardiasturnoExtendsMapper.generateCalendarioProgramado(nextIdCalendarioProgramado, calendarioItem,  idInstitucion.toString(), today, usuario.getIdusuario().toString());
					 List<ScsConfConjuntoGuardias> confList = scsConfConjuntoGuardiasMapper.selectConfById(calendarioItem.getIdCalG(), today,  usuario.getIdusuario().toString());
					 confList.forEach(conf -> {
						 ScsHcoConfProgCalendarios historico = new ScsHcoConfProgCalendarios();
							historico.setEstado(new Short(calendarioItem.getEstado()));
							historico.setFechamodificacion(new Date());
							historico.setIdconjuntoguardia(new Long(calendarioItem.getIdCalG()));
							historico.setIdguardia(conf.getIdguardia());
							historico.setIdinstitucion(idInstitucion);
							//String idCalendarioProgramado = scsGuardiasturnoExtendsMapper.getLastProgramacion(idInstitucion.toString());
							historico.setIdprogcalendario(new Long(nextIdCalendarioProgramado));
							historico.setIdturno(conf.getIdturno());
							//OBTENER ORDEN DE SCS_CONF_CONJUNTO_GUARDIAS
							if (conf.getOrden() != null) {
								historico.setOrden(new Integer(conf.getOrden()));
							}
							historico.setUsumodificacion(usuario.getIdusuario());
							int response2 = scsHcoConfProgCalendariosMapper.insertSelective(historico);
					 });
					
					
					if (response == 0)
						{
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
					
						LOGGER.info("newCalendarioProgramado() -> Entrada para obtener los datos del calendario");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"newCalendarioProgramado() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
		}
	

	@Scheduled(cron = "${cron.pattern.scheduled.guardias.generarCalendario: 0 * 0/1 * * *}")

	@Override
	public InsertResponseDTO generarCalendarioAsync () {
		LOGGER.info("generarCalendarioAsync() -> Entrada al servicio para búsqueda de las guardias");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		GuardiasTurnoItem guardiaBean = new GuardiasTurnoItem();
		List<DatosCalendarioProgramadoItem> programacionItemListFull = new ArrayList<>();
		
		Error error = new Error();
		try {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"generarCalendarioAsync() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					
					List<DatosCalendarioProgramadoItem> programacionItemList = scsGuardiasturnoExtendsMapper.getAllCalendariosProgramadosSigaClassiquePendiente ();
					if (programacionItemList != null && !programacionItemList.isEmpty()) {
						
						programacionItemList.forEach(d ->{
							String numGuardias = scsGuardiasturnoExtendsMapper.getNumGuardiasCalProg(d.getIdCalG(), d.getIdCalendarioProgramado(), null);
							String turno = scsGuardiasturnoExtendsMapper.getTurnoCalProg( d.getIdTurno(), d.getIdCalG(), null);
							String guardia = scsGuardiasturnoExtendsMapper.getGuardiaCalProg( d.getIdTurno(), d.getIdGuardia(), d.getIdCalG(), null);
							List<String> observaciones = scsGuardiasturnoExtendsMapper.getObservacionesCalendario(d.getIdGuardia(), d.getIdTurno(), null, d.getFechaDesde(), d.getFechaHasta());
							String observacionesSt = "";
							if (observaciones != null && !observaciones.isEmpty() && observaciones.get(0) != null) {
								for (int i = 0; i <=observaciones.size(); i++) {
									observacionesSt = observacionesSt.concat(observaciones.get(i) + " ,");
								}
							}

							if(numGuardias != null){
								d.setNumGuardias(numGuardias);
							}
							if(turno != null){
							d.setTurno(turno);
							}
							if(guardia != null){
							d.setGuardia(guardia);
							}
							if(observacionesSt != null){
							d.setObservaciones(observacionesSt);
							}
							programacionItemListFull.add(d);
						});
						programacionItemListFull.forEach(programacionItem -> {
								HcoConfProgCalendariosItem hcoConfProgCalendariosItem = new HcoConfProgCalendariosItem();						
							try {
							if(programacionItem.getEstado().equals(ESTADO_PROGRAMADO)) {
								//Insertamos en el historico
								String res = scsGuardiasturnoExtendsMapper.checkHistorico(programacionItem, null);
								if (res == null) {
									scsGuardiasturnoExtendsMapper.insertarHistorico(programacionItem, null);
								}
								
								programacionItem.setEstado(EN_PROCESO);
								scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
								
							}else if(programacionItem.getEstado().equals(REPROGRAMADO)){
								programacionItem.setEstado(EN_PROCESO);
								scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
							}
							
							
							//Obtenemos la siguiente guardia programada y no generada
							hcoConfProgCalendariosItem = scsGuardiasturnoExtendsMapper.getNextGuardiaProgramadaNoGenerada(programacionItem, null);
							if (hcoConfProgCalendariosItem != null) {
								hcoConfProgCalendariosItem.setEstado(EN_PROCESO);
								scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
								
								// El metodo crear calerndario nos creara los calendarios. Hay mas de uno ya que pueden tener guardias vincualdas
								String textoAutomatico = "Calendario generado automáticamente desde la programación de calendarios";
								int idCalendario =  crearCalendario(guardiaBean, programacionItem, hcoConfProgCalendariosItem.getIdinstitucion(), hcoConfProgCalendariosItem.getIdturno(),
										hcoConfProgCalendariosItem.getIdguardia(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), textoAutomatico, null, null, null);
								if(idCalendario<=0) {
									throw new Exception("generarCalendarioAsync: Error al crear el Calendario de guardias");
								}
								
								inicializaParaGenerarCalendario(new Integer(hcoConfProgCalendariosItem.getIdinstitucion()),new Integer(hcoConfProgCalendariosItem.getIdturno()),
										new Integer(hcoConfProgCalendariosItem.getIdguardia()),new Integer(idCalendario), programacionItem.getFechaDesde(),programacionItem.getFechaHasta());
								generarCalendario2();
			
								programacionItem.setEstado(GENERADO);//FINALIZADO
								scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
								String nombreFicheroSalida =  idTurno1 + "." + idGuardia1 + "." + idCalendarioGuardias1 + "-" + fechaInicio1.replace('/', '.') + "-" + fechaFin1.replace('/', '.') + "-log";
								scsGuardiasturnoExtendsMapper.setLogName(programacionItem.getIdInstitucion(), programacionItem.getIdCalendarioProgramado().toString(), programacionItem.getObservaciones(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), nombreFicheroSalida, programacionItem.getIdTurno(), programacionItem.getIdGuardia());
								generarExcelLog(nombreFicheroSalida);
								//si ya no quedan guardias pendientes de esta programacion la ponemos en estado finalizada
								if(scsGuardiasturnoExtendsMapper.getNextGuardiaConfigurada(null, programacionItem.getIdCalendarioProgramado())==null){
									programacionItem.setEstado(GENERADO);//FINALIZADO
									scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
								}
							}
							
							//tx.commit();
						if (hcoConfProgCalendariosItem == null && error.getDescription() == null)
						{
							error.setCode(400);
							insertResponseDTO.setStatus(SigaConstants.KO);
						} else if (error.getCode() == null) {
							error.setCode(200);
							insertResponseDTO.setStatus(SigaConstants.OK);
						}
						} catch (Exception e) {
							LOGGER.error(
							"generarCalendarioAsync() -> Se ha producido un error al trabajar con el histórico",
							e);
							if(hcoConfProgCalendariosItem!=null){
								hcoConfProgCalendariosItem.setEstado(PROCESADO_CON_ERRORES);
								scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
								
							}
							programacionItem.setEstado(PROCESADO_CON_ERRORES);
							scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, null);
							
							error.setCode(500);
							insertResponseDTO.setStatus(SigaConstants.KO);
							error.setDescription(e.toString());
						}
						});
					}
						LOGGER.info("generarCalendarioAsync() -> Entrada para obtener los datos del calendario");
		} catch (Exception e) {
			LOGGER.error(
					"generarCalendarioAsync() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
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
	public InsertResponseDTO generarCalendario (HttpServletRequest request, DatosCalendarioProgramadoItem programacionItem) {
		LOGGER.info("generarCalendario() -> Entrada al servicio para búsqueda de las guardias");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		HcoConfProgCalendariosItem hcoConfProgCalendariosItem = new HcoConfProgCalendariosItem();
		GuardiasTurnoItem guardiaBean = new GuardiasTurnoItem();

		
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				if (usuarios != null && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					usuModificacion1 = usuario.getIdusuario();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String today = formatter.format(new Date());
					LOGGER.info(
							"generarCalendario() / scsGuardiasturnoExtendsMapper.comboGuardias() -> Entrada a scsGuardiasturnoExtendsMapper para obtener las guardias");
					
					
					try {
					if(programacionItem.getEstado().equals(ESTADO_PROGRAMADO)) {
						//Insertamos en el historico
						String res = scsGuardiasturnoExtendsMapper.checkHistorico(programacionItem, idInstitucion.toString());
						if (res == null) {
							scsGuardiasturnoExtendsMapper.insertarHistorico(programacionItem, idInstitucion.toString());
						}
						
						programacionItem.setEstado(EN_PROCESO);
						scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
						
					}else if(programacionItem.getEstado().equals(REPROGRAMADO)){
						programacionItem.setEstado(EN_PROCESO);
						scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
					}
					
	
					//Obtenemos la siguiente guardia programada y no generada
					hcoConfProgCalendariosItem = scsGuardiasturnoExtendsMapper.getNextGuardiaProgramadaNoGenerada(programacionItem, idInstitucion.toString());
					if (hcoConfProgCalendariosItem != null) {
						hcoConfProgCalendariosItem.setEstado(EN_PROCESO);
						scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
						
						// El metodo crear calerndario nos creara los calendarios. Hay mas de uno ya que pueden tener guardias vincualdas
						String textoAutomatico = "Calendario generado automáticamente desde la programación de calendarios";
						int idCalendario =  crearCalendario(guardiaBean, programacionItem, hcoConfProgCalendariosItem.getIdinstitucion(), hcoConfProgCalendariosItem.getIdturno(),
								hcoConfProgCalendariosItem.getIdguardia(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), textoAutomatico, null, null, null);
						if(idCalendario<=0) {
							throw new Exception("Error al crear el Calendario de guardias");
						}
						
						inicializaParaGenerarCalendario(new Integer(hcoConfProgCalendariosItem.getIdinstitucion()),new Integer(hcoConfProgCalendariosItem.getIdturno()),
								new Integer(hcoConfProgCalendariosItem.getIdguardia()),new Integer(idCalendario), programacionItem.getFechaDesde(),programacionItem.getFechaHasta());
						generarCalendario2();
	
						programacionItem.setEstado(GENERADO);//FINALIZADO
						scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
						String nombreFicheroSalida =  idTurno1 + "." + idGuardia1 + "." + idCalendarioGuardias1 + "-" + fechaInicio1.replace('/', '.') + "-" + fechaFin1.replace('/', '.') + "-log";
						scsGuardiasturnoExtendsMapper.setLogName(programacionItem.getIdInstitucion(), programacionItem.getIdCalendarioProgramado().toString(), programacionItem.getObservaciones(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), nombreFicheroSalida, programacionItem.getIdTurno(), programacionItem.getIdGuardia());
						generarExcelLog(nombreFicheroSalida);
						//si ya no quedan guardias pendientes de esta programacion la ponemos en estado finalizada
						if(scsGuardiasturnoExtendsMapper.getNextGuardiaConfigurada(idInstitucion.toString(), programacionItem.getIdCalendarioProgramado())==null){
							programacionItem.setEstado(GENERADO);//FINALIZADO
							scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
						}
					}
						//tx.commit();
					if (hcoConfProgCalendariosItem == null && error.getDescription() == null)
					{
						error.setCode(400);
						insertResponseDTO.setStatus(SigaConstants.KO);
					} else if (error.getCode() == null) {
						error.setCode(200);
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					} catch (Exception e) {
						LOGGER.error(
						"generarCalendario() -> Se ha producido un error al trabajar con el histórico",
						e);
						if(hcoConfProgCalendariosItem!=null){
							hcoConfProgCalendariosItem.setEstado(PROCESADO_CON_ERRORES);
							scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
							
						}
						programacionItem.setEstado(PROCESADO_CON_ERRORES);
						scsGuardiasturnoExtendsMapper.updateEstado(programacionItem, idInstitucion.toString());
						
						error.setCode(500);
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setDescription(e.toString());
					}
						LOGGER.info("generarCalendario() -> Entrada para obtener los datos del calendario");
					}
			}
		} catch (Exception e) {
			LOGGER.error(
					"generarCalendario() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		insertResponseDTO.setError(error);
		return insertResponseDTO;
		}
	
	
	int crearCalendario(GuardiasTurnoItem guardiaBean, DatosCalendarioProgramadoItem programacionItem, String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String observaciones, Integer idTurnoPrincipal, Integer idGuardiaPrincipal, Integer idCalendarioPrincipal) throws Exception{
		List<CabeceraGuardiasCalendarioItem> cabGuardiaList = new ArrayList<CabeceraGuardiasCalendarioItem>();	
		String idPersonaUltimoAnterior, fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior;
		//Comprobamos que no haya ninguna cabecera de calendario para el turno , guardia,
		//fecha desde y fecha hasta. Si esta generada damos error. si no lo esta la cogemos 
		//y le concatenamos las observaciones
			List<GuardiasCalendarioItem> calGuardiaList = scsGuardiasturnoExtendsMapper.getCalGuardiavVector(programacionItem, idInstitucion.toString());
			if (calGuardiaList != null && calGuardiaList.size() > 0) {
//				calGuardiaList.forEach(calGuardia -> {
				GuardiasCalendarioItem calGuardia = calGuardiaList.get(0);

				
				String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
				String NEW_FORMAT = "dd/MM/yyyy";
				String fechainicio2 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calGuardia.getFechainicio());
				calGuardia.setFechainicio(fechainicio2);
				String fechafin2 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, calGuardia.getFechafin());
				calGuardia.setFechafin(fechafin2);
					CabeceraGuardiasCalendarioItem cabGuardia = scsGuardiasturnoExtendsMapper.cabGuardiavVector(calGuardia, idInstitucion.toString());
					if (cabGuardia != null) {
						cabGuardiaList.add(cabGuardia);
					}

					
					if (cabGuardiaList != null && cabGuardiaList.size() > 0) {
						throw new Exception("El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta + "] ya está generado");
					}
					// modificamos las observaciones
					if (calGuardia.getObservaciones() != null && !calGuardia.getObservaciones().equals("") && observaciones != null && !calGuardia.getObservaciones().equals(observaciones)) {
						calGuardia.setObservaciones(calGuardia.getObservaciones() + ". " + observaciones);
			//			String[] claves = { ScsCalendarioGuardiasBean.C_IDINSTITUCION, ScsCalendarioGuardiasBean.C_IDGUARDIA, ScsCalendarioGuardiasBean.C_IDTURNO, ScsCalendarioGuardiasBean.C_IDCALENDARIOGUARDIAS };
			//			String[] campos = { ScsCalendarioGuardiasBean.C_OBSERVACIONES };
						//admCalendarioGuardia.updateDirect(admCalendarioGuardia.beanToHashTable(calendarioGuardiasBean), claves, campos);
					}			
//				});
					return Integer.parseInt(calGuardia.getIdcalendarioguardias());
			}
			

			
			// Calculando idPersonaUltimoAnterior de guardias turno
			List<GuardiasTurnoItem> guardiasTurnoList = scsGuardiasturnoExtendsMapper.getIdPersonaUltimoAnterior(idTurno, idGuardia, idInstitucion);
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
			
			String idcalendarioguardias =  scsGuardiasturnoExtendsMapper.getIdCalendarioGuardias(idTurno, idGuardia, idInstitucion);
			if (idcalendarioguardias.equals("")){
				idcalendarioguardias = "1";
				}
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(new Date());
			String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
			String OLD_FORMAT2 = "yyyy-MM-dd HH:mm:ss.S";
			String OLD_FORMAT = "dd/MM/yyyy";
//			if (!fechaSuscUltimoAnterior.isEmpty()) {
//			fechaSuscUltimoAnterior = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaSuscUltimoAnterior);
//			}
			
			try {
				String fechaHastaOK = null;
				if (!fechaHasta.isEmpty() && fechaHasta != null) {
					fechaHastaOK = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaHasta);
				}
				String fechaDesdeOK = null;
				if (!fechaDesde.isEmpty() && fechaDesde != null) {
					fechaDesdeOK = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaDesde);
				}
				String todayOK = null;
				if (!today.isEmpty() && today != null) {
				    todayOK = changeDateFormat(OLD_FORMAT, NEW_FORMAT, today);
				}
				String fechaSuscUltimoAnteriorOK = null;
				if (!fechaSuscUltimoAnterior.isEmpty() && fechaSuscUltimoAnterior != null) {
					fechaSuscUltimoAnteriorOK = changeDateFormat(OLD_FORMAT2, NEW_FORMAT, fechaSuscUltimoAnterior);
				}
				scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(idTurnoPrincipal, idGuardiaPrincipal, idCalendarioPrincipal, observaciones, idTurno, idGuardia, fechaHastaOK, fechaDesdeOK, idcalendarioguardias, idInstitucion, idPersonaUltimoAnterior, todayOK , fechaSuscUltimoAnteriorOK, idGrupoGuardiaColegiadoAnterior, usuModificacion1.toString());
				return Integer.parseInt(idcalendarioguardias);
			}catch(Exception e) {
				return 0;
			}

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
	
	public void inicializaParaGenerarCalendario(Integer idInstitucion, Integer idTurno,
			Integer idGuardia, Integer idCalendarioGuardias, String fechaInicio, String fechaFin) {
		idInstitucion1 = idInstitucion;
		idTurno1 = idTurno;
		idGuardia1 = idGuardia;
		idCalendarioGuardias1 = idCalendarioGuardias;
		fechaInicio1 = fechaInicio;
		fechaFin1 = fechaFin;
	}
	
	public void generarCalendario2() throws Exception{
		String porGrupos = "false";
		boolean rotacion = false;
		
		GuardiasTurnoItem guardia = scsGuardiasturnoExtendsMapper.getGuardia(idGuardia1.toString(), idTurno1.toString(), idInstitucion1.toString());
		porGrupos = guardia.getPorGrupos();
		rotacion = Boolean.parseBoolean(guardia.getRotarComponentes());
		String nombreGuardia = guardia.getNombre();
		
		// validando que no haya ninguna guardia realizada
		
		if (!validarBorradoGuardias(idInstitucion1,
				idCalendarioGuardias1, idTurno1, idGuardia1)) {
			LOGGER.error("No se puede borrar un CALENDARIO de guardias porque existen guardias realizadas");
//			throw new Exception(
//					"No se puede borrar un CALENDARIO de guardias porque existen guardias realizadas");
		}
		
		// obteniendo las Guardias vinculadas
		List<GuardiasTurnoItem> guardiasVinculadas = scsGuardiasturnoExtendsMapper.getGuardiasVnculadas(idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString());
		
//		log = LogFileWriter
//				.getLogFileWriter(
//						rp.returnProperty("sjcs.directorioFisicoGeneracionCalendarios")
//								+ File.separator + idInstitucion,
//						getNombreFicheroLogCalendario(idTurno, idGuardia,
//								idCalendarioGuardias, fechaInicio, fechaFin));
//								
//				sjcs.directorioFisicoGeneracionCalendarios = /Datos/SIGA/ficheros/calendarios\2005
//				
//				getNombreFicheroLogCalendario =	(idTurno + "." + idGuardia + "." + idCalendarioGuardias + "-"
//				+ fechaInicio.replace('/', '.') + "-" + fechaFin.replace('/',
//				'.')) = 809.701.26-12.07.2021-24.07.2021
//					
//				log.clear();

				// iniciando transaccion
//				tx = this.usrBean.getTransactionPesada();
//				tx.begin();
				
				// si hay guardias vinculadas, hay que crear los calendarios antes que nada
				String textoAutomatico = "";
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				List<GuardiasCalendarioItem> calendariosVinculados = new ArrayList<GuardiasCalendarioItem>();
				if (guardiasVinculadas.size() != 0) {
				for (GuardiasTurnoItem guardiaV : guardiasVinculadas) {
//					lineaLog = new ArrayList<String>();
//					lineaLog.add("Creando calendario para:");
//					lineaLog.add("Guardia vinculada '" + guardiaV.getNombre()
//							+ "'...");
					GuardiasCalendarioItem calendarioGuardiasBean = new GuardiasCalendarioItem(
					guardiaV.getIdInstitucion().toString(),
					guardiaV.getIdTurno().toString(),
					guardiaV.getIdGuardia().toString(),
					null,
					fechaFin1.toString(),
					fechaInicio1.toString(),
					textoAutomatico,
					today,
					"0",
					null,
					null,
					null,
					new Integer(idTurno1).toString(), new Integer(idGuardia1).toString(),
					new Integer(idCalendarioGuardias1).toString());
					
					
					int idCalendario = crearCalendario2(calendarioGuardiasBean.getIdinstitucion(),
					calendarioGuardiasBean.getIdturno(),
					calendarioGuardiasBean.getIdguardia(),
					calendarioGuardiasBean.getFechainicio(),
					calendarioGuardiasBean.getFechafin(),
					calendarioGuardiasBean.getObservaciones(),
					new Integer(calendarioGuardiasBean.getIdturnoprincipal()),
					new Integer(calendarioGuardiasBean.getIdguardiaprincipal()),
					new Integer(calendarioGuardiasBean.getIdcalendarioguardiasprincipal()));
					
					
					if (idCalendario > 0) {
						calendarioGuardiasBean
								.setIdcalendarioguardias(Integer.toString(idCalendario));

						//lineaLog.add("OK");
						calendariosVinculados.add(calendarioGuardiasBean);
						} else {
							//lineaLog.add("Fallo. Puede que ya exista el calendario");
						//log.addLog(lineaLog);
							Map<String, Object> mapLog = new HashMap();
							mapLog.put("*Fallo. Puede que ya exista el calendario", "");
							listaDatosExcelGeneracionCalendarios.add(mapLog);
							LOGGER.info("*Fallo. Puede que ya exista el calendario");
						}
				
						}
				}
				
						// inicializando calendario
						inicializaParaMatriz(new Integer(
						idInstitucion1), new Integer(idTurno1),
						new Integer(idGuardia1), new Integer(idCalendarioGuardias1),
						calendariosVinculados,  null); //enviar log en lugar de null en el ultimo param
						
						// obteniendo los periodos
						try {
							LOGGER.info(beanGuardiasTurno1);
							calcularMatrizPeriodosDiasGuardiaAutomatico();
							List<Integer> lDiasASeparar = getDiasASeparar(new Integer(
									idInstitucion1), new Integer(idTurno1),
									new Integer(idGuardia1));

							// obteniendo la matriz de letrados de guardia
							listaDatosExcelGeneracionCalendarios = new ArrayList<>();
							Map<String, Object> mapLog = new HashMap();
							mapLog.put("*INICIO generacion", guardia.getNombre() + " (" + fechaInicio1 + " - "
											+ fechaFin1 + ")");
							listaDatosExcelGeneracionCalendarios.add(mapLog);
							LOGGER.info(new String[] {
									"*INICIO generacion",
									guardia.getNombre() + " (" + fechaInicio1 + " - "
											+ fechaFin1 + ")" });
							
							if (porGrupos.equals("1")) {
								calcularMatrizLetradosGuardiaPorGrupos(
										lDiasASeparar, rotacion);
							} else {
								calcularMatrizLetradosGuardia(lDiasASeparar);
							}
							
							//log.addLog(new String[] { "FIN generacion" });
							Map<String, Object> mapLog1 = new HashMap();
							mapLog1.put("*FIN generacion", "");
							listaDatosExcelGeneracionCalendarios.add(mapLog1);
							LOGGER.info("*FIN generacion" + "");
							
						} catch (Exception e) {
							if(e.equals("periodoSinDias")){
								//log.addLog(new String[] { " ", "Sin periodos" } );
								
								Map<String, Object> mapLog = new HashMap();
								mapLog.put("* ", "Sin periodos");
								listaDatosExcelGeneracionCalendarios.add(mapLog);
								LOGGER.info("* " + "Sin periodos");
							}else{
								throw e;
							}

						}
			
	}
	
	//Comprueba antes de borrar un CALENDARIO de guardias que no exista ninguna guardia realizada. 
	public boolean validarBorradoGuardias(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {
	int totalLetrados = 0;
	boolean correcto = false;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(new Date());
			totalLetrados = scsGuardiasturnoExtendsMapper.getTotalLetrados(idInstitucion.toString(), idCalendarioGuardias.toString(), idTurno.toString(), idGuardia.toString(), today);

			if (totalLetrados == 0)
				correcto = true;
		} catch (Exception e) {
			correcto = false;
		}
		return correcto;
	}
	
	public void inicializaParaMatriz(Integer idInstitucion, Integer idTurno, Integer idGuardia, Integer idCalendarioGuardias,
			List<GuardiasCalendarioItem> calendariosVinculados, LogFile log) throws Exception{
		List<GuardiasTurnoItem> vGuardias; 
		// 1. GUARDIATURNO: Todos los datos de la guardia
		
		try {
			vGuardias = scsGuardiasturnoExtendsMapper.getAllDatosGuardia(idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString());
			if (!vGuardias.isEmpty())
				beanGuardiasTurno1 = vGuardias.get(0);
		} catch (Exception e) {
			beanGuardiasTurno1= null;
		}
		// Calendarios vinculados (guardias vinculadas)
		if (calendariosVinculados == null || calendariosVinculados.isEmpty())
			calendariosVinculados1 = null;
		else
			calendariosVinculados1 = calendariosVinculados;
		
		
		// 2. CALENDARIO: Fecha de inicio y fin
		try {
			List<GuardiasCalendarioItem> vCalendarioGuardias = scsGuardiasturnoExtendsMapper.getCalGuardias(idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString());
			
			if (!vCalendarioGuardias.isEmpty()) {
				GuardiasCalendarioItem beanCalendarioGuardias = (GuardiasCalendarioItem) vCalendarioGuardias.get(0);
				
				String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
				String NEW_FORMAT = "dd/MM/yyyy";
				fechaInicio1 = changeDateFormat(OLD_FORMAT, NEW_FORMAT, beanCalendarioGuardias.getFechainicio());
				fechaFin1 = changeDateFormat(OLD_FORMAT, NEW_FORMAT,beanCalendarioGuardias.getFechafin());
			}
		} catch (Exception e) {
			fechaInicio1 = "";
			fechaFin1 = "";
		}
		
		 
		// 3. CALENDARIO FESTIVOS:
		Calendar calFechaFin = StringToCalendar(fechaFin1);
		calFechaFin.add(Calendar.MONTH, 1); //sumamos 1 mes
		// esto se hace por si la generacion llega a otros dias posteriores a la fecha de fin; esto es, 
		// cuando la configuracion es por semanas/quincenas/meses 
		// pero el calendario termina antes del ultimo dia de la semana/quincena/mes
		vDiasFestivos1 = obtenerFestivosTurno(idInstitucion, idTurno, fechaInicio1, calFechaFin.getTime());

		// 4. INICIALIZACION DE LOS 2 ARRAYS:
		arrayPeriodosDiasGuardiaSJCS1 = new ArrayList();
	    arrayPeriodosLetradosSJCS1 = new ArrayList();
		//this.log = log;
	}
	
	List<String> obtenerFestivosTurno(Integer idInstitucion, Integer idTurno, String fechaInicio, Date fechaFin) throws Exception{
		 LocalDate date4 = ZonedDateTime
		            .parse(fechaFin.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
		            .toLocalDate();
		 	String OLD_FORMAT = "yyyy-MM-dd";
			String NEW_FORMAT = "dd/MM/yyyy";
			String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		return scsGuardiasturnoExtendsMapper.getFestivosTurno(fechaInicio, fechaFinOk.toString(), idInstitucion1.toString(), Integer.toString(INSTITUCION_CGAE), idTurno.toString());
	}
	
	Calendar StringToCalendar(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY", Locale.ENGLISH);
		cal.setTime(sdf.parse(date));
		return cal;
	}
	
	
	public void calcularMatrizPeriodosDiasGuardiaAutomatico() throws Exception
	{
		// generando calendario normal
		arrayPeriodosDiasGuardiaSJCS1 = new ArrayList();
		ArrayList<ArrayList<String>> listaDiasPeriodos = this.obtenerPeriodosGuardia();
		if (listaDiasPeriodos != null && !listaDiasPeriodos.isEmpty())
			arrayPeriodosDiasGuardiaSJCS1.addAll(listaDiasPeriodos);
		else
			throw new Exception("periodoSinDias");
		
		// En el caso de que existan guardias vinculadas, se ha de generar un periodo mas:
		// Pero no sirve generar un calendario ampliado (que termine mas tarde), 
		// porque no hay forma de saber cual es el ultimo dia del calendario real
		// Asi que la unica forma es: 
		// generar el calendario real (antes) y (ahora) uno ampliado para obtener el periodo de mas
		if (calendariosVinculados1 != null) {
			// obteniendo una fechaFin suficientemente posterior
			try {
				SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT_JAVA);
				SimpleDateFormat miFormatoFecha = new SimpleDateFormat (DATE_FORMAT_SHORT_SPANISH);
				Date dFechaInicio = sdf.parse (fechaInicio1);
				ArrayList<String> ultimoPeriodo = listaDiasPeriodos.get(listaDiasPeriodos.size()-1);
				Date dFechaFin = miFormatoFecha.parse (ultimoPeriodo.get(ultimoPeriodo.size()-1));
				
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
	
	
	private ArrayList<ArrayList<String>> obtenerPeriodosGuardia() throws Exception
	{
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
			CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(beanGuardiasTurno1, fechaInicio1, fechaFin1, vDiasFestivos1);
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
	
	
	public List<Integer> getDiasASeparar(Integer idInstitucion, Integer idTurno, Integer idGuardia) throws Exception{
		List<String> alDiasASeparar = getDiasASeparar2(idInstitucion, idTurno, idGuardia);
		List<Integer> alDiasASepararEnUnidadesDiarias = null;
		if(alDiasASeparar!=null && alDiasASeparar.size()>0){
			alDiasASepararEnUnidadesDiarias = new ArrayList();
			for (int i=0; i<alDiasASeparar.size(); i++){
				String diasSemana = alDiasASeparar.get(i);
				for (int j = 0; j < diasSemana.length(); j++) {
					char diaSemana = diasSemana.charAt(j);
					
					alDiasASepararEnUnidadesDiarias.add(new Integer 
							(CalendarioAutomatico.convertirUnidadesDiasSemana (diaSemana)));
				}
			}
		}
		
		return alDiasASepararEnUnidadesDiarias;
	}
	
	public List<String> getDiasASeparar2(Integer idInstitucion, Integer idTurno, Integer idGuardia)
			throws Exception
			{
				String agrupar = "0";
				return scsGuardiasturnoExtendsMapper.getDiasASeparar(idGuardia1.toString(), idTurno1.toString(), idInstitucion1.toString(), agrupar);
			}
	
	public int crearCalendario2(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String observaciones, Integer idTurnoPrincipal, Integer idGuardiaPrincipal, Integer idCalendarioPrincipal) throws Exception {


		String idPersonaUltimoAnterior, fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior;


		//Comprobamos que no haya ninguna cabecera de calendario para el turno , guardia,
		//fecha desde y fecha hasta. Si esta generada damos error. si no lo esta la cogemos 
		//y le concatenamos las observaciones

		List<GuardiasCalendarioItem> calGuardiavVector = scsGuardiasturnoExtendsMapper.getCalGuardiavVector2(idTurno.toString(), idGuardia.toString(), fechaDesde.toString(), fechaHasta.toString(), idInstitucion.toString());
		
		if (calGuardiavVector != null && calGuardiavVector.size() > 0) {
			GuardiasCalendarioItem calendarioGuardiasBean = (GuardiasCalendarioItem) calGuardiavVector.get(0);
			StringBuffer sqlCabecera = new StringBuffer();		
			List<CabeceraGuardiasCalendarioItem> cabGuardiavVector = scsGuardiasturnoExtendsMapper.cabGuardiavVector2(calendarioGuardiasBean, idInstitucion.toString());

			if (cabGuardiavVector != null && cabGuardiavVector.size() > 0) {
				throw new Exception("El calendario para esas fechas [" + fechaDesde + "-" + fechaHasta + "] ya está generado");
			}
			// modificamos las observaciones
			if (calendarioGuardiasBean.getObservaciones() != null && !calendarioGuardiasBean.getObservaciones().equals("") && observaciones != null && !calendarioGuardiasBean.getObservaciones().equals(observaciones)) {
				calendarioGuardiasBean.setObservaciones(calendarioGuardiasBean.getObservaciones() + ". " + observaciones);
	//			String[] claves = { ScsCalendarioGuardiasBean.C_IDINSTITUCION, ScsCalendarioGuardiasBean.C_IDGUARDIA, ScsCalendarioGuardiasBean.C_IDTURNO, ScsCalendarioGuardiasBean.C_IDCALENDARIOGUARDIAS };
	//			String[] campos = { ScsCalendarioGuardiasBean.C_OBSERVACIONES };
				//admCalendarioGuardia.updateDirect(admCalendarioGuardia.beanToHashTable(calendarioGuardiasBean), claves, campos);
			}			
//		});
			return Integer.parseInt(calendarioGuardiasBean.getIdcalendarioguardias());
	}
	
	// Calculando idPersonaUltimoAnterior de guardias turno
	List<GuardiasTurnoItem> guardiasTurnoList = scsGuardiasturnoExtendsMapper.getIdPersonaUltimoAnterior(idTurno.toString(), idGuardia.toString(), idInstitucion.toString());
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
	
	String idcalendarioguardias2 =  scsGuardiasturnoExtendsMapper.getIdCalendarioGuardias(idTurno.toString(), idGuardia.toString(), idInstitucion.toString());
	if (idcalendarioguardias2.equals("")){
		idcalendarioguardias2 = "1";
		}
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String today = formatter.format(new Date());
	
	try {
		scsGuardiasturnoExtendsMapper.insertarRegistroCalendarioGuardias(idTurnoPrincipal, idGuardiaPrincipal, idCalendarioPrincipal, observaciones, idTurno.toString(), idGuardia.toString(), fechaHasta, fechaDesde, idcalendarioguardias2, idInstitucion.toString(), idPersonaUltimoAnterior, today, fechaSuscUltimoAnterior, idGrupoGuardiaColegiadoAnterior, usuModificacion1.toString());
		return Integer.parseInt(idcalendarioguardias2);
	}catch(Exception e) {
		return 0;
	}
	}
	
	
	/**
	 * Genera las cabeceras de guardias del calendario, a partir de la lista de dias/periodos
	 * Cuando la guardia es por grupos
	 * Nota: previamente se ha calculado mediante el metodo "calcularMatrizPeriodosDiasGuardia()"
	 */
	public void calcularMatrizLetradosGuardiaPorGrupos(List<Integer> lDiasASeparar, boolean rotacion) throws Exception
	{

		// Variables
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con letrado
		int numeroLetradosGuardia; // Numero de letrados necesarios para cada periodo
		
		HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales;
		ArrayList<SaltoCompGuardiaGrupoItem> alSaltos; // Lista de saltos
		ArrayList<SaltoCompGuardiaGrupoItem> alCompensaciones; // Lista de compensaciones
		
		ArrayList<LetradoInscripcionItem> alLetradosOrdenados= new ArrayList<LetradoInscripcionItem>();  // Cola de letrados en la guardia
		
		int posicion; // Orden de cada componente en la cola y en la lista de guardias generada
		ArrayList<LetradoInscripcionItem> grupoLetrados; // Lista de letrados en el grupo
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		Puntero punteroListaLetrados;
		LetradoInscripcionItem unLetrado = null;
		
		ScsGrupoguardiacolegiado beanGrupoLetrado; // Bean para guardar cambios en los grupos
		Hashtable hashGrupoLetrado; // Hash para guardar cambios en los grupos
		
		ArrayList<String> lineaLog; // Linea para escribir en LOG
		
		Long idPersona = null;
		String fechaSubs="";

		
		try {
			
			// Crea una copia de la cola de guardias de grupos de un calendario
			crearRegistroGrupoGuardiaColegiadoCalendario();
			
			// obteniendo bajas temporales por letrado
			String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0)).get(0);
			ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(arrayPeriodosDiasGuardiaSJCS1.size()-1);
			String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size()-1);
			hmBajasTemporales = getLetradosDiasBajaTemporal(idInstitucion1, idTurno1, idGuardia1, primerDia, ultimoDia);
			
			// obteniendo saltos
			alSaltos = getSaltosCompensacionesPendientesGuardia(idInstitucion1, idTurno1, idGuardia1, null, "S");
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmGruposConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
			ArrayList<LetradoInscripcionItem> grupoConSaltos;
			for (SaltoCompGuardiaGrupoItem bean : alSaltos) {
				if ((grupoConSaltos = (ArrayList<LetradoInscripcionItem>) hmGruposConSaltos.get(bean.getIdGrupoGuardia())) == null) {
					grupoConSaltos = new ArrayList<LetradoInscripcionItem>();
					grupoConSaltos.add(bean.getLetrados().get(0)); // se inserta uno de los letrados, que lleva ya el idSaltoCompensacionGrupo
					hmGruposConSaltos.put(bean.getIdGrupoGuardia(), grupoConSaltos);
				}
				else {
					grupoConSaltos.add(bean.getLetrados().get(0)); // se inserta uno de los letrados, que lleva ya el idSaltoCompensacionGrupo
				}
			}

			// obteniendo numero de letrados necesarios para cada periodo
			numeroLetradosGuardia = beanGuardiasTurno1.getNumeroLetradosGuardia().intValue();
			//log.addLog(new String[] {"Minimo Letrados por Grupo", Integer.toString(numeroLetradosGuardia)});
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
				//log.addLog(new String[] {"Guardias vinculadas", this.calendariosVinculados.toString()});
				Map<String, Object> mapLog2 = new HashMap();
				mapLog2.put("*Guardias vinculadas", calendariosVinculados1.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Guardias vinculadas" + calendariosVinculados1.toString());
				inicial = 1;
			}
			
			// Para cada dia o conjunto de dias:
			for (int i = inicial; i < arrayPeriodosDiasGuardiaSJCS1.size(); i++) {
				// obteniendo conjunto de dias
				// Nota: cada periodo es un arraylist de fechas (String en formato de fecha corto DD/MM/YYYY)
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
				alLetradosOrdenados = getColaGuardia(idInstitucion1, idTurno1, idGuardia1,
						(String) diasGuardia.get(0), (String) diasGuardia.get(diasGuardia.size() - 1));
				
				//log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
				
				Map<String, Object> mapLog4 = new HashMap();
				mapLog4.put("*Cola", alLetradosOrdenados.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog4);
				LOGGER.info("*Cola" + alLetradosOrdenados.toString());

				if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//					throw new Exception("No existe cola de letrados de guardia");
					LOGGER.error("No existe cola de letrados de guardia");

				// obteniendo las compensaciones. Se obtienen dentro de este
				// bucle, ya que si hay incompatibilidades se añade una compensacion
				alCompensaciones = getSaltosCompensacionesPendientesGuardia(idInstitucion1, idTurno1, idGuardia1,(String) diasGuardia.get(diasGuardia.size() - 1), "C");
//				log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
				Map<String, Object> mapLog5 = new HashMap();
				mapLog5.put("*Compensaciones ", alCompensaciones.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog5);
				LOGGER.info("*Compensaciones " + alCompensaciones.toString());
//				log.addLog(new String[] {"Saltos", hmGruposConSaltos.toString()});
				Map<String, Object> mapLog6 = new HashMap();
				mapLog6.put("*Saltos", hmGruposConSaltos.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog6);
				LOGGER.info("*Saltos" + hmGruposConSaltos.toString());

				// buscando grupo que no tenga restricciones (incompatibilidades, bajas temporales, saltos)
				grupoLetrados = getSiguienteGrupo(alCompensaciones, alLetradosOrdenados, punteroListaLetrados, diasGuardia, hmGruposConSaltos, hmBajasTemporales);
				
				if (grupoLetrados == null) {
//					log.addLog(new String[] {"FIN generacion", "Todos los grupos tienen restricciones que no permiten generar el calendario"});
					Map<String, Object> mapLog7 = new HashMap();
					mapLog7.put("*FIN generacion", " Todos los grupos tienen restricciones que no permiten generar el calendario");
					listaDatosExcelGeneracionCalendarios.add(mapLog7);
					LOGGER.info("*FIN generacion");
//					throw new Exception("Todos los grupos tienen restricciones que no permiten generar el calendario");
					LOGGER.error("Todos los grupos tienen restricciones que no permiten generar el calendario");
				}
				else {
//					log.addLog(new String[] {"Grupo seleccionado", grupoLetrados.toString()});
				Map<String, Object> mapLog8 = new HashMap();
				mapLog8.put("*Grupo seleccionado ", grupoLetrados.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog8);
				LOGGER.info("*Grupo seleccionado " + grupoLetrados.toString());
				// comprobando minimo de letrados en la guardia
				if (grupoLetrados.size() < numeroLetradosGuardia) {
//					log.addLog(new String[] {"¡¡ AVISO !!", "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: " + grupoLetrados.size() + " < " + numeroLetradosGuardia});
				Map<String, Object> mapLog9 = new HashMap();
				mapLog9.put("*¡¡ AVISO !!", "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: " + grupoLetrados.size() + " < " + numeroLetradosGuardia);
				listaDatosExcelGeneracionCalendarios.add(mapLog9);
				LOGGER.info("*¡¡ AVISO !!" + "El numero de letrados en el grupo es menor que el minimo configurado para la guardia: " + grupoLetrados.size() + " < " + numeroLetradosGuardia);
				}
				// guardando ultimo de cola
				if (rotacion)
					unLetrado = grupoLetrados.get(0);
				else
					unLetrado = grupoLetrados.get(grupoLetrados.size()-1);
				if (unLetrado.getSaltoocompensacion() == null)
					cambiarUltimoCola(unLetrado.getIdinstitucion(), unLetrado.getIdturno(), 
							unLetrado.getIdguardia(), unLetrado.getIdpersona(), 
							unLetrado.getInscripcionGuardia().getFechaSuscripcion().toString(),unLetrado.getIdGrupoGuardiaColegiado());
				
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
						if (posicion == 1){
							letrado.setOrdenGrupo(grupoLetrados.size());
						}else{
							letrado.setOrdenGrupo(posicion-1);
						}
					}else{
							letrado.setOrdenGrupo(posicion);
					}
						
					hashGrupoLetrado = new Hashtable();
					hashGrupoLetrado.put("IDGRUPOGUARDIACOLEGIADO", letrado.getIdGrupoGuardiaColegiado());
					beanGrupoLetrado = new ScsGrupoguardiacolegiado();
					beanGrupoLetrado.setIdgrupoguardia(letrado.getIdGrupoGuardiaColegiado());
					beanGrupoLetrado = (ScsGrupoguardiacolegiado)  scsGrupoguardiacolegiadoMapper.selectByPrimaryKey(letrado.getIdGrupoGuardiaColegiado());
					beanGrupoLetrado.setOrden(letrado.getOrdenGrupo());
					beanGrupoLetrado.setFechamodificacion(new Date());
					beanGrupoLetrado.setUsumodificacion(usuModificacion1);
					scsGuardiasturnoExtendsMapper.updateByPrimaryKeySelective2(beanGrupoLetrado);
					posicion++;
				}
				
				
				//Asignamos valores superiores al tamaño de la lista a los letrados no activos
				reordenarRestoGrupoLetrados(grupoLetrados.get(0).getGrupo(), grupoLetrados.size());
							
				arrayPeriodosLetradosSJCS1.add(alLetradosInsertar);
				
				
				// guardando las guardias en BD
				if (calendariosVinculados1 == null) {
					almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, diasGuardia, lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
							);
				}
				else {
					// guardando la principal
					almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, primerPeriodo, lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//							UtilidadesString.getMensajeIdioma(this.usrBean,
//									"gratuita.literal.comentario.sustitucion")
							);

					// guardando para cada una de las vinculadas
					for (GuardiasCalendarioItem calendario : calendariosVinculados1) {
						// modificando la guardia y calendario en el que se insertaran las guardias
						for (LetradoInscripcionItem lg : alLetradosInsertar) {
							lg.setIdinstitucion(new Integer(calendario.getIdinstitucion()).shortValue());
							lg.setIdturno(new Integer(calendario.getIdturno()));
							lg.setIdguardia(new Integer(calendario.getIdguardia()));
						}
						
						// guardando en BD
						almacenarAsignacionGuardia(new Integer(calendario.getIdcalendarioguardias()), alLetradosInsertar, segundoPeriodo, lDiasASeparar, "gratuita.literal.comentario.sustitucion"
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

		} catch (Exception e) {
			arrayPeriodosLetradosSJCS1 = null;
			throw new Exception(e + "");
		}
	} // calcularMatrizLetradosGuardiaPorGrupos()
	
	
	
	
	
	public HashMap<Long, TreeMap<String, BajasTemporalesItem>> getLetradosDiasBajaTemporal(Integer idInstitucion,
			Integer idTurno,
			Integer idGuardia,
			String fechaDesde,
			String fechaHasta) throws Exception
	{
		BajasTemporalesItem bajasBean;
		String idPersona;
		Date fechaBT;
		TreeMap<String, BajasTemporalesItem> bajasDePersona;
		
		HashMap<Long, TreeMap<String, BajasTemporalesItem>> mSalida = null;
		
		try {

			List<BajasTemporalesItem> datos =  scsGuardiasturnoExtendsMapper.getBajasTemporalesGuardias(idInstitucion.toString(), idTurno.toString(), idGuardia.toString(), fechaDesde, fechaHasta);

			mSalida = new HashMap<Long, TreeMap<String, BajasTemporalesItem>>();
			for (int i = 0; i < datos.size(); i++) {
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
			}

		} catch (Exception e) {
			throw new Exception(e + ": Error al obtener los getDiasBajaTemporal: " + e.toString());
		}
		
		return mSalida;
	}    
	
	
	private void crearRegistroGrupoGuardiaColegiadoCalendario() throws Exception {
		try {
			scsGuardiasturnoExtendsMapper.insertGrupoGuardiaColegiadoCalendario(idCalendarioGuardias1.toString(), idTurno1.toString(), idInstitucion1.toString(), idGuardia1.toString());
			
		} catch (Exception e) {
			throw new Exception("Error al crear la copia de la cola de guardias de grupos de un calendario: " + e);
		}	
	}
	
	
	/**
	 * Obtiene los saltos o las compensaciones pendientes dada una guardia.
	 * Para cada salto o compensacion, anyade al bean los letrados del grupo correspondiente.
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
			Integer idTurno,
			Integer idGuardia,
			String fechaGuardia,
			String saltoOcompensacion) throws Exception
	{
		// Variables
		List<SaltoCompGuardiaGrupoItem> compensaciones;
		ArrayList<SaltoCompGuardiaGrupoItem> resultado;
		SaltoCompGuardiaGrupoItem compensacion;

		// obteniendo las compensaciones
		compensaciones = scsGuardiasturnoExtendsMapper.getSaltosCompensacionesGrupo(saltoOcompensacion, idTurno.toString(), idInstitucion.toString(), idGuardia.toString());

		// obteniendo los letrados de cada grupo que tiene compensacion
		resultado = new ArrayList<SaltoCompGuardiaGrupoItem>();
		for (int i = 0; i < compensaciones.size(); i++) {
			compensacion = compensaciones.get(i);
			ArrayList<LetradoInscripcionItem> letradoInscripciones = getLetradosGrupo(idInstitucion, idTurno, idGuardia,
					compensacion.getIdGrupoGuardia().toString(), saltoOcompensacion, 
					compensacion.getIdSaltoCompensacionGrupo().toString(), fechaGuardia);
			//Si no hay ningun letrado inscrito del grupo que hay que compensar continuamos
			if(letradoInscripciones==null)
				continue;
			compensacion.setLetrados(letradoInscripciones);
			resultado.add(compensacion);
		}

		return resultado;
}
	
	public ArrayList<LetradoInscripcionItem> getLetradosGrupo(Integer idInstitucion, Integer idTurno, Integer idGuardia, String idGrupoGuardia, String saltoCompensacion, String idSaltoCompensacionGrupo, String fechaGuardia) throws Exception {
		try {
	
			// Variables
			List<InscripcionGuardiaItem> vectorLetrados;
			ArrayList<LetradoInscripcionItem> listaLetrados = new ArrayList<LetradoInscripcionItem>();
			LetradoInscripcionItem letradoGuardia;
			InscripcionGuardiaItem inscripcionGuardia;
	
			// obteniendo lista de letrados
			vectorLetrados = getLetradosGrupo(idInstitucion.toString(), idTurno.toString(), idGuardia.toString(), idGrupoGuardia.toString(),fechaGuardia);
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
			throw new Exception (e + ": Error al ejecutar getLetradosGrupo()");
		}				
	} // getLetradosGrupo()
	
	
	
	/**
	 * Obtiene los letrados dado un grupo
	 */
	public List<InscripcionGuardiaItem> getLetradosGrupo(String idInstitucion, String idTurno, String idGuardia, String idGrupoGuardia, String fechaGuardia) throws Exception {
		try  {
			if (idGrupoGuardia == null || idGrupoGuardia.equals(""))
				return null;

			List<InscripcionGuardiaItem> datos = null;

				datos =  scsGuardiasturnoExtendsMapper.getLetradosGrupos(fechaGuardia, idTurno, idInstitucion, idGuardia, idGrupoGuardia);
//				for (int i = 0; i < rc.size(); i++) {
//					Row fila = (Row) rc.get(i);
//					Hashtable<String, Object> htFila = fila.getRow();
//					if(((String)htFila.get("ACTIVO"))!=null && ((String)htFila.get("ACTIVO")).equals("1") )
//						datos.add(beanToHashCola(htFila));
//				}
			
			return datos;
			
		} catch (Exception e) {
			throw new Exception (e + ": Error al ejecutar getLetradosGrupo()");
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
	public ArrayList<LetradoInscripcionItem> getColaGuardia(Integer idInstitucion, Integer idTurno, Integer idGuardia, String fechaInicio, String fechaFin) throws Exception {
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
			ArrayList<LetradoInscripcionItem> colaLetrados = new ArrayList<LetradoInscripcionItem>();
	
			//Actualizar cola guardia para evitar que el ultimo grupo quede a caballo
			actualizarColaGuardiaConUltimoColegiadoPorGrupo(idInstitucion, idTurno, idGuardia);
			
			// obteniendo la guardia

			GuardiasTurnoItem vGuardia =  scsGuardiasturnoExtendsMapper.getGuardia(idGuardia.toString(), idTurno.toString(), idInstitucion.toString());
			beanGuardia = (GuardiasTurnoItem) vGuardia;
			porGrupos = beanGuardia.getPorGrupos().equals("1");//true
			idOrdenacionColas = beanGuardia.getIdOrdenacionColas();
			idPersonaUltimo = beanGuardia.getIdPersona_Ultimo();
			idGrupoGuardiaUltimo = beanGuardia.getIdGrupoGuardiaColegiado_Ultimo();
			if (idGrupoGuardiaUltimo == null) {
				idGrupoGuardiaUltimo2 = "";
			}else {
				idGrupoGuardiaUltimo2 = idGrupoGuardiaUltimo.toString();
			}
			if (beanGuardia.getFechaSuscripcion_Ultimo() != null && !beanGuardia.getFechaSuscripcion_Ultimo().isEmpty()) {
				fechaSuscripcionUltimo = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.S").parse(beanGuardia.getFechaSuscripcion_Ultimo());
			}else {
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
				ultimoAnterior = new InscripcionGuardiaItem(idInstitucion.toString(), idTurno.toString(), idGuardia.toString(), idPersonaUltimo.toString(), fechaSuscripcionUltimo, idGrupoGuardiaUltimo2);
	
			// obteniendo lista de letrados (ordenada)
			List<InscripcionGuardiaItem> listaLetrados = null;
			if(idGuardia!=null) {
				 listaLetrados = getColaGuardia2(idInstitucion.toString(), 
					idTurno.toString(), idGuardia.toString(), fechaInicio, fechaFin, porGrupos, orden);
			}else{
				 listaLetrados = getColaGuardia2(idInstitucion.toString(), 
						idTurno.toString(), null, fechaInicio, fechaFin, porGrupos, orden);
			}	
			if (listaLetrados == null || listaLetrados.size() == 0)
				return colaLetrados;
			
			if (ultimoAnterior == null) {
				// si no existe ultimo colegiado, se empieza la cola desde el primero en la lista
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = (InscripcionGuardiaItem) listaLetrados.get(i);
					if (punteroInscripciones.getEstado() != null && punteroInscripciones.getEstado().equals("1")) //true
					{
						LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
						letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
						colaLetrados.add(letradoInscripcionItem);
					}
						
						
				}
			}
			else {
				// ordenando la cola en funcion del idPersonaUltimo guardado
				List<LetradoInscripcionItem> colaAuxiliar = new ArrayList<LetradoInscripcionItem>();
				foundUltimo = false;
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = listaLetrados.get(i);
	
					// insertando en la cola si la inscripcion esta activa
					if (punteroInscripciones.getEstado() != null && punteroInscripciones.getEstado().equals("1")) { //true
						// El primero que se anyade es el siguiente al ultimo
						LetradoInscripcionItem letradoInscripcionItem = new LetradoInscripcionItem();
						letradoInscripcionItem.setInscripcionGuardia(punteroInscripciones);
						if (foundUltimo) {
							colaLetrados.add(letradoInscripcionItem);
						} else {
							colaAuxiliar.add(letradoInscripcionItem);
						}
					}
					
					// revisando si se encontro ya al ultimo
					if (!foundUltimo && punteroInscripciones.equals(ultimoAnterior))
						foundUltimo = true;
				}
				colaLetrados.addAll(colaAuxiliar);
			}
	
			// usando saltos si es necesario (en guardias no)
	
			return colaLetrados;
			
		} catch (Exception e) {
			throw new Exception (e + ": Error al ejecutar getColaGuardia()");
		}			
	} // getColaGuardia()
	
	
	
	
	
	
	public void actualizarColaGuardiaConUltimoColegiadoPorGrupo(Integer idInstitucion, Integer idTurno, Integer idGuardia)
	{
		// actualizando el ultimo colegiado de la guardia al ultimo colegiado del grupo (que era ultimo de la guardia)
		try {
			// obteniendo el ultimo colegiado del grupo tal que es el ultimo asignado en la cola:
			Hashtable registro = scsGuardiasturnoExtendsMapper.getUltimoColegiadoGrupo(idTurno.toString(), idInstitucion.toString(), idGuardia.toString());
				if (registro != null) {
					Hashtable<String, String> hashGuardiasTurno = new Hashtable<String, String>();
					hashGuardiasTurno.put("IDGUARDIA", idGuardia.toString());
					hashGuardiasTurno.put("IDINSTITUCION", idInstitucion.toString());
					hashGuardiasTurno.put("IDTURNO", idTurno.toString());
					
					GuardiasTurnoItem beanGuardiasTurno = new GuardiasTurnoItem();
					beanGuardiasTurno = scsGuardiasturnoExtendsMapper.getIdPersonaUltimoAnterior(idTurno.toString(), idGuardia.toString(), idInstitucion.toString()).get(0);
					beanGuardiasTurno.setIdPersona_Ultimo(new Long((String) registro.get("IDPERSONA")));
					beanGuardiasTurno.setIdGrupoGuardiaColegiado_Ultimo(new Long((String) registro.get("IDGRUPOGUARDIACOLEGIADO")));
					beanGuardiasTurno.setFechaSuscripcion_Ultimo((String) registro.get("FECHASUSCRIPCION"));
					scsGuardiasturnoExtendsMapper.updateGuardiasTurno(beanGuardiasTurno);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * Obtiene las inscripciones ordenadas para formar la cola de una guardia dada una fecha.
	 * Importante: obtiene todas las inscripciones, no solo las que estan de alta, ya que luego 
	 * hay que ordenarlas segun el puntero, que puede estar apuntando a una inscripcion de baja
	 */
	public List<InscripcionGuardiaItem> getColaGuardia2(String idinstitucion, String idturno, String idguardia, String fechaInicio, String fechaFin, boolean porGrupos, String order) throws Exception {
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

				datos = scsGuardiasturnoExtendsMapper.getColaGuardia(fechaInicio, fechaFin, idinstitucion, idturno, idguardia, order);
//				for (int i = 0; i < rc.size(); i++) {
//					Row fila = (Row) rc.get(i);
//					Hashtable<String, Object> htFila = fila.getRow();
//					datos.add(beanToHashCola(htFila));
//				}
			return datos;
			
		} catch (Exception e) {
			throw new Exception (e + ": Error al ejecutar getColaGuardia()");
		}				
	} //getColaGuardia()
	
	
	
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
	public String getOrderBy(String idOrdenacionColas)
			throws Exception
	{
		// obteniendo el orden
		List<ScsOrdenacioncolas> vOrden = scsGuardiasturnoExtendsMapper.getOrdenacionColas(idOrdenacionColas);
		ScsOrdenacioncolas ordenBean = (ScsOrdenacioncolas) vOrden.get(0);
		Short apellidos = ordenBean.getAlfabeticoapellidos();
		Short antiguedad = ordenBean.getAntiguedadcola();
		Short fechanacimiento = ordenBean.getFechanacimiento();
		Short numerocolegiado = ordenBean.getNumerocolegiado();
		
		// calculando order by
		String orden = "";
		for (int i=4; i>0; i--) {
			if (Math.abs(apellidos) == i) {
				orden += "ALFABETICOAPELLIDOS";
				if (Math.abs(apellidos) != apellidos) orden += " desc";
				orden += "," + "NOMBRE";
				if (Math.abs(apellidos) != apellidos) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(antiguedad) == i) {
				orden += "ANTIGUEDADCOLA";
				if (Math.abs(antiguedad) != antiguedad) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(fechanacimiento) == i) {
				orden += "FECHANACIMIENTO";
				if (Math.abs(fechanacimiento) != fechanacimiento) orden += " desc";
				orden += ", ";
			}
			if (Math.abs(numerocolegiado) == i) {
				//orden += "to_number("+ScsOrdenacionColasBean.C_NUMEROCOLEGIADO+")"; esta linea queda comentada para que se vea que el to_number es peligroso				 
				orden += "lpad(NUMEROCOLEGIADO,20,'0')";
				if (Math.abs(numerocolegiado) != numerocolegiado) orden += " desc";
				orden += ", ";
			}
		} 
		
		if (orden.equals("")) { 
			// En el caso de que no se haya establecido orden, se ordena por antiguedad en la cola
			orden = "ANTIGUEDADCOLA";
			orden += ", ";
		}
		orden = orden.substring(0, orden.length()-2); // quitando ultima coma
		
		return orden;
	} //getOrderBy()
	
	
	
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
			ArrayList<LetradoInscripcionItem> alLetradosOrdenados,
			Puntero punteroLetrado,
			ArrayList<String> diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales) throws Exception
	{
		// Variables
		ArrayList<LetradoInscripcionItem> grupoLetrados;
		SaltoCompGuardiaGrupoItem compensacion = null;
		boolean grupoValido;
		int restricciones;

		// obteniendo grupo de entre los compensados
		grupoValido = false;
		Iterator<SaltoCompGuardiaGrupoItem> iterador = alCompensaciones.iterator();
		while (iterador.hasNext() && !grupoValido) {
			compensacion = iterador.next();
			
			// comprobando cada letrado del grupo
			//log.addLog(new String[] {"Probando Grupo Compensado", compensacion.getLetrados().toString()});
			
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Probando Grupo Compensado", compensacion.getLetrados().toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Probando Grupo Compensado" + compensacion.getLetrados().toString());
			grupoValido = true;
			for (LetradoInscripcionItem lg : compensacion.getLetrados()) {
				if (!comprobarRestriccionesLetradoCompensado(lg, diasGuardia, null,
						compensacion.getIdSaltoCompensacionGrupo().toString(), hmBajasTemporales))
					grupoValido = false;
				if (!grupoValido)
					break; // salir de las comprobaciones por letrado si uno de ellos no es valido
			}
			if (!grupoValido) {
				//log.addLog(new String[] {"Grupo Compensado no valido", compensacion.getLetrados().toString()});
				Map<String, Object> mapLog1 = new HashMap();
				mapLog1.put("*Grupo Compensado no valido", compensacion.getLetrados().toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog1);
				LOGGER.info("*Grupo Compensado no valido" + compensacion.getLetrados().toString());
			}
			}
				
		// si bien, cumplir la compensacion de grupo:
		if (grupoValido) {
			//log.addLog(new String[] {"Compensacion de grupo cumplida"});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Compensacion de grupo cumplida", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Compensacion de grupo cumplida" );
			String motivoCumplimiento = "Compensacion de grupo cumplida";
			cumplirSaltoCompensacion(compensacion.getIdSaltoCompensacionGrupo().toString(),
					diasGuardia.get(0), motivoCumplimiento, idInstitucion1.toString(), idTurno1.toString(), 
					idGuardia1.toString(), idCalendarioGuardias1.toString());
			grupoLetrados = compensacion.getLetrados();

		} else {
			// obteniendo grupo de la cola
			grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
			if (grupoLetrados != null) {
			int idgrupoinicial = grupoLetrados.get(0).getGrupo();
			while (grupoLetrados != null && !grupoValido) {
				
				// comprobando cada letrado del grupo
				//log.addLog(new String[] {"Probando Grupo", grupoLetrados.toString()});
				
				Map<String, Object> mapLog3 = new HashMap();
				mapLog3.put("*Probando Grupo", grupoLetrados.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog3);
				LOGGER.info("*Probando Grupo" + grupoLetrados.toString());
				grupoValido = true;
				for (LetradoInscripcionItem lg : grupoLetrados) {
					if (!comprobarRestriccionesLetradoCola(lg, diasGuardia, hmPersonasConSaltos, hmBajasTemporales, false)) {
						grupoValido = false;
						break; // salir de las comprobaciones por letrado si uno de ellos no es valido
					}
				}
				if (!grupoValido) {
					Map<String, Object> mapLog4 = new HashMap();
					//log.addLog(new String[] {"Grupo no valido", grupoLetrados.toString()});
					mapLog4.put("*Grupo no valido", grupoLetrados.toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog4);
					LOGGER.info("*Grupo no valido" + grupoLetrados.toString());
					grupoLetrados = getGrupoLetrados(alLetradosOrdenados, punteroLetrado);
					if (idgrupoinicial == grupoLetrados.get(0).getGrupo())
						break;
				}
			}
		}
		}
		if (grupoValido){
			modifyOrderGruposLetrados(grupoLetrados.get(0).getGrupo());			
			return grupoLetrados;		
		}else{
			return null;
		}
	} // getSiguienteGrupo()
	
	
	
	
	private boolean comprobarRestriccionesLetradoCompensado(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia,
			Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo,
			HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales) throws Exception
	{
		// Controles

		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			//log.addLog(new String[] {"Encontrado Baja temporal", letradoGuardia.toString(), diasGuardia.toString()});
			Map<String, Object> mapLog1 = new HashMap();
			mapLog1.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog1);
			LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' +  diasGuardia.toString());
			if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals(""))
				// ... crear un salto cumplido (como si fuera un log)
				insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Cumplido en dia de guardia " + diasGuardia.get(0));
			else
				// ... crear un salto cumplido (como si fuera un log)
				crearSaltoBT(letradoGuardia.getGrupo().toString(), "Cumplido en dia de guardia " + diasGuardia.get(0), "",
						idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), 
						idCalendarioGuardias1.toString(),idCalendarioGuardias1.toString(), letradoGuardia.getBajaTemporal());
			return false; // y no seleccionar
		}

		// si hay incompatibilidad
		if (isIncompatible(letradoGuardia, diasGuardia)) {
			//log.addLog(new String[] {"Encontrado Incompatibilidad", letradoGuardia.toString(), diasGuardia.toString()});
			if (letradoGuardia.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
			}else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
			
			}
			return false; // no seleccionar
		}

		// cumpliendo compensacion
		if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals("")) {
			//log.addLog(new String[] {"Compensacion cumplida", letradoGuardia.toString()});
			Map<String, Object> mapLog1 = new HashMap();
			mapLog1.put("*Compensacion cumplida", letradoGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog1);
			LOGGER.info("*Compensacion cumplida" + letradoGuardia.toString());
			
			cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", ":id="+idCalendarioGuardias1.toString()+":Cumplido en fecha ("+diasGuardia.get(0)+"):finid="+idCalendarioGuardias1.toString()+":");
			iteCompensaciones.remove();
			return false;
		}
		else {
			// nada, hay que cumplir la compensacion cuando todos los letrados esten comprobados

		// una vez comprobado todo, se selecciona a este letrado
		//log.addLog(new String[] {"Letrado ok", letradoGuardia.toString()});
			Map<String, Object> mapLog2 = new HashMap();
			mapLog2.put("*Letrado ok", letradoGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog2);
			LOGGER.info("*Letrado ok" + letradoGuardia.toString());
		return true;
		}
	} // comprobarRestriccionesLetradoCompensado()
	
	private void cumplirSaltoCompensacion(LetradoInscripcionItem letradoGuardia,
				  ArrayList diasGuardia,
				  String saltoOCompensacion,
				  String motivo) throws Exception
	{
	ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
	saltoCompensacion.setIdinstitucion(idInstitucion1.shortValue());
	saltoCompensacion.setIdturno(idTurno1);
	saltoCompensacion.setIdguardia(idGuardia1);
	saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
	saltoCompensacion.setSaltoocompensacion(saltoOCompensacion);
	
	//saltoCompensacion.setIdCalendarioGuardiasCreacion(this.idCalendarioGuardias);

	saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
	//saltoCompensacion.setFechaCumplimiento(GstDate.getApplicationFormatDate("", (String) diasGuardia.get(0)) );
	if(idGuardia1!=null)
	saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);
	saltoCompensacion.setMotivos(motivo);
	marcarSaltoCompensacion(saltoCompensacion, diasGuardia.get(0).toString());
	}
	
	/**
	 * Cumple un salto/compensacion dado.
	 * Cuidado: si no se indica turno/guardia, se pueden cumplir muchos a la vez.
	 * 
	 * @param saltoCompensacion
	 * @throws ClsExceptions
	 */
	public void marcarSaltoCompensacion(ScsSaltoscompensaciones saltoCompensacion, String fechaCumplimiento) throws Exception
	{
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
			scsGuardiasturnoExtendsMapper.marcarSaltoCompensacion(usuModificacion1, idTurno1.toString(), saltoCompensacion,s_idpersona, s_idinstitucion, s_idturno, s_idguardia, s_saltocompensacion, fechaCumplimiento);


		} catch (Exception e) {
			throw new Exception(e + "Excepcion en marcarSaltoCompensacionBBDD." + e.toString());
		}
	} //marcarSaltoCompensacion()
	
	
	public void cumplirSaltoCompensacion(String idSaltoCompensacionGrupo,
			String fechaCumplimiento,
			String motivoCumplimiento,
			String idInstitucion_Cumpli,
			String idTurno_Cumpli,
			String idGuardia_Cumpli,
			String idCalendarioGuardias_Cumpli) throws Exception
	{
		SaltoCompGuardiaGrupoItem scg  = new SaltoCompGuardiaGrupoItem();
		
		scg.setIdSaltoCompensacionGrupo(idSaltoCompensacionGrupo);
		scg.setFechaCumplimiento(fechaCumplimiento);
		scg.setMotivoCumplimiento(motivoCumplimiento);
		scg.setIdInstitucion_Cumpli(idInstitucion_Cumpli);
		scg.setIdTurno_Cumpli(idTurno_Cumpli);
		scg.setIdGuardia_Cumpli(idGuardia_Cumpli);
		scg.setIdCalendarioGuardias_Cumpli(idCalendarioGuardias_Cumpli);

		scsGuardiasturnoExtendsMapper.updateSaltosCompensacionesGrupo(scg,  idInstitucion1.toString(),  usuModificacion1);
	}
	
	/**
	 * Si el letrado esta de baja seteamos la baja temporarl en el objeto LetradoGuardia, para luego insertar un salto
	 */
	private boolean isLetradoBajaTemporal(TreeMap<String, BajasTemporalesItem> hmBajasTemporales,
			ArrayList diasGuardia,
			LetradoInscripcionItem letradoGuardia)
	{
		boolean isLetradoBaja = false;
		BajasTemporalesItem bajaTemporal;

		if (hmBajasTemporales == null)
			return isLetradoBaja;
		
		for (int j = 0; j < diasGuardia.size(); j++) {
			String fechaPeriodo = (String) diasGuardia.get(j);
			if (hmBajasTemporales.containsKey(fechaPeriodo)) {
				bajaTemporal = hmBajasTemporales.get(fechaPeriodo);
				letradoGuardia.setBajaTemporal(bajaTemporal);
				isLetradoBaja = true;
				break;
			}
		}

		return isLetradoBaja;
	}
	
	
	private void insertarNuevoSaltoBT(LetradoInscripcionItem letradoGuardia,
			  ArrayList diasGuardia,
			  String motivo) throws Exception
	{
		String saltoOCompensacion = "S";
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
//		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones(
//		idInstitucion1, idTurno1, idGuardia1, idCalendarioGuardias1,
//		letradoGuardia.getIdpersona(), saltoOCompensacion, "sysdate");
		
		saltoCompensacion.setIdcalendarioguardiascreacion(idCalendarioGuardias1);
		saltoCompensacion.setFechacumplimiento(new Date(diasGuardia.get(0).toString()));
		saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);
		
		// obteniendo motivo
		StringBuffer descripcion = new StringBuffer();
		BajasTemporalesItem bajaTemporalBean = letradoGuardia.getBajaTemporal();
		if(bajaTemporalBean.getTipo().equals(TIPO_COD_VACACION)) {
		//descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_VACACION));
		}
		else if(bajaTemporalBean.getTipo().equals(TIPO_COD_BAJA))
		//descripcion.append(UtilidadesString.getMensajeIdioma(TIPO_DESC_BAJA));
		descripcion.append(" ");
		descripcion.append(bajaTemporalBean.getDescripcion());
		saltoCompensacion.setMotivos(descripcion + ": " + motivo);
		
		insertarSaltoCompensacion(saltoCompensacion);
	}
	
	public void insertarSaltoCompensacion(ScsSaltoscompensaciones salto) throws Exception{

		
		salto.setUsumodificacion(usuModificacion1);
		salto.setFechamodificacion(new Date());
		
		Long idSaltosTurno = Long.valueOf(getNuevoIdSaltosTurno(salto.getIdinstitucion().toString(),salto.getIdturno().toString()));
		salto.setIdsaltosturno(idSaltosTurno);
		scsSaltoscompensacionesMapper.insertSelective(salto);
		
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
		}
		catch (Exception e) {
			throw new Exception(e + ": Excepcion en ScsSaltosCompensacionesAdm.getNuevoIdSaltosTurno().");
		}
		return nuevoId;		
	}
	
	public void crearSaltoBT(String idGrupoGuardia,
			String fecha,
			String motivo,
			String idInstitucion,
			String idTurno,
			String idGuardia,
			String idCalendarioGuardias,
			String idCalendarioGuardiasCreacion,
			BajasTemporalesItem btBean) throws Exception
	{
		// obtencion de la descripcion de la Baja temporal
		StringBuffer motivoBT = new StringBuffer();
		if (btBean.getTipo().equals(TIPO_COD_VACACION))
			motivoBT.append( TIPO_DESC_VACACION);
		else if (btBean.getTipo().equals(TIPO_COD_BAJA))
			motivoBT.append( TIPO_DESC_BAJA);
		motivoBT.append(" ");
		motivoBT.append(btBean.getDescripcion());
		motivo = motivoBT + ": " + motivo;
		Hashtable<String, String> hash = new Hashtable<String, String>();
		SaltoCompGuardiaGrupoItem scg  = new SaltoCompGuardiaGrupoItem();
		
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


		if (idCalendarioGuardiasCreacion != null){
			scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
		}

		scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion,  usuModificacion1);

	}
	
	/** 
	 * Calcula un nuevo idSaltoCompensacionGrupo para la tabla
	 * 
	 * @return String con el nuevo identificador.
	 * @throws ClsExceptions
	 */	
	public String getNuevoIdCalProg() throws Exception
	{
		String nuevoId = "";

		try {
			nuevoId = scsGuardiasturnoExtendsMapper.nextIdCalprog();
		} catch (Exception e) {
			throw new Exception(e + ": Error al obtener nuevo id calendarios programados");
		}

		return nuevoId;
	} // getNuevoIdSaltoCompensacionGrupo()
	
	public String getNuevoIdSaltoCompensacionGrupo() throws Exception
	{
		String nuevoId = "";

		try {
			nuevoId = scsGuardiasturnoExtendsMapper.nextIdSaltoOComp();
		} catch (Exception e) {
			throw new Exception(e + ": Error al obtener nuevo idsaltocompensaciongrupo");
		}

		return nuevoId;
	} // getNuevoIdSaltoCompensacionGrupo()
	private boolean isIncompatible(LetradoInscripcionItem letradoGuardia, ArrayList diasGuardia)
			throws Exception
	{
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
	private boolean hayIncompatibilidadGuardias(LetradoInscripcionItem letrado, ArrayList periodoDiasGuardia) throws Exception {
		boolean salida = false;
		String idInstitucion = null;
		String idGuardia = null;
		String idTurno = null;
		String idPersona = null;
		
		try {
			if (letrado.getIdinstitucion() != null) {
				idInstitucion = letrado.getIdinstitucion().toString();
			}
			if (letrado.getIdguardia() != null) {
				idGuardia = letrado.getIdguardia().toString();
			}
			if (letrado.getIdturno() != null) {
				idTurno = letrado.getIdturno().toString();
			}
			if (letrado.getIdpersona() != null) {
				idPersona = letrado.getIdpersona().toString();
			}
			if (letrado.getInscripcionGuardia()!= null) {
				idInstitucion = letrado.getInscripcionGuardia().getIdInstitucion().toString();
				idGuardia = letrado.getInscripcionGuardia().getIdGuardia().toString();
				idTurno = letrado.getInscripcionGuardia().getIdturno().toString();
				idPersona = letrado.getInscripcionGuardia().getIdPersona().toString();
			}else if (letrado.getInscripcionTurno()!= null) {
				idInstitucion = letrado.getInscripcionTurno().getIdinstitucion().toString();
				idGuardia = null;
				idTurno = letrado.getInscripcionTurno().getIdturno().toString();
				idPersona = letrado.getInscripcionTurno().getIdpersona().toString();
			}
			
			salida = validarIncompatibilidadGuardia(idInstitucion, idTurno, idGuardia, periodoDiasGuardia, idPersona);
		} catch (Exception e) {
			throw new Exception (e + ": Excepcion en noHayIncompatibilidadGuardias.");
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
			if (letrado.getIdinstitucion() != null) {
			miHash.put("IDINSTITUCION", letrado.getIdinstitucion().toString());
			}
			if (letrado.getIdguardia() != null) {
			miHash.put("IDGUARDIA", letrado.getIdguardia().toString());
			}
			if (letrado.getIdturno() != null) {
			miHash.put("IDTURNO", letrado.getIdturno().toString());
			}
			if (periodoDiasGuardia.get(0) != null) {
			miHash.put("FECHAINICIO", (String)periodoDiasGuardia.get(0));
			}
			if (periodoDiasGuardia.get(periodoDiasGuardia.size()-1) != null) {
			miHash.put("FECHAFIN", (String)periodoDiasGuardia.get(periodoDiasGuardia.size()-1));}
			if (letrado.getIdpersona() != null) {
			miHash.put("IDPERSONA", letrado.getIdpersona().toString());
			}
			
			salida = validarSeparacionGuardias(miHash);
		} catch (Exception e) {
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
			}else {
				salida = false;		
			}

		} catch (Exception e) {
	        throw e;
		}
		return salida;
	}
	
	
	/** 
	 * Validacion: Consulta para buscar los dias de separacion entre guardias y validarlos
	 * Devuelve 0 si no encuentra separaciones incorrectas
	 * 
	 * La Hash debe tener los siguientes datos:
	 * -String "IDPERSONA"
	 * -String "IDINSTITUCION"
	 * -String "IDTURNO"
	 * -String "IDGUARDIA"
	 * -String "FECHAINICIO" del periodo de esta guardia
	 * -String "FECHAFIN" del periodo de esta guardia
	 * Parametros opcionales (claves opcionales):
	 * -String "FECHAINICIO_ORIGINAL" del periodo original para esta guardia. Puede no venir en la hash (solo se rellena en las permutas)
	 * -String "FECHAFIN_ORIGINAL" del periodo original para esta guardia. Puede no venir en la hash (solo se rellena en las permutas)
	 * 
	 * @return String con la consulta SQL.
	 * @throws ClsExceptions
	 */	
	public String validacionSeparacionGuardias(Hashtable miHash) throws Exception{
		String consulta = "";
		String idinstitucion="", idguardia="", idturno="", idpersona="";
		String fechaPeriodoUltimoDia="", fechaPeriodoPrimerDia="", fechaPeriodoPrimerDiaOriginal="";
		StringBuffer sBuffer; 
		String fechaMIN="", fechaMAX="";
		String total="";

		try {
			idinstitucion = (String)miHash.get("IDINSTITUCION");
			idguardia = (String)miHash.get("IDGUARDIA");
			idturno = (String)miHash.get("IDTURNO");
			idpersona = (String)miHash.get("IDPERSONA");
			fechaPeriodoPrimerDia =  (String)miHash.get("FECHAINICIO"); //Fecha del periodo de la primera guardia
			fechaPeriodoUltimoDia = (String)miHash.get("FECHAFIN"); //Fecha del periodo	de la ultima guardia
			fechaPeriodoPrimerDiaOriginal = (String)miHash.get("FECHAINICIO_ORIGINAL"); //Fecha del periodo de la primera guardia
			
			//Si tenemos fechas originales (venimos de permutas) es true.
			boolean esPermuta = miHash.containsKey("FECHAFIN_ORIGINAL") && miHash.containsKey("FECHAINICIO_ORIGINAL");
			
			//Consulto la maxima fecha inicio para el periodo en la cabecera de guardias:

//			fechaMAX =  scsGuardiasturnoExtendsMapper.maxFechaInicioPeriodoCabGuardia( idpersona, idinstitucion,  idturno,  idguardia,  esPermuta,  fechaPeriodoPrimerDiaOriginal,  fechaPeriodoPrimerDia);
//			String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
//			String NEW_FORMAT = "dd/MM/yyyy";
//			fechaMAX = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMAX);
			//Consulto la minima fecha inicio para el periodo en la cabecera de guardias:

//			fechaMIN = scsGuardiasturnoExtendsMapper.minFechaInicioPeriodoCabGuardia(idpersona, idinstitucion,  idturno,  idguardia,  esPermuta,  fechaPeriodoPrimerDiaOriginal,  fechaPeriodoUltimoDia);
//			fechaMIN = changeDateFormat(OLD_FORMAT, NEW_FORMAT, fechaMIN);
			total = scsGuardiasturnoExtendsMapper.diasSeparacionEntreGuardias( idpersona, idinstitucion,  idturno,  idguardia,  esPermuta,  fechaPeriodoPrimerDiaOriginal,  fechaPeriodoPrimerDia,  fechaPeriodoUltimoDia);

		} catch (Exception e){
			throw new Exception(e +": Excepcion en ScsGuardiasColegiadoAdm.validacionIncompatibilidad(). Consulta SQL:"+consulta);
		}
		return total;
	}	
	
	
	//Comprueba si hay incompatibilidades de guardia en el calendario
	public boolean validarIncompatibilidadGuardia(String idInstitucion, String idTurno, String idGuardia, ArrayList arrayDiasGuardia, String idPersona) {

		String vFechaFin;
		boolean encontrado = false;
		StringBuffer sql = null; 
		try {
			Iterator iterDiasGuardia = arrayDiasGuardia.iterator ();
			String fechaGuardia;
			while (iterDiasGuardia.hasNext () && !encontrado) {
				fechaGuardia = (String) iterDiasGuardia.next ();
				
				vFechaFin = scsGuardiasturnoExtendsMapper.checkIncompatibilidadesCalendario( idPersona, idInstitucion,  idTurno,  idGuardia,  fechaGuardia);

				if ( ! vFechaFin.isEmpty ())
					encontrado = true;
			}
		} catch (Exception e) {
			encontrado = true;
		}
		return !encontrado;
	}
	
	
	/**
	 * A partir de una lista de letrados, obtiene un grupo
	 * 
	 * @param alLetradosOrdenados
	 * @param punteroLetrado
	 * @return
	 * @throws ClsExceptions
	 */
	private ArrayList<LetradoInscripcionItem> getGrupoLetrados(ArrayList<LetradoInscripcionItem> alLetradosOrdenados,
			Puntero punteroLetrado) throws Exception
	{
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
		while (letrado != null && letrado.getGrupo() == null) {
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
		else if (letrado.getGrupo() == null)
			return null;
		else{
			numeroGrupo = letrado.getGrupo();
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
			else if (letrado.getGrupo() == null || letrado.getGrupo() != numeroGrupo)
				nuevoGrupo = true;
		} while (!nuevoGrupo && fin != punteroLetrado.getValor());
		
		if (grupoLetrados.size() == 0)
			return null;
		else
			return grupoLetrados;
	} // getGrupoLetrados()
	
	
	private boolean comprobarRestriccionesLetradoCola(LetradoInscripcionItem letradoGuardia, ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales, boolean ficheroCarga) throws Exception {


		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			//log.addLog(new String[] { "Encontrado Baja temporal", letradoGuardia.toString(), diasGuardia.toString() });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Baja temporal" + letradoGuardia.toString() + ' ' + diasGuardia.toString());
			if (!ficheroCarga) {
				if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals(""))
					// ... crear un salto cumplido (como si fuera un log)
					insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Cumplido en dia de guardia " + diasGuardia.get(0));
				else
					// ... crear un salto cumplido (como si fuera un log)
					crearSaltoBT(letradoGuardia.getGrupo().toString(), diasGuardia.get(0), "Cumplido en dia de guardia " + diasGuardia.get(0), idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString(), idCalendarioGuardias1.toString(), letradoGuardia.getBajaTemporal());
			}
			return false; // y no seleccionar
		}

		// si tiene saltos, ...
		List<LetradoInscripcionItem> alSaltos;
		if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals("")) {
			if ((alSaltos = hmPersonasConSaltos.get(letradoGuardia.getIdpersona())) != null) {
				//log.addLog(new String[] { "Encontrado Salto", letradoGuardia.toString() });
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Salto", letradoGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Salto" + letradoGuardia.toString());
				if (!ficheroCarga) {
					// ... compensar uno
					cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "S", ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":");
					alSaltos.remove(0);
					if (alSaltos.size() == 0)
						hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());

				}
				return false; // y no seleccionar
			}

		} else if ((alSaltos = hmPersonasConSaltos.get(new Long(letradoGuardia.getGrupo()))) != null) {
			//log.addLog(new String[] { "Encontrado Salto de grupo" });
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Salto de grupo", "");
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Salto de grupo");
			if (!ficheroCarga) {
				// ... compensar uno
				cumplirSaltoCompensacion(alSaltos.get(0).getIdSaltoCompensacionGrupo(), diasGuardia.get(0), ":id=" + idCalendarioGuardias1.toString() + ":Cumplido en fecha (" + diasGuardia.get(0) + "):finid=" + idCalendarioGuardias1.toString() + ":", idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString());
				alSaltos.remove(0);
				if (alSaltos.size() == 0)
					hmPersonasConSaltos.remove(new Long(letradoGuardia.getGrupo()));
			}
			return false; // y no seleccionar
		}

		// si hay incompatibilidad, ...
		if (isIncompatible(letradoGuardia, diasGuardia)) {
			//log.addLog(new String[] { "Encontrado Incompatibilidad", letradoGuardia.toString(), diasGuardia.toString() });
			if (letradoGuardia.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
			}else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
			
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
				
				if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals("")) {
					// ... crear compensacion
					// BNS INC_07349_SIGA
					insertarNuevoSaltoCompensacion(letradoGuardia, diasGuardia, "C", motivo);
				} else {
					// ... crear compensacion
					crearSaltoCompensacion(letradoGuardia.getGrupo().toString(), diasGuardia.get(0), motivo, idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), idCalendarioGuardias1.toString(), "C", null);
				}
			}
			
			return false; // y no seleccionar
		}

		// una vez comprobado todo, se selecciona a este letrado
		//log.addLog(new String[] { "Letrado ok", letradoGuardia.toString() });
		if (letradoGuardia.getInscripcionGuardia() != null) {
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrado ok ", letradoGuardia.getInscripcionGuardia().getApellido2().toString() + " " + letradoGuardia.getInscripcionGuardia().getApellido1().toString() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " +  letradoGuardia.getInscripcionGuardia().getApellido2().toString() + " " + letradoGuardia.getInscripcionGuardia().getApellido1().toString() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString());
		}else if (letradoGuardia.getInscripcionTurno() != null) {
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrado ok ", letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok " + letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
		
		}
		return true;
	} // comprobarRestriccionesLetradoCola()
	
	private void insertarNuevoSaltoCompensacion(LetradoInscripcionItem letradoGuardia,
			ArrayList diasGuardia,
			String saltoOCompensacion,
			String motivo) throws Exception
	{
		ScsSaltoscompensaciones saltoCompensacion = new ScsSaltoscompensaciones();
		saltoCompensacion.setIdinstitucion(idInstitucion1.shortValue());
		saltoCompensacion.setIdturno(idTurno1);
		saltoCompensacion.setIdguardia(idGuardia1);
		saltoCompensacion.setIdcalendarioguardias(idCalendarioGuardias1);
		//saltoCompensacion.setIdpersona(letradoGuardia.getIdpersona());
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
	
	
	public void crearSaltoCompensacion(String idGrupoGuardia,
			String fecha,
			String motivo,
			String idInstitucion,
			String idTurno,
			String idGuardia,
			String idCalendarioGuardias,
			String saltoCompensacion,
			String idCalendarioGuardiasCreacion) throws Exception
	{
		SaltoCompGuardiaGrupoItem scg  = new SaltoCompGuardiaGrupoItem();
		
		scg.setIdSaltoCompensacionGrupo(getNuevoIdSaltoCompensacionGrupo());
		scg.setIdGrupoGuardia(Long.parseLong(idGrupoGuardia));
		scg.setSaltoCompensacion(saltoCompensacion);
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


		if (idCalendarioGuardiasCreacion != null){
			scg.setIdCalendarioGuardiasCreacion(idCalendarioGuardiasCreacion);
		}

		scsGuardiasturnoExtendsMapper.guardarSaltosCompensacionesGrupo(scg, idInstitucion,  usuModificacion1);
	}
	
	/**
	 * Modifica el orden de la cola de guardia de letrados incrementando su orden en una constante (3000).
	 * Este metodo es necesario para no producir un error de restricción unica de orden en bbdd
	 */
	public void modifyOrderGruposLetrados(int idGrupo) {
		boolean repetido = false;
		Hashtable hash = new Hashtable();
		StringBuffer sql = new StringBuffer();
		int orden=0;		
		ArrayList<Hashtable<String, String>> grupo = new ArrayList<Hashtable<String,String>>();
		Hashtable registro = scsGuardiasturnoExtendsMapper.getGrupoData(String.valueOf(idGrupo));

		try {										
					if (registro != null){
						orden = new Integer((String)registro.get("ORDEN"))+ new Integer(30000);
						updateOrderBBDD(new Integer((String)registro.get("IDGRUPOGUARDIA")),
								orden,
								new Integer((String)registro.get("IDGRUPOGUARDIACOLEGIADO")));											
					}

		} catch (Exception e) {
			// throw new ClsExceptions (e,
			// "Error al recuperar la posicion del ultimo letrado");
		}
	}
	
	/**
	 * Actualiza en bbdd el orden modificado necesario para el algoritmo de rotación de guardias
	 */
	public void updateOrderBBDD(int idGuardia, int orden, int idGrupoColegiado){
		Hashtable<String, String> hash = new Hashtable<String, String>();
		Hashtable<String, String> hashDataOld;
		try {
			ScsGrupoguardiacolegiado scsGrupoguardiacolegiado = new ScsGrupoguardiacolegiado();
			scsGrupoguardiacolegiado.setIdguardia(idGuardia);
			scsGrupoguardiacolegiado.setIdgrupoguardiacolegiado(Long.valueOf(idGrupoColegiado));
			scsGrupoguardiacolegiado.setOrden(orden);

			scsGrupoguardiacolegiadoMapper.updateByPrimaryKeySelective(scsGrupoguardiacolegiado);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Genera las cabeceras de guardias del calendario, a partir de la lista de dias/periodos
	 * Cuando la guardia no es por grupos
	 * Nota: previamente se ha calculado mediante el metodo "calcularMatrizPeriodosDiasGuardia()"
	 */
	public void calcularMatrizLetradosGuardia(List lDiasASeparar) throws Exception
	{
		// Variables generales
		ArrayList<String> diasGuardia, primerPeriodo, segundoPeriodo; // Periodo o dia de guardia para rellenar con letrado
		int numeroLetradosGuardia; // Numero de letrados necesarios para cada periodo
		HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales;
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos; // Lista de saltos
		List<LetradoInscripcionItem> alCompensaciones; // Lista de compensaciones
		ArrayList<LetradoInscripcionItem> alLetradosOrdenados; // Cola de letrados en la guardia
		Puntero punteroListaLetrados;
		int posicion;
		ArrayList<LetradoInscripcionItem> alLetradosInsertar; // Lista de letrados obtenidos para cada periodo
		LetradoInscripcionItem unLetrado;

		try {
			// obteniendo bajas temporales por letrado
			String primerDia = ((ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0)).get(0);
			ArrayList<String> ultimoPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(arrayPeriodosDiasGuardiaSJCS1.size()-1);
			String ultimoDia = (ultimoPeriodo).get(ultimoPeriodo.size()-1);
			hmBajasTemporales = getLetradosDiasBajaTemporal(idInstitucion1, idTurno1, idGuardia1, primerDia, ultimoDia);
			
			// obteniendo saltos
			hmPersonasConSaltos = getSaltos(idInstitucion1, idTurno1, idGuardia1);

			// obteniendo numero de letrados necesarios para cada periodo
			numeroLetradosGuardia = beanGuardiasTurno1.getNumeroLetradosGuardia().intValue();
			
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrados por dia ", Integer.toString(numeroLetradosGuardia));
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrados por dia " + Integer.toString(numeroLetradosGuardia));
			//log.addLog(new String[] {"Letrados por dia", Integer.toString(numeroLetradosGuardia)});
			if(numeroLetradosGuardia>0){
	
				// Si hay guardias vinculadas, hay que mirar dos periodos a la vez, 
				// por lo que se comienza con uno ya en memoria
				int inicial;
				primerPeriodo = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(0);
				segundoPeriodo = null;
				if (calendariosVinculados1 == null)
					inicial = 0;
				else {
					Map<String, Object> mapLog2 = new HashMap();
					//log.addLog(new String[] {"Guardias vinculadas", calendariosVinculados1.toString()});
					mapLog2.put("*Guardias vinculadas ", calendariosVinculados1.toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog2);
					LOGGER.info("*Guardias vinculadas " + calendariosVinculados1.toString());
					inicial = 1;
				}
				
				// Para cada dia o conjunto de dias:
				for (int i = inicial; i < arrayPeriodosDiasGuardiaSJCS1.size(); i++) {
					posicion = 1;
					// obteniendo conjunto de dias
					// Nota: cada periodo es un arraylist de fechas (String en formato de fecha corto DD/MM/YYYY)
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
					alLetradosOrdenados = getColaGuardia(idInstitucion1, idTurno1, idGuardia1,
							(String) diasGuardia.get(0), (String) diasGuardia.get(diasGuardia.size() - 1));
					//log.addLog(new String[] {"Cola", alLetradosOrdenados.toString()});
					Map<String, Object> mapLog2 = new HashMap();
					if (alLetradosOrdenados != null && !alLetradosOrdenados.isEmpty()) {
						alLetradosOrdenados.forEach(letrado ->{
						if (letrado.getInscripcionGuardia() != null) {
							mapLog2.put("*Cola ", letrado.getInscripcionGuardia().getApellido2().toString() + " " +  letrado.getInscripcionGuardia().getApellido1().toString() + ", " +  letrado.getInscripcionGuardia().getNombre().toString());
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Cola " + letrado.getInscripcionGuardia().getApellido2().toString() + " " +  letrado.getInscripcionGuardia().getApellido1().toString() + ", " +  letrado.getInscripcionGuardia().getNombre().toString());
						}else if(letrado.getInscripcionTurno() != null) {
							mapLog2.put("*Cola ", letrado.getInscripcionTurno().getApellidos1() + " " + letrado.getInscripcionTurno().getApellidos2() + ", " + letrado.getInscripcionTurno().getNombre());
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Cola " + letrado.getInscripcionTurno().getApellidos1() + " " + letrado.getInscripcionTurno().getApellidos2() + ", " + letrado.getInscripcionTurno().getNombre());
						
						}
						});
					}else {
						mapLog2.put("*Cola vacía", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Cola vacía");
					}
					
	
					if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0)
//						throw new Exception("No existe cola de letrados de guardia");
						LOGGER.error("No existe cola de letrados de guardia");
	
					// obteniendo las compensaciones. Se obtienen dentro de este
					// bucle, ya que si hay incompatibilidades se añade una compensacion
					alCompensaciones = getCompensaciones(idInstitucion1, idTurno1, idGuardia1,(String) diasGuardia.get(0));
//					log.addLog(new String[] {"Compensaciones", alCompensaciones.toString()});
					Map<String, Object> mapLog3 = new HashMap();
					if (alCompensaciones != null && !alCompensaciones.isEmpty()) {
						alCompensaciones.forEach(compensacion -> {
							if (compensacion.getInscripcionGuardia() != null) {
								mapLog3.put("*Compensaciones ", compensacion.getInscripcionGuardia().getApellido1() + " " + compensacion.getInscripcionGuardia().getApellido2() + ", " + compensacion.getInscripcionGuardia().getNombre());
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("*Compensaciones " + compensacion.getInscripcionGuardia().getApellido1() + " " + compensacion.getInscripcionGuardia().getApellido2() + ", " + compensacion.getInscripcionGuardia().getNombre());
							}else if(compensacion.getInscripcionTurno() != null) {
								mapLog3.put("*Compensaciones ", compensacion.getInscripcionTurno().getApellidos1() + " " + compensacion.getInscripcionTurno().getApellidos2() + ", " + compensacion.getInscripcionTurno().getNombre());
								listaDatosExcelGeneracionCalendarios.add(mapLog3);
								LOGGER.info("*Compensaciones " + compensacion.getInscripcionTurno().getApellidos1() + " " + compensacion.getInscripcionTurno().getApellidos2() + ", " + compensacion.getInscripcionTurno().getNombre());
							
							}
							});
					}else {
						mapLog3.put("*No existen compensaciones", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
						LOGGER.info("*No existen compensaciones");
					}

//					log.addLog(new String[] {"Saltos", hmPersonasConSaltos.toString()});
					Map<String, Object> mapLog4 = new HashMap();
					if (hmPersonasConSaltos != null && !hmPersonasConSaltos.isEmpty()) {
						for(Map.Entry<Long, ArrayList<LetradoInscripcionItem>> personasConSaltos : hmPersonasConSaltos.entrySet()) {
							personasConSaltos.getValue().forEach(pSalto -> {
								if (pSalto.getInscripcionGuardia() != null) {
									mapLog3.put("*Saltos ", pSalto.getInscripcionGuardia().getApellido1() + " " + pSalto.getInscripcionGuardia().getApellido2() + ", " + pSalto.getInscripcionGuardia().getNombre());
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + pSalto.getInscripcionGuardia().getApellido1() + " " + pSalto.getInscripcionGuardia().getApellido2() + ", " + pSalto.getInscripcionGuardia().getNombre());
								}else if(pSalto.getInscripcionTurno() != null) {
									mapLog3.put("*Saltos ", pSalto.getInscripcionTurno().getApellidos1() + " " + pSalto.getInscripcionTurno().getApellidos2() + ", " + pSalto.getInscripcionTurno().getNombre());
									listaDatosExcelGeneracionCalendarios.add(mapLog3);
									LOGGER.info("*Saltos " + pSalto.getInscripcionTurno().getApellidos1() + " " + pSalto.getInscripcionTurno().getApellidos2() + ", " + pSalto.getInscripcionTurno().getNombre());
								
								}
							});
					
						}
					}else {
						mapLog4.put("*No existen saltos", "");
						listaDatosExcelGeneracionCalendarios.add(mapLog4);
						LOGGER.info("*No existen saltos");
					}
					// Para cada plaza que hay que ocupar en dia/conjunto de dias:
					int letradosInsertados = 0;
					for (int k = 0; k < numeroLetradosGuardia; k++) {
						// obteniendo el letrado a asignar.
						// ATENCION: este metodo es el nucleo del proceso
						LetradoInscripcionItem letradoGuardia = getSiguienteLetrado(alCompensaciones, alLetradosOrdenados,
								punteroListaLetrados, diasGuardia, hmPersonasConSaltos, hmBajasTemporales);
	
						// guardando la asignacion de guardia si se encontro letrado
						// hay que hacerlo aqui para no repetir letrado el mismo dia
						if (letradoGuardia != null) {
							//log.addLog(new String[] {"Letrado seleccionado", letradoGuardia.toString()});
							if (letradoGuardia.getInscripcionGuardia() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								mapLog5.put("*Letrado seleccionado ", letradoGuardia.getInscripcionGuardia().getApellido2().toString() + " " + letradoGuardia.getInscripcionGuardia().getApellido1().toString() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString());
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " +  letradoGuardia.getInscripcionGuardia().getApellido2().toString() + " " + letradoGuardia.getInscripcionGuardia().getApellido1().toString() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString());
							}else if (letradoGuardia.getInscripcionTurno() != null) {
								Map<String, Object> mapLog5 = new HashMap();
								mapLog5.put("*Letrado seleccionado ", letradoGuardia.getInscripcionTurno().getApellidos2().toString() + " " + letradoGuardia.getInscripcionTurno().getApellidos1().toString() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString());
								listaDatosExcelGeneracionCalendarios.add(mapLog5);
								LOGGER.info("*Letrado seleccionado " +  letradoGuardia.getInscripcionTurno().getApellidos2().toString() + " " + letradoGuardia.getInscripcionTurno().getApellidos1().toString() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString());
							
							}
							alLetradosInsertar = new ArrayList<LetradoInscripcionItem>();
							letradoGuardia.setPeriodoGuardias(diasGuardia);
							LetradoInscripcionItem letradoGuardiaClone = (LetradoInscripcionItem) letradoGuardia;
							letradoGuardiaClone.setPosicion(posicion);
							posicion++;
							alLetradosInsertar.add(letradoGuardiaClone);
							arrayPeriodosLetradosSJCS1.add(alLetradosInsertar);
							
							// guardando las guardias en BD
							if (calendariosVinculados1 == null) {
								almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, diasGuardia, lDiasASeparar,"gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
										);
							}
							else {
								// guardando la principal
								almacenarAsignacionGuardia(idCalendarioGuardias1, alLetradosInsertar, primerPeriodo, lDiasASeparar, "gratuita.literal.comentario.sustitucion"
//										UtilidadesString.getMensajeIdioma(usuModificacion1,
//												"gratuita.literal.comentario.sustitucion")
										);
	
								// guardando para cada una de las vinculadas
								for (GuardiasCalendarioItem calendario : calendariosVinculados1) {
									// modificando la guardia y calendario en el que se insertaran las guardias
									for (LetradoInscripcionItem lg : alLetradosInsertar) {
										lg.setIdinstitucion(new Short(calendario.getIdinstitucion()));
										lg.setIdturno(Integer.parseInt(calendario.getIdturno()));
										lg.setIdguardia(Integer.parseInt(calendario.getIdguardia()));
									}
									
									// guardando en BD
									almacenarAsignacionGuardia(new Integer(calendario.getIdcalendarioguardias()), alLetradosInsertar, segundoPeriodo, lDiasASeparar, "gratuita.literal.comentario.sustitucion"
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
							mapLog5.put("*FIN generacion ", "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
							listaDatosExcelGeneracionCalendarios.add(mapLog5);
							LOGGER.info("*FIN generacion " + "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
						}
					} // FIN Para cada plaza que hay que ocupar en dia/conjunto de dias
	
					// controlando que se insertaron tantos letrados como hacian falta
					if (letradosInsertados != numeroLetradosGuardia) {
//						log.addLog(new String[] {"FIN generacion", UtilidadesString.getMensajeIdioma(this.usrBean,
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes")});
						Map<String, Object> mapLog5 = new HashMap();
						mapLog5.put("*FIN generacion ", "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
						listaDatosExcelGeneracionCalendarios.add(mapLog5);
						LOGGER.info("*FIN generacion " + "gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
//						throw new Exception(
//								"gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
					}
	
					// actualizando el ultimo letrado en la guardia solo si no es de la lista de compensaciones
					int punteroUltimo = 0;
					if (punteroListaLetrados.getValor() == 0)
						punteroUltimo = alLetradosOrdenados.size() - 1;
					else
						punteroUltimo = punteroListaLetrados.getValor() - 1;
	
					if (alLetradosOrdenados.size() != 0) {
					unLetrado = alLetradosOrdenados.get(punteroUltimo);
					
					if (unLetrado.getSaltoocompensacion() == null)
						cambiarUltimoCola(unLetrado.getIdinstitucion(), unLetrado.getIdturno(), 
								unLetrado.getIdguardia(), unLetrado.getIdpersona(), 
								unLetrado.getInscripcionGuardia().getFechaSuscripcion().toString(),unLetrado.getIdGrupoGuardiaColegiado());
					}
					// avanzando el puntero de dia en el caso de guardias vinculadas
					if (calendariosVinculados1 != null)
						primerPeriodo = segundoPeriodo;
				} // FIN Para cada dia o conjunto de dias
			}else{
				//log.addLog(new String[] {"Calendario sin letrados generado correctamente"});
				Map<String, Object> mapLog2 = new HashMap();
				mapLog2.put("*Calendario sin letrados generado correctamente", "");
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Calendario sin letrados generado correctamente");
			}
			
			
		} catch (Exception e) {
			arrayPeriodosLetradosSJCS1 = null;
			throw new Exception(e + "");
		}
	} // calcularMatrizLetradosGuardia()
	
	/**
	 * Obtiene todos los saltos asociados a una guardia
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param idGuardia
	 * @return
	 * @throws ClsExceptions
	 */
	public HashMap<Long, ArrayList<LetradoInscripcionItem>> getSaltos(Integer idInstitucion, Integer idTurno, Integer idGuardia) throws Exception
	{
		
		// Variables
		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos;
		Hashtable<String, Object> htFila;
		
		try {

				hmPersonasConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
				List<String> idPersona2List =getQuerySaltosCompensacionesActivos("S", idInstitucion, idTurno, idGuardia);
				
//				idPersona2List.forEach(idPersona2 -> {



						String idPersonasSeparatedByComma = idPersona2List.stream().collect(Collectors.joining(","));

						List<CenPersonaItem> personas = getPersonaPorId(idPersonasSeparatedByComma);
						if (personas.size() != 0) {
						personas.forEach(persona -> {
							ArrayList<LetradoInscripcionItem> alLetradosSaltados;
							LetradoInscripcionItem letradoSeleccionado = null;

					try {
						letradoSeleccionado = new LetradoInscripcionItem(persona, idInstitucion,
									idTurno, idGuardia, "S");
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
	private List<String> getQuerySaltosCompensacionesActivos(String tipo,
			Integer idInstitucion,
			Integer idTurno,
			Integer idGuardia)
	{
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
		
		List<String> idPersona = scsGuardiasturnoExtendsMapper.getSaltoCompensacionesActivo( s_idinstitucion, s_idturno, s_idguardia, null, s_saltocompensacion);
		return idPersona;
			
	} //getQuerySaltosCompensacionesActivos()
	
	/**
	 * Devuelve una persona a partir de su idPersona  
	 * @version 1	 
	 * @param nifcif de la persona a buscar 
	 */	
	public List<CenPersonaItem> getPersonaPorId (String idPersona) throws Exception{

		
		try {
			CenPersonaItem perBean = null;
			
			Hashtable codigos = new Hashtable();
			codigos.put(new Integer(1),idPersona);
			List<CenPersonaItem> personas =  scsGuardiasturnoExtendsMapper.getPersonaById(idPersona);
			
//			if ((personas != null) && personas.size() == 1) {
//				perBean = (CenPersonaItem)personas.get(0);
//			}
//
//			return perBean;
			return personas;
		}
		catch (Exception e) {
			throw new Exception (e + ": Error al recuperar los datos");
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
	public ArrayList<LetradoInscripcionItem> getCompensaciones(Integer idInstitucion, Integer idTurno, Integer idGuardia,String fecha) throws Exception
	{
		// Controles
		// Variables
		ArrayList<LetradoInscripcionItem> alLetradosCompensados;
		Hashtable<String, Object> htFila;
		List<String> idPersonaList;


		try {
			// obtiene las compesaciones de letrados que no esten de baja en la guardia
				alLetradosCompensados = new ArrayList<LetradoInscripcionItem>();
				idPersonaList = getQuerySaltosCompensacionesActivos("C", idInstitucion, idTurno,
							idGuardia);
//				idPersonaList.forEach(idPersona -> {
					if (idPersonaList.size() != 0) {
						String idPersonasSeparatedByComma = idPersonaList.stream().collect(Collectors.joining(","));
						if( idGuardia!=null ){
							List<InscripcionGuardiaItem> inscripcionGuardiaList = new ArrayList<InscripcionGuardiaItem>();
							try {
								inscripcionGuardiaList = getInscripcionGuardiaActiva(idInstitucion.toString(), idTurno.toString(),
										idGuardia.toString(), idPersonasSeparatedByComma, fecha);
							} catch (Exception e) {
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

						}else{
							List<InscripcionTurnoItem> inscripcionTurnoList = new ArrayList<InscripcionTurnoItem>();
							try {
								inscripcionTurnoList = getInscripcionTurnoActiva(idInstitucion.toString(), idTurno.toString(), idPersonasSeparatedByComma, fecha);
							} catch (Exception e) {
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
			throw new Exception(e + ": Error al obtener letrados compensados  en BD.");
		}

		return alLetradosCompensados;
	}
	
	
	/**
	 * Obtiene las inscripcion activa de la persona en la guardia en la fecha dada
	 */
	public List<InscripcionTurnoItem> getInscripcionTurnoActiva(String idinstitucion, String idturno, String idpersona, String fecha) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))		return null;
			if (idturno == null || idturno.equals(""))					return null;
			if (idpersona == null || idpersona.equals(""))				return null;
			if (fecha == null || fecha.equals(""))						return null;
			
			if(!fecha.equals("sysdate"))
				fecha = "'"+fecha+"'";

			List<InscripcionTurnoItem> datos = null;
			datos =  scsGuardiasturnoExtendsMapper.getInscripcionesTurnoActiva(idpersona,idinstitucion, idturno, fecha);
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
			throw new Exception (e + ": Error al ejecutar getInscripcionActiva()");
		}					
	} //getInscripcionActiva()
	
	
	/**
	 * Obtiene las inscripcion activa de la persona en la guardia en la fecha dada
	 */
	public List<InscripcionGuardiaItem> getInscripcionGuardiaActiva(String idinstitucion, String idturno, String idguardia, String idpersona, String fecha) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))		return null;
			if (idturno == null || idturno.equals(""))					return null;
			if (idguardia == null || idguardia.equals(""))				return null;
			if (idpersona == null || idpersona.equals(""))				return null;
			if (fecha == null || fecha.equals(""))						return null;
			if(!fecha.equals("sysdate"))
				fecha = "'"+fecha+"'";

			
			List<InscripcionGuardiaItem> datos = null;
			datos = scsGuardiasturnoExtendsMapper.getInscripcionesGuardiaActiva(idpersona, idinstitucion, idguardia, fecha, idturno);
//			if (datos.size() != 0) {
//				return datos.get(0);
//			}else {
//				return null;
//			}
			return datos;
			
		} catch (Exception e) {
			throw new Exception (e + ": Error al ejecutar getInscripcionActiva()");
		}				
	}
	
	
	/**
	 * Cambia el ultimo letrado de la cola de la guardia indicada por el nuevo que se ha solicitado
	 */
	public void cambiarUltimoCola (Short idInstitucion,
								   Integer idTurno,
								   Integer idGuardia,
								   Long idPersona_Ultimo,
								   String fechaSuscripcion_Ultimo,
								   Long idGrupoGuardiaColegiado_Ultimo)
		throws Exception
	{
		
		String sIdinstitucion = null;
		String sIdTurno = null;
		String sIdGuardia = null;
		if (idInstitucion != null){
			sIdinstitucion = idInstitucion.toString();
		}
		if (idTurno != null){
			sIdTurno = idTurno.toString();
		}
		if (idGuardia != null){
			sIdGuardia = idGuardia.toString();
		}
		String sIdpersona = (idPersona_Ultimo == null) ? "null" : idPersona_Ultimo.toString();
		String sFechaSusc = (fechaSuscripcion_Ultimo == null || fechaSuscripcion_Ultimo.equals("")) ?
				"null" : fechaSuscripcion_Ultimo.toString();
		String sIdGrupoGuardiaColegiado_Ultimo = (idGrupoGuardiaColegiado_Ultimo == null) ? "null" : idGrupoGuardiaColegiado_Ultimo.toString();
		
//		String[] campos = 
//		{
//				ScsGuardiasTurnoBean.C_IDPERSONA_ULTIMO,
//				ScsGuardiasTurnoBean.C_IDGRUPOGUARDIACOLEGIADO_ULTIMO,
//				ScsGuardiasTurnoBean.C_FECHASUSCRIPCION_ULTIMO,
//				ScsGuardiasTurnoBean.C_USUMODIFICACION,
//				ScsGuardiasTurnoBean.C_FECHAMODIFICACION
//		};
//		SCS_GUARDIASTURNO
//		Hashtable hash = new Hashtable();
//		hash.put(ScsGuardiasTurnoBean.C_IDINSTITUCION, sIdinstitucion);
//		hash.put(ScsGuardiasTurnoBean.C_IDTURNO, sIdTurno);
//		hash.put(ScsGuardiasTurnoBean.C_IDGUARDIA, sIdGuardia);
//		hash.put(ScsGuardiasTurnoBean.C_IDPERSONA_ULTIMO, sIdpersona);
//		hash.put(ScsGuardiasTurnoBean.C_IDGRUPOGUARDIACOLEGIADO_ULTIMO, sIdGrupoGuardiaColegiado_Ultimo);
//		hash.put(ScsGuardiasTurnoBean.C_FECHASUSCRIPCION_ULTIMO, sFechaSusc);
//		hash.put(ScsGuardiasTurnoBean.C_USUMODIFICACION, this.usrbean.getUserName());
//		hash.put(ScsGuardiasTurnoBean.C_FECHAMODIFICACION, "SYSDATE");
//		
//		this.updateDirect(hash, this.getClavesBean(), campos);
		LocalDate date4 = ZonedDateTime
	            .parse(sFechaSusc.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
	            .toLocalDate();
	 	String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd/MM/yyyy";
//		String OLD_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
//		String NEW_FORMAT = "dd/MM/yyyy";
		sFechaSusc = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		scsGuardiasturnoExtendsMapper.cambiarUltimoCola2(sIdinstitucion, sIdTurno,  sIdGuardia,  sIdpersona,  sIdGrupoGuardiaColegiado_Ultimo, 
				 sFechaSusc,  usuModificacion1.toString());
	} // cambiarUltimoCola()
	
	/**
	 * Almacena para una lista de letrados las guardias con los registros correspondientes a sus dias de guardia.
	 * @param arrayLetrados
	 * @param periodoDiasGuardia
	 * @param lDiasASeparar
	 * @param mensaje
	 * @throws ClsExceptions
	 */
	private void almacenarAsignacionGuardia(Integer idCalendarioGuardias, ArrayList arrayLetrados, ArrayList periodoDiasGuardia,List lDiasASeparar, String mensaje) throws Exception {
		Iterator iter;
		Iterator iterLetrados;
		String fechaInicioPeriodo=null, fechaFinPeriodo=null, fechaPeriodo=null;
		ScsCabeceraguardias beanCabeceraGuardias;
		ScsGuardiascolegiado beanGuardiasColegiado;
		LetradoInscripcionItem letrado;

		try {

			List alPeriodosSinAgrupar = getPeriodos(periodoDiasGuardia,lDiasASeparar);
			for (int j = 0; j < alPeriodosSinAgrupar.size(); j++) {
				ArrayList alDiasPeriodo = (ArrayList) alPeriodosSinAgrupar.get(j);

				fechaInicioPeriodo = (String)alDiasPeriodo.get(0);
				fechaFinPeriodo = (String)alDiasPeriodo.get(alDiasPeriodo.size()-1);

				iterLetrados = arrayLetrados.iterator();
				while (iterLetrados.hasNext()) {
					letrado = (LetradoInscripcionItem)iterLetrados.next();
					String OLD_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
					String NEW_FORMAT = "dd/MM/yyyy";
					//Paso1: inserto un registro cada guardia:
					beanCabeceraGuardias = new ScsCabeceraguardias();
					//beanCabeceraGuardias.setIdinstitucion(letrado.getIdinstitucion());

					if (letrado.getInscripcionGuardia() != null) {
						beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
						//beanCabeceraGuardias.setIdturno(letrado.getIdturno());
						beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
						//beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
						beanCabeceraGuardias.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
						//beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
						beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
					}else if (letrado.getInscripcionTurno() != null) {
						beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
						//beanCabeceraGuardias.setIdturno(letrado.getIdturno());
						beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionTurno().getIdturno()));
						//beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
						beanCabeceraGuardias.setIdguardia(null);
						//beanCabeceraGuardias.setIdpersona(letrado.getIdpersona());
						beanCabeceraGuardias.setIdpersona(new Long(letrado.getInscripcionTurno().getIdpersona()));
					}

					beanCabeceraGuardias.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
					//beanCabeceraGuardias.setIdturno(letrado.getIdturno());
					beanCabeceraGuardias.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
					//beanCabeceraGuardias.setIdguardia(letrado.getIdguardia());
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
					String valorValidar = getValor(idInstitucion1.toString(),"SCS","VALIDAR_VOLANTE","N"); //DUDA
					if (valorValidar.equals("N")) {
						// directamente quedan validados
						beanCabeceraGuardias.setValidado("1");//true
					} else {
						// quedan pendientes de validacion
						beanCabeceraGuardias.setValidado("0");//false
					}

					beanCabeceraGuardias.setFechaalta(new Date());
					beanCabeceraGuardias.setUsualta(new Integer(usuModificacion1));
					beanCabeceraGuardias.setPosicion(letrado.getPosicion().shortValue());
					if(letrado.getNumeroGrupo() != null && !letrado.getNumeroGrupo().equals("")){
						beanCabeceraGuardias.setNumerogrupo(Integer.parseInt(letrado.getNumeroGrupo()));
					}
					
					
					//Se comprueba si para ese periodo existe una guardia para el letrado
					if(validaGuardiaLetradoPeriodo(beanCabeceraGuardias.getIdinstitucion(), beanCabeceraGuardias.getIdturno(), beanCabeceraGuardias.getIdguardia(), beanCabeceraGuardias.getIdpersona(), fechaInicioPeriodo, fechaFinPeriodo))
						throw new Exception("gratuita.calendarios.guardias.mensaje.existe");

					if (letrado.getInscripcionGuardia() != null) {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias , fechaInicioPSt, fechaFinPSt, today, letrado.getInscripcionGuardia().getIdInstitucion(), letrado.getInscripcionGuardia().getIdturno(), letrado.getInscripcionGuardia().getIdGuardia(), letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
					}else if(letrado.getInscripcionTurno() != null) {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias , fechaInicioPSt, fechaFinPSt, today, letrado.getInscripcionTurno().getIdinstitucion().toString(), letrado.getInscripcionTurno().getIdturno().toString(), null, letrado.getInscripcionTurno().getIdpersona().toString(), fechaAlta);
					}else {
						scsCabeceraguardiasMapper.insertSelective2(beanCabeceraGuardias , fechaInicioPSt, fechaFinPSt, today, letrado.getInscripcionGuardia().getIdInstitucion(), letrado.getInscripcionGuardia().getIdturno(), letrado.getInscripcionGuardia().getIdGuardia(), letrado.getInscripcionGuardia().getIdPersona(), fechaAlta);
					}
					//Paso2: inserto un registro por dia de guardia en cada guardia:
					iter = alDiasPeriodo.iterator();
					while (iter.hasNext()) {
						fechaPeriodo = (String)iter.next();

						beanGuardiasColegiado = new ScsGuardiascolegiado();
						if (letrado.getInscripcionGuardia() != null) {
							beanGuardiasColegiado.setIdinstitucion(new Short(letrado.getInscripcionGuardia().getIdInstitucion()));
							beanGuardiasColegiado.setIdturno(new Integer(letrado.getInscripcionGuardia().getIdturno()));
							beanGuardiasColegiado.setIdguardia(new Integer(letrado.getInscripcionGuardia().getIdGuardia()));
							beanGuardiasColegiado.setIdpersona(new Long(letrado.getInscripcionGuardia().getIdPersona()));
						}else if (letrado.getInscripcionTurno()!= null) {
							beanGuardiasColegiado.setIdinstitucion(new Short(letrado.getInscripcionTurno().getIdinstitucion()));
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
						fechaInicioPSt = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2,fechaInicioPeriodo);
						beanGuardiasColegiado.setFechafin(new Date(fechaPeriodo));
						fechaFinPSt = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2,fechaPeriodo);
						beanGuardiasColegiado.setDiasguardia(new Long(beanGuardiasTurno1.getDiasGuardia().longValue()));
						beanGuardiasColegiado.setDiasacobrar(new Long(beanGuardiasTurno1.getDiasPagados().longValue()));
						beanGuardiasColegiado.setFechamodificacion(new Date());	
						today = changeDateFormat(OLD_FORMAT2, NEW_FORMAT2,formatter.format(new Date()));
						beanGuardiasColegiado.setUsumodificacion(usuModificacion1);
						beanGuardiasColegiado.setReserva("N");
						beanGuardiasColegiado.setObservaciones("-");
						beanGuardiasColegiado.setFacturado("N");
						beanGuardiasColegiado.setPagado("N");
						beanGuardiasColegiado.setIdfacturacion(null);	
						scsGuardiascolegiadoMapper.insertSelective2(beanGuardiasColegiado, fechaInicioPSt, fechaFinPSt, today);
					}
				}
			}
		
		} catch (Exception e) {		
			e.printStackTrace();
			throw new Exception(e + ": Excepcion en almacenarAsignacionGuardia.");
		}
	}
	
	/**
	 * Metodo que genera una array de periodos de de fecha a partir de otro. La restriccion que tenemos para 
	 * partir el array inicial es que contenga dias que se agrupan(que vienen en alDiasAgrupar)
	 * @param alPeriodo
	 * @param alDiasAgrupar
	 * @return
	 * @throws Exception
	 */
	private List getPeriodos(ArrayList alPeriodo, List alDiasAgrupar) throws Exception
	{
		List lPeriodosSinAgrupar = new ArrayList();
		if(alDiasAgrupar==null || alDiasAgrupar.size()<1){
			lPeriodosSinAgrupar.add(alPeriodo);
			return lPeriodosSinAgrupar;
		}
        List alDias = new ArrayList();
		for (int i = 0; i < alPeriodo.size(); i++) {
			String date = (String)alPeriodo.get(i);
			Calendar c = Calendar.getInstance ();
		    c.setTime (new Date(date));
		    if(alDiasAgrupar.contains(new Integer (c.get (Calendar.DAY_OF_WEEK)))){
		    	//añadimos el acuemulado anterios si no esta vacio
		    	if(alDias.size()>0){
		    		lPeriodosSinAgrupar.add(alDias);
		    		
		    	}
		    	//Creamos un nuevo almacen para el dia
		    	alDias = new ArrayList();
		    	alDias.add(date);
		    	//lo añadimos al que devolveremos
		    	lPeriodosSinAgrupar.add(alDias);
		    	//inicializamos de nuevo el acumulador de dias
		    	alDias = new ArrayList();
		    	
		    }else{
		    	alDias.add(date);
		    }
		}
		//añadimos el último
		if(alDias.size()>0)
			lPeriodosSinAgrupar.add(alDias);
			
		return lPeriodosSinAgrupar; 
	}
	
	public boolean validaGuardiaLetradoPeriodo(Short idInstitucion, Integer idTurno, Integer idGuardia, Long idPersona, String fechaInicio, String fechaFin) throws Exception{
		
		boolean existeGuardiaLetrado=false;	
		String consulta = "";
		
		try {
			int cabecera = scsGuardiasturnoExtendsMapper.validaGuardiaLetradoPeriodo(idPersona.toString(),  idInstitucion.toString(),  idTurno.toString(),  idGuardia.toString(),  fechaInicio,  fechaFin);
				
				if(cabecera == 0)
					existeGuardiaLetrado=false;
				else
					existeGuardiaLetrado=true;
		}
		catch (Exception e){
			throw new Exception(e +": Excepcion en ScsCabeceraGuardiasAdm.validaGuardiaLetradoPeriodo().");
		}
		return existeGuardiaLetrado;
	}
	
	
	
	public String getValor(String idInstitucion, String idModulo, String idParametro, String valorDefecto) throws Exception
	{
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
			//DUDA
			
			instituciones =  scsGuardiasturnoExtendsMapper.getInstitucionParam(idModulo, idParametro);		
			if (instituciones.contains(idInstitucion)) {
				salida = "S";
			} else {
				salida = "N";
			}}
		catch (Exception e) {
			throw new Exception (e+ "Error al obtener el valor del parámetro " + idParametro);
		}
		if (salida==null) {
			return valorDefecto;
		} else {
			return salida;
		}
	}
	
	private LetradoInscripcionItem getSiguienteLetrado(List<LetradoInscripcionItem> alCompensaciones,
			List alLetradosOrdenados,
			Puntero punteroLetrado,
			ArrayList diasGuardia,
			HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales) throws Exception
	{
		LetradoInscripcionItem letradoGuardia, auxLetradoSeleccionado;

		letradoGuardia = null;

		// recorriendo compensaciones
		if (alCompensaciones != null && alCompensaciones.size() > 0) {

			Iterator<LetradoInscripcionItem> iterador = alCompensaciones.iterator();
			while (iterador.hasNext()) {
				auxLetradoSeleccionado = (LetradoInscripcionItem) iterador.next();
				//log.addLog(new String[] {"Probando Letrado Compensado", auxLetradoSeleccionado.toString()});
				Map<String, Object> mapLog = new HashMap();
//				if (auxLetradoSeleccionado.getPersona() != null) {
//				Map<String, Object> mapLog2 = new HashMap();
//				mapLog2.put("*Probando Letrado Compensado ", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				listaDatosExcelGeneracionCalendarios.add(mapLog2);
//				LOGGER.info("*Probando Letrado Compensado " + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//				}
				
				if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog2 = new HashMap();
				mapLog2.put("*Probando Letrado Compensado ", auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog2);
				LOGGER.info("*Probando Letrado Compensado " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
				}else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
					Map<String, Object> mapLog2 = new HashMap();
					mapLog2.put("*Probando Letrado Compensado ", auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog2);
					LOGGER.info("*Probando Letrado Compensado " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
					}
				// vale
				if (comprobarRestriccionesLetradoCompensado(auxLetradoSeleccionado, diasGuardia, iterador, null, hmBajasTemporales)) {
					letradoGuardia = auxLetradoSeleccionado;
					break;
				}
				else {
					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							mapLog2.put("*Letrado Compensado no valido ", auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Letrado Compensado no valido " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
						}else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
							Map<String, Object> mapLog2 = new HashMap();
							mapLog2.put("*Letrado Compensado no valido ", auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
							listaDatosExcelGeneracionCalendarios.add(mapLog2);
							LOGGER.info("*Letrado Compensado no valido " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
							}
					//log.addLog(new String[] {"Letrado Compensado no valido", auxLetradoSeleccionado.toString()});
//					Map<String, Object> mapLog3 = new HashMap();
//					mapLog3.put("*Letrado Compensado no valido", auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
//					listaDatosExcelGeneracionCalendarios.add(mapLog3);
//					LOGGER.info("*Letrado Compensado no valido" + auxLetradoSeleccionado.getPersona().getApellidos2().toString() + " " + auxLetradoSeleccionado.getPersona().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getPersona().getNombre().toString());
				}
					
			}
		}
		if (letradoGuardia != null)
			return letradoGuardia;

		// recorriendo la cola
		if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {

			int fin = punteroLetrado.getValor();
			do {
				auxLetradoSeleccionado = (LetradoInscripcionItem) alLetradosOrdenados.get(punteroLetrado.getValor());
				//log.addLog(new String[] {"Probando Letrado", auxLetradoSeleccionado.toString()});
				if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
					Map<String, Object> mapLog = new HashMap();
					mapLog.put("*Probando Letrado", auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog);
					LOGGER.info("*Probando Letrado" + auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
				}else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
					Map<String, Object> mapLog2 = new HashMap();
					mapLog2.put("*Probando Letrado ", auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
					listaDatosExcelGeneracionCalendarios.add(mapLog2);
					LOGGER.info("*Probando Letrado " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
					}
				// vale
				if (comprobarRestriccionesLetradoCola(auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos, hmBajasTemporales, false))
					letradoGuardia = auxLetradoSeleccionado;
				else {
					if (auxLetradoSeleccionado.getInscripcionGuardia() != null) {
						Map<String, Object> mapLog3 = new HashMap();
						mapLog3.put("*Letrado no valido", auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
						listaDatosExcelGeneracionCalendarios.add(mapLog3);
						LOGGER.info("*Letrado no valido" + auxLetradoSeleccionado.getInscripcionGuardia().getApellido2().toString() + " " + auxLetradoSeleccionado.getInscripcionGuardia().getApellido1().toString() + ", " + auxLetradoSeleccionado.getInscripcionGuardia().getNombre().toString());
					}else if (auxLetradoSeleccionado.getInscripcionTurno() != null) {
						Map<String, Object> mapLog2 = new HashMap();
						mapLog2.put("*Letrado no valido ", auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
						listaDatosExcelGeneracionCalendarios.add(mapLog2);
						LOGGER.info("*Letrado no valido " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos2().toString() + " " + auxLetradoSeleccionado.getInscripcionTurno().getApellidos1().toString() + ", " + auxLetradoSeleccionado.getInscripcionTurno().getNombre().toString());
						}
					//log.addLog(new String[] {"Letrado no valido", auxLetradoSeleccionado.toString()});
				}
				// obteniendo siguiente en la cola
				if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
					punteroLetrado.setValor(punteroLetrado.getValor() + 1);
				else
					punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio

			} while (letradoGuardia == null && fin != punteroLetrado.getValor());
		}

		if (letradoGuardia != null) {
			return letradoGuardia;
		}else {
			return null;
		}
	} // getSiguienteLetrado()
	
	
	private boolean comprobarRestriccionesLetradoCompensado2(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia,
			Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo,
			HashMap<Long, TreeMap<String,BajasTemporalesItem>> hmBajasTemporales) throws Exception
	{

		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			//log.addLog(new String[] {"Encontrado Baja temporal", letradoGuardia.toString(), diasGuardia.toString()});
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Encontrado Baja temporal", letradoGuardia.toString() + ' ' + diasGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Encontrado Baja temporal" +  letradoGuardia.toString() + ' ' + diasGuardia.toString());
			if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals("")){
				// ... crear un salto cumplido (como si fuera un log)
				insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Cumplido en dia de guardia " + diasGuardia.get(0));
			}
			else {
				// ... crear un salto cumplido (como si fuera un log)
				crearSaltoBT(letradoGuardia.getGrupo().toString(), "Cumplido en dia de guardia " + diasGuardia.get(0), "",
						idInstitucion1.toString(), idTurno1.toString(), idGuardia1.toString(), 
						idCalendarioGuardias1.toString(),this.idCalendarioGuardias1.toString(), letradoGuardia.getBajaTemporal());
			return false; // y no seleccionar
			}
		}

		// si hay incompatibilidad
		if (isIncompatible(letradoGuardia, diasGuardia)) {
			//log.addLog(new String[] {"Encontrado Incompatibilidad", letradoGuardia.toString(), diasGuardia.toString()});
			if (letradoGuardia.getInscripcionGuardia() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionGuardia().getApellido1() + " " + letradoGuardia.getInscripcionGuardia().getApellido2() + ", " + letradoGuardia.getInscripcionGuardia().getNombre().toString() + ' ' + diasGuardia.toString());
			}else if (letradoGuardia.getInscripcionTurno() != null) {
				Map<String, Object> mapLog = new HashMap();
				mapLog.put("*Encontrado Incompatibilidad ", letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
				listaDatosExcelGeneracionCalendarios.add(mapLog);
				LOGGER.info("*Encontrado Incompatibilidad " + letradoGuardia.getInscripcionTurno().getApellidos1() + " " + letradoGuardia.getInscripcionTurno().getApellidos2() + ", " + letradoGuardia.getInscripcionTurno().getNombre().toString() + ' ' + diasGuardia.toString());
			
			}
			return false; // no seleccionar
		}

		// cumpliendo compensacion
		if (letradoGuardia.getGrupo() == null || letradoGuardia.getGrupo().toString().equals("")) {
			//log.addLog(new String[] {"Compensacion cumplida", letradoGuardia.toString()});
			
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Compensacion cumplida", letradoGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Compensacion cumplida" + letradoGuardia.toString());
			cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", ":id="+idCalendarioGuardias1+":Cumplido en fecha ("+diasGuardia.get(0)+"):finid="+idCalendarioGuardias1.toString()+":");
			iteCompensaciones.remove();
		return false;
		}
		else {
			// nada, hay que cumplir la compensacion cuando todos los letrados esten comprobados

		// una vez comprobado todo, se selecciona a este letrado
		//log.addLog(new String[] {"Letrado ok", letradoGuardia.toString()});
			Map<String, Object> mapLog = new HashMap();
			mapLog.put("*Letrado ok", letradoGuardia.toString());
			listaDatosExcelGeneracionCalendarios.add(mapLog);
			LOGGER.info("*Letrado ok" + letradoGuardia.toString());
		return true;
		}
	} // comprobarRestriccionesLetradoCompensado()
	
	/**
	 * Asigna un orden a los letrados inscritos en un grupo de guardia pero si apuntados en él,
	 * en orden consecutivo a partir del ultimo letrado apuntado en el grupo de guardia.
	*/
	public void reordenarRestoGrupoLetrados(int idGrupo, int sizeGrupo) {
		boolean repetido = false;
		Hashtable hash = new Hashtable();
		StringBuffer sql = new StringBuffer();
		int orden=0;		
		ArrayList<Hashtable<String, String>> grupo = new ArrayList<Hashtable<String,String>>();
		sql.append(" SELECT gg.idgrupoguardia, ggc.idgrupoguardiacolegiado, orden ");
		sql.append(" FROM scs_grupoguardiacolegiado ggc, scs_grupoguardia gg ");
		sql.append(" WHERE ggc.idgrupoguardia = gg.idgrupoguardia ");
		sql.append(" AND gg.idgrupoguardia ="+idGrupo+" ");
		sql.append(" AND ggc.orden > "+30000+ " ");
		sql.append(" ORDER BY gg.idgrupoguardia, orden");
		try {								
					Hashtable registro = (Hashtable)  scsGuardiasturnoExtendsMapper.reordenarRestoGrupoLetrados(String.valueOf(idGrupo));							
					if (registro != null){
						orden = sizeGrupo+1;
						updateOrderBBDD(new Integer((String)registro.get("IDGRUPOGUARDIA")),
								orden,
								new Integer((String)registro.get("IDGRUPOGUARDIACOLEGIADO")));											
					}
		} catch (Exception e) {
			// throw new ClsExceptions (e,
			// "Error al recuperar la posicion del ultimo letrado");
		}
	}
	
	@Override
	public ByteArrayInputStream descargarZIPExcelLog(HttpServletRequest request, List<DatosCalendarioProgramadoItem> programacionItemList) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> nombresFicherosList = new ArrayList<>();
		programacionItemList.forEach(programacionItem ->{
			String nombreLog = scsGuardiasturnoExtendsMapper.getLogName(idInstitucion.toString(), programacionItem.getIdCalendarioProgramado().toString(), programacionItem.getObservaciones(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), programacionItem.getIdTurno(), programacionItem.getIdGuardia());
			if (nombreLog != null){
			nombresFicherosList.add(nombreLog);
			}
			
		});
		if (!nombresFicherosList.isEmpty()) {
		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (String nombreFichero : nombresFicherosList) {
				String pathFicheroSalida = "sjcs.directorioFisicoGeneracionCalendarios.{" + idInstitucion.toString() + "}" ;
				pathFicheroSalida = getRutaFicheroSalida(idInstitucion.toString(), nombreFichero);
				String nombreFicheroSalida =  nombreFichero + ".xlsx";
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
		}else {
			return null;
		}
	}
	@Override
	public DatosDocumentoItem descargarExcelLog(HttpServletRequest request, DatosCalendarioProgramadoItem programacionItem) {
		String directorioPlantillaClase = "";
		DatosDocumentoItem docGenerado = new DatosDocumentoItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ArrayList<String> nombresConsultasDatos = null;
		String pathPlantilla = null;
		String pathFicheroSalida = "sjcs.directorioFisicoGeneracionCalendarios.{" + idInstitucion.toString() + "}" ;
		String nombreLog = scsGuardiasturnoExtendsMapper.getLogName(idInstitucion.toString(), programacionItem.getIdCalendarioProgramado().toString(), programacionItem.getObservaciones(), programacionItem.getFechaDesde(), programacionItem.getFechaHasta(), programacionItem.getIdTurno(), programacionItem.getIdGuardia());
		
		try {
			pathFicheroSalida = getRutaFicheroSalida(idInstitucion.toString(), nombreLog);
			String nombreFicheroSalida =  nombreLog + ".xlsx";
			String path = pathFicheroSalida + nombreFicheroSalida;
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
		String pathFicheroSalida = "sjcs.directorioFisicoGeneracionCalendarios.{" + idInstitucion1.toString() + "}" ;
		List<List<Map<String, Object>>> listaDatosExcelGeneracionCalendarios2 = new ArrayList<>();
		List<String> nombresFicherosList = new ArrayList<>();

		try {
		listaDatosExcelGeneracionCalendarios2.add(listaDatosExcelGeneracionCalendarios);
		pathFicheroSalida = getRutaFicheroSalida(idInstitucion1.toString(), nombreLog.toString());
			docGenerado = _generacionDocService.generarExcelGeneracionCalendario(pathFicheroSalida, nombreLog + ".xlsx", listaDatosExcelGeneracionCalendarios2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return docGenerado;
	}
	
	private String getRutaFicheroSalida(String idInstitucion, String folder) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.parametroRutaSalidaInformes);
		
		GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);
		
		String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion + SigaConstants.pathSeparator +  folder + SigaConstants.pathSeparator;
		
		return rutaTmp;
	}
	


	@Override
	public InscripcionesResponseDTO getInscripciones(InscripcionDatosEntradaDTO inscripcionesBody, HttpServletRequest request) {
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
				
				
				
				inscripciones = scsInscripcionguardiaExtendsMapper.getListadoInscripciones(inscripcionesBody, idInstitucion.toString());
				
				for(BusquedaInscripcionItem inscrip:inscripciones) {
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
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		String inscripciones=null;
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getInscripciones() -> Entrada para obtener las inscripciones");
				
				
				//DESCOMENTAR CUANDO SUBA CRISTINA
				//inscripciones = scsInscripcionguardiaExtendsMapper.getValidarInscripciones(validarBody, idInstitucion.toString());

				
				LOGGER.info("getInscripciones() -> Salida ya con los datos recogidos");
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
				this.LOGGER.info("getTipoDiaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = this.admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				this.LOGGER.info("getTipoDiaGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					org.itcgae.siga.DTO.scs.GuardiasItem guardia = this.scsGuardiasturnoExtendsMapper.getTipoDiaGuardia(idTurno, idGuardia, idInstitucion);
					if (guardia != null) {
						guardia.setTipoDia("Labor. " + guardia.getSeleccionLaborables() + ", Fest. " + guardia.getSeleccionFestivos());
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
	public UpdateResponseDTO denegarInscripciones(BusquedaInscripcionItem denegarBody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		String inscripciones=null;
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getInscripciones() -> Entrada para obtener las inscripciones");
				
				
				//DESCOMENTAR CUANDO SUBA CRISTINA
				//inscripciones = scsInscripcionguardiaExtendsMapper.getDenegarInscripciones(denegarBody, idInstitucion.toString());

				
				LOGGER.info("getInscripciones() -> Salida ya con los datos recogidos");
			}
		}
		
		upd.setStatus(inscripciones);
		return upd;
	}
	
	@Override
	public GuardiasDTO busquedaGuardiasColegiado(GuardiasItem guardiaItem,HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		String inscripciones=null;
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
				
				List<GuardiasItem> guardiasColegiado = scsCabeceraguardiasExtendsMapper.busquedaGuardiasColegiado(guardiaItem, idInstitucion.toString());
				
				Date fechaActural = new Date();
				
				for(GuardiasItem guardiaCol: guardiasColegiado) {
					
					
					//obtenemos el tipo de dias por guardia.
					GuardiasItem diasGuardias = new GuardiasItem();
					
					
					diasGuardias.setIdTurno(guardiaCol.getIdTurno());
					diasGuardias.setIdGuardia(guardiaCol.getIdGuardia());
					
					
					List<GuardiasItem> guardiasTurno = scsGuardiasturnoExtendsMapper.searchGuardias2(diasGuardias,
							idInstitucion.toString(), usuarios.get(0).getIdlenguaje(), tamMaximo);
					
					if(guardiasTurno != null && guardiasTurno.size() > 0) {
						guardiasTurno = guardiasTurno.stream().map(it -> {
							it.setTipoDia(("Selección: Labor. " + it.getSeleccionLaborables() + ", Fest. "
									+ it.getSeleccionFestivos()).replace("null", ""));
							return it;
						}).collect(Collectors.toList());
						guardiaCol.setTipoDiasGuardia(guardiasTurno.get(0).getTipoDia());
					}else {
						guardiaCol.setTipoDiasGuardia("Sin dias en la Guardia.");
					}
					
					
					//obtenemos la posicion de un colegiado para un grupo en caso de que esta guardia este en un grupo.
					ScsGrupoguardiacolegiadoExample example = new ScsGrupoguardiacolegiadoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(Integer.parseInt(guardiaCol.getIdTurno()))
					.andIdguardiaEqualTo(Integer.parseInt(guardiaCol.getIdGuardia())).andIdpersonaEqualTo(Long.parseLong(guardiaCol.getIdPersona()));
					List<ScsGrupoguardiacolegiado> grupos = scsGrupoguardiacolegiadoExtendsMapper.selectByExample(example);
					if(grupos == null || grupos.isEmpty()) {
						guardiaCol.setOrdenGrupo("Sin Grupo");
					}else {
						guardiaCol.setOrdenGrupo(grupos.get(0).getOrden().toString());
					}
					
					
					//Obtencion de la guardia con permuta. Como no se sabe a que puntero hace referencia en SCS_PERMUTAGUARDIA se hace la busqueda en ambos casos
					ScsPermutaCabeceraExample permutaExample = new ScsPermutaCabeceraExample();
					permutaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(Integer.parseInt(guardiaCol.getIdTurno()))
					.andIdguardiaEqualTo(Integer.parseInt(guardiaCol.getIdGuardia())).andIdpersonaEqualTo(Long.parseLong(guardiaCol.getIdPersona()))
					.andIdcalendarioguardiasEqualTo(Integer.parseInt(guardiaCol.getIdCalendarioGuardias()));
					
					List<ScsPermutaCabecera> tienePermutaCabecera = scsPermutaCabeceraMapper.selectByExample(permutaExample);
					
					if(!tienePermutaCabecera.isEmpty()) {
						ScsPermutaguardiasExample permutaGuardiaSolExample = new ScsPermutaguardiasExample();
						permutaGuardiaSolExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdPerCabSolicitanteEqualTo(tienePermutaCabecera.get(0).getIdPermutaCabecera());
						
						List<ScsPermutaguardias> tienePermutaGuardiaSol = scsPermutaguardiasMapper.selectByExample(permutaGuardiaSolExample);
						
						if(!tienePermutaGuardiaSol.isEmpty()) {
							if(tienePermutaGuardiaSol.get(0).getFechaconfirmacion() == null) {
								guardiaCol.setEstadoGuardia("Permuta Solicidata.");
							}else {
								if(guardiaCol.getFechahasta() == null) {
									guardiaCol.setEstadoGuardia("Pendiente de Realizar.");
								}
								
								if((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("0")){
									guardiaCol.setEstadoGuardia("Realizada y no validada.");
								}
								
								if(((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("1"))  && guardiaCol.getFacturado() == null ){
									guardiaCol.setEstadoGuardia("Realizada y validada.");
									
								}
								if((guardiaCol.getFacturado() != null && guardiaCol.getFacturado().equals("1")) && ((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("1"))) {
									
									FcsFacturacionjgExample facturacionExample = new FcsFacturacionjgExample();
									facturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdfacturacionEqualTo(guardiaCol.getIdFacturacion());
									
									List<FcsFacturacionjg> facturas = fcsFacturacionJGExtendsMapper.selectByExample(facturacionExample);
									if(!facturas.isEmpty()) {
										guardiaCol.setEstadoGuardia("Facturada - " + facturas.get(0).getNombre());
									}
									
								}
							}
						}else {
							ScsPermutaguardiasExample permutaGuardiaConfExample = new ScsPermutaguardiasExample();
							permutaGuardiaConfExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdPerCabConfirmadorEqualTo(tienePermutaCabecera.get(0).getIdPermutaCabecera());
							
							List<ScsPermutaguardias> tienePermutaGuardiaConf = scsPermutaguardiasMapper.selectByExample(permutaGuardiaConfExample);
							guardiaCol.setEstadoGuardia("Sin permuta asociada.");
							if(!tienePermutaGuardiaConf.isEmpty()) {
								if(tienePermutaGuardiaConf.get(0).getFechaconfirmacion() == null) {
									guardiaCol.setEstadoGuardia("Permuta Solicidata.");
								}else {
									if(guardiaCol.getFechahasta() == null) {
										guardiaCol.setEstadoGuardia("Pendiente de Realizar.");
									}
									
									if((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("0")){
										guardiaCol.setEstadoGuardia("Realizada y no validada.");
									}
									
									if(((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("1"))  && guardiaCol.getFacturado() == null ){
										guardiaCol.setEstadoGuardia("Realizada y validada.");
										
									}
									if((guardiaCol.getFacturado() != null && guardiaCol.getFacturado().equals("1")) && ((guardiaCol.getFechahasta() != null) && guardiaCol.getValidada().equals("1"))) {
										
										FcsFacturacionjgExample facturacionExample = new FcsFacturacionjgExample();
										facturacionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdfacturacionEqualTo(guardiaCol.getIdFacturacion());
										
										List<FcsFacturacionjg> facturas = fcsFacturacionJGExtendsMapper.selectByExample(facturacionExample);
										if(!facturas.isEmpty()) {
											guardiaCol.setEstadoGuardia("Facturada - " + facturas.get(0).getNombre());
										}
										
									}
								}
							}else {
								guardiaCol.setEstadoGuardia("Sin permuta asociada.");
							}
						}
					}else {
						guardiaCol.setEstadoGuardia("Sin permuta asociada.");
					}
	
					
				}
				
				
				
				guardiasDTO.setGuardiaItems(guardiasColegiado);
			}
		}
		
		return guardiasDTO;
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
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()) );
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());
					guardia.setFechavalidacion(guardiasItem.getFechaValidacion());

					response = scsCabeceraguardiasExtendsMapper.validarSolicitudGuardia(guardia);

							
					

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
					guardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()) );
					guardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
					guardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
					guardia.setFechainicio(guardiasItem.getFechadesde());
					if(guardiasItem.getFechaValidacion() == null) {
						response = 0;
					}else {
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

			if (usuarios != null && usuarios.size() > 0 ) {
				LOGGER.info("eliminarGuardiaColegiado() -> Entrada para borrar las incompatibilidades");
				
				ScsCabeceraguardiasKey keyGuardia = new ScsCabeceraguardiasKey();
				
				keyGuardia.setIdinstitucion(idInstitucion);
				keyGuardia.setIdturno(Integer.parseInt(guardiasItem.getIdTurno()) );
				keyGuardia.setIdguardia(Integer.parseInt(guardiasItem.getIdGuardia()));
				keyGuardia.setIdpersona(Long.parseLong(guardiasItem.getIdPersona()));
				keyGuardia.setFechainicio(guardiasItem.getFechadesde());
				
				response = scsCabeceraguardiasExtendsMapper.deleteByPrimaryKey(keyGuardia);
				
				LOGGER.info("eliminarGuardiaColegiados() -> Salida ya con los datos recogidos");
			}else {
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

}