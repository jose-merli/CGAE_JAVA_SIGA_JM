package org.itcgae.siga.cen.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BancoBicDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITarjetaDatosBancariosService {

	
	public DatosBancariosDTO searchBanksData(int numPagina,DatosBancariosSearchDTO datosBancariosSearchDTO , HttpServletRequest request);
	
	public DeleteResponseDTO deleteBanksData(DatosBancariosDeleteDTO datosBancariosDeleteDTO , HttpServletRequest request);
	
	public DatosBancariosDTO searchGeneralData(int numPagina,DatosBancariosSearchDTO datosBancariosSearchDTO , HttpServletRequest request);

	public MandatosDTO searchMandatos(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO, HttpServletRequest request);

	public UpdateResponseDTO insertBanksData(DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws IOException, NamingException, SQLException, Exception;

	public ComboDTO getLabelEsquema(HttpServletRequest request);

	public UpdateResponseDTO updateMandatos(MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request);

	public BancoBicDTO searchBanks(DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO, HttpServletRequest request);

	public UpdateResponseDTO updateBanksData(DatosBancariosInsertDTO datosBancariosInsertDTO, HttpServletRequest request) throws Exception;



	
	
}


