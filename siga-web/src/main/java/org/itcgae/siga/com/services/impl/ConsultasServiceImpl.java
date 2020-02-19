package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.com.CampoDinamicoItem;
import org.itcgae.siga.DTOs.com.CamposDinamicosDTO;
import org.itcgae.siga.DTOs.com.ConsultaDTO;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.com.KeyItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.ConEjecucion;
import org.itcgae.siga.db.entities.ConEjecucionExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModModeloPerfiles;
import org.itcgae.siga.db.entities.ModModeloPlantilladocumento;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantilladocumento;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaExample;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.ConEjecucionMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModModeloPerfilesMapper;
import org.itcgae.siga.db.mappers.ModModeloPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModModelocomunicacionMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocumentoMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConListadoModelosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConListadoPlantillasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConModulosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConObjetivoExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvTipoEnvioExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModKeyclasecomunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModPlantillaDocumentoConsultaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.BusinessSQLException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultasServiceImpl implements IConsultasService {

	private static final String CARACTER_REEMPLAZO_PUNTOS = "####";

	private Logger LOGGER = Logger.getLogger(ConsultasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;

	@Autowired
	private ConObjetivoExtendsMapper _conObjetivoExtendsMapper;

	@Autowired
	private ConModulosExtendsMapper _conModulosExtendsMapper;

	@Autowired
	private ConClaseComunicacionExtendsMapper _conClaseComunicacionExtendsMapper;

	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;

	@Autowired
	private ConListadoModelosExtendsMapper _conListadoModelosExtendsMapper;

	@Autowired
	private ConListadoPlantillasExtendsMapper _conListadoPlantillasExtendsMapper;

	@Autowired
	private ConConsultaMapper _conConsultaMapper;

	@Autowired
	private ModPlantilladocConsultaMapper _modPlantilladocConsultaMapper;

	@Autowired
	private ModPlantillaenvioConsultaMapper _modPlantillaenvioConsultaMapper;

	@Autowired
	private ModKeyclasecomunicacionExtendsMapper _modKeyclasecomunicacionExtendsMapper;

	@Autowired
	private EnvTipoEnvioExtendsMapper _envTipoEnvioExtendsMapper;

	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	private ModModelocomunicacionMapper _modModelocomunicacionMapper;

	@Autowired
	private ModModeloPlantilladocumentoMapper _modModeloPlantilladocumentoMapper;

	@Autowired
	private ModPlantilladocumentoMapper _modPlantilladocumentoMapper;

	@Autowired
	private ModModeloPerfilesMapper modModeloPerfilesMapper;

	@Autowired
	private ModPlantillaDocumentoConsultaExtendsMapper _modPlantillaDocumentoConsultaExtendsMapper;

	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;

	@Autowired
	private ConEjecucionMapper _conEjecucionMapper;

	@Override
	public ComboDTO modulo(HttpServletRequest request) {
		LOGGER.info("modulo() -> Entrada al servicio para obtener combo modulos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _conModulosExtendsMapper.selectModulos();

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener combo modulo");

		return comboDTO;
	}

	@Override
	public ComboDTO objetivo(HttpServletRequest request) {
		LOGGER.info("objetivo() -> Entrada al servicio para obtener combo objetivos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

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
				comboItems = _conObjetivoExtendsMapper.selectObjetivos(usuario.getIdlenguaje());

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener combo objetivos");

		return comboDTO;
	}

	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		LOGGER.info("claseComunicacion() -> Entrada al servicio para obtener combo claseComunicacion");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

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

				comboItems = _conClaseComunicacionExtendsMapper.selectTipoClaseComunicacion(usuario.getIdlenguaje());

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo claseComunicacion");

		return comboDTO;
	}

	@Override
	public ComboDTO claseComunicacionByModulo(String idModulo, HttpServletRequest request) {
		LOGGER.info(
				"claseComunicacion() -> Entrada al servicio para obtener combo claseComunicacion filtrado por módulo");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _conClaseComunicacionExtendsMapper.selectTipoClaseComunicacionByModulo(idModulo);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo claseComunicacion");

		return comboDTO;
	}

	@Override
	public ConsultasDTO consultasSearch(HttpServletRequest request, ConsultasSearch filtros) {
		LOGGER.info("consultasSearch() -> Entrada al servicio de búsqueda de consultas");

		ConsultasDTO consultasDTO = new ConsultasDTO();
		List<ConsultaItem> consultasList = new ArrayList<ConsultaItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> listaPerfiles = UserTokenUtils.getPerfilesFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					consultasList = _conConsultasExtendsMapper.selectConsultasSearch(usuario.getIdinstitucion(),
							usuario.getIdlenguaje(), listaPerfiles, filtros);
					if (consultasList.size() > 0) {
						consultasDTO.setConsultaItem(consultasList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					consultasDTO.setError(error);
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("consultasSearch() -> Salida del servicio de búsqueda de consultas");
		return consultasDTO;
	}

	@Override
	@Transactional
	public ConsultaDTO duplicarConsulta(HttpServletRequest request, ConsultaItem consulta) {
		LOGGER.info("duplicarConsulta() -> Entrada al servicio de duplicar consultas");

		Error error = new Error();
		ConsultaDTO respuesta = new ConsultaDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				try {
					AdmUsuarios usuario = usuarios.get(0);
					String idConsulta = consulta.getIdConsulta();
					ConConsultaKey key = new ConConsultaKey();
					key.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
					key.setIdinstitucion(Short.valueOf(consulta.getIdInstitucion()));
					ConConsulta conConsulta = _conConsultaMapper.selectByPrimaryKey(key);
					NewIdDTO id = _conConsultasExtendsMapper.selectMaxIDConsulta();
					conConsulta.setIdconsulta(Long.valueOf(id.getNewId()));

					String descripcion = consulta.getNombre() + SigaConstants.SUFIJO_CONSULTA_COM_DUPLICADO;
					conConsulta.setIdinstitucion(idInstitucion);
					if (idInstitucion.shortValue() != SigaConstants.IDINSTITUCION_2000.shortValue()) {
						conConsulta.setGeneral("N");
					} else {
						conConsulta.setGeneral("S");
					}
					conConsulta.setDescripcion(descripcion);
					conConsulta.setFechamodificacion(new Date());
					conConsulta.setUsumodificacion(usuario.getIdusuario());
					_conConsultaMapper.insert(conConsulta);

					// Cambiamos la consulta a devolver

					consulta.setIdConsulta(String.valueOf(conConsulta.getIdconsulta()));
					consulta.setGenerica(conConsulta.getGeneral());
					consulta.setIdInstitucion(String.valueOf(conConsulta.getIdinstitucion()));
					consulta.setNombre(conConsulta.getDescripcion());

					respuesta.setConsultaItem(consulta);

					ModPlantilladocConsultaExample example = new ModPlantilladocConsultaExample();
					example.createCriteria().andIdconsultaEqualTo(Long.valueOf(idConsulta))
							.andIdinstitucionEqualTo(Short.valueOf(consulta.getIdInstitucion())).andFechabajaIsNull();
					List<ModPlantilladocConsulta> modPlantilladocConsulta = _modPlantilladocConsultaMapper
							.selectByExample(example);

					if (null != modPlantilladocConsulta && modPlantilladocConsulta.size() > 0) {

						for (ModPlantilladocConsulta mod : modPlantilladocConsulta) {
							mod.setIdinstitucionConsulta(Short.valueOf(consulta.getIdInstitucion()));
							mod.setFechamodificacion(new Date());
							mod.setUsumodificacion(usuario.getIdusuario());
							mod.setIdconsulta(Long.valueOf(consulta.getIdConsulta()));
							_modPlantilladocConsultaMapper.insert(mod);
						}
					}

					// Creando el modelo por defecto asociado a la copia
					ModModelocomunicacion modeloCom = crearModelo(consulta, usuario, idInstitucion);
					_modModelocomunicacionMapper.insert(modeloCom);

					generacionDeRelaciones(modeloCom, conConsulta, usuario, idInstitucion);

					// Le añadimos al modelo todos los perfiles
					List<ComboItem> comboItems = admPerfilExtendsMapper.selectListadoPerfiles(idInstitucion);

					if (comboItems != null && comboItems.size() > 0) {
						for (ComboItem item : comboItems) {
							ModModeloPerfiles perfil = new ModModeloPerfiles();
							perfil.setIdmodelocomunicacion(Long.valueOf(modeloCom.getIdmodelocomunicacion()));
							perfil.setFechamodificacion(new Date());
							perfil.setIdperfil(item.getValue());
							perfil.setUsumodificacion(usuario.getIdusuario());
							perfil.setIdinstitucion(idInstitucion);
							modModeloPerfilesMapper.insert(perfil);
						}
					}

				} catch (Exception e) {
					error.setCode(500);
					error.setMessage("Error al duplicar las consultas");
					error.setDescription(e.getMessage());
					respuesta.setError(error);
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("duplicarConsulta() -> Salida del servicio de duplicar consultas");
		return respuesta;
	}

	@Override
	@Transactional
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem[] consultas) {
		LOGGER.info("borrarConsulta() -> Entrada al servicio de borrar consulta");

		Error respuesta = new Error();

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
					boolean noBorrada = false;
					for (int i = 0; i < consultas.length; i++) {
						ConConsultaKey consultaKey = new ConConsultaKey();
						consultaKey.setIdconsulta(Long.valueOf(consultas[i].getIdConsulta()));
						consultaKey.setIdinstitucion(Short.valueOf(consultas[i].getIdInstitucion()));
						ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(consultaKey);
						boolean consultaAsociada = false;
						if (consulta.getIdobjetivo() != null) {
							ModPlantilladocConsultaExample plantillaDocExample = new ModPlantilladocConsultaExample();
							plantillaDocExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdconsultaEqualTo(consulta.getIdconsulta());
							List<ModPlantilladocConsulta> plantillaConsulta = _modPlantilladocConsultaMapper
									.selectByExample(plantillaDocExample);
							if (plantillaConsulta != null && plantillaConsulta.size() > 0) {
								consultaAsociada = true;
							}
							ModPlantillaenvioConsultaExample envioConsultaExample = new ModPlantillaenvioConsultaExample();
							envioConsultaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdconsultaEqualTo(consulta.getIdconsulta());
							List<ModPlantillaenvioConsulta> plantillaEnvio = _modPlantillaenvioConsultaMapper
									.selectByExample(envioConsultaExample);
							if (plantillaEnvio != null && plantillaEnvio.size() > 0) {
								consultaAsociada = true;
							}
						}
						boolean general = false;

						if (consulta.getGeneral().equals("S") || consulta.getGeneral().equals("1")) {
							general = true;
						}
						if (general) {
							// las consultas genericas solo las puede borrar el colegio general
							if (idInstitucion == 2000) {
								// if(!consultaAsociada){
								// _conConsultaMapper.deleteByPrimaryKey(consultaKey);
								// }else{
								if (consulta.getFechabaja() != null) {
									consulta.setFechabaja(null);
								} else {
									consulta.setFechabaja(new Date());
								}
								consulta.setFechamodificacion(new Date());
								consulta.setUsumodificacion(usuario.getIdusuario());
								_conConsultaMapper.updateByPrimaryKey(consulta);
								// }
							} else {
								noBorrada = true;
							}
						} else if (!general && consulta.getIdinstitucion().equals(idInstitucion)) {
							// if(!consultaAsociada){
							// _conConsultaMapper.deleteByPrimaryKey(consultaKey);
							// }else{
							if (consulta.getFechabaja() != null) {
								consulta.setFechabaja(null);
							} else {
								consulta.setFechabaja(new Date());
							}
							consulta.setFechamodificacion(new Date());
							consulta.setUsumodificacion(usuario.getIdusuario());
							_conConsultaMapper.updateByPrimaryKey(consulta);
							// }
						} else {
							noBorrada = true;
						}
					}
					respuesta.setCode(200);
					// Si ha habido alguna consulta no borrada se le indica mediante un mensaje al
					// front para indicarselo al usuario
					if (noBorrada) {
						respuesta.setMessage("noBorrar");
					}
					respuesta.setDescription("Consultas borradas");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al borrar consulta");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("borrarConsulta() -> Salida del servicio de borrar consulta");
		return respuesta;

	}

	@Override
	@Transactional
	public Error guardarDatosGenerales(HttpServletRequest request, ConsultaItem consultaDTO) {
		LOGGER.info("guardarDatosGenerales() -> Entrada al servicio para guardar tarjeta general");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error respuesta = new Error();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					boolean camposIncorrectos = false;
					if (consultaDTO.getIdConsulta() == null) {
						ConConsulta consulta = new ConConsulta();
						NewIdDTO maxId = _conConsultasExtendsMapper.selectMaxIDConsulta();
						consulta.setIdconsulta(Long.valueOf(maxId.getNewId()));
						consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setIdinstitucion(idInstitucion);
						consulta.setObservaciones(consultaDTO.getDescripcion());
						consulta.setDescripcion(consultaDTO.getNombre());
						consulta.setIdobjetivo(Long.parseLong(consultaDTO.getIdObjetivo()));

						if (consultaDTO.getIdClaseComunicacion() != null
								&& !"".equalsIgnoreCase(consultaDTO.getIdClaseComunicacion())) {

							consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						}
						consulta.setGeneral(consultaDTO.getGenerica());
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						String sentencia = "";
						switch (consultaDTO.getIdObjetivo().toString()) {
						case "1":
							consulta.setTipoconsulta("E");
							sentencia = insertarSelectDestinatarios() + "<FROM></FROM>";
							break;
						case "2":
							consulta.setTipoconsulta("M");
							sentencia = "<SELECT></SELECT><FROM></FROM>";
							break;
						case "3":
							consulta.setTipoconsulta("W");
							sentencia = "<SELECT></SELECT><FROM></FROM>";
							break;
						case "4":
							consulta.setTipoconsulta("C");
							sentencia = "<SELECT></SELECT><FROM></FROM>";
							break;
						}

						// Si tiene clase de comunicación añadimos el where con las claves
						sentencia = insertarClaves(consulta.getIdclasecomunicacion(), sentencia);
						consulta.setSentencia(sentencia);

						// INSERTAMOS LA CONSULTA
						_conConsultaMapper.insert(consulta);

						// Creamos el modelo de comunicación con la clase Consultas genericas para
						// generacion de excel o informe

						ModModelocomunicacion modeloCom = crearModelo(consultaDTO, usuario, idInstitucion);
						//
						// SE INSERTA EL MODELO DE COMUNICACIÓN POR DEFECTO
						_modModelocomunicacionMapper.insert(modeloCom);

						// ModPlantilladocumento plantilla = new ModPlantilladocumento();

						// Creamos la plantilla de documento para geenración de excel
						// plantilla.setFechamodificacion(new Date());
						// plantilla.setIdioma(SigaConstants.LENGUAJE_DEFECTO);
						// plantilla.setPlantilla(modeloCom.getNombre());
						// plantilla.setUsumodificacion(usuario.getIdusuario());
						// _modPlantilladocumentoMapper.insert(plantilla);
						//
						// //Creamos la plantilla en el resto de idiomas
						//
						// plantilla.setIdioma("2");
						// _modPlantillaDocumentoConsultaExtendsMapper.insertModPlantillaDocumento(plantilla);
						//
						// plantilla.setIdioma("3");
						// _modPlantillaDocumentoConsultaExtendsMapper.insertModPlantillaDocumento(plantilla);
						//
						// plantilla.setIdioma("4");
						// _modPlantillaDocumentoConsultaExtendsMapper.insertModPlantillaDocumento(plantilla);
						// -------------------------------------

						generacionDeRelaciones(modeloCom, consulta, usuario, idInstitucion);

						// Le añadimos al modelo todos los perfiles
						List<ComboItem> comboItems = admPerfilExtendsMapper.selectListadoPerfiles(idInstitucion);

						if (comboItems != null && comboItems.size() > 0) {
							for (ComboItem item : comboItems) {
								ModModeloPerfiles perfil = new ModModeloPerfiles();
								perfil.setIdmodelocomunicacion(Long.valueOf(modeloCom.getIdmodelocomunicacion()));
								perfil.setFechamodificacion(new Date());
								perfil.setIdperfil(item.getValue());
								perfil.setUsumodificacion(usuario.getIdusuario());
								perfil.setIdinstitucion(idInstitucion);
								modModeloPerfilesMapper.insert(perfil);
							}
						}

						respuesta.setMessage(consulta.getIdconsulta().toString());
						respuesta.setDescription(consulta.getSentencia());
						respuesta.setInfoURL(consulta.getIdinstitucion().toString());
						respuesta.setCode(200);

					} else {
						Long objetivoAnterior = (long) 0;
						Short claseAnterior = (short) 0;
						ConConsultaKey key = new ConConsultaKey();
						key.setIdconsulta(Long.parseLong(consultaDTO.getIdConsulta()));
						key.setIdinstitucion(idInstitucion);
						ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
						if (consultaDTO.getIdModulo() != null)
							consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setDescripcion(consultaDTO.getNombre());
						consulta.setObservaciones(consultaDTO.getDescripcion());
						consulta.setGeneral(consultaDTO.getGenerica());

						objetivoAnterior = consulta.getIdobjetivo();
						claseAnterior = consulta.getIdclasecomunicacion();

						if (consultaDTO.getIdClaseComunicacion() != null
								&& !"".equalsIgnoreCase(consultaDTO.getIdClaseComunicacion())) {
							consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						}
						consulta.setIdobjetivo(Long.valueOf(consultaDTO.getIdObjetivo()));
						String sentencia = consulta.getSentencia();
						switch (consultaDTO.getIdObjetivo()) {
						case "1":
							// Destinarios
							consulta.setTipoconsulta("E");
							// camposIncorrectos = comprobarCamposDestinarios(consulta.getSentencia());
							// if(objetivoAnterior !=
							// Long.valueOf(consultaDTO.getIdObjetivo()).longValue()){
							// sentencia = insertarSelectDestinatarios(sentencia);
							// } Se comenta esta parte para la resolución de la incidencia SIGARNV-1233
							// insertarSelectDestinatarios(consulta.getSentencia());
							break;
						case "2":
							// Multidocumento
							consulta.setTipoconsulta("M");
							break;
						case "3":
							// condicionales
							consulta.setTipoconsulta("W");
							break;
						case "4":
							consulta.setTipoconsulta("C");
							break;
						default:
							consulta.setTipoconsulta("C");
							break;
						}

						// Si tiene clase de comunicación añadimos el where con las claves y el objetivo
						// se ha cambiado
						if (consultaDTO.getIdClaseComunicacion() != null
								&& !"".equals(consultaDTO.getIdClaseComunicacion())
								&& (claseAnterior == null || claseAnterior.shortValue() != Short
										.valueOf(consultaDTO.getIdClaseComunicacion()).shortValue())) {
							sentencia = insertarClaves(Short.parseShort(consultaDTO.getIdClaseComunicacion()),
									sentencia);
						}

						consulta.setSentencia(sentencia);

						if (consultaDTO.getIdObjetivo().equals("E")) {
							if (!camposIncorrectos) {
								respuesta.setCode(400);
								respuesta.setMessage("La estructura de la consulta no es correcta");
							} else {
								_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
								respuesta.setCode(200);
								respuesta.setMessage(consulta.getIdconsulta().toString());
								respuesta.setInfoURL(consulta.getIdinstitucion().toString());
								respuesta.setDescription(consulta.getSentencia());
							}
						} else {
							_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
							respuesta.setCode(200);
							respuesta.setMessage(consulta.getIdconsulta().toString());
							respuesta.setInfoURL(consulta.getIdinstitucion().toString());
							respuesta.setDescription(consulta.getSentencia());
						}

						// Actualizamos el idClaseComunicacion de la tabla modModeloComunicacion

						// if(resultado == 1) {

						// ModPlantilladocConsultaExample modPlantilladocConsultaExample = new
						// ModPlantilladocConsultaExample();
						// modPlantilladocConsultaExample.createCriteria().andIdconsultaEqualTo(consulta.getIdconsulta()).andIdinstitucionEqualTo(consulta.getIdinstitucion());
						// List<ModPlantilladocConsulta> modPlantilladocConsulta =
						// _modPlantilladocConsultaMapper.selectByExample(modPlantilladocConsultaExample);
						//
						// if(!modPlantilladocConsulta.isEmpty() && modPlantilladocConsulta.size() > 0)
						// {
						// ModModelocomunicacion modModelocomunicacion =
						// _modModelocomunicacionMapper.selectByPrimaryKey(modPlantilladocConsulta.get(0).getIdmodelocomunicacion());
						//
						// if(modModelocomunicacion != null) {
						// modModelocomunicacion.setIdclasecomunicacion(consulta.getIdclasecomunicacion());
						//
						// _modModelocomunicacionMapper.updateByPrimaryKey(modModelocomunicacion);
						// }
						// }

						// }
					}

				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al guardar datos generales");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("guardarDatosGenerales() -> Salida del servicio para guardar tarjeta general");
		return respuesta;
	}

	private ModModelocomunicacion crearModelo(ConsultaItem consulta, AdmUsuarios usuario, Short idInstitucion) {
		ModModelocomunicacion modeloCom = new ModModelocomunicacion();
		modeloCom.setDescripcion(consulta.getNombre());
		modeloCom.setFechamodificacion(new Date());
		modeloCom.setIdclasecomunicacion(Short.valueOf(SigaConstants.ID_CLASE_CONSULTA_GENERICA)); // para que el modelo
																									// por defecto salga
																									// en el ejecutar
		modeloCom.setIdinstitucion(idInstitucion);
		modeloCom.setNombre(consulta.getNombre());

		if (consulta.getIdInstitucion().equals(SigaConstants.IDINSTITUCION_0)) {
			modeloCom.setIdinstitucion(SigaConstants.IDINSTITUCION_2000);
			modeloCom.setPordefecto(SigaConstants.SI);
		} else {
			modeloCom.setIdinstitucion(idInstitucion);
			modeloCom.setPordefecto(SigaConstants.NO);
		}
		modeloCom.setPreseleccionar("SI");
		modeloCom.setUsumodificacion(usuario.getIdusuario());
		modeloCom.setVisible((short) 1);

		return modeloCom;
	}

	private void generacionDeRelaciones(ModModelocomunicacion modeloCom, ConConsulta consulta, AdmUsuarios usuario,
			Short idInstitucion) {
		Integer i = 0;
		do {

			ModPlantilladocumento plantilla = new ModPlantilladocumento();
			plantilla.setFechamodificacion(new Date());
			plantilla.setPlantilla(modeloCom.getNombre());
			plantilla.setUsumodificacion(usuario.getIdusuario());
			Integer idioma = i + 1;
			plantilla.setIdioma(idioma.toString());
			_modPlantilladocumentoMapper.insert(plantilla);

			// Creamos la relación con la plantilla de documento para generación de excel
			ModModeloPlantilladocumento plantillaDoc = new ModModeloPlantilladocumento();
			plantillaDoc.setFechaasociacion(new Date());
			plantillaDoc.setFechamodificacion(new Date());
			plantillaDoc.setFormatosalida(String.valueOf(SigaConstants.FORMATO_SALIDA.XLS.getCodigo()));
			plantillaDoc.setIdinforme((long) 1);
			plantillaDoc.setIdmodelocomunicacion(modeloCom.getIdmodelocomunicacion());
			plantillaDoc.setNombreficherosalida(modeloCom.getNombre());
			plantillaDoc.setUsumodificacion(usuario.getIdusuario());
			plantillaDoc.setIdplantilladocumento(plantilla.getIdplantilladocumento());
			plantillaDoc.setGeneracionexcel(Short.parseShort(SigaConstants.DB_TRUE));
			_modModeloPlantilladocumentoMapper.insert(plantillaDoc);

			// Asociamos la consulta a la plantilla de documento

			ModPlantilladocConsulta plantillaConsulta = new ModPlantilladocConsulta();
			plantillaConsulta.setFechamodificacion(new Date());
			plantillaConsulta.setIdconsulta(consulta.getIdconsulta());
			plantillaConsulta.setIdinstitucion(idInstitucion);
			plantillaConsulta.setIdinstitucionConsulta(idInstitucion);
			plantillaConsulta.setIdmodelocomunicacion(modeloCom.getIdmodelocomunicacion());
			plantillaConsulta.setIdplantilladocumento(plantilla.getIdplantilladocumento());
			plantillaConsulta.setUsumodificacion(usuario.getIdusuario());
			_modPlantilladocConsultaMapper.insert(plantillaConsulta);
			i++;
		} while (i < 4);
	}

	@Override
	public ConsultaListadoModelosDTO obtenerModelosComunicacion(HttpServletRequest request, ConsultaItem consulta) {
		LOGGER.info("obtenerModelosComunicacion() -> Entrada al servicio de obtener modelos que contienen la consulta");

		ConsultaListadoModelosDTO conListadoModelosDTO = new ConsultaListadoModelosDTO();
		List<ModelosComunicacionItem> modeloList = new ArrayList<ModelosComunicacionItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					modeloList = _conListadoModelosExtendsMapper
							.selectListadoModelos(Short.valueOf(consulta.getIdInstitucion()), consulta.getIdConsulta());
					if (modeloList.size() > 0) {
						conListadoModelosDTO.setListadoModelos(modeloList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					conListadoModelosDTO.setError(error);
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("obtenerModelosComunicacion() -> Salida al servicio de obtener modelos que contienen la consulta");

		return conListadoModelosDTO;
	}

	@Override
	public ConsultaListadoPlantillasDTO obtenerPlantillasEnvio(HttpServletRequest request, ConsultaItem consulta) {
		LOGGER.info("obtenerPlantillasEnvio() -> Entrada al servicio de obtener plantillas que contienen la consulta");

		ConsultaListadoPlantillasDTO conListadoPlantillasDTO = new ConsultaListadoPlantillasDTO();
		List<PlantillaEnvioItem> plantillasList = new ArrayList<PlantillaEnvioItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					plantillasList = _conListadoPlantillasExtendsMapper.selectListadoPlantillas(idInstitucion,
							usuario.getIdlenguaje(), consulta.getIdConsulta());
					if (plantillasList.size() > 0) {
						conListadoPlantillasDTO.setListadoPlantillas(plantillasList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					conListadoPlantillasDTO.setError(error);
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("obtenerPlantillasEnvio() -> Salida al servicio de obtener plantillas que contienen la consulta");

		return conListadoPlantillasDTO;
	}

	@Override
	@Transactional
	public Error guardarConsulta(HttpServletRequest request, ConsultaItem consultaDTO) {
		LOGGER.info("guardarConsulta() -> Entrada al servicio para guardar la sentencia de la consulta");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error respuesta = new Error();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {
					boolean etiquetasIncorrectas = false;
					boolean camposIncorrectos = false;
					boolean objetivoIncorrecto = false;
					boolean noContieneInstitucion = false;

					ConConsultaKey key = new ConConsultaKey();
					key.setIdconsulta(Long.parseLong(consultaDTO.getIdConsulta()));
					key.setIdinstitucion(idInstitucion);
					ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
					etiquetasIncorrectas = comprobarEtiquetas(consultaDTO.getSentencia());

					// Comprombamos que en el where se tienen las keys asociadas a la clase
					camposIncorrectos = comprobarClaves(consultaDTO.getSentencia(),
							consultaDTO.getIdClaseComunicacion());

					// Comprobamos que cumple el objetivo
					objetivoIncorrecto = comprobarObjetivo(consultaDTO.getSentencia(), consultaDTO.getIdObjetivo());

					noContieneInstitucion = comprobarInstitucion(consultaDTO.getSentencia());

					if (etiquetasIncorrectas) {
						respuesta.setCode(400);
						respuesta.setMessage("Faltan etiquetas");
					} else if (camposIncorrectos) {
						respuesta.setCode(400);
						respuesta.setMessage("Faltan claves por rellenar");
					} else if (noContieneInstitucion) {
						respuesta.setCode(400);
						respuesta.setMessage("La consulta ha de tener la clave %%IDINSTITUCION%%");
					} else if (objetivoIncorrecto) {
						respuesta.setCode(400);
						respuesta.setMessage("No cumple con las restricciones del objetivo");
					} else {
						consulta.setSentencia(consultaDTO.getSentencia());
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
						respuesta.setCode(200);
						respuesta.setMessage("Consulta guardada");
					}
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al guardar consulta");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("guardarConsulta() -> Salida del servicio para guardar la sentencia de la consulta");
		return respuesta;
	}

	private boolean comprobarInstitucion(String sentencia) {
		boolean incorrecta = false;
		sentencia = sentencia.toUpperCase();
		if (sentencia.indexOf(SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + SigaConstants.CAMPO_IDINSTITUCION
				+ SigaConstants.REPLACECHAR_PREFIJO_SUFIJO) == -1) {
			incorrecta = true;
		}
		return incorrecta;
	}

	@Override
	public ResponseFileDTO ejecutarConsulta(HttpServletRequest request, ConsultaItem consulta) {

		LOGGER.info("ejecutarConsulta() -> Entrada al servicio para ejecutar una consulta");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseFileDTO response = new ResponseFileDTO();
		Error error = new Error();
		File excel = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				try {

					// Reemplazamos los campos dinamicos
					String sentencia = procesarEjecutarConsulta(usuario, consulta.getSentencia(),
							consulta.getCamposDinamicos(), true);

					// Map<String,String> mapa = new HashMap<String,String>();
					// mapa = obtenerMapaConsulta(sentencia);

					sentencia = quitarEtiquetas(sentencia.toUpperCase());

					if (sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER)
							|| sentencia.contains(SigaConstants.SENTENCIA_CREATE)
							|| sentencia.contains(SigaConstants.SENTENCIA_DELETE)
							|| sentencia.contains(SigaConstants.SENTENCIA_DROP)
							|| sentencia.contains(SigaConstants.SENTENCIA_INSERT)
							|| sentencia.contains(SigaConstants.SENTENCIA_UPDATE))) {

						LOGGER.error("ejecutarConsulta() -> Consulta no permitida: " + sentencia);
						error.setCode(400);
						error.setDescription("Consulta no permitida");
						error.setMessage("Consulta no permitida");
						response.setError(error);
					} else {
						List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);

						if (result != null && result.size() > 0) {
							Workbook workBook = crearExcel(result);

							// Obtenemos la ruta temporal
							GenPropertiesKey key = new GenPropertiesKey();
							key.setFichero(SigaConstants.FICHERO_SIGA);
							key.setParametro(SigaConstants.parametroRutaSalidaInformes);

							GenProperties rutaFicherosSalida = _genPropertiesMapper.selectByPrimaryKey(key);

							String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion
									+ SigaConstants.pathSeparator + SigaConstants.carpetaTmp;

							File aux = new File(rutaTmp);
							// creo directorio si no existe
							aux.mkdirs();
							String nombreFichero = SigaConstants.nombreExcelConsulta + new Date().getTime() + ".xlsx";
							excel = new File(rutaTmp, nombreFichero);
							FileOutputStream fileOut = new FileOutputStream(
									rutaTmp + SigaConstants.pathSeparator + nombreFichero);
							workBook.write(fileOut);
							fileOut.close();
							workBook.close();
							response.setFile(excel);
							response.setResultados(true);
						} else {
							response.setResultados(false);
						}
					}
				} catch (Exception e) {
					LOGGER.error("ejecutarConsulta() -> Error al ejecutar la consulta: " + e.getMessage());
					e.printStackTrace();
					error.setCode(500);
					error.setDescription(e.getCause().toString());
					error.setMessage("Error al ejecutar la consulta");
					response.setError(error);
				}

			}
		}
		LOGGER.info("ejecutarConsulta() -> Salida del servicio para ejecutar una consulta");
		return response;
	}

	@Override
	public String quitarEtiquetas(String sentencia) {
		return SIGAHelper.quitarEtiquetas(sentencia);
	}

	public boolean comprobarCamposDestinarios(String sentencia) {
		boolean camposIncorrectos = false;

		if (sentencia != null) {
			sentencia = sentencia.toUpperCase();

			// Obtenemos el SELECT de la consulta
			int indexInicio = sentencia.indexOf("<SELECT>") + 8;
			int indexFinal = sentencia.indexOf("</SELECT>");
			if (indexInicio > -1 && indexFinal > -1) {
				String select = sentencia.substring(indexInicio, indexFinal);

				if (!select.contains("IDINSTITUCION")) {
					camposIncorrectos = true;
				}
				if (!select.contains("IDPERSONA")) {
					camposIncorrectos = true;
				}
				if (!select.contains("CODIGOPOSTAL")) {
					camposIncorrectos = true;
				}
				if (!select.contains("CORREOELECTRONICO")) {
					camposIncorrectos = true;
				}
				if (!select.contains("DOMICILIO")) {
					camposIncorrectos = true;
				}
				if (!select.contains("MOVIL")) {
					camposIncorrectos = true;
				}
				if (!select.contains("FAX1")) {
					camposIncorrectos = true;
				}
				if (!select.contains("FAX2")) {
					camposIncorrectos = true;
				}
				if (!select.contains("IDPAIS")) {
					camposIncorrectos = true;
				}
				if (!select.contains("IDPROVINCIA")) {
					camposIncorrectos = true;
				}
				if (!select.contains("IDPOBLACION")) {
					camposIncorrectos = true;
				}

			} else {
				camposIncorrectos = true;
			}
		} else {
			camposIncorrectos = true;
		}

		return camposIncorrectos;
	}

	public boolean comprobarEtiquetas(String sentencia) {
		boolean etiquetasInsuficientes = false;
		sentencia = sentencia.toUpperCase();
		if (sentencia != null) {
			if (!sentencia.contains("<SELECT>") || !sentencia.contains("</SELECT>")) {
				etiquetasInsuficientes = true;
			}
			if (!sentencia.contains("<FROM>") || !sentencia.contains("</FROM>")) {
				etiquetasInsuficientes = true;
			}
		}

		return etiquetasInsuficientes;
	}

	public boolean comprobarClaves(String sentencia, String idClaseComunicacion) {
		boolean incorrecta = false;
		String sentenciaUpper = sentencia.toUpperCase();
		List<KeyItem> listaKeys = null;
		sentencia = sentencia.toUpperCase();

		if (idClaseComunicacion != null && !"".equals(idClaseComunicacion)) {
			listaKeys = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Short.parseShort(idClaseComunicacion));
			for (KeyItem key : listaKeys) {

				String etiquetaKey = SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + key.getNombre().toUpperCase()
						+ SigaConstants.REPLACECHAR_PREFIJO_SUFIJO;
				if (sentencia.indexOf(etiquetaKey) == -1) {

					incorrecta = true;
				}
			}
		}

		return incorrecta;
	}

	public boolean comprobarObjetivo(String sentencia, String idObjetivo) {
		boolean incorrecto = false;
		sentencia = sentencia.toUpperCase();
		if (Long.parseLong(idObjetivo) == SigaConstants.OBJETIVO.DESTINATARIOS.getCodigo().longValue()) {
			incorrecto = comprobarCamposDestinarios(sentencia);
		}

		return incorrecto;
	}

	public String insertarSelectDestinatarios(String sentencia) {

		int indexInicio = -1;
		int indexFinal = -1;

		if (sentencia != null) {
			indexInicio = sentencia.indexOf("<SELECT>") + 8;
			indexFinal = sentencia.indexOf("</SELECT>");
		} else {
			sentencia = "";
		}

		String select = "";

		select += " CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\", ";
		select += " CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\", ";
		select += " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ";
		select += " CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\", ";
		select += " CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\", ";
		select += " CEN_DIRECCIONES.MOVIL AS \"MOVIL\", ";
		select += " CEN_DIRECCIONES.FAX1 AS \"FAX1\", ";
		select += " CEN_DIRECCIONES.FAX2 AS \"FAX2\", ";
		select += " CEN_DIRECCIONES.IDPAIS AS \"IDPAIS\", ";
		select += " CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\", ";
		select += " CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\" ";

		if (indexInicio > -1 && indexFinal > -1) {
			// Si ya tenía un select introducido le añadimos los daots de direccion
			if (indexFinal + 9 <= sentencia.length()
					&& sentencia.substring(indexInicio, indexFinal).toUpperCase().indexOf("SELECT") > -1) {
				String selectConsulta = sentencia.substring(indexInicio, indexFinal);
				sentencia = sentencia.substring(0, indexInicio) + selectConsulta + ", " + select
						+ sentencia.substring(indexFinal, sentencia.length());
			} else {
				sentencia = sentencia.substring(0, indexInicio) + " SELECT " + select
						+ sentencia.substring(indexFinal, sentencia.length());
			}
		} else {
			sentencia = "<SELECT>" + select + "</SELECT>" + sentencia;
		}

		return sentencia;
	}

	public String insertarSelectDestinatarios() {

		String select = "<SELECT> SELECT ";

		select += " CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\", ";
		select += " CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\", ";
		select += " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ";
		select += " CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\", ";
		select += " CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\", ";
		select += " CEN_DIRECCIONES.MOVIL AS \"MOVIL\", ";
		select += " CEN_DIRECCIONES.FAX1 AS \"FAX1\", ";
		select += " CEN_DIRECCIONES.FAX2 AS \"FAX2\", ";
		select += " CEN_DIRECCIONES.IDPAIS AS \"IDPAIS\", ";
		select += " CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\", ";
		select += " CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\" </SELECT>";

		return select;
	}

	public String insertarClaves(Short idClaseComunicaciones, String sentencia) {

		int indexInicio = -1;
		int indexFinal = -1;

		if (sentencia != null) {
			indexInicio = sentencia.indexOf("<WHERE>") + 7;
			indexFinal = sentencia.indexOf("</WHERE>");
		} else {
			sentencia = "";
		}

		String sentenciaFinal = "";
		String where = "";

		List<KeyItem> listaKeys = null;

		if (idClaseComunicaciones != null) {
			listaKeys = _modKeyclasecomunicacionExtendsMapper.selectKeyClase(idClaseComunicaciones);
			for (int i = 0; i < listaKeys.size(); i++) {
				KeyItem key = listaKeys.get(i);
				if (i != 0) {
					where = where + " AND ";
				}
				String etiquetaKey = SigaConstants.REPLACECHAR_PREFIJO_SUFIJO + key.getNombre()
						+ SigaConstants.REPLACECHAR_PREFIJO_SUFIJO;
				where = where + key.getTabla().trim() + "." + key.getNombre() + " = " + etiquetaKey;
			}
		}

		if (indexInicio > -1 && indexFinal > -1) {
			if (indexFinal + 8 <= sentencia.length()
					&& sentencia.substring(indexInicio, indexFinal).toUpperCase().indexOf("WHERE") > -1) {
				String whereConsulta = sentencia.substring(indexInicio, indexFinal);
				if (!"".equalsIgnoreCase(where)) {
					sentenciaFinal = sentencia.substring(0, indexInicio) + whereConsulta + " AND " + where
							+ sentencia.substring(indexFinal, sentencia.length());
				} else {
					sentenciaFinal = sentencia.substring(0, indexInicio) + whereConsulta
							+ sentencia.substring(indexFinal, sentencia.length());
				}
			} else {
				if (!"".equalsIgnoreCase(where)) {
					sentenciaFinal = sentencia.substring(0, indexInicio) + " WHERE " + where
							+ sentencia.substring(indexFinal, sentencia.length());
				} else {
					sentenciaFinal = sentencia.substring(0, indexInicio)
							+ sentencia.substring(indexFinal, sentencia.length());
				}
			}
		} else {
			if (!"".equalsIgnoreCase(where)) {
				sentenciaFinal = sentencia + "<WHERE>" + " WHERE " + where + "</WHERE>";
			}
		}

		return sentenciaFinal;
	}

	public String obtenerSelect(String consulta) {
		String select = " ";

		int inicioSelect = consulta.indexOf("<SELECT>") + 8;
		int finSelect = consulta.indexOf("</SELECT>");
		select = consulta.substring(inicioSelect, finSelect);

		return select;
	}

	public String obtenerFrom(String consulta) {
		String from = "";

		int inicioFrom = consulta.indexOf("<FROM>") + 6;
		int finFrom = consulta.indexOf("</FROM>");
		from = consulta.substring(inicioFrom, finFrom);

		return from;
	}

	public String obtenerJoin(String consulta) {
		String join = "";

		int inicioJoin = consulta.indexOf("<JOIN>") + 6;
		int finJoin = consulta.indexOf("</JOIN>");
		if (consulta.indexOf("<JOIN>") != -1) {
			join = consulta.substring(inicioJoin, finJoin);
			join = join.replace("join", "");
		}

		return join;
	}

	public String obtenerOuterJoin(String consulta) {
		String outerJoin = "";

		int inicioJoin = consulta.indexOf("<OUTERJOIN>") + 11;
		int finJoin = consulta.indexOf("</OUTERJOIN>");
		if (consulta.indexOf("<OUTERJOIN>") != -1) {
			outerJoin = consulta.substring(inicioJoin, finJoin);
			outerJoin = outerJoin.replace("outer join", "");
		}

		return outerJoin;
	}

	public String obtenerInnerJoin(String consulta) {
		String outerJoin = "";

		int inicioJoin = consulta.indexOf("<INNERJOIN>") + 11;
		int finJoin = consulta.indexOf("</INNERJOIN>");
		if (consulta.indexOf("<INNERJOIN>") != -1) {
			outerJoin = consulta.substring(inicioJoin, finJoin);
			outerJoin = outerJoin.replace("INNER JOIN", "");
		}

		return outerJoin;
	}

	public String obtenerLeftJoin(String consulta) {
		String leftJoin = "";

		int inicioJoin = consulta.indexOf("<LEFTJOIN>") + 10;
		int finJoin = consulta.indexOf("</LEFTJOIN>");
		if (consulta.indexOf("<LEFTJOIN>") != -1) {
			leftJoin = consulta.substring(inicioJoin, finJoin);
			leftJoin = leftJoin.replace("left join", "");
		}

		return leftJoin;
	}

	public String obtenerWhere(String consulta) {
		String where = "";

		if (consulta.indexOf("<WHERE>") != -1) {
			int inicioWhere = consulta.indexOf("<WHERE>") + 8;
			int finWhere = consulta.indexOf("</WHERE>");
			where = consulta.substring(inicioWhere, finWhere);

		}

		return where;
	}

	public String obtenerOrderBy(String consulta) {
		String orderBy = "";

		if (consulta.indexOf("<ORDERBY>") != -1) {
			int inicioOrder = consulta.indexOf("<ORDERBY>") + 10;
			int finOrder = consulta.indexOf("</ORDERBY>");
			orderBy = consulta.substring(inicioOrder, finOrder);
		}

		return orderBy;
	}

	public String obtenerGroupBy(String consulta) {
		String groupBy = "";

		if (consulta.indexOf("<GROUPBY>") != -1) {
			int inicioGroupBy = consulta.indexOf("<GROUPBY>") + 10;
			int finGroupBy = consulta.indexOf("</GROUPBY>");
			groupBy = consulta.substring(inicioGroupBy, finGroupBy) + " ";
		}

		return groupBy;
	}

	public String obtenerHaving(String consulta) {
		String having = "";

		if (consulta.indexOf("<HAVING>") != -1) {
			int inicioHaving = consulta.indexOf("<HAVING>") + 8;
			int finHaving = consulta.indexOf("</HAVING>");
			having = consulta.substring(inicioHaving, finHaving) + " ";
		}

		return having;
	}

	public String obtenerUnion(String consulta) {
		String union = "";

		if (consulta.indexOf("<UNION>") != -1) {
			int inicioUnion = consulta.indexOf("<UNION>") + 7;
			int finUnion = consulta.indexOf("</UNION>");
			union = consulta.substring(inicioUnion, finUnion) + " ";
		}

		return union;
	}

	public String obtenerUnionAll(String consulta) {
		String union = "";

		if (consulta.indexOf("<UNIONALL>") != -1) {
			int inicioUnion = consulta.indexOf("<UNIONALL>") + 10;
			int finUnion = consulta.indexOf("</UNIONALL>");
			union = consulta.substring(inicioUnion, finUnion) + " ";
		}

		return union;
	}

	public Workbook crearExcel(List<Map<String, Object>> result) {

		// Creamos el libro de excel
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Query");

		// Le aplicamos estilos a las cabeceras
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		// headerFont.setItalic(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		// Recorremos el map y vamos metiendo celdas
		List<String> columnsKey = new ArrayList<String>();
		int rowNum = 1;
		int index = 0;
		if (result.size() > 0) {
			for (String value : result.get(0).keySet()) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(value);
				cell.setCellStyle(headerCellStyle);
				columnsKey.add(value);
				index++;
			}

			for (Map<String, Object> map : result) {

				Row row = sheet.createRow(rowNum++);
				int cell = 0;

				for (int i = 0; i < columnsKey.size(); i++) {
					Object campo = map.get(columnsKey.get(i).trim());
					if (campo == null || campo.toString().trim() == "") {
						row.createCell(cell).setCellValue("null");
					} else {
						row.createCell(cell).setCellValue(campo.toString());
					}
					cell++;
				}
			}

			for (int i = 0; i < index; i++) {
				sheet.autoSizeColumn(i);
			}
		}

		return workbook;

	}

	@Override
	public Map<String, String> obtenerMapaConsulta(String consulta) {
		Map<String, String> mapa = new HashMap<String, String>();

		mapa.put("selectValue", obtenerSelect(consulta));
		mapa.put("fromValue", obtenerFrom(consulta));
		mapa.put("innerJoinValue", obtenerInnerJoin(consulta));
		mapa.put("joinValue", obtenerJoin(consulta));
		mapa.put("leftJoinValue", obtenerLeftJoin(consulta));
		mapa.put("outerJoinValue", obtenerOuterJoin(consulta));
		mapa.put("whereValue", obtenerWhere(consulta));
		mapa.put("orderByValue", obtenerOrderBy(consulta));
		mapa.put("groupByValue", obtenerGroupBy(consulta));
		mapa.put("havingValue", obtenerHaving(consulta));
		mapa.put("unionValue", obtenerUnion(consulta));
		mapa.put("unionAllValue", obtenerUnionAll(consulta));
		return mapa;
	}

	@Override
	public CamposDinamicosDTO obtenerCamposConsulta(HttpServletRequest request, String idClaseComunicacion,
			String consulta) {

		LOGGER.info(
				"obtenerCamposConsulta() -> Entrada al servicio para obtener los parámetros dinámicos de la consulta");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		CamposDinamicosDTO response = new CamposDinamicosDTO();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				List<CampoDinamicoItem> listaCampos = null;
				try {

					/*
					 * List<KeyItem> listaKeys = null;
					 * 
					 * if(idClaseComunicacion != null){ listaKeys =
					 * _modKeyclasecomunicacionExtendsMapper.selectKeyClase(Short.parseShort(
					 * idClaseComunicacion)); }
					 */

					listaCampos = obtenerCamposDinamicos(usuario, false, consulta);
					response.setCamposDinamicos(listaCampos);

				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					response.setError(error);
					e.printStackTrace();
				}
			}

		}
		LOGGER.info(
				"obtenerCamposConsulta() -> Salida del servicio para obtener los parámetros dinámicos de la consulta");
		return response;
	}

	@Override
	public ArrayList<CampoDinamicoItem> obtenerCamposDinamicos(AdmUsuarios usuario, boolean comunicar, String consulta)
			throws Exception {

		ArrayList<CampoDinamicoItem> listaCamposDinamicos = new ArrayList<CampoDinamicoItem>();
		CampoDinamicoItem campoDinamico = null;

		List<String> tipoDatos = new ArrayList<String>();
		String campo = "";
		String alias = "";

		// Cargamos el vector de tipo de los tipos de datos de los criterios dinamicos
		tipoDatos.add(SigaConstants.ETIQUETATIPONUMERO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOTEXTO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOFECHA);
		tipoDatos.add(SigaConstants.ETIQUETATIPOMULTIVALOR);

		String sentencia_aux = "";
		String sentenciaOriginalAux = "";

		boolean continuar = true;
		List<String> ayuda = new ArrayList<String>();

		List<String> operadoresList = new ArrayList<String>();
		operadoresList.add("=");
		operadoresList.add("!=");
		operadoresList.add(">");
		operadoresList.add(">=");
		operadoresList.add("<");
		operadoresList.add("<=");
		operadoresList.add("IS NULL");
		operadoresList.add("LIKE");
		operadoresList.add("OPERADOR");

		String selectExperta = consulta.toUpperCase().replaceAll("\r\n", " ");
		String selectOriginal = consulta.replaceAll("\r\n", " ");

		if ((selectExperta.indexOf(SigaConstants.ETIQUETATIPONUMERO)) >= 0
				|| (selectExperta.indexOf(SigaConstants.ETIQUETATIPOTEXTO)) >= 0
				|| (selectExperta.indexOf(SigaConstants.ETIQUETATIPOFECHA) >= 0
						|| (selectExperta.indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR)) >= 0)
				|| selectExperta.indexOf(SigaConstants.ETIQUETATIPOENVIO) > 0) {
			if (selectExperta.toUpperCase().indexOf("%%IDINSTITUCION%%") >= 0) {
				selectExperta = selectExperta.toUpperCase().replaceAll("%%IDINSTITUCION%%",
						String.valueOf(usuario.getIdinstitucion()));
				selectOriginal = selectOriginal.replaceAll("%%IDINSTITUCION%%",
						String.valueOf(usuario.getIdinstitucion()));
			}
		} else {
			return null;
		}

		try {
			// Buscamos los criterios dinamicos que pueda haber en la sentencia select
			// construida
			String critCampoSalida = selectExperta;
			sentencia_aux = critCampoSalida;
			sentenciaOriginalAux = selectOriginal;
			continuar = true;

			for (int i = 0; i < tipoDatos.size(); i++) {// Para cada tipo de datos
				continuar = true;
				sentencia_aux = critCampoSalida;
				sentenciaOriginalAux = selectOriginal;
				String operadorEncontrado = "";
				while ((continuar) && (sentencia_aux.length() > 0)) {
					operadorEncontrado = "";
					// int pos_ini2 =sentencia_aux.lastIndexOf(v_tipoDatos.get(i).toString());
					int pos_ini = sentencia_aux.indexOf(tipoDatos.get(i).toString());
					if (pos_ini >= 0) {
						campoDinamico = new CampoDinamicoItem();

						String sentenciaA = sentencia_aux.substring(0, pos_ini);

						String operadores[] = sentenciaA.split("%%");
						for (int j = operadores.length - 1; j >= 0; j--) {
							String operador = operadores[j];
							if (operadoresList.contains(operador)) {
								operadorEncontrado = operador;
								break;

							}

						}

						// --int pos_fin = sentencia_aux.indexOf("AND");
						String sentenciaAyuda = sentencia_aux.substring(pos_ini);
						int pos_fin = sentenciaAyuda.indexOf("AND");
						String sentenciaAyudaOriginal = null;
						if (pos_fin == -1)
							sentenciaAyudaOriginal = sentenciaOriginalAux.substring(pos_ini);
						else
							sentenciaAyudaOriginal = sentenciaOriginalAux.substring(pos_ini, pos_ini + pos_fin);

						campo = getAliasMostrar(sentenciaA);
						campoDinamico.setCampo(campo);
						// listaCampos.add(campo);

						alias = getAliasCompleto(sentenciaA);
						campoDinamico.setAlias(alias);
						// listaAlias.add(alias);

						int posicionValue = sentenciaAyudaOriginal.toUpperCase().indexOf(" DEFECTO ");
						String valorDefecto = null;
						if (posicionValue >= 0) {
							String valueDefecto = sentenciaAyudaOriginal.substring(posicionValue);
							int inicio = valueDefecto.indexOf("\"");
							String auxiliar = valueDefecto.substring(inicio + 1);
							int fin = auxiliar.indexOf("\"");
							String retorno = null;
							if (inicio != -1 && fin != -1) {
								valorDefecto = auxiliar.substring(0, fin);
							}
						}
						// listaValorDefecto.add(valorDefecto==null?"":valorDefecto);
						campoDinamico.setValorDefecto(valorDefecto == null ? "" : valorDefecto);

						int posicionNulo = sentenciaAyudaOriginal.toUpperCase().indexOf(" NULO ");
						String valorNulo = null;
						if (posicionNulo >= 0) {
							String valueNulo = sentenciaAyudaOriginal.substring(posicionNulo);
							int inicio = valueNulo.indexOf("\"");
							String auxiliar = valueNulo.substring(inicio + 1);
							int fin = auxiliar.indexOf("\"");
							String retorno = null;
							if (inicio != -1 && fin != -1) {
								valorNulo = auxiliar.substring(0, fin);
							}
						}
						// listaValorNulo.add(valorNulo==null?Boolean.FALSE:valorNulo.toLowerCase().equals("si")?Boolean.TRUE:Boolean.FALSE);
						campoDinamico.setValorNulo(valorNulo == null ? Boolean.FALSE
								: valorNulo.toLowerCase().equals("si") ? Boolean.TRUE : Boolean.FALSE);

						if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPONUMERO)) {

							// listaOperaciones.add(operadorEncontrado);
							campoDinamico.setOperacion(operadorEncontrado);

							// listaValores.add(null);
							campoDinamico.setValores(null);

							// tipoCampo.add("N");
							campoDinamico.setTipoDato(SigaConstants.TIPONUMERO);

							// ayuda.add("-1");
							campoDinamico.setAyuda("-1");

						} else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOTEXTO)) {
							// listaOperaciones.add(operadorEncontrado);
							campoDinamico.setOperacion(operadorEncontrado);

							// listaValores.add(null);
							campoDinamico.setValores(null);

							// tipoCampo.add("A");
							campoDinamico.setTipoDato(SigaConstants.TIPOTEXTO);

							// ayuda.add("-1");
							campoDinamico.setAyuda("-1");
						} else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOFECHA)) {

							// listaOperaciones.add(operadorEncontrado);
							campoDinamico.setOperacion(operadorEncontrado);

							// listaValores.add(null);
							campoDinamico.setValores(null);

							if (operadorEncontrado.equalsIgnoreCase("IS NULL")) {
								// tipoCampo.add("X");
								campoDinamico.setTipoDato(SigaConstants.TIPOFECHANULA);
							} else {
								// tipoCampo.add("D");
								campoDinamico.setTipoDato(SigaConstants.TIPOFECHA);
							}

							// ayuda.add("-1");
							campoDinamico.setAyuda("-1");

							if (valorDefecto != null && valorDefecto.equalsIgnoreCase("sysdate")) {
								// listaValorDefecto.add(new Date().toString());
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

								Date today = Calendar.getInstance().getTime();

								String sDate = df.format(today);

								campoDinamico.setValorDefecto(sDate);
							}

						} else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOMULTIVALOR)) {

							// listaOperaciones.add(operadorEncontrado);
							campoDinamico.setOperacion(operadorEncontrado);

							// Obtenemos los resultados de la query multivalor

							String selectAyuda = obtenerSelectAyuda(sentenciaAyuda, usuario);
							// ayuda.add(selectAyuda+"%%");
							campoDinamico.setAyuda(selectAyuda + "%%");

							if (selectAyuda != null && !selectAyuda.equals("")) {
								List<Map<String, Object>> valores = _conConsultasExtendsMapper
										.ejecutarConsultaString(selectAyuda);
								/*
								 * RowsContainer rc2 = null; rc2 = new RowsContainer(); rc2.query(selectAyuda);
								 * listaValores.add(rc2.getAll()); tipoCampo.add("MV");
								 */

								campoDinamico.setValores(valores);
								campoDinamico.setTipoDato(SigaConstants.TIPOMULTIVALOR);
							}

						}

						listaCamposDinamicos.add(campoDinamico);
					} else {
						continuar = false;
					}

					sentencia_aux = sentencia_aux.substring(pos_ini + tipoDatos.get(i).toString().length());
					sentenciaOriginalAux = sentenciaOriginalAux
							.substring(pos_ini + tipoDatos.get(i).toString().length());
				}

			}

			if (!comunicar && selectExperta.indexOf(SigaConstants.ETIQUETATIPOENVIO) > 0) {

				// Si encontramos la etiqueta %%tipoenvio%% añadimos como valor dinamico el
				// combo de tipo de envios
				campoDinamico = new CampoDinamicoItem();
				campoDinamico.setOperacion(null);
				campoDinamico.setTipoDato(SigaConstants.TIPOENVIO);
				campoDinamico.setAlias(SigaConstants.NOMBRETIPOENVIO);
				campoDinamico.setCampo(SigaConstants.NOMBRETIPOENVIO);

				List<Map<String, Object>> valores = _envTipoEnvioExtendsMapper
						.selectTipoEnviosConsultas(usuario.getIdlenguaje());
				campoDinamico.setValores(valores);
				listaCamposDinamicos.add(campoDinamico);
			}

			return listaCamposDinamicos;

		} catch (Exception e) {
			throw e;
		}
	}

	protected static String getAliasMostrar(String sentencia) {
		String operador = "";
		sentencia = sentencia.toUpperCase();
		int pos_AND = sentencia.lastIndexOf(" AND ");
		int pos_OR = sentencia.lastIndexOf(" OR ");
		int pos_WHERE = sentencia.lastIndexOf("WHERE");
		int posicion = -1;
		if (pos_AND < 0 && pos_OR < 0) {
			posicion = sentencia.toUpperCase().lastIndexOf("WHERE");
			sentencia = sentencia.substring(posicion + "WHERE".length());
			if (sentencia.toUpperCase().lastIndexOf(" AS ") >= 0) {// Existe Alias
				int posAs = sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia = sentencia.substring(posAs + " AS ".length(), posEtiquetaOperador).replaceAll("\"", "");

			} else {// no hay alias
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}
				sentencia = sentencia.substring(0, posEtiquetaOperador);

				if (sentencia.indexOf(".") >= 0) {
					sentencia = sentencia.substring(sentencia.indexOf(".") + 1);
				}

			}

		} else {
			if ((pos_AND > pos_OR) && (pos_AND > pos_WHERE)) {
				operador = " AND ";
				posicion = pos_AND;
			} else if ((pos_OR > pos_AND) && (pos_OR > pos_WHERE)) {
				operador = " OR ";
				posicion = pos_OR;
			} else if ((pos_WHERE > pos_AND) && (pos_WHERE > pos_OR)) {
				operador = "WHERE";
				posicion = pos_WHERE;
			}
			sentencia = sentencia.substring(posicion + operador.length());

			if (sentencia.toUpperCase().lastIndexOf(" AS ") >= 0) {// Existe Alias
				int posAs = sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia = sentencia.substring(posAs + " AS ".length(), posEtiquetaOperador).replaceAll("\"", "");

			} else {// no hay alias
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				if (posEtiquetaOperador > -1) {
					sentencia = sentencia.substring(0, posEtiquetaOperador);
				}

				if (sentencia.indexOf(".") >= 0) {
					sentencia = sentencia.substring(sentencia.indexOf(".") + 1);
				}

			}
		}

		return sentencia;
	}

	protected static String getAliasCompleto(String sentencia) {
		String operador = "";
		int pos_AND = sentencia.lastIndexOf(" AND ");
		int pos_OR = sentencia.lastIndexOf(" OR ");
		int pos_WHERE = sentencia.lastIndexOf(" WHERE ");
		int posicion = -1;

		if (pos_AND < 0 && pos_OR < 0) {
			posicion = sentencia.toUpperCase().lastIndexOf("WHERE");
			sentencia = sentencia.substring(posicion + "WHERE".length());
			if (sentencia.toUpperCase().lastIndexOf(" AS ") >= 0) {// Existe Alias
				int posAs = sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia = sentencia.substring(posAs, posEtiquetaOperador);

			} else {// no hay alias

				/*
				 * sentencia=sentencia.substring(0,sentencia.toUpperCase().indexOf(ClsConstants.
				 * ETIQUETAOPERADOR));
				 * 
				 * if (sentencia.indexOf(".")>=0){
				 * sentencia=sentencia.substring(sentencia.indexOf(".")+1); }
				 */
				sentencia = "-1";

			}
		} else {
			if ((pos_AND > pos_OR) && (pos_AND > pos_WHERE)) {
				operador = " AND ";
				posicion = pos_AND;
			} else if ((pos_OR > pos_AND) && (pos_OR > pos_WHERE)) {
				operador = " OR ";
				posicion = pos_OR;
			} else if ((pos_WHERE > pos_AND) && (pos_WHERE > pos_OR)) {
				operador = "WHERE";
				posicion = pos_WHERE;
			}
			sentencia = sentencia.substring(posicion + operador.length());

			if (sentencia.toUpperCase().lastIndexOf(" AS ") >= 0) {// Existe Alias
				int posAs = sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador = sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if (posEtiquetaOperador == -1) {
					posEtiquetaOperador = sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia = sentencia.substring(posAs, posEtiquetaOperador);

			} else {
				sentencia = "-1";
			}
		}

		return sentencia;
	}

	protected static String obtenerSelectAyuda(String select, AdmUsuarios usuario) throws SigaExceptions {
		select = select.toUpperCase().replaceAll(SigaConstants.ETIQUETAOPERADOR, "");
		String select1 = "";
		int posCritMulti;
		String selectCritMulti = "";
		if (select.toUpperCase().indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR) >= 0) {
			select1 = select.substring(select.toUpperCase().indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR));
			posCritMulti = SigaConstants.ETIQUETATIPOMULTIVALOR.length();
			selectCritMulti = select1.substring(posCritMulti);
		} else {
			selectCritMulti = select;
		}

		if (selectCritMulti.toUpperCase().indexOf("%%") >= 0) {
			String selectCritMulti1 = "";
			selectCritMulti1 = selectCritMulti.toUpperCase();
			selectCritMulti1 = selectCritMulti1.replaceAll("%%IDIOMA%%", usuario.getIdlenguaje());
			selectCritMulti1 = selectCritMulti1.replaceAll("@IDIOMA@", usuario.getIdlenguaje());
			selectCritMulti1 = selectCritMulti1.substring(0, selectCritMulti1.toUpperCase().indexOf("%%"));

			return selectCritMulti1;
		} else {
			return selectCritMulti;
			// eSTO ES QUE YA ESTAN RESUELTOS quitamos la excepcion
			// throw new SigaExceptions("Error al obtener los valores de l");
		}

	}

	@Override
	public String procesarEjecutarConsulta(AdmUsuarios usuario, String sentencia, List<CampoDinamicoItem> listaCampos,
			boolean sustituyeInstitucion) throws ParseException {

		// Variables para crear consulta parametrizada BIND
		int iParametroBind = 0;
		Hashtable<Integer, Object> codigosBind = new Hashtable<Integer, Object>();
		Hashtable<Integer, Object> codigosLike = new Hashtable<Integer, Object>();

		String sentenciaCabecera = "";

		sentencia = sentencia.toUpperCase();

		// sustituyendo la marca '%%idinstitucion%%' por la institucion actual
		// cuando no se trate de una consulta experta de facturacion
		while (sustituyeInstitucion && sentencia.toUpperCase().indexOf("%%IDINSTITUCION%%") > -1) {
			iParametroBind++;
			sentencia = UtilidadesString.replaceFirstIgnoreCase(sentencia, "%%IDINSTITUCION%%",
					":@" + iParametroBind + "@:");
			codigosBind.put(new Integer(iParametroBind), usuario.getIdinstitucion());
		}

		// sustituyendo la marca '%%idioma%%' por el idioma del USERBEAN
		sentencia = sentencia.replaceAll("%%IDIOMA%%", usuario.getIdlenguaje());

		List<String> operadoresList = new ArrayList<String>();
		operadoresList.add("=");
		operadoresList.add("!=");
		operadoresList.add(">");
		operadoresList.add(">=");
		operadoresList.add("<");
		operadoresList.add("<=");
		operadoresList.add("IS NULL");
		operadoresList.add("LIKE");
		operadoresList.add("OPERADOR");

		String criteriosDinamicos = "";
		String sentenciaAux, sentenciaAux1, sentenciaAux2;
		String operador = "";
		String etiqueta = "";
		int posEtiquetaOperador = 0;
		int posAlias = 0;
		boolean continuar = true;
		int pos_ini = 0;
		int pos_iniEtiqueta = 0;
		sentencia = sentencia.replaceAll("\r\n", " ");
		int j = 0;

		// Por cada tipo de filtro
		String alias = "";
		List<String> tipoDatos = new ArrayList<String>();

		tipoDatos.add(SigaConstants.ETIQUETATIPONUMERO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOTEXTO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOFECHA);
		tipoDatos.add(SigaConstants.ETIQUETATIPOMULTIVALOR);
		tipoDatos.add(SigaConstants.ETIQUETATIPOENVIO);

		for (int i = 0; i < tipoDatos.size(); i++) {
			if (listaCampos != null && j < listaCampos.size() && listaCampos.get(j) != null) {
				continuar = true;
				pos_ini = 0;

				while (continuar && pos_ini <= sentencia.length()) {
					if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPONUMERO))
						etiqueta = SigaConstants.ETIQUETATIPONUMERO;
					else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOTEXTO))
						etiqueta = SigaConstants.ETIQUETATIPOTEXTO;
					else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOFECHA))
						etiqueta = SigaConstants.ETIQUETATIPOFECHA;
					else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOENVIO))
						etiqueta = SigaConstants.ETIQUETATIPOENVIO;
					else
						etiqueta = SigaConstants.ETIQUETATIPOMULTIVALOR;

					if (etiqueta.equals(SigaConstants.ETIQUETATIPOMULTIVALOR)) {
						int iMV = sentencia.indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR);
						if (iMV > -1) {
							etiqueta = sentencia.substring(iMV,
									2 + sentencia.indexOf("%%", iMV + SigaConstants.ETIQUETATIPOMULTIVALOR.length()));
						}
					} else if (j < listaCampos.size() && listaCampos.get(j) != null
							&& listaCampos.get(j).getAyuda() != null && !listaCampos.get(j).getAyuda().equals("-1")) {
						// cuando existe select de ayuda porque estamos con la etiqueta multivalor
						// etiqueta += cDinamicos[j].getHp().replaceAll
						// (ClsConstants.CONSTANTESUSTITUIRCOMILLAS,"\"");
					}
					pos_iniEtiqueta = sentencia.indexOf(etiqueta);
					sentenciaAux = sentencia.substring(0, pos_iniEtiqueta + etiqueta.length());

					if (pos_iniEtiqueta >= 0) {
						if (etiqueta.equals(SigaConstants.ETIQUETATIPOENVIO)) {
							// Si es de tipoEnvio reemplazamos el valor
							sentencia = sentencia.replace(SigaConstants.ETIQUETATIPOENVIO,
									listaCampos.get(j).getValor());
							j++;
						} else {
							operador = listaCampos.get(j).getOperacion();

							// controlando que el operador es "esta vacio"

							if (!operador.equals(SigaConstants.IS_NULL) && !listaCampos.get(j).getValor().equals("")) {
								if (listaCampos.get(j).getTipoDato().equals(SigaConstants.TIPOFECHA)) {
									iParametroBind++;
									criteriosDinamicos = "TO_DATE (:@" + iParametroBind + "@:"
											+ ", 'YYYY/MM/DD HH24:MI:SS')";

									String fecha = listaCampos.get(j).getValor();
									// This could be MM/dd/yyyy, you original value is ambiguous
									SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
									Date dateValue = input.parse(fecha);

									SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
									String nuevoValor = output.format(dateValue);
									// listaCampos.get(j).setValor("'" + nuevoValor + "'");

									// String aux = listaCampos.get(j).getValor();
									if(operador.equals("LIKE")) {
										codigosBind.put(new Integer(iParametroBind), "" + nuevoValor + "");
									}else {
										codigosBind.put(new Integer(iParametroBind), "'" + nuevoValor + "'");
									}
								} else {
									iParametroBind++;
									criteriosDinamicos = ":@" + iParametroBind + "@:";
									
									if(operador.equals("LIKE")) {
										codigosBind.put(new Integer(iParametroBind),
												"" + listaCampos.get(j).getValor() + "");
									}else {
										codigosBind.put(new Integer(iParametroBind),
												"'" + listaCampos.get(j).getValor() + "'");
									}
									
									if (operador.equals(SigaConstants.LIKE.toUpperCase())) {
										codigosLike.put(new Integer(iParametroBind), listaCampos.get(j).getValor());
									}
								}
							} else {
								operador = "IS NULL";
								criteriosDinamicos = "";
							}

							sentenciaAux = sentenciaAux.substring(0, pos_iniEtiqueta) + criteriosDinamicos
									+ sentenciaAux.substring(pos_iniEtiqueta + etiqueta.length());
							String operadores[] = sentenciaAux.split("%%");
							String operadorEncontrado = null;
							for (int jta = operadores.length - 1; jta >= 0; jta--) {
								String operadorCast = operadores[jta];
								if (operadoresList.contains(operadorCast)) {
									operadorEncontrado = "%%" + operadorCast + "%%";
									break;

								}

							}
							if (operador.equalsIgnoreCase("IS NULL")) {
								posEtiquetaOperador = sentenciaAux.toUpperCase().lastIndexOf(operadorEncontrado);
								sentenciaAux1 = sentenciaAux
										.substring(posEtiquetaOperador,
												posEtiquetaOperador + operadorEncontrado.length())
										.replaceAll(operadorEncontrado, " " + operador + " ");

							} else {
								posEtiquetaOperador = sentenciaAux.toUpperCase().lastIndexOf(operadorEncontrado);
								sentenciaAux1 = sentenciaAux.substring(posEtiquetaOperador)
										.replaceAll(operadorEncontrado, " " + operador + " ");

							}

							sentenciaAux = sentenciaAux.substring(0, posEtiquetaOperador) + sentenciaAux1;
							if (!listaCampos.get(j).getAlias().equals("-1")) {
								alias = listaCampos.get(j).getAlias();
								posAlias = sentenciaAux.lastIndexOf(alias);
								sentenciaAux2 = sentenciaAux.substring(posAlias + alias.length());
								sentenciaAux = sentenciaAux.substring(0, posAlias) + sentenciaAux2;
							}
							String sentenciaAuxFin = sentencia.substring(pos_iniEtiqueta + etiqueta.length());
							int indiceAnd = sentenciaAuxFin.indexOf("AND");
							int indexDefecto = sentenciaAuxFin.toUpperCase().indexOf("DEFECTO");
							int indexNulo = sentenciaAuxFin.toUpperCase().indexOf("NULO");
							if (indiceAnd > -1) {
								if (indexDefecto > -1 && indexDefecto < indiceAnd)
									sentenciaAuxFin = sentenciaAuxFin.substring(0, indexDefecto) + " "
											+ sentenciaAuxFin.substring(indiceAnd);
								else if (indexNulo > -1 && indexNulo < indiceAnd)
									sentenciaAuxFin = sentenciaAuxFin.substring(0, indexNulo) + " "
											+ sentenciaAuxFin.substring(indiceAnd);
								// else
								// sentenciaAuxFin= " "+sentenciaAuxFin.substring(indiceAnd);
							} else {
								if (indexDefecto > -1)
									sentenciaAuxFin = sentenciaAuxFin.substring(0, indexDefecto);
								else if (indexNulo > -1)
									sentenciaAuxFin = sentenciaAuxFin.substring(0, indexNulo);

							}

							// La linea siguiente se hace por si hubiera alguna operacino oracle al texto
							// para eliminar el ultimo parentesis
							if (operador.equalsIgnoreCase("IS NULL")) {
								int indiceAND = sentenciaAuxFin.indexOf("AND");
								int indiceParentesis = -1;
								if (indiceAND > -1) {
									indiceParentesis = sentenciaAuxFin.substring(0, indiceAND).indexOf(")");

								} else {
									indiceParentesis = sentenciaAuxFin.indexOf(")");

								}

								if (indiceParentesis > -1) {
									if (sentenciaAux.indexOf("(") == -1)
										sentenciaAuxFin = sentenciaAuxFin.replaceFirst("\\)", "");
								}
							}

							sentencia = sentenciaAux + sentenciaAuxFin;
							pos_ini = pos_iniEtiqueta + sentenciaAux1.length();

							j++;
						}
					} else {
						continuar = false;
					}
				} // while
			} else {
				break;
			}

		} // for

		// Se ordena las bind variables porque las he dejado desordenadas
		// busco las ocurrencias de :@x para cambiarlas por :y ordenado
		String sentenciaAux3 = sentencia;
		int indice = sentenciaAux3.indexOf(":@", 0);
		int contadorOrdenados = 0;
		Hashtable codigosOrdenados = new Hashtable();
		while (indice != -1) {
			String numero = sentenciaAux3.substring(indice + 2, sentenciaAux3.indexOf("@:", indice));
			if (codigosLike.containsKey(new Integer(numero))) {
				sentencia = sentencia.replaceFirst(":@" + numero + "@:",
						"'%" + (String) codigosBind.get(new Integer(numero)) + "%'");
			} else {
				contadorOrdenados++;
				codigosOrdenados.put(new Integer(contadorOrdenados),
						String.valueOf(codigosBind.get(new Integer(numero))));
				sentencia = sentencia.replaceFirst(":@" + numero + "@:",
						String.valueOf(codigosBind.get(new Integer(numero))));
			}
			indice = sentenciaAux3.indexOf(":@", indice + 2);
		}

		sentencia = UtilidadesString.replaceAllIgnoreCase(sentencia, "@FECHA@", "SYSDATE");
		sentencia = UtilidadesString.replaceAllIgnoreCase(sentencia, "@IDIOMA@", usuario.getIdlenguaje());

		// Se intentan sustituir los parametros de las funciones de cen_colegiado
		sentencia = sustituirParametrosColegiado(sentencia);

		return sentencia;
	}

	public String sustituirParametrosColegiado(String select) {
		select = select.replaceAll("@FECHA@", "SYSDATE");
		if (select.indexOf(SigaConstants.NOMBRETABLA_CEN_CLIENTE) != -1) {
			// CONTIENE LA TABLA CEN_CLIENTE.
			select = select.replaceAll("@IDPERSONA@",
					SigaConstants.NOMBRETABLA_CEN_CLIENTE + "." + SigaConstants.C_IDPERSONA);
			select = select.replaceAll("@IDINSTITUCION@",
					SigaConstants.NOMBRETABLA_CEN_CLIENTE + "." + SigaConstants.C_IDINSTITUCION);
		} else if (select.indexOf(SigaConstants.NOMBRETABLA_CEN_COLEGIADO) != -1) {
			// CONTIENE LA TABLA CEN_COLEGIADO.
			select = select.replaceAll("@IDPERSONA@",
					SigaConstants.NOMBRETABLA_CEN_COLEGIADO + "." + SigaConstants.C_IDPERSONA);
			select = select.replaceAll("@IDINSTITUCION@",
					SigaConstants.NOMBRETABLA_CEN_COLEGIADO + "." + SigaConstants.C_IDINSTITUCION);
		}

		return select;
	}

	public List<Map<String, Object>> ejecutarConsultaConClaves(String sentencia) throws Exception {

		List<Map<String, Object>> result = null;
		Date inicial = new Date();
		try {

//			sentencia = quitarEtiquetas(sentencia.toUpperCase());
//			sentencia = quitaPuntosAlias(sentencia);

			boolean contienePuntosAlias = sentencia.indexOf(CARACTER_REEMPLAZO_PUNTOS) > -1;

			if (sentencia != null && (sentencia.contains(SigaConstants.SENTENCIA_ALTER)
					|| sentencia.contains(SigaConstants.SENTENCIA_CREATE)
					|| sentencia.contains(SigaConstants.SENTENCIA_DELETE)
					|| sentencia.contains(SigaConstants.SENTENCIA_DROP)
					|| sentencia.contains(SigaConstants.SENTENCIA_INSERT)
					|| sentencia.contains(SigaConstants.SENTENCIA_UPDATE))) {

				LOGGER.error("ejecutarConsultaConClaves() -> Consulta no permitida: " + sentencia);
			} else {
				result = _conConsultasExtendsMapper.ejecutarConsultaString(sentencia);
			}

			if (contienePuntosAlias && result != null && result.size() > 0) {
				List<Map<String, Object>> resultCopia = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> mapaResult : result) {
					Map<String, Object> mapaResultCopia = new LinkedHashMap<String, Object>();
					for (String key : mapaResult.keySet()) {
						Object obj = mapaResult.get(key);
						mapaResultCopia.put(key.replaceAll(CARACTER_REEMPLAZO_PUNTOS, "."), obj);
					}
					resultCopia.add(mapaResultCopia);
				}

				return resultCopia;
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar la consulta", e);
			if ((e instanceof DataAccessException) && e.getCause() != null) {
				throw new BusinessSQLException(e.getMessage(), e);
			} else {
				throw e;
			}
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> ejecutarConsultaConClavesLog(String sentencia, AdmUsuarios usuario,
			ModelosComunicacionItem modelosComunicacionItem, ConsultaItem consulta)
			throws ParseException, SigaExceptions {
		List<Map<String, Object>> resultDatos = null;
		String sentenciaCompleta = null;
		BigDecimal idEjecucion = null;
		
		Date inicialDate = new Date();
		try {

			sentenciaCompleta = obtenerSentencia(sentencia);
			
			idEjecucion = insertarLogEjecucion(inicialDate, Short.valueOf(usuario.getIdinstitucion()),
					Integer.valueOf(usuario.getIdusuario()),
					Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
					Long.valueOf(consulta.getIdConsulta()), Short.valueOf(consulta.getIdInstitucion()), "",
					sentenciaCompleta);
		
			resultDatos = ejecutarConsultaConClaves(sentenciaCompleta);

			updateLogEjecucion(inicialDate, Integer.valueOf(usuario.getIdusuario()), idEjecucion, "");

		} catch (BusinessSQLException e) {
			LOGGER.error(e);
			updateLogEjecucion(inicialDate, Integer.valueOf(usuario.getIdusuario()), idEjecucion, e.getCause().getCause().toString());

//			insertarLogEjecucion(inicialDate, Short.valueOf(usuario.getIdinstitucion()),
//					Integer.valueOf(usuario.getIdusuario()),
//					Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
//					Long.valueOf(consulta.getIdConsulta()), Short.valueOf(consulta.getIdInstitucion()), e.getMessage(),
//					sentenciaCompleta);
			throw new BusinessException(
					"Error al ejecutar la consulta " + consulta.getDescripcion() + " " + e.getMessage(), e.getCause());
		} catch (Exception e) {
			LOGGER.warn("Error al ejejcutar la consulta" + e);
			updateLogEjecucion(inicialDate, Integer.valueOf(usuario.getIdusuario()), idEjecucion, e.getMessage());

//			insertarLogEjecucion(inicialDate, Short.valueOf(usuario.getIdinstitucion()),
//					Integer.valueOf(usuario.getIdusuario()),
//					Long.valueOf(modelosComunicacionItem.getIdModeloComunicacion()),
//					Long.valueOf(consulta.getIdConsulta()), Short.valueOf(consulta.getIdInstitucion()), e.getMessage(),
//					sentenciaCompleta);
			throw new BusinessException("Error al ejecutar la consulta " + consulta.getDescripcion(), e);
		}

		return resultDatos;
	}

	private String obtenerSentencia(String sentencia) {

		String sentenciaCompleta = null;

		sentencia = quitarEtiquetas(sentencia.toUpperCase());
		sentenciaCompleta = quitaPuntosAlias(sentencia);

		return sentenciaCompleta;
	}

	public static void main(String[] args) {
		String query = "select nombbre as \"nombre\", codigo	as \"C.P.\" as cod, 'FECHA\"' || SYSDATE     AS  \"FECHA..HOY\","
				+ "'otro campo'\nas \"otro.y\", '= \" as \"t..al\", \"pepe\" as juan from tal where pascual";
		ConsultasServiceImpl consultasServiceImpl = new ConsultasServiceImpl();

		consultasServiceImpl.quitaPuntosAlias(query.toUpperCase());
		// query = "select nombbre as \"nombre\", codigo as \"CP\" from tal where
		// pascual";
		// consultasServiceImpl.quitaPuntosAlias(query.toUpperCase());
	}

	private String quitaPuntosAlias(String query) {

		String patternStr = "\\s+AS\\s+\"";
		Pattern pattern = Pattern.compile(patternStr);
		String query1 = "";
		String query2 = query;
		LOGGER.debug(query);
		int toIndex = 0;
		if (query2 != null) {
			Matcher matcher = pattern.matcher(query2);
			while (matcher.find()) {
				toIndex = query2.indexOf("\"", matcher.end() + 1);
				String alias = query2.substring(matcher.end(), toIndex);
				alias = alias.replaceAll("\\.", CARACTER_REEMPLAZO_PUNTOS);
				query1 += query2.substring(0, matcher.end()) + alias;
				query2 = query2.substring(toIndex);
				matcher = pattern.matcher(query2);
			}
			query = query1 + query2;
		}
		LOGGER.debug(query);
		return query;

	}

	public BigDecimal insertarLogEjecucion(Date inicial, Short idInstitucion, Integer idUsuario, Long idModelo,
			Long idConsulta, Short idInstitucionConsulta, String mensaje, String sentencia) {

		BigDecimal idEjecucion = null;

		// tiempo final
		Date finalDate = new Date();
		// Tiempo de ejecución de la consulta en ms
		Long diff = finalDate.getTime() - inicial.getTime();
		// Fecha de ejecución
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.format(inicial);

		ConEjecucion conEjecucionInsert = new ConEjecucion();
		// Setteo de valores
		conEjecucionInsert.setIdinstitucion(idInstitucion);
		conEjecucionInsert.setIdusuario(idUsuario);
		conEjecucionInsert.setIdmodelo(idModelo);
		conEjecucionInsert.setIdconsulta(idConsulta);
		conEjecucionInsert.setFechaejecucion(inicial);
		conEjecucionInsert.setIdinstitucionConsulta(idInstitucionConsulta);
		conEjecucionInsert.setSentencia(sentencia);
		_conEjecucionMapper.insert(conEjecucionInsert);

		idEjecucion = conEjecucionInsert.getIdejecucion();
		return idEjecucion;
	}

	public void updateLogEjecucion(Date inicial, Integer idUsuario, BigDecimal idEjecucion, String mensaje) {

		// tiempo final
		Date finalDate = new Date();
		// Tiempo de ejecución de la consulta en ms
		Long diff = finalDate.getTime() - inicial.getTime();
		// Fecha de ejecución
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.format(inicial);

		
		ConEjecucionExample conEjecucionExample = new ConEjecucionExample();
		conEjecucionExample.createCriteria()
		.andIdejecucionEqualTo(idEjecucion);
		
		
		List<ConEjecucion> conEjecuciones = _conEjecucionMapper.selectByExample(conEjecucionExample);
		
		if (null != conEjecuciones && conEjecuciones.size() > 0) {
			
			ConEjecucion conEjecucion = conEjecuciones.get(0);
			
			// Setteo de valores
			conEjecucion.setIdusuario(idUsuario);
			conEjecucion.setFechaejecucion(inicial);
			conEjecucion.setTiempoejecucion(Long.valueOf(diff));
			conEjecucion.setMensaje(mensaje);
			
			_conEjecucionMapper.updateByPrimaryKey(conEjecucion);

		}
		
	}

}
