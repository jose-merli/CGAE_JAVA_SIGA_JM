package org.itcgae.siga.com.services;

import java.io.File;

public interface IPFDService {
	
	public String firmarPDF(File fichero) throws Exception;

	public String obtenerDocumentoFirmado(String csv) throws Exception;	

	public byte[] getBytes(String file) throws Exception;
	
	public File createFile(byte[] bytes,String path,String fileName) throws Exception;
}
