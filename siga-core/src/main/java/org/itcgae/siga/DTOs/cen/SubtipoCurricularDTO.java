package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubtipoCurricularDTO {
	private List<SubtipoCurricularItem> subtipoCurricularItems = new ArrayList<SubtipoCurricularItem>();
	private Error error = null;
	
	@JsonProperty("subtipoCurricularItems")
	public List<SubtipoCurricularItem> getSubtipoCurricularItems() {
		return subtipoCurricularItems;
	}
	public void setSubtipoCurricularItems(List<SubtipoCurricularItem> subtipoCurricularItems) {
		this.subtipoCurricularItems = subtipoCurricularItems;
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
	    SubtipoCurricularDTO subtipoCurricularDTO = (SubtipoCurricularDTO) o;
	    return Objects.equals(this.subtipoCurricularItems, subtipoCurricularDTO.subtipoCurricularItems) &&
	        Objects.equals(this.error, subtipoCurricularDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(subtipoCurricularItems, error);
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SubtipoCurricularDTO {\n");
	    
	    sb.append("    subtipoCurricularItems: ").append(toIndentedString(subtipoCurricularItems)).append("\n");
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
