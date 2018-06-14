package org.itcgae.siga.DTOs.cen;

import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaPersonaDTO {

	private List<FichaPersonaItem> fichaPersonaItem;
	private Error error;
	
	@JsonProperty("fichaPersonaItem")
	public List<FichaPersonaItem> getFichaPersonaItem() {
		return fichaPersonaItem;
	}
	public void setFichaPersonaItem(List<FichaPersonaItem> fichaPersonaItem) {
		this.fichaPersonaItem = fichaPersonaItem;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaPersonaDTO {\n");
	    
	    sb.append("    fichaPersonaItem: ").append(toIndentedString(fichaPersonaItem)).append("\n");
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
	
	@Override
	public int hashCode() {
		return Objects.hash(fichaPersonaItem, error);
	}

	
}
