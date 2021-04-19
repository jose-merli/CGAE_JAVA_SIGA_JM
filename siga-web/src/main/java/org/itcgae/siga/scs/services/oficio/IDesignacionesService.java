package org.itcgae.siga.scs.services.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;

public interface IDesignacionesService {

	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request);

	public List<DesignaItem> busquedaDesignas(DesignaItem item, HttpServletRequest request);

	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request, Boolean historico);

	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request);

	public UpdateResponseDTO deleteContrario(ScsContrariosdesigna[] item, HttpServletRequest request);

	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem designa, HttpServletRequest request);
	
	public DeleteResponseDTO deleteInteresado(ScsDefendidosdesigna item, HttpServletRequest request);

	public InsertResponseDTO insertInteresado(ScsDefendidosdesigna item, HttpServletRequest request);

	public InsertResponseDTO insertContrario(ScsContrariosdesigna contrario, HttpServletRequest request);
	
	public ActuacionDesignaDTO busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request);

	public UpdateResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request);

	public DeleteResponseDTO eliminarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			HttpServletRequest request);

	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request);
	
	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request);

	ComboDTO comboTipoMotivo(HttpServletRequest request);

	public UpdateResponseDTO guardarProcurador(List<ProcuradorItem> procurador, HttpServletRequest request);
	
	public List<DesignaItem> getDatosAdicionales(DesignaItem designa, HttpServletRequest request);
}
