package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.util.SSCellRange;
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
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample;
import org.itcgae.siga.db.entities.ScsProcurador;
import org.itcgae.siga.db.entities.ScsProcuradorExample;
import org.itcgae.siga.db.entities.ScsTipoactuacion;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;

import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.scs.services.oficio.IGestionInscripcionesService;
import org.itcgae.siga.scs.services.oficio.IGestionTurnosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionInscripcionesServiceImpl implements IGestionInscripcionesService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;

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

	@Override
	public ComboDTO comboTurnos(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTurnos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsInscripcionturnoExtendsMapper.comboTurnos(idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTurnosExtendsMapper.comboTurnos() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.info("comboTurnos() -> Salida del servicio para obtener combo actuaciones");
		return comboDTO;
	}

	@Override
	public InscripcionesDTO busquedaInscripciones(InscripcionesItem inscripcionesItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDTO inscripcionesDTO = new InscripcionesDTO();
		List<InscripcionesItem> inscripcionesItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				GenParametrosExample genParametrosExample = new GenParametrosExample();

				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				LOGGER.info(
						"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				String fechahasta = "";
				String fechadesde = "";
				String afechade = "";
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
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

				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaInscripciones(inscripcionesItem,
						idInstitucion, fechadesde, fechahasta, afechade, tamMaximo);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (inscripcionesItems != null) {
					inscripcionesDTO.setInscripcionesItems(inscripcionesItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return inscripcionesDTO;
	}
	
	@Override
	public InscripcionesTarjetaOficioDTO busquedaTarjeta(InscripcionesItem inscripcionesItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesTarjetaOficioDTO inscripcionesDTO = new InscripcionesTarjetaOficioDTO();
		List<InscripcionesItem> inscripcionesItems = null;
		List<InscripcionesTarjetaOficioItem> inscripcionesHistoricoItems = new ArrayList<InscripcionesTarjetaOficioItem>();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {


				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaTarjeta(inscripcionesItem,idInstitucion);
				

				if (inscripcionesItems != null) {
					InscripcionesTarjetaOficioItem solicitud = new InscripcionesTarjetaOficioItem();
					InscripcionesTarjetaOficioItem solicitud1 = new InscripcionesTarjetaOficioItem();
					InscripcionesTarjetaOficioItem solicitud2 = new InscripcionesTarjetaOficioItem();
					InscripcionesTarjetaOficioItem solicitud3 = new InscripcionesTarjetaOficioItem();
					InscripcionesTarjetaOficioItem solicitud4 = new InscripcionesTarjetaOficioItem();

					
						if(inscripcionesItems.get(0).getFechasolicitud() != null) {
							
							solicitud.setAccion("Solicitud");
							solicitud.setObservaciones(inscripcionesItems.get(0).getObservacionessolicitud());
							solicitud.setFecha(inscripcionesItems.get(0).getFechasolicitud());
							solicitud.setId("0");
							inscripcionesHistoricoItems.add(solicitud);
						}
						if(inscripcionesItems.get(0).getFechasolicitudbaja() != null) {
							solicitud1.setAccion("Solicitud de Baja");
							solicitud1.setObservaciones(inscripcionesItems.get(0).getObservacionesbaja());
							solicitud1.setFecha(inscripcionesItems.get(0).getFechasolicitudbaja());
							solicitud1.setId("2");
							inscripcionesHistoricoItems.add(solicitud1);

						}
						
						if(inscripcionesItems.get(0).getFechavalidacion() != null) {
							solicitud2.setAccion("Validación de Alta");
							solicitud2.setObservaciones(inscripcionesItems.get(0).getObservacionesvalidacion());
							solicitud2.setFecha(inscripcionesItems.get(0).getFechavalidacion());
							solicitud2.setId("1");
							inscripcionesHistoricoItems.add(solicitud2);

						}
						
						if(inscripcionesItems.get(0).getFechadenegacion() != null) {
							solicitud3.setAccion("Denegación");
							solicitud3.setObservaciones(inscripcionesItems.get(0).getObservacionesdenegacion());
							solicitud3.setFecha(inscripcionesItems.get(0).getFechadenegacion());
							solicitud3.setId("4");
							inscripcionesHistoricoItems.add(solicitud3);

						}
						
						if(inscripcionesItems.get(0).getFechabaja() != null) {
							solicitud4.setAccion("Validación Baja");
							solicitud4.setObservaciones(inscripcionesItems.get(0).getObservacionesvalbaja());
							solicitud4.setFecha(inscripcionesItems.get(0).getFechabaja());
							solicitud4.setId("3");
							inscripcionesHistoricoItems.add(solicitud4);

						}
						Collections.sort(inscripcionesHistoricoItems, new Comparator<InscripcionesTarjetaOficioItem>() {
						    public int compare(InscripcionesTarjetaOficioItem m1, InscripcionesTarjetaOficioItem m2) {
						        return m1.getId().compareTo(m2.getId());
						    }
						});
					inscripcionesDTO.setInscripcionesTarjetaItems(inscripcionesHistoricoItems);
				}
			
			}
		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return inscripcionesDTO;
	}
	
	@Override
	public InscripcionesDTO TarjetaColaOficio(InscripcionesItem inscripcionesItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDTO inscripcionesDTO = new InscripcionesDTO();
		List<InscripcionesItem> inscripcionesItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String busquedaOrden = "";

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				List<ScsTurno> listaTurno = null;
				
				
				ScsTurnoExample exampleturno = new ScsTurnoExample();
				exampleturno.createCriteria()
						.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
						.andIdinstitucionEqualTo(idInstitucion);
				
				listaTurno = scsTurnosExtendsMapper.selectByExample(exampleturno);
				
				ScsTurno turno = listaTurno.get(0);
				
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = new ArrayList<ComboItem>();
				comboItems = scsOrdenacioncolasExtendsMapper.ordenColasEnvios(turno.getIdordenacioncolas().toString());
				comboDTO.setCombooItems(comboItems);

				for (int i = 0; i < comboDTO.getCombooItems().size(); i++) {
					if (!comboDTO.getCombooItems().get(i).getLabel().toString().equals("0")) {
						busquedaOrden += comboDTO.getCombooItems().get(i).getValue() + ",";
					}
				}
				if (busquedaOrden != null && busquedaOrden.length() > 0) {
					busquedaOrden = busquedaOrden.substring(0, busquedaOrden.length() - 1);
				}
				
				Date prueba = inscripcionesItem.getFechasolicitud();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = dateFormat.format(prueba);
				
				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaColaOficio(inscripcionesItem, strDate, busquedaOrden,
						idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (inscripcionesItems != null) {
					inscripcionesDTO.setInscripcionesItems(inscripcionesItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return inscripcionesDTO;
	}
	
	@Override
	public UpdateResponseDTO updateValidar(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		LOGGER.info("updatePartidasPres() ->  Entrada al servicio para guardar edicion de Partida presupuestaria");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;
		List<InscripcionesItem> trabajosSJCS = null;

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
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						List<ScsInscripcionturno> listainscripcion = null;
						ScsInscripcionturnoExample exampleinscripcion = new ScsInscripcionturnoExample();
						exampleinscripcion.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

						listainscripcion = scsInscripcionturnoExtendsMapper.selectByExample(exampleinscripcion);
						for (int i = 0; i < listainscripcion.size(); i++) {

							ScsInscripcionturno inscripcionturno = listainscripcion.get(i);

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
							if (inscripcionesItem.getFechasolicitudbaja() == null) {
								inscripcionturno.setObservacionesvalidacion(inscripcionesItem.getObservaciones());
								inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
								inscripcionturno.setFechadenegacion(null);
								inscripcionturno.setObservacionesdenegacion(null);
								response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);

							} else {
								inscripcionturno.setObservacionesvalbaja(inscripcionesItem.getObservaciones());
								inscripcionturno.setFechasolicitudbaja(inscripcionesItem.getFechaActual());
								inscripcionturno.setFechabaja(inscripcionesItem.getFechaActual());
								inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
								response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);
							}

						}						
						if(inscripcionesItem.getEstadonombre().equals("Pendiente de Alta")) {
							if (inscripcionesItem.getTipoguardias().equals("Obligatorias")
									|| inscripcionesItem.getTipoguardias().equals("Todas o ninguna")) {
								
								List<ScsInscripcionguardia> inscripcionguardia = null;
								ScsInscripcionguardiaExample exampleguardia = new ScsInscripcionguardiaExample();
								exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
										.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

								inscripcionguardia = scsInscripcionguardiaMapper.selectByExample(exampleguardia);
								for (int i = 0; i < inscripcionguardia.size(); i++) {
								ScsInscripcionguardia guardia = inscripcionguardia.get(i);
								guardia.setObservacionesvalbaja(inscripcionesItem.getObservaciones());
								guardia.setFechasolicitudbaja(inscripcionesItem.getFechaActual());
								guardia.setFechabaja(inscripcionesItem.getFechaActual());
								guardia.setFechavalidacion(inscripcionesItem.getFechaActual());
								response = scsInscripcionguardiaMapper.updateByPrimaryKey(guardia);
							}
							}
						}
						if(inscripcionesItem.getEstadonombre().equals("Pendiente de Baja")) {
							if (inscripcionesItem.getTipoguardias().equals("Obligatorias")
									|| inscripcionesItem.getTipoguardias().equals("Todas o ninguna")) {
								
								List<ScsInscripcionguardia> inscripcionguardia = null;
								ScsInscripcionguardiaExample exampleguardia = new ScsInscripcionguardiaExample();
								exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
										.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

								inscripcionguardia = scsInscripcionguardiaMapper.selectByExample(exampleguardia);
								for (int i = 0; i < inscripcionguardia.size(); i++) {
								ScsInscripcionguardia guardia = inscripcionguardia.get(i);
								guardia.setObservacionesvalbaja(inscripcionesItem.getObservaciones());
								guardia.setFechasolicitudbaja(inscripcionesItem.getFechaActual());
								guardia.setFechabaja(inscripcionesItem.getFechaActual());
								guardia.setFechavalidacion(inscripcionesItem.getFechaActual());
								response = scsInscripcionguardiaMapper.updateByPrimaryKey(guardia);
							}
							}
						}
						
						if(inscripcionesItem.getEstadonombre().equals("Pendiente de Baja")) {
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							String fechaActual;
							Date fecha = new Date();
							fechaActual = dateFormat.format(fecha);
							List<InscripcionesItem> trabajosPendientes = null;
							trabajosSJCS = scsInscripcionturnoExtendsMapper.busquedaTrabajosGuardias(inscripcionesItem, idInstitucion,fechaActual);
							trabajosPendientes = scsInscripcionturnoExtendsMapper.busquedaTrabajosPendientes(inscripcionesItem, idInstitucion, fechaActual);
							if(trabajosSJCS.size()>0 || trabajosPendientes.size() >0) {								
								error.setCode(400);								
								error.setDescription("justiciaGratuita.oficio.inscripciones.mensajeSJCS");
								updateResponseDTO.setError(error);
								return updateResponseDTO;
							}

						}
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}
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
	public UpdateResponseDTO updateDenegar(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
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
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						List<ScsInscripcionturno> listainscripcion = null;
						ScsInscripcionturnoExample exampleinscripcion = new ScsInscripcionturnoExample();
						exampleinscripcion.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

						listainscripcion = scsInscripcionturnoExtendsMapper.selectByExample(exampleinscripcion);
						for (int i = 0; i < listainscripcion.size(); i++) {

							ScsInscripcionturno inscripcionturno = listainscripcion.get(i);

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");						
								inscripcionturno.setObservacionesdenegacion(inscripcionesItem.getObservaciones());
								inscripcionturno.setFechasolicitudbaja(inscripcionesItem.getFechaActual());
								inscripcionturno.setFechabaja(inscripcionesItem.getFechaActual());
								inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
								response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);						
						}
						
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}
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
	public UpdateResponseDTO updateCambiarFecha(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
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
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						List<ScsInscripcionturno> listainscripcion = null;
						ScsInscripcionturnoExample exampleinscripcion = new ScsInscripcionturnoExample();
						exampleinscripcion.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

						listainscripcion = scsInscripcionturnoExtendsMapper.selectByExample(exampleinscripcion);
						for (int i = 0; i < listainscripcion.size(); i++) {

							ScsInscripcionturno inscripcionturno = listainscripcion.get(i);

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");						
								if(inscripcionesItem.getEstadonombre() == "Pendiente de Alta") {
									inscripcionturno.setFechasolicitud(inscripcionesItem.getFechaActual());
									inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());
								}
								
								if(inscripcionesItem.getEstadonombre() == "Alta") {
									if(inscripcionesItem.getFechaActual().after(inscripcionesItem.getFechasolicitud()) 
											|| inscripcionesItem.getFechaActual().equals(inscripcionesItem.getFechasolicitud()) ) {
										inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
										inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());

									}else {
										response = 0;
										error.setCode(400);
										error.setDescription("enviosMasivos.literal.fechaHora");
										updateResponseDTO.setStatus(SigaConstants.KO);
									}
									
								}
								
								if(inscripcionesItem.getEstadonombre() == "Pendiente de Baja") {
									if(inscripcionesItem.getFechaActual().after(inscripcionesItem.getFechasolicitud()) 
											|| inscripcionesItem.getFechaActual().equals(inscripcionesItem.getFechasolicitud()) ) {
										inscripcionturno.setFechasolicitudbaja(inscripcionesItem.getFechaActual());
										inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());

									}else {
										response = 0;
										error.setCode(400);
										error.setDescription("enviosMasivos.literal.fechaHora");
										updateResponseDTO.setStatus(SigaConstants.KO);
									}
									
								}
								
								if(inscripcionesItem.getEstadonombre() == "Baja") {
									if(inscripcionesItem.getFechaActual().after(inscripcionesItem.getFechasolicitud()) 
											|| inscripcionesItem.getFechaActual().equals(inscripcionesItem.getFechasolicitud()) ) {
										inscripcionturno.setFechabaja(inscripcionesItem.getFechaActual());
										inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());

									}else {
										response = 0;
										error.setCode(400);
										error.setDescription("enviosMasivos.literal.fechaHora");
										updateResponseDTO.setStatus(SigaConstants.KO);
									}
									
								}
								
								if(inscripcionesItem.getEstadonombre() == "Denegada") {
									if(inscripcionesItem.getFechaActual().after(inscripcionesItem.getFechasolicitud()) 
											|| inscripcionesItem.getFechaActual().equals(inscripcionesItem.getFechasolicitud()) ) {
										inscripcionturno.setFechadenegacion(inscripcionesItem.getFechaActual());
										inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());

									}else {
										response = 0;
										error.setCode(400);
										error.setDescription("enviosMasivos.literal.fechaHora");
										updateResponseDTO.setStatus(SigaConstants.KO);
									}
									
								}
								
								response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);						
						}
						
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}
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
	public UpdateResponseDTO updateSolicitarBaja(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
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
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						List<ScsInscripcionturno> listainscripcion = null;
						ScsInscripcionturnoExample exampleinscripcion = new ScsInscripcionturnoExample();
						exampleinscripcion.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

						listainscripcion = scsInscripcionturnoExtendsMapper.selectByExample(exampleinscripcion);

						ScsInscripcionturno inscripcionturno = new ScsInscripcionturno();
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
						if (inscripcionesItem.getValidarinscripciones().equals("S")) {

							inscripcionturno.setFechavalidacion(new Date());
							inscripcionturno.setFechasolicitudbaja(new Date());
							inscripcionturno.setFechabaja(null);
							inscripcionturno.setFechadenegacion(null);
						} else {
							inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());
							inscripcionturno.setFechasolicitudbaja(new Date());
							List<ScsInscripcionguardia> inscripcionguardia = null;
							ScsInscripcionguardiaExample exampleguardia = new ScsInscripcionguardiaExample();
							exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
									.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));

							inscripcionguardia = scsInscripcionguardiaMapper.selectByExample(exampleguardia);

							ScsInscripcionguardia guardia = new ScsInscripcionguardia();
							guardia.setFechabaja(new Date());

							response = scsInscripcionguardiaMapper.updateByExampleSelective(guardia, exampleguardia);

						}
						response = scsInscripcionturnoExtendsMapper.updateByExampleSelective(inscripcionturno,
								exampleinscripcion);

							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							String fechaActual;
							Date fecha = new Date();
							fechaActual = dateFormat.format(fecha);
							List<InscripcionesItem> trabajosSJCS = null;
							List<InscripcionesItem> trabajosPendientes = null;
							trabajosSJCS = scsInscripcionturnoExtendsMapper.busquedaTrabajosGuardias(inscripcionesItem, idInstitucion,fechaActual);
							trabajosPendientes = scsInscripcionturnoExtendsMapper.busquedaTrabajosPendientes(inscripcionesItem, idInstitucion, fechaActual);
							if(trabajosSJCS.size()>0 || trabajosPendientes.size() >0) {								
								error.setCode(400);								
								error.setDescription("justiciaGratuita.oficio.inscripciones.mensajeSJCS");
								updateResponseDTO.setError(error);
								return updateResponseDTO;
							}

						
						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}
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
	public InscripcionesDTO busquedaTarjetaInscripciones(InscripcionesItem inscripcionesItem,
			HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InscripcionesDTO inscripcionesDTO = new InscripcionesDTO();
		List<InscripcionesItem> inscripcionesItems = null;
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

				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaTarjetaInscripciones(inscripcionesItem,
						idInstitucion);

				LOGGER.info(
						"busquedaColaOficio()  -> Salida a scsOrdenacioncolasExtendsMapper para obtener orden colas");

				if (inscripcionesItems != null) {
					inscripcionesDTO.setInscripcionesItems(inscripcionesItems);
				}
			}

		}
		LOGGER.info("busquedaColaOficio() -> Salida del servicio para obtener la busqueda Cola Oficio");
		return inscripcionesDTO;
	}

}
