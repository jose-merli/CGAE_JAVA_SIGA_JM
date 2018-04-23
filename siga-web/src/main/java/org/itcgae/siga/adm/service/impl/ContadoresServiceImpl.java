package org.itcgae.siga.adm.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
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
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContadoresServiceImpl implements IContadoresService{

	
	
	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;
	
	
	@Override
	public ComboDTO getContadorModules() {

		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		comboItems = admContadorExtendsMapper.getModules();
		
		if(!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem); 
			combo.setCombooItems(comboItems);
		}

		return combo;
	
	}

	@Override
	public ComboDTO getContadorMode( HttpServletRequest request) {

		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios .createCriteria().andNifEqualTo(dni);
		exampleUsuarios.createCriteria().andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		comboItems = admContadorExtendsMapper.getMode(usuario.getIdlenguaje());
		
		if(!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem); 
			combo.setCombooItems(comboItems);
		}

		return combo;
	
	}

	@Override
	public ContadorDTO getContadorSearch(int numPagina, ContadorRequestDTO contadorRequestDTO,
			HttpServletRequest request) {
		ContadorDTO contadorResponse = new ContadorDTO();
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);


		String institucion = nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length());
		contadorRequestDTO.setIdInstitucion(institucion);
		//Realizamos la llamada al servicio para obtener los contadores
		List<AdmContadorDTO> contadores = this.admContadorExtendsMapper.getContadoresSearch(numPagina, contadorRequestDTO);
		contadorResponse.setContadorItems(contadores);
		
		return contadorResponse;
	}

	@Override
	public UpdateResponseDTO updateContador(AdmContadorDTO contadorUpdateDTO, HttpServletRequest request) {

		UpdateResponseDTO updateResponseDTO =  new 	UpdateResponseDTO(); 
		
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios .createCriteria().andNifEqualTo(dni);
		exampleUsuarios.createCriteria().andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		int response = 0;
		
		AdmContador record = new AdmContador();
		
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

		//Llamamos al método que actualizará el registro
		response = this.admContadorExtendsMapper.updateByPrimaryKeySelective(record);
		
		if(response == 1)
			updateResponseDTO.setStatus(SigaConstants.OK);
		else
			updateResponseDTO.setStatus(SigaConstants.KO);
		
		return updateResponseDTO;
	}

	
	
	

	
}
