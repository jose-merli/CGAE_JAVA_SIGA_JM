package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaCompraProductosDTO {
	
	private List<ListaCompraProductosItem> ListaCompraProductosItems = new ArrayList<ListaCompraProductosItem>();
	private Error error = null;
	
	public List<ListaCompraProductosItem> getListaCompraProductosItems() {
		return ListaCompraProductosItems;
	}
	public void setListaCompraProductosItems(List<ListaCompraProductosItem> ListaCompraProductosItems) {
		this.ListaCompraProductosItems = ListaCompraProductosItems;
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
		result = prime * result + ((ListaCompraProductosItems == null) ? 0 : ListaCompraProductosItems.hashCode());
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
		ListaCompraProductosDTO other = (ListaCompraProductosDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (ListaCompraProductosItems == null) {
			if (other.ListaCompraProductosItems != null)
				return false;
		} else if (!ListaCompraProductosItems.equals(other.ListaCompraProductosItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaCompraProductosDTO [ListaCompraProductosItems=" + ListaCompraProductosItems + ", error=" + error + "]";
	}
	
}

