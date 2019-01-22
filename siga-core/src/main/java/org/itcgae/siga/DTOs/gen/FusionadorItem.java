package org.itcgae.siga.DTOs.gen;

import java.util.Objects;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FusionadorItem   {
  
  private String idPersonaDestino = null;
  private String idPersonaOrigen = null;
  private String listaDirecciones = null;
  private String listaDireccionesNoSeleccionadas = null;

  

  public String getIdPersonaDestino() {
	return idPersonaDestino;
}

public void setIdPersonaDestino(String idPersonaDestino) {
	this.idPersonaDestino = idPersonaDestino;
}

public String getIdPersonaOrigen() {
	return idPersonaOrigen;
}

public void setIdPersonaOrigen(String idPersonaOrigen) {
	this.idPersonaOrigen = idPersonaOrigen;
}

public String getListaDirecciones() {
	return listaDirecciones;
}

public void setListaDirecciones(String listaDirecciones) {
	this.listaDirecciones = listaDirecciones;
}

public String getListaDireccionesNoSeleccionadas() {
	return listaDireccionesNoSeleccionadas;
}

public void setListaDireccionesNoSeleccionadas(String listaDireccionesNoSeleccionadas) {
	this.listaDireccionesNoSeleccionadas = listaDireccionesNoSeleccionadas;
}

@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FusionadorItem fusionadorItem = (FusionadorItem) o;
    return Objects.equals(this.idPersonaDestino, fusionadorItem.idPersonaDestino) &&
        Objects.equals(this.idPersonaOrigen, fusionadorItem.idPersonaOrigen)&&
        Objects.equals(this.listaDirecciones, fusionadorItem.listaDirecciones) &&
        Objects.equals(this.listaDireccionesNoSeleccionadas, fusionadorItem.listaDireccionesNoSeleccionadas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPersonaDestino, idPersonaOrigen,listaDirecciones,listaDireccionesNoSeleccionadas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FusionadorItem {\n");
    sb.append("    idPersonaDestino: ").append(toIndentedString(idPersonaDestino)).append("\n");
    sb.append("    idPersonaOrigen: ").append(toIndentedString(idPersonaOrigen)).append("\n");
    sb.append("    listaDirecciones: ").append(toIndentedString(listaDirecciones)).append("\n");
    sb.append("    listaDireccionesNoSeleccionadas: ").append(toIndentedString(listaDireccionesNoSeleccionadas)).append("\n");
    
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

