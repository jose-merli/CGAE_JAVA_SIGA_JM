package org.itcgae.siga.scs.services.guardia;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaDTO;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseDTO;

public interface AsistenciaService {
	
	public ComboDTO getTurnosByColegiadoFecha(HttpServletRequest request, String guardiaDia, String idPersona);
	
	public ComboDTO getTiposAsistenciaColegio (HttpServletRequest request, String idTurno, String idGuardia);
	
	public ComboDTO getColegiadosGuardiaDia (HttpServletRequest request, String idTurno, String idGuardia, String guardiaDia);
	
	public TarjetaAsistenciaResponseDTO searchAsistencias (HttpServletRequest request, FiltroAsistenciaItem filtro);

	public ComboDTO getJuzgados (HttpServletRequest request, String idTurno);
	
	public ComboDTO getComisarias (HttpServletRequest request, String idTurno);
}
