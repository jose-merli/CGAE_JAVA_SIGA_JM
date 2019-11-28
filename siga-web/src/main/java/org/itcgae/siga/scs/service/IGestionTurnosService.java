package org.itcgae.siga.scs.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.TurnosDTO;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.TarjetaPesosDTO;
import org.itcgae.siga.DTOs.gen.Error;

public interface IGestionTurnosService {
	public TurnosDTO busquedaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateTurnos(TurnosDTO turnosDTO, HttpServletRequest request);

	public TurnosDTO busquedaFichaTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO updateDatosGenerales(TurnosItem turnosItem, HttpServletRequest request);

	public InsertResponseDTO createTurnos(TurnosItem turnosItem, HttpServletRequest request);

	public Error guardartarjetaPesos(HttpServletRequest request,TarjetaPesosDTO tarjetaPesos);

	public UpdateResponseDTO updateConfiguracion(TurnosItem turnosItem, HttpServletRequest request);
	
	public TurnosDTO busquedaColaOficio(TurnosItem turnosItem, HttpServletRequest request);
	
	public TurnosDTO busquedaColaGuardia(TurnosItem turnosItem, HttpServletRequest request);

	public UpdateResponseDTO eliminateColaOficio(TurnosDTO turnosDTO, HttpServletRequest request);

	public UpdateResponseDTO eliminateColaGuardia(TurnosDTO turnosDTO, HttpServletRequest request); 
}
