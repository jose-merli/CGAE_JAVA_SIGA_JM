package org.itcgae.siga.age.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.CalendarItem;
import org.itcgae.siga.DTOs.age.PermisoCalendarioItem;
import org.itcgae.siga.DTOs.age.PermisosCalendarioDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarDTO;
import org.itcgae.siga.DTOs.age.PermisosPerfilesCalendarItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.age.service.IFichaCalendarioService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;
import org.itcgae.siga.db.entities.AgePermisoscalendario;
import org.itcgae.siga.db.entities.AgePermisoscalendarioExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgePermisosCalendarioExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeTipocalendarioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichaCalendarioServiceImpl implements IFichaCalendarioService {

	private Logger LOGGER = Logger.getLogger(FichaCalendarioServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AgeTipocalendarioExtendsMapper ageTipocalendarioExtendsMapper;

	@Autowired
	private AgeCalendarioExtendsMapper ageCalendarioExtendsMapper;

	@Autowired
	private AgePermisosCalendarioExtendsMapper agePermisosCalendarioExtendsMapper;

	@Override
	public ComboDTO getCalendarType(HttpServletRequest request) {

		LOGGER.info("getCalendarType() -> Entrada al servicio para obtener los tipos de calendarios");

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
					"getCalendarType() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalendarType() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCalendarType() / ageTipocalendarioExtendsMapper.getCalendarType() -> Entrada a ageTipocalendarioExtendsMapper para obtener los diferentes tipos de calendarios");
				comboItems = ageTipocalendarioExtendsMapper.getCalendarType(usuario.getIdlenguaje());
				LOGGER.info(
						"getCalendarType() / ageTipocalendarioExtendsMapper.getCalendarType() -> Salida de ageTipocalendarioExtendsMapper para obtener los diferentes tipos de calendarios");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getCalendarType() -> Salida del servicio para obtener los tipos de calendarios");

		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updatePermissions(PermisosCalendarioDTO permisosCalendarioDTO,
			HttpServletRequest request) {
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
					"updatePermisos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updatePermisos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					for (PermisoCalendarioItem permisoCalendarioItem : permisosCalendarioDTO
							.permisosCalendarioItems()) {

						AgePermisoscalendarioExample examplePermiso = new AgePermisoscalendarioExample();
						examplePermiso.createCriteria().andIdperfilEqualTo(permisoCalendarioItem.getIdPerfil())
								.andIdcalendarioEqualTo(Long.valueOf(permisoCalendarioItem.getIdCalendario()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"insertCalendar() / agePermisosCalendarioExtendsMapper.selectByExample(examplePermiso) -> Entrada a agePermisosCalendarioExtendsMapper para buscar si el perfil ya tiene un permiso");

						List<AgePermisoscalendario> permisos = agePermisosCalendarioExtendsMapper
								.selectByExample(examplePermiso);

						LOGGER.info(
								"insertCalendar() / agePermisosCalendarioExtendsMapper.selectByExample(examplePermiso) -> Salida a ageCalendarioExtendsMapper para buscar si el perfil ya tiene un permiso");

						if (null != permisos && permisos.size() > 0) {
							AgePermisoscalendario permiso = permisos.get(0);

							LOGGER.info(
									"insertCalendar() / agePermisosCalendarioExtendsMapper.updatePermiso(...) -> Entrada a agePermisosCalendarioExtendsMapper para modificar el permiso de calendario si el perfil ya existe");

							permiso.setFechamodificacion(new Date());
							permiso.setTipoacceso(Long.valueOf(permisoCalendarioItem.getDerechoacceso()));
							permiso.setUsumodificacion(usuario.getIdusuario().longValue());
							response = agePermisosCalendarioExtendsMapper.updateByPrimaryKey(permiso);

							LOGGER.info(
									"insertCalendar() / agePermisosCalendarioExtendsMapper.updatePermiso(...) -> Salida a agePermisosCalendarioExtendsMapper para modificar el permiso de calendario si el perfil ya existe");

						} else {

							AgePermisoscalendario permission = new AgePermisoscalendario();
							permission.setIdinstitucion(idInstitucion);
							permission.setIdcalendario(Long.valueOf(permisoCalendarioItem.getIdCalendario()));
							permission.setTipoacceso(Long.valueOf(permisoCalendarioItem.getDerechoacceso()));
							permission.setUsumodificacion(usuario.getIdusuario().longValue());
							permission.setIdperfil(permisoCalendarioItem.getIdPerfil());
							permission.setFechamodificacion(new Date());

							LOGGER.info(
									"insertCalendar() / agePermisosCalendarioExtendsMapper.insert(permission) -> Entrada a agePermisosCalendarioExtendsMapper para insertar el nuevo permiso de calendario a un perfil");

							response = agePermisosCalendarioExtendsMapper.insert(permission);

							LOGGER.info(
									"insertCalendar() / agePermisosCalendarioExtendsMapper.insert(permission) -> Salida a agePermisosCalendarioExtendsMapper para insertar el nuevo permiso de calendario a un perfil");

						}

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}
				
				if (response == 0) {
					error.setCode(400);
					error.setDescription(
							"Se ha producido un error en BBDD contacte con su administrador");
				} else {
					error.setCode(200);
				}


			}
		}

		updateResponseDTO.setError(error);
		return updateResponseDTO;

	}

	@Override
	public InsertResponseDTO saveCalendar(CalendarItem calendarItem, HttpServletRequest request) {
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		String idCalendario = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Creamos un nuevo calendario
				AgeCalendario ageCalendarioInsert = new AgeCalendario();
				// IdCalendario es una Secuencia
				ageCalendarioInsert.setIdinstitucion(idInstitucion);
				ageCalendarioInsert.setUsumodificacion(usuario.getIdusuario().longValue());
				ageCalendarioInsert.setFechamodificacion(new Date());
				ageCalendarioInsert.setIdtipocalendario(Long.parseLong(calendarItem.getIdTipoCalendario()));
				ageCalendarioInsert.setDescripcion(calendarItem.getDescripcion());
				ageCalendarioInsert.setColor(calendarItem.getColor());

				LOGGER.info(
						"saveCalendar() / ageCalendarioExtendsMapper.insert(ageCalendarioInsert) -> Entrada a ageCalendarioExtendsMapper para insertar un calendario");
				response = ageCalendarioExtendsMapper.insert(ageCalendarioInsert);
				LOGGER.info(
						"saveCalendar() / ageCalendarioExtendsMapper.insert(ageCalendarioInsert) -> Salida a ageCalendarioExtendsMapper para insertar un calendario");

				LOGGER.info(
						"saveCalendar() / ageCalendarioExtendsMapper.selectMaxCalendar() -> Entrada a ageCalendarioExtendsMapper para obtener idCalendario del calendario insertado");
				List<ComboItem> calendarInserted = ageCalendarioExtendsMapper.selectMaxCalendar();
				LOGGER.info(
						"saveCalendar() / ageCalendarioExtendsMapper.selectMaxCalendar() -> Salida a ageCalendarioExtendsMapper para obtener idCalendario del calendario insertado");
				idCalendario = calendarInserted.get(0).getValue();

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al insertar nuevo calendario");
				} else {
					error.setCode(200);
				}
			}
		}
		insertResponseDTO.setId(idCalendario);
		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateCalendar(CalendarItem calendarItem, HttpServletRequest request) {
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateCalendar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateCalendar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				AgeCalendarioExample exampleCalendario = new AgeCalendarioExample();
				exampleCalendario.createCriteria().andIdcalendarioEqualTo(Long.valueOf(calendarItem.getIdCalendario()))
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"updateCalendar() / ageCalendarioExtendsMapper.selectByExample(examplePermiso) -> Entrada a ageCalendarioExtendsMapper para buscar si el calendario ya existe");

				List<AgeCalendario> calendarios = ageCalendarioExtendsMapper.selectByExample(exampleCalendario);

				LOGGER.info(
						"updateCalendar() / ageCalendarioExtendsMapper.selectByExample(examplePermiso) -> Salida a ageCalendarioExtendsMapper para buscar si el calendario ya existe");

				if (null != calendarios && calendarios.size() > 0) {
					AgeCalendario calendario = calendarios.get(0);

					LOGGER.info(
							"insertCalendar() / ageCalendarioExtendsMapper.updateByPrimaryKey(calendario) -> Entrada a ageCalendarioExtendsMapper para modificar el calendario");

					calendario.setFechamodificacion(new Date());
					calendario.setUsumodificacion(usuario.getIdusuario().longValue());
					calendario.setColor(calendarItem.getColor());
					calendario.setDescripcion(calendarItem.getDescripcion());
					response = ageCalendarioExtendsMapper.updateByPrimaryKey(calendario);

					LOGGER.info(
							"insertCalendar() / ageCalendarioExtendsMapper.updateByPrimaryKey(calendario) -> Salida a ageCalendarioExtendsMapper para modificar el calendario");

				}

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al modificar nuevo calendario");
				} else {
					error.setCode(200);
				}
			}
		}

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public CalendarDTO getCalendar(String idCalendario, HttpServletRequest request) {
		LOGGER.info("getCalendar() -> Entrada al servicio para obtener un calendario especifico");

		AgeCalendario calendar = new AgeCalendario();
		CalendarDTO calendarioDTO = new CalendarDTO();
		CalendarItem calendarItem = new CalendarItem();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			LOGGER.info(
					"getCalendar() / ageCalendarioExtendsMapper.selectByPrimaryKey(idCalendario) -> Entrada a ageCalendarioExtendsMapper para obtener los permisos de los perfiles");
			calendar = ageCalendarioExtendsMapper.selectByPrimaryKey(Long.parseLong(idCalendario));
			LOGGER.info(
					"getCalendar() / ageCalendarioExtendsMapper.selectByPrimaryKey(idCalendario) -> Salida de ageCalendarioExtendsMapper para obtener los permisos de los perfiles");

			if (calendar != null) {

				calendarItem.setIdTipoCalendario(calendar.getIdtipocalendario().toString());
				calendarItem.setColor(calendar.getColor());
				calendarItem.setDescripcion(calendar.getDescripcion());
			}

			calendarioDTO.setCalendar(calendarItem);

		}

		LOGGER.info("getCalendar() -> Salida del servicio para obtener un calendario especifico");

		return calendarioDTO;
	}

	@Override
	public PermisosPerfilesCalendarDTO getProfilesPermissions(String idCalendario, HttpServletRequest request) {
		LOGGER.info(
				"getPermisosProfiles() -> Entrada al servicio para obtener los permisos de calendario de todos los perfiles");

		List<PermisosPerfilesCalendarItem> profilesCalendar = new ArrayList<PermisosPerfilesCalendarItem>();
		PermisosPerfilesCalendarDTO permisosPerfilesCalendarioDTO = new PermisosPerfilesCalendarDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			LOGGER.info(
					"getProfilesPermissions() / agePermisosCalendarioExtendsMapper.getPermisosProfiles() -> Entrada a agePermisosCalendarioExtendsMapper para obtener los permisos de los perfiles");
			profilesCalendar = agePermisosCalendarioExtendsMapper.getProfilesPermissions(idCalendario,
					idInstitucion.toString());
			LOGGER.info(
					"getProfilesPermissions() / agePermisosCalendarioExtendsMapper.getPermisosProfiles() -> Salida de agePermisosCalendarioExtendsMapper para obtener los permisos de los perfiles");

			permisosPerfilesCalendarioDTO.setPermisosPerfilesCalendar(profilesCalendar);

		}

		LOGGER.info(
				"getPermisosProfiles() -> Salida del servicio para obtener los permisos de calendario de todos los perfiles");

		return permisosPerfilesCalendarioDTO;
	}

}
