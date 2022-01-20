package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaProductosDTO {
	
	private List<ListaProductosItem> listaProductosItems = new ArrayList<ListaProductosItem>();
	private Error error = null;
	
	public List<ListaProductosItem> getListaProductosItems() {
		return listaProductosItems;
	}
	public void setListaProductosItems(List<ListaProductosItem> listaProductosItems) {
		this.listaProductosItems = listaProductosItems;
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
		result = prime * result + ((listaProductosItems == null) ? 0 : listaProductosItems.hashCode());
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
		ListaProductosDTO other = (ListaProductosDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaProductosItems == null) {
			if (other.listaProductosItems != null)
				return false;
		} else if (!listaProductosItems.equals(other.listaProductosItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaProductosDTO [listaProductosItems=" + listaProductosItems + ", error=" + error + "]";
	}
	
}
