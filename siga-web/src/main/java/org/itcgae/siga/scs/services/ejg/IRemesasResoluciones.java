package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;

public interface IRemesasResoluciones {
	
	RemesaResolucionDTO buscarRemesasResoluciones( RemesasResolucionItem remesasResolucionItem, HttpServletRequest request);

}
