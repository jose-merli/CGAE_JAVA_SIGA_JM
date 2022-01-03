package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FicherosAbonosDTO {
	private List<FicherosAbonosItem> ficherosAbonosItems = new ArrayList<>();
	private Error error = null;
	
	public List<FicherosAbonosItem> getFicherosAbonosItems() {
		return ficherosAbonosItems;
	}
	
	public void setFicherosAbonosItems(List<FicherosAbonosItem> ficherosAbonosItems) {
		this.ficherosAbonosItems = ficherosAbonosItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, ficherosAbonosItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FicherosAbonosDTO other = (FicherosAbonosDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(ficherosAbonosItems, other.ficherosAbonosItems);
	}
	
	@Override
	public String toString() {
		return "FicherosAbonosDTO [ficherosAbonosItems=" + ficherosAbonosItems + ", error=" + error + "]";
	}
	
}
