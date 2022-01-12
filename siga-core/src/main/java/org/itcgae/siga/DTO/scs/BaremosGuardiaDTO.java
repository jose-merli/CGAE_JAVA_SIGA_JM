
package org.itcgae.siga.DTO.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class BaremosGuardiaDTO {

	private List<BaremosGuardiaItem> baremosGuardiaItems = new ArrayList<BaremosGuardiaItem>();
	private Error error = null;

	public List<BaremosGuardiaItem> getBaremosGuardiaItems() {
		return baremosGuardiaItems;
	}

	public void setBaremosGuardiaItems(List<BaremosGuardiaItem> baremosGuardiaItems) {
		this.baremosGuardiaItems = baremosGuardiaItems;
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
		result = prime * result + ((baremosGuardiaItems == null) ? 0 : baremosGuardiaItems.hashCode());
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
		BaremosGuardiaDTO other = (BaremosGuardiaDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (baremosGuardiaItems == null) {
			if (other.baremosGuardiaItems != null)
				return false;
		} else if (!baremosGuardiaItems.equals(other.baremosGuardiaItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaremosGuardiaDTO [guardiaItems=" + baremosGuardiaItems + ", error=" + error + "]";
	}

}
