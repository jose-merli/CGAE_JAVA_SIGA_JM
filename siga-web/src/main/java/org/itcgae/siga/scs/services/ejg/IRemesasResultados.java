package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IRemesasResultados {
	
	RemesaResultadoDTO buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request);
	
	ResponseEntity<InputStreamResource> descargarFicheros(List<RemesasResultadoItem> listaRemesasResultadoItem,
			HttpServletRequest request);

}
