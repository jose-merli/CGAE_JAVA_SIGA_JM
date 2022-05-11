package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionItem;
import org.itcgae.siga.DTOs.scs.ModulosDTO;
import org.itcgae.siga.DTOs.scs.ModulosItem;

public interface IModulosYBasesService {
	
	public ModulosDTO searchModules(ModulosItem modulosItem, HttpServletRequest request);

	public UpdateResponseDTO deleteModules(ModulosDTO modulosDTO, HttpServletRequest request);

	public AcreditacionDTO searchAcreditaciones(String idProcedimiento, HttpServletRequest request);

	public ComboDTO getAcreditaciones(String idProcedimiento, HttpServletRequest request);

	public UpdateResponseDTO updateModules(ModulosItem modulosItem, HttpServletRequest request);

	public UpdateResponseDTO deleteAcreditacion(AcreditacionDTO acreditacionDTO, HttpServletRequest request);

	public UpdateResponseDTO updateAcreditacion(AcreditacionDTO acreditacionDTO, HttpServletRequest request);

	public InsertResponseDTO createAcreditacion(AcreditacionItem acreditacionItem, HttpServletRequest request);

	public InsertResponseDTO createModules(ModulosItem modulosItem, HttpServletRequest request);

	public ComboDTO getProcedimientos(HttpServletRequest request, String idJurisdiccion);

	UpdateResponseDTO checkModules(ModulosDTO modulosDTO, HttpServletRequest request);

}
