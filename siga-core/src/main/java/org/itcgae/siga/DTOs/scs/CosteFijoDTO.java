package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CosteFijoDTO   {
  
  private List<CosteFijoItem> costeFijoItems = new ArrayList<CosteFijoItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public CosteFijoDTO costeFijoItems(List<CosteFijoItem> costeFijoItems) {
    this.costeFijoItems = costeFijoItems;
    return this;
  }
  
  @JsonProperty("costeFijoItems")
  public List<CosteFijoItem> getCosteFijoItems() {
    return costeFijoItems;
  }
  
  public void setCosteFijoItems(List<CosteFijoItem> costeFijoItems) {
    this.costeFijoItems = costeFijoItems;
  }
  
  
  /**
   * 
   **/
  public CosteFijoDTO error(Error error) {
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
    CosteFijoDTO costeFijoDTO = (CosteFijoDTO) o;
    return Objects.equals(this.costeFijoItems, costeFijoDTO.costeFijoItems) &&
        Objects.equals(this.error, costeFijoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(costeFijoItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CosteFijoDTO {\n");
    
    sb.append("    costeFijoItems: ").append(toIndentedString(costeFijoItems)).append("\n");
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

