package org.itcgae.siga.com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.adm.service.impl.PerfilServiceImpl;
import org.itcgae.siga.com.services.IModelosYcomunicacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/modelos")
public class ModelosYComunicacionesController {
	
	@Autowired
	IModelosYcomunicacionesService _modelosYcomunicacionesService;
	
	@Autowired
	PerfilServiceImpl perfilServiceImpl;	
	
	@RequestMapping(value = "/search",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosModelosComunicacionesDTO> modelosComunicacionSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody DatosModelosComunicacionesSearch filtros) {
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesSearch(request, filtros);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/historico",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosModelosComunicacionesDTO> modelosComunicacionHistoricoSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody DatosModelosComunicacionesSearch filtros) {
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesHistoricoSearch(request, filtros);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/duplicar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> duplicarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		Error response = _modelosYcomunicacionesService.duplicarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/borrar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		Error response = _modelosYcomunicacionesService.borrarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@RequestMapping(value = "/detalle/datosGenerales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarDatosGenerales(HttpServletRequest request, @RequestBody TarjetaModeloConfiguracionDTO datosTarjeta) {
		
		Error respuesta = _modelosYcomunicacionesService.guardarDatosGenerales(request, datosTarjeta);
		
		if(respuesta.getCode()== 200)
			return new ResponseEntity<Error>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/detalle/perfiles",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> perfiles(HttpServletRequest request) {
		
		ComboDTO response = perfilServiceImpl.getPerfiles(request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/perfilesModelo",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerEtiquetasEnvio(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		ComboDTO response = _modelosYcomunicacionesService.obtenerPerfilesModelo(request, modelo.getIdInstitucion(), modelo.getIdModeloComunicacion());
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/guardarPerfiles",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarPerfilesModelo(HttpServletRequest request, @RequestBody TarjetaPerfilesDTO tarjetaPerfiles) {

		Error response = _modelosYcomunicacionesService.guardarPerfilesModelo(request, tarjetaPerfiles);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/informes",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasDocumentosDTO> obtenerInformes(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		PlantillasDocumentosDTO response = _modelosYcomunicacionesService.obtenerInformes(request, modelo.getIdInstitucion(), modelo.getIdModeloComunicacion());
		return new ResponseEntity<PlantillasDocumentosDTO>(response, HttpStatus.OK);
	}
	
	//TODO
	@RequestMapping(value = "/detalle/guardarInformes",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasDocumentosDTO> guardarInformes(HttpServletRequest request, @RequestBody String idModeloComuncacion, @RequestBody String idInstitucion) {
		
		PlantillasDocumentosDTO response = _modelosYcomunicacionesService.obtenerInformes(request, idModeloComuncacion, idInstitucion);
		return new ResponseEntity<PlantillasDocumentosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/combo/consultas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerComboConsultas(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ComboDTO response = _modelosYcomunicacionesService.obtenerConsultasDisponibles(request, plantillaDoc);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/plantilla/consultas",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultas(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ConsultasDTO response = _modelosYcomunicacionesService.obtenerConsultasPlantilla(request, plantillaDoc, false);
		return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/plantilla/consultas/historico",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerConsultasHistorico(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		ConsultasDTO response = _modelosYcomunicacionesService.obtenerConsultasPlantilla(request, plantillaDoc, true);
		return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/guardarPlantillaDoc",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarPlantillaDocumento(HttpServletRequest request, @RequestBody TarjetaPlantillaDocumentoDTO plantillaDoc) {
		
		Error response = _modelosYcomunicacionesService.guardarPlantillaDocumento(request, plantillaDoc);
		return new ResponseEntity<Error>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/plantilla/formatos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerFormatoSalida(HttpServletRequest request) {
		
		ComboDTO response = _modelosYcomunicacionesService.obtenerFormatoSalida(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/plantilla/sufijos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> obtenerSufijos(HttpServletRequest request) {
		
		ComboDTO response = _modelosYcomunicacionesService.obtenerSufijos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
