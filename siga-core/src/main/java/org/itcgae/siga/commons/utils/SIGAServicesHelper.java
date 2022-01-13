package org.itcgae.siga.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.itcgae.siga.exception.BusinessException;

public class SIGAServicesHelper {
	
	private static Logger LOGGER = Logger.getLogger(SIGAServicesHelper.class);


public static void uploadFichero(String pathDirectorio,String nombreFichero,byte [] bytesFichero) throws BusinessException{	
	File directorio = new File(pathDirectorio);
	if(!directorio.exists()) 
		directorio.mkdirs();
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
		throw new BusinessException("Error upload Fichero"+e.getClass().getName());
	}finally{
		if(theBAOS!=null)
			try {
				theBAOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    theBAOS = null;
		}
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
}
