package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosIntegrantesService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenProvinciasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosIntegrantesServiceImpl implements ITarjetaDatosIntegrantesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosIntegrantesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;
	
	@Autowired
	private CenProvinciasExtendsMapper cenProvinciasExtendsMapper;
	
	@Autowired
	private CenCargoExtendsMapper cenCargoExtendsMapper;





	@Override
	public DatosIntegrantesDTO searchIntegrantesData(int numPagina, DatosIntegrantesSearchDTO datosIntegrantesSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchIntegrantesData() -> Entrada al servicio para la búsqueda por filtros de integrantes ");
		
		List<DatosIntegrantesItem> datosIntegrantesItem = new ArrayList<DatosIntegrantesItem>();
		DatosIntegrantesDTO datosIntegrantesDTO = new DatosIntegrantesDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de integrantes ");
				datosIntegrantesSearchDTO.setIdInstitucion(idInstitucion.toString());
				datosIntegrantesItem = cenComponentesExtendsMapper.selectIntegrantes(datosIntegrantesSearchDTO);
				LOGGER.info(
						"searchIntegrantesData() / cenComponentesExtendsMapper.selectIntegrantes() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de integrantes ");

				datosIntegrantesDTO.setDatosIntegrantesItem(datosIntegrantesItem);
			} 
			else {
				LOGGER.warn("searchIntegrantesData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchIntegrantesData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchIntegrantesData() -> Salida del servicio para la búsqueda por filtros de integrantes ");
		return datosIntegrantesDTO;
	}


	@Override
	public ComboDTO getProvinces(HttpServletRequest request) {
		LOGGER.info("getProvinces() -> Entrada al servicio para búsqueda de las provincias");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Entrada a cenProvinciasExtendsMapper para obtener listado de provincias ");
		comboItems = cenProvinciasExtendsMapper.selectDistinctProvinces();
		LOGGER.info(
				"getProvinces() / cenProvinciasExtendsMapper.selectDistinctProvinces() -> Salida de cenProvinciasExtendsMapper para obtener listado de provincias ");
		
		
		if(null != comboItems && comboItems.size() > 0) {
			ComboItem element = new ComboItem();
			element.setLabel("");
			element.setValue("");
			comboItems.add(0, element);
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getProvinces() -> Salida al servicio para búsqueda de las provincias");
		return comboDTO;
	}


	@Override
	public ComboDTO getCargos(HttpServletRequest request) {
		LOGGER.info("getCargos() -> Entrada al servicio para búsqueda de cargos");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		AdmUsuarios usuario = new AdmUsuarios();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCargos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				usuario = usuarios.get(0);
				
				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Entrada a cenCargoExtendsMapper para obtener listado de cargos");
				comboItems = cenCargoExtendsMapper.getCargos(usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				LOGGER.info(
						"getCargos() / cenCargoExtendsMapper.getCargos() -> Salida de cenCargoExtendsMapper para obtener listado de cargos ");
				
				
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}
			}

		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCargos() -> Salida al servicio para búsqueda de cargos");
		return comboDTO;
	}

	
	
}
