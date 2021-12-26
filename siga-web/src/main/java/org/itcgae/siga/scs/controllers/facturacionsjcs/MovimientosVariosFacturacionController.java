package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.scs.services.facturacionsjcs.IMovimientosVariosFactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MovimientosVariosFacturacionController {

    @Autowired
    private IMovimientosVariosFactServices movimientosVariosFactServices;

    @RequestMapping(value = "/movimientosVarios/busquedaMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovimientosVariosFacturacionDTO> buscarMovimientosVarios(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) {
        MovimientosVariosFacturacionDTO response = movimientosVariosFactServices.buscarMovimientosVarios(movimientos, request);
        return new ResponseEntity<MovimientosVariosFacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/movimientosVarios/deleteMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DeleteResponseDTO> deleteMovimientosVarios(@RequestBody List<MovimientosVariosFacturacionItem> movimientos, HttpServletRequest request) throws Exception {
        DeleteResponseDTO response = movimientosVariosFactServices.deleteMovimientosVarios(movimientos, request);

        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/movimientosVarios/saveClienteMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> insertMovimiento(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) throws Exception {
        InsertResponseDTO response = movimientosVariosFactServices.saveClienteMovimientosVarios(movimientos, request);

        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/movimientosVarios/updateClienteMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> updateMovimiento(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) throws Exception {
        UpdateResponseDTO response = movimientosVariosFactServices.updateClienteMovimientosVarios(movimientos, request);

        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/movimientosVarios/getListadoPagos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovimientosVariosFacturacionDTO> getListadoPagos(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) {
        MovimientosVariosFacturacionDTO response = movimientosVariosFactServices.getListadoPagos(movimientos, request);
        return new ResponseEntity<MovimientosVariosFacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/movimientosVarios/saveMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> saveMovimientosVarios(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) throws Exception {
        InsertResponseDTO response = movimientosVariosFactServices.saveMovimientosVarios(movimientos, request);

        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/movimientosVarios/updateMovimientosVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> updateMovimientosVarios(@RequestBody MovimientosVariosFacturacionItem movimientos, HttpServletRequest request) throws Exception {
        UpdateResponseDTO response = movimientosVariosFactServices.updateMovimientosVarios(movimientos, request);

        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/movimientosVarios/getMovimientoVarioPorId")
    ResponseEntity<MovimientosVariosFacturacionDTO> getMovimientoVarioPorId(@RequestParam("idMovimiento") String idMovimiento, HttpServletRequest request) {
        MovimientosVariosFacturacionDTO response = movimientosVariosFactServices.getMovimientoVarioPorId(idMovimiento, request);
        return new ResponseEntity<MovimientosVariosFacturacionDTO>(response, HttpStatus.OK);
    }

}
