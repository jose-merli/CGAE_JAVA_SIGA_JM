package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class SolIncorporacionDTO {

	private List<SolIncorporacionItem> SolIncorporacionItems = new ArrayList<SolIncorporacionItem>();
	private Error error = null;
	
	
	public List<SolIncorporacionItem> getSolIncorporacionItems() {
		return SolIncorporacionItems;
	}


	public void setSolIncorporacionItems(List<SolIncorporacionItem> solIncorporacionItems) {
		SolIncorporacionItems = solIncorporacionItems;
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
		result = prime * result + ((SolIncorporacionItems == null) ? 0 : SolIncorporacionItems.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
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
		SolIncorporacionDTO other = (SolIncorporacionDTO) obj;
		if (SolIncorporacionItems == null) {
			if (other.SolIncorporacionItems != null)
				return false;
		} else if (!SolIncorporacionItems.equals(other.SolIncorporacionItems))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "SolIncorporacionDTO [SolIncorporacionItems=" + SolIncorporacionItems + ", error=" + error + "]";
	}
	
	
	
}
