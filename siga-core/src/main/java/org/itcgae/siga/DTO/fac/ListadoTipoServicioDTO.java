package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListadoTipoServicioDTO {
	private List<TiposServiciosItem> tiposServiciosItems = new ArrayList<TiposServiciosItem>();
	private Error error = null;
	
	public List<TiposServiciosItem> getTiposServiciosItems() {
		return tiposServiciosItems;
	}
	
	public void setTiposServiciosItems(List<TiposServiciosItem> tiposServiciosItems) {
		this.tiposServiciosItems = tiposServiciosItems;
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
		result = prime * result + ((tiposServiciosItems == null) ? 0 : tiposServiciosItems.hashCode());
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
		ListadoTipoServicioDTO other = (ListadoTipoServicioDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (tiposServiciosItems == null) {
			if (other.tiposServiciosItems != null)
				return false;
		} else if (!tiposServiciosItems.equals(other.tiposServiciosItems))
			return false;
		return true;
	}
	
}
