package org.itcgae.siga.fac.services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionItem;

public interface IGestionFichaCompraSuscripcionService {
	
	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem peticion);

	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha, Boolean fromAprobar) throws Exception;
	
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;

	public UpdateResponseDTO aprobarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;

	public UpdateResponseDTO aprobarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;

	public InsertResponseDTO denegarPeticionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception;

	public InsertResponseDTO aprobarCompraMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception;

	public InsertResponseDTO denegarPeticion(HttpServletRequest request, String nSolicitud) throws Exception;

	public InsertResponseDTO updateProductosPeticion(HttpServletRequest request, FichaCompraSuscripcionItem peticion)
			throws Exception;

	public ListaProductosCompraDTO getListaProductosCompra(HttpServletRequest request, String idPeticion);

	public String getPermisoModificarImporteProducto(HttpServletRequest request);

	public ListaFacturasPeticionDTO getFacturasPeticion(HttpServletRequest request, String nSolicitud);

	public ListaDescuentosPeticionDTO getDescuentosPeticion(HttpServletRequest request, String nSolicitud);

	public InsertResponseDTO saveAnticipoPeticion(HttpServletRequest request, ListaDescuentosPeticionItem anticipoLista) throws SigaExceptions;

	public DeleteResponseDTO deleteAnticipoPeticion(HttpServletRequest request,
			List<ListaDescuentosPeticionItem> anticiposLista) throws SigaExceptions;

	public UpdateResponseDTO anularPeticion(HttpServletRequest request, String nSolicitud) throws Exception;

	ListaServiciosSuscripcionDTO getListaServiciosSuscripcion(HttpServletRequest request, String nSolicitud, Date aFechaDe);

	InsertResponseDTO updateServiciosPeticion(HttpServletRequest request, FichaCompraSuscripcionItem peticion)
			throws Exception;

	InsertResponseDTO aprobarSuscripcionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception;

	InsertResponseDTO anularPeticionMultiple(HttpServletRequest request, FichaCompraSuscripcionItem[] peticiones)
			throws Exception;

	UpdateResponseDTO anadirAnticipoCompra(HttpServletRequest request, ListaDescuentosPeticionItem anticipo)
			throws Exception;
}
