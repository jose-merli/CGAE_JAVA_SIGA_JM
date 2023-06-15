package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IMovimientosVariosFactServices {

    public MovimientosVariosFacturacionDTO buscarMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);

    public DeleteResponseDTO deleteMovimientosVarios(List<MovimientosVariosFacturacionItem> movimientos, HttpServletRequest request) throws Exception;

    public InsertResponseDTO saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);

    public UpdateResponseDTO updateClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientos, HttpServletRequest request);

    public MovimientosVariosFacturacionDTO getListadoPagos(MovimientosVariosFacturacionItem movimientos,
                                                           HttpServletRequest request);

    public InsertResponseDTO saveMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
                                                           HttpServletRequest request);

    public UpdateResponseDTO updateMovimientosVarios(MovimientosVariosFacturacionItem movimientos,
                                                             HttpServletRequest request);

    public MovimientosVariosFacturacionDTO getMovimientoVarioPorId(String idMovimiento, HttpServletRequest request);
}
