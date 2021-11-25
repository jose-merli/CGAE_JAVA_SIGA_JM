package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.fac.services.ITiposServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiposServiciosController {
	
	@Autowired 
	private ITiposServiciosService tiposServiciosService;
	
    //Datos tabla pantalla Maestros --> Tipos Servicios
	@GetMapping(value = "/pys/listadoTipoServicio")
	ResponseEntity<ListadoTipoServicioDTO> listadoTipoServicio(HttpServletRequest request) { 
		ListadoTipoServicioDTO response = tiposServiciosService.searchTiposServicios(request);
		return new ResponseEntity<ListadoTipoServicioDTO>(response, HttpStatus.OK);
	}
	
    //Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Servicios
	@GetMapping(value = "/pys/listadoTipoServicioHistorico")
	ResponseEntity<ListadoTipoServicioDTO> listadoTipoServicioHistorico(HttpServletRequest request) { 
		ListadoTipoServicioDTO response = tiposServiciosService.searchTiposServiciosHistorico(request);
		return new ResponseEntity<ListadoTipoServicioDTO>(response, HttpStatus.OK);
	}
	
    //Obtiene los datos del combo categoria de servicios (PYS_TIPOSERVICIOS)
	@GetMapping(value = "/combo/tipoServicios")
	ResponseEntity<ComboDTO> comboTiposServicios(HttpServletRequest request) { 
		ComboDTO response = tiposServiciosService.comboTiposServicios(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
    //Crea/edita los tipos de servicios (PYS_SERVICIOS)
    @PostMapping(value="/pys/crearEditarServicio")
    ResponseEntity<DeleteResponseDTO> crearEditarServicio(@RequestBody ListadoTipoServicioDTO listadoServicios, HttpServletRequest request){
        DeleteResponseDTO response = new DeleteResponseDTO();
        try {
             response = tiposServiciosService.crearEditarServicio(listadoServicios, request);
            
            return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
        }catch(Exception e){
            response.error(UtilidadesString.creaError(e.toString()));
            return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
	
    //Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que este inhabilitado.
	@PostMapping(value="/pys/activarDesactivarServicio")
	ResponseEntity<ServicioDTO> activarDesactivarServicio(@RequestBody ListadoTipoServicioDTO listadoServicios, HttpServletRequest request){
		ServicioDTO response = tiposServiciosService.activarDesactivarServicio(listadoServicios, request);
		return new ResponseEntity<ServicioDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoServicioByIdCategoria")
	ResponseEntity<ComboDTO> listadoTipoServicioByIdCategoria(HttpServletRequest request, @RequestParam String idCategoria) { 
		ComboDTO response = tiposServiciosService.searchTiposServiciosByIdCategoria(request, idCategoria);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pys/listadoTipoServicioByIdCategoriaMultiple")
	ResponseEntity<ComboDTO> listadoTipoServicioByIdCategoriaMultiple(HttpServletRequest request, @RequestParam String idCategoria) { 
		ComboDTO response = tiposServiciosService.searchTiposServiciosByIdCategoriaMultiple(request, idCategoria);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
}
