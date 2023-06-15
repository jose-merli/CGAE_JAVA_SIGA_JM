package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITiposProductosService {
	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request);
	public ComboDTO searchTiposProductosByIdCategoria(HttpServletRequest request, String idCategoria);
	public ListadoTipoProductoDTO searchTiposProductosHistorico(HttpServletRequest request);
	public ComboDTO comboTiposProductos(HttpServletRequest request);
	public DeleteResponseDTO crearEditarProducto(ListadoTipoProductoDTO listadoProductos, HttpServletRequest request) throws Exception;
	public ProductoDTO activarDesactivarProducto(ListadoTipoProductoDTO listadoProductos, HttpServletRequest request);
	public ComboDTO searchTiposProductosByIdCategoriaMultiple(HttpServletRequest request, String idCategoria);
}
