package org.itcgae.siga.adm.service;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionUsuariosGruposService {

	public ComboDTO getUsersRole();
	
	public ComboDTO getUsersProfile();
	
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO);
	
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO);
	
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO);
	
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO);
}
