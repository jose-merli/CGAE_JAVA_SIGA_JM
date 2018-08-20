package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaColegiadosServiceImpl implements IBusquedaColegiadosService{
	
	private Logger LOGGER = Logger.getLogger(BusquedaColegiadosServiceImpl.class);
	
	@Autowired
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenEstadocolegialExtendsMapper cenEstadocolegialExtendsMapper;
	
	@Autowired
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;
	
	@Override
	public ComboDTO getCivilStatus(HttpServletRequest request) {
		
		LOGGER.info("getCivilStatus() -> Entrada al servicio para obtener los tipos de estado civil");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
				comboItems = cenEstadocivilExtendsMapper.distinctCivilStatus(usuario.getIdlenguaje());
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
				
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCivilStatus() -> Salida del servicio para obtener los tipos de estado civil");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getSituacion(HttpServletRequest request) {
		
		LOGGER.info("getSituacion() -> Entrada al servicio para obtener los tipos situacion de un colegiado");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");
				comboItems = cenEstadocolegialExtendsMapper.distinctSituacionColegial(usuario.getIdlenguaje());
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");
				
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getSituacion() -> Salida del servicio para obtener los tipos situacion de un colegiado");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getCVCategory(HttpServletRequest request) {
		
		LOGGER.info("getCVCategory() -> Entrada al servicio para obtener los tipos de categorías curriculares");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Entrada a cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");
				comboItems = cenTiposcvExtendsMapper.selectCategoriaCV(usuario.getIdlenguaje());
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Salida de cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");
				
				ComboItem comboItem = new ComboItem();
				comboItem.setLabel("");
				comboItem.setValue("");
				comboItems.add(0, comboItem);
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCVCategory() -> Salida del servicio para obtener los tipos de categorías curriculares");
		
		return comboDTO;
	}

}
