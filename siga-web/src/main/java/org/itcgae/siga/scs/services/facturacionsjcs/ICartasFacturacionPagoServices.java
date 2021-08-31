package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosDTO;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;

public interface ICartasFacturacionPagoServices {

	public CartasFacturacionPagosDTO buscarCartasfacturacion(CartasFacturacionPagosItem cartasFacturacionPagosItem, HttpServletRequest request);

	public CartasFacturacionPagosDTO buscarCartaspago(CartasFacturacionPagosItem cartasFacturacionPagosItem, HttpServletRequest request);

	
}