package org.itcgae.siga.DTOs.cen;

import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RetencionesDTO {

	
	private List<RetencionesItem> retencionesItemList;
	private Error error;
	private boolean activo;
	
	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	/**
	 *
	 */
	public RetencionesDTO retencionesItemList(List<RetencionesItem> retencionesItemList){
		this.retencionesItemList = retencionesItemList;
		return this;
	}
	
	
	@JsonProperty("retencionesItemList")
	public List<RetencionesItem> getRetencionesItemList() {
		return retencionesItemList;
	}
	public void setRetencionesItemList(List<RetencionesItem> retencionesItemList) {
		this.retencionesItemList = retencionesItemList;
	}
	
	
	/**
	 *
	 */
	public RetencionesDTO error(Error error){
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
	    RetencionesDTO retencionesDTO = (RetencionesDTO) o;
	    return Objects.equals(this.retencionesItemList, retencionesDTO.retencionesItemList) &&
	    		Objects.equals(this.error, retencionesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(retencionesItemList, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class RetencionesDTO {\n");
	    
	    sb.append("retencionesItemList: ").append(toIndentedString(retencionesItemList)).append("\n");
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
