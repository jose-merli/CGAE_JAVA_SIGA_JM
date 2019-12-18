package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InscripcionesDTO   {
  
  private List<InscripcionesItem> inscripcionesItem = new ArrayList<InscripcionesItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public InscripcionesDTO inscripcionesItems(List<InscripcionesItem> inscripcionesItem) {
    this.inscripcionesItem = inscripcionesItem;
    return this;
  }
  
  @JsonProperty("inscripcionesItem")
  public List<InscripcionesItem> getInscripcionesItems() {
    return inscripcionesItem;
  }
  
  public void setInscripcionesItems(List<InscripcionesItem> inscripcionesItem) {
    this.inscripcionesItem = inscripcionesItem;
  }
  
  
  /**
   * 
   **/
  public InscripcionesDTO error(Error error) {
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
    InscripcionesDTO inscripcionesDTO = (InscripcionesDTO) o;
    return Objects.equals(this.inscripcionesItem, inscripcionesDTO.inscripcionesItem) &&
        Objects.equals(this.error, inscripcionesDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inscripcionesItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class inscripcionesDTO {\n");
    
    sb.append("    inscripcionesItems: ").append(toIndentedString(inscripcionesItem)).append("\n");
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

