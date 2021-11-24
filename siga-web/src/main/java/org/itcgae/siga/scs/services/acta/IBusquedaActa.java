package org.itcgae.siga.scs.services.acta;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.ScsActacomision;


public interface IBusquedaActa {
	

	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public ComboDTO comboSufijoActa(HttpServletRequest request) throws SigaExceptions;

	public InsertResponseDTO guardarActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO anadirEJGPendientesCAJG(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO abrirActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;

	public UpdateResponseDTO cerrarActa(ActasItem actasItem, HttpServletRequest request) throws Exception;

	public ScsActacomision getActa(ActasItem actasItem, HttpServletRequest request);

	public DeleteResponseDTO borrarActas(List<ActasItem> actasItem, HttpServletRequest request) throws SigaExceptions;

	public String getNumActa(HttpServletRequest request) throws SigaExceptions;
	
	public String getNumActa(ActasItem actasItem, HttpServletRequest request) throws SigaExceptions;
	public CenInstitucion getAbreviatura(HttpServletRequest request);
	
}
