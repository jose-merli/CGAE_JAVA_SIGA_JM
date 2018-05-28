package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaFotoDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class TarjetaDatosGeneralesServiceImpl implements ITarjetaDatosGeneralesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosGeneralesServiceImpl.class);
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;
	
	@Override
	public UpdateResponseDTO loadPhotography(PersonaJuridicaFotoDTO  personaJuridicaFotoDTO, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(
				"loadPhotography() -> Entrada al servicio para cargar de una ruta la fotografía de una persona jurídica");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<GenProperties> genProperties = new ArrayList<GenProperties>();
		ComboItem comboItem = new ComboItem();
		String pathFinal = "";

		// obtener el directorio para la fotografia de la persona
		LOGGER.debug("loadPhotography() -> Obtener el directorio para la fotografia de la persona jurídica");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");
		genProperties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");

		if (null != genProperties && genProperties.size() > 0) {
			String path = genProperties.get(0).getValor() + "/";
			pathFinal = pathFinal.concat(path);

			// obtener el nombre del archivo de la fotografía
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> Entrada a cenPersonaExtendsMapper para obtener el nombre del archivo de la fotografía");
			comboItem = cenPersonaExtendsMapper.loadPhotography(personaJuridicaFotoDTO.getIdPersona());
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> Salida de cenPersonaExtendsMapper para obtener el nombre del archivo de la fotografía");

			if (null != comboItem) {
				pathFinal = pathFinal.concat(comboItem.getValue());
				LOGGER.info(
						"loadPhotography() -> Se obtiene fotografia de la persona jurídica del path:  " + pathFinal);

				// Se coge la imagen con el logo
				File file = new File(pathFinal);
				FileInputStream fis = null;

				try {
					fis = new FileInputStream(file);
					// Parece que soporta otros tipos, como png
					response.setContentType(MediaType.IMAGE_JPEG_VALUE);
					// se pasa el logo en la respuesta http
					IOUtils.copy(fis, response.getOutputStream());
					updateResponseDTO.setStatus(SigaConstants.OK);

				} catch (FileNotFoundException e) {
					LOGGER.error("loadPhotography() -> No se ha encontrado el fichero", e);
					updateResponseDTO.setStatus(SigaConstants.KO);

				} catch (IOException e1) {
					LOGGER.error(
							"loadPhotography() -> No se han podido escribir los datos binarios de la fotografía en la respuesta HttpServletResponse",
							e1);
					updateResponseDTO.setStatus(SigaConstants.KO);
					e1.printStackTrace();
				} finally {
					if (null != fis)
						try {
							fis.close();
						} catch (IOException e) {
							LOGGER.error("loadPhotography() -> No se ha cerrado el archivo que contiene la fotografía", e);
							updateResponseDTO.setStatus(SigaConstants.KO);
							e.printStackTrace();
						}
				}

			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> "
						+ updateResponseDTO.getStatus()
						+ ". No se ha podido obtener el nombre del archivo de la fotografía: " + pathFinal);

			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("loadPhotography() / genPropertiesMapper.selectByExample() ->" + updateResponseDTO.getStatus()
					+ ".No se ha podido obtener el directorio para la fotografia de la persona jurídica");
		}
		
		LOGGER.info("loadPhotography() -> Salida del servicio para cargar de una ruta la fotografía de una persona jurídica");
		
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO uploadPhotography(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {
		
		LOGGER.info(
				"uploadPhotography() -> Entrada al servicio para guardar una fotografía de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String idPersona = request.getParameter("idPersona");
		int response = 0;
		
		// obtener path para almacenar las fotografias
		LOGGER.debug("uploadPhotography() -> Obtener path para almacenar las fotografias");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener directorio de la fotografía");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener directorio de la fotografía");
		
		if (null != properties && properties.size() > 0) {
			String pathImagenes = properties.get(0).getValor() + "/";

			// Coger archivo del request
			LOGGER.debug("uploadPhotography() -> Coger fotografía del request");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

			// comprobar extension de la fotografia
			LOGGER.debug("uploadPhotography() -> Comprobar extension de la fotografia");
			if (extension == null || extension.trim().equals("")
					|| (!extension.trim().toUpperCase().equals(".JPG") && !extension.trim().toUpperCase().equals(".GIF")
							&& !extension.trim().toUpperCase().equals(".PNG")
							&& !extension.trim().toUpperCase().equals(".JPEG"))) {

				try {
					throw new SigaExceptions("messages.error.imagen.tipoNoCorrecto");
				} catch (SigaExceptions e) {
					e.printStackTrace();
				}
			}

			// Crear nombre del archivo a guardar
			LOGGER.debug("uploadPhotography() -> Crear nombre de la fotografía a guardar");
			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Entrada a cenPersonaExtendsMapper para nifcif de una persona");
			List<CenPersona> cenPersonas = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Salida de cenPersonaExtendsMapper para nifcif de una persona");

			if (null != cenPersonas && cenPersonas.size() > 0) {
				String nifCif = cenPersonas.get(0).getNifcif();

				fileName = nifCif + "_" + fileName;

				BufferedOutputStream stream = null;
				// Guardar el archivo
				LOGGER.debug("uploadPhotography() -> Guardar la fotografía");
				try {
					File aux = new File(pathImagenes);
					// creo directorio si no existe
					aux.mkdirs();
					File serverFile = new File(pathImagenes, fileName);
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(file.getBytes());

				} catch (FileNotFoundException e) {
					LOGGER.error("uploadPhotography() -> Error al buscar la fotografía de la persona jurídica en el directorio indicado", e);
				} catch (IOException ioe) {
					LOGGER.error("uploadPhotography() -> Error al guardar la fotografía de la persona jurídica en el directorio indicado",
							ioe);
				} finally {
					// close the stream
					LOGGER.debug("uploadPhotography() -> Cierre del stream de la fotografía de la persona jurídica");
					stream.close();
				}
				
				
				// actualizar nombre de la fotografia en base de datos
				LOGGER.debug("uploadPhotography() -> actualizar nombre de la fotografia en base de datos");
				CenClienteExample cenClienteExample = new CenClienteExample();
				cenClienteExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));
				CenCliente cenCliente = new CenCliente();
				cenCliente.setFotografia(fileName);
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Entrada a cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				response = cenClienteMapper.updateByExample(cenCliente, cenClienteExample);
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Salida de cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				if(response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.warn(
							"loadPhotography() / cenClienteMapper.updateByExample() -> " + updateResponseDTO.getStatus() + " .Nombre de la fotografía de una persona jurídica actualizado correctamente");
	
				}
				else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"loadPhotography() / cenClienteMapper.updateByExample() -> " + updateResponseDTO.getStatus() + " .No se ha podido actualizar el nombre de la fotografía de una persona jurídica");
				}
				
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ".No existen ninguna persona con en idPersona:" + idPersona + " indicado");
			}

		}
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"loadPhotography() / genPropertiesMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ".No se pudo obtener el directorio de la fotografía");
		}
		
		LOGGER.info(
				"uploadPhotography() -> Salida del servicio para guardar una fotografía de una persona jurídica");
		
		return updateResponseDTO;
	}
	
	
	
	
	

}
