package org.itcgae.siga.cen.controllers;



import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BancoBicDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchAnexosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.FicheroDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosDownloadDTO;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class TarjetaDatosBancariosController {

	@Autowired 
	private ITarjetaDatosBancariosService tarjetaDatosBancariosService;
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosBancariosDTO> searchBanksData(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		DatosBancariosDTO response = tarjetaDatosBancariosService.searchBanksData(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<DatosBancariosDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/busquedaPerJuridica/datosBancariosDelete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DeleteResponseDTO>deleteBanksData(@RequestBody DatosBancariosDeleteDTO datosBancariosDeleteDTO, HttpServletRequest request) { 
		DeleteResponseDTO response = tarjetaDatosBancariosService.deleteBanksData(datosBancariosDeleteDTO, request);
		
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<DeleteResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosGeneralSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosBancariosDTO> searchGeneralData(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		DatosBancariosDTO response = tarjetaDatosBancariosService.searchGeneralData(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<DatosBancariosDTO >(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/MandatosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MandatosDTO> searchMandatos(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request) { 
		MandatosDTO response = tarjetaDatosBancariosService.searchMandatos(numPagina, datosBancariosSearchDTO, request);
		return new ResponseEntity<MandatosDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosInsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertBanksData(@RequestBody DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = tarjetaDatosBancariosService.insertBanksData(datosBancariosInsertDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/comboEsquema",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> getLabelColegios(HttpServletRequest request) {
		ComboDTO response = tarjetaDatosBancariosService.getLabelEsquema(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/mandatosInsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateMandatos(@RequestBody MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) throws IOException, NamingException, SQLException { 
		UpdateResponseDTO response = tarjetaDatosBancariosService.updateMandatos(mandatosUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/solicitudInsertBanksData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> solicitudInsertBanksData(@RequestBody DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws Exception { 
		InsertResponseDTO response = tarjetaDatosBancariosService.solicitudInsertBanksData(datosBancariosInsertDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/BanksSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BancoBicDTO> searchBanks( @RequestBody DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO, HttpServletRequest request) { 
		BancoBicDTO response = tarjetaDatosBancariosService.searchBanks(datosBancariosSearchBancoDTO, request);
		return new ResponseEntity<BancoBicDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/datosBancariosUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateBanksData(@RequestBody DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws Exception { 
		UpdateResponseDTO response = tarjetaDatosBancariosService.updateBanksData(datosBancariosInsertDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/AnexosSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DatosBancariosAnexoDTO> searchAnexos(@RequestParam("numPagina") int numPagina, @RequestBody DatosBancariosSearchAnexosDTO datosBancariosSearchAnexosDTO, HttpServletRequest request) { 
		DatosBancariosAnexoDTO response = tarjetaDatosBancariosService.searchAnexos(numPagina, datosBancariosSearchAnexosDTO, request);
		return new ResponseEntity<DatosBancariosAnexoDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "busquedaPerJuridica/updateAnexos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UpdateResponseDTO> updateAnexos(@RequestBody MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) throws IOException, NamingException, SQLException { 
		UpdateResponseDTO response = tarjetaDatosBancariosService.updateAnexos(mandatosUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	
	@RequestMapping(value = "busquedaPerJuridica/insertAnexos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InsertResponseDTO> insertAnexos(@RequestBody MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) throws IOException, NamingException, SQLException { 
		InsertResponseDTO response = tarjetaDatosBancariosService.InsertAnexos(mandatosUpdateDTO, request);
		if(response.getStatus().equals(SigaConstants.OK))
		return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<InsertResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@RequestMapping(value = "busquedaPerJuridica/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<UpdateResponseDTO> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		UpdateResponseDTO response = tarjetaDatosBancariosService.uploadFile(request);
		if (response.getStatus().equals(SigaConstants.OK))
			return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.OK);
		else return new ResponseEntity<UpdateResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	 @RequestMapping(value = "busquedaPerJuridica/downloadFile", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document" } )
	    public ResponseEntity<byte[]> downloadFile(@RequestBody MandatosDownloadDTO mandatosDownloadDTO, HttpServletRequest request, HttpServletResponse response) {
	    	FicheroDTO ficheroDTO = tarjetaDatosBancariosService.downloadFile(mandatosDownloadDTO,request,response);
	    		HttpHeaders respuestaHeader = new HttpHeaders();
	    		                respuestaHeader.add("content-disposition", "attachment; filename= " + ficheroDTO.getFileName()); 
	    		 

	    	return new ResponseEntity<byte[]>(ficheroDTO.getFile(),respuestaHeader, HttpStatus.OK);
	    }
	 
	 
	 @RequestMapping(value = "busquedaPerJuridica/fileDownloadInformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<ComboItem> fileDownloadInformation(@RequestBody MandatosDownloadDTO mandatosDownloadDTO, HttpServletRequest request) { 
		 ComboItem response = tarjetaDatosBancariosService.fileDownloadInformation(mandatosDownloadDTO, request);
			return new ResponseEntity<ComboItem>(response, HttpStatus.OK);
		}
	
	 
	 @RequestMapping(value = "busquedaPerJuridica/getLengthCodCountry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		ResponseEntity<Integer> getLengthCodCountry(@RequestBody String ccountry, HttpServletRequest request) { 
		 int response = tarjetaDatosBancariosService.getLengthCodCountry(ccountry, request);
			return new ResponseEntity<Integer>(response, HttpStatus.OK);
		}
	 
	 
//	 @RequestMapping(value = "busquedaPerJuridica/downloadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	    public ResponseEntity<FilesDTO> downloadFile(@RequestBody MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) {
//		 
//		 FilesDTO filesDTO = new FilesDTO();
//		 filesDTO.setExtension(".pdf");
//		 filesDTO.setNameFile("2006002472110");
//		 // con esto vaaaa !!!
//		 byte[] archivo = null;
//			File file = new File("C://IISIGA/anexos/2006002472110.pdf");
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(file);
//				// se pasa el logo en la respuesta http
//				//response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//				InputStream input = fis;
//				//archivo = IOUtils.toByteArray(input);
//				
//				IOUtils.copy(fis, filesDTO.getResponse().getOutputStream());
//				
//			}catch (FileNotFoundException e) {
//				System.out.println("Adas");
//				
//			} catch (IOException e1) {
//				System.out.println("Adasd");
//				e1.printStackTrace();
//			}
//			finally {
//				if(null!= fis)
//					try {
//						fis.close();
//					} catch (IOException e) {
//						System.out.println("asdasd");
//						e.printStackTrace();
//					}
//			}
//		 
//
//			
//			
//			return new ResponseEntity<FilesDTO>(filesDTO, HttpStatus.OK);
//	    }
	 
	
}
