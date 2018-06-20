package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;

public interface ITarjetaDatosBancariosService {

	
	public DatosBancariosDTO searchBanksData(int numPagina,DatosBancariosSearchDTO datosBancariosSearchDTO , HttpServletRequest request);
	
	public DeleteResponseDTO deleteBanksData(DatosBancariosDeleteDTO datosBancariosDeleteDTO , HttpServletRequest request);
	
	public DatosBancariosDTO searchGeneralData(int numPagina,DatosBancariosSearchDTO datosBancariosSearchDTO , HttpServletRequest request);

	public MandatosDTO searchMandatos(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request);	
	
}


