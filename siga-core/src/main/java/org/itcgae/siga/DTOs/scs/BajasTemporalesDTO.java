package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BajasTemporalesDTO   {
  
  private List<BajasTemporalesItem> bajasTemporalesItem = new ArrayList<BajasTemporalesItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public BajasTemporalesDTO bajasTemporalesItems(List<BajasTemporalesItem> bajasTemporalesItem) {
    this.bajasTemporalesItem = bajasTemporalesItem;
    return this;
  }
  
  @JsonProperty("bajasTemporalesItem")
  public List<BajasTemporalesItem> getBajasTemporalesItems() {
    return bajasTemporalesItem;
  }
  
  public void setBajasTemporalesItems(List<BajasTemporalesItem> bajasTemporalesItem) {
    this.bajasTemporalesItem = bajasTemporalesItem;
  }
  
  
  /**
   * 
   **/
  public BajasTemporalesDTO error(Error error) {
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
    BajasTemporalesDTO bajasTemporalesDTO = (BajasTemporalesDTO) o;
    return Objects.equals(this.bajasTemporalesItem, bajasTemporalesDTO.bajasTemporalesItem) &&
        Objects.equals(this.error, bajasTemporalesDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bajasTemporalesItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class inscripcionesDTO {\n");
    
    sb.append("    inscripcionesItems: ").append(toIndentedString(bajasTemporalesItem)).append("\n");
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

