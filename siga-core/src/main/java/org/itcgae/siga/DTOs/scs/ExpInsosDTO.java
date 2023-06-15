package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpInsosDTO {
	private List<ExpInsosItem> expInsosItems = new ArrayList<ExpInsosItem>();
	  private Error error = null;

	  /**
	   * 
	   **/
	  public ExpInsosDTO expInsosItems(List<ExpInsosItem> expInsosItems) {
	    this.expInsosItems = expInsosItems;
	    return this;
	  }
	  
	  @JsonProperty("expInsosItems")
	  public List<ExpInsosItem> getExpInsosItems() {
	    return expInsosItems;
	  }
	  
	  public void setExpInsosItems(List<ExpInsosItem> expInsosItems) {
	    this.expInsosItems = expInsosItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public ExpInsosDTO error(Error error) {
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
	    ExpInsosDTO expInsosDTO = (ExpInsosDTO) o;
	    return Objects.equals(this.expInsosItems, expInsosDTO.expInsosItems) &&
	        Objects.equals(this.error, expInsosDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(expInsosItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ExpInsosDTO {\n");
	    
	    sb.append("    expInsosItems: ").append(toIndentedString(expInsosItems)).append("\n");
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
