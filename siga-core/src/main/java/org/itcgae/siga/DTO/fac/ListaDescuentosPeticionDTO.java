package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaDescuentosPeticionDTO {

	private List<ListaDescuentosPeticionItem> listaDescuentosPeticionItems = new ArrayList<ListaDescuentosPeticionItem>();
	private Error error = null;
	
	public List<ListaDescuentosPeticionItem> getListaDescuentosPeticionItem() {
		return listaDescuentosPeticionItems;
	}
	public void setListaDescuentosPeticionItem(List<ListaDescuentosPeticionItem> listaDescuentosPeticionItems) {
		this.listaDescuentosPeticionItems = listaDescuentosPeticionItems;
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
		result = prime * result + ((listaDescuentosPeticionItems == null) ? 0 : listaDescuentosPeticionItems.hashCode());
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
		ListaDescuentosPeticionDTO other = (ListaDescuentosPeticionDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaDescuentosPeticionItems == null) {
			if (other.listaDescuentosPeticionItems != null)
				return false;
		} else if (!listaDescuentosPeticionItems.equals(other.listaDescuentosPeticionItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaDescuentossDTO [listaDescuentosPeticionItems=" + listaDescuentosPeticionItems + ", error=" + error + "]";
	}
}
