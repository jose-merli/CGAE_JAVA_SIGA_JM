package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IProductosService {
	public ComboDTO comboIva(HttpServletRequest request);
	public ComboDTO comboTipoFormaPago(HttpServletRequest request);
	public ProductoDetalleDTO detalleProducto(HttpServletRequest request, int idTipoProducto, int idProducto, int idProductoInstitucion);
	public InsertResponseDTO nuevoProducto(ProductoDetalleDTO producto, HttpServletRequest request);
	public DeleteResponseDTO editarProducto(ProductoDetalleDTO producto, HttpServletRequest request);
	public ListaProductosDTO searchListadoProductos(HttpServletRequest request, FiltroProductoItem filtroProductoItem);
	public DeleteResponseDTO reactivarBorradoFisicoLogicoProductos(ListaProductosDTO listadoProductos, HttpServletRequest request);
}
