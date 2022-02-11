package org.itcgae.siga.form.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesDTO;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesItem;
import org.itcgae.siga.DTOs.form.CertificadoCursoDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.form.PreciosCursoDTO;
import org.itcgae.siga.DTOs.form.PreciosCursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEvento;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.AgeGeneracionnotificaciones;
import org.itcgae.siga.db.entities.AgeGeneracionnotificacionesExample;
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.itcgae.siga.db.entities.AgePersonaEvento;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ForCertificadoscurso;
import org.itcgae.siga.db.entities.ForCertificadoscursoExample;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.entities.ForEventoCurso;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.entities.ForInscripcionesmasivas;
import org.itcgae.siga.db.entities.ForInscripcionesmasivasExample;
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;
import org.itcgae.siga.db.entities.ForTemacursoCurso;
import org.itcgae.siga.db.entities.ForTemacursoCursoExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysPreciosservicios;
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.mappers.AgeGeneracionnotificacionesMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ForEventoCursoMapper;
import org.itcgae.siga.db.mappers.ForInscripcionesmasivasMapper;
import org.itcgae.siga.db.mappers.ForTemacursoCursoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeEventoExtendsMapper;
import org.itcgae.siga.db.services.age.mappers.AgeNotificacioneseventoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscvExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCalificacionesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCertificadoscursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionesmasivasExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForPersonacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForRolesExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTipocosteExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTiposervicioCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTiposervicioExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysFormapagoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysPreciosserviciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductosinstitucionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysSuscripcionExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.form.services.IFichaCursosService;
import org.itcgae.siga.form.services.IFichaInscripcionService;
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
	private ForEventoCursoMapper forEventoCursoMapper;

	@Autowired
	private AgeEventoExtendsMapper ageEventoExtendsMapper;

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
	private AgeNotificacioneseventoExtendsMapper ageNotificacioneseventoExtendsMapper;

	@Autowired
	private AgeGeneracionnotificacionesMapper ageGeneracionnotificacionesMapper;

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

	@Autowired
	private CenNocolegiadoMapper cenNocolegiadoMapper;

	@Autowired
	private ForInscripcionesmasivasExtendsMapper forInscripcionesmasivasExtendsMapper;

	@Autowired
	private PysServiciosExtendsMapper pysServiciosExtendsMapper;

	@Autowired
	private PysServiciosinstitucionExtendsMapper pysServiciosinstitucionExtendsMapper;

	@Autowired
	private PysPreciosserviciosExtendsMapper pysPreciosserviciosExtendsMapper;

	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;

	@Autowired
	private PysSuscripcionExtendsMapper pysSuscripcionExtendsMapper;

	@Autowired
	private PysServiciossolicitadosMapper pysServiciossolicitadosMapper;

	@Autowired
	private ForTemacursoExtendsMapper forTemacursoExtendsMapper;

	@Autowired
	private ForTemacursoCursoMapper forTemacursoCursoMapper;

	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;

	@Autowired
	private ForCalificacionesExtendsMapper forCalificacionesExtendsMapper;

	@Autowired
	private PysProductosinstitucionExtendsMapper pysProductosinstitucionExtendsMapper;

	@Autowired
	private ForCertificadoscursoExtendsMapper forCertificadoscursoExtendsMapper;

	@Autowired
	private IFichaInscripcionService fichaInscripcionService;

	@Autowired
	private CenDatoscvExtendsMapper cenDatosCvExtendsMapper;

	@Autowired
	private ForCertificadoscursoExtendsMapper forCertificadosCursoExtendsMapper;

	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

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
				ForCurso curso = forCursoExtendsMapper.selectByPrimaryKeyExtends(new Long(idCurso));
				formadoresCursoItem = forPersonacursoExtendsMapper
						.getTrainersCourse(curso.getIdinstitucion().toString(), idCurso, usuario.getIdlenguaje());
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);

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
						"getRolesTrainers() / forRolesExtendsMapper.getRolesTrainers -> Entrada a forRolesExtendsMapper para obtener los roles");
				comboItems = forRolesExtendsMapper.getRolesTrainers(idInstitucion.toString(), usuario.getIdlenguaje());
				LOGGER.info(
						"getRolesTrainers() / forRolesExtendsMapper.getRolesTrainers -> Salida de forRolesExtendsMapper para obtener los roles");
			}

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
	@Transactional(timeout=2400)
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
		int responseCenNoColegiado = 1;
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
						comboItems = cenPersonaExtendsMapper.selectMaxIdPersona(usuario.getIdinstitucion().toString());
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

					// Lo añadimos en cen_nocolegiado
					CenNocolegiado recordNoColegido = new CenNocolegiado();
					recordNoColegido.setIdinstitucion(usuario.getIdinstitucion());
					recordNoColegido.setIdpersona(idPersona);
					recordNoColegido.setTipo("1");
					recordNoColegido.setFechamodificacion(new Date());
					recordNoColegido.setUsumodificacion(usuario.getIdusuario());
					recordNoColegido.setSociedadsj("0");
					responseCenNoColegiado = cenNocolegiadoMapper.insertSelective(recordNoColegido);

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
				} else if (responseCenNoColegiado == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

					// Añadimos al formador
				} else {
					// Si es tutor, buscamos si existe otro formador asignado como formador y se lo
					// desasignamos porque
					// solamente puede existir un formador
					if (formadorCursoItem.getTutor() == SigaConstants.ASIGNAR_TUTOR) {
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
							tutor.setTutor(SigaConstants.DESIGNAR_TUTOR);
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
	@Transactional(timeout=2400)
	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request) {

		LOGGER.info("deleteTrainersCourse() -> Salida del servicio para dar de baja a los formadores de un curso");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		updateResponseDTO.setStatus(SigaConstants.OK);
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
					Boolean existePersona = Boolean.FALSE;
					for (FormadorCursoItem formador : formadorCursoDTO.getFormadoresCursoItem()) {

						// Comprobamos que el formador no esté asignado a alguna sesión

						List<AgePersonaEvento> personaSesion = forPersonacursoExtendsMapper
								.selectSesionesFormador(formador);
						if (null != personaSesion && personaSesion.size() > 0) {
							response = 0;
							error.setCode(400);
							error.setDescription(
									"Los formadores a eliminar no pueden estar asignados a ninguna sesión");
							existePersona = Boolean.TRUE;
							updateResponseDTO.setStatus(SigaConstants.KO);
							break;

						}
					}
					if (!existePersona) {

						for (FormadorCursoItem formador : formadorCursoDTO.getFormadoresCursoItem()) {
							ForPersonaCursoExample exampleFormador = new ForPersonaCursoExample();
							exampleFormador.createCriteria()
									.andIdformadorEqualTo(Long.valueOf(formador.getIdFormador()));

							LOGGER.info(
									"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a forPersonacursoExtendsMapper para obtener los formadores de un curso");
							List<ForPersonaCurso> formadoresList = forPersonacursoExtendsMapper
									.selectByExample(exampleFormador);
							LOGGER.info(
									"deleteTrainersCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida a forPersonacursoExtendsMapper para obtener los formadores de un curso");

							if (null != formadoresList && formadoresList.size() > 0) {
								ForPersonaCurso formadorDelete = formadoresList.get(0);

								LOGGER.info(
										"deleteTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(notification) -> Entrada a forPersonacursoExtendsMapper para dar de baja a un formador");

								formadorDelete.setFechamodificacion(new Date());
								formadorDelete.setUsumodificacion(usuario.getIdusuario().longValue());
								formadorDelete.setFechabaja(new Date());
								forPersonacursoExtendsMapper.updateByPrimaryKey(formadorDelete);

								LOGGER.info(
										"deleteTrainersCourse() / forPersonacursoExtendsMapper.updateByPrimaryKey(notification) -> Salida a forPersonacursoExtendsMapper para dar de baja a un formador");
							}

						}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
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
	@Transactional(timeout=2400)
	public InsertResponseDTO saveCourse(CursoItem cursoItem, HttpServletRequest request) {

		LOGGER.info("saveCourse() -> Entrada al servicio para insertar un curso");

		int response = 2;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ForCurso forCursoInsert = new ForCurso();
		String codigo = null;

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

					ForCursoExample forcursoExample = new ForCursoExample();
					forcursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andNombrecursoEqualTo(cursoItem.getNombreCurso());

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para buscar si existe un curso con la misma descripción");

					List<ForCurso> forCursoList = forCursoExtendsMapper.selectByExample(forcursoExample);

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.selectByExample() -> Salida a forCursoExtendsMapper para buscar si existe un curso con la misma descripción");

					if (null != forCursoList && forCursoList.size() > 0) {

						error.setCode(400);
						error.setDescription("El nombre del curso ya esta asociado a otro curso");

					} else {

						// Insertamos curso en la tabla For_Curso
						forCursoInsert.setIdinstitucion(idInstitucion);
						forCursoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
						forCursoInsert.setFechamodificacion(new Date());
						forCursoInsert.setNombrecurso(cursoItem.getNombreCurso());
						forCursoInsert.setIdvisibilidadcurso(Short.valueOf(cursoItem.getIdVisibilidad()));
						forCursoInsert.setDescripcion(cursoItem.getDescripcionEstado());
						forCursoInsert.setFechainscripciondesde(cursoItem.getFechaInscripcionDesdeDate());
						forCursoInsert.setFechainscripcionhasta(cursoItem.getFechaInscripcionHastaDate());
						forCursoInsert.setIdestadocurso(Long.valueOf(cursoItem.getIdEstado()));
						if (cursoItem.getPlazasDisponibles() != null) {
							forCursoInsert.setNumeroplazas(Long.valueOf(cursoItem.getPlazasDisponibles()));
						}
						if (cursoItem.getMinimoAsistencia() != null) {
							forCursoInsert.setMinimoasistencia(Long.valueOf(cursoItem.getMinimoAsistencia()));
						}
						forCursoInsert.setFlagarchivado(Short.valueOf("0"));
						forCursoInsert.setLugar(cursoItem.getLugar());
						forCursoInsert
								.setAutovalidacioninscripcion(cursoItem.getAutovalidacionInscripcion().shortValue());
						forCursoInsert.setEncuestasatisfaccion(cursoItem.getEncuesta());
						forCursoInsert.setInformacionadicional(cursoItem.getAdicional());
						forCursoInsert.setDocumentacionadjunta(cursoItem.getAdjunto());

						forCursoInsert.setIdtiposervicio(cursoItem.getTipoServicios() == null ? null
								: Long.parseLong(cursoItem.getTipoServicios()));

						// Obtenemos el código del curso
						codigo = getCounterCourse(idInstitucion);
						forCursoInsert.setCodigocurso(codigo);

						LOGGER.info(
								"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Entrada a forCursoExtendsMapper para insertar un curso");
						response = forCursoExtendsMapper.insert(forCursoInsert);
						LOGGER.info(
								"saveCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Salida a forCursoExtendsMapper para insertar un curso");

						// Si existe tiposervicio, se guarda en la tabla TipoServicios
						// if (cursoItem.getTipoServicios() != null &&
						// cursoItem.getTipoServicios().size() > 0) {
						//
						// for (ComboItem servicio : cursoItem.getTipoServicios()) {
						//
						// ForTiposervicioCurso forTipoServicioCurso = new ForTiposervicioCurso();
						// forTipoServicioCurso.setFechabaja(null);
						// forTipoServicioCurso.setFechamodificacion(new Date());
						// forTipoServicioCurso.setIdcurso(forCursoInsert.getIdcurso());
						// forTipoServicioCurso.setIdinstitucion(idInstitucion);
						// forTipoServicioCurso.setIdttiposervicio(Long.valueOf(servicio.getValue()));
						// forTipoServicioCurso.setUsumodificacion(usuario.getIdusuario().longValue());
						//
						// response = forTiposervicioCursoExtendsMapper.insert(forTipoServicioCurso);
						//
						// }
						// }

						// Si existen temas, se guarda en la tabla TemasCurso
						if (cursoItem.getTemasCombo() != null && cursoItem.getTemasCombo().size() > 0) {

							for (ComboItem tema : cursoItem.getTemasCombo()) {

								ForTemacursoCurso forTemacursoCurso = new ForTemacursoCurso();
								forTemacursoCurso.setFechabaja(null);
								forTemacursoCurso.setFechamodificacion(new Date());
								forTemacursoCurso.setIdinstitucion(idInstitucion);
								forTemacursoCurso.setIdcurso(forCursoInsert.getIdcurso());
								forTemacursoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
								forTemacursoCurso.setIdtemacurso(Long.valueOf(tema.getValue()));

								response = forTemacursoCursoMapper.insert(forTemacursoCurso);

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

						if (response == 0) {
							error.setCode(400);
							error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
						} else if (response == 1) {
							error.setCode(200);

							error.setDescription(
									"Se ha dado de alta el curso con el código " + codigo + " correctamente. ");
							insertResponseDTO.setId(Long.toString(forCursoInsert.getIdcurso()));
							insertResponseDTO.setStatus(codigo);
							insertResponseDTO.setError(error);

							response = createServiceCourse(forCursoInsert, usuario, idInstitucion);

							if (response == 0) {
								error.setCode(400);
								error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

							} else {
								error.setCode(200);
							}
						}

					}

				} catch (Exception e) {
					response = 0;
				}

			}
		}

		insertResponseDTO.setError(error);

		LOGGER.info("saveCourse() -> Salida del servicio para insertar un curso");

		return insertResponseDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO updateCourse(CursoItem cursoItem, HttpServletRequest request) {
		LOGGER.info("updateCourse() -> Entrada al servicio para modificar los eventos");

		int response = 2;
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

					ForCursoExample forcursoExample = new ForCursoExample();
					forcursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andNombrecursoEqualTo(cursoItem.getNombreCurso())
							.andIdcursoNotEqualTo(cursoItem.getIdCurso());

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para buscar si existe un curso con la misma descripción");

					List<ForCurso> forCursoList = forCursoExtendsMapper.selectByExample(forcursoExample);

					LOGGER.info(
							"saveCourse() / forCursoExtendsMapper.selectByExample() -> Salida a forCursoExtendsMapper para buscar si existe un curso con la misma descripción");

					if (null != forCursoList && forCursoList.size() > 0) {

						error.setCode(400);
						error.setDescription("El nombre del curso ya esta asociado a otro curso");

					} else {

						LOGGER.info(
								"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para modificar un curso");

						response = forCursoExtendsMapper.updateCourse(cursoItem, usuario);

						LOGGER.info(
								"updateCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para modificar un evento");

						if (cursoItem.getTemasCombo() != null && cursoItem.getTemasCombo().size() > 0) {

							// Eliminamos el tema que no se encuentre en la lista actual
							ForTemacursoCursoExample forTemacursoCursoExample = new ForTemacursoCursoExample();
							forTemacursoCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdcursoEqualTo(cursoItem.getIdCurso()).andFechabajaIsNull();

							LOGGER.info(
									"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemacursoCursoMapper para buscar los temas registrados de un curso");

							List<ForTemacursoCurso> forTemacursoCursoAntiguosList = forTemacursoCursoMapper
									.selectByExample(forTemacursoCursoExample);

							LOGGER.info(
									"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemacursoCursoMapper para buscar los temas registrados de un curso");

							List<ForTemacursoCurso> forTemacursoCursoDarBaja = new ArrayList<ForTemacursoCurso>();

							// Si hay temas que estan dados de alta, comprobamos que se encuentra en la
							// modificacion actual
							if (!forTemacursoCursoAntiguosList.isEmpty()) {

								for (ForTemacursoCurso temasAsignadosAntiguos : forTemacursoCursoAntiguosList) {
									boolean flag = false;
									for (int i = 0; cursoItem.getTemasCombo().size() > i; i++) {

										if (temasAsignadosAntiguos.getIdtemacurso() == Long
												.valueOf(cursoItem.getTemasCombo().get(i).getValue())) {
											flag = true;
											i = cursoItem.getTemasCombo().size();
										}
									}

									// Si no se encuentra en la lista actual la borramos
									if (!flag) {
										forTemacursoCursoDarBaja.add(temasAsignadosAntiguos);
									}
								}

								for (ForTemacursoCurso temaCursoBaja : forTemacursoCursoDarBaja) {

									temaCursoBaja.setUsumodificacion(usuario.getIdusuario().longValue());
									temaCursoBaja.setFechabaja(new Date());
									temaCursoBaja.setFechamodificacion(new Date());

									LOGGER.info(
											"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a forTemacursoCursoMapper para dar de baja a un tema de un curso");

									response = forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja);

									LOGGER.info(
											"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a forTemacursoCursoMapper para dar de baja a un tema de un curso");
								}
							}

							// Añadimos temas
							for (ComboItem tema : cursoItem.getTemasCombo()) {

								// Para cada temas comprobamos si ya existe la relacion
								ForTemacursoCursoExample forTemacursoExample = new ForTemacursoCursoExample();
								forTemacursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdcursoEqualTo(cursoItem.getIdCurso())
										.andIdtemacursoEqualTo(Long.valueOf(tema.getValue()));

								LOGGER.info(
										"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemacursoCursoMapper para buscar los temas registrados de un curso");

								List<ForTemacursoCurso> forTemacursoCursoList = forTemacursoCursoMapper
										.selectByExample(forTemacursoExample);

								LOGGER.info(
										"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemacursoCursoMapper para buscar los temas registrados de un curso");

								// Si no existe la creamos
								if (forTemacursoCursoList.isEmpty()) {

									ForTemacursoCurso forTemacursoCurso = new ForTemacursoCurso();
									forTemacursoCurso.setFechabaja(null);
									forTemacursoCurso.setFechamodificacion(new Date());
									forTemacursoCurso.setIdinstitucion(idInstitucion);
									forTemacursoCurso.setIdcurso(cursoItem.getIdCurso());
									forTemacursoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
									forTemacursoCurso.setIdtemacurso(Long.valueOf(tema.getValue()));

									LOGGER.info(
											"updateCourse() / forTemacursoCursoMapper.insert(forTipoServicioCurso) -> Entrada a forTemacursoCursoMapper para insertar un tema de un curso");

									response = forTemacursoCursoMapper.insert(forTemacursoCurso);

									LOGGER.info(
											"updateCourse() / forTemacursoCursoMapper.insert(forTipoServicioCurso) -> Salida a forTemacursoCursoMapper para insertar un tema de un curso");

									// Si existe
								} else {
									// Comprobamos que si fecha de baja esta a null, si no esta la modificamos
									if (forTemacursoCursoList.get(0).getFechabaja() != null) {
										ForTemacursoCurso forTemaCurso = forTemacursoCursoList.get(0);
										forTemaCurso.setFechabaja(null);
										forTemaCurso.setUsumodificacion(usuario.getIdusuario().longValue());
										forTemaCurso.setFechamodificacion(new Date());

										LOGGER.info(
												"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(forTemaCurso) -> Entrada a forTemacursoCursoMapper para dar de alta a un tema de un curso");

										response = forTemacursoCursoMapper.updateByPrimaryKey(forTemaCurso);

										LOGGER.info(
												"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(forTemaCurso) -> Salida a forTemacursoCursoMapper para dar de alta a un tema de un curso");
									}
								}
							}

							// Comprobamos si existe algun tema para el curso y les damos de baja
						} else {
							// Eliminamos Tema
							ForTemacursoCursoExample forTemacursoCursoExample = new ForTemacursoCursoExample();
							forTemacursoCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdcursoEqualTo(cursoItem.getIdCurso()).andFechabajaIsNull();

							LOGGER.info(
									"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemacursoCursoMapper para buscar los temas registrados de un curso");

							List<ForTemacursoCurso> forTemacursoCursoAntiguosList = forTemacursoCursoMapper
									.selectByExample(forTemacursoCursoExample);

							LOGGER.info(
									"updateCourse() / forTemacursoCursoMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemacursoCursoMapper para buscar los temas registrados de un curso");

							for (ForTemacursoCurso temasAsignadosAntiguos : forTemacursoCursoAntiguosList) {

								temasAsignadosAntiguos.setUsumodificacion(usuario.getIdusuario().longValue());
								temasAsignadosAntiguos.setFechabaja(new Date());
								temasAsignadosAntiguos.setFechamodificacion(new Date());

								LOGGER.info(
										"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a forTemacursoCursoMapper para dar de baja a un tema de un curso");

								response = forTemacursoCursoMapper.updateByPrimaryKey(temasAsignadosAntiguos);

								LOGGER.info(
										"updateCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a forTemacursoCursoMapper para dar de baja a un tema de un curso");

							}
						}

						// Si existe idEventoInscripcion se debe guardar la relacion entre el evento y
						// el curso
						if (cursoItem.getIdEventoInicioInscripcion() != null) {

							ForEventoCurso forEventoCurso = new ForEventoCurso();
							forEventoCurso.setFechabaja(null);
							forEventoCurso.setFechamodificacion(new Date());
							forEventoCurso.setIdcurso(cursoItem.getIdCurso());
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
									"updateCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							List<AgeEvento> eventoList = ageEventoExtendsMapper.selectByExample(exampleEvento);

							LOGGER.info(
									"updateCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							if (null != eventoList && eventoList.size() > 0) {
								AgeEvento evento = eventoList.get(0);

								evento.setFechabaja(null);
								evento.setFechamodificacion(new Date());
								evento.setUsumodificacion(usuario.getIdusuario().longValue());

								LOGGER.info(
										"updateCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

								response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

								LOGGER.info(
										"updateCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

							}

						}

						// Si existe idEventoInscripcion se debe guardar la relacion entre el evento y
						// el curso
						if (cursoItem.getIdEventoFinInscripcion() != null) {

							ForEventoCurso forEventoCurso = new ForEventoCurso();
							forEventoCurso.setFechabaja(null);
							forEventoCurso.setFechamodificacion(new Date());
							forEventoCurso.setIdcurso(cursoItem.getIdCurso());
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
									"updateCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							List<AgeEvento> eventoList = ageEventoExtendsMapper.selectByExample(exampleEvento);

							LOGGER.info(
									"updateCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							if (null != eventoList && eventoList.size() > 0) {
								AgeEvento evento = eventoList.get(0);

								evento.setFechabaja(null);
								evento.setFechamodificacion(new Date());
								evento.setUsumodificacion(usuario.getIdusuario().longValue());

								LOGGER.info(
										"updateCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

								response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

								LOGGER.info(
										"updateCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

							}

						}

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response == 1) {
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
	@Transactional(timeout=2400)
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

							if (course.getIdestadocurso() == SigaConstants.ABIERTO_CURSO
									|| course.getIdestadocurso() == SigaConstants.ANUNCIADO_CURSO) {

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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchCourse() / ageEventoExtendsMapper.searchEvent() -> Entrada a ageEventoExtendsMapper para obtener un evento especifico");

				cursoItem = forCursoExtendsMapper.searchCourseByIdcurso(idCurso, idInstitucion,
						usuario.getIdlenguaje());

				LOGGER.info(
						"searchCourse() / ageEventoExtendsMapper.searchEvent() -> Salida de ageEventoExtendsMapper para obtener un evento especifico");
			}
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getSessionsCourse() / ageEventoExtendsMapper.searchEvent() -> Entrada a ageEventoExtendsMapper para obtener un evento especifico");
				eventoList = ageEventoExtendsMapper.getSessionsCourse(cursoItem.getIdTipoEvento().toString(),
						cursoItem.getIdCurso().toString(), cursoItem.getIdInstitucion(), usuario.getIdlenguaje());
				LOGGER.info(
						"getSessionsCourse() / ageEventoExtendsMapper.searchEvent() -> Salida de ageEventoExtendsMapper para obtener un evento especifico");

			}
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getServicesCourse() / forTiposervicioExtendsMapper.getServiceType -> Entrada a forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según la institucion");
				comboItems = pysServiciosExtendsMapper.getServicesCourse(idInstitucion.toString(),
						usuario.getIdlenguaje());
				LOGGER.info(
						"getServicesCourse() / forTiposervicioExtendsMapper.getServiceType -> Salida de forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según la institucion");
			}
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getServicesSpecificCourse() / forTiposervicioCursoExtendsMapper.getServicesSpecificCourse -> Entrada a forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según el curso");
				comboItems = forTiposervicioCursoExtendsMapper.getServicesSpecificCourse(idInstitucion.toString(),
						idCurso, usuario.getIdlenguaje());
				LOGGER.info(
						"getServicesSpecificCourse() / forTiposervicioCursoExtendsMapper.getServicesSpecificCourse -> Salida de forTiposervicioExtendsMapper para obtener los tipo de servicio de un curso según el curso");
			}
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
				datosHashtable.put(SigaConstants.CODIGO_CURSO, cursoItem.getCodigoCurso());

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

				datosHashtable.put(SigaConstants.FORMA_PAGO, modosPagoArray);
				datosHashtable.put(SigaConstants.NIF, "");
				datosVector.add(datosHashtable);

				// 2. Crea el fichero excel con las columnas indicadas
				File file = createExcelInscriptionsFile(SigaConstants.CAMPOSPLANTILLACURSO, datosVector);

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
	@Transactional(timeout=2400)
	public UpdateResponseDTO uploadFileExcel(int idCurso, MultipartHttpServletRequest request)
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
		String nombreFichero = file.getOriginalFilename();
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
			ForCurso curso = forCursoExtendsMapper.selectByPrimaryKeyExtends(Long.parseLong(String.valueOf(idCurso)));
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Parseamos las inscripciones introducidas en el excel
				List<InscripcionItem> inscripcionList = parseExcelFile(datos, usuario, idCurso);

				// Buscamos si existe algun error en las inscripciones del excel
				for (InscripcionItem inscripcion : inscripcionList) {
					if (!inscripcion.getErrores().isEmpty()) {
						registrosErroneos++;
					}
				}

				// Insertamos en tabla For_inscripcion_Masiva el fichero que se ha parseado
				forInscripcionesmasivas.setIdinstitucion(curso.getIdinstitucion());
				forInscripcionesmasivas.setNombrefichero(nombreFichero);
				forInscripcionesmasivas.setFechamodificacion(new Date());
				forInscripcionesmasivas.setUsumodificacion(Long.valueOf(usuario.getIdusuario()));
				forInscripcionesmasivas.setIdcurso(Long.valueOf(idCurso));
				String numLineas = String.valueOf(inscripcionList.size());
				forInscripcionesmasivas.setNumerolineastotales(Long.valueOf(numLineas));
				String lineasCorrectas = String.valueOf(inscripcionList.size() - registrosErroneos);
				forInscripcionesmasivas.setInscripcionescorrectas(Long.valueOf(lineasCorrectas));

				LOGGER.info(
						"uploadFileExcel() / forInscripcionesmasivasMapper.insert(forInscripcionesmasivas) -> Entrada a forInscripcionesmasivasMapper para insertar el fichero parseado carga masiva de inscripciones");

				int result = forInscripcionesmasivasMapper.insert(forInscripcionesmasivas);

				LOGGER.info(
						"uploadFileExcel() / forInscripcionesmasivasMapper.insert(forInscripcionesmasivas) -> Salida a forInscripcionesmasivasMapper para insertar el fichero parseado carga masiva de inscripciones");

				// Si no hay errores se insertan las inscripciones nuevas
				if (registrosErroneos == 0) {

					try {
						for (InscripcionItem inscripcion : inscripcionList) {

							ForInscripcion inscripcionInsert = new ForInscripcion();
							inscripcionInsert.setFechabaja(null);
							inscripcionInsert.setFechamodificacion(new Date());
							inscripcionInsert.setFechasolicitud(new Date());
							inscripcionInsert.setIdcurso(Long.valueOf(inscripcion.getIdCurso()));
							inscripcionInsert.setIdestadoinscripcion(SigaConstants.INSCRIPCION_PENDIENTE);
							inscripcionInsert.setIdinstitucion(curso.getIdinstitucion());
							inscripcionInsert.setIdpersona(inscripcion.getIdPersona());
							inscripcionInsert.setUsumodificacion(usuario.getIdusuario().longValue());
							inscripcionInsert.setIdcargainscripcion(forInscripcionesmasivas.getIdcargainscripcion());
							if (null != inscripcion.getFormaPago()) {
								inscripcionInsert.setIdformapago(Long.parseLong(inscripcion.getFormaPago()));
							}

							LOGGER.info(
									"uploadFileExcel() / forInscripcionExtendsMapper.insert(inscripcionInsert) -> Entrada a forInscripcionExtendsMapper para insertar una inscripcion");

							result = forInscripcionExtendsMapper.insert(inscripcionInsert);

							LOGGER.info(
									"uploadFileExcel() / forInscripcionExtendsMapper.insert(inscripcionInsert) -> Salida a forInscripcionExtendsMapper para insertar una inscripcion");

							if (result == 0) {
								errores += "Error al insertar en cargas masivas";
								error.setDescription(errores);
								updateResponseDTO.setError(error);
							}

						}

					} catch (Exception e) {
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					}
					// Si hay algun error en alguna de ellas, no se inserta ninguna y se indica que
					// hay errores
				} else {

					for (InscripcionItem inscripcion : inscripcionList) {

						Hashtable<String, Object> e = new Hashtable<String, Object>();
						e = convertItemtoHash(inscripcion);
						// Guardar log
						datosLog.add(e);
						errores += inscripcion.getErrores();

					}

					error.setCode(400);
					error.setDescription("Hay errores en las inscripciones subidas");
				}

				// Generamos el fichero de errores
				byte[] bytesLog = ExcelHelper.createExcelBytes(SigaConstants.CAMPOSPLOGCURSO, datosLog);

				Long idFile = uploadFile(file.getBytes(), forInscripcionesmasivas, false, usuario);
				Long idLogFile = uploadFile(bytesLog, forInscripcionesmasivas, true, usuario);
				ForInscripcionesmasivas record = new ForInscripcionesmasivas();
				record.setIdfichero(idFile);
				record.setIdficherolog(idLogFile);
				record.setIdcargainscripcion(forInscripcionesmasivas.getIdcargainscripcion());
				forInscripcionesmasivasMapper.updateByPrimaryKeySelective(record);
			}

		}

		LOGGER.info("uploadFile() -> Salida al servicio para subir un archivo");
		updateResponseDTO.setStatus(SigaConstants.OK);
		error.setDescription(errores);

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Transactional(timeout=2400)
	public List<InscripcionItem> parseExcelFile(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario,
			int idCurso) throws BusinessException {

		List<InscripcionItem> inscripcionItemList = new ArrayList<InscripcionItem>();
		InscripcionItem inscripcionItem = null;
		ForCurso cursoRecibido = forCursoExtendsMapper
				.selectByPrimaryKeyExtends(Long.parseLong(String.valueOf(idCurso)));
		Short idInstitucion = cursoRecibido.getIdinstitucion();

		StringBuffer errorLinea = null;
		String codigoCurso = null;

		try {
			// Por cada línea del excell
			for (Hashtable<String, Object> hashtable : datos) {

				inscripcionItem = new InscripcionItem();
				inscripcionItem.setIdInstitucion(idInstitucion.toString());
				errorLinea = new StringBuffer();

				// Comprobacion si el codigo curso esta introducido es correcto
				if ((hashtable.get(SigaConstants.CODIGO_CURSO) != null
						&& !hashtable.get(SigaConstants.CODIGO_CURSO).toString().equals(""))) {

					ForCursoExample forCursoExample = new ForCursoExample();
					forCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdcursoEqualTo(Long.valueOf(idCurso));

					LOGGER.info(
							"parseExcelFile() / forCursoExtendsMapper.selectByExample(forCursoExample) -> Entrada a forCursoExtendsMapper para buscar el curso seleccionado");

					List<ForCurso> cursoList = forCursoExtendsMapper.selectByExample(forCursoExample);

					LOGGER.info(
							"parseExcelFile() / forCursoExtendsMapper.selectByExample(forCursoExample) -> Salida a forCursoExtendsMapper para buscar el curso seleccionado");

					if (null != cursoList && cursoList.size() > 0) {
						ForCurso curso = cursoList.get(0);
						codigoCurso = curso.getCodigocurso();

						// Si el curso introducido coincide con el seleccionado lo guardamos en el
						// objeto inscripcion
						if (codigoCurso.equals(hashtable.get(SigaConstants.CODIGO_CURSO).toString())) {
							inscripcionItem.setCodigoCurso(hashtable.get(SigaConstants.CODIGO_CURSO).toString());
							inscripcionItem.setIdCurso(Integer.valueOf(idCurso).toString());
							inscripcionItem.setNombreCurso(curso.getNombrecurso());
							// Si no existe indicamos que el codigo del curso debe ser introducido
						} else {
							inscripcionItem.setCodigoCurso("Error");
							errorLinea.append("Código del curso es erróneo.");
						}

					}
					// Si el campo esta vacio indicamos que este campo dee ser introducido
				} else {
					errorLinea.append("Es obligatorio introducir el código del curso.");
					inscripcionItem.setCodigoCurso("Error");
				}

				// Comprobacion si el metodo de pago esta introducido
				if ((hashtable.get(SigaConstants.FORMA_PAGO) != null
						&& !hashtable.get(SigaConstants.FORMA_PAGO).toString().equals(""))) {

					LOGGER.info(
							"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Entrada a pysFormapagoExtendsMapper para obtener los metodos de pago existentes");
					List<ComboItem> modosPagosList = pysFormapagoExtendsMapper
							.getWayToPayWithIdFormapago(usuario.getIdlenguaje());
					LOGGER.info(
							"generateExcelInscriptions() / pysFormapagoExtendsMapper.getWayToPay -> Salida de pysFormapagoExtendsMapper para obtener los metodos de pago existentes");

					boolean isModoPago = false;
					ComboItem modoPagoSelected = new ComboItem();

					// Comprobamos si el metodo de pago introducido es correcto
					for (ComboItem modoPago : modosPagosList) {
						if (modoPago.getLabel().equals(hashtable.get(SigaConstants.FORMA_PAGO))) {
							isModoPago = true;
							modoPagoSelected = modoPago;
						}
					}

					// Si el metodo de pago es correcto, se guarda
					if (isModoPago) {
						inscripcionItem.setFormaPago(modoPagoSelected.getValue());
						inscripcionItem.setFormaPagoNombre(hashtable.get(SigaConstants.FORMA_PAGO).toString());
						// Si no existe se indica que no es correcto
					} else {
						inscripcionItem.setFormaPago("Error");
						inscripcionItem.setFormaPagoNombre("Error");
						errorLinea.append("Es obligatorio decir la forma de pago del curso.");
					}

					// Si no se ha seleccionado ningún modo de pago se indica que el campo es
					// obligatorio
				} else {
					errorLinea.append("Es obligatorio decir la forma de pago del curso.");
					inscripcionItem.setFormaPago("Error");
				}

				// Comprobar que el nif esta introducido
				if (hashtable.get(SigaConstants.NIF) != null
						&& !hashtable.get(SigaConstants.NIF).toString().equals("")) {

					// Se comprueba si esta en cen_persona
					Long idPersona = getIdPersonaVerify((String) hashtable.get(SigaConstants.NIF), idInstitucion);
					inscripcionItem.setIdPersona(idPersona);

					// Si se encuentra debemos comprobar que este en cen_cliente en la institucion a
					// la que pertenece el curso
					if (inscripcionItem.getIdPersona() != null) {

						CenPersona cenPersona = cenPersonaMapper.selectByPrimaryKey(idPersona);

						String nombreCompleto = cenPersona.getNombre() + " " + cenPersona.getApellidos1();

						if (cenPersona.getApellidos2() != null) {
							nombreCompleto += " " + cenPersona.getApellidos2();
						}
						inscripcionItem.setNombrePersona(nombreCompleto);
						inscripcionItem.setNifPersona(cenPersona.getNifcif());

						if (inscripcionItem.getIdCurso() != null) {
							// Buscamos la institución del curso al que pertenece
							LOGGER.info(
									"saveInscripcion() / forCursoExtendsMapper.selectByPrimaryKeyExtends(idCurso) -> Entrada a forCursoExtendsMapper para recuperar el curso de la inscripcion");

							ForCurso curso = forCursoExtendsMapper
									.selectByPrimaryKeyExtends(Long.parseLong(inscripcionItem.getIdCurso()));

							LOGGER.info(
									"saveInscripcion() / forCursoExtendsMapper.selectByPrimaryKeyExtends(idCurso) -> Salida a forCursoExtendsMapper para recuperar el curso de la inscripcion");

							CenClienteExample cenClienteExample = new CenClienteExample();
							cenClienteExample.createCriteria().andIdinstitucionEqualTo(curso.getIdinstitucion())
									.andIdpersonaEqualTo(inscripcionItem.getIdPersona());

							List<CenCliente> cenClienteList = cenClienteExtendsMapper
									.selectByExample(cenClienteExample);

							// Si no se encuentra debemos añadirlo
							if (null == cenClienteList || cenClienteList.size() == 0) {

								CenCliente cenCliente = new CenCliente();
								cenCliente.setIdpersona(inscripcionItem.getIdPersona());
								cenCliente.setIdinstitucion(curso.getIdinstitucion());
								cenCliente.setFechaalta(new Date());
								cenCliente.setCaracter("P");
								cenCliente.setPublicidad(SigaConstants.DB_FALSE);
								cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
								// Para crear un cliente debemos rellenar comisiones, con que dato?
								cenCliente.setComisiones("0");
								cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
								cenCliente.setFechamodificacion(new Date());
								cenCliente.setUsumodificacion(usuario.getIdusuario());
								cenCliente.setIdlenguaje(usuario.getIdlenguaje());
								cenCliente.setExportarfoto(SigaConstants.DB_FALSE);

								LOGGER.info(
										"generateExcelInscriptions() / cenClienteMapper.insert() -> Entrada a cenClienteMapper para crear un nuevo colegiado");

								int responseCenCliente = 0;
								responseCenCliente = cenClienteMapper.insert(cenCliente);

								// Además debemos insertar en cen_nocolegiado
								CenNocolegiado cenNocolegiado = new CenNocolegiado();

								// Se pone a cero ya que al ser una persona física no tiene tipo ni sociedad
								cenNocolegiado.setTipo("0");
								cenNocolegiado.setIdpersona(inscripcionItem.getIdPersona());
								cenNocolegiado.setIdinstitucion(curso.getIdinstitucion());
								cenNocolegiado.setFechamodificacion(new Date());
								cenNocolegiado.setFechaBaja(null);
								cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
								// Para crear un nocoleagiado debemos rellenar campo sociedadsj, con que dato?
								cenNocolegiado.setSociedadsj("0");
								cenNocolegiado.setSociedadprofesional("0");

								LOGGER.info(
										"generateExcelInscriptions() / cenClienteMapper.insert() -> Salida de cenClienteMapper para crear un nuevo colegiado");

								cenNocolegiadoMapper.insert(cenNocolegiado);

								LOGGER.info(
										"generateExcelInscriptions() / cenColegiadoExtendsMapper.insert() -> Salida de cenColegiadoExtendsMapper para crear un nuevo colegiado");

								// Si se encuentra comprobamos que este en cen_colegiado
							} else {

								// Comprobamos que este colegiado en la institucion a la que pertenece el curso
								CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
								cenColegiadoExample.createCriteria().andIdinstitucionEqualTo(curso.getIdinstitucion())
										.andIdpersonaEqualTo(inscripcionItem.getIdPersona());

								List<CenColegiado> cenColegiadoList = cenColegiadoMapper
										.selectByExample(cenColegiadoExample);

								// Si no es colegiado comprobamos que este en cen_nocolegiado de la misma
								// institucion
								if (null == cenColegiadoList || cenColegiadoList.size() == 0) {

									CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
									cenNocolegiadoExample.createCriteria()
											.andIdinstitucionEqualTo(curso.getIdinstitucion())
											.andIdpersonaEqualTo(inscripcionItem.getIdPersona());

									List<CenNocolegiado> cenNocolegiadoList = cenNocolegiadoMapper
											.selectByExample(cenNocolegiadoExample);

									// Si no se encuentra debemos añadirlo como no colegiado a la institución a la
									// que pertenece el curso
									if (null == cenNocolegiadoList || cenNocolegiadoList.size() == 0) {

										CenNocolegiado cenNocolegiado = new CenNocolegiado();
										cenNocolegiado.setTipo("0");
										cenNocolegiado.setIdpersona(inscripcionItem.getIdPersona());
										cenNocolegiado.setIdinstitucion(curso.getIdinstitucion());
										cenNocolegiado.setFechamodificacion(new Date());
										cenNocolegiado.setFechaBaja(null);
										cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
										cenNocolegiado.setSociedadsj("0");
										// Este campo es obligatorio
										cenNocolegiado.setSociedadprofesional("0");

										LOGGER.info(
												"generateExcelInscriptions() / cenNocolegiadoMapper.insert() -> Salida de cenNocolegiadoMapper para crear un nuevo nocolegiado");

										int result = cenNocolegiadoMapper.insert(cenNocolegiado);

										LOGGER.info(
												"generateExcelInscriptions() / cenNocolegiadoMapper.insert() -> Salida de cenNocolegiadoMapper para crear un nuevo nocolegiado");

									}
								}
							}
						}

					} else {
						errorLinea.append("No esta registrada la persona en bbdd.");
						inscripcionItem.setNombrePersona("Error");
						inscripcionItem.setNifPersona(hashtable.get(SigaConstants.NIF).toString());
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
							.andIdpersonaEqualTo(inscripcionItem.getIdPersona())
							.andIdestadoinscripcionNotEqualTo(SigaConstants.INSCRIPCION_CANCELADA).andFechabajaIsNull();

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
					// Si no esta introducido indicamos que el campo debe ser obligatorio
				}

				inscripcionItem.setErrores(errorLinea.toString());
				inscripcionItemList.add(inscripcionItem);

			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			errorLinea = new StringBuffer();
			errorLinea.append("Se ha producido un error en BBDD contacte con su administrador");
		}

		return inscripcionItemList;

	}

	private Long uploadFile(byte[] excelBytes, ForInscripcionesmasivas forInscripcionesmasivas, boolean isLog,
			AdmUsuarios usuario) throws BusinessException {
		Date dateLog = new Date(0);
		LOGGER.info(dateLog + ":inicio.ForInscripcionesmasivas.uploadFile");
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
		genPropertiesExampleP.createCriteria().andParametroEqualTo("cen.cargaExcel.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathGF = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(pathGF);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.inscripciones");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.info(dateLog + ":fin.CargaMasivaDatosGFImpl.getDirectorioFichero");
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

	public FichaPersonaItem getPersonaVerify(Long idPersona, Short idInstitucion) {

		FichaPersonaItem personaSearch = null;

		if (idPersona == null) {
			throw new BusinessException("Campo nif obligatorio");
		} else if (idPersona != null) {
			personaSearch = cenPersonaExtendsMapper.getPersonaisColegiadoWithIdPersona(idPersona.toString(),
					idInstitucion.toString());
		}

		return personaSearch;
	}

	private Hashtable<String, Object> convertItemtoHash(InscripcionItem inscripcionItem) {
		Hashtable<String, Object> e = new Hashtable<String, Object>();

		if (inscripcionItem.getCodigoCurso() != null) {
			e.put(SigaConstants.CODIGO_CURSO, inscripcionItem.getCodigoCurso());
		}
		if (inscripcionItem.getNombreCurso() != null) {
			e.put(SigaConstants.NOMBRE_CURSO, inscripcionItem.getNombreCurso());
		}
		if (inscripcionItem.getFormaPago() != null) {
			e.put(SigaConstants.FORMA_PAGO, inscripcionItem.getFormaPagoNombre());
		}
		if (inscripcionItem.getNifPersona() != null) {
			e.put(SigaConstants.NIF, inscripcionItem.getNifPersona());
		}
		if (inscripcionItem.getNombrePersona() != null) {
			e.put(SigaConstants.NOMBRE_PERSONA, inscripcionItem.getNombrePersona());
		}
		if (inscripcionItem.getErrores() != null) {
			e.put(SigaConstants.ERRORES, inscripcionItem.getErrores());
		}
		return e;
	}

	@Override
	public CargaMasivaInscripcionesDTO getMassiveLoadInscriptions(HttpServletRequest request, String idCurso,
			boolean historico) {

		LOGGER.info(
				"getMassiveLoadInscriptions() -> Entrada al servicio para obtener los nombres de las plantillas de inscripciones masivas a un curso");

		CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO = new CargaMasivaInscripcionesDTO();
		List<CargaMasivaInscripcionesItem> cargaMasivaInscripciones = new ArrayList<CargaMasivaInscripcionesItem>();
		Error errores = new Error();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int response = 1;

		if (null != idInstitucion) {

			try {
				ForCurso curso = forCursoExtendsMapper.selectByPrimaryKeyExtends(Long.parseLong(idCurso));
				LOGGER.info(
						"getMassiveLoadInscriptions() / forInscripcionExtendsMapper.getCountIncriptions -> Entrada a forInscripcionExtendsMapper para obtener los nombres de las plantillas de inscripciones masivas a un curso");
				cargaMasivaInscripciones = forInscripcionesmasivasExtendsMapper.getMassiveLoadInscriptions(idCurso,
						String.valueOf(curso.getIdinstitucion()), historico);
				LOGGER.info(
						"getMassiveLoadInscriptions() / forInscripcionExtendsMapper.getCountIncriptions -> Salida de forInscripcionExtendsMapper para obtener los nombres de las plantillas de inscripciones masivas a un curso");

			} catch (Exception e) {
				response = 0;

			}

		}

		LOGGER.info(
				"getMassiveLoadInscriptions() -> Salida del servicio para obtener los nombres de las plantillas de inscripciones masivas a un curso");

		if (response == 0) {
			errores.setCode(400);
		} else {
			errores.setCode(200);
		}

		cargaMasivaInscripcionesDTO.setCargaMasivaInscripcionesItem(cargaMasivaInscripciones);
		cargaMasivaInscripcionesDTO.setError(errores);

		return cargaMasivaInscripcionesDTO;

	}

	@Override
	public UpdateResponseDTO deleteInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"deleteInscriptionsCourse() -> Salida del servicio para cambiar el estado a las inscripciones a cancelado");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean deleteInscripcion = false;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteInscriptionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteInscriptionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CargaMasivaInscripcionesItem fichero : cargaMasivaInscripcionesDTO
							.getCargaMasivaInscripcionesItem()) {

						ForInscripcionExample exampleInscripcion = new ForInscripcionExample();
						exampleInscripcion.createCriteria()
								.andIdcargainscripcionEqualTo(Long.valueOf(fichero.getIdCargaInscripcion()));

						LOGGER.info(
								"deleteInscriptionsCourse() / forInscripcionExtendsMapper.selectByExample() -> Entrada a forInscripcionExtendsMapper para obtener las inscripciones del fichero seleccionado");
						List<ForInscripcion> inscriptionList = forInscripcionExtendsMapper
								.selectByExample(exampleInscripcion);
						LOGGER.info(
								"deleteInscriptionsCourse() / forInscripcionExtendsMapper.selectByExample() -> Salida a forInscripcionExtendsMapper para obtener las inscripciones del fichero seleccionado");

						// Comprobamos que existan inscripciones del fichero seleccionado
						if (null != inscriptionList && inscriptionList.size() > 0) {

							for (ForInscripcion inscription : inscriptionList) {

								// Comprobamos que las inscripciones tengan el estado pendiente para cancelarlas
								if (inscription.getIdestadoinscripcion() == SigaConstants.INSCRIPCION_PENDIENTE) {

									LOGGER.info(
											"deleteInscriptionsCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper cambiar el estado a las inscripciones a cancelado");

									inscription.setFechamodificacion(new Date());
									inscription.setUsumodificacion(usuario.getIdusuario().longValue());
									inscription.setIdestadoinscripcion(SigaConstants.INSCRIPCION_CANCELADA);
									response = forInscripcionExtendsMapper.updateByPrimaryKey(inscription);

									deleteInscripcion = true;
									LOGGER.info(
											"deleteInscriptionsCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Salida a forInscripcionExtendsMapper cambiar el estado a las inscripciones a cancelado");
									// Si no estan en estado pendiente no se pueden cancelar
								} else {
									error.setCode(400);
									error.setDescription("Las inscripcions no estan en estado pendiente");
								}
							}
							// Si no hay es que el fichero tiene inscripciones erróneas
						} else {
							error.setDescription("Fichero con inscripciones erróneas.");
						}

						if (deleteInscripcion) {

							// Probar cuando nos indiquen que como quieren que mostremos ficheros eliminados
							ForInscripcionesmasivasExample forInscripcionesmasivasExample = new ForInscripcionesmasivasExample();
							forInscripcionesmasivasExample.createCriteria()
									.andIdcargainscripcionEqualTo(Long.valueOf(fichero.getIdCargaInscripcion()))
									.andIdinstitucionEqualTo(idInstitucion);

							LOGGER.info(
									"deleteInscriptionsCourse() / cenCargaMasivaExtendsMapper.selectByExample() -> Entrada a cenCargaMasivaExtendsMapper para obtener el fichero seleccionado");
							List<ForInscripcionesmasivas> cargaMasivaList = forInscripcionesmasivasExtendsMapper
									.selectByExample(forInscripcionesmasivasExample);
							LOGGER.info(
									"deleteInscriptionsCourse() / cenCargaMasivaExtendsMapper.selectByExample() -> Salida a cenCargaMasivaExtendsMapper para obtener el fichero seleccionado");

							if (null != cargaMasivaList && cargaMasivaList.size() > 0) {
								ForInscripcionesmasivas ficheroDelete = cargaMasivaList.get(0);
								ficheroDelete.setFechabaja(new Date());

								forInscripcionesmasivasExtendsMapper.updateByPrimaryKey(ficheroDelete);
							}

						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador.");
				}
			}
		}

		if (response == 1) {
			error.setCode(200);
			error.setDescription("Inscripciones canceladas correctamente.");

		}

		LOGGER.info(
				"deleteInscriptionsCourse() -> Salida del servicio para cambiar el estado a las inscripciones a cancelado");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Transactional(timeout=2400)
	public int createServiceCourse(ForCurso cursoItem, AdmUsuarios usuario, Short idInstitucion) {

		LOGGER.info("createServiceCourse() -> Entrada del servicio que crea los servicios para un curso");

		int response = 1;
		try {

			// PysServicios pysServicios = new PysServicios();
			// pysServicios.setFechamodificacion(new Date());
			// pysServicios.setIdinstitucion(idInstitucion);
			// pysServicios.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
			// pysServicios.setUsumodificacion(usuario.getIdusuario());
			// pysServicios.setDescripcion(cursoItem.getNombrecurso());
			// NewIdDTO idServicio =
			// pysServiciosExtendsMapper.selectMaxIdServicio(idInstitucion);
			// pysServicios.setIdservicio(Long.valueOf(idServicio.getNewId()));
			//
			// LOGGER.info(
			// "createServiceCourse() / pysServiciosMapper.insert() -> Entrada a
			// pysServiciosMapper para insertar un servicio");
			//
			// response = pysServiciosExtendsMapper.insert(pysServicios);

			LOGGER.info(
					"createServiceCourse() / pysServiciosMapper.insert() -> Salida a pysServiciosMapper para insertar un servicio");

			PysServiciosinstitucion pysServiciosinstitucion = new PysServiciosinstitucion();
			pysServiciosinstitucion.setFechamodificacion(new Date());
			pysServiciosinstitucion.setIdinstitucion(idInstitucion);
			pysServiciosinstitucion.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
			pysServiciosinstitucion.setUsumodificacion(usuario.getIdusuario());
			pysServiciosinstitucion.setDescripcion(cursoItem.getNombrecurso());
			pysServiciosinstitucion.setAutomatico("0");
			pysServiciosinstitucion.setIdservicio(cursoItem.getIdtiposervicio());
			NewIdDTO idServicioInstitucion = pysServiciosinstitucionExtendsMapper
					.selectMaxIdServicioinstitucion(idInstitucion, cursoItem.getIdtiposervicio());
			pysServiciosinstitucion.setIdserviciosinstitucion(Long.valueOf(idServicioInstitucion.getNewId()));
			pysServiciosinstitucion.setMomentocargo("P");
			pysServiciosinstitucion.setIniciofinalponderado("I");
			pysServiciosinstitucion.setSolicitarbaja("0");
			pysServiciosinstitucion.setSolicitaralta("0");
			pysServiciosinstitucion.setIdtipoiva(13); // Se pone el iva general por defecto

			LOGGER.info(
					"createServiceCourse() / pysServiciosinstitucionExtendsMapper.insert() -> Entrada a pysServiciosinstitucionExtendsMapper para insertar un servicio institucion");

			response = pysServiciosinstitucionExtendsMapper.insert(pysServiciosinstitucion);

			LOGGER.info(
					"createServiceCourse() / pysServiciosinstitucionExtendsMapper.insert() -> Salida a pysServiciosinstitucionExtendsMapper para insertar un servicio institucion");

			PysPreciosservicios pysPreciosservicios = new PysPreciosservicios();
			pysPreciosservicios.setFechamodificacion(new Date());
			pysPreciosservicios.setIdinstitucion(idInstitucion);
			pysPreciosservicios.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
			pysPreciosservicios.setUsumodificacion(usuario.getIdusuario());
			pysPreciosservicios.setDescripcion(cursoItem.getNombrecurso());
			pysPreciosservicios.setIdservicio(cursoItem.getIdtiposervicio());
			pysPreciosservicios.setIdserviciosinstitucion(pysServiciosinstitucion.getIdserviciosinstitucion());
			pysPreciosservicios.setIdperiodicidad(SigaConstants.PERIOCIDAD_1MES);
			BigDecimal valor = new BigDecimal("0");
			pysPreciosservicios.setValor(valor);
			pysPreciosservicios.setCriterios("SELECT IDINSTITUCION, IDPERSONA FROM CEN_CLIENTE WHERE IDINSTITUCION = "
					+ idInstitucion + " AND IDPERSONA = @IDPERSONA@");
			pysPreciosservicios.setPordefecto("1");
			NewIdDTO idPrecioServicio = pysPreciosserviciosExtendsMapper.selectMaxIdPrecioServicio(idInstitucion,
					cursoItem.getIdtiposervicio(), pysServiciosinstitucion.getIdserviciosinstitucion(),
					SigaConstants.PERIOCIDAD_1MES);
			pysPreciosservicios.setIdpreciosservicios(Short.valueOf(idPrecioServicio.getNewId()));

			LOGGER.info(
					"createServiceCourse() / pysPreciosserviciosExtendsMapper.insert() -> Entrada a pysPreciosserviciosExtendsMapper para insertar un precio servicio");

			response = pysPreciosserviciosExtendsMapper.insert(pysPreciosservicios);

			LOGGER.info(
					"createServiceCourse() / pysPreciosserviciosExtendsMapper.insert() -> Salida a pysPreciosserviciosExtendsMapper para insertar un precio servicio");

			cursoItem.setIdservicio(Long.valueOf(pysServiciosinstitucion.getIdserviciosinstitucion()));

			LOGGER.info(
					"createServiceCourse() / forCursoExtendsMapper.insert() -> Entrada a forCursoExtendsMapper para guardar idServicio creado al curso correspondiente");

			response = forCursoExtendsMapper.updateByPrimaryKey(cursoItem);

			LOGGER.info(
					"createServiceCourse() / forCursoExtendsMapper.insert() -> Salida a forCursoExtendsMapper para guardar idServicio creado al curso correspondiente");

		} catch (Exception e) {
			LOGGER.debug("Error BBDD");
			response = 0;
		}

		LOGGER.info("createServiceCourse() -> Salida del servicio que crea los servicios para un curso");

		return response;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO autovalidateInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"autovalidateInscriptionsCourse() -> Salida del servicio para cambiar el estado a las inscripciones a aprobado");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"autovalidateInscriptionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"autovalidateInscriptionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CargaMasivaInscripcionesItem fichero : cargaMasivaInscripcionesDTO
							.getCargaMasivaInscripcionesItem()) {

						ForInscripcionExample exampleInscripcion = new ForInscripcionExample();
						exampleInscripcion.createCriteria()
								.andIdcargainscripcionEqualTo(Long.valueOf(fichero.getIdCargaInscripcion()));

						LOGGER.info(
								"autovalidateInscriptionsCourse() / forInscripcionExtendsMapper.selectByExample() -> Entrada a forInscripcionExtendsMapper para obtener las inscripciones del fichero seleccionado");
						List<ForInscripcion> inscriptionList = forInscripcionExtendsMapper
								.selectByExample(exampleInscripcion);
						LOGGER.info(
								"autovalidateInscriptionsCourse() / forInscripcionExtendsMapper.selectByExample() -> Salida a forInscripcionExtendsMapper para obtener las inscripciones del fichero seleccionado");

						if (null != inscriptionList && inscriptionList.size() > 0) {
							// Primero comprobamos las plazas disponibles del curso
							CursoItem inscripcionesAprobadas = forInscripcionExtendsMapper
									.compruebaPlazasAprobadas(fichero.getIdCurso());
							if (null == inscripcionesAprobadas) {
								inscripcionesAprobadas = new CursoItem();
								inscripcionesAprobadas.setInscripciones("0");
							}
							ForCurso cursoEntidad = forCursoExtendsMapper
									.selectByPrimaryKeyExtends(Long.parseLong(fichero.getIdCurso()));
							Long plazasdisponibles = 0L;
							if (null != cursoEntidad) {
								if (null != cursoEntidad.getNumeroplazas()) {
									plazasdisponibles = cursoEntidad.getNumeroplazas()
											- Long.parseLong(inscripcionesAprobadas.getInscripciones());
								}
							}
							if (plazasdisponibles >= Long.parseLong(fichero.getNumeroLineasTotales())) {

								for (ForInscripcion inscription : inscriptionList) {

									if (inscription.getIdestadoinscripcion() == SigaConstants.INSCRIPCION_PENDIENTE) {

										PysPeticioncomprasuscripcion pysPeticioncomprasuscripcion = new PysPeticioncomprasuscripcion();
										pysPeticioncomprasuscripcion.setFechamodificacion(new Date());
										pysPeticioncomprasuscripcion.setIdinstitucion(inscription.getIdinstitucion());
										pysPeticioncomprasuscripcion.setUsumodificacion(usuario.getIdusuario());
										pysPeticioncomprasuscripcion.setTipopeticion("A");
										pysPeticioncomprasuscripcion.setIdestadopeticion(Short.valueOf("20"));
										NewIdDTO idPeticion = pysPeticioncomprasuscripcionExtendsMapper
												.selectMaxIdPeticion(inscription.getIdinstitucion());
										pysPeticioncomprasuscripcion.setIdpeticion(Long.valueOf(idPeticion.getNewId()));
										pysPeticioncomprasuscripcion.setIdpersona(inscription.getIdpersona());
										pysPeticioncomprasuscripcion.setFecha(new Date());
										pysPeticioncomprasuscripcion.setNumOperacion("1");

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

										response = pysPeticioncomprasuscripcionExtendsMapper
												.insert(pysPeticioncomprasuscripcion);

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Salida a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

										NewIdDTO idservicio = pysServiciosExtendsMapper.selectIdServicioByIdCurso(
												inscription.getIdinstitucion(), inscription.getIdcurso());
										NewIdDTO idserviciosinstitucion = pysServiciosinstitucionExtendsMapper
												.selectIdServicioinstitucionByIdServicio(idInstitucion,
														Long.valueOf(inscription.getIdcurso()));

										PysServiciossolicitados pysServiciossolicitados = new PysServiciossolicitados();
										pysServiciossolicitados.setFechamodificacion(new Date());
										pysServiciossolicitados.setIdinstitucion(inscription.getIdinstitucion());
										pysServiciossolicitados.setUsumodificacion(usuario.getIdusuario());
										pysServiciossolicitados.setAceptado("A");
										pysServiciossolicitados
												.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
										pysServiciossolicitados.setIdservicio(Long.valueOf(idservicio.getNewId()));
										pysServiciossolicitados.setIdserviciosinstitucion(
												Long.valueOf(idserviciosinstitucion.getNewId()));
										pysServiciossolicitados.setIdpeticion(
												Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
										pysServiciossolicitados.setIdpersona(inscription.getIdpersona());
										pysServiciossolicitados.setCantidad(1);
										pysServiciossolicitados.setIdformapago(Short.valueOf(inscription.getIdformapago().toString()));

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para insertar el servicio solicitado");

										response = pysServiciossolicitadosMapper.insert(pysServiciossolicitados);

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Salida a pysServiciossolicitadosMapper para insertar el servicio solicitado");

										PysSuscripcion pysSuscripcion = new PysSuscripcion();
										pysSuscripcion.setFechamodificacion(new Date());
										pysSuscripcion.setIdinstitucion(inscription.getIdinstitucion());
										pysSuscripcion.setUsumodificacion(usuario.getIdusuario());
										pysSuscripcion.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
										pysSuscripcion.setIdservicio(Long.valueOf(idservicio.getNewId()));
										pysSuscripcion.setIdserviciosinstitucion(
												Long.valueOf(idserviciosinstitucion.getNewId()));
										pysSuscripcion.setIdpeticion(
												Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
										pysSuscripcion.setIdpersona(inscription.getIdpersona());
										pysSuscripcion.setCantidad(1);
										pysSuscripcion.setIdformapago(Short.valueOf(inscription.getIdformapago().toString()));
										pysSuscripcion.setFechasuscripcion(new Date());

										CursoItem curso = forCursoExtendsMapper.searchCourseByIdcurso(
												inscription.getIdcurso().toString(), inscription.getIdinstitucion(),
												usuario.getIdlenguaje());

										pysSuscripcion.setDescripcion(curso.getNombreCurso());
										NewIdDTO idSuscripcion = pysSuscripcionExtendsMapper.selectMaxIdSuscripcion(
												inscription.getIdinstitucion(), Long.valueOf(idservicio.getNewId()),
												Long.valueOf(idserviciosinstitucion.getNewId()));
										pysSuscripcion.setIdsuscripcion(Long.valueOf(idSuscripcion.getNewId()));

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Entrada a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

										response = pysSuscripcionExtendsMapper.insert(pysSuscripcion);

										LOGGER.info(
												"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Salida a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

										LOGGER.info(
												"autovalidateInscriptionsCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper cambiar el estado a las inscripciones aprobada");

										// Guardamos el idPeticion y actualizamos la inscripcion a aprobada
										inscription.setFechamodificacion(new Date());
										inscription.setIdpeticionsuscripcion(Long.valueOf(idPeticion.getNewId()));
										inscription.setUsumodificacion(usuario.getIdusuario().longValue());
										inscription.setIdestadoinscripcion(SigaConstants.INSCRIPCION_APROBADA);
										response = forInscripcionExtendsMapper.updateByPrimaryKey(inscription);

										LOGGER.info(
												"autovalidateInscriptionsCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Salida a forInscripcionExtendsMapper cambiar el estado a las inscripciones a aprobada");

									} else {
										error.setDescription(
												"Las inscripciones no se pueden aprobar porque no están en estado pendiente.");

									}
								}
							} else {
								error.setDescription(
										"No existen plazas disponibles en el curso para validar las inscripciones");
							}
						} else {
							error.setDescription("Fichero con inscripciones erróneas.");
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
			error.setDescription("Inscripciones validadas correctamente.");
		}

		LOGGER.info(
				"autovalidateInscriptionsCourse() -> Salida del servicio para cambiar el estado a las inscripciones a aprobado");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public ComboDTO getTopicsCourse(HttpServletRequest request) {
		LOGGER.info("getTopicsCourse() -> Entrada al servicio para obtener los temas de un curso");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getTopicsCourse() / forTemacursoExtendsMapper.getTopicsCourse -> Entrada a forTemacursoExtendsMapper para obtener los temas de un curso");
				comboItems = forTemacursoExtendsMapper.getTopicsCourse(usuario.getIdlenguaje(),
						idInstitucion.toString());
				LOGGER.info(
						"getTopicsCourse() / forTemacursoExtendsMapper.getTopicsCourse -> Salida de forTemacursoExtendsMapper para obtener los temas de un curso");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTopicsCourse() -> Salida del servicio para obtener los temas de un curso");

		return comboDTO;
	}

	@Override
	public ComboDTO getTopicsSpecificCourse(HttpServletRequest request, String idCurso) {
		LOGGER.info("getTopicsSpecificCourse() -> Entrada al servicio para obtener los temas un curso según el curso");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTopicsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getTopicsSpecificCourse() / forTemacursoExtendsMapper.getTopicsSpecificCourse -> Entrada a forTemacursoExtendsMapper para obtener los temas de un curso según el curso");
				comboItems = forTemacursoExtendsMapper.getTopicsSpecificCourse(usuario.getIdlenguaje(),
						idInstitucion.toString(), idCurso);
				LOGGER.info(
						"getTopicsSpecificCourse() / forTemacursoExtendsMapper.getTopicsSpecificCourse -> Salida de forTemacursoExtendsMapper para obtener los temas de un curso según el curso");
			}

		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getTopicsSpecificCourse() -> Salida del servicio para obtener los temas de un curso según el curso");

		return comboDTO;
	}

	private String getCounterCourse(Short idInstitucion) {
		String contador = "";
		int response = 2;

		AdmContadorExample admContadorExample = new AdmContadorExample();
		admContadorExample.createCriteria().andIdmoduloEqualTo(SigaConstants.MODULO_CONTADOR)
				.andIdinstitucionEqualTo(idInstitucion);

		List<AdmContador> counterList = admContadorExtendsMapper.selectByExample(admContadorExample);

		if (null != counterList && counterList.size() > 0) {
			AdmContador counter = counterList.get(0);

			Integer longitudContador = counter.getLongitudcontador();
			String sufijo = counter.getSufijo();
			Long numContador = counter.getContador();

			counter.setContador(numContador + 1);

			LOGGER.info(
					"getCounterCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a forTemacursoCursoMapper para dar de baja a un tema de un curso");

			response = admContadorExtendsMapper.updateByPrimaryKey(counter);

			LOGGER.info(
					"getCounterCourse() / forTemacursoCursoMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a forTemacursoCursoMapper para dar de baja a un tema de un curso");

			String formatted = String.format("%0" + longitudContador + "d", numContador);

			contador = formatted + sufijo;
		}

		return contador;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO cancelCourse(CursoDTO cursoDTO, HttpServletRequest request) {

		LOGGER.info("cancelCourse() -> Entrada al servicio para cancelar las inscripciones y sesiones de un curso");

		int response = 2;
		int numCancelCourse = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cancelCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"cancelCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				String estadoFinalizado = String.valueOf(SigaConstants.FINALIZADO_CURSO);
				String estadoCancelado = String.valueOf(SigaConstants.CANCELADO_CURSO);

				try {
					for (CursoItem cursoItem : cursoDTO.getCursoItem()) {
						if (cursoItem.getIdEstado() != null && !cursoItem.getIdEstado().equals(estadoFinalizado)
								&& !cursoItem.getIdEstado().equals(estadoCancelado)) {

							// Se buscan las inscripciones que pertenecen al curso
							ForInscripcionExample forInscripcionExample = new ForInscripcionExample();
							forInscripcionExample.createCriteria().andIdcursoEqualTo(cursoItem.getIdCurso())
									.andIdestadoinscripcionNotEqualTo(SigaConstants.INSCRIPCION_CANCELADA)
									.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNull();

							LOGGER.info(
									"cancelCourse() / forInscripcionExtendsMapper.selectByExample() -> Entrada a forInscripcionExtendsMapper para obtener las inscripciones de un curso");

							List<ForInscripcion> inscripcionList = forInscripcionExtendsMapper
									.selectByExample(forInscripcionExample);

							LOGGER.info(
									"cancelCourse() / forInscripcionExtendsMapper.selectByExample() -> Salida de forInscripcionExtendsMapper para obtener las inscripciones de un curso");

							// Si el curso tiene inscripciones se cancelan todas
							if (null != inscripcionList && inscripcionList.size() > 0) {

								for (ForInscripcion inscripcion : inscripcionList) {

									inscripcion.setIdestadoinscripcion(SigaConstants.INSCRIPCION_CANCELADA);
									inscripcion.setUsumodificacion(usuario.getIdusuario().longValue());
									inscripcion.setFechamodificacion(new Date());

									LOGGER.info(
											"cancelCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper para cancelar las inscripciones de un curso");

									response = forInscripcionExtendsMapper.updateByPrimaryKey(inscripcion);

									LOGGER.info(
											"cancelCourse() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper para cancelar las inscripciones de un curso");

								}

							} else {
								error.setDescription("No existen inscripciones para este curso");
							}

							// Se buscan las sesiones que estan en estado Planificada y las cancelaremos
							List<EventoItem> sessionsList = ageEventoExtendsMapper.getSessionsCourseByState(
									SigaConstants.EVENTO_SESION, cursoItem.getIdCurso().toString(),
									idInstitucion.toString(), SigaConstants.EVENTO_PLANIFICADO,
									usuario.getIdlenguaje());

							// Si existen cambios el estado a cancelada
							if (null != sessionsList && sessionsList.size() > 0) {

								for (EventoItem sesion : sessionsList) {

									// Buscamos el evento session para cambiar su estado
									AgeEventoExample ageEventoExample = new AgeEventoExample();
									ageEventoExample.createCriteria()
											.andIdeventoEqualTo(Long.valueOf(sesion.getIdEvento()));

									LOGGER.info(
											"cancelCourse() / ageEventoExtendsMapper.selectByExample() -> Entrada a forInscripcionExtendsMapper para buscar las sesiones de un curso");

									List<AgeEvento> eventoList = ageEventoExtendsMapper
											.selectByExample(ageEventoExample);

									LOGGER.info(
											"cancelCourse() / ageEventoExtendsMapper.selectByExample() -> Salida a forInscripcionExtendsMapper para buscar las sesiones de un curso");

									if (null != eventoList && eventoList.size() > 0) {
										AgeEvento sesionCancel = eventoList.get(0);

										sesionCancel.setIdestadoevento(Long.valueOf(SigaConstants.EVENTO_CANCELADO));
										sesionCancel.setUsumodificacion(usuario.getIdusuario().longValue());
										sesionCancel.setFechamodificacion(new Date());

										LOGGER.info(
												"cancelCourse() / ageEventoExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper para cancelar las sesiones de un curso");

										response = ageEventoExtendsMapper.updateByPrimaryKey(sesionCancel);

										LOGGER.info(
												"cancelCourse() / ageEventoExtendsMapper.updateByPrimaryKey() -> Salida a forInscripcionExtendsMapper para cancelar las sesiones de un curso");

									}
								}

							} else {
								error.setDescription("No existen sesiones con estado planificado");
							}

							// Se cambia el curso a cancelado
							ForCursoExample forCursoExample = new ForCursoExample();
							forCursoExample.createCriteria().andIdcursoEqualTo(cursoItem.getIdCurso());

							LOGGER.info(
									"cancelCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper buscamos el curso");

							List<ForCurso> cursoList = forCursoExtendsMapper.selectByExample(forCursoExample);

							LOGGER.info(
									"cancelCourse() / forCursoExtendsMapper.selectByExample() -> Salida de forCursoExtendsMapper buscamos el curso");

							ForCurso cursoCancelado = cursoList.get(0);
							cursoCancelado.setIdestadocurso(Long.valueOf(SigaConstants.CANCELADO_CURSO));
							cursoCancelado.setUsumodificacion(usuario.getIdusuario().longValue());
							cursoCancelado.setFechamodificacion(new Date());

							LOGGER.info(
									"cancelCourse() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper cancelamos el curso");

							response = forCursoExtendsMapper.updateByPrimaryKey(cursoCancelado);

							LOGGER.info(
									"cancelCourse() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper cancelamos el curso");

							numCancelCourse += 1;

						} else {
							error.setDescription("El curso no se puede cancelar porque ya esta finalizado o cancelado");
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

			if (cursoDTO.getCursoItem() != null && cursoDTO.getCursoItem().size() == 1) {
				error.setDescription("Se ha cancelado el curso correctamente");
			} else {
				error.setDescription("Se han cancelado " + numCancelCourse + "/" + cursoDTO.getCursoItem().size()
						+ " cursos seleccionados");
			}

		} else if (response == 2) {

			if (cursoDTO.getCursoItem().size() == 1) {
				error.setDescription("No se puede cancelar el curso seleccionado porque esta finalizado o cancelado");
			} else if (cursoDTO.getCursoItem().size() == cursoDTO.getCursoItem().size() - numCancelCourse) {
				error.setDescription(
						"No se puede cancelar los cursos seleccionados porque estan finalizados o cancelados");
			} else {
				error.setDescription("Se han cancelado " + numCancelCourse + "/" + cursoDTO.getCursoItem().size()
						+ " cursos selecionados");
			}
		}

		LOGGER.info("cancelCourse() -> Salida del servicio para cancelar las inscripciones y sesiones de un curso");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO finishCourse(CursoDTO cursoDTO, HttpServletRequest request) {
		LOGGER.info("finishCourse() -> Entrada al servicio para finalizar un curso");

		int response = 2;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int numFinishCourse = 0;
		boolean faltaAlumnos = false;
		boolean cursoNoImpartido = false;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"finishCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"finishCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CursoItem cursoItem : cursoDTO.getCursoItem()) {

						ForCursoExample forCursoExample = new ForCursoExample();
						forCursoExample.createCriteria().andIdcursoEqualTo(cursoItem.getIdCurso())
								.andIdestadocursoEqualTo(Long.valueOf(SigaConstants.ESTADO_CURSO_IMPARTIDO));

						LOGGER.info(
								"finishCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para obtener el curso");

						List<ForCurso> forCursoList = forCursoExtendsMapper.selectByExample(forCursoExample);

						LOGGER.info(
								"finishCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para obtener el curso");

						if (null != forCursoList && forCursoList.size() > 0) {

							ForCurso curso = forCursoList.get(0);

							ForInscripcionExample forInscripcionExample = new ForInscripcionExample();
							forInscripcionExample.createCriteria().andIdcursoEqualTo(cursoItem.getIdCurso())
									.andIdestadoinscripcionEqualTo(SigaConstants.INSCRIPCION_APROBADA)
									.andIdinstitucionEqualTo(curso.getIdinstitucion()).andIdcalificacionIsNull();

							LOGGER.info(
									"finishCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para comprobar las calificaciones de los alumnos de un curso");

							List<ForInscripcion> forInscripcionList = forInscripcionExtendsMapper
									.selectByExample(forInscripcionExample);

							LOGGER.info(
									"finishCourse() / forCursoExtendsMapper.selectByExample() -> Entrada a forCursoExtendsMapper para comprobar las calificaciones de los alumnos de un curso");

							if (null != forInscripcionList && forInscripcionList.size() > 0) {
								faltaAlumnos = true;
								error.setDescription(
										"No se puede finalizar el curso poque todavía quedan alumnos por calificar");
							} else {
								// Si se ha calificado previamente a todos los alumnos se finaliza el curso y
								// Se generan los certificados para cada inscripcion
								InscripcionItem inscripcionItem = new InscripcionItem();
								inscripcionItem.setIdCurso(curso.getIdcurso().toString());
								inscripcionItem.setIdInstitucion(idInstitucion.toString());
								inscripcionItem
										.setIdEstadoInscripcion(String.valueOf(SigaConstants.INSCRIPCION_APROBADA));

								LOGGER.info(
										"finishCourse() / forInscripcionExtendsMapper.selectInscripciones() -> Entrada a forInscripcionExtendsMapper para buscar las inscripciones a las cuales se generara un certificado");

								List<InscripcionItem> inscriptionItemList = forInscripcionExtendsMapper
										.selectInscripciones(inscripcionItem, usuario.getIdlenguaje());

								LOGGER.info(
										"finishCourse() / forInscripcionExtendsMapper.selectInscripciones() -> Entrada a forInscripcionExtendsMapper para buscar las inscripciones a las cuales se generara un certificado");

								// Generamos los certificados para cada curso si existen inscripciones para este
								// curso
								if (null != inscriptionItemList && inscriptionItemList.size() > 0) {

									List<ForCertificadoscurso> listCertificadosCurso = null;
									InsertResponseDTO responseInsert = new InsertResponseDTO();

									for (InscripcionItem inscripcion : inscriptionItemList) {

										if (inscripcion.getEmitirCertificado() == null) {
											if (inscripcion.getEmitirCertificado().intValue() == SigaConstants.EMITIR_CERTIFICADO.intValue()) {
												// Añadimos los certificados si existen para el curso y calificacion
												ForCertificadoscursoExample certificadosCursoExample = new ForCertificadoscursoExample();
												certificadosCursoExample.createCriteria()
														.andIdcursoEqualTo(Long.valueOf(inscripcion.getIdCurso()))
														.andIdcalificacionEqualTo(inscripcion.getIdCalificacion());

												listCertificadosCurso = forCertificadosCursoExtendsMapper
														.selectByExample(certificadosCursoExample);

												if (null != listCertificadosCurso && listCertificadosCurso.size() > 0) {
													responseInsert = fichaInscripcionService
															.generarSolicitudCertificados(inscripcion, request);
												}
											}
										}

										// añadimos un registro curricular al usuario de la inscripción si la
										// calificación está aprobada
										if (null != inscripcion.getIdPersona()
												&& (inscripcion.getIdCalificacion() > 1)) {
											CenDatoscv recordInsert = new CenDatoscv();
											recordInsert.setFechamodificacion(new Date());
											recordInsert.setUsumodificacion(usuario.getIdusuario());
											recordInsert.setIdpersona(inscripcion.getIdPersona());
											recordInsert.setIdtipocv(Short.parseShort("1")); // Se pone como tipo de
																								// dato curricular el
																								// titulacion curso
											recordInsert
													.setIdinstitucion(Short.parseShort(inscripcion.getIdInstitucion()));
											recordInsert.setCertificado("1");
											recordInsert.setFechainicio(curso.getFechaimparticiondesde());
											recordInsert.setFechafin(curso.getFechaimparticionhasta());
											recordInsert.setDescripcion(curso.getNombrecurso());
											recordInsert.setIdinstitucion(idInstitucion);

											NewIdDTO idCvBD = cenDatosCvExtendsMapper.getMaxIdCv(
													String.valueOf(idInstitucion),
													inscripcion.getIdPersona().toString());
											if (idCvBD == null) {
												recordInsert.setIdcv(Short.parseShort("1"));
											} else {
												int idCv = Integer.parseInt(idCvBD.getNewId()) + 1;
												recordInsert.setIdcv(Short.parseShort("" + idCv));
											}
											response = cenDatosCvExtendsMapper.insertSelective(recordInsert);
										}

										if ((null != listCertificadosCurso && null != responseInsert.getError())
												&& responseInsert.getError().getCode() == 400) {
											response = 0;
											error.setCode(400);
											error.setDescription(
													"Se ha producido un error en BBDD contacte con su administrador");
										} else {
											response = 1;
											error.setCode(200);
											error.setDescription(
													"Se ha generado correctamente el certificado de la inscripcion");

										}
									}
								}

								// Generados los certificados finalizamos el curso
								curso.setIdestadocurso(SigaConstants.FINALIZADO_CURSO);
								curso.setUsumodificacion(usuario.getIdusuario().longValue());
								curso.setFechamodificacion(new Date());

								LOGGER.info(
										"finishCourse() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper para finalizar el curso");

								response = forCursoExtendsMapper.updateByPrimaryKey(curso);

								LOGGER.info(
										"finishCourse() / forCursoExtendsMapper.updateByPrimaryKey() -> Entrada a forCursoExtendsMapper para finalizar el curso");

								numFinishCourse += 1;

								// Si queda algún alumno por calificar no se puede finalizar el curso

							}

						} else {
							cursoNoImpartido = true;
							error.setDescription("El curso no esta en estado impartido y no se puede finalizar");
						}
					}

				} catch (Exception e) {
					LOGGER.error(e.getStackTrace());
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

			}
		}

		if (response == 1) {
			error.setCode(200);

			if (cursoDTO.getCursoItem() != null && cursoDTO.getCursoItem().size() == 1) {
				error.setDescription("Se ha finalizado el curso correctamente");
			} else {
				error.setDescription("Se han finalizado " + numFinishCourse + "/" + cursoDTO.getCursoItem().size()
						+ " cursos seleccionados");
			}

		} else if (response == 2) {

			if (cursoDTO.getCursoItem().size() == 1) {
				if (faltaAlumnos) {
					error.setDescription(
							"No se puede finalizar el curso seleccionado porque faltan alumnos por calificar");
				} else if (cursoNoImpartido) {
					error.setDescription(
							"No se puede finalizar el curso seleccionado porque no esta impartido o esta finalizado");
				}

			} else if (cursoDTO.getCursoItem().size() == cursoDTO.getCursoItem().size() - numFinishCourse) {
				error.setDescription(
						"No se pueden finalizar los cursos seleccionados porque estan finalizados o no estan impartidos");
			} else {
				error.setDescription("Se han finalizado " + numFinishCourse + "/" + cursoDTO.getCursoItem().size()
						+ " cursos selecionados");
			}
		}

		LOGGER.info("finishCourse() -> Salida del servicio para finalizar un curso");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public PreciosCursoDTO getPricesCourse(HttpServletRequest request, String idCurso) {
		LOGGER.info("getPricesCourse() -> Entrada al servicio para obtener los precios de un curso");

		PreciosCursoDTO preciosCursoDTO = new PreciosCursoDTO();
		List<PreciosCursoItem> preciosCursoItem = new ArrayList<PreciosCursoItem>();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getPricesCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getPricesCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ForCursoExample forCursoExample = new ForCursoExample();
					forCursoExample.createCriteria().andIdcursoEqualTo(Long.valueOf(idCurso));

					List<ForCurso> forCursoList = forCursoExtendsMapper.selectByExample(forCursoExample);

					if (null != forCursoList && forCursoList.size() > 0) {
						ForCurso curso = forCursoList.get(0);

						LOGGER.info(
								"getPricesCourse() / pysPreciosserviciosExtendsMapper.selectPricesCourse -> Entrada a pysPreciosserviciosExtendsMapper para obtener los precios de un curso");

						preciosCursoItem = pysPreciosserviciosExtendsMapper.selectPricesCourse(curso.getIdinstitucion(),
								curso.getIdtiposervicio(), usuario.getIdlenguaje(), curso.getNombrecurso());

						LOGGER.info(
								"getPricesCourse() / pysPreciosserviciosExtendsMapper.selectPricesCourse -> Salida de pysPreciosserviciosExtendsMapper para obtener los precios de un curso");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

			}
		}

		preciosCursoDTO.setPreciosCursoItem(preciosCursoItem);
		preciosCursoDTO.setError(error);

		LOGGER.info("getPricesCourse() -> Salida del servicio para obtener los precios de un curso");

		return preciosCursoDTO;
	}

	@Override
	public ComboDTO getQualificationsCourse(HttpServletRequest request) {
		LOGGER.info("getQualificationsCourse() -> Entrada al servicio para obtener las calificaciones de un curso");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getQualificationsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getQualificationsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getQualificationsCourse() / forCalificacionesExtendsMapper.getQualificationsCourse -> Entrada a forCalificacionesExtendsMapper para obtener  las calificaciones de un curso");
				comboItems = forCalificacionesExtendsMapper.getQualificationsCourse(usuario.getIdlenguaje());
				LOGGER.info(
						"getQualificationsCourse() / forCalificacionesExtendsMapper.getQualificationsCourse -> Salida de forCalificacionesExtendsMapper para obtener  las calificaciones de un curso");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getQualificationsCourse() -> Salida del servicio para obtener las calificaciones de un curso");

		return comboDTO;
	}

	@Override
	public CertificadoCursoDTO getTypesCertificatesCourse(HttpServletRequest request) {
		LOGGER.info("getTypesCertificatesCourse() -> Entrada al servicio para obtener los tipos de certificados");

		CertificadoCursoDTO certifcadoCursoDTO = new CertificadoCursoDTO();
		List<CertificadoCursoItem> certifcadosCursoItem = new ArrayList<CertificadoCursoItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"getTypesCertificatesCourse() / pysProductosinstitucionExtendsMapper.selectTypesCertificatesCourse -> Entrada a pysProductosinstitucionExtendsMapper para obtener los certificados de un curso");
			certifcadosCursoItem = pysProductosinstitucionExtendsMapper.selectTypesCertificatesCourse(idInstitucion);
			LOGGER.info(
					"getTypesCertificatesCourse() / pysProductosinstitucionExtendsMapper.selectTypesCertificatesCourse -> Salida de pysProductosinstitucionExtendsMapper para obtener los certificados de un curso");

		}

		certifcadoCursoDTO.setCertificadoCursoItem(certifcadosCursoItem);

		LOGGER.info("getTypesCertificatesCourse() -> Salida del servicio para obtener los tipos de certificados");

		return certifcadoCursoDTO;
	}

	@Override
	public CertificadoCursoDTO getCertificatesCourse(HttpServletRequest request, String idCurso) {

		LOGGER.info("getCertificatesCourse() -> Entrada al servicio para obtener los certificados de un curso");

		CertificadoCursoDTO certifcadoCursoDTO = new CertificadoCursoDTO();
		List<CertificadoCursoItem> certifcadosCursoItem = new ArrayList<CertificadoCursoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getCertificatesCourse() / forCertificadoscursoExtendsMapper.getCertificatesCourse -> Entrada a forCertificadoscursoExtendsMapper para obtener los certificados de un curso");

				ForCurso curso = forCursoExtendsMapper.selectByPrimaryKeyExtends(Long.parseLong(idCurso));
				certifcadosCursoItem = forCertificadoscursoExtendsMapper.getCertificatesCourse(idCurso,
						String.valueOf(curso.getIdinstitucion()), usuario.getIdlenguaje());

				LOGGER.info(
						"getCertificatesCourse() / forCertificadoscursoExtendsMapper.getCertificatesCourse -> Salida de forCertificadoscursoExtendsMapper para obtener los certificados de un curso");

			}
		}

		certifcadoCursoDTO.setCertificadoCursoItem(certifcadosCursoItem);

		LOGGER.info("getCertificatesCourse() -> Salida del servicio para obtener los certificados de un curso");

		return certifcadoCursoDTO;
	}

	@Override
	public InsertResponseDTO saveCertificateCourse(CertificadoCursoItem certificadoCursoItem,
			HttpServletRequest request) {

		LOGGER.info("saveCertificateCourse() -> Entrada al servicio para guardar el certificado de un curso");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveCertificateCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveCertificateCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Comprobamos que el certificado introducido no esta registrado en bbdd
					ForCertificadoscursoExample forCertificadoscursoExample = new ForCertificadoscursoExample();
					forCertificadoscursoExample.createCriteria().andFechabajaIsNull()
							.andIdcursoEqualTo(Long.valueOf(certificadoCursoItem.getIdCurso()))
							.andIdproductoEqualTo(Long.valueOf(certificadoCursoItem.getIdProducto()))
							.andIdtipoproductoEqualTo(Long.valueOf(certificadoCursoItem.getIdTipoProducto()))
							.andIdproductoinstitucionEqualTo(
									Long.valueOf(certificadoCursoItem.getIdProductoInstitucion()))
							.andIdcalificacionEqualTo(Long.valueOf(certificadoCursoItem.getIdCalificacion()));

					List<ForCertificadoscurso> certificateList = forCertificadoscursoExtendsMapper
							.selectByExample(forCertificadoscursoExample);

					// Si no esta registrado lo guardamos
					if (null == certificateList || certificateList.size() == 0) {

						ForCertificadoscurso forCertificadoscurso = new ForCertificadoscurso();
						forCertificadoscurso.setFechabaja(null);
						forCertificadoscurso.setFechamodificacion(new Date());
						forCertificadoscurso.setIdcalificacion(Long.valueOf(certificadoCursoItem.getIdCalificacion()));
						forCertificadoscurso.setIdcurso(Long.valueOf(certificadoCursoItem.getIdCurso()));
						forCertificadoscurso.setIdinstitucion(idInstitucion);
						forCertificadoscurso.setIdproducto(Long.valueOf(certificadoCursoItem.getIdProducto()));
						forCertificadoscurso.setIdproductoinstitucion(
								Long.valueOf(certificadoCursoItem.getIdProductoInstitucion()));
						forCertificadoscurso.setIdtipoproducto(Long.valueOf(certificadoCursoItem.getIdTipoProducto()));
						BigDecimal precio = new BigDecimal(certificadoCursoItem.getPrecio());
						forCertificadoscurso.setPrecio(precio);
						forCertificadoscurso.setUsumodificacion(usuario.getIdusuario().longValue());

						response = forCertificadoscursoExtendsMapper.insert(forCertificadoscurso);

					} else {
						error.setDescription("Registrado certificado con la misma calificación");
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
			error.setDescription("Certificado registrado correctamente");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("saveCertificateCourse() -> Entrada al servicio para guardar el certificado de un curso");

		return insertResponseDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO updateCertificatesCourse(CertificadoCursoDTO certifcadoCursoDTO,
			HttpServletRequest request) {

		LOGGER.info("updateCertificatesCourse() -> Entrada al servicio para modificar los certificados de un curso");

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
					"updateCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CertificadoCursoItem certificadoCursoItem : certifcadoCursoDTO.getCertificadoCursoItem()) {

						// Comprobamos que el certificado introducido no esta registrado en bbdd
						ForCertificadoscursoExample forCertificadoscursoExample = new ForCertificadoscursoExample();
						forCertificadoscursoExample.createCriteria().andFechabajaIsNull().andIdcertificadocursoEqualTo(
								Long.valueOf(certificadoCursoItem.getIdCertificadoCurso()));

						List<ForCertificadoscurso> certificateList = forCertificadoscursoExtendsMapper
								.selectByExample(forCertificadoscursoExample);

						if (null != certificateList && certificateList.size() > 0) {

							ForCertificadoscurso forCertificadoscurso = certificateList.get(0);
							forCertificadoscurso.setFechamodificacion(new Date());
							forCertificadoscurso
									.setIdcalificacion(Long.valueOf(certificadoCursoItem.getIdCalificacion()));
							forCertificadoscurso.setIdproducto(Long.valueOf(certificadoCursoItem.getIdProducto()));
							forCertificadoscurso.setIdproductoinstitucion(
									Long.valueOf(certificadoCursoItem.getIdProductoInstitucion()));
							forCertificadoscurso
									.setIdtipoproducto(Long.valueOf(certificadoCursoItem.getIdTipoProducto()));
							BigDecimal precio = new BigDecimal(certificadoCursoItem.getPrecio());
							forCertificadoscurso.setPrecio(precio);
							forCertificadoscurso.setUsumodificacion(usuario.getIdusuario().longValue());

							response = forCertificadoscursoExtendsMapper.updateByPrimaryKey(forCertificadoscurso);

						} else {
							error.setDescription("No se encuentra el registro en BBDD del certificado");
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
			error.setDescription("Certificados modificados correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateCertificatesCourse() -> Entrada al servicio para modificar el certificado de un curso");

		return updateResponseDTO;
	}

	@Override
	@Transactional(timeout=2400)
	public UpdateResponseDTO deleteCertificatesCourse(CertificadoCursoDTO certifcadoCursoDTO,
			HttpServletRequest request) {
		LOGGER.info("deleteCertificatesCourse() -> Entrada al servicio para eliminar los certificados de un curso");

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
					"deleteCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteCertificatesCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CertificadoCursoItem certificadoCursoItem : certifcadoCursoDTO.getCertificadoCursoItem()) {

						// Comprobamos que el certificado introducido no esta registrado en bbdd
						ForCertificadoscursoExample forCertificadoscursoExample = new ForCertificadoscursoExample();
						forCertificadoscursoExample.createCriteria().andFechabajaIsNull()
								.andIdcursoEqualTo(Long.valueOf(certificadoCursoItem.getIdCurso()))
								.andIdproductoEqualTo(Long.valueOf(certificadoCursoItem.getIdProducto()))
								.andIdtipoproductoEqualTo(Long.valueOf(certificadoCursoItem.getIdTipoProducto()))
								.andIdproductoinstitucionEqualTo(
										Long.valueOf(certificadoCursoItem.getIdProductoInstitucion()))
								.andIdcalificacionEqualTo(Long.valueOf(certificadoCursoItem.getIdCalificacion()));
						List<ForCertificadoscurso> certificateList = forCertificadoscursoExtendsMapper
								.selectByExample(forCertificadoscursoExample);

						if (null != certificateList && certificateList.size() > 0) {

							ForCertificadoscurso forCertificadoscurso = certificateList.get(0);
							forCertificadoscurso.setFechamodificacion(new Date());
							forCertificadoscurso.setUsumodificacion(usuario.getIdusuario().longValue());
							forCertificadoscurso.setFechabaja(new Date());

							response = forCertificadoscursoExtendsMapper.updateByPrimaryKey(forCertificadoscurso);

						} else {
							error.setDescription("No se encuentra el registro en BBDD del certificado");
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
			error.setDescription("Certificados eliminados correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteCertificatesCourse() -> Entrada al servicio para eliminar el certificado de un curso");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO releaseCourse(CursoDTO cursoDTO, HttpServletRequest request) {
		LOGGER.info("releaseCourse() -> Entrada al servicio para desanunciar los cursos");

		int response = 2;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int numReleaseCourse = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"releaseCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"releaseCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					for (CursoItem curso : cursoDTO.getCursoItem()) {

						ForCursoExample exampleCourse = new ForCursoExample();
						exampleCourse.createCriteria().andIdcursoEqualTo(Long.valueOf(curso.getIdCurso()))
								.andIdestadocursoEqualTo(SigaConstants.ANUNCIADO_CURSO)
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"releaseCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Entrada a forCursoExtendsMapper para comprobar si el curso esta anunciado");

						List<ForCurso> courseList = forCursoExtendsMapper.selectByExample(exampleCourse);

						LOGGER.info(
								"releaseCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Salida a forCursoExtendsMapper para comprobar si el curso esta anunciado");

						if (null != courseList && courseList.size() > 0) {
							ForCurso course = courseList.get(0);

							LOGGER.info(
									"releaseCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para desanunciar el curso");

							course.setFechamodificacion(new Date());
							course.setUsumodificacion(usuario.getIdusuario().longValue());
							course.setIdestadocurso(SigaConstants.ABIERTO_CURSO);
							response = forCursoExtendsMapper.updateByPrimaryKey(course);

							LOGGER.info(
									"releaseCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para desanunciar el curso");

							numReleaseCourse += 1;
						}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response == 1) {
					error.setCode(200);

					error.setDescription("Se han desanunciado " + numReleaseCourse + "/"
							+ cursoDTO.getCursoItem().size() + " cursos selecionados");
				} else if (response == 2) {

					if (cursoDTO.getCursoItem().size() == cursoDTO.getCursoItem().size() - numReleaseCourse) {
						error.setDescription(
								"No se puede desanunciar los cursos seleccionados porque su estado no es anunciado");
					} else {
						error.setDescription("Se han desanunciado " + numReleaseCourse + "/"
								+ cursoDTO.getCursoItem().size() + " cursos selecionados");
					}
				}
			}
		}

		LOGGER.info("releaseCourse() -> Salida del servicio para desanunciar los cursos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO announceCourse(CursoDTO cursoDTO, HttpServletRequest request) {
		LOGGER.info("announceCourse() -> Entrada al servicio para anunciar los cursos");

		int response = 2;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int numAnnounceCourse = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"announceCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"announceCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					for (CursoItem curso : cursoDTO.getCursoItem()) {

						ForCursoExample exampleCourse = new ForCursoExample();
						exampleCourse.createCriteria().andIdcursoEqualTo(Long.valueOf(curso.getIdCurso()))
								.andIdestadocursoEqualTo(SigaConstants.ABIERTO_CURSO)
								.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

						LOGGER.info(
								"announceCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Entrada a forCursoExtendsMapper para comprobar si el curso esta abierto");

						List<ForCurso> courseList = forCursoExtendsMapper.selectByExample(exampleCourse);

						LOGGER.info(
								"announceCourse() / forCursoExtendsMapper.selectByExample(exampleCourse) -> Salida a forCursoExtendsMapper para comprobar si el curso esta abierto");

						if (null != courseList && courseList.size() > 0) {
							ForCurso course = courseList.get(0);

							LOGGER.info(
									"announceCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Entrada a forCursoExtendsMapper para anunciar el curso");

							course.setFechamodificacion(new Date());
							course.setUsumodificacion(usuario.getIdusuario().longValue());
							course.setIdestadocurso(SigaConstants.ANUNCIADO_CURSO);
							response = forCursoExtendsMapper.updateByPrimaryKey(course);

							LOGGER.info(
									"announceCourse() / forCursoExtendsMapper.updateByPrimaryKey(event) -> Salida a forCursoExtendsMapper para anunciar el curso");

							numAnnounceCourse += 1;
						}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (response == 1) {
					error.setCode(200);

					error.setDescription("Se han anunciado " + numAnnounceCourse + "/" + cursoDTO.getCursoItem().size()
							+ " cursos selecionados");
				} else if (response == 2) {

					if (cursoDTO.getCursoItem().size() == cursoDTO.getCursoItem().size() - numAnnounceCourse) {
						error.setDescription(
								"No se puede anunciar los cursos seleccionados porque su estado no es abierto");
					} else {
						error.setDescription("Se han anunciado " + numAnnounceCourse + "/"
								+ cursoDTO.getCursoItem().size() + " cursos selecionados");
					}
				}
			}
		}

		LOGGER.info("announceCourse() -> Salida del servicio para para anunciar los cursos");

		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> generateExcelMasiveInscriptions(
			CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request) {

		LOGGER.info("downloadOriginalFile() -> Entrada al servicio para generar la plantilla de errores");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Extraer el path
		if (null != cargaMasivaInscripcionesDTO) {
			if (null != cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem()
					&& cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().size() > 0) {
				cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().get(0).getNombreFichero();

				String path = getDirectorioFichero(idInstitucion);
				if (cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().get(0).getNumeroLineasTotales()
						.equals(cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().get(0)
								.getInscripcionesCorrectas())) {
					path += File.separator + idInstitucion + "_"
							+ cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().get(0).getIdFichero() + "."
							+ SigaConstants.tipoExcelXls;
				} else {
					path += File.separator + "log_" + idInstitucion + "_"
							+ cargaMasivaInscripcionesDTO.getCargaMasivaInscripcionesItem().get(0).getIdFicheroLog()
							+ "." + SigaConstants.tipoExcelXls;
				}
				File file = new File(path);

				// Preparar la descarga
				InputStream fileStream = null;
				ResponseEntity<InputStreamResource> res = null;
				try {
					fileStream = new FileInputStream(file);
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

					headers.setContentLength(file.length());
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
							HttpStatus.OK);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				LOGGER.info("downloadOriginalFile() -> Salida del servicio para generar la plantilla de errores");

				return res;
			}
		}
		return null;
	}

	@Override
	public UpdateResponseDTO cancelSessionsCourse(EventoDTO eventoDTO, HttpServletRequest request) {

		LOGGER.info("cancelSessionsCourse() -> Entrada al servicio para cancelar las sesiones de un curso");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int numEventos = eventoDTO.getEventos().size();
		int numEventosCancelados = 0;
		int response = 2;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cancelSessionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"cancelSessionsCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					for (EventoItem eventoItem : eventoDTO.getEventos()) {
						if (eventoItem.getIdEstadoEvento() != null && eventoItem.getIdEstadoEvento()
								.equals(String.valueOf(SigaConstants.ESTADO_EVENTO_PLANIFICADO))) {

							// Se busca la sesion y la cancelamos
							AgeEvento sesionCancel = ageEventoExtendsMapper
									.selectByPrimaryKey(Long.valueOf(eventoItem.getIdEvento()));

							sesionCancel.setIdestadoevento(Long.valueOf(SigaConstants.EVENTO_CANCELADO));
							sesionCancel.setUsumodificacion(usuario.getIdusuario().longValue());
							sesionCancel.setFechamodificacion(new Date());

							LOGGER.info(
									"cancelSessionsCourse() / ageEventoExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper para cancelar las sesiones de un curso");

							response = ageEventoExtendsMapper.updateByPrimaryKey(sesionCancel);

							LOGGER.info(
									"cancelSessionsCourse() / ageEventoExtendsMapper.updateByPrimaryKey() -> Salida a forInscripcionExtendsMapper para cancelar las sesiones de un curso");

							// Eliminamos las notificaciones del evento
							AgeNotificacioneseventoExample ageNotificacioneseventoExample = new AgeNotificacioneseventoExample();
							ageNotificacioneseventoExample.createCriteria()
									.andIdeventoEqualTo(sesionCancel.getIdevento())
									.andIdinstitucionEqualTo(sesionCancel.getIdinstitucion());

							List<AgeNotificacionesevento> ageNotificacioneseventoList = ageNotificacioneseventoExtendsMapper
									.selectByExample(ageNotificacioneseventoExample);

							if (null != ageNotificacioneseventoList && ageNotificacioneseventoList.size() > 0) {

								for (AgeNotificacionesevento notification : ageNotificacioneseventoList) {

									// Eliminamos notificacion
									notification.setUsumodificacion(usuario.getIdusuario().longValue());
									notification.setFechamodificacion(new Date());
									notification.setFechabaja(new Date());

									LOGGER.info(
											"saveNotification() / ageNotificacioneseventoExtendsMapper.updateByPrimaryKeySelective(ageGeneracionnotificaciones) -> Entrada a ageNotificacioneseventoExtendsMapper para insertar cuando se generará una notificacion");

									response = ageNotificacioneseventoExtendsMapper
											.updateByPrimaryKeySelective(notification);

									LOGGER.info(
											"saveNotification() / ageNotificacioneseventoExtendsMapper.updateByPrimaryKeySelective(ageGeneracionnotificaciones) -> Salida a ageNotificacioneseventoExtendsMapper para insertar cuando se generará una notificacion");

									// Eliminamos la generacion de la notificacion que eliminamos
									AgeGeneracionnotificacionesExample ageGeneracionnotificacionesExample = new AgeGeneracionnotificacionesExample();
									ageGeneracionnotificacionesExample.createCriteria()
											.andIdnotificacioneventoEqualTo(notification.getIdnotificacionevento())
											.andIdeventoEqualTo(notification.getIdevento());

									List<AgeGeneracionnotificaciones> ageGeneracionnotificacionesList = ageGeneracionnotificacionesMapper
											.selectByExample(ageGeneracionnotificacionesExample);

									if (null != ageGeneracionnotificacionesList
											&& ageGeneracionnotificacionesList.size() > 0) {

										AgeGeneracionnotificaciones ageGeneracionnotificacion = ageGeneracionnotificacionesList
												.get(0);

										ageGeneracionnotificacion
												.setUsumodificacion(usuario.getIdusuario().longValue());
										ageGeneracionnotificacion.setFechamodificacion(new Date());
										ageGeneracionnotificacion.setFechabaja(new Date());

										LOGGER.info(
												"saveNotification() / ageGeneracionnotificacionesMapper.insert(ageGeneracionnotificaciones) -> Entrada a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

										response = ageGeneracionnotificacionesMapper
												.updateByPrimaryKeySelective(ageGeneracionnotificacion);

										LOGGER.info(
												"saveNotification() / ageGeneracionnotificacionesMapper.insert(ageGeneracionnotificaciones) -> Salida a ageGeneracionnotificacionesMapper para insertar cuando se generará una notificacion");

									}
								}
							}

							numEventosCancelados++;

						}
					}

				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				}

				if (numEventos == numEventosCancelados) {
					error.setCode(200);
					error.setDescription("La sesiones han sido canceladas correctamente");
				} else if (numEventosCancelados == 0) {
					if (numEventos == 1) {
						error.setDescription("No se puede cancelar la sesión porque su estado no es planificado");
					} else {
						error.setDescription("No se pueden cancelar las sesiones porque su estado no es planificado");

					}
					error.setCode(400);
				} else {
					error.setCode(200);
					error.setDescription("Solo se han cancelado las sesiones que tenían estado planificado");

				}
			}
		}

		LOGGER.info("cancelSessionsCourse() -> Salida del servicio para cancelar las sesiones de un curso");

		updateResponseDTO.setError(error);
		return updateResponseDTO;

	}

}
