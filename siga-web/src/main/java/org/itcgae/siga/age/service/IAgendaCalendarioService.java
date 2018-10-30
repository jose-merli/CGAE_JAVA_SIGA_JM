package org.itcgae.siga.age.service;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.age.CalendarDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;

public interface IAgendaCalendarioService {
	
	public CalendarDTO getCalendariosByIdInstitucion(HttpServletRequest request);
	
	public EventoDTO getEventosByIdCalendario(String idCalendario);
	
}
