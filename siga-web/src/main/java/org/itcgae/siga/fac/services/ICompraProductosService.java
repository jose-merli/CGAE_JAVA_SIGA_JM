package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ICompraProductosService {
	
	public ComboDTO comboEstadoFactura(HttpServletRequest request);
	
	public ListaCompraProductosDTO getListaCompraProductos(HttpServletRequest request, FiltrosCompraProductosItem peticion);


}
