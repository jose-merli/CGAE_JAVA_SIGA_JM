package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.entities.ForEventoCurso;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.ForEventoCursoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForRolesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTipocosteExtendsMapper;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private ForEventoCursoMapper forEventoCursoMapper;

	@Autowired
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

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
		// Recogemos la lista de cursos cuya fechaInicioImpartición sea igual que la
		// fecha actual
		forCursoFiltroFechaIni.setFechaimparticiondesde(new Date());
		List<ForCurso> listaCursosFechaIni = forCursoExtendsMapper.selectCursosFechaAuto(forCursoFiltroFechaIni);

		LOGGER.info(
				"updateEstadoCursoAuto() / forCursoExtendsMapper.selectCursosFechaAuto() -> Entrada a forCursoExtendsMapper para obtener un listado con cursos");
		// FechaFin = FechaActual-1 --> Se cambiará el estado a "Impartido"
		// Recogemos la lista de cursos cuya fechaFinImpartición sea menor que la fecha
		// actual,
		// ya que se tiene que cambiar el estado el día después de su
		// fechaFinImpartición
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		forCursoFiltroFechaFin.setFechaimparticionhasta(calendar.getTime());
		List<ForCurso> listaCursosFechaFin = forCursoExtendsMapper.selectCursosFechaAuto(forCursoFiltroFechaFin);

		// TODO Analizar qué hacer en caso de error (int == 0)
		int correctoEnCurso = 0;
		int correctoImpartido = 0;

		LOGGER.info(
				"updateEstadoCursoAuto() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper para actualizar el curso");
		// Recorremos cada lista y haremos el update del estado que corresponda en cada
		// caso
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

						try {

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
							nuevaPersona
									.setIdtipoidentificacion(Short.valueOf(formadorCursoItem.getTipoIdentificacion()));
							nuevaPersona.setNaturalde(null);
							nuevaPersona.setNifcif(formadorCursoItem.getNif());
							nuevaPersona.setNombre(formadorCursoItem.getNombre());
							nuevaPersona.setSexo(null);
							nuevaPersona.setUsumodificacion(usuario.getIdusuario());

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
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

					} else if (responseCenCliente == 0) {
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					} else {

						if (formadorCursoItem.getTutor() == ASIGNAR_TUTOR) {
							ForPersonaCursoExample exampleTutor = new ForPersonaCursoExample();
							exampleTutor.createCriteria().andTutorEqualTo(formadorCursoItem.getTutor())
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
							error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

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
								error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
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
	@Transactional
	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {

		LOGGER.info("deleteTrainersCourse() -> Salida del servicio para dar de baja a los formadores de un curso");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

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

				try {

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

		LOGGER.info("deleteTrainersCourse() -> Salida del servicio para dar de baja a los formadores de un curso");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO saveCourse(CursoItem cursoItem, HttpServletRequest request) {

		LOGGER.info("saveCourse() -> Entrada al servicio para insertar un curso");

		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ForCurso forCursoInsert = new ForCurso();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					forCursoInsert.setIdinstitucion(idInstitucion);
					forCursoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
					forCursoInsert.setFechamodificacion(new Date());
					forCursoInsert.setNombrecurso(cursoItem.getNombreCurso());
					forCursoInsert.setCodigocurso(cursoItem.getCodigoCurso());
					forCursoInsert.setIdvisibilidadcurso(Short.valueOf(cursoItem.getIdVisibilidad()));
					forCursoInsert.setDescripcion(cursoItem.getDescripcionEstado());
					forCursoInsert.setFechainscripciondesde(cursoItem.getFechaInscripcionDesdeDate());
					forCursoInsert.setFechainscripcionhasta(cursoItem.getFechaInscripcionHastaDate());
					forCursoInsert.setIdestadocurso(Long.valueOf(cursoItem.getIdEstado()));
					forCursoInsert.setNumeroplazas(Long.valueOf(cursoItem.getPlazasDisponibles()));
					forCursoInsert.setMinimoasistencia(Long.valueOf(cursoItem.getMinimoAsistencia()));
					forCursoInsert.setFlagarchivado(Short.valueOf("0"));
					forCursoInsert.setLugar(cursoItem.getLugar());

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Entrada a forCursoExtendsMapper para insertar un curso");
					response = forCursoExtendsMapper.insert(forCursoInsert);
					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Salida a forCursoExtendsMapper para insertar un curso");

					// Si existe idEventoInscripcion se debe guardar la relacion entre el evento y
					// el curso
					if (cursoItem.getIdEventoInicioInscripcion() != null) {

						ForEventoCurso forEventoCurso = new ForEventoCurso();
						forEventoCurso.setFechabaja(null);
						forEventoCurso.setFechamodificacion(new Date());
						forEventoCurso.setIdcurso(forCursoInsert.getIdcurso());
						forEventoCurso.setIdevento(Long.valueOf(cursoItem.getIdEventoInicioInscripcion()));
						forEventoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
						forEventoCurso.setIdinstitucion(idInstitucion);

						response = forEventoCursoMapper.insert(forEventoCurso);

						// Se da de alta al evento creado para ese curso

						AgeEventoExample exampleEvento = new AgeEventoExample();
						exampleEvento.createCriteria()
								.andIdeventoEqualTo(Long.valueOf(cursoItem.getIdEventoInicioInscripcion()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNotNull();

						LOGGER.info(
								"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

						List<AgeEvento> eventoList = ageEventoExtendsMapper.selectByExample(exampleEvento);

						LOGGER.info(
								"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

						if (null != eventoList && eventoList.size() > 0) {
							AgeEvento evento = eventoList.get(0);

							evento.setIdevento(Long.valueOf(cursoItem.getIdEventoInicioInscripcion()));
							evento.setFechabaja(null);
							evento.setFechamodificacion(new Date());
							evento.setUsumodificacion(usuario.getIdusuario().longValue());

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

							response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

						}

					}

					// Si existe idEventoInscripcion se debe guardar la relacion entre el evento y
					// el curso
					if (cursoItem.getIdEventoFinInscripcion() != null) {

						ForEventoCurso forEventoCurso = new ForEventoCurso();
						forEventoCurso.setFechabaja(null);
						forEventoCurso.setFechamodificacion(new Date());
						forEventoCurso.setIdcurso(forCursoInsert.getIdcurso());
						forEventoCurso.setIdevento(Long.valueOf(cursoItem.getIdEventoFinInscripcion()));
						forEventoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
						forEventoCurso.setIdinstitucion(idInstitucion);

						response = forEventoCursoMapper.insert(forEventoCurso);

						// Se da de alta al evento creado para ese curso

						AgeEventoExample exampleEvento = new AgeEventoExample();
						exampleEvento.createCriteria()
								.andIdeventoEqualTo(Long.valueOf(cursoItem.getIdEventoFinInscripcion()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNotNull();

						LOGGER.info(
								"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

						List<AgeEvento> eventoList = ageEventoExtendsMapper.selectByExample(exampleEvento);

						LOGGER.info(
								"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

						if (null != eventoList && eventoList.size() > 0) {
							AgeEvento evento = eventoList.get(0);

							evento.setIdevento(Long.valueOf(cursoItem.getIdEventoFinInscripcion()));
							evento.setFechabaja(null);
							evento.setFechamodificacion(new Date());
							evento.setUsumodificacion(usuario.getIdusuario().longValue());

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

							response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

						}

					}

				} catch (Exception e) {
					response = 0;
				}
				if (response == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				} else {
					error.setCode(200);

					insertResponseDTO.setId(Long.toString(forCursoInsert.getIdcurso()));
					insertResponseDTO.setError(error);
				}
			}
		}
		LOGGER.info("saveCourse() -> Salida del servicio para insertar un curso");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateCourse(CursoItem cursoItem, HttpServletRequest request) {
		LOGGER.info("updateCourse() -> Entrada al servicio para modificar los eventos");

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
					"updateCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para modificar un curso");

				response = forCursoExtendsMapper.updateCourse(cursoItem, usuario);

				LOGGER.info(
						"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para modificar un evento");

				if (response == 0) {
					error.setCode(400);
					error.setDescription("Error al modificar evento");
				} else {
					error.setCode(200);
					updateResponseDTO.setId(cursoItem.getIdCurso().toString());
				}
			}
		}

		LOGGER.info("updateCourse() -> Salida del servicio para modificar los eventos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO releaseOrAnnounceCourse(CursoDTO cursoDTO, HttpServletRequest request) {
		LOGGER.info("releaseOrAnnounceCourse() -> Entrada al servicio para modificar los eventos");

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
					"releaseOrAnnounceCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"releaseOrAnnounceCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					for (CursoItem curso : cursoDTO.getCursoItem()) {

						ForCursoExample exampleCourse = new ForCursoExample();
						exampleCourse.createCriteria().andIdcursoEqualTo(Long.valueOf(curso.getIdCurso()))
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"releaseOrAnnounceCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Entrada a forCursoExtendsMapper para buscar si existe el curso");

						List<ForCurso> courseList = forCursoExtendsMapper.selectByExample(exampleCourse);

						LOGGER.info(
								"releaseOrAnnounceCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Salida a forCursoExtendsMapper para buscar si existe el curso");

						if (null != courseList && courseList.size() > 0) {
							ForCurso course = courseList.get(0);

							if (course.getIdestadocurso() == ABIERTO_CURSO
									|| course.getIdestadocurso() == ANUNCIADO_CURSO) {

								LOGGER.info(
										"releaseOrAnnounceCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para cambiar el estado de un curso");

								course.setFechamodificacion(new Date());
								course.setUsumodificacion(usuario.getIdusuario().longValue());
								course.setIdestadocurso(Long.valueOf(curso.getIdEstado()));
								response = forCursoExtendsMapper.updateByPrimaryKey(course);

								LOGGER.info(
										"releaseOrAnnounceCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para cambiar el estado de un curso");

							}
						}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response != 0) {
					error.setCode(200);
				}
			}
		}

		LOGGER.info(
				"releaseOrAnnounceCoursereleaseOrAnnounceCourse() -> Salida del servicio para anunciar o desanunciar los eventos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public CursoItem searchCourse(String idCurso, HttpServletRequest request) {
		LOGGER.info("searchCourse() -> Entrada al servicio para obtener un evento especifico");

		CursoItem cursoItem = new CursoItem();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"searchCourse() / ageEventoExtendsMapper.searchEvent() -> Entrada a ageEventoExtendsMapper para obtener un evento especifico");
			cursoItem = forCursoExtendsMapper.searchCourseByIdcurso(idCurso, idInstitucion);
			LOGGER.info(
					"searchCourse() / ageEventoExtendsMapper.searchEvent() -> Salida de ageEventoExtendsMapper para obtener un evento especifico");

		}

		LOGGER.info("searchCourse() -> Salida del servicio para obtener un evento especifico");

		return cursoItem;
	}

	@Override
	public EventoDTO getSessionsCourse(CursoItem cursoItem, HttpServletRequest request) {
		LOGGER.info("getSessionsCourse() -> Entrada al servicio para obtener un evento especifico");

		List<EventoItem> eventoList = new ArrayList<EventoItem>();
		EventoDTO eventoDTO = new EventoDTO();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getSessionsCourse() / ageEventoExtendsMapper.searchEvent() -> Entrada a ageEventoExtendsMapper para obtener un evento especifico");
			eventoList = ageEventoExtendsMapper.getSessionsCourse(cursoItem.getIdTipoEvento().toString(), cursoItem.getIdCurso().toString(), idInstitucion.toString());
			LOGGER.info(
					"getSessionsCourse() / ageEventoExtendsMapper.searchEvent() -> Salida de ageEventoExtendsMapper para obtener un evento especifico");

		}

		LOGGER.info("getSessionsCourse() -> Salida del servicio para obtener un evento especifico");

		eventoDTO.setEventos(eventoList);
		return eventoDTO;
	}

}
