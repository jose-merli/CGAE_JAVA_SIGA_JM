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

    PagosjgDTO getPago(String idPago, HttpServletRequest request);

    PagosjgDTO buscarPagos(PagosjgItem pagosItem, HttpServletRequest request);

    PagosjgDTO datosGeneralesPagos(String idPago, HttpServletRequest request);

    PagosjgDTO historicoPagos(String idPago, HttpServletRequest request);

    InsertResponseDTO savePago(PagosjgItem pagosjgItem, HttpServletRequest request);

    UpdateResponseDTO updatePago(PagosjgItem pagosjgItem, HttpServletRequest request);

    DeleteResponseDTO deletePago(PagosjgItem pagosjgItem, HttpServletRequest request);

    ConceptoPagoDTO comboConceptosPago(String idFacturacion, String idPago, HttpServletRequest request);

    ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request);

    UpdateResponseDTO saveConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem, HttpServletRequest request);

    DeleteResponseDTO deleteConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                         HttpServletRequest request);

    InsertResponseDTO ejecutarPagoSJCS(String idPago, boolean simular, HttpServletRequest request);

    ComboDTO comboPropTranferenciaSepa(HttpServletRequest request);

    ComboDTO comboPropOtrasTranferencias(HttpServletRequest request);

    ComboDTO comboSufijos(HttpServletRequest request);

    ComboDTO comboCuentasBanc(HttpServletRequest request);

    UpdateResponseDTO saveConfigFichAbonos(PagosjgItem pagosjgItem, HttpServletRequest request);

    PagosjgDTO getConfigFichAbonos(String idPago, HttpServletRequest request);

    StringDTO getNumApuntesPago(String idPago, HttpServletRequest request);

    CompensacionFacDTO getCompensacionFacturas(String idPago, HttpServletRequest request);

    UpdateResponseDTO cerrarPago(String idPago, HttpServletRequest request);

    UpdateResponseDTO cerrarPagoManual(String idPago, List<String> idsParaEnviar, HttpServletRequest request);

    UpdateResponseDTO deshacerCierre(String idPago, HttpServletRequest request);
}