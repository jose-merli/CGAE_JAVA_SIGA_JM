package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class FichaTarjetaPreciosDTO {

	private List<FichaTarjetaPreciosItem> fichaTarjetaPreciosItem = new ArrayList<FichaTarjetaPreciosItem>();
	private Error error = null;
	
	public List<FichaTarjetaPreciosItem> getFichaTarjetaPreciosItem() {
		return fichaTarjetaPreciosItem;
	}
	public void setFichaTarjetaPreciosItem(List<FichaTarjetaPreciosItem> fichaTarjetaPreciosItem) {
		this.fichaTarjetaPreciosItem = fichaTarjetaPreciosItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((fichaTarjetaPreciosItem == null) ? 0 : fichaTarjetaPreciosItem.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichaTarjetaPreciosDTO other = (FichaTarjetaPreciosDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (fichaTarjetaPreciosItem == null) {
			if (other.fichaTarjetaPreciosItem != null)
				return false;
		} else if (!fichaTarjetaPreciosItem.equals(other.fichaTarjetaPreciosItem))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FichaTarjetaPreciosDTO [fichaTarjetaPreciosItem=" + fichaTarjetaPreciosItem + ", error=" + error + "]";
	}
	
	
}
