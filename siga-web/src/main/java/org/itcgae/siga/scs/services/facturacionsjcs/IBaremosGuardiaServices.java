package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBaremosGuardiaServices {
	
	public BaremosRequestDTO searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request);
	public BaremosGuardiaDTO getGuardiasByConf(HttpServletRequest request);
	public ComboDTO getTurnoForGuardia(HttpServletRequest request);
	public BaremosGuardiaDTO insertBaremo(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request);
	public BaremosGuardiaDTO updateBaremo(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request);
	
}