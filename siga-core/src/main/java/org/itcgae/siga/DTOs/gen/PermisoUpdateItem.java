package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PermisoUpdateItem   {
  
  private String idGrupo = null;
  private String id = null;
  private String derechoacceso = null;

  
  /**
   **/
  public PermisoUpdateItem idGrupo(String idGrupo) {
    this.idGrupo = idGrupo;
    return this;
  }
  
  
  @JsonProperty("idGrupo")
  public String getIdGrupo() {
    return idGrupo;
  }
  public void setIdGrupo(String idGrupo) {
    this.idGrupo = idGrupo;
  }

  
  /**
   **/
  public PermisoUpdateItem data(String id) {
    this.id = id;
    return this;
  }
  
  
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  
  /**
   **/
  public PermisoUpdateItem derechoacceso(String derechoacceso) {
    this.derechoacceso = derechoacceso;
    return this;
  }
  
  
  @JsonProperty("derechoacceso")
  public String getDerechoacceso() {
    return derechoacceso;
  }
  public void setDerechoacceso(String derechoacceso) {
    this.derechoacceso = derechoacceso;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermisoUpdateItem permisoItem = (PermisoUpdateItem) o;
    return Objects.equals(this.idGrupo, permisoItem.idGrupo) &&
        Objects.equals(this.id, permisoItem.id) &&
        Objects.equals(this.derechoacceso, permisoItem.derechoacceso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idGrupo, id,derechoacceso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoItem {\n");
    
    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    derechoacceso: ").append(toIndentedString(derechoacceso)).append("\n");
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

