
package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class SaltoCompGuardiaDTO {

	private List<SaltoCompGuardiaItem> saltosCompItems = new ArrayList<SaltoCompGuardiaItem>();
	private Error error = null;

	public List<SaltoCompGuardiaItem> getSaltosCompItems() {
		return saltosCompItems;
	}

	public void setSaltosCompItems(List<SaltoCompGuardiaItem> saltosCompItems) {
		this.saltosCompItems = saltosCompItems;
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
		result = prime * result + ((saltosCompItems == null) ? 0 : saltosCompItems.hashCode());
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
		SaltoCompGuardiaDTO other = (SaltoCompGuardiaDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (saltosCompItems == null) {
			if (other.saltosCompItems != null)
				return false;
		} else if (!saltosCompItems.equals(other.saltosCompItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SaltosCompGuardDTO [saltosCompItems=" + saltosCompItems + ", error=" + error + "]";
	}

}
