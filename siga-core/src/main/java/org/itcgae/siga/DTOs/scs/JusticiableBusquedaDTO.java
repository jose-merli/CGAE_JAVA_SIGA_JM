package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableBusquedaDTO {

	 private List<JusticiableBusquedaItem> justiciableBusquedaItems = new ArrayList<JusticiableBusquedaItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public JusticiableBusquedaDTO justiciableBusquedaItems(List<JusticiableBusquedaItem> justiciableBusquedaItems) {
	    this.justiciableBusquedaItems = justiciableBusquedaItems;
	    return this;
	  }
	  
	  @JsonProperty("justiciableBusquedaItems")
	  public List<JusticiableBusquedaItem> getJusticiableBusquedaItems() {
	    return justiciableBusquedaItems;
	  }
	  
	  public void setJusticiableBusquedaItem(List<JusticiableBusquedaItem> justiciableBusquedaItems) {
	    this.justiciableBusquedaItems = justiciableBusquedaItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public JusticiableBusquedaDTO error(Error error) {
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
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    JusticiableBusquedaDTO justiciableBusquedaDTO = (JusticiableBusquedaDTO) o;
	    return Objects.equals(this.justiciableBusquedaItems, justiciableBusquedaDTO.justiciableBusquedaItems) &&
	        Objects.equals(this.error, justiciableBusquedaDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(justiciableBusquedaItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class JusticiableBusquedaDTO {\n");
	    
	    sb.append("    justiciableBusquedaItems: ").append(toIndentedString(justiciableBusquedaItems)).append("\n");
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
