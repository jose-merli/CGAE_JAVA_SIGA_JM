package org.itcgae.siga.adm.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.adm.AdmContadorUpdateDTO;
import org.itcgae.siga.DTOs.adm.ContadorDTO;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IContadoresService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContadoresServiceImpl implements IContadoresService{

	private Logger LOGGER = Logger.getLogger(ContadoresServiceImpl.class);
	
	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;
	
	
	@Override
	public ComboDTO getContadorModules() {
		LOGGER.info("getContadorModules() -> Entrada al servicio para obtener los tipos de módulos de abogacía");
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info("getContadorModules() / admContadorExtendsMapper.getModules() -> Entrada a admContadorExtendsMapper para obtener los tipos de módulos de abogacía");
		comboItems = admContadorExtendsMapper.getModules();
		LOGGER.info("getContadorModules() / admContadorExtendsMapper.getModules() -> Salida de admContadorExtendsMapper para obtener los tipos de módulos de abogacía");
		if(!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem); 
			combo.setCombooItems(comboItems);
		}
		else {
			LOGGER.warn("getContadorModules() -> No existen tipos de módulos de abogacía en la base de datos");
		}

		LOGGER.info("getContadorModules() -> Salida del servicio para obtener los tipos de módulos de abogacía");
		return combo;
	
	}

	@Override
	public ComboDTO getContadorMode( HttpServletRequest request) {
		LOGGER.info("getContadorMode() -> Entrada al servicio para obtener los tipos de modos de contadores");
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios .createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("getContadorMode() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("getActionType() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		AdmUsuarios usuario = usuarios.get(0);
		LOGGER.info("getActionType() / admContadorExtendsMapper.getMode() -> Entrada a admContadorExtendsMapper para obtener los modos de contadores");
		comboItems = admContadorExtendsMapper.getMode(usuario.getIdlenguaje());
		LOGGER.info("getActionType() / admContadorExtendsMapper.getMode() -> Salida de admContadorExtendsMapper para obtener los modos de contadores");
		
		if(!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
//			comboItem.setLabel("");
//			comboItem.setValue("-1");
//			comboItems.add(0, comboItem); 
			combo.setCombooItems(comboItems);
		}
		else {
			LOGGER.warn("getContadorMode() -> No existen los tipos de modos de contadores en la base de datos");
		}

		LOGGER.info("getContadorMode() -> Salida del servicio para obtener los tipos de modos de contadores");
		return combo;
	
	}

	@Override
	public ContadorDTO getContadorSearch(int numPagina, ContadorRequestDTO contadorRequestDTO,
			HttpServletRequest request) {
		
		LOGGER.info("getContadorSearch() -> Entrada al servicio para la búsqueda de contadores");
		ContadorDTO contadorResponse = new ContadorDTO();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		Short institucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		contadorRequestDTO.setIdInstitucion(String.valueOf(institucion));
		//Realizamos la llamada al servicio para obtener los contadores
		
		LOGGER.info("getContadorSearch() / admContadorExtendsMapper.getContadoresSearch() -> Entrada a admContadorExtendsMapper para  búsqueda de contadores");
		List<AdmContadorDTO> contadores = admContadorExtendsMapper.getContadoresSearch(numPagina, contadorRequestDTO);
		LOGGER.info("getContadorSearch() / admContadorExtendsMapper.getContadoresSearch() -> Salida de admContadorExtendsMapper para  búsqueda de contadores");
		
		if(null != contadores && contadores.size() > 0) {
			contadorResponse.setContadorItems(contadores);
		}
		else {
			LOGGER.warn("getContadorSearch() -> No existen contadores en la base de datos");
		}
		
		
		LOGGER.info("getContadorSearch() -> Salida del servicio para la búsqueda de contadores");
		return contadorResponse;
	}

	@Override
	public UpdateResponseDTO updateContador(AdmContadorUpdateDTO contadorUpdateDTO, HttpServletRequest request) {

		LOGGER.info("updateContador() -> Entrada al servicio para la actualización de contadores");
		UpdateResponseDTO updateResponseDTO =  new 	UpdateResponseDTO(); 
		int response = 0;
		
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("updateContador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		LOGGER.info("updateContador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		
		if(null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			
			
			AdmContador record = new AdmContador();
			
			if(null != usuario && null != usuario.getIdusuario())
			{
				record.setIdinstitucion(idInstitucion);
				record.setIdcontador(contadorUpdateDTO.getIdcontador());
				record.setNombre(contadorUpdateDTO.getNombre());
				record.setDescripcion(contadorUpdateDTO.getDescripcion());
				record.setModificablecontador(contadorUpdateDTO.getModificablecontador());
				record.setModo(contadorUpdateDTO.getModo());
				record.setContador(contadorUpdateDTO.getContador());
				record.setPrefijo(contadorUpdateDTO.getPrefijo());
				record.setSufijo(contadorUpdateDTO.getSufijo());
				record.setLongitudcontador(contadorUpdateDTO.getLongitudcontador());
				record.setFechareconfiguracion(contadorUpdateDTO.getFechareconfiguracion());
				record.setReconfiguracioncontador(contadorUpdateDTO.getReconfiguracioncontador());
				record.setReconfiguracionprefijo(contadorUpdateDTO.getReconfiguracionprefijo());
				record.setReconfiguracionsufijo(contadorUpdateDTO.getReconfiguracionsufijo());
				record.setIdtabla(contadorUpdateDTO.getIdtabla());
				record.setIdcampocontador(contadorUpdateDTO.getIdcampocontador());
				record.setIdcampoprefijo(contadorUpdateDTO.getIdcampoprefijo());
				record.setIdcamposufijo(contadorUpdateDTO.getIdcamposufijo());
				record.setIdmodulo(contadorUpdateDTO.getIdmodulo());
				record.setGeneral(contadorUpdateDTO.getGeneral());
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario.getIdusuario());
				record.setFechacreacion(contadorUpdateDTO.getFechacreacion());
				record.setUsucreacion(contadorUpdateDTO.getUsucreacion());
			}
			else {
				LOGGER.warn("getContadorSearch() -> No existe un usuario logeado o su idusuario es nulo");
			}
			

			//Llamamos al método que actualizará el registro
			LOGGER.info("updateContador() / admContadorExtendsMapper.updateByPrimaryKey() -> Entrada a admContadorExtendsMapper para actualizar información de contadores");
			response = this.admContadorExtendsMapper.updateByPrimaryKey(record);
			LOGGER.info("updateContador() / admContadorExtendsMapper.updateByPrimaryKey() -> Salida de admContadorExtendsMapper para actualizar información de contadores");
			
		}
		else {
			LOGGER.warn("updateContador() / admUsuariosExtendsMapper.selectByExample() -> No se ha podido obtener información del usuario logeado");
		}
	
		
		// respuesta si se actualiza correctamente
		if(response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info("updateContador() / admContadorExtendsMapper.updateByPrimaryKey() -> OK. Características de contadores actualizadas correctamente en bd");
		}
			
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateContador() / admContadorExtendsMapper.updateByPrimaryKey() ->  KO. Características de contadores NO actualizadas correctamente en bd");
		}
			
		LOGGER.info("updateContador() -> Salida del servicio para la actualización de contadores");
		return updateResponseDTO;
	}

	
	
	

	
}
