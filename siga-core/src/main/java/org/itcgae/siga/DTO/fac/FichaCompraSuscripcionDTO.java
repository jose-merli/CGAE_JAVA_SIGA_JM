package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaCompraSuscripcionDTO {

	private List<FichaCompraSuscripcionItem> fichaCompraSuscripcionItem = new ArrayList<FichaCompraSuscripcionItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public FichaCompraSuscripcionDTO fichaCompraSuscripcionItem(List<FichaCompraSuscripcionItem> fichaCompraSuscripcionItem) {
	    this.fichaCompraSuscripcionItem = fichaCompraSuscripcionItem;
	    return this;
	  }
	  
	  @JsonProperty("fichaCompraSuscripcionItem")
	  public List<FichaCompraSuscripcionItem> getFichaCompraSuscripcionItem() {
	    return fichaCompraSuscripcionItem;
	  }
	  
	  public void setFichaCompraSuscripcionItem(List<FichaCompraSuscripcionItem> fichaCompraSuscripcionItem) {
	    this.fichaCompraSuscripcionItem = fichaCompraSuscripcionItem;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public FichaCompraSuscripcionDTO error(Error error) {
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
	    FichaCompraSuscripcionDTO areasDTO = (FichaCompraSuscripcionDTO) o;
	    return Objects.equals(this.fichaCompraSuscripcionItem, areasDTO.fichaCompraSuscripcionItem) &&
	        Objects.equals(this.error, areasDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(fichaCompraSuscripcionItem, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class fichaCompraSuscripcionDTO {\n");
	    
	    sb.append("    fichaCompraSuscripcionItems: ").append(toIndentedString(fichaCompraSuscripcionItem)).append("\n");
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
