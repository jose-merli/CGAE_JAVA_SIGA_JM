package org.itcgae.siga.scs.services.oficio;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;

public interface IDesignacionesService{

	public JustificacionExpressItem busquedaJustificacionExpres(JustificacionExpressItem item, HttpServletRequest request);
	
	public DesignaItem busquedaDesignas(DesignaItem item, HttpServletRequest request);
	
}
