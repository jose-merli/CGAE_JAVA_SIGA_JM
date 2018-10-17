package org.itcgae.siga.DTOs.age;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PermisosPerfilesCalendarItem   {
  
  private String idPerfil;
  private String nombrePerfil; 
  private String derechoacceso;

  
  /**
   **/
  public PermisosPerfilesCalendarItem idPerfil(String idPerfil) {
    this.idPerfil = idPerfil;
    return this;
  }
  
  
  @JsonProperty("idPerfil")
  public String getIdPerfil() {
    return idPerfil;
  }
  public void setIdPerfil(String idPerfil) {
    this.idPerfil = idPerfil;
  }

  
  /**
   **/
  public PermisosPerfilesCalendarItem nombrePerfil(String nombrePerfil) {
    this.nombrePerfil = nombrePerfil;
    return this;
  }
  
  
  @JsonProperty("nombrePerfil")
  public String getNombrePerfil() {
    return nombrePerfil;
  }
  public void setNombrePerfil(String nombrePerfil) {
    this.nombrePerfil = nombrePerfil;
  }
    
  /**
   **/
  public PermisosPerfilesCalendarItem derechoacceso(String derechoacceso) {
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
    PermisosPerfilesCalendarItem permisoItem = (PermisosPerfilesCalendarItem) o;
    return Objects.equals(this.idPerfil, permisoItem.idPerfil) &&
        Objects.equals(this.derechoacceso, permisoItem.derechoacceso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPerfil, nombrePerfil ,derechoacceso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisosPerfilesCalendarItem {\n");
    
    sb.append("    idPerfil: ").append(toIndentedString(idPerfil)).append("\n");
    sb.append("    nombrePerfil: ").append(toIndentedString(nombrePerfil)).append("\n");
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

