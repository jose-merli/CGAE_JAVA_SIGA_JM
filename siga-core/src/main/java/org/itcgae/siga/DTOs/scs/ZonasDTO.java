package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZonasDTO   {
  
  private List<ZonasItem> zonasItems = new ArrayList<ZonasItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public ZonasDTO zonasItems(List<ZonasItem> zonasItems) {
    this.zonasItems = zonasItems;
    return this;
  }
  
  @JsonProperty("zonasItems")
  public List<ZonasItem> getZonasItems() {
    return zonasItems;
  }
  
  public void setZonasItems(List<ZonasItem> zonasItems) {
    this.zonasItems = zonasItems;
  }
  
  
  /**
   * 
   **/
  public ZonasDTO error(Error error) {
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
    ZonasDTO zonasDTO = (ZonasDTO) o;
    return Objects.equals(this.zonasItems, zonasDTO.zonasItems) &&
        Objects.equals(this.error, zonasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zonasItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ZonasDTO {\n");
    
    sb.append("    zonasItems: ").append(toIndentedString(zonasItems)).append("\n");
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

