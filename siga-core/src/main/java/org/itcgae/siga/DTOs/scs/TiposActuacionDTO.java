package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TiposActuacionDTO   {
  
  private List<TiposActuacionItem> tiposActuacionItem = new ArrayList<TiposActuacionItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public TiposActuacionDTO tiposAsistenciaItem(List<TiposActuacionItem> tiposActuacionItem) {
    this.tiposActuacionItem = tiposActuacionItem;
    return this;
  }
  
  public List<TiposActuacionItem> getTiposActuacionItem() {
    return tiposActuacionItem;
  }
  
  public void setPartidasItem(List<TiposActuacionItem> tiposActuacionItem) {
    this.tiposActuacionItem = tiposActuacionItem;
  }
  
  
  /**
   * 
   **/
  public TiposActuacionDTO error(Error error) {
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
    TiposActuacionDTO tiposActuacionDTO = (TiposActuacionDTO) o;
    return Objects.equals(this.tiposActuacionItem, tiposActuacionDTO.tiposActuacionItem) &&
        Objects.equals(this.error, tiposActuacionDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tiposActuacionItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class partidasDTO {\n");
    
    sb.append("    partidasItem: ").append(toIndentedString(tiposActuacionItem)).append("\n");
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

