package org.itcgae.siga.DTOs.adm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class QueryCatalogosDTO   {
  
  private String catalogo = null;
  private String codigoExt = null;
  private String descripcion = null;
  private String idInstitucion = null;
  
  /**
   **/
  public QueryCatalogosDTO catalogo(String catalogo) {
    this.catalogo = catalogo;
    return this;
  }
  
  
  @JsonProperty("catalogo")
  public String getCatalogo() {
    return catalogo;
  }
  public void setCatalogo(String catalogo) {
    this.catalogo = catalogo;
  }

  
  /**
   **/
  public QueryCatalogosDTO codigoExt(String codigoExt) {
    this.codigoExt = codigoExt;
    return this;
  }
  
  
  @JsonProperty("codigoExt")
  public String getCodigoExt() {
    return codigoExt;
  }
  public void setCodigoExt(String codigoExt) {
    this.codigoExt = codigoExt;
  }

  /**
   **/
  public QueryCatalogosDTO descripcion(String descripcion) {
    this.codigoExt = descripcion;
    return this;
  }
  
  
  @JsonProperty("descripcion")
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  
  /**
   **/
  public QueryCatalogosDTO idInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
    return this;
  }
  
  
  @JsonProperty("idInstitucion")
  public String getIdInstitucion() {
    return codigoExt;
  }
  public void setIdInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryCatalogosDTO comboItem = (QueryCatalogosDTO) o;
    return Objects.equals(this.catalogo, comboItem.catalogo) &&
        Objects.equals(this.codigoExt, comboItem.codigoExt)&&
        Objects.equals(this.descripcion, comboItem.descripcion)&&
        Objects.equals(this.idInstitucion, comboItem.idInstitucion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogo, codigoExt,descripcion,idInstitucion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryCatalogosDTO {\n");
    
    sb.append("    catalogo: ").append(toIndentedString(catalogo)).append("\n");
    sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
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

