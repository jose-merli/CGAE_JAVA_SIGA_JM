package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AreasDTO;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.DTOs.scs.MateriasDTO;

public interface IFichaAreasService {
	
	public ComboDTO getJurisdicciones(HttpServletRequest request);

	public AreasDTO searchAreas(AreasItem areasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteAreas(AreasDTO areasDTO, HttpServletRequest request);

	public UpdateResponseDTO updateArea(AreasItem areasItem, HttpServletRequest request);

	public InsertResponseDTO createArea(AreasItem areasItem, HttpServletRequest request);

	public MateriasDTO searchMaterias(String idArea, HttpServletRequest request);

	public UpdateResponseDTO updateMaterias(AreasDTO areasDTO, HttpServletRequest request);

	UpdateResponseDTO deleteMaterias(AreasDTO areasDTO, HttpServletRequest request);

	public InsertResponseDTO createMaterias(AreasItem areasItem, HttpServletRequest request);

	public UpdateResponseDTO activateAreas(AreasDTO areasDTO, HttpServletRequest request);

}
