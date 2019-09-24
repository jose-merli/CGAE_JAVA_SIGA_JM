package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.PrisionDTO;
import org.itcgae.siga.DTO.scs.PrisionItem;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;

public interface IBusquedaPrisionesService {

	public PrisionDTO searchPrisiones(PrisionItem prisionItem, HttpServletRequest request);

	public UpdateResponseDTO deletePrisiones(PrisionDTO prisionDTO, HttpServletRequest request);

	public UpdateResponseDTO activatePrisiones(PrisionDTO prisionDTO, HttpServletRequest request);
}
