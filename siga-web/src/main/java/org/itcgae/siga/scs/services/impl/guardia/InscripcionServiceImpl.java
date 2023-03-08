package org.itcgae.siga.scs.services.impl.guardia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionMod;
import org.itcgae.siga.DTOs.scs.GestionInscripcion;
import org.itcgae.siga.DTOs.scs.GuardiasTurnosItem;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TrabajosSJCSInsGuardiaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionturnoKey;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoKey;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

	private final String VALIDACION_BAJA = "Validacion Baja";
	private final String DENEGACION = "Denegacion";
	private final String SOLICITUD_DE_ALTA = "Solicitud de Alta";
	private final String SOLICITUD_DE_BAJA = "Solicitud de Baja";
	private final String SOLICITUD = "Solicitud";

	private Logger LOGGER = Logger.getLogger(InscripcionServiceImpl.class);

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;

	@Autowired
	private ScsInscripcionguardiaExtendsMapper inscripcionGuardiaExtensdsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

	@Autowired
	private ScsInscripcionturnoMapper scsInscripcionturnoMapper;

	@Autowired
	private ScsInscripcionguardiaMapper scsInscripcionguardiaMapper;

	@Override
	@Transactional
	public UpdateResponseDTO validarInscripciones(List<BusquedaInscripcionMod> validarBody, HttpServletRequest request)
			throws Exception {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//BusquedaInscripcionMod objeto = new BusquedaInscripcionMod();
		//GrupoGuardiaColegiadoItem objetoFK = new GrupoGuardiaColegiadoItem();

		int contadorKO = 0;

		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("validarInscripciones() -> Entrada para validar las inscripciones");

				for (BusquedaInscripcionMod inscripcion : validarBody) {

					//Obtenemos la inscripcion
					ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
					key.setFechasuscripcion(inscripcion.getFechasolicitud());
					key.setIdguardia(Integer.valueOf(inscripcion.getIdguardia()));
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.valueOf(inscripcion.getIdpersona()));
					key.setIdturno(Integer.valueOf(inscripcion.getIdturno()));
					
					ScsInscripcionguardia objeto = inscripcionGuardiaExtensdsMapper.selectByPrimaryKey(key);
					
					ScsInscripcionturnoKey keyInscripcion = new ScsInscripcionturnoKey();

					keyInscripcion.setIdturno(Integer.valueOf(inscripcion.getIdturno()));
					keyInscripcion.setIdinstitucion(idInstitucion);
                    keyInscripcion.setIdpersona(Long.parseLong(inscripcion.getIdpersona()));
                    keyInscripcion.setFechasolicitud( inscripcion.getFechasolicitud());
                    
                    ScsInscripcionturno inscripcionTurno = scsInscripcionturnoMapper.selectByPrimaryKey(keyInscripcion);
					
					if(objeto!= null) {
						/*if(inscripcion.getFechasolicitudNUEVA() != null) {
							objeto.setFechasuscripcion(inscripcion.getFechasolicitudNUEVA());
						}else {
							if(inscripcion.getFechasolicitud() != null) {
								objeto.setFechasuscripcion(inscripcion.getFechasolicitud());
							}
						}*/
						
						objeto.setFechamodificacion(new Date());
						objeto.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						inscripcionTurno.setFechamodificacion(new Date());
						inscripcionTurno.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						if(inscripcion.getFechabaja() != null) {
							objeto.setFechabaja(inscripcion.getFechabaja());
							inscripcionTurno.setFechabaja(inscripcion.getFechabaja());
						}
						
						/*if(inscripcion.getObservacionessolicitudNUEVA() != null) {
							objeto.setObservacionessuscripcion(inscripcion.getObservacionessolicitudNUEVA());
						}*/
						
						if(inscripcion.getObservacionesbaja() != null) {
							objeto.setObservacionesbaja(inscripcion.getObservacionesbaja());
							inscripcionTurno.setObservacionesbaja(inscripcion.getObservacionesbaja());
						}
						
						if(inscripcion.getFechasolicitudbajaNUEVA() != null) {
							objeto.setFechasolicitudbaja(inscripcion.getFechasolicitudbajaNUEVA());
							inscripcionTurno.setFechasolicitudbaja(inscripcion.getFechasolicitudbajaNUEVA());
						}
						
						if(inscripcion.getObservacionesvalbajaNUEVA() != null) {
							objeto.setObservacionesvalbaja(inscripcion.getObservacionesvalbajaNUEVA());
							inscripcionTurno.setObservacionesvalbaja(inscripcion.getObservacionesvalbajaNUEVA());
						}
						
						if(inscripcion.getFechavalidacionNUEVA() != null) {
							objeto.setFechavalidacion(inscripcion.getFechavalidacionNUEVA());
							inscripcionTurno.setFechavalidacion(inscripcion.getFechavalidacionNUEVA());
						}
						
						if(inscripcion.getObservacionesvalidacionNUEVA() != null) {
							objeto.setObservacionesvalidacion(inscripcion.getObservacionesvalidacionNUEVA());
							inscripcionTurno.setObservacionesvalidacion(inscripcion.getObservacionesvalidacionNUEVA());
						}
						
						if(inscripcion.getFechadenegacionNUEVA() != null) {
							objeto.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
							inscripcionTurno.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
						}
						
						if(inscripcion.getObservacionesdenegacionNUEVA() != null) {
							objeto.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
							inscripcionTurno.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
						}
						
						contadorKO = inscripcionGuardiaExtensdsMapper.updateByPrimaryKeySelective(objeto);
						contadorKO = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionTurno);
	
						LOGGER.info(
								"validarInscripciones() -> Salida ya con los datos actualizados y la inscripción validada");
					}
				}
			}
		}

		if (contadorKO != 0) {
			upd.setStatus(SigaConstants.OK);
		} else {
			upd.setStatus(SigaConstants.KO);
			throw (new Exception("Error al Validar la inscripcion"));
		}

		return upd;
	}

	@Override
	public UpdateResponseDTO solicitarBajaInscripcion(List<BusquedaInscripcionMod> solicitarbajabody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int inscripciones = 0;
		int contadorKO = 0;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"solicitarBajaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("solicitarBajaInscripcion() -> Entrada para modificar los datos correspondientes");


				for (BusquedaInscripcionMod a : solicitarbajabody) {
					
					int usuario = usuarios.get(0).getIdusuario();

					/*
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(a.getIdpersona()))
							.andIdturnoEqualTo(Integer.valueOf(a.getIdturno())).andIdguardiaEqualTo(Integer.valueOf(a.getIdguardia()));
								
					List<ScsInscripcionguardia> listaInscripciones = inscripcionGuardiaExtensdsMapper.selectByExample(example);

					ScsInscripcionguardia record = listaInscripciones.get(0);
					*/
					//Obtenemos la inscripcion
					ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
					key.setFechasuscripcion(a.getFechasolicitud());
					key.setIdguardia(Integer.valueOf(a.getIdguardia()));
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.valueOf(a.getIdpersona()));
					key.setIdturno(Integer.valueOf(a.getIdturno()));
					
					ScsInscripcionguardia record = inscripcionGuardiaExtensdsMapper.selectByPrimaryKey(key);
					
					if(record!= null) {
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario);
						
						if (a.getFechasolicitudNUEVA() != null) {
							record.setFechasuscripcion(a.getFechasolicitudNUEVA());
						}
						
						if (a.getFechabaja() != null) {
							record.setFechabaja(a.getFechabaja());
						}
						if (a.getObservacionessolicitudNUEVA() != null) {
							record.setObservacionessuscripcion(a.getObservacionessolicitudNUEVA());
						}
						if (a.getObservacionesbaja() != null ) {
							record.setObservacionesbaja(a.getObservacionesbaja());
						}
						
						if (a.getFechasolicitudbajaNUEVA() != null) {
							record.setFechasolicitudbaja(a.getFechasolicitudbajaNUEVA());
						}
						
						if (a.getFechavalidacionNUEVA() != null) {
							record.setFechavalidacion(a.getFechavalidacionNUEVA());
						}
						if (a.getObservacionesvalidacionNUEVA() != null ) {
							record.setObservacionesvalidacion(a.getObservacionesvalidacionNUEVA());
						}
						if (a.getFechadenegacionNUEVA() != null) {
							record.setFechadenegacion(a.getFechadenegacionNUEVA());
						}
						if (a.getObservacionesdenegacionNUEVA() != null ) {
							record.setObservacionesdenegacion(a.getObservacionesdenegacionNUEVA());
						}
						if (a.getObservacionesvalbajaNUEVA() != null ) {
							record.setObservacionesvalbaja(a.getObservacionesvalbajaNUEVA());
						}
						
						inscripciones = inscripcionGuardiaExtensdsMapper.updateByPrimaryKeySelective(record);
					}
					if (inscripciones != 0) {
						contadorKO++;
					}
						
				}

				LOGGER.info("solicitarBajaInscripcion() -> Salida ya con los datos modificados");
			}
		}

		if (contadorKO != 0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);

		return upd;
	}

	@Override
	public UpdateResponseDTO denegarInscripcion(List<BusquedaInscripcionMod> cambiarfechabody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<BusquedaInscripcionItem> inscripciones = new
		// ArrayList<BusquedaInscripcionItem>();
		int inscripciones = 0;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cambiarFechaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("denegarInscripcion() -> Entrada para modificar los datos correspondientes");

				for (BusquedaInscripcionMod a : cambiarfechabody) {
					
					int usuario = usuarios.get(0).getIdusuario();

					/*
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(a.getIdpersona()))
							.andIdturnoEqualTo(Integer.valueOf(a.getIdturno())).andIdguardiaEqualTo(Integer.valueOf(a.getIdguardia()));
								
					List<ScsInscripcionguardia> listaInscripciones = inscripcionGuardiaExtensdsMapper.selectByExample(example);

					ScsInscripcionguardia record = listaInscripciones.get(0);
					 */
					//Obtenemos la inscripcion
					ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
					key.setFechasuscripcion(a.getFechasolicitud());
					key.setIdguardia(Integer.valueOf(a.getIdguardia()));
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.valueOf(a.getIdpersona()));
					key.setIdturno(Integer.valueOf(a.getIdturno()));
					
					ScsInscripcionguardia record = inscripcionGuardiaExtensdsMapper.selectByPrimaryKey(key);
					
					ScsInscripcionturnoKey keyInscripcion = new ScsInscripcionturnoKey();

					keyInscripcion.setIdturno(Integer.valueOf(a.getIdturno()));
					keyInscripcion.setIdinstitucion(idInstitucion);
                    keyInscripcion.setIdpersona(Long.parseLong(a.getIdpersona()));
                    keyInscripcion.setFechasolicitud( a.getFechasolicitud());
                    
                    ScsInscripcionturno inscripcionTurno = scsInscripcionturnoMapper.selectByPrimaryKey(keyInscripcion);
					
					if(record!= null) {
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario);
						
						inscripcionTurno.setFechamodificacion(new Date());
						inscripcionTurno.setUsumodificacion(usuario);
						
						if (a.getFechadenegacionNUEVA() != null) {
							record.setFechadenegacion(a.getFechadenegacionNUEVA());
							inscripcionTurno.setFechadenegacion(a.getFechadenegacionNUEVA());
							if(a.getObservacionesdenegacionNUEVA()!= null) {
								record.setObservacionesdenegacion(a.getObservacionesdenegacionNUEVA());
								inscripcionTurno.setObservacionesdenegacion(a.getObservacionesdenegacionNUEVA());
							}
						}
						
						inscripciones = inscripcionGuardiaExtensdsMapper.updateByPrimaryKeySelective(record);
						inscripciones = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionTurno);
						
					}
	
					LOGGER.info("denegarInscripcion() -> Salida ya con los datos modificados");
				}
			}
		}

		if (inscripciones != 0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);

		return upd;
	}

	@Override
	public UpdateResponseDTO cambiarFechaInscripcion(List<BusquedaInscripcionMod> cambiarfechabody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// List<BusquedaInscripcionItem> inscripciones = new
		// ArrayList<BusquedaInscripcionItem>();
		int inscripciones = 0;
		int contadorKO = 0;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cambiarFechaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("cambiarFechaInscripcion() -> Entrada para modificar los datos correspondientes");

				for (BusquedaInscripcionMod a : cambiarfechabody) {

					int usuario = usuarios.get(0).getIdusuario();
					
					/*
					ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(a.getIdpersona()))
							.andIdturnoEqualTo(Integer.valueOf(a.getIdturno())).andIdguardiaEqualTo(Integer.valueOf(a.getIdguardia()));
								
					List<ScsInscripcionguardia> listaInscripciones = inscripcionGuardiaExtensdsMapper.selectByExample(example);

					ScsInscripcionguardia record = listaInscripciones.get(0);
					*/
					//Obtenemos la inscripcion
					ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
					key.setFechasuscripcion(a.getFechasolicitud());
					key.setIdguardia(Integer.valueOf(a.getIdguardia()));
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.valueOf(a.getIdpersona()));
					key.setIdturno(Integer.valueOf(a.getIdturno()));
					
					ScsInscripcionguardia record = inscripcionGuardiaExtensdsMapper.selectByPrimaryKey(key);
					
					if(record!= null) {
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuario);
						
						if (a.getFechabaja() != null) {
							record.setFechabaja(a.getFechabaja());
							if(a.getObservacionessolicitudNUEVA()!= null) {
								record.setObservacionesvalbaja(a.getObservacionessolicitudNUEVA());
							}
						}
						
						if (a.getFechavalidacionNUEVA() != null) {
							record.setFechavalidacion(a.getFechavalidacionNUEVA());
							if(a.getObservacionessolicitudNUEVA()!= null) {
								record.setObservacionesvalidacion(a.getObservacionessolicitudNUEVA());
							}
						}
						
						inscripciones = inscripcionGuardiaExtensdsMapper.updateByPrimaryKeySelective(record);
					}
					if (inscripciones != 0)
						contadorKO++;
				}

				LOGGER.info("cambiarFechaInscripcion() -> Salida ya con los datos modificados");
			}
		}

		if (contadorKO != 0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);

		return upd;
	}

	@Override
	public Boolean buscarSaltosCompensaciones(List<BusquedaInscripcionMod> buscarSaltosCompensaciones,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<SaltoCompGuardiaItem> saltosList = new ArrayList<SaltoCompGuardiaItem>();
		List<SaltoCompGuardiaItem> compensacionesList = new ArrayList<SaltoCompGuardiaItem>();

		int contadorKO = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarSaltosCompensaciones() -> Entrada para recoger los datos correspondientes");

				for (BusquedaInscripcionMod a : buscarSaltosCompensaciones) {

					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();
					String saltos = "S";
					String compensaciones = "C";

					// buscar primero los saltos y si hay que los borre, que primero busque para que
					// no de error de eliminación si no hay
					saltosList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),
							idturno, idguardia, idpersona,saltos);

					
					// lo mismo para las compensaciones
					compensacionesList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(
							idInstitucion.toString(), idturno, idguardia, idpersona, compensaciones);
					

					if (saltosList.size() != 0 || compensacionesList.size() != 0) {
						contadorKO++;
					}
				}

				LOGGER.info(
						"buscarSaltosCompensaciones() -> Salida ya con los datos de saltos y compensaciones recogidos");
			}
		}

		boolean existe;

		if (contadorKO == 0) {
			existe = false;
		}else {
			existe = true;
		}
			

		return existe; // luego cuando vaya al front si data es igual a true significa que hay saltos y
						// compensaciones, si no es que no hay.
	}

	@Override
	public Boolean buscarTrabajosSJCS(List<BusquedaInscripcionMod> buscarTabajosSJCS, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<TrabajosSJCSInsGuardiaItem> trabajosSJCSListGuardias = new ArrayList<TrabajosSJCSInsGuardiaItem>();
		List<TrabajosSJCSInsGuardiaItem> trabajosSJCSListPendientes = new ArrayList<TrabajosSJCSInsGuardiaItem>();

		int contadorKO = 0;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual;
		Date fecha = new Date();
		fechaActual = dateFormat.format(fecha);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarTrabajosSJCS() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarTrabajosSJCS() -> Entrada para recoger los datos correspondientes");

				for (BusquedaInscripcionMod a : buscarTabajosSJCS) {

					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();

					trabajosSJCSListGuardias= inscripcionGuardiaExtensdsMapper.busquedaTrabajosGuardias(idpersona,idturno, idguardia ,idInstitucion, fechaActual);
					trabajosSJCSListPendientes = inscripcionGuardiaExtensdsMapper.busquedaTrabajosPendientes(idpersona, idturno, idInstitucion, fechaActual);
					
					if(trabajosSJCSListGuardias.size()>0 || trabajosSJCSListPendientes.size() >0) {
						contadorKO++;
					}
				
				}

				LOGGER.info("buscarTrabajosSJCS() -> Salida ya con los datos de los trabajos SJCS recogidos");
			}
		}

		boolean existe;

		if (contadorKO > 0 ) {
			existe = true;
		}else {
			existe = false;
		}

		return existe;
	}

	@Override
	public Boolean buscarGuardiasAsocTurnos(List<BusquedaInscripcionMod> buscarGuardiasAsocTurnos,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasTurnosItem> listaGuardias = new ArrayList<GuardiasTurnosItem>();

		int contadorKO = 0;
		int OKUpdateTurno = 0;
		int OKUpdateGuardia = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarGuardiasAsocTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarGuardiasAsocTurnos() -> Entrada para recoger los datos correspondientes");

				String requeridaValidacion = "";

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA = null;

				for (BusquedaInscripcionMod a : buscarGuardiasAsocTurnos) {

					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();

					listaGuardias = inscripcionGuardiaExtensdsMapper
							.getbuscarGuardiasAsocTurnos(idInstitucion.toString(), idturno, idguardia, idpersona);

					// modificar el turno según si es requerida o no
					String validarinscripciones = inscripcionGuardiaExtensdsMapper
							.validarInscripcionesCampo(idInstitucion.toString(), idturno, idguardia, idpersona);

					if (validarinscripciones.equals("S")) {
						// se queda en Pendiente de Baja, donde la fecha de baja es null
						a.setFechabaja(null);
					}

					Date fechaHoy = new Date();
					if (validarinscripciones.equals("N")) {
						// se cambia a baja donde la fecha de baja es distinta de null
						a.setFechabaja(fechaHoy);

					}

					if (a.getFechabaja() != null) {
						FECHABAJA = formatter.format(a.getFechabaja());
					}

					// actualizar scs_inscripcionturno
					if (a.getFechabaja() != null) {
						OKUpdateTurno = inscripcionGuardiaExtensdsMapper
								.UpdateInscripcionTurno(idInstitucion.toString(), idturno, a, FECHABAJA);
					}

					if (listaGuardias.size() > 0) {
						for (GuardiasTurnosItem b : listaGuardias) {
							// buscar si es Requerida o no
							requeridaValidacion = inscripcionGuardiaExtensdsMapper
									.requeridaValidacionCampo(idInstitucion.toString(), idturno, idguardia, idpersona);
							// modificar la fecha y el estado
							if (requeridaValidacion.equals("S")) {
								// se queda en Pendiente de Baja, donde la fecha de baja es null
								a.setFechabaja(null);
							}

							Date fechaHoy2 = new Date();
							if (requeridaValidacion.equals("N")) {
								// se cambia a baja donde la fecha de baja es distinta de null
								a.setFechabaja(fechaHoy2);

							}

							if (a.getFechabaja() != null) {
								FECHABAJA = formatter.format(a.getFechabaja());
							}

							// actualizar scs_inscripcionguardia
							if (a.getFechabaja() != null) {
								OKUpdateGuardia = inscripcionGuardiaExtensdsMapper.UpdateInscripcionGuardia(
										idInstitucion.toString(), idturno, b.getIdguardia(), a, FECHABAJA);
							}
						}
					}

					if (OKUpdateTurno != 0 || OKUpdateGuardia != 0)
						contadorKO++;
				}

				LOGGER.info("buscarGuardiasAsocTurnos() -> Salida ya con los datos de los trabajos SJCS recogidos");
			}
		}

		boolean existe;

		if (contadorKO != 0)
			existe = true;
		else
			existe = false;

		return existe;
	}

	@Override
	@Transactional
	public DeleteResponseDTO eliminarSaltosCompensaciones(List<BusquedaInscripcionMod> eliminarSaltosCompensaciones,
			HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<SaltoCompGuardiaItem> saltosList = new ArrayList<SaltoCompGuardiaItem>();
		List<SaltoCompGuardiaItem> compensacionesList = new ArrayList<SaltoCompGuardiaItem>();

		int contadorKO = 0;
		int saltosN = 0;
		int compensacionesN = 0;

		DeleteResponseDTO drp = new DeleteResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"eliminarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("eliminarSaltosCompensaciones() -> Entrada para borrar los datos correspondientes");

				for (BusquedaInscripcionMod inscripcion : eliminarSaltosCompensaciones) {

					String idturno = inscripcion.getIdturno();
					String idguardia = inscripcion.getIdguardia();
					String idpersona = inscripcion.getIdpersona();
					String saltos = "S";
					String compensaciones = "C";

					// buscar primero los saltos y si hay que los borre, que primero busque para que
					// no de error de eliminación si no hay
					saltosList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),
							idturno, idguardia, idpersona,saltos);

					if (saltosList.size() > 0) {
						// query de eliminar saltos
						
						saltosN = inscripcionGuardiaExtensdsMapper.getEliminarSaltoCompensancion(
								idInstitucion.toString(), idturno, idguardia, idpersona,saltos);
					}
					// lo mismo para las compensaciones
					compensacionesList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(
							idInstitucion.toString(), idturno, idguardia, idpersona, compensaciones);
					if (compensacionesList.size() > 0) {
						// query de eliminar compensaciones
						compensacionesN = inscripcionGuardiaExtensdsMapper.getEliminarSaltoCompensancion(
								idInstitucion.toString(), idturno, idguardia, idpersona, compensaciones);
					}

					if (saltosN != 0 || compensacionesN != 0) {
						contadorKO++;
					}
						
				}

				LOGGER.info(
						"eliminarSaltosCompensaciones() -> Salida ya con los datos de saltos y compensaciones borrados");
			}
		}

		if (contadorKO != 0) {
			drp.setStatus(SigaConstants.KO);
			throw (new Exception());
		}
			
		else {
			drp.setStatus(SigaConstants.OK);
		}
			

		return drp;

	}

	@Override
	public InsertResponseDTO insertarInscripciones(BusquedaInscripcionItem inscripcion, HttpServletRequest request) {
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
				LOGGER.info(
						"insertarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"insertarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					// Comprobamos si existe la inscripcion en BBDD

					List<InscripcionGuardiaItem> inscripcionSearch = inscripcionGuardiaExtensdsMapper
							.buscarInscripcion(idInstitucion, inscripcion, usuarios.get(0));

					if (inscripcionSearch != null && inscripcionSearch.isEmpty()) {
						// Si no existe la insertamos
						int insertado = inscripcionGuardiaExtensdsMapper.insertarInscripcion(idInstitucion, inscripcion,
								usuarios.get(0));

						if (insertado == 1) {
							response.setStatus(SigaConstants.OK);
						} else {
							response.setStatus(SigaConstants.KO);
							LOGGER.error("InscripcionServiceImpl.insertarInscripciones() -> " + errorStr);

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
	public HistoricoInscripcionDTO historicoInscripcion(BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		LOGGER.info("historicoInscripcion() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		HistoricoInscripcionDTO inscripcionHistoricaDTO = new HistoricoInscripcionDTO();
		List<HistoricoInscripcionItem> inscripcionHistoricaItems = new ArrayList<HistoricoInscripcionItem>();
		List<InscripcionGuardiaItem> inscripcionesGuardiaItems = null;
		int cont = 0;

		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					inscripcionesGuardiaItems = inscripcionGuardiaExtensdsMapper.buscarInscripcion(idInstitucion,
							inscripcion, usuarios.get(0));

					if (inscripcionesGuardiaItems != null) {

						HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
						HistoricoInscripcionItem solicitud1 = new HistoricoInscripcionItem();
						HistoricoInscripcionItem solicitud2 = new HistoricoInscripcionItem();
						HistoricoInscripcionItem solicitud3 = new HistoricoInscripcionItem();
						HistoricoInscripcionItem solicitud4 = new HistoricoInscripcionItem();

						if (inscripcionesGuardiaItems.get(0).getFechaSuscripcion() != null) {
							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechaSuscripcion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							solicitud.setFecha(fecha);
							solicitud.setAccion(SOLICITUD);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionessuscripcion());
							inscripcionHistoricaItems.add(solicitud);
							cont++;

						}

						if (inscripcionesGuardiaItems.get(0).getFechasolicitudbaja() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechasolicitudbaja()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							solicitud1.setFecha(fecha);
							solicitud1.setAccion(SOLICITUD_DE_BAJA);
							solicitud1.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesbaja());
							inscripcionHistoricaItems.add(solicitud1);
							cont++;

						}

						if (inscripcionesGuardiaItems.get(0).getFechavalidacion() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechavalidacion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							solicitud2.setFecha(fecha);
							solicitud2.setAccion(SOLICITUD_DE_ALTA);
							solicitud2.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesvalidacion());
							inscripcionHistoricaItems.add(solicitud2);
							cont++;

						}
						if (inscripcionesGuardiaItems.get(0).getFechadenegacion() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechadenegacion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							solicitud3.setFecha(fecha);
							solicitud3.setAccion(DENEGACION);
							solicitud3.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesdenegacion());
							inscripcionHistoricaItems.add(solicitud3);
							cont++;

						}

						if (inscripcionesGuardiaItems.get(0).getFechaBaja() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechaBaja()).getTime();

							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							solicitud4.setFecha(fecha);
							solicitud4.setAccion(VALIDACION_BAJA);
							solicitud4.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesvalbaja());
							inscripcionHistoricaItems.add(solicitud4);
							cont++;

						}

						if (cont == 0) {
							error.setCode(500);
							error.setDescription("Error al filtrar por fechas, no hay fecha asignable");
							inscripcionHistoricaDTO.setError(error);
						}

						Collections.sort(inscripcionHistoricaItems, new Comparator<HistoricoInscripcionItem>() {
							public int compare(HistoricoInscripcionItem m1, HistoricoInscripcionItem m2) {
								return m1.getFecha().compareTo(m2.getFecha());
							}
						});
						inscripcionHistoricaDTO.setInscripcionesTarjetaItems(inscripcionHistoricaItems);
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

			inscripcionHistoricaDTO.setError(error);
		}
		LOGGER.info("historicoInscripcion() -> Salida para insertar inscripciones");
		return inscripcionHistoricaDTO;
	}

	@Override
    public InscripcionesDisponiblesDTO inscripcionesDisponibles(BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		LOGGER.info("inscripcionesDisponibles() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InscripcionesDisponiblesDTO inscripciones = new InscripcionesDisponiblesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
//					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtensdsMapper
//							.inscripcionesDisponibles(idInstitucion, usuarios.get(0), inscripcion);
					
					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtensdsMapper.busquedaTarjetaInscripcionesGuardia(idInstitucion, usuarios.get(0), inscripcion);
					
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
    public InscripcionesDisponiblesDTO inscripcionesDisponiblesGuardia(BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		LOGGER.info("inscripcionesDisponibles() -> Entrada para insertar inscripciones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InscripcionesDisponiblesDTO inscripciones = new InscripcionesDisponiblesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"inscripcionesDisponibles() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
//					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtensdsMapper
//							.inscripcionesDisponibles(idInstitucion, usuarios.get(0), inscripcion);
					
					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtensdsMapper.busquedaTarjetaInscripcionesTurnosConGuardia(idInstitucion, usuarios.get(0), inscripcion);
					
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
	public InscripcionesDisponiblesDTO inscripcionPorguardia(BusquedaInscripcionItem inscripcion,
			HttpServletRequest request) {
		LOGGER.info("inscripcionPorguardia() -> Entrada para buscar inscripciones por guardia");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDisponiblesDTO inscripciones = new InscripcionesDisponiblesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.info(
						"inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<GestionInscripcion> inscripcionPorguardia = inscripcionGuardiaExtensdsMapper
							.inscripcionPorguardia(idInstitucion, usuarios.get(0), inscripcion.getIdguardia(),
									inscripcion.getIdpersona());
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
				LOGGER.info(
						"inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"inscripcionPorguardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				if (usuarios != null && !usuarios.isEmpty()) {
					List<LetradoGuardiaItem> searchLetradosInscripcion = scsGuardiasturnoExtendsMapper
							.searchLetradosInscripcion(Short.toString(idInstitucion), idGuardia);
					for (LetradoGuardiaItem letradoGuardiaItem : searchLetradosInscripcion) {
						ComboItem comboItem = new ComboItem();
						comboItem.setLabel(
								letradoGuardiaItem.getNumeroColegiado() + "/" + letradoGuardiaItem.getNumeroGrupo());
						comboItem.setValue(letradoGuardiaItem.getNumeroGrupo());
						lista.add(comboItem);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"InscripcionServiceImpl.inscripcionPorguardia() -> Se ha producido un error en la busqueda de inscripciones por guardia.",
					e);

			error.setCode(500);
			error.setDescription("Error en la busqueda de inscripciones por guardia.");
			error.setMessage(e.getMessage());

			combo.setError(error);
		}

		combo.setCombooItems(lista);
		return combo;
	}

	@Override
	public UpdateResponseDTO updateInscripcion(BusquedaInscripcionMod updateInscripcion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int inscripciones = 0;
		int contadorKO = 0;
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("updateInscripcion() -> Entrada para modificar los datos correspondientes");

				int usuario = usuarios.get(0).getIdusuario();

				ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(updateInscripcion.getIdpersona()))
						.andIdturnoEqualTo(Integer.valueOf(updateInscripcion.getIdturno())).andIdguardiaEqualTo(Integer.valueOf(updateInscripcion.getIdguardia()));
							
				List<ScsInscripcionguardia> listaInscripciones = inscripcionGuardiaExtensdsMapper.selectByExample(example);

				ScsInscripcionguardia record = listaInscripciones.get(0);
				
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario);
				
				if (updateInscripcion.getFechabaja() != null) {
					record.setFechabaja(updateInscripcion.getFechabaja());
				}
				
				if (updateInscripcion.getFechavalidacionNUEVA() != null ) {
					record.setFechavalidacion(updateInscripcion.getFechavalidacionNUEVA());
				}
				
				inscripciones = inscripcionGuardiaExtensdsMapper.updateByPrimaryKeySelective(record );

				if (inscripciones == 0)
					contadorKO++;

				LOGGER.info("updateInscripcion() -> Salida ya con los datos modificados");
			}
		}

		if (contadorKO != 0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);

		return upd;
	}

	@Override
	@Transactional
	public InsertResponseDTO insertSolicitarAlta(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		LOGGER.info("insertSolicitarAlta() ->  Entrada al servicio para guardar nuevas inscripciones");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
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
					"insertSolicitarAlta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertSolicitarAlta() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {

						// Buscamos el turno asociado para determinar si ese turno requiere validacion
						// del colegio para las inscripciones o no
						ScsTurnoKey turnoKey = new ScsTurnoKey();
						turnoKey.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
						turnoKey.setIdinstitucion(idInstitucion);
						ScsTurno turnoItem = scsTurnoMapper.selectByPrimaryKey(turnoKey);
						String valid = turnoItem.getValidarinscripciones();

						// Buscamos las inscripciones ya existentes
						ScsInscripcionturnoExample scsInscripcionturnoExample = new ScsInscripcionturnoExample();
						scsInscripcionturnoExample.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

						List<ScsInscripcionturno> oldListInscripcionturno = scsInscripcionturnoMapper
								.selectByExample(scsInscripcionturnoExample);

						// Si hay inscripciones ya existentes las eliminamos
						if (!oldListInscripcionturno.isEmpty()) {

							for (ScsInscripcionturno scsInscripcionturno : oldListInscripcionturno) {

								ScsInscripcionturnoKey scsInscripcionturnoKey = new ScsInscripcionturnoKey();
								scsInscripcionturnoKey.setIdturno(scsInscripcionturno.getIdturno());
								scsInscripcionturnoKey.setIdinstitucion(scsInscripcionturno.getIdinstitucion());
								scsInscripcionturnoKey.setIdpersona(scsInscripcionturno.getIdpersona());
								scsInscripcionturnoKey.setFechasolicitud(scsInscripcionturno.getFechasolicitud());

								scsInscripcionturnoMapper.deleteByPrimaryKey(scsInscripcionturnoKey);

							}

						}

						// Procedemos a insertar la nueva inscripción
						ScsInscripcionturno inscripcionturno = new ScsInscripcionturno();
						inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservacionessolicitud());
						inscripcionturno.setFechasolicitud(inscripcionesItem.getFechasolicitud());

						if (inscripcionesItem.getEstadonombre()!= null &&
								(inscripcionesItem.getEstadonombre().equals("NoPermisos")
								|| inscripcionesItem.getEstadonombre().equals("PendienteDeValidar"))) {
							inscripcionturno.setFechavalidacion(null);
						} else {
							if (valid == "N")
								inscripcionturno.setFechavalidacion(new Date());
						}

						inscripcionturno.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
						inscripcionturno.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
						inscripcionturno.setIdinstitucion(idInstitucion);
						inscripcionturno.setFechamodificacion(new Date());
						inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());

						int respTurno = scsInscripcionturnoExtendsMapper.insert(inscripcionturno);
						
						response = respTurno;

						// Creamos inscripcion a turno
						
						if(respTurno != 0 && inscripcionesItem.getIdguardia() != null && !inscripcionesItem.getIdguardia().isEmpty()) {
							// ScsInscripcionguardiaExample exampleguardia = new
							// ScsInscripcionguardiaExample();
							// exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							// .andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()));

							ScsInscripcionguardiaKey keyinscripcionguardia = new ScsInscripcionguardiaKey();

							keyinscripcionguardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
							keyinscripcionguardia.setIdinstitucion(idInstitucion);
							keyinscripcionguardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
							keyinscripcionguardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
							keyinscripcionguardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));

							ScsInscripcionguardia oldInscripcionguardia = scsInscripcionguardiaMapper
									.selectByPrimaryKey(keyinscripcionguardia);

							if (oldInscripcionguardia != null) {
								scsInscripcionguardiaMapper.deleteByPrimaryKey(keyinscripcionguardia);
							}

							ScsInscripcionguardia guardia = new ScsInscripcionguardia();

							guardia.setObservacionessuscripcion(inscripcionesItem.getObservacionessolicitud());
							if (inscripcionesItem.getEstadonombre()!= null
									&& (inscripcionesItem.getEstadonombre().equals("NoPermisos")
									|| inscripcionesItem.getEstadonombre().equals("PendienteDeValidar"))) {
								guardia.setFechavalidacion(null);
							} else {
								if (valid == "N")
									guardia.setFechavalidacion(new Date());
							}
							guardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
							guardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
							guardia.setIdinstitucion(idInstitucion);
							guardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));
							guardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
							guardia.setFechamodificacion(new Date());
							guardia.setUsumodificacion(usuarios.get(0).getIdusuario());

							response = scsInscripcionguardiaMapper.insert(guardia);
						}
					
						}
						

					LOGGER.info("insertSolicitarAlta() / -> Salida del servicio para guardar nuevas inscripciones");

				}

				catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la partida presupuestaria");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificado la inscripción excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la inscripción correctamente");
				}

				insertResponseDTO.setError(error);

				LOGGER.info("insertSolicitarAlta() -> Salida del servicio para guardar una nueva inscripcion");

			}

		}

		return insertResponseDTO;
	}
}
