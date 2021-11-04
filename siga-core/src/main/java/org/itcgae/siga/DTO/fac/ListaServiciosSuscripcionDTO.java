package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaServiciosSuscripcionDTO {

	private List<ListaServiciosSuscripcionItem> listaServiciosSuscripcionItems = new ArrayList<ListaServiciosSuscripcionItem>();
	private Error error = null;
	
	public List<ListaServiciosSuscripcionItem> getListaServiciosSuscripcionItems() {
		return listaServiciosSuscripcionItems;
	}
	public void setListaServiciosSuscripcionItems(List<ListaServiciosSuscripcionItem> listaServiciosSuscripcionItems) {
		this.listaServiciosSuscripcionItems = listaServiciosSuscripcionItems;
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
		result = prime * result + ((listaServiciosSuscripcionItems == null) ? 0 : listaServiciosSuscripcionItems.hashCode());
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
		ListaServiciosSuscripcionDTO other = (ListaServiciosSuscripcionDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaServiciosSuscripcionItems == null) {
			if (other.listaServiciosSuscripcionItems != null)
				return false;
		} else if (!listaServiciosSuscripcionItems.equals(other.listaServiciosSuscripcionItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaServiciosSuscripcionDTO [listaServiciosSuscripcionItems=" + listaServiciosSuscripcionItems + ", error=" + error + "]";
	}
	
}
