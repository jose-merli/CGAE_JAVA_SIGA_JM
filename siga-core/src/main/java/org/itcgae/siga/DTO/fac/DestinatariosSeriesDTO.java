package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DestinatariosSeriesDTO {

	private List<SerieFacturacionItem> destinatariosSeriesItems = new ArrayList<>();
	private Error error = null;
	
	public List<SerieFacturacionItem> getDestinatariosSeriesItems() {
		return destinatariosSeriesItems;
	}
	
	public void setDestinatariosSeriesItems(List<SerieFacturacionItem> destinatariosSeriesItems) {
		this.destinatariosSeriesItems = destinatariosSeriesItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(destinatariosSeriesItems, error);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DestinatariosSeriesDTO other = (DestinatariosSeriesDTO) obj;
		return Objects.equals(destinatariosSeriesItems, other.destinatariosSeriesItems)
				&& Objects.equals(error, other.error);
	}
	
	@Override
	public String toString() {
		return "DestinatariosSeriesDTO [destinatariosSeriesItems=" + destinatariosSeriesItems + ", error=" + error
				+ "]";
	}
	
}
