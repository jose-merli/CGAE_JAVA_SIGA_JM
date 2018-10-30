package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import java.util.ArrayList;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PermisoRequestItem   {
  
  private String idGrupo = null;
  private String idInstitucion = null;
  private String idRolCertificado = null;
  private String idInstitucionCertificado = null;
  
  public String getIdInstitucionCertificado() {
	return idInstitucionCertificado;
}


public void setIdInstitucionCertificado(String idInstitucionCertificado) {
	this.idInstitucionCertificado = idInstitucionCertificado;
}


public String getIdRolCertificado() {
	return idRolCertificado;
}


public void setIdRolCertificado(String idRolCertificado) {
	this.idRolCertificado = idRolCertificado;
}


/**
   **/
  public PermisoRequestItem label(String idGrupo) {
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
  public PermisoRequestItem idInstitucion(String idInstitucion) {
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
    PermisoRequestItem permisoItem = (PermisoRequestItem) o;
    return Objects.equals(this.idGrupo, permisoItem.idGrupo) &&
        Objects.equals(this.idInstitucion, permisoItem.idInstitucion)&&
        Objects.equals(this.idRolCertificado, permisoItem.idRolCertificado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idGrupo, idInstitucion,idRolCertificado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoRequestItem {\n");
    
    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
    sb.append("    idRolCertificado: ").append(toIndentedString(idRolCertificado)).append("\n");
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

