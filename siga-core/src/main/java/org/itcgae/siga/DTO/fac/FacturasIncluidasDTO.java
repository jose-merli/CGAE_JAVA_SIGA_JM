package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacturasIncluidasDTO {
	private List<FacturasIncluidasItem> facturasIncluidaItems = new ArrayList<>();
	private Error error = null;
	
	public List<FacturasIncluidasItem> getFacturasIncluidasItem() {
		return facturasIncluidaItems;
	}
	
	public void setFacturasIncluidasItem(List<FacturasIncluidasItem> facturaItems) {
		this.facturasIncluidaItems = facturaItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, facturasIncluidaItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturasIncluidasDTO other = (FacturasIncluidasDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(facturasIncluidaItems, other.facturasIncluidaItems);
	}
	
	@Override
	public String toString() {
		return "FacturasIncluidasDTO [facturasIncluidasItem=" + facturasIncluidaItems + ", error=" + error + "]";
	}
}
