package org.itcgae.siga.scs.services.impl.guardia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionAsistenciaItem;
import org.itcgae.siga.DTOs.scs.ColegiadoJGItem;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseDTO;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenBajastemporalesExample;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgExample;
import org.itcgae.siga.db.entities.ScsSolicitudAceptada;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsPersonajgExample.Criteria;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBajasTemporalesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoasistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiascolegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSolicitudAceptadaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoAsistenciaColegioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.AsistenciaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

	private Logger LOGGER = Logger.getLogger(AsistenciaServiceImpl.class);
	@Autowired
	private ScsGuardiascolegiadoExtendsMapper scsGuardiascolegiadoExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;
	
	@Autowired
	private ScsTipoAsistenciaColegioExtendsMapper scsTipoAsistenciaColegioExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;
	
	@Autowired
	private ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;
	
	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;
	
	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;
	
	@Autowired
	private ScsActuacionasistenciaMapper scsActuacionasistenciaMapper;
	
	@Autowired
	private ScsBajasTemporalesExtendsMapper scsBajasTemporalesExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private ScsSolicitudAceptadaExtendsMapper scsSolicitudAceptadaExtendsMapper;
	
	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;
	
	@Autowired
	private ScsEstadoasistenciaExtendsMapper scsEstadoasistenciaExtendsMapper;
	
	@Override
	public ComboDTO getTurnosByColegiadoFecha(HttpServletRequest request, String guardiaDia, String idPersona) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getGuardiasByColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getGuardiasByColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					//Si la fecha trae hora la recortamos
					if(guardiaDia.length() > 10) {
						
						guardiaDia = guardiaDia.substring(0, 11);
						
					}
					List<ComboItem> combosItems = scsGuardiascolegiadoExtendsMapper.getTurnosByColegiadoFecha(idPersona, idInstitucion, guardiaDia);
					comboDTO.setCombooItems(combosItems);
					
					if(combosItems == null
							|| combosItems.isEmpty()) {
						
						error.setCode(200);
						error.setMessage("sjcs.guardia.asistencia.nohayguardia");
						error.description("sjcs.guardia.asistencia.nohayguardia");
						comboDTO.setError(error);
						
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("getGuardiasByColegiadoFecha() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las guardias del colegiado para la fecha" + guardiaDia + ": " + e);
			error.description("Error al traer las guardias del colegiado para la fecha" + guardiaDia + ": " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public ComboDTO getTiposAsistenciaColegio(HttpServletRequest request, String idTurno, String idGuardia) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		short idTipoGuardia;
		Integer idLenguaje = 0;
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getTiposAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getTiposAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					
					example.createCriteria()
							.andIdguardiaEqualTo(Integer.valueOf(idGuardia))
							.andIdturnoEqualTo(Integer.valueOf(idTurno))
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);
					
					if(guardiasList != null
							&& !guardiasList.isEmpty()) {
						
						idTipoGuardia = guardiasList.get(0).getIdtipoguardia();
						idLenguaje = Integer.parseInt(usuarios.get(0).getIdlenguaje());
						
						List<TiposAsistenciaItem> tiposAsistenciaItems = scsTipoAsistenciaColegioExtendsMapper.getTiposAsistenciaColegiado(idInstitucion, idLenguaje, idTipoGuardia);
						
						List<ComboItem> comboItems = (List<ComboItem>) tiposAsistenciaItems.stream().map(x -> {
																													ComboItem comboItem = new ComboItem();
																													comboItem.value(x.getIdtipoasistenciacolegio() + x.getPordefecto());
																													comboItem.label(x.getTipoasistencia());
																													return comboItem;
																													
																											  }).collect(Collectors.toList());
						
						comboDTO.setCombooItems(comboItems);
						
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("getTiposAsistenciaColegio() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al obtener los tipos de asistencia: " + e);
			error.description("Error al obtener los tipos de asistencia: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public ComboDTO getColegiadosGuardiaDia(HttpServletRequest request, String idTurno, String idGuardia,
			String guardiaDia) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getColegiadosGuardiaDia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getColegiadosGuardiaDia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					//Si la fecha trae hora la recortamos
					if(guardiaDia.length() > 10) {
						
						guardiaDia = guardiaDia.substring(0, 11);
						
					}
					
					List<ComboItem> comboItems = scsGuardiascolegiadoExtendsMapper.getColegiadosGuardiaDia(idTurno, idGuardia, idInstitucion, guardiaDia);
					
					if(comboItems != null
							&& !comboItems.isEmpty()) {
						comboDTO.setCombooItems(comboItems);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("getColegiadosGuardiaDia() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al obtener los colegiados que tienen guardia: " + e);
			error.description("Error al obtener los colegiados que tienen guardia: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public TarjetaAsistenciaResponseDTO searchAsistencias(HttpServletRequest request, FiltroAsistenciaItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaAsistenciaResponseDTO tarjetaAsistenciaResponseDTO = new TarjetaAsistenciaResponseDTO();
		List<TarjetaAsistenciaResponseItem> tarjetaAsistenciaResponseItems = new ArrayList<TarjetaAsistenciaResponseItem>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					GenPropertiesExample exampleProperties = new GenPropertiesExample();
					exampleProperties.createCriteria().andFicheroEqualTo("SIGA").andParametroEqualTo("codigo.general.scs_tipoasistencia.volanteExpres");
					List<GenProperties> properties = genPropertiesMapper.selectByExample(exampleProperties);
					
					if(properties != null
							&& !properties.isEmpty()) {
						filtro.setIdTipoAsistencia(properties.get(0).getValor());
						
						List<TarjetaAsistenciaItem> tarjetaAsistenciaItems = scsAsistenciaExtendsMapper.searchAsistencias(filtro, idInstitucion);
						
						if(tarjetaAsistenciaItems != null
								&& !tarjetaAsistenciaItems.isEmpty()) {
							
							//Modificamos los datos y agrupamos las actuaciones por asistencia
							Map<String, List<TarjetaAsistenciaItem>> actuacionesPorAsistencia = tarjetaAsistenciaItems.stream().map((TarjetaAsistenciaItem tarjetaAsistenciaItem) ->{
																																														tarjetaAsistenciaItem.setAnioNumero("A"+tarjetaAsistenciaItem.getAnio()+"/"+tarjetaAsistenciaItem.getNumero());
																																														
																																														
																																														if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getNif())
																																																&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getApellido1())
																																																&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getApellido2())
																																																&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getNombre())
																																																&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getSexo())) {
																																															String descripcionSexo = "";
																																															switch(tarjetaAsistenciaItem.getSexo()) {
																																															 	case "H":
																																															 		descripcionSexo = "HOMBRE";
																																															 		break;
																																															 	case "M":
																																															 		descripcionSexo = "MUJER";
																																															 		break;
																																															 	default:
																																															 		descripcionSexo = "NO CONSTA";
																																															 		break;
																																															}
																																															tarjetaAsistenciaItem.setAsistido(tarjetaAsistenciaItem.getNif() 
																																																	+ " - " + tarjetaAsistenciaItem.getApellido1() + " "
																																																	+ tarjetaAsistenciaItem.getApellido2() + ", "
																																																	+ tarjetaAsistenciaItem.getNombre() + " - "
																																																	+ descripcionSexo);
																																														
																																														}
																																														
																																														if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getEjgAnio())
																																																&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getEjgNumero())) {
																																															tarjetaAsistenciaItem.setEjgAnioNumero("E"+tarjetaAsistenciaItem.getAnio()+"/"+tarjetaAsistenciaItem.getEjgNumero());
																																														}
																																														
																																														return tarjetaAsistenciaItem;
																																													}).collect(Collectors.groupingBy((TarjetaAsistenciaItem item) -> item.getAnioNumero()));
							
							actuacionesPorAsistencia.forEach((asistencia, actuaciones) ->{
								
								TarjetaAsistenciaResponseItem responseItem = new TarjetaAsistenciaResponseItem();
								
								List<ActuacionAsistenciaItem> actuacAsistenciaItems = new ArrayList<ActuacionAsistenciaItem>();
								actuaciones.forEach(actuacion -> {
										//Seteamos los datos de la asistencia
										responseItem.setAnio(actuacion.getAnio());
										responseItem.setNumero(actuacion.getNumero());
										responseItem.setAnioNumero(actuacion.getAnioNumero());
										responseItem.setAsistido(actuacion.getAsistido());
										responseItem.setIdDelito(actuacion.getIdDelito());
										responseItem.setObservaciones(actuacion.getObservaciones());
										responseItem.setEjgAnio(actuacion.getEjgAnio());
										responseItem.setEjgNumero(actuacion.getEjgNumero());
										responseItem.setEjgAnioNumero(actuacion.getEjgAnioNumero());
										responseItem.setNombre(actuacion.getNombre());
										responseItem.setApellido1(actuacion.getApellido1());
										responseItem.setApellido2(actuacion.getApellido2());
										responseItem.setNif(actuacion.getNif());
										responseItem.setSexo(actuacion.getSexo());
										
										//Seteamos los datos de las actuaciones de la asistencia y los añadimos a la lista
										ActuacionAsistenciaItem actuacionAsistenciaItem = new ActuacionAsistenciaItem();
										actuacionAsistenciaItem.setFechaActuacion(actuacion.getFchaActuacion());
										actuacionAsistenciaItem.setLugar(actuacion.getLugar());
										actuacionAsistenciaItem.setNumeroAsunto(actuacion.getNumeroAsunto());
										actuacionAsistenciaItem.setFechaJustificacion(actuacion.getFchaJustificacion());
										actuacionAsistenciaItem.setComisariaJuzgado(actuacion.getComisariaJuzgado());
										
										actuacAsistenciaItems.add(actuacionAsistenciaItem);
								});
								responseItem.setActuaciones(actuacAsistenciaItems);
								tarjetaAsistenciaResponseItems.add(responseItem);
							});
						}
						tarjetaAsistenciaResponseDTO.setResponseItems(tarjetaAsistenciaResponseItems);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("searchAsistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las asistencias: " + e);
			error.description("Error al buscar las asistencias: " + e);
			tarjetaAsistenciaResponseDTO.setError(error);
		}
		return tarjetaAsistenciaResponseDTO;
	}
	
	@Override
	public ComboDTO getJuzgados(HttpServletRequest request, String idTurno) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getJuzgados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					List<ComboItem> combosItems = scsJuzgadoExtendsMapper.getJuzgadosByIdTurno(idInstitucion, idTurno);
					comboDTO.setCombooItems(combosItems);
				}
			}
		}catch(Exception e) {
			LOGGER.error("getJuzgados() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer los juzgados: " + e);
			error.description("Error al traer los juzgados: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public ComboDTO getComisarias(HttpServletRequest request, String idTurno) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getComisarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getComisarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					List<ComboItem> combosItems = scsComisariaExtendsMapper.getComisariasByIdTurno(idInstitucion, idTurno);
					comboDTO.setCombooItems(combosItems);
				}
			}
		}catch(Exception e) {
			LOGGER.error("getJuzgados() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las comisarias: " + e);
			error.description("Error al traer las comisarias: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public DeleteResponseDTO guardarAsistenciasExpres(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"guardarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"guardarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					//Obtenemos el tipo de asistencia general para Asistencias Express
					GenPropertiesExample exampleProperties = new GenPropertiesExample();
					exampleProperties.createCriteria().andFicheroEqualTo("SIGA").andParametroEqualTo("codigo.general.scs_tipoasistencia.volanteExpres");
					List<GenProperties> properties = genPropertiesMapper.selectByExample(exampleProperties);
					String	tipoAsistenciaGeneral = properties.stream().findFirst().orElse(new GenProperties()).getValor();
					
					if(asistencias != null
							&& !asistencias.isEmpty()) {
						
						procesarSustitucionGuardia(asistencias.get(0).getFiltro(), idInstitucion);
						
						asistencias.forEach((TarjetaAsistenciaResponseItem asistencia) -> {
							
							//Comprobamos si existe el justiciable, si no, lo insertamos en scs_personajg y devolvemos idPersona
							String idPersona = getIdPersonaJusticiable(asistencia, idInstitucion, usuarios.get(0));
							
							if(UtilidadesString.esCadenaVacia(asistencia.getAnioNumero())) {
								//Si no viene informado el anionumero es que se trata de una nueva asistencia
								LOGGER.info("guardarAsistencias() / Nueva asistencia");
								
								//Obtenemos proximo numero de una nueva asistencia
								String anioAsistencia = asistencia.getFiltro().getDiaGuardia().split("/")[2];
								String numeroAsistencia = scsAsistenciaExtendsMapper.getNextNumeroAsistencia(anioAsistencia, idInstitucion);
								
								//Montamos bean e insertamos asistencia
								ScsAsistencia asistenciaBBDD = fromTarjetaAsistenciaItemToScsAsistencia(asistencia, anioAsistencia, numeroAsistencia, tipoAsistenciaGeneral, idPersona, idInstitucion, usuarios.get(0));
								int responseAsistencia = scsAsistenciaExtendsMapper.insertSelective(asistenciaBBDD);
								
								if(responseAsistencia == 0) {
									LOGGER.error("guardarAsistencias() / No se ha insertado la nueva asistencia");
									error.setCode(500);
									error.setMessage("Error al insertar la nueva asistencia");
									error.description("Error al insertar la nueva asistencia");
								}

								procesarDelitosAsistencia(asistencia, anioAsistencia, numeroAsistencia, idInstitucion);
								
								//Recorremos actuaciones y las insertamos
								for(int i = 0; i < asistencia.getActuaciones().size(); i++) {
									
									ScsActuacionasistencia actuacionBBDD = fromActuacionAsistenciaItemToScsActuacionasistencia(asistencia.getActuaciones().get(i), asistencia, anioAsistencia, numeroAsistencia, tipoAsistenciaGeneral, idInstitucion, true , usuarios.get(0));
									
									int responseActuacion = scsActuacionasistenciaMapper.insertSelective(actuacionBBDD);
									if(responseActuacion == 0) {
										LOGGER.error("guardarAsistencias() / No se ha insertado la nueva actuacion");
										error.setCode(500);
										error.setMessage("Error al insertar la nueva actuacion");
										error.description("Error al insertar la nueva actuacion");
									}
								}
								
							}else {
								LOGGER.info("guardarAsistencias() / Asistencia existente, actualizamos");
								//Actualizamos asistencia y actuaciones
								ScsAsistencia asistenciaBBDD = fromTarjetaAsistenciaItemToScsAsistencia(asistencia, null, null, tipoAsistenciaGeneral, idPersona, idInstitucion, usuarios.get(0));								scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(asistenciaBBDD);
								int responseAsistencia = scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(asistenciaBBDD);
								if(responseAsistencia == 0) {
									LOGGER.error("guardarAsistencias() / No se ha actualizado la asistencia");
									error.setCode(500);
									error.setMessage("Error al actualizar las asistencias");
									error.description("Error al actualizar las asistencias");
								}
								for(int i = 0; i < asistencia.getActuaciones().size(); i++) {
									//Comprobamos si es una nueva actuacion
									boolean isNuevaActuacion = isNuevaActuacion(asistencia, i+1, idInstitucion);
									
									ScsActuacionasistencia actuacionBBDD = fromActuacionAsistenciaItemToScsActuacionasistencia(asistencia.getActuaciones().get(i), asistencia, null, null, tipoAsistenciaGeneral, idInstitucion, isNuevaActuacion, usuarios.get(0));
									
									//Si es nueva la insertamos, si no updateamos
									if (isNuevaActuacion) {
										LOGGER.info("guardarAsistencias() / Nueva actuacion");
										int responseActuacion = scsActuacionasistenciaMapper.insertSelective(actuacionBBDD);
										if(responseActuacion == 0) {
											LOGGER.error("guardarAsistencias() / No se ha insertado al nueva Actuacion");
											error.setCode(500);
											error.setMessage("Error al insertar la nueva actuacion");
											error.description("Error al insertar la nueva actuacion");
										}
									}else {
										LOGGER.info("guardarAsistencias() / Actuacion existente, actualizamos");
										actuacionBBDD.setIdactuacion(Long.valueOf(i+1));
										int responseActuacion = scsAsistenciaExtendsMapper.updateAsistenciaExpress(actuacionBBDD);
										if(responseActuacion == 0) {
											LOGGER.error("guardarAsistencias() / No se han actualizado las actuaciones");
											error.setCode(500);
											error.setMessage("Error al actualizar las actuaciones");
											error.description("Error al actualizar las actuaciones");
										}
									}
								}
								
							}
							
						});
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar las asistencias: " + e);
			error.description("Error al guardar las asistencias: " + e);
			deleteResponseDTO.setError(error);
		}
		return deleteResponseDTO;
	}
	
	/**
	 * Metodo para obtener el IdPersona del justiciable introducido en la tabla de asistencias. 
	 * Si no existe, se crea y se devuelve el nuevo IdPersona del nuevo justiciable
	 * 
	 * @param asistencia
	 * @param idInstitucion
	 * @return
	 */
	private String getIdPersonaJusticiable (TarjetaAsistenciaResponseItem asistencia, Short idInstitucion, AdmUsuarios usuario){
		String idPersona = null;
		
		int response = 0;
		
		ScsPersonajgExample personajgExample = new ScsPersonajgExample();
		personajgExample.createCriteria().andNifEqualTo(asistencia.getNif().trim());

		
		List<ScsPersonajg> listaJusticiables = scsPersonajgExtendsMapper.selectByExample(personajgExample);
		
		//Si existe obtenemos el idPersona y actualizamos datos
		if(listaJusticiables != null
				&& !listaJusticiables.isEmpty()) {
			idPersona = listaJusticiables.get(0).getIdpersona().toString();
			ScsPersonajg personajgToUpdate = new ScsPersonajg();
			
			personajgToUpdate.setIdpersona(Long.valueOf(idPersona));
			personajgToUpdate.setNif(asistencia.getNif());
			personajgToUpdate.setApellido1(asistencia.getApellido1());
			personajgToUpdate.setApellido2(asistencia.getApellido2());
			personajgToUpdate.setNombre(asistencia.getNombre());
			personajgToUpdate.setSexo(asistencia.getSexo());
			personajgToUpdate.setIdinstitucion(idInstitucion);
			personajgToUpdate.setFechamodificacion(new Date());
			personajgToUpdate.setUsumodificacion(usuario.getIdusuario());
			
			scsPersonajgExtendsMapper.updateByPrimaryKeySelective(personajgToUpdate);
		//Si no, lo creamos y nos quedamos con su nuevo idPersona
		}else {
			String newIdPersona = scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion).getNewId();
			ScsPersonajg newPersonajg = new ScsPersonajg();
			
			newPersonajg.setIdpersona(Long.valueOf(newIdPersona)+1);			
			newPersonajg.setNif(asistencia.getNif());
			newPersonajg.setApellido1(asistencia.getApellido1());
			newPersonajg.setApellido2(asistencia.getApellido2());
			newPersonajg.setNombre(asistencia.getNombre());
			newPersonajg.setSexo(asistencia.getSexo());
			newPersonajg.setIdinstitucion(idInstitucion);
			newPersonajg.setFechamodificacion(new Date());
			newPersonajg.setUsumodificacion(usuario.getIdusuario());
			
			response = scsPersonajgExtendsMapper.insertSelective(newPersonajg);
			
			//Todo OK, devolvemos el idpersona del nuevo justiciable creado
			if(response == 1) {
				idPersona = newPersonajg.getIdpersona().toString();
			}
			
		}
		return idPersona;
	}
	
	/**
	 * 
	 * Metodo que convierte un TarjetaAsistenciaResponseItem en un ScsAsistencia para insertarlo/actualizar BBDD
	 * 
	 * @param asistencia
	 * @param anioAsistencia
	 * @param numeroAsistencia
	 * @param tipoAsistenciaGeneral
	 * @param idPersonaJg
	 * @param idInstitucion
	 * @return
	 */
	private ScsAsistencia fromTarjetaAsistenciaItemToScsAsistencia(TarjetaAsistenciaResponseItem asistencia, 
			String anioAsistencia, 
			String numeroAsistencia,
			String tipoAsistenciaGeneral,
			String idPersonaJg,
			Short idInstitucion,
			AdmUsuarios usuario) {
		ScsAsistencia asistenciaBBDD = new ScsAsistencia();
		try {
			if(!UtilidadesString.esCadenaVacia(anioAsistencia)) {
				asistenciaBBDD.setAnio(Short.valueOf(anioAsistencia));
			}else if (!UtilidadesString.esCadenaVacia(asistencia.getAnio())) {
				asistenciaBBDD.setAnio(Short.valueOf(asistencia.getAnio()));
			}
			
			if(!UtilidadesString.esCadenaVacia(numeroAsistencia)) {
				asistenciaBBDD.setNumero(Long.valueOf(numeroAsistencia));
			}else if(!UtilidadesString.esCadenaVacia(asistencia.getNumero())) {
				asistenciaBBDD.setNumero(Long.valueOf(asistencia.getNumero()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFiltro().getDiaGuardia())) {
				asistenciaBBDD.setFechahora(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFiltro().getDiaGuardia()));
			} else {
				asistenciaBBDD.setFechahora(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaAsistencia()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFechaCierre())){
				asistenciaBBDD.setFechacierre(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaCierre()));
			}
			if(!UtilidadesString.esCadenaVacia(asistencia.getFechaSolicitud())) {
				asistenciaBBDD.setFechasolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaSolicitud()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFechaEstado())) {
				asistenciaBBDD.setFechaestadoasistencia(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaEstado()));
			}

			if(!UtilidadesString.esCadenaVacia(idPersonaJg)) {
				asistenciaBBDD.setIdpersonajg(Long.valueOf(idPersonaJg));
			}
			asistenciaBBDD.setIdinstitucion(idInstitucion);
			if(!UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTurno())) {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getFiltro().getIdTurno()));
			} else {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getIdTurno()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdGuardia())) {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getFiltro().getIdGuardia()));
			} else {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getIdGuardia()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdLetradoGuardia())) {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getFiltro().getIdLetradoGuardia()));
			} else {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getIdLetradoGuardia()));
			}
			
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getObservaciones())) {
				asistenciaBBDD.setObservaciones(asistencia.getObservaciones());
			}
			if(!UtilidadesString.esCadenaVacia(tipoAsistenciaGeneral)) {
				asistenciaBBDD.setIdtipoasistencia(Short.valueOf(tipoAsistenciaGeneral));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTipoAsistenciaColegiado())) {
				asistenciaBBDD.setIdtipoasistenciacolegio(Short.valueOf(asistencia.getFiltro().getIdTipoAsistenciaColegiado()));
			}else if(!UtilidadesString.esCadenaVacia(asistencia.getIdTipoAsistenciaColegio())) {
				asistenciaBBDD.setIdtipoasistencia(Short.valueOf(asistencia.getIdTipoAsistenciaColegio()));
				asistenciaBBDD.setIdtipoasistenciacolegio(Short.valueOf(asistencia.getIdTipoAsistenciaColegio()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getEstado())) {
				asistenciaBBDD.setIdestadoasistencia(Short.valueOf(asistencia.getEstado()));
			}
			
			if(!UtilidadesString.esCadenaVacia(asistencia.getIdSolicitudCentralita())) {
				asistenciaBBDD.setIdsolicitudcentralita(Integer.valueOf(asistencia.getIdSolicitudCentralita()));
			}
			
			asistenciaBBDD.setFechamodificacion(new Date());
			asistenciaBBDD.setUsumodificacion(usuario.getIdusuario());
		} catch (ParseException e) {
			LOGGER.error("guardarAsistencias() / ERROR AL PARSEAR FECHAS: "+ e.getMessage(), e);
		} catch(Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR GENERICO: "+ e.getMessage(), e);
			throw e;
		}
		
		return asistenciaBBDD;
	}
	
	/**
	 * 
	 * Metodo que convierte el ActuacionAsistenciaItem en el bean de BBDD ScsActuacionasistencia
	 * 
	 * @param actuacion
	 * @param anioAsistencia
	 * @param numeroAsistencia
	 * @param tipoAsistenciaGeneral
	 * @param idInstitucion
	 * @return
	 * @throws ParseException 
	 */
	private ScsActuacionasistencia fromActuacionAsistenciaItemToScsActuacionasistencia (ActuacionAsistenciaItem actuacion,
			TarjetaAsistenciaResponseItem asistencia,
			String anioAsistencia, 
			String numeroAsistencia,
			String tipoAsistenciaGeneral,
			Short idInstitucion,
			boolean newActuacion,
			AdmUsuarios usuario) {
		
		ScsActuacionasistencia actuacionBBDD = new ScsActuacionasistencia();
		try {
			
			
			if(!UtilidadesString.esCadenaVacia(anioAsistencia)) {
				actuacionBBDD.setAnio(Short.valueOf(anioAsistencia));
			}else {
				actuacionBBDD.setAnio(Short.valueOf(asistencia.getAnio()));
			}
			
			if(!UtilidadesString.esCadenaVacia(numeroAsistencia)) {
				actuacionBBDD.setNumero(Long.valueOf(numeroAsistencia));
			}else {
				actuacionBBDD.setNumero(Long.valueOf(asistencia.getNumero()));
			}
			actuacionBBDD.setIdinstitucion(idInstitucion);
			actuacionBBDD.setIdtipoasistencia(Short.valueOf(tipoAsistenciaGeneral));
			actuacionBBDD.setFechajustificacion(new SimpleDateFormat("dd/MM/yyyy").parse(actuacion.getFechaJustificacion()));
			if(!UtilidadesString.esCadenaVacia(actuacion.getFechaActuacion())) {
				actuacionBBDD.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(actuacion.getFechaActuacion()));
			}
			if(!UtilidadesString.esCadenaVacia(actuacion.getNumeroAsunto())) {
				actuacionBBDD.setNumeroasunto(actuacion.getNumeroAsunto());
			}
			
			//Si hemos rellenado el lugar (Id comisaria o id juzgado)
			if(!UtilidadesString.esCadenaVacia(actuacion.getLugar())) {
				if("C".equals(actuacion.getComisariaJuzgado())) {
					actuacionBBDD.setIdinstitucionComis(idInstitucion);
					actuacionBBDD.setIdcomisaria(Long.valueOf(actuacion.getLugar()));
				}else {
					actuacionBBDD.setIdinstitucionJuzg(idInstitucion);
					actuacionBBDD.setIdjuzgado(Long.valueOf(actuacion.getLugar()));
				}
			}

			actuacionBBDD.setFechamodificacion(new Date());
			actuacionBBDD.setUsumodificacion(usuario.getIdusuario());
			
			if(newActuacion) {
				getMaxIdActuacionAsistencia(actuacionBBDD);
				actuacionBBDD.setUsucreacion(usuario.getIdusuario());
				actuacionBBDD.setFechacreacion(new Date());
				actuacionBBDD.setAcuerdoextrajudicial((short)0);
				actuacionBBDD.setIdtipoactuacion((short)1);
			}
			
		} catch (ParseException e) {
			LOGGER.error("guardarAsistencias() / ERROR AL PARSEAR FECHAS: "+ e.getMessage(), e);
		}  catch(Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR GENERICO: "+ e.getMessage(), e);
			throw e;
		}
		
		return actuacionBBDD;
	}
	
	
	/**
	 * Metodo que obtiene y setea el siguiente IdActuacion
	 * 
	 * @param actuacionBBDD
	 */
	private void getMaxIdActuacionAsistencia (ScsActuacionasistencia actuacionBBDD) {
		
		ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
		scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(actuacionBBDD.getAnio())
														.andNumeroEqualTo(actuacionBBDD.getNumero())
														.andIdinstitucionEqualTo(actuacionBBDD.getIdinstitucion());
		scsActuacionasistenciaExample.setOrderByClause("IDACTUACION");
		
		List<ScsActuacionasistencia> listaActuaciones = scsActuacionasistenciaMapper.selectByExample(scsActuacionasistenciaExample);
		
		if(listaActuaciones != null
				&& !listaActuaciones.isEmpty()) {
			
			actuacionBBDD.setIdactuacion(listaActuaciones.get(listaActuaciones.size() - 1).getIdactuacion() + 1);
			
		}else {
			actuacionBBDD.setIdactuacion(Long.valueOf(1));
		}
	}
	
	
	/**
	 * 
	 * Metodo que averigua si se trata de una nueva actuacion a traves del idActuacion, si no existe un registro con su id será una nueva actuacion
	 * 
	 * @param asistencia
	 * @param idActuacion
	 * @param idInstitucion
	 * @return
	 */
	private boolean isNuevaActuacion (TarjetaAsistenciaResponseItem asistencia, int idActuacion, Short idInstitucion) {
		boolean isNuevaActuacion = false;
		
		ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
		scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
														.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()))
														.andIdinstitucionEqualTo(idInstitucion)
														.andIdactuacionEqualTo(Long.valueOf(idActuacion));
		
		List<ScsActuacionasistencia> listaActuaciones = scsActuacionasistenciaMapper.selectByExample(scsActuacionasistenciaExample);
		
		isNuevaActuacion = (listaActuaciones == null || listaActuaciones.isEmpty());
		
		return isNuevaActuacion;
	}
	
	
	/**
	 * Metodo que se encarga de aniadir el letrado de la asistencia como refuerzo en la guardia o de sustituirlo
	 * 
	 * 
	 * @param filtro
	 * @throws Exception 
	 */
	private void procesarSustitucionGuardia(FiltroAsistenciaItem filtro, Short idInstitucion) throws Exception {
		
			
		if(filtro.isSustituto()) {
			//Sustituimos el que está de guardia por el de la asistencia
			LOGGER.info("procesarSustitucionGuardia() / Pendiente de implementar, se sustituye letrado de guardia");
			
		}else {
				
			LOGGER.info("procesarSustitucionGuardia() / Pendiente de implementar, se añade el letrado de la asistencia como refuerzo en la guardia");
			
		}
	}

	@Override
	public TarjetaAsistenciaResponseDTO searchAsistenciasByIdSolicitud(HttpServletRequest request, String idSolicitud) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaAsistenciaResponseDTO tarjetaAsistenciaResponseDTO = new TarjetaAsistenciaResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"searchAsistenciasByIdSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"searchAsistenciasByIdSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
					scsAsistenciaExample.createCriteria().andIdsolicitudcentralitaEqualTo(Integer.valueOf(idSolicitud))
														 .andIdinstitucionEqualTo(idInstitucion);
					List<ScsAsistencia> listaAsistencias = scsAsistenciaExtendsMapper.selectByExample(scsAsistenciaExample);
					if(listaAsistencias != null
							&& !listaAsistencias.isEmpty()) {
						
						List<TarjetaAsistenciaResponseItem> listaAsistenciasResponse = listaAsistencias.stream().map(asistencia ->{
							TarjetaAsistenciaResponseItem asistenciaResponse = new TarjetaAsistenciaResponseItem();
							asistenciaResponse.setAnioNumero(asistencia.getAnio() + "/"+ asistencia.getNumero());
							asistenciaResponse.setFechaGuardia(new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechahora()));
							return asistenciaResponse;
						}).collect(Collectors.toList());
						
						for(int i = 0; i < listaAsistenciasResponse.size() ; i++) {
							
							TarjetaAsistenciaResponseItem asistenciaResponse = listaAsistenciasResponse.get(i);
							ScsAsistencia asistenciaBBDD = listaAsistencias.get(i);
							
							ScsGuardiasturnoExample scsGuardiasturnoExample = new ScsGuardiasturnoExample();
							scsGuardiasturnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(asistenciaBBDD.getIdturno()).andIdguardiaEqualTo(asistenciaBBDD.getIdguardia());
							
							List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper.selectByExample(scsGuardiasturnoExample);
							
							if(guardias !=null
									&& !guardias.isEmpty()) {
								asistenciaResponse.setDescripcionGuardia(guardias.get(0).getNombre());
							}
							
							FichaPersonaItem colegiado =  cenPersonaExtendsMapper.getColegiadoByIdPersona(String.valueOf(asistenciaBBDD.getIdpersonacolegiado()),idInstitucion);
							
							if(colegiado != null) {
								asistenciaResponse.setNombreColegiado(colegiado.getApellido1() + " " + colegiado.getApellido2() + " " + colegiado.getNombre());
								asistenciaResponse.setNumeroColegiado(colegiado.getNumeroColegiado());
							}
							
							if(asistenciaBBDD.getIdpersonajg() != null) {
								ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
								scsPersonajgExample.createCriteria().andIdpersonaEqualTo(asistenciaBBDD.getIdpersonajg()).andIdinstitucionEqualTo(idInstitucion);
								List<ScsPersonajg> justiciables = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);
								
								if(justiciables != null
										&& !justiciables.isEmpty()) {
									
									if(!UtilidadesString.esCadenaVacia(justiciables.get(0).getApellido2())) {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() +" "+justiciables.get(0).getApellido2()+" "+ justiciables.get(0).getNombre());
									}else {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() +" "+ justiciables.get(0).getNombre());
									}								
								}
							}
							
						}
						
						tarjetaAsistenciaResponseDTO.setResponseItems(listaAsistenciasResponse);
				
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("searchAsistenciasByIdSolicitud() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las asistencias asociadas a la solicitud: " + e);
			error.description("Error al traer las asistencias asociadas a la solicitud: " + e);
			tarjetaAsistenciaResponseDTO.setError(error);
		}
		return tarjetaAsistenciaResponseDTO;
	}

	@Override
	public StringDTO getDefaultTipoAsistenciaColegio(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO idTipoAsistencia = new StringDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"getDefaultTipoAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"getDefaultTipoAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					List<Short> instituciones = new ArrayList<Short>();
					instituciones.add(idInstitucion);
					instituciones.add((short)0);
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria()
					.andFechaBajaIsNull()
					.andModuloEqualTo("SCS")
					.andParametroEqualTo("CV_TIPO_ASISTENCIAALDETENIDO")
					.andIdinstitucionIn(instituciones);
					
					List<GenParametros> parametros = genParametrosExtendsMapper.selectByExample(genParametrosExample);
					
					if(parametros != null
							&& !parametros.isEmpty()) {
						
						idTipoAsistencia.setValor(parametros.get(0).getValor());						
					}
				
				}
			}
		}catch(Exception e) {
			LOGGER.error("getDefaultTipoAsistenciaColegio() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar el tipo de asistencia por defecto " + e);
			error.description("Error al buscar el tipo de asistencia por defecto " + e);
		}
		return idTipoAsistencia;
	}

	@Override
	public InsertResponseDTO guardarAsistencia(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if(usuarios != null
						&& !usuarios.isEmpty()) {
					
					for (int i = 0; i < asistencias.size(); i++) {
						
						TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem = asistencias.get(i);
						
						if(UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getAnio())
								&& UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getNumero())) {
							
							//Validamos guardia
							
							//Insertamos asistencia
							ScsAsistencia asistencia = fromTarjetaAsistenciaItemToScsAsistencia(asistencias.get(i), null, null, null, null, idInstitucion, usuarios.get(0));
							//Obtenemos proximo numero de una nueva asistencia
							String anioAsistencia = tarjetaAsistenciaResponseItem.getFechaAsistencia().substring(0, 11).split("/")[2].trim();
							String numeroAsistencia = scsAsistenciaExtendsMapper.getNextNumeroAsistencia(anioAsistencia, idInstitucion);
							
							asistencia.setNumero(Long.valueOf(numeroAsistencia));
							asistencia.setAnio(Short.valueOf(anioAsistencia));
							
							int inserted = scsAsistenciaExtendsMapper.insertSelective(asistencia);
							
							//Si viene de una preasistencia, la pasamos a confirmada
							if(inserted > 0) {
								
									insertResponse.setId(asistencia.getAnio() +"/"+asistencia.getNumero());

									if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getIdSolicitudCentralita())) {
								
										inserted = 0;
										ScsSolicitudAceptada scsSolicitudAceptada = new ScsSolicitudAceptada();
										scsSolicitudAceptada.setIdsolicitud(Integer.valueOf(asistencia.getIdsolicitudcentralita()));
										scsSolicitudAceptada.setEstado(Short.valueOf("1")); //Estado confirmada
										scsSolicitudAceptada.setUsumodificacion(usuarios.get(0).getIdusuario());
										scsSolicitudAceptada.setFechamodificacion(new Date());
										
										inserted += scsSolicitudAceptadaExtendsMapper.updateByPrimaryKeySelective(scsSolicitudAceptada);
									}
									
							}
						} else {
							//Actualizamos los datos de la asistencia
						}
						
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("guardarAsistencia() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar la asistencia: " + e);
			error.description("Error al guardar la asistencia: " + e);
			insertResponse.setError(error);
		}
		return insertResponse;
	}

	@Override
	public UpdateResponseDTO updateEstadoAsistencia(HttpServletRequest request, List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					for (int i = 0; i < asistencias.size(); i++) {

						TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem = asistencias.get(i);

						if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getAnio())
								&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getNumero())) {
							int rowsUpdated = 0;
							ScsAsistencia scsAsistencia = new ScsAsistencia();
							scsAsistencia.setFechaestadoasistencia(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaEstado()));
							scsAsistencia.setIdestadoasistencia(Short.valueOf(tarjetaAsistenciaResponseItem.getEstado()));
							scsAsistencia.setAnio(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()));
							scsAsistencia.setNumero(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()));
							scsAsistencia.setIdinstitucion(idInstitucion);

							rowsUpdated = scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(scsAsistencia);

							//Si es una anulacion, anulamos sus actuaciones asociadas
							if("2".equals(tarjetaAsistenciaResponseItem.getEstado())){

								ScsActuacionasistencia scsActuacionasistencia = new ScsActuacionasistencia();
								scsActuacionasistencia.setAnulacion((short)1);

								ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
								scsActuacionasistenciaExample.createCriteria()
										.andIdinstitucionEqualTo(idInstitucion)
										.andAnioEqualTo(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()))
										.andNumeroEqualTo(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()));

								rowsUpdated += scsActuacionasistenciaMapper.updateByExampleSelective(scsActuacionasistencia,scsActuacionasistenciaExample);

							}

							if(rowsUpdated > 0) {
								updateResponse.setStatus(SigaConstants.OK);
								updateResponse.setId(tarjetaAsistenciaResponseItem.getAnioNumero());
							} else {

								updateResponse.setStatus(SigaConstants.KO);
								error.setCode(500);
								error.setMessage("No se ha actualizado ningun registro");
								error.description("No se ha actualizado ningun registro");
								updateResponse.setError(error);

							}

						}else {
							updateResponse.setStatus(SigaConstants.KO);
							error.setCode(500);
							error.setMessage("El año o el numero no van informados");
							error.description("El año o el numero no van informados");
							updateResponse.setError(error);
						}
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("guardarAsistencia() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar la asistencia: " + e);
			error.description("Error al guardar la asistencia: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	@Override
	public TarjetaAsistenciaResponseDTO buscarTarjetaAsistencias(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaAsistenciaResponseDTO tarjetaAsistenciaResponseDTO = new TarjetaAsistenciaResponseDTO();
		String anio = anioNumero.split("/")[0];
		String numero = anioNumero.split("/")[1];
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"buscarTarjetaAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"buscarTarjetaAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
					scsAsistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(anio)).andNumeroEqualTo(Long.valueOf(numero)).andIdinstitucionEqualTo(idInstitucion);
					List<ScsAsistencia> listaAsistencias = scsAsistenciaExtendsMapper.selectByExample(scsAsistenciaExample);
					if(listaAsistencias != null
							&& !listaAsistencias.isEmpty()) {
						
						List<TarjetaAsistenciaResponseItem> listaAsistenciasResponse = listaAsistencias.stream().map(asistencia ->{
							TarjetaAsistenciaResponseItem asistenciaResponse = new TarjetaAsistenciaResponseItem();
							asistenciaResponse.setAnioNumero(asistencia.getAnio() + "/"+ asistencia.getNumero());
							asistenciaResponse.setFechaAsistencia(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(asistencia.getFechahora()));
							asistenciaResponse.setIdGuardia(String.valueOf(asistencia.getIdguardia()));
							asistenciaResponse.setIdTurno(String.valueOf(asistencia.getIdturno()));
							asistenciaResponse.setIdLetradoGuardia(String.valueOf(asistencia.getIdpersonacolegiado()));
							asistenciaResponse.setAnio(String.valueOf(asistencia.getAnio()));
							asistenciaResponse.setNumero(String.valueOf(asistencia.getNumero()));
							asistenciaResponse.setEstado(String.valueOf(asistencia.getIdestadoasistencia()));
							asistenciaResponse.setFechaCierre(new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechacierre()));
							asistenciaResponse.setFechaSolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(asistencia.getFechasolicitud()));
							asistenciaResponse.setFechaEstado(new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechaestadoasistencia()));
							asistenciaResponse.setIdTipoAsistenciaColegio(String.valueOf(asistencia.getIdtipoasistenciacolegio()));
							return asistenciaResponse;
						}).collect(Collectors.toList());
						
						for(int i = 0; i < listaAsistenciasResponse.size() ; i++) {
							
							TarjetaAsistenciaResponseItem asistenciaResponse = listaAsistenciasResponse.get(i);
							ScsAsistencia asistenciaBBDD = listaAsistencias.get(i);
							
							ScsGuardiasturnoExample scsGuardiasturnoExample = new ScsGuardiasturnoExample();
							scsGuardiasturnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(asistenciaBBDD.getIdturno()).andIdguardiaEqualTo(asistenciaBBDD.getIdguardia());
							
							List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper.selectByExample(scsGuardiasturnoExample);
							
							if(guardias !=null
									&& !guardias.isEmpty()) {
								asistenciaResponse.setDescripcionGuardia(guardias.get(0).getNombre());
							}
							
							ScsTurnoExample scsTurnoExample = new ScsTurnoExample();
							scsTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(asistenciaBBDD.getIdturno());
							
							List<ScsTurno> turnos = scsTurnosExtendsMapper.selectByExample(scsTurnoExample);
							
							if(turnos != null
									&& !turnos.isEmpty()) {
								asistenciaResponse.setDescripcionTurno(turnos.get(0).getNombre());
							}
							
							List<ComboItem> estadosAsistencia = scsEstadoasistenciaExtendsMapper.comboEstadosAsistencia(usuarios.get(0).getIdlenguaje());
							
							String descripcionEstado = estadosAsistencia.stream()
									.filter( estado -> estado.getValue().equals(asistenciaBBDD.getIdestadoasistencia().toString()))
									.findFirst().get().getLabel();
							
							asistenciaResponse.setDescripcionEstado(descripcionEstado);
							
							List<TiposAsistenciaItem> tiposAsistenciaColegio = scsTipoAsistenciaColegioExtendsMapper.getTiposAsistenciaColegiado(idInstitucion, Integer.valueOf(usuarios.get(0).getIdlenguaje()), guardias.get(0).getIdtipoguardia());
							
							String descripcionTipoAsistencia = tiposAsistenciaColegio.stream()
									.filter(tipoAsistencia -> tipoAsistencia.getIdtipoasistenciacolegio().equals(asistenciaBBDD.getIdtipoasistenciacolegio().toString()))
									.findFirst().get().getTipoasistencia();
							
							asistenciaResponse.setDescripcionTipoAsistenciaColegio(descripcionTipoAsistencia);
							
							FichaPersonaItem colegiado =  cenPersonaExtendsMapper.getColegiadoByIdPersona(String.valueOf(asistenciaBBDD.getIdpersonacolegiado()),idInstitucion);
							
							if(colegiado != null) {
								asistenciaResponse.setNombreColegiado(colegiado.getApellido1() + " " + colegiado.getApellido2() + " " + colegiado.getNombre());
								asistenciaResponse.setNumeroColegiado(colegiado.getNumeroColegiado());
							}
							
							if(asistenciaBBDD.getIdpersonajg() != null) {
								ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
								scsPersonajgExample.createCriteria().andIdpersonaEqualTo(asistenciaBBDD.getIdpersonajg()).andIdinstitucionEqualTo(idInstitucion);
								List<ScsPersonajg> justiciables = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);
								
								if(justiciables != null
										&& !justiciables.isEmpty()) {
									
									if(!UtilidadesString.esCadenaVacia(justiciables.get(0).getApellido2())) {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() +" "+justiciables.get(0).getApellido2()+" "+ justiciables.get(0).getNombre());
									}else {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() +" "+ justiciables.get(0).getNombre());
									}								
								}
							}
							
							ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
							scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(anio)).andNumeroEqualTo(Long.valueOf(numero)).andIdinstitucionEqualTo(idInstitucion);
							
							List<ScsActuacionasistencia> actuaciones = scsActuacionasistenciaMapper.selectByExample(scsActuacionasistenciaExample);
							
							if(actuaciones != null
									&& !actuaciones.isEmpty()) {
								
								asistenciaResponse.setNumeroActuaciones(String.valueOf(actuaciones.size()));
								boolean validadas = actuaciones.stream().allMatch(actuacion -> actuacion.getValidada().equals("1"));				
								if(validadas) {
									asistenciaResponse.setValidada("SI");
								}else {
									asistenciaResponse.setValidada("NO");
								}
							}else {
								asistenciaResponse.setValidada("NO");
								asistenciaResponse.setNumeroActuaciones("0");
							}
							
						}
						
						tarjetaAsistenciaResponseDTO.setResponseItems(listaAsistenciasResponse);
				
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("buscarTarjetaAsistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las asistencias asociadas a la solicitud: " + e);
			error.description("Error al traer las asistencias asociadas a la solicitud: " + e);
			tarjetaAsistenciaResponseDTO.setError(error);
		}
		return tarjetaAsistenciaResponseDTO;
	}
	
	private void procesarDelitosAsistencia (TarjetaAsistenciaResponseItem tarjetaAsistenciaItem, String anio, String numero, Short idInstitucion){



	}

}
