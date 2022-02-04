package org.itcgae.siga.fac.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.CargaMasivaComprasItem;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.DTO.fac.FacRegistroFichContaDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IContabilidadExportacionService {
	
	public FacRegistroFichContaDTO search(FacRegistroFichConta facRegistroFichConta, HttpServletRequest request)
			throws Exception;

	public FacRegistroFichContaDTO maxIdContabilidad(HttpServletRequest request) throws Exception;
	
	public UpdateResponseDTO guardarRegistroFichConta(FacRegistroFichConta facRegistroFichConta,
			HttpServletRequest request) throws Exception;
	
	public DeleteResponseDTO desactivarReactivarRegistroFichConta(List <FacRegistroFichConta> facRegistrosFichConta,
			HttpServletRequest request) throws Exception;
	
	public ResponseEntity<InputStreamResource> descargarFicherosContabilidad(List <FacRegistroFichConta> facRegistrosFichConta,
			HttpServletRequest request);
}
