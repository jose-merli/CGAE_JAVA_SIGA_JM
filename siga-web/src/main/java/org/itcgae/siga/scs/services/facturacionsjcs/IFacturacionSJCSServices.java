package org.itcgae.siga.scs.services.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;

public interface IFacturacionSJCSServices {

	public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public FacturacionDeleteDTO eliminarFacturaciones(int idFactura, HttpServletRequest request);
	
	public FacturacionDTO datosFacturacion(String idFacturacion, HttpServletRequest request);
	
	public FacturacionDTO historicoFacturacion(String idFacturacion, HttpServletRequest request);
	
	public StringDTO numApuntes(String idFacturacion, HttpServletRequest request);
	
	public InsertResponseDTO saveFacturacion(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateFacturacion(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public InsertResponseDTO ejecutarFacturacion(String idFacturacion, HttpServletRequest request);
	
	public InsertResponseDTO reabrirFacturacion(String idFacturacion, HttpServletRequest request);
	
	public InsertResponseDTO simularFacturacion(String idFacturacion, HttpServletRequest request);
	
	public FacturacionDTO conceptosFacturacion(String idFacturacion, HttpServletRequest request);
	
	public InsertResponseDTO saveConceptosFac(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateConceptosFac(FacturacionItem facturacionItem, HttpServletRequest request);
	
	public DeleteResponseDTO deleteConceptosFac(List<FacturacionItem> facturacionDTO, HttpServletRequest request);
	
	public PagosjgDTO datosPagos(String idFacturacion, HttpServletRequest request);

	public void ejecutaFacturacionSJCS();
}