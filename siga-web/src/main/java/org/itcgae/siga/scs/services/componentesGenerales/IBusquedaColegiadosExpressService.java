package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.ColegiadoJGDTO;
import org.itcgae.siga.DTO.scs.ColegiadoJGItem;

public interface IBusquedaColegiadosExpressService {

	public ColegiadoJGDTO busquedaColegiadosExpress(String colegiadoJGItem, HttpServletRequest request);
}
