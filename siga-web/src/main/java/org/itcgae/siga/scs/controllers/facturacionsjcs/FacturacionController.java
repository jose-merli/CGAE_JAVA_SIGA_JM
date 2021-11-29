package org.itcgae.siga.scs.controllers.facturacionsjcs;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FacturacionController {

    @Autowired
    private IFacturacionSJCSServices facturacionServices;

    @RequestMapping(value = "/facturacionsjcs/buscarfacturaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDTO> buscarFacturaciones(@RequestBody FacturacionItem facturacionItem,
                                                       HttpServletRequest request) {
        FacturacionDTO response = facturacionServices.buscarFacturaciones(facturacionItem, request);
        return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/eliminarFacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDeleteDTO> eliminarFacturaciones(@RequestBody FacturacionItem facturacionItem,
                                                               HttpServletRequest request) {
        FacturacionDeleteDTO response = facturacionServices.eliminarFacturaciones(facturacionItem, request);
        if (response.getStatus() == SigaConstants.OK) {
            return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/datosfacturacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDTO> datosfacturacion(@RequestParam("idFacturacion") String idFacturacion,
                                                    HttpServletRequest request) {
        FacturacionDTO response = facturacionServices.datosFacturacion(idFacturacion, request);
        return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/historicofacturacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDTO> historicoFacturacion(@RequestParam("idFacturacion") String idFacturacion,
                                                        HttpServletRequest request) {
        FacturacionDTO response = facturacionServices.historicoFacturacion(idFacturacion, request);
        return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/numApuntes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StringDTO> numApuntes(@RequestParam("idFacturacion") String idFacturacion,
                                         HttpServletRequest request) {
        StringDTO response = facturacionServices.numApuntes(idFacturacion, request);
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/saveFacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> saveFacturacion(@RequestBody FacturacionItem facturacionItem,
                                                      HttpServletRequest request) {
        InsertResponseDTO response = facturacionServices.saveFacturacion(facturacionItem, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/updateFacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> updateFacturacion(@RequestBody FacturacionItem facturacionItem,
                                                        HttpServletRequest request) {
        UpdateResponseDTO response = facturacionServices.updateFacturacion(facturacionItem, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/ejecutarfacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> ejecutarFacturacion(@RequestBody String idFacturacion,
                                                          HttpServletRequest request) {
        InsertResponseDTO response = facturacionServices.ejecutarFacturacion(idFacturacion, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/reabrirfacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> reabrirFacturacion(@RequestBody String idFacturacion,
                                                         HttpServletRequest request) {
        InsertResponseDTO response = facturacionServices.reabrirFacturacion(idFacturacion, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/simularfacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> simularFacturacion(@RequestBody String idFacturacion,
                                                         HttpServletRequest request) {
        InsertResponseDTO response = facturacionServices.simularFacturacion(idFacturacion, request);
        if (response.getError().getCode() == 200) {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/facturacionsjcs/tarjetaConceptosfac", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FacturacionDTO> conceptosFacturacion(@RequestParam("idFacturacion") String idFacturacion,
                                                        HttpServletRequest request) {
        FacturacionDTO response = facturacionServices.conceptosFacturacion(idFacturacion, request);
        return new ResponseEntity<FacturacionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/saveConceptosFac", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsertResponseDTO> saveConceptosFac(@RequestBody List<FacturacionItem> listaFacturacionItem,
                                                       HttpServletRequest request) {
        InsertResponseDTO response = facturacionServices.saveConceptosFac(listaFacturacionItem, request);
        return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/updateConceptosFac", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> updateConceptosFac(@RequestBody List<FacturacionItem> listaFacturacionItem,
                                                         HttpServletRequest request) {
        UpdateResponseDTO response = facturacionServices.updateConceptosFac(listaFacturacionItem, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/deleteConceptosFac", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DeleteResponseDTO> deleteConceptosFac(@RequestBody List<FacturacionItem> facturacionDto,
                                                         HttpServletRequest request) {
        DeleteResponseDTO response = facturacionServices.deleteConceptosFac(facturacionDto, request);
        return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/facturacionsjcs/datospagos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PagosjgDTO> datosPagos(@RequestParam("idFacturacion") String idFacturacion,
                                          HttpServletRequest request) {
        PagosjgDTO response = facturacionServices.datosPagos(idFacturacion, request);
        return new ResponseEntity<PagosjgDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/facturacionsjcs/getFacturacionesPorAsuntoActuacionDesigna")
    ResponseEntity<FacturacionesAsuntoDTO> getFacturacionesPorAsuntoActuacionDesigna(@RequestBody ScsActuaciondesigna scsActuaciondesigna, HttpServletRequest request) {
        FacturacionesAsuntoDTO response = facturacionServices.getFacturacionesPorAsuntoActuacionDesigna(scsActuaciondesigna, request);
        return new ResponseEntity<FacturacionesAsuntoDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/facturacionsjcs/getFacturacionesPorAsuntoAsistencia")
    ResponseEntity<FacturacionesAsuntoDTO> getFacturacionesPorAsuntoAsistencia(@RequestBody ScsAsistencia scsAsistencia, HttpServletRequest request) {
        FacturacionesAsuntoDTO response = facturacionServices.getFacturacionesPorAsuntoAsistencia(scsAsistencia, request);
        return new ResponseEntity<FacturacionesAsuntoDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/facturacionsjcs/getFacturacionesPorAsuntoActuacionAsistencia")
    ResponseEntity<FacturacionesAsuntoDTO> getFacturacionesPorAsuntoActuacionAsistencia(@RequestBody ScsActuacionasistencia scsActuacionasistencia, HttpServletRequest request) {
        FacturacionesAsuntoDTO response = facturacionServices.getFacturacionesPorAsuntoActuacionAsistencia(scsActuacionasistencia, request);
        return new ResponseEntity<FacturacionesAsuntoDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/facturacionsjcs/getFacturacionesPorGuardia")
    ResponseEntity<FacturacionesAsuntoDTO> getFacturacionesPorGuardia(@RequestBody ScsCabeceraguardias scsCabeceraguardias, HttpServletRequest request) {
        FacturacionesAsuntoDTO response = facturacionServices.getFacturacionesPorGuardia(scsCabeceraguardias, request);
        return new ResponseEntity<FacturacionesAsuntoDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/facturacionsjcs/getFacturacionesPorEJG")
    ResponseEntity<FacturacionesAsuntoDTO> getFacturacionesPorEJG(@RequestBody ScsEjg scsEjg, HttpServletRequest request) {
        FacturacionesAsuntoDTO response = facturacionServices.getFacturacionesPorEJG(scsEjg, request);
        return new ResponseEntity<FacturacionesAsuntoDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/facturacionsjcs/getAgrupacionDeTurnosPorTurno")
    ResponseEntity<StringDTO> getAgrupacionDeTurnosPorTurno(@RequestParam String idTurno, HttpServletRequest request) {
        StringDTO response = facturacionServices.getAgrupacionDeTurnosPorTurno(idTurno, request);
        return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
    }
}
