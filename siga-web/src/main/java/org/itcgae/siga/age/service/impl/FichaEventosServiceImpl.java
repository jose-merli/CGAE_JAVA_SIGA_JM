package org.itcgae.siga.age.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
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
import org.itcgae.siga.db.entities.AgeFestivos;
import org.itcgae.siga.db.entities.AgeRepeticionevento;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPartidojudicial;
import org.itcgae.siga.db.entities.CenPoblaciones;
import org.itcgae.siga.db.entities.CenPoblacionesExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioExample;
import org.itcgae.siga.db.mappers.AgeEventoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenPartidojudicialExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeDiassemanaExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEstadoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeFestivosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeRepeticionEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipoeventosExtendsMapper;
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
import org.springframework.web.client.RestTemplate;

@Service
public class FichaEventosServiceImpl implements IFichaEventosService {

	private Logger LOGGER = Logger.getLogger(FichaEventosServiceImpl.class);
	
	@Autowired
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired 
	private AgeEventoMapper ageEventoMapper;
	
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
	
	@Override
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
					"saveNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				if(eventoItem.getDatosRepeticion() != null) {
					AgeRepeticionevento ageRepeticionEvento = new AgeRepeticionevento();
					ageRepeticionEvento.setIdinstitucion(idInstitucion);
					ageRepeticionEvento.setFechainicio(eventoItem.getDatosRepeticion().getFechaInicio());
					ageRepeticionEvento.setFechafin(eventoItem.getDatosRepeticion().getFechaFin());
					ageRepeticionEvento.setFechabaja(null);
					ageRepeticionEvento.setUsumodificacion(usuario.getIdusuario().longValue());
					ageRepeticionEvento.setFechamodificacion(new Date());
					if(eventoItem.getDatosRepeticion().getValoresRepeticion().length != 0) {
						String valoresrepeticion = Arrays.toString(eventoItem.getDatosRepeticion().getValoresRepeticion());
						ageRepeticionEvento.setValoresrepeticion(valoresrepeticion);
					}else {
						ageRepeticionEvento.setValoresrepeticion(null);
					}
					ageRepeticionEvento.setTiporepeticion(eventoItem.getDatosRepeticion().getTipoRepeticion());
					ageRepeticionEvento.setTipodiasrepeticion(eventoItem.getDatosRepeticion().getTipoDiasRepeticion());
					
					LOGGER.info(
							"saveEventCalendar() / ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento) -> Entrada a ageRepeticionEventoExtendsMapper para insertar los datos de repeticion del evento");
					response = ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento);
					LOGGER.info(
							"saveEventCalendar() / ageRepeticionEventoExtendsMapper.insert(ageRepeticionEvento) -> Salida a ageRepeticionEventoExtendsMapper para insertar los datos de repeticion del evento");
					
					LOGGER.info(
							"saveEventCalendar() / ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent() -> Entrada a ageRepeticionEventoExtendsMapper para obtener idRepeticionEvento de los datos de repeticion insertados");
					List<ComboItem> repeticionEventoInserted = ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent();
					LOGGER.info(
							"saveEventCalendar() / ageRepeticionEventoExtendsMapper.selectMaxRepetitionEvent() -> Salida a ageRepeticionEventoExtendsMapper para obtener idRepeticionEvento de los datos de repeticion insertados");
					idRepeticionEvento = repeticionEventoInserted.get(0).getValue();
				}
				
				AgeEvento ageEventoInsert = new AgeEvento();
				ageEventoInsert.setIdcalendario(Long.valueOf(eventoItem.getIdCalendario()));
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
				
				if(idRepeticionEvento != null) {
					ageEventoInsert.setIdrepeticionevento(Long.valueOf(idRepeticionEvento));
				}

				LOGGER.info(
						"saveEventCalendar() / ageEventoMapper.insert(ageEventoInsert) -> Entrada a ageEventoMapper para insertar un evento");
				response = ageEventoMapper.insert(ageEventoInsert);
				LOGGER.info(
						"saveEventCalendar() / ageEventoMapper.insert(ageEventoInsert) -> Salida a ageEventoMapper para insertar un evento");

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al insertar nuevo evento");
				} else {
					error.setCode(200);
				}
			}
		}
		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}
	
	

	@Override
	public FormadorCursoDTO getTrainersLabels(String idCurso, HttpServletRequest request) {
		LOGGER.info(
				"getTrainers() -> Entrada al servicio para obtener los formadores de un curso especifico");

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

		LOGGER.info(
				"getTrainers() -> Salida del servicio para obtener los formadores de un curso especifico");

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
		File XLSFile = ExcelHelper.createExcelFile(orderList, datosVector,
				"PlantillaAsistencia");

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
		for(AsistenciaCursoItem asist : asistenciasCursoItem) {
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
		LOGGER.info(
				"getTypeEvent() -> Entrada al servicio para obtener los tipos de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
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

		LOGGER.info(
				"getTypeEvent() -> Salida del servicio para obtener los tipos de eventos");
		
		return comboDTO;

	}

	@Override
	public ComboDTO getEventStates(HttpServletRequest request) {
		LOGGER.info(
				"getEventStates() -> Entrada al servicio para obtener los estados de eventos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
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

		LOGGER.info(
				"getEventStates() -> Salida del servicio para obtener los estados de eventos");
		
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
				
				for(GenDiccionario rc : repetirCada) {
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
		LOGGER.info(
				"getDaysWeek() -> Entrada al servicio para obtener los días de la semana");

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

		LOGGER.info(
				"getDaysWeek() -> Salida del servicio para obtener los días de la semana");
		
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
				
				ageEventoMapper.insert(record);
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

}
