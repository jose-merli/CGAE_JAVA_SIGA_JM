package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmRolExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.mappers.AdmRolMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;

@Service
public class GestionUsuariosGruposServiceImpl implements IGestionUsuariosGruposService{

	@Autowired
	private AdmRolMapper admRolMapper;
	
	@Autowired 
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	
	/***
	 * Get the different users roles 
	 * Return ComboDTO (id, value) where id is the identificator rol and value is the description of these rol.
	 */
	@Override
	public ComboDTO getUsersRole() {
		AdmRolExample example = new AdmRolExample();
		List<AdmRol> roles = new ArrayList<AdmRol>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		String unblocked = "N";
		
		example.createCriteria().andBloqueadoEqualTo(String.valueOf(unblocked));
		example.setOrderByClause("DESCRIPCION ASC"); 
		roles = admRolMapper.selectByExample(example);
		
		if(roles!= null && roles.size() > 0)
		{
			for (AdmRol admRol : roles) {
				ComboItem comboItem = new ComboItem();
				comboItem.setId(admRol.getIdrol());
				comboItem.setValue(admRol.getDescripcion());
				comboItems.add(comboItem);			
			}
		}
		
		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get the different users profiles. 
	 * Return object ComboDto (id, description) sorted by description(natural String order) and 
	 * where each element id is unique for a description 
	 */
	@Override
	public ComboDTO getUsersProfile() {
		AdmPerfilExample example = new AdmPerfilExample();
		HashMap<String, String> hashProfiles = new HashMap<String, String>();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		
		example.setOrderByClause("DESCRIPCION ASC"); 
		example.setDistinct(true);
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);
		
		if(profiles!= null && profiles.size() > 0){
			for (AdmPerfil admPerfil : profiles){
				hashProfiles.put(admPerfil.getIdperfil(), admPerfil.getDescripcion());
				
			}
			
			
			hashProfiles.forEach(
					(id,description) ->{
						ComboItem comboItem = new ComboItem();
						comboItem.setId(id);
						comboItem.setValue(description);
						comboItems.add(comboItem);					
						}
			);
			
			// ordenar por descripcion
			Comparator<ComboItem> orderBydDescription = new Comparator<ComboItem>(){
				public int compare(ComboItem combo1, ComboItem combo2){
					return String.valueOf(combo1.getValue()).compareTo(String.valueOf(combo2.getValue()));
				}
			};
			
			comboItems.sort(orderBydDescription);
			
		}
		
		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get users information, showing them on different pages on web page. This information can be searched by a web user using different parameters. Call the mapper to get them.
	 * Return the users information.
	 */
	@Override
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO) {
		List<UsuarioItem> usuarioItems = new ArrayList<UsuarioItem>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		
		usuarioItems = admUsuariosExtendsMapper.getUsersByFilter(numPagina, usuarioRequestDTO);
		
		if(usuarioItems!=null)
		{
			usuarioDTO.setUsuarioItem(usuarioItems);
		}
		
		return usuarioDTO;
	}

	
	@Override
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		
		if(!usuarioUpdateDTO.getActivo().equalsIgnoreCase("") || !usuarioUpdateDTO.getCodigoExterno().equalsIgnoreCase(""))
		{
			response1 = admUsuariosExtendsMapper.updateUsersAdmUserTable(usuarioUpdateDTO);
		}
		if(!usuarioUpdateDTO.getGrupo().equalsIgnoreCase(""))
		{
			response2 = admUsuariosExtendsMapper.updateUsersAdmPerfilTable(usuarioUpdateDTO);
		}
		
		if(response1 == 1 && response2 == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return updateResponseDTO;
	}

	@Override
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO) {
		
		AdmUsuarios cambioUsuarios = new AdmUsuarios();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 1;
		
		if(!usuarioDeleteDTO.getIdUsuario().equalsIgnoreCase("") && !usuarioDeleteDTO.getIdInstitucion().equalsIgnoreCase(""))
		{
			Date fechaActual  = new Date();
			
			cambioUsuarios.setIdusuario(Integer.valueOf(usuarioDeleteDTO.getIdUsuario()));
			cambioUsuarios.setIdinstitucion(Short.valueOf(usuarioDeleteDTO.getIdInstitucion()));
			cambioUsuarios.setFechaBaja(fechaActual);
			cambioUsuarios.setActivo("N");
			response = admUsuariosExtendsMapper.updateByPrimaryKeySelective(cambioUsuarios);
		}
		else 
			response = 0;
		
		if(response == 1)
			deleteResponseDTO.setStatus("OK");
		else
			deleteResponseDTO.setStatus("ERROR");
		
		return deleteResponseDTO;
	}

}
