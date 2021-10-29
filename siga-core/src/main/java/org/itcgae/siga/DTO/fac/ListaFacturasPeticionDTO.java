package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListaFacturasPeticionDTO {

	private List<ListaFacturasPeticionItem> listaFacturasPeticionItems = new ArrayList<ListaFacturasPeticionItem>();
	private Error error = null;
	
	public List<ListaFacturasPeticionItem> getListaFacturasPeticionItem() {
		return listaFacturasPeticionItems;
	}
	public void setListaFacturasPeticionItem(List<ListaFacturasPeticionItem> listaFacturasPeticionItems) {
		this.listaFacturasPeticionItems = listaFacturasPeticionItems;
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
		result = prime * result + ((listaFacturasPeticionItems == null) ? 0 : listaFacturasPeticionItems.hashCode());
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
		ListaFacturasPeticionDTO other = (ListaFacturasPeticionDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (listaFacturasPeticionItems == null) {
			if (other.listaFacturasPeticionItems != null)
				return false;
		} else if (!listaFacturasPeticionItems.equals(other.listaFacturasPeticionItems))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaFacturasDTO [listaFacturasPeticionItems=" + listaFacturasPeticionItems + ", error=" + error + "]";
	}
	
}
