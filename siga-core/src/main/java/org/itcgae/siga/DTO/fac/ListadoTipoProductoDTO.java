package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListadoTipoProductoDTO {
	private List<TiposProductosItem> tiposProductosItems = new ArrayList<TiposProductosItem>();
	private Error error = null;
	
	public List<TiposProductosItem> getTiposProductosItems() {
		return tiposProductosItems;
	}
	
	public void setTiposProductosItems(List<TiposProductosItem> tiposProductosItems) {
		this.tiposProductosItems = tiposProductosItems;
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
		result = prime * result + ((tiposProductosItems == null) ? 0 : tiposProductosItems.hashCode());
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
		ListadoTipoProductoDTO other = (ListadoTipoProductoDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (tiposProductosItems == null) {
			if (other.tiposProductosItems != null)
				return false;
		} else if (!tiposProductosItems.equals(other.tiposProductosItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TiposProductosDTO [tiposProductosItems=" + tiposProductosItems + ", error=" + error + "]";
	}
	
}
