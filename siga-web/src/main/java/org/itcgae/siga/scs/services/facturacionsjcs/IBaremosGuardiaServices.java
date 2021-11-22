package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;

public interface IBaremosGuardiaServices {
	
	public BaremosGuardiaDTO searchBaremosGuardia(Integer idTurno, Integer idGuardia, HttpServletRequest request);
	
}