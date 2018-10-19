package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoCurricularDTO {
	
	private List<TipoCurricularItem> tipoCurricularItems = new ArrayList<TipoCurricularItem>();
	private Error error = null;
	
	@JsonProperty("tipoCurricularItems")
	public List<TipoCurricularItem> getTipoCurricularItems() {
		return tipoCurricularItems;
	}
	public void setTipoCurricularItems(List<TipoCurricularItem> tipoCurricularItems) {
		this.tipoCurricularItems = tipoCurricularItems;
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
	    TipoCurricularDTO tipoCurricularDTO = (TipoCurricularDTO) o;
	    return Objects.equals(this.tipoCurricularItems, tipoCurricularDTO.tipoCurricularItems) &&
	        Objects.equals(this.error, tipoCurricularDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipoCurricularItems, error);
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TipoCurricularDTO {\n");
	    
	    sb.append("    tipoCurricularItems: ").append(toIndentedString(tipoCurricularItems)).append("\n");
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
