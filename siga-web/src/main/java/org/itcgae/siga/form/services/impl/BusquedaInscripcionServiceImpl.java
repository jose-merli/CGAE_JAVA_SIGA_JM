package org.itcgae.siga.form.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.ForCambioinscripcion;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.ForCambioinscripcionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForEstadoinscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.form.services.IBusquedaInscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaInscripcionServiceImpl implements IBusquedaInscripcionService{
	
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
	
	@Override
	public ComboDTO getEstadosInscripcion(HttpServletRequest request) {
		
		LOGGER.info("getEstadosInscripcion() -> Entrada al servicio para obtener los estados de una inscripcion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstadosInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getEstadosInscripcion() / forInscripcionExtendsMapper.distinctEstadoInscripcion -> Entrada a forInscripcionExtendsMapper para obtener los diferentes estados de una inscripcion");
				comboItems = forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion(usuario.getIdlenguaje());
				LOGGER.info(
						"getEstadosInscripcion() / forEstadoInscripcionExtendsMapper.distinctEstadoInscripcion -> Salida de forEstadoInscripcionExtendsMapper para obtener los diferentes estados de una inscripcion");
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getEstadosInscripcion() -> Salida del servicio para obtener los diferentes estados de una inscripcion");
		
		return comboDTO;
	}

	@Override
	public InscripcionDTO searchInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("searchInscripcion() -> Entrada al servicio para obtener inscripciones");
		
		String token = request.getHeader("Authorization");
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<CenPersona> listCenPersona;

		if (letrado.equalsIgnoreCase("S")) {
			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
			listCenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
			if(!listCenPersona.isEmpty()) {
				CenPersona cenPersona = listCenPersona.get(0);
				inscripcionItem.setIdPersona(cenPersona.getIdpersona());
			}
		}
		
		InscripcionDTO inscripcionDTO = new InscripcionDTO();
		List<InscripcionItem> inscripcionItemList = new ArrayList<InscripcionItem>();
		
		inscripcionItemList = forInscripcionExtendsMapper.selectInscripciones(inscripcionItem);
		inscripcionDTO.setInscripcionItem(inscripcionItemList);

		//TODO comprobar caso de null
		if (inscripcionItemList.isEmpty()) {
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
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCalificacionesEmitidas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCalificacionesEmitidas() / forInscripcionExtendsMapper.getCalificacionesEmitidas -> Entrada a forInscripcionExtendsMapper para obtener las diferentes calificaciones");
				comboItems = forInscripcionExtendsMapper.getCalificacionesEmitidas(usuario.getIdlenguaje());
				LOGGER.info(
						"getCalificacionesEmitidas() / forEstadoInscripcionExtendsMapper.getCalificacionesEmitidas -> Salida de forEstadoInscripcionExtendsMapper para obtener las diferentes calificaciones");
				
				//TODO Quitar en un futuro
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("Todos");
				comboItem.setValue("-1");
				comboItems.add(0, comboItem);
				
				ComboItem comboItem1 = new ComboItem();
				comboItem1.setLabel("Sin calificación");
				comboItem1.setValue("-2");
				comboItems.add(0, comboItem1);
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCalificacionesEmitidas() -> Salida del servicio para obtener los diferentes estados de una inscripcion");
		
		return comboDTO;
	}

	@Override
	public Object updateEstado(List<InscripcionItem> listInscripcionItem, HttpServletRequest request) {
		LOGGER.info("updateEstado() -> Entrada al servicio para actualizar el estado correspodiente a las inscripciones");
		
		List<Long> arrayIds = new ArrayList<>();
		List<String> arrayCursosIds = new ArrayList<>();
		
		int resultado = 0;
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idInstitucionToString =  String.valueOf(idInstitucion);
		
		// Obtenemos el tipo de accion que hemos pulsado.
		// Para ello cogeremos el primer objeto de la lista que nos ha enviado el front
		Short tipoAccion = listInscripcionItem.get(0).getTipoAccion();
		
		Long idEstadoUpdate = null;
		
		switch (tipoAccion) {
		case 0: // 0 --> Aprobar
			arrayCursosIds = comprobarPlazasDisponibles(listInscripcionItem);
			if (arrayCursosIds.isEmpty()) {
				arrayIds = comprobarAccionAprobar(listInscripcionItem, idInstitucionToString);
				idEstadoUpdate = 3L;
			} else {
//				arrayCursosIds = formateaListaCursosError(listInscripcionItem, idInstitucionToString);
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
			// TODO este método no comprueba correctamente los cursos que han fallado. REVISAR
			// Actualmente se usa para que al front le llegue un array --> List<String>
			arrayCursosIds = formateaListaCursosError(listInscripcionItem, idInstitucionToString);
			return arrayCursosIds;
		} else {
		
			//Entidad que se va a rellenar con los valores a actualizar
			ForInscripcion record = new ForInscripcion();
			record.setIdestadoinscripcion(idEstadoUpdate);
			
			//Obtenemos el usuario para setear el campo "usumodificiacion"
			String dniUser = UserTokenUtils.getDniFromJWTToken(token);
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
			
			LOGGER.info(
					"updateEstado() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
			List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
			
			AdmUsuarios usuario = usuarios.get(0);
			
			if(usuario == null) {
				LOGGER.warn(
						"updateEstado() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
				return 0;  //Devolvemos 0 inscripciones actualizados porque no se va a poder realizar el update al no haber recuperado al usuario
			}else {
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
	
	public List<Long> comprobarAccionAprobar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Aprobar inscripción: aprobará la inscripción o inscripciones seleccionadas
		// que estén en estado Pendiente.  
		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan ser aceptadas
			if (idInstitucionToString.equals(inscripcion.getIdInstitucion()) && (SigaConstants.ESTADO_INSCRIPCION_PENDIENTE.equals(inscripcion.getIdEstadoInscripcion()))) {
				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}
	
	public List<String> formateaListaCursosError(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Aprobar inscripción: aprobará la inscripción o inscripciones seleccionadas
		// que estén en estado Pendiente.  
		List<String> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan ser aceptadas
			if (!idInstitucionToString.equals(inscripcion.getIdInstitucion()) || (!SigaConstants.ESTADO_INSCRIPCION_PENDIENTE.equals(inscripcion.getIdEstadoInscripcion()))) {
				arrayIds.add(inscripcion.getNombreCurso());
			}
		}

		return arrayIds;
	}
	
	public List<Long> comprobarAccionCancelar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Cancelar inscripción: se cancelarán inscripciones en estado aprobadas
		
		//TODO
		// Si la inscripción (suscripción de servicio) ya ha sido pagada, se realizará
		// la cancelación de la suscripción y el abono por el módulo de Facturación.

		//TODO
		//Se emitirán las comunicaciones establecidas ¿? 

		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan ser aceptadas
			if (idInstitucionToString.equals(inscripcion.getIdInstitucion()) && (SigaConstants.ESTADO_INSCRIPCION_APROBADA.equals(inscripcion.getIdEstadoInscripcion()))) {
				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}
	
	public List<Long> comprobarAccionRechazar(List<InscripcionItem> listInscripcionItem, String idInstitucionToString) {
		// Rechazar: se rechaza la solicitud/solicitudes de inscripción seleccionadas
		
		List<Long> arrayIds = new ArrayList<>();
		for (InscripcionItem inscripcion : listInscripcionItem) {

			// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan ser aceptadas
			if (idInstitucionToString.equals(inscripcion.getIdInstitucion())) {
				arrayIds.add(inscripcion.getIdInscripcion());
			}
		}

		return arrayIds;
	}
	
	public Integer insertarMotivo(List<InscripcionItem> listInscripciones, List<Long> listArrays, AdmUsuarios usuario) {
		Integer resultado = 0;
		// Obtenemos el motivo con el primer objeto de la lista que nos ha enviado el front
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
		LOGGER.info("updateCalificacion() -> Entrada al servicio para actualizar el estado correspodiente a las inscripciones");
		ForInscripcion forInscripcionRecord = new ForInscripcion();
		int resultado = 0;
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		//Obtenemos el usuario para setear el campo "usumodificiacion"
		String dniUser = UserTokenUtils.getDniFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
		
		LOGGER.info(
				"updateCalificacion() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		
		AdmUsuarios usuario = usuarios.get(0);
		
		if(usuario == null) {
			LOGGER.warn(
					"updateCalificacion() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
			return 0; // Devolvemos 0 inscripciones actualizados porque no se va a poder realizar el
						// update al no haber recuperado al usuario
		} else {
			forInscripcionRecord.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion			
		}

		forInscripcionRecord.setFechamodificacion(new Date()); // seteamos la fecha de modificación
		forInscripcionRecord.setCalificacion(new BigDecimal(inscripcionItem.getCalificacion()));

		String idCalificacion = UtilidadesString.traduceNota(inscripcionItem.getCalificacion());
		forInscripcionRecord.setIdcalificacion(idCalificacion != null ? Long.parseLong(idCalificacion) : null);
		
		ForInscripcionExample example = new ForInscripcionExample();
		example.createCriteria().andIdinscripcionEqualTo(inscripcionItem.getIdInscripcion());
		

		LOGGER.info(
				"updateCalificacion() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para invocar a updateByExampleSelective para actualizar inscripciones según los criterios establecidos");
		resultado = forInscripcionExtendsMapper.updateByExampleSelective(forInscripcionRecord, example);
		
		return resultado;
	}
	
	private List<String> comprobarPlazasDisponibles(List<InscripcionItem> listInscripcionItem) {
		LOGGER.info("compruebaPlazas() -> Entrada al servicio comprobar si quedan plazas del curso especificado");
		List<String> arrayCursoIds = new ArrayList<>();
		
		for (InscripcionItem inscripcionItem : listInscripcionItem) {
			CursoItem cursoItem = forInscripcionExtendsMapper.compruebaPlazas(inscripcionItem.getIdCurso()); 

			Integer numPlazas = Integer.parseInt(cursoItem.getNumPlazas() == null ? "0" : cursoItem.getNumPlazas());
			Integer inscripciones = Integer.parseInt(cursoItem.getInscripciones());
			
			if(inscripciones >= numPlazas)
				arrayCursoIds.add(cursoItem.getNombreCurso());
		}
		
		return arrayCursoIds;
		
	}
	
	@Override
	public FichaPersonaItem searchPersona(HttpServletRequest request) {
		
		FichaPersonaItem fichaPersona = new FichaPersonaItem();
		
		String token = request.getHeader("Authorization");

		//Obtenemos el usuario para setear el campo "usumodificiacion"
		String dniUser = UserTokenUtils.getDniFromJWTToken(token);
		
		
		LOGGER.info(
				"searchPersona() -> Entrada al servicio para la recuperar la ficha de persona");

		CenPersonaExample cenPersonaExample = new CenPersonaExample();
		cenPersonaExample.createCriteria().andNifcifEqualTo(dniUser);
		List<CenPersona> listCenPersonaItem = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
		
		if(!listCenPersonaItem.isEmpty()) {
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
		
//		List<AdmUsuariosEfectivosPerfil> listCenPersonaItem;
		
		String token = request.getHeader("Authorization");
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		List<String> perfiles = UserTokenUtils.getPerfilesFromJWTToken(token);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		
		//Obtenemos el usuario para setear el campo "usumodificiacion"
//		String dniUser = UserTokenUtils.getDniFromJWTToken(token);
//		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//		exampleUsuarios.createCriteria().andNifEqualTo(dniUser).andIdinstitucionEqualTo(idInstitucion);
//		
//		LOGGER.info(
//				"isAdministrador() / admUsuariosMapper.selectByExample() -> Entrada a admUsuariosMapper para obtener al usuario que está realizando la acción");
//		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
//		
//		AdmUsuarios usuario = usuarios.get(0);
//		
//		if(usuario == null) {
//			LOGGER.warn(
//					"isAdministrador() / admUsuariosMapper.selectByExample() -> No se ha podido recuperar al usuario logeado, no se realiza el update");
//			return isAdministrador;
//		} else {
//			AdmUsuariosEfectivosPerfilExample admUsuariosEfectivosPerfilExample = new AdmUsuariosEfectivosPerfilExample();
//			admUsuariosEfectivosPerfilExample.createCriteria().andIdusuarioEqualTo(usuario.getIdusuario()).andIdinstitucionEqualTo(idInstitucion).andIdperfilIn(UtilidadesString.formateaListaPerfiles(perfiles));
//			listCenPersonaItem = admUsuariosEfectivosPerfil.selectByExample(admUsuariosEfectivosPerfilExample);
//			
//			if(!listCenPersonaItem.isEmpty()) {
//				isAdministrador = Boolean.TRUE;
//			}
//			
//		}
		
		if(letrado.equalsIgnoreCase("N"))
			isAdministrador = Boolean.TRUE;
			
		return isAdministrador;
	}
	
	
}
