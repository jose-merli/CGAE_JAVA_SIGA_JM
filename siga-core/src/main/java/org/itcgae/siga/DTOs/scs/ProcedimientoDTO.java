package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcedimientoDTO   {
  
  private List<ProcedimientoItem> procedimientosItems = new ArrayList<ProcedimientoItem>();
  private String idJuzgado = null;
  private Error error = null;

  
  /**
   * 
   **/
  public ProcedimientoDTO procedimientosItems(List<ProcedimientoItem> procedimientoItems) {
    this.procedimientosItems = procedimientoItems;
    return this;
  }
  
  @JsonProperty("procedimientosItems")
  public List<ProcedimientoItem> getProcedimientosItems() {
    return procedimientosItems;
  }
  
  public void setProcedimientosItems(List<ProcedimientoItem> procedimientoItems) {
    this.procedimientosItems = procedimientoItems;
  }
  
  /**
	 **/
	public ProcedimientoDTO idJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
		return this;
	}

	@JsonProperty("idJuzgado")
	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}
  
  /**
   * 
   **/
  public ProcedimientoDTO error(Error error) {
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
    ProcedimientoDTO juzgadoDTO = (ProcedimientoDTO) o;
    return Objects.equals(this.procedimientosItems, juzgadoDTO.procedimientosItems) &&
    		Objects.equals(this.idJuzgado, juzgadoDTO.idJuzgado) &&
        Objects.equals(this.error, juzgadoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(procedimientosItems, idJuzgado, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcedimientoDTO {\n");
    
    sb.append("    procedimientosItems: ").append(toIndentedString(procedimientosItems)).append("\n");
    sb.append("    idJuzgado: ").append(toIndentedString(idJuzgado)).append("\n");
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

