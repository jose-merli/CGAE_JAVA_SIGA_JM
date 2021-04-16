package org.itcgae.siga.scs.controllers.oficio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
//import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oficio")
public class DesignacionesController {

	@Autowired
	private ComboService comboService;

	@Autowired
	private IDesignacionesService designacionesService;

	// Busqueda designaciones

	@RequestMapping(value = "/designas/comboTipoDesigna", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/busquedaJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<JustificacionExpressItem>> busquedaJustificacionExpres(
			@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		List<JustificacionExpressItem> response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<List<JustificacionExpressItem>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/busquedaDesignaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/busquedaListaContrarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaContrarioJusticiableItem>> busquedaListaContrarios(@RequestBody DesignaItem item,
			HttpServletRequest request) {
		List<ListaContrarioJusticiableItem> response = designacionesService.busquedaListaContrarios(item, request);
		if (response != null) {
			return new ResponseEntity<List<ListaContrarioJusticiableItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ListaContrarioJusticiableItem>>(
					new ArrayList<ListaContrarioJusticiableItem>(), HttpStatus.OK);
		}
	}

	// FIN Busqueda designaciones

	// 3.3.6.2.2. Tarjeta Datos Generales

	@RequestMapping(value = "/designas/createnewDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createDesigna(@RequestBody DesignaItem designaItem, HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.createDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 3.3.6.2.3. Tarjeta Detalle Designación

	@RequestMapping(value = "/designas/updateDetalleDesignacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDetalleDesignacion(@RequestBody DesignaItem designaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDetalleDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

//	@RequestMapping(value = "/designas/comboEstadoDesignacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<ComboDTO> comboEstadoDesignacion(HttpServletRequest request) {
//
//		ComboDTO response = new ComboDTO(); //TODO
//		if (response.getError() == null)
//			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
//		else
//			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@RequestMapping(value = "/designas/comboJuzgado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgado(HttpServletRequest request) {

		ComboDTO response = comboService.comboJuzgadoDesignaciones(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/comboProcedimientos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientos(HttpServletRequest request) {

		ComboDTO response = comboService.comboProcedimientos(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/comboModulo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {

		ComboDTO response = comboService.comboModulo(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/designas/comboDelitos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDelitos(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); // TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 3.3.6.2.4. Tarjeta Datos Adicionales

	@RequestMapping(value = "/designas/updateDatosAdicionales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosAdicionales(@RequestBody DesignaItem designaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDatosAdicionales(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// 3.3.6.2.5. Tarjeta Datos Facturación

	@RequestMapping(value = "/designas/comboPartidaPresupuestaria", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPartidaPresupuestaria(HttpServletRequest request) {

		ComboDTO response = comboService.getComboPartidasPresupuestarias(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 3.3.6.2.6. Tarjeta Interesados

	// 3.3.6.2.6.3. Ficha detalle del interesado

	// 3.3.6.2.7. Tarjeta Contrarios

	// 3.3.6.2.7.3. Ficha detalle del contrario

	// 3.3.6.2.8. Tarjeta Procurador

	@RequestMapping(value = "/designas/comboMotivo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboMotivo(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); // TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 3.3.6.2.9. Tarjeta Letrados de la designación

	// 3.3.6.2.9.3. Ficha cambio del letrado designado

	// 3.3.6.2.10. Tarjeta Relaciones

	// 3.3.6.2.12. Tarjeta Comunicaciones

	// 3.3.6.2.13. Tarjeta Documentación

	@RequestMapping(value = "/designas/comboAsociado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAsociado(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); // TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/designas/comboTipoDocumentacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoDocumentacion(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); // TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 3.3.6.2.14. Tarjeta actuaciones

	@PostMapping(value = "/designas/busquedaActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActuacionDesignaDTO> busquedaActDesigna(DesignaItem designaItem, HttpServletRequest request) {
		ActuacionDesignaDTO response = designacionesService.busquedaActDesigna(designaItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.3.6.2.14.3. Ficha Actuación
	
	// 3.3.6.2.2. Ficha Procurador

	@RequestMapping(value = "/designas/busquedaProcurador",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> busquedaProcurador(@RequestBody List<String> procurador, HttpServletRequest request) {
		ProcuradorDTO response = designacionesService.busquedaProcurador(procurador, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/designas/comboTipoMotivo")
	public ResponseEntity<ComboDTO> comboTipoMotivo(HttpServletRequest request) {
		ComboDTO response = designacionesService.comboTipoMotivo(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
