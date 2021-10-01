package org.itcgae.siga.fac.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosDTO;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface IServiciosService {
	
	public ListaServiciosDTO searchListadoServicios(HttpServletRequest request, FiltroServicioItem filtroServicioItem);
	public DeleteResponseDTO reactivarBorradoFisicoLogicoServicios(ListaServiciosDTO listadoServicios, HttpServletRequest request) throws Exception;
	public ListaCodigosPorColegioDTO obtenerCodigosPorColegioServicios(HttpServletRequest request);
	public InsertResponseDTO nuevoServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception;
	public DeleteResponseDTO editarServicio(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception;
	public ServicioDetalleDTO detalleServicio(HttpServletRequest request, int idTipoServicio, int idServicio, int idServiciosInstitucion);
	//public ComboDTO comboCondicionSuscripcion(HttpServletRequest request, int idConsulta);
	public ComboDTO comboCondicionSuscripcion(HttpServletRequest request);
	public InsertResponseDTO crearEditarFormaPago(ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception;
	public DeleteResponseDTO borrarSuscripcionesBajas(BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, HttpServletRequest request) throws Exception;
	public FichaTarjetaPreciosDTO detalleTarjetaPrecios(HttpServletRequest request, ServicioDetalleDTO servicio);
	public ComboDTO comboPeriodicidad(HttpServletRequest request);
	

}
