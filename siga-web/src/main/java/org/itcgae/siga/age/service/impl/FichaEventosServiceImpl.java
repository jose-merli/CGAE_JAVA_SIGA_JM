package org.itcgae.siga.age.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.form.AsistenciaCursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.IFichaEventosService;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.mappers.AgeEventoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEstadoeventosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipoeventosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	
	@Override
	public InsertResponseDTO saveEventCalendar(EventoItem eventoItem, HttpServletRequest request) {
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

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

				// Creamos un nuevo calendario
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

				LOGGER.info(
						"saveEvent() / ageEventoMapper.insert(ageEventoInsert) -> Entrada a ageEventoMapper para insertar un evento");
				response = ageEventoMapper.insert(ageEventoInsert);
				LOGGER.info(
						"saveEvent() / ageEventoMapper.insert(ageEventoInsert) -> Salida a ageEventoMapper para insertar un evento");

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

}
