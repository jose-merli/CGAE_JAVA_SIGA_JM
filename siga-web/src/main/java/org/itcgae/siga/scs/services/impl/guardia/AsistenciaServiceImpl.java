package org.itcgae.siga.scs.services.impl.guardia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.entities.ScsPersonajgExample.Criteria;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
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
	private ScsCalendarioguardiasMapper scsCalendarioguardiasMapper;
	
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

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private ScsDelitosasistenciaMapper scsDelitosasistenciaMapper;

	@Autowired
	private ScsCabeceraguardiasExtendsMapper scsCabeceraguardiasExtendsMapper;

	@Autowired
	private ScsContrariosasistenciaMapper scsContrariosasistenciaMapper;

	@Autowired
	private ScsCaractasistenciaMapper scsCaractasistenciaMapper;

	@Autowired
	private ScsDefendidosdesignasExtendsMapper scsDefendidosdesignasExtendsMapper;

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
										if(actuacion.getIdDelito() != null
                                            && actuacion.getIdDelito().equals(actuaciones.get(0).getIdDelito())){
                                            actuacAsistenciaItems.add(actuacionAsistenciaItem);
                                        }
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

								procesarDelitosAsistenciaExpress(asistencia, anioAsistencia, numeroAsistencia, idInstitucion, usuarios.get(0));
								
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
								procesarDelitosAsistenciaExpress(asistencia,null, null, idInstitucion, usuarios.get(0));
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

							//Insertamos asistencia
							ScsAsistencia asistencia = fromTarjetaAsistenciaItemToScsAsistencia(asistencias.get(i), null, null, null, null, idInstitucion, usuarios.get(0));
							//Obtenemos proximo numero de una nueva asistencia
							String anioAsistencia = tarjetaAsistenciaResponseItem.getFechaAsistencia().substring(0, 11).split("/")[2].trim();
							String numeroAsistencia = scsAsistenciaExtendsMapper.getNextNumeroAsistencia(anioAsistencia, idInstitucion);
							
							asistencia.setNumero(Long.valueOf(numeroAsistencia));
							asistencia.setAnio(Short.valueOf(anioAsistencia));

							//Validamos guardias colegiado
							procesaGuardiasColegiado(tarjetaAsistenciaResponseItem, idInstitucion);
							
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
							//Actualizamos
							int affectedRows = 0;
							ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
							scsAsistenciaKey.setIdinstitucion(idInstitucion);
							scsAsistenciaKey.setAnio(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()));
							scsAsistenciaKey.setNumero(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()));
							ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

							scsAsistencia.setIdpersonacolegiado(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
							if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getIdTipoAsistenciaColegio())){
								scsAsistencia.setIdtipoasistenciacolegio(Short.valueOf(tarjetaAsistenciaResponseItem.getIdTipoAsistenciaColegio()));
							} else {
								scsAsistencia.setIdtipoasistenciacolegio(null);
							}

							if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getFechaCierre())){
								scsAsistencia.setFechacierre(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaCierre()));
							}else{
								scsAsistencia.setFechacierre(null);
							}

							if(!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getFechaSolicitud())){
								scsAsistencia.setFechasolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(tarjetaAsistenciaResponseItem.getFechaSolicitud()));
							}else{
								scsAsistencia.setFechasolicitud(null);
							}
							affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

							if(affectedRows > 0 ){
								insertResponse.setId(tarjetaAsistenciaResponseItem.getAnioNumero());
								insertResponse.setStatus(SigaConstants.OK);
							}else{
								insertResponse.setStatus(SigaConstants.KO);
								error.setCode(500);
								error.setDescription("No se actualizó ninguna fila al intentar actualizar la asistencia");
								insertResponse.setError(error);
							}

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


	/**
	 * Método que recoge toda la información necesaria para mostrarla en la ficha de asistencia
	 *
	 * @param request
	 * @param anioNumero
	 * @return
	 */
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
							asistenciaResponse.setNig(asistencia.getNig());
							asistenciaResponse.setNumDiligencia(asistencia.getNumerodiligencia());
							asistenciaResponse.setNumProcedimiento(asistencia.getNumeroprocedimiento());
							if(asistencia.getIdpretension() != null){
								asistenciaResponse.setIdProcedimiento(String.valueOf(asistencia.getIdpretension()));
							}
							if(asistencia.getComisaria() != null) {
								asistenciaResponse.setComisaria(String.valueOf(asistencia.getComisaria()));
							}
							if(asistencia.getJuzgado() != null) {
								asistenciaResponse.setJuzgado(String.valueOf(asistencia.getJuzgado()));
							}
							if(asistencia.getIdpersonajg() != null){
								asistenciaResponse.setIdPersonaJg(String.valueOf(asistencia.getIdpersonajg()));
							}
							if(asistencia.getFechacierre() != null) {
								asistenciaResponse.setFechaCierre(new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechacierre()));
							}
							if(asistencia.getFechasolicitud() != null) {
								asistenciaResponse.setFechaSolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(asistencia.getFechasolicitud()));
							}
							if(asistencia.getFechaestadoasistencia() != null) {
								asistenciaResponse.setFechaEstado(new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechaestadoasistencia()));
							}
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

									asistenciaResponse.setNif(justiciables.get(0).getNif());
									asistenciaResponse.setNombre(justiciables.get(0).getNombre());
									asistenciaResponse.setApellido1(justiciables.get(0).getApellido1());
									asistenciaResponse.setApellido2(justiciables.get(0).getApellido2());

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

							List<ListaContrarioJusticiableItem> contrarios = scsAsistenciaExtendsMapper.searchListaContrarios(anioNumero, idInstitucion, false);
							if(contrarios != null && !contrarios.isEmpty()){
								asistenciaResponse.setNumContrarios(String.valueOf(contrarios.size()));
								asistenciaResponse.setPrimerContrario(contrarios.get(0));
							}

							List<RelacionesItem> relaciones = scsAsistenciaExtendsMapper.searchRelaciones(anio,numero,idInstitucion,Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(),1);
							if(relaciones != null
                                && !relaciones.isEmpty()){
							    if(!UtilidadesString.esCadenaVacia(relaciones.get(0).getFechaDictamen())){
							        relaciones.get(0).setFechaDictamen(relaciones.get(0).getFechaDictamen());
                                }
                                if(!UtilidadesString.esCadenaVacia(relaciones.get(0).getFechaResolucion())){
                                    relaciones.get(0).setFechaResolucion(relaciones.get(0).getFechaResolucion());
                                }
							    asistenciaResponse.setPrimeraRelacion(relaciones.get(0));
                            }
							ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
							scsDelitosasistenciaExample.createCriteria()
									.andNumeroEqualTo(asistenciaBBDD.getNumero())
									.andAnioEqualTo(asistenciaBBDD.getAnio())
									.andIdinstitucionEqualTo(asistenciaBBDD.getIdinstitucion());
							List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper.selectByExample(scsDelitosasistenciaExample);
							if(delitos != null
								&& !delitos.isEmpty()){
								List<String> idDelitos = new ArrayList<>();
								delitos.forEach( delito ->{
									idDelitos.add(String.valueOf(delito.getIddelito()));
								});
								asistenciaResponse.setDelitos(idDelitos);
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

	/**
	 *
	 * Método que procesa los delitos seleccionados en la tabla de Asistencias Expres para insertarlos en SCS_DELITOSASISTENCIA
	 *
	 * @param tarjetaAsistenciaItem
	 * @param anioParam
	 * @param numeroParam
	 * @param idInstitucion
	 * @param usuario
	 */
	
	private void procesarDelitosAsistenciaExpress (TarjetaAsistenciaResponseItem tarjetaAsistenciaItem, String anioParam, String numeroParam, Short idInstitucion, AdmUsuarios usuario){

		String anio = "";
		String numero = "";

		if(UtilidadesString.esCadenaVacia(anioParam)
		&& UtilidadesString.esCadenaVacia(numeroParam)){
			anio = tarjetaAsistenciaItem.getAnio();
			numero = tarjetaAsistenciaItem.getNumero();
		} else {
			anio = anioParam;
			numero = numeroParam;
		}
		//Borramos los asociados e insertamos el nuevo
		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(Short.valueOf(anio))
				.andNumeroEqualTo(Long.valueOf(numero));

		scsDelitosasistenciaMapper.deleteByExample(scsDelitosasistenciaExample);

		ScsDelitosasistencia scsDelitosasistencia = new ScsDelitosasistencia();
		int affectedRows = 0 ;
		scsDelitosasistencia.setAnio(Short.valueOf(anio));
		scsDelitosasistencia.setNumero(Long.valueOf(numero));
		scsDelitosasistencia.setIdinstitucion(idInstitucion);
		scsDelitosasistencia.setIddelito(Short.valueOf(tarjetaAsistenciaItem.getIdDelito()));
		scsDelitosasistencia.setFechamodificacion(new Date());
		scsDelitosasistencia.setUsumodificacion(usuario.getIdusuario());
		affectedRows = scsDelitosasistenciaMapper.insertSelective(scsDelitosasistencia);

	}

	/**
	 * Método que procesa las guardias de colegiado y las cabeceras de guardia al crear una asistencia
	 *
	 *
	 * @param tarjetaAsistenciaResponseItem
	 */
	private void procesaGuardiasColegiado(TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem, Short idInstitucion){

		try {

			int affectedRows = 0;
			//Buscamos las guardias de colegiado para el letrado seleccionado
			List<ScsGuardiascolegiado> guardiascolegiados = scsGuardiascolegiadoExtendsMapper.getGuardiasColegiadoNoSustitucion(tarjetaAsistenciaResponseItem, idInstitucion);

			//Si no hay guardias de colegiado creadas
			if(guardiascolegiados == null
				|| guardiascolegiados.isEmpty()) {

				//Nos devuelve el idpersona de la persona que esta en guardia para esa fecha
				guardiascolegiados = scsGuardiascolegiadoExtendsMapper.getGuardiasColegiadoEnFecha(tarjetaAsistenciaResponseItem, idInstitucion);
				//Buscamos sus respectivas guardias de colegiado
				if (guardiascolegiados != null
						&& !guardiascolegiados.isEmpty()) {
					guardiascolegiados = scsGuardiascolegiadoExtendsMapper.getGuardiasColegiado(tarjetaAsistenciaResponseItem, idInstitucion, String.valueOf(guardiascolegiados.get(0).getIdpersona()));
				}
			}

			//Si hay guardias de colegiado
			if(guardiascolegiados != null
					&& !guardiascolegiados.isEmpty()){

				ScsGuardiascolegiado firstGuardiaColegiado = guardiascolegiados.get(0);
				ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
				scsCabeceraguardias.setIdpersona(firstGuardiaColegiado.getIdpersona());
				scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
				scsCabeceraguardias.setIdinstitucion(idInstitucion);
				scsCabeceraguardias.setFechainicio(firstGuardiaColegiado.getFechainicio());
				scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
				scsCabeceraguardias = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(scsCabeceraguardias);

				//Si el letrado estaba ya de guardia, validamos las cabeceras
				if(firstGuardiaColegiado.getIdpersona().longValue() == Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia())){

					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);
					affectedRows += scsCabeceraguardiasExtendsMapper.updateByPrimaryKeySelective(scsCabeceraguardias);

					if(affectedRows  <= 0){
						LOGGER.error("procesaGuardiasColegiado() / El letrado de la asistencia estaba de guardia pero no se actualizo ninguna fila");
					}

				//Si no, lo aniadimos como refuerzo
				} else {

					//Recorremos para setear el idPersona del colegiado que tentemos que insertar(el que hace la asistencia)
					for (ScsGuardiascolegiado scsGuardiascolegiado : guardiascolegiados) {
						//Para cada guardia colegiado accedemos a sus cabeceras de guardia, a las que luego habra que setear del idPersona y ponerlo en la ultima posicion(por eso se ha obtenido el ultimo de la fila)

						scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");
						scsGuardiascolegiado.setFacturado("N");
						scsGuardiascolegiado.setIdfacturacion(null);
						scsGuardiascolegiado.setPagado("N");
						scsGuardiascolegiado.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
						scsGuardiascolegiado.setFechamodificacion(new Date());
						scsGuardiascolegiado.setUsumodificacion(0);

					}

					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short)(scsCabeceraguardias.getPosicion().shortValue()+1));
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					guardiascolegiados.stream().forEach(scsGuardiascolegiado -> {
						scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);
					});
					if(affectedRows <= 0){
						LOGGER.error("procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}
			//No hay guardias de colegiado creadas para la fecha, se crean
			} else {

				ScsCalendarioguardiasExample example = new ScsCalendarioguardiasExample();

				example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()))
						.andIdguardiaEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()))
						.andFechainicioLessThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()))
						.andFechainicioGreaterThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));

				List<ScsCalendarioguardias> scsCalendarioguardias = scsCalendarioguardiasMapper.selectByExample(example);

				if(scsCalendarioguardias != null
					&& !scsCalendarioguardias.isEmpty()){

					ScsGuardiascolegiado scsGuardiascolegiado = new ScsGuardiascolegiado();
					scsGuardiascolegiado.setIdinstitucion(idInstitucion);
					scsGuardiascolegiado.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsGuardiascolegiado.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsGuardiascolegiado.setFechainicio(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado.setFechafin(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsGuardiascolegiado.setDiasguardia((long)1);
					scsGuardiascolegiado.setDiasacobrar((long)1);
					scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");

					scsGuardiascolegiado.setReserva("N");
					scsGuardiascolegiado.setFacturado("N");
					scsGuardiascolegiado.setIdfacturacion(null);
					scsGuardiascolegiado.setPagado("N");
					scsGuardiascolegiado.setFechamodificacion(new Date());
					scsGuardiascolegiado.setUsumodificacion(0);

					ScsCabeceraguardias scsCabeceraguardias  =  new ScsCabeceraguardias();
					scsCabeceraguardias.setIdinstitucion(idInstitucion);
					scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsCabeceraguardias.setIdcalendarioguardias(scsCalendarioguardias.get(0).getIdcalendarioguardias());

					scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsCabeceraguardias.setFechainicio(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short)1);
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setSustituto("0");
					scsCabeceraguardias.setFechaalta(new Date());

					//Las metemos validadas
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					affectedRows += scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);

					if(affectedRows <= 0){
						LOGGER.error("procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}

			}

		} catch (Exception e) {
			LOGGER.error("procesarGuardiasColegiado () / Error al procesar las guardias de colegiado durante la asistencia, " + e, e);
		}

	}

	@Override
	public UpdateResponseDTO asociarJusticiable(HttpServletRequest request, JusticiableItem justiciable, String anioNumero, String actualizaDatos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					if(!UtilidadesString.esCadenaVacia(anioNumero)){

						ScsPersonajg scsPersonajg = new ScsPersonajg();
						if(!UtilidadesString.esCadenaVacia(justiciable.getIdPersona())){

							scsPersonajg.setIdinstitucion(Short.valueOf(justiciable.getIdInstitucion()));
							scsPersonajg.setIdpersona(Long.valueOf(justiciable.getIdPersona()));
							scsPersonajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajg);

							scsPersonajg.setNombre(justiciable.getNombre());
							scsPersonajg.setApellido1(justiciable.getApellido1());
							scsPersonajg.setApellido2(justiciable.getApellido2());
							scsPersonajg.setTipopersonajg(justiciable.getTipoPersonajg());
							scsPersonajg.setFechamodificacion(new Date());

						} else{

							ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();

							scsPersonajgExample.createCriteria().andNifEqualTo(justiciable.getNif().toUpperCase())
									.andIdinstitucionEqualTo(idInstitucion);

							scsPersonajgExample.setOrderByClause("FECHAMODIFICACION DESC");

							List<ScsPersonajg> personajgs = scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);

							if(personajgs != null
								&& !personajgs.isEmpty()){
								scsPersonajg = personajgs.get(0);
								scsPersonajg.setNif(justiciable.getNif());
								scsPersonajg.setNombre(justiciable.getNombre());
								scsPersonajg.setApellido1(justiciable.getApellido1());
								scsPersonajg.setApellido2(justiciable.getApellido2());
								scsPersonajg.setFechamodificacion(new Date());
								justiciable.setIdPersona(String.valueOf(scsPersonajg.getIdpersona()));
							}
						}

						//Actualizamos datos del justiciable
						if(!UtilidadesString.esCadenaVacia(actualizaDatos)
								&& "S".equals(actualizaDatos)) {

							affectedRows += scsPersonajgExtendsMapper.updateByPrimaryKeySelective(scsPersonajg);
						//Duplicamos el justiciable
						} else if( "N".equals(actualizaDatos)){

							String newIdPersona = scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion).getNewId();
							scsPersonajg.setIdpersona(Long.valueOf(newIdPersona)+1);
							affectedRows += scsPersonajgExtendsMapper.insertSelective(scsPersonajg);
							justiciable.setIdPersona(String.valueOf(scsPersonajg.getIdpersona()));

						}

						ScsAsistencia scsAsistencia = new ScsAsistencia();
						scsAsistencia.setIdpersonajg(Long.valueOf(justiciable.getIdPersona()));
						ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
						scsAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]));

						affectedRows += scsAsistenciaExtendsMapper.updateByExampleSelective(scsAsistencia,scsAsistenciaExample);

					}

					if(affectedRows <= 0){
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
						updateResponse.setId(anioNumero);
					} else{
						updateResponse.setStatus(SigaConstants.OK);
						updateResponse.setId(anioNumero);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("asociarJusticiable() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar el justiciable: " + e);
			error.description("Error al asociar el justiciable: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	@Override
	public UpdateResponseDTO desasociarJusticiable(HttpServletRequest request, JusticiableItem justiciable, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desasociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desasociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					if(!UtilidadesString.esCadenaVacia(anioNumero)){

						ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
						scsAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]));

						List<ScsAsistencia> scsAsistencias = scsAsistenciaExtendsMapper.selectByExample(scsAsistenciaExample);

						if(scsAsistencias != null
							&& !scsAsistencias.isEmpty()) {
							ScsAsistencia scsAsistencia = scsAsistencias.get(0);
							scsAsistencia.setIdpersonajg(null);
							affectedRows += scsAsistenciaExtendsMapper.updateByExample(scsAsistencia,scsAsistenciaExample);
						}
					}

					if(affectedRows <= 0){
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
						updateResponse.setId(anioNumero);
					} else{
						updateResponse.setStatus(SigaConstants.OK);
						updateResponse.setId(anioNumero);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("desasociarJusticiable() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al desasociar el justiciable: " + e);
			error.description("Error al desasociar el justiciable: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	/**
	 * Metodo encargado de buscar los contrarios asociados a una asistencia
	 *
	 * @param request
	 * @param anioNumero
	 * @return
	 */
	@Override
	public List<ListaContrarioJusticiableItem> searchListaContrarios(HttpServletRequest request, String anioNumero, boolean mostrarHistorico) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ListaContrarioJusticiableItem> listaContrarios = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchListaContrarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListaContrarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					if(!UtilidadesString.esCadenaVacia(anioNumero)){

						listaContrarios = scsAsistenciaExtendsMapper.searchListaContrarios(anioNumero, idInstitucion, mostrarHistorico);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("searchListaContrarios() / ERROR: "+ e.getMessage(), e);
		}
		return listaContrarios;
	}

	/**
	 * Metodo encargado de insertar en la tabla SCS_CONTRARIOSASISTENCIA para asociar un justiciable contrario (SCS_PERSONAJG) a la asistencia (SCS_ASISTENCIA)
	 *
	 * @param request
	 * @param justiciables
	 * @param anioNumero
	 * @return
	 */
	@Override
	public InsertResponseDTO asociarContrario(HttpServletRequest request, List<JusticiableItem> justiciables, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					for (int i = 0; i < justiciables.size(); i++) {
						ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
						scsContrariosasistenciaExample.createCriteria()
								.andIdinstitucionEqualTo(idInstitucion)
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andIdpersonaEqualTo(Long.valueOf(justiciables.get(i).getIdPersona()));

						List<ScsContrariosasistencia> contrariosYaExistentes = scsContrariosasistenciaMapper.selectByExample(scsContrariosasistenciaExample);

						if(contrariosYaExistentes != null
								&& !contrariosYaExistentes.isEmpty()){ //Si el contrario ya estaba asociado, es que estaba dado de baja, por lo que seteamos fechaBaja a null
							ScsContrariosasistencia contrario = contrariosYaExistentes.get(0);
							contrario.setFechabaja(null);
							affectedRows += scsContrariosasistenciaMapper.updateByExample(contrario, scsContrariosasistenciaExample);
						}else { //Es un contrario nuevo, insertamos

							if (!UtilidadesString.esCadenaVacia(anioNumero)) {

								ScsContrariosasistencia scsContrariosasistencia = new ScsContrariosasistencia();
								scsContrariosasistencia.setAnio(Short.valueOf(anioNumero.split("/")[0]));
								scsContrariosasistencia.setNumero(Long.valueOf(anioNumero.split("/")[1]));
								scsContrariosasistencia.setIdpersona(Long.valueOf(justiciables.get(i).getIdPersona()));
								scsContrariosasistencia.setIdinstitucion(idInstitucion);
								scsContrariosasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsContrariosasistencia.setFechamodificacion(new Date());

								affectedRows = scsContrariosasistenciaMapper.insertSelective(scsContrariosasistencia);
							}
						}
					}
					if(affectedRows <= 0){
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha insertado ningún registro");
						insertResponseDTO.setError(error);
						insertResponseDTO.setId(anioNumero);
					} else{
						insertResponseDTO.setStatus(SigaConstants.OK);
						insertResponseDTO.setId(anioNumero);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("asociarContrario() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar el contrario: " + e);
			error.description("Error al asociar el contrario: " + e);
			insertResponseDTO.setError(error);
		}
		return insertResponseDTO;
	}

	/**
	 * Metodo encargado de insertar en la tabla SCS_CONTRARIOSASISTENCIA para asociar un justiciable contrario (SCS_PERSONAJG) a la asistencia (SCS_ASISTENCIA)
	 *
	 * @param request
	 * @param contrarios
	 * @param anioNumero
	 * @return
	 */
	@Override
	public UpdateResponseDTO desasociarContrario(HttpServletRequest request, List<ListaContrarioJusticiableItem> contrarios, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desasociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desasociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					if(!UtilidadesString.esCadenaVacia(anioNumero)
						&& contrarios != null
						&& !contrarios.isEmpty()){

						for (int i = 0; i < contrarios.size(); i++) {
							ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();

							scsContrariosasistenciaExample.createCriteria()
									.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
									.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(Long.valueOf(contrarios.get(i).getIdPersona()));

							ScsContrariosasistencia scsContrariosasistencia = new ScsContrariosasistencia();
							scsContrariosasistencia.setFechabaja(new Date());
							affectedRows += scsContrariosasistenciaMapper.updateByExampleSelective(scsContrariosasistencia,scsContrariosasistenciaExample);

						}
					}

					if(affectedRows <= 0){
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha borrado ningún registro");
						updateResponseDTO.setError(error);
					} else{
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("desasociarContrario() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al desasociar el contrario: " + e);
			error.description("Error al desasociar el contrario: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public TarjetaDefensaJuridicaDTO searchTarjetaDefensaJuridica(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaDefensaJuridicaDTO tarjetaDefensaJuridicaDTO = new TarjetaDefensaJuridicaDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if(scsAsistencia != null){
						TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem = new TarjetaDefensaJuridicaItem();
						tarjetaDefensaJuridicaItem.setNig(scsAsistencia.getNig());
						tarjetaDefensaJuridicaItem.setComentariosDelitos(scsAsistencia.getDelitosimputados());
						tarjetaDefensaJuridicaItem.setObservaciones(scsAsistencia.getDatosdefensajuridica());
						tarjetaDefensaJuridicaItem.setNumDiligencia(scsAsistencia.getNumerodiligencia());
						tarjetaDefensaJuridicaItem.setNumProcedimiento(scsAsistencia.getNumeroprocedimiento());
						if(scsAsistencia.getIdpretension() != null) {
							tarjetaDefensaJuridicaItem.setIdProcedimiento(scsAsistencia.getIdpretension().toString());
						}
						if(scsAsistencia.getComisaria() != null) {
							tarjetaDefensaJuridicaItem.setIdComisaria(scsAsistencia.getComisaria().toString());
						}else if(scsAsistencia.getJuzgado() != null){
							tarjetaDefensaJuridicaItem.setIdJuzgado(scsAsistencia.getJuzgado().toString());
						}

						ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
						scsDelitosasistenciaExample.createCriteria()
								.andAnioEqualTo(scsAsistencia.getAnio())
								.andNumeroEqualTo(scsAsistencia.getNumero())
								.andIdinstitucionEqualTo(idInstitucion);

						List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper.selectByExample(scsDelitosasistenciaExample);

						if(delitos != null
							&& !delitos.isEmpty()){
							List<String> idDelitos = new ArrayList<>();
							delitos.stream().forEach(delito ->{
								idDelitos.add(delito.getIddelito().toString());
							});
							tarjetaDefensaJuridicaItem.setIdDelitos(idDelitos);
						}

						tarjetaDefensaJuridicaDTO.getResponseItems().add(tarjetaDefensaJuridicaItem);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("searchTarjetaDefensaJuridica() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar los datos de la defensa juridica: " + e);
			error.description("Error al buscar los datos de la defensa juridica: " + e);
			tarjetaDefensaJuridicaDTO.setError(error);
		}
		return tarjetaDefensaJuridicaDTO;
	}

	/**
	 * Metodo encargado de guardar los datos juridicos de la asistencia en SCS_ASISTENCIA.
	 * Dependiendo de si elegimos comisaria o juzgado, se modificara tambien la tabla SCS_CARACTASISTENCIA.
	 *
	 * @param request
	 * @param tarjetaDefensaJuridicaItem
	 * @param anioNumero
	 * @return
	 */
	@Override
	public UpdateResponseDTO guardarTarjetaDefensaJuridica(HttpServletRequest request, TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {

					if(!UtilidadesString.esCadenaVacia(anioNumero)) {

						ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
						scsAsistenciaKey.setIdinstitucion(idInstitucion);
						scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
						scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));

						//Actualizamos los delitos asociados seleccionados en el multiselect en SCS_DELITOSASISTENCIA
						procesarDelitos(tarjetaDefensaJuridicaItem, idInstitucion, anioNumero);

						ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
						scsAsistencia.setNig(tarjetaDefensaJuridicaItem.getNig());
						scsAsistencia.setDelitosimputados(tarjetaDefensaJuridicaItem.getComentariosDelitos());
						scsAsistencia.setDatosdefensajuridica(tarjetaDefensaJuridicaItem.getObservaciones());
						scsAsistencia.setNumerodiligencia(tarjetaDefensaJuridicaItem.getNumDiligencia());
						scsAsistencia.setNumeroprocedimiento(tarjetaDefensaJuridicaItem.getNumProcedimiento());
						if(!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdProcedimiento())){
							scsAsistencia.setIdpretension(Short.valueOf(tarjetaDefensaJuridicaItem.getIdProcedimiento()));
						}else{
							scsAsistencia.setIdpretension(null);
						}

						ScsCaractasistenciaKey scsCaractasistenciaKey = new ScsCaractasistenciaKey();
						scsCaractasistenciaKey.setAnio(scsAsistencia.getAnio());
						scsCaractasistenciaKey.setNumero(scsAsistencia.getNumero());
						scsCaractasistenciaKey.setIdinstitucion(idInstitucion);
						ScsCaractasistencia scsCaractasistencia = scsCaractasistenciaMapper.selectByPrimaryKey(scsCaractasistenciaKey);

						if(!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdComisaria())){
							scsAsistencia.setComisaria(Long.valueOf(tarjetaDefensaJuridicaItem.getIdComisaria()));
							scsAsistencia.setComisariaidinstitucion(idInstitucion);
							scsAsistencia.setJuzgado(null);
							scsAsistencia.setJuzgadoidinstitucion(null);
							//Si elegimos comisaria y la caracteristica de la asistencia estaba creada, hay que dejarlo
							//reflejado en SCS_CARACTASISTENCIA
							if(scsCaractasistencia != null){
								scsCaractasistencia.setIdjuzgado(null);
								scsCaractasistencia.setIdinstitucionJuzgado(null);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.updateByPrimaryKey(scsCaractasistencia);
							}
						} else if(!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdJuzgado())){
							scsAsistencia.setJuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
							scsAsistencia.setJuzgadoidinstitucion(idInstitucion);
							scsAsistencia.setComisaria(null);
							scsAsistencia.setComisariaidinstitucion(null);
							//Si se selecciona juzgado hay que dejarlo registrado en la tabla SCS_CARACTASISTENCIA
							//Si no tiene creado registro lo creamos y si lo tiene creado lo actualizamos
							if(scsCaractasistencia == null){
								scsCaractasistencia = new ScsCaractasistencia();
								scsCaractasistencia.setAnio(scsAsistencia.getAnio());
								scsCaractasistencia.setNumero(scsAsistencia.getNumero());
								scsCaractasistencia.setIdinstitucion(idInstitucion);
								scsCaractasistencia.setIdjuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
								scsCaractasistencia.setIdinstitucionJuzgado(idInstitucion);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.insertSelective(scsCaractasistencia);
							} else{
								scsCaractasistencia.setIdjuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
								scsCaractasistencia.setIdinstitucionJuzgado(idInstitucion);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.updateByPrimaryKey(scsCaractasistencia);
							}

						}else{
							scsAsistencia.setComisaria(null);
							scsAsistencia.setJuzgado(null);
							scsAsistencia.setComisariaidinstitucion(null);
							scsAsistencia.setJuzgadoidinstitucion(null);
						}

						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

					}

					if(affectedRows <= 0){
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha borrado ningún registro");
						updateResponseDTO.setError(error);
					} else{
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("guardarTarjetaDefensaJuridica() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar los datos de defensa juridica: " + e);
			error.description("Error al guardar los datos de defensa juridica: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}
	private void procesarDelitos(TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, Short idInstitucion , String anioNumero){

		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria()
				.andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
				.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]));
		scsDelitosasistenciaMapper.deleteByExample(scsDelitosasistenciaExample);

		if(tarjetaDefensaJuridicaItem.getIdDelitos() != null
			&& !tarjetaDefensaJuridicaItem.getIdDelitos().isEmpty()){

			for (int i = 0; i < tarjetaDefensaJuridicaItem.getIdDelitos().size(); i++) {

				String idDelito = tarjetaDefensaJuridicaItem.getIdDelitos().get(i);
				ScsDelitosasistencia scsDelitosasistencia = new ScsDelitosasistencia();
				scsDelitosasistencia.setAnio(Short.valueOf(anioNumero.split("/")[0]));
				scsDelitosasistencia.setNumero(Long.valueOf(anioNumero.split("/")[1]));
				scsDelitosasistencia.setIdinstitucion(idInstitucion);
				scsDelitosasistencia.setIddelito(Short.valueOf(idDelito));
				scsDelitosasistencia.setUsumodificacion(1);
				scsDelitosasistencia.setFechamodificacion(new Date());

				scsDelitosasistenciaMapper.insertSelective(scsDelitosasistencia);
			}

		}
	}


	@Override
	public TarjetaObservacionesDTO searchTarjetaObservaciones(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaObservacionesDTO tarjetaObservacionesDTO = new TarjetaObservacionesDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if(scsAsistencia != null){

						TarjetaObservacionesItem tarjetaObservacionesItem = new TarjetaObservacionesItem();
						tarjetaObservacionesItem.setIncidencias(scsAsistencia.getIncidencias());
						tarjetaObservacionesItem.setObservaciones(scsAsistencia.getObservaciones());

						tarjetaObservacionesDTO.getResponseItems().add(tarjetaObservacionesItem);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("searchTarjetaObservaciones() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar la tarjeta observaciones: " + e);
			error.description("Error al buscar la tarjeta observaciones: " + e);
			tarjetaObservacionesDTO.setError(error);
		}
		return tarjetaObservacionesDTO;
	}

	@Override
	public UpdateResponseDTO guardarTarjetaObservaciones(HttpServletRequest request, TarjetaObservacionesItem tarjetaObservacionesItem, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if(scsAsistencia != null
						&& tarjetaObservacionesItem != null){

						scsAsistencia.setObservaciones(tarjetaObservacionesItem.getObservaciones());
						scsAsistencia.setIncidencias(tarjetaObservacionesItem.getIncidencias());
						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
					}

					if(affectedRows > 0){
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					}else{
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("guardarTarjetaObservaciones() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al actualizar la tarjeta observaciones: " + e);
			error.description("Error al actualizar la tarjeta observaciones: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public RelacionesDTO searchRelaciones(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RelacionesDTO relacionesDTO = new RelacionesDTO();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)) {

					GenParametrosExample genParametrosExample = new GenParametrosExample();

					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"PreAsistenciaServiceImpl.searchRelaciones() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					LOGGER.info(
							"AsistenciaServiceImpl.searchRelaciones() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}

					List<RelacionesItem> relacionesItems = scsAsistenciaExtendsMapper.searchRelaciones(anioNumero.split("/")[0],anioNumero.split("/")[1],idInstitucion, Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(),tamMaximo);

					if(relacionesItems != null
						&& !relacionesItems.isEmpty()){
						relacionesDTO.setRelacionesItem(relacionesItems);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("searchRelaciones() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las relaciones: " + e);
			error.description("Error al buscar las relaciones: " + e);
			relacionesDTO.setError(error);
		}
		return relacionesDTO;
	}

	@Override
	public UpdateResponseDTO asociarDesigna(HttpServletRequest request, String anioNumero, DesignaItem designaItem, String copiarDatos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if(scsAsistencia != null
							&& designaItem != null){
						scsAsistencia.setDesignaAnio((short)designaItem.getAno());
						scsAsistencia.setDesignaNumero(Long.valueOf(designaItem.getCodigo()));
						scsAsistencia.setDesignaTurno(Integer.valueOf(designaItem.getIdTurnos()[0]));
						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

						if("S".equals(copiarDatos)) { //Pasamos los datos de la asistencia a la designa
							affectedRows += updateDesignaConAsistencia(scsAsistencia);
						}
					}

					if(affectedRows > 0){
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					}else{
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("asociarDesigna() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar la designa a la asistencia: " + e);
			error.description("Error al asociar la designa a la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO eliminarRelacion(HttpServletRequest request, String anioNumero, List<RelacionesItem> asuntos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()
						&& !UtilidadesString.esCadenaVacia(anioNumero)
						&& asuntos != null) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if(scsAsistencia != null){
						for (int i = 0; i < asuntos.size(); i++) {

							if("D".equals(String.valueOf(asuntos.get(i).getSjcs().charAt(0)))){
								scsAsistencia.setDesignaAnio(null);
								scsAsistencia.setDesignaNumero(null);
								scsAsistencia.setDesignaTurno(null);
								affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
							}else if("E".equals(String.valueOf(asuntos.get(i).getSjcs().charAt(0)))){

								scsAsistencia.setEjganio(null);
								scsAsistencia.setEjgnumero(null);
								scsAsistencia.setEjgidtipoejg(null);
								affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
							}

						}
					}

					if(affectedRows > 0){
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					}else{
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		}catch(Exception e) {
			LOGGER.error("asociarDesigna() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar la designa a la asistencia: " + e);
			error.description("Error al actualizar la designa a la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	/**
	 * Metodo encargado de actualizar la designa con los datos de la asistencia
	 * @param scsAsistencia
	 * @return
	 */
	private int updateDesignaConAsistencia(ScsAsistencia scsAsistencia){
		int affectedRows = 0;
		ScsDesignaKey scsDesignaKey = new ScsDesignaKey();
		scsDesignaKey.setAnio(scsAsistencia.getDesignaAnio());
		scsDesignaKey.setNumero(scsAsistencia.getDesignaNumero());
		scsDesignaKey.setIdturno(scsAsistencia.getDesignaTurno());
		scsDesignaKey.setIdinstitucion(scsAsistencia.getIdinstitucion());

		//Actualizamos los datos generales
		ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(scsDesignaKey);
		scsDesigna.setNig(scsAsistencia.getNig());
		scsDesigna.setNumprocedimiento(scsAsistencia.getNumeroprocedimiento());
		scsDesigna.setIdpretension(scsAsistencia.getIdpretension());
		scsDesigna.setIdjuzgado(scsAsistencia.getJuzgado());
		scsDesigna.setIdinstitucionJuzg(scsAsistencia.getJuzgadoidinstitucion());
		affectedRows += scsDesignacionesExtendsMapper.updateByPrimaryKey(scsDesigna);

		//Insertamos el interesado (asistido de la asistencia)
		if(scsAsistencia.getIdpersonajg() != null ) {
			ScsDefendidosdesigna scsDefendidosdesigna = new ScsDefendidosdesigna();
			scsDefendidosdesigna.setIdinstitucion(scsAsistencia.getIdinstitucion());
			scsDefendidosdesigna.setAnio(scsAsistencia.getDesignaAnio());
			scsDefendidosdesigna.setNumero(scsAsistencia.getDesignaNumero());
			scsDefendidosdesigna.setIdturno(scsAsistencia.getDesignaTurno());
			scsDefendidosdesigna.setIdpersona(scsAsistencia.getIdpersonajg());
			scsDefendidosdesigna.setUsumodificacion(1);
			scsDefendidosdesigna.setFechamodificacion(new Date());
			affectedRows += scsDefendidosdesignasExtendsMapper.insertSelective(scsDefendidosdesigna);
		}

		//TODO Falta copiar delitos asistencia tras MERGE con EJG, revisar updateDatosAdicionales en DesignacionesServiceImpl

		return affectedRows;
	}

}
