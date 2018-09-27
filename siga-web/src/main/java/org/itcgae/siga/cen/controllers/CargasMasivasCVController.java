package org.itcgae.siga.cen.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.cen.services.ICargasMasivasCVService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.CargaMasivaDatosCVVo;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class CargasMasivasCVController {

	@Autowired
	private ICargasMasivasCVService iCargasMasivasCVService;
	
	@RequestMapping(value = "cargaMasivaDatosCurriculares/downloadFile",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> downloadFile (HttpServletRequest request) throws SigaExceptions, IOException {

		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String,Object>>();
		Hashtable<String, Object> datosHashtable =  new Hashtable<String, Object>();
		datosHashtable.put(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo(),"nnnnnn");
		datosHashtable.put(CargaMasivaDatosCVVo.PERSONANIF.getCampo(),"nnnnnnnna" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo(),"dd/mm/yyyy" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_FECHAFIN.getCampo(), "dd/mm/yyyy" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_CREDITOS.getCampo(), "nnn");
		datosHashtable.put(CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo(), "dd/mm/yyyy");
		datosHashtable.put(CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo(),"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		datosHashtable.put(CargaMasivaDatosCVVo.TIPOCVCOD.getCampo(), "aaa");
		datosHashtable.put(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo(),"aaa");
		datosHashtable.put(CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo(),"aaa");
		datosVector.add(datosHashtable);
		datosHashtable =  new Hashtable<String, Object>();
		datosHashtable.put(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo(),"Opcional. Si nulo nif/cif requerido");
		datosHashtable.put(CargaMasivaDatosCVVo.PERSONANIF.getCampo(),"Opcional. Si nulo colegiadonumero requerido" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo(),"Opcional" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_FECHAFIN.getCampo(), "Opcional" );
		datosHashtable.put(CargaMasivaDatosCVVo.C_CREDITOS.getCampo(), "Opcional");
		datosHashtable.put(CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo(), "Opcional");
		datosHashtable.put(CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo(),"Opcional" );
		datosHashtable.put(CargaMasivaDatosCVVo.TIPOCVCOD.getCampo(), "Requerido");
		datosHashtable.put(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo(),"Opcional");
		datosHashtable.put(CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo(),"Opcional");
		datosVector.add(datosHashtable);
			 
			 
		File exampleFile =  iCargasMasivasCVService.createExcelFile(iCargasMasivasCVService.CAMPOSEJEMPLO,datosVector);
		request.setAttribute("nombreFichero", exampleFile.getName());
		request.setAttribute("rutaFichero", exampleFile.getPath());
		request.setAttribute("accion", "");
		
//      version 1
//		InputStream targetStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(exampleFile));
//		InputStreamResource resource = new InputStreamResource(targetStream);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Access-Control-Expose-Headers","Content-Disposition");
		headers.add("Content-Disposition", "filename=" +  exampleFile.getName());
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		 return new ResponseEntity<byte[]> ( Files.readAllBytes(exampleFile.toPath()), headers, HttpStatus.OK);
//		return ResponseEntity.ok().headers(headers).contentLength(FileUtils.readFileToByteArray(exampleFile).length)
//				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

		
//      version 2
//		byte[] contentInBytes = Files.readAllBytes(exampleFile.toPath());
//		FileOutputStream fop = new FileOutputStream(exampleFile);
//		fop.write(contentInBytes);
//		fop.flush();
//		fop.close();
		
//		 InputStream inputStream = new FileInputStream(exampleFile.getPath());
//		    InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
//		  
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.setContentLength(Files.size(Paths.get(exampleFile.getPath())));
//		    return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);

		
	}
		
	

	@RequestMapping(value = "cargaMasivaDatosCurriculares/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = iCargasMasivasCVService.uploadFile(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
