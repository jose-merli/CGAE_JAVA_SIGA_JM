package org.itcgae.siga.DTOs.scs;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class PagosjgDTO {

	private List<PagosjgItem>  pagosjgItem = new ArrayList<PagosjgItem>();
	private Error error = null;
	
	public PagosjgDTO PagosjgItem(List<PagosjgItem> PagosjgItem){
		this.pagosjgItem = PagosjgItem;
		return this;
	}
	
	public List<PagosjgItem> getPagosjgItem() {
		return pagosjgItem;
	}
	
	public void setPagosjgItem(List<PagosjgItem> PagosjgItem) {
		this.pagosjgItem = PagosjgItem;
	}
	
	public PagosjgDTO error(Error error){
		this.error = error;
		return this;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((pagosjgItem == null) ? 0 : pagosjgItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagosjgDTO other = (PagosjgDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (pagosjgItem == null) {
			if (other.pagosjgItem != null)
				return false;
		} else if (!pagosjgItem.equals(other.pagosjgItem))
			return false;
		return true;
	}

	
}