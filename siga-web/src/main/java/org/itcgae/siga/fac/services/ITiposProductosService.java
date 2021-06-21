package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDTO;

public interface ITiposProductosService {
	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request);
	public ProductoDTO activarDesactivarProducto(ListadoTipoProductoDTO listadoProductos, HttpServletRequest request);
}
