package org.itcgae.siga.scs.services.intercambios;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Primary
public interface ICargaDesignaProcuradores {

	RemesaResolucionDTO buscarDesignaProcuradores( RemesasResolucionItem remesasResolucionItem, HttpServletRequest request);

	String obtenerTipoPCAJG(HttpServletRequest request);

	EcomOperacionTipoaccionDTO obtenerDesignaProcuradores(HttpServletRequest request);

	AdmContador recuperarDatosContador(HttpServletRequest request);

	EcomOperacionTipoaccionDTO obtenerOperacionTipoAccion(HttpServletRequest request);

	UpdateResponseDTO guardarDesignaProcuradores(RemesasResolucionItem remesasResolucionItem,
			MultipartHttpServletRequest request);
	
}
