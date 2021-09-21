package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpedienteEconomicoDTO {
	private List<ExpedienteEconomicoItem> expEconItems = new ArrayList<ExpedienteEconomicoItem>();
	  private Error error = null;
	  
	  /**
	   * 
	   **/
	  public ExpedienteEconomicoDTO expEconItems(List<ExpedienteEconomicoItem> expEconItems) {
	    this.expEconItems = expEconItems;
	    return this;
	  }
	  
	  @JsonProperty("expEconItems")
		public List<ExpedienteEconomicoItem> getExpEconItems() {
			return expEconItems;
		}
		public void setExpEconItems(List<ExpedienteEconomicoItem> expEconItems) {
			this.expEconItems = expEconItems;
		}
	public ExpedienteEconomicoDTO error(Error error) {
	    this.error = error;
	    return this;
	  }
	  
	  @JsonProperty("error")
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
			result = prime * result + ((expEconItems == null) ? 0 : expEconItems.hashCode());
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
			ExpedienteEconomicoDTO other = (ExpedienteEconomicoDTO) obj;
			if (error == null) {
				if (other.error != null)
					return false;
			} else if (!error.equals(other.error))
				return false;
			if (expEconItems == null) {
				if (other.expEconItems != null)
					return false;
			} else if (!expEconItems.equals(other.expEconItems))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ExpedienteEconomicoDTO [expEconItems=" + expEconItems + ", error=" + error + ", getExpEconItems()="
					+ getExpEconItems() + ", getError()=" + getError() + ", hashCode()=" + hashCode() + ", getClass()="
					+ getClass() + ", toString()=" + super.toString() + "]";
		}
}
