package org.itcgae.siga.scs.services.intercambios;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ICargaDesignaProcuradores {

	RemesaResolucionDTO buscarDesignaProcuradores( RemesasResolucionItem remesasResolucionItem, HttpServletRequest request);

	String obtenerTipoPCAJG(HttpServletRequest request);

	EcomOperacionTipoaccionDTO obtenerDesignaProcuradores(HttpServletRequest request);

	AdmContador recuperarDatosContador(HttpServletRequest request);
	
}
