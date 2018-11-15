package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolicitModifDatosBasicosDTO {
	private List<CenSolicitmodifdatosbasicos> solicitModifDatosBasicosItems = new ArrayList<CenSolicitmodifdatosbasicos>();
	private Error error = null;
	
	@JsonProperty("solicitModifDatosBasicosItems")
	public List<CenSolicitmodifdatosbasicos> getSolModificacionItems() {
		return solicitModifDatosBasicosItems;
	}
	public void setSolModificacionItems(List<CenSolicitmodifdatosbasicos> solicitModifDatosBasicosItems) {
		this.solicitModifDatosBasicosItems = solicitModifDatosBasicosItems;
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
	    SolicitModifDatosBasicosDTO solicitModifDatosBasicosDTO = (SolicitModifDatosBasicosDTO) o;
	    return Objects.equals(this.solicitModifDatosBasicosItems, solicitModifDatosBasicosDTO.solicitModifDatosBasicosItems) &&
	        Objects.equals(this.error, solicitModifDatosBasicosDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(solicitModifDatosBasicosItems, error);
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SolicitModifDatosBasicosItem {\n");
	    
	    sb.append("    solicitModifDatosBasicosItems: ").append(toIndentedString(solicitModifDatosBasicosItems)).append("\n");
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
