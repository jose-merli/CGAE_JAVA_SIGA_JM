package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermutaDTO2 {
	  private List<PermutaItem2> permutaItems = new ArrayList<PermutaItem2>();
	    private org.itcgae.siga.DTOs.gen.Error error = null;
	    
	    @JsonProperty("permutaItems")
		public List<PermutaItem2> getPermutaItems() {
			return permutaItems;
		}
		public void setPermutaItems(List<PermutaItem2> permutaItems) {
			this.permutaItems = permutaItems;
		}
		
		@JsonProperty("error")
		public org.itcgae.siga.DTOs.gen.Error getError() {
			return error;
		}
		public void setError(org.itcgae.siga.DTOs.gen.Error error) {
			this.error = error;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(getPermutaItems(), getError());
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        PermutaDTO that = (PermutaDTO) obj;
	        return Objects.equals(getPermutaItems(), that.getPermutaItems()) &&
	                Objects.equals(getError(), that.getError());
		}
		@Override
		public String toString() {
			return "PermutaDTO{" +
            "permutaItems=" + permutaItems +
            ", error=" + error +
            '}';
		}
		
		
	    
	    
}
