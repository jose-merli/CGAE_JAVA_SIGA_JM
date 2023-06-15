package org.itcgae.siga.com.services;

import java.io.File;

public interface IPFDService {
	
    public String firmarPDF(File fichero) throws Exception;
    
	public String obtenerDocumentoFirmado(String csv) throws Exception;

    public String obtenerDocumentoEEJGFirmado(String csv) throws Exception;

    public byte[] getBytes(String file) throws Exception;
	
	public File createTempFile(byte[] bytes,String fileName) throws Exception;
}
