package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ComboColaOrdenadaDTO {
private List<ComboColaOrdenadaItem> colaorden = new ArrayList<ComboColaOrdenadaItem>();
	
	private Error error = null;		
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<ComboColaOrdenadaItem> getColaOrden() {
		return colaorden;
	}
	public void setColaOrden(List<ComboColaOrdenadaItem> colaorden) {
		this.colaorden = colaorden;
	}

}
