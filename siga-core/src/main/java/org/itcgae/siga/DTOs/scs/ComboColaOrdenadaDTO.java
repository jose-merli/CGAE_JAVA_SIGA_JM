package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.com.SufijoItem;
import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

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
