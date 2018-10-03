package org.itcgae.siga.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.itcgae.siga.exception.BusinessException;

public class SIGAServicesHelper {

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
}
