package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IRemesasResultados {
	
	RemesaResultadoDTO buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request);
	
	ResponseEntity<InputStreamResource> descargarFicheros(List<RemesasResultadoItem> listaRemesasResultadoItem,
			HttpServletRequest request);
	
	AdmContador recuperarDatosContador(HttpServletRequest request);
	
	UpdateResponseDTO guardarRemesaResultado(RemesasResolucionItem remesasResolucionItem, MultipartHttpServletRequest request);

}
