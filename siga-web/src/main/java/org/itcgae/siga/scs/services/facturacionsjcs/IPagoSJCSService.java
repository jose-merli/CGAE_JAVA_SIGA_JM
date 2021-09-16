package org.itcgae.siga.scs.services.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPagoSJCSService {

    public PagosjgDTO buscarPagos(PagosjgItem pagosItem, HttpServletRequest request);

    public PagosjgDTO datosGeneralesPagos(String idPago, HttpServletRequest request);

    public PagosjgDTO historicoPagos(String idPago, HttpServletRequest request);

    public InsertResponseDTO savePago(PagosjgItem pagosjgItem, HttpServletRequest request);

    public UpdateResponseDTO updatePago(PagosjgItem pagosjgItem, HttpServletRequest request);

    public ConceptoPagoDTO comboConceptosPago(String idFacturacion, String idPago, HttpServletRequest request);

    public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request);

    public UpdateResponseDTO saveConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem, HttpServletRequest request);

    public DeleteResponseDTO deleteConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                                HttpServletRequest request);

    public InsertResponseDTO ejecutarPagoSJCS(String idPago, HttpServletRequest request);

    public ComboDTO comboPropTranferenciaSepa(HttpServletRequest request);

    public ComboDTO comboPropOtrasTranferencias(HttpServletRequest request);

    public ComboDTO comboSufijos(HttpServletRequest request);

    public ComboDTO comboCuentasBanc(HttpServletRequest request);

    public UpdateResponseDTO saveConfigFichAbonos(PagosjgItem pagosjgItem, HttpServletRequest request);

    public PagosjgDTO getConfigFichAbonos(String idPago, HttpServletRequest request);

    public StringDTO getNumApuntesPago(String idPago, HttpServletRequest request);

    public CompensacionFacDTO getCompensacionFacturas(String idPago, HttpServletRequest request);

    public void cerrarPago(String idPago, HttpServletRequest request);

}
