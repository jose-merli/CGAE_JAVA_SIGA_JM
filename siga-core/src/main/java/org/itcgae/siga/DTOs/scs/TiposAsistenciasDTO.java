package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TiposAsistenciasDTO   {
  
  private List<TiposAsistenciaItem> tiposAsistenciaItem = new ArrayList<TiposAsistenciaItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public TiposAsistenciasDTO tiposAsistenciaItem(List<TiposAsistenciaItem> tiposAsistenciaItem) {
    this.tiposAsistenciaItem = tiposAsistenciaItem;
    return this;
  }
  
  public List<TiposAsistenciaItem> getTiposAsistenciasItem() {
    return tiposAsistenciaItem;
  }
  
  public void setPartidasItem(List<TiposAsistenciaItem> tiposAsistenciaItem) {
    this.tiposAsistenciaItem = tiposAsistenciaItem;
  }
  
  
  /**
   * 
   **/
  public TiposAsistenciasDTO error(Error error) {
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
    TiposAsistenciasDTO tiposAsistenciaDTO = (TiposAsistenciasDTO) o;
    return Objects.equals(this.tiposAsistenciaItem, tiposAsistenciaDTO.tiposAsistenciaItem) &&
        Objects.equals(this.error, tiposAsistenciaDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tiposAsistenciaItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class partidasDTO {\n");
    
    sb.append("    partidasItem: ").append(toIndentedString(tiposAsistenciaItem)).append("\n");
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

