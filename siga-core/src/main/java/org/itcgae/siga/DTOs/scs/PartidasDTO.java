package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartidasDTO   {
  
  private List<PartidasItem> partidasItem = new ArrayList<PartidasItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public PartidasDTO partidasItem(List<PartidasItem> partidasItem) {
    this.partidasItem = partidasItem;
    return this;
  }
  
  public List<PartidasItem> getPartidasItem() {
    return partidasItem;
  }
  
  public void setPartidasItem(List<PartidasItem> partidasItem) {
    this.partidasItem = partidasItem;
  }
  
  
  /**
   * 
   **/
  public PartidasDTO error(Error error) {
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
    PartidasDTO partidasDTO = (PartidasDTO) o;
    return Objects.equals(this.partidasItem, partidasDTO.partidasItem) &&
        Objects.equals(this.error, partidasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partidasItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class partidasDTO {\n");
    
    sb.append("    partidasItem: ").append(toIndentedString(partidasItem)).append("\n");
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

