package org.itcgae.siga.cen.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaFotoDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ITarjetaDatosGeneralesService {

	public UpdateResponseDTO loadPhotography(PersonaJuridicaFotoDTO  personaJuridicaFotoDTO, HttpServletRequest request, HttpServletResponse response);
	
	public UpdateResponseDTO uploadPhotography(MultipartHttpServletRequest request) throws IllegalStateException, IOException;
}


