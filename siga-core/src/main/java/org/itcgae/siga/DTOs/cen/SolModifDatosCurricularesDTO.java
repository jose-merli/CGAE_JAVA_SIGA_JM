package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class SolModifDatosCurricularesDTO {
	private List<SolModifDatosCurricularesItem> solModifDatosCurricularesItems = new ArrayList<SolModifDatosCurricularesItem>();
	private Error error = null;
	
	
	public List<SolModifDatosCurricularesItem> getSolModifDatosCurricularesItems() {
		return solModifDatosCurricularesItems;
	}
	public void setSolModifDatosCurricularesItems(List<SolModifDatosCurricularesItem> solModifDatosCurricularesItems) {
		this.solModifDatosCurricularesItems = solModifDatosCurricularesItems;
	}
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
	    SolModifDatosCurricularesDTO solModifDatosCurricularesDTO = (SolModifDatosCurricularesDTO) o;
	    return Objects.equals(this.solModifDatosCurricularesItems, solModifDatosCurricularesDTO.solModifDatosCurricularesItems) &&
	    		Objects.equals(this.error, solModifDatosCurricularesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(solModifDatosCurricularesItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SolModifDatosCurricularesDTO {\n");
	    
	    sb.append("solModifDatosCurricularesItems: ").append(toIndentedString(solModifDatosCurricularesItems)).append("\n");
	    sb.append("error: ").append(toIndentedString(error)).append("\n");
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
