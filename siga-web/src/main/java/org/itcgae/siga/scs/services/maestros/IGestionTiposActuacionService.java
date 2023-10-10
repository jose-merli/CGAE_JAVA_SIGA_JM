package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTOs.scs.TiposActuacionDTO;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TiposAsistenciasDTO;

public interface IGestionTiposActuacionService {
	public TiposActuacionDTO searchTiposActuacion(boolean historico, HttpServletRequest request);

	public ComboDTO getTiposActuacion(HttpServletRequest request);
	
	public UpdateResponseDTO updateTiposActuaciones(TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteTipoActuacion(TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request);

	public UpdateResponseDTO activateTipoActuacion(TiposActuacionDTO tiposActuacionDTO, HttpServletRequest request);

	public InsertResponseDTO createTiposActuacion(TiposActuacionItem tiposActuacionItem, HttpServletRequest request);

	public StringDTO searchTipoActuacionPorDefecto(String descripcionTipoAsistencia, String juzgadoComisaria, HttpServletRequest request);

	

}
