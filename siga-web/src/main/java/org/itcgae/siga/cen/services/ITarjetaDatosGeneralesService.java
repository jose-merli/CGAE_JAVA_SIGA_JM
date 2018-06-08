package org.itcgae.siga.cen.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ITarjetaDatosGeneralesService {

	public void loadPhotography(HttpServletRequest request, HttpServletResponse response);
	
	public UpdateResponseDTO uploadPhotography(MultipartHttpServletRequest request) throws IllegalStateException, IOException;
	
	public PersonaJuridicaDTO searchGeneralData(int numPagina, @RequestBody PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request);
	
	public UpdateResponseDTO createLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request);
	
	public UpdateResponseDTO updateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request);
}

