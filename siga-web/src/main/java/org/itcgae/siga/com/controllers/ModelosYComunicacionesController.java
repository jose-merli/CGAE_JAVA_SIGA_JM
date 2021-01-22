package org.itcgae.siga.com.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesDTO;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaModeloBorrarDTO;
import org.itcgae.siga.DTOs.com.PlantillasDocumentosDTO;
import org.itcgae.siga.DTOs.com.PlantillasModeloDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.TarjetaModeloConfiguracionDTO;
import org.itcgae.siga.DTOs.com.TarjetaPerfilesDTO;
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
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesSearch(request, filtros, false);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchModelo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModelosComunicacionItem> searchCourse(@RequestBody String idModelo, HttpServletRequest request) {
		ModelosComunicacionItem response = _modelosYcomunicacionesService.modeloYComunicacionesSearchModelo(request, idModelo);
		return new ResponseEntity<ModelosComunicacionItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/historico",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosModelosComunicacionesDTO> modelosComunicacionHistoricoSearch(@RequestParam("numPagina") int numPagina, HttpServletRequest request, @RequestBody DatosModelosComunicacionesSearch filtros) {
		
		DatosModelosComunicacionesDTO response = _modelosYcomunicacionesService.modeloYComunicacionesSearch(request, filtros, true);
		return new ResponseEntity<DatosModelosComunicacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/duplicar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> duplicarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem modelo) {
		
		String response = _modelosYcomunicacionesService.duplicarModeloComunicaciones(request, modelo);

		if(response != null && !response.equals(""))
			return new ResponseEntity<String>(response, HttpStatus.OK);
		else
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
//		if(response.getCode() == 200)
//			return new ResponseEntity<Error>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/borrar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem[] modelo) {
		
		Error response = _modelosYcomunicacionesService.borrarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/rehabilitar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> rehabilitarModelo(HttpServletRequest request, @RequestBody ModelosComunicacionItem[] modelo) {
		
		Error response = _modelosYcomunicacionesService.rehabilitarModeloComunicaciones(request, modelo);

		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@RequestMapping(value = "/detalle/datosGenerales",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> guardarDatosGenerales(HttpServletRequest request, @RequestBody TarjetaModeloConfiguracionDTO datosTarjeta) {
		
		ResponseDataDTO respuesta = _modelosYcomunicacionesService.guardarDatosGenerales(request, datosTarjeta);
		
		if(respuesta.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(respuesta, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/detalle/datosGenerales/comprobarNom",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	Boolean comprobarNombreModeloComunicacion(HttpServletRequest request, @RequestBody TarjetaModeloConfiguracionDTO datosTarjeta) {
		
		Boolean respuesta = _modelosYcomunicacionesService.comprobarNombreModeloComunicacion(request, datosTarjeta);
		
		return respuesta;
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
	
	
	@RequestMapping(value = "/colegiosModelo",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> colegiosPredeterminado(HttpServletRequest request) {
		
		ComboDTO response = _modelosYcomunicacionesService.colegiosModelo(request);
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
	
	@RequestMapping(value = "/detalle/informes/borrar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarInformes(HttpServletRequest request, @RequestBody PlantillaDocumentoBorrarDTO[] plantillasDoc) {
		
		Error response = _modelosYcomunicacionesService.borrarInformes(request, plantillasDoc);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "detalle/plantillasEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasModeloDTO> obtenerPlantillasEnvio(HttpServletRequest request, @RequestBody String idModelo) {
		
		PlantillasModeloDTO response = _modelosYcomunicacionesService.obtenerPlantillasEnviosModeloSearch(request, idModelo);

		if(response.getError() == null)
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "detalle/plantillasEnvioHist",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasModeloDTO> obtenerPlantillasEnvioHist(HttpServletRequest request, @RequestBody String idModelo) {
		
		PlantillasModeloDTO response = _modelosYcomunicacionesService.obtenerPlantillasEnviosModeloSearchHist(request, idModelo);

		if(response.getError() == null)
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/detalle/borrarPlantillaEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> borrarPlantillaEnvio(HttpServletRequest request, @RequestBody PlantillaModeloBorrarDTO[] plantillas) {

		Error response = _modelosYcomunicacionesService.borrarPlantillaEnviosModelo(request, plantillas);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/detalle/plantillasComunicacionByIdClase",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> plantillasComunicacionByIdClase(@RequestParam("idClase") String idClaseComunicacion, HttpServletRequest request) {

		ComboDTO response = _modelosYcomunicacionesService.obtenerPlantillasComunicacion(idClaseComunicacion, request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/detalle/plantillasComunicacion",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPlantillasComunicacion(HttpServletRequest request) {

		ComboDTO response = _modelosYcomunicacionesService.obtenerPlantillasComunicacion(null, request);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/detalle/guardarPlantillaEnvio",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> guardarPlantillaEnvio(HttpServletRequest request, @RequestBody List<PlantillaModeloBorrarDTO> listPlantilla) {

		Error response = _modelosYcomunicacionesService.guardarPlantillaEnviosModelo(request, listPlantilla);
		if(response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value = "/detalle/tipoEnvioPlantilla",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillaEnvioItem> obtenerTipoEnvioPlantilla(HttpServletRequest request, @RequestBody String idPlantilla) {

		PlantillaEnvioItem response = _modelosYcomunicacionesService.obtenerTipoEnvioPlantilla(request, idPlantilla);
		return new ResponseEntity<PlantillaEnvioItem>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/detalle/plantillaDefecto",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PlantillasModeloDTO> obtenerPlantillaPorDefecto(HttpServletRequest request, @RequestBody String idModelo) {

		PlantillasModeloDTO response = _modelosYcomunicacionesService.obtenerPlantillaPorDefecto(request, idModelo);
		if(response.getError() == null)
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<PlantillasModeloDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
