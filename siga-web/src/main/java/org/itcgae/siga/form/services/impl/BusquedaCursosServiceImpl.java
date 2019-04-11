package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.AgeEventoExample;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForCursoExample;
import org.itcgae.siga.db.entities.ForEventoCurso;
import org.itcgae.siga.db.entities.ForTemacursoCurso;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.ForTemacursoCursoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadocursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTiposervicioCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForVisibilidadcursoExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaCursosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaCursosServiceImpl implements IBusquedaCursosService {

	private Logger LOGGER = Logger.getLogger(BusquedaCursosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ForEstadocursoExtendsMapper forEstadocursoExtendsMapper;

	@Autowired
	private ForVisibilidadcursoExtendsMapper forVisibilidadcursoExtendsMapper;

	@Autowired
	private ForTemacursoExtendsMapper forTemacursoExtendsMapper;

	@Autowired
	private ForCursoExtendsMapper forCursoExtendsMapper;
	
	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;
	
	@Autowired 
	private ForTiposervicioCursoExtendsMapper forTiposervicioCursoExtendsMapper;
	
	@Autowired
	private ForTemacursoCursoMapper forTemacursoCursoMapper;
	
	@Autowired 
	private FichaCursosServiceImpl fichaCursosServiceImpl;

	@Autowired
	AdmUsuariosMapper admUsuariosMapper;

	@Override
	public ComboDTO getVisibilidadCursos(HttpServletRequest request) {

		LOGGER.info("getVisibilidadCursos() -> Entrada al servicio para obtener la visibilidad de los cursos");

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
					"getVisibilidadCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getVisibilidadCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getVisibilidadCursos() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Entrada a forVisibilidadcursolExtendsMapper para obtener la visibilidad de los cursos");
				comboItems = forVisibilidadcursoExtendsMapper.distinctVisibilidadCurso(usuario.getIdlenguaje());
				LOGGER.info(
						"getVisibilidadCursos() / forVisibilidadcursolExtendsMapper.distinctVisibilidadCurso() -> Salida de forVisibilidadcursolExtendsMapper para obtener la visibilidad de los cursos");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getVisibilidadCursos() -> Salida del servicio para obtener la visibilidad de los cursos");

		return comboDTO;
	}

	@Override
	public ComboDTO getEstadosCursos(HttpServletRequest request) {

		LOGGER.info("getEstadosCursos() -> Entrada al servicio para obtener los tipos situacion de un colegiado");

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
					"getEstadosCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadosCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadosCursos() / forEstadocursolExtendsMapper.distinctEstadoCurso -> Entrada a forEstadocursolExtendsMapper para obtener los diferentes estados de un curso");
				comboItems = forEstadocursoExtendsMapper.distinctEstadoCurso(usuario.getIdlenguaje());
				LOGGER.info(
						"getEstadosCursos() / forEstadocursolExtendsMapper.distinctEstadoCurso -> Salida de forEstadocursolExtendsMapper para obtener los diferentes estados de un curso");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getEstadosCursos() -> Salida del servicio para obtener los diferentes estados de un curso");

		return comboDTO;
	}

	@Override
	public ComboDTO getTemasCursos(HttpServletRequest request) {

		LOGGER.info("getTemasCursos() -> Entrada al servicio para obtener los diferentes temas para un curso");

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
					"getTemasCursos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getTemasCursos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getTemasCursos() / forTemacursoExtendsMapper.distinctTemaCurso() -> Entrada a forTemacursoExtendsMapper para obtener los diferentes temas para un curso");
				comboItems = forTemacursoExtendsMapper.distinctTemaCurso(idInstitucion,usuario.getIdlenguaje());
				LOGGER.info(
						"getTemasCursos() / forTemacursoExtendsMapper.distinctTemaCurso() -> Salida de forTemacursoExtendsMapper para obtener los diferentes temas para un curso");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTemasCursos() -> Salida del servicio para obtener los temas para un curso");

		return comboDTO;
	}

	@Override
	public CursoDTO searchCurso(CursoItem cursoItem, HttpServletRequest request) {

		LOGGER.info("searchCurso() -> Entrada al servicio para obtener cursos");

		CursoDTO cursoDTO = new CursoDTO();
		List<CursoItem> cursoItemList = new ArrayList<CursoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);

		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.info(
					"searchCurso() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"searchCurso() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				if (letrado.equalsIgnoreCase("S")) {
					
					LOGGER.info(
							"searchCurso() / forCursoExtendsMapper.selectCursosIsColegiado() -> Entrada a forCursoExtendsMapper para obtener los cursos");
					
					cursoItemList = forCursoExtendsMapper.selectCursosIsColegiado(idInstitucion, cursoItem, usuario.getIdlenguaje());
					
					LOGGER.info(
							"searchCurso() / forCursoExtendsMapper.selectCursosIsColegiado() -> Entrada a forCursoExtendsMapper para obtener los cursos");
					
				}else {
					LOGGER.info(
							"searchCurso() / forCursoExtendsMapper.selectCursos() -> Entrada a forCursoExtendsMapper para obtener los cursos");
					
					cursoItemList = forCursoExtendsMapper.selectCursos(idInstitucion, cursoItem, usuario.getIdlenguaje());
					
					LOGGER.info(
							"searchCurso() / forCursoExtendsMapper.selectCursos() -> Entrada a forCursoExtendsMapper para obtener los cursos");
				}
				
				
				
				cursoDTO.setCursoItem(cursoItemList);

				if (cursoItemList == null || cursoItemList.size() == 0) {

					LOGGER.warn(
							"searchCurso() / forCursoExtendsMapper.selectCursos() -> No existen cursos para el filtro introducido");
				}

			}
		}

		return cursoDTO;
	}

	@Override
	public int archivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request) {

		LOGGER.info("archivarCursos() -> Entrada al servicio para archivar cursos");

		List<Long> arrayIds = new ArrayList<>();
		int resultado = 0;

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String idInstitucionToString = String.valueOf(idInstitucion);

		// Para obtener los ids de los cursos a actualizar
		for (CursoItem curso : listCursoItem) {

			// Añadimos a la lista de ids únicamente los ids de los cursos que pueden
			// archivarse
			if (idInstitucionToString.equals(curso.getIdInstitucion())
					&& (SigaConstants.CURSO_SIN_ARCHIVAR == curso.getFlagArchivado())
					&& (SigaConstants.ESTADO_CURSO_CANCELADO.equals(curso.getIdEstado())
							|| SigaConstants.ESTADO_CURSO_FINALIZADO.equals(curso.getIdEstado()))) {
				arrayIds.add(curso.getIdCurso());
			}
		}

		// Si no hay curso que cumpla las condiciones para archivar devolvemos 0
		if (arrayIds.isEmpty()) {
			return 0;
		} else {
			// Entidad que se va a rellenar con los valores a actualizar
			ForCurso record = new ForCurso();

			record.setFlagarchivado((short) 1); // estado archivado

			// Obtenemos el usuario para setear el campo "usumodificiacion"
			String dniUser = UserTokenUtils.getDniFromJWTToken(token);
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"archivarCursos() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);

			AdmUsuarios usuario = usuarios.get(0);

			if (usuario == null) {
				LOGGER.warn(
						"archivarCursos() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0; // Devolvemos 0 cursos archivados porque no se va a poder realizar el update al
							// no haber recuperado al usuario
			} else {
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
			}

			record.setFechamodificacion(new Date()); // seteamos la fecha de modificación

			ForCursoExample example = new ForCursoExample();
			example.createCriteria().andIdcursoIn(arrayIds);

			LOGGER.info(
					"archivarCursos() / forCursoExtendsMapper.updateByExampleSelective() -> Entrada a forCursoExtendsMapper para invocar a updateByExampleSelective para actualizar cursos según los criterios establecidos");
			resultado = forCursoExtendsMapper.updateByExampleSelective(record, example);
		}

		return resultado;
	}

	@Override
	public int desarchivarCursos(List<CursoItem> listCursoItem, HttpServletRequest request) {
		LOGGER.info("desarchivarCursos() -> Entrada al servicio para desarchivar cursos");

		List<Long> arrayIds = new ArrayList<>();
		int resultado = 0;

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		String idInstitucionToString = String.valueOf(idInstitucion);

		// Para obtener los ids de los cursos a actualizar
		for (CursoItem curso : listCursoItem) {

			// Añadimos a la lista de ids únicamente los ids de los cursos que pueden
			// desarchivarse
			if (idInstitucionToString.equals(curso.getIdInstitucion())
					&& (SigaConstants.CURSO_ARCHIVADO == curso.getFlagArchivado())) {
				arrayIds.add(curso.getIdCurso());
			}
		}

		// Si no hay curso que cumpla las condiciones para desarchivar devolvemos 0
		if (arrayIds.isEmpty()) {
			return 0;
		} else {

			// Entidad que se va a rellenar con los valores a actualizar
			ForCurso record = new ForCurso();

			record.setFlagarchivado((short) 0); // estado desarchivado

			// Obtenemos el usuario para setear el campo "usumodificiacion"
			String dniUser = UserTokenUtils.getDniFromJWTToken(token);
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);

			LOGGER.info(
					"desarchivarCursos() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);

			AdmUsuarios usuario = usuarios.get(0);

			if (usuario == null) {
				LOGGER.warn(
						"desarchivarCursos() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0; // Devolvemos 0 cursos desarchivados porque no se va a poder realizar el update
							// al no haber recuperado al usuario
			} else {
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
			}

			record.setFechamodificacion(new Date()); // seteamos la fecha de modificación

			ForCursoExample example = new ForCursoExample();
			example.createCriteria().andIdcursoIn(arrayIds);

			LOGGER.info(
					"desarchivarCursos() / forCursoExtendsMapper.updateByExampleSelective() -> Entrada a forCursoExtendsMapper para invocar a updateByExampleSelective para actualizar cursos según los criterios establecidos");
			resultado = forCursoExtendsMapper.updateByExampleSelective(record, example);

		}

		return resultado;
	}

	@Override
	public InsertResponseDTO saveCourses(List<CursoItem> listCursoItem, HttpServletRequest request) {

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

					for (CursoItem cursoItem : listCursoItem) {
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

							// response = forEventoCursoMapper.insert(forEventoCurso);

							// Se da de alta al evento creado para ese curso

							AgeEventoExample exampleEvento = new AgeEventoExample();
							exampleEvento.createCriteria()
									.andIdeventoEqualTo(Long.valueOf(cursoItem.getIdEventoInicioInscripcion()))
									.andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNotNull();

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							// List<AgeEvento> eventoList =
							// ageEventoExtendsMapper.selectByExample(exampleEvento);

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) -> Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de alta");

							// if (null != eventoList && eventoList.size() > 0) {
							// AgeEvento evento = eventoList.get(0);

							// evento.setIdevento(Long.valueOf(cursoItem.getIdEventoInicioInscripcion()));
							// evento.setFechabaja(null);
							// evento.setFechamodificacion(new Date());
							// evento.setUsumodificacion(usuario.getIdusuario().longValue());

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

							// response = ageEventoExtendsMapper.updateByPrimaryKey(evento);

							LOGGER.info(
									"saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada a ageEventoExtendsMapper para dar de alta al evento");

						}

					}

					// Si existe idEventoInscripcion se debe guardar la relacion entre el evento y
					// el curso
					// if (cursoItem.getIdEventoFinInscripcion() != null) {
					//
					// ForEventoCurso forEventoCurso = new ForEventoCurso();
					// forEventoCurso.setFechabaja(null);
					// forEventoCurso.setFechamodificacion(new Date());
					// forEventoCurso.setIdcurso(forCursoInsert.getIdcurso());
					// forEventoCurso.setIdevento(Long.valueOf(cursoItem.getIdEventoFinInscripcion()));
					// forEventoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
					// forEventoCurso.setIdinstitucion(idInstitucion);
					//
					// response = forEventoCursoMapper.insert(forEventoCurso);
					//
					// // Se da de alta al evento creado para ese curso
					//
					// AgeEventoExample exampleEvento = new AgeEventoExample();
					// exampleEvento.createCriteria()
					// .andIdeventoEqualTo(Long.valueOf(cursoItem.getIdEventoFinInscripcion()))
					// .andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andFechabajaIsNotNull();
					//
					// LOGGER.info(
					// "saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) ->
					// Entrada a ageEventoExtendsMapper para buscar el evento que debemos dar de
					// alta");
					//
					// List<AgeEvento> eventoList =
					// ageEventoExtendsMapper.selectByExample(exampleEvento);
					//
					// LOGGER.info(
					// "saveCourse() / ageEventoExtendsMapper.selectByExample(exampleEvento) ->
					// Salida a ageEventoExtendsMapper para buscar el evento que debemos dar de
					// alta");
					//
					// if (null != eventoList && eventoList.size() > 0) {
					// AgeEvento evento = eventoList.get(0);
					//
					// evento.setIdevento(Long.valueOf(cursoItem.getIdEventoFinInscripcion()));
					// evento.setFechabaja(null);
					// evento.setFechamodificacion(new Date());
					// evento.setUsumodificacion(usuario.getIdusuario().longValue());
					//
					// LOGGER.info(
					// "saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada
					// a ageEventoExtendsMapper para dar de alta al evento");
					//
					// response = ageEventoExtendsMapper.updateByPrimaryKey(evento);
					//
					// LOGGER.info(
					// "saveCourse() / ageEventoExtendsMapper.updateByPrimaryKey(evento) -> Entrada
					// a ageEventoExtendsMapper para dar de alta al evento");
					//
					// }
					//
					// }
					// }

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
	public InsertResponseDTO duplicateCourse(CursoItem cursoItem, HttpServletRequest request) {

		LOGGER.info("duplicateCourse() -> Entrada al servicio para insertar un curso");

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
					"duplicateCourse() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"duplicateCourse() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					// Insertamos curso en la tabla For_Curso
					forCursoInsert.setIdinstitucion(idInstitucion);
					forCursoInsert.setUsumodificacion(usuario.getIdusuario().longValue());
					forCursoInsert.setFechamodificacion(new Date());
					forCursoInsert.setNombrecurso(cursoItem.getNombreCurso());
					forCursoInsert.setIdvisibilidadcurso(Short.valueOf(cursoItem.getIdVisibilidad()));
					forCursoInsert.setDescripcion(cursoItem.getDescripcionEstado());
					forCursoInsert.setFechainscripciondesde(cursoItem.getFechaInscripcionDesdeDate());
					forCursoInsert.setFechainscripcionhasta(cursoItem.getFechaInscripcionHastaDate());
					forCursoInsert.setIdestadocurso(Long.parseLong(SigaConstants.ESTADO_CURSO_ABIERTO));
					if (cursoItem.getPlazasDisponibles() != null) {
						forCursoInsert.setNumeroplazas(Long.valueOf(cursoItem.getPlazasDisponibles()));
					}
					if (cursoItem.getMinimoAsistencia() != null) {
						forCursoInsert.setMinimoasistencia(Long.valueOf(cursoItem.getMinimoAsistencia()));
					}
					forCursoInsert.setFlagarchivado(Short.valueOf("0"));
					forCursoInsert.setLugar(cursoItem.getLugar());
					forCursoInsert.setAutovalidacioninscripcion(cursoItem.getAutovalidacionInscripcion().shortValue());
					forCursoInsert.setEncuestasatisfaccion(cursoItem.getEncuesta());
					forCursoInsert.setInformacionadicional(cursoItem.getAdicional());
					forCursoInsert.setDocumentacionadjunta(cursoItem.getAdjunto());

					forCursoInsert.setIdtiposervicio(cursoItem.getTipoServicios() == null ? null : Long.parseLong(cursoItem.getTipoServicios()));
					
					// Obtenemos el código del curso
					codigo = getCounterCourse(idInstitucion);
					forCursoInsert.setCodigocurso(codigo);

					LOGGER.info(
							"duplicateCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Entrada a forCursoExtendsMapper para insertar un curso");
					
					response = forCursoExtendsMapper.insert(forCursoInsert);
					
					LOGGER.info(
							"duplicateCourse() / forCursoExtendsMapper.insert(forCursoInsert) -> Salida a forCursoExtendsMapper para insertar un curso");

					// Si existe tiposervicio, se guarda en la tabla TipoServicios
//					if (cursoItem.getTipoServicios() != null && cursoItem.getTipoServicios().size() > 0) {
//
//						for (ComboItem servicio : cursoItem.getTipoServicios()) {
//
//							ForTiposervicioCurso forTipoServicioCurso = new ForTiposervicioCurso();
//							forTipoServicioCurso.setFechabaja(null);
//							forTipoServicioCurso.setFechamodificacion(new Date());
//							forTipoServicioCurso.setIdcurso(forCursoInsert.getIdcurso());
//							forTipoServicioCurso.setIdinstitucion(idInstitucion);
//							forTipoServicioCurso.setIdttiposervicio(Long.valueOf(servicio.getValue()));
//							forTipoServicioCurso.setUsumodificacion(usuario.getIdusuario().longValue());
//
//							response = forTiposervicioCursoExtendsMapper.insert(forTipoServicioCurso);
//
//						}
//					}

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
				
				} catch (Exception e) {
					response = 0;
				}
				if (response == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				} else {
					error.setCode(200);

					error.setDescription("Se ha dado de alta el curso con el código " + codigo + " correctamente. ");
					insertResponseDTO.setId(Long.toString(forCursoInsert.getIdcurso()));
					insertResponseDTO.setStatus(codigo);
					insertResponseDTO.setError(error);

					response = fichaCursosServiceImpl.createServiceCourse(forCursoInsert, usuario, idInstitucion);

					if (response == 0) {
						error.setCode(400);
						error.setDescription("Se ha producido un error en BBDD contacte con su administrador");

					} else {
						error.setCode(200);
						insertResponseDTO.setError(error);
					}
				}
			}
		}
		LOGGER.info("saveCourse() -> Salida del servicio para insertar un curso");

		return insertResponseDTO;
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
					"getCounterCourse() / admContadorExtendsMapper.updateByPrimaryKey(counter) -> Entrada a admContadorExtendsMapper para obtener el codigo del curso");

			response = admContadorExtendsMapper.updateByPrimaryKey(counter);

			LOGGER.info(
					"getCounterCourse() / admContadorExtendsMapper.updateByPrimaryKey(counter) -> Salida a admContadorExtendsMapper para obtener el codigo del curso");

			String formatted = String.format("%0" + longitudContador + "d", numContador);

			contador = formatted + sufijo;
		}

		return contador;
	}

}
