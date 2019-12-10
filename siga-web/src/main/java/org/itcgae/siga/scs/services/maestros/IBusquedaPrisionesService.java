package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.PrisionDTO;
import org.itcgae.siga.DTOs.scs.PrisionItem;

public interface IBusquedaPrisionesService {

	public PrisionDTO searchPrisiones(PrisionItem prisionItem, HttpServletRequest request);

	public UpdateResponseDTO deletePrisiones(PrisionDTO prisionDTO, HttpServletRequest request);

	public UpdateResponseDTO activatePrisiones(PrisionDTO prisionDTO, HttpServletRequest request);
}
