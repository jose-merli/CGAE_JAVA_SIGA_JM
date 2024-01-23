package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;


import org.itcgae.siga.DTO.scs.GuardiasDTO;
import org.itcgae.siga.DTO.scs.GuardiasItem;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.TurnosDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;

public interface IGestionTurnosService {
	public TurnosDTO busquedaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateTurnos(TurnosDTO turnosDTO, HttpServletRequest request);

	public TurnosDTO busquedaFichaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosGenerales(TurnosItem turnosItem, HttpServletRequest request);

	public InsertResponseDTO createTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO updateConfiguracion(TurnosItem turnosItem, HttpServletRequest request);
	
	public TurnosDTO busquedaColaOficio(TurnosItem turnosItem, HttpServletRequest request);
	
	public TurnosDTO busquedaColaOficioPrimerLetrado(TurnosItem turnosItem, HttpServletRequest request);
	
	public TurnosDTO busquedaColaGuardia(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateColaOficio(TurnosDTO turnosDTO, HttpServletRequest request);

	public UpdateResponseDTO eliminateColaGuardia(TurnosDTO turnosDTO, HttpServletRequest request);

	public GuardiasDTO busquedaGuardias(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateGuardia(GuardiasDTO guardiasDTO, HttpServletRequest request);

	public UpdateResponseDTO updateUltimo(TurnosItem turnosItem, HttpServletRequest request);
	
	public UpdateResponseDTO updateUltimoGuardias(TurnosItem turnosItem, HttpServletRequest request);

	public Error guardartarjetaPesos2(HttpServletRequest request, TarjetaPesosDTO tarjetaPesos); 
	
	public UpdateResponseDTO changeRequisitoGuardias(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO activarTurnos(TurnosDTO turnosDTO, HttpServletRequest request);
}
