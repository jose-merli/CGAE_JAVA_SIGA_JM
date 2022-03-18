package org.itcgae.siga.form.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.ForCambioinscripcion;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.ForCambioinscripcionMapper;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadoinscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysPreciosserviciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysSuscripcionExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaInscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaInscripcionServiceImpl implements IBusquedaInscripcionService {

	private Logger LOGGER = Logger.getLogger(BusquedaInscripcionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ForEstadoinscripcionExtendsMapper forEstadoInscripcionExtendsMapper;

	@Autowired
	private ForInscripcionExtendsMapper forInscripcionExtendsMapper;

	@Autowired
	private ForCambioinscripcionMapper forCambioInscripcionMapper;

	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private ForCursoExtendsMapper forCursoExtendsMapper;

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

	@Override
	public ComboDTO getEstadosInscripcion(HttpServletRequest request) {

		LOGGER.info("getEstadosInscripcion() -> Entrada al servicio para obtener los estados de una inscripcion");

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
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadosInscripcion() / forInscripcionExtendsMapper.distinctEstadoInscripcion -> Entrada a forInscripcionExtendsMapper para obtener los diferentes estados de una inscripcion");
				comboItems = forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion(usuario.getIdlenguaje());
				LOGGER.info(
						"getEstadosInscripcion() / forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion -> Salida de forEstadoInscripcionExtendsMapper para obtener los diferentes estados de una inscripcion");

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getEstadosInscripcion() -> Salida del servicio para obtener los diferentes estados de una inscripcion");

		return comboDTO;
	}

	@Override
	public InscripcionDTO searchInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("searchInscripcion() -> Entrada al servicio para obtener inscripciones");

		String token = request.getHeader("Authorization");
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		List<CenPersona> listCenPersona;
		InscripcionDTO inscripcionDTO = new InscripcionDTO();
		List<InscripcionItem> inscripcionItemList = new ArrayList<InscripcionItem>();

		if (null != idInstitucion) {
		
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				if (letrado.equalsIgnoreCase("S")) {
					CenPersonaExample cenPersonaExample = new CenPersonaExample();
					cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
					listCenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
					if (!listCenPersona.isEmpty()) {
						CenPersona cenPersona = listCenPersona.get(0);
						inscripcionItem.setIdPersona(cenPersona.getIdpersona());
					}
				} else {
					inscripcionItem.setIdInstitucion(idInstitucion.toString());
				}
				inscripcionItemList = forInscripcionExtendsMapper.selectInscripciones(inscripcionItem, usuario.getIdlenguaje());
				inscripcionDTO.setInscripcionItem(inscripcionItemList);
			}
		}

		if (inscripcionItemList.isEmpty() || inscripcionItemList == null) {
			LOGGER.warn(
					"searchInscripcion() / forInscripcionExtendsMapper.selectInscripciones() -> No existen inscripciones para el filtro introducido");
		}

		return inscripcionDTO;
	}

	@Override
	public ComboDTO getCalificacionesEmitidas(HttpServletRequest request) {

		LOGGER.info("getCalificacionesEmitidas() -> Entrada al servicio para obtener las calificaciones emitidas");

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
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCalificacionesEmitidas() / forInscripcionExtendsMapper.getCalificacionesEmitidas -> Entrada a forInscripcionExtendsMapper para obtener las diferentes calificaciones");
				comboItems = forInscripcionExtendsMapper.getCalificacionesEmitidas(usuario.getIdlenguaje());
				LOGGER.info(
						"getCalificacionesEmitidas() / forEstadoInscripcionExtendsMapper.getCalificacionesEmitidas -> Salida de forEstadoInscripcionExtendsMapper para obtener las diferentes calificaciones");

				// TODO Quitar en un futuro
				
				ComboItem comboItem1 = new ComboItem();
				comboItem1.setLabel("Sin calificación");
				comboItem1.setValue("-2");
				comboItems.add(comboItem1);
				
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("Todos");
				comboItem.setValue("-1");
				comboItems.add(comboItem);

				
				
				
			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info(
				"getCalificacionesEmitidas() -> Salida del servicio para obtener los diferentes estados de una inscripcion");

		return comboDTO;
	}

	@Override
	public Object updateEstado(List<InscripcionItem> listInscripcionItem, HttpServletRequest request) {
		LOGGER.info(
				"updateEstado() -> Entrada al servicio para actualizar el estado correspodiente a las inscripciones");

		List<Long> arrayIds = new ArrayList<>();
		List<String> arrayCursosIds = new ArrayList<>();
		List<String> arrayCursosIdsConPlazasDisponibles = new ArrayList<>();

		int resultado = 0;

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idInstitucionToString = String.valueOf(idInstitucion);
		// Obtenemos el usuario para setear el campo "usumodificiacion"
		String dniUser = UserTokenUtils.getDniFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);

		LOGGER.info(
				"updateEstado() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);

		AdmUsuarios usuario = usuarios.get(0);
		// Obtenemos el tipo de accion que hemos pulsado.
		// Para ello cogeremos el primer objeto de la lista que nos ha enviado el front
		Short tipoAccion = listInscripcionItem.get(0).getTipoAccion();

		Long idEstadoUpdate = null;

		switch (tipoAccion) {
		case 0: // 0 --> Aprobar
			arrayCursosIds = comprobarPlazasDisponibles(listInscripcionItem);
			arrayCursosIdsConPlazasDisponibles = cursosConPlazasDisponibles(listInscripcionItem);
			if (arrayCursosIds.isEmpty() || !arrayCursosIdsConPlazasDisponibles.isEmpty()) {
				arrayIds = comprobarAccionAprobar(listInscripcionItem, idInstitucionToString,
						arrayCursosIdsConPlazasDisponibles, usuario, idInstitucion);
				idEstadoUpdate = 3L;
			} else {
				// arrayCursosIds = formateaListaCursosError(listInscripcionItem,
				// idInstitucionToString);
				arrayCursosIds.add("Sin plazas");
				return arrayCursosIds;
			}
			break;
		case 1: // 1 --> Cancelar
			arrayIds = comprobarAccionCancelar(listInscripcionItem, idInstitucionToString);
			idEstadoUpdate = 4L;
			break;
		case 2: // 2 --> Rechazar
			arrayIds = comprobarAccionRechazar(listInscripcionItem, idInstitucionToString);
			idEstadoUpdate = 2L;
			break;
		default:
			break;
		}

		// Si no hay inscripciones que cumplan las condiciones para actualizar su estado
		if (arrayIds.isEmpty()) {
			// TODO este método no comprueba correctamente los cursos que han fallado.
			// REVISAR
			// Actualmente se usa para que al front le llegue un array --> List<String>
			arrayCursosIds = formateaListaCursosError(listInscripcionItem, idInstitucionToString);
			return arrayCursosIds;
		} else {

			// Entidad que se va a rellenar con los valores a actualizar
			ForInscripcion record = new ForInscripcion();
			record.setIdestadoinscripcion(idEstadoUpdate);

			if (usuario == null) {
				LOGGER.warn(
						"updateEstado() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0; // Devolvemos 0 inscripciones actualizados porque no se va a poder realizar el
							// update al no haber recuperado al usuario
			} else {
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
			}

			record.setFechamodificacion(new Date()); // seteamos la fecha de modificación

			ForInscripcionExample example = new ForInscripcionExample();
			example.createCriteria().andIdinscripcionIn(arrayIds);

			LOGGER.info(
					"updateEstado() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para invocar a updateByExampleSelective para actualizar inscripciones según los criterios establecidos");
			resultado = forInscripcionExtendsMapper.updateByExampleSelective(record, example);
			if (resultado > 0) {
				resultado = insertarMotivo(listInscripcionItem, arrayIds, usuario);
			}
		}

		return resultado;
	}

	public List<Long> comprobarAccionAprobar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString,
			List<String> arrayCursosIdsConPlazasDisponibles, AdmUsuarios usuario, Short idInstitucion) {
		// Aprobar inscripción: aprobará la inscripción o inscripciones seleccionadas
		// que estén en estado Pendiente.
		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan
			// ser aceptadas
			if ((idInstitucionToString.equals(inscripcion.getIdInstitucion())
					&& (SigaConstants.ESTADO_INSCRIPCION_PENDIENTE.equals(inscripcion.getIdEstadoInscripcion())))
					&& arrayCursosIdsConPlazasDisponibles.contains(inscripcion.getIdCurso())) {
				int response = 0;
				PysPeticioncomprasuscripcion pysPeticioncomprasuscripcion = new PysPeticioncomprasuscripcion();
				pysPeticioncomprasuscripcion.setFechamodificacion(new Date());
				pysPeticioncomprasuscripcion.setIdinstitucion(idInstitucion);
				pysPeticioncomprasuscripcion.setUsumodificacion(usuario.getIdusuario());
				pysPeticioncomprasuscripcion.setTipopeticion("A");
				pysPeticioncomprasuscripcion.setIdestadopeticion(Short.valueOf("20"));
				NewIdDTO idPeticion = pysPeticioncomprasuscripcionExtendsMapper.selectMaxIdPeticion(idInstitucion);
				pysPeticioncomprasuscripcion.setIdpeticion(Long.valueOf(idPeticion.getNewId()));
				pysPeticioncomprasuscripcion.setIdpersona(inscripcion.getIdPersona());
				pysPeticioncomprasuscripcion.setFecha(new Date());
				pysPeticioncomprasuscripcion.setNumOperacion("1");

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

				response = pysPeticioncomprasuscripcionExtendsMapper.insert(pysPeticioncomprasuscripcion);

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Salida a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

				NewIdDTO idservicio = pysServiciosExtendsMapper.selectIdServicioByIdCurso(idInstitucion,
						Long.valueOf(inscripcion.getIdCurso()));
				NewIdDTO idserviciosinstitucion = pysServiciosinstitucionExtendsMapper
						.selectIdServicioinstitucionByIdServicio(idInstitucion, Long.valueOf(inscripcion.getIdCurso()));

				PysServiciossolicitados pysServiciossolicitados = new PysServiciossolicitados();
				pysServiciossolicitados.setFechamodificacion(new Date());
				pysServiciossolicitados.setIdinstitucion(idInstitucion);
				pysServiciossolicitados.setUsumodificacion(usuario.getIdusuario());
				pysServiciossolicitados.setAceptado("A");
				pysServiciossolicitados.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
				pysServiciossolicitados.setIdservicio(Long.valueOf(idservicio.getNewId()));
				pysServiciossolicitados.setIdserviciosinstitucion(Long.valueOf(idserviciosinstitucion.getNewId()));
				pysServiciossolicitados.setIdpeticion(Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
				pysServiciossolicitados.setIdpersona(inscripcion.getIdPersona());
				pysServiciossolicitados.setCantidad(1);
				if (null != inscripcion.getFormaPago() ) {
					pysServiciossolicitados.setIdformapago(Short.valueOf(inscripcion.getFormaPago().toString()));
				}else{
					pysServiciossolicitados.setIdformapago(Short.valueOf("10"));
				}
			
				

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para insertar el servicio solicitado");

				response = pysServiciossolicitadosMapper.insert(pysServiciossolicitados);

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Salida a pysServiciossolicitadosMapper para insertar el servicio solicitado");

				PysSuscripcion pysSuscripcion = new PysSuscripcion();
				pysSuscripcion.setFechamodificacion(new Date());
				pysSuscripcion.setIdinstitucion(idInstitucion);
				pysSuscripcion.setUsumodificacion(usuario.getIdusuario());
				pysSuscripcion.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
				pysSuscripcion.setIdservicio(Long.valueOf(idservicio.getNewId()));
				pysSuscripcion.setIdserviciosinstitucion(Long.valueOf(idserviciosinstitucion.getNewId()));
				pysSuscripcion.setIdpeticion(Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
				pysSuscripcion.setIdpersona(inscripcion.getIdPersona());
				pysSuscripcion.setCantidad(1);
				if (null != inscripcion.getFormaPago() ) {
					pysSuscripcion.setIdformapago(Short.valueOf(inscripcion.getFormaPago().toString()));
				}else{
					pysSuscripcion.setIdformapago(Short.valueOf("10"));
				}
				pysSuscripcion.setFechasuscripcion(new Date());

				CursoItem curso = forCursoExtendsMapper.searchCourseByIdcurso(inscripcion.getIdCurso(), idInstitucion,
						usuario.getIdlenguaje());

				pysSuscripcion.setDescripcion(curso.getNombreCurso());
				NewIdDTO idSuscripcion = pysSuscripcionExtendsMapper.selectMaxIdSuscripcion(idInstitucion,
						Long.valueOf(idservicio.getNewId()), Long.valueOf(idserviciosinstitucion.getNewId()));
				pysSuscripcion.setIdsuscripcion(Long.valueOf(idSuscripcion.getNewId()));

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Entrada a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

				response = pysSuscripcionExtendsMapper.insert(pysSuscripcion);

				LOGGER.info(
						"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Salida a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

				// Entidad que se va a rellenar con los valores a actualizar
				ForInscripcion record = new ForInscripcion();
				record.setIdpeticionsuscripcion(Long.valueOf(idPeticion.getNewId()));
				record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
				record.setFechamodificacion(new Date()); // seteamos la fecha de modificación

				ForInscripcionExample example = new ForInscripcionExample();
				example.createCriteria().andIdinscripcionEqualTo(inscripcion.getIdInscripcion());

				LOGGER.info(
						"updateEstado() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para invocar a updateByExampleSelective para actualizar inscripciones según los criterios establecidos");
				response = forInscripcionExtendsMapper.updateByExampleSelective(record, example);

				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}

	public List<String> formateaListaCursosError(List<InscripcionItem> listInscripcionItem,
			String idInstitucionToString) {
		// Aprobar inscripción: aprobará la inscripción o inscripciones seleccionadas
		// que estén en estado Pendiente.
		List<String> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan
			// ser aceptadas
			if (!idInstitucionToString.equals(inscripcion.getIdInstitucion())
					|| (!SigaConstants.ESTADO_INSCRIPCION_PENDIENTE.equals(inscripcion.getIdEstadoInscripcion()))) {
				arrayIds.add(inscripcion.getNombreCurso());
			}
		}

		return arrayIds;
	}

	public List<Long> comprobarAccionCancelar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Cancelar inscripción: se cancelarán inscripciones en estado aprobadas

		// TODO
		// Si la inscripción (suscripción de servicio) ya ha sido pagada, se realizará
		// la cancelación de la suscripción y el abono por el módulo de Facturación.

		// TODO
		// Se emitirán las comunicaciones establecidas ¿?

		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan
			// ser aceptadas
			if (idInstitucionToString.equals(inscripcion.getIdInstitucion())
					&& (SigaConstants.ESTADO_INSCRIPCION_APROBADA.equals(inscripcion.getIdEstadoInscripcion()))) {
				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}

	public List<Long> comprobarAccionRechazar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Rechazar: se rechaza la solicitud/solicitudes de inscripción seleccionadas

		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan
			// ser aceptadas
			if (idInstitucionToString.equals(inscripcion.getIdInstitucion())) {
				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}

	public Integer insertarMotivo(List<InscripcionItem> listInscripciones, List<Long> listArrays, AdmUsuarios usuario) {
		Integer resultado = 0;
		// Obtenemos el motivo con el primer objeto de la lista que nos ha enviado el
		// front
		String motivo = listInscripciones.get(0).getMotivo();

		for (InscripcionItem inscripcionItem : listInscripciones) {
			if (listArrays.contains(inscripcionItem.getIdInscripcion())) {
				ForCambioinscripcion recordCambioEstado = new ForCambioinscripcion();

				recordCambioEstado.setFechamodificacion(new Date());
				recordCambioEstado.setUsumodificacion(usuario.getIdusuario().longValue());
				recordCambioEstado.setIdestadoinscripcion(Long.parseLong(inscripcionItem.getIdEstadoInscripcion()));
				recordCambioEstado.setIdinscripcion(inscripcionItem.getIdInscripcion());
				recordCambioEstado.setIdinstitucion(Short.parseShort(inscripcionItem.getIdInstitucion()));
				recordCambioEstado.setMotivo(motivo);

				resultado += forCambioInscripcionMapper.insert(recordCambioEstado);
			}
		}

		return resultado;
	}

	@Override
	public int updateCalificacion(InscripcionItem inscripcionItem, HttpServletRequest request) {
		LOGGER.info(
				"updateCalificacion() -> Entrada al servicio para actualizar el estado correspodiente a las inscripciones");
		int resultado = 0;

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Obtenemos el usuario para setear el campo "usumodificiacion"
		String dniUser = UserTokenUtils.getDniFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);

		LOGGER.info(
				"updateCalificacion() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);

		LOGGER.info(
				"updateCalificacion() / admUsuariosMapper.selectByExample() -> Salida a admUsuariosMapper para obtener al usuario que está realizando la acción");

		if (usuarios != null && !usuarios.isEmpty()) {

			AdmUsuarios usuario = usuarios.get(0);

			ForInscripcionExample example = new ForInscripcionExample();
			example.createCriteria().andIdinscripcionEqualTo(inscripcionItem.getIdInscripcion());

			LOGGER.info(
					"updateCalificacion() / forInscripcionExtendsMapper.selectByExample() -> Entrada a forInscripcionExtendsMapper para obtener la inscripcion a calificar");

			List<ForInscripcion> inscripciones = forInscripcionExtendsMapper.selectByExample(example);

			LOGGER.info(
					"updateCalificacion() / forInscripcionExtendsMapper.selectByExample() -> Salida a forInscripcionExtendsMapper para obtener la inscripcion a calificar");

			if (inscripciones != null && !inscripciones.isEmpty()) {

				ForInscripcion forInscripcion = inscripciones.get(0);

				forInscripcion.setUsumodificacion(usuario.getIdusuario().longValue());
				forInscripcion.setFechamodificacion(new Date());

				if (inscripcionItem.getCalificacion() != null) {
					forInscripcion.setCalificacion(new BigDecimal(inscripcionItem.getCalificacion()));
				} else {
					forInscripcion.setCalificacion(null);
				}

				String idCalificacion = UtilidadesString.traduceNota(inscripcionItem.getCalificacion());
				forInscripcion.setIdcalificacion(idCalificacion != null ? Long.parseLong(idCalificacion) : null);

				LOGGER.info(
						"updateCalificacion() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Entrada a forInscripcionExtendsMapper para modificar la calificación");

				resultado = forInscripcionExtendsMapper.updateByPrimaryKey(forInscripcion);

				LOGGER.info(
						"updateCalificacion() / forInscripcionExtendsMapper.updateByPrimaryKey() -> Salida a forInscripcionExtendsMapper para modificar la calificación");

			}

		}

		LOGGER.info(
				"updateCalificacion() -> Salida al servicio para actualizar el estado correspodiente a las inscripciones");

		return resultado;
	}

	private List<String> comprobarPlazasDisponibles(List<InscripcionItem> listInscripcionItem) {
		LOGGER.info("compruebaPlazas() -> Entrada al servicio comprobar si quedan plazas del curso especificado");
		List<String> arrayCursoIds = new ArrayList<>();
		// Primero comprobamos el numero de plazas que se intenta aprobar de cada curso
		HashMap<Integer, Integer> numeroInscripcionesCursos = new HashMap<Integer, Integer>();
		Collection<String> idCursos = new ArrayList<String>();
		for (InscripcionItem inscripcionItem : listInscripcionItem) {

			if (null != numeroInscripcionesCursos.get(Integer.parseInt(inscripcionItem.getIdCurso()))) {
				numeroInscripcionesCursos.put(Integer.parseInt(inscripcionItem.getIdCurso()),
						numeroInscripcionesCursos.get(Integer.parseInt(inscripcionItem.getIdCurso())) + 1);
			} else {
				numeroInscripcionesCursos.put(Integer.parseInt(inscripcionItem.getIdCurso()), 1);
				idCursos.add(inscripcionItem.getIdCurso());
			}

		}
		for (Iterator iterator = idCursos.iterator(); iterator.hasNext();) {
			String idCurso = (String) iterator.next();

			// Primero comprobamos las plazas disponibles del curso
			CursoItem inscripcionesAprobadas = forInscripcionExtendsMapper.compruebaPlazasAprobadas(idCurso);
			if (null == inscripcionesAprobadas) {
				inscripcionesAprobadas = new CursoItem();
				inscripcionesAprobadas.setInscripciones("0");
			}

			ForCurso cursoEntidad = forCursoExtendsMapper.selectByPrimaryKeyExtends(Long.parseLong(idCurso));
			Long plazasdisponibles = 0L;

			if (null != cursoEntidad) {
				if (null != cursoEntidad.getNumeroplazas()) {
					plazasdisponibles = cursoEntidad.getNumeroplazas()
							- Long.parseLong(inscripcionesAprobadas.getInscripciones());
				}
			}
			if (plazasdisponibles < numeroInscripcionesCursos.get(Integer.parseInt(idCurso))) {
				arrayCursoIds.add(idCurso);
			}
		}

		return arrayCursoIds;

	}

	private List<String> cursosConPlazasDisponibles(List<InscripcionItem> listInscripcionItem) {
		LOGGER.info("compruebaPlazas() -> Entrada al servicio comprobar si quedan plazas del curso especificado");
		List<String> arrayCursoIds = new ArrayList<>();
		// Primero comprobamos el numero de plazas que se intenta aprobar de cada curso
		HashMap<Integer, Integer> numeroInscripcionesCursos = new HashMap<Integer, Integer>();
		Collection<String> idCursos = new ArrayList<String>();
		for (InscripcionItem inscripcionItem : listInscripcionItem) {

			if (null != numeroInscripcionesCursos.get(Integer.parseInt(inscripcionItem.getIdCurso()))) {
				numeroInscripcionesCursos.put(Integer.parseInt(inscripcionItem.getIdCurso()),
						numeroInscripcionesCursos.get(Integer.parseInt(inscripcionItem.getIdCurso())) + 1);
			} else {
				numeroInscripcionesCursos.put(Integer.parseInt(inscripcionItem.getIdCurso()), 1);
				idCursos.add(inscripcionItem.getIdCurso());
			}

		}
		for (Iterator iterator = idCursos.iterator(); iterator.hasNext();) {
			String idCurso = (String) iterator.next();

			// Primero comprobamos las plazas disponibles del curso
			CursoItem inscripcionesAprobadas = forInscripcionExtendsMapper.compruebaPlazasAprobadas(idCurso);
			if (null == inscripcionesAprobadas) {
				inscripcionesAprobadas = new CursoItem();
				inscripcionesAprobadas.setInscripciones("0");
			}

			ForCurso cursoEntidad = forCursoExtendsMapper.selectByPrimaryKeyExtends(Long.parseLong(idCurso));
			Long plazasdisponibles = 0L;

			if (null != cursoEntidad) {
				if (null != cursoEntidad.getNumeroplazas()) {
					plazasdisponibles = cursoEntidad.getNumeroplazas()
							- Long.parseLong(inscripcionesAprobadas.getInscripciones());
				}
			}
			if (numeroInscripcionesCursos.get(Integer.parseInt(idCurso)) <= plazasdisponibles) {
				arrayCursoIds.add(idCurso);
			}
		}

		return arrayCursoIds;

	}

	@Override
	public FichaPersonaItem searchPersona(HttpServletRequest request) {

		FichaPersonaItem fichaPersona = new FichaPersonaItem();

		String token = request.getHeader("Authorization");

		// Obtenemos el usuario para setear el campo "usumodificiacion"
		String dniUser = UserTokenUtils.getDniFromJWTToken(token);

		LOGGER.info("searchPersona() -> Entrada al servicio para la recuperar la ficha de persona");

		CenPersonaExample cenPersonaExample = new CenPersonaExample();
		cenPersonaExample.createCriteria().andNifcifEqualTo(dniUser);
		List<CenPersona> listCenPersonaItem = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);

		if (!listCenPersonaItem.isEmpty()) {
			fichaPersona.setNombre(listCenPersonaItem.get(0).getNombre());
			fichaPersona.setApellido1(listCenPersonaItem.get(0).getApellidos1());
			fichaPersona.setApellido2(listCenPersonaItem.get(0).getApellidos2());
			fichaPersona.setNif(listCenPersonaItem.get(0).getNifcif());
			fichaPersona.setIdPersona(String.valueOf(listCenPersonaItem.get(0).getIdpersona()));
			fichaPersona.setTipoIdentificacion(String.valueOf(listCenPersonaItem.get(0).getIdtipoidentificacion()));
		}

		return fichaPersona;
	}

	@Override
	public Boolean isAdministrador(HttpServletRequest request) {
		Boolean isAdministrador = Boolean.FALSE;

		// List<AdmUsuariosEfectivosPerfil> listCenPersonaItem;

		String token = request.getHeader("Authorization");
		// Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);

		// Obtenemos el usuario para setear el campo "usumodificiacion"
		// String dniUser = UserTokenUtils.getDniFromJWTToken(token);
		// AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		// exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
		//
		// LOGGER.info(
		// "isAdministrador() / admUsuariosMapper.selectByExample() -> Entrada a
		// admUsuariosMapper para obtener al usuario que está realizando la acción");
		// List<AdmUsuarios> usuarios =
		// admUsuariosMapper.selectByExample(exampleUsuarios);
		//
		// AdmUsuarios usuario = usuarios.get(0);
		//
		// if(usuario == null) {
		// LOGGER.warn(
		// "isAdministrador() / admUsuariosMapper.selectByExample() -> No se ha podido
		// recuperar al usuario logeado, no se realiza el update");
		// return isAdministrador;
		// } else {
		// AdmUsuariosEfectivosPerfilExample admUsuariosEfectivosPerfilExample = new
		// AdmUsuariosEfectivosPerfilExample();
		// admUsuariosEfectivosPerfilExample.createCriteria().andIdusuarioEqualTo(usuario.getIdusuario()).andIdinstitucionEqualTo(idInstitucion).andIdperfilIn(UtilidadesString.formateaListaPerfiles(perfiles));
		// listCenPersonaItem =
		// admUsuariosEfectivosPerfil.selectByExample(admUsuariosEfectivosPerfilExample);
		//
		// if(!listCenPersonaItem.isEmpty()) {
		// isAdministrador = Boolean.TRUE;
		// }
		//
		// }

		if (letrado.equalsIgnoreCase("N"))
			isAdministrador = Boolean.TRUE;

		return isAdministrador;
	}

	@Override
	public InscripcionItem selectInscripcionByPrimaryKey(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("selectInscripcionByPrimaryKey() -> Entrada al servicio para obtener inscripciones");

		// String token = request.getHeader("Authorization");
		// String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		// String dni = UserTokenUtils.getDniFromJWTToken(token);
		//// Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<CenPersona> listCenPersona;

		InscripcionItem inscripcionItemSearch = new InscripcionItem();

		inscripcionItemSearch = forInscripcionExtendsMapper.selectInscripcionByPrimaryKey(inscripcionItem);

		// TODO comprobar caso de null
		if (inscripcionItemSearch == null) {
			LOGGER.warn(
					"selectInscripcionByPrimaryKey() / forInscripcionExtendsMapper.selectInscripciones() -> No existen inscripciones para el filtro introducido");
		}

		return inscripcionItemSearch;
	}

}
