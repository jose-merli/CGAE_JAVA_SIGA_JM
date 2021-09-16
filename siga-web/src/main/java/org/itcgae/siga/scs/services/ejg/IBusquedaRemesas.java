package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.db.entities.AdmContador;


public interface IBusquedaRemesas {
	
	ComboDTO comboEstado(HttpServletRequest request);

	RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);

	DeleteResponseDTO borrarRemesas(List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request);

	EstadoRemesaDTO listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);
	
	AdmContador getUltimoRegitroRemesa(HttpServletRequest request);

	UpdateResponseDTO guardarRemesa(RemesasItem remesasItem, HttpServletRequest request);
	
}
