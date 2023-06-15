package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaSuscripcionesDTO {
	
	private List<ListaSuscripcionesItem> listaSuscripcionesItems = new ArrayList<ListaSuscripcionesItem>();
	private Error error = null;
	
	public List<ListaSuscripcionesItem> getListaSuscripcionesItems() {
		return listaSuscripcionesItems;
	}
	public void setListaSuscripcionesItems(List<ListaSuscripcionesItem> listaSuscripcionesItems) {
		this.listaSuscripcionesItems = listaSuscripcionesItems;
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
		result = prime * result + ((listaSuscripcionesItems == null) ? 0 : listaSuscripcionesItems.hashCode());
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
		ListaSuscripcionesDTO other = (ListaSuscripcionesDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaSuscripcionesItems == null) {
			if (other.listaSuscripcionesItems != null)
				return false;
		} else if (!listaSuscripcionesItems.equals(other.listaSuscripcionesItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaSuscripcionesDTO [listaSuscripcionesItems=" + listaSuscripcionesItems + ", error=" + error + "]";
	}

}
