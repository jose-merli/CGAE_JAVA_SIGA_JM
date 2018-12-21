package org.itcgae.siga.form.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;
import org.itcgae.siga.DTOs.cen.CargaMasivaDatosGFItem;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.entities.ForEventoCurso;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.entities.ForInscripcionesmasivas;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.entities.ForTiposervicioCurso;
import org.itcgae.siga.db.entities.ForTiposervicioCursoExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.ForEventoCursoMapper;
import org.itcgae.siga.db.mappers.ForInscripcionesmasivasMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForRolesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTipocosteExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTiposervicioCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTiposervicioExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysFormapagoExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

	@Autowired
	private ForTiposervicioExtendsMapper forTiposervicioExtendsMapper;

	@Autowired
	private ForTiposervicioCursoExtendsMapper forTiposervicioCursoExtendsMapper;

	@Autowired
	private ForInscripcionExtendsMapper forInscripcionExtendsMapper;

	@Autowired
	private PysFormapagoExtendsMapper pysFormapagoExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private ForInscripcionesmasivasMapper forInscripcionesmasivasMapper;

	@Autowired
	private IFicherosService ficherosService;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

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
	@Transactional
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

				try {

					for (FormadorCursoItem formadorCursoItem : formadorCursoDTO.getFormadoresCursoItem()) {

						ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
						exampleFormador.createCriteria().andIdpersonaEqualTo(formadorCursoItem.getIdPersona())
								.andIdcursoEqualTo(formadorCursoItem.getIdCurso())
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

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
							if (formadorCursoItem.getIdRol() == null) {
								formador.setIdrol(null);
							} else {
								formador.setIdrol(Short.valueOf(formadorCursoItem.getIdRol()));
							}
							formador.setIdtipocoste(Short.valueOf(formadorCursoItem.getIdTipoCoste()));
							formador.setTarifa(formadorCursoItem.getTarifa().longValue());
							formador.setTutor(formadorCursoItem.getTutor());
							response = forPersonacursoExtendsMapper.updateByPrimaryKey(formador);

							LOGGER.info(
									"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(formador) -> Salida a forPersonacursoExtendsMapper para modificar al formador");

						} else {

						}

						if (response == 0) {
							error.setCode(400);
							error.setDescription("Error al modificar el formador");
						} else {
							error.setCode(200);
						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}
			}
		}

		LOGGER.info("updateTrainersCourse() -> Entrada al servicio para editar a los formadores");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO saveTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {

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

				FormadorCursoItem formadorCursoItem = formadorCursoDTO.getFormadoresCursoItem().get(0);

				// No esta registrado en la tabla cen_persona...
				if (formadorCursoItem.getIdPersona() == null) {
					// Lo añadimos a la tabla cen_persona
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
						nuevaPersona.setIdtipoidentificacion(Short.valueOf(formadorCursoItem.getTipoIdentificacion()));
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

					// Si no existe error lo añadimos a la tabla cen_cliente
					CenCliente recordCliente = new CenCliente();
					recordCliente = rellenarInsertCenCliente(usuario, idPersona, idInstitucion);
					responseCenCliente = cenClienteMapper.insertSelective(recordCliente);

					// Si esta registrado en la tabla cen_persona obtenemos su idPersona
				} else {
					idPersona = formadorCursoItem.getIdPersona();
				}

				if (responseCenPersona == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

				} else if (responseCenCliente == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

					// Añadimos al formador
				} else {
					// Si es tutor, buscamos si existe otro formador asignado como formador y se lo
					// desasignamos porque
					// solamente puede existir un formador
					if (formadorCursoItem.getTutor() == ASIGNAR_TUTOR) {
						ForPersonaCursoExample exampleTutor = new ForPersonaCursoExample();
						exampleTutor.createCriteria().andTutorEqualTo(formadorCursoItem.getTutor())
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNull()
								.andIdcursoEqualTo(formadorCursoItem.getIdCurso());

						LOGGER.info(
								"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar si existe algun formador como tutor");

						List<ForPersonaCurso> tutorList = forPersonacursoExtendsMapper.selectByExample(exampleTutor);

						LOGGER.info(
								"saveTrainersCourse() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida a forPersonacursoExtendsMapper para buscar si existe algun formador como tutor");

						if (null != tutorList && tutorList.size() > 0) {
							ForPersonaCurso tutor = tutorList.get(0);

							LOGGER.info(
									"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(tutor) -> Entrada a forPersonacursoExtendsMapper para designar el tutor");

							tutor.setFechamodificacion(new Date());
							tutor.setUsumodificacion(usuario.getIdusuario().longValue());
							tutor.setTutor(DESIGNAR_TUTOR);
							tutor.setIdrol(null);
							responseTutor = forPersonacursoExtendsMapper.updateByPrimaryKey(tutor);

							LOGGER.info(
									"updateTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(tutor) -> Salida a forPersonacursoExtendsMapper para designar el tutor");
						}
					}

					// Si existía tutor se modifica y si no ha habido error...
					if (responseTutor == 0) {
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

						// Guardamos al formador
					} else {

						ForPersonaCurso forPersonaCursoInsert = new ForPersonaCurso();
						forPersonaCursoInsert.setIdpersona(idPersona);
						forPersonaCursoInsert.setIdcurso(formadorCursoItem.getIdCurso());
						if (formadorCursoItem.getIdRol() == null) {
							forPersonaCursoInsert.setIdrol(null);
						} else {
							forPersonaCursoInsert.setIdrol(Short.valueOf(formadorCursoItem.getIdRol()));
						}
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
						exampleFormador.createCriteria().andIdformadorEqualTo(Long.valueOf(formador.getIdFormador()));

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
					// Insertamos curso en la tabla For_Curso
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
					forCursoInsert.setAutovalidacioninscripcion(cursoItem.getAutovalidacionInscripcion().shortValue());

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Entrada a forCursoExtendsMapper para insertar un curso");
					response = forCursoExtendsMapper.insert(forCursoInsert);
					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Salida a forCursoExtendsMapper para insertar un curso");

					// Si existe tiposervicio, se guarda en la tabla TipoServicios
					if (cursoItem.getTipoServicios() != null && cursoItem.getTipoServicios().size() > 0) {

						for (ComboItem servicio : cursoItem.getTipoServicios()) {

							ForTiposervicioCurso forTipoServicioCurso = new ForTiposervicioCurso();
							forTipoServicioCurso.setFechabaja(null);
							forTipoServicioCurso.setFechamodificacion(new Date());
							forTipoServicioCurso.setIdcurso(forCursoInsert.getIdcurso());
							forTipoServicioCurso.setIdinstitucion(idInstitucion);
							forTipoServicioCurso.setIdttiposervicio(Long.valueOf(servicio.getValue()));
							forTipoServicioCurso.setUsumodificacion(usuario.getIdusuario().longValue());

							response = forTiposervicioCursoExtendsMapper.insert(forTipoServicioCurso);

						}
					}

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
	@Transactional
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

				try {
					LOGGER.info(
							"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para modificar un curso");

					response = forCursoExtendsMapper.updateCourse(cursoItem, usuario);

					LOGGER.info(
							"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para modificar un evento");

					if (cursoItem.getTipoServicios() != null && cursoItem.getTipoServicios().size() > 0) {

						// Añadimos Servicio
						for (ComboItem servicio : cursoItem.getTipoServicios()) {

							// Para cada servicio comprobamos si ya existe la relacion
							ForTiposervicioCursoExample forTiposervicioCursoExample = new ForTiposervicioCursoExample();
							forTiposervicioCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdcursoEqualTo(cursoItem.getIdCurso())
									.andIdttiposervicioEqualTo(Long.valueOf(servicio.getValue()));

							List<ForTiposervicioCurso> forTipoServicioCursoList = forTiposervicioCursoExtendsMapper
									.selectByExample(forTiposervicioCursoExample);

							// Si no existe la creamos
							if (forTipoServicioCursoList.isEmpty()) {

								ForTiposervicioCurso forTipoServicioCurso = new ForTiposervicioCurso();
								forTipoServicioCurso.setFechabaja(null);
								forTipoServicioCurso.setFechamodificacion(new Date());
								forTipoServicioCurso.setIdcurso(cursoItem.getIdCurso());
								forTipoServicioCurso.setIdinstitucion(idInstitucion);
								forTipoServicioCurso.setIdttiposervicio(Long.valueOf(servicio.getValue()));
								forTipoServicioCurso.setUsumodificacion(usuario.getIdusuario().longValue());

								response = forTiposervicioCursoExtendsMapper.insert(forTipoServicioCurso);

								// Si existe cambiamos el servicio a null
							} else {
								forTipoServicioCursoList.get(0).setFechabaja(null);
								forTipoServicioCursoList.get(0).setUsumodificacion(usuario.getIdusuario().longValue());
								forTipoServicioCursoList.get(0).setFechamodificacion(new Date());
							}
						}

						// Eliminamos Servicio
						ForTiposervicioCursoExample forTiposerviciocursoExample = new ForTiposervicioCursoExample();
						forTiposerviciocursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcursoEqualTo(cursoItem.getIdCurso()).andFechabajaIsNull();

						List<ForTiposervicioCurso> forTipoServicioCursoAntiguosList = forTiposervicioCursoExtendsMapper
								.selectByExample(forTiposerviciocursoExample);
						List<ForTiposervicioCurso> forTipoServicioCursoDarBaja = new ArrayList<ForTiposervicioCurso>();

						for (ForTiposervicioCurso servicioAsignadosAntiguos : forTipoServicioCursoAntiguosList) {

							for (int i = 0; cursoItem.getTipoServicios().size() > i; i++) {

								if (servicioAsignadosAntiguos.getIdttiposervicio() != Long
										.valueOf(cursoItem.getTipoServicios().get(i).getValue())) {
									forTipoServicioCursoDarBaja.add(servicioAsignadosAntiguos);
								}
							}
						}

						for (ForTiposervicioCurso servicioCursoBaja : forTipoServicioCursoDarBaja) {

							servicioCursoBaja.setUsumodificacion(usuario.getIdusuario().longValue());
							servicioCursoBaja.setFechabaja(new Date());
							servicioCursoBaja.setFechamodificacion(new Date());

							response = forTiposervicioCursoExtendsMapper.updateByPrimaryKey(servicioCursoBaja);
						}
						// Comprobamos si existe algun servicio para el curso y les damos de baja
					} else {
						// Eliminamos Servicio
						ForTiposervicioCursoExample forTiposerviciocursoExample = new ForTiposervicioCursoExample();
						forTiposerviciocursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcursoEqualTo(cursoItem.getIdCurso()).andFechabajaIsNull();

						List<ForTiposervicioCurso> forTipoServicioCursoAntiguosList = forTiposervicioCursoExtendsMapper
								.selectByExample(forTiposerviciocursoExample);

						for (ForTiposervicioCurso servicioAsignadosAntiguos : forTipoServicioCursoAntiguosList) {

							servicioAsignadosAntiguos.setUsumodificacion(usuario.getIdusuario().longValue());
							servicioAsignadosAntiguos.setFechabaja(new Date());
							servicioAsignadosAntiguos.setFechamodificacion(new Date());

							response = forTiposervicioCursoExtendsMapper.updateByPrimaryKey(servicioAsignadosAntiguos);
						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response != 0) {
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
			eventoList = ageEventoExtendsMapper.getSessionsCourse(cursoItem.getIdTipoEvento().toString(),
					cursoItem.getIdCurso().toString(), idInstitucion.toString());
			LOGGER.info(
					"getSessionsCourse() / ageEventoExtendsMapper.searchEvent() -> Salida de ageEventoExtendsMapper para obtener un evento especifico");

		}

		LOGGER.info("getSessionsCourse() -> Salida del servicio para obtener un evento especifico");

		eventoDTO.setEventos(eventoList);
		return eventoDTO;
	}

	@Override
	public ComboDTO getServicesCourse(HttpServletRequest request) {
		LOGGER.info(
				"getServicesCourse() -> Entrada al servicio para obtener los tipo de servicio de un curso según la institucion");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getServicesCourse() / forTiposervicioExtendsMapper.getServiceType -> Entrada a forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según la institucion");
			comboItems = forTiposervicioExtendsMapper.getServicesCourse(idInstitucion.toString());
			LOGGER.info(
					"getServicesCourse() / forTiposervicioExtendsMapper.getServiceType -> Salida de forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según la institucion");

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getServicesCourse() -> Salida del servicio para obtener los tipo de servicio de un curso según la institucion");

		return comboDTO;
	}

	@Override
	public ComboDTO getServicesSpecificCourse(HttpServletRequest request, String idCurso) {
		LOGGER.info(
				"getServicesSpecificCourse() -> Entrada al servicio para obtener los tipo de servicio de un curso según el curso");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getServicesSpecificCourse() / forTiposervicioCursoExtendsMapper.getServicesSpecificCourse -> Entrada a forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según el curso");
			comboItems = forTiposervicioCursoExtendsMapper.getServicesSpecificCourse(idInstitucion.toString(), idCurso);
			LOGGER.info(
					"getServicesSpecificCourse() / forTiposervicioCursoExtendsMapper.getServicesSpecificCourse -> Salida de forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según el curso");

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getServicesSpecificCourse() -> Salida del servicio para obtener los tipo de servicio de un curso según el curso");

		return comboDTO;
	}

	@Override
	public InscripcionItem getCountIncriptions(HttpServletRequest request, String idCurso) {
		LOGGER.info(
				"getCountIncriptions() -> Entrada al servicio para obtener un resumen de los estados de las inscripciones a un curso");

		InscripcionItem inscripcionItem = new InscripcionItem();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int response = 0;

		if (null != idInstitucion) {

			try {

				LOGGER.info(
						"getCountIncriptions() / forInscripcionExtendsMapper.getCountIncriptions -> Entrada a forInscripcionExtendsMapper para obtener un resumen de los estados de las inscripciones a un curso");
				inscripcionItem = forInscripcionExtendsMapper.getCountIncriptions(idCurso);
				LOGGER.info(
						"getCountIncriptions() / forInscripcionExtendsMapper.getCountIncriptions -> Salida de forInscripcionExtendsMapper para obtener un resumen de los estados de las inscripciones a un curso");

				int totales = Integer.parseInt(inscripcionItem.getPendientes())
						+ Integer.parseInt(inscripcionItem.getAprobadas())
						+ Integer.parseInt(inscripcionItem.getRechazadas())
						+ Integer.parseInt(inscripcionItem.getCanceladas());
				inscripcionItem.setTotales(String.valueOf(totales));

			} catch (Exception e) {
				response = 0;
			}
		}

		LOGGER.info(
				"getCountIncriptions() -> Salida del servicio para obtener un resumen de los estados de las inscripciones a un curso");

		return inscripcionItem;
	}

	@Override
	public File createExcelInscriptionsFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {
		LOGGER.info("createExcelInscriptionsFile() -> Entrada al servicio que crea la plantilla Excel");

		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, "PlantillaInscripciones");

		LOGGER.info("createExcelInscriptionsFile() -> Salida al servicio que crea la plantilla Excel");

		return XLSFile;
	}

	@Override
	public ResponseEntity<InputStreamResource> generateExcelInscriptions(CursoItem cursoItem,
			HttpServletRequest request) {
		LOGGER.info("generateExcelInscriptions() -> Entrada al servicio para generar la plantilla Excel Inscripcion");

		ResponseEntity<InputStreamResource> res = null;
		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
		List<StringDTO> modosPago = new ArrayList<StringDTO>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"generateExcelInscriptions() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"generateExcelInscriptions() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// 1. Se definen las columnas que conforman la plantilla

				// 1.1 Se rellena las fila con el codigo del curso y la forma de pago del curso
				datosHashtable = new Hashtable<String, Object>();
				datosHashtable.put(CODIGO_CURSO, cursoItem.getCodigoCurso());

				LOGGER.info(
						"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Entrada a pysFormapagoExtendsMapper para obtener los metodos de pago existentes");
				modosPago = pysFormapagoExtendsMapper.getWayToPay(usuario.getIdlenguaje());
				LOGGER.info(
						"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Salida de pysFormapagoExtendsMapper para obtener los metodos de pago existentes");

				String[] modosPagoArray = new String[modosPago.size()];
				// modosPagoArray = modosPago.toArray(modosPagoArray);

				for (int i = 0; i < modosPago.size(); i++) {
					modosPagoArray[i] = String.valueOf(modosPago.get(i).getValor());
				}

				datosHashtable.put(FORMA_PAGO, modosPagoArray);
				datosHashtable.put(NIF, "");
				datosVector.add(datosHashtable);

				// 2. Crea el fichero excel con las columnas indicadas
				File file = createExcelInscriptionsFile(IFichaCursosService.CAMPOSPLANTILLA, datosVector);

				// 3. Se convierte el fichero en array de bytes para enviarlo al front
				InputStream fileStream = null;

				try {
					fileStream = new FileInputStream(file);
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

					headers.setContentLength(file.length());
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
							HttpStatus.OK);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("generateExcelInscriptions() -> Salida del servicio para generar la plantilla Excel Inscripcion");

		return res;
	}

	@Override
	@Transactional
	public UpdateResponseDTO uploadFileExcel(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {
		LOGGER.info("uploadFile() -> Entrada al servicio para guardar un archivo");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		// Controlar errores
		Error error = new Error();
		String errores = "";

		// Coger archivo del request
		LOGGER.debug("uploadFile() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());

		// Extraer la información del excel
		LOGGER.debug("uploadFile() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int registrosErroneos = 0;
		ForInscripcionesmasivas forInscripcionesmasivas = new ForInscripcionesmasivas();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"uploadFileExcel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"uploadFileExcel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				List<InscripcionItem> inscripcionList = parseExcelFile(datos, usuario);

				for (InscripcionItem inscripcion : inscripcionList) {
					if (!inscripcion.getErrores().isEmpty()) {
						registrosErroneos++;
					}
				}

				if (registrosErroneos != 0) {

					try {
						for (InscripcionItem inscripcion : inscripcionList) {
							if (inscripcion.getErrores().isEmpty()) {

								ForInscripcion inscripcionInsert = new ForInscripcion();
								inscripcionInsert.setFechabaja(null);
								inscripcionInsert.setFechamodificacion(new Date());
								inscripcionInsert.setIdcurso(Long.valueOf(inscripcion.getIdCurso()));
								inscripcionInsert.setIdestadoinscripcion(INSCRIPCION_PENDIENTE);
								inscripcionInsert.setIdinstitucion(idInstitucion);
								inscripcionInsert.setIdpersona(inscripcion.getIdPersona());
								inscripcionInsert.setUsumodificacion(usuario.getIdusuario().longValue());

								forInscripcionExtendsMapper.insert(inscripcionInsert);

							} else {
								registrosErroneos++;
							}

							Hashtable<String, Object> e = new Hashtable<String, Object>();
							e = convertItemtoHash(inscripcion);
							// Guardar log
							datosLog.add(e);
							errores += inscripcion.getErrores();

						}

					} catch (Exception e) {
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					}
				} else {
					error.setCode(400);
					error.setDescription("Hay errores en las inscripciones subidas");
				}

				byte[] bytesLog = ExcelHelper.createExcelBytes(IFichaCursosService.CAMPOSPLOG, datosLog);

				forInscripcionesmasivas.setIdinstitucion(idInstitucion);
				forInscripcionesmasivas.setNombrefichero(ICargasMasivasGFService.nombreFicheroEjemplo);
				forInscripcionesmasivas.setFechamodificacion(new Date());
				forInscripcionesmasivas.setUsumodificacion(Long.valueOf(usuario.getIdusuario()));
				forInscripcionesmasivas.setIdcurso(Long.valueOf("27"));

				Long idFile = uploadFile(file.getBytes(), forInscripcionesmasivas, false, usuario);
				// Long idLogFile = uploadFile(bytesLog, cenCargamasivaGF, true, usuario);
				// forInscripcionesmasivas.setIdfichero(idFile);
				// forInscripcionesmasivas.setIdficherolog(idLogFile);
				//
				int result = forInscripcionesmasivasMapper.insert(forInscripcionesmasivas);

				if (result == 0) {
					errores += "Error al insertar en cargas masivas";
					error.setDescription(errores);
					updateResponseDTO.setError(error);
				}

			}

		}

		LOGGER.info("uploadFile() -> Salida al servicio para subir un archivo");
		updateResponseDTO.setStatus(SigaConstants.OK);
		error.setDescription(errores);
		// int correctos = cenCargamasivaGF.getNumregistros() - registrosErroneos;
		// error.setMessage("Fichero cargado correctamente. Registros Correctos: " +
		// correctos
		// + "<br/> Registros Erroneos: " + cenCargamasivaGF.getNumregistroserroneos());
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	public List<InscripcionItem> parseExcelFile(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario)
			throws BusinessException {

		List<InscripcionItem> inscripcionItemList = new ArrayList<InscripcionItem>();
		InscripcionItem inscripcionItem = null;

		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();
		Short idInstitucion = usuario.getIdinstitucion();

		StringBuffer errorLinea = null;

		for (Hashtable<String, Object> hashtable : datos) {

			inscripcionItem = new InscripcionItem();
			inscripcionItem.setIdInstitucion(idInstitucion.toString());
			errorLinea = new StringBuffer();

			// Comprobacion si el codigo curso esta introducido es correcto
			if ((hashtable.get(CODIGO_CURSO) != null && !hashtable.get(CODIGO_CURSO).toString().equals(""))) {

				ForCursoExample forCursoExample = new ForCursoExample();
				forCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andCodigocursoEqualTo(hashtable.get(CODIGO_CURSO).toString());

				LOGGER.info(
						"parseExcelFile() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar el formador existente");

				List<ForCurso> cursoList = forCursoExtendsMapper.selectByExample(forCursoExample);

				LOGGER.info(
						"parseExcelFile() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida a forPersonacursoExtendsMapper para buscar el formador existente");

				if (null != cursoList && cursoList.size() > 0) {
					ForCurso curso = cursoList.get(0);
					inscripcionItem.setCodigoCurso(curso.getCodigocurso());
					inscripcionItem.setIdCurso(curso.getIdcurso().toString());
				} else {
					inscripcionItem.setCodigoCurso("Error");
					errorLinea.append("Es obligatorio introducir el código del curso.");
				}

			} else {
				errorLinea.append("Es obligatorio introducir el código del curso.");
				inscripcionItem.setCodigoCurso("Error");
			}

			// Comprobacion si el metodo de pago esta introducido
			if ((hashtable.get(FORMA_PAGO) != null && !hashtable.get(FORMA_PAGO).toString().equals(""))) {

				LOGGER.info(
						"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Entrada a pysFormapagoExtendsMapper para obtener los metodos de pago existentes");
				List<StringDTO> modosPagosList = pysFormapagoExtendsMapper.getWayToPay(usuario.getIdlenguaje());
				LOGGER.info(
						"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Salida de pysFormapagoExtendsMapper para obtener los metodos de pago existentes");

				boolean isModoPago = false;

				for (StringDTO modoPago : modosPagosList) {
					if (modoPago.getValor().equals(hashtable.get(FORMA_PAGO))) {
						isModoPago = true;
					}
				}

				if (isModoPago) {
					inscripcionItem.setFormaPago((String) hashtable.get(FORMA_PAGO));
				} else {
					inscripcionItem.setFormaPago("Error");
					errorLinea.append("Es obligatorio decir la forma de pago del curso.");
				}

			} else {
				errorLinea.append("Es obligatorio decir la forma de pago del curso.");
				inscripcionItem.setFormaPago("Error");
			}

			// Comprobar que el nif esta introducido
			if (hashtable.get(NIF) != null && !hashtable.get(NIF).toString().equals("")) {

				try {
					// Se comprueba si esta en cen_persona
					Long idPersona = getIdPersonaVerify((String) hashtable.get(NIF), idInstitucion);
					inscripcionItem.setIdPersona(idPersona);

				} catch (Exception e) {
					errorLinea.append(e.getMessage() + ". ");
					inscripcionItem.setNombrePersona("Error");
				}

				// Si se encuentre debemos comprobar que este en cen_cliente
				if (inscripcionItem.getIdPersona() != null) {
					FichaPersonaItem persona = new FichaPersonaItem();
					persona = getPersonaVerify(inscripcionItem.getIdPersona().toString(), idInstitucion.toString());

					if (persona != null) {
						CenClienteExample cenClienteExample = new CenClienteExample();
						cenClienteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(inscripcionItem.getIdPersona());

						List<CenCliente> cenClienteList = cenClienteExtendsMapper.selectByExample(cenClienteExample);
						if (null == cenClienteList || cenClienteList.size() == 0) {

							// Crear CenCliente
							// CRear noColegiado
						}

					} else {
						errorLinea.append("No existe la persona.");
						inscripcionItem.setNombrePersona("Error");
					}

				} else {
					errorLinea.append("Es obligatorio introducir el nif/cif. ");
					inscripcionItem.setNombrePersona("Error");
				}

				// Se comprueba si la persona esta inscrita ya en el curso, si no esta inscrita
				// se inscribe

				if (inscripcionItem.getIdCurso() != null && inscripcionItem.getIdCurso() != ""
						&& inscripcionItem.getIdPersona() != null) {

					ForInscripcionExample forInscripcionExample = new ForInscripcionExample();
					forInscripcionExample.createCriteria().andIdcursoEqualTo(Long.valueOf(inscripcionItem.getIdCurso()))
							.andIdpersonaEqualTo(inscripcionItem.getIdPersona());

					LOGGER.info(
							"parseExcelFile() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Entrada a forPersonacursoExtendsMapper para buscar el formador existente");

					List<ForInscripcion> inscripcionList = forInscripcionExtendsMapper
							.selectByExample(forInscripcionExample);

					LOGGER.info(
							"parseExcelFile() / forPersonacursoExtendsMapper.selectByExample(exampleFormador) -> Salida a forPersonacursoExtendsMapper para buscar el formador existente");

					if (null != inscripcionList && inscripcionList.size() > 0) {
						inscripcionItem.setDescripcionEstado("Error");
						errorLinea.append("Ya esta el usuario inscrito.");
					}
				}

				inscripcionItem.setErrores(errorLinea.toString());
				inscripcionItemList.add(inscripcionItem);

			}
		}

		return inscripcionItemList;
	}

	private Long uploadFile(byte[] excelBytes, ForInscripcionesmasivas forInscripcionesmasivas, boolean isLog,
			AdmUsuarios usuario) throws BusinessException {
		Date dateLog = new Date(0);
		LOGGER.info(dateLog + ":inicio.CargaInscripcionesMasiva.uploadFile");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(forInscripcionesmasivas.getIdinstitucion());
		ficheroVo.setDirectorio(directorioFichero);
		String nombreFicheroString = forInscripcionesmasivas.getNombrefichero();
		ficheroVo.setNombre(nombreFicheroString);
		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(forInscripcionesmasivas.getIdinstitucion());
		ficheroVo.setFichero(excelBytes);
		ficheroVo.setExtension("xls");

		ficheroVo.setUsumodificacion(Integer.valueOf(usuario.getUsumodificacion()));
		ficheroVo.setFechamodificacion(new Date());
		ficherosService.insert(ficheroVo);

		if (isLog) {
			String descripcion = "log_" + ficheroVo.getDescripcion();
			ficheroVo.setDescripcion(descripcion);
			String nombreFichero = "log_" + ficheroVo.getNombre();
			ficheroVo.setNombre(nombreFichero);
		}

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		LOGGER.info(dateLog + ":fin.CargaMasivaInscripcion.uploadFile");
		return ficheroVo.getIdfichero();

	}

	private String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.info(dateLog + ":inicio.CargaInscripcionesMasiva.getDirectorioFichero");

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		// genPropertiesPath.get(0).getValor()
		StringBuffer directorioFichero = new StringBuffer("C:\\Users\\DTUser\\Documents\\cargas");
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaGF");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		// genPropertiesDirectorio.get(0).getValor()
		directorioFichero.append("inscripciones");

		LOGGER.info(dateLog + ":fin.CargaMasivaInscripciones.getDirectorioFichero");
		return directorioFichero.toString();
	}

	public Long getIdPersonaVerify(String personaNif, Short idInstitucion) {

		Long idPersonaSearch = null;

		if (personaNif == null || personaNif == "") {
			throw new BusinessException("Campo nif obligatorio");
		} else if (personaNif != null && personaNif != "") {
			idPersonaSearch = cenPersonaExtendsMapper.getIdPersonaWithNif(personaNif);
		}

		return idPersonaSearch;
	}

	public FichaPersonaItem getPersonaVerify(String idPersona, String string) {

		FichaPersonaItem personaSearch = null;

		if (idPersona == null || idPersona == "") {
			throw new BusinessException("Campo nif obligatorio");
		} else if (idPersona != null && idPersona != "") {
			personaSearch = cenPersonaExtendsMapper.getPersonaisColegiadoWithIdPersona(idPersona, string.toString());
		}

		return personaSearch;
	}

	private Hashtable<String, Object> convertItemtoHash(InscripcionItem inscripcionItem) {
		Hashtable<String, Object> e = new Hashtable<String, Object>();

		if (inscripcionItem.getCodigoCurso() != null) {
			e.put(CODIGO_CURSO, inscripcionItem.getCodigoCurso());
		}
		if (inscripcionItem.getFormaPago() != null) {
			e.put(FORMA_PAGO, inscripcionItem.getFormaPago());
		}
		if (inscripcionItem.getIdPersona() != null) {
			e.put(NIF, inscripcionItem.getNombrePersona());
		}
		return e;
	}

}
