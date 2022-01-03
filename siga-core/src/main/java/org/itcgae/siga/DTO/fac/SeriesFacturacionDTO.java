package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class SeriesFacturacionDTO {
	private List<SerieFacturacionItem> serieFacturacionItems = new ArrayList<>();
	private Error error = null;
	
	public List<SerieFacturacionItem> getSerieFacturacionItems() {
		return serieFacturacionItems;
	}
	
	public void setSerieFacturacionItems(List<SerieFacturacionItem> serieFacturacionItems) {
		this.serieFacturacionItems = serieFacturacionItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, serieFacturacionItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeriesFacturacionDTO other = (SeriesFacturacionDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(serieFacturacionItems, other.serieFacturacionItems);
	}
	
	@Override
	public String toString() {
		return "SeriesFacturacionDTO [serieFacturacionItems=" + serieFacturacionItems + ", error=" + error + "]";
	}
	
}
