package org.itcgae.siga.fac.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.BorrarSuscripcionBajaItem;
import org.itcgae.siga.DTO.fac.ComboPreciosSuscripcionDTO;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosDTO;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltroServicioItem;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO2;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.fac.services.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiciosController {
	
	@Autowired 
	private IServiciosService serviciosService;

	//Servicio que devuelve la informacion necesaria para la tabla en Facturacion --> Servicios.
	@PostMapping(value = "/pys/buscarServicios")
	ResponseEntity<ListaServiciosDTO> listadoServicios(HttpServletRequest request, @RequestBody FiltroServicioItem filtroServicioItem) { 
		ListaServiciosDTO response = serviciosService.searchListadoServicios(request, filtroServicioItem);
		return new ResponseEntity<ListaServiciosDTO>(response, HttpStatus.OK);
	}
	
	//Servicio que da de baja logica o lo reactiva (le establece fechabaja en la columna BD a null en caso de reactivar o la actual en caso de baja) al servicio en caso de que tenga usos existentes o lo borra fisicamente (elimina el registro de la bd) en caso de que no tenga ninguna.
	@PostMapping(value="/pys/reactivarBorradoFisicoLogicoServicios")
	ResponseEntity<DeleteResponseDTO> reactivarBorradoFisicoLogicoServicios(@RequestBody ListaServiciosDTO listadoServicios, HttpServletRequest request) {
		DeleteResponseDTO response = new DeleteResponseDTO();
		try {
			 response = serviciosService.reactivarBorradoFisicoLogicoServicios(listadoServicios, request);
			
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Servicio que obtiene una lista de todos los codigos de X colegio (institucion) para evitar que el usuario introduza uno ya existente en el front
	@GetMapping(value = "/pys/obtenerCodigosPorColegioServicios")
	ResponseEntity<ListaCodigosPorColegioDTO> obtenerCodigosPorColegioServicios(HttpServletRequest request) { 
		ListaCodigosPorColegioDTO response = serviciosService.obtenerCodigosPorColegioServicios(request);
		return new ResponseEntity<ListaCodigosPorColegioDTO>(response, HttpStatus.OK);
	}
	
	//Servicio que crea registro en pys_serviciosinstitucion (tarjeta datos generales ficha servicio pantalla servicios en facturacion)
	@PostMapping(value="/pys/nuevoServicio")
	ResponseEntity<InsertResponseDTO> nuevoServicio(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) {
		InsertResponseDTO response = new InsertResponseDTO();
		
		try {
			 response = serviciosService.nuevoServicio(servicio, request);
			
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch(BusinessException e) {
			response.error(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.error(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Servicio que edita el registro en pys_serviciosinstitucion (tarjeta datos generales ficha servicio pantalla servicios en facturacion)
	@PostMapping(value="/pys/editarServicio")
	ResponseEntity<DeleteResponseDTO> editarServicio(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) throws Exception{
		DeleteResponseDTO response = new DeleteResponseDTO();
		try {
			response = serviciosService.editarServicio(servicio, request);

			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		} catch(BusinessException e) {
			response.error(UtilidadesString.creaError(e.getMessage()));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.error(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Trae toda la informacion de las distintas tarjetas de ficha servicio en pantalla servicios en facturacion necesaria al entrar por editar
	@GetMapping(value = "/pys/detalleServicio")
	ResponseEntity<ServicioDetalleDTO> detalleServicio(HttpServletRequest request, @RequestParam int idTipoServicio, @RequestParam int idServicio, @RequestParam int idServiciosInstitucion) { 
		ServicioDetalleDTO response = serviciosService.detalleServicio(request, idTipoServicio, idServicio, idServiciosInstitucion);
		return new ResponseEntity<ServicioDetalleDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/CondicionSuscripcion")
	ResponseEntity<ComboDTO> comboCondicionSuscripcion(HttpServletRequest request) { 
		ComboDTO response = serviciosService.comboCondicionSuscripcion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	//Servicio que guarda informacion tanto en pys_serviciosinstitucion como en pys_formapagoservicios (tarjeta formas de pago ficha servicio pantalla servicios en facturacion)
	@PostMapping(value="/pys/formasPagoServicio")
	ResponseEntity<InsertResponseDTO> crearEditarFormaPago(@RequestBody ServicioDetalleDTO servicio, HttpServletRequest request) {	
		InsertResponseDTO response = new InsertResponseDTO();
		
		try {
			 response = serviciosService.crearEditarFormaPago(servicio, request);
			
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		}catch(Exception e){
			response.error(UtilidadesString.creaError(e.toString()));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	//Servicio que mediante un PL-SQL da de baja las suscripciones al servicio o borra las bajas en determinada fecha.
	@PostMapping(value="/pys/borrarSuscripcionesBajas")
	ResponseEntity<DeleteResponseDTO> borrarSuscripcionesBajas(@RequestBody BorrarSuscripcionBajaItem borrarSuscripcionBajaItem, HttpServletRequest request) {
		DeleteResponseDTO response = serviciosService.borrarSuscripcionesBajas(borrarSuscripcionBajaItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	//Servicio que trae todos los precios de un servicio concreto. (Tabla pys_preciosservicios)
	@PostMapping(value = "/pys/detalleTarjetaPrecios")
	ResponseEntity<FichaTarjetaPreciosDTO> detalleTarjetaPrecios(HttpServletRequest request, @RequestBody ServicioDetalleDTO servicio) { 
		FichaTarjetaPreciosDTO response = serviciosService.detalleTarjetaPrecios(request, servicio);
		return new ResponseEntity<FichaTarjetaPreciosDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/combo/comboPeriodicidad")
	ResponseEntity<ComboDTO> comboPeriodicidad(HttpServletRequest request) { 
		ComboDTO response = serviciosService.comboPeriodicidad(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
//	@GetMapping(value = "/combo/comboPreciosServPers")
//	ResponseEntity<ComboPreciosSuscripcionDTO> comboPreciosServPers(HttpServletRequest request, @RequestParam String idPersona, @RequestParam int idServicio, @RequestParam int idTipoServicios, @RequestParam int idServiciosInstitucion) { 
	@PostMapping(value="/combo/comboPreciosServPers")
	ResponseEntity<ComboPreciosSuscripcionDTO> comboPreciosServPers(HttpServletRequest request, @RequestBody FichaCompraSuscripcionItem peticion) {
	ComboPreciosSuscripcionDTO response = serviciosService.comboPreciosServPers(request, peticion.getIdPersona(), peticion.getServicios().get(0).getIdServicio(), 
			peticion.getServicios().get(0).getIdTipoServicios(), peticion.getServicios().get(0).getIdServiciosInstitucion());
		return new ResponseEntity<ComboPreciosSuscripcionDTO>(response, HttpStatus.OK);
	}
	
	//Crea y edita en la tabla pys_preciosservicios usando la informacion de la tarjeta precios en la ficha servicio pantalla servicios en facturacion
	@PostMapping(value="/pys/crearEditarPrecios")
	ResponseEntity<InsertResponseDTO> crearEditarPrecios(@RequestBody FichaTarjetaPreciosDTO listaPrecios, HttpServletRequest request){
		InsertResponseDTO response = new InsertResponseDTO();
		try {
			response = serviciosService.crearEditarPrecios(listaPrecios, request);
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.error(UtilidadesString.creaError("general.mensaje.error.bbdd"));
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Elimina el/los registro de precios en pys_preciosservicios de un servicio.
	@PostMapping(value="/pys/eliminarPrecios")
	ResponseEntity<DeleteResponseDTO> eliminarPrecio(@RequestBody FichaTarjetaPreciosDTO precios, HttpServletRequest request){
		DeleteResponseDTO response = serviciosService.eliminarPrecio(precios, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
}