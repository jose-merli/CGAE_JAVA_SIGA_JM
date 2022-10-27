package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.InscripcionesDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesTarjetaOficioItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
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
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.scs.services.oficio.IGestionInscripcionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionInscripcionesServiceImpl implements IGestionInscripcionesService {

	private Logger LOGGER = Logger.getLogger(GestionInscripcionesServiceImpl.class);

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;

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
	private ScsTurnoMapper scsTurnoMapper;

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
		Error error = new Error();
		InscripcionesDTO inscripcionesDTO = new InscripcionesDTO();
		List<InscripcionesItem> inscripcionesItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						"busquedaInscripciones() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
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

				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaInscripcionesTurno(inscripcionesItem,
						idInstitucion, fechadesde, fechahasta, afechade, tamMaximo);

				if((inscripcionesItems != null) && tamMaximo != null && (inscripcionesItems.size()) > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					inscripcionesDTO.setError(error);
					inscripcionesItems.remove(inscripcionesItems.size()-1);
					}
				
				LOGGER.info(
						"busquedaInscripciones() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (inscripcionesItems != null) {
					inscripcionesDTO.setInscripcionesItems(inscripcionesItems);
				}
			}

		}
		LOGGER.info("busquedaInscripciones() -> Salida del servicio para obtener los costes fijos");
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

					
						if(inscripcionesItems.get(0).getFechasolicitud() != null && (inscripcionesItems.get(0).getObservacionessolicitud() != null ||inscripcionesItems.get(0).getObservacionessolicitud().equals(""))) {
							
							solicitud.setAccion("Solicitud");
							solicitud.setObservaciones(inscripcionesItems.get(0).getObservacionessolicitud());
							solicitud.setFecha(inscripcionesItems.get(0).getFechasolicitud());
							solicitud.setId("0");
							inscripcionesHistoricoItems.add(solicitud);
						}
						if(inscripcionesItems.get(0).getFechasolicitudbaja() != null && (inscripcionesItems.get(0).getObservacionesbaja() != null && !inscripcionesItems.get(0).getObservacionesbaja().equals(""))) {
							solicitud1.setAccion("Solicitud de Baja");
							solicitud1.setObservaciones(inscripcionesItems.get(0).getObservacionesbaja());
							solicitud1.setFecha(inscripcionesItems.get(0).getFechasolicitudbaja());
							solicitud1.setId("2");
							inscripcionesHistoricoItems.add(solicitud1);

						}
						
						if(inscripcionesItems.get(0).getFechavalidacion() != null && (inscripcionesItems.get(0).getObservacionesvalidacion() != null && !inscripcionesItems.get(0).getObservacionesvalidacion().equals(""))) {
							solicitud2.setAccion("Validación de Alta");
							solicitud2.setObservaciones(inscripcionesItems.get(0).getObservacionesvalidacion());
							solicitud2.setFecha(inscripcionesItems.get(0).getFechavalidacion());
							solicitud2.setId("1");
							inscripcionesHistoricoItems.add(solicitud2);

						}
						
						if(inscripcionesItems.get(0).getFechadenegacion() != null && (inscripcionesItems.get(0).getObservacionesdenegacion() != null && !inscripcionesItems.get(0).getObservacionesdenegacion().equals(""))) {
							solicitud3.setAccion("Denegación");
							solicitud3.setObservaciones(inscripcionesItems.get(0).getObservacionesdenegacion());
							solicitud3.setFecha(inscripcionesItems.get(0).getFechadenegacion());
							solicitud3.setId("4");
							inscripcionesHistoricoItems.add(solicitud3);

						}
						
						if(inscripcionesItems.get(0).getFechabaja() != null && (inscripcionesItems.get(0).getObservacionesvalbaja() != null && !inscripcionesItems.get(0).getObservacionesvalbaja().equals(""))) {
							solicitud4.setAccion("Validación Baja");
							solicitud4.setObservaciones(inscripcionesItems.get(0).getObservacionesvalbaja());
							solicitud4.setFecha(inscripcionesItems.get(0).getFechabaja());
							solicitud4.setId("3");
							inscripcionesHistoricoItems.add(solicitud4);

						}
						Collections.sort(inscripcionesHistoricoItems, new Comparator<InscripcionesTarjetaOficioItem>() {
						    public int compare(InscripcionesTarjetaOficioItem m1, InscripcionesTarjetaOficioItem m2) {
						        return m2.getFecha().compareTo(m1.getFecha());
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
		//SIGARNV-2471@DTT.JAMARTIN@11/07/2022@INICIO
		List<InscripcionesItem> inscripcionesItems = new ArrayList<>();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		boolean foundUltimo;
		InscripcionesItem punteroInscripcion = null;
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
				String idPersona_ultimo = turno.getIdpersonaUltimo().toString();
				
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
				
				Date prueba = inscripcionesItem.getFechaActual();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = dateFormat.format(prueba);
				
				if(turno.getIdpersonaUltimo() != null) {
					List<InscripcionesItem> inscripcionesAuxiliar = new ArrayList<InscripcionesItem>();
					List<InscripcionesItem> listaInscripciones = scsInscripcionturnoExtendsMapper.busquedaColaOficio2(inscripcionesItem, strDate, busquedaOrden, idInstitucion);
					foundUltimo = false;
					int indiceOrden = 0;

					for (int i = 0; i < listaInscripciones.size(); i++) {

						punteroInscripcion = listaInscripciones.get(i);
						punteroInscripcion.setOrden(String.valueOf(i + 1));

						if (foundUltimo) {
							inscripcionesItems.add(punteroInscripcion);
						} else {
							inscripcionesAuxiliar.add(punteroInscripcion);
						}
						
						if (!foundUltimo && (punteroInscripcion.getIdpersona().equals(idPersona_ultimo))) {
							foundUltimo = true;
						}
					}

					if (inscripcionesAuxiliar.size() > 0) {
						inscripcionesItems.addAll(inscripcionesAuxiliar);
					}

					// Reordenar correctamente la lista
					for (InscripcionesItem inscripcion : inscripcionesItems) {
						inscripcion.setOrden(String.valueOf(indiceOrden + 1));
						inscripcionesItems.set(indiceOrden, inscripcion);
						indiceOrden++;
					}
					
				} else {
					inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaColaOficio(inscripcionesItem, strDate, busquedaOrden, idInstitucion);
				}
				//SIGARNV-2471@DTT.JAMARTIN@11/07/2022@FIN 
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
						ScsInscripcionturnoKey keyinscripcion = new ScsInscripcionturnoKey();

                        keyinscripcion.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
                        keyinscripcion.setIdinstitucion(idInstitucion);
                        keyinscripcion.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
                        keyinscripcion.setFechasolicitud( inscripcionesItem.getFechasolicitud());
                        
                        ScsInscripcionturno inscripcionturno = scsInscripcionturnoMapper.selectByPrimaryKey(keyinscripcion);

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
							if (inscripcionesItem.getFechasolicitudbaja() == null) {
								inscripcionturno.setObservacionesvalidacion(inscripcionesItem.getObservaciones());
								inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
							} else {
								inscripcionturno.setObservacionesvalbaja(inscripcionesItem.getObservaciones());
								inscripcionturno.setFechabaja(inscripcionesItem.getFechaActual());
							}		
							
							inscripcionturno.setFechamodificacion(new Date());
							inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);
							
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
								guardia.setFechavalidacion(inscripcionesItem.getFechaActual());
								guardia.setObservacionesvalidacion(inscripcionesItem.getObservaciones());
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuarios.get(0).getIdusuario());
								
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
								guardia.setFechabaja(inscripcionesItem.getFechaActual());
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuarios.get(0).getIdusuario());
								
								response = scsInscripcionguardiaMapper.updateByPrimaryKey(guardia);
							}
							}
						}
						
