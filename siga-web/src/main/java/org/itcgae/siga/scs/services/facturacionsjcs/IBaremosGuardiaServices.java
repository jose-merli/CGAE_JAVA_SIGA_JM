package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;

public interface IBaremosGuardiaServices {
	
	public BaremosRequestDTO searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request);
	
}