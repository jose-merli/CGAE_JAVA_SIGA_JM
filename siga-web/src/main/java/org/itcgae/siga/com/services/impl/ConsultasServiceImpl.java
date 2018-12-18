package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.com.services.IConsultasService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error duplicarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarDatosGenerales(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error obtenerModelosComunicacion(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error obtenerPlantillasEnvio(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarConsulta(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error ejecutarConsulta(HttpServletRequest request, String consulta) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
