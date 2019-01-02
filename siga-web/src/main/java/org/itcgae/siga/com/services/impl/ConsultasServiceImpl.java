package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import org.itcgae.siga.com.services.IConsultasService;
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
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
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
						String descripcion = "Copia " + i+1 +" - " + consulta.getDescripcion();
						consulta.setIdinstitucion(idInstitucion);
						consulta.setDescripcion(descripcion);
						consulta.setFechamodificacion(new Date());
						consulta.setUsumodificacion(usuario.getIdusuario());
						_conConsultaMapper.insert(consulta);
					}
					 
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
						}else if(!general && idInstitucion == consulta.getIdinstitucion()){
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
						consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setIdinstitucion(idInstitucion);
						consulta.setObservaciones(consulta.getObservaciones());
						consulta.setDescripcion(consulta.getDescripcion());
						consulta.setIdobjetivo(Long.parseLong(consultaDTO.getIdObjetivo()));
						consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						if(consultaDTO.getGenerica().equals("1")){
							consulta.setGeneral("S");
						}else{
							consulta.setGeneral("N");
						}
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
						respuesta.setMessage("Consulta creada");
					}else{
						ConConsultaKey key = new ConConsultaKey();
						key.setIdconsulta(Long.parseLong(consultaDTO.getIdConsulta()));
						key.setIdinstitucion(Short.parseShort(consultaDTO.getIdInstitucion()));
						ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
						consulta.setIdmodulo(Short.valueOf(consultaDTO.getIdModulo()));
						consulta.setDescripcion(consultaDTO.getNombre());
						consulta.setObservaciones(consultaDTO.getDescripcion());
						if(consultaDTO.getGenerica().equals("1")){
							consulta.setGeneral("S");
						}else{
							consulta.setGeneral("N");
						}
						consulta.setIdclasecomunicacion(Short.valueOf(consultaDTO.getIdClaseComunicacion()));
						switch(consultaDTO.getIdObjetivo()){
						case "1":
							//Destinarios
							consulta.setTipoconsulta("E");
							camposIncorrectos = comprobarCamposDestinarios(consulta.getSentencia());
							//insertarSelectDestinatarios(consulta.getSentencia());
							break;
						case "2":
							consulta.setTipoconsulta("M");
							break;
						case "3":
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
							}
						}else{
							respuesta.setCode(200);
							respuesta.setMessage("Consulta actualizada");
						}
						respuesta.setCode(200);
						respuesta.setMessage("Consulta actualizada");
						
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
					key.setIdinstitucion(Short.parseShort(consultaDTO.getIdInstitucion()));
					ConConsulta consulta = _conConsultaMapper.selectByPrimaryKey(key);
					camposIncorrectos = comprobarEtiquetas(consultaDTO.getSentencia());
					
					if(camposIncorrectos){
						respuesta.setCode(400);
						respuesta.setMessage("Etiquetas insuficientes");
					}else{
						
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
	public Error ejecutarConsulta(HttpServletRequest request, String consulta) {
		// TODO Auto-generated method stub
		return null;
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
		
		if(!sentencia.contains("<SELECT>") && !sentencia.contains("</SELECT>")){
			etiquetasInsuficientes = true;
		}
		if(!sentencia.contains("<FROM>") && !sentencia.contains("</FROM>")){
			etiquetasInsuficientes = true;
		}
		return etiquetasInsuficientes;
	}
	
	public String insertarSelectDestinatarios (String sentencia){
		
		int indexInicio = sentencia.indexOf("<SELECT>" +6);
		int indexFinal = sentencia.indexOf("</SELECT>");
		String select = sentencia.substring(indexInicio, indexFinal);
		
		if(!sentencia.contains("CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\"")){
			select+= " CEN_CLIENTE.IDINSTITUCION AS \"IDINSTITUCION\"";
		}
		if(!sentencia.contains("CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\"")){
			select+= " CEN_CLIENTE.IDPERSONA AS \"IDPERSONA\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\"")){
			select+= " CEN_DIRECCIONES.CORREOELECTRONICO AS \"CORREOELECTRONICO\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\"")){
			select+= " CEN_DIRECCIONES.DOMICILIO AS \"DOMICILIO\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.MOVIL AS \"MOVIL\"")){
			select+= " CEN_DIRECCIONES.MOVIL AS \"MOVIL\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.FAX1 AS \"FAX1\"")){
			select+= " CEN_DIRECCIONES.FAX1 AS \"FAX1\"";	
		}
		if(!sentencia.contains("CEN_DIRECCIONES.CODIGOPOSTAL AS \"CODIGOPOSTAL\"")){
			select+= " CEN_DIRECCIONES.FAX2 AS \"FAX2\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\"")){
			select+= " CEN_DIRECCIONES.IDPAIS AS AS \"IDPAIS\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\"")){
			select+= " CEN_DIRECCIONES.IDPROVINCIA AS \"IDPROVINCIA\"";
		}
		if(!sentencia.contains("CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\"")){
			select+= " CEN_DIRECCIONES.IDPOBLACION AS \"IDPOBLACION\"";
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
	
}
