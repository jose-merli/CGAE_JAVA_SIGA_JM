package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComunicacionCobroDTO {
	private List<ComunicacionCobroItem> comunicacionCobroItems = new ArrayList<>();
	private Error error = null;
	
	public List<ComunicacionCobroItem> getComunicacionCobroItems() {
		return comunicacionCobroItems;
	}
	
	public void setComunicacionCobroItems(List<ComunicacionCobroItem> comunicacionCobroItems) {
		this.comunicacionCobroItems = comunicacionCobroItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, comunicacionCobroItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComunicacionCobroDTO other = (ComunicacionCobroDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(comunicacionCobroItems, other.comunicacionCobroItems);
	}
	
	@Override
	public String toString() {
		return "ComunicacionCobroDTO [comunicacionCobroItems=" + comunicacionCobroItems + ", error=" + error + "]";
	}
	
}
