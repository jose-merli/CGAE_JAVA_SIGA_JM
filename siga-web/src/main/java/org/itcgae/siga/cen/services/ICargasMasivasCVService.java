package org.itcgae.siga.cen.services;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ICargasMasivasCVService {

	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public ResponseEntity<InputStreamResource> generateExcelCV();
	
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException;
	
	public CargaMasivaDTO searchCV(CargaMasivaItem cargaMasivaItem, HttpServletRequest request);

}
