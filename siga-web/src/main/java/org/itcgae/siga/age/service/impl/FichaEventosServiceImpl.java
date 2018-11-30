package org.itcgae.siga.age.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.age.FestivosItem;
import org.itcgae.siga.DTOs.form.AsistenciaCursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.FestivosDTO;
import org.itcgae.siga.DTOs.gen.ListOfResult;
import org.itcgae.siga.age.service.IFichaEventosService;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.AgeFestivos;
import org.itcgae.siga.db.entities.AgeRepeticionevento;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPartidojudicial;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenPoblacionesExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenPartidojudicialExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeDiassemanaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEstadoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeFestivosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeRepeticionEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.CenInfluenciaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPoblacionesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
public class FichaEventosServiceImpl implements IFichaEventosService {

	private Logger LOGGER = Logger.getLogger(FichaEventosServiceImpl.class);

	@Autowired
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

	@Autowired
	private AgeTipoeventosExtendsMapper ageTipoeventosExtendsMapper;

	@Autowired
	private AgeEstadoeventosExtendsMapper ageEstadoeventosExtendsMapper;

	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;

	@Autowired
	private AgeDiassemanaExtendsMapper ageDiassemanaExtendsMapper;

	@Autowired
	private AgeRepeticionEventoExtendsMapper ageRepeticionEventoExtendsMapper;
	
	@Autowired
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;

	@Autowired
	private AgeFestivosExtendsMapper ageFestivosExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionMapper;
	
	@Autowired
	private CenPartidojudicialExtendsMapper cenPartidojudicialExtendsMapper;
	
	@Autowired
	private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;
	
	@Value("${url.rapis}")
	private String urlRapis;

	
	@Autowired
	private CenInfluenciaExtendsMapper cenInfluenciaExtendsMapper;

	@Override
	@Transactional
	public InsertResponseDTO saveEventCalendar(EventoItem eventoItem, HttpServletRequest request) {
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		String idRepeticionEvento = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			try {
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);

					if (eventoItem.getFechaInicioRepeticion() != null && eventoItem.getFechaFinRepeticion() != null
							&& eventoItem.getValoresRepeticion().length != 0) {
						AgeRepeticionevento ageRepeticionEvento = new AgeRepeticionevento();
						ageRepeticionEvento.setIdinstitucion(idInstitucion);
						ageRepeticionEvento.setFechainicio(eventoItem.getFechaInicioRepeticion());
						ageRepeticionEvento.setFechafin(eventoItem.getFechaFinRepeticion());
						ageRepeticionEvento.setFechabaja(null);
						ageRepeticionEvento.setUsumodificacion(usuario.getIdusuario().longValue());
						ageRepeticionEvento.setFechamodificacion(new Date());
						if (eventoItem.getValoresRepeticion().length != 0) {
							String valoresrepeticion = Arrays.toString(eventoItem.getValoresRepeticion());
							ageRepeticionEvento.setValoresrepeticion(valoresrepeticion);
						} else {
							ageRepeticionEvento.setValoresrepeticion(null);
						}
						ageRepeticionEvento.setTiporepeticion(eventoItem.getTipoRepeticion());
						ageRepeticionEvento.setTipodiasrepeticion(eventoItem.getTipoDiasRepeticion());

						LOGGER.info(
								"saveEventCalendar() / ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento) -> Entrada a ageRepeticionEventoExtendsMapper para insertar los datos de repeticion del evento");
						response = ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento);
						LOGGER.info(
								"saveEventCalendar() / ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento) -> Salida a ageRepeticionEventoExtendsMapper para insertar los datos de repeticion del evento");

						LOGGER.info(
								"saveEventCalendar() / ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent() -> Entrada a ageRepeticionEventoExtendsMapper para obtener idRepeticionEvento de los datos de repeticion insertados");
						List<ComboItem> repeticionEventoInserted = ageRepeticionEventoExtendsMapper
								.selectMaxRepetitionEvent();
						LOGGER.info(
								"saveEventCalendar() / ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent() -> Salida a ageRepeticionEventoExtendsMapper para obtener idRepeticionEvento de los datos de repeticion insertados");
						idRepeticionEvento = repeticionEventoInserted.get(0).getValue();
					}

