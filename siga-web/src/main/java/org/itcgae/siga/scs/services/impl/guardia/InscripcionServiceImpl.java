package org.itcgae.siga.scs.services.impl.guardia;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.itcgae.siga.DTOs.scs.GrupoGuardiaColegiadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasTurnosItem;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TrabajosSJCSInsGuardiaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.scs.services.guardia.InscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
	public UpdateResponseDTO validarInscripciones(List<BusquedaInscripcionMod> validarBody, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		BusquedaInscripcionMod objeto = new BusquedaInscripcionMod();
		GrupoGuardiaColegiadoItem objetoFK = new GrupoGuardiaColegiadoItem();
		
		int contadorKO=0;
		
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("validarInscripciones() -> Entrada para validar las inscripciones");
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA=null,FECHADENEGACION=null,FECHASOLICITUD=null,FECHASOLICITUDBAJA=null,FECHAVALIDACION=null,FECHAVALORALTA=null,FECHAVALORBAJA=null;
				String FECHADENEGACIONNUEVA=null,FECHASOLICITUDNUEVA=null,FECHASOLICITUDBAJANUEVA=null,FECHAVALIDACIONNUEVA=null,FECHACREACIONNUEVA=null;

				for(BusquedaInscripcionMod a: validarBody) {
					
				        if(a.getFechabaja() != null) {
							 FECHABAJA=formatter.format(a.getFechabaja());
						}
						
						if(a.getFechadenegacion() != null) {
							 FECHADENEGACION=formatter.format(a.getFechadenegacion());
						}
						
						if(a.getFechasolicitud() != null) {
							 FECHASOLICITUD=formatter.format(a.getFechasolicitud());
						}

						if(a.getFechasolicitudbaja() != null) {
							 FECHASOLICITUDBAJA=formatter.format(a.getFechasolicitudbaja());
						}
						

						if(a.getFechavalidacion() != null) {
							 FECHAVALIDACION=formatter.format(a.getFechavalidacion());
						}
						
						if(a.getFechavaloralta() != null) {
							 FECHAVALORALTA=formatter.format(a.getFechavaloralta());
						}
						
						if(a.getFechavalorbaja() != null) {
							 FECHAVALORBAJA=formatter.format(a.getFechavalorbaja());
						}
						
						
						if(a.getFechadenegacionNUEVA() != null) {
							 FECHADENEGACIONNUEVA=formatter.format(a.getFechadenegacionNUEVA());
						}
						
						if(a.getFechasolicitudNUEVA() != null) {
							 FECHASOLICITUDNUEVA=formatter.format(a.getFechasolicitudNUEVA());
						}

						if(a.getFechasolicitudbajaNUEVA() != null) {
							 FECHASOLICITUDBAJANUEVA=formatter.format(a.getFechasolicitudbajaNUEVA());
						}
						

						if(a.getFechavalidacionNUEVA() != null) {
							 FECHAVALIDACIONNUEVA=formatter.format(a.getFechavalidacionNUEVA());
						}
						
						
						
					objeto= inscripcionGuardiaExtensdsMapper.getObjetoFrontValidarInscripcion(a,idInstitucion.toString(),FECHABAJA,FECHADENEGACION,FECHASOLICITUD,FECHASOLICITUDBAJA,
							FECHAVALIDACION,FECHAVALORALTA,FECHAVALORBAJA);
					
					objetoFK=inscripcionGuardiaExtensdsMapper.getObjetoFKGrupoColegiado(a,idInstitucion.toString(),FECHASOLICITUD);
					
				if(objetoFK != null) {
					
				
						if(objetoFK.getFechacreacion() != null) {
							 FECHACREACIONNUEVA=formatter.format(objetoFK.getFechacreacion());
						}
					
						int deleteFK = inscripcionGuardiaExtensdsMapper.getDeleteFKGrupoColegiado(objeto,FECHASOLICITUD);
						
						int delete=0;
						
						if(deleteFK==1) {
						
							 delete = inscripcionGuardiaExtensdsMapper.getDeleteObjetoFrontValidarInscripcion(objeto,FECHABAJA,FECHADENEGACION,
									FECHASOLICITUD,FECHASOLICITUDBAJA,FECHAVALIDACION,FECHAVALORALTA,FECHAVALORBAJA);
							
						}
						int usuario=usuarios.get(0).getIdusuario();
						
						
						int insert = inscripcionGuardiaExtensdsMapper.getInsertObjetoValidarInscripcion(a,usuario,objetoFK,FECHABAJA,FECHADENEGACIONNUEVA,FECHASOLICITUDBAJANUEVA, FECHASOLICITUDNUEVA, FECHAVALIDACIONNUEVA);
						int insertFK= inscripcionGuardiaExtensdsMapper.getInsertFKGrupoGuardiaColegiado(objetoFK,usuario,FECHASOLICITUDNUEVA,FECHACREACIONNUEVA);
						
						if(insert==0 || delete==0 || insertFK==0)
							contadorKO++;
						
				}else {
					
					
					int delete = inscripcionGuardiaExtensdsMapper.getDeleteObjetoFrontValidarInscripcion(objeto,FECHABAJA,FECHADENEGACION,
								FECHASOLICITUD,FECHASOLICITUDBAJA,FECHAVALIDACION,FECHAVALORALTA,FECHAVALORBAJA);
					
					int usuario=usuarios.get(0).getIdusuario();

					int insert = inscripcionGuardiaExtensdsMapper.getInsertObjetoValidarInscripcion(a,usuario,objetoFK,FECHABAJA,FECHADENEGACIONNUEVA,FECHASOLICITUDBAJANUEVA, FECHASOLICITUDNUEVA, FECHAVALIDACIONNUEVA);

					if(insert==0 || delete==0 )
						contadorKO++;
				}
				

				
				LOGGER.info("validarInscripciones() -> Salida ya con los datos actualizados y la inscripción validada");
			
				}
			}
		}
		
		if(contadorKO==1)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);
		//upd.setStatus(SigaConstants.OK); //siempre devuelve un OK
		return upd;
	}

	@Override
	public UpdateResponseDTO denegarInscripciones(List<BusquedaInscripcionMod> denegarBody,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		int inscripciones= 0;
		int contadorKO=0;
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"denegarInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("denegarInscripciones() -> Entrada para denegar modificando los datos correspondientes");
				
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA=null,FECHADENEGACION=null,FECHASOLICITUD=null,FECHASOLICITUDBAJA=null,FECHAVALIDACION=null,FECHAVALORALTA=null,FECHAVALORBAJA=null;
				String FECHADENEGACIONNUEVA=null,FECHASOLICITUDNUEVA=null,FECHASOLICITUDBAJANUEVA=null,FECHAVALIDACIONNUEVA=null,FECHACREACIONNUEVA=null;

						
				for(BusquedaInscripcionMod a: denegarBody) {
					
					if(a.getFechabaja() != null) {
						 FECHABAJA=formatter.format(a.getFechabaja());
					}
					
					if(a.getFechadenegacion() != null) {
						 FECHADENEGACION=formatter.format(a.getFechadenegacion());
					}
					
					if(a.getFechasolicitud() != null) {
						 FECHASOLICITUD=formatter.format(a.getFechasolicitud());
					}

					if(a.getFechasolicitudbaja() != null) {
						 FECHASOLICITUDBAJA=formatter.format(a.getFechasolicitudbaja());
					}
					

					if(a.getFechavalidacion() != null) {
						 FECHAVALIDACION=formatter.format(a.getFechavalidacion());
					}
					
					if(a.getFechavaloralta() != null) {
						 FECHAVALORALTA=formatter.format(a.getFechavaloralta());
					}
					
					if(a.getFechavalorbaja() != null) {
						 FECHAVALORBAJA=formatter.format(a.getFechavalorbaja());
					}
					
					if(a.getFechadenegacionNUEVA() != null) {
						 FECHADENEGACIONNUEVA=formatter.format(a.getFechadenegacionNUEVA());
					}
					
					if(a.getFechasolicitudNUEVA() != null) {
						 FECHASOLICITUDNUEVA=formatter.format(a.getFechasolicitudNUEVA());
					}

					if(a.getFechasolicitudbajaNUEVA() != null) {
						 FECHASOLICITUDBAJANUEVA=formatter.format(a.getFechasolicitudbajaNUEVA());
					}
					

					if(a.getFechavalidacionNUEVA() != null) {
						 FECHAVALIDACIONNUEVA=formatter.format(a.getFechavalidacionNUEVA());
					}
					
					
					int usuario=usuarios.get(0).getIdusuario();
					
					inscripciones = inscripcionGuardiaExtensdsMapper.getValidarDenegarSBajaCFechaInscripciones(a, idInstitucion.toString(),FECHABAJA,FECHADENEGACIONNUEVA,
							FECHASOLICITUDNUEVA,FECHASOLICITUDBAJANUEVA,FECHAVALIDACIONNUEVA,FECHAVALORALTA,FECHAVALORBAJA,usuario);
					
					if(inscripciones==0)
						contadorKO++;
				}

				
				LOGGER.info("denegarInscripciones() -> Salida ya con los datos denegados");
			}
		}
		
		if(contadorKO>0)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);
		
		return upd;
		
	}
	
	@Override
	public UpdateResponseDTO solicitarBajaInscripcion(List<BusquedaInscripcionMod> solicitarbajabody, HttpServletRequest request) { 
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		int inscripciones= 0;
		int contadorKO=0;
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"solicitarBajaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("solicitarBajaInscripcion() -> Entrada para modificar los datos correspondientes");
				
				
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA=null,FECHADENEGACION=null,FECHASOLICITUD=null,FECHASOLICITUDBAJA=null,FECHAVALIDACION=null,FECHAVALORALTA=null,FECHAVALORBAJA=null;
				String FECHADENEGACIONNUEVA=null,FECHASOLICITUDNUEVA=null,FECHASOLICITUDBAJANUEVA=null,FECHAVALIDACIONNUEVA=null,FECHACREACIONNUEVA=null;

						
				for(BusquedaInscripcionMod a: solicitarbajabody) {
					
					if(a.getFechabaja() != null) {
						 FECHABAJA=formatter.format(a.getFechabaja());
					}
					
					if(a.getFechadenegacion() != null) {
						 FECHADENEGACION=formatter.format(a.getFechadenegacion());
					}
					
					if(a.getFechasolicitud() != null) {
						 FECHASOLICITUD=formatter.format(a.getFechasolicitud());
					}

					if(a.getFechasolicitudbaja() != null) {
						 FECHASOLICITUDBAJA=formatter.format(a.getFechasolicitudbaja());
					}
					

					if(a.getFechavalidacion() != null) {
						 FECHAVALIDACION=formatter.format(a.getFechavalidacion());
					}
					
					if(a.getFechavaloralta() != null) {
						 FECHAVALORALTA=formatter.format(a.getFechavaloralta());
					}
					
					if(a.getFechavalorbaja() != null) {
						 FECHAVALORBAJA=formatter.format(a.getFechavalorbaja());
					}
					
					if(a.getFechadenegacionNUEVA() != null) {
						 FECHADENEGACIONNUEVA=formatter.format(a.getFechadenegacionNUEVA());
					}
					
					if(a.getFechasolicitudNUEVA() != null) {
						 FECHASOLICITUDNUEVA=formatter.format(a.getFechasolicitudNUEVA());
					}

					if(a.getFechasolicitudbajaNUEVA() != null) {
						 FECHASOLICITUDBAJANUEVA=formatter.format(a.getFechasolicitudbajaNUEVA());
					}
					

					if(a.getFechavalidacionNUEVA() != null) {
						 FECHAVALIDACIONNUEVA=formatter.format(a.getFechavalidacionNUEVA());
					}
					
					
					int usuario=usuarios.get(0).getIdusuario();
					
					inscripciones = inscripcionGuardiaExtensdsMapper.getValidarDenegarSBajaCFechaInscripciones(a, idInstitucion.toString(),FECHABAJA,FECHADENEGACIONNUEVA,
							FECHASOLICITUDNUEVA,FECHASOLICITUDBAJANUEVA,FECHAVALIDACIONNUEVA,FECHAVALORALTA,FECHAVALORBAJA,usuario);
					
					if(inscripciones==0)
						contadorKO++;
				}

				
				LOGGER.info("solicitarBajaInscripcion() -> Salida ya con los datos modificados");
			}
		}
		
		if(contadorKO>0)
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
		//List<BusquedaInscripcionItem> inscripciones = new ArrayList<BusquedaInscripcionItem>();
		int inscripciones= 0;
		int contadorKO=0;
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"cambiarFechaInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("cambiarFechaInscripcion() -> Entrada para modificar los datos correspondientes");
				
				
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA=null,FECHAVALIDACIONNUEVA=null;

						
				for(BusquedaInscripcionMod a: cambiarfechabody) {
					
					if(a.getFechabaja() != null) {
						 FECHABAJA=formatter.format(a.getFechabaja());
					}
					
					if(a.getFechavalidacionNUEVA() != null) {
						 FECHAVALIDACIONNUEVA=formatter.format(a.getFechavalidacionNUEVA());
					}
					
					
					int usuario=usuarios.get(0).getIdusuario();
					
					inscripciones = inscripcionGuardiaExtensdsMapper.getCFechaInscripciones(a, idInstitucion.toString(),FECHABAJA,
							FECHAVALIDACIONNUEVA,usuario);
					
					if(inscripciones==0)
						contadorKO++;
				}

				
				LOGGER.info("cambiarFechaInscripcion() -> Salida ya con los datos modificados");
			}
		}
		
		if(contadorKO==1)
			upd.setStatus(SigaConstants.OK);
		else
			upd.setStatus(SigaConstants.KO);
		
		return upd;
	}
	
	
	@Override
	public Boolean buscarSaltosCompensaciones(List<BusquedaInscripcionMod> buscarSaltosCompensaciones, HttpServletRequest request) { 
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<SaltoCompGuardiaItem> saltosList = new ArrayList<SaltoCompGuardiaItem>();
		List<SaltoCompGuardiaItem> compensacionesList = new ArrayList<SaltoCompGuardiaItem>();

		int contadorKO=0;
		int saltosN=0;
		int compensacionesN=0;
		
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarSaltosCompensaciones() -> Entrada para recoger los datos correspondientes");
				
						
				for(BusquedaInscripcionMod a: buscarSaltosCompensaciones) {
					
					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();
					String saltos = "S";
					String compensaciones = "C";
					
					int usuario=usuarios.get(0).getIdusuario();
					
					saltosList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,saltos);
					
					compensacionesList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,compensaciones);

					
					if(saltosList.size()!=0 || compensacionesList.size()!=0)
						contadorKO++;
				}
	
				LOGGER.info("buscarSaltosCompensaciones() -> Salida ya con los datos de saltos y compensaciones recogidos");
			}
		}
		
		boolean existe;
		
		if(contadorKO==1)
			existe=true;
		else
			existe=false;
		
		return existe; //luego cuando vaya al front si data es igual a true significa que hay saltos y compensaciones, si no es que no hay. 
	}
	
	@Override
	public Boolean buscarTrabajosSJCS(List<BusquedaInscripcionMod> buscarTabajosSJCS, HttpServletRequest request) { 
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<TrabajosSJCSInsGuardiaItem> trabajosSJCSList = new ArrayList<TrabajosSJCSInsGuardiaItem>();

		int contadorKO=0;
		int saltosN=0;
		int compensacionesN=0;
		
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarTrabajosSJCS() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarTrabajosSJCS() -> Entrada para recoger los datos correspondientes");
				
						
				for(BusquedaInscripcionMod a: buscarTabajosSJCS) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String FECHADESDE=null,FECHAHASTA=null;
					
					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();
				
					int usuario=usuarios.get(0).getIdusuario();
					
					trabajosSJCSList = inscripcionGuardiaExtensdsMapper.getBuscarTrabajosSJCS(idInstitucion.toString(),idturno,idguardia,idpersona,FECHADESDE,FECHAHASTA);
					
					
					
					if(trabajosSJCSList.size()!=0)
						contadorKO++;
				}
	
				LOGGER.info("buscarTrabajosSJCS() -> Salida ya con los datos de los trabajos SJCS recogidos");
			}
		}
		
		boolean existe;
		
		if(contadorKO==1)
			existe=true;
		else
			existe=false;
		
		return existe;
	}
	
	@Override
	public Boolean buscarGuardiasAsocTurnos(List<BusquedaInscripcionMod> buscarGuardiasAsocTurnos, HttpServletRequest request) { 
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<GuardiasTurnosItem> listaGuardias = new ArrayList<GuardiasTurnosItem>();

		int contadorKO=0;
		int OKUpdateTurno=0;
		int OKUpdateGuardia=0;
		
		UpdateResponseDTO upd = new UpdateResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"buscarGuardiasAsocTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("buscarGuardiasAsocTurnos() -> Entrada para recoger los datos correspondientes");
				
				String requeridaValidacion="";
				

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String FECHABAJA=null;
					
				
				for(BusquedaInscripcionMod a: buscarGuardiasAsocTurnos) {
					
					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();
				
					int usuario=usuarios.get(0).getIdusuario();
					
					listaGuardias = inscripcionGuardiaExtensdsMapper.getbuscarGuardiasAsocTurnos(idInstitucion.toString(),idturno,idguardia,idpersona);
				
					//modificar el turno según si es requerida o no 
					String validarinscripciones = inscripcionGuardiaExtensdsMapper.validarInscripcionesCampo(idInstitucion.toString(),idturno,idguardia,idpersona);
					
					if(validarinscripciones.equals("S")) {
						//se queda en Pendiente de Baja, donde la fecha de baja es null
						a.setFechabaja(null);
					}
					
					Date fechaHoy = new Date();
					if(validarinscripciones.equals("N")) {
						//se cambia a baja donde la fecha de baja es distinta de null
						 a.setFechabaja(fechaHoy);
							
					}
					
					if(a.getFechabaja() != null) {
						 FECHABAJA=formatter.format(a.getFechabaja());
					} 
					
					//actualizar scs_inscripcionturno
					if(a.getFechabaja() != null) {
						OKUpdateTurno = inscripcionGuardiaExtensdsMapper.UpdateInscripcionTurno(idInstitucion.toString(),idturno,a,FECHABAJA);
					}
					
					if(listaGuardias.size()>0) {
						for(GuardiasTurnosItem b: listaGuardias) {
							//buscar si es Requerida o no 
							requeridaValidacion = inscripcionGuardiaExtensdsMapper.requeridaValidacionCampo(idInstitucion.toString(),idturno,idguardia,idpersona);
							//modificar la fecha y el estado
							if(requeridaValidacion.equals("S")) {
								//se queda en Pendiente de Baja, donde la fecha de baja es null
								a.setFechabaja(null);
							}
							
							Date fechaHoy2 = new Date();
							if(requeridaValidacion.equals("N")){
								//se cambia a baja donde la fecha de baja es distinta de null
								 a.setFechabaja(fechaHoy2);
									
							}
							
							if(a.getFechabaja() != null) {
								 FECHABAJA=formatter.format(a.getFechabaja());
							} 
							
							
							//actualizar scs_inscripcionguardia
							if(a.getFechabaja() != null) {
								OKUpdateGuardia = inscripcionGuardiaExtensdsMapper.UpdateInscripcionGuardia(idInstitucion.toString(),idturno,b.getIdguardia(), a, FECHABAJA);
							}
						}
					}
					
					if(OKUpdateTurno != 0 || OKUpdateGuardia != 0)
						contadorKO++;
				}
	
				LOGGER.info("buscarGuardiasAsocTurnos() -> Salida ya con los datos de los trabajos SJCS recogidos");
			}
		}
		
		boolean existe;
		
		if(contadorKO==1)
			existe=true;
		else
			existe=false;
		
		return existe;
	}
	
	@Override
	public DeleteResponseDTO eliminarSaltosCompensaciones(List<BusquedaInscripcionMod> eliminarSaltosCompensaciones, HttpServletRequest request) { 
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<SaltoCompGuardiaItem> saltosList = new ArrayList<SaltoCompGuardiaItem>();
		List<SaltoCompGuardiaItem> compensacionesList = new ArrayList<SaltoCompGuardiaItem>();

		int contadorKO=0;
		int saltosN=0;
		int compensacionesN=0;
		
		DeleteResponseDTO drp = new DeleteResponseDTO();		
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"eliminarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("eliminarSaltosCompensaciones() -> Entrada para borrar los datos correspondientes");
				
						
				for(BusquedaInscripcionMod a: eliminarSaltosCompensaciones) {
					
					String idturno = a.getIdturno();
					String idguardia = a.getIdguardia();
					String idpersona = a.getIdpersona();
					String saltos = "S";
					String compensaciones = "C";
					
					int usuario=usuarios.get(0).getIdusuario();
					
					//buscar primero los saltos y si hay que los borre, que primero busque para que no de error de eliminación si no hay
					saltosList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,saltos);
					
						if(saltosList.size()>0) {
							//query de eliminar saltos
							 saltosN = inscripcionGuardiaExtensdsMapper.getEliminarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,saltos);
						}
					//lo mismo para las compensaciones
					compensacionesList = inscripcionGuardiaExtensdsMapper.getBuscarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,compensaciones);
						if(compensacionesList.size()>0) {
							//query de eliminar compensaciones
							 compensacionesN = inscripcionGuardiaExtensdsMapper.getEliminarSaltoCompensancion(idInstitucion.toString(),idturno,idguardia,idpersona,compensaciones);
						}
					
					if(saltosN==0 || compensacionesN==0)
						contadorKO++;
				}
	
				LOGGER.info("eliminarSaltosCompensaciones() -> Salida ya con los datos de saltos y compensaciones borrados");
			}
		}
		
		
		if(contadorKO==1)
			drp.setStatus(SigaConstants.OK);
		else
			drp.setStatus(SigaConstants.KO);		
		
		return drp; 
		
	}

	

	
	
	
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
