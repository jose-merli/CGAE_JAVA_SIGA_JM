package org.itcgae.siga.cen.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.DatosRegistralesDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface ITarjetaDatosRegistralesService {

	public ComboDTO getActividadProfesionalPer(PersonaJuridicaActividadDTO personaJuridicaActividadDTO, HttpServletRequest request);
	
	public ComboDTO getActividadProfesional(HttpServletRequest request);
	
	public DatosRegistralesDTO searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO, HttpServletRequest request);
}
