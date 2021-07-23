package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.HistoricoInscripcionDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionesDisponiblesDTO;

public interface InscripcionService {
	
	public InsertResponseDTO insertarInscripciones(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public HistoricoInscripcionDTO historicoInscripcion(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public InscripcionesDisponiblesDTO inscripcionesDisponibles(InscripcionGuardiaItem inscripcion, HttpServletRequest request);
	
	public InscripcionesDisponiblesDTO inscripcionPorguardia(InscripcionGuardiaItem inscripcion, HttpServletRequest request);

	public ComboDTO comboLetrados(HttpServletRequest request, String idGuardia);

}
