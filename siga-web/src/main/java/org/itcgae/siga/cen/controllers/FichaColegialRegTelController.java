package org.itcgae.siga.cen.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.cen.services.IFichaColegialSociedadesService;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FichaColegialRegTelController {

	@Autowired
	private IFichaColegialRegTelService  iFichaColegialRegtelService;
	
	@RequestMapping(value = "fichaColegialRegTel/searchListDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchList(@RequestParam("numPagina") int numPagina, @RequestBody String idPersona,HttpServletRequest request) throws Exception { 
		DocushareDTO response = iFichaColegialRegtelService.searchListDoc(numPagina, idPersona, request);
		return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaColegialRegTel/searchListDocNoCol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchListNoCol(@RequestParam("numPagina") int numPagina, @RequestBody String idPersona,HttpServletRequest request) throws Exception { 
		DocushareDTO response = iFichaColegialRegtelService.searchListDocNoCol(numPagina, idPersona, request);
		return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "fichaColegialRegTel/searchListDir", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchDir(@RequestParam("numPagina") int numPagina, @RequestBody DocuShareObjectVO docu, HttpServletRequest request) throws Exception { 
		DocushareDTO response = iFichaColegialRegtelService.searchListDir(numPagina, docu, request);
		return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaColegialRegTel/searchListDirNoCol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocushareDTO> searchDirNoCol(@RequestParam("numPagina") int numPagina, @RequestBody DocuShareObjectVO docu, HttpServletRequest request) throws Exception { 
		DocushareDTO response = iFichaColegialRegtelService.searchListDirNoCol(numPagina, docu, request);
		return new ResponseEntity<DocushareDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaColegialRegTel/downloadDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadDoc(@RequestBody DocuShareObjectVO docushareObjectVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseEntity<InputStreamResource> res = iFichaColegialRegtelService.downloadDoc(docushareObjectVO, request);
		return res;
	}
	
	@RequestMapping(value = "fichaColegialRegTel/permisos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPermisos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String res = iFichaColegialRegtelService.getPermisoRegTel(request);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaColegialRegTel/insertCollection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> insertCollection(@RequestBody String idPersona,HttpServletRequest request) throws Exception { 
		String response = iFichaColegialRegtelService.insertCollection(idPersona, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "fichaColegialRegTel/insertCollectionNoCol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> insertCollectionNoCol(@RequestBody String idPersona,HttpServletRequest request) throws Exception { 
		String response = iFichaColegialRegtelService.insertCollectionNoCol(idPersona, request);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	
}
