package org.itcgae.siga.scs.services.justiciables;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionJusticiableService {
	
	public ComboDTO getMinusvalias(HttpServletRequest request);

	public ComboDTO getProfesiones(HttpServletRequest request);


}
