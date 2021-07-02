package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IProductosService {
	public ComboDTO comboIva(HttpServletRequest request);
	public ComboDTO comboTipoFormaPago(HttpServletRequest request);
}
