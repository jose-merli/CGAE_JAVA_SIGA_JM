package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.ListaFacturasPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaDescuentosPeticionItem;

public interface IGestionFichaCompraSuscripcionService {
	
	public FichaCompraSuscripcionItem getFichaCompraSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem peticion);

	public InsertResponseDTO solicitarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;
	
	public InsertResponseDTO solicitarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;

	public UpdateResponseDTO aprobarCompra(HttpServletRequest request, FichaCompraSuscripcionItem ficha) throws Exception;

	public UpdateResponseDTO aprobarSuscripcion(HttpServletRequest request, FichaCompraSuscripcionItem ficha);

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
}
