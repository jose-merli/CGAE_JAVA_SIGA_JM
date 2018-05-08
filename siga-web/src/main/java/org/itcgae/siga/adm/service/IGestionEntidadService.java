package org.itcgae.siga.adm.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.EntidadLenguajeInstitucionDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IGestionEntidadService {

	public EntidadLenguajeInstitucionDTO getInstitutionLenguage(HttpServletRequest request);
	
	public ComboDTO getLenguages(HttpServletRequest request);
	
	public CreateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IOException;
	
	public CreateResponseDTO uploadLenguage(String idLenguaje, HttpServletRequest request);
}
