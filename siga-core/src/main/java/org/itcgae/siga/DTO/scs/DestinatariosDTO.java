package org.itcgae.siga.DTO.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DestinatariosDTO   {
  
  private List<DestinatariosItem> destinatariosItem = new ArrayList<DestinatariosItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public DestinatariosDTO destinatariosItem(List<DestinatariosItem> destinatariosItem) {
    this.destinatariosItem = destinatariosItem;
    return this;
  }
  
  public List<DestinatariosItem> getDestinatariosItem() {
    return destinatariosItem;
  }
  
  public void setDestinatariosItem(List<DestinatariosItem> destinatariosItem) {
    this.destinatariosItem = destinatariosItem;
  }
  
  
  /**
   * 
   **/
  public DestinatariosDTO error(Error error) {
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
    DestinatariosDTO destinatariosDTO = (DestinatariosDTO) o;
    return Objects.equals(this.destinatariosItem, destinatariosDTO.destinatariosItem) &&
        Objects.equals(this.error, destinatariosDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destinatariosItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class partidasDTO {\n");
    
    sb.append("    partidasItem: ").append(toIndentedString(destinatariosItem)).append("\n");
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

