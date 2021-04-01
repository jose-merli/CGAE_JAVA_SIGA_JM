package org.itcgae.siga.scs.services.oficio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;

public interface IDesignacionesService{

	public List<DesignaItem> busquedaJustificacionExpres(JustificacionExpressItem item, HttpServletRequest request);
	
	public List<DesignaItem> busquedaDesignas(DesignaItem item, HttpServletRequest request);
	
	public ComboDTO modulo(HttpServletRequest request);

	public ComboDTO comboProcedimientos(HttpServletRequest request);
}
