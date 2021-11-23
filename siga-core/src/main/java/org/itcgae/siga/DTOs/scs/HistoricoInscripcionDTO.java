package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricoInscripcionDTO {
	 private List<HistoricoInscripcionItem> inscripcionesTarjetaItem = new ArrayList<HistoricoInscripcionItem>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public HistoricoInscripcionDTO inscripcionesTarjetaItem(List<HistoricoInscripcionItem> inscripcionesTarjetaItem) {
	    this.inscripcionesTarjetaItem = inscripcionesTarjetaItem;
	    return this;
	  }
	  
	  @JsonProperty("inscripcionesTarjetaItem")
	  public List<HistoricoInscripcionItem> getInscripcionesTarjetaItems() {
	    return inscripcionesTarjetaItem;
	  }
	  
	  public void setInscripcionesTarjetaItems(List<HistoricoInscripcionItem> inscripcionesTarjetaItem) {
	    this.inscripcionesTarjetaItem = inscripcionesTarjetaItem;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public HistoricoInscripcionDTO error(Error error) {
	    this.error = error;
	    return this;
	  }
	  
	  @JsonProperty("error")
	  public Error getError() {
	    return error;
	  }
	  
	  public void setError(Error error) {
	    this.error = error;
	  }
	  
	  
	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    HistoricoInscripcionDTO inscripcionesTarjetaDTO = (HistoricoInscripcionDTO) o;
	    return Objects.equals(this.inscripcionesTarjetaItem, inscripcionesTarjetaDTO.inscripcionesTarjetaItem) &&
	        Objects.equals(this.error, inscripcionesTarjetaDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(inscripcionesTarjetaItem, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class inscripcionesDTO {\n");
	    
	    sb.append("    inscripcionesTarjetaItem: ").append(toIndentedString(inscripcionesTarjetaItem)).append("\n");
	    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
