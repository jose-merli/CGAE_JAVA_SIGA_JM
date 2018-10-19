package org.itcgae.siga.gen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.gen.DiccionarioDTO;

public interface IDiccionarioService {


	public  DiccionarioDTO  getDiccionario(String lenguaje, HttpServletRequest request);

	public  UsuarioDTO getUsuario(HttpServletRequest request);


}
