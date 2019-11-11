package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AcreditacionDTO;
import org.itcgae.siga.DTO.scs.CosteFijoDTO;
import org.itcgae.siga.DTO.scs.CosteFijoItem;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionTiposAsistenciaService {
	public TiposAsistenciasDTO searchTiposAsistencia(boolean historico, HttpServletRequest request);

	public ComboDTO getTiposGuardia(HttpServletRequest request);


	public UpdateResponseDTO updateTiposAsistencias(TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteTipoAsitencia(TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request);

	public UpdateResponseDTO activateTipoAsitencia(TiposAsistenciasDTO tiposAsistenciasDTO, HttpServletRequest request);

	public InsertResponseDTO createTiposAsistencia(TiposAsistenciaItem tiposAsistenciaItem, HttpServletRequest request);

}
