package org.itcgae.siga.scs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTO.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTO.scs.JusticiableDTO;
import org.itcgae.siga.DTO.scs.JusticiableItem;
import org.itcgae.siga.DTO.scs.JusticiableTelefonoDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionJusticiableService {
	
	public ComboDTO getMinusvalias(HttpServletRequest request);

	public ComboDTO getProfesiones(HttpServletRequest request);
	
	public ComboDTO getPoblacion(String idPoblacion, HttpServletRequest request);
	
	public ComboDTO getTipoVias(HttpServletRequest request);

	public JusticiableDTO searchJusticiable (JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);

	public InsertResponseDTO createJusticiable(JusticiableItem justiciableItem,  HttpServletRequest request);
	
	public UpdateResponseDTO updateJusticiable(JusticiableItem justiciableItem, boolean datosGenerales, HttpServletRequest request);
	
	public JusticiableTelefonoDTO getTelefonos(JusticiableItem justiciableItem, HttpServletRequest request);
	
	public AsuntosJusticiableDTO searchAsuntosJusticiable(String idPersona, HttpServletRequest request);
	
	public AsuntosJusticiableDTO searchAsuntosConClave(List<AsuntosClaveJusticiableItem> asuntosClaveList, boolean fromJusticiable, HttpServletRequest request);

	public JusticiableDTO getJusticiableByNif (JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);

	public UpdateResponseDTO associateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request);

	public UpdateResponseDTO disassociateRepresentante(JusticiableItem justiciableItem, HttpServletRequest request);	
}
