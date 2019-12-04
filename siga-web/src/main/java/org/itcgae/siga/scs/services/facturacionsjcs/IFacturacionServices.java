package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.FacturacionDTO;
import org.itcgae.siga.DTO.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTO.scs.FacturacionItem;

public interface IFacturacionServices {

	public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public FacturacionDeleteDTO eliminarFacturaciones(int idFactura, HttpServletRequest request);
}
