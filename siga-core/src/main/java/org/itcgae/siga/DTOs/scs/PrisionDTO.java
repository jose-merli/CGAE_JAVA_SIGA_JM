package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrisionDTO   {
  
  private List<PrisionItem> prisionItems = new ArrayList<PrisionItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public PrisionDTO prisionItems(List<PrisionItem> prisionItems) {
    this.prisionItems = prisionItems;
    return this;
  }
  
  @JsonProperty("prisionItems")
  public List<PrisionItem> getPrisionItems() {
    return prisionItems;
  }
  
  public void setPrisionItems(List<PrisionItem> prisionItems) {
    this.prisionItems = prisionItems;
  }
  
  
  /**
   * 
   **/
  public PrisionDTO error(Error error) {
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
    PrisionDTO prisionDTO = (PrisionDTO) o;
    return Objects.equals(this.prisionItems, prisionDTO.prisionItems) &&
        Objects.equals(this.error, prisionDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prisionItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrisionDTO {\n");
    
    sb.append("    prisionItems: ").append(toIndentedString(prisionItems)).append("\n");
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

