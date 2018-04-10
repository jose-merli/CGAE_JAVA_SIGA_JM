package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CatalogoMaestroDTO   {
  
  private List<CatalogoMaestroItem> catalogoMaestroItem = new ArrayList<CatalogoMaestroItem>();
  private Error error = null;

  
  /**
   **/
  public CatalogoMaestroDTO catalogoMaestroItem(List<CatalogoMaestroItem> catalogoMaestroItem) {
    this.catalogoMaestroItem = catalogoMaestroItem;
    return this;
  }
  
  
  @JsonProperty("catalogoMaestroItem")
  public List<CatalogoMaestroItem> getCatalogoMaestroItem() {
    return catalogoMaestroItem;
  }
  public void setCatalogoMaestroItem(List<CatalogoMaestroItem> catalogoMaestroItem) {
    this.catalogoMaestroItem = catalogoMaestroItem;
  }

  
  /**
   **/
  public CatalogoMaestroDTO error(Error error) {
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
    CatalogoMaestroDTO catalogoMaestroDto = (CatalogoMaestroDTO) o;
    return Objects.equals(this.catalogoMaestroItem, catalogoMaestroDto.catalogoMaestroItem) &&
        Objects.equals(this.error, catalogoMaestroDto.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogoMaestroItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CatalogoMaestroDto {\n");
    
    sb.append("    catalogoMaestroItem: ").append(toIndentedString(catalogoMaestroItem)).append("\n");
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

