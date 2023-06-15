package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaServiciosDTO {
	
	private List<ListaServiciosItem> listaServiciosItems = new ArrayList<ListaServiciosItem>();
	private Error error = null;
	
	public List<ListaServiciosItem> getListaServiciosItems() {
		return listaServiciosItems;
	}
	public void setListaServiciosItems(List<ListaServiciosItem> listaServiciosItems) {
		this.listaServiciosItems = listaServiciosItems;
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
		result = prime * result + ((listaServiciosItems == null) ? 0 : listaServiciosItems.hashCode());
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
		ListaServiciosDTO other = (ListaServiciosDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaServiciosItems == null) {
			if (other.listaServiciosItems != null)
				return false;
		} else if (!listaServiciosItems.equals(other.listaServiciosItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaServiciosDTO [listaServiciosItems=" + listaServiciosItems + ", error=" + error + "]";
	}

}
