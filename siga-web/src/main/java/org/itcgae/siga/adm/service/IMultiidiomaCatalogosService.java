package org.itcgae.siga.adm.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IMultiidiomaCatalogosService {

	public ComboDTO getCatalogLenguage();
	
	public ComboDTO getCatalogEntity(HttpServletRequest request);

	public MultiidiomaCatalogoDTO catalogSearch(int numPagina, MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, HttpServletRequest request);
	
	public UpdateResponseDTO catalogUpdate(MultiidiomaCatalogoUpdateDTO multiidiomaCatalogoUpdateDTO, HttpServletRequest request);
}

