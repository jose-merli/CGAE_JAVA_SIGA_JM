package org.itcgae.siga.DTOs.gen;

import java.util.ArrayList;
import java.util.List;

public class ComboMutualidadDTO {
	
	private List<ComboItemMutualidad> combooItems = new ArrayList<ComboItemMutualidad>();
	private Error error = null;
	  
	  
	public List<ComboItemMutualidad> getCombooItems() {
		return combooItems;
	}
	public void setCombooItems(List<ComboItemMutualidad> combooItems) {
		this.combooItems = combooItems;
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
		result = prime * result + ((combooItems == null) ? 0 : combooItems.hashCode());
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
		ComboMutualidadDTO other = (ComboMutualidadDTO) obj;
		if (combooItems == null) {
			if (other.combooItems != null)
				return false;
		} else if (!combooItems.equals(other.combooItems))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		return true;
	}
	  
	
	  

}
