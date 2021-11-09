package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaProductosCompraDTO {

	private List<ListaProductosCompraItem> listaProductosCompraItems = new ArrayList<ListaProductosCompraItem>();
	private Error error = null;
	
	public List<ListaProductosCompraItem> getListaProductosCompraItems() {
		return listaProductosCompraItems;
	}
	public void setListaProductosCompraItems(List<ListaProductosCompraItem> listaProductosCompraItems) {
		this.listaProductosCompraItems = listaProductosCompraItems;
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
		result = prime * result + ((listaProductosCompraItems == null) ? 0 : listaProductosCompraItems.hashCode());
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
		ListaProductosCompraDTO other = (ListaProductosCompraDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaProductosCompraItems == null) {
			if (other.listaProductosCompraItems != null)
				return false;
		} else if (!listaProductosCompraItems.equals(other.listaProductosCompraItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaProductosCompraDTO [listaProductosCompraItems=" + listaProductosCompraItems + ", error=" + error + "]";
	}
}