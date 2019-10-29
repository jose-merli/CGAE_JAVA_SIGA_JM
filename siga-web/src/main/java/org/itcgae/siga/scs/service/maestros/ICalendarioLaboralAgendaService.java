package org.itcgae.siga.scs.service.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.age.EventoItem;

public interface ICalendarioLaboralAgendaService {
	
	public EventoDTO searchFestivos(EventoItem eventoItem, HttpServletRequest request);
	
	public UpdateResponseDTO deleteFestivos(EventoDTO eventoDTO, HttpServletRequest request);

	public UpdateResponseDTO activateFestivos(EventoDTO eventoDTO, HttpServletRequest request);

}
