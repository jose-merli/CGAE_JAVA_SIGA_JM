package org.itcgae.siga.scs.services.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;


public interface IBusquedaRemesas {
	
	ComboDTO comboEstado(HttpServletRequest request);

	RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);

	DeleteResponseDTO borrarRemesas(List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request);
	
}
