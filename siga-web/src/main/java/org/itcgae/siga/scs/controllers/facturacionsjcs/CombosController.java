package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.scs.services.facturacionsjcs.ICombosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CombosController {

    @Autowired
    private ICombosServices combosServices;

    @RequestMapping(value = "/combo/comboFactEstados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboFactEstados(HttpServletRequest request) {
        ComboDTO response = combosServices.comboFactEstados(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/comboPagoEstados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboPagoEstados(HttpServletRequest request) {
        ComboDTO response = combosServices.comboPagoEstados(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/comboFactConceptos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboFactConceptos(HttpServletRequest request) {
        ComboDTO response = combosServices.comboFactConceptos(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/comboFactColegio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboFactColegio(HttpServletRequest request) {
        ComboDTO response = combosServices.comboFactColegio(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/comboPagosColegio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboPagos(HttpServletRequest request) {
        ComboDTO response = combosServices.comboPagosColegio(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/comboFacturaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboFacturaciones(HttpServletRequest request) {
        ComboDTO response = combosServices.comboFacturaciones(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/combo/colegiosProcuradores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComboDTO> comboColegiosProcuradores(HttpServletRequest request) {
        ComboDTO response = combosServices.comboColegiosProcuradores(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/combo/getComboDestinatarios")
    ResponseEntity<ComboDTO> getComboDestinatarios(HttpServletRequest request) {
        ComboDTO response = combosServices.getComboDestinatarios(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/combo/getComboPagosRetenciones")
    ResponseEntity<ComboDTO> getComboPagosRetenciones(HttpServletRequest request) {
        ComboDTO response = combosServices.getComboPagosRetenciones(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }
    
    @GetMapping("/combo/getComboColegios")
    ResponseEntity<ComboDTO> getComboColegios(HttpServletRequest request) {
        ComboDTO response = combosServices.getComboColegios(request);
        return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
    }
    
    @GetMapping("/combo/grupoFacturacionByColegio")
	public ResponseEntity<ComboDTO> comboGrupoFacturacion(@RequestParam("idColegio")String idColegio,HttpServletRequest request) {
		ComboDTO response = combosServices.getComboGrupoFacturacionByColegio(idColegio,request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value="/combo/comboFactMovimientos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboFactMovimientos(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboFactMovimientos(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value="/combo/comboAplicadoEnPago", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboAplicadoEnPago(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboAplicadoEnPago(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value="/combo/comboAgrupacionEnTurnos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboAgrupacionEnTurnos(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboAgrupacionEnTurnos(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value="/combo/comboTiposMovimientos", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboTiposMovimientos(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboTiposMovimientos(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}
    
    @RequestMapping(value="/combo/comboCertificacionSJCS", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
   	ResponseEntity<ComboDTO> comboCertificacionSJCS(HttpServletRequest request) {
   		ComboDTO response = combosServices.comboCertificacionSJCS(request);
   		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
   	}

}
