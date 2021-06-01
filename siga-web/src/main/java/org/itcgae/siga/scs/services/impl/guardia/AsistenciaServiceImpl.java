package org.itcgae.siga.scs.services.impl.guardia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionAsistenciaItem;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaDTO;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseDTO;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiascolegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsJuzgadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoAsistenciaColegioExtendsMapper;
import org.itcgae.siga.scs.services.guardia.AsistenciaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.mail.imap.protocol.Item;

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

}
