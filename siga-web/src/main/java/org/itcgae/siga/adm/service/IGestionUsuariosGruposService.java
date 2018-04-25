package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioGruposDTO;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionUsuariosGruposService {

	public ComboDTO getUsersRole();
	
	public ComboDTO getUsersProfile();
	
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request);
	
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request);
	
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request);

	public UsuarioGruposDTO getUsersGroupsSearch(int numPagina, HttpServletRequest request);

	public DeleteResponseDTO deleteUsersGroup(UsuarioDeleteDTO usuarioDeleteDTO, HttpServletRequest request);

	public UsuarioGruposDTO getUsersGroupsHistoric(int numPagina, HttpServletRequest request);
}
