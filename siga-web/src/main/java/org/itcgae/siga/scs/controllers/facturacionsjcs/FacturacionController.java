package org.itcgae.siga.scs.controllers.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.FacAbonoDTO;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.FacturacionesAsuntoDTO;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuaciondesigna;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionSJCSServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<FacturacionDeleteDTO>(response, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/facturacionsjcs/archivarFacturacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> archivarFacturacion(@RequestBody List<FacturacionItem> facturacionItems, HttpServletRequest request) {
    	UpdateResponseDTO response = facturacionServices.archivarFacturacion(facturacionItems, request);
        if (response.getStatus().equals("403")) {
            return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
        } else {
        	return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
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
    
    @PostMapping(path = "/facturacionsjcs/getFicheroErroresFacturacion")
    public ResponseEntity<Resource> getFicheroErroresFacturacion(@RequestBody String idFacturacion,  HttpServletRequest request)    {
		ResponseEntity<Resource> response = null;
		Resource resource = null;
		Boolean error = false;

		try {
			resource = facturacionServices.getFicheroErroresFacturacion(idFacturacion, request);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
			response = ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception e) {
			error = true;
		}

		if (error) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}
	
	@RequestMapping(value="/facturacionsjcs/buscarAbonosSJCS", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FacAbonoDTO> buscarAbonosSJCS(@RequestBody FacAbonoItem facAbono, HttpServletRequest request){
		FacAbonoDTO response = facturacionServices.buscarAbonosSJCS(facAbono, request);
		return new ResponseEntity<FacAbonoDTO>(response, HttpStatus.OK);
	}
}
