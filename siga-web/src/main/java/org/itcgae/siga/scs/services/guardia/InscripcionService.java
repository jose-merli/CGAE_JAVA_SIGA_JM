package org.itcgae.siga.scs.services.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionMod;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;

public interface InscripcionService {
	
	public InsertResponseDTO insertarInscripciones(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public HistoricoInscripcionDTO historicoInscripcion(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public InscripcionesDisponiblesDTO inscripcionesDisponibles(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public InscripcionesDisponiblesDTO inscripcionPorguardia(InscripcionGuardiaItem inscripcion, HttpServletRequest request);

	public ComboDTO comboLetrados(HttpServletRequest request, String idGuardia);
	
	public UpdateResponseDTO validarInscripciones(List<BusquedaInscripcionMod> validarbody, HttpServletRequest request);
	
	public UpdateResponseDTO denegarInscripciones(List<BusquedaInscripcionMod> denegarbody, HttpServletRequest request);
	
	public UpdateResponseDTO solicitarBajaInscripcion(List<BusquedaInscripcionMod> solicitarbajabody, HttpServletRequest request);
	
	public UpdateResponseDTO cambiarFechaInscripcion(List<BusquedaInscripcionMod> cambiarfechabody, HttpServletRequest request);

	public Boolean buscarSaltosCompensaciones(List<BusquedaInscripcionMod> buscarSaltosCompensaciones,
			HttpServletRequest request);

	public DeleteResponseDTO eliminarSaltosCompensaciones(List<BusquedaInscripcionMod> eliminarSaltosCompensaciones,
			HttpServletRequest request);

	public Boolean buscarTrabajosSJCS(List<BusquedaInscripcionMod> buscarTrabajosSJCS, HttpServletRequest request);

	public Boolean buscarGuardiasAsocTurnos(List<BusquedaInscripcionMod> buscarGuardiasAsocTurnos,
			HttpServletRequest request);
	

	

}
