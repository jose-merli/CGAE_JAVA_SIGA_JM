package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AgeCalendario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsistenciaEventoDTO   {
  
  private List<AsistenciaEventoItem> asistenciaEventoItem = new ArrayList<AsistenciaEventoItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public AsistenciaEventoDTO asistenciaEventoItem(List<AsistenciaEventoItem> asistenciaEventoItem) {
    this.asistenciaEventoItem = asistenciaEventoItem;
    return this;
  }
  
  @JsonProperty("asistenciaEventoItem")
  public List<AsistenciaEventoItem> getAsistenciaEventoItem() {
    return asistenciaEventoItem;
  }
  
  public void setCalendarItems(List<AsistenciaEventoItem> asistenciaEventoItem) {
    this.asistenciaEventoItem = asistenciaEventoItem;
  }
 
  /**
   * 
   **/
  public AsistenciaEventoDTO error(Error error) {
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
    AsistenciaEventoDTO asistenciaEventoDTO = (AsistenciaEventoDTO) o;
    return Objects.equals(this.asistenciaEventoItem, asistenciaEventoDTO.asistenciaEventoItem) &&
        Objects.equals(this.error, asistenciaEventoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asistenciaEventoItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsistenciaEventoDTO {\n");
    
    sb.append("    asistenciaEventoItem: ").append(toIndentedString(asistenciaEventoItem)).append("\n");
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

