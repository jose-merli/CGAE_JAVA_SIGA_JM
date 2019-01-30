package org.itcgae.siga.DTOs.com;

import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ClaseComunicacionesDTO {
	
	private List<ClaseComunicacionItem> clasesComunicaciones;
	private Error error;	
	
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<ClaseComunicacionItem> getClasesComunicaciones() {
		return clasesComunicaciones;
	}
	public void setClasesComunicaciones(List<ClaseComunicacionItem> clasesComunicaciones) {
		this.clasesComunicaciones = clasesComunicaciones;
	}

}
