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
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionturnoKey;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsTurnoKey;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

	private final String VALIDACION_ALTA = "Validacion Alta";
	private final String VALIDACION_BAJA = "Validacion Baja";
	private final String DENEGACION = "Denegacion";
	private final String SOLICITUD_DE_ALTA = "Solicitud Alta";
	private final String SOLICITUD_DE_BAJA = "Solicitud Baja";
	private final String SOLICITUD = "Solicitud";

	private Logger LOGGER = Logger.getLogger(InscripcionServiceImpl.class);

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;

	@Autowired
	private ScsInscripcionguardiaExtendsMapper inscripcionGuardiaExtendsMapper;

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
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private ScsGuardiasturnoMapper scsGuardiasturnoMapper;

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
		boolean primera = true;

		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("validarInscripciones() -> Entrada para validar las inscripciones");
				
				// Contiene los turnos que han sido procesados
				List<String> listaTurnosProcesados = new ArrayList<String>();
				
				// Contiene las personas que han sido procesadas
				List<String> listaPersonasProcesadas = new ArrayList<String>();

				for (BusquedaInscripcionMod inscripcion : validarBody) {
					
					// Necesaria para controlar qué turnos se han procesado
					String idTurno = inscripcion.getIdturno();
					
					// Obtenemos el turno
					List<ScsTurno> listaturno = null;
					ScsTurnoExample exampleturno = new ScsTurnoExample();
					if(idTurno != null) {
					exampleturno.createCriteria().andIdturnoEqualTo(Integer.parseInt(idTurno))
							.andIdinstitucionEqualTo(idInstitucion);
					}
					listaturno = scsTurnosExtendsMapper.selectByExample(exampleturno);
					
					ScsTurno turno = listaturno.get(0);
					
					// Si el turno está configurado como "A elegir"
					if (turno != null && turno.getGuardias() == 2) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							//Obtenemos la inscripcion
							ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
							key.setFechasuscripcion(inscripcion.getFechasolicitud());
							key.setIdguardia(Integer.valueOf(inscripcion.getIdguardia()));
							key.setIdinstitucion(idInstitucion);
							key.setIdpersona(Long.valueOf(inscripcion.getIdpersona()));
							key.setIdturno(Integer.valueOf(inscripcion.getIdturno()));
							
							ScsInscripcionguardia objeto = inscripcionGuardiaExtendsMapper.selectByPrimaryKey(key);
							
							ScsInscripcionturnoKey keyInscripcion = new ScsInscripcionturnoKey();

							
					        keyInscripcion.setFechasolicitud(inscripcion.getFechasolicitud());
					        
					        InscripcionesItem inscripcionesItem = new InscripcionesItem();
					        inscripcionesItem.setIdturno(inscripcion.getIdturno());
					        inscripcionesItem.setIdinstitucion(String.valueOf(idInstitucion));
					        inscripcionesItem.setIdpersona(inscripcion.getIdpersona());
							List<InscripcionesItem> inscripcionTurno = scsInscripcionturnoExtendsMapper.validarExisteTurnoDeAlta(inscripcionesItem);
							// Para validar un alta, es necesario que haya un turno dado de alta (existe fechaValidacion y no tiene fechaBaja)
							if(inscripcionTurno.isEmpty() && inscripcion.getEstado().equals("0")) {
								upd.setStatus(SigaConstants.KO);
								throw (new Exception("Error al Validar la inscripcion, no hay turnos de alta"));
							}
							if(objeto != null) {
								/*if(inscripcion.getFechasolicitudNUEVA() != null) {
									objeto.setFechasuscripcion(inscripcion.getFechasolicitudNUEVA());
								}else {
									if(inscripcion.getFechasolicitud() != null) {
										objeto.setFechasuscripcion(inscripcion.getFechasolicitud());
									}
								}*/
								
								objeto.setFechamodificacion(new Date());
								objeto.setUsumodificacion(usuarios.get(0).getIdusuario());

								
								if(inscripcion.getFechabaja() != null) {
									objeto.setFechabaja(inscripcion.getFechabaja());
								}
								
								/*if(inscripcion.getObservacionessolicitudNUEVA() != null) {
									objeto.setObservacionessuscripcion(inscripcion.getObservacionessolicitudNUEVA());
								}*/
								
								if(inscripcion.getObservacionesbaja() != null) {
									objeto.setObservacionesbaja(inscripcion.getObservacionesbaja());
								}
								
								if(inscripcion.getFechasolicitudbajaNUEVA() != null) {
									objeto.setFechasolicitudbaja(inscripcion.getFechasolicitudbajaNUEVA());
								}
								
								if(inscripcion.getObservacionesvalbajaNUEVA() != null) {
									objeto.setObservacionesvalbaja(inscripcion.getObservacionesvalbajaNUEVA());
								}
								
								if(inscripcion.getFechavalidacionNUEVA() != null) {
									objeto.setFechavalidacion(inscripcion.getFechavalidacionNUEVA());
								}
								
								if(inscripcion.getObservacionesvalidacionNUEVA() != null) {
									objeto.setObservacionesvalidacion(inscripcion.getObservacionesvalidacionNUEVA());
								}
								
								if(inscripcion.getFechadenegacionNUEVA() != null) {
									objeto.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
								}
								
								if(inscripcion.getObservacionesdenegacionNUEVA() != null) {
									objeto.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
								}
								
								contadorKO = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(objeto);
								
								// Añadimos el turno a la lista de turnos procesados
								listaTurnosProcesados.add(idTurno);
								
								// Añadimos la persona a la lista de personas procesadas
								listaPersonasProcesadas.add(inscripcion.getIdpersona());
							}
						}
					// Comprobamos si la inscripción ya ha sido procesada y el turno no está configurado como "A elegir"
					} else if (turno != null && turno.getGuardias() != 2 && !listaTurnosProcesados.contains(idTurno)) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							// Procesa la inscripción del turno y sus guardas según configuración
							contadorKO = procesaTurnoValidacion(inscripcion, idInstitucion, usuarios, turno.getGuardias(), idTurno);
							
							// Añadimos el turno a la lista de turnos procesados
							listaTurnosProcesados.add(idTurno);
							
							// Añadimos la persona a la lista de personas procesadas
							listaPersonasProcesadas.add(inscripcion.getIdpersona());
						}
					// Procesa inscripciones de guardias de un turno que no está configurado como "A elegir" y que ya ha sido procesado
					} else if (turno != null && turno.getGuardias() != 2 && !listaPersonasProcesadas.contains(inscripcion.getIdpersona())) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							// Procesa las inscripciones de guardias del turno según configuración
							contadorKO = procesaGuardiasSinTurnoValidacion(inscripcion, idInstitucion, usuarios, turno.getGuardias(), idTurno);
							
							// Añadimos la persona a la lista de personas procesadas
							listaPersonasProcesadas.add(inscripcion.getIdpersona());
						}
					}
				}
				
				LOGGER.info(
						"validarInscripciones() -> Salida ya con los datos actualizados y la inscripción validada");
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

	private int procesaGuardiasSinTurnoValidacion(BusquedaInscripcionMod inscripcion, Short idInstitucion, List<AdmUsuarios> usuarios,
			Short confGuardia, String idTurno) {
		
		LOGGER.info("procesaGuardiasSinTurnoValidacion() -> Entrada para procesar la validación de las inscripciones de guardia sin actualizar el turno");
		
		int contadorKO = 0;
			
		List<ScsInscripcionguardia> inscripcionesGuardia = null;
		
		ScsInscripcionguardiaExample exampleInscripcionGuardias = new ScsInscripcionguardiaExample();
		exampleInscripcionGuardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andIdturnoEqualTo(Integer.parseInt(idTurno))
				.andIdpersonaEqualTo(Long.parseLong(inscripcion.getIdpersona()));
		inscripcionesGuardia = scsInscripcionguardiaMapper.selectByExample(exampleInscripcionGuardias);
		
		for (ScsInscripcionguardia inscripcionGuardia : inscripcionesGuardia) {
			contadorKO = procesaGuardiasTurnoValidacion(inscripcion, inscripcionGuardia, idInstitucion, usuarios);
		}
		
		LOGGER.info("procesaGuardiasSinTurnoValidacion() -> Salida para procesar la validación de las inscripciones de guardia sin actualizar el turno");
		
		return contadorKO;
	}

	private int procesaTurnoValidacion(BusquedaInscripcionMod inscripcion, Short idInstitucion, List<AdmUsuarios> usuarios,
			Short confGuardia, String idTurno) {
		
		LOGGER.info("procesaTurnoValidacion() -> Entrada para procesar la validación de inscripciones del turno y sus guardias");
		
		int contadorKO = 0;
		
		 InscripcionesItem inscripcionesItem = new InscripcionesItem();
        inscripcionesItem.setIdturno(inscripcion.getIdturno());
        inscripcionesItem.setIdinstitucion(String.valueOf(idInstitucion));
        inscripcionesItem.setIdpersona(inscripcion.getIdpersona());
		List<InscripcionesItem> inscripcionTurno = scsInscripcionturnoExtendsMapper.validarExisteTurnoDeAlta(inscripcionesItem);
		// Para validar un alta, es necesario que haya un turno dado de alta (existe fechaValidacion y no tiene fechaBaja)
		if(inscripcionTurno.isEmpty() && inscripcion.getEstado().equals("0")) {
			return contadorKO;
		}
	
		List<ScsInscripcionguardia> inscripcionesGuardia = null;
		
		ScsInscripcionguardiaExample exampleInscripcionGuardias = new ScsInscripcionguardiaExample();
		exampleInscripcionGuardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andIdturnoEqualTo(Integer.parseInt(idTurno))
				.andIdpersonaEqualTo(Long.parseLong(inscripcion.getIdpersona()));
		inscripcionesGuardia = scsInscripcionguardiaMapper.selectByExample(exampleInscripcionGuardias);
		
		for (ScsInscripcionguardia inscripcionGuardia : inscripcionesGuardia) {
			contadorKO = procesaGuardiasTurnoValidacion(inscripcion, inscripcionGuardia, idInstitucion, usuarios);
		}
			
		LOGGER.info("procesaTurnoValidacion() -> Salida para procesar la validación de inscripciones del turno y sus guardias");
		
		return contadorKO;
	}

	private int procesaGuardiasTurnoValidacion(BusquedaInscripcionMod inscripcion, ScsInscripcionguardia objeto, Short idInstitucion, List<AdmUsuarios> usuarios) {
		int contadorKO = 0;
		
		if(objeto != null) {
			/*if(inscripcion.getFechasolicitudNUEVA() != null) {
				objeto.setFechasuscripcion(inscripcion.getFechasolicitudNUEVA());
			}else {
				if(inscripcion.getFechasolicitud() != null) {
					objeto.setFechasuscripcion(inscripcion.getFechasolicitud());
				}
			}*/
			
			objeto.setFechamodificacion(new Date());
			objeto.setUsumodificacion(usuarios.get(0).getIdusuario());
			
			if(inscripcion.getFechabaja() != null) {
				objeto.setFechabaja(inscripcion.getFechabaja());
			}
			
			/*if(inscripcion.getObservacionessolicitudNUEVA() != null) {
				objeto.setObservacionessuscripcion(inscripcion.getObservacionessolicitudNUEVA());
			}*/
			
			if(inscripcion.getObservacionesbaja() != null) {
				objeto.setObservacionesbaja(inscripcion.getObservacionesbaja());
			}
			
			if(inscripcion.getFechasolicitudbajaNUEVA() != null) {
				objeto.setFechasolicitudbaja(inscripcion.getFechasolicitudbajaNUEVA());
			}
			
			if(inscripcion.getObservacionesvalbajaNUEVA() != null) {
				objeto.setObservacionesvalbaja(inscripcion.getObservacionesvalbajaNUEVA());
			}
			
			if(inscripcion.getFechavalidacionNUEVA() != null) {
				objeto.setFechavalidacion(inscripcion.getFechavalidacionNUEVA());
			}
			
			if(inscripcion.getObservacionesvalidacionNUEVA() != null) {
				objeto.setObservacionesvalidacion(inscripcion.getObservacionesvalidacionNUEVA());
			}
			
			if(inscripcion.getFechadenegacionNUEVA() != null) {
				objeto.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
			}
			
			if(inscripcion.getObservacionesdenegacionNUEVA() != null) {
				objeto.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
			}
			
			contadorKO = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(objeto);
		}
		
		return contadorKO;
		
	}
	
	private int procesaGuardiasSinTurnoDenegacion(BusquedaInscripcionMod inscripcion, Short idInstitucion, List<AdmUsuarios> usuarios,
			Short confGuardia, String idTurno) {
		
		LOGGER.info("procesaGuardiasSinTurnoDenegacion() -> Entrada para procesar la denegación de inscripciones de guardia sin actualizar el turno");
		
		int contadorKO = 0;
			
		List<ScsInscripcionguardia> inscripcionesGuardia = null;
		
		ScsInscripcionguardiaExample exampleInscripcionGuardias = new ScsInscripcionguardiaExample();
		exampleInscripcionGuardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andIdturnoEqualTo(Integer.parseInt(idTurno))
				.andIdpersonaEqualTo(Long.parseLong(inscripcion.getIdpersona()));
		inscripcionesGuardia = scsInscripcionguardiaMapper.selectByExample(exampleInscripcionGuardias);
		
		for (ScsInscripcionguardia inscripcionGuardia : inscripcionesGuardia) {
			contadorKO = procesaGuardiasTurnoDenegacion(inscripcion, inscripcionGuardia, idInstitucion, usuarios);
		}
		
		LOGGER.info("procesaGuardiasSinTurnoDenegacion() -> Salida para procesar la denegación de inscripciones de guardia sin actualizar el turno");
		
		return contadorKO;
	}

	private int procesaTurnoDenegacion(BusquedaInscripcionMod inscripcion, Short idInstitucion, List<AdmUsuarios> usuarios,
			Short confGuardia, String idTurno) {
		
		LOGGER.info("procesaTurnoDenegacion() -> Entrada para procesar la denegación de inscripciones del turno y sus guardias");
		
		int contadorKO = 0;
		
		ScsInscripcionturnoKey keyInscripcion = new ScsInscripcionturnoKey();

		keyInscripcion.setIdturno(Integer.valueOf(inscripcion.getIdturno()));
		keyInscripcion.setIdinstitucion(idInstitucion);
        keyInscripcion.setIdpersona(Long.parseLong(inscripcion.getIdpersona()));
        keyInscripcion.setFechasolicitud(inscripcion.getFechasolicitud());
        
        ScsInscripcionturno inscripcionTurno = scsInscripcionturnoMapper.selectByPrimaryKey(keyInscripcion);
			
        if (inscripcionTurno != null) {
        	inscripcionTurno.setFechamodificacion(new Date());
    		inscripcionTurno.setUsumodificacion(usuarios.get(0).getIdusuario());
    				
    		if (inscripcion.getFechadenegacionNUEVA() != null) {
    			inscripcionTurno.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
    			if(inscripcion.getObservacionesdenegacionNUEVA()!= null) {
    				inscripcionTurno.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
    			}
    		}
    		
    		contadorKO = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionTurno);
        }		
		
		if (contadorKO != 0) {
		
			List<ScsInscripcionguardia> inscripcionesGuardia = null;
			
			ScsInscripcionguardiaExample exampleInscripcionGuardias = new ScsInscripcionguardiaExample();
			exampleInscripcionGuardias.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(Integer.parseInt(idTurno))
					.andIdpersonaEqualTo(Long.parseLong(inscripcion.getIdpersona()));
			inscripcionesGuardia = scsInscripcionguardiaMapper.selectByExample(exampleInscripcionGuardias);
		
			for (ScsInscripcionguardia inscripcionGuardia : inscripcionesGuardia) {
				contadorKO = procesaGuardiasTurnoDenegacion(inscripcion, inscripcionGuardia, idInstitucion, usuarios);
			}
		}
		
		LOGGER.info("procesaTurnoDenegacion() -> Salida para procesar la denegación de inscripciones del turno y sus guardias");
		
		return contadorKO;
	}

	private int procesaGuardiasTurnoDenegacion(BusquedaInscripcionMod inscripcion, ScsInscripcionguardia record, Short idInstitucion, List<AdmUsuarios> usuarios) {
		int contadorKO = 0;
		int usuario = usuarios.get(0).getIdusuario();
		
		if(record!= null) {
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario);
			
			if (inscripcion.getFechadenegacionNUEVA() != null) {
				record.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
				if(inscripcion.getObservacionesdenegacionNUEVA()!= null) {
					record.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
				}
			}
			
			contadorKO = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(record);
		}
		
		return contadorKO;
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
					
					ScsInscripcionguardia record = inscripcionGuardiaExtendsMapper.selectByPrimaryKey(key);
					
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
						
						inscripciones = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(record);
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
		
		int contadorKO = 0;
		boolean primera = true;
		
		UpdateResponseDTO upd = new UpdateResponseDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cambiarFechaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("denegarInscripcion() -> Entrada para modificar los datos correspondientes");
				
				// Contiene los turnos que han sido procesados
				List<String> listaTurnosProcesados = new ArrayList<String>();
				
				// Contiene las personas que han sido procesadas
				List<String> listaPersonasProcesadas = new ArrayList<String>();

				for (BusquedaInscripcionMod inscripcion : cambiarfechabody) {
					
					// Necesaria para controlar qué turnos se han procesado
					String idTurno = inscripcion.getIdturno();
					
					// Obtenemos el turno
					List<ScsTurno> listaturno = null;
					ScsTurnoExample exampleturno = new ScsTurnoExample();
					if(idTurno != null) {
					exampleturno.createCriteria().andIdturnoEqualTo(Integer.parseInt(idTurno))
							.andIdinstitucionEqualTo(idInstitucion);
					}
					listaturno = scsTurnosExtendsMapper.selectByExample(exampleturno);
					
					ScsTurno turno = listaturno.get(0);
					
					int usuario = usuarios.get(0).getIdusuario();

					// Si el turno está configurado como "A elegir"
					if (turno != null && turno.getGuardias() == 2) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							//Obtenemos la inscripcion
							ScsInscripcionguardiaKey key = new ScsInscripcionguardiaKey();
							key.setFechasuscripcion(inscripcion.getFechasolicitud());
							key.setIdguardia(Integer.valueOf(inscripcion.getIdguardia()));
							key.setIdinstitucion(idInstitucion);
							key.setIdpersona(Long.valueOf(inscripcion.getIdpersona()));
							key.setIdturno(Integer.valueOf(inscripcion.getIdturno()));
							
							ScsInscripcionguardia record = inscripcionGuardiaExtendsMapper.selectByPrimaryKey(key);
		                    							
							if(record!= null) {
								record.setFechamodificacion(new Date());
								record.setUsumodificacion(usuario);
								
								if (inscripcion.getFechadenegacionNUEVA() != null) {
									record.setFechadenegacion(inscripcion.getFechadenegacionNUEVA());
									if(inscripcion.getObservacionesdenegacionNUEVA()!= null) {
										record.setObservacionesdenegacion(inscripcion.getObservacionesdenegacionNUEVA());
									}
								}
								
								contadorKO = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(record);
								
								// Añadimos el turno a la lista de turnos procesados
								listaTurnosProcesados.add(idTurno);
								
								// Añadimos la persona a la lista de personas procesadas
								listaPersonasProcesadas.add(inscripcion.getIdpersona());
							}
						}
					// Comprobamos si la inscripción ya ha sido procesada y el turno no está configurado como "A elegir"
					} else if (turno != null && turno.getGuardias() != 2 && !listaTurnosProcesados.contains(idTurno)) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							// Procesa la inscripción del turno y sus guardas según configuración
							contadorKO = procesaTurnoDenegacion(inscripcion, idInstitucion, usuarios, turno.getGuardias(), idTurno);
							
							// Añadimos el turno a la lista de turnos procesados
							listaTurnosProcesados.add(idTurno);
							
							// Añadimos la persona a la lista de personas procesadas
							listaPersonasProcesadas.add(inscripcion.getIdpersona());
						}
					// Procesa inscripciones de guardias de un turno que no está configurado como "A elegir" y que ya ha sido procesado
					} else if (turno != null && turno.getGuardias() != 2 && !listaPersonasProcesadas.contains(inscripcion.getIdpersona())) {
						if (contadorKO != 0 || primera) {
							primera = false;
							
							// Procesa las inscripciones de guardias del turno según configuración
							contadorKO = procesaGuardiasSinTurnoDenegacion(inscripcion, idInstitucion, usuarios, turno.getGuardias(), idTurno);
							
							// Añadimos la persona a la lista de personas procesadas
							listaPersonasProcesadas.add(inscripcion.getIdpersona());
						}
					}
	
					LOGGER.info("denegarInscripcion() -> Salida ya con los datos modificados");
				}
			}
		}

		if (contadorKO != 0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);

		return upd;
	}

	@Override
	public UpdateResponseDTO cambiarFechaInscripcion(List<BusquedaInscripcionMod> cambiarfechabody, HttpServletRequest request) {
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
					
					ScsInscripcionguardia record = inscripcionGuardiaExtendsMapper.selectByPrimaryKey(key);
					
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
						
						inscripciones = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(record);
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
					saltosList = inscripcionGuardiaExtendsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),
							idturno, idguardia, idpersona,saltos);

					
					// lo mismo para las compensaciones
					compensacionesList = inscripcionGuardiaExtendsMapper.getBuscarSaltoCompensancion(
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

					trabajosSJCSListGuardias= inscripcionGuardiaExtendsMapper.busquedaTrabajosGuardias(idpersona,idturno, idguardia ,idInstitucion, fechaActual);
					trabajosSJCSListPendientes = inscripcionGuardiaExtendsMapper.busquedaTrabajosPendientes(idpersona, idturno, idInstitucion, fechaActual);
					
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

					listaGuardias = inscripcionGuardiaExtendsMapper
							.getbuscarGuardiasAsocTurnos(idInstitucion.toString(), idturno, idguardia, idpersona);

					// modificar el turno según si es requerida o no
					String validarinscripciones = inscripcionGuardiaExtendsMapper
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
						OKUpdateTurno = inscripcionGuardiaExtendsMapper
								.UpdateInscripcionTurno(idInstitucion.toString(), idturno, a, FECHABAJA);
					}

					if (listaGuardias.size() > 0) {
						for (GuardiasTurnosItem b : listaGuardias) {
							// buscar si es Requerida o no
							requeridaValidacion = inscripcionGuardiaExtendsMapper
									.requeridaValidacionCampo(idInstitucion.toString(), idturno, idguardia, idpersona);
							if(requeridaValidacion != null) {
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
							}							

							if (a.getFechabaja() != null) {
								FECHABAJA = formatter.format(a.getFechabaja());
							}

							// actualizar scs_inscripcionguardia
							if (a.getFechabaja() != null) {
								OKUpdateGuardia = inscripcionGuardiaExtendsMapper.UpdateInscripcionGuardia(
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
					saltosList = inscripcionGuardiaExtendsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),
							idturno, idguardia, idpersona,saltos);

					if (saltosList.size() > 0) {
						// query de eliminar saltos
						
						saltosN = inscripcionGuardiaExtendsMapper.getEliminarSaltoCompensancion(
								idInstitucion.toString(), idturno, idguardia, idpersona,saltos);
					}
					// lo mismo para las compensaciones
					compensacionesList = inscripcionGuardiaExtendsMapper.getBuscarSaltoCompensancion(
							idInstitucion.toString(), idturno, idguardia, idpersona, compensaciones);
					if (compensacionesList.size() > 0) {
						// query de eliminar compensaciones
						compensacionesN = inscripcionGuardiaExtendsMapper.getEliminarSaltoCompensancion(
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

					List<InscripcionGuardiaItem> inscripcionSearch = inscripcionGuardiaExtendsMapper
							.buscarInscripcion(idInstitucion, inscripcion, usuarios.get(0));

					if (inscripcionSearch != null && inscripcionSearch.isEmpty()) {
						// Si no existe la insertamos
						int insertado = inscripcionGuardiaExtendsMapper.insertarInscripcion(idInstitucion, inscripcion,
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
	public HistoricoInscripcionDTO historicoInscripcion(BusquedaInscripcionItem inscripcion, HttpServletRequest request) {
		
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
				LOGGER.info("historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("historicoInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					inscripcionesGuardiaItems = inscripcionGuardiaExtendsMapper.buscarInscripcion(idInstitucion, inscripcion, usuarios.get(0));

					if (inscripcionesGuardiaItems != null) {

						if (inscripcionesGuardiaItems.get(0).getFechaSuscripcion() != null) {
							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechaSuscripcion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());
							
							HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
							solicitud.setFecha(fecha);
							solicitud.setAccion(SOLICITUD_DE_ALTA);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionessuscripcion());
							inscripcionHistoricaItems.add(solicitud);
							cont++;
						}
						
						if (inscripcionesGuardiaItems.get(0).getFechavalidacion() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechavalidacion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
							solicitud.setFecha(fecha);
							solicitud.setAccion(VALIDACION_ALTA);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesvalidacion());
							inscripcionHistoricaItems.add(solicitud);
							cont++;
						}

						if (inscripcionesGuardiaItems.get(0).getFechasolicitudbaja() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechasolicitudbaja()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
							solicitud.setFecha(fecha);
							solicitud.setAccion(SOLICITUD_DE_BAJA);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesbaja());
							inscripcionHistoricaItems.add(solicitud);
							cont++;
						}


						if (inscripcionesGuardiaItems.get(0).getFechabaja() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechabaja()).getTime();

							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
							solicitud.setFecha(fecha);
							solicitud.setAccion(VALIDACION_BAJA);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesvalbaja());
							inscripcionHistoricaItems.add(solicitud);
							cont++;
						}
						
						if (inscripcionesGuardiaItems.get(0).getFechadenegacion() != null) {

							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							long ts = (inscripcionesGuardiaItems.get(0).getFechadenegacion()).getTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(ts);
							String fecha = formatter.format(calendar.getTime());

							HistoricoInscripcionItem solicitud = new HistoricoInscripcionItem();
							solicitud.setFecha(fecha);
							solicitud.setAccion(DENEGACION);
							solicitud.setObservacion(inscripcionesGuardiaItems.get(0).getObservacionesdenegacion());
							inscripcionHistoricaItems.add(solicitud);
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
			LOGGER.error("InscripcionServiceImpl.historicoInscripcion() -> Se ha producido un error en la busqueda de inscripciones.", e);
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
					
					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtendsMapper.busquedaTarjetaInscripcionesGuardia(idInstitucion, usuarios.get(0), inscripcion);
					
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
					
					List<GestionInscripcion> inscripcionesDisponibles = inscripcionGuardiaExtendsMapper.busquedaTarjetaInscripcionesTurnosConGuardia(idInstitucion, usuarios.get(0), inscripcion);
					
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
	public InscripcionesDisponiblesDTO inscripcionPorguardia(BusquedaInscripcionItem inscripcion, HttpServletRequest request) {
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
					List<GestionInscripcion> inscripcionPorguardia = inscripcionGuardiaExtendsMapper
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
			LOGGER.error("InscripcionServiceImpl.inscripcionPorguardia() -> Se ha producido un error en la busqueda de inscripciones por guardia.", e);
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

			LOGGER.info("updateInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("updateInscripcion() -> Entrada para modificar los datos correspondientes");

				int usuario = usuarios.get(0).getIdusuario();

				ScsInscripcionguardiaExample example = new ScsInscripcionguardiaExample();
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(updateInscripcion.getIdpersona())).andIdturnoEqualTo(Integer.valueOf(updateInscripcion.getIdturno())).andIdguardiaEqualTo(Integer.valueOf(updateInscripcion.getIdguardia()));
							
				List<ScsInscripcionguardia> listaInscripciones = inscripcionGuardiaExtendsMapper.selectByExample(example);

				ScsInscripcionguardia record = listaInscripciones.get(0);
				
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario);
				
				if (updateInscripcion.getFechabaja() != null) {
					record.setFechabaja(updateInscripcion.getFechabaja());
				}
				
				if (updateInscripcion.getFechavalidacionNUEVA() != null ) {
					record.setFechavalidacion(updateInscripcion.getFechavalidacionNUEVA());
				}
				
				inscripciones = inscripcionGuardiaExtendsMapper.updateByPrimaryKeySelective(record );

				if (inscripciones == 0)
					contadorKO++;

				LOGGER.info("updateInscripcion() -> Salida ya con los datos modificados");
			}
		}

		if (contadorKO != 0) {
			upd.setStatus(SigaConstants.OK);
		} else {
			upd.setStatus(SigaConstants.KO);
		}

		return upd;
	}

	@Override
	@Transactional
	public InsertResponseDTO insertSolicitarAlta(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		LOGGER.info("insertSolicitarAlta() ->  Entrada al servicio para guardar nuevas inscripciones");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		int existentes = 0;
		int errores = 0;
		int creados = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("insertSolicitarAlta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("insertSolicitarAlta() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
					
					try {

						// Buscamos el turno asociado para determinar si ese turno requiere validacion del colegio para las inscripciones o no
						//ScsTurnoKey turnoKey = new ScsTurnoKey();
						//turnoKey.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
						//turnoKey.setIdinstitucion(idInstitucion);
						//ScsTurno turnoItem = scsTurnoMapper.selectByPrimaryKey(turnoKey);
						//String valid = turnoItem.getValidarinscripciones();

						// Buscamos las inscripciones ya existentes en el turno
						ScsInscripcionturnoExample scsInscripcionturnoExample = new ScsInscripcionturnoExample();
						scsInscripcionturnoExample.createCriteria().andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()))
								.andFechabajaIsNull().andFechadenegacionIsNull().andFechasolicitudbajaIsNull().andFechavalidacionIsNotNull();

						List<ScsInscripcionturno> listInscripcionturno = scsInscripcionturnoMapper.selectByExample(scsInscripcionturnoExample);
						if (listInscripcionturno.isEmpty()) {
							
							// Procedemos a insertar la nueva inscripción en el turno
							ScsInscripcionturno inscripcionturno = new ScsInscripcionturno();
							inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservacionessolicitud());
							inscripcionturno.setFechasolicitud(inscripcionesItem.getFechasolicitud());
							inscripcionturno.setFechavalidacion(null);
							inscripcionturno.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
							inscripcionturno.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
							inscripcionturno.setIdinstitucion(idInstitucion);
							inscripcionturno.setFechamodificacion(new Date());
							inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());

							scsInscripcionturnoExtendsMapper.insert(inscripcionturno);
							
							creados++;
							
						}
						
						if(inscripcionesItem.getIdguardia() != null && !inscripcionesItem.getIdguardia().isEmpty()) {
							
							//Buscamos si existe la inscripción en la guardia
							ScsInscripcionguardia inscripcionguardia = inscripcionGuardiaExtendsMapper.getLastInscripciones(inscripcionesItem.getIdguardia(), inscripcionesItem.getIdturno(), inscripcionesItem.getIdpersona(), idInstitucion.toString());
							if(inscripcionguardia == null) {
								
								ScsInscripcionguardia guardia = new ScsInscripcionguardia();
								guardia.setObservacionessuscripcion(inscripcionesItem.getObservacionessolicitud());
								guardia.setFechavalidacion(null);
								guardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
								guardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
								guardia.setIdinstitucion(idInstitucion);
								guardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));
								guardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuarios.get(0).getIdusuario());

								scsInscripcionguardiaMapper.insert(guardia);
								
								creados++;
							} else {
								existentes++;
							}
						}
					
					} catch (Exception e) {
						errores++;
					}
				}

				LOGGER.info("insertSolicitarAlta() / -> Salida del servicio para guardar nuevas inscripciones");
			
				Error error = new Error();
				error.setCode(200);
				error.setDescription("Se ha realizado: " + creados + " Inscripciones (Pendientes de validar), " + existentes + " Inscripciones ya existian y " + errores + " inscripciones han dado error");

				insertResponseDTO.setError(error);

				LOGGER.info("insertSolicitarAlta() -> Salida del servicio para guardar una nueva inscripcion");
			}
		}
		
		return insertResponseDTO;
	}
}