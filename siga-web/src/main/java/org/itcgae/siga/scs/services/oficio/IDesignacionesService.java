package org.itcgae.siga.scs.services.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;

public interface IDesignacionesService {

	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request);

	public List<DesignaItem> busquedaDesignas(DesignaItem item, HttpServletRequest request);

	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request);

	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request);

	public ActuacionDesignaDTO busquedaActDesigna(HttpServletRequest request);
}
