package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableTelefonoDTO {

	 private List<JusticiableTelefonoItem> telefonosJusticiables = new ArrayList<JusticiableTelefonoItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public JusticiableTelefonoDTO telefonosJusticiables(List<JusticiableTelefonoItem> telefonosJusticiables) {
	    this.telefonosJusticiables = telefonosJusticiables;
	    return this;
	  }
	  
	  @JsonProperty("telefonosJusticiables")
	  public List<JusticiableTelefonoItem> getTelefonosJusticiables() {
	    return telefonosJusticiables;
	  }
	  
	  public void setTelefonosJusticiables(List<JusticiableTelefonoItem> telefonosJusticiables) {
	    this.telefonosJusticiables = telefonosJusticiables;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public JusticiableTelefonoDTO error(Error error) {
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
	    JusticiableTelefonoDTO justiciableTelefonoDTO = (JusticiableTelefonoDTO) o;
	    return Objects.equals(this.telefonosJusticiables, justiciableTelefonoDTO.telefonosJusticiables) &&
	        Objects.equals(this.error, justiciableTelefonoDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(telefonosJusticiables, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class JusticiableTelefonoDTO {\n");
	    
	    sb.append("    telefonosJusticiables: ").append(toIndentedString(telefonosJusticiables)).append("\n");
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
