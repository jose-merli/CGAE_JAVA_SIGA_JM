package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnosDTO   {
  
  private List<TurnosItem> turnosItem = new ArrayList<TurnosItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public TurnosDTO turnosItems(List<TurnosItem> turnosItem) {
    this.turnosItem = turnosItem;
    return this;
  }
  
  @JsonProperty("turnosItem")
  public List<TurnosItem> getTurnosItems() {
    return turnosItem;
  }
  
  public void setTurnosItems(List<TurnosItem> turnosItem) {
    this.turnosItem = turnosItem;
  }
  
  
  /**
   * 
   **/
  public TurnosDTO error(Error error) {
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
    TurnosDTO turnosDTO = (TurnosDTO) o;
    return Objects.equals(this.turnosItem, turnosDTO.turnosItem) &&
        Objects.equals(this.error, turnosDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(turnosItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class areasDTO {\n");
    
    sb.append("    areasItems: ").append(toIndentedString(turnosItem)).append("\n");
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

