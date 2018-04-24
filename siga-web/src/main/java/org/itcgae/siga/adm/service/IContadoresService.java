package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.AdmContadorUpdateDTO;
import org.itcgae.siga.DTOs.adm.ContadorDTO;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IContadoresService {

	public ComboDTO getContadorModules();
	
	public ComboDTO getContadorMode( HttpServletRequest request);
	
	public ContadorDTO getContadorSearch(int numPagina, ContadorRequestDTO contadorRequestDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateContador(AdmContadorUpdateDTO contadorUpdateDTO, HttpServletRequest request);
	
	
	
}
