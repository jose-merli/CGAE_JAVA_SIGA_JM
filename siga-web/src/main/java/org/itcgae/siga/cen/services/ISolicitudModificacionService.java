package org.itcgae.siga.cen.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ISolicitudModificacionService {
	public ComboDTO getComboTipoModificacion(HttpServletRequest request);
	public ComboDTO getComboEstado(HttpServletRequest request);
	public SolModificacionDTO searchModificationRequest(int numPagina, SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request);
	public UpdateResponseDTO processGeneralModificationRequest(ArrayList<SolModificacionItem> solModificacionDTO,
			HttpServletRequest request);
	public InsertResponseDTO insertGeneralModificationRequest(SolModificacionItem solModificacionItem,
			HttpServletRequest request);
	public UpdateResponseDTO denyGeneralModificationRequest(ArrayList<SolModificacionItem> solModificacionDTO,
			HttpServletRequest request); 
}
