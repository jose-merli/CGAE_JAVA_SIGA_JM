package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.PysTipoiva;

public interface IProductosService {
	public ComboDTO comboIva(HttpServletRequest request);
	public ComboDTO comboIvaNoDerogados(HttpServletRequest request);
	public InsertResponseDTO crearEditarFormaPago(ProductoDetalleDTO producto, HttpServletRequest request) throws Exception;
	public ComboDTO comboTipoFormaPago(HttpServletRequest request);
	public ComboDTO comboTipoFormaPagoInternet(HttpServletRequest request);
	public ComboDTO comboTipoFormaPagoSecretaria(HttpServletRequest request);
	public ProductoDetalleDTO detalleProducto(HttpServletRequest request, int idTipoProducto, int idProducto, int idProductoInstitucion);
	public InsertResponseDTO nuevoProducto(ProductoDetalleDTO producto, HttpServletRequest request) throws Exception;
	public DeleteResponseDTO editarProducto(ProductoDetalleDTO producto, HttpServletRequest request);
	public ListaProductosDTO searchListadoProductos(HttpServletRequest request, FiltroProductoItem filtroProductoItem);
	public DeleteResponseDTO reactivarBorradoFisicoLogicoProductos(ListaProductosDTO listadoProductos, HttpServletRequest request) throws Exception;
	public ListaCodigosPorColegioDTO obtenerCodigosPorColegio(HttpServletRequest request);
	public PysTipoiva getIvaDetail(HttpServletRequest request, String idTipoIva);
}
