package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.scs.services.facturacionsjcs.IPagoSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pagosjcs")
public class PagoSJCSController {

    @Autowired
    private IPagoSJCSService iPagoSJCSService;

    @PostMapping("/buscarPagos")
    ResponseEntity<PagosjgDTO> buscarPagos(@RequestBody PagosjgItem pagosItem, HttpServletRequest request) {
        PagosjgDTO response = iPagoSJCSService.buscarPagos(pagosItem, request);
        return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/datosGeneralesPago")
    ResponseEntity<PagosjgDTO> datosGeneralesPago(@RequestParam("idPago") String idPago, HttpServletRequest request) {
        PagosjgDTO response = iPagoSJCSService.datosGeneralesPagos(idPago, request);
        return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/historicoPagos")
    ResponseEntity<PagosjgDTO> historicoPagos(@RequestParam("idPago") String idPago, HttpServletRequest request) {
        PagosjgDTO response = iPagoSJCSService.historicoPagos(idPago, request);
        return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/savePago")
    ResponseEntity<InsertResponseDTO> savePago(@RequestBody PagosjgItem pagosjgItem, HttpServletRequest request) {
        InsertResponseDTO response = iPagoSJCSService.savePago(pagosjgItem, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/updatePago")
    ResponseEntity<UpdateResponseDTO> updatePago(@RequestBody PagosjgItem pagosjgItem, HttpServletRequest request) {
        UpdateResponseDTO response = iPagoSJCSService.updatePago(pagosjgItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/comboConceptosPago")
    ResponseEntity<ConceptoPagoDTO> comboConceptosPago(@RequestParam("idFacturacion") String idFacturacion, @RequestParam("idPago") String idPago,
                                                       HttpServletRequest request) {
        ConceptoPagoDTO response = iPagoSJCSService.comboConceptosPago(idFacturacion, idPago, request);
        return new ResponseEntity<ConceptoPagoDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/getConceptosPago")
    ResponseEntity<ConceptoPagoDTO> getConceptosPago(@RequestParam("idPago") String idPago,
                                                     @RequestParam("idFacturacion") String idFacturacion,
                                                     HttpServletRequest request) {
        ConceptoPagoDTO response = iPagoSJCSService.getConceptosPago(idPago, idFacturacion, request);
        return new ResponseEntity<ConceptoPagoDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/saveConceptoPago")
    ResponseEntity<UpdateResponseDTO> saveConceptoPago(@RequestBody List<ConceptoPagoItem> listaConceptoPagoItem,
                                                       HttpServletRequest request) {
        UpdateResponseDTO response = iPagoSJCSService.saveConceptoPago(listaConceptoPagoItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteConceptoPago")
    ResponseEntity<DeleteResponseDTO> deleteConceptoPago(@RequestBody List<ConceptoPagoItem> listaConceptoPagoItem,
                                                         HttpServletRequest request) {
        DeleteResponseDTO response = iPagoSJCSService.deleteConceptoPago(listaConceptoPagoItem, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/comboPropTranferenciaSepa")
    ResponseEntity<ComboDTO> comboPropTranferenciaSepa(HttpServletRequest request) {
        ComboDTO response = iPagoSJCSService.comboPropTranferenciaSepa(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/comboPropOtrasTranferencias")
    ResponseEntity<ComboDTO> comboPropOtrasTranferencias(HttpServletRequest request) {
        ComboDTO response = iPagoSJCSService.comboPropOtrasTranferencias(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/comboSufijos")
    ResponseEntity<ComboDTO> comboSufijos(HttpServletRequest request) {
        ComboDTO response = iPagoSJCSService.comboSufijos(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/comboCuentasBanc")
    ResponseEntity<ComboDTO> comboCuentasBanc(HttpServletRequest request) {
        ComboDTO response = iPagoSJCSService.comboCuentasBanc(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @PostMapping("saveConfigFichAbonos")
    ResponseEntity<UpdateResponseDTO> saveConfigFichAbonos(@RequestBody PagosjgItem pagosjgItem, HttpServletRequest request) {
        UpdateResponseDTO response = iPagoSJCSService.saveConfigFichAbonos(pagosjgItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("getConfigFichAbonos")
    ResponseEntity<PagosjgDTO> getConfigFichAbonos(@RequestParam("idPago") String idPago, HttpServletRequest request) {
        PagosjgDTO response = iPagoSJCSService.getConfigFichAbonos(idPago, request);
        return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
    }

    @GetMapping("getNumApuntesPago")
    ResponseEntity<StringDTO> getNumApuntesPago(@RequestParam("idPago") String idPago, HttpServletRequest request) {
        StringDTO response = iPagoSJCSService.getNumApuntesPago(idPago, request);
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }
}
