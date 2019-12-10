package org.itcgae.siga.scs.services.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTO.scs.AsuntosJusticiableItem;

public interface BusquedaAsuntosService {

	public AsuntosJusticiableDTO searchClaveAsuntosEJG(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosAsistencias(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosDesignaciones(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);

	public AsuntosJusticiableDTO searchClaveAsuntosSOJ(HttpServletRequest request,
			AsuntosJusticiableItem asuntosJusticiableItem);
	
	

}
