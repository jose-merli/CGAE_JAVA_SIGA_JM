package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AcreditacionDTO;
import org.itcgae.siga.DTO.scs.CosteFijoDTO;
import org.itcgae.siga.DTO.scs.CosteFijoItem;
import org.itcgae.siga.DTO.scs.FundamentoResolucionItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTO.scs.TiposAsistenciasDTO;
import org.itcgae.siga.DTO.scs.TurnosDTO;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesDTO;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IGestionTurnosService {
	public TurnosDTO busquedaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateTurnos(TurnosDTO turnosDTO, HttpServletRequest request);

	public TurnosDTO busquedaFichaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosGenerales(TurnosItem turnosItem, HttpServletRequest request);

	public InsertResponseDTO createTurnos(TurnosItem turnosItem, HttpServletRequest request);


}
