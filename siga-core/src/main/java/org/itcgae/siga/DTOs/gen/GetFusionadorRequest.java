package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class GetFusionadorRequest   {
  
  private String idGrupo = null;
  private String idInstitucion = null;


  
  /**
   **/
  public GetFusionadorRequest label(String idGrupo) {
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
  public GetFusionadorRequest idInstitucion(String idInstitucion) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetFusionadorRequest permisoItem = (GetFusionadorRequest) o;
    return Objects.equals(this.idGrupo, permisoItem.idGrupo) &&
        Objects.equals(this.idInstitucion, permisoItem.idInstitucion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idGrupo, idInstitucion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoRequestItem {\n");
    
    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
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

