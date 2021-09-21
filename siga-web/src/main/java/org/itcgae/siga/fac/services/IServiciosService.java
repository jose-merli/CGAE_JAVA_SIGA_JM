package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;

public interface IServiciosService {
	
	public ListaServiciosDTO searchListadoServicios(HttpServletRequest request, FiltroServicioItem filtroServicioItem);
	public DeleteResponseDTO reactivarBorradoFisicoLogicoServicios(ListaServiciosDTO listadoServicios, HttpServletRequest request);
	public ListaCodigosPorColegioDTO obtenerCodigosPorColegioServicios(HttpServletRequest request);
	public InsertResponseDTO nuevoServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception;
	public ServicioDetalleDTO detalleServicio(HttpServletRequest request, int idTipoServicio, int idServicio, int idServicioInstitucion);

}
