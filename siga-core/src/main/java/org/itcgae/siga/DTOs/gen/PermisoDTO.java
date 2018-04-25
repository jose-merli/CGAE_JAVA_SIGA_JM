package org.itcgae.siga.DTOs.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PermisoDTO   {
  
  private List<PermisoItem> permisoItems = new ArrayList<PermisoItem>();
  private Error error = null;

  
  /**
   **/
  public PermisoDTO permisoItems(List<PermisoItem> permisoItems) {
    this.permisoItems = permisoItems;
    return this;
  }
  
  
  @JsonProperty("permisoItems")
  public List<PermisoItem> getPermisoItems() {
    return permisoItems;
  }
  public void setPermisoItems(List<PermisoItem> permisoItems) {
    this.permisoItems = permisoItems;
  }

  
  /**
   **/
  public PermisoDTO error(Error error) {
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
    PermisoDTO permisoDTO = (PermisoDTO) o;
    return Objects.equals(this.permisoItems, permisoDTO.permisoItems) &&
        Objects.equals(this.error, permisoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(permisoItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoDTO {\n");
    
    sb.append("    permisoItems: ").append(toIndentedString(permisoItems)).append("\n");
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

