package org.itcgae.siga.scs.services.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;

public interface IMovimientosVariosFactServices {
	
    public MovimientosVariosFacturacionDTO buscarMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);
    
    public DeleteResponseDTO deleteMovimientosVarios(List<MovimientosVariosFacturacionItem>  movimientos, HttpServletRequest request) throws Exception;

	public InsertResponseDTO saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);

	public UpdateResponseDTO updateClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);

	public MovimientosVariosFacturacionDTO getListadoPagos(MovimientosVariosFacturacionItem movimientos,
			HttpServletRequest request);

	public InsertResponseDTO saveDatosGenMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
			HttpServletRequest request);

	public UpdateResponseDTO updateDatosGenMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
			HttpServletRequest request);

	public InsertResponseDTO saveCriteriosMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
			HttpServletRequest request);

	public UpdateResponseDTO updateCriteriosMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
			HttpServletRequest request);

    


    

}
