package org.itcgae.siga.scs.controllers.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.scs.services.facturacionsjcs.IPagoSJCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/getConceptosPago")
	ResponseEntity<ConceptoPagoDTO> getConceptosPago(@RequestParam("idPago") String idPago,
			@RequestParam("idFacturacion") String idFacturacion, HttpServletRequest request) {
		ConceptoPagoDTO response = iPagoSJCSService.getConceptosPago(idPago, idFacturacion, request);
		return new ResponseEntity<ConceptoPagoDTO>(response, HttpStatus.OK);
	}

	@PostMapping("/saveConceptoPago")
	ResponseEntity<UpdateResponseDTO> saveConceptoPago(@RequestBody List<ConceptoPagoItem> listaConceptoPagoItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = iPagoSJCSService.saveConceptoPago(listaConceptoPagoItem, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}

}
