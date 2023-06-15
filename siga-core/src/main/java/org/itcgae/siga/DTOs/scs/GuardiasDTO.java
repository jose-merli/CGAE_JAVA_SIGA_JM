
package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class GuardiasDTO {

	private List<GuardiasItem> guardiaItems = new ArrayList<GuardiasItem>();
	private Error error = null;

	public List<GuardiasItem> getGuardiaItems() {
		return guardiaItems;
	}

	public void setGuardiaItems(List<GuardiasItem> guardiaItems) {
		this.guardiaItems = guardiaItems;
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
		result = prime * result + ((guardiaItems == null) ? 0 : guardiaItems.hashCode());
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
		GuardiasDTO other = (GuardiasDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (guardiaItems == null) {
			if (other.guardiaItems != null)
				return false;
		} else if (!guardiaItems.equals(other.guardiaItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GuardiasDTO [guardiaItems=" + guardiaItems + ", error=" + error + "]";
	}

}
