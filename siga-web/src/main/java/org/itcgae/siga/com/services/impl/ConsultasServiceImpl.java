package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultaListadoModelosDTO;
import org.itcgae.siga.DTOs.com.ConsultaListadoPlantillasDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaExample;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantilladocConsultaMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConListadoModelosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConListadoPlantillasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConModulosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConObjetivoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ConsultasServiceImpl implements IConsultasService{

	private Logger LOGGER = Logger.getLogger(ConsultasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
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
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
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

				
				comboItems = _conClaseComunicacionExtendsMapper.selectTipoClaseComunicacion();
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}	
		
		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo claseComunicacion");
		
		return comboDTO;
	}
	
	@Override
	public ComboDTO claseComunicacionByModulo(String idModulo, HttpServletRequest request) {
		LOGGER.info("claseComunicacion() -> Entrada al servicio para obtener combo claseComunicacion filtrado por módulo");
		
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
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
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

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					consultasList = _conConsultasExtendsMapper.selectConsultasSearch(usuario.getIdinstitucion(),
							usuario.getIdlenguaje(), filtros);
					if (consultasList.size() > 0) {
						consultasDTO.setConsultaItem(consultasList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					consultasDTO.setError(error);
				}
			}
		}
		LOGGER.info("consultasSearch() -> Salida del servicio de búsqueda de consultas");
		return consultasDTO;
	}

	@Override
	public Error duplicarConsulta(HttpServletRequest request, ConsultaItem[] consultas) {
		LOGGER.info("duplicarConsulta() -> Entrada al servicio de duplicar consultas");

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
					
					for (int i = 0; i < consultas.length; i++) {
						ConConsultaKey key = new ConConsultaKey();
						key.setIdconsulta(Long.valueOf(consultas[i].getIdConsulta()));
						key.setIdinstitucion(Short.valueOf(consultas[i].getIdInstitucion()));
						ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
						NewIdDTO id = _conConsultasExtendsMapper.selectMaxIDConsulta();
						consulta.setIdconsulta(Long.valueOf(id.getNewId()));
						String descripcion = "Copia " + i+1 +"_" + consulta.getDescripcion();
						consulta.setIdinstitucion(idInstitucion);
						if(idInstitucion == 2000){
							consulta.setGeneral("S");
						}else{
							consulta.setGeneral("N");
						}
						consulta.setDescripcion(descripcion);
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						_conConsultaMapper.insert(consulta);
					}
					respuesta.setCode(200);
					respuesta.setDescription("Consultas duplicadas");
					 
				}catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al duplicar las consultas");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("duplicarConsulta() -> Salida del servicio de duplicar consultas");
		return respuesta;
	}

	@Override
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
						if(consulta.getIdobjetivo() != null){
							ModPlantilladocConsultaExample plantillaDocExample = new ModPlantilladocConsultaExample();
							plantillaDocExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdconsultaEqualTo(consulta.getIdconsulta());
							List<ModPlantilladocConsulta> plantillaConsulta = _modPlantilladocConsultaMapper.selectByExample(plantillaDocExample);
							if(plantillaConsulta != null && plantillaConsulta.size() > 0){
								consultaAsociada = true;
							}
							ModPlantillaenvioConsultaExample envioConsultaExample = new ModPlantillaenvioConsultaExample();
							envioConsultaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdconsultaEqualTo(consulta.getIdconsulta());
							List<ModPlantillaenvioConsulta> plantillaEnvio = _modPlantillaenvioConsultaMapper.selectByExample(envioConsultaExample);
							if(plantillaEnvio != null && plantillaEnvio.size() > 0){
								consultaAsociada = true;
							}
						}
						boolean general = false;
						
						if(consulta.getGeneral().equals("S") || consulta.getGeneral().equals("1")){
							general = true;
						}
						if(general){
							//las consultas genericas solo las puede borrar el colegio general
							if(idInstitucion == 2000){
								if(!consultaAsociada){
									_conConsultaMapper.deleteByPrimaryKey(consultaKey);
								}else{
									consulta.setFechabaja(new Date());
									consulta.setFechamodificacion(new Date());
									consulta.setUsumodificacion(usuario.getIdusuario());
									_conConsultaMapper.updateByPrimaryKey(consulta);
								}
							}else{
								noBorrada = true;
							}
						}else if(!general && consulta.getIdinstitucion().equals(idInstitucion)){
							if(!consultaAsociada){
								_conConsultaMapper.deleteByPrimaryKey(consultaKey);
							}else{
								consulta.setFechabaja(new Date());
								consulta.setFechamodificacion(new Date());
								consulta.setUsumodificacion(usuario.getIdusuario());
								_conConsultaMapper.updateByPrimaryKey(consulta);
							}
						}else{
							noBorrada = true;
						}
					}
					respuesta.setCode(200);
					//Si ha habido alguna consulta no borrada se le indica mediante un mensaje al front para indicarselo al usuario
					if(noBorrada){
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
				try{
					boolean camposIncorrectos = false;
					if(consultaDTO.getIdConsulta() == null){
						ConConsulta consulta = new ConConsulta();
						NewIdDTO maxId = _conConsultasExtendsMapper.selectMaxIDConsulta();
						consulta.setIdconsulta(Long.valueOf(maxId.getNewId()));
						consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setIdinstitucion(idInstitucion);
						consulta.setObservaciones(consultaDTO.getDescripcion());
						consulta.setDescripcion(consultaDTO.getNombre());
						consulta.setIdobjetivo(Long.parseLong(consultaDTO.getIdObjetivo()));
						consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						consulta.setGeneral(consultaDTO.getGenerica());
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						switch(consultaDTO.getIdObjetivo().toString()){
							case "1":
								consulta.setTipoconsulta("E");
								consulta.setSentencia(insertarSelectDestinatarios()+ "<FROM></FROM>");
								break;
							case "2":
								consulta.setTipoconsulta("M");
								consulta.setSentencia("<SELECT></SELECT><FROM></FROM>");
								break;
							case "3":
								consulta.setTipoconsulta("W");
								consulta.setSentencia("<SELECT></SELECT><FROM></FROM>");
								break;
							case "4":
								consulta.setTipoconsulta("C");
								consulta.setSentencia("<SELECT></SELECT><FROM></FROM>");
								break;
						}
						_conConsultaMapper.insert(consulta);
						respuesta.setMessage(consulta.getIdconsulta().toString());
						respuesta.setDescription(consulta.getSentencia());
						respuesta.setInfoURL(consulta.getIdinstitucion().toString());
						respuesta.setCode(200);
					}else{
						ConConsultaKey key = new ConConsultaKey();
						key.setIdconsulta(Long.parseLong(consultaDTO.getIdConsulta()));
						key.setIdinstitucion(Short.parseShort(consultaDTO.getIdInstitucion()));
						ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
						consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setDescripcion(consultaDTO.getNombre());
						consulta.setObservaciones(consultaDTO.getDescripcion());
						consulta.setGeneral(consultaDTO.getGenerica());
						consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						consulta.setIdobjetivo(Long.valueOf(consultaDTO.getIdObjetivo()));
						switch(consultaDTO.getIdObjetivo()){
						case "1":
							//Destinarios
							consulta.setTipoconsulta("E");
							camposIncorrectos = comprobarCamposDestinarios(consulta.getSentencia());
							//insertarSelectDestinatarios(consulta.getSentencia());
							break;
						case "2":
							//Multidocumento	
							consulta.setTipoconsulta("M");
							break;
						case "3":
							//condicionales
							consulta.setTipoconsulta("W");
							break;
						case "4":
							consulta.setTipoconsulta("C");
							break;
						default:
							consulta.setTipoconsulta("C");
							break;
						}
						
						if(consultaDTO.getIdObjetivo().equals("E")){
							if(!camposIncorrectos){
								respuesta.setCode(400);
								respuesta.setMessage("La estructura de la consulta no es correcta");
							}else{
								_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
								respuesta.setCode(200);
								respuesta.setMessage(consulta.getIdconsulta().toString());
								respuesta.setInfoURL(consulta.getIdinstitucion().toString());
							}
						}else{
							_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
							respuesta.setCode(200);
							respuesta.setMessage(consulta.getIdconsulta().toString());
							respuesta.setInfoURL(consulta.getIdinstitucion().toString());
						}
						
					}
					
				}catch (Exception e) {
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
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(consulta.getIdInstitucion()));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					modeloList = _conListadoModelosExtendsMapper.selectListadoModelos(usuario.getIdinstitucion(),
							 consulta);
					if (modeloList.size() > 0) {
						conListadoModelosDTO.setListadoModelos(modeloList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					conListadoModelosDTO.setError(error);
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
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(consulta.getIdInstitucion()));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					plantillasList = _conListadoPlantillasExtendsMapper.selectListadoPlantillas(usuario.getIdinstitucion(),
							usuario.getIdlenguaje(), consulta);
					if (plantillasList.size() > 0) {
						conListadoPlantillasDTO.setListadoPlantillas(plantillasList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					conListadoPlantillasDTO.setError(error);
				}
			}
		}
		LOGGER.info("obtenerPlantillasEnvio() -> Salida al servicio de obtener plantillas que contienen la consulta");
		
		return conListadoPlantillasDTO;
	}

	@Override
	public Error guardarConsulta(HttpServletRequest request, ConsultaItem consultaDTO) {
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
				try{
					boolean camposIncorrectos = false;
					
					ConConsultaKey key = new ConConsultaKey();
					key.setIdconsulta(Long.parseLong(consultaDTO.getIdConsulta()));
					key.setIdinstitucion(idInstitucion);
					ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
					camposIncorrectos = comprobarEtiquetas(consultaDTO.getSentencia());
					
					if(camposIncorrectos){
						respuesta.setCode(400);
						respuesta.setMessage("Campos incorrectos");
					}else{
						consulta.setSentencia(consultaDTO.getSentencia());
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						_conConsultaMapper.updateByPrimaryKeyWithBLOBs(consulta);
						respuesta.setCode(200);
						respuesta.setMessage("Consulta guardada");
					}
				}catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al guardar consulta");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		LOGGER.info("guardarDatosGenerales() -> Salida del servicio para guardar tarjeta general");
		return respuesta;
	}

	@Override
	public File ejecutarConsulta(HttpServletRequest request, String consulta) {
		
		LOGGER.info("ejecutarConsulta() -> Entrada al servicio para ejecutar una consulta");
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		File excel = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {
				
				try{
					Map<String,String> mapa = new HashMap<String,String>();
					mapa = obtenerMapaConsulta(consulta);
					List<Map<String,Object>> result = _conConsultasExtendsMapper.ejecutarConsulta(mapa);
					if(result != null){
						Workbook workBook = crearExcel(result);
						File aux = new File(SigaConstants.rutaExcelConsultaTemp);
						// creo directorio si no existe
						aux.mkdirs();
						String nombreFichero = SigaConstants.nombreExcelConsulta + new Date().getTime()+".xlsx";
						excel = new File(SigaConstants.rutaExcelConsultaTemp, nombreFichero);
						FileOutputStream fileOut = new FileOutputStream(SigaConstants.rutaExcelConsultaTemp + nombreFichero);
						workBook.write(fileOut);
				        fileOut.close();
				        workBook.close();
					}
				}catch (Exception e) {
					LOGGER.error("ejecutarConsulta() -> Error al ejecutar la consulta: " + e.getMessage());
					e.printStackTrace();
				}
		
			}
		}
		LOGGER.info("ejecutarConsulta() -> Salida del servicio para ejecutar una consulta");
		return excel;
	}
	
	public boolean comprobarCamposDestinarios (String sentencia){
		boolean camposIncorrectos = false;
		
		if(!sentencia.contains("CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.MOVIL AS \"MOVIL\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.FAX1 AS \"FAX1\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.FAX2 AS \"FAX2\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\"")){
			camposIncorrectos = true;
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\"")){
			camposIncorrectos = true;
		}
		
		return camposIncorrectos;
	}

	public boolean comprobarEtiquetas(String sentencia){
		boolean etiquetasInsuficientes = false;
//		Pattern regexSelect = Pattern.compile("\\bSELECT\\b");
//		Matcher match = regexSelect.matcher(sentencia);
//		
//		if(!match.find()){
//			etiquetasInsuficientes = true;
//		}
		
		if(!sentencia.contains("<SELECT>") || !sentencia.contains("</SELECT>")){
			etiquetasInsuficientes = true;
		}
		if(!sentencia.contains("<FROM>") || !sentencia.contains("</FROM>")){
			etiquetasInsuficientes = true;
		}
		return etiquetasInsuficientes;
	}
	
	public String insertarSelectDestinatarios (String sentencia){
		
		int indexInicio = sentencia.indexOf("<SELECT>")+8;
		int indexFinal = sentencia.indexOf("</SELECT>");
		String select = sentencia.substring(indexInicio, indexFinal);

		
		if(!sentencia.contains("CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\"")){
			select+= " CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\", ";
		}
		if(!sentencia.contains("CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\"")){
			select+= " CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\"")){
			select+= " CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\"")){
			select+= " CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.MOVIL AS \"MOVIL\"")){
			select+= " CEN_DIRECCIONES.MOVIL AS \"MOVIL\", ";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\", ";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.FAX1 AS \"FAX1\", ")){
			select+= " CEN_DIRECCIONES.FAX1 AS \"FAX1\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.FAX2 AS \"FAX2\"")){
			select+= " CEN_DIRECCIONES.FAX2 AS \"FAX2\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\"")){
			select+= " CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\"")){
			select+= " CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\", ";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\"")){
			select+= " CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\", ";
		}
		StringBuffer sentenciaFinal = new StringBuffer(sentencia);
		sentenciaFinal.insert(indexInicio, select);
		
		return sentenciaFinal.toString();
	}
	
	public String insertarSelectDestinatarios (){

		return "<SELECT>CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\""
				+ "CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\""
				+ "CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\""
				+ "CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\""
				+ "CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\""
				+ "CEN_DIRECCIONES.MOVIL AS \"MOVIL\""
				+ "CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\""
				+ "CEN_DIRECCIONES.FAX1 AS \"FAX1\""
				+ "CEN_DIRECCIONES.FAX2 AS \"FAX2\""
				+ "CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\""
				+ "CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\""
				+ "CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\""
				+ "</SELECT>";
	}
	
	public String obtenerSelect(String consulta){
		String select = " ";
		
		int inicioSelect = consulta.indexOf("<SELECT>")+8;
		int finSelect = consulta.indexOf("</SELECT>");
		select = consulta.substring(inicioSelect, finSelect);
		
		
		return select;
	}
	
	public String obtenerFrom(String consulta){
		String from = "";
		
		int inicioFrom = consulta.indexOf("<FROM>")+6;
		int finFrom = consulta.indexOf("</FROM>");
		from = consulta.substring(inicioFrom, finFrom);
		
		return from;
	}
	
	public String obtenerJoin(String consulta){
		String join = "";
		
		int inicioJoin = consulta.indexOf("<JOIN>")+6;
		int finJoin = consulta.indexOf("</JOIN>");
		if(consulta.indexOf("<JOIN>") != -1){
			join = consulta.substring(inicioJoin, finJoin);
			join = join.replace("join", "");
		}

		return join;
	}
	
	public String obtenerOuterJoin(String consulta){
		String outerJoin = "";
		
		int inicioJoin = consulta.indexOf("<OUTERJOIN>")+11;
		int finJoin = consulta.indexOf("</OUTERJOIN>");
		if(consulta.indexOf("<OUTERJOIN>") != -1){
			outerJoin = consulta.substring(inicioJoin, finJoin);
			outerJoin = outerJoin.replace("outer join", "");
		}

		return outerJoin;
	}
	
	public String obtenerInnerJoin(String consulta){
		String outerJoin = "";
		
		int inicioJoin = consulta.indexOf("<INNERJOIN>")+11;
		int finJoin = consulta.indexOf("</INNERJOIN>");
		if(consulta.indexOf("<INNERJOIN>") != -1){
			outerJoin = consulta.substring(inicioJoin, finJoin);
			outerJoin = outerJoin.replace("INNER JOIN", "");
		}

		return outerJoin;
	}
	
	public String obtenerLeftJoin(String consulta){
		String leftJoin = "";
		
		int inicioJoin = consulta.indexOf("<LEFTJOIN>")+10;
		int finJoin = consulta.indexOf("</LEFTJOIN>");
		if(consulta.indexOf("<LEFTJOIN>") != -1){
			leftJoin = consulta.substring(inicioJoin, finJoin);
			leftJoin = leftJoin.replace("left join", "");
		}

		return leftJoin;
	}
	
	public String obtenerWhere(String consulta){
		String where = "";
		
		if(consulta.indexOf("<WHERE>") != -1){
			int inicioWhere = consulta.indexOf("<WHERE>")+8;
			int finWhere = consulta.indexOf("</WHERE>");
			where = consulta.substring(inicioWhere, finWhere);
			
		}

		return where;
	}
	
	public String obtenerOrderBy(String consulta){
		String orderBy = "";

		if(consulta.indexOf("<ORDERBY>") != -1){
			int inicioOrder = consulta.indexOf("<ORDERBY>")+10;
			int finOrder = consulta.indexOf("</ORDERBY>");
			orderBy = consulta.substring(inicioOrder, finOrder);
		}


		return orderBy;
	}
	
	public String obtenerGroupBy(String consulta){
		String groupBy = "";

		if(consulta.indexOf("<GROUPBY>") != -1){
			int inicioGroupBy = consulta.indexOf("<GROUPBY>")+10;
			int finGroupBy = consulta.indexOf("</GROUPBY>");
			groupBy = consulta.substring(inicioGroupBy, finGroupBy)+" ";
		}


		return groupBy;
	}
	public String obtenerHaving(String consulta){
		String having = "";

		if(consulta.indexOf("<HAVING>") != -1){
			int inicioHaving = consulta.indexOf("<HAVING>")+8;
			int finHaving = consulta.indexOf("</HAVING>");
			having = consulta.substring(inicioHaving, finHaving)+" ";
		}

		return having;
	}
	
	public Workbook crearExcel(List<Map<String, Object>> result){
		
		//Creamos el libro de excel
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Query");
		
		//Le aplicamos estilos a las cabeceras
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		//headerFont.setItalic(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        
        Row headerRow = sheet.createRow(0);

        
        //Recorremos el map y vamos metiendo celdas
        List<String> columnsKey = new ArrayList<String>();
        int rowNum = 1;
        int index = 0;
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
        	
        	for(int i = 0; i < columnsKey.size(); i++){
        		Object campo = map.get(columnsKey.get(i).trim());
        		if(campo == null || campo.toString().trim() == ""){
        			row.createCell(cell).setCellValue("null");
        		}else{
        			row.createCell(cell).setCellValue(campo.toString());
        		}
        		cell++;
        	}
		}
        
        for(int i = 0; i < index; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;

	}
	
	@Override
	public Map<String,String> obtenerMapaConsulta(String consulta){
		Map<String,String> mapa = new HashMap<String,String>();
		
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
		return mapa;
	}
	
}
