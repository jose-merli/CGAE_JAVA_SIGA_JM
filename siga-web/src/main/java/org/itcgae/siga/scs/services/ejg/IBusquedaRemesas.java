package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.RemesaAccionItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmContador;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IBusquedaRemesas {
	
	ComboDTO comboEstado(HttpServletRequest request);

	RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);

	DeleteResponseDTO borrarRemesas(List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request);

	EstadoRemesaDTO listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);
	
	AdmContador getUltimoRegitroRemesa(HttpServletRequest request);

	UpdateResponseDTO guardarRemesa(RemesasItem remesasItem, HttpServletRequest request) throws Exception;
	
	EJGRemesaDTO getEJGRemesa(RemesasItem remesasItem, HttpServletRequest request);

	DeleteResponseDTO borrarExpedientesRemesa(List<EJGRemesaItem> remesasBusquedaItem, HttpServletRequest request);

	CheckAccionesRemesasDTO getAcciones(RemesasItem remesasItem, HttpServletRequest request);

	InsertResponseDTO ejecutaOperacionRemesa(RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions;

	InputStreamResource descargarLogErrores(RemesaAccionItem remesaAccionItem, HttpServletRequest request);
	
	ResponseEntity<InputStreamResource> descargar(RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions;
	
}
