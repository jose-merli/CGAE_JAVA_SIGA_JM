package org.itcgae.siga.com.services;

import java.io.File;

import org.itcgae.siga.exception.BusinessException;

public interface IPFDService {
	
	public String firmarPDF(File fichero) throws BusinessException;

	public String obtenerDocumentoFirmado(String csv) throws BusinessException;	

}
