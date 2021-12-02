package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.itcgae.siga.DTOs.gen.Error;

public class RelacionesDTO {
	  private List<RelacionesItem> relacionesItem = new ArrayList<RelacionesItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public RelacionesDTO relacionesItem(List<RelacionesItem> relacionesItem) {
	    this.relacionesItem = relacionesItem;
	    return this;
	  }
	  
	  @JsonProperty("relacionesItem")
	  public List<RelacionesItem> getRelacionesItems() {
	    return relacionesItem;
	  }
	  
	  public void setRelacionesItem(List<RelacionesItem> relacionesItem) {
	    this.relacionesItem = relacionesItem;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public RelacionesDTO error(Error error) {
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
	    RelacionesDTO relacionesDTO = (RelacionesDTO) o;
	    return Objects.equals(this.relacionesItem, relacionesDTO.relacionesItem) &&
	        Objects.equals(this.error, relacionesDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(relacionesItem, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class RelacionesDTO {\n");
	    
	    sb.append("    relacionesItem: ").append(toIndentedString(relacionesItem)).append("\n");
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
