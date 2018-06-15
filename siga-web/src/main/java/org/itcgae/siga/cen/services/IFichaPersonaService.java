package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPerSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaDTO;

public interface IFichaPersonaService {

	public FichaPersonaDTO searchPersonFile(int numPagina, FichaPerSearchDTO fichaPerSearch, HttpServletRequest request);
	public UpdateResponseDTO disassociatePerson(DesasociarPersonaDTO desasociarPersona, HttpServletRequest request);
	public UpdateResponseDTO savePerson(AsociarPersonaDTO asociarPersona, HttpServletRequest request);

}
