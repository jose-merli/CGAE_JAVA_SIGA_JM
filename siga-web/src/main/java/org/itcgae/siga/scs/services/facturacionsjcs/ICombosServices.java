package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ICombosServices {
	
	public ComboDTO comboFactEstados(HttpServletRequest request);
	
}
