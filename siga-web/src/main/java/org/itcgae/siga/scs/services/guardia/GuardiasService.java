package org.itcgae.siga.scs.services.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DeleteIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.GuardiasDTO;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDTO;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaveIncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.TurnosDTO;

public interface GuardiasService {

	public GuardiasDTO searchGuardias(GuardiasItem guardiasItem, HttpServletRequest request);

	public UpdateResponseDTO deleteGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO activateGuardias(GuardiasDTO guardiasDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateGuardia(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateColaGuardia(InscripcionGuardiaDTO guardiaBody, HttpServletRequest request);
	
	public GuardiasItem detalleGuardia(GuardiasItem guardiaTurno, HttpServletRequest request);

	public InsertResponseDTO createGuardia(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public GuardiasItem resumenGuardia(GuardiasItem guardiasItem, HttpServletRequest request);
		
	public GuardiasDTO tarjetaIncompatibilidades(String idGuardia, HttpServletRequest request);
	
	public ComboDTO getBaremos(String idGuardia, HttpServletRequest request);
	
	public DatosCalendarioItem getCalendario(String idGuardia, HttpServletRequest request);

	public GuardiasItem resumenConfiguracionCola(GuardiasItem guardia, HttpServletRequest request);
	
	public InscripcionGuardiaDTO searchColaGuardia(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateUltimoCola(GuardiasItem guardiasItem, HttpServletRequest request);
	
	public GuardiasDTO resumenIncompatibilidades(GuardiasItem guardiasItem, HttpServletRequest request);

	public TurnosDTO resumenTurno(String idTurno, HttpServletRequest request);

	public UpdateResponseDTO guardarColaGuardias(List<InscripcionGuardiaItem> inscripciones, HttpServletRequest request);

	public IncompatibilidadesDTO getIncompatibilidades(IncompatibilidadesDatosEntradaItem incompBody,
			HttpServletRequest request);

	public DeleteResponseDTO deleteIncompatibilidades(
			DeleteIncompatibilidadesDatosEntradaItem deleteIncompatibilidadesBody, HttpServletRequest request);

	public DeleteResponseDTO saveIncompatibilidades(SaveIncompatibilidadesDatosEntradaItem incompatibilidadesBody,
			HttpServletRequest request);


}
