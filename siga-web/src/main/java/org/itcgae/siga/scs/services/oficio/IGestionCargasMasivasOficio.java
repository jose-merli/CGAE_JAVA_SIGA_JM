package org.itcgae.siga.scs.services.oficio;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Primary
public interface IGestionCargasMasivasOficio {
	
	public ResponseEntity<InputStreamResource>  descargarModelo(HttpServletRequest requestCargasMasivas, String turnos, String guardias, String tipo);

	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector, String tipo)
			throws BusinessException;

	public DeleteResponseDTO uploadFileIT(MultipartHttpServletRequest request) throws IllegalStateException, IOException;

	public DeleteResponseDTO uploadFileBT(MultipartHttpServletRequest request) throws IllegalStateException, IOException;


}
