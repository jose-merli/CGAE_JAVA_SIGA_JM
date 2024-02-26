package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;

public interface IBusquedaColegiadosExpressService {

	public ColegiadoJGDTO busquedaColegiadosExpress(String colegiadoJGItem, HttpServletRequest requestc);
	
	ColegiadosSJCSDTO busquedaColegiadoEJG(ColegiadosSJCSItem datos, HttpServletRequest request);
	
	ComboDTO comboTurnos(String pantalla, String idTurno, HttpServletRequest request);
	
	public String getTipoTurnos(String idTurno, HttpServletRequest request);
}
