package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EstadosPagosDTO {
	private List<EstadosPagosItem> estadosPagosItems = new ArrayList<>();
	private Error error = null;
	
	public List<EstadosPagosItem> getEstadosPagosItems() {
		return estadosPagosItems;
	}
	
	public void setEstadosPagosItems(List<EstadosPagosItem> estadosPagosItems) {
		this.estadosPagosItems = estadosPagosItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, estadosPagosItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadosPagosDTO other = (EstadosPagosDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(estadosPagosItems, other.estadosPagosItems);
	}
	
	@Override
	public String toString() {
		return "EstadosPagosDTO [estadosPagosItems=" + estadosPagosItems + ", error=" + error + "]";
	}
	
}
