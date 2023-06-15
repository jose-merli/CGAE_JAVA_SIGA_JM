package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModulosDTO   {
  
  private List<ModulosItem> modulosItem = new ArrayList<ModulosItem>();
  private Error error = null;
  private String baja;

  
  /**
   * 
   **/
  public ModulosDTO modulosItem(List<ModulosItem> modulosItem) {
    this.modulosItem = modulosItem;
    return this;
  }
  
  public String getBaja() {
	return baja;
}

public void setBaja(String baja) {
	this.baja = baja;
}

@JsonProperty("modulosItem")
  public List<ModulosItem> getModulosItem() {
    return modulosItem;
  }
  
  public void setModulosItem(List<ModulosItem> modulosItem) {
    this.modulosItem = modulosItem;
  }
  
  
  /**
   * 
   **/
  public ModulosDTO error(Error error) {
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
    ModulosDTO areasDTO = (ModulosDTO) o;
    return Objects.equals(this.modulosItem, areasDTO.modulosItem) &&
        Objects.equals(this.error, areasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modulosItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class areasDTO {\n");
    
    sb.append("    areasItems: ").append(toIndentedString(modulosItem)).append("\n");
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

