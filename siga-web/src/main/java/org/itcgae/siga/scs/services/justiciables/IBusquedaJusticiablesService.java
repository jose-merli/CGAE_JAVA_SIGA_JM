package org.itcgae.siga.scs.services.justiciables;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.PersonaTelefonoDTO;

public interface IBusquedaJusticiablesService {
	
	public ComboDTO getComboRoles(HttpServletRequest request);

	public PersonaTelefonoDTO getTelefonos(HttpServletRequest request);

	public JusticiableBusquedaDTO searchJusticiables (JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);
}
