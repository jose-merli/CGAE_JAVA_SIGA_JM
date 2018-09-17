package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesDTO;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesSearchDTO;

public interface IFichaDatosCurricularesService {

	
	public FichaDatosCurricularesDTO searchDatosCurriculares(FichaDatosCurricularesSearchDTO fichaDatosCurricularesSearchDTO, HttpServletRequest request); 

	
	
}


