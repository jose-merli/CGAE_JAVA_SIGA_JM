package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FicherosDevolucionesDTO {
	private List<FicherosDevolucionesItem> ficherosDevolucionesItems = new ArrayList<>();
	private Error error = null;
	
	public List<FicherosDevolucionesItem> getFicherosDevolucionesItems() {
		return ficherosDevolucionesItems;
	}
	
	public void setFicherosDevolucionesItems(List<FicherosDevolucionesItem> ficherosDevolucionesItems) {
		this.ficherosDevolucionesItems = ficherosDevolucionesItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, ficherosDevolucionesItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FicherosDevolucionesDTO other = (FicherosDevolucionesDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(ficherosDevolucionesItems, other.ficherosDevolucionesItems);
	}
	
	@Override
	public String toString() {
		return "FicherosDevolucionesDTO [ficherosDevolucionesItems=" + ficherosDevolucionesItems + ", error=" + error + "]";
	}
	
}
