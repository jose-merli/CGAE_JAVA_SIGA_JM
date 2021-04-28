package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComunicacionesDTO   {
  
  private List<ComunicacionesItem> comunicacionesItem = new ArrayList<ComunicacionesItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public ComunicacionesDTO comunicacionesItem(List<ComunicacionesItem> comunicacionesItem) {
    this.comunicacionesItem = comunicacionesItem;
    return this;
  }
  
  @JsonProperty("comunicacionesItem")
  public List<ComunicacionesItem> getProcuradorItems() {
    return comunicacionesItem;
  }
  
  public void setComunicacionesItem(List<ComunicacionesItem> comunicacionesItem) {
    this.comunicacionesItem = comunicacionesItem;
  }
  
  
  /**
   * 
   **/
  public ComunicacionesDTO error(Error error) {
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
    ComunicacionesDTO comunicacionesDTO = (ComunicacionesDTO) o;
    return Objects.equals(this.comunicacionesItem, comunicacionesDTO.comunicacionesItem) &&
        Objects.equals(this.error, comunicacionesDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comunicacionesItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcuradorDTO {\n");
    
    sb.append("    procuradorItems: ").append(toIndentedString(comunicacionesItem)).append("\n");
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

