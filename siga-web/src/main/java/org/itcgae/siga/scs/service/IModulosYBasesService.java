package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ModulosDTO;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IModulosYBasesService {
	
	public ModulosDTO searchModules(ModulosItem modulosItem, HttpServletRequest request);

	public UpdateResponseDTO deleteModules(ModulosDTO modulosDTO, HttpServletRequest request);

}
