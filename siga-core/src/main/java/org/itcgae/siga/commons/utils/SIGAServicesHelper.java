package org.itcgae.siga.commons.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import java.util.regex.Pattern;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class SIGAServicesHelper {
	
	private static Logger LOGGER = Logger.getLogger(SIGAServicesHelper.class);

	public static void uploadFichero(String pathDirectorio, String nombreFichero, byte [] bytesFichero) throws BusinessException{
		LOGGER.debug("SIGAServicesHelper.uploadFichero() - INICIO");
		LOGGER.debug("Comprobamos si el directorio de subida existe - " + pathDirectorio);
		File directorio = new File(pathDirectorio);
		if(!directorio.exists()) {
			LOGGER.debug("El directorio NO existe, lo creamos");
			directorio.mkdirs();
			SIGAHelper.addPerm777(directorio);
			LOGGER.debug("Directorio creado con éxito");
		}
			
		StringBuffer pathFichero = new StringBuffer(pathDirectorio);
		pathFichero.append(File.separator);
		pathFichero.append(nombreFichero);
		
		File file = new File(pathFichero.toString());
		OutputStream theBAOS = null;
		try {
			if(!file.exists())
				file.createNewFile();

			SIGAHelper.addPerm777(file);
			theBAOS = new FileOutputStream(file);
			theBAOS.write(bytesFichero);
			theBAOS.flush();

		} catch (Exception e) {
			LOGGER.warn("Error upload Fichero() " + e.getMessage());
			throw new BusinessException("Error upload Fichero: " + e.getClass().getName());
		}finally{
			LOGGER.debug("SIGAServicesHelper.uploadFichero() - FIN");
			if(theBAOS!=null)
				try {
					theBAOS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				theBAOS = null;
		}
	}

	public static InputStreamResource doZipToDownload(List<File> list) throws BusinessException {
		InputStreamResource result = null;

		ByteArrayOutputStream byteArrayOutputStream;
		BufferedOutputStream bufferedOutputStream;
		ZipOutputStream zipOutputStream = null;

		try {
			if (list != null && list.size() > 0) {

				byteArrayOutputStream = new ByteArrayOutputStream();
				bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
				zipOutputStream = new ZipOutputStream(bufferedOutputStream);

				for (File baos : list) {
					if (baos.exists()) {
						ZipEntry ze = new ZipEntry(baos.getName());
						zipOutputStream.putNextEntry(ze);
						FileInputStream fis = new FileInputStream(baos);

						// Copia del fichero al zip
						IOUtils.copy(fis, zipOutputStream);

						zipOutputStream.flush();
						fis.close();
						zipOutputStream.closeEntry();
					}
				}

				zipOutputStream.close();

				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
				result = new InputStreamResource(byteArrayInputStream);
			}

		} catch (Exception e) {
			throw new BusinessException("Error al crear fichero zip", e);
		} finally {
			try {
				if (zipOutputStream != null) {
					zipOutputStream.close();
				}
			} catch (Exception eee) {
			}
		}

		return result;
	}

	public static ResponseEntity<InputStreamResource> descargarFicheros(List<File> listaFicheros, MediaType fileContentType, MediaType zipContentType, String zipName) {
		ResponseEntity<InputStreamResource> res = null;

		if (listaFicheros != null && !listaFicheros.isEmpty()) {
			if (listaFicheros.size() == 1) {
				LOGGER.info("descargarFicheros() -> Entrada al servicio para recuperar un único archivo");

				try {
					FileInputStream fileInputStream = new FileInputStream(listaFicheros.get(0));

					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(fileContentType);
					headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + listaFicheros.get(0).getName());
					headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileInputStream), headers, HttpStatus.OK);
				} catch (FileNotFoundException e) {
					throw new BusinessException("messages.general.error.ficheroNoExiste");
				}

				LOGGER.info("descargarFicheros() -> Saliendo servicio para recuperar un único archivo");
			} else {
				LOGGER.info("descargarFicheros() -> Entrada al servicio para recuperar varios archivos");

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(zipContentType);
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipName);
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

				res = new ResponseEntity<InputStreamResource>(SIGAServicesHelper.doZipToDownload(listaFicheros), headers, HttpStatus.OK);

				LOGGER.info("descargarFicheros() -> Saliendo servicio para recuperar varios archivos");
			}

		} else {
			throw new BusinessException("messages.general.error.ficheroNoExiste");
		}

		return res;
	}

	public static File createFile(byte[] bytes, String dir, String fileName) throws BusinessException {
		Path pDir = Paths.get(dir);
		pDir.toFile().mkdirs();
		File returnFile = pDir.resolve(fileName).toFile();
		try (FileOutputStream fileos = new FileOutputStream(returnFile);) {
			fileos.write(bytes);
			fileos.flush();
			fileos.close();
		} catch (Exception e) {
			String sError = "error en createFile " + dir + " filename:" + fileName + " exc:" + e;
			LOGGER.error(sError);
			throw new BusinessException(sError, e);
		}

		return returnFile;
	}

	/**
	 * Comprueba si un email es válido.
	 * @param email Correo electrónico a validar
	 * @return true si es válido, de lo contrario false
	 */
	public static boolean isValidEmailAddress(String email) {

		Pattern EXPRESION_REGULAR_PATTERN_MAIL = Pattern.compile(SigaConstants.EXPRESION_REGULAR_MAIL2, Pattern.CASE_INSENSITIVE);
        return EXPRESION_REGULAR_PATTERN_MAIL.matcher(email).matches() ;
	}
}
