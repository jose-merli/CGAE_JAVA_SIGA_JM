package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.JusticiableBusquedaDTO;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaJusticiablesService {
	
	public ComboDTO getComboRoles(HttpServletRequest request);

	public JusticiableBusquedaDTO searchJusticiables (JusticiableBusquedaItem justiciableBusquedaItem, HttpServletRequest request);
}
