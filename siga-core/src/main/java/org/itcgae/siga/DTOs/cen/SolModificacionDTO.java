package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModificacionDTO {
	private List<SolModificacionItem> solModificacionItems = new ArrayList<SolModificacionItem>();
	private Error error = null;
	
	@JsonProperty("solModificacionItems")
	public List<SolModificacionItem> getSolModificacionItems() {
		return solModificacionItems;
	}
	public void setSolModificacionItems(List<SolModificacionItem> solModificacionItems) {
		this.solModificacionItems = solModificacionItems;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    SolModificacionDTO solModificacionDTO = (SolModificacionDTO) o;
	    return Objects.equals(this.solModificacionItems, solModificacionDTO.solModificacionItems) &&
	        Objects.equals(this.error, solModificacionDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(solModificacionItems, error);
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SolModificacionItem {\n");
	    
	    sb.append("    solModificacionItems: ").append(toIndentedString(solModificacionItems)).append("\n");
	    sb.append("    error: ").append(toIndentedString(error)).append("\n");
	    sb.append("}");
	    return sb.toString();
	}

	/**
	* Convert the given object to string with each line indented by 4 spaces
	* (except the first line).
	*/
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}
}
