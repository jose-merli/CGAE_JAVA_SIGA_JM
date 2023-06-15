package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableDTO {

	 private List<JusticiableBusquedaItem> asuntos = new ArrayList<JusticiableBusquedaItem>();
	 private List<JusticiableItem> justiciableItems = new ArrayList<JusticiableItem>();
	  private Error error = null;
	  private JusticiableItem justiciable = new JusticiableItem();

	  
	  /**
	   * 
	   **/
	  public JusticiableDTO asuntos(List<JusticiableBusquedaItem> asuntos) {
	    this.asuntos = asuntos;
	    return this;
	  }
	  
	  @JsonProperty("asuntos")
	  public List<JusticiableBusquedaItem> getAsuntos() {
	    return asuntos;
	  }
	  
	  public void setAsuntos(List<JusticiableBusquedaItem> asuntos) {
	    this.asuntos = asuntos;
	  }
	  
	  /**
	   * 
	   **/
	  public JusticiableDTO justiciableItems(List<JusticiableItem> justiciableItems) {
	    this.justiciableItems = justiciableItems;
	    return this;
	  }
	  
	  @JsonProperty("justiciableItems")
	  public List<JusticiableItem> getJusticiableItems() {
	    return justiciableItems;
	  }
	  
	  public void setJusticiableItems(List<JusticiableItem> justiciableItems) {
	    this.justiciableItems = justiciableItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public JusticiableDTO error(Error error) {
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
	  public JusticiableDTO justiciable(JusticiableItem justiciable) {
	    this.justiciable = justiciable;
	    return this;
	  }
	  
	  @JsonProperty("justiciable")
	  public JusticiableItem getJusticiable() {
	    return justiciable;
	  }
	  
	  public void setJusticiable(JusticiableItem justiciable) {
	    this.justiciable = justiciable;
	  }
	  
	  
	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    JusticiableDTO justiciableDTO = (JusticiableDTO) o;
	    return Objects.equals(this.asuntos, justiciableDTO.asuntos) &&
	        Objects.equals(this.justiciableItems, justiciableDTO.justiciableItems) &&
	        Objects.equals(this.error, justiciableDTO.error) &&
	        Objects.equals(this.justiciable, justiciableDTO.justiciable);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(asuntos, justiciableItems, error, justiciable);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class JusticiableDTO {\n");
	    
	    sb.append("    asuntos: ").append(toIndentedString(asuntos)).append("\n");
	    sb.append("    justiciableItems: ").append(toIndentedString(justiciableItems)).append("\n");
	    sb.append("    error: ").append(toIndentedString(error)).append("\n");
	    sb.append("    justiciable: ").append(toIndentedString(justiciable)).append("\n");
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
