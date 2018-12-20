package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
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
import org.itcgae.siga.db.mappers.ConConsultaMapper;
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
<<<<<<< HEAD
	private ConListadoModelosExtendsMapper _conListadoModelosExtendsMapper;
	
	@Autowired
	private ConListadoPlantillasExtendsMapper _conListadoPlantillasExtendsMapper;
=======
	private ConConsultaMapper _conConsultaMapper;
>>>>>>> generado entidad para consultas y servicio de borrar
	
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

				AdmUsuarios usuario = usuarios.get(0);
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
	public java.lang.Error duplicarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
<<<<<<< HEAD
	public java.lang.Error borrarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
=======
	public Error borrarConsulta(HttpServletRequest request, String[] idConsulta) {
		LOGGER.info("borrarConsulta() -> Entrada al servicio de búsqueda de consultas");

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
				
				try {
					for (int i = 0; i < idConsulta.length; i++) {
						ConConsultaKey key = new ConConsultaKey();
						key.setIdconsulta(Long.valueOf(idConsulta[0]));
						key.setIdinstitucion(idInstitucion);
						_conConsultaMapper.deleteByPrimaryKey(key);
					}
					respuesta.setCode(200);
					respuesta.setDescription("Consultas borradas");
				} catch (Exception e) {
					respuesta.setCode(500);
					respuesta.setMessage("Error al borrar consulta");
					respuesta.setDescription(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("borrarConsulta() -> Salida del servicio de búsqueda de consultas");
		return respuesta;
>>>>>>> generado entidad para consultas y servicio de borrar
	}

	@Override
	public java.lang.Error guardarDatosGenerales(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsultaListadoModelosDTO obtenerModelosComunicacion(HttpServletRequest request, String idConsulta) {
	LOGGER.info("obtenerModelosComunicacion() -> Entrada al servicio de obtener modelos que contienen la consulta");
		
	ConsultaListadoModelosDTO conListadoModelosDTO = new ConsultaListadoModelosDTO();
		List<ModelosComunicacionItem> modeloList = new ArrayList<ModelosComunicacionItem>();

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
					modeloList = _conListadoModelosExtendsMapper.selectListadoModelos(usuario.getIdinstitucion(),
							 idConsulta);
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
	public ConsultaListadoPlantillasDTO obtenerPlantillasEnvio(HttpServletRequest request, String idConsulta) {
		LOGGER.info("obtenerPlantillasEnvio() -> Entrada al servicio de obtener plantillas que contienen la consulta");
		
		ConsultaListadoPlantillasDTO conListadoPlantillasDTO = new ConsultaListadoPlantillasDTO();
		List<PlantillaEnvioItem> plantillasList = new ArrayList<PlantillaEnvioItem>();

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
					plantillasList = _conListadoPlantillasExtendsMapper.selectListadoPlantillas(usuario.getIdinstitucion(),
							usuario.getIdlenguaje(), idConsulta);
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
	public java.lang.Error guardarConsulta(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.lang.Error ejecutarConsulta(HttpServletRequest request, String consulta) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
