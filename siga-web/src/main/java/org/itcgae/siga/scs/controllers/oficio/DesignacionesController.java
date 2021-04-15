package org.itcgae.siga.scs.controllers.oficio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	//Busqueda designaciones
	
	@RequestMapping(value = "/designas/comboTipoDesigna",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/busquedaJustificacionExpres",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<JustificacionExpressItem>> busquedaJustificacionExpres(@RequestBody JustificacionExpressItem item, HttpServletRequest request) {
		List<JustificacionExpressItem> response = designacionesService.busquedaJustificacionExpres(item, request);
		return new ResponseEntity<List<JustificacionExpressItem>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/busquedaDesignaciones",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<DesignaItem>> busquedaDesignas(@RequestBody DesignaItem item, HttpServletRequest request) {
		List<DesignaItem> response = designacionesService.busquedaDesignas(item, request);
		if(response != null) {
			return new ResponseEntity<List<DesignaItem>>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<DesignaItem>>(new ArrayList<DesignaItem>(), HttpStatus.OK);
		}
	}
	
	
	
	@RequestMapping(value = "/comboModulo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulos(HttpServletRequest request) {

		ComboDTO response = comboService.comboModulo(request);
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
	
	
	// FIN Busqueda designaciones
	
	
	// 3.3.6.2.2.	Tarjeta Datos Generales
	
	@RequestMapping(value = "/designas/comboTurnosDatosGenerales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTurnosDatosGenerales(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/designas/comboTipoDesignacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoDesignacion(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 3.3.6.2.3.	Tarjeta Detalle Designación
	
	
	@RequestMapping(value = "/designas/updateDetalleDesignacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDetalleDesignacion(@RequestBody DesignaItem designaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDetalleDesigna(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/designas/comboEstadoDesignacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboEstadoDesignacion(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/comboJuzgado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboJuzgado(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/comboProcedimiento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboProcedimiento(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/comboModulo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboModulo(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/comboDelitos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboDelitos(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 3.3.6.2.4.	Tarjeta Datos Adicionales

	@RequestMapping(value = "/designas/updateDatosAdicionales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateDatosAdicionales(@RequestBody DesignaItem designaItem, HttpServletRequest request) {
		UpdateResponseDTO response = designacionesService.updateDatosAdicionales(designaItem, request);
		if (response.getError().getCode() == 200)
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	// 3.3.6.2.5.	Tarjeta Datos Facturación 
	
	@RequestMapping(value = "/designas/comboPartidaPresupuestaria", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboPartidaPresupuestaria(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
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

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 3.3.6.2.9.	Tarjeta Letrados de la designación
	
	// 3.3.6.2.9.3.	Ficha cambio del letrado designado
	
	// 3.3.6.2.10.	Tarjeta Relaciones
	
	
	// 3.3.6.2.12.	Tarjeta Comunicaciones
	
	// 3.3.6.2.13.	Tarjeta Documentación
	
	@RequestMapping(value = "/designas/comboAsociado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboAsociado(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/designas/comboTipoDocumentacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboTipoDocumentacion(HttpServletRequest request) {

		ComboDTO response = new ComboDTO(); //TODO
		if (response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 3.3.6.2.14.	Tarjeta actuaciones
	
	// 3.3.6.2.14.3.	Ficha Actuación
	

}
 
