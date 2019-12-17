package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;

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

	public Error guardartarjetaPesos(HttpServletRequest request,TarjetaPesosDTO tarjetaPesos);

}
