package org.itcgae.siga.fac.controllers;


import org.itcgae.siga.DTO.fac.FichaMonederoItem;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaMonederosItem;
import org.itcgae.siga.DTO.fac.ListaMovimientosMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosMonederoDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.fac.services.ILineaanticipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LineaanticipoController {

    @Autowired
    private ILineaanticipoService walletService;

    @PostMapping(value = "/pys/getMonederos")
    ResponseEntity<ListaMonederoDTO> getMonederos(HttpServletRequest request, @RequestBody FiltroMonederoItem filtroMonederoItem) {
        ListaMonederoDTO response = walletService.listarMonederos(request, filtroMonederoItem);
        return new ResponseEntity<>(response, response.getError().getCode() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping(value = "/pys/updateMovimientosMonedero")
    ResponseEntity<UpdateResponseDTO> updateMovimientosMonedero(HttpServletRequest request, @RequestBody FichaMonederoItem fichaMonederoItem) throws Exception {

    	UpdateResponseDTO response = walletService.updateMovimientosMonedero(request, fichaMonederoItem);
		if (response.getStatus().equals(SigaConstants.OK)) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/pys/getListaMovimientosMonedero")
    ResponseEntity<ListaMovimientosMonederoDTO> getListaMovimientosMonedero(HttpServletRequest request, String idAnticipo, String idPersona) throws Exception {

    	ListaMovimientosMonederoDTO response = walletService.getListaMovimientosMonedero(request, idAnticipo, idPersona);
		if (response.getError() == null) {
			return new ResponseEntity<ListaMovimientosMonederoDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ListaMovimientosMonederoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/pys/getListaServiciosMonedero")
    ResponseEntity<ListaServiciosMonederoDTO> getListaServiciosMonedero(HttpServletRequest request, String idAnticipo, String idPersona) throws Exception {

    	ListaServiciosMonederoDTO response = walletService.getListaServiciosMonedero(request, idAnticipo, idPersona);
		if (response.getError() == null) {
			return new ResponseEntity<ListaServiciosMonederoDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ListaServiciosMonederoDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping(value = "/pys/updateServiciosMonedero")
    ResponseEntity<UpdateResponseDTO> updateServiciosMonedero(HttpServletRequest request, @RequestBody FichaMonederoItem fichaMonederoItem) throws Exception {

    	UpdateResponseDTO response = walletService.updateServiciosMonedero(request, fichaMonederoItem);
		if (response.getStatus().equals(SigaConstants.OK)) {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/pys/liquidarMonederos")
    ResponseEntity<InsertResponseDTO> liquidarMonederos(HttpServletRequest request, @RequestBody List<ListaMonederosItem> monederos) throws Exception {

    	InsertResponseDTO response = walletService.liquidarMonederos(request, monederos);
		if (response.getStatus().equals(SigaConstants.OK)) {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
