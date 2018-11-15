package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
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
	private ForCursoExtendsMapper forCursoExtendsMapper;
	
	@Autowired
	private ForPersonacursoExtendsMapper forPersonacursoExtendsMapper;

	@Autowired
	private ForRolesExtendsMapper forRolesExtendsMapper;

	@Autowired
	private ForTipocosteExtendsMapper forTipocosteExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Override
	public void updateEstadoCursoAuto() {
		LOGGER.info(
				"updateEstadoCursoAuto()  -> Entrada al servicio para actualizar automáticamente los cursos que correspondan");

		// Este método se encargará de actualizar el estado de los cursos cuando
		// corresponda de manera automática (Scheduled)
		
		ForCurso forCursoFiltroFechaIni = new ForCurso();
		ForCurso forCursoFiltroFechaFin = new ForCurso();
		
		LOGGER.info(
				"updateEstadoCursoAuto() / forCursoExtendsMapper.selectCursosFechaAuto() -> Entrada a forCursoExtendsMapper para obtener un listado con cursos");
		// FechaInicio = FechaActual --> Se cambiará el estado a "En curso"
		// Recogemos la lista de cursos cuya fechaInicioImpartición sea igual que la fecha actual
		forCursoFiltroFechaIni.setFechaimparticiondesde(new Date());
		List<ForCurso> listaCursosFechaIni = forCursoExtendsMapper.selectCursosFechaAuto(forCursoFiltroFechaIni);
		
		LOGGER.info(
				"updateEstadoCursoAuto() / forCursoExtendsMapper.selectCursosFechaAuto() -> Entrada a forCursoExtendsMapper para obtener un listado con cursos");
		// FechaFin = FechaActual-1 --> Se cambiará el estado a "Impartido"
		// Recogemos la lista de cursos cuya fechaFinImpartición sea menor que la fecha actual, 
		// ya que se tiene que cambiar el estado el día después de su fechaFinImpartición
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		forCursoFiltroFechaFin.setFechaimparticionhasta(calendar.getTime());
		List<ForCurso> listaCursosFechaFin = forCursoExtendsMapper.selectCursosFechaAuto(forCursoFiltroFechaFin);
		
		// TODO Analizar qué hacer en caso de error (int == 0)
		int correctoEnCurso = 0;
		int correctoImpartido = 0;
		
		LOGGER.info(
				"updateEstadoCursoAuto() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper para actualizar el curso");
		// Recorremos cada lista y haremos el update del estado que corresponda en cada caso
		for (ForCurso forCurso : listaCursosFechaIni) {
			forCurso.setIdestadocurso(Long.parseLong(SigaConstants.ESTADO_CURSO_EN_CURSO));
			forCurso.setFechamodificacion(new Date());
			correctoEnCurso = forCursoExtendsMapper.updateByPrimaryKeySelective(forCurso);
		}
		
		for (ForCurso forCurso : listaCursosFechaFin) {
			forCurso.setIdestadocurso(Long.parseLong(SigaConstants.ESTADO_CURSO_IMPARTIDO));
			forCurso.setFechamodificacion(new Date());
			correctoImpartido = forCursoExtendsMapper.updateByPrimaryKeySelective(forCurso);
		}
	}
	
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

				formadoresCursoDTO.setFormadoresCursoItem(formadoresCursoItem);
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

				for (FormadorCursoItem formadorCursoItem : formadorCursoDTO.getFormadoresCursoItem()) {

					ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
					exampleFormador.createCriteria().andIdpersonaEqualTo(formadorCursoItem.getIdPersona())
							.andIdcursoEqualTo(formadorCursoItem.getIdCurso())
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
							.andIdrolEqualTo(Short.valueOf(formadorCursoItem.getIdRol()));

					LOGGER.info(
							"updateTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar el formador existente");

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
						formador.setTutor(formadorCursoItem.getTutor());
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

		int responseCenPersona = 1;
		int responseForPersonaCurso = 1;
		int responseCenCliente = 1;
		int responseTutor = 1;
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
				Long idPersona = null;

				ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
				exampleFormador.createCriteria().andIdpersonaEqualTo(formadorCursoItem.getIdPersona())
						.andIdrolEqualTo(Short.valueOf(formadorCursoItem.getIdRol()))
						.andIdcursoEqualTo(formadorCursoItem.getIdCurso())
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNull();

				LOGGER.info(
						"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para obtener los formadores de un curso");
				List<ForPersonaCurso> formadores = forPersonacursoExtendsMapper.selectByExample(exampleFormador);
				LOGGER.info(
						"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida de forPersonacursoExtendsMapper para obtener los formadores de un curso");

				if (null == formadores || formadores.size() == 0) {

					if (formadorCursoItem.getIdPersona() == null) {

						CenPersona nuevaPersona = new CenPersona();
						List<ComboItem> comboItems = new ArrayList<ComboItem>();
						comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
						idPersona = Long.valueOf(comboItems.get(0).getValue()) + 1;

						nuevaPersona.setApellidos1(formadorCursoItem.getApellidos());
						nuevaPersona.setApellidos2(null);
						nuevaPersona.setFallecido("0");
						nuevaPersona.setFechamodificacion(new Date());
						nuevaPersona.setFechanacimiento(null);
						nuevaPersona.setIdestadocivil(null);
						nuevaPersona.setIdpersona(idPersona);
						nuevaPersona.setIdtipoidentificacion(Short.valueOf(formadorCursoItem.getTipoIdentificacion()));
						nuevaPersona.setNaturalde(null);
						nuevaPersona.setNifcif(formadorCursoItem.getNif());
						nuevaPersona.setNombre(formadorCursoItem.getNombre());
						nuevaPersona.setSexo(null);
						nuevaPersona.setUsumodificacion(usuario.getIdusuario());

						try {
							LOGGER.info(
									"saveTrainersCourse() / cenPersonaExtendsMapper.insert(nuevaPersona) -> Entrada a cenPersonaExtendsMapper para insertar en la tabla cen_persona al nuevo formador");

							responseCenPersona = cenPersonaExtendsMapper.insert(nuevaPersona);

							LOGGER.info(
									"saveTrainersCourse() / cenPersonaExtendsMapper.insert(nuevaPersona) -> Salida a cenPersonaExtendsMapper para insertar en la tabla cen_persona al nuevo formador");
						} catch (Exception e) {
							error.setMessage("Error al insertar al nuevo formador en la tabla cen_persona");
						}

						CenCliente recordCliente = new CenCliente();
						recordCliente = rellenarInsertCenCliente(usuario, idPersona, idInstitucion);
						responseCenCliente = cenClienteMapper.insertSelective(recordCliente);

					} else {
						idPersona = formadorCursoItem.getIdPersona();
					}

					if (responseCenPersona == 0) {
						error.setCode(400);
						error.setDescription("Error al insertar al nuevo formador en la tabla cen_persona");

					} else if (responseCenCliente == 0) {
						error.setCode(400);
						error.setDescription("Error al insertar al nuevo formador en la tabla cen_cliente");
					} else {

						if (formadorCursoItem.getTutor() == ASIGNAR_TUTOR) {
							ForPersonaCursoExample exampleTutor = new ForPersonaCursoExample();
							exampleTutor.createCriteria()
							.andTutorEqualTo(formadorCursoItem.getTutor())
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNull()
							.andIdcursoEqualTo(formadorCursoItem.getIdCurso());

							LOGGER.info(
									"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar si existe algun formador como tutor");

							List<ForPersonaCurso> tutorList = forPersonacursoExtendsMapper
									.selectByExample(exampleTutor);

							LOGGER.info(
									"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida a forPersonacursoExtendsMapper para buscar si existe algun formador como tutor");

							if (null != tutorList && tutorList.size() > 0) {
								ForPersonaCurso tutor = tutorList.get(0);

								LOGGER.info(
										"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(tutor) -> Entrada a forPersonacursoExtendsMapper para designar el tutor");

								tutor.setFechamodificacion(new Date());
								tutor.setUsumodificacion(usuario.getIdusuario().longValue());
								tutor.setTutor(DESIGNAR_TUTOR);
								responseTutor = forPersonacursoExtendsMapper.updateByPrimaryKey(tutor);

								LOGGER.info(
										"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(tutor) -> Salida a forPersonacursoExtendsMapper para designar el tutor");
							}
						}

						if (responseTutor == 0) {
							error.setCode(400);
							error.setDescription("Error al designar un tutor a un formador");

						} else {

							ForPersonaCurso forPersonaCursoInsert = new ForPersonaCurso();
							forPersonaCursoInsert.setIdpersona(idPersona);
							forPersonaCursoInsert.setIdcurso(formadorCursoItem.getIdCurso());
							forPersonaCursoInsert.setIdrol(Short.valueOf(formadorCursoItem.getIdRol()));
							forPersonaCursoInsert.setIdinstitucion(idInstitucion);
							forPersonaCursoInsert.setIdtipocoste(Short.valueOf(formadorCursoItem.getIdTipoCoste()));
							forPersonaCursoInsert.setTarifa(formadorCursoItem.getTarifa().longValue());
							forPersonaCursoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
							forPersonaCursoInsert.setFechamodificacion(new Date());
							forPersonaCursoInsert.setFechabaja(null);
							forPersonaCursoInsert.setTutor(formadorCursoItem.getTutor());

							LOGGER.info(
									"saveTrainersCourse() / forPersonacursoExtendsMapper.insert(forPersonaCursoInsert) -> Entrada a ageNotificacioneseventoMapper para insertar un formador");
							responseForPersonaCurso = forPersonacursoExtendsMapper.insert(forPersonaCursoInsert);
							LOGGER.info(
									"saveTrainersCourse() / forPersonacursoExtendsMapper.insert(forPersonaCursoInsert) -> Salida a ageNotificacioneseventoMapper para insertar un formador");

							if (responseForPersonaCurso == 0) {
								error.setCode(400);
								error.setDescription("Error al insertar nuevo formador");
							} else {
								error.setCode(200);
							}
						}

					}

				} else {
					error.setMessage("Ya existe el formador añadido con ese rol");
				}
			}
		}

		LOGGER.info("saveTrainersCourse() -> Entrada al servicio para insertar un nuevo formador");

		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	protected CenCliente rellenarInsertCenCliente(AdmUsuarios usuario, Long maxIdPersona, Short idInstitucion) {
		CenCliente record = new CenCliente();

		record.setIdpersona(maxIdPersona);
		record.setIdinstitucion(Short.valueOf(idInstitucion));
		record.setFechaalta(new Date());
		record.setCaracter("P");
		record.setPublicidad(SigaConstants.DB_FALSE);
		record.setGuiajudicial(SigaConstants.DB_FALSE);
		record.setComisiones(SigaConstants.DB_FALSE);
		record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdlenguaje(usuario.getIdlenguaje());
		record.setExportarfoto(SigaConstants.DB_FALSE);

		return record;
	}

	@Override
	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {

		LOGGER.info("deleteTrainersCourse() -> Salida del servicio para dar de baja a los formadores de un curso");

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
					"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				for (FormadorCursoItem formador : formadorCursoDTO.getFormadoresCursoItem()) {

					ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
					exampleFormador.createCriteria().andIdpersonaEqualTo(formador.getIdPersona())
							.andIdrolEqualTo(Short.valueOf(formador.getIdRol()))
							.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

					LOGGER.info(
							"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a forPersonacursoExtendsMapper para obtener los formadores de un curso");
					List<ForPersonaCurso> formadoresList = forPersonacursoExtendsMapper
							.selectByExample(exampleFormador);
					LOGGER.info(
							"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida a forPersonacursoExtendsMapper para obtener los formadores de un curso");

					if (null != formadoresList && formadoresList.size() > 0) {
						ForPersonaCurso formadorDelete = formadoresList.get(0);

						LOGGER.info(
								"deleteTrainersCourse() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Entrada a ageCalendarioExtendsMapper para dar de baja a la notificacion");

						formadorDelete.setFechamodificacion(new Date());
						formadorDelete.setUsumodificacion(usuario.getIdusuario().longValue());
						formadorDelete.setFechabaja(new Date());
						forPersonacursoExtendsMapper.updateByPrimaryKey(formadorDelete);

						LOGGER.info(
								"deleteTrainersCourse() / ageNotificacioneseventoMapper.updateByPrimaryKey(notification) -> Salida a ageCalendarioExtendsMapper para dar de baja a notificacion");

					}
				}
			}
		}

		LOGGER.info("deleteTrainersCourse() -> Salida del servicio para dar de baja a los formadores de un curso");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

}
