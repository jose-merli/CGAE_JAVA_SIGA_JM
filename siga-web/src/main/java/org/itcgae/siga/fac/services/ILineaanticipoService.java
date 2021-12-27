package org.itcgae.siga.fac.services;

import org.itcgae.siga.DTO.fac.FichaMonederoItem;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaMonederosItem;
import org.itcgae.siga.DTO.fac.ListaMovimientosMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosMonederoDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface ILineaanticipoService {
    public ListaMonederoDTO listarMonederos(HttpServletRequest request, FiltroMonederoItem filtroMonederoItem);

	public UpdateResponseDTO updateMovimientosMonedero(HttpServletRequest request, FichaMonederoItem fichaMonederoItem) throws Exception;

	public ListaMovimientosMonederoDTO getListaMovimientosMonedero(HttpServletRequest request, String idAnticipo, String idPersona);

	public ListaServiciosMonederoDTO getListaServiciosMonedero(HttpServletRequest request, String idAnticipo, String idPersona);
	
	public UpdateResponseDTO updateServiciosMonedero(HttpServletRequest request, FichaMonederoItem ficha) throws Exception;

	public InsertResponseDTO liquidarMonederos(HttpServletRequest request, List<ListaMonederosItem> monederos)
			throws Exception;
}
