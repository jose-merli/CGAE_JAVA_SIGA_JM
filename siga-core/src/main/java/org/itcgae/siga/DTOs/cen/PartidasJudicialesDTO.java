package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartidasJudicialesDTO   {
  
  private List<PartidasJudicialesItem> partidasJudicialesItem = new ArrayList<PartidasJudicialesItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public PartidasJudicialesDTO partidasJudicialesItem(List<PartidasJudicialesItem> partidasJudicialesItem) {
    this.partidasJudicialesItem = partidasJudicialesItem;
    return this;
  }
  
  public List<PartidasJudicialesItem> getPartidasJudicialesItem() {
    return partidasJudicialesItem;
  }
  
  public void setPartidasJudicialesItem(List<PartidasJudicialesItem> partidasJudicialesItem) {
    this.partidasJudicialesItem = partidasJudicialesItem;
  }
  
  
  /**
   * 
   **/
  public PartidasJudicialesDTO error(Error error) {
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
    PartidasJudicialesDTO partidasJudicialesDTO = (PartidasJudicialesDTO) o;
    return Objects.equals(this.partidasJudicialesItem, partidasJudicialesDTO.partidasJudicialesItem) &&
        Objects.equals(this.error, partidasJudicialesDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partidasJudicialesItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class partidasDTO {\n");
    
    sb.append("    partidasItem: ").append(toIndentedString(partidasJudicialesItem)).append("\n");
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

