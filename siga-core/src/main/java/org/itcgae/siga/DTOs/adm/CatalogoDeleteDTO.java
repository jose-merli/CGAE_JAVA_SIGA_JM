package org.itcgae.siga.DTOs.adm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CatalogoDeleteDTO   {
  
  private String idRegistro = null;
  private String tabla = null;
  private String idtabla = null;
  private String idInstitucion = null;
  private String idLenguaje = null;
  
  
  /**
   **/
  public CatalogoDeleteDTO idRegistro(String idRegistro) {
    this.idRegistro = idRegistro;
    return this;
  }
  
  
  @JsonProperty("idRegistro")
  public String getIdRegistro() {
    return idRegistro;
  }
  public void setIdRegistro(String idRegistro) {
    this.idRegistro = idRegistro;
  }

  /**
   **/
  public CatalogoDeleteDTO tabla(String tabla) {
    this.tabla = tabla;
    return this;
  }
  
  
  @JsonProperty("tabla")
  public String getTabla() {
    return tabla;
  }
  public void setTabla(String tabla) {
    this.tabla = tabla;
  }
  /**
   **/
  public CatalogoDeleteDTO idtabla(String idtabla) {
    this.idtabla = idtabla;
    return this;
  }
  
  
  @JsonProperty("idtabla")
  public String getIdTablaRegistro() {
    return idRegistro;
  }
  public void setIdTabla(String idtabla) {
    this.idtabla = idtabla;
  }
  

  
  
  
  /**
   **/
  public CatalogoDeleteDTO idInstitucion(String idInstitucion) {
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
  public CatalogoDeleteDTO idLenguaje(String idLenguaje) {
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
    CatalogoDeleteDTO catalogoItem = (CatalogoDeleteDTO) o;
    return Objects.equals(this.idRegistro, catalogoItem.idRegistro) &&
        Objects.equals(this.idInstitucion, catalogoItem.idInstitucion)&&
	    Objects.equals(this.idtabla, catalogoItem.idtabla)&&
	    Objects.equals(this.tabla, catalogoItem.tabla)&&
	    Objects.equals(this.idLenguaje, catalogoItem.idLenguaje);

  }

  @Override
  public int hashCode() {
    return Objects.hash(idRegistro, idInstitucion,idtabla,tabla,idLenguaje);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboItem {\n");
    
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
    sb.append("    idtabla: ").append(toIndentedString(idtabla)).append("\n");
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
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

