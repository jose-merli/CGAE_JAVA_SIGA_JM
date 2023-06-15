package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Primary
public interface IRemesasResoluciones {
	
	RemesaResolucionDTO buscarRemesasResoluciones( RemesasResolucionItem remesasResolucionItem, HttpServletRequest request);

	ResponseEntity<InputStreamResource> descargarRemesasResolucion(List<RemesasResolucionItem> remesasResolucionItem,
			HttpServletRequest request);

	EcomOperacionTipoaccionDTO obtenerOperacionTipoAccion(HttpServletRequest request);

	EcomOperacionTipoaccionDTO obtenerResoluciones(HttpServletRequest request);

	UpdateResponseDTO guardarRemesaResolucion(RemesasResolucionItem remesasResolucionItem,
			MultipartHttpServletRequest request);

	UpdateResponseDTO actualizarRemesaResolucion(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request);

	AdmContador recuperarDatosContador(HttpServletRequest request);

}
