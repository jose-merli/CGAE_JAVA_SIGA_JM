package org.itcgae.siga.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.com.ClaseComunicacionesDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DialogoComunicacionItem;
import org.itcgae.siga.DTOs.com.GenerarComunicacionItem;
import org.itcgae.siga.DTOs.com.KeysDTO;
import org.itcgae.siga.DTOs.com.ModeloDialogoItem;
import org.itcgae.siga.DTOs.com.ModelosComunicacionSearch;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDateDTO;
import org.itcgae.siga.DTOs.com.TipoEnvioDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.FileInfoDTO;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dialogoComunicacion")
public class DialogoComunicacionController {
	
	private static final Logger LOGGER = Logger.getLogger(DialogoComunicacionController.class);	
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	IDialogoComunicacionService _dialogoComunicacionService;
	
	@RequestMapping(value = "/clasesComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> comboClasesComunicacion(HttpServletRequest request, @RequestBody String rutaClaseComunicacion) {
		
		ComboDTO response = _dialogoComunicacionService.obtenerClaseComunicaciones(request, rutaClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/claseComunicacion",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ClaseComunicacionesDTO> obtenerClaseComunicacion(HttpServletRequest request, @RequestBody String rutaClaseComunicacion) {
		
		ClaseComunicacionesDTO response = _dialogoComunicacionService.obtenerClaseComunicacionesUnica(request, rutaClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<ClaseComunicacionesDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ClaseComunicacionesDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/modelosSearch",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ModelosComunicacionSearch> modelosComunicacionSearch(HttpServletRequest request, @RequestBody ModeloDialogoItem modeloDTO) {
		
		ModelosComunicacionSearch response = _dialogoComunicacionService.obtenerModelos(request, modeloDTO);
		if(response.getError() == null)
			return new ResponseEntity<ModelosComunicacionSearch>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ModelosComunicacionSearch>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/tipoEnvios",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<TipoEnvioDTO> obtenertiposEnvioModelo(HttpServletRequest request, @RequestBody String idPlantilla) {
		
		TipoEnvioDTO response = _dialogoComunicacionService.obtenertipoEnvioPlantilla(request, idPlantilla);
		if(response.getError() == null)
			return new ResponseEntity<TipoEnvioDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<TipoEnvioDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/descargar",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<InputStreamResource> descargar(@RequestBody FileInfoDTO fileInfoDTO) {
		
		
		if(fileInfoDTO == null || fileInfoDTO.getFilePath() == null || fileInfoDTO.getName() == null) {
			throw new BusinessException("Falta información para recuperar el fichero");
		}
			
			
		File file = new File(fileInfoDTO.getFilePath());
		if (file == null || !file.exists()) {
			throw new BusinessException("El fichero no existe");
		}
		
		InputStream targetStream;
		try {
			targetStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
			throw new BusinessException("Fichero no encontrado", e);
		}		

		InputStreamResource resource = new InputStreamResource(targetStream);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Access-Control-Expose-Headers","Content-Disposition");
		headers.add("Content-Disposition", "filename=" + SigaConstants.nombreZip + ".zip");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	@RequestMapping(value = "/nombredoc",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<FileInfoDTO> obtenerNombre(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo, HttpServletResponse resp) throws InterruptedException, ExecutionException {
		
		//File file = _dialogoComunicacionService.obtenerNombre(request, dialogo, resp);
		FileInfoDTO fileInfoDTO = new FileInfoDTO();
		
		CompletableFuture<File> completableFuture = _dialogoComunicacionService.obtenerNombre(request, dialogo, resp);
		
		try {
			File file;
			file = completableFuture.get(5, TimeUnit.MINUTES);
			
			if(file != null) {
				fileInfoDTO.setFilePath(file.getAbsolutePath());
				fileInfoDTO.setName(file.getName());
				return new ResponseEntity<FileInfoDTO>(fileInfoDTO, HttpStatus.OK);
			}else {
				return new ResponseEntity<FileInfoDTO>(fileInfoDTO, HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
		} catch (TimeoutException e) {
			String mensaje = "504 - TimeOut";
			try {
				// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				
				AdmUsuarios usuario = usuarios.get(0);
			
				GenRecursosExample genRecursosExample = new GenRecursosExample();
				genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroNuevo")
						.andIdlenguajeEqualTo(usuario.getIdlenguaje());
				List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);

				mensaje = genRecursos.get(0).getDescripcion();
			}catch (Exception ex) {
				LOGGER.info(ex.getCause());
			}
	
			completableFuture.cancel(true);
			fileInfoDTO.setMessageError(mensaje);
			return new ResponseEntity<FileInfoDTO>(fileInfoDTO, HttpStatus.GATEWAY_TIMEOUT);	
		}
		
		
	}
	
	@RequestMapping(value = "/generarEnvios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Error> generarEnvios(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {

		Error response = _dialogoComunicacionService.generarEnvios(request, dialogo);
		if (response.getCode() == 200)
			return new ResponseEntity<Error>(response, HttpStatus.OK);
		else
			return new ResponseEntity<Error>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/keys",  method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<KeysDTO> obtenertiposKeysClase(HttpServletRequest request, @RequestBody String idClaseComunicacion) {
		
		KeysDTO response = _dialogoComunicacionService.obtenerKeysClaseComunicacion(request, idClaseComunicacion);
		if(response.getError() == null)
			return new ResponseEntity<KeysDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<KeysDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@RequestMapping(value = "/obtenerCamposDinamicos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsultasDTO> obtenerCamposDinamicos(HttpServletRequest request, @RequestBody DialogoComunicacionItem dialogo) {
		ConsultasDTO response = _dialogoComunicacionService.obtenerCamposModelo(request, dialogo);
		if (response.getError() == null)
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ConsultasDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@RequestMapping(value = "/fechaProgramada",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDateDTO> obtenerFechaProgramada(HttpServletRequest request) {
		
		ResponseDateDTO response = _dialogoComunicacionService.obtenerFechaProgramada(request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDateDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDateDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/maxModelos",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDataDTO> obtenerNumMaximoModelos(HttpServletRequest request) {
		
		ResponseDataDTO response = _dialogoComunicacionService.obtenerNumMaximoModelos(request);
		if(response.getError() == null)
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ResponseDataDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
