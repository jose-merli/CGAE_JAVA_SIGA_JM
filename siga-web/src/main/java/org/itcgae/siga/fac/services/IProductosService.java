package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IProductosService {
	public ComboDTO comboIva(HttpServletRequest request);
	public ComboDTO comboTipoFormaPago(HttpServletRequest request);
	public ListaProductosDTO searchListadoProductos(HttpServletRequest request, FiltroProductoItem filtroProductoItem);
}
