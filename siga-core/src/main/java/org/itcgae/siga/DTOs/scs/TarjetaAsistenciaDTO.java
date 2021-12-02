package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaAsistenciaDTO {

	private List<TarjetaAsistenciaItem> tarjetaAsistenciaItems = new ArrayList<TarjetaAsistenciaItem>();
	private org.itcgae.siga.DTOs.gen.Error error = null;
	
	
	@JsonProperty("tarjetaAsistenciaItems")
	public List<TarjetaAsistenciaItem> getTarjetaAsistenciaItems() {
		return tarjetaAsistenciaItems;
	}
	
	public void setTarjetaAsistenciaItems(List<TarjetaAsistenciaItem> tarjetaAsistenciaItems) {
		this.tarjetaAsistenciaItems = tarjetaAsistenciaItems;
	}
	
	@JsonProperty("error")
	public org.itcgae.siga.DTOs.gen.Error getError() {
		return error;
	}
	public void setError(org.itcgae.siga.DTOs.gen.Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(tarjetaAsistenciaItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaAsistenciaDTO other = (TarjetaAsistenciaDTO) obj;
		return Objects.equals(tarjetaAsistenciaItems, other.tarjetaAsistenciaItems);
	}
	
	@Override
	public String toString() {
		return "TarjetaAsistenciaDTO [tarjetaAsistenciaItems=" + tarjetaAsistenciaItems + ", error=" + error + "]";
	}

}
