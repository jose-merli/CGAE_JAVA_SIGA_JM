package org.itcgae.siga.DTO.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosJusticiableDTO   {
  
  private List<AsuntosJusticiableItem> asuntosJusticiableItem = new ArrayList<AsuntosJusticiableItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public AsuntosJusticiableDTO asuntosJusticiableItems(List<AsuntosJusticiableItem> asuntosJusticiableItem) {
    this.asuntosJusticiableItem = asuntosJusticiableItem;
    return this;
  }
  
  @JsonProperty("asuntosJusticiableItem")
  public List<AsuntosJusticiableItem> getAsuntosJusticiableItem() {
    return asuntosJusticiableItem;
  }
  
  public void setAsuntosJusticiableItem(List<AsuntosJusticiableItem> asuntosJusticiableItem) {
    this.asuntosJusticiableItem = asuntosJusticiableItem;
  }
  
  
  /**
   * 
   **/
  public AsuntosJusticiableDTO error(Error error) {
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
    AsuntosJusticiableDTO areasDTO = (AsuntosJusticiableDTO) o;
    return Objects.equals(this.asuntosJusticiableItem, areasDTO.asuntosJusticiableItem) &&
        Objects.equals(this.error, areasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asuntosJusticiableItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class areasDTO {\n");
    
    sb.append("    areasItems: ").append(toIndentedString(asuntosJusticiableItem)).append("\n");
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

