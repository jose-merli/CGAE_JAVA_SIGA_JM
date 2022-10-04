package org.itcgae.siga.scs.services.impl.guardia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.*;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
import org.itcgae.siga.scs.services.guardia.AsistenciaService;
import org.itcgae.siga.scs.services.guardia.GuardiasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

	private final Logger LOGGER = Logger.getLogger(AsistenciaServiceImpl.class);
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
	private ScsActuacionasistenciaExtendsMapper scsActuacionasistenciaExtendsMapper;

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

	@Autowired
	private ScsDesignasLetradoExtendsMapper scsDesignasLetradoExtendsMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsDelitosejgMapper scsDelitosejgMapper;

	@Autowired
	private ScsContrariosejgExtendsMapper scsContrariosejgExtendsMapper;

	@Autowired
	private ScsDocumentacionasiMapper scsDocumentacionasiMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	@Autowired
	private GenFicheroMapper genFicheroMapper;

	@Autowired
	private ScsHitofacturableguardiaExtendsMapper scsHitofacturableguardiaExtendsMapper;

	@Autowired
	private ScsTipoactuacionExtendsMapper scsTipoactuacionExtendsMapper;

	@Autowired
	private GuardiasColegiadoServiceImpl guardiasColegiadoServiceImpl;
	
	@Autowired
	private GuardiasService guardiasService;
	
	@Autowired
	private ScsSaltoscompensacionesExtendsMapper scsSaltoscompensacionesExtendsMapper;

	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;


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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getTurnosByColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getTurnosByColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					// Si la fecha trae hora la recortamos
					if (guardiaDia.length() > 10) {

						guardiaDia = guardiaDia.substring(0, 11);

					}
					List<ComboItem> combosItems = scsGuardiascolegiadoExtendsMapper.getTurnosByColegiadoFecha(idPersona,
							idInstitucion, guardiaDia);
					comboDTO.setCombooItems(combosItems);

					if (combosItems == null || combosItems.isEmpty()) {

						error.setCode(200);
						error.setMessage("sjcs.guardia.asistencia.nohayguardia");
						error.description("sjcs.guardia.asistencia.nohayguardia");
						comboDTO.setError(error);

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getTurnosByColegiadoFecha() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las guardias del colegiado para la fecha" + guardiaDia + ": " + e);
			error.description("Error al traer las guardias del colegiado para la fecha" + guardiaDia + ": " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public ComboDTO getGuardiasByTurnoColegiadoFecha(HttpServletRequest request, String guardiaDia, String idTurno,
			String idPersona) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getGuardiasByTurnoColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getGuardiasByTurnoColegiadoFecha() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					// Si la fecha trae hora la recortamos
					if (guardiaDia.length() > 10) {

						guardiaDia = guardiaDia.substring(0, 11);

					}
					List<ComboItem> combosItems = scsGuardiascolegiadoExtendsMapper
							.getGuardiasByTurnoColegiadoFecha(idPersona, idInstitucion, guardiaDia, idTurno);
					comboDTO.setCombooItems(combosItems);

				}
			}
		} catch (Exception e) {
			LOGGER.error("getTurnosByColegiadoFecha() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getTiposAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getTiposAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					ScsGuardiasturnoExample example = new ScsGuardiasturnoExample();
					String[] arrayGuardiasString = idGuardia.split(",");
					List<String> listaGuardiasString = Arrays.asList(arrayGuardiasString);
					List<Integer> listaGuardiasInteger = listaGuardiasString.stream().map(s -> Integer.parseInt(s))
							.collect(Collectors.toList());
					String[] arrayTurnosString = idTurno.split(",");
					List<String> listaTurnosString = Arrays.asList(arrayTurnosString);
					List<Integer> listaTurnosInteger = listaTurnosString.stream().map(s -> Integer.parseInt(s))
							.collect(Collectors.toList());
					example.createCriteria().andIdguardiaIn(listaGuardiasInteger).andIdturnoIn(listaTurnosInteger)
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsGuardiasturno> guardiasList = scsGuardiasturnoExtendsMapper.selectByExample(example);

					if (guardiasList != null && !guardiasList.isEmpty()) {

						idTipoGuardia = guardiasList.get(0).getIdtipoguardia();
						idLenguaje = Integer.parseInt(usuarios.get(0).getIdlenguaje());

						List<TiposAsistenciaItem> tiposAsistenciaItems = scsTipoAsistenciaColegioExtendsMapper
								.getTiposAsistenciaColegiado(idInstitucion, idLenguaje, idTipoGuardia);

						List<ComboItem> comboItems = (List<ComboItem>) tiposAsistenciaItems.stream().map(x -> {
							ComboItem comboItem = new ComboItem();
							comboItem.value(x.getIdtipoasistenciacolegio());
							comboItem.label(x.getTipoasistencia());
							return comboItem;

						}).collect(Collectors.toList());

						comboDTO.setCombooItems(comboItems);

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getTiposAsistenciaColegio() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getColegiadosGuardiaDia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getColegiadosGuardiaDia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					// Si la fecha trae hora la recortamos
					if (guardiaDia.length() > 10) {

						guardiaDia = guardiaDia.substring(0, 11);

					}

					List<ComboItem> comboItems = scsGuardiascolegiadoExtendsMapper.getColegiadosGuardiaDia(idTurno,
							idGuardia, idInstitucion, guardiaDia);

					if (comboItems != null && !comboItems.isEmpty()) {
						comboDTO.setCombooItems(comboItems);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getColegiadosGuardiaDia() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al obtener los colegiados que tienen guardia: " + e);
			error.description("Error al obtener los colegiados que tienen guardia: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Override
	public TarjetaAsistenciaResponseDTO2 searchAsistenciasExpress(HttpServletRequest request,
			FiltroAsistenciaItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		TarjetaAsistenciaResponseDTO2 tarjetaAsistenciaResponseDTO = new TarjetaAsistenciaResponseDTO2();
		List<TarjetaAsistenciaResponse2Item> tarjetaAsistenciaResponseItems = new ArrayList<TarjetaAsistenciaResponse2Item>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					GenPropertiesExample exampleProperties = new GenPropertiesExample();
					exampleProperties.createCriteria().andFicheroEqualTo("SIGA")
							.andParametroEqualTo("codigo.general.scs_tipoasistencia.volanteExpres");
					List<GenProperties> properties = genPropertiesMapper.selectByExample(exampleProperties);

					if (properties != null && !properties.isEmpty()) {
						//filtro.setIdTipoAsistencia(properties.get(0).getValor());

						List<TarjetaAsistenciaItem2> tarjetaAsistenciaItems = scsAsistenciaExtendsMapper
								.searchAsistenciasExpress(filtro, idInstitucion);
						tarjetaAsistenciaItems.forEach(asistencia -> {
							List<String> listaIdDelitos = scsAsistenciaExtendsMapper.getDelitosFromAsistencia(
									asistencia.getAnio(), asistencia.getNumero(), idInstitucion.toString());
							asistencia.setIdDelito(listaIdDelitos);
						});

						if (tarjetaAsistenciaItems != null && !tarjetaAsistenciaItems.isEmpty()) {

							// Modificamos los datos y agrupamos las actuaciones por asistencia
							Map<String, List<TarjetaAsistenciaItem2>> actuacionesPorAsistencia = tarjetaAsistenciaItems
									.stream().map((TarjetaAsistenciaItem2 tarjetaAsistenciaItem) -> {
										tarjetaAsistenciaItem.setAnioNumero("A" + tarjetaAsistenciaItem.getAnio() + "/"
												+ tarjetaAsistenciaItem.getNumero());

										if (!UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getNif())
												&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getApellido1())
												&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getApellido2())
												&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getNombre())
												&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getSexo())) {
											String descripcionSexo = "";
											switch (tarjetaAsistenciaItem.getSexo()) {
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
											tarjetaAsistenciaItem.setAsistido(tarjetaAsistenciaItem.getNif() + " - "
													+ tarjetaAsistenciaItem.getApellido1() + " "
													+ tarjetaAsistenciaItem.getApellido2() + ", "
													+ tarjetaAsistenciaItem.getNombre() + " - " + descripcionSexo);

										}

										if (!UtilidadesString.esCadenaVacia(tarjetaAsistenciaItem.getEjgAnio())
												&& !UtilidadesString
														.esCadenaVacia(tarjetaAsistenciaItem.getEjgNumero())) {
											tarjetaAsistenciaItem.setEjgAnioNumero("E" + tarjetaAsistenciaItem.getAnio()
													+ "/" + tarjetaAsistenciaItem.getEjgNumero());
										}

										return tarjetaAsistenciaItem;
									}).collect(Collectors
											.groupingBy((TarjetaAsistenciaItem2 item) -> item.getAnioNumero()));

							actuacionesPorAsistencia.forEach((asistencia, actuaciones) -> {

								TarjetaAsistenciaResponse2Item responseItem = new TarjetaAsistenciaResponse2Item();

								List<ActuacionAsistenciaItem> actuacAsistenciaItems = new ArrayList<ActuacionAsistenciaItem>();
								for (int i = 0; i < actuaciones.size(); i++) {

									TarjetaAsistenciaItem2 actuacion = actuaciones.get(i);
									// Seteamos los datos de la asistencia
									responseItem.setAnio(actuacion.getAnio());
									responseItem.setNumero(actuacion.getNumero());
									responseItem.setAnioNumero(actuacion.getAnioNumero());
									responseItem.setAsistido(actuacion.getAsistido());
									responseItem.setIdDelito(actuacion.getIdDelito());
									responseItem.setObservaciones(actuacion.getObservaciones());
									responseItem.setEjgAnio(actuacion.getEjgAnio());
									responseItem.setEjgNumero(actuacion.getEjgNumero());
									responseItem.setEjgAnioNumero(actuacion.getEjgAnioNumero());
									responseItem.setIdTipoEjg(actuacion.getIdTipoEjg());
									responseItem.setNombre(actuacion.getNombre());
									responseItem.setApellido1(actuacion.getApellido1());
									responseItem.setApellido2(actuacion.getApellido2());
									responseItem.setNif(actuacion.getNif());
									responseItem.setSexo(actuacion.getSexo());

									// Seteamos los datos de las actuaciones de la asistencia y los añadimos a la
									// lista
									ActuacionAsistenciaItem actuacionAsistenciaItem = new ActuacionAsistenciaItem();
									actuacionAsistenciaItem.setFechaActuacion(actuacion.getFchaActuacion());
									actuacionAsistenciaItem.setLugar(actuacion.getLugar());
									actuacionAsistenciaItem.setNumeroAsunto(actuacion.getNumeroAsunto());
									actuacionAsistenciaItem.setFechaJustificacion(actuacion.getFchaJustificacion());
									actuacionAsistenciaItem.setComisariaJuzgado(actuacion.getComisariaJuzgado());
									if ((i > 0 && actuacion.getIdDelito() != null
											&& actuacion.getIdDelito().equals(actuaciones.get(i).getIdDelito()))
											|| i == 0 || (i > 0 && actuacion.getIdDelito().isEmpty())) {
										actuacAsistenciaItems.add(actuacionAsistenciaItem);
									}

								}
								responseItem.setActuaciones(actuacAsistenciaItems);
								tarjetaAsistenciaResponseItems.add(responseItem);
							});
						}
						tarjetaAsistenciaResponseDTO.setResponseItems(
								tarjetaAsistenciaResponseItems.stream().sorted().collect(Collectors.toList()));
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("searchAsistencias() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

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
		} catch (Exception e) {
			LOGGER.error("getJuzgados() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getComisarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getComisarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {
					List<ComboItem> combosItems = scsComisariaExtendsMapper.getComisariasByIdTurno(idInstitucion,
							idTurno);
					comboDTO.setCombooItems(combosItems);
				}
			}
		} catch (Exception e) {
			LOGGER.error("getJuzgados() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las comisarias: " + e);
			error.description("Error al traer las comisarias: " + e);
			comboDTO.setError(error);
		}
		return comboDTO;
	}

	@Transactional
	@Override
	public DeleteResponseDTO guardarAsistenciasExpres(HttpServletRequest request,
			List<TarjetaAsistenciaResponse2Item> asistencias) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					// Obtenemos el tipo de asistencia general para Asistencias Express
					GenPropertiesExample exampleProperties = new GenPropertiesExample();
					exampleProperties.createCriteria().andFicheroEqualTo("SIGA")
							.andParametroEqualTo("codigo.general.scs_tipoasistencia.volanteExpres");
					List<GenProperties> properties = genPropertiesMapper.selectByExample(exampleProperties);
					String tipoAsistenciaGeneral = properties.stream().findFirst().orElse(new GenProperties())
							.getValor();

					if (asistencias != null && !asistencias.isEmpty()) {

						procesarSustitucionGuardia(asistencias.get(0).getFiltro(), idInstitucion, request);

						asistencias.forEach((TarjetaAsistenciaResponse2Item asistencia) -> {

							// Comprobamos si existe el justiciable, si no, lo insertamos en scs_personajg y
							// devolvemos idPersona
							String idPersona = getIdPersonaJusticiable(asistencia, idInstitucion, usuarios.get(0));

							if (UtilidadesString.esCadenaVacia(asistencia.getAnioNumero())) {
								// Si no viene informado el anionumero es que se trata de una nueva asistencia
								LOGGER.info("guardarAsistencias() / Nueva asistencia");

								// Obtenemos proximo numero de una nueva asistencia
								String anioAsistencia = asistencia.getFiltro().getDiaGuardia().split("/")[2];
								String numeroAsistencia = scsAsistenciaExtendsMapper
										.getNextNumeroAsistencia(anioAsistencia, idInstitucion);

								// Montamos bean e insertamos asistencia
								ScsAsistencia asistenciaBBDD = fromTarjetaAsistenciaItemToScsAsistencia2(asistencia,
										anioAsistencia, numeroAsistencia, tipoAsistenciaGeneral, idPersona,
										idInstitucion, usuarios.get(0));
								// Como es una nueva Asistencia, le ponemos estado ACTIVO
								asistenciaBBDD.setIdestadoasistencia((short) 1);
								asistenciaBBDD.setIdorigenasistencia((short) 30); // 30 - Es una asistencia expres
								int responseAsistencia = scsAsistenciaExtendsMapper.insertSelective(asistenciaBBDD);

								if (responseAsistencia == 0) {
									LOGGER.error("guardarAsistencias() / No se ha insertado la nueva asistencia");
									error.setCode(500);
									error.setMessage("Error al insertar la nueva asistencia");
									error.description("Error al insertar la nueva asistencia");
								}

								procesarDelitosAsistenciaExpress(asistencia, anioAsistencia, numeroAsistencia,
										idInstitucion, usuarios.get(0));

								// Recorremos actuaciones y las insertamos
								for (int i = 0; i < asistencia.getActuaciones().size(); i++) {

									ScsActuacionasistencia actuacionBBDD = fromActuacionAsistenciaItemToScsActuacionasistencia(
											asistencia.getActuaciones().get(i), asistencia, anioAsistencia,
											numeroAsistencia, tipoAsistenciaGeneral, idInstitucion, true,
											usuarios.get(0));

									int responseActuacion = scsActuacionasistenciaExtendsMapper
											.insertSelective(actuacionBBDD);
									if (responseActuacion == 0) {
										LOGGER.error("guardarAsistencias() / No se ha insertado la nueva actuacion");
										error.setCode(500);
										error.setMessage("Error al insertar la nueva actuacion");
										error.description("Error al insertar la nueva actuacion");
									}
								}

							} else {
								LOGGER.info("guardarAsistencias() / Asistencia existente, actualizamos");
								// Actualizamos asistencia y actuaciones
								ScsAsistencia asistenciaBBDD = fromTarjetaAsistenciaItemToScsAsistencia2(asistencia,
										null, null, tipoAsistenciaGeneral, idPersona, idInstitucion, usuarios.get(0));
								scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(asistenciaBBDD);
								int responseAsistencia = scsAsistenciaExtendsMapper
										.updateByPrimaryKeySelective(asistenciaBBDD);
								if (responseAsistencia == 0) {
									LOGGER.error("guardarAsistencias() / No se ha actualizado la asistencia");
									error.setCode(500);
									error.setMessage("Error al actualizar las asistencias");
									error.description("Error al actualizar las asistencias");

								}
								procesarDelitosAsistenciaExpress(asistencia, null, null, idInstitucion,
										usuarios.get(0));
								for (int i = 0; i < asistencia.getActuaciones().size(); i++) {
									// Comprobamos si es una nueva actuacion
									boolean isNuevaActuacion = isNuevaActuacion(asistencia, i + 1, idInstitucion);

									ScsActuacionasistencia actuacionBBDD = fromActuacionAsistenciaItemToScsActuacionasistencia(
											asistencia.getActuaciones().get(i), asistencia, null, null,
											tipoAsistenciaGeneral, idInstitucion, isNuevaActuacion, usuarios.get(0));

									// Si es nueva la insertamos, si no updateamos
									if (isNuevaActuacion) {
										LOGGER.info("guardarAsistencias() / Nueva actuacion");
										int responseActuacion = scsActuacionasistenciaExtendsMapper
												.insertSelective(actuacionBBDD);
										if (responseActuacion == 0) {
											LOGGER.error(
													"guardarAsistencias() / No se ha insertado al nueva Actuacion");
											error.setCode(500);
											error.setMessage("Error al insertar la nueva actuacion");
											error.description("Error al insertar la nueva actuacion");
										}
									} else {
										LOGGER.info("guardarAsistencias() / Actuacion existente, actualizamos");
										actuacionBBDD.setIdactuacion(Long.valueOf(i + 1));
										int responseActuacion = scsAsistenciaExtendsMapper
												.updateAsistenciaExpress(actuacionBBDD);
										if (responseActuacion == 0) {
											LOGGER.error(
													"guardarAsistencias() / No se han actualizado las actuaciones");
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
		} catch (Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar las asistencias: " + e);
			error.description("Error al guardar las asistencias: " + e);
			deleteResponseDTO.setError(error);
		}
		return deleteResponseDTO;
	}

	/**
	 * Metodo para obtener el IdPersona del justiciable introducido en la tabla de
	 * asistencias. Si no existe, se crea y se devuelve el nuevo IdPersona del nuevo
	 * justiciable
	 * 
	 * @param asistencia
	 * @param idInstitucion
	 * @return
	 */
	private String getIdPersonaJusticiable(TarjetaAsistenciaResponse2Item asistencia, Short idInstitucion,
			AdmUsuarios usuario) {
		String idPersona = null;

		int response = 0;

		ScsPersonajgExample personajgExample = new ScsPersonajgExample();
		if(asistencia.getNif() != null)
		personajgExample.createCriteria().andNifEqualTo(asistencia.getNif().trim());

		List<ScsPersonajg> listaJusticiables = scsPersonajgExtendsMapper.selectByExample(personajgExample);

		// Si existe obtenemos el idPersona y actualizamos datos
		if (listaJusticiables != null && !listaJusticiables.isEmpty()) {
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
			// Si no, lo creamos y nos quedamos con su nuevo idPersona
		} else {
			String newIdPersona = scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion).getNewId();
			ScsPersonajg newPersonajg = new ScsPersonajg();

			newPersonajg.setIdpersona(Long.valueOf(newIdPersona) + 1);
			newPersonajg.setNif(asistencia.getNif());
			newPersonajg.setApellido1(asistencia.getApellido1());
			newPersonajg.setApellido2(asistencia.getApellido2());
			newPersonajg.setNombre(asistencia.getNombre());
			newPersonajg.setSexo(asistencia.getSexo());
			newPersonajg.setIdinstitucion(idInstitucion);
			newPersonajg.setFechamodificacion(new Date());
			newPersonajg.setUsumodificacion(usuario.getIdusuario());

			response = scsPersonajgExtendsMapper.insertSelective(newPersonajg);

			// Todo OK, devolvemos el idpersona del nuevo justiciable creado
			if (response == 1) {
				idPersona = newPersonajg.getIdpersona().toString();
			}

		}
		return idPersona;
	}

	/**
	 * 
	 * Metodo que convierte un TarjetaAsistenciaResponseItem en un ScsAsistencia
	 * para insertarlo/actualizar BBDD
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
			String anioAsistencia, String numeroAsistencia, String tipoAsistenciaGeneral, String idPersonaJg,
			Short idInstitucion, AdmUsuarios usuario) {
		ScsAsistencia asistenciaBBDD = new ScsAsistencia();
		try {
			if (!UtilidadesString.esCadenaVacia(anioAsistencia)) {
				asistenciaBBDD.setAnio(Short.valueOf(anioAsistencia));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getAnio())) {
				asistenciaBBDD.setAnio(Short.valueOf(asistencia.getAnio()));
			}

			if (!UtilidadesString.esCadenaVacia(numeroAsistencia)) {
				asistenciaBBDD.setNumero(Long.valueOf(numeroAsistencia));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getNumero())) {
				asistenciaBBDD.setNumero(Long.valueOf(asistencia.getNumero()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getDiaGuardia())) {
				asistenciaBBDD
						.setFechahora(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFiltro().getDiaGuardia()));
			} else {
				asistenciaBBDD
						.setFechahora(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaAsistencia()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaCierre())) {
				asistenciaBBDD.setFechacierre(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaCierre()));
			}
			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaSolicitud())) {
				asistenciaBBDD.setFechasolicitud(
						new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaSolicitud()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaEstado())) {
				asistenciaBBDD.setFechaestadoasistencia(
						new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaEstado()));
			}

			if (!UtilidadesString.esCadenaVacia(idPersonaJg)) {
				asistenciaBBDD.setIdpersonajg(Long.valueOf(idPersonaJg));
			}
			asistenciaBBDD.setIdinstitucion(idInstitucion);
			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTurno())) {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getFiltro().getIdTurno()));
			} else {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getIdTurno()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdGuardia())) {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getFiltro().getIdGuardia()));
			} else {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getIdGuardia()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdLetradoGuardia())) {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getFiltro().getIdLetradoGuardia()));
			} else {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getIdLetradoGuardia()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getObservaciones())) {
				asistenciaBBDD.setObservaciones(asistencia.getObservaciones());
			}
			if (!UtilidadesString.esCadenaVacia(tipoAsistenciaGeneral)) {
				asistenciaBBDD.setIdtipoasistencia(Short.valueOf(tipoAsistenciaGeneral));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTipoAsistenciaColegiado())) {
				asistenciaBBDD.setIdtipoasistenciacolegio(
						Short.valueOf(asistencia.getFiltro().getIdTipoAsistenciaColegiado()));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getIdTipoAsistenciaColegio())) {
				asistenciaBBDD.setIdtipoasistencia((short) 6); // Guardia 24 h. Asist. al detenido (procedimiento penal
																// general)
				asistenciaBBDD.setIdtipoasistenciacolegio(Short.valueOf(asistencia.getIdTipoAsistenciaColegio()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getEstado())) {
				asistenciaBBDD.setIdestadoasistencia(Short.valueOf(asistencia.getEstado()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getIdSolicitudCentralita())) {
				asistenciaBBDD.setIdsolicitudcentralita(Integer.valueOf(asistencia.getIdSolicitudCentralita()));
				asistenciaBBDD.setIdorigenasistencia((short) 40); // 40 - Proviene de una solicitud de centralita
			}

			asistenciaBBDD.setFechamodificacion(new Date());
			asistenciaBBDD.setUsumodificacion(usuario.getIdusuario());
		} catch (ParseException e) {
			LOGGER.error("guardarAsistencias() / ERROR AL PARSEAR FECHAS: " + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR GENERICO: " + e.getMessage(), e);
			throw e;
		}

		return asistenciaBBDD;
	}

	private ScsAsistencia fromTarjetaAsistenciaItemToScsAsistencia2(TarjetaAsistenciaResponse2Item asistencia,
			String anioAsistencia, String numeroAsistencia, String tipoAsistenciaGeneral, String idPersonaJg,
			Short idInstitucion, AdmUsuarios usuario) {
		ScsAsistencia asistenciaBBDD = new ScsAsistencia();
		try {
			if (!UtilidadesString.esCadenaVacia(anioAsistencia)) {
				asistenciaBBDD.setAnio(Short.valueOf(anioAsistencia));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getAnio())) {
				asistenciaBBDD.setAnio(Short.valueOf(asistencia.getAnio()));
			}

			if (!UtilidadesString.esCadenaVacia(numeroAsistencia)) {
				asistenciaBBDD.setNumero(Long.valueOf(numeroAsistencia));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getNumero())) {
				asistenciaBBDD.setNumero(Long.valueOf(asistencia.getNumero()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getDiaGuardia())) {
				asistenciaBBDD
						.setFechahora(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFiltro().getDiaGuardia()));
			} else {
				asistenciaBBDD
						.setFechahora(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaAsistencia()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaCierre())) {
				asistenciaBBDD.setFechacierre(new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaCierre()));
			}
			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaSolicitud())) {
				asistenciaBBDD.setFechasolicitud(
						new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(asistencia.getFechaSolicitud()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getFechaEstado())) {
				asistenciaBBDD.setFechaestadoasistencia(
						new SimpleDateFormat("dd/MM/yyyy").parse(asistencia.getFechaEstado()));
			}

			if (!UtilidadesString.esCadenaVacia(idPersonaJg)) {
				asistenciaBBDD.setIdpersonajg(Long.valueOf(idPersonaJg));
			}
			asistenciaBBDD.setIdinstitucion(idInstitucion);
			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTurno())) {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getFiltro().getIdTurno()));
			} else {
				asistenciaBBDD.setIdturno(Integer.valueOf(asistencia.getIdTurno()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdGuardia())) {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getFiltro().getIdGuardia()));
			} else {
				asistenciaBBDD.setIdguardia(Integer.valueOf(asistencia.getIdGuardia()));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdLetradoGuardia())) {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getFiltro().getIdLetradoGuardia()));
			} else {
				asistenciaBBDD.setIdpersonacolegiado(Long.valueOf(asistencia.getIdLetradoGuardia()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getObservaciones())) {
				asistenciaBBDD.setObservaciones(asistencia.getObservaciones());
			}
			if (!UtilidadesString.esCadenaVacia(tipoAsistenciaGeneral)) {
				asistenciaBBDD.setIdtipoasistencia(Short.valueOf(tipoAsistenciaGeneral));
			}

			if (asistencia.getFiltro() != null
					&& !UtilidadesString.esCadenaVacia(asistencia.getFiltro().getIdTipoAsistenciaColegiado())) {
				asistenciaBBDD.setIdtipoasistenciacolegio(
						Short.valueOf(asistencia.getFiltro().getIdTipoAsistenciaColegiado()));
			} else if (!UtilidadesString.esCadenaVacia(asistencia.getIdTipoAsistenciaColegio())) {
				asistenciaBBDD.setIdtipoasistencia((short) 6); // Guardia 24 h. Asist. al detenido (procedimiento penal
																// general)
				asistenciaBBDD.setIdtipoasistenciacolegio(Short.valueOf(asistencia.getIdTipoAsistenciaColegio()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getEstado())) {
				asistenciaBBDD.setIdestadoasistencia(Short.valueOf(asistencia.getEstado()));
			}

			if (!UtilidadesString.esCadenaVacia(asistencia.getIdSolicitudCentralita())) {
				asistenciaBBDD.setIdsolicitudcentralita(Integer.valueOf(asistencia.getIdSolicitudCentralita()));
				asistenciaBBDD.setIdorigenasistencia((short) 40); // 40 - Proviene de una solicitud de centralita
			}

			asistenciaBBDD.setFechamodificacion(new Date());
			asistenciaBBDD.setUsumodificacion(usuario.getIdusuario());
		} catch (ParseException e) {
			LOGGER.error("guardarAsistencias() / ERROR AL PARSEAR FECHAS: " + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR GENERICO: " + e.getMessage(), e);
			throw e;
		}

		return asistenciaBBDD;
	}

	/**
	 * 
	 * Metodo que convierte el ActuacionAsistenciaItem en el bean de BBDD
	 * ScsActuacionasistencia
	 * 
	 * @param actuacion
	 * @param anioAsistencia
	 * @param numeroAsistencia
	 * @param tipoAsistenciaGeneral
	 * @param idInstitucion
	 * @return
	 * @throws ParseException
	 */
	private ScsActuacionasistencia fromActuacionAsistenciaItemToScsActuacionasistencia(
			ActuacionAsistenciaItem actuacion, TarjetaAsistenciaResponse2Item asistencia, String anioAsistencia,
			String numeroAsistencia, String tipoAsistenciaGeneral, Short idInstitucion, boolean newActuacion,
			AdmUsuarios usuario) {

		ScsActuacionasistencia actuacionBBDD = new ScsActuacionasistencia();
		try {

			if (!UtilidadesString.esCadenaVacia(anioAsistencia)) {
				actuacionBBDD.setAnio(Short.valueOf(anioAsistencia));
			} else {
				actuacionBBDD.setAnio(Short.valueOf(asistencia.getAnio()));
			}

			if (!UtilidadesString.esCadenaVacia(numeroAsistencia)) {
				actuacionBBDD.setNumero(Long.valueOf(numeroAsistencia));
			} else {
				actuacionBBDD.setNumero(Long.valueOf(asistencia.getNumero()));
			}
			actuacionBBDD.setIdinstitucion(idInstitucion);
			actuacionBBDD.setIdtipoasistencia(Short.valueOf(tipoAsistenciaGeneral));
			actuacionBBDD
					.setFechajustificacion(new SimpleDateFormat("dd/MM/yyyy").parse(actuacion.getFechaJustificacion()));
			if (!UtilidadesString.esCadenaVacia(actuacion.getFechaActuacion())) {
				actuacionBBDD.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(actuacion.getFechaActuacion()));
			}
			if (!UtilidadesString.esCadenaVacia(actuacion.getNumeroAsunto())) {
				actuacionBBDD.setNumeroasunto(actuacion.getNumeroAsunto());
			}

			// Si hemos rellenado el lugar (Id comisaria o id juzgado)
			if (!UtilidadesString.esCadenaVacia(actuacion.getLugar())) {
				if ("C".equals(actuacion.getComisariaJuzgado())) {
					actuacionBBDD.setIdinstitucionComis(idInstitucion);
					actuacionBBDD.setIdcomisaria(Long.valueOf(actuacion.getLugar()));
				} else {
					actuacionBBDD.setIdinstitucionJuzg(idInstitucion);
					actuacionBBDD.setIdjuzgado(Long.valueOf(actuacion.getLugar()));
				}
			}

			actuacionBBDD.setFechamodificacion(new Date());
			actuacionBBDD.setUsumodificacion(usuario.getIdusuario());

			if (newActuacion) {
				getMaxIdActuacionAsistencia(actuacionBBDD);
				actuacionBBDD.setUsucreacion(usuario.getIdusuario());
				actuacionBBDD.setFechacreacion(new Date());
				actuacionBBDD.setAcuerdoextrajudicial((short) 0);
				actuacionBBDD.setIdtipoactuacion((short) 1);
			}

		} catch (ParseException e) {
			LOGGER.error("guardarAsistencias() / ERROR AL PARSEAR FECHAS: " + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("guardarAsistencias() / ERROR GENERICO: " + e.getMessage(), e);
			throw e;
		}

		return actuacionBBDD;
	}

	/**
	 * Metodo que obtiene y setea el siguiente IdActuacion
	 * 
	 * @param actuacionBBDD
	 */
	private void getMaxIdActuacionAsistencia(ScsActuacionasistencia actuacionBBDD) {

		ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
		scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(actuacionBBDD.getAnio())
				.andNumeroEqualTo(actuacionBBDD.getNumero()).andIdinstitucionEqualTo(actuacionBBDD.getIdinstitucion());
		scsActuacionasistenciaExample.setOrderByClause("IDACTUACION");

		List<ScsActuacionasistencia> listaActuaciones = scsActuacionasistenciaExtendsMapper
				.selectByExample(scsActuacionasistenciaExample);

		if (listaActuaciones != null && !listaActuaciones.isEmpty()) {

			actuacionBBDD.setIdactuacion(listaActuaciones.get(listaActuaciones.size() - 1).getIdactuacion() + 1);

		} else {
			actuacionBBDD.setIdactuacion(Long.valueOf(1));
		}
	}

	/**
	 * 
	 * Metodo que averigua si se trata de una nueva actuacion a traves del
	 * idActuacion, si no existe un registro con su id será una nueva actuacion
	 * 
	 * @param asistencia
	 * @param idActuacion
	 * @param idInstitucion
	 * @return
	 */
	private boolean isNuevaActuacion(TarjetaAsistenciaResponse2Item asistencia, int idActuacion, Short idInstitucion) {
		boolean isNuevaActuacion = false;

		ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
		scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
				.andNumeroEqualTo(Long.valueOf(asistencia.getNumero())).andIdinstitucionEqualTo(idInstitucion)
				.andIdactuacionEqualTo(Long.valueOf(idActuacion));

		List<ScsActuacionasistencia> listaActuaciones = scsActuacionasistenciaExtendsMapper
				.selectByExample(scsActuacionasistenciaExample);

		isNuevaActuacion = (listaActuaciones == null || listaActuaciones.isEmpty());

		return isNuevaActuacion;
	}

	/**
	 * Metodo que se encarga de aniadir el letrado de la asistencia como refuerzo en
	 * la guardia o de sustituirlo
	 * 
	 * 
	 * @param filtro
	 * @throws Exception
	 */
	@Transactional
	private void procesarSustitucionGuardia(FiltroAsistenciaItem filtro, Short idInstitucion,
			HttpServletRequest request) throws SigaExceptions {

		try {
			if ("S".equals(filtro.getIsSustituto()) && !UtilidadesString.esCadenaVacia(filtro.getIdLetradoManual())) {
				String[] datos = new String[9];
				datos[0] = filtro.getIdTurno();
				datos[1] = filtro.getIdGuardia();
				datos[2] = String.valueOf(new SimpleDateFormat("dd/MM/yyyy").parse(filtro.getDiaGuardia()).getTime()); // Fecha
																														// desde
																														// la
																														// que
																														// se
																														// sustituye
				datos[3] = filtro.getIdLetradoGuardia(); // Letrado al que vamos a sustituir
				datos[4] = filtro.getIdLetradoManual(); // Letrado que sustituye
				datos[5] = String.valueOf(new SimpleDateFormat("dd/MM/yyyy").parse(filtro.getDiaGuardia()).getTime()); // Fecha
																														// de
																														// sustitucion
				datos[6] = ""; // Comentario
				datos[7] = "N"; // Salto o compensacion
				datos[8] = "";
				// Sustituimos el que está de guardia por el de la asistencia
				LOGGER.info("procesarSustitucionGuardia() / Pendiente de implementar, se sustituye letrado de guardia");
				UpdateResponseDTO updateResponseDTO = guardiasColegiadoServiceImpl.sustituirGuardiaColeg(datos,
						request);
			} else {

				LOGGER.info(
						"procesarSustitucionGuardia() / Pendiente de implementar, se añade el letrado de la asistencia como refuerzo en la guardia");
				TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem = new TarjetaAsistenciaResponseItem();
				tarjetaAsistenciaResponseItem.setIdTurno(filtro.getIdTurno());
				tarjetaAsistenciaResponseItem.setIdGuardia(filtro.getIdGuardia());
				tarjetaAsistenciaResponseItem.setFechaAsistencia(filtro.getDiaGuardia() + " 00:00");
				tarjetaAsistenciaResponseItem.setIdLetradoGuardia(filtro.getIdLetradoGuardia());
				procesaGuardiasColegiado(tarjetaAsistenciaResponseItem, idInstitucion);

			}

		} catch (Exception e) {
			throw new SigaExceptions(e, "Error al añadir como refuerzo o sustituir el letrado de guardia : " + e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchAsistenciasByIdSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchAsistenciasByIdSolicitud() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
					scsAsistenciaExample.createCriteria().andIdsolicitudcentralitaEqualTo(Integer.valueOf(idSolicitud))
							.andIdinstitucionEqualTo(idInstitucion);
					List<ScsAsistencia> listaAsistencias = scsAsistenciaExtendsMapper
							.selectByExample(scsAsistenciaExample);
					if (listaAsistencias != null && !listaAsistencias.isEmpty()) {

						List<TarjetaAsistenciaResponseItem> listaAsistenciasResponse = listaAsistencias.stream()
								.map(asistencia -> {
									TarjetaAsistenciaResponseItem asistenciaResponse = new TarjetaAsistenciaResponseItem();
									asistenciaResponse
											.setAnioNumero(asistencia.getAnio() + "/" + asistencia.getNumero());
									asistenciaResponse.setFechaGuardia(
											new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechahora()));
									return asistenciaResponse;
								}).collect(Collectors.toList());

						for (int i = 0; i < listaAsistenciasResponse.size(); i++) {

							TarjetaAsistenciaResponseItem asistenciaResponse = listaAsistenciasResponse.get(i);
							ScsAsistencia asistenciaBBDD = listaAsistencias.get(i);

							ScsGuardiasturnoExample scsGuardiasturnoExample = new ScsGuardiasturnoExample();
							scsGuardiasturnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(asistenciaBBDD.getIdturno())
									.andIdguardiaEqualTo(asistenciaBBDD.getIdguardia());

							List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper
									.selectByExample(scsGuardiasturnoExample);

							if (guardias != null && !guardias.isEmpty()) {
								asistenciaResponse.setDescripcionGuardia(guardias.get(0).getNombre());
							}

							FichaPersonaItem colegiado = cenPersonaExtendsMapper.getColegiadoByIdPersona(
									String.valueOf(asistenciaBBDD.getIdpersonacolegiado()), idInstitucion);

							if (colegiado != null) {
								asistenciaResponse.setNombreColegiado(colegiado.getApellido1() + " "
										+ colegiado.getApellido2() + " " + colegiado.getNombre());
								asistenciaResponse.setNumeroColegiado(colegiado.getNumeroColegiado());
							}

							if (asistenciaBBDD.getIdpersonajg() != null) {
								ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
								scsPersonajgExample.createCriteria()
										.andIdpersonaEqualTo(asistenciaBBDD.getIdpersonajg())
										.andIdinstitucionEqualTo(idInstitucion);
								List<ScsPersonajg> justiciables = scsPersonajgExtendsMapper
										.selectByExample(scsPersonajgExample);

								if (justiciables != null && !justiciables.isEmpty()) {

									if (!UtilidadesString.esCadenaVacia(justiciables.get(0).getApellido2())) {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() + " "
												+ justiciables.get(0).getApellido2() + " "
												+ justiciables.get(0).getNombre());
									} else {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() + " "
												+ justiciables.get(0).getNombre());
									}
								}
							}

						}

						tarjetaAsistenciaResponseDTO.setResponseItems(listaAsistenciasResponse);

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("searchAsistenciasByIdSolicitud() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"getDefaultTipoAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getDefaultTipoAsistenciaColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					List<Short> instituciones = new ArrayList<Short>();
					instituciones.add(idInstitucion);
					instituciones.add((short) 0);
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andFechaBajaIsNull().andModuloEqualTo("SCS")
							.andParametroEqualTo("CV_TIPO_ASISTENCIAALDETENIDO").andIdinstitucionIn(instituciones);

					List<GenParametros> parametros = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					if (parametros != null && !parametros.isEmpty()) {

						idTipoAsistencia.setValor(parametros.get(0).getValor());
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("getDefaultTipoAsistenciaColegio() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar el tipo de asistencia por defecto " + e);
			error.description("Error al buscar el tipo de asistencia por defecto " + e);
		}
		return idTipoAsistencia;
	}

	@Transactional
	@Override
	public InsertResponseDTO guardarAsistencia(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias, String idAsistenciaCopy, String isLetrado) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		Error error = new Error();
		 
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					for (int i = 0; i < asistencias.size(); i++) {

						TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem = asistencias.get(i);

						if (UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getNumero())) {

							// Insertamos asistencia
							ScsAsistencia asistencia = fromTarjetaAsistenciaItemToScsAsistencia(asistencias.get(i),
									null, null, null, null, idInstitucion, usuarios.get(0));
							// Obtenemos proximo numero de una nueva asistencia
							String anioAsistencia = tarjetaAsistenciaResponseItem.getFechaAsistencia().substring(0, 11)
									.split("/")[2].trim();
							String numeroAsistencia = scsAsistenciaExtendsMapper.getNextNumeroAsistencia(anioAsistencia,
									idInstitucion);

							asistencia.setNumero(Long.valueOf(numeroAsistencia));
							asistencia.setAnio(Short.valueOf(anioAsistencia));
							if (tarjetaAsistenciaResponseItem.getIdJuzgado() != null && ! tarjetaAsistenciaResponseItem.getIdJuzgado().isEmpty())
							asistencia.setJuzgado(Long.valueOf(tarjetaAsistenciaResponseItem.getIdJuzgado()));

							// Validamos guardias colegiado
							try {
							procesaGuardiasColegiado2(tarjetaAsistenciaResponseItem, idInstitucion, isLetrado);
							}catch(Exception e) {
								if (e.equals("procesarGuardiasColegiado () / El usuario es colegiado y no existe una guardia para la fecha seleccionada. No puede continuar")) {
									error.setCode(409);
									error.setMessage("Error al guardar la asistencia: " + e);
									error.description("Error al guardar la asistencia: " + e);
								}
							}

							
							comprobacionIncompatibilidades(tarjetaAsistenciaResponseItem, idInstitucion, usuarios.get(0));
							int inserted = scsAsistenciaExtendsMapper.insertSelective(asistencia);

							// Si viene de una preasistencia, la pasamos a confirmada
							if (inserted > 0) {

								insertResponse.setId(asistencia.getAnio() + "/" + asistencia.getNumero());

								if (!UtilidadesString
										.esCadenaVacia(tarjetaAsistenciaResponseItem.getIdSolicitudCentralita())) {

									inserted = 0;
									ScsSolicitudAceptada scsSolicitudAceptada = new ScsSolicitudAceptada();
									scsSolicitudAceptada
											.setIdsolicitud(Integer.valueOf(asistencia.getIdsolicitudcentralita()));
									scsSolicitudAceptada.setEstado(Short.valueOf("1")); // Estado confirmada
									scsSolicitudAceptada.setUsumodificacion(usuarios.get(0).getIdusuario());
									scsSolicitudAceptada.setFechamodificacion(new Date());

									inserted += scsSolicitudAceptadaExtendsMapper
											.updateByPrimaryKeySelective(scsSolicitudAceptada);

									List<ComboItem> tiposActuaciones = scsTipoactuacionExtendsMapper.getComboActuacion(
											asistencia.getIdtipoasistencia().toString(),
											usuarios.get(0).getIdlenguaje(), idInstitucion);

									// Además le creamos su primera actuacion
									ScsActuacionasistencia scsActuacionasistencia = new ScsActuacionasistencia();
									scsActuacionasistencia.setAnio(asistencia.getAnio());
									scsActuacionasistencia.setNumero(asistencia.getNumero());
									scsActuacionasistencia.setIdinstitucion(idInstitucion);
									scsActuacionasistencia.setIdactuacion((long) 1);
									scsActuacionasistencia
											.setIdtipoactuacion(Short.valueOf(tiposActuaciones.get(0).getValue()));
									scsActuacionasistencia.setFecha(asistencia.getFechahora());
									scsActuacionasistencia.setIdtipoasistencia(asistencia.getIdtipoasistencia());
									scsActuacionasistencia.setIdcomisaria(asistencia.getComisaria());
									scsActuacionasistencia
											.setIdinstitucionComis(asistencia.getComisariaidinstitucion());
									scsActuacionasistencia.setIdjuzgado(asistencia.getJuzgado());
									scsActuacionasistencia.setIdinstitucionJuzg(asistencia.getJuzgadoidinstitucion());
									scsActuacionasistencia.setNig(asistencia.getNig());
									scsActuacionasistencia.setDiadespues("N");
									scsActuacionasistencia.setValidada("0");
									scsActuacionasistencia.setAcuerdoextrajudicial((short) 0);
									scsActuacionasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
									scsActuacionasistencia.setFechamodificacion(new Date());
									scsActuacionasistencia.setUsucreacion(usuarios.get(0).getIdusuario());
									scsActuacionasistencia.setFechacreacion(new Date());
									inserted += scsActuacionasistenciaExtendsMapper.insert(scsActuacionasistencia);
								}
								// Si viene relleno el idAsistenciaCopy deberemos copiar los datos de dicha
								// asistencia
								if (!UtilidadesString.esCadenaVacia(idAsistenciaCopy)) {
									duplicarAsistencia(asistencia, idAsistenciaCopy, idInstitucion);
								}

							}
						} else {
							// Actualizamos
							int affectedRows = 0;
							ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
							scsAsistenciaKey.setIdinstitucion(idInstitucion);
							scsAsistenciaKey.setAnio(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()));
							scsAsistenciaKey.setNumero(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()));
							ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper
									.selectByPrimaryKey(scsAsistenciaKey);

							scsAsistencia.setIdpersonacolegiado(
									Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
							if (!UtilidadesString
									.esCadenaVacia(tarjetaAsistenciaResponseItem.getIdTipoAsistenciaColegio())) {
								scsAsistencia.setIdtipoasistenciacolegio(
										Short.valueOf(tarjetaAsistenciaResponseItem.getIdTipoAsistenciaColegio()));
							} else {
								scsAsistencia.setIdtipoasistenciacolegio(null);
							}

							if (!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getFechaCierre())) {
								scsAsistencia.setFechacierre(new SimpleDateFormat("dd/MM/yyyy")
										.parse(tarjetaAsistenciaResponseItem.getFechaCierre()));
							} else {
								scsAsistencia.setFechacierre(null);
							}

							if (!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getFechaSolicitud())) {
								scsAsistencia.setFechasolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm")
										.parse(tarjetaAsistenciaResponseItem.getFechaSolicitud()));
							} else {
								scsAsistencia.setFechasolicitud(null);
							}
							
							// Validamos guardias colegiado
							try {
							procesaGuardiasColegiado2(tarjetaAsistenciaResponseItem, idInstitucion, isLetrado);
							}catch(Exception e) {
								if (e.equals("procesarGuardiasColegiado () / El usuario es colegiado y no existe una guardia para la fecha seleccionada. No puede continuar")) {
									error.setCode(409);
									error.setMessage("Error al guardar la asistencia: " + e);
									error.description("Error al guardar la asistencia: " + e);
								}
							}

							
							comprobacionIncompatibilidades(tarjetaAsistenciaResponseItem, idInstitucion, usuarios.get(0));
							affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

							if (affectedRows > 0) {
								insertResponse.setId(tarjetaAsistenciaResponseItem.getAnioNumero());
								insertResponse.setStatus(SigaConstants.OK);
							} else {
								insertResponse.setStatus(SigaConstants.KO);
								error.setCode(500);
								error.setDescription(
										"No se actualizó ninguna fila al intentar actualizar la asistencia");
								insertResponse.setError(error);
							}

						}

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("guardarAsistencia() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar la asistencia: " + e);
			error.description("Error al guardar la asistencia: " + e);
			insertResponse.setError(error);
			
			if (error.getCode() == 409) {
				insertResponse.setStatus("ERRORASOCIADAS");
			}
		}
		return insertResponse;
	}

	private ArrayList<ArrayList<String>> obtenerPeriodosGuardia(TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem, Integer idInstitucion) throws Exception {
		// Variables
		ArrayList arrayDiasGuardiaCGAE;
		PeriodoEfectivoItem periodoEfectivo;
		ArrayList<String> arrayDias;
		Iterator iter;
		Date fecha;
		String sFecha;
		SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<ArrayList<String>> listaFinal = new ArrayList<ArrayList<String>>();
		GuardiasTurnoItem beanGuardiasTurno1 = null;
		List<GuardiasTurnoItem> vGuardias = scsGuardiasturnoExtendsMapper.getAllDatosGuardia(idInstitucion.toString(), tarjetaAsistenciaResponseItem.getIdTurno().toString(),
				tarjetaAsistenciaResponseItem.getIdGuardia().toString());
			if (!vGuardias.isEmpty())
				 beanGuardiasTurno1 = vGuardias.get(0);
			List<String> vDiasFestivos1 = obtenerFestivosTurno(idInstitucion, Integer.parseInt(tarjetaAsistenciaResponseItem.getIdTurno()), tarjetaAsistenciaResponseItem.getFechaAsistencia(), new SimpleDateFormat("dd/MM/yyyy").parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
		try {
			// generando la matriz de periodos
			CalendarioAutomatico calendarioAutomatico = new CalendarioAutomatico(beanGuardiasTurno1, tarjetaAsistenciaResponseItem.getFechaAsistencia(),
					tarjetaAsistenciaResponseItem.getFechaAsistencia(), vDiasFestivos1);
			arrayDiasGuardiaCGAE = calendarioAutomatico.obtenerMatrizDiasGuardia();

			// recorriendo lista de periodos para anhadir en cada periodo
			// las fechas de dias de guardia en formato corto
			for (int i = 0; i < arrayDiasGuardiaCGAE.size(); i++) {
				periodoEfectivo = (PeriodoEfectivoItem) arrayDiasGuardiaCGAE.get(i);

				// obteniendo los dis por cada periodo
				arrayDias = new ArrayList<String>();
				iter = periodoEfectivo.iterator();
				while (iter.hasNext()) {
					fecha = (Date) iter.next();
					sFecha = datef.format(fecha);
					arrayDias.add(sFecha);
				}

				// insertando lista de dias (en formato corto) para el generador posterior
				// Nota: no se insertan los arrays de periodos vacios que si guarda el CGAE
				if (!arrayDias.isEmpty())
					listaFinal.add(arrayDias);
			}

			return listaFinal;

		} catch (Exception e) {
			throw new Exception(e + ": Excepcion al calcular la matriz de periodos de dias de guardias.");
		}
	}
	
	
	
	List<String> obtenerFestivosTurno(Integer idInstitucion, Integer idTurno, String fechaInicio, Date fechaFin)
			throws Exception {
		LocalDate date4 = ZonedDateTime
				.parse(fechaFin.toString(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH))
				.toLocalDate();
		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd/MM/yyyy";
		String fechaFinOk = changeDateFormat(OLD_FORMAT, NEW_FORMAT, date4.toString());
		String fechaInicioOk = changeDateFormat("dd/MM/yyyy HH:mm", NEW_FORMAT, fechaInicio);
		return scsGuardiasturnoExtendsMapper.getFestivosTurno(fechaInicioOk, fechaFinOk.toString(),
				idInstitucion.toString(), Integer.toString(2000), idTurno.toString());
	}
	
	private String changeDateFormat(String OLD_FORMAT, String NEW_FORMAT, String oldDateString) {
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(oldDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		return newDateString;
	}
	private void comprobacionIncompatibilidades(TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem,
			Short idInstitucion, AdmUsuarios usu) throws Exception {
		ArrayList<ArrayList<String>> arrayPeriodosDiasGuardiaSJCS1 = new ArrayList();
		ArrayList arrayDiasGuardia = new ArrayList();
		// si el letrado no puede ser seleccionado por alguna incompatibilidad de la guardia, se deberá crear una compensación.
		ArrayList<ArrayList<String>> listaDiasPeriodos = obtenerPeriodosGuardia(tarjetaAsistenciaResponseItem, (int)idInstitucion);
		if (listaDiasPeriodos != null && !listaDiasPeriodos.isEmpty())
			arrayPeriodosDiasGuardiaSJCS1.addAll(listaDiasPeriodos);
		for (int i = 0; i < arrayPeriodosDiasGuardiaSJCS1.size(); i++) {
			arrayDiasGuardia = (ArrayList<String>) arrayPeriodosDiasGuardiaSJCS1.get(i);
		}
		boolean incompatible = guardiasService.validarIncompatibilidadGuardia(idInstitucion.toString(), tarjetaAsistenciaResponseItem.getIdTurno(), tarjetaAsistenciaResponseItem.getIdGuardia(),
				arrayDiasGuardia, tarjetaAsistenciaResponseItem.getIdLetradoGuardia());
		if (incompatible) {
			ScsSaltoscompensaciones salto = new ScsSaltoscompensaciones();
			salto.setIdinstitucion(idInstitucion);
			salto.setIdturno(Integer.parseInt(tarjetaAsistenciaResponseItem.getIdTurno()));
			salto.setIdguardia(Integer.parseInt(tarjetaAsistenciaResponseItem.getIdGuardia()));
			salto.setIdpersona(Long.parseLong(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
			salto.setSaltoocompensacion("S");
			salto.setFecha(new Date());
			salto.setMotivos("Añadiendo letrado con incompatibilidades en una Asistencia");
			try {
				LOGGER.info("El letrado no puede ser seleccionado por incompatibilidad de la guardia, se va a crear una compensación");
				try {
					salto.setUsumodificacion(usu.getIdusuario());
					salto.setFechamodificacion(new Date());
					java.util.Date date = new java.util.Date();
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
					String fechaFormat = sdf.format(salto.getFecha());
					Long idSaltosTurno = Long
							.valueOf(getNuevoIdSaltosTurno(salto.getIdinstitucion().toString(), salto.getIdturno().toString()));
					salto.setIdsaltosturno(idSaltosTurno);
					scsSaltoscompensacionesExtendsMapper.insertManual(salto, fechaFormat);
				} catch (Exception e) {
					LOGGER.error("Error insertando nuevo salto/compensacion: " + e);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	public String getNuevoIdSaltosTurno(String idInstitucion, String idTurno) throws Exception {
		String nuevoId = "";
		try {
			nuevoId = scsGuardiasturnoExtendsMapper.getNuevoId(idInstitucion, idTurno);
			if (nuevoId == null)
				nuevoId = "1";
		} catch (Exception e) {
			throw new Exception(e + ": Excepcion en ScsSaltosCompensacionesAdm.getNuevoIdSaltosTurno().");
		}
		return nuevoId;
	}
	@Override
	public UpdateResponseDTO updateEstadoAsistencia(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					for (int i = 0; i < asistencias.size(); i++) {

						TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem = asistencias.get(i);

						if (!UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getAnio())
								&& !UtilidadesString.esCadenaVacia(tarjetaAsistenciaResponseItem.getNumero())) {

//								SE ELIMINA POR PETICIÓN EN LA INCIDENCIA 2602
//								if (("2".equals(tarjetaAsistenciaResponseItem.getEstado())
//										&& !isFacturada(tarjetaAsistenciaResponseItem, idInstitucion))
//										|| !"2".equals(tarjetaAsistenciaResponseItem.getEstado())) {
								int rowsUpdated = 0;
								ScsAsistencia scsAsistencia = new ScsAsistencia();
								scsAsistencia.setFechaestadoasistencia(new SimpleDateFormat("dd/MM/yyyy")
										.parse(tarjetaAsistenciaResponseItem.getFechaEstado()));
								scsAsistencia.setIdestadoasistencia(
										Short.valueOf(tarjetaAsistenciaResponseItem.getEstado()));
								scsAsistencia.setAnio(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()));
								scsAsistencia.setNumero(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()));
								scsAsistencia.setIdinstitucion(idInstitucion);

								rowsUpdated = scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(scsAsistencia);

								// Si es una anulacion, anulamos sus actuaciones asociadas
								if ("2".equals(tarjetaAsistenciaResponseItem.getEstado())) {

									ScsActuacionasistencia scsActuacionasistencia = new ScsActuacionasistencia();
									scsActuacionasistencia.setAnulacion((short) 1);

									ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
									scsActuacionasistenciaExample.createCriteria()
											.andIdinstitucionEqualTo(idInstitucion)
											.andAnioEqualTo(Short.valueOf(tarjetaAsistenciaResponseItem.getAnio()))
											.andNumeroEqualTo(Long.valueOf(tarjetaAsistenciaResponseItem.getNumero()))
											.andFacturadoEqualTo("0");

									rowsUpdated += scsActuacionasistenciaExtendsMapper.updateByExampleSelective(
											scsActuacionasistencia, scsActuacionasistenciaExample);

								}

								if (rowsUpdated > 0) {
									updateResponse.setStatus(SigaConstants.OK);
									updateResponse.setId(tarjetaAsistenciaResponseItem.getAnioNumero());
								} else {

									updateResponse.setStatus(SigaConstants.KO);
									error.setCode(500);
									error.setMessage("No se ha actualizado ningun registro");
									error.description("No se ha actualizado ningun registro");
									updateResponse.setError(error);

								}
//							} else {
//								updateResponse.setStatus(SigaConstants.KO);
//								error.setCode(500);
//								error.setMessage(
//										"No se ha anulado la asistencia ni sus actuaciones, ya está facturada");
//								error.description(
//										"No se ha anulado la asistencia ni sus actuaciones, ya está facturada");
//								updateResponse.setError(error);
//							}

						} else {
							updateResponse.setStatus(SigaConstants.KO);
							error.setCode(500);
							error.setMessage("El año o el numero no van informados");
							error.description("El año o el numero no van informados");
							updateResponse.setError(error);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("guardarAsistencia() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar la asistencia: " + e);
			error.description("Error al guardar la asistencia: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	/**
	 * Método que recoge toda la información necesaria para mostrarla en la ficha de
	 * asistencia
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
		String anio = "";
		String numero = "";
		if(anioNumero != null && !anioNumero.isEmpty()) {
			anio = anioNumero.split("/")[0];
			numero = anioNumero.split("/")[1];
		}
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"buscarTarjetaAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"buscarTarjetaAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && usuarios.size() > 0) {

					ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
					scsAsistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(anio))
							.andNumeroEqualTo(Long.valueOf(numero)).andIdinstitucionEqualTo(idInstitucion);
					List<ScsAsistencia> listaAsistencias = scsAsistenciaExtendsMapper
							.selectByExample(scsAsistenciaExample);
					if (listaAsistencias != null && !listaAsistencias.isEmpty()) {

						List<TarjetaAsistenciaResponseItem> listaAsistenciasResponse = listaAsistencias.stream()
								.map(asistencia -> {
									TarjetaAsistenciaResponseItem asistenciaResponse = new TarjetaAsistenciaResponseItem();
									// Datos generales
									asistenciaResponse
											.setAnioNumero(asistencia.getAnio() + "/" + asistencia.getNumero());
									asistenciaResponse.setFechaAsistencia(
											new SimpleDateFormat("dd/MM/yyyy HH:mm").format(asistencia.getFechahora()));
									asistenciaResponse.setIdGuardia(String.valueOf(asistencia.getIdguardia()));
									asistenciaResponse.setIdTurno(String.valueOf(asistencia.getIdturno()));
									asistenciaResponse
											.setIdLetradoGuardia(String.valueOf(asistencia.getIdpersonacolegiado()));
									asistenciaResponse.setAnio(String.valueOf(asistencia.getAnio()));
									asistenciaResponse.setNumero(String.valueOf(asistencia.getNumero()));
									asistenciaResponse.setEstado(String.valueOf(asistencia.getIdestadoasistencia()));
									asistenciaResponse.setNig(asistencia.getNig());
									asistenciaResponse.setNumDiligencia(asistencia.getNumerodiligencia());
									asistenciaResponse.setNumProcedimiento(asistencia.getNumeroprocedimiento());
									asistenciaResponse.setIdInstitucion(asistencia.getIdinstitucion().toString());
									if (asistencia.getIdpretension() != null) {
										asistenciaResponse
												.setIdProcedimiento(String.valueOf(asistencia.getIdpretension()));
									}
									if (asistencia.getComisaria() != null) {
										asistenciaResponse.setComisaria(String.valueOf(asistencia.getComisaria()));
									}
									if (asistencia.getJuzgado() != null) {
										asistenciaResponse.setJuzgado(String.valueOf(asistencia.getJuzgado()));
									}
									if (asistencia.getIdpersonajg() != null) {
										asistenciaResponse.setIdPersonaJg(String.valueOf(asistencia.getIdpersonajg()));
									}
									if (asistencia.getFechacierre() != null) {
										asistenciaResponse.setFechaCierre(
												new SimpleDateFormat("dd/MM/yyyy").format(asistencia.getFechacierre()));
									}
									if (asistencia.getFechasolicitud() != null) {
										asistenciaResponse.setFechaSolicitud(new SimpleDateFormat("dd/MM/yyyy HH:mm")
												.format(asistencia.getFechasolicitud()));
									}
									if (asistencia.getFechaestadoasistencia() != null) {
										asistenciaResponse.setFechaEstado(new SimpleDateFormat("dd/MM/yyyy")
												.format(asistencia.getFechaestadoasistencia()));
									}
									asistenciaResponse.setIdTipoAsistenciaColegio(
											String.valueOf(asistencia.getIdtipoasistenciacolegio()));
									return asistenciaResponse;
								}).collect(Collectors.toList());

						for (int i = 0; i < listaAsistenciasResponse.size(); i++) {

							TarjetaAsistenciaResponseItem asistenciaResponse = listaAsistenciasResponse.get(i);
							ScsAsistencia asistenciaBBDD = listaAsistencias.get(i);

							// Descripcion y nombres de Guardia, Turno, Estado Asistencia y Tipo Asistencia
							// Colegio
							ScsGuardiasturnoExample scsGuardiasturnoExample = new ScsGuardiasturnoExample();
							scsGuardiasturnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(asistenciaBBDD.getIdturno())
									.andIdguardiaEqualTo(asistenciaBBDD.getIdguardia());

							List<ScsGuardiasturno> guardias = scsGuardiasturnoExtendsMapper
									.selectByExample(scsGuardiasturnoExample);

							if (guardias != null && !guardias.isEmpty()) {
								asistenciaResponse.setDescripcionGuardia(guardias.get(0).getNombre());
							}

							ScsTurnoExample scsTurnoExample = new ScsTurnoExample();
							scsTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(asistenciaBBDD.getIdturno());

							List<ScsTurno> turnos = scsTurnosExtendsMapper.selectByExample(scsTurnoExample);

							if (turnos != null && !turnos.isEmpty()) {
								asistenciaResponse.setDescripcionTurno(turnos.get(0).getNombre());
							}

							if (asistenciaBBDD.getIdestadoasistencia() != null) {

								List<ComboItem> estadosAsistencia = scsEstadoasistenciaExtendsMapper
										.comboEstadosAsistencia(usuarios.get(0).getIdlenguaje());

								String descripcionEstado = estadosAsistencia.stream()
										.filter(estado -> estado.getValue()
												.equals(asistenciaBBDD.getIdestadoasistencia().toString()))
										.findFirst().get().getLabel();

								asistenciaResponse.setDescripcionEstado(descripcionEstado);
							}

							if (asistenciaBBDD.getIdtipoasistenciacolegio() != null) {
								List<TiposAsistenciaItem> tiposAsistenciaColegio = scsTipoAsistenciaColegioExtendsMapper
										.getTiposAsistenciaColegiado(idInstitucion,
												Integer.valueOf(usuarios.get(0).getIdlenguaje()),
												guardias.get(0).getIdtipoguardia());
								String descripcionTipoAsistencia = tiposAsistenciaColegio.stream()
										.filter(tipoAsistencia -> tipoAsistencia.getIdtipoasistenciacolegio()
												.equals(asistenciaBBDD.getIdtipoasistenciacolegio().toString()))
										.findFirst().orElse(new TiposAsistenciaItem()).getTipoasistencia();

								asistenciaResponse.setDescripcionTipoAsistenciaColegio(descripcionTipoAsistencia);
							}

							// Letrado asociado
							FichaPersonaItem colegiado = cenPersonaExtendsMapper.getColegiadoByIdPersona(
									String.valueOf(asistenciaBBDD.getIdpersonacolegiado()), idInstitucion);

							if (colegiado != null) {
								asistenciaResponse.setNombreColegiado(colegiado.getApellido1() + " "
										+ colegiado.getApellido2() + " " + colegiado.getNombre());
								asistenciaResponse.setNumeroColegiado(colegiado.getNumeroColegiado());
							}

							// Asistido
							if (asistenciaBBDD.getIdpersonajg() != null) {
								ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
								scsPersonajgExample.createCriteria()
										.andIdpersonaEqualTo(asistenciaBBDD.getIdpersonajg())
										.andIdinstitucionEqualTo(idInstitucion);
								List<ScsPersonajg> justiciables = scsPersonajgExtendsMapper
										.selectByExample(scsPersonajgExample);

								if (justiciables != null && !justiciables.isEmpty()) {

									if (!UtilidadesString.esCadenaVacia(justiciables.get(0).getApellido2())) {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() + " "
												+ justiciables.get(0).getApellido2() + " "
												+ justiciables.get(0).getNombre());
									} else {
										asistenciaResponse.setAsistido(justiciables.get(0).getApellido1() + " "
												+ justiciables.get(0).getNombre());
									}

									asistenciaResponse.setNif(justiciables.get(0).getNif());
									asistenciaResponse.setNombre(justiciables.get(0).getNombre());
									asistenciaResponse.setApellido1(justiciables.get(0).getApellido1());
									asistenciaResponse.setApellido2(justiciables.get(0).getApellido2());

								}
							}

							// Datos tarjeta resumen sobre actuaciones
							ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
							scsActuacionasistenciaExample.createCriteria().andAnioEqualTo(Short.valueOf(anio))
									.andNumeroEqualTo(Long.valueOf(numero)).andIdinstitucionEqualTo(idInstitucion);

							List<ScsActuacionasistencia> actuaciones = scsActuacionasistenciaExtendsMapper
									.selectByExample(scsActuacionasistenciaExample);

							if (actuaciones != null && !actuaciones.isEmpty()) {

								asistenciaResponse.setNumeroActuaciones(String.valueOf(actuaciones.size()));

								boolean validadas = actuaciones.stream()
										.allMatch(actuacion -> "1".equals(actuacion.getValidada()));
								String numJustificadas = String.valueOf(actuaciones.stream()
										.filter(actuacion -> actuacion.getFechajustificacion() != null).count());
								String numValidadas = String.valueOf(actuaciones.stream()
										.filter(actuacion -> "1".equals(actuacion.getValidada())).count());
								String numFacturadas = String.valueOf(actuaciones.stream()
										.filter(actuacion -> "1".equals(actuacion.getFacturado())).count());
								asistenciaResponse.setNumJustificadas(numJustificadas);
								asistenciaResponse.setNumValidadas(numValidadas);
								asistenciaResponse.setNumFacturadas(numFacturadas);

								if (validadas) {
									asistenciaResponse.setValidada("SI");
								} else {
									asistenciaResponse.setValidada("NO");
								}

								if (actuaciones.size() == 1) {
									asistenciaResponse.setActuaciones(new ArrayList<ActuacionAsistenciaItem>());
									List<ActuacionAsistenciaItem> actuacionesItems = scsAsistenciaExtendsMapper
											.searchActuaciones(asistenciaResponse.getAnio(),
													asistenciaResponse.getNumero(), idInstitucion,
													Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(), "N");
									if (actuacionesItems != null && !actuacionesItems.isEmpty()) {
										asistenciaResponse.getActuaciones().add(actuacionesItems.get(0));
									}
								}
							} else {
								asistenciaResponse.setValidada("NO");
								asistenciaResponse.setNumeroActuaciones("0");
							}

							// Primer contrario
							List<ListaContrarioJusticiableItem> contrarios = scsAsistenciaExtendsMapper
									.searchListaContrarios(anioNumero, idInstitucion, false);
							if (contrarios != null && !contrarios.isEmpty()) {
								asistenciaResponse.setNumContrarios(String.valueOf(contrarios.size()));
								asistenciaResponse.setPrimerContrario(contrarios.get(0));
							}

							// Primera Relacion
							List<RelacionesItem> relaciones = scsAsistenciaExtendsMapper.searchRelaciones(anio, numero,
									idInstitucion, Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(), 2);
							if (relaciones != null && !relaciones.isEmpty()) {
								asistenciaResponse.setPrimeraRelacion(relaciones.get(0));
							}

							// Delitos
							ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
							scsDelitosasistenciaExample.createCriteria().andNumeroEqualTo(asistenciaBBDD.getNumero())
									.andAnioEqualTo(asistenciaBBDD.getAnio())
									.andIdinstitucionEqualTo(asistenciaBBDD.getIdinstitucion());
							List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper
									.selectByExample(scsDelitosasistenciaExample);
							if (delitos != null && !delitos.isEmpty()) {
								List<String> idDelitos = new ArrayList<>();
								delitos.forEach(delito -> {
									idDelitos.add(String.valueOf(delito.getIddelito()));
								});
								asistenciaResponse.setDelitos(idDelitos);
							}
							// Numero de documentos
							ScsDocumentacionasiExample scsDocumentacionasiExample = new ScsDocumentacionasiExample();
							scsDocumentacionasiExample.createCriteria()
									.andIdinstitucionEqualTo(asistenciaBBDD.getIdinstitucion())
									.andAnioEqualTo(asistenciaBBDD.getAnio())
									.andNumeroEqualTo(asistenciaBBDD.getNumero());
							List<ScsDocumentacionasi> documentos = scsDocumentacionasiMapper
									.selectByExample(scsDocumentacionasiExample);

							if (documentos != null && !documentos.isEmpty()) {
								asistenciaResponse.setNumDocumentos(String.valueOf(documentos.size()));
							}

							// Baremo fuera de guardia
							List<Long> hitosFueraGuardia = new ArrayList<>();
							hitosFueraGuardia.add((long) 6);
							hitosFueraGuardia.add((long) 9);
							hitosFueraGuardia.add((long) 24);
							hitosFueraGuardia.add((long) 25);
							ScsHitofacturableguardiaExample scsHitofacturableguardiaExample = new ScsHitofacturableguardiaExample();
							scsHitofacturableguardiaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdturnoEqualTo(asistenciaBBDD.getIdturno())
									.andIdguardiaEqualTo(asistenciaBBDD.getIdguardia()).andIdhitoIn(hitosFueraGuardia);
							List<ScsHitofacturableguardia> hitos = scsHitofacturableguardiaExtendsMapper
									.selectByExample(scsHitofacturableguardiaExample);
							if (hitos != null && !hitos.isEmpty()) {
								asistenciaResponse.setDiaDespuesDisabled(false);
							} else {
								asistenciaResponse.setDiaDespuesDisabled(true);
							}
						}

						tarjetaAsistenciaResponseDTO.setResponseItems(listaAsistenciasResponse);

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("buscarTarjetaAsistencias() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al traer las asistencias asociadas a la solicitud: " + e);
			error.description("Error al traer las asistencias asociadas a la solicitud: " + e);
			tarjetaAsistenciaResponseDTO.setError(error);
		}
		return tarjetaAsistenciaResponseDTO;
	}

	/**
	 *
	 * Método que procesa los delitos seleccionados en la tabla de Asistencias
	 * Expres para insertarlos en SCS_DELITOSASISTENCIA
	 *
	 * @param tarjetaAsistenciaItem
	 * @param anioParam
	 * @param numeroParam
	 * @param idInstitucion
	 * @param usuario
	 */
	@Transactional
	private void procesarDelitosAsistenciaExpress(TarjetaAsistenciaResponse2Item tarjetaAsistenciaItem,
			String anioParam, String numeroParam, Short idInstitucion, AdmUsuarios usuario) {

		String anio = "";
		String numero = "";

		if (UtilidadesString.esCadenaVacia(anioParam) && UtilidadesString.esCadenaVacia(numeroParam)) {
			anio = tarjetaAsistenciaItem.getAnio();
			numero = tarjetaAsistenciaItem.getNumero();
		} else {
			anio = anioParam;
			numero = numeroParam;
		}
		// Borramos los asociados e insertamos el nuevo si ha escogido alguno
		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(Short.valueOf(anio)).andNumeroEqualTo(Long.valueOf(numero));

		scsDelitosasistenciaMapper.deleteByExample(scsDelitosasistenciaExample);
		if (tarjetaAsistenciaItem.getIdDelito() != null) {
			tarjetaAsistenciaItem.getIdDelito().forEach(delito -> {
				String anio2 = "";
				String numero2 = "";

				if (!UtilidadesString.esCadenaVacia(delito)) {

					if (UtilidadesString.esCadenaVacia(anioParam) && UtilidadesString.esCadenaVacia(numeroParam)) {
						anio2 = tarjetaAsistenciaItem.getAnio();
						numero2 = tarjetaAsistenciaItem.getNumero();
					} else {
						anio2 = anioParam;
						numero2 = numeroParam;
					}

					ScsDelitosasistencia scsDelitosasistencia = new ScsDelitosasistencia();
					int affectedRows = 0;
					scsDelitosasistencia.setAnio(Short.valueOf(anio2));
					scsDelitosasistencia.setNumero(Long.valueOf(numero2));
					scsDelitosasistencia.setIdinstitucion(idInstitucion);
					scsDelitosasistencia.setIddelito(Short.valueOf(delito));
					scsDelitosasistencia.setFechamodificacion(new Date());
					scsDelitosasistencia.setUsumodificacion(usuario.getIdusuario());
					affectedRows = scsDelitosasistenciaMapper.insertSelective(scsDelitosasistencia);
				}
			});
		}

	}
	/**
	 * Método que procesa las guardias de colegiado y las cabeceras de guardia al
	 * crear una asistencia
	 *
	 *
	 * @param tarjetaAsistenciaResponseItem
	 */
	private void procesaGuardiasColegiado(TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem,
			Short idInstitucion) throws SigaExceptions {

		try {

			int affectedRows = 0;
			// Buscamos las guardias de colegiado para el letrado seleccionado
			List<ScsGuardiascolegiado> guardiascolegiados = scsGuardiascolegiadoExtendsMapper
					.getGuardiasColegiadoNoSustitucion(tarjetaAsistenciaResponseItem, idInstitucion);

			// Si no hay guardias de colegiado creadas
			if (guardiascolegiados == null || guardiascolegiados.isEmpty()) {

				// Nos devuelve el idpersona de la persona que esta en guardia para esa fecha
				guardiascolegiados = scsGuardiascolegiadoExtendsMapper
						.getGuardiasColegiadoEnFecha(tarjetaAsistenciaResponseItem, idInstitucion);
				// Buscamos sus respectivas guardias de colegiado
				if (guardiascolegiados != null && !guardiascolegiados.isEmpty()) {
					guardiascolegiados = scsGuardiascolegiadoExtendsMapper.getGuardiasColegiado(
							tarjetaAsistenciaResponseItem, idInstitucion,
							String.valueOf(guardiascolegiados.get(0).getIdpersona()));
				}
			}

			// Si hay guardias de colegiado
			if (guardiascolegiados != null && !guardiascolegiados.isEmpty()) {

				ScsGuardiascolegiado firstGuardiaColegiado = guardiascolegiados.get(0);
				ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
				scsCabeceraguardias.setIdpersona(firstGuardiaColegiado.getIdpersona());
				scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
				scsCabeceraguardias.setIdinstitucion(idInstitucion);
				scsCabeceraguardias.setFechainicio(firstGuardiaColegiado.getFechainicio());
				scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
				scsCabeceraguardias = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(scsCabeceraguardias);

				// Si el letrado estaba ya de guardia, validamos las cabeceras
				if (firstGuardiaColegiado.getIdpersona().longValue() == Long
						.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia())) {

					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);
					affectedRows += scsCabeceraguardiasExtendsMapper.updateByPrimaryKeySelective(scsCabeceraguardias);

					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / El letrado de la asistencia estaba de guardia pero no se actualizo ninguna fila");
					}

					// Si no, lo aniadimos como refuerzo
				} else {

					// Recorremos para setear el idPersona del colegiado que tentemos que
					// insertar(el que hace la asistencia)
					for (ScsGuardiascolegiado scsGuardiascolegiado : guardiascolegiados) {
						// Para cada guardia colegiado accedemos a sus cabeceras de guardia, a las que
						// luego habra que setear del idPersona y ponerlo en la ultima posicion(por eso
						// se ha obtenido el ultimo de la fila)

						scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");
						scsGuardiascolegiado.setFacturado("N");
						scsGuardiascolegiado.setIdfacturacion(null);
						scsGuardiascolegiado.setPagado("N");
						scsGuardiascolegiado
								.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
						scsGuardiascolegiado.setFechamodificacion(new Date());
						scsGuardiascolegiado.setUsumodificacion(0);

					}

					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short) (scsCabeceraguardias.getPosicion().shortValue() + 1));
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechavalidacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					guardiascolegiados.stream().forEach(scsGuardiascolegiado -> {
						scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);
					});
					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}
				// No hay guardias de colegiado creadas para la fecha, se crean
			} else {

				ScsCalendarioguardiasExample example = new ScsCalendarioguardiasExample();

				example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()))
						.andIdguardiaEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()))
						.andFechainicioLessThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy")
								.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()))
						.andFechainicioGreaterThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy")
								.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));

				List<ScsCalendarioguardias> scsCalendarioguardias = scsCalendarioguardiasMapper
						.selectByExample(example);

				if (scsCalendarioguardias != null && !scsCalendarioguardias.isEmpty()) {

					ScsGuardiascolegiado scsGuardiascolegiado = new ScsGuardiascolegiado();
					scsGuardiascolegiado.setIdinstitucion(idInstitucion);
					scsGuardiascolegiado.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsGuardiascolegiado.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsGuardiascolegiado.setFechainicio(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado.setFechafin(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado
							.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsGuardiascolegiado.setDiasguardia((long) 1);
					scsGuardiascolegiado.setDiasacobrar((long) 1);
					scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");

					scsGuardiascolegiado.setReserva("N");
					scsGuardiascolegiado.setFacturado("N");
					scsGuardiascolegiado.setIdfacturacion(null);
					scsGuardiascolegiado.setPagado("N");
					scsGuardiascolegiado.setFechamodificacion(new Date());
					scsGuardiascolegiado.setUsumodificacion(0);

					ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
					scsCabeceraguardias.setIdinstitucion(idInstitucion);
					scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsCabeceraguardias.setIdcalendarioguardias(scsCalendarioguardias.get(0).getIdcalendarioguardias());

					scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsCabeceraguardias.setFechainicio(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setFechaFin(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short) 1);
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setSustituto("0");
					scsCabeceraguardias.setFechaalta(new Date());

					// Las metemos validadas
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechavalidacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					affectedRows += scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);

					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}
	

			}
			
			

		} catch (Exception e) {
			LOGGER.error(
					"procesarGuardiasColegiado () / Error al procesar las guardias de colegiado durante la asistencia, "
							+ e,
					e);
			throw new SigaExceptions(e,
					"procesarGuardiasColegiado () / Error al procesar las guardias de colegiado durante la asistencia, "
							+ e);
		}

	}

	/**
	 * Método que procesa las guardias de colegiado y las cabeceras de guardia al
	 * crear una asistencia
	 *
	 *
	 * @param tarjetaAsistenciaResponseItem
	 */
	private void procesaGuardiasColegiado2(TarjetaAsistenciaResponseItem tarjetaAsistenciaResponseItem,
			Short idInstitucion, String isLetrado) throws SigaExceptions {

		try {

			int affectedRows = 0;
			// Buscamos las guardias de colegiado para el letrado seleccionado
			List<ScsGuardiascolegiado> guardiascolegiados = scsGuardiascolegiadoExtendsMapper
					.getGuardiasColegiadoNoSustitucion(tarjetaAsistenciaResponseItem, idInstitucion);

			// Si no hay guardias de colegiado creadas
			if (guardiascolegiados == null || guardiascolegiados.isEmpty()) {

				// Nos devuelve el idpersona de la persona que esta en guardia para esa fecha
				guardiascolegiados = scsGuardiascolegiadoExtendsMapper
						.getGuardiasColegiadoEnFecha(tarjetaAsistenciaResponseItem, idInstitucion);
				// Buscamos sus respectivas guardias de colegiado
				if (guardiascolegiados != null && !guardiascolegiados.isEmpty()) {
					guardiascolegiados = scsGuardiascolegiadoExtendsMapper.getGuardiasColegiado(
							tarjetaAsistenciaResponseItem, idInstitucion,
							String.valueOf(guardiascolegiados.get(0).getIdpersona()));
				}
			}

			// Si hay guardias de colegiado
			if (guardiascolegiados != null && !guardiascolegiados.isEmpty()) {

				ScsGuardiascolegiado firstGuardiaColegiado = guardiascolegiados.get(0);
				ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
				scsCabeceraguardias.setIdpersona(firstGuardiaColegiado.getIdpersona());
				scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
				scsCabeceraguardias.setIdinstitucion(idInstitucion);
				scsCabeceraguardias.setFechainicio(firstGuardiaColegiado.getFechainicio());
				scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
				scsCabeceraguardias = scsCabeceraguardiasExtendsMapper.selectByPrimaryKey(scsCabeceraguardias);

				// Si el letrado estaba ya de guardia, validamos las cabeceras
				if (firstGuardiaColegiado.getIdpersona().longValue() == Long
						.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia())) {

					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);
					affectedRows += scsCabeceraguardiasExtendsMapper.updateByPrimaryKeySelective(scsCabeceraguardias);

					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / El letrado de la asistencia estaba de guardia pero no se actualizo ninguna fila");
					}

					// Si no, lo aniadimos como refuerzo
				} else {

					// Recorremos para setear el idPersona del colegiado que tentemos que
					// insertar(el que hace la asistencia)
					for (ScsGuardiascolegiado scsGuardiascolegiado : guardiascolegiados) {
						// Para cada guardia colegiado accedemos a sus cabeceras de guardia, a las que
						// luego habra que setear del idPersona y ponerlo en la ultima posicion(por eso
						// se ha obtenido el ultimo de la fila)

						scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");
						scsGuardiascolegiado.setFacturado("N");
						scsGuardiascolegiado.setIdfacturacion(null);
						scsGuardiascolegiado.setPagado("N");
						scsGuardiascolegiado
								.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
						scsGuardiascolegiado.setFechamodificacion(new Date());
						scsGuardiascolegiado.setUsumodificacion(0);

					}

					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short) (scsCabeceraguardias.getPosicion().shortValue() + 1));
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechavalidacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					guardiascolegiados.stream().forEach(scsGuardiascolegiado -> {
						scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);
					});
					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}
				// No hay guardias de colegiado creadas para la fecha, se crean
			} else {
				if (!Boolean.parseBoolean(isLetrado)) {

				ScsCalendarioguardiasExample example = new ScsCalendarioguardiasExample();

				example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()))
						.andIdguardiaEqualTo(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()))
						.andFechainicioLessThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy")
								.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()))
						.andFechainicioGreaterThanOrEqualTo(new SimpleDateFormat("dd/MM/yyyy")
								.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));

				List<ScsCalendarioguardias> scsCalendarioguardias = scsCalendarioguardiasMapper
						.selectByExample(example);

				if (scsCalendarioguardias != null && !scsCalendarioguardias.isEmpty()) {

					ScsGuardiascolegiado scsGuardiascolegiado = new ScsGuardiascolegiado();
					scsGuardiascolegiado.setIdinstitucion(idInstitucion);
					scsGuardiascolegiado.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsGuardiascolegiado.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsGuardiascolegiado.setFechainicio(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado.setFechafin(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsGuardiascolegiado
							.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsGuardiascolegiado.setDiasguardia((long) 1);
					scsGuardiascolegiado.setDiasacobrar((long) 1);
					scsGuardiascolegiado.setObservaciones("Inclusión en guardia por refuerzo");

					scsGuardiascolegiado.setReserva("N");
					scsGuardiascolegiado.setFacturado("N");
					scsGuardiascolegiado.setIdfacturacion(null);
					scsGuardiascolegiado.setPagado("N");
					scsGuardiascolegiado.setFechamodificacion(new Date());
					scsGuardiascolegiado.setUsumodificacion(0);

					ScsCabeceraguardias scsCabeceraguardias = new ScsCabeceraguardias();
					scsCabeceraguardias.setIdinstitucion(idInstitucion);
					scsCabeceraguardias.setIdturno(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdTurno()));
					scsCabeceraguardias.setIdcalendarioguardias(scsCalendarioguardias.get(0).getIdcalendarioguardias());

					scsCabeceraguardias.setIdguardia(Integer.valueOf(tarjetaAsistenciaResponseItem.getIdGuardia()));
					scsCabeceraguardias.setFechainicio(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setFechaFin(new SimpleDateFormat("dd/MM/yyyy")
							.parse(tarjetaAsistenciaResponseItem.getFechaAsistencia()));
					scsCabeceraguardias.setIdpersona(Long.valueOf(tarjetaAsistenciaResponseItem.getIdLetradoGuardia()));
					scsCabeceraguardias.setPosicion((short) 1);
					scsCabeceraguardias.setFechamodificacion(new Date());
					scsCabeceraguardias.setSustituto("0");
					scsCabeceraguardias.setFechaalta(new Date());

					// Las metemos validadas
					scsCabeceraguardias.setValidado("1");
					scsCabeceraguardias.setFechavalidacion(new Date());
					scsCabeceraguardias.setUsumodificacion(0);

					affectedRows += scsCabeceraguardiasExtendsMapper.insertSelective(scsCabeceraguardias);
					affectedRows += scsGuardiascolegiadoExtendsMapper.insertSelective(scsGuardiascolegiado);

					if (affectedRows <= 0) {
						LOGGER.error(
								"procesaGuardiasColegiado() / Se intento aniadir el letrado como refuerzo en la guardia pero no se inserto nada");
					}
				}
			}else {
				throw new Exception("procesarGuardiasColegiado () / El usuario es colegiado y no existe una guardia para la fecha seleccionada. No puede continuar");
			}

			}
			
			

		} catch (Exception e) {
			LOGGER.error(
					"procesarGuardiasColegiado () / Error al procesar las guardias de colegiado durante la asistencia, "
							+ e,
					e);
			throw new SigaExceptions(e,
					"procesarGuardiasColegiado () / Error al procesar las guardias de colegiado durante la asistencia, "
							+ e);
		}

	}

	@Override
	public UpdateResponseDTO asociarJusticiable(HttpServletRequest request, JusticiableItem justiciable,
			String anioNumero, String actualizaDatos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (!UtilidadesString.esCadenaVacia(anioNumero)) {

						ScsPersonajg scsPersonajg = new ScsPersonajg();
						if (!UtilidadesString.esCadenaVacia(justiciable.getIdPersona())) {

							scsPersonajg.setIdinstitucion(Short.valueOf(justiciable.getIdInstitucion()));
							scsPersonajg.setIdpersona(Long.valueOf(justiciable.getIdPersona()));
							scsPersonajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajg);

							scsPersonajg.setNombre(justiciable.getNombre());
							scsPersonajg.setApellido1(justiciable.getApellido1());
							scsPersonajg.setApellido2(justiciable.getApellido2());
							scsPersonajg.setTipopersonajg(justiciable.getTipoPersonajg());
							scsPersonajg.setFechamodificacion(new Date());

						} else {

							ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();

							scsPersonajgExample.createCriteria().andNifEqualTo(justiciable.getNif().toUpperCase())
									.andIdinstitucionEqualTo(idInstitucion);

							scsPersonajgExample.setOrderByClause("FECHAMODIFICACION DESC");

							List<ScsPersonajg> personajgs = scsPersonajgExtendsMapper
									.selectByExample(scsPersonajgExample);

							if (personajgs != null && !personajgs.isEmpty()) {
								scsPersonajg = personajgs.get(0);
								scsPersonajg.setNif(justiciable.getNif());
								scsPersonajg.setNombre(justiciable.getNombre());
								scsPersonajg.setApellido1(justiciable.getApellido1());
								scsPersonajg.setApellido2(justiciable.getApellido2());
								scsPersonajg.setFechamodificacion(new Date());
								justiciable.setIdPersona(String.valueOf(scsPersonajg.getIdpersona()));
							}
						}

						// Actualizamos datos del justiciable
						if (!UtilidadesString.esCadenaVacia(actualizaDatos) && "S".equals(actualizaDatos)) {

							affectedRows += scsPersonajgExtendsMapper.updateByPrimaryKeySelective(scsPersonajg);
							// Duplicamos el justiciable
						} else if ("N".equals(actualizaDatos)) {

							String newIdPersona = scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion).getNewId();
							scsPersonajg.setIdpersona(Long.valueOf(newIdPersona) + 1);
							affectedRows += scsPersonajgExtendsMapper.insertSelective(scsPersonajg);
							justiciable.setIdPersona(String.valueOf(scsPersonajg.getIdpersona()));

						}

						ScsAsistencia scsAsistencia = new ScsAsistencia();
						scsAsistencia.setIdpersonajg(Long.valueOf(justiciable.getIdPersona()));
						ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
						scsAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]));

						affectedRows += scsAsistenciaExtendsMapper.updateByExampleSelective(scsAsistencia,
								scsAsistenciaExample);

					}

					if (affectedRows <= 0) {
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
						updateResponse.setId(anioNumero);
					} else {
						updateResponse.setStatus(SigaConstants.OK);
						updateResponse.setId(anioNumero);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarJusticiable() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar el justiciable: " + e);
			error.description("Error al asociar el justiciable: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	@Override
	public UpdateResponseDTO desasociarJusticiable(HttpServletRequest request, JusticiableItem justiciable,
			String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desasociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desasociarJusticiable() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (!UtilidadesString.esCadenaVacia(anioNumero)) {

						ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
						scsAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]));

						List<ScsAsistencia> scsAsistencias = scsAsistenciaExtendsMapper
								.selectByExample(scsAsistenciaExample);

						if (scsAsistencias != null && !scsAsistencias.isEmpty()) {
							ScsAsistencia scsAsistencia = scsAsistencias.get(0);
							scsAsistencia.setIdpersonajg(null);
							affectedRows += scsAsistenciaExtendsMapper.updateByExample(scsAsistencia,
									scsAsistenciaExample);
						}
					}

					if (affectedRows <= 0) {
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
						updateResponse.setId(anioNumero);
					} else {
						updateResponse.setStatus(SigaConstants.OK);
						updateResponse.setId(anioNumero);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("desasociarJusticiable() / ERROR: " + e.getMessage(), e);
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
	public List<ListaContrarioJusticiableItem> searchListaContrarios(HttpServletRequest request, String anioNumero,
			boolean mostrarHistorico) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ListaContrarioJusticiableItem> listaContrarios = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchListaContrarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListaContrarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (!UtilidadesString.esCadenaVacia(anioNumero)) {

						listaContrarios = scsAsistenciaExtendsMapper.searchListaContrarios(anioNumero, idInstitucion,
								mostrarHistorico);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("searchListaContrarios() / ERROR: " + e.getMessage(), e);
		}
		return listaContrarios;
	}

	/**
	 * Metodo encargado de insertar en la tabla SCS_CONTRARIOSASISTENCIA para
	 * asociar un justiciable contrario (SCS_PERSONAJG) a la asistencia
	 * (SCS_ASISTENCIA)
	 *
	 * @param request
	 * @param justiciables
	 * @param anioNumero
	 * @return
	 */
	@Override
	public InsertResponseDTO asociarContrario(HttpServletRequest request, List<JusticiableItem> justiciables,
			String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					for (int i = 0; i < justiciables.size(); i++) {
						ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
						scsContrariosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
								.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
								.andIdpersonaEqualTo(Long.valueOf(justiciables.get(i).getIdPersona()));

						List<ScsContrariosasistencia> contrariosYaExistentes = scsContrariosasistenciaMapper
								.selectByExample(scsContrariosasistenciaExample);

						if (contrariosYaExistentes != null && !contrariosYaExistentes.isEmpty()) { // Si el contrario ya
																									// estaba asociado,
																									// es que estaba
																									// dado de baja, por
																									// lo que seteamos
																									// fechaBaja a null
							ScsContrariosasistencia contrario = contrariosYaExistentes.get(0);
							contrario.setFechabaja(null);
							affectedRows += scsContrariosasistenciaMapper.updateByExample(contrario,
									scsContrariosasistenciaExample);
						} else { // Es un contrario nuevo, insertamos

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
					if (affectedRows <= 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha insertado ningún registro");
						insertResponseDTO.setError(error);
						insertResponseDTO.setId(anioNumero);
					} else {
						insertResponseDTO.setStatus(SigaConstants.OK);
						insertResponseDTO.setId(anioNumero);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarContrario() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar el contrario: " + e);
			error.description("Error al asociar el contrario: " + e);
			insertResponseDTO.setError(error);
		}
		return insertResponseDTO;
	}

	/**
	 * Metodo encargado de insertar en la tabla SCS_CONTRARIOSASISTENCIA para
	 * asociar un justiciable contrario (SCS_PERSONAJG) a la asistencia
	 * (SCS_ASISTENCIA)
	 *
	 * @param request
	 * @param contrarios
	 * @param anioNumero
	 * @return
	 */
	@Override
	public UpdateResponseDTO desasociarContrario(HttpServletRequest request,
			List<ListaContrarioJusticiableItem> contrarios, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desasociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desasociarContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (!UtilidadesString.esCadenaVacia(anioNumero) && contrarios != null && !contrarios.isEmpty()) {

						for (int i = 0; i < contrarios.size(); i++) {
							ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();

							scsContrariosasistenciaExample.createCriteria()
									.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
									.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
									.andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(Long.valueOf(contrarios.get(i).getIdPersona()));

							ScsContrariosasistencia scsContrariosasistencia = new ScsContrariosasistencia();
							scsContrariosasistencia.setFechabaja(new Date());
							affectedRows += scsContrariosasistenciaMapper
									.updateByExampleSelective(scsContrariosasistencia, scsContrariosasistenciaExample);

						}
					}

					if (affectedRows <= 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha borrado ningún registro");
						updateResponseDTO.setError(error);
					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("desasociarContrario() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null) {
						TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem = new TarjetaDefensaJuridicaItem();
						tarjetaDefensaJuridicaItem.setNig(scsAsistencia.getNig());
						tarjetaDefensaJuridicaItem.setComentariosDelitos(scsAsistencia.getDelitosimputados());
						tarjetaDefensaJuridicaItem.setObservaciones(scsAsistencia.getDatosdefensajuridica());
						tarjetaDefensaJuridicaItem.setNumDiligencia(scsAsistencia.getNumerodiligencia());
						tarjetaDefensaJuridicaItem.setNumProcedimiento(scsAsistencia.getNumeroprocedimiento());
						if (scsAsistencia.getIdpretension() != null) {
							tarjetaDefensaJuridicaItem.setIdProcedimiento(scsAsistencia.getIdpretension().toString());
						}
						if (scsAsistencia.getComisaria() != null) {
							tarjetaDefensaJuridicaItem.setIdComisaria(scsAsistencia.getComisaria().toString());
						} else if (scsAsistencia.getJuzgado() != null) {
							tarjetaDefensaJuridicaItem.setIdJuzgado(scsAsistencia.getJuzgado().toString());
						}

						ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
						scsDelitosasistenciaExample.createCriteria().andAnioEqualTo(scsAsistencia.getAnio())
								.andNumeroEqualTo(scsAsistencia.getNumero()).andIdinstitucionEqualTo(idInstitucion);

						List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper
								.selectByExample(scsDelitosasistenciaExample);

						if (delitos != null && !delitos.isEmpty()) {
							List<String> idDelitos = new ArrayList<>();
							delitos.stream().forEach(delito -> {
								idDelitos.add(delito.getIddelito().toString());
							});
							tarjetaDefensaJuridicaItem.setIdDelitos(idDelitos);
						}

						tarjetaDefensaJuridicaDTO.getResponseItems().add(tarjetaDefensaJuridicaItem);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("searchTarjetaDefensaJuridica() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar los datos de la defensa juridica: " + e);
			error.description("Error al buscar los datos de la defensa juridica: " + e);
			tarjetaDefensaJuridicaDTO.setError(error);
		}
		return tarjetaDefensaJuridicaDTO;
	}

	/**
	 * Metodo encargado de guardar los datos juridicos de la asistencia en
	 * SCS_ASISTENCIA. Dependiendo de si elegimos comisaria o juzgado, se modificara
	 * tambien la tabla SCS_CARACTASISTENCIA.
	 *
	 * @param request
	 * @param tarjetaDefensaJuridicaItem
	 * @param anioNumero
	 * @return
	 */
	@Override
	public UpdateResponseDTO guardarTarjetaDefensaJuridica(HttpServletRequest request,
			TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarTarjetaDefensaJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					if (!UtilidadesString.esCadenaVacia(anioNumero)) {

						ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
						scsAsistenciaKey.setIdinstitucion(idInstitucion);
						scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
						scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));

						// Actualizamos los delitos asociados seleccionados en el multiselect en
						// SCS_DELITOSASISTENCIA
						procesarDelitos(tarjetaDefensaJuridicaItem, idInstitucion, anioNumero);

						ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
						scsAsistencia.setNig(tarjetaDefensaJuridicaItem.getNig());
						scsAsistencia.setDelitosimputados(tarjetaDefensaJuridicaItem.getComentariosDelitos());
						scsAsistencia.setDatosdefensajuridica(tarjetaDefensaJuridicaItem.getObservaciones());
						scsAsistencia.setNumerodiligencia(tarjetaDefensaJuridicaItem.getNumDiligencia());
						scsAsistencia.setNumeroprocedimiento(tarjetaDefensaJuridicaItem.getNumProcedimiento());
						if (!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdProcedimiento())) {
							scsAsistencia
									.setIdpretension(Short.valueOf(tarjetaDefensaJuridicaItem.getIdProcedimiento()));
						} else {
							scsAsistencia.setIdpretension(null);
						}

						ScsCaractasistenciaKey scsCaractasistenciaKey = new ScsCaractasistenciaKey();
						scsCaractasistenciaKey.setAnio(scsAsistencia.getAnio());
						scsCaractasistenciaKey.setNumero(scsAsistencia.getNumero());
						scsCaractasistenciaKey.setIdinstitucion(idInstitucion);
						ScsCaractasistencia scsCaractasistencia = scsCaractasistenciaMapper
								.selectByPrimaryKey(scsCaractasistenciaKey);

						if (!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdComisaria())) {
							scsAsistencia.setComisaria(Long.valueOf(tarjetaDefensaJuridicaItem.getIdComisaria()));
							scsAsistencia.setComisariaidinstitucion(idInstitucion);
							scsAsistencia.setJuzgado(null);
							scsAsistencia.setJuzgadoidinstitucion(null);
							// Si elegimos comisaria y la caracteristica de la asistencia estaba creada, hay
							// que dejarlo
							// reflejado en SCS_CARACTASISTENCIA
							if (scsCaractasistencia != null) {
								scsCaractasistencia.setIdjuzgado(null);
								scsCaractasistencia.setIdinstitucionJuzgado(null);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.updateByPrimaryKey(scsCaractasistencia);
							}
						} else if (!UtilidadesString.esCadenaVacia(tarjetaDefensaJuridicaItem.getIdJuzgado())) {
							scsAsistencia.setJuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
							scsAsistencia.setJuzgadoidinstitucion(idInstitucion);
							scsAsistencia.setComisaria(null);
							scsAsistencia.setComisariaidinstitucion(null);
							// Si se selecciona juzgado hay que dejarlo registrado en la tabla
							// SCS_CARACTASISTENCIA
							// Si no tiene creado registro lo creamos y si lo tiene creado lo actualizamos
							if (scsCaractasistencia == null) {
								scsCaractasistencia = new ScsCaractasistencia();
								scsCaractasistencia.setAnio(scsAsistencia.getAnio());
								scsCaractasistencia.setNumero(scsAsistencia.getNumero());
								scsCaractasistencia.setIdinstitucion(idInstitucion);
								scsCaractasistencia
										.setIdjuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
								scsCaractasistencia.setIdinstitucionJuzgado(idInstitucion);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.insertSelective(scsCaractasistencia);
							} else {
								scsCaractasistencia
										.setIdjuzgado(Long.valueOf(tarjetaDefensaJuridicaItem.getIdJuzgado()));
								scsCaractasistencia.setIdinstitucionJuzgado(idInstitucion);
								scsCaractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
								scsCaractasistencia.setFechamodificacion(new Date());
								affectedRows += scsCaractasistenciaMapper.updateByPrimaryKey(scsCaractasistencia);
							}

						} else {
							scsAsistencia.setComisaria(null);
							scsAsistencia.setJuzgado(null);
							scsAsistencia.setComisariaidinstitucion(null);
							scsAsistencia.setJuzgadoidinstitucion(null);
						}

						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

					}

					if (affectedRows <= 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha borrado ningún registro");
						updateResponseDTO.setError(error);
					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("guardarTarjetaDefensaJuridica() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar los datos de defensa juridica: " + e);
			error.description("Error al guardar los datos de defensa juridica: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Transactional
	private void procesarDelitos(TarjetaDefensaJuridicaItem tarjetaDefensaJuridicaItem, Short idInstitucion,
			String anioNumero) {

		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
				.andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]));
		scsDelitosasistenciaMapper.deleteByExample(scsDelitosasistenciaExample);

		if (tarjetaDefensaJuridicaItem.getIdDelitos() != null && !tarjetaDefensaJuridicaItem.getIdDelitos().isEmpty()) {

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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null) {

						TarjetaObservacionesItem tarjetaObservacionesItem = new TarjetaObservacionesItem();
						tarjetaObservacionesItem.setIncidencias(scsAsistencia.getIncidencias());
						tarjetaObservacionesItem.setObservaciones(scsAsistencia.getObservaciones());

						tarjetaObservacionesDTO.getResponseItems().add(tarjetaObservacionesItem);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("searchTarjetaObservaciones() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar la tarjeta observaciones: " + e);
			error.description("Error al buscar la tarjeta observaciones: " + e);
			tarjetaObservacionesDTO.setError(error);
		}
		return tarjetaObservacionesDTO;
	}

	@Override
	public UpdateResponseDTO guardarTarjetaObservaciones(HttpServletRequest request,
			TarjetaObservacionesItem tarjetaObservacionesItem, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"guardarTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarTarjetaObservaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null && tarjetaObservacionesItem != null) {

						scsAsistencia.setObservaciones(tarjetaObservacionesItem.getObservaciones());
						scsAsistencia.setIncidencias(tarjetaObservacionesItem.getIncidencias());
						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("guardarTarjetaObservaciones() / ERROR: " + e.getMessage(), e);
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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

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

					List<RelacionesItem> relacionesItems = scsAsistenciaExtendsMapper.searchRelaciones(
							anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion,
							Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(), tamMaximo);

					if (relacionesItems != null && !relacionesItems.isEmpty()) {
						relacionesDTO.setRelacionesItem(relacionesItems);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("searchRelaciones() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las relaciones: " + e);
			error.description("Error al buscar las relaciones: " + e);
			relacionesDTO.setError(error);
		}
		return relacionesDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO asociarDesigna(HttpServletRequest request, String anioNumero, DesignaItem designaItem,
			String copiarDatos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null && designaItem != null) {
						scsAsistencia.setDesignaAnio((short) designaItem.getAno());
						scsAsistencia.setDesignaNumero(Long.valueOf(designaItem.getCodigo()));
						if (designaItem.getIdTurnos() != null && designaItem.getIdTurnos().length > 0) { // Si traemos
																											// el id
																											// turno
																											// informado
							scsAsistencia.setDesignaTurno(Integer.valueOf(designaItem.getIdTurnos()[0]));
						} else { // Si venimos de la pantalla de busqueda de asuntos traeremos la abreviatura
							ScsTurnoExample scsTurnoExample = new ScsTurnoExample();
							scsTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andAbreviaturaEqualTo(designaItem.getNombreTurno().split("/")[0]);
							List<ScsTurno> turnos = scsTurnosExtendsMapper.selectByExample(scsTurnoExample);
							if (turnos != null && !turnos.isEmpty()) {
								scsAsistencia.setDesignaTurno(turnos.get(0).getIdturno());
							}
						}
						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

						if ("S".equals(copiarDatos)) { // Pasamos los datos de la asistencia a la designa
							affectedRows += updateDesignaConAsistencia(scsAsistencia);
						}
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarDesigna() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar la designa a la asistencia: " + e);
			error.description("Error al asociar la designa a la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO eliminarRelacion(HttpServletRequest request, String anioNumero,
			List<RelacionesItem> asuntos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"eliminarRelacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"eliminarRelacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)
						&& asuntos != null) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null) {
						for (int i = 0; i < asuntos.size(); i++) {

							if ("D".equals(String.valueOf(asuntos.get(i).getSjcs().charAt(0)))) {
								scsAsistencia.setDesignaAnio(null);
								scsAsistencia.setDesignaNumero(null);
								scsAsistencia.setDesignaTurno(null);
								affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
							} else if ("E".equals(String.valueOf(asuntos.get(i).getSjcs().charAt(0)))) {

								scsAsistencia.setEjganio(null);
								scsAsistencia.setEjgnumero(null);
								scsAsistencia.setEjgidtipoejg(null);
								affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);
							}

						}
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarDesigna() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar la designa a la asistencia: " + e);
			error.description("Error al actualizar la designa a la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO asociarEjg(HttpServletRequest request, String anioNumero, EjgItem ejg,
			String copiarDatos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
					scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsAsistenciaKey.setIdinstitucion(idInstitucion);
					ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

					if (scsAsistencia != null && ejg != null) {
						scsAsistencia.setEjganio(Short.valueOf(ejg.getAnnio()));
						scsAsistencia.setEjgnumero(Long.valueOf(ejg.getNumero()));
						scsAsistencia.setEjgidtipoejg(Short.valueOf(ejg.getTipoEJG()));
						scsAsistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsAsistencia.setFechamodificacion(new Date());
						affectedRows += scsAsistenciaExtendsMapper.updateByPrimaryKey(scsAsistencia);

						if ("S".equals(copiarDatos)) { // Pasamos los datos de la asistencia al EJG
							affectedRows += updateEJGconAsistencia(scsAsistencia, usuarios.get(0).getIdusuario());
						}
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarEJG() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar la designa a la asistencia: " + e);
			error.description("Error al asociar la designa a la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public DocumentacionAsistenciaDTO searchDocumentacion(HttpServletRequest request, String anioNumero,
			String idActuacion) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DocumentacionAsistenciaDTO documentacionAsistenciaDTO = new DocumentacionAsistenciaDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchDocumentacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchDocumentacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {
					List<ComboItem> comboAsociado = scsAsistenciaExtendsMapper.comboAsociadoAsistencia(
							anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion,
							Integer.valueOf(usuarios.get(0).getIdlenguaje()));

					List<ComboItem> comboTipoDoc = scsAsistenciaExtendsMapper.comboTipoDocumentosAsistencia();

					List<DocumentacionAsistenciaItem> documentacion = scsAsistenciaExtendsMapper.searchDocumentacion(
							anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion, idActuacion);

					if (documentacion != null && !documentacion.isEmpty()) {

						for (DocumentacionAsistenciaItem documento : documentacion) {

							if ("0".equals(documento.getAsociado())) {
								documento.setDescAsociado("Asistencia");
							} else {
								documento.setDescAsociado(comboAsociado.stream()
										.filter(comboItem -> comboItem.getValue().equals(documento.getAsociado()))
										.findFirst().get().getLabel());
							}
							documento.setDescTipoDoc(comboTipoDoc.stream()
									.filter(comboItem -> comboItem.getValue().equals(documento.getIdTipoDoc()))
									.findFirst().get().getLabel());

						}

						documentacionAsistenciaDTO.setDocumentacionAsistenciaItems(documentacion);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("searchDocumentacion() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar la documentacion: " + e);
			error.description("Error al buscar la documentacion: " + e);
			documentacionAsistenciaDTO.setError(error);
		}
		return documentacionAsistenciaDTO;
	}

	@Override
	public TarjetaAsistenciaResponseDTO searchAsistencias(HttpServletRequest request, FiltroAsistenciaItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Integer tamMaximo;
		List<GenParametros> tamMax = new ArrayList<>();
		TarjetaAsistenciaResponseDTO tarjetaAsistenciaResponseDTO = new TarjetaAsistenciaResponseDTO();
		List<TarjetaAsistenciaResponseItem> asistencias = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && filtro != null) {

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

					if (!UtilidadesString.esCadenaVacia(filtro.getNumColegiado())) {
						List<ColegiadoJGItem> colegiados = cenPersonaExtendsMapper
								.busquedaColegiadoExpress(filtro.getNumColegiado(), idInstitucion.toString());
						if (colegiados != null && !colegiados.isEmpty()) {
							filtro.setIdLetradoGuardia(colegiados.get(0).getIdPersona());
						}
					}
					asistencias = scsAsistenciaExtendsMapper.searchAsistencias(filtro, idInstitucion,
							Integer.parseInt(usuarios.get(0).getIdlenguaje()), tamMaximo);

					if (tamMaximo != null && asistencias != null && !asistencias.isEmpty()
							&& asistencias.size() > tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						tarjetaAsistenciaResponseDTO.setError(error);
						asistencias.remove(asistencias.size() - 1);
					}
					tarjetaAsistenciaResponseDTO.setResponseItems(asistencias);
				}

			}
		} catch (Exception e) {
			LOGGER.error("searchAsistencias() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las asistencias: " + e);
			error.description("Error al buscar las asistencias: " + e);
			tarjetaAsistenciaResponseDTO.setError(error);
		}
		return tarjetaAsistenciaResponseDTO;
	}

	@Override
	public InsertResponseDTO subirDocumentoAsistencia(MultipartHttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();
		int affectedRows = 0;
		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				String anioNumero = request.getParameter("anioNumero");
				Iterator<String> itr = request.getFileNames();

				while (itr.hasNext()) {

					MultipartFile file = request.getFile(itr.next());
					String nombreFichero = file.getOriginalFilename().split(";")[0];
					String json = file.getOriginalFilename().split(";")[1].replaceAll("%22", "\"");
					DocumentacionAsistenciaItem documentacionAsistenciaItem = objectMapper.readValue(json,
							DocumentacionAsistenciaItem.class);
					String extension = FilenameUtils.getExtension(nombreFichero);

					Long idFichero = uploadFile(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion,
							nombreFichero, extension, anioNumero);

					MaxIdDto nuevoId = scsDesignacionesExtendsMapper.getNewIdDocumentacionAsi(idInstitucion);

					ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();

					scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(nuevoId.getIdMax().toString()));
					scsDocumentacionasi.setNombrefichero(nombreFichero);
					scsDocumentacionasi.setIdtipodocumento(Short.valueOf(documentacionAsistenciaItem.getIdTipoDoc()));
					if (!UtilidadesString.esCadenaVacia(documentacionAsistenciaItem.getAsociado())
							&& !"0".equals(documentacionAsistenciaItem.getAsociado())) {
						scsDocumentacionasi.setIdactuacion(Long.valueOf(documentacionAsistenciaItem.getAsociado()));
					}
					scsDocumentacionasi.setIdfichero(idFichero);
					scsDocumentacionasi.setFechaentrada(new Date());
					scsDocumentacionasi.setFechamodificacion(new Date());
					scsDocumentacionasi.setObservaciones(documentacionAsistenciaItem.getObservaciones());
					scsDocumentacionasi.setIdinstitucion(idInstitucion);
					scsDocumentacionasi.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsDocumentacionasi.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
					affectedRows += scsDocumentacionasiMapper.insertSelective(scsDocumentacionasi);

					if (affectedRows > 0) {
						insertResponseDTO.setStatus(SigaConstants.OK);
						insertResponseDTO.setId(anioNumero);
					} else {
						LOGGER.error(
								"AsistenciaServiceImpl.subirDocumentoAsistencia() / Error al insertar el registro del documento de la asistencia");
						error.setCode(500);
						error.setDescription("Error al insertar el registro del documento de la asistencia");
						insertResponseDTO.setError(error);
					}

				}

				String documentos = request.getParameter("documentosActualizar");
				List<DocumentacionAsistenciaItem> listaDocumentos = objectMapper.readValue(documentos,
						new TypeReference<List<DocumentacionAsistenciaItem>>() {
						});

				if (listaDocumentos != null && !listaDocumentos.isEmpty()) {

					for (DocumentacionAsistenciaItem documento : listaDocumentos) {

						affectedRows = 0;

						ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();
						scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(documento.getIdDocumentacion()));
						scsDocumentacionasi.setIdinstitucion(idInstitucion);
						scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsDocumentacionasi.setFechamodificacion(new Date());
						scsDocumentacionasi.setObservaciones(documento.getObservaciones());

						affectedRows += scsDocumentacionasiMapper.updateByPrimaryKeySelective(scsDocumentacionasi);

						if (affectedRows > 0) {
							insertResponseDTO.setStatus(SigaConstants.OK);
							insertResponseDTO.setId(anioNumero);
						} else {
							LOGGER.error("subirDocumentoAsistencia() / Error al actualizar los datos de un documento");
							error.setCode(500);
							error.setDescription("Error al actualizar los datos de un documento");
							insertResponseDTO.setError(error);
						}

					}

				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"AsistenciaServiceImpl.subirDocumentoAsistencia() -> Se ha producido un error al subir un fichero perteneciente a la asistencia",
					e);
			error.setCode(500);
			error.setDescription("Error al subir el fichero perteneciente a la asistencia");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarDocumentoAsistencia(HttpServletRequest request,
			List<DocumentacionAsistenciaItem> documentos, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"AsistenciaServiceImpl.eliminarDocumentosAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"AsistenciaServiceImpl.eliminarDocumentosAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				for (DocumentacionAsistenciaItem doc : documentos) {

					String path = getDirectorioFicheroAsi(idInstitucion);
					path += File.separator + idInstitucion + "_" + doc.getIdFichero()
							+ doc.getNombreFichero()
									.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
									.toLowerCase();

					File file = new File(path);

					if (file.exists()) {
						file.delete();
					}

					ScsDocumentacionasiKey scsDocumentacionasiKey = new ScsDocumentacionasi();
					scsDocumentacionasiKey.setIddocumentacionasi(Integer.valueOf(doc.getIdDocumentacion()));
					scsDocumentacionasiKey.setIdinstitucion(idInstitucion);
					affectedRows = scsDocumentacionasiMapper.deleteByPrimaryKey(scsDocumentacionasiKey);

					if (affectedRows > 0) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					} else {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"AsistenciaServiceImpl.eliminarDocumentosAsistencia() -> Se ha producido un error en la eliminación de documentos asociados a la asistencia");
						error.setCode(500);
						error.setDescription(
								"Se ha producido un error en la eliminación de documentos asociados a la asistencia");
						deleteResponseDTO.setError(error);
					}

					GenFicheroKey genFicheroKey = new GenFicheroKey();

					genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
					genFicheroKey.setIdinstitucion(idInstitucion);
					affectedRows = 0;
					affectedRows += genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

					if (affectedRows > 0) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					} else {
						LOGGER.error(
								"AsistenciaServiceImpl.eliminarDocumentosAsistencia() -> Se ha producido un error en la eliminación de documentos asociados a la asistencia");
						deleteResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription(
								"Se ha producido un error en la eliminación de documentos asociados a la asistencia");
						deleteResponseDTO.setError(error);
					}
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"AsistenciaServiceImpl.eliminarDocumentosAsistencia() -> Se ha producido un error en la eliminación de documentos asociados a la asistencia",
					e);
			error.setCode(500);
			error.setDescription("Se ha producido un error en la eliminación de documentos asociados a la asistencia");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumento(HttpServletRequest request,
			List<DocumentacionAsistenciaItem> documentos) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"AsistenciaServiceImpl.descargarDocumento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"AsistenciaServiceImpl.descargarDocumento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !documentos.isEmpty()) {

				if (documentos.size() == 1) {
					String extension = "";

					GenFicheroKey genFicheroKey = new GenFicheroKey();
					genFicheroKey.setIdfichero(Long.valueOf(documentos.get(0).getIdFichero()));
					genFicheroKey.setIdinstitucion(idInstitucion);
					extension = genFicheroMapper.selectByPrimaryKey(genFicheroKey).getExtension();

					String path = getDirectorioFicheroAsi(idInstitucion);
					path += File.separator + idInstitucion + "_" + documentos.get(0).getIdFichero() + "." + extension;

					File file = new File(path);
					fileStream = new FileInputStream(file);

					String tipoMime = getMimeType("." + extension);

					headers.setContentType(MediaType.parseMediaType(tipoMime));
					if (UtilidadesString.esCadenaVacia(documentos.get(0).getNombreFichero())) {
						documentos.get(0).setNombreFichero("default." + extension);
					}
					headers.set("Content-Disposition",
							"attachment; filename=\"" + documentos.get(0).getNombreFichero() + "\"");
					headers.setContentLength(file.length());

				} else {
					fileStream = getZipFileDocumentosAsi(documentos, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentos.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"AsistenciaServiceImpl.descargarDocumento() -> Se ha producido un error al descargar un archivo asociado a la asistencia",
					e);
		}

		return res;
	}

	@Override
	public UpdateResponseDTO saveCaracteristicas(HttpServletRequest request,
			CaracteristicasAsistenciaItem caracteristicasAsistenciaItem, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"saveCaracteristicas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"saveCaracteristicas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					ScsCaractasistenciaKey scsCaractasistenciaKey = new ScsCaractasistenciaKey();
					scsCaractasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsCaractasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsCaractasistenciaKey.setIdinstitucion(idInstitucion);
					ScsCaractasistencia caractasistencia = scsCaractasistenciaMapper
							.selectByPrimaryKey(scsCaractasistenciaKey);

					if (caractasistencia != null) {
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdOrigenContacto())) {
							caractasistencia.setIdorigencontacto(
									Long.valueOf(caracteristicasAsistenciaItem.getIdOrigenContacto()));
							caractasistencia
									.setDescripcioncontacto(caracteristicasAsistenciaItem.getDescOrigenContacto());
						} else {
							caractasistencia.setIdorigencontacto(null);
							caractasistencia.setDescripcioncontacto(null);
						}
						if (caracteristicasAsistenciaItem.isViolenciaDomestica()) {
							caractasistencia.setViolenciadomestica("1");
						} else {
							caractasistencia.setViolenciadomestica("0");
						}
						if (caracteristicasAsistenciaItem.isViolenciaGenero()) {
							caractasistencia.setViolenciagenero("1");
						} else {
							caractasistencia.setViolenciagenero("0");
						}
						if (caracteristicasAsistenciaItem.isContraLibertadSexual()) {
							caractasistencia.setContralalibertadsexual("1");
						} else {
							caractasistencia.setContralalibertadsexual("0");
						}
						if (caracteristicasAsistenciaItem.isMenorAbuso()) {
							caractasistencia.setVictimamenorabusomaltrato("1");
						} else {
							caractasistencia.setVictimamenorabusomaltrato("0");
						}
						if (caracteristicasAsistenciaItem.isDiscapacidadPsiquicaAbuso()) {
							caractasistencia.setPersonacondiscapacidad("1");
						} else {
							caractasistencia.setPersonacondiscapacidad("0");
						}
						if (caracteristicasAsistenciaItem.isJudicial()) {
							caractasistencia.setJudicial("0");
						} else {
							caractasistencia.setJudicial("0");
						}
						if (caracteristicasAsistenciaItem.isPenal()) {
							caractasistencia.setPenal("1");
						} else {
							caractasistencia.setPenal("0");
						}
						if (caracteristicasAsistenciaItem.isCivil()) {
							caractasistencia.setCivil("1");
						} else {
							caractasistencia.setCivil("0");
						}
						if (caracteristicasAsistenciaItem.isInterposicionDenuncia()) {
							caractasistencia.setInterposiciondenuncia("1");
						} else {
							caractasistencia.setInterposiciondenuncia("0");
						}
						if (caracteristicasAsistenciaItem.isMedidasCautelares()) {
							caractasistencia.setSolicitudmedidascautelares("1");
						} else {
							caractasistencia.setSolicitudmedidascautelares("0");
						}
						if (caracteristicasAsistenciaItem.isOrdenProteccion()) {
							caractasistencia.setOrdenproteccion("1");
						} else {
							caractasistencia.setOrdenproteccion("0");
						}
						if (caracteristicasAsistenciaItem.isOtras()) {
							caractasistencia.setOtras("1");
							caractasistencia.setOtrasdescripcion(caracteristicasAsistenciaItem.getOtrasDesc());
						} else {
							caractasistencia.setOtras("0");
							caractasistencia.setOtrasdescripcion(null);
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getAsesoramiento())) {
							if (caracteristicasAsistenciaItem.getAsesoramiento().equals("true")) {
								caractasistencia.setAsesoramiento("1");
							} else {
								caractasistencia.setAsesoramiento("0");
							}
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getMinisterioFiscal())
								&& "S".equals(caracteristicasAsistenciaItem.getMinisterioFiscal())) {
							caractasistencia.setInterposicionministfiscal("1");
						} else {
							caractasistencia.setInterposicionministfiscal("0");
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getMedicoForense())
								&& "S".equals(caracteristicasAsistenciaItem.getMedicoForense())) {
							caractasistencia.setIntervencionmedicoforense("1");
						} else {
							caractasistencia.setIntervencionmedicoforense("0");
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdProcedimiento())) {
							caractasistencia
									.setIdpretension(Short.valueOf(caracteristicasAsistenciaItem.getIdProcedimiento()));
						} else {
							caractasistencia.setIdpretension(null);
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getNumColegiado())) {
							List<ColegiadoJGItem> colegiados = cenPersonaExtendsMapper.busquedaColegiadoExpress(
									caracteristicasAsistenciaItem.getNumColegiado(), String.valueOf(idInstitucion));

							if (colegiados != null && !colegiados.isEmpty()) {
								caractasistencia.setIdpersona(Long.valueOf(colegiados.get(0).getIdPersona()));
							}
						} else {
							caractasistencia.setIdpersona(null);
						}
						
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdJuzgado())) {
							caractasistencia.setIdjuzgado(Long.valueOf(caracteristicasAsistenciaItem.getIdJuzgado()));
						}
						caractasistencia.setNumeroprocedimiento(caracteristicasAsistenciaItem.getNumeroProcedimiento());
						caractasistencia.setNig(caracteristicasAsistenciaItem.getNig());
						caractasistencia.setFechamodificacion(new Date());
						caractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
						affectedRows += scsCaractasistenciaMapper.updateByPrimaryKey(caractasistencia);
					} else {

						caractasistencia = new ScsCaractasistencia();
						caractasistencia.setAnio(Short.valueOf(anioNumero.split("/")[0]));
						caractasistencia.setNumero(Long.valueOf(anioNumero.split("/")[1]));
						caractasistencia.setIdinstitucion(idInstitucion);
						caractasistencia.setFechamodificacion(new Date());
						caractasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdJuzgado())) {
							caractasistencia.setIdjuzgado(Long.valueOf(caracteristicasAsistenciaItem.getIdJuzgado()));
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdOrigenContacto())) {
							caractasistencia.setIdorigencontacto(
									Long.valueOf(caracteristicasAsistenciaItem.getIdOrigenContacto()));
							caractasistencia
									.setDescripcioncontacto(caracteristicasAsistenciaItem.getDescOrigenContacto());
						} else {
							caractasistencia.setIdorigencontacto(null);
							caractasistencia.setDescripcioncontacto(null);
						}
						if (caracteristicasAsistenciaItem.isViolenciaDomestica()) {
							caractasistencia.setViolenciadomestica("1");
						} else {
							caractasistencia.setViolenciadomestica("0");
						}
						if (caracteristicasAsistenciaItem.isViolenciaGenero()) {
							caractasistencia.setViolenciagenero("1");
						} else {
							caractasistencia.setViolenciagenero("0");
						}
						if (caracteristicasAsistenciaItem.isContraLibertadSexual()) {
							caractasistencia.setContralalibertadsexual("1");
						} else {
							caractasistencia.setContralalibertadsexual("0");
						}
						if (caracteristicasAsistenciaItem.isMenorAbuso()) {
							caractasistencia.setVictimamenorabusomaltrato("1");
						} else {
							caractasistencia.setVictimamenorabusomaltrato("0");
						}
						if (caracteristicasAsistenciaItem.isDiscapacidadPsiquicaAbuso()) {
							caractasistencia.setPersonacondiscapacidad("1");
						} else {
							caractasistencia.setPersonacondiscapacidad("0");
						}
						if (caracteristicasAsistenciaItem.isJudicial()) {
							caractasistencia.setJudicial("0");
						} else {
							caractasistencia.setJudicial("0");
						}
						if (caracteristicasAsistenciaItem.isPenal()) {
							caractasistencia.setPenal("1");
						} else {
							caractasistencia.setPenal("0");
						}
						if (caracteristicasAsistenciaItem.isCivil()) {
							caractasistencia.setCivil("1");
						} else {
							caractasistencia.setCivil("0");
						}
						if (caracteristicasAsistenciaItem.isInterposicionDenuncia()) {
							caractasistencia.setInterposiciondenuncia("1");
						} else {
							caractasistencia.setInterposiciondenuncia("0");
						}
						if (caracteristicasAsistenciaItem.isMedidasCautelares()) {
							caractasistencia.setSolicitudmedidascautelares("1");
						} else {
							caractasistencia.setSolicitudmedidascautelares("0");
						}
						if (caracteristicasAsistenciaItem.isOrdenProteccion()) {
							caractasistencia.setOrdenproteccion("1");
						} else {
							caractasistencia.setOrdenproteccion("0");
						}
						if (caracteristicasAsistenciaItem.isOtras()) {
							caractasistencia.setOtras("1");
							caractasistencia.setOtrasdescripcion(caracteristicasAsistenciaItem.getOtrasDesc());
						} else {
							caractasistencia.setOtras("0");
							caractasistencia.setOtrasdescripcion(null);
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getAsesoramiento())) {
							if (caracteristicasAsistenciaItem.getAsesoramiento().equals("true")) {
								caractasistencia.setAsesoramiento("1");
							} else {
								caractasistencia.setAsesoramiento("0");
							}
						} else {
							caractasistencia.setAsesoramiento(null);
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getMinisterioFiscal())
								&& "S".equals(caracteristicasAsistenciaItem.getMinisterioFiscal())) {
							caractasistencia.setInterposicionministfiscal("1");
						} else {
							caractasistencia.setInterposicionministfiscal("0");
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getMedicoForense())
								&& "S".equals(caracteristicasAsistenciaItem.getMedicoForense())) {
							caractasistencia.setIntervencionmedicoforense("1");
						} else {
							caractasistencia.setIntervencionmedicoforense("0");
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getIdProcedimiento())) {
							caractasistencia
									.setIdpretension(Short.valueOf(caracteristicasAsistenciaItem.getIdProcedimiento()));
						} else {
							caractasistencia.setIdpretension(null);
						}
						if (!UtilidadesString.esCadenaVacia(caracteristicasAsistenciaItem.getNumColegiado())) {
							List<ColegiadoJGItem> colegiados = cenPersonaExtendsMapper.busquedaColegiadoExpress(
									caracteristicasAsistenciaItem.getNumColegiado(), String.valueOf(idInstitucion));

							if (colegiados != null && !colegiados.isEmpty()) {
								caractasistencia.setIdpersona(Long.valueOf(colegiados.get(0).getIdPersona()));
							}
						} else {
							caractasistencia.setIdpersona(null);
						}
						caractasistencia.setNumeroprocedimiento(caracteristicasAsistenciaItem.getNumeroProcedimiento());
						caractasistencia.setNig(caracteristicasAsistenciaItem.getNig());
						affectedRows += scsCaractasistenciaMapper.insert(caractasistencia);
					}

					if (affectedRows > 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setId(anioNumero);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("saveCaracteristicas() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al guardar las caracteristicas de la asistencia: " + e);
			error.description("Error al guardar las caracteristicas de la asistencia: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public CaracteristicasAsistenciaDTO searchCaracteristicas(HttpServletRequest request, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CaracteristicasAsistenciaDTO caracteristicasAsistenciaDTO = new CaracteristicasAsistenciaDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchCaracteristicas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchCaracteristicas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {
					CaracteristicasAsistenciaItem caracteristicasAsistenciaItem = new CaracteristicasAsistenciaItem();
					ScsCaractasistenciaKey scsCaractasistenciaKey = new ScsCaractasistenciaKey();
					scsCaractasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
					scsCaractasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
					scsCaractasistenciaKey.setIdinstitucion(idInstitucion);
					ScsCaractasistencia caractasistencia = scsCaractasistenciaMapper
							.selectByPrimaryKey(scsCaractasistenciaKey);

					if (caractasistencia != null) {

						if (caractasistencia.getIdorigencontacto() != null) {
							caracteristicasAsistenciaItem
									.setIdOrigenContacto(caractasistencia.getIdorigencontacto().toString());
							caracteristicasAsistenciaItem
									.setDescOrigenContacto(caractasistencia.getDescripcioncontacto());
						}
						caracteristicasAsistenciaItem
								.setViolenciaDomestica("1".equals(caractasistencia.getViolenciadomestica()));
						caracteristicasAsistenciaItem
								.setViolenciaGenero("1".equals(caractasistencia.getViolenciagenero()));
						caracteristicasAsistenciaItem
								.setContraLibertadSexual("1".equals(caractasistencia.getContralalibertadsexual()));
						caracteristicasAsistenciaItem
								.setMenorAbuso("1".equals(caractasistencia.getVictimamenorabusomaltrato()));
						caracteristicasAsistenciaItem
								.setDiscapacidadPsiquicaAbuso("1".equals(caractasistencia.getPersonacondiscapacidad()));
						caracteristicasAsistenciaItem.setJudicial("1".equals(caractasistencia.getJudicial()));
						caracteristicasAsistenciaItem.setPenal("1".equals(caractasistencia.getPenal()));
						caracteristicasAsistenciaItem.setCivil("1".equals(caractasistencia.getCivil()));
						caracteristicasAsistenciaItem
								.setInterposicionDenuncia("1".equals(caractasistencia.getInterposiciondenuncia()));
						caracteristicasAsistenciaItem
								.setMedidasCautelares("1".equals(caractasistencia.getSolicitudmedidascautelares()));
						caracteristicasAsistenciaItem
								.setOrdenProteccion("1".equals(caractasistencia.getOrdenproteccion()));
						caracteristicasAsistenciaItem.setOtras("1".equals(caractasistencia.getOtras()));
						caracteristicasAsistenciaItem.setOtrasDesc(caractasistencia.getOtrasdescripcion());
						caracteristicasAsistenciaItem.setAsesoramiento(caractasistencia.getAsesoramiento());
						caracteristicasAsistenciaItem.setMedicoForense(
								"1".equals(caractasistencia.getIntervencionmedicoforense()) ? "S" : "N");
						caracteristicasAsistenciaItem.setMinisterioFiscal(
								"1".equals(caractasistencia.getInterposicionministfiscal()) ? "S" : "N");
						if (caractasistencia.getIdpersona() != null) {
							FichaPersonaItem colegiado = cenPersonaExtendsMapper
									.getColegiadoByIdPersona(caractasistencia.getIdpersona().toString(), idInstitucion);
							if (colegiado != null) {
								caracteristicasAsistenciaItem.setNumColegiado(colegiado.getNumeroColegiado());
							}
						}
						if (caractasistencia.getIdpretension() != null) {
							caracteristicasAsistenciaItem
									.setIdProcedimiento(caractasistencia.getIdpretension().toString());
						}
						if (caractasistencia.getNumeroprocedimiento() != null) {
							caracteristicasAsistenciaItem.setNumeroProcedimiento(caractasistencia.getNumeroprocedimiento());
						}
						if (caractasistencia.getNig() != null) {
							caracteristicasAsistenciaItem.setNig(caractasistencia.getNig());
						}
						if (caractasistencia.getIdjuzgado() != null) {
							caracteristicasAsistenciaItem.setIdJuzgado(caractasistencia.getIdjuzgado().toString());
						}
						caracteristicasAsistenciaDTO.getCaracteristicasAsistenciaItems()
								.add(caracteristicasAsistenciaItem);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("searchCaracteristicas() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar los datos de las caracteristicas: " + e);
			error.description("Error al buscar los datos de las caracteristicas: " + e);
			caracteristicasAsistenciaDTO.setError(error);
		}
		return caracteristicasAsistenciaDTO;
	}

	@Override
	public ActuacionAsistenciaDTO searchActuaciones(HttpServletRequest request, String anioNumero,
			String mostrarHistorico) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ActuacionAsistenciaDTO actuacionAsistenciaDTO = new ActuacionAsistenciaDTO();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchActuaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchActuaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					List<ActuacionAsistenciaItem> actuaciones = scsAsistenciaExtendsMapper.searchActuaciones(
							anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion,
							Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(), mostrarHistorico);

					if (actuaciones != null && !actuaciones.isEmpty()) {
						actuacionAsistenciaDTO.setActuacionAsistenciaItems(actuaciones);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("searchActuaciones() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las actuaciones asociadas a la asistencia: " + e);
			error.description("Error al buscar las actuaciones asociadas a la asistencia: " + e);
			actuacionAsistenciaDTO.setError(error);
		}
		return actuacionAsistenciaDTO;
	}

	@Override
	public UpdateResponseDTO updateEstadoActuacion(HttpServletRequest request,
			List<ActuacionAsistenciaItem> actuaciones, String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		boolean facturada = false;
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"updateEstadoActuacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"updateEstadoActuacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)) {

					for (ActuacionAsistenciaItem actuacion : actuaciones) {
						ScsActuacionasistencia scsActuacionasistencia = new ScsActuacionasistencia();
						// Si se quiere anular, debemos comprobar que no este facturada la actuacion
						if ("1".equals(actuacion.getAnulada())) {
							ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
							scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(actuacion.getIdActuacion()));
							scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
							scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
							scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
							scsActuacionasistencia = scsActuacionasistenciaExtendsMapper
									.selectByPrimaryKey(scsActuacionasistenciaKey);
							//actuacion.setAnulada("0");

							if (scsActuacionasistencia != null && "1".equals(scsActuacionasistencia.getFacturado())) {
								facturada = true;
							}

						}
						// Si no esta facturada anulamos

						if (!facturada) {
							scsActuacionasistencia.setIdactuacion(Long.valueOf(actuacion.getIdActuacion()));
							scsActuacionasistencia.setAnio(Short.valueOf(anioNumero.split("/")[0]));
							scsActuacionasistencia.setNumero(Long.valueOf(anioNumero.split("/")[1]));
							scsActuacionasistencia.setIdinstitucion(idInstitucion);
							scsActuacionasistencia.setAnulacion(Short.valueOf(actuacion.getAnulada()));

							affectedRows += scsActuacionasistenciaExtendsMapper
									.updateByPrimaryKeySelective(scsActuacionasistencia);
							// Si esta facurada ponemos un mensaje de error
//						} else {
//							updateResponseDTO.setStatus(SigaConstants.KO);
//							error.setCode(500);
//							error.setDescription("No se puede anular una actuacion facturada");
//							error.setMessage("No se puede anular una actuacion facturada");
//							updateResponseDTO.setError(error);
						}
					}
					if (!facturada) {
						if (affectedRows > 0) {
							updateResponseDTO.setStatus(SigaConstants.OK);
							updateResponseDTO.setId(anioNumero);
						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setCode(500);
							error.setDescription("No se actualizo ningun registro");
							updateResponseDTO.setError(error);
						}
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("updateEstadoActuacion() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al actualizar el estado de la actuacion: " + e);
			error.description("Error al actualizar el estado de la actuacion: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarActuaciones(HttpServletRequest request, List<ActuacionAsistenciaItem> actuaciones,
			String anioNumero) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"eliminarActuaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"eliminarActuaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && !UtilidadesString.esCadenaVacia(anioNumero)
						&& actuaciones != null) {

					for (ActuacionAsistenciaItem actuacion : actuaciones) {

						ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
						scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
						scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
						scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
						scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(actuacion.getIdActuacion()));
						affectedRows += scsActuacionasistenciaExtendsMapper
								.deleteByPrimaryKey(scsActuacionasistenciaKey);

					}

					if (affectedRows > 0) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					} else {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						deleteResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("eliminarActuaciones() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al eliminar fisicamente la actuacion: " + e);
			error.description("Error al eliminar fisicamente la actuacion: " + e);
			deleteResponseDTO.setError(error);
		}
		return deleteResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarAsistencias(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"eliminarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"eliminarAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && asistencias != null && !asistencias.isEmpty()) {
					// Consultamos las actuaciones para verificar si no están facturadas, en caso de
					// que lo estén mandamos mensaje de error controlado
					for (TarjetaAsistenciaResponseItem asistencia : asistencias) {

						ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
						scsActuacionasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
								.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()));
						List<ScsActuacionasistencia> actuaciones = scsActuacionasistenciaExtendsMapper
								.selectByExample(scsActuacionasistenciaExample);
						if (actuaciones != null && !actuaciones.isEmpty()
								&& actuaciones.stream().anyMatch(actuacion -> "1".equals(actuacion.getFacturado()))) {
							error.setCode(500);
							error.setDescription("No se pueden eliminar las actuaciones que están facturadas");
							error.setMessage("No se pueden eliminar las actuaciones que están facturadas");
							deleteResponseDTO.setError(error);
							break;
						}
					}
					// Si no ha habido error, borramos
					if (deleteResponseDTO.getError() == null) {
						for (TarjetaAsistenciaResponseItem asistencia : asistencias) {

							// Borramos la documentacion
							ScsDocumentacionasiExample scsDocumentacionasiExample = new ScsDocumentacionasiExample();
							scsDocumentacionasiExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
									.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()));

							affectedRows += scsDocumentacionasiMapper.deleteByExample(scsDocumentacionasiExample);

							// Borramos las actuaciones
							ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
							scsActuacionasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
									.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()));

							affectedRows += scsActuacionasistenciaExtendsMapper
									.deleteByExample(scsActuacionasistenciaExample);

							// Borramos los contrarios
							ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
							scsContrariosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
									.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()));

							affectedRows += scsContrariosasistenciaMapper
									.deleteByExample(scsContrariosasistenciaExample);

							// Borramos los delitos
							ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
							scsDelitosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andAnioEqualTo(Short.valueOf(asistencia.getAnio()))
									.andNumeroEqualTo(Long.valueOf(asistencia.getNumero()));
							affectedRows += scsDelitosasistenciaMapper.deleteByExample(scsDelitosasistenciaExample);

							// Por ultimo, borramos la asistencia
							ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
							scsAsistenciaKey.setIdinstitucion(idInstitucion);
							scsAsistenciaKey.setAnio(Short.valueOf(asistencia.getAnio()));
							scsAsistenciaKey.setNumero(Long.valueOf(asistencia.getNumero()));
							affectedRows += scsAsistenciaExtendsMapper.deleteByPrimaryKey(scsAsistenciaKey);

						}
					}

					if (affectedRows > 0) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					} else if (deleteResponseDTO.getError() == null) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						deleteResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("eliminarAsistencias() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al eliminar fisicamente las asistencias: " + e);
			error.description("Error al eliminar fisicamente las asistencias: " + e);
			deleteResponseDTO.setError(error);
		}
		return deleteResponseDTO;
	}

	@Override
	public StringDTO isUnicaAsistenciaPorGuardia(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO stringDTO = new StringDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"isUnicaAsistenciaPorGuardia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"isUnicaAsistenciaPorGuardia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					for (TarjetaAsistenciaResponseItem asistencia : asistencias) {

						ScsCabeceraguardiasKey scsCabeceraguardiasKey = new ScsCabeceraguardiasKey();
						scsCabeceraguardiasKey.setIdguardia(Integer.valueOf(asistencia.getIdGuardia()));
						scsCabeceraguardiasKey.setIdinstitucion(idInstitucion);
						scsCabeceraguardiasKey.setIdpersona(Long.valueOf(asistencia.getIdLetradoGuardia()));
						scsCabeceraguardiasKey.setIdturno(Integer.valueOf(asistencia.getIdTurno()));
						scsCabeceraguardiasKey
								.setFechainicio(SigaConstants.DATE_FORMAT.parse(asistencia.getFechaAsistencia()));

						ScsCabeceraguardias scsCabeceraguardias = scsCabeceraguardiasExtendsMapper
								.selectByPrimaryKey(scsCabeceraguardiasKey);

						if (scsCabeceraguardias != null) {
							ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
							scsAsistenciaExample.createCriteria()
									.andIdguardiaEqualTo(scsCabeceraguardias.getIdguardia())
									.andIdturnoEqualTo(scsCabeceraguardias.getIdturno())
									.andFechahoraBetween(scsCabeceraguardias.getFechainicio(),
											scsCabeceraguardias.getFechaFin())
									.andIdpersonacolegiadoEqualTo(scsCabeceraguardias.getIdpersona());
							long numAsistencias = scsAsistenciaExtendsMapper.countByExample(scsAsistenciaExample);
							if (numAsistencias == 0) {
								stringDTO.setValor("S");
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("isUnicaAsistenciaPorGuardia() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al obtener el numero de asistencias: " + e);
			error.description("Error al obtener el numero de asistencias: " + e);
		}
		return stringDTO;
	}

	@Override
	public UpdateResponseDTO desvalidarGuardiaAsistencia(HttpServletRequest request,
			List<TarjetaAsistenciaResponseItem> asistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		long numAsistencias = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desvalidarGuardiaAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desvalidarGuardiaAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					for (TarjetaAsistenciaResponseItem asistencia : asistencias) {

						ScsCabeceraguardiasKey scsCabeceraguardiasKey = new ScsCabeceraguardiasKey();
						scsCabeceraguardiasKey.setIdguardia(Integer.valueOf(asistencia.getIdGuardia()));
						scsCabeceraguardiasKey.setIdinstitucion(idInstitucion);
						scsCabeceraguardiasKey.setIdpersona(Long.valueOf(asistencia.getIdLetradoGuardia()));
						scsCabeceraguardiasKey.setIdturno(Integer.valueOf(asistencia.getIdTurno()));
						scsCabeceraguardiasKey
								.setFechainicio(SigaConstants.DATE_FORMAT.parse(asistencia.getFechaAsistencia()));

						ScsCabeceraguardias scsCabeceraguardias = scsCabeceraguardiasExtendsMapper
								.selectByPrimaryKey(scsCabeceraguardiasKey);

						if (scsCabeceraguardias != null) {
							ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
							scsAsistenciaExample.createCriteria()
									.andIdguardiaEqualTo(scsCabeceraguardias.getIdguardia())
									.andIdturnoEqualTo(scsCabeceraguardias.getIdturno())
									.andFechahoraBetween(scsCabeceraguardias.getFechainicio(),
											scsCabeceraguardias.getFechaFin())
									.andIdpersonacolegiadoEqualTo(scsCabeceraguardias.getIdpersona());
							numAsistencias = scsAsistenciaExtendsMapper.countByExample(scsAsistenciaExample);
							if (numAsistencias == 0) {
								affectedRows += scsCabeceraguardiasExtendsMapper
										.desvalidarGuardiaColegiado(scsCabeceraguardias);
							}
						}
					}

					if (affectedRows > 0 || numAsistencias != 0) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se actualizo ningun registro");
						updateResponseDTO.setError(error);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("desvalidarGuardiaAsistencia() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al actualizar el estado de la actuacion: " + e);
			error.description("Error al actualizar el estado de la actuacion: " + e);
			updateResponseDTO.setError(error);
		}
		return updateResponseDTO;
	}

	/**
	 * Metodo encargado de actualizar la designa con los datos de la asistencia
	 * 
	 * @param scsAsistencia
	 * @return
	 */
	@Transactional
	private int updateDesignaConAsistencia(ScsAsistencia scsAsistencia) {
		int affectedRows = 0;
		ScsDesignaKey scsDesignaKey = new ScsDesignaKey();
		scsDesignaKey.setAnio(scsAsistencia.getDesignaAnio());
		scsDesignaKey.setNumero(scsAsistencia.getDesignaNumero());
		scsDesignaKey.setIdturno(scsAsistencia.getDesignaTurno());
		scsDesignaKey.setIdinstitucion(scsAsistencia.getIdinstitucion());

		// Actualizamos los datos generales
		ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(scsDesignaKey);
		scsDesigna.setNig(scsAsistencia.getNig());
		scsDesigna.setNumprocedimiento(scsAsistencia.getNumeroprocedimiento().split("/")[0]);
		scsDesigna.setAnioprocedimiento(Short.valueOf(scsAsistencia.getNumeroprocedimiento().split("/")[1]));
		scsDesigna.setObservaciones(scsAsistencia.getObservaciones());
		scsDesigna.setIdpretension(scsAsistencia.getIdpretension());
		scsDesigna.setIdjuzgado(scsAsistencia.getJuzgado());
		scsDesigna.setIdinstitucionJuzg(scsAsistencia.getJuzgadoidinstitucion());
		affectedRows += scsDesignacionesExtendsMapper.updateByPrimaryKey(scsDesigna);

		// Insertamos el interesado (asistido de la asistencia)
		if (scsAsistencia.getIdpersonajg() != null) {
			// Borramos los interesados originales
			ScsDefendidosdesignaExample scsDefendidosdesignaExample = new ScsDefendidosdesignaExample();
			scsDefendidosdesignaExample.createCriteria().andAnioEqualTo(scsAsistencia.getDesignaAnio())
					.andNumeroEqualTo(scsAsistencia.getDesignaNumero())
					.andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
					.andIdturnoEqualTo(scsAsistencia.getDesignaTurno());

			scsDefendidosdesignasExtendsMapper.deleteByExample(scsDefendidosdesignaExample);

			// Introducimos los nuevos
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

		if (scsAsistencia.getIdpersonacolegiado() != null) {
			// Borramos el letrado original
			ScsDesignasletradoExample scsDesignasletradoExample = new ScsDesignasletradoExample();
			scsDesignasletradoExample.createCriteria().andAnioEqualTo(scsAsistencia.getDesignaAnio())
					.andNumeroEqualTo(scsAsistencia.getDesignaNumero())
					.andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
					.andIdturnoEqualTo(scsAsistencia.getDesignaTurno());
			scsDesignasLetradoExtendsMapper.deleteByExample(scsDesignasletradoExample);

			// Introducimos el de la asistencia
			ScsDesignasletrado scsDesignasletrado = new ScsDesignasletrado();
			scsDesignasletrado.setAnio(scsAsistencia.getDesignaAnio());
			scsDesignasletrado.setNumero(scsAsistencia.getDesignaNumero());
			scsDesignasletrado.setIdturno(scsAsistencia.getDesignaTurno());
			scsDesignasletrado.setIdpersona(scsAsistencia.getIdpersonacolegiado());
			scsDesignasletrado.setFechadesigna(scsAsistencia.getFechahora());
			scsDesignasletrado.setIdinstitucion(scsAsistencia.getIdinstitucion());
			scsDesignasletrado.setManual((short) 0);
			scsDesignasletrado.setLetradodelturno("S");
			scsDesignasletrado.setFechamodificacion(new Date());
			scsDesignasletrado.setUsumodificacion(1);
			affectedRows += scsDesignasLetradoExtendsMapper.insertSelective(scsDesignasletrado);
		}
		ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
		scsContrariosasistenciaExample.createCriteria().andAnioEqualTo(scsAsistencia.getAnio())
				.andNumeroEqualTo(scsAsistencia.getNumero()).andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
				.andFechabajaIsNotNull();

		List<ScsContrariosasistencia> contrarios = scsContrariosasistenciaMapper
				.selectByExample(scsContrariosasistenciaExample);

		// Si hay contrarios en la asistencia los copiamos
		if (contrarios != null && !contrarios.isEmpty()) {
			// Borramos los originales seteandoles la fecha baja
			ScsContrariosdesignaExample scsContrariosdesignaExample = new ScsContrariosdesignaExample();
			scsContrariosdesignaExample.createCriteria().andAnioEqualTo(scsAsistencia.getDesignaAnio())
					.andNumeroEqualTo(scsAsistencia.getDesignaNumero())
					.andIdturnoEqualTo(scsAsistencia.getDesignaTurno())
					.andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion());
			scsContrariosdesignaMapper.deleteByExample(scsContrariosdesignaExample);

			// Insertamos los nuevos
			for (ScsContrariosasistencia contrario : contrarios) {

				ScsContrariosdesigna scsContrariosdesigna = new ScsContrariosdesigna();
				scsContrariosdesigna.setAnio(scsAsistencia.getDesignaAnio());
				scsContrariosdesigna.setNumero(scsAsistencia.getDesignaNumero());
				scsContrariosdesigna.setIdturno(scsAsistencia.getDesignaTurno());
				scsContrariosdesigna.setIdinstitucion(scsAsistencia.getIdinstitucion());
				scsContrariosdesigna.setIdpersona(contrario.getIdpersona());
				scsContrariosdesigna.setUsumodificacion(1);
				scsContrariosdesigna.setFechamodificacion(new Date());
				affectedRows += scsContrariosdesignaMapper.insertSelective(scsContrariosdesigna);
			}
		}

		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria().andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
				.andAnioEqualTo(scsAsistencia.getAnio()).andNumeroEqualTo(scsAsistencia.getNumero());

		List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper.selectByExample(scsDelitosasistenciaExample);

		if (delitos != null && !delitos.isEmpty()) {
			String delitosString = delitos.stream().map(delito -> delito.getIddelito().toString() + ",").reduce("",
					String::concat);
			// El ultimo delito tendra una coma, se la quitamos
			delitosString = delitosString.substring(0, delitosString.length() - 1);
			ScsDesigna scsDesigna1 = new ScsDesigna();
			scsDesigna1.setAnio(scsAsistencia.getDesignaAnio());
			scsDesigna1.setNumero(scsAsistencia.getDesignaNumero());
			scsDesigna1.setIdturno(scsAsistencia.getDesignaTurno());
			scsDesigna1.setIdinstitucion(scsAsistencia.getIdinstitucion());
			scsDesigna1.setDelitos(delitosString);
			affectedRows += scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna1);
		}

		return affectedRows;
	}

	/**
	 * Metodo que copia los datos de la Asistencia al EJG
	 *
	 * @param scsAsistencia
	 * @param usuario 
	 * @return
	 */
	@Transactional
	private int updateEJGconAsistencia(ScsAsistencia scsAsistencia, Integer usuario) {
		int affectedRows = 0;
		ScsEjgKey scsEjgKey = new ScsEjgKey();
		scsEjgKey.setAnio(scsAsistencia.getEjganio());
		scsEjgKey.setNumero(scsAsistencia.getEjgnumero());
		scsEjgKey.setIdinstitucion(scsAsistencia.getIdinstitucion());
		scsEjgKey.setIdtipoejg(scsAsistencia.getEjgidtipoejg());
		ScsEjg scsEjg = scsEjgExtendsMapper.selectByPrimaryKey(scsEjgKey);

		// Copiamos los delitos si los hubiera
		// Primero borramos los originales
		ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();

		delitosEjgExample.createCriteria().andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
				.andAnioEqualTo(scsEjg.getAnio()).andNumeroEqualTo(scsEjg.getNumero())
				.andIdtipoejgEqualTo(scsEjg.getIdtipoejg());

		scsDelitosejgMapper.deleteByExample(delitosEjgExample);

		ScsDelitosasistenciaExample delitosAsistenciaExample = new ScsDelitosasistenciaExample();

		delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
				.andAnioEqualTo(scsAsistencia.getAnio()).andNumeroEqualTo(scsAsistencia.getNumero());

		List<ScsDelitosasistencia> delitosAsistencia = scsDelitosasistenciaMapper
				.selectByExample(delitosAsistenciaExample);
		// Si la asistencia tuviera delitos los copiamos en la tabla de delitos ejg
		if (delitosAsistencia != null && !delitosAsistencia.isEmpty()) {
			String delitosString = "";

			ScsDelitosejg delitosejg = new ScsDelitosejg();
			delitosejg.setAnio(scsEjg.getAnio());
			delitosejg.setNumero(scsEjg.getNumero());
			delitosejg.setIdinstitucion(scsEjg.getIdinstitucion());
			delitosejg.setIdtipoejg(scsEjg.getIdtipoejg());
			delitosejg.setUsumodificacion(usuario);
			delitosejg.setFechamodificacion(new Date());

			for (ScsDelitosasistencia delitoAsistencia : delitosAsistencia) {
				if (!UtilidadesString.esCadenaVacia(delitosString)) {
					delitosString += ",";
				}
				delitosString += delitoAsistencia.getIddelito();

				delitosejg.setIddelito(delitoAsistencia.getIddelito());

				affectedRows += scsDelitosejgMapper.insert(delitosejg);
			}
			// Seteamos el string de delitos para posteriormente updatear
			if (!UtilidadesString.esCadenaVacia(delitosString)) {
				scsEjg.setDelitos(delitosString);
			} else {
				scsEjg.setDelitos(null);
			}
		}

		// Copiamos los contrarios
		ScsContrariosejgExample scsContrariosejgExample = new ScsContrariosejgExample();
		scsContrariosejgExample.createCriteria().andIdinstitucionEqualTo(scsEjg.getIdinstitucion())
				.andAnioEqualTo(scsEjg.getAnio()).andNumeroEqualTo(scsEjg.getNumero())
				.andIdtipoejgEqualTo(scsEjg.getIdtipoejg());
		scsContrariosejgExtendsMapper.deleteByExample(scsContrariosejgExample);

		ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
		scsContrariosasistenciaExample.createCriteria().andAnioEqualTo(scsAsistencia.getAnio())
				.andNumeroEqualTo(scsAsistencia.getNumero()).andIdinstitucionEqualTo(scsAsistencia.getIdinstitucion())
				.andFechabajaIsNotNull();

		List<ScsContrariosasistencia> contrarios = scsContrariosasistenciaMapper
				.selectByExample(scsContrariosasistenciaExample);
		if (contrarios != null && !contrarios.isEmpty()) {
			ScsContrariosejg newContrarioEjg = new ScsContrariosejg();
			newContrarioEjg.setAnio(scsEjg.getAnio());
			newContrarioEjg.setNumero(scsEjg.getNumero());
			newContrarioEjg.setIdtipoejg(scsEjg.getIdtipoejg());
			newContrarioEjg.setIdinstitucion(scsEjg.getIdinstitucion());
			newContrarioEjg.setFechamodificacion(new Date());
			newContrarioEjg.setUsumodificacion(usuario);

			for (ScsContrariosasistencia contrario : contrarios) {
				newContrarioEjg.setIdpersona(contrario.getIdpersona());
				affectedRows += scsContrariosejgExtendsMapper.insertSelective(newContrarioEjg);
			}
		}

		// Copiamos datos restantes
		scsEjg.setGuardiaturnoIdturno(scsAsistencia.getIdturno());
		scsEjg.setGuardiaturnoIdguardia(scsAsistencia.getIdguardia());
		//No se incluye el letrado como tramitador
		//scsEjg.setIdpersona(scsAsistencia.getIdpersonacolegiado());
		scsEjg.setIdpersonajg(scsAsistencia.getIdpersonajg());
		scsEjg.setJuzgado(scsAsistencia.getJuzgado());
		scsEjg.setJuzgadoidinstitucion(scsAsistencia.getJuzgadoidinstitucion());
		scsEjg.setComisaria(scsAsistencia.getComisaria());
		scsEjg.setComisariaidinstitucion(scsAsistencia.getComisariaidinstitucion());
		if (!UtilidadesString.esCadenaVacia(scsAsistencia.getNumeroprocedimiento())) {
			scsEjg.setNumeroprocedimiento(scsAsistencia.getNumeroprocedimiento());
		} else {
			scsEjg.setNumeroprocedimiento(null);
			scsEjg.setAnioprocedimiento(null);
		}
		scsEjg.setNumerodiligencia(scsAsistencia.getNumerodiligencia());
		scsEjg.setNig(scsAsistencia.getNig());
		scsEjg.setIdpretension(scsAsistencia.getIdpretension().longValue());
		scsEjg.setUsumodificacion(usuario);
		scsEjg.setFechamodificacion(new Date());

		affectedRows += scsEjgExtendsMapper.updateByPrimaryKey(scsEjg);
		
		//Creamos el solicitante en la unidad familiar
		if(scsAsistencia.getIdpersonajg() != null) {
			
			ScsUnidadfamiliarejg record = new ScsUnidadfamiliarejg();
			record.setAnio(scsEjg.getAnio());
			record.setEncalidadde("SOLICITANTE");
			record.setFechamodificacion(new Date());
			record.setIdinstitucion(scsEjg.getIdinstitucion());
			record.setIdpersona(scsAsistencia.getIdpersonajg());
			record.setIdtipoejg(scsEjg.getIdtipoejg());
			record.setNumero(scsEjg.getNumero());
			record.setObservaciones("Copiado desde la asistencia: "+scsAsistencia.getAnio()+"/"+scsAsistencia.getNumero());
			record.setSolicitante(new Short("1"));
			record.setUsumodificacion(usuario);
			affectedRows += scsUnidadfamiliarejgMapper.insert(record);
		}

		return affectedRows;
	}

	/**
	 * Metodo que llama al servicio de insert en GEN_FICHERO y sube el fichero
	 * físico
	 *
	 * @param bytes
	 * @param idUsuario
	 * @param idInstitucion
	 * @param nombreFichero
	 * @param extension
	 * @param anioNumero
	 * @return
	 */
	private Long uploadFile(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
			String extension, String anioNumero) {

		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFicheroAsi(idInstitucion);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado a la asistencia " + anioNumero);

		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());

		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());

		return ficheroVo.getIdfichero();
	}

	/**
	 * Metodo que obtiene el directorio donde se almacenan los documentos de las
	 * asistencias
	 *
	 * @param idInstitucion
	 * @return
	 */
	private String getDirectorioFicheroAsi(Short idInstitucion) {

		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(path);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraemos el path concreto para actuaciones
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.asistencias");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		return directorioFichero.toString();
	}

	/**
	 *
	 * Metodo que devuelve el tipo Mime (Cabecera Content-Type) dependiendo el tipo
	 * de extension del documento
	 *
	 * @param extension
	 * @return
	 */
	private String getMimeType(String extension) {

		String mime = "";

		switch (extension.toLowerCase()) {

		case ".doc":
			mime = "application/msword";
			break;
		case ".docx":
			mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			break;
		case ".pdf":
			mime = "application/pdf";
			break;
		case ".jpg":
			mime = "image/jpeg";
			break;
		case ".png":
			mime = "image/png";
			break;
		case ".rtf":
			mime = "application/rtf";
			break;
		case ".txt":
			mime = "text/plain";
			break;
		}

		return mime;
	}

	/**
	 * Metodo que devuelve un ZIP en {@link InputStream} para cuando se va a
	 * descargar mas de un archivo a la vez
	 *
	 * @param documentos
	 * @param idInstitucion
	 * @return
	 */
	private InputStream getZipFileDocumentosAsi(List<DocumentacionAsistenciaItem> documentos, Short idInstitucion) {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (DocumentacionAsistenciaItem doc : documentos) {
				int i = 0;
				GenFicheroKey genFicheroKey = new GenFicheroKey();
				genFicheroKey.setIdfichero(Long.valueOf(documentos.get(0).getIdFichero()));
				genFicheroKey.setIdinstitucion(idInstitucion);
				String extension = genFicheroMapper.selectByPrimaryKey(genFicheroKey).getExtension();
				if (UtilidadesString.esCadenaVacia(doc.getNombreFichero())) {
					doc.setNombreFichero("default(" + (i++) + ")." + extension);
				}
				zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
				String path = getDirectorioFicheroAsi(idInstitucion);
				path += File.separator + idInstitucion + "_" + doc.getIdFichero() + "." + extension;
				File file = new File(path);
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	/**
	 * Metodo que comprueba si la asistencia esta ya facturada para no permitir la
	 * anulacion
	 * 
	 * @param asistencia
	 * @return
	 */
	private boolean isFacturada(TarjetaAsistenciaResponseItem asistencia, Short idInstitucion) {
		boolean isFacturada = false;

		ScsAsistenciaKey scsAsistenciaKey = new ScsAsistencia();
		scsAsistenciaKey.setAnio(Short.valueOf(asistencia.getAnio()));
		scsAsistenciaKey.setNumero(Long.valueOf(asistencia.getNumero()));
		scsAsistenciaKey.setIdinstitucion(idInstitucion);
		ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
		if (scsAsistencia != null) {
			isFacturada = "1".equals(scsAsistencia.getFacturado());
		}

		return isFacturada;
	}

	@Transactional
	private void duplicarAsistencia(ScsAsistencia newAsistencia, String idAsistenciaCopy, Short idInstitucion) {
		ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
		scsAsistenciaKey.setAnio(Short.valueOf(idAsistenciaCopy.split("/")[0]));
		scsAsistenciaKey.setNumero(Long.valueOf(idAsistenciaCopy.split("/")[1]));
		scsAsistenciaKey.setIdinstitucion(idInstitucion);
		ScsAsistencia scsAsistenciaOld = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

		newAsistencia.setObservaciones(scsAsistenciaOld.getObservaciones());
		newAsistencia.setDatosdefensajuridica(scsAsistenciaOld.getDatosdefensajuridica());
		newAsistencia.setIncidencias(scsAsistenciaOld.getIncidencias());
		newAsistencia.setJuzgado(scsAsistenciaOld.getJuzgado());
		newAsistencia.setJuzgadoidinstitucion(scsAsistenciaOld.getJuzgadoidinstitucion());
		newAsistencia.setComisaria(scsAsistenciaOld.getComisaria());
		newAsistencia.setComisariaidinstitucion(scsAsistenciaOld.getComisariaidinstitucion());
		newAsistencia.setIdorigenasistencia(scsAsistenciaOld.getIdorigenasistencia());
		newAsistencia.setIdpretension(scsAsistenciaOld.getIdpretension());
		newAsistencia.setNig(scsAsistenciaOld.getNig());
		newAsistencia.setNumeroprocedimiento(scsAsistenciaOld.getNumeroprocedimiento());
		//newAsistencia.setIdpersonajg(scsAsistenciaOld.getIdpersonajg());
		newAsistencia.setDelitosimputados(scsAsistenciaOld.getDelitosimputados());

		scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(newAsistencia);

		ScsContrariosasistenciaExample scsContrariosasistenciaExample = new ScsContrariosasistenciaExample();
		scsContrariosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(scsAsistenciaOld.getAnio()).andNumeroEqualTo(scsAsistenciaOld.getNumero())
				.andFechabajaIsNotNull();
		List<ScsContrariosasistencia> contrarios = scsContrariosasistenciaMapper
				.selectByExample(scsContrariosasistenciaExample);

		if (contrarios != null && !contrarios.isEmpty()) {
			for (int i = 0; i < contrarios.size(); i++) {
				ScsContrariosasistencia contrario = contrarios.get(i);
				contrario.setAnio(newAsistencia.getAnio());
				contrario.setNumero(newAsistencia.getNumero());

				scsContrariosasistenciaMapper.insert(contrario);
			}
		}

		ScsDelitosasistenciaExample scsDelitosasistenciaExample = new ScsDelitosasistenciaExample();
		scsDelitosasistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
				.andAnioEqualTo(scsAsistenciaOld.getAnio()).andNumeroEqualTo(scsAsistenciaOld.getNumero());
		List<ScsDelitosasistencia> delitos = scsDelitosasistenciaMapper.selectByExample(scsDelitosasistenciaExample);
		if (delitos != null && !delitos.isEmpty()) {
			for (int i = 0; i < delitos.size(); i++) {
				ScsDelitosasistencia delito = delitos.get(i);
				delito.setAnio(newAsistencia.getAnio());
				delito.setNumero(newAsistencia.getNumero());
				delito.setIdinstitucion(newAsistencia.getIdinstitucion());
				delito.setFechamodificacion(new Date());
				scsDelitosasistenciaMapper.insert(delito);
			}
		}
	}
}
