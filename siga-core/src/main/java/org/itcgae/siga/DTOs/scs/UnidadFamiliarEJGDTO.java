package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnidadFamiliarEJGDTO {
	 private List<UnidadFamiliarEJGItem> unidadFamiliarEJGItems = new ArrayList<UnidadFamiliarEJGItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public UnidadFamiliarEJGDTO unidadFamiliarEJGItems(List<UnidadFamiliarEJGItem> unidadFamiliarEJGItems) {
	    this.setUnidadFamiliarEJGItems(unidadFamiliarEJGItems);
	    return this;
	  }
	  
	  @JsonProperty("unidadFamiliarEJGItems")
		public List<UnidadFamiliarEJGItem> getUnidadFamiliarEJGItems() {
			return unidadFamiliarEJGItems;
		}

		public void setUnidadFamiliarEJGItems(List<UnidadFamiliarEJGItem> unidadFamiliarEJGItems) {
			this.unidadFamiliarEJGItems = unidadFamiliarEJGItems;
		}
	  
	  /**
	   * 
	   **/
	  public UnidadFamiliarEJGDTO error(Error error) {
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
	    UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = (UnidadFamiliarEJGDTO) o;
	    return Objects.equals(this.unidadFamiliarEJGItems, unidadFamiliarEJGDTO.unidadFamiliarEJGItems) &&
	        Objects.equals(this.error, unidadFamiliarEJGDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(unidadFamiliarEJGItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class areasDTO {\n");
	    
	    sb.append("    areasItems: ").append(toIndentedString(unidadFamiliarEJGItems)).append("\n");
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
