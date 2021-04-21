package org.itcgae.siga.scs.controllers.oficio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
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

	/**
	 * busquedaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/busquedaJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<JustificacionExpressItem>> busquedaJustificacionExpres(
			@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		List<JustificacionExpressItem> response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<List<JustificacionExpressItem>>(response, HttpStatus.OK);
	}
	
	/**
	 * insertaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertaJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevaJustificacionExpres(@RequestBody List<ActuacionesJustificacionExpressItem> item, HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.insertaJustificacionExpres(item, request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}

	/**
	 * actualizaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/actualizaJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizaJustificacionesExpres(@RequestBody List<ActuacionesJustificacionExpressItem> item, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.actualizaJustificacionExpres(item, request);
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	/**
	 * eliminaJustificacionExpres
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/eliminaJustificacionExpres", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminaJustificacionExpres(@RequestBody List<ActuacionesJustificacionExpressItem> item, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminaJustificacionExpres(item, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDesignaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/busquedaProcedimientoDesignas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaProcedimientoDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaProcedimientoDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/busquedaModuloDesignas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaModuloDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaModuloDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	// 3.3.6.2.4. Tarjeta Detalle Designacion
	
	@RequestMapping(value = "/comboProcedimientosConJuzgado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientosConJuzgado(HttpServletRequest request, @RequestBody String idJuzgado) {

		ComboDTO response = comboService.comboProcedimientosConJuzgado(request, idJuzgado);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/comboModulosConJuzgado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulosConJuzgado(HttpServletRequest request, @RequestBody String idJuzgado) {

		ComboDTO response = comboService.comboModulosConJuzgado(request, idJuzgado);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/comboModulosConProcedimientos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulosConProcedimientos(HttpServletRequest request, @RequestBody String idPretension) {

		ComboDTO response = comboService.comboModulosConProcedimientos(request, idPretension);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/comboProcedimientosConModulo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientosConModulo(HttpServletRequest request,@RequestBody String idModulo) {

		ComboDTO response = comboService.comboProcedimientosConModulo(request, idModulo);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 3.3.6.2.4. Tarjeta Datos Adicionales

	@RequestMapping(value = "/designas/getDatosAdicionales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> getDatosAdicionales(HttpServletRequest request, @RequestBody DesignaItem designaItem) {
		List<DesignaItem> response = designacionesService.getDatosAdicionales(designaItem ,request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
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
	
	// 3.3.6.2.6.	Tarjeta Interesados
	
	@RequestMapping(value = "/designas/busquedaListaInteresados",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaInteresadoJusticiableItem>> busquedaListaInteresados(@RequestBody String[] item, HttpServletRequest request) {
		DesignaItem designa = new DesignaItem();
		String ano = item[3].substring(1,5);
		designa.setAno(Integer.parseInt(ano));
		designa.setNombreTurno(item[1]);
		designa.setIdTurno(Integer.parseInt(item[0]));
		designa.setNumero(Integer.parseInt(item[2]));
		List<ListaInteresadoJusticiableItem> response = designacionesService.busquedaListaInteresados(designa, request);
		if(response != null) {
			return new ResponseEntity<List<ListaInteresadoJusticiableItem>>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<ListaInteresadoJusticiableItem>>(new ArrayList<ListaInteresadoJusticiableItem>(), HttpStatus.OK);
		}
	}
	
	// [ idInstitucion,  idPersona, this.selectedDatos.anio,  this.selectedDatos.idTurno, this.selectedDatos.numero]
		@RequestMapping(value = "/designas/deleteInteresado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<DeleteResponseDTO> deleteInteresado(@RequestBody String[] item, HttpServletRequest request) {
			ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();
			interesado.setIdinstitucion(Short.parseShort(item[0]));
			interesado.setIdpersona(Long.parseLong(item[1]));
			interesado.setAnio(Short.parseShort(item[2]));
			interesado.setIdturno(Integer.parseInt(item[3]));
			interesado.setNumero(Long.parseLong(item[4]));
			DeleteResponseDTO response = designacionesService.deleteInteresado(interesado, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		//[ designa.idInstitucion,  justiciable.idPersona, designa.anio,  designa.idTurno, designa.numero]
		@RequestMapping(value = "/designas/insertInteresado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<InsertResponseDTO> insertInteresado(@RequestBody String[] item, HttpServletRequest request) {
			String anio = item[2].substring(1,5);
			ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();
			interesado.setIdinstitucion(Short.parseShort(item[0]));
			interesado.setIdpersona(Long.parseLong(item[1]));
			interesado.setAnio(Short.parseShort(anio));
			interesado.setIdturno(Integer.parseInt(item[3]));
			interesado.setNumero(Long.parseLong(item[4]));
			InsertResponseDTO response = designacionesService.insertInteresado(interesado, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	// 3.3.6.2.6.3.	Ficha detalle del interesado 

		//[ designa.idInstitucion,  justiciable.idPersona, designa.anio,  designa.idTurno, designa.numero, representante]
		@RequestMapping(value = "/designas/updateRepresentanteInteresado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateRepresentanteInteresado(@RequestBody String[] item, HttpServletRequest request) {
			String anio = item[2].substring(1,5);
			ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();
			interesado.setIdinstitucion(Short.parseShort(item[0]));
			interesado.setIdpersona(Long.parseLong(item[1]));
			interesado.setAnio(Short.parseShort(anio));
			interesado.setIdturno(Integer.parseInt(item[3]));
			interesado.setNumero(Long.parseLong(item[4]));
			interesado.setNombrerepresentante(item[5]);
			UpdateResponseDTO response = designacionesService.updateRepresentanteInteresado(interesado, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	// 3.3.6.2.7.	Tarjeta Contrarios
	
	// [designaItem.idTurno, nombreTurno, numero, anio, historico]
		@RequestMapping(value = "/designas/busquedaListaContrarios",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<List<ListaContrarioJusticiableItem>> busquedaListaContrarios(@RequestBody String[] item, HttpServletRequest request) {
			DesignaItem designa = new DesignaItem();
			String ano = item[3].substring(1,5);
			designa.setAno(Integer.parseInt(ano));
			designa.setNombreTurno(item[1]);
			designa.setIdTurno(Integer.parseInt(item[0]));
			designa.setNumero(Integer.parseInt(item[2]));
			List<ListaContrarioJusticiableItem> response = designacionesService.busquedaListaContrarios(designa, request, Boolean.parseBoolean(item[4]));
			if(response != null) {
				return new ResponseEntity<List<ListaContrarioJusticiableItem>>(response, HttpStatus.OK);
			}else {
				return new ResponseEntity<List<ListaContrarioJusticiableItem>>(new ArrayList<ListaContrarioJusticiableItem>(), HttpStatus.OK);
			}
		}

		// [ idInstitucion,  idPersona, this.selectedDatos.anio,  this.selectedDatos.idTurno, this.selectedDatos.numero]
		@RequestMapping(value = "/designas/deleteContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> deleteContrario(@RequestBody String[] item, HttpServletRequest request) {
			ScsContrariosdesigna contrario = new ScsContrariosdesigna();
			contrario.setIdinstitucion(Short.parseShort(item[0]));
			contrario.setIdpersona(Long.parseLong(item[1]));
			contrario.setAnio(Short.parseShort(item[2]));
			contrario.setIdturno(Integer.parseInt(item[3]));
			contrario.setNumero(Long.parseLong(item[4]));
			UpdateResponseDTO response = designacionesService.deleteContrario(contrario, request);
//			UpdateResponseDTO response = designacionesService.deleteContrario(item, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		//[ designa.idInstitucion,  justiciable.idPersona, designa.anio,  designa.idTurno, designa.numero]
		@RequestMapping(value = "/designas/insertContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<InsertResponseDTO> insertContrario(@RequestBody String[] item, HttpServletRequest request) {
			String anio = item[2].substring(1,5);
			ScsContrariosdesigna contrario = new ScsContrariosdesigna();
			contrario.setIdinstitucion(Short.parseShort(item[0]));
			contrario.setIdpersona(Long.parseLong(item[1]));
			contrario.setAnio(Short.parseShort(anio));
			contrario.setIdturno(Integer.parseInt(item[3]));
			contrario.setNumero(Long.parseLong(item[4]));
			InsertResponseDTO response = designacionesService.insertContrario(contrario, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	// 3.3.6.2.7.3.	Ficha detalle del contrario
	
	// 3.3.6.2.8.	Tarjeta Procurador

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
	public ResponseEntity<ActuacionDesignaDTO> busquedaActDesigna(
			@RequestBody ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, HttpServletRequest request) {
		ActuacionDesignaDTO response = designacionesService.busquedaActDesigna(actuacionDesignaRequestDTO, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getNewIdActuDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MaxIdDto> getNewIdActuDesigna(
			@RequestBody ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, HttpServletRequest request) {
		MaxIdDto response = designacionesService.getNewIdActuDesigna(actuacionDesignaRequestDTO, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/reactivarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> reactivarActDesigna(
			@RequestBody List<ActuacionDesignaItem> listaActuacionDesignaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.anularReactivarActDesigna(listaActuacionDesignaItem, false,
				request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/anularActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> anularActDesigna(
			@RequestBody List<ActuacionDesignaItem> listaActuacionDesignaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.anularReactivarActDesigna(listaActuacionDesignaItem, true,
				request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/eliminarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> eliminarActDesigna(
			@RequestBody List<ActuacionDesignaItem> listaActuacionDesignaItem, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminarActDesigna(listaActuacionDesignaItem, request);
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
	
	@RequestMapping(value = "/designas/guardarProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarProcurador(@RequestBody List<ProcuradorItem> procuradorItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.guardarProcurador(procuradorItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/nuevoProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevoProcurador(@RequestBody ProcuradorItem procuradorItem, HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.nuevoProcurador(procuradorItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	@GetMapping("/comboPrisiones")
	public ResponseEntity<ComboDTO> comboPrisiones(HttpServletRequest request) {
		ComboDTO response = designacionesService.comboPrisiones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
