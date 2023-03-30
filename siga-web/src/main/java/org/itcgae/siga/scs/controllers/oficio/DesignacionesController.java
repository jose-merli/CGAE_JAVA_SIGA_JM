package org.itcgae.siga.scs.controllers.oficio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.ComunicacionesDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.GuardaJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoDesignaDTO;
import org.itcgae.siga.DTOs.scs.ListDTO;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	ResponseEntity<InsertResponseDTO> nuevaJustificacionExpres(
			@RequestBody ActuacionesJustificacionExpressItem item, HttpServletRequest request) {
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
	ResponseEntity<UpdateResponseDTO> actualizaJustificacionesExpres(
			@RequestBody GuardaJustificacionExpressItem item, HttpServletRequest request) {
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
	ResponseEntity<DeleteResponseDTO> eliminaJustificacionExpres(
			@RequestBody List<ActuacionesJustificacionExpressItem> item, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminaJustificacionExpres(item, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/busquedaDesignaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaDesignas(item, request);
//		if(response == null || response.isEmpty()) {
//			response = designacionesService.busquedaNuevaDesigna(item, request);
//		}
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/busquedaNuevaDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaNuevaDesigna(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaNuevaDesigna(item, request, false);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/busquedaProcedimientoDesignas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaProcedimientoDesignas(@RequestBody DesignaItem item,
			HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaProcedimientoDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/busquedaModuloDesignas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaModuloDesignas(@RequestBody DesignaItem item,
			HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaModuloDesignas(item, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/busquedaJuzgadoDesignas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> busquedaJuzgadoDesignas(@RequestBody Integer idJuzgado,
			HttpServletRequest request) {
		String response = designacionesService.busquedaJuzgadoDesignas(idJuzgado, request);
		if (response != null) {
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(new String(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	// FIN Busqueda designaciones

	// 3.3.6.2.2. Tarjeta Datos Generales

	@RequestMapping(value = "/designas/createnewDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> createDesigna(@RequestBody DesignaItem designaItem, HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.createDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else if (response.getError().getCode() == 202)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.ACCEPTED);
		else if (response.getError().getCode() == 406)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.NOT_ACCEPTABLE);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/designas/updateDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDesigna(@RequestBody DesignaItem designaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else if (response.getError().getCode() == 400)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.BAD_REQUEST);
		else if (response.getError().getCode() == 406)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.NOT_ACCEPTABLE);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/designas/deleteDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> deleteDesigna(@RequestBody List<DesignaItem> item, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.deleteDesigna(item, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// 3.3.6.2.3. Tarjeta Detalle Designación

	@RequestMapping(value = "/designas/updateDetalleDesignacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDetalleDesignacion(@RequestBody DesignaItem designaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDetalleDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else if (response.getError().getCode() == 400)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.BAD_REQUEST);
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

	@RequestMapping(value = "/designas/comboJuzgado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgado(HttpServletRequest request,String idJuzgado) {

		ComboDTO response = comboService.comboJuzgadoDesignaciones(request,idJuzgado);
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
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request, ActuacionDesignaItem item) {
		ComboDTO response = comboService.comboModulo(request,item);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/designas/comboDelitos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDelitos(@RequestBody DesignaItem designaItem, HttpServletRequest request) {

		ComboDTO response = comboService.comboDelitos(designaItem, request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	// 3.3.6.2.4. Tarjeta Detalle Designacion

	@RequestMapping(value = "/comboProcedimientosConJuzgado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientosConJuzgado(HttpServletRequest request, @RequestBody String idJuzgado) {

		ComboDTO response = comboService.comboProcedimientosConJuzgado(request, idJuzgado, null);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/comboModulosConJuzgado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulosConJuzgado(HttpServletRequest request, String idJuzgado, String fecha) {
		ComboDTO response = comboService.comboModulosConJuzgado(request, idJuzgado,fecha);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/comboAllModulos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAllModulos(HttpServletRequest request) {

		ComboDTO response = comboService.comboAllModulos(request);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/comboModulosConProcedimientos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulosConProcedimientos(HttpServletRequest request,
			 String idPretension, String fecha) {
		ComboDTO response = comboService.comboModulosConProcedimientos(request, idPretension,fecha);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/comboProcedimientosConModulo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimientosConModulo(HttpServletRequest request, @RequestBody String idModulo) {

		ComboDTO response = comboService.comboProcedimientosConModulo(request, idModulo);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/designas/existeDesginaJuzgadoProcedimiento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DesignaItem> existeDesginaJuzgadoProcedimiento(HttpServletRequest request,
			@RequestBody DesignaItem designaItem) {
		DesignaItem response = designacionesService.existeDesginaJuzgadoProcedimiento(designaItem, request);
		if (response != null) {
			return new ResponseEntity<DesignaItem>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<DesignaItem>(new DesignaItem(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	// 3.3.6.2.4. Tarjeta Datos Adicionales

	@RequestMapping(value = "/designas/getDatosAdicionales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> getDatosAdicionales(HttpServletRequest request,
			@RequestBody DesignaItem designaItem) {
		List<DesignaItem> response = designacionesService.getDatosAdicionales(designaItem, request);
		if (response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(),
					HttpStatus.INTERNAL_SERVER_ERROR);
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
	ResponseEntity<ComboDTO> comboPartidaPresupuestaria(HttpServletRequest request, String importe) {

		ComboDTO response = comboService.getComboPartidasPresupuestarias(request, importe);
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/getPartidaPresupuestariaDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getPartidaPresupuestariaDesigna(HttpServletRequest request, @RequestBody DesignaItem designaItem) {
		ComboDTO response = designacionesService.getPartidaPresupuestariaDesigna(request, designaItem);
			if (response.getError() == null)
				return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		

	}
	
	@RequestMapping(value = "/designas/updatePartidaPresupuestaria", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updatePartidaPresupuestaria(@RequestBody DesignaItem designaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updatePartidaPresupuestaria(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// 3.3.6.2.6. Tarjeta Interesados

	@RequestMapping(value = "/designas/busquedaListaInteresados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaInteresadoJusticiableItem>> busquedaListaInteresados(@RequestBody String[] item,
			HttpServletRequest request) {
		DesignaItem designa = new DesignaItem();
		String ano = item[3].substring(1, 5);
		designa.setAno(Integer.parseInt(ano));
		designa.setNombreTurno(item[1]);
		designa.setIdTurno(Integer.parseInt(item[0]));
		designa.setNumero(Integer.parseInt(item[2]));
		List<ListaInteresadoJusticiableItem> response = designacionesService.busquedaListaInteresados(designa, request);
		if (response != null) {
			return new ResponseEntity<List<ListaInteresadoJusticiableItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ListaInteresadoJusticiableItem>>(
					new ArrayList<ListaInteresadoJusticiableItem>(), HttpStatus.OK);
		}
	}

	// [ idInstitucion, idPersona, this.selectedDatos.anio,
	// this.selectedDatos.idTurno, this.selectedDatos.numero]
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

	// [ designa.idInstitucion, justiciable.idPersona, designa.anio,
	// designa.idTurno, designa.numero]
	@RequestMapping(value = "/designas/insertInteresado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertInteresado(@RequestBody String[] item, HttpServletRequest request) {
		String anio = item[2].substring(1, 5);
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

	// 3.3.6.2.6.3. Ficha detalle del interesado

	// [ designa.idInstitucion, justiciable.idPersona, designa.anio,
	// designa.idTurno, designa.numero, representante]
	@RequestMapping(value = "/designas/updateRepresentanteInteresado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateRepresentanteInteresado(@RequestBody String[] item,
			HttpServletRequest request) {
		String anio = item[2].substring(1, 5);
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
	// 3.3.6.2.7. Tarjeta Contrarios

	// [designaItem.idTurno, nombreTurno, numero, anio, historico]
	@RequestMapping(value = "/designas/busquedaListaContrarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaContrarioJusticiableItem>> busquedaListaContrarios(@RequestBody String[] item,
			HttpServletRequest request) {
		DesignaItem designa = new DesignaItem();
		String ano = item[3].substring(1, 5);
		designa.setAno(Integer.parseInt(ano));
		designa.setNombreTurno(item[1]);
		designa.setIdTurno(Integer.parseInt(item[0]));
		designa.setNumero(Integer.parseInt(item[2]));
		List<ListaContrarioJusticiableItem> response = designacionesService.busquedaListaContrarios(designa, request,
				Boolean.parseBoolean(item[4]));
		if (response != null) {
			return new ResponseEntity<List<ListaContrarioJusticiableItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ListaContrarioJusticiableItem>>(
					new ArrayList<ListaContrarioJusticiableItem>(), HttpStatus.OK);
		}
	}

	// [ idInstitucion, idPersona, this.selectedDatos.anio,
	// this.selectedDatos.idTurno, this.selectedDatos.numero]
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

	// [ designa.idInstitucion, justiciable.idPersona, designa.anio,
	// designa.idTurno, designa.numero]
	@RequestMapping(value = "/designas/insertContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertContrario(@RequestBody String[] item, HttpServletRequest request) {
		String anio = item[2].substring(1, 5);
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

	// 3.3.6.2.7.3. Ficha detalle del contrario

	// 3.3.6.2.8. Tarjeta Procurador
	// 3.3.6.2.7.3.	Ficha detalle del contrario

		//[ designa.idInstitucion,  justiciable.idPersona, designa.anio,  designa.idTurno, designa.numero, representante]
		@RequestMapping(value = "/designas/updateRepresentanteContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateRepresentanteContrario(@RequestBody String[] item, HttpServletRequest request) {
			String anio = item[2].substring(1,5);
			ScsContrariosdesigna contrario = new ScsContrariosdesigna();
			contrario.setIdinstitucion(Short.parseShort(item[0]));
			contrario.setIdpersona(Long.parseLong(item[1]));
			contrario.setAnio(Short.parseShort(anio));
			contrario.setIdturno(Integer.parseInt(item[3]));
			contrario.setNumero(Long.parseLong(item[4]));
			contrario.setNombrerepresentante(item[5]);
			UpdateResponseDTO response = designacionesService.updateRepresentanteContrario(contrario, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		// [designa.idInstitucion, sessionStorage.getItem("personaDesigna"), designa.ano, designa.numero, designa.idTurno, this.generalBody.idProcurador, this.generalBody.idInstitucion]
		@RequestMapping(value = "/designas/updateProcuradorContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateProcuradorContrario(@RequestBody String[] item, HttpServletRequest request) {
//		ResponseEntity<UpdateResponseDTO> updateProcuradorContrario(@RequestBody ScsContrariosdesigna contrario, HttpServletRequest request) {
			ScsContrariosdesigna contrario = new ScsContrariosdesigna();
			contrario.setIdinstitucion(Short.parseShort(item[0]));
			contrario.setIdpersona(Long.parseLong(item[1]));
			String anio = item[2].substring(1,5);
			contrario.setAnio(Short.parseShort(anio));
			contrario.setNumero(Long.parseLong(item[3]));
			contrario.setIdturno(Integer.parseInt(item[4]));
			if(item[5]!=null)contrario.setIdprocurador(Long.valueOf(item[5]));
			else contrario.setIdprocurador(null);
			if(item[6]!=null)contrario.setIdinstitucionProcu(Short.parseShort(item[6]));
			else contrario.setIdinstitucionProcu(null);
			UpdateResponseDTO response = designacionesService.updateProcuradorContrario(contrario, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		//[ designa.idInstitucion,  sessionStorage.getItem("personaDesigna"), designa.ano,  designa.idTurno, designa.numero, this.generalBody.idPersona, this.generalBody.nombre]
		@RequestMapping(value = "/designas/updateAbogadoContrario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<UpdateResponseDTO> updateAbogadoContrario(@RequestBody String[] item, HttpServletRequest request) {
			String anio = item[2].substring(1,5);
			ScsContrariosdesigna contrario = new ScsContrariosdesigna();
			contrario.setIdinstitucion(Short.parseShort(item[0]));
			contrario.setIdpersona(Long.parseLong(item[1]));
			contrario.setAnio(Short.parseShort(anio));
			contrario.setIdturno(Integer.parseInt(item[3]));
			contrario.setNumero(Long.parseLong(item[4]));
			contrario.setIdabogadocontrario(Long.parseLong(item[5]));
			contrario.setNombreabogadocontrario(item[6]);
			UpdateResponseDTO response = designacionesService.updateAbogadoContrario(contrario, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		@RequestMapping(value = "/designas/searchAbogadoByIdPersona", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<ColegiadoItemDTO> searchAbogadoById(@RequestBody String idPersona, HttpServletRequest request) {
			
			 ColegiadoItemDTO response = designacionesService.SearchAbogadoByIdPersona(idPersona, request);
			if (response.getError().getCode() == 200)
				return new ResponseEntity< ColegiadoItemDTO>(response, HttpStatus.OK);
			else
				return new ResponseEntity< ColegiadoItemDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

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
	
//	[ designa.ano,  designa.idTurno, designa.numero]
	@RequestMapping(value = "/designas/busquedaDesignacionActual", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DesignaItem>  busquedaDesignaActual(
//			@RequestBody ScsDesigna designa,
			@RequestBody String[] item,
			HttpServletRequest request) {
		ScsDesigna designa = new ScsDesigna();
		String ano = item[0].substring(1, 5);
		designa.setAnio((short) Integer.parseInt(ano));
		designa.setIdturno(Integer.parseInt(item[1]));
        designa.setNumero((long) Integer.parseInt(item[2]));
        DesignaItem response = designacionesService.busquedaDesignaActual(designa, request);
		if (response != null) {
			return new ResponseEntity<DesignaItem>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<DesignaItem>(
					new DesignaItem(), HttpStatus.OK);
		}
	}
	
//	[ designa.ano,  designa.idTurno, designa.numero]
	@RequestMapping(value = "/designas/busquedaLetradosDesignacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ListaLetradosDesignaItem>>  busquedaLetradosDesigna(
//			@RequestBody ScsDesigna designa,
			@RequestBody String[] item,
			HttpServletRequest request) {
		ScsDesigna designa = new ScsDesigna();
		String ano = item[0].substring(1, 5);
		designa.setAnio((short) Integer.parseInt(ano));
		designa.setIdturno(Integer.parseInt(item[1]));
		designa.setNumero((long) Integer.parseInt(item[2]));
		if(item.length == 4) {
			designa.setNumprocedimiento(item[3]);
		}else {
			designa.setNumprocedimiento(null);
		}
		List<ListaLetradosDesignaItem> response = designacionesService.busquedaLetradosDesigna(designa, request);
		if (response != null) {
			return new ResponseEntity<List<ListaLetradosDesignaItem>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ListaLetradosDesignaItem>>(
					new ArrayList<ListaLetradosDesignaItem>(), HttpStatus.OK);
		}
	}

	// 3.3.6.2.9.3. Ficha cambio del letrado designado

	
	//Servicio de guardado - El elemento ITEM llega con los siguientes elementos de front (ficha.ts)
//	[designa.ano, designa.idTurno, designa.numero, 
//     this.saliente.body.idPersona,  this.saliente.body.observaciones, this.saliente.body.motivoRenuncia, this.saliente.body.fechaDesigna, this.saliente.body.fechaSolRenuncia,
//     this.entrante.body.fechaDesigna, this.entrante.body.idPersona this.saliente.body.compensacion, this.entrante.body.salto, 
//	   this.entrante.body.art27, this.entrante.minDateDesigna]
	@RequestMapping(value = "/designas/updateLetradoDesigna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateLetradoDesigna(@RequestBody String[] item,HttpServletRequest request) throws Exception {		
		
		UpdateResponseDTO response = designacionesService.updateLetradoDesigna(item, request);
		if (response.getError().getCode().intValue() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else if (response.getError().getCode().intValue() == 202)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.ACCEPTED);
		else if (response.getError().getCode().intValue() == 406)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.NOT_ACCEPTABLE);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/designas/compruebaLetradoInscritoEnTurno", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> compruebaLetradoInscritoEnTurno(@RequestBody ScsDesignasletrado designasletrado, HttpServletRequest request) {
		ResponseDataDTO response = new ResponseDataDTO();
		try {
			response = designacionesService.compruebaLetradoInscritoEnTurno(designasletrado, request);
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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

	@PostMapping(value = "/designas/reactivarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> reactivarActDesigna(
			@RequestBody List<ActuacionDesignaItem> listaActuacionDesignaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.anularReactivarActDesigna(listaActuacionDesignaItem, false,
				request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/validarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> validarActDesigna(@RequestBody ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.validarDesvalidarActDesigna(actuacionDesignaItem, true,
				request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/designas/desvalidarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> desvalidarActDesigna(
			@RequestBody ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.validarDesvalidarActDesigna(actuacionDesignaItem, false,
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

	@PostMapping(value = "/designas/guardarNewActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsertResponseDTO> guardarActDesigna(@RequestBody ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.guardarActDesigna(actuacionDesignaItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/actualizarActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> actualizarActDesigna(@RequestBody ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.actualizarActDesigna(actuacionDesignaItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	} 
	
	@GetMapping(value = "/comboMotivosCambioActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComboDTO> comboMotivosCambioActDesigna(HttpServletRequest request) {
		ComboDTO response = designacionesService.comboMotivosCambioActDesigna(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/desgignas/formatoProcedimiento", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringDTO> formatoProcedimiento(HttpServletRequest request) {
		StringDTO response = designacionesService.formatoProcedimiento(request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/updateJustiActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> guardarJustiActDesigna(@RequestBody ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateJustiActDesigna(actuacionDesignaItem, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getHistorioAccionesActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActuacionDesignaItem> getHistorioAccionesActDesigna(
			@RequestBody ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, HttpServletRequest request) {
		ActuacionDesignaItem response = designacionesService.getHistorioAccionesActDesigna(actuacionDesignaRequestDTO, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.3.6.2.2. Ficha Procurador

	@RequestMapping(value = "/designas/busquedaProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> busquedaProcurador(@RequestBody List<String> procurador, HttpServletRequest request) {
		ProcuradorDTO response = designacionesService.busquedaProcurador(procurador, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/designas/comboTipoMotivo")
	public ResponseEntity<ComboDTO> comboTipoMotivo(HttpServletRequest request) {
		ComboDTO response = designacionesService.comboTipoMotivo(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/designas/guardarProcuradorEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> guardarProcuradorEJG(@RequestBody  ProcuradorItem procuradorItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.guardarProcuradorEJG(procuradorItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/compruebaProcurador",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> compruebaProcurador(@RequestBody String procurador, HttpServletRequest request) {
		ProcuradorDTO response = designacionesService.compruebaProcurador(procurador, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/guardarProcurador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> nuevoProcurador(@RequestBody ProcuradorItem procuradorItem,
			HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.guardarProcurador(procuradorItem, request);
		if (response.getStatus()=="OK")
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/compruebaFechaProcurador",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ProcuradorDTO> compruebaFechaProcurador(@RequestBody ProcuradorItem procurador, HttpServletRequest request) {
		ProcuradorDTO response = designacionesService.compruebaFechaProcurador(procurador, request);
		return new ResponseEntity<ProcuradorDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboPrisiones")
	public ResponseEntity<ComboDTO> comboPrisiones(HttpServletRequest request) {
		ComboDTO response = designacionesService.comboPrisiones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/busquedaRelaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RelacionesDTO> busquedaRelaciones(@RequestBody List<String> procurador, HttpServletRequest request) {
		RelacionesDTO response = designacionesService.busquedaRelaciones(procurador, request);
		return new ResponseEntity<RelacionesDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/eliminarRelacion",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteResponseDTO> eliminarRelacion(
			@RequestBody List<String> listaRelaciones, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminarRelacion(listaRelaciones, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/eliminarRelacionAsistenciaDesigna",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateResponseDTO> eliminarRelacionAsistenciaDesigna(
			@RequestBody RelacionesItem listaRelaciones, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.eliminarRelacionAsistenciaDesigna(listaRelaciones, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getLetradoDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LetradoDesignaDTO> getLetradoDesigna(
			@RequestBody AsuntosClaveJusticiableItem asuntoClave, HttpServletRequest request) {
		LetradoDesignaDTO response = designacionesService.getLetradoDesigna(asuntoClave, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/designas/busquedaComunicaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EnviosMasivosDTO> busquedaComunicaciones(@RequestBody List<String> comunicaciones, HttpServletRequest request) {
		EnviosMasivosDTO response = designacionesService.busquedaComunicaciones(comunicaciones, request);
		return new ResponseEntity<EnviosMasivosDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/subirDocumentoActDesigna", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<InsertResponseDTO> subirDocumentoActDesigna(MultipartHttpServletRequest request) {
		InsertResponseDTO response = designacionesService.subirDocumentoActDesigna(request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getDocumentosPorActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentoActDesignaDTO> getDocumentosPorActDesigna(
			@RequestBody DocumentoActDesignaItem documentoActDesignaItem, HttpServletRequest request) {
		DocumentoActDesignaDTO response = designacionesService.getDocumentosPorActDesigna(documentoActDesignaItem,
				request);
		return new ResponseEntity<DocumentoActDesignaDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/descargarDocumentosActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			@RequestBody List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = designacionesService
				.descargarDocumentosActDesigna(listaDocumentoActDesignaItem, request);
		return response;
	}
	
	@PostMapping(value = "/designas/descargarDocumentosActDesignaJustificacionExpres", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosActDesignaJustificacionExpres(
			@RequestBody DocumentoDesignaItem documentoDesignaItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = designacionesService
				.descargarDocumentosActDesignaJustificacionExpres(documentoDesignaItem, request);
		return response;
	}
	
	@PostMapping(value = "/designas/eliminarDocumentosActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentosActDesigna(
			@RequestBody List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminarDocumentosActDesigna(listaDocumentoActDesignaItem,
				request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getDocumentosPorDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentoDesignaDTO> getDocumentosPorDesigna(
			@RequestBody DocumentoDesignaItem documentoDesignaItem, HttpServletRequest request) {
		DocumentoDesignaDTO response = designacionesService.getDocumentosPorDesigna(documentoDesignaItem, request);
		return new ResponseEntity<DocumentoDesignaDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/subirDocumentoDesigna", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<InsertResponseDTO> subirDocumentoDesigna(MultipartHttpServletRequest request) {
		InsertResponseDTO response = designacionesService.subirDocumentoDesigna(request);
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/eliminarDocumentosDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO> eliminarDocumentosDesigna(
			@RequestBody List<DocumentoDesignaItem> listaDocumentoDesignaItem, HttpServletRequest request) {
		DeleteResponseDTO response = designacionesService.eliminarDocumentosDesigna(listaDocumentoDesignaItem, request);
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/descargarDocumentosDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosDesigna(
			@RequestBody List<DocumentoDesignaItem> listaDocumentoDesignaItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = designacionesService
				.descargarDocumentosDesigna(listaDocumentoDesignaItem, request);
		return response;
	}
	
	@PostMapping(value = "/designas/descargarDocumentosDesignaJustificacionExpres", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargarDocumentosDesignaJustificacionExpres(
			@RequestBody JustificacionExpressItem justificacionExpressItem, HttpServletRequest request) {
		ResponseEntity<InputStreamResource> response = designacionesService
				.descargarDocumentosDesignaJustificacionExpres(justificacionExpressItem, request);
		return response;
	}
	
	@PostMapping(value = "/designas/asociarEjgDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> asociarEjgDesigna(@RequestBody List<String> designaItem, HttpServletRequest request) {
		InsertResponseDTO response = designacionesService.asociarEjgDesigna(designaItem, request);

		if (response.getError().getCode() == 200)
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/designas/asociarAsistenciaDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> asociarAsistenciaDesigna(@RequestBody List<String> designaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.asociarAsistenciaDesigna(designaItem, request);

		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/designas/actualizarPartidaPresupuestariaActDesigna", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> actualizarPartidaPresupuestariaActDesigna(@RequestBody ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.actualizarPartidaPresupuestariaActDesigna(actuacionDesignaItem, request);
		
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/designas/getDelitos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ListDTO> getDelitos(@RequestBody DesignaItem designaItem, HttpServletRequest request) {
		ListDTO response = designacionesService.getDelitos(designaItem, request);
		return new ResponseEntity<ListDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/getPreDesignaEJG", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> getPreDesignaEJG(@RequestBody ScsEjgdesigna datos, HttpServletRequest request) throws Exception {
		InsertResponseDTO response = designacionesService.getPreDesignaEJG(datos, request);
		if (response.getStatus().equals("OK"))
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/getEjgDesigna", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<EjgDesignaDTO> getEjgDesigna(HttpServletRequest request, String idTurno, String ano, String numero ) {
		DesignaItem designa = new DesignaItem();
		designa.setIdTurno(Integer.valueOf(idTurno));
		designa.setAno(Integer.valueOf(ano));
		designa.setNumero(Integer.valueOf(numero));
		EjgDesignaDTO response = designacionesService.getEjgDesigna(designa, request);
		return new ResponseEntity<EjgDesignaDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getEJG", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ScsEjg> comboTipoEJG(HttpServletRequest request, String num, String anioEjg, String tipoEjg) {
		EjgItem item = new EjgItem();
		item.setAnnio(anioEjg);
		item.setNumero(num);
		item.setTipoEJG(tipoEjg);
		ScsEjg response = designacionesService.getEJG(item, request);
		return new ResponseEntity<ScsEjg>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/designas/queryImprimirJustificacionExpress",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringDTO> queryImprimirJustificacionExpress(@RequestBody JustificacionExpressItem item,HttpServletRequest request) {
		
		StringDTO response = designacionesService.queryImprimirJustificacionExpress(item, request);
		return new ResponseEntity<StringDTO>(response, HttpStatus.OK);
	}
	

	
}
