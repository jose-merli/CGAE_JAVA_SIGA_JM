package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;

public interface GuardiasColegiadoService {
	//obtener datos para las tarjetas de la ficha
	public GuardiasDTO getGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	public TurnosDTO getTurnoGuardiaColeg(TurnosItem turnosItem, HttpServletRequest request);
	//public GuardiasDTO getCalendarioColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	public ColegiadoDTO getColegiado(ColegiadoItem guardiasItem, HttpServletRequest request);
	//public PermutaDTO getPemutasColeg(PermutaItem permutaItem, HttpServletRequest request);
	
	//acciones tarjeta Datos Generales de guardias de colegiado
	public UpdateResponseDTO  updateGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	public InsertResponseDTO  insertGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	
	//acciones tarjeta Sustituciones Guardias de Colegiado
	public UpdateResponseDTO  sustituirGuardiaColeg(GuardiasItem guardiasItem, HttpServletRequest request);
	
	//acciones tarjeta Permutas Guardias de Colegiado.
	//public UpdateResponseDTO  validarPermuta(PermutaDTO permutaDTO, HttpServletRequest request);
	

}
