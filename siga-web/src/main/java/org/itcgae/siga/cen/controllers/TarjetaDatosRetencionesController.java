package org.itcgae.siga.cen.controllers;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosRetencionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TarjetaDatosRetencionesController {

    @Autowired
    private ITarjetaDatosRetencionesService tarjetaDatosRetencionesService;

    @RequestMapping(value = "retenciones/tipoRetencion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MaestroRetencionDTO> getRetentionTypes(HttpServletRequest request) {
        MaestroRetencionDTO response = tarjetaDatosRetencionesService.getRetentionTypes(request);
        return new ResponseEntity<MaestroRetencionDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "retenciones/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RetencionesDTO> getRetenciones(@RequestParam("numPagina") int numPagina, @RequestBody PersonaSearchDTO personaSearchDTO, HttpServletRequest request) {
        RetencionesDTO response = tarjetaDatosRetencionesService.getRetenciones(numPagina, personaSearchDTO, request);
        return new ResponseEntity<RetencionesDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "retenciones/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponseDTO> updateRetenciones(@RequestBody List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, @RequestParam("idPersona") String idPersona, HttpServletRequest request) {
        UpdateResponseDTO response = tarjetaDatosRetencionesService.updateRetenciones(etiquetaRetencionesDTO, idPersona, request);
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    /*@RequestMapping(value = "retenciones/searchRetencionColegiado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RetencionesDTO> getRetencionesColegial(@RequestParam("numPagina") int numPagina, @RequestBody PersonaSearchDTO personaSearchDTO, HttpServletRequest request) {
        RetencionesDTO response = tarjetaDatosRetencionesService.getRetencionesColegial(numPagina, personaSearchDTO, request);
        return new ResponseEntity<RetencionesDTO>(response, HttpStatus.OK);
    }*/

    /*@RequestMapping(value = "retenciones/selectRetencionesColegialYSociedades", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RetencionesDTO> selectRetencionesColegialYSociedades(@RequestParam("numPagina") int numPagina, @RequestBody PersonaSearchDTO personaSearchDTO, HttpServletRequest request) {
        RetencionesDTO response = tarjetaDatosRetencionesService.selectRetencionesColegialYSociedades(numPagina, personaSearchDTO, request);
        return new ResponseEntity<RetencionesDTO>(response, HttpStatus.OK);
    }*/
	
	@RequestMapping(value = "retenciones/searchLiquidacionSociedad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RetencionesDTO> getLiquidacionSJCS(@RequestBody PersonaSearchDTO personaSearchDTO, HttpServletRequest request) { 
		RetencionesDTO response = tarjetaDatosRetencionesService.getLiquidacionSJCS(personaSearchDTO, request);
		return new ResponseEntity<RetencionesDTO >(response, HttpStatus.OK);
	}
}