//						if(inscripcionesItem.getEstadonombre().equals("Pendiente de Baja")) {
//							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//							String fechaActual;
//							Date fecha = new Date();
//							fechaActual = dateFormat.format(fecha);
//							List<InscripcionesItem> trabajosPendientes = null;
//							trabajosSJCS = scsInscripcionturnoExtendsMapper.busquedaTrabajosGuardias(inscripcionesItem, idInstitucion,fechaActual);
//							trabajosPendientes = scsInscripcionturnoExtendsMapper.busquedaTrabajosPendientes(inscripcionesItem, idInstitucion, fechaActual);
//							if(trabajosSJCS.size()>0 || trabajosPendientes.size() >0) {								
//								error.setCode(400);								
//								error.setDescription("justiciaGratuita.oficio.inscripciones.mensajeSJCS");
//								updateResponseDTO.setError(error);
//								return updateResponseDTO;
//							}
//
//						}
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
		LOGGER.info("updateDenegar() ->  Entrada al servicio para denegar inscripciones");

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
					"updateDenegar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDenegar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						ScsInscripcionturnoKey keyinscripcion = new ScsInscripcionturnoKey();

                        keyinscripcion.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
                        keyinscripcion.setIdinstitucion(idInstitucion);
                        keyinscripcion.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
                        keyinscripcion.setFechasolicitud( inscripcionesItem.getFechasolicitud());
                        
                        ScsInscripcionturno inscripcionturno = scsInscripcionturnoMapper.selectByPrimaryKey(keyinscripcion);

							//Denegacion de peticion de baja
							if(inscripcionesItem.getEstado().equals("2")){
							inscripcionturno.setObservacionesdenegacion(inscripcionesItem.getObservaciones());
							inscripcionturno.setFechasolicitudbaja(null);
							inscripcionturno.setFechabaja(null);
							}
							//Denegacion de peticion de alta
							if(inscripcionesItem.getEstado().equals("0")){
							inscripcionturno.setObservacionesdenegacion(inscripcionesItem.getObservaciones());
							inscripcionturno.setFechadenegacion(inscripcionesItem.getFechaActual());
							}
							

							inscripcionturno.setFechamodificacion(new Date());
							inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							LOGGER.info("updateDenegar() / scsInscripcionturnoExtendsMapper.updateByPrimaryKey() -> Entrada de comprobacion estado y actualizacion");

							response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);	
								
							LOGGER.info("updateDenegar() / scsInscripcionturnoExtendsMapper.updateByPrimaryKey() -> Salida de comprobacion estado y actualizacion");					
						
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
					error.setDescription("No se ha modificado la inscripcion");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la inscripcion excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la inscripcion correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateDenegar() -> Salida del servicio para denegar inscripciones");

			}

		}

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO updateCambiarFecha(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		LOGGER.info("updateCambiarFecha() ->  Entrada al servicio para actualizar fechas efectivas");

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
					"updateCambiarFecha() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCambiarFecha() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						ScsInscripcionturnoKey keyinscripcion = new ScsInscripcionturnoKey();

                        keyinscripcion.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
                        keyinscripcion.setIdinstitucion(idInstitucion);
                        keyinscripcion.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
                        keyinscripcion.setFechasolicitud( inscripcionesItem.getFechasolicitud());
                        
                        ScsInscripcionturno inscripcionturno = scsInscripcionturnoMapper.selectByPrimaryKey(keyinscripcion);

                        ScsInscripcionguardiaExample exampleinscripcionA = new ScsInscripcionguardiaExample();
						exampleinscripcionA.createCriteria()
								.andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()));
						List<ScsInscripcionguardia> guardias = scsInscripcionguardiaMapper.selectByExample(exampleinscripcionA);

							LOGGER.info(
									"updateCambiarFecha()  -> Entrada a GestionInscripcionesServiceImpl");
								
								if(inscripcionesItem.getEstadonombre().equals("Alta") || inscripcionesItem.getEstadonombre().equals("Pendiente de Baja") ) {
									int ccindex = 0;
									int cc = 0;
									while(ccindex<guardias.size() && cc==0) {
										if(inscripcionesItem.getFechaActual().after(guardias.get(ccindex).getFechavalidacion())) cc++;
										ccindex++;
									}
									if(cc>0){
										response = 0;
										error.setCode(400);
										error.setDescription("oficio.busquedaInscripcion.cambioFecha.validacionErrorColegiado");
										updateResponseDTO.setStatus(SigaConstants.KO);
										break;
									}
									
									//Final comprobación
									if(inscripcionesItem.getEstadonombre().equals("Alta")
											|| inscripcionesItem.getFechaActual().before(inscripcionturno.getFechasolicitudbaja())) {
										inscripcionturno.setFechavalidacion(inscripcionesItem.getFechaActual());
										if(inscripcionesItem.getEstadonombre().equals("Alta") ) {
											inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());
										}else if(inscripcionesItem.getEstadonombre().equals("Pendiente de Baja")) {
											inscripcionturno.setObservacionesvalbaja(inscripcionesItem.getObservaciones());
										}
										inscripcionturno.setFechamodificacion(new Date());
										inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
										
										response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);
									}else {
										response = 0;
										error.setCode(400);
										error.setDescription("justiciaGratuita.oficio.inscripciones.errorFechaAltaBaja");
										updateResponseDTO.setStatus(SigaConstants.KO);
										break;
									}
									
								}
								
								else if(inscripcionesItem.getEstadonombre().equals("Baja")) {
									int ccindex = 0;
									int cc = 0;
									while(ccindex<guardias.size() && cc==0) {
										if(guardias.get(ccindex).getFechabaja()!= null && inscripcionesItem.getFechaActual().before(guardias.get(ccindex).getFechabaja()) ) cc++;
										ccindex++;
									}
									if(cc>0){
										response = 0;
										error.setCode(400);
										error.setDescription("oficio.busquedaInscripcion.cambioFecha.bajaErrorColegiado");
										updateResponseDTO.setStatus(SigaConstants.KO);
										break;
									}
									
									//Final comprobación
									
									//if(inscripcionesItem.getFechaActual().after(inscripcionesItem.getFechabaja()) 
									//		|| inscripcionesItem.getFechaActual().equals(inscripcionesItem.getFechabaja()) 
									//		|| response != 0) {
									//if(response !=0) {
										inscripcionturno.setFechabaja(inscripcionesItem.getFechaActual());
										inscripcionturno.setObservacionesbaja(inscripcionesItem.getObservaciones());

										inscripcionturno.setFechamodificacion(new Date());
										inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
										
										response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);

									//}//else {
									//	response = 0;
									//	error.setCode(400);
									//	error.setDescription("justiciaGratuita.oficio.inscripciones.errorFechaBaja");
									//	updateResponseDTO.setStatus(SigaConstants.KO);
									//	break;
									//}
									
								
								
									//response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);						
						}
						
						LOGGER.info(
								"updateCambiarFecha()  -> Salida de GestionInscripcionesServiceImpl");
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
					error.setDescription("No se ha modificado la inscripcion");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado la inscripciones excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la inscripcion correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateCosteFijo() -> Salida del servicio para actualizar las fechas efectivas");

			}

		}

		return updateResponseDTO;
	}

	//EN PROCESO
	@Override
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
						
						if (inscripcionesItem.getEstadonombre().equals("NoPermisos")
								|| inscripcionesItem.getEstadonombre().equals("PendienteDeValidar")) {
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

						response = scsInscripcionturnoMapper.insert(inscripcionturno);

						// Creamos inscripcion a turno
						if (inscripcionesItem.getIdguardia() != null) {

							// ScsInscripcionguardiaExample exampleguardia = new
							// ScsInscripcionguardiaExample();
							// exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							// .andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()));

							ScsInscripcionguardiaKey keyinscripcionguardia = new ScsInscripcionguardiaKey();

							keyinscripcionguardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
							keyinscripcionguardia.setIdinstitucion(idInstitucion);
							keyinscripcionguardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
							keyinscripcionguardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());

							ScsInscripcionguardia oldInscripcionguardia = scsInscripcionguardiaMapper
									.selectByPrimaryKey(keyinscripcionguardia);

							if (oldInscripcionguardia != null) {
								scsInscripcionguardiaMapper.deleteByPrimaryKey(keyinscripcionguardia);
							}

							ScsInscripcionguardia guardia = new ScsInscripcionguardia();

							guardia.setObservacionessuscripcion(inscripcionesItem.getObservacionessolicitud());
							guardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
							if (inscripcionesItem.getEstadonombre().equals("NoPermisos")
									|| inscripcionesItem.getEstadonombre().equals("PendienteDeValidar")) {
								guardia.setFechavalidacion(null);
							} else {
								if (valid == "N")
									guardia.setFechavalidacion(new Date());
							}
							guardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
							guardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
							guardia.setIdinstitucion(idInstitucion);
							guardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));
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
							"Se han modificiado la partida presupuestaria excepto algunos que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la partida presupuestaria correctamente");
				}

				insertResponseDTO.setError(error);

				LOGGER.info("insertSolicitarAlta() -> Salida del servicio para guardar una nueva inscripcion");

			}

		}

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateSolicitarBaja(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		LOGGER.info("updateSolicitarBaja() ->  Entrada al servicio para solicitar baja en una inscripcion");

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
					"updateSolicitarBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateSolicitarBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
						
                        ScsInscripcionturnoKey keyinscripcion = new ScsInscripcionturnoKey();

                        keyinscripcion.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
                        keyinscripcion.setIdinstitucion(idInstitucion);
                        keyinscripcion.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
                        keyinscripcion.setFechasolicitud( inscripcionesItem.getFechasolicitud());
                        
                        ScsInscripcionturno inscripcionturno = scsInscripcionturnoMapper.selectByPrimaryKey(keyinscripcion);
                        
                        ScsInscripcionguardiaExample exampleguardia = new ScsInscripcionguardiaExample();
                        exampleguardia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                .andIdturnoEqualTo(Integer.parseInt(inscripcionesItem.getIdturno()))
                                .andIdpersonaEqualTo(Long.parseLong(inscripcionesItem.getIdpersona()))
                        		.andIdinstitucionEqualTo(idInstitucion);
                        
												
						LOGGER.info(
								"updateSolicitarBaja() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsPartidasPresupuestariaMapper para buscar los costes fijos propios");
						
						
						List<ScsInscripcionguardia> guardias = scsInscripcionguardiaMapper.selectByExample(exampleguardia);
						
						
						if (inscripcionesItem.getValidarinscripciones().equals("S")) {
							
							inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());
							inscripcionturno.setFechasolicitudbaja(new Date());
							inscripcionturno.setFechamodificacion(new Date());
							inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
							
//							inscripcionturno.setIdinstitucion(Short.parseShort(inscripcionesItem.getIdinstitucion()));
//							inscripcionturno.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
//							inscripcionturno.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
//							inscripcionturno.setFechasolicitud(inscripcionesItem.getFechasolicitud());
							
							response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);
							
							if(!guardias.isEmpty()) {
							
								for(ScsInscripcionguardia guardia:guardias) {
		                        
								guardia.setObservacionesbaja(inscripcionesItem.getObservaciones());
								guardia.setFechasolicitudbaja(new Date());
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuarios.get(0).getIdusuario());
								
//								guardia.setIdinstitucion(Short.parseShort(inscripcionesItem.getIdinstitucion()));
//								guardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
//								guardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));
//								guardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
//								guardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
//	
								response = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
								
								}
							
							}
						} else {
							
							inscripcionturno.setObservacionessolicitud(inscripcionesItem.getObservaciones());
							inscripcionturno.setFechasolicitudbaja(new Date());
							inscripcionturno.setFechabaja(new Date());
							inscripcionturno.setFechamodificacion(new Date());
							inscripcionturno.setUsumodificacion(usuarios.get(0).getIdusuario());
							
//							inscripcionturno.setIdinstitucion(Short.parseShort(inscripcionesItem.getIdinstitucion()));
//							inscripcionturno.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
//							inscripcionturno.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
//							inscripcionturno.setFechasolicitud(inscripcionesItem.getFechasolicitud());
							
							response = scsInscripcionturnoExtendsMapper.updateByPrimaryKey(inscripcionturno);
							
							if(!guardias.isEmpty()) {	
								
	                        	for(ScsInscripcionguardia guardia:guardias) {
	                        	
								guardia.setObservacionesbaja(inscripcionesItem.getObservaciones());
								guardia.setFechasolicitudbaja(new Date());
								guardia.setFechabaja(new Date());
								guardia.setFechamodificacion(new Date());
								guardia.setUsumodificacion(usuarios.get(0).getIdusuario());
								
//								guardia.setIdinstitucion(Short.parseShort(inscripcionesItem.getIdinstitucion()));
//								guardia.setIdturno(Integer.parseInt(inscripcionesItem.getIdturno()));
//								guardia.setIdguardia(Integer.parseInt(inscripcionesItem.getIdguardia()));
//								guardia.setIdpersona(Long.parseLong(inscripcionesItem.getIdpersona()));
//								guardia.setFechasuscripcion(inscripcionesItem.getFechasolicitud());
								
								response = scsInscripcionguardiaMapper.updateByPrimaryKeySelective(guardia);
	                        	}
							}

						}
						


						
						LOGGER.info(
								"updateSolicitarBaja() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
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
					error.setDescription("No se ha modificado inscripción");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription(
							"Se han modificiado las inscripciones excepto algunas que tiene las mismas características");

				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la inscripción correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateSolicitarBaja() -> Salida del servicio para solicitar baja en una inscripcion");

			}

		}

		return updateResponseDTO;
	}
	
	@Override
	public Boolean checkTrabajosSJCS(InscripcionesDTO inscripcionesDTO,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual;
		Date fecha = new Date();
		fechaActual = dateFormat.format(fecha);
		int incidencias = 0;
		
		for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
			List<InscripcionesItem> trabajosSJCS = null;
			List<InscripcionesItem> trabajosPendientes = null;
			trabajosSJCS = scsInscripcionturnoExtendsMapper.busquedaTrabajosGuardias(inscripcionesItem, idInstitucion,fechaActual);
			trabajosPendientes = scsInscripcionturnoExtendsMapper.busquedaTrabajosPendientes(inscripcionesItem, idInstitucion, fechaActual);
			if(trabajosSJCS.size()>0 || trabajosPendientes.size() >0) {
				incidencias++;
			}
		}
		if(incidencias > 0) return true;
		else return false;
}
	
	@Override
	public InscripcionesDTO checkSaltos(InscripcionesDTO inscripcionesDTO,
			HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<InscripcionesItem> inscripcionesSaltos = new ArrayList();
		for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
			
			inscripcionesSaltos.addAll(scsInscripcionturnoExtendsMapper.checkSaltos(inscripcionesItem, dateFormat.format(inscripcionesItem.getFechaActual()), null,
					idInstitucion));
			
		}
		InscripcionesDTO inscripcionesDTOReturn = new InscripcionesDTO();
		inscripcionesDTOReturn.setInscripcionesItems(inscripcionesSaltos);
		return inscripcionesDTOReturn;
}

	public UpdateResponseDTO updateBorrarSaltos(InscripcionesDTO inscripcionesDTO, HttpServletRequest request) {
		
		LOGGER.info("updateBorrarSaltos() ->  Entrada al servicio para borrar saltos y compensaciones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);


		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			int response = 0;
			Error error = new Error();
			try {
			if (null != usuarios && usuarios.size() > 0) {
				
				
				List<InscripcionesItem>inscripcionesSaltos =new ArrayList();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				int usumodificacion = usuarios.get(0).getUsumodificacion();
				for (InscripcionesItem inscripcionesItem : inscripcionesDTO.getInscripcionesItems()) {
					
					response = scsInscripcionturnoExtendsMapper.borrarSaltos(inscripcionesItem, idInstitucion, usumodificacion);
				
				}
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
				error.setDescription("No se ha borrado parte de los saltos o copmpensaciones");
				updateResponseDTO.setStatus(SigaConstants.KO);
			} else if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se ha modificado el salto o la compensacion correctamente");
			}

			updateResponseDTO.setError(error);

			LOGGER.info("updateBorrarSaltos() -> Salida del servicio para borrar saltos y compensaciones");

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
		List<InscripcionesItem> inscripciones = null;
		String busquedaOrden = "";
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaTarjetaInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaTarjetaInscripciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"busquedaTarjetaInscripciones() -> Entrada a csInscripcionturnoExtendsMapper para obtener las tarjetas de inscripciones");

				//SIGARNV-2009@DTT.JAMARTIN@06/08/2021@INICIO
				//Obtenemos todos los turnos
				inscripcionesItems = scsInscripcionturnoExtendsMapper.busquedaTarjetaInscripciones(inscripcionesItem,
						idInstitucion, usuario.getIdlenguaje());
				
				//Consultamos a que turnos esta inscrito la persona
				List<InscripcionesItem> inscripcionesBorrar = scsInscripcionturnoExtendsMapper.buscaTurnoInscrito(inscripcionesItems, idInstitucion, inscripcionesItem.getIdpersona());

				//Ahora quitamos los turnos donde esta inscrito del listado de turnos
				for(int i=0; i < inscripcionesItems.size(); i++) {
					for(int j=0; j < inscripcionesBorrar.size(); j++) {
						if(inscripcionesItems.get(i).getIdturno() == inscripcionesBorrar.get(j).getIdturno()) {
							inscripcionesItems.remove(i);
						}
					}
				}
				
//				//Sacamos las guardias de los turnos donde no esta inscrito
//				List<InscripcionesItem> guardias = scsInscripcionturnoExtendsMapper.buscarGuardiasTurnosNoInscritos(inscripcionesItems, idInstitucion, inscripcionesItem.getIdpersona());
//
//				//Ahora recorremos este listado de guardias y se las asignamos a los turnos
//				for(int k=0; k < inscripcionesItems.size(); k++) {
//					for(int l=0; l < guardias.size(); l++) {
//						if(inscripcionesItems.get(k).getIdturno().equals(guardias.get(l).getIdturno())) {
//							inscripcionesItems.get(k).setIdguardia(guardias.get(l).getIdguardia());
//							inscripcionesItems.get(k).setGuardias(guardias.get(l).getGuardias());
//							inscripcionesItems.get(k).setNombre_guardia(guardias.get(l).getNombre_guardia());
//							inscripcionesItems.get(k).setTipoguardias(guardias.get(l).getTipoguardias()); 
//						}
//					}
//				}
				//SIGARNV-2009@DTT.JAMARTIN@06/08/2021@FIN 
				
				//Se añade la asignacion de guardias para el caso en el que se editando una inscripcion
				if(inscripcionesItem.getIdturno()!= null) {
					//Sacamos las guardias de los turnos donde esta inscrito
					List<InscripcionesItem> guardiasInscritas = scsInscripcionturnoExtendsMapper.buscarGuardiasTurnosInscritos(inscripcionesItems, idInstitucion, inscripcionesItem.getIdpersona());

					//Ahora recorremos este listado de guardiasInscritas y se las asignamos a los turnos
					for(int k=0; k < inscripcionesItems.size(); k++) {
						for(int l=0; l < guardiasInscritas.size(); l++) {
							if(inscripcionesItems.get(k).getIdturno().equals(guardiasInscritas.get(l).getIdturno())) {
								inscripcionesItems.get(k).setIdguardia(guardiasInscritas.get(l).getIdguardia());
								inscripcionesItems.get(k).setGuardias(guardiasInscritas.get(l).getGuardias());
								inscripcionesItems.get(k).setNombre_guardia(guardiasInscritas.get(l).getNombre_guardia());
								inscripcionesItems.get(k).setTipoguardias(guardiasInscritas.get(l).getTipoguardias()); 
							}
						}
					}
				}
				else {
					//Sacamos las guardias de los turnos donde no esta inscrito
					List<InscripcionesItem> guardias = scsInscripcionturnoExtendsMapper.buscarGuardiasTurnosNoInscritos(inscripcionesItems, idInstitucion, inscripcionesItem.getIdpersona());

					//Ahora recorremos este listado de guardias y se las asignamos a los turnos
					for(int k=0; k < inscripcionesItems.size(); k++) {
						for(int l=0; l < guardias.size(); l++) {
							if(inscripcionesItems.get(k).getIdturno().equals(guardias.get(l).getIdturno())) {
								inscripcionesItems.get(k).setIdguardia(guardias.get(l).getIdguardia());
								inscripcionesItems.get(k).setGuardias(guardias.get(l).getGuardias());
								inscripcionesItems.get(k).setNombre_guardia(guardias.get(l).getNombre_guardia());
								inscripcionesItems.get(k).setTipoguardias(guardias.get(l).getTipoguardias()); 
							}
						}
					}
				}
					
				
				LOGGER.info(
						"busquedaTarjetaInscripciones()  -> Salida a csInscripcionturnoExtendsMapper para obtener la tarjetas de inscripciones");

				if (inscripcionesItems != null) {
					inscripcionesDTO.setInscripcionesItems(inscripcionesItems);
				}
			}

		}
		LOGGER.info("busquedaTarjetaInscripciones() -> Salida del servicio para obtener las tarjetas de inscripciones");
		return inscripcionesDTO;
	}

}
