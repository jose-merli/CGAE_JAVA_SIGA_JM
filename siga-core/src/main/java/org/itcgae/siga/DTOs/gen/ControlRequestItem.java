package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ControlRequestItem   {
  
  private String idProceso;
  private String idGrupo;
  private String institucion;


  
  /**
   **/
  public ControlRequestItem idProceso(String idProceso) {
    this.idProceso = idProceso;
    
    return this;
  }
  
  
  @JsonProperty("idProceso")
  public String getIdProceso() {
    return idProceso;
  }
  public void setIdProceso(String idProceso) {
    this.idProceso = idProceso;
  }

  
  
  /**
   **/
  public ControlRequestItem idGrupo(String idGrupo) {
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
  public ControlRequestItem institucion(String institucion) {
    this.idProceso = institucion;
    
    return this;
  }
  
  
  @JsonProperty("institucion")
  public String getInstitucion() {
    return institucion;
  }
  public void setInstitucion(String institucion) {
    this.institucion = institucion;
  }
  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ControlRequestItem permisoItem = (ControlRequestItem) o;
    return Objects.equals(this.idProceso, permisoItem.idProceso)&&
    		Objects.equals(this.idGrupo, permisoItem.idGrupo)&&
    		Objects.equals(this.institucion, permisoItem.institucion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProceso,idGrupo, institucion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ControlRequestItem {\n");
    
    sb.append("    idProceso: ").append(toIndentedString(idProceso)).append("\n");
    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
    sb.append("    institucion: ").append(toIndentedString(institucion)).append("\n");
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

