package org.itcgae.siga.scs.controllers.guardia;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.scs.services.guardia.ActuacionAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/actuacionAsistencia")
public class ActuacionAsistenciaController {

    @Autowired
    private ActuacionAsistenciaService actuacionAsistenciaService;


    @GetMapping(value = "/searchTarjetaActuacion")
    public ResponseEntity<ActuacionAsistenciaDTO> searchTarjetaActuacion(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        ActuacionAsistenciaDTO response = null;
        try {
            response = actuacionAsistenciaService.searchTarjetaActuacion(request, anioNumero, idActuacion);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<ActuacionAsistenciaDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/searchTarjetaDatosGenerales")
    public ResponseEntity<DatosGeneralesActuacionAsistenciaDTO> searchTarjetaDatosGenerales(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        DatosGeneralesActuacionAsistenciaDTO response = null;
        try {
            response = actuacionAsistenciaService.searchTarjetaDatosGenerales(request, anioNumero, idActuacion);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<DatosGeneralesActuacionAsistenciaDTO>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/recuperaHitoNueve")
    public ResponseEntity<Boolean> searchHitoNueve(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String idInstitucion) {
        boolean response = false;
        try {
            response = actuacionAsistenciaService.searchHitoNueve(request, anioNumero, idInstitucion);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/saveDatosGenerales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> saveDatosGenerales(HttpServletRequest request, @RequestBody DatosGeneralesActuacionAsistenciaItem datosGenerales, @RequestParam String anioNumero) {
        UpdateResponseDTO response = null;
        try {
            response = actuacionAsistenciaService.saveDatosGenerales(request, datosGenerales, anioNumero);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/searchTarjetaJustificacion")
    public ResponseEntity<TarjetaJustificacionActuacionAsistenciaDTO> searchTarjetaJustificacion(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        TarjetaJustificacionActuacionAsistenciaDTO response = null;
        try {
            response = actuacionAsistenciaService.searchTarjetaJustificacion(request,anioNumero,idActuacion);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<TarjetaJustificacionActuacionAsistenciaDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/saveTarjetaJustificacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> saveTarjetaJustificacion(HttpServletRequest request, @RequestBody TarjetaJustificacionActuacionAsistenciaItem tarjeta, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        UpdateResponseDTO response = null;
        try {
            response = actuacionAsistenciaService.saveTarjetaJustificacion(request, anioNumero, idActuacion, tarjeta);
        }catch(Exception e) {
            throw e;
        }
        return new  ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/updateEstadoActuacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateResponseDTO> updateEstadoActuacion(HttpServletRequest request, @RequestBody TarjetaJustificacionActuacionAsistenciaItem tarjeta, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        UpdateResponseDTO response = null;
        try {
            response = actuacionAsistenciaService.updateEstadoActuacion(request, anioNumero, idActuacion, tarjeta);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/searchHistorico")
    public ResponseEntity<HistoricoActuacionAsistenciaDTO> searchHistorico(HttpServletRequest request, @RequestParam String anioNumero, @RequestParam String idActuacion) {
        HistoricoActuacionAsistenciaDTO response = null;
        try {
            response = actuacionAsistenciaService.searchHistorico(request, anioNumero, idActuacion);
        }catch(Exception e) {
            throw e;
        }
        return new ResponseEntity<HistoricoActuacionAsistenciaDTO>(response, HttpStatus.OK);
    }

}
