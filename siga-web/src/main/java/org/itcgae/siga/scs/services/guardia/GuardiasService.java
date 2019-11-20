package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface GuardiasService {

	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);

	public GuardiasItem detalleGuardia(String idGuardia, HttpServletRequest request);

}