					AgeCalendarioExample exampleCalendario = new AgeCalendarioExample();
					exampleCalendario.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
							.andIdtipocalendarioEqualTo(Long.valueOf(eventoItem.getIdTipoCalendario()));

					LOGGER.info(
							"saveEventCalendar() / ageCalendarioExtendsMapper.selectByExample(exampleCalendario) -> Entrada a ageCalendarioExtendsMapper para obtener el idCalendario al que pertene el evento");
					List<AgeCalendario> calendarios = ageCalendarioExtendsMapper.selectByExample(exampleCalendario);
					LOGGER.info(
							"saveEventCalendar() / ageCalendarioExtendsMapper.selectByExample(exampleCalendario) -> Salida de ageCalendarioExtendsMapper para obtener el idCalendario al que pertene el evento");

					if (null != calendarios && calendarios.size() > 0) {
						AgeCalendario calendario = calendarios.get(0);

						AgeEvento ageEventoInsert = new AgeEvento();
						ageEventoInsert.setIdcalendario(calendario.getIdcalendario());
						ageEventoInsert.setTitulo(eventoItem.getTitulo());
						ageEventoInsert.setFechainicio(eventoItem.getFechaInicio());
						ageEventoInsert.setFechafin(eventoItem.getFechaFin());
						ageEventoInsert.setFechabaja(null);
						ageEventoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
						ageEventoInsert.setFechamodificacion(new Date());
						ageEventoInsert.setIdinstitucion(idInstitucion);
						ageEventoInsert.setLugar(eventoItem.getLugar());
						ageEventoInsert.setDescripcion(eventoItem.getDescripcion());
						ageEventoInsert.setRecursos(eventoItem.getRecursos());
						ageEventoInsert.setIdtipoevento(Long.valueOf(eventoItem.getIdTipoEvento()));
						ageEventoInsert.setIdestadoevento(Long.valueOf(eventoItem.getIdEstadoEvento()));

						if (idRepeticionEvento != null) {
							ageEventoInsert.setIdrepeticionevento(Long.valueOf(idRepeticionEvento));
						}

						LOGGER.info(
								"saveEventCalendar() / ageEventoMapper.insert(ageEventoInsert) -> Entrada a ageEventoMapper para insertar un evento");
						response = ageEventoExtendsMapper.insert(ageEventoInsert);
						LOGGER.info(
								"saveEventCalendar() / ageEventoMapper.insert(ageEventoInsert) -> Salida a ageEventoMapper para insertar un evento");

						if (response == 0) {
							error.setCode(400);
							error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
						} else {
							error.setCode(200);

							if (idRepeticionEvento != null) {
								response = generateEvents(eventoItem, ageEventoInsert);

								if (response == 0) {
									error.setCode(400);
									error.setDescription(
											"Se ha producido un error en BBDD contacte con su administrador");
								} else {
									error.setCode(200);
								}

							}

						}
					} else {
						error.setCode(400);
						error.setDescription("No existe el calendario");
					}
				}
			} catch (Exception e) {
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
			}
		}
		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	private int generateEvents(EventoItem eventoItem, AgeEvento ageEventoInsert) {

		int response = 0;
		Date fechaInicio = eventoItem.getFechaInicioRepeticion();
		Calendar fechaCalendarIni = Calendar.getInstance();
		fechaCalendarIni.setTime(fechaInicio);
		fechaCalendarIni.set(Calendar.HOUR_OF_DAY, 0);
		fechaInicio = fechaCalendarIni.getTime();
		Date fechaFin = eventoItem.getFechaFinRepeticion();
		Calendar fechaCalendarFinal = Calendar.getInstance();
		fechaCalendarFinal.setTime(fechaFin);
		fechaCalendarFinal.set(Calendar.HOUR_OF_DAY, 0);
		fechaFin = fechaCalendarFinal.getTime();
		Integer[] valoresRepeticion = eventoItem.getValoresRepeticion();
		String tipoRepeticion = eventoItem.getTipoRepeticion();
		List<Date> fechas = new ArrayList<Date>();

		// Si son todos los martes, cogemos el primer martes de ese dia y luego le
		// sumamos 7 para encontrar los siguientes hasta superar el dia final
		for (int i = 0; valoresRepeticion.length > i; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaInicio);
			if (tipoRepeticion.equalsIgnoreCase("S")) {
				if (valoresRepeticion[i] > cal.get(Calendar.DAY_OF_WEEK)) {
					cal.add(Calendar.DAY_OF_WEEK, valoresRepeticion[i] - cal.get(Calendar.DAY_OF_WEEK));

				} else if (valoresRepeticion[i] < cal.get(Calendar.DAY_OF_WEEK)) {
					// Pasamos el dia que seleccionamos al domingo
					switch (cal.get(Calendar.DAY_OF_WEEK)) {
					case 1:
						cal.add(Calendar.DAY_OF_WEEK, 0);
						break;
					case 2:
						cal.add(Calendar.DAY_OF_WEEK, 6);
						break;
					case 3:
						cal.add(Calendar.DAY_OF_WEEK, 5);
						break;
					case 4:
						cal.add(Calendar.DAY_OF_WEEK, 4);
						break;
					case 5:
						cal.add(Calendar.DAY_OF_WEEK, 3);
						break;
					case 6:
						cal.add(Calendar.DAY_OF_WEEK, 2);
						break;
					case 7:
						cal.add(Calendar.DAY_OF_WEEK, 1);
						break;
					}

					switch (valoresRepeticion[i]) {
					case 2:
						cal.add(Calendar.DAY_OF_WEEK, 1);
						break;
					case 3:
						cal.add(Calendar.DAY_OF_WEEK, 2);
						break;
					case 4:
						cal.add(Calendar.DAY_OF_WEEK, 3);
						break;
					case 5:
						cal.add(Calendar.DAY_OF_WEEK, 4);
						break;
					case 6:
						cal.add(Calendar.DAY_OF_WEEK, 5);
						break;
					case 7:
						cal.add(Calendar.DAY_OF_WEEK, 6);
						break;
					case 1:
						break;

					}
				} else {
					cal.add(Calendar.DAY_OF_YEAR, 7);
				}

				while (cal.getTime().before(fechaFin) || cal.getTime().equals(fechaFin)) {
					fechas.add(cal.getTime());
					cal.add(Calendar.DAY_OF_YEAR, 7);
				}

			}

			else {
				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.YEAR, cal.get(Calendar.YEAR));
				cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
				cal2.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				cal2.set(Calendar.HOUR_OF_DAY, 0);
				cal2.set(Calendar.MINUTE, 0);
				cal2.set(Calendar.SECOND, 0);
				cal2.set(Calendar.MILLISECOND, 0);

				if (cal2.get(Calendar.DAY_OF_MONTH) != Integer.valueOf(valoresRepeticion[i])) {
					if (cal2.get(Calendar.MONTH) == 1) {
						cal2.add(Calendar.MONTH, 1);
						cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
					} else {
						cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
					}
				}

				if (cal2.getTime().before(fechaInicio)) {

					cal2.add(Calendar.MONTH, 1);
					if (cal2.get(Calendar.DAY_OF_MONTH) != Integer.valueOf(valoresRepeticion[i])) {
						if (cal2.get(Calendar.MONTH) == 1) {
							cal2.add(Calendar.MONTH, 1);
							cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
						} else {
							cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
						}
					}
				}

				while ((cal2.getTime().before(fechaFin) || cal2.getTime().equals(fechaFin))) {
					fechas.add(cal2.getTime());
					cal2.add(Calendar.MONTH, 1);
					if (cal2.get(Calendar.DAY_OF_MONTH) != Integer.valueOf(valoresRepeticion[i])) {
						if (cal2.get(Calendar.MONTH) == 1) {
							cal2.add(Calendar.MONTH, 1);
							cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
						} else {
							cal2.set(Calendar.DAY_OF_MONTH, Integer.valueOf(valoresRepeticion[i]));
						}
					}
				}
			}
		}

		if (!fechas.isEmpty()) {

			LOGGER.info(
					"saveEventCalendar() / ageEventoExtendsMapper.selectMaxEvent() -> Entrada a ageEventoExtendsMapper para obtener idEvento del evento insertado");
			List<ComboItem> eventos = ageEventoExtendsMapper.selectMaxEvent();
			LOGGER.info(
					"saveEventCalendar() / ageEventoExtendsMapper.selectMaxEvent() -> Salida a ageEventoExtendsMapper para obtener idEvento del evento insertado");
			String idEvento = eventos.get(0).getValue();

			ageEventoInsert.setIdeventooriginal(Long.valueOf(idEvento));

			AgeEventoExample exampleEvento = new AgeEventoExample();
			exampleEvento.createCriteria().andIdeventoEqualTo(Long.valueOf(idEvento))
					.andIdinstitucionEqualTo(ageEventoInsert.getIdinstitucion());

			LOGGER.info(
					"saveEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar si existe la notificacion");

			List<AgeEvento> eventosList = ageEventoExtendsMapper.selectByExample(exampleEvento);

			LOGGER.info(
					"saveEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar si existe la notificacion");

			if (null != eventosList && eventosList.size() > 0) {
				AgeEvento evento = eventosList.get(0);

				evento.setFechamodificacion(new Date());
				evento.setUsumodificacion(evento.getUsumodificacion());
				evento.setIdeventooriginal(evento.getIdevento());

				LOGGER.info(
						"saveEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para editar el campo idEventoOriginal");

				response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

				LOGGER.info(
						"saveEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Salida a ageEventoExtendsMapper para editar el campo idEventoOriginal");

				ageEventoInsert.setIdeventooriginal(evento.getIdevento());

				for (Iterator<Date> iterator = fechas.iterator(); iterator.hasNext();) {

					Date fechaEventInsert = (Date) iterator.next();
					Calendar fechaCalendarInsert = Calendar.getInstance();
					fechaCalendarInsert.setTime(fechaEventInsert);
					Calendar fechaCalendarInicio = Calendar.getInstance();
					fechaCalendarInicio.setTime(ageEventoInsert.getFechainicio());
					fechaCalendarInsert.set(Calendar.HOUR_OF_DAY, fechaCalendarInicio.get(Calendar.HOUR_OF_DAY));
					fechaCalendarInsert.set(Calendar.MINUTE, fechaCalendarInicio.get(Calendar.MINUTE));
					ageEventoInsert.setFechainicio(fechaCalendarInsert.getTime());
					Calendar fechaCalendarFin = Calendar.getInstance();
					fechaCalendarFin.setTime(ageEventoInsert.getFechafin());
					fechaCalendarInsert.set(Calendar.HOUR_OF_DAY, fechaCalendarFin.get(Calendar.HOUR_OF_DAY));
					fechaCalendarInsert.set(Calendar.MINUTE, fechaCalendarFin.get(Calendar.MINUTE));
					ageEventoInsert.setFechafin(fechaCalendarInsert.getTime());

					LOGGER.info(
							"generateEvents() / ageEventoMapper.insert(ageEventoInsert) -> Entrada a ageEventoMapper para insertar los eventos replicados");
					response = ageEventoExtendsMapper.insert(ageEventoInsert);
					LOGGER.info(
							"generateEvents() / ageEventoMapper.insert(ageEventoInsert) -> Salida a ageEventoMapper para insertar los eventos replicados");

				}

			}
		}

		return response;
	}

	@Override
	public FormadorCursoDTO getTrainersLabels(String idCurso, HttpServletRequest request) {
		LOGGER.info("getTrainers() -> Entrada al servicio para obtener los formadores de un curso especifico");

		FormadorCursoDTO formadoresCursoDTO = new FormadorCursoDTO();
		List<FormadorCursoItem> formadoresCursoItem = new ArrayList<FormadorCursoItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			LOGGER.info(
					"getTrainers() / forPersonacursoExtendsMapper.getTrainers(idInstitucion, idCurso) -> Entrada a forPersonacursoExtendsMapper para obtener los formadores de un curso especifico");
			formadoresCursoItem = forPersonacursoExtendsMapper.getTrainersLabels(idInstitucion.toString(), idCurso);
			LOGGER.info(
					"getTrainers() / forPersonacursoExtendsMapper.getTrainers(idInstitucion, idCurso) -> Salida de forPersonacursoExtendsMapper para obtener los formadores de un curso especifico");

			formadoresCursoDTO.setFormadoresCursoItem(formadoresCursoItem);

		}

		LOGGER.info("getTrainers() -> Salida del servicio para obtener los formadores de un curso especifico");

		return formadoresCursoDTO;
	}

	@Override
	public File createExcelAssistanceFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {

		LOGGER.info("createExcelAssistanceFile() -> Entrada al servicio que crea la plantilla Excel");

		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, "PlantillaAsistencia");

		LOGGER.info("createExcelAssistanceFile() -> Salida al servicio que crea la plantilla Excel");

		return XLSFile;

	}

	@Override
	public ResponseEntity<InputStreamResource> generateExcelAssistance(List<AsistenciaCursoItem> asistenciasCursoItem) {

		LOGGER.info("generateExcelAssistance() -> Entrada al servicio para generar la plantilla Excel Asistencia");

		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();

		// 1. Se definen las columnas que conforman la plantilla

		// 1.1 Se rellena las filas con los asistentes al curso
		for (AsistenciaCursoItem asist : asistenciasCursoItem) {
			datosHashtable = new Hashtable<String, Object>();
			datosHashtable.put(IFichaEventosService.NOMBRE, asist.getNombre());
			datosHashtable.put(IFichaEventosService.ASISTENCIA, " ");
			datosVector.add(datosHashtable);
		}

		// 2. Crea el fichero excel
		File file = createExcelAssistanceFile(IFichaEventosService.CAMPOSPLANTILLA, datosVector);

		// 3. Se convierte el fichero en array de bytes para enviarlo al front
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.info("generateExcelAssistance() -> Salida del servicio para generar la plantilla Excel Asistencia");

		return res;
	}

	@Override
	public ComboDTO getTypeEvent(HttpServletRequest request) {
		LOGGER.info("getTypeEvent() -> Entrada al servicio para obtener los tipos de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTypeEvent() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeEvent() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeEvent() / ageTipoeventosExtendsMapper.getTypeEvent() -> Entrada a ageTipoeventosExtendsMapper para obtener los tipos de eventos");
				comboItems = ageTipoeventosExtendsMapper.getTypeEvent(usuario.getIdlenguaje());
				LOGGER.info(
						"getTypeEvent() / ageTipoeventosExtendsMapper.getTypeEvent() -> Salida de ageTipoeventosExtendsMapper para obtener los tipos de eventos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeEvent() -> Salida del servicio para obtener los tipos de eventos");

		return comboDTO;

	}

	@Override
	public ComboDTO getEventStates(HttpServletRequest request) {
		LOGGER.info("getEventStates() -> Entrada al servicio para obtener los estados de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEventStates() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEventStates() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEventStates() / ageEstadoeventosExtendsMapper.getEventStates() -> Entrada a ageTipoeventosExtendsMapper para obtener los estados de eventos");
				comboItems = ageEstadoeventosExtendsMapper.getEventStates(usuario.getIdlenguaje());
				LOGGER.info(
						"getEventStates() / ageEstadoeventosExtendsMapper.getEventStates() -> Salida de ageTipoeventosExtendsMapper para obtener los estados de eventos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getEventStates() -> Salida del servicio para obtener los estados de eventos");

		return comboDTO;

	}

	@Override
	public ComboDTO getRepeatEvery(HttpServletRequest request) {
		LOGGER.info(
				"getRepeatEvery() -> Entrada al servicio para obtener el rango de tiempo que va a repetir un eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getRepeatEvery() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getRepeatEvery() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				GenDiccionarioExample exampleDiccionario = new GenDiccionarioExample();
				exampleDiccionario.createCriteria().andIdlenguajeEqualTo(usuario.getIdlenguaje())
						.andIdrecursoLike("fichaEventos.datosRepeticion.repetirCada%");

				LOGGER.info(
						"getRepeatEvery() / genDiccionarioExtendsMapper.selectByExample(exampleDiccionario) -> Entrada a genDiccionarioExtendsMapper para obtener el rango de tiempo que va a repetir un eventos");

				List<GenDiccionario> repetirCada = genDiccionarioExtendsMapper.selectByExample(exampleDiccionario);

				for (GenDiccionario rc : repetirCada) {
					ComboItem item = new ComboItem();
					item.setLabel(rc.getDescripcion());
					item.setValue(String.valueOf(rc.getDescripcion().charAt(0)));

					comboItems.add(item);
				}
				LOGGER.info(
						"getRepeatEvery() / genDiccionarioExtendsMapper.selectByExample(exampleDiccionario) -> Salida de genDiccionarioExtendsMapper para obtener el rango de tiempo que va a repetir un eventos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getRepeatEvery() -> Salida del servicio para obtener el rango de tiempo que va a repetir un eventos");

		return comboDTO;
	}

	@Override
	public ComboDTO getDaysWeek(HttpServletRequest request) {
		LOGGER.info("getDaysWeek() -> Entrada al servicio para obtener los días de la semana");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDaysWeek() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDaysWeek() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getDaysWeek() / ageDiassemanaExtendsMapper.getDaysWeek(usuario.getIdlenguaje()) -> Entrada a genDiccionarioExtendsMapper para obtener los días de la semana");

				comboItems = ageDiassemanaExtendsMapper.getDaysWeek(usuario.getIdlenguaje());

				LOGGER.info(
						"getDaysWeek() / ageDiassemanaExtendsMapper.getDaysWeek(usuario.getIdlenguaje()) -> Salida de genDiccionarioExtendsMapper para obtener los días de la semana");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getDaysWeek() -> Salida del servicio para obtener los días de la semana");

		return comboDTO;
	}
	
	@Override
	public ComboDTO getJudicialDistrict (HttpServletRequest request) {
		LOGGER.info("getJudicialDistrict() -> Entrada al servicio para obtener los partido judiciales donde se puede realiza el evento");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
		
				LOGGER.info(
						"getJudicialDistrict() / cenInfluenciaExtendsMapper.getJudicialDistrict(idInstitucion.toString()) -> Entrada a cenInfluenciaExtendsMapper para obtener los partido judiciales donde se puede realiza el evento");

				comboItems = cenInfluenciaExtendsMapper.getJudicialDistrict(idInstitucion.toString());

				LOGGER.info(
						"getJudicialDistrict() / cenInfluenciaExtendsMapper.getJudicialDistrict(idInstitucion.toString()) -> Salida de cenInfluenciaExtendsMapper para obtener los partido judiciales donde se puede realiza el evento");

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getJudicialDistrict() -> Salida del servicio para obtener los partido judiciales donde se puede realiza el evento");

		return comboDTO;
	}
	
	@Override
	public void generaEventosLaboral() {
		//TODO Cambiar por la etiqueta de la descripcion del calendario en BBDD
		String descripcionCalendario = "Calendario Laboral";
		
		AgeCalendarioExample ageCalendarioExample = new AgeCalendarioExample();
		ageCalendarioExample.createCriteria().andDescripcionEqualTo(descripcionCalendario);
		
		List<AgeCalendario> listAgeCalendario = ageCalendarioExtendsMapper.selectByExample(ageCalendarioExample);
		
		for (AgeCalendario ageCalendario : listAgeCalendario) {
			Short idInstitucionCalendario = ageCalendario.getIdinstitucion();
			
			List<FestivosItem> listFestivosItem = ageFestivosExtendsMapper.getFestivos(idInstitucionCalendario);
			
			for (FestivosItem ageFestivos : listFestivosItem) {
				AgeEvento record = new AgeEvento();
				
				record.setIdcalendario(ageCalendario.getIdcalendario());
				record.setIdinstitucion(idInstitucionCalendario);
				record.setTitulo(ageFestivos.getTipoFestivo());
				record.setFechainicio(ageFestivos.getFecha());
				record.setFechafin(ageFestivos.getFecha());
				record.setLugar(ageFestivos.getLugar());
				record.setDescripcion(ageFestivos.getDenominacion());
				record.setRecursos(null);
				//TODO Revisar que idEstadoEvento e idTipoEvento se debería de poner
//				record.setIdestadoevento(idestadoevento);
				record.setIdtipoevento(Long.parseLong("9"));
				record.setFechabaja(null);
				record.setUsumodificacion((long)0);
				record.setFechamodificacion(new Date());
				
				ageEventoExtendsMapper.insert(record);
			}
			
		}
		
	}
	
	//TODO Revisar
	@Override
	public void insertarFestivosAuto() {
		List<CenInstitucion> listCenInstitucion = cenInstitucionMapper.selectByExample(new CenInstitucionExample());
		
		for (CenInstitucion cenInstitucion : listCenInstitucion) {
			Short idInstitucion = cenInstitucion.getIdinstitucion();
			
			List<CenPartidojudicial> listCenPartidoJudicial = cenPartidojudicialExtendsMapper.getPartidoByInstitucion(idInstitucion);

			// Obtenemos el listado con los festivos insertados para comprobar que no se inserten duplicados en la misma fecha, misma idInstitucion
			List<String> listFechaFestivosPrevio = ageFestivosExtendsMapper.getFechaFestivos(idInstitucion);
			
			for (CenPartidojudicial cenPartidojudicial : listCenPartidoJudicial) {
				Long idPartidoJudicial = cenPartidojudicial.getIdpartido();
				CenPoblacionesExample cenPoblacionExample = new CenPoblacionesExample();
				cenPoblacionExample.createCriteria().andIdpartidoEqualTo(idPartidoJudicial).andSedejudicialEqualTo((short)1);
				List<CenPoblaciones> listCenPoblaciones = cenPoblacionesExtendsMapper.selectByExample(cenPoblacionExample);
				
				for (CenPoblaciones cenPoblaciones : listCenPoblaciones) {
					String idPoblacion = cenPoblaciones.getIdpoblacion().substring(0, 5);
					FestivosDTO festivosDTO = llamadaUrl(idPoblacion).getBody();
					
					List<ListOfResult> listResultado = festivosDTO.getListOfResult();

					for (ListOfResult listOfResult : listResultado) {
						if (!listFechaFestivosPrevio.contains(listOfResult.getFecha())) {

							AgeFestivos ageFestivos = new AgeFestivos();

							ageFestivos.setDenominacion(listOfResult.getDenominacion());

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								ageFestivos.setFecha(sdf.parse(listOfResult.getFecha()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							ageFestivos.setIdinstitucion(idInstitucion);
							ageFestivos.setIdpartido(idPartidoJudicial);
							ageFestivos.setTipofestivo(listOfResult.getTipoDeFestivo().getDenominacion());

							ageFestivosExtendsMapper.insert(ageFestivos);
						}
					}
				}
			}
		}
	}
	
	private ResponseEntity<FestivosDTO> llamadaUrl(String idPartidoJudicial) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<FestivosDTO> result = restTemplate
				.exchange(urlRapis + "festivos/buscar?codigoMunicipio=" + idPartidoJudicial + "&tipoFestivo=&fecha=&page=0&size=9999999",
				HttpMethod.GET, entity, FestivosDTO.class);
		
		return result;
	}
	
	
	//TODO Revisar
	private void insertarFestivosEvento(EventoItem eventoItem) {
		
		AgeFestivos ageFestivos = new AgeFestivos();

		ageFestivos.setDenominacion(eventoItem.getDescripcion());
		ageFestivos.setFecha(eventoItem.getFechaInicio());
		ageFestivos.setIdinstitucion(eventoItem.getIdInstitucion());
		ageFestivos.setIdpartido(eventoItem.getIdPartidoJudicial());
		
		//TODO Poner como property de BBDD
		ageFestivos.setTipofestivo("Festivo Manual");

		ageFestivosExtendsMapper.insert(ageFestivos);
	}


	@Override
	public UpdateResponseDTO updateEventCalendar(EventoItem eventoItem, HttpServletRequest request) {
		LOGGER.info("updateEventCalendar() -> Entrada al servicio para modificar los eventos");

		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				AgeEventoExample exampleEvent = new AgeEventoExample();
				exampleEvent.createCriteria().andIdeventoEqualTo(Long.valueOf(eventoItem.getIdEvento()))
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"updateEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Entrada a ageNotificacioneseventoMapper para buscar si existe el evento");

				List<AgeEvento> eventList = ageEventoExtendsMapper.selectByExample(exampleEvent);

				LOGGER.info(
						"updateEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Salida a ageNotificacioneseventoMapper para buscar si existe el evento");

				if (null != eventList && eventList.size() > 0) {
					AgeEvento event = eventList.get(0);

					LOGGER.info(
							"updateEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Entrada a ageCalendarioExtendsMapper para modificar un evento");

					event.setFechamodificacion(new Date());
					event.setUsumodificacion(usuario.getIdusuario().longValue());
					event.setTitulo(eventoItem.getTitulo());
					event.setIdestadoevento(Long.valueOf(eventoItem.getIdEstadoEvento()));
					event.setLugar(eventoItem.getLugar());
					event.setDescripcion(eventoItem.getDescripcion());
					event.setRecursos(eventoItem.getRecursos());
					response = ageEventoExtendsMapper.updateByPrimaryKey(event);

					LOGGER.info(
							"updateEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(event) -> Salida a ageCalendarioExtendsMapper para modificar un evento");

				}

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al modificar evento");
				} else {
					error.setCode(200);
				}
			}
		}

		LOGGER.info("updateEventCalendar() -> Salida del servicio para modificar los eventos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteEventCalendar(EventoDTO eventoDTO, HttpServletRequest request) {
		LOGGER.info("deleteEventCalendar() -> Entrada al servicio para dar de baja a un evento especifico");

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
					"deleteEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteEventCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (EventoItem event : eventoDTO.getEventos()) {

						AgeEventoExample exampleEvent = new AgeEventoExample();
						exampleEvent.createCriteria().andIdeventoEqualTo(Long.valueOf(event.getIdEvento()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"deleteEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Entrada a ageEventoExtendsMapper para buscar si existe el evento");

						List<AgeEvento> eventList = ageEventoExtendsMapper.selectByExample(exampleEvent);

						LOGGER.info(
								"deleteEventCalendar() / ageEventoExtendsMapper.selectByExample(exampleEvent) -> Salida a ageEventoExtendsMapper para buscar si existe el evento");

						if (null != eventList && eventList.size() > 0) {
							AgeEvento eventDelete = eventList.get(0);

							LOGGER.info(
									"deleteEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(eventDelete) -> Entrada a ageEventoExtendsMapper para dar de baja el evento");

							eventDelete.setFechamodificacion(new Date());
							eventDelete.setUsumodificacion(usuario.getIdusuario().longValue());
							eventDelete.setFechabaja(new Date());
							ageEventoExtendsMapper.updateByPrimaryKey(eventDelete);

							LOGGER.info(
									"deleteEventCalendar() / ageEventoExtendsMapper.updateByPrimaryKey(eventDelete) -> Salida a ageEventoExtendsMapper para dar de baja el evento");

						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}
			}
		}

		if (response == 1) {
			error.setCode(200);
		}

		LOGGER.info("deleteEventCalendar() -> Salida del servicio para dar de baja a un evento especifico");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

}
