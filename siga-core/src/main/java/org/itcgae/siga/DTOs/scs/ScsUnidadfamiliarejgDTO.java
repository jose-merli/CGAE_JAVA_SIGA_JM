package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScsUnidadfamiliarejgDTO {
	
	 private List<ScsUnidadfamiliarejg> unidadFamiliarItems = new ArrayList<ScsUnidadfamiliarejg>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public ScsUnidadfamiliarejgDTO unidadFamiliarItems(List<ScsUnidadfamiliarejg> unidadFamiliarItems) {
	    this.unidadFamiliarItems = unidadFamiliarItems;
	    return this;
	  }
	  
	  @JsonProperty("unidadFamiliarItems")
	  public List<ScsUnidadfamiliarejg> getunidadFamiliarItems() {
	    return unidadFamiliarItems;
	  }
	  
	  public void setunidadFamiliarItems(List<ScsUnidadfamiliarejg> unidadFamiliarItems) {
	    this.unidadFamiliarItems = unidadFamiliarItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public ScsUnidadfamiliarejgDTO error(Error error) {
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
	  
	  /**
	   * 
	   **/
	  
	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    ScsUnidadfamiliarejgDTO ScsUnidadfamiliarejgDTO = (ScsUnidadfamiliarejgDTO) o;
	    return Objects.equals(this.unidadFamiliarItems, ScsUnidadfamiliarejgDTO.unidadFamiliarItems) &&
	        Objects.equals(this.error, ScsUnidadfamiliarejgDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(unidadFamiliarItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ScsUnidadfamiliarejgDTO {\n");
	    
	    sb.append("    unidadFamiliarItems: ").append(toIndentedString(unidadFamiliarItems)).append("\n");
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
