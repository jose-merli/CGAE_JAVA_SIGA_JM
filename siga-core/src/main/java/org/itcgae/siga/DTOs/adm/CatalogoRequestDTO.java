package org.itcgae.siga.DTOs.adm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CatalogoRequestDTO   {
  
  private String catalogo = null;
  private String codigoExt = null;
  private String descripcion = null;
  private String idInstitucion = null;
  private String idLenguaje = null;
  
  /**
   **/
  public CatalogoRequestDTO catalogo(String catalogo) {
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
  public CatalogoRequestDTO codigoExt(String codigoExt) {
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
  public CatalogoRequestDTO descripcion(String descripcion) {
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
  public CatalogoRequestDTO idInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
    return this;
  }
  
  
  @JsonProperty("idInstitucion")
  public String getIdInstitucion() {
    return idInstitucion;
  }
  public void setIdInstitucion(String idInstitucion) {
    this.idInstitucion = idInstitucion;
  }


  
  /**
   **/
  public CatalogoRequestDTO idLenguaje(String idLenguaje) {
    this.idLenguaje = idLenguaje;
    return this;
  }
  
  
  @JsonProperty("idLenguaje")
  public String getIdLenguaje() {
    return idLenguaje;
  }
  public void setIdLenguaje(String idLenguaje) {
    this.idLenguaje = idLenguaje;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatalogoRequestDTO comboItem = (CatalogoRequestDTO) o;
    return Objects.equals(this.catalogo, comboItem.catalogo) &&
        Objects.equals(this.codigoExt, comboItem.codigoExt)&&
        Objects.equals(this.descripcion, comboItem.descripcion)&&
        Objects.equals(this.idInstitucion, comboItem.idInstitucion)&&
    	Objects.equals(this.idLenguaje, comboItem.idLenguaje);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogo, codigoExt,descripcion,idInstitucion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboItem {\n");
    
    sb.append("    catalogo: ").append(toIndentedString(catalogo)).append("\n");
    sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
    sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
    
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

