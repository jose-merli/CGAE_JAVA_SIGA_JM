package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerJuridicaService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposociedadExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaPerJuridicaServiceImpl implements IBusquedaPerJuridicaService{

	private Logger LOGGER = Logger.getLogger(BusquedaPerJuridicaServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenTiposociedadExtendsMapper cenTiposociedadExtendsMapper;
	
	@Autowired
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;
	
	@Override
	public ComboDTO getSocietyTypes(HttpServletRequest request) {
		LOGGER.info("getSocietyTypes() -> Entrada al servicio para obtener los tipos de sociedad");
		
		List<ComboItem> comboItem = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				LOGGER.info(
						"getSocietyTypes() / cenTiposociedadExtendsMapper.getSocietyTypes() -> Entrada a cenTiposociedadExtendsMapper para obtener lista de sociedades");
				comboItem = cenTiposociedadExtendsMapper.getSocietyTypes(usuario.getIdlenguaje());
				LOGGER.info(
						"getSocietyTypes() / cenTiposociedadExtendsMapper.getSocietyTypes() -> Salida de cenTiposociedadExtendsMapper para obtener lista de sociedades");
				comboDTO.setCombooItems(comboItem);
				
			}
			else {
				LOGGER.warn("getSocietyTypes() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getSocietyTypes() -> idInstitucion del token nula");
		}
		
		
		
		LOGGER.info("getSocietyTypes() -> Salida del servicio para obtener los tipos de sociedad");
		return comboDTO;
	}

	
	@Override
	public ComboDTO getLabel(HttpServletRequest request) {

		List<ComboItem> comboItem = new ArrayList<ComboItem>();
		ComboDTO comboDTO = new ComboDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logeado
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Entrada a cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboItem = cenGruposclienteExtendsMapper.getLabel(usuario);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboDTO.setCombooItems(comboItem);
			}
			else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		return comboDTO;
	}


	@Override
	public BusquedaJuridicaDTO searchLegalPersons(int numPagina, BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
