package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;

public interface IFacturacionServices {

	public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public FacturacionDeleteDTO eliminarFacturaciones(int idFactura, HttpServletRequest request);
	
	public FacturacionDTO datosFacturacion(String idFacturacion, HttpServletRequest request);
	
	public FacturacionDTO historicoFacturacion(String idFacturacion, HttpServletRequest request);
	
	public InsertResponseDTO saveFacturacion(FacturacionItem facturacionItem, HttpServletRequest request);
}