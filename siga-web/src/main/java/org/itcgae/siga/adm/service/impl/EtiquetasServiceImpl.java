package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.EtiquetaDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaItem;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.DTOs.adm.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IEtiquetasService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenDiccionarioExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtiquetasServiceImpl implements IEtiquetasService{

	
	@Autowired
	private GenDiccionarioExtendsMapper genDiccionarioExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Override
	public ComboDTO getLabelLenguage() {
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> combooItems = new ArrayList<ComboItem>();
		ComboItem comboItem = new ComboItem();
		
		
		combooItems = genDiccionarioExtendsMapper.getLabelLenguage();
		// primer elemento a vacio para componente de pantalla dropdown
		comboItem.setLabel("");
		comboItem.setValue("");
		combooItems.add(0,comboItem);
		comboDTO.setCombooItems(combooItems);
		
		return comboDTO;
	}

	@Override
	public EtiquetaDTO searchLabels(int numPagina, EtiquetaSearchDTO etiquetaSearchDTO, HttpServletRequest request) {
		EtiquetaDTO etiquetaDTO = new EtiquetaDTO();
		List<EtiquetaItem> etiquetaItems =  new ArrayList<EtiquetaItem>();
		
		if(!etiquetaSearchDTO.getIdiomaBusqueda().equals(null) && !etiquetaSearchDTO.getIdiomaTraduccion().equals(null)) {
			
			etiquetaItems = genDiccionarioExtendsMapper.searchLabels(numPagina, etiquetaSearchDTO);
		}
		
		etiquetaDTO.setEtiquetaItem(etiquetaItems);
		
		return etiquetaDTO;
	}

	@Override
	public UpdateResponseDTO updateLabel(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request) {
		int response = 0;
		GenDiccionario record = new GenDiccionario();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Conseguimos idUsuario del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		if(!usuario.getIdusuario().equals(null) && 
				!etiquetaUpdateDTO.getIdLenguaje().equals(null) && !etiquetaUpdateDTO.getIdLenguaje().equalsIgnoreCase("") &&
				!etiquetaUpdateDTO.getIdRecurso().equals(null) && !etiquetaUpdateDTO.getIdRecurso().equalsIgnoreCase("")
				) {
			record.setDescripcion(etiquetaUpdateDTO.getDescripcion());
			record.setIdlenguaje(etiquetaUpdateDTO.getIdLenguaje());
			record.setIdrecurso(etiquetaUpdateDTO.getIdRecurso());
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
			
			response  = genDiccionarioExtendsMapper.updateByPrimaryKeySelective(record);
		}
		
		if(response == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return updateResponseDTO;
	}

}
