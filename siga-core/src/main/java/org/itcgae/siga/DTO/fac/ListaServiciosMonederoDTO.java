package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaServiciosMonederoDTO {

	private List<ListaServiciosMonederoItem> listaServiciosMonederoItems = new ArrayList<ListaServiciosMonederoItem>();
	private Error error = null;
	
	public List<ListaServiciosMonederoItem> getListaServiciosMonederoItems() {
		return listaServiciosMonederoItems;
	}
	public void setListaServiciosMonederoItems(List<ListaServiciosMonederoItem> listaServiciosMonederoItems) {
		this.listaServiciosMonederoItems = listaServiciosMonederoItems;
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
		result = prime * result + ((listaServiciosMonederoItems == null) ? 0 : listaServiciosMonederoItems.hashCode());
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
		ListaServiciosMonederoDTO other = (ListaServiciosMonederoDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaServiciosMonederoItems == null) {
			if (other.listaServiciosMonederoItems != null)
				return false;
		} else if (!listaServiciosMonederoItems.equals(other.listaServiciosMonederoItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaServiciosMonederoDTO [listaServiciosMonederoItems=" + listaServiciosMonederoItems + ", error=" + error + "]";
	}
}
