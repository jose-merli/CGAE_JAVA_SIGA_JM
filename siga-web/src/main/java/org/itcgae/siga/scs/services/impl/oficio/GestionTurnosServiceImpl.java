package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.scs.services.oficio.IGestionTurnosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionTurnosServiceImpl implements IGestionTurnosService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsOrdenacioncolasMapper scsOrdenacioncolasMapper;

	@Autowired
	private ScsInscripcionturnoMapper scsInscripcionturnoMapper;

	@Autowired
	private ScsGuardiasturnoMapper scsGuardiasturnoMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private ScsInscripcionguardiaMapper scsInscripcionguardiaMapper;

	@Autowired
	private ScsOrdenacionColasExtendsMapper scsOrdenacioncolasExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;
	
	@Override
	public TurnosDTO busquedaTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		
		Error error = new Error();	
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnosDTO = new TurnosDTO();
		List<TurnosItem> turnosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				turnosItems = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");


				if((turnosItems != null) && (turnosItems.size()) >= 200) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de 200 resultados, pero se muestran sólo los 200 más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					turnosDTO.setError(error);
				}
				
				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return turnosDTO;
	}

	@Override
	public TurnosDTO busquedaFichaTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnosDTO = new TurnosDTO();
		List<TurnosItem> turnosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				turnosItems = scsTurnosExtendsMapper.busquedaFichaTurnos(turnosItem, idInstitucion, usuarios.get(0).getIdlenguaje());
				
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return turnosDTO;
	}

	@Transactional
	public UpdateResponseDTO eliminateTurnos(TurnosDTO turnosDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					List<List<InscripcionTurnoItem>> inscripcionesTurno = new ArrayList<>();
					List<InscripcionTurnoItem> inscripcion = new ArrayList<>();
					List<List<ScsInscripcionguardia>> inscripcionesGuardiasTurno = new ArrayList<>();
					List<ScsInscripcionguardia> inscripcionGuardia = new ArrayList<>();
					List<GuardiasItem> guardiasConfiguradas = new ArrayList<>();
					List<List<GuardiasItem>> guardiasConfiguradasTurno = new ArrayList<>();
					
					for (TurnosItem turnosItem : turnosDTO.getTurnosItems()) {

						ScsTurno turno = new ScsTurno();
						turno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
						turno.setIdinstitucion(idInstitucion);

						turno = scsTurnosExtendsMapper.selectByPrimaryKey(turno);
						turno.setFechabaja(new Date());
						turno.setVisibilidad("0");
						

						turno.setFechamodificacion(new Date());
						turno.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						response = scsTurnosExtendsMapper.updateByPrimaryKey(turno);
						
						//Obtenemos las inscripciones al turno en estado ALTA
						inscripcion = scsTurnosExtendsMapper.selectInscripcionAltasTurnoByTurno(idInstitucion, turnosItem.getIdturno());
						if(inscripcion.size() != 0) {
							inscripcionesTurno.add(inscripcion);
						}
						
						//Obtenemos las guardias inscritas al turno en alta.
						inscripcionGuardia = scsGuardiasturnoExtendsMapper.selectGuardiaTurnoAltasByTurno(idInstitucion, turnosItem.getIdturno());
						if(inscripcionGuardia.size() != 0) {
							inscripcionesGuardiasTurno.add(inscripcionGuardia);
						}
						
						//Obtenemos las guardiasConfiguradasTurno.
						guardiasConfiguradas = scsGuardiasturnoExtendsMapper.selectGuardiaConfiguradasTurno(idInstitucion, turnosItem.getIdturno());
						if(guardiasConfiguradas.size() != 0) {
							guardiasConfiguradasTurno.add(guardiasConfiguradas);
						}
						
						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");
					}

					//ELIMINAMOS LAS INSCRIPCIONES A LOS TURNOS
					for(List<InscripcionTurnoItem> listaInscripcionesTurnos : inscripcionesTurno) {
						for(InscripcionTurnoItem inscripcionTurnoAAnular : listaInscripcionesTurnos) {
							ScsInscripcionturno turnoupdate = new ScsInscripcionturno();
							turnoupdate.setIdinstitucion(inscripcionTurnoAAnular.getIdinstitucion());
							turnoupdate.setIdturno(inscripcionTurnoAAnular.getIdturno());
							turnoupdate.setIdpersona(inscripcionTurnoAAnular.getIdpersona());
							turnoupdate.setFechasolicitud(inscripcionTurnoAAnular.getFechasolicitud());
							
							ScsInscripcionturno turnoActualupdate = scsInscripcionturnoExtendsMapper.selectByPrimaryKey(turnoupdate);
							
							//Si no tiene fecha baja solicitud se añade la de hoy
							if(turnoActualupdate.getFechasolicitudbaja() == null) turnoActualupdate.setFechasolicitudbaja(new Date());
							
							turnoActualupdate.setFechabaja(new Date());
							turnoActualupdate.setFechamodificacion(new Date());
							turnoActualupdate.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsInscripcionturnoMapper.updateByPrimaryKey(turnoActualupdate);
							
						}
					}
					
					//ELIMINAMOS LAS INSCRIPCIONES A LAS GUARDIAS DEL TURNO
					for(List<ScsInscripcionguardia> listaInscripcionesGuardia : inscripcionesGuardiasTurno) {
						for(ScsInscripcionguardia inscripcionGuardiaAAnular : listaInscripcionesGuardia) {
							ScsInscripcionguardia guardiaupdate = new ScsInscripcionguardia();
							guardiaupdate.setIdinstitucion(inscripcionGuardiaAAnular.getIdinstitucion());
							guardiaupdate.setIdturno(inscripcionGuardiaAAnular.getIdturno());
							guardiaupdate.setIdpersona(inscripcionGuardiaAAnular.getIdpersona());
							guardiaupdate.setFechasuscripcion(inscripcionGuardiaAAnular.getFechasuscripcion());
							guardiaupdate.setIdguardia(inscripcionGuardiaAAnular.getIdguardia());
	
							ScsInscripcionguardia guardiaActualUpdate = scsInscripcionguardiaMapper.selectByPrimaryKey(guardiaupdate);
							
							if(guardiaActualUpdate.getFechasolicitudbaja() == null) guardiaActualUpdate.setFechasolicitudbaja(new Date());
							
							guardiaActualUpdate.setFechabaja(new Date());
							guardiaActualUpdate.setFechamodificacion(new Date());
							guardiaActualUpdate.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsInscripcionguardiaMapper.updateByPrimaryKey(guardiaActualUpdate);
							
						}
					}
					
//					//ELIMINAMOS LAS GUARDIAS CONFIGURADAS PARA EL TURNO
					for(List<GuardiasItem> listaInscripcionesGuardia : guardiasConfiguradasTurno) {
						for(GuardiasItem inscripcionGuardiaAAnular : listaInscripcionesGuardia) {
							ScsGuardiasturno guardiaupdate = new ScsGuardiasturno();
							guardiaupdate.setIdinstitucion(new Short(inscripcionGuardiaAAnular.getJurisdiccion()));
							guardiaupdate.setIdturno(Integer.parseInt(inscripcionGuardiaAAnular.getIdTurno()));
							guardiaupdate.setIdguardia(Integer.parseInt(inscripcionGuardiaAAnular.getIdGuardia()));
							ScsGuardiasturno guardiaTurnoActual = scsGuardiasturnoMapper.selectByPrimaryKey(guardiaupdate);
							guardiaTurnoActual.setFechabaja(new Date());
							guardiaTurnoActual.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsGuardiasturnoMapper.updateByPrimaryKey(guardiaTurnoActual);
							
						}
					}
					
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO activarTurnos(TurnosDTO turnosDTO, HttpServletRequest request) {
		LOGGER.info("activarTurnos() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activarTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activarTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					List<List<InscripcionTurnoItem>> inscripcionesTurno = new ArrayList<>();
					List<InscripcionTurnoItem> inscripcion = new ArrayList<>();
					List<List<ScsInscripcionguardia>> inscripcionesGuardiasTurno = new ArrayList<>();
					List<ScsInscripcionguardia> inscripcionGuardia = new ArrayList<>();
					List<GuardiasItem> guardiasConfiguradas = new ArrayList<>();
					List<List<GuardiasItem>> guardiasConfiguradasTurno = new ArrayList<>();
					for (TurnosItem turnosItem : turnosDTO.getTurnosItems()) {

						ScsTurno turno = new ScsTurno();
						turno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
						turno.setIdinstitucion(idInstitucion);
						turno = scsTurnosExtendsMapper.selectByPrimaryKey(turno);
						turno.setFechabaja(null);
						turno.setVisibilidad("1");
						turno.setFechamodificacion(new Date());
						turno.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"activarTurnos() / scsTurnosExtendsMapper.updateByPrimaryKey() -> Entrada a scsTurnosExtendsMapper para activar turnos");

						response = scsTurnosExtendsMapper.updateByPrimaryKey(turno);
						 
						if(turnosItem.getFechabaja() != null) { // Si viene la fecha informada
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						     String fechaComoCadena = sdf.format(turnosItem.getFechabaja());

							
							//Obtenemos las inscripciones al turno en estado de BAJA con fecha informada fechabaja
							inscripcion = scsTurnosExtendsMapper.selectInscripcionTurnoBajasByTurnoFechaBaja(idInstitucion, turnosItem.getIdturno(),fechaComoCadena);
							if(inscripcion.size() != 0) {
								inscripcionesTurno.add(inscripcion);
							}
							
							//Obtenemos las guardias inscritas al turno en Baja.
							inscripcionGuardia = scsGuardiasturnoExtendsMapper.selectGuardiaTurnoBajasByTurnoFecha(idInstitucion, turnosItem.getIdturno(), fechaComoCadena);
							if(inscripcionGuardia.size() != 0) {
								inscripcionesGuardiasTurno.add(inscripcionGuardia);
							}
							
							//Obtenemos las guardiasConfiguradasTurno.
							guardiasConfiguradas = scsGuardiasturnoExtendsMapper.selectGuardiaConfiguradasBajaByTurnoFechaBaja(idInstitucion, turnosItem.getIdturno(),fechaComoCadena);
							if(inscripcionGuardia.size() != 0) {
								guardiasConfiguradasTurno.add(guardiasConfiguradas);
							}
						}
						 
						
						LOGGER.info(
								"activarTurnos() /  scsTurnosExtendsMapper.updateByPrimaryKey() -> Entrada a scsTurnosExtendsMapper para activar turnos");
					}

					//ELIMINAMOS LAS INSCRIPCIONES A LOS TURNOS
					for(List<InscripcionTurnoItem> listaInscripcionesTurnos : inscripcionesTurno) {
						for(InscripcionTurnoItem inscripcionTurnoAAnular : listaInscripcionesTurnos) {
							ScsInscripcionturno turnoupdate = new ScsInscripcionturno();
							turnoupdate.setIdinstitucion(inscripcionTurnoAAnular.getIdinstitucion());
							turnoupdate.setIdturno(inscripcionTurnoAAnular.getIdturno());
							turnoupdate.setIdpersona(inscripcionTurnoAAnular.getIdpersona());
							turnoupdate.setFechasolicitud(inscripcionTurnoAAnular.getFechasolicitud());
							
							ScsInscripcionturno turnoActualupdate = scsInscripcionturnoExtendsMapper.selectByPrimaryKey(turnoupdate);
							
							if(turnoActualupdate.getFechasolicitudbaja().equals(turnoActualupdate.getFechabaja()))
								turnoActualupdate.setFechasolicitudbaja(null);
							
							turnoActualupdate.setFechabaja(null);
							turnoActualupdate.setFechamodificacion(new Date());
							turnoActualupdate.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsInscripcionturnoMapper.updateByPrimaryKey(turnoActualupdate);
							
						}
					}
					
					//ELIMINAMOS LAS INSCRIPCIONES A LAS GUARDIAS DEL TURNO
					for(List<ScsInscripcionguardia> listaInscripcionesGuardia : inscripcionesGuardiasTurno) {
						for(ScsInscripcionguardia inscripcionGuardiaAAnular : listaInscripcionesGuardia) {
							ScsInscripcionguardia guardiaupdate = new ScsInscripcionguardia();
							guardiaupdate.setIdinstitucion(inscripcionGuardiaAAnular.getIdinstitucion());
							guardiaupdate.setIdturno(inscripcionGuardiaAAnular.getIdturno());
							guardiaupdate.setIdpersona(inscripcionGuardiaAAnular.getIdpersona());
							guardiaupdate.setFechasuscripcion(inscripcionGuardiaAAnular.getFechasuscripcion());
							guardiaupdate.setIdguardia(inscripcionGuardiaAAnular.getIdguardia());
	
							ScsInscripcionguardia guardiaActualUpdate = scsInscripcionguardiaMapper.selectByPrimaryKey(guardiaupdate);
	
							if(guardiaActualUpdate.getFechasolicitudbaja().equals(guardiaActualUpdate.getFechabaja()))
								guardiaActualUpdate.setFechasolicitudbaja(null);
							
							guardiaActualUpdate.setFechabaja(null);
							guardiaActualUpdate.setFechamodificacion(new Date());
							guardiaActualUpdate.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsInscripcionguardiaMapper.updateByPrimaryKey(guardiaActualUpdate);
							
						}
					}
					
//					//ELIMINAMOS LAS GUARDIAS CONFIGURADAS PARA EL TURNO
					for(List<GuardiasItem> listaInscripcionesGuardia : guardiasConfiguradasTurno) {
						for(GuardiasItem inscripcionGuardiaAAnular : listaInscripcionesGuardia) {
							ScsGuardiasturno guardiaupdate = new ScsGuardiasturno();
							guardiaupdate.setIdinstitucion(new Short(inscripcionGuardiaAAnular.getJurisdiccion()));
							guardiaupdate.setIdturno(Integer.parseInt(inscripcionGuardiaAAnular.getIdTurno()));
							guardiaupdate.setIdguardia(Integer.parseInt(inscripcionGuardiaAAnular.getIdGuardia()));
							ScsGuardiasturno guardiaTurnoActual = scsGuardiasturnoMapper.selectByPrimaryKey(guardiaupdate);
							
							guardiaTurnoActual.setFechabaja(null);
							guardiaTurnoActual.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsGuardiasturnoMapper.updateByPrimaryKey(guardiaTurnoActual);
							
						}
					}
					
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activarTurnos() -> Salida del servicio para activar turnos");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDatosGenerales(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
					ScsTurnoExample ejemplo = new ScsTurnoExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAbreviaturaEqualTo(turnosItem.getAbreviatura()).andIdturnoNotEqualTo(Integer.parseInt(turnosItem.getIdturno()));

					List<ScsTurno> turnosExistentes = scsTurnosExtendsMapper.selectByExample(ejemplo);

					if ((turnosExistentes != null && turnosExistentes.size() > 0)) {
						response = 0;
						error.setCode(400);
						error.setDescription("justiciaGratuita.oficio.turnos.yaexisteabreviatura");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {

						ScsTurnoExample example = new ScsTurnoExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()));

						ScsTurno scsTurno = new ScsTurno();
						List<ScsTurno> scsTurnoLista = scsTurnosExtendsMapper.selectByExample(example);
						scsTurno = scsTurnoLista.get(0);

						scsTurno.setIdinstitucion(idInstitucion);
						scsTurno.setAbreviatura(turnosItem.getAbreviatura());
						scsTurno.setNombre(turnosItem.getNombre());
						scsTurno.setCodigoext(turnosItem.getCodigoext());
						scsTurno.setIdpartidapresupuestaria(Integer.parseInt(turnosItem.getIdpartidapresupuestaria()));
						scsTurno.setIdgrupofacturacion(Short.parseShort(turnosItem.getIdgrupofacturacion()));
						scsTurno.setIdarea(Short.parseShort(turnosItem.getIdarea()));
						scsTurno.setIdmateria(Short.parseShort(turnosItem.getIdmateria()));
						scsTurno.setIdzona(Short.parseShort(turnosItem.getIdzona()));
						scsTurno.setIdsubzona(Short.parseShort(turnosItem.getIdsubzona()));
						
						if(turnosItem.getIdjurisdiccion() != null){
							scsTurno.setIdjurisdiccion(Short.parseShort(turnosItem.getIdjurisdiccion()));
						}else {
							scsTurno.setIdjurisdiccion(null);
						}
						
						if(turnosItem.getIdtipoturno() != null) {
						scsTurno.setIdtipoturno(Short.parseShort(turnosItem.getIdtipoturno()));
						}else {
							scsTurno.setIdtipoturno(null);
						}
						
						scsTurno.setFechamodificacion(new Date());
						scsTurno.setDescripcion(turnosItem.getDescripcion());
						scsTurno.setRequisitos(turnosItem.getRequisitos());
						scsTurno.setUsumodificacion(usuarios.get(0).getIdusuario());
						// scsTurno.setVisiblemovil(Short.parseShort(turnosItem.getVisiblemovil()));
						// scsTurno.setActivarretriccionacredit(turnosItem.getActivarretriccionacredit());
						// scsTurno.setValidarjustificaciones(turnosItem.getValidarjustificaciones());
						// scsTurno.setValidarinscripciones(turnosItem.getValidarinscripciones());
						// scsTurno.setLetradoactuaciones(turnosItem.getLetradoactuaciones());
						// scsTurno.setLetradoasistencias(turnosItem.getLetradoasistencias());
						// scsTurno.setGuardias(Short.parseShort(turnosItem.getIdguardias()));

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

						response = scsTurnosExtendsMapper.updateByPrimaryKey(scsTurno);
//								updateByPrimaryKeySelective(scsTurno);

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}
				} catch (Exception e) {
					LOGGER.error(
							"GestionTurnosServicesImpl.updateDatosGenerales() " + e.getMessage());
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateUltimo(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

					ScsTurnoExample example = new ScsTurnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()));

					ScsTurno scsTurno = new ScsTurno();
					List<ScsTurno> scsTurnoLista = scsTurnosExtendsMapper.selectByExample(example);
					scsTurno = scsTurnoLista.get(0);

					scsTurno.setIdinstitucion(idInstitucion);
					scsTurno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
					scsTurno.setFechamodificacion(new Date());
					scsTurno.setIdpersonaUltimo(Long.parseLong(turnosItem.getIdpersona()));
					scsTurno.setFechasolicitudUltimo(new Date());
					scsTurno.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

					scsTurnosExtendsMapper.updateUltimo(turnosItem, idInstitucion);

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}
	
	/**
	 * updateUltimoGuardias
	 */
	@Override
	public UpdateResponseDTO updateUltimoGuardias(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() ->  Entrada al servicio para modificar el ultimo de la cola de guardia");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() -> Realizando el update....");

					response = scsTurnosExtendsMapper.updateUltimoGuardias(turnosItem, idInstitucion);

					LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() -> Update realizado");
				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error("GestionTurnosServicesImpl.updateUltimoGuardias() -> Error realizando update: ", e);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("GestionTurnosServicesImpl.updateUltimoGuardias() -> Salida del servicio");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateConfiguracion(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de configuracion de turnos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
					List<ScsTurno> listaturno = null;
					ScsTurnoExample exampleturno = new ScsTurnoExample();
					if(turnosItem.getIdturno() != null) {
					exampleturno.createCriteria().andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()))
							.andIdinstitucionEqualTo(idInstitucion);
					}
					listaturno = scsTurnosExtendsMapper.selectByExample(exampleturno);

					ScsTurno turno = new ScsTurno();
					turno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
					turno.setIdinstitucion(idInstitucion);
					turno.setVisiblemovil(Short.parseShort(turnosItem.getVisiblemovil()));
					turno.setActivarretriccionacredit(turnosItem.getActivarretriccionacredit());
					turno.setDesignadirecta("N");
					turno.setFechamodificacion(new Date());
					turno.setGuardias(Short.parseShort(turnosItem.getIdguardias()));
					turno.setLetradoactuaciones(turnosItem.getLetradoactuaciones());
					turno.setLetradoasistencias(turnosItem.getLetradoasistencias());
					turno.setValidarinscripciones(turnosItem.getValidarinscripciones());
					turno.setValidarjustificaciones(turnosItem.getValidarjustificaciones());

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

					response = scsTurnosExtendsMapper.updateByPrimaryKeySelective(turno);

					List<ScsInscripcionturno> inscripcionturno = null;
					ScsInscripcionturnoExample example = new ScsInscripcionturnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno())).andFechabajaIsNull()
							.andFechavalidacionIsNotNull();
					inscripcionturno = scsInscripcionturnoMapper.selectByExample(example);

					List<ScsGuardiasturno> guardiasturno = null;
					ScsGuardiasturnoExample exampleguardias = new ScsGuardiasturnoExample();
					exampleguardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()));
					guardiasturno = scsGuardiasturnoMapper.selectByExample(exampleguardias);
					if ((listaturno.get(0).getGuardias() == 1 && turnosItem.getIdguardias().equals("0"))
							|| (listaturno.get(0).getGuardias() == 2 && turnosItem.getIdguardias().equals("0"))) {

						for (int i = 0; i < inscripcionturno.size(); i++) {
							Long persona = inscripcionturno.get(i).getIdpersona();
							for (ScsGuardiasturno guardia : guardiasturno) {
								Integer numeroguardia = guardia.getIdguardia();
								List<ScsInscripcionguardia> guardias = null;
								ScsInscripcionguardiaExample exampleinscripcionguardias = new ScsInscripcionguardiaExample();
								exampleinscripcionguardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdguardiaEqualTo(numeroguardia)
										.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()))
										.andIdpersonaEqualTo(persona);
								guardias = scsInscripcionguardiaMapper.selectByExample(exampleinscripcionguardias);
								if (guardias.size() == 0) {
									ScsInscripcionguardia nuevaguardia = new ScsInscripcionguardia();
									nuevaguardia.setIdpersona(persona);
									nuevaguardia.setIdinstitucion(idInstitucion);
									nuevaguardia.setIdguardia(numeroguardia);
									nuevaguardia.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
									nuevaguardia.setFechadenegacion(null);
									nuevaguardia.setFechavalidacion(new Date());
									nuevaguardia.setFechasuscripcion(new Date());
									nuevaguardia.setFechamodificacion(new Date());
									nuevaguardia.setUsumodificacion(usuarios.get(0).getIdusuario());
									scsInscripcionguardiaMapper.insert(nuevaguardia);
									LOGGER.info(
											"updateinscripcionguardia() / updateinscripcionguardia.insert() -> Entrada a updateinscripcionguardiaMapper para insertar el nuevo guardia");
								}
							}
						}
					}

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public Error guardartarjetaPesos2(HttpServletRequest request, TarjetaPesosDTO tarjetaPesos) {

		LOGGER.info("guardarPerfilesModelo() -> Entrada al servicio para guardar datos perfiles");
		Integer idOrdenacionNuevo = 0;
		Error respuesta = new Error();
		int response = 0;
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					NewIdDTO idOrdenacion = scsTurnosExtendsMapper.getIdOrdenacion(idInstitucion);
					ScsOrdenacioncolas ordenacion = new ScsOrdenacioncolas();
					idOrdenacionNuevo = (Integer.parseInt(idOrdenacion.getNewId()) + 1);
					// añadimos las etiquetas seleccionadas
					ScsOrdenacioncolas key = new ScsOrdenacioncolas();
					key.setFechamodificacion(new Date());
					key.setUsumodificacion(usuario.getUsumodificacion());
					key.setAlfabeticoapellidos(Short.parseShort("0"));
					key.setAntiguedadcola(Short.parseShort("0"));
					key.setFechanacimiento(Short.parseShort("0"));
					key.setNumerocolegiado(Short.parseShort("0"));
					for (int i = 0; i < tarjetaPesos.getPesosSeleccionados().size(); i++) {
						switch (tarjetaPesos.getPesosSeleccionados().get(i).getPor_filas()) {
						case "ALFABETICOAPELLIDOS":
							if (tarjetaPesos.getPesosSeleccionados().get(i).getOrden().equals("ascendente")) {
								key.setAlfabeticoapellidos(
										Short.parseShort(tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							} else {
								key.setAlfabeticoapellidos(Short
										.parseShort("-" + tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							}
							break;

						case "NUMEROCOLEGIADO":
							if (tarjetaPesos.getPesosSeleccionados().get(i).getOrden().equals("ascendente")) {
								key.setNumerocolegiado(
										Short.parseShort(tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							} else {
								key.setNumerocolegiado(Short
										.parseShort("-" + tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							}
							break;

						case "ANTIGUEDADCOLA":
							if (tarjetaPesos.getPesosSeleccionados().get(i).getOrden().equals("ascendente")) {
								key.setAntiguedadcola(
										Short.parseShort(tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							} else {
								key.setAntiguedadcola(Short
										.parseShort("-" + tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							}
							break;

						case "FECHANACIMIENTO":
							if (tarjetaPesos.getPesosSeleccionados().get(i).getOrden().equals("ascendente")) {
								key.setFechanacimiento(
										Short.parseShort(tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							} else {
								key.setFechanacimiento(Short
										.parseShort("-" + tarjetaPesos.getPesosSeleccionados().get(i).getNumero()));
							}
							break;

						default:
							break;
						}

					}
					ScsOrdenacioncolasExample example = new ScsOrdenacioncolasExample();
					example.createCriteria().andAlfabeticoapellidosEqualTo(key.getAlfabeticoapellidos())
							.andNumerocolegiadoEqualTo(key.getNumerocolegiado())
							.andAntiguedadcolaEqualTo(key.getAntiguedadcola())
							.andFechanacimientoEqualTo(key.getFechanacimiento());

					List<ScsOrdenacioncolas> scsordenacion = scsOrdenacioncolasExtendsMapper.selectByExample(example);

					if (scsordenacion != null && scsordenacion.size() > 0) {
						ordenacion = scsordenacion.get(0);
					} else {
						key.setIdordenacioncolas(idOrdenacionNuevo);
						scsOrdenacioncolasExtendsMapper.insertSelective(key);
					}

					ScsTurno turno = new ScsTurno();
					turno.setIdturno(Integer.parseInt(tarjetaPesos.getIdturno()));
					turno.setIdinstitucion(idInstitucion);
					if (scsordenacion != null && scsordenacion.size() > 0) {
						turno.setIdordenacioncolas(ordenacion.getIdordenacioncolas());
					} else {
						turno.setIdordenacioncolas(idOrdenacionNuevo);
					}
					response = scsTurnosExtendsMapper.updateByPrimaryKeySelective(turno);

					respuesta.setCode(200);
					respuesta.setDescription("Datos perfiles del modelo guardados correctamente");
					respuesta.setMessage("Updates correcto");
				} catch (Exception e) {
					response = 0;
					respuesta.setCode(500);
					respuesta.setDescription(e.getMessage());
					respuesta.setMessage("Error");
				}

			}
		}
		LOGGER.info("guardarPerfilesModelo() -> Salida del servicio para guardar datos perfiles");
		return respuesta;
	}

	@Override
	public InsertResponseDTO createTurnos(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("createTurnos() ->  Entrada al servicio para insertar turnos");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idTurnoNuevo = 0;
		Integer idOrdenacionNuevo = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);
			
			LOGGER.info(
					"createTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() >= 0) {
				try {
					ScsTurnoExample ejemplo = new ScsTurnoExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAbreviaturaEqualTo(turnosItem.getAbreviatura());

					List<ScsTurno> turnosExistentes = scsTurnosExtendsMapper.selectByExample(ejemplo);

					if ((turnosExistentes != null && turnosExistentes.size() > 0)) {
						response = 0;
						error.setCode(400);
						error.setDescription("justiciaGratuita.oficio.turnos.yaexisteabreviatura");
						insertResponseDTO.setStatus(SigaConstants.KO);
						insertResponseDTO.setError(error);
						return insertResponseDTO;
					} else {
						ScsTurno turno = new ScsTurno();
						ScsOrdenacioncolas ordenacion = new ScsOrdenacioncolas();
						NewIdDTO idTurno = scsTurnosExtendsMapper.getIdTurno(idInstitucion);
						NewIdDTO idOrdenacion = scsTurnosExtendsMapper.getIdOrdenacion(idInstitucion);

						if (idTurno == null) {
							idTurnoNuevo = 1;
							turno.setIdturno(1);
						} else {
							idOrdenacionNuevo = (Integer.parseInt(idOrdenacion.getNewId()) + 1);

							ordenacion.setIdordenacioncolas(Integer.parseInt(idOrdenacionNuevo.toString()));
							ordenacion.setAlfabeticoapellidos(Short.parseShort("4"));
							ordenacion.setFechamodificacion(new Date());
							ordenacion.setFechanacimiento(Short.parseShort("0"));
							ordenacion.setAntiguedadcola(Short.parseShort("0"));
							ordenacion.setNumerocolegiado(Short.parseShort("0"));
							ordenacion.setUsumodificacion(usuarios.get(0).getIdusuario());
							ordenacion.setOrdenacionmanual(Short.parseShort("0"));
							response = scsOrdenacioncolasMapper.insert(ordenacion);

							turno.setIdordenacioncolas(Integer.parseInt(idOrdenacionNuevo.toString()));
							idTurnoNuevo = (Integer.parseInt(idTurno.getNewId()) + 1);
							turno.setIdturno(Integer.parseInt(idTurnoNuevo.toString()));
						}

						turno.setIdinstitucion(idInstitucion);
						turno.setAbreviatura(turnosItem.getAbreviatura());
						turno.setNombre(turnosItem.getNombre());
						turno.setCodigoext(turnosItem.getCodigoext());
						turno.setIdpartidapresupuestaria(Integer.parseInt(turnosItem.getIdpartidapresupuestaria()));
						turno.setIdgrupofacturacion(Short.parseShort(turnosItem.getIdgrupofacturacion()));
						turno.setIdarea(Short.parseShort(turnosItem.getIdarea()));
						turno.setIdmateria(Short.parseShort(turnosItem.getIdmateria()));
						if(turnosItem.getIdjurisdiccion() != null){
							turno.setIdjurisdiccion(Short.parseShort(turnosItem.getIdjurisdiccion()));
						}
						turno.setIdzona(Short.parseShort(turnosItem.getIdzona()));
						turno.setIdsubzona(Short.parseShort(turnosItem.getIdsubzona()));
						if(turnosItem.getIdtipoturno() != null){
							turno.setIdtipoturno(Short.parseShort(turnosItem.getIdtipoturno()));
						}
						turno.setFechamodificacion(new Date());
						turno.setDescripcion(turnosItem.getDescripcion());
						turno.setRequisitos(turnosItem.getRequisitos());
						turno.setUsumodificacion(usuarios.get(0).getIdusuario());
						turno.setGuardias(Short.parseShort("2"));
						turno.setVisiblemovil(Short.parseShort("0"));
						turno.setActivarretriccionacredit("S");
						turno.setValidarjustificaciones("S");
						turno.setValidarinscripciones("S");
						turno.setLetradoactuaciones("N");
						turno.setLetradoasistencias("N");
						turno.setDesignadirecta("N");
						turno.setVisibilidad("1");

						LOGGER.info(
								"createTurnos() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

						response = scsTurnosExtendsMapper.insert(turno);

						LOGGER.info(
								"createTurnos() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

					}

				} catch (Exception e) {
					LOGGER.error(
							"createTurnos()" + e.getMessage());

					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if (error.getDescription() == null) {
				error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idTurnoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;
	}

	public ComboColaOrdenadaDTO ordenCola(HttpServletRequest request, TurnosItem turnosItem) {
		LOGGER.info("getPerfiles() -> Entrada al servicio para obtener los perfiles disponibles");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ComboColaOrdenadaDTO comboDTO = new ComboColaOrdenadaDTO();
		List<ComboColaOrdenadaItem> comboItems = new ArrayList<ComboColaOrdenadaItem>();

		try {
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				AdmUsuarios usuario = usuarios.get(0);
				comboItems = scsOrdenacioncolasExtendsMapper.ordenColas(turnosItem.getIdordenacioncolas());
				comboDTO.setColaOrden(comboItems);

			}
		} catch (Exception e) {
			Error error = new Error();
			error.setCode(500);
			error.setDescription("Error al guardar datos generales");
			error.setMessage(e.getMessage());
			comboDTO.setError(error);
			e.printStackTrace();
		}

		LOGGER.info("getPerfiles() -> Salida del servicio para obtener los perfiles disponibles");
		return comboDTO;
	}

	@Override
	public TurnosDTO busquedaColaOficio(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TurnosDTO turnosDTO = new TurnosDTO();
		List<TurnosItem> turnosItems = new ArrayList<>();
		boolean foundUltimo;
		TurnosItem punteroTurno = null;
		String busquedaOrden = "";

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaColaOficio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaColaOficio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"busquedaColaOficio() -> Entrada a scsOrdenacioncolasExtendsMapper para obtener orden colas");
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				comboItems = scsOrdenacioncolasExtendsMapper.ordenColasEnvios(turnosItem.getIdordenacioncolas());
				comboDTO.setCombooItems(comboItems);

				for (int i = 0; i < comboDTO.getCombooItems().size(); i++) {
					if (!comboDTO.getCombooItems().get(i).getLabel().toString().equals("0")) {
						busquedaOrden += comboDTO.getCombooItems().get(i).getValue() + ",";
					}
				}

				if (busquedaOrden != null && busquedaOrden.length() > 0) {
					busquedaOrden = busquedaOrden.substring(0, busquedaOrden.length() - 1);
				}

				Date prueba = turnosItem.getFechaActual();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = dateFormat.format(prueba);
				if (turnosItem.getIdpersona_ultimo() != null) {
					List<TurnosItem> turnosAuxiliar = new ArrayList<TurnosItem>();
					List<TurnosItem> listaTurnos = scsTurnosExtendsMapper.busquedaColaOficio(turnosItem, strDate, busquedaOrden, idInstitucion);
					foundUltimo = false;
					int indiceOrden = 0;

					for (int i = 0; i < listaTurnos.size(); i++) {

						punteroTurno = listaTurnos.get(i);
						punteroTurno.setOrden(String.valueOf(i + 1));

						if (foundUltimo) {
							turnosItems.add(punteroTurno);
						} else {
							turnosAuxiliar.add(punteroTurno);
						}

						if (!foundUltimo && (punteroTurno.getIdpersona().equals(turnosItem.getIdpersona_ultimo()))) {
							foundUltimo = true;
						}
					}

					if (turnosAuxiliar.size() > 0) {
						turnosItems.addAll(turnosAuxiliar);
					}

					// Reordenar correctamente la lista
					for (TurnosItem turno : turnosItems) {
						turno.setOrden(String.valueOf(indiceOrden + 1));
						turnosItems.set(indiceOrden, turno);
						indiceOrden++;
					}

				} else {
					turnosItems = scsTurnosExtendsMapper.busquedaColaOficio2(turnosItem, strDate, busquedaOrden,
							idInstitucion);
				}

				LOGGER.info(
						"busquedaColaOficio()  -> Salida a scsOrdenacioncolasExtendsMapper para obtener orden colas");

				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("busquedaColaOficio() -> Salida del servicio para obtener la busqueda Cola Oficio");
		return turnosDTO;
	}

	@Override
	public TurnosDTO busquedaColaGuardia(TurnosItem turnosItem, HttpServletRequest request) {
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		TurnosDTO turnosDTO = new TurnosDTO();
		
		List<TurnosItem> turnosItems = null;
		List<ScsGuardiasturno> scsGuardiaLista = null;
		String busquedaOrden = "";
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaColaOficio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaColaOficio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"busquedaColaOficio() -> Entrada a scsOrdenacioncolasExtendsMapper para obtener orden colas");
				ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
				example.createCriteria().andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()))
						.andIdguardiaEqualTo(Integer.parseInt(turnosItem.getIdcomboguardias()))
						.andIdinstitucionEqualTo(idInstitucion);
				scsGuardiaLista = scsGuardiasturnoExtendsMapper.selectByExample(example);

				ScsGuardiasturno guardiasItems = scsGuardiaLista.get(0);

				if(guardiasItems.getIdpersonaUltimo()!=null){
					turnosItem.setIdpersona_ultimo(String.valueOf(guardiasItems.getIdpersonaUltimo()));
				}
				
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				comboItems = scsOrdenacioncolasExtendsMapper
						.ordenColasEnvios(guardiasItems.getIdordenacioncolas().toString());
				comboDTO.setCombooItems(comboItems);

				for (int i = 0; i < comboDTO.getCombooItems().size(); i++) {
					if (!comboDTO.getCombooItems().get(i).getLabel().toString().equals("0")) {
						busquedaOrden += comboDTO.getCombooItems().get(i).getValue() + ",";
					}
				}
				if (busquedaOrden != null && busquedaOrden.length() > 0) {
					busquedaOrden = busquedaOrden.substring(0, busquedaOrden.length() - 1);
				}
				Date prueba = turnosItem.getFechaActual();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = dateFormat.format(prueba);
				turnosItems = scsTurnosExtendsMapper.busquedaColaGuardia(turnosItem,strDate, busquedaOrden,
						idInstitucion);
				
				turnosItems = turnosItems.stream().map(item ->{
					DateFormat dateFormatBBDD = new SimpleDateFormat("yyyy-MM-dd");
					try {
						if(!UtilidadesString.esCadenaVacia(item.getFechavalidacion())) {
							item.setFechavalidacion(dateFormat.format(dateFormatBBDD.parse(item.getFechavalidacion())));
						}
						if(!UtilidadesString.esCadenaVacia(item.getFechabajaguardia())) {
							item.setFechabajaguardia(dateFormat.format(dateFormatBBDD.parse(item.getFechabajaguardia())));
						}
					} catch (ParseException e) {
						LOGGER.error("busquedaColaGuardias() -> Error al parsear la fechavalidacion y/o fechabajaguardia");
					}
					return item;
					}).collect(Collectors.toList());

				LOGGER.info(
						"busquedaColaGuardias()  -> Salida a scsOrdenacioncolasExtendsMapper para obtener orden colas");

				if (turnosItems != null) {
					turnosDTO.setTurnosItems(turnosItems);
				}
			}

		}
		LOGGER.info("busquedaColaOficio() -> Salida del servicio para obtener la busqueda Cola Oficio");
		return turnosDTO;
	}

	@Override
	public UpdateResponseDTO eliminateColaOficio(TurnosDTO turnosDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (TurnosItem turnosItem : turnosDTO.getTurnosItems()) {

						ScsInscripcionturnoExample turno = new ScsInscripcionturnoExample();
						turno.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()))
								.andIdpersonaEqualTo(Long.parseLong(turnosItem.getIdpersona()));

						LOGGER.info(
								"deleteProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsInscripcionturno> inscripcionturnosList = scsInscripcionturnoMapper
								.selectByExample(turno);

						if (null != inscripcionturnosList && inscripcionturnosList.size() > 0) {

							ScsInscripcionturno turnoupdate = inscripcionturnosList.get(0);

							if (turnosItem.getFechabajapersona() == null) {
								turnoupdate.setFechabaja(new Date());
							} else {
								turnoupdate.setFechabaja(null);
							}
							turnoupdate.setFechamodificacion(new Date());
							turnoupdate.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

							response = scsInscripcionturnoMapper.updateByPrimaryKey(turnoupdate);
						}

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO eliminateGuardia(GuardiasDTO guardiasDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (GuardiasItem guardiasItem : guardiasDTO.getGuardiaItems()) {

						ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.parseInt(guardiasItem.getIdTurno()))
								.andIdguardiaEqualTo(Integer.parseInt(guardiasItem.getIdGuardia()));

						LOGGER.info(
								"deleteProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsGuardiasturno> guardiasturnoList = scsGuardiasturnoExtendsMapper
								.selectByExample(example);

						if (null != guardiasturnoList && guardiasturnoList.size() > 0) {

							ScsGuardiasturno guardiasupdate = guardiasturnoList.get(0);

							if (guardiasItem.getFechabaja() == null) {
								guardiasupdate.setFechabaja(new Date());
							} else {
								guardiasupdate.setFechabaja(null);
							}
							guardiasupdate.setFechamodificacion(new Date());
							guardiasupdate.setUsumodificacion(usuarios.get(0).getIdusuario());

							LOGGER.info(
									"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

							response = scsGuardiasturnoExtendsMapper.updateByPrimaryKey(guardiasupdate);
						}

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO eliminateColaGuardia(TurnosDTO turnosDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (TurnosItem turnosItem : turnosDTO.getTurnosItems()) {

						ScsInscripcionguardiaExample guardia = new ScsInscripcionguardiaExample();
						guardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()))
								.andIdguardiaEqualTo(Integer.parseInt(turnosItem.getIdcomboguardias()))
								.andIdpersonaEqualTo(Long.parseLong(turnosItem.getIdpersona()));

						LOGGER.info(
								"deleteProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsInscripcionguardia> inscripcionguardiasList = scsInscripcionguardiaMapper
								.selectByExample(guardia);

						if (null != inscripcionguardiasList && inscripcionguardiasList.size() > 0) {
							for (int i = 0; i < inscripcionguardiasList.size(); i++) {
								ScsInscripcionguardia guardiaupdate = inscripcionguardiasList.get(i);

								if (turnosItem.getFechabajaguardia() == null) {
									guardiaupdate.setFechabaja(new Date());
								} else {
									guardiaupdate.setFechabaja(null);
								}
								guardiaupdate.setFechamodificacion(new Date());
								guardiaupdate.setUsumodificacion(usuarios.get(0).getIdusuario());

								LOGGER.info(
										"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

								response = scsInscripcionguardiaMapper.updateByPrimaryKey(guardiaupdate);
							}
						}

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}

	@Override
	public GuardiasDTO busquedaGuardias(TurnosItem turnosItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiasDTO = new GuardiasDTO();
		List<GuardiasItem> guardiaItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				guardiaItems = scsGuardiasturnoExtendsMapper.searchGuardias(turnosItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");
				guardiaItems = guardiaItems.stream().map(it -> {
					it.setTipoDia(("Labor. " + it.getSeleccionLaborables() + ", Fest. " + it.getSeleccionFestivos())
							.replace("null", ""));
					return it;
				}).collect(Collectors.toList());

				if (guardiaItems != null) {
					guardiasDTO.setGuardiaItems(guardiaItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return guardiasDTO;
	}
	
	@Override
	public UpdateResponseDTO changeRequisitoGuardias(TurnosItem turnosItem, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");

					ScsTurnoExample example = new ScsTurnoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(turnosItem.getIdturno()));

					ScsTurno scsTurno = new ScsTurno();
					
					List<ScsTurno> scsTurnoLista = scsTurnosExtendsMapper.selectByExample(example);
					scsTurno = scsTurnoLista.get(0);

					scsTurno.setIdinstitucion(idInstitucion);
					scsTurno.setIdturno(Integer.parseInt(turnosItem.getIdturno()));
					scsTurno.setFechamodificacion(new Date());
					if(turnosItem.getIdpersona() != null) {
					scsTurno.setIdpersonaUltimo(Long.parseLong(turnosItem.getIdpersona()));
					}
					scsTurno.setFechasolicitudUltimo(new Date());
					scsTurno.setUsumodificacion(usuarios.get(0).getIdusuario());
					
					InscripcionesItem inscripcionesItem = new InscripcionesItem();
					inscripcionesItem.setIdinstitucion(idInstitucion.toString());
					inscripcionesItem.setIdturno(turnosItem.getIdturno());

					String fechahasta = "";
					String fechadesde = "";
					String afechade = "";
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					if (inscripcionesItem.getFechadesde() != null) {
						Date fechad = inscripcionesItem.getFechadesde();

						fechadesde = dateFormat.format(fechad);
						if (inscripcionesItem.getFechahasta() != null) {
							Date fechah = inscripcionesItem.getFechahasta();
							fechahasta = dateFormat.format(fechah);

						}
					}
					if (inscripcionesItem.getAfechade() != null) {
						Date afechades = inscripcionesItem.getAfechade();
						afechade = dateFormat.format(afechades);
					}
					
					List<InscripcionesItem> inscripcionesTurno = null;
					inscripcionesTurno = scsInscripcionturnoExtendsMapper.busquedaInscripciones(inscripcionesItem,
							idInstitucion, fechadesde, fechahasta, afechade, null);
					List<ComboItem> guardiasTurno = scsGuardiasturnoExtendsMapper.comboGuardias(turnosItem.getIdturno(),
							idInstitucion.toString());
				
					List<InscripcionesItem> colegiadosAinscribir = new ArrayList<>();
					for(InscripcionesItem inscripciones: inscripcionesTurno) {
						for(ComboItem guardia: guardiasTurno) {
							if(inscripciones.getIdguardia().equals(guardia.getValue())) {
								colegiadosAinscribir.add(inscripciones);
							}
						}
						
					}
					
					//Añadimos los colegiados a las guardias

				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}
}
