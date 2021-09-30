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
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.GenParametros;


public interface IBusquedaRemesas {
	
	ComboDTO comboEstado(HttpServletRequest request);

	RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);

	DeleteResponseDTO borrarRemesas(List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request);

	EstadoRemesaDTO listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request);
	
	AdmContador getUltimoRegitroRemesa(HttpServletRequest request);

	UpdateResponseDTO guardarRemesa(RemesasItem remesasItem, HttpServletRequest request);
	
	EJGRemesaDTO getEJGRemesa(RemesasItem remesasItem, HttpServletRequest request);

	DeleteResponseDTO borrarExpedientesRemesa(List<EJGRemesaItem> remesasBusquedaItem, HttpServletRequest request);

	CheckAccionesRemesasDTO checkAcciones(RemesasItem remesasItem, HttpServletRequest request);
	
}
