package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComisariaDTO   {
  
  private List<ComisariaItem> comisariaItems = new ArrayList<ComisariaItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public ComisariaDTO prisionItems(List<ComisariaItem> comisariaItems) {
    this.comisariaItems = comisariaItems;
    return this;
  }
  
  @JsonProperty("comisariaItems")
  public List<ComisariaItem> getComisariaItems() {
    return comisariaItems;
  }
  
  public void setComisariaItems(List<ComisariaItem> comisariaItems) {
    this.comisariaItems = comisariaItems;
  }
  
  
  /**
   * 
   **/
  public ComisariaDTO error(Error error) {
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
    ComisariaDTO comisariaDTO = (ComisariaDTO) o;
    return Objects.equals(this.comisariaItems, comisariaDTO.comisariaItems) &&
        Objects.equals(this.error, comisariaDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comisariaItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComisariaDTO {\n");
    
    sb.append("    comisariaItems: ").append(toIndentedString(comisariaItems)).append("\n");
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

