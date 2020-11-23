package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class RolPerfilDTO   {
  
  private String idRol = null;
  private String descripcion = null;
  private String grupopordefecto = null;

  
  /**
   **/
  public RolPerfilDTO idRol(String idRol) {
    this.idRol = idRol;
    return this;
  }
  
  
  @JsonProperty("idRol")
  public String getIdRol() {
    return idRol;
  }
  public void setIdRol(String idRol) {
    this.idRol = idRol;
  }

  
  /**
   **/
  public RolPerfilDTO descripcion(String descripcion) {
    this.descripcion = descripcion;
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
  public RolPerfilDTO grupopordefecto(String grupopordefecto) {
    this.grupopordefecto = grupopordefecto;
    return this;
  }
  
  
  @JsonProperty("grupopordefecto")
  public String getGrupopordefecto() {
    return grupopordefecto;
  }
  public void setGrupopordefecto(String grupopordefecto) {
    this.grupopordefecto = grupopordefecto;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RolPerfilDTO comboItem = (RolPerfilDTO) o;
    return Objects.equals(this.idRol, comboItem.idRol) &&
    		Objects.equals(this.grupopordefecto, comboItem.grupopordefecto) &&
        Objects.equals(this.descripcion, comboItem.descripcion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(descripcion, idRol,grupopordefecto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RolPerfilDTO {\n");
    sb.append("    idRol: ").append(toIndentedString(idRol)).append("\n");
    sb.append("    grupopordefecto: ").append(toIndentedString(grupopordefecto)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    
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

