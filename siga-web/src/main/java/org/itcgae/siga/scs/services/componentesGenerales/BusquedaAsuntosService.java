package org.itcgae.siga.scs.services.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;

public interface BusquedaAsuntosService {

	public AsuntosJusticiableDTO searchClaveAsuntosEJG(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosAsistencias(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosDesignaciones(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosSOJ(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);

	UpdateResponseDTO copyEjg2Soj(List<String> datos, HttpServletRequest request) throws Exception;

	UpdateResponseDTO copyEjg2Asis(List<String> datos, HttpServletRequest request) throws Exception;

	InsertResponseDTO copyEjg2Designa(List<String> datos, HttpServletRequest request) throws Exception;

	UpdateResponseDTO copyDesigna2Asis(List<String> datos, HttpServletRequest request) throws Exception;

	InsertResponseDTO copyDesigna2Ejg(List<String> datos, HttpServletRequest request) throws Exception;

	UpdateResponseDTO copyDesigna2Soj(List<String> datos, HttpServletRequest request) throws Exception;

	UpdateResponseDTO copyAsis2Soj(List<String> datos, HttpServletRequest request) throws Exception;

	UpdateResponseDTO copyAsis2Ejg(List<String> datos, HttpServletRequest request) throws Exception;

	InsertResponseDTO copyAsis2Designa(List<String> datos, HttpServletRequest request) throws Exception;
	
	

}
