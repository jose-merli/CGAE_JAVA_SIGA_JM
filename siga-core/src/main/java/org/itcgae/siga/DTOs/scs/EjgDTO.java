package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EjgDTO {
	private List<EjgItem> ejgItems = new ArrayList<EjgItem>();
	  private Error error = null;

	  /**
	   * 
	   **/
	  public EjgDTO ejgItems(List<EjgItem> ejgItems) {
	    this.ejgItems = ejgItems;
	    return this;
	  }
	  
	  @JsonProperty("ejgItems")
	  public List<EjgItem> getEjgItems() {
	    return ejgItems;
	  }
	  
	  public void setEjgItems(List<EjgItem> ejgItems) {
	    this.ejgItems = ejgItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public EjgDTO error(Error error) {
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
	    EjgDTO ejgDTO = (EjgDTO) o;
	    return Objects.equals(this.ejgItems, ejgDTO.ejgItems) &&
	        Objects.equals(this.error, ejgDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(ejgItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EjgDTO {\n");
	    
	    sb.append("    ejgItems: ").append(toIndentedString(ejgItems)).append("\n");
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
