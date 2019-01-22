package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class EventoDTO   {
  
	private List<EventoItem> eventos = new ArrayList<EventoItem>();
	
	private Error error = null;

	public List<EventoItem> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoItem> eventos) {
		this.eventos = eventos;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public boolean equals(Object obj) {
		EventoDTO listaObj = (EventoDTO) obj;
		if(listaObj.getEventos() == eventos)
			return true;
		else
			return false;
	}
}

