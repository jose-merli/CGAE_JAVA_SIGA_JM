package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForRolesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTipocosteExtendsMapper;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaCursosServiceImpl implements IFichaCursosService {

	private Logger LOGGER = Logger.getLogger(FichaCursosServiceImpl.class);

	@Autowired
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;

	@Autowired
	private ForRolesExtendsMapper forRolesExtendsMapper;

	@Autowired
	private ForTipocosteExtendsMapper forTipocosteExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public FormadorCursoDTO getTrainersCourse(String idCurso, HttpServletRequest request) {
		LOGGER.info("getTrainersCourse() -> Entrada al servicio para obtener los formadores de un curso especifico");

		FormadorCursoDTO formadoresCursoDTO = new FormadorCursoDTO();
		List<FormadorCursoItem> formadoresCursoItem = new ArrayList<FormadorCursoItem>();

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
				LOGGER.info(
						"getTrainersCourse() / forPersonacursoExtendsMapper.getTrainers(idInstitucion, idCurso) -> Entrada a forPersonacursoExtendsMapper para obtener los formadores de un curso especifico");
				formadoresCursoItem = forPersonacursoExtendsMapper.getTrainersCourse(idInstitucion.toString(), idCurso,
						usuario.getIdlenguaje());
				LOGGER.info(
						"getTrainersCourse() / forPersonacursoExtendsMapper.getTrainers(idInstitucion, idCurso) -> Salida de forPersonacursoExtendsMapper para obtener los formadores de un curso especifico");

				formadoresCursoDTO.setFormadorCursoItem(formadoresCursoItem);
			}
		}

		LOGGER.info("getTrainersCourse() -> Salida del servicio para obtener los formadores de un curso especifico");

		return formadoresCursoDTO;
	}

	@Override
	public ComboDTO getRolesTrainers(HttpServletRequest request) {

		LOGGER.info("getRolesTrainers() -> Entrada al servicio para obtener los roles de los formadores");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getRolesTrainers() / forRolesExtendsMapper.getRolesTrainers -> Entrada a forRolesExtendsMapper para obtener los roles");
			comboItems = forRolesExtendsMapper.getRolesTrainers(idInstitucion.toString());
			LOGGER.info(
					"getRolesTrainers() / forRolesExtendsMapper.getRolesTrainers -> Salida de forRolesExtendsMapper para obtener los roles");

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getRolesTrainers() -> Salida del servicio para obtener los roles de los formadores");

		return comboDTO;
	}

	@Override
	public ComboDTO getTypeCostTrainers(HttpServletRequest request) {

		LOGGER.info("getTypeCostTrainers() -> Entrada al servicio para obtener los tipos de costes de los formadores");

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
					"getTypeCostTrainers() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTypeCostTrainers() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTypeCostTrainers() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Entrada a forVisibilidadcursolExtendsMapper para obtener los tipos de costes de los formadores");
				comboItems = forTipocosteExtendsMapper.getTypeCostTrainers(usuario.getIdlenguaje());
				LOGGER.info(
						"getTypeCostTrainers() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Salida de forVisibilidadcursolExtendsMapper para obtener los tipos de costes de los formadores");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTypeCostTrainers() -> Salida del servicio para obtener los tipos de costes de los formadores");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {

		LOGGER.info("updateTrainersCourse() -> Entrada al servicio para editar a los formadores");

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
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateNotification() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (FormadorCursoItem formadorCursoItem : formadorCursoDTO.getFormadorCursoItem()) {

					ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
					exampleFormador.createCriteria().andIdpersonaEqualTo(formadorCursoItem.getIdPersona())
							.andIdcursoEqualTo(formadorCursoItem.getIdCurso())
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

					LOGGER.info(
							"updateTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar el formador existente");

					boolean prueba = forPersonacursoExtendsMapper.equals(exampleFormador); 
					List<ForPersonaCurso> formadoresList = forPersonacursoExtendsMapper
							.selectByExample(exampleFormador);

					LOGGER.info(
							"updateTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida a forPersonacursoExtendsMapper para buscar el formador existente");

					if (null != formadoresList && formadoresList.size() > 0) {
						ForPersonaCurso formador = formadoresList.get(0);

						LOGGER.info(
								"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(formador) -> Entrada a forPersonacursoExtendsMapper para modificar al formador");

						formador.setFechamodificacion(new Date());
						formador.setUsumodificacion(usuario.getIdusuario().longValue());
						formador.setIdrol(Short.valueOf(formadorCursoItem.getIdRol()));
						formador.setIdtipocoste(Short.valueOf(formadorCursoItem.getIdTipoCoste()));
						formador.setTarifa(formadorCursoItem.getTarifa().longValue());
						response = forPersonacursoExtendsMapper.updateByPrimaryKey(formador);

						LOGGER.info(
								"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(formador) -> Salida a forPersonacursoExtendsMapper para modificar al formador");

					}

					if (response == 0) {
						error.setCode(400);
						error.setDescription("Error al modificar el formador");
					} else {
						error.setCode(200);
					}
				}
			}
		}

		LOGGER.info("updateTrainersCourse() -> Entrada al servicio para editar a los formadores");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO saveTrainersCourse(FormadorCursoItem formadorCursoItem, HttpServletRequest request) {

		LOGGER.info("saveTrainersCourse() -> Entrada al servicio para para insertar un nuevo formador");

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
					"saveTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				ForPersonaCurso forPersonaCursoInsert = new ForPersonaCurso();
				forPersonaCursoInsert.setIdpersona(formadorCursoItem.getIdPersona());
				forPersonaCursoInsert.setIdcurso(formadorCursoItem.getIdCurso());
				forPersonaCursoInsert.setIdrol(Short.valueOf(formadorCursoItem.getIdRol()));
				forPersonaCursoInsert.setIdinstitucion(idInstitucion);
				forPersonaCursoInsert.setIdtipocoste(Short.valueOf(formadorCursoItem.getIdTipoCoste()));
				forPersonaCursoInsert.setTarifa(formadorCursoItem.getTarifa().longValue());
				forPersonaCursoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
				forPersonaCursoInsert.setFechamodificacion(new Date());

				LOGGER.info(
						"saveTrainersCourse() / forPersonacursoExtendsMapper.insert(forPersonaCursoInsert) -> Entrada a ageNotificacioneseventoMapper para insertar un formador");
				response = forPersonacursoExtendsMapper.insert(forPersonaCursoInsert);
				LOGGER.info(
						"saveTrainersCourse() / forPersonacursoExtendsMapper.insert(forPersonaCursoInsert) -> Salida a ageNotificacioneseventoMapper para insertar un formador");

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al insertar nuevo formador");
				} else {
					error.setCode(200);
				}
			}
		}

		LOGGER.info("saveTrainersCourse() -> Entrada al servicio para insertar un nuevo formador");

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

}
