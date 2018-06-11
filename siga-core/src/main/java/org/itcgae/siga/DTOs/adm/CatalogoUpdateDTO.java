package org.itcgae.siga.DTOs.adm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CatalogoUpdateDTO   {
  
  private String idRegistro = null;
  private String tabla = null;
  private String idtabla = null;
  private String idInstitucion = null;
  private String descripcion = null;
  private String codigoExt = null;
  private String idLenguaje = null;
  private String local;
  
  
  


  /**
   **/
  public CatalogoUpdateDTO idRegistro(String idRegistro) {
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
  public CatalogoUpdateDTO tabla(String tabla) {
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
  public CatalogoUpdateDTO idtabla(String idtabla) {
    this.idtabla = idtabla;
    return this;
  }
  
  
  @JsonProperty("idtabla")
  public String getIdTabla() {
    return idtabla;
  }
  
  
  public void setIdTabla(String idtabla) {
    this.idtabla = idtabla;
  }
  
  /**
   **/
  public CatalogoUpdateDTO codigoExt(String codigoExt) {
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
  public CatalogoUpdateDTO descripcion(String descripcion) {
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
  public CatalogoUpdateDTO idInstitucion(String idInstitucion) {
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
  public CatalogoUpdateDTO idLenguaje(String idLenguaje) {
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
  
  
  /**
   **/
  public CatalogoUpdateDTO local(String local) {
    this.local = local;
    return this;
  }
  
  @JsonProperty("local")
  public String getLocal() {
	return local;
  }


  public void setLocal(String local) {
	this.local = local;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatalogoUpdateDTO catalogoItem = (CatalogoUpdateDTO) o;
    return Objects.equals(this.idRegistro, catalogoItem.idRegistro) &&
        Objects.equals(this.codigoExt, catalogoItem.codigoExt)&&
        Objects.equals(this.descripcion, catalogoItem.descripcion)&&
        Objects.equals(this.idInstitucion, catalogoItem.idInstitucion)&&
	    Objects.equals(this.idtabla, catalogoItem.idtabla)&&
	    Objects.equals(this.tabla, catalogoItem.tabla)&&
	    Objects.equals(this.idLenguaje, catalogoItem.idLenguaje) &&
	    Objects.equals(this.local, catalogoItem.local);

  }

  @Override
  public int hashCode() {
    return Objects.hash(idRegistro, codigoExt,descripcion,idInstitucion,idtabla,tabla,idLenguaje, local);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboItem {\n");
    
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
    sb.append("    idtabla: ").append(toIndentedString(idtabla)).append("\n");
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
    sb.append("    local: ").append(toIndentedString(local)).append("\n");
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

