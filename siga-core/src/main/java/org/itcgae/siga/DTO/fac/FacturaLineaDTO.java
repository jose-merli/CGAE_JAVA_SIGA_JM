package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacturaLineaDTO {
	private List<FacturaLineaItem> facturaLineaItems = new ArrayList<>();
	private Error error = null;
	
	public List<FacturaLineaItem> getFacturasLineasItems() {
		return facturaLineaItems;
	}
	
	public void setFacturasLineasItems(List<FacturaLineaItem> facturaLineaItems) {
		this.facturaLineaItems = facturaLineaItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, facturaLineaItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturaLineaDTO other = (FacturaLineaDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(facturaLineaItems, other.facturaLineaItems);
	}
	
	@Override
	public String toString() {
		return "FacturaLineaDTO [facturaLineaItems=" + facturaLineaItems + ", error=" + error + "]";
	}
	
}
