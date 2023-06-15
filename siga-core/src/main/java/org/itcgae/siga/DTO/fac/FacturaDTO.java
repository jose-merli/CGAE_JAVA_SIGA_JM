package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacturaDTO {
	private List<FacturaItem> facturaItems = new ArrayList<>();
	private Error error = null;
	
	public List<FacturaItem> getFacturasItems() {
		return facturaItems;
	}
	
	public void setFacturasItems(List<FacturaItem> facturaItems) {
		this.facturaItems = facturaItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, facturaItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturaDTO other = (FacturaDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(facturaItems, other.facturaItems);
	}
	
	@Override
	public String toString() {
		return "FacturasDTO [facturasItems=" + facturaItems + ", error=" + error + "]";
	}
	
}
