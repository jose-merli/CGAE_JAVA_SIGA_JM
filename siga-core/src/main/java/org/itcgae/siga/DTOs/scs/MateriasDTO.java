package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MateriasDTO   {
  
  private List<MateriasItem> materiasItems = new ArrayList<MateriasItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public MateriasDTO materiasItems(List<MateriasItem> materiasItems) {
    this.materiasItems = materiasItems;
    return this;
  }
  
  @JsonProperty("materiasItems")
  public List<MateriasItem> getmateriasItems() {
    return materiasItems;
  }
  
  public void setmateriasItems(List<MateriasItem> materiasItems) {
    this.materiasItems = materiasItems;
  }
  
  
  /**
   * 
   **/
  public MateriasDTO error(Error error) {
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
    MateriasDTO materiasDTO = (MateriasDTO) o;
    return Objects.equals(this.materiasItems, materiasDTO.materiasItems) &&
        Objects.equals(this.error, materiasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(materiasItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class materiasDTO {\n");
    
    sb.append("    materiasItems: ").append(toIndentedString(materiasItems)).append("\n");
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

