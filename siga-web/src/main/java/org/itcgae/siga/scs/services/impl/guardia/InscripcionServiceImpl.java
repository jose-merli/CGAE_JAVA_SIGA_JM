package org.itcgae.siga.scs.services.impl.guardia;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.GestionInscripcion;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class InscripcionServiceImpl implements InscripcionService {

	private final String VALIDACION_BAJA = "Validacion Baja";
	private final String DENEGACION = "Denegacion";
	private final String SOLICITUD_DE_ALTA = "Solicitud de Alta";
	private final String SOLICITUD_DE_BAJA = "Solicitud de Baja";
	private final String SOLICITUD = "Solicitud";

	private Logger LOGGER = Logger.getLogger(InscripcionServiceImpl.class);

	@Autowired
	private ScsInscripcionguardiaExtendsMapper inscripcionGuardiaExtensdsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;
	
	@Override
	public InsertResponseDTO insertarInscripciones(InscripcionGuardiaItem inscripcion, HttpServletRequest request) {
		LOGGER.info("insertarInscripciones() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO response = new InsertResponseDTO();
		Error error = new Error();
		String errorStr = "Se ha producido un error al tratar de guardar la inscripcion.";
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info("insertarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("insertarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					//Comprobamos si existe la inscripcion en BBDD
					
					List<InscripcionGuardiaItem> inscripcionSearch = inscripcionGuardiaExtensdsMapper.buscarInscripcion(idInstitucion, inscripcion, usuarios.get(0));
					
					if (inscripcionSearch != null && inscripcionSearch.isEmpty()) {
						//Si no existe la insertamos
						int insertado = inscripcionGuardiaExtensdsMapper.insertarInscripcion(idInstitucion, inscripcion, usuarios.get(0));
						
						if (insertado == 1) {
							response.setStatus(SigaConstants.OK);
						} else {
							response.setStatus(SigaConstants.KO);
							LOGGER.error("InscripcionServiceImpl.insertarInscripciones() -> "
									+ errorStr);
							
							error.setCode(500);
							error.setDescription(errorStr);
							response.setError(error);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.insertarInscripciones() -> Se ha producido un error en la insercion de inscripciones.",
					e);

			error.setCode(500);
			error.setDescription("Error en la insercion de inscripciones.");
			error.setMessage(e.getMessage());

			response.setError(error);
		}
		LOGGER.info("insertarInscripciones() -> Salida para insertar inscripciones");
		return response;
	}

	@Override
	public HistoricoInscripcionDTO historicoInscripcion(InscripcionGuardiaItem inscripcion, HttpServletRequest request) {
		LOGGER.info("historicoInscripcion() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		HistoricoInscripcionDTO inscripcionHistorica = new HistoricoInscripcionDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info("historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<InscripcionGuardiaItem> inscripcionSearch = inscripcionGuardiaExtensdsMapper.buscarInscripcion(idInstitucion, inscripcion, usuarios.get(0));
					for(InscripcionGuardiaItem inscripcionItem : inscripcionSearch) {
						if (inscripcionItem.getFechaSuscripcion() != null) {
							inscripcionHistorica.setFecha(inscripcionItem.getFechaSuscripcion());
							inscripcionHistorica.setAccion(SOLICITUD);
							inscripcionHistorica.setObservacion(inscripcionItem.getObservacionessuscripcion());
						}else if (inscripcionItem.getFechasolicitudbaja() != null) {
							inscripcionHistorica.setFecha(inscripcionItem.getFechasolicitudbaja());
							inscripcionHistorica.setAccion(SOLICITUD_DE_BAJA);
							inscripcionHistorica.setObservacion(inscripcionItem.getObservacionesbaja());
						}else if (inscripcionItem.getFechavalidacion() != null) {
							inscripcionHistorica.setFecha(inscripcionItem.getFechavalidacion());
							inscripcionHistorica.setAccion(SOLICITUD_DE_ALTA);
							inscripcionHistorica.setObservacion(inscripcionItem.getObservacionesvalidacion());
						}else if (inscripcionItem.getFechadenegacion() != null) {
							inscripcionHistorica.setFecha(inscripcionItem.getFechadenegacion());
							inscripcionHistorica.setAccion(DENEGACION);
							inscripcionHistorica.setObservacion(inscripcionItem.getObservacionesdenegacion());
						}else if (inscripcionItem.getFechabaja() != null) {
							inscripcionHistorica.setFecha(inscripcionItem.getFechaBaja());
							inscripcionHistorica.setAccion(VALIDACION_BAJA);
							inscripcionHistorica.setObservacion(inscripcionItem.getObservacionesvalbaja());
						}else {
							error.setCode(500);
							error.setDescription("Error al filtrar por fechas, no hay fecha asignable");
							inscripcionHistorica.setError(error);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.historicoInscripcion() -> Se ha producido un error en la busqueda de inscripciones.",
					e);

			error.setCode(500);
			error.setDescription("Error en la busqueda de inscripciones.");
			error.setMessage(e.getMessage());

			inscripcionHistorica.setError(error);
		}		
		LOGGER.info("historicoInscripcion() -> Salida para insertar inscripciones");
		return inscripcionHistorica;
	}

	@Override
	public InscripcionesDisponiblesDTO inscripcionesDisponibles(InscripcionGuardiaItem inscripcion, HttpServletRequest request) {
		LOGGER.info("inscripcionesDisponibles() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDisponiblesDTO inscripciones= new InscripcionesDisponiblesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info("inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtensdsMapper.inscripcionesDisponibles(idInstitucion, usuarios.get(0));
					inscripciones.setAccion(inscripcionesDisponibles);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.inscripcionesDisponibles() -> Se ha producido un error en la busqueda de inscripciones disponibles.",
					e);

			error.setCode(500);
			error.setDescription("Error en la busqueda de inscripciones.");
			error.setMessage(e.getMessage());

			inscripciones.setError(error);
		}
		LOGGER.info("inscripcionesDisponibles() -> Salida para insertar inscripciones");
		return inscripciones;
	}

	@Override
	public InscripcionesDisponiblesDTO inscripcionPorguardia(InscripcionGuardiaItem inscripcion, HttpServletRequest request) {
		LOGGER.info("inscripcionPorguardia() -> Entrada para buscar inscripciones por guardia");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDisponiblesDTO inscripciones= new InscripcionesDisponiblesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info("inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<GestionInscripcion> inscripcionPorguardia = inscripcionGuardiaExtensdsMapper.inscripcionPorguardia(idInstitucion, usuarios.get(0), inscripcion.getIdGuardia());
					inscripciones.setAccion(inscripcionPorguardia);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.inscripcionPorguardia() -> Se ha producido un error en la busqueda de inscripciones por guardia.",
					e);

			error.setCode(500);
			error.setDescription("Error en la busqueda de inscripciones por guardia.");
			error.setMessage(e.getMessage());

			inscripciones.setError(error);
		}
		LOGGER.info("inscripcionPorguardia() -> Salida para buscar inscripciones por guardia");
		return inscripciones;
	}

	@Override
	public ComboDTO comboLetrados(HttpServletRequest request, String idGuardia) {
		LOGGER.info("inscripcionPorguardia() -> Entrada para buscar inscripciones por guardia");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();
		List<ComboItem> lista = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info("inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<LetradoGuardiaItem> searchLetradosInscripcion = scsGuardiasturnoExtendsMapper.searchLetradosInscripcion(Short.toString(idInstitucion), idGuardia);
					for (LetradoGuardiaItem letradoGuardiaItem : searchLetradosInscripcion) {
						ComboItem comboItem = new ComboItem();
						comboItem.setLabel(letradoGuardiaItem.getNumeroColegiado()+"/"+letradoGuardiaItem.getNumeroGrupo());
						comboItem.setValue(letradoGuardiaItem.getNumeroGrupo());
						lista.add(comboItem);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.inscripcionPorguardia() -> Se ha producido un error en la busqueda de inscripciones por guardia.", e);

			error.setCode(500);
			error.setDescription("Error en la busqueda de inscripciones por guardia.");
			error.setMessage(e.getMessage());

			combo.setError(error);
		}
		
		combo.setCombooItems(lista);
		return combo;
	}
}
