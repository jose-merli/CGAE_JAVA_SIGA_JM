package org.itcgae.siga.scs.services.acta;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.commons.utils.SigaExceptions;


public interface IBusquedaActa {
	

	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public DeleteResponseDTO borrarActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public ComboDTO comboSufijoActa(HttpServletRequest request) throws SigaExceptions;

	public InsertResponseDTO guardarActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO anadirEJGPendientesCAJG(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO abrirActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO cerrarActa(ActasItem actasItem, HttpServletRequest request) throws Exception;
	
}
