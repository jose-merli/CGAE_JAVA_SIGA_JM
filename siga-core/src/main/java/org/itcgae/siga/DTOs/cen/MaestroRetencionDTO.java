package org.itcgae.siga.DTOs.cen;

import java.util.List;
import java.util.Objects;
import org.itcgae.siga.DTOs.gen.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MaestroRetencionDTO {

	private List<MaestroRetencionItem> maestroRetencionItem;
	private Error error;
	
	
	/**
	 *
	 */
	public MaestroRetencionDTO maestroRetencionItem( List<MaestroRetencionItem> maestroRetencionItem){
		this.maestroRetencionItem = maestroRetencionItem;
		return this;
	}
	
	@JsonProperty("maestroRetencionItem")
	public List<MaestroRetencionItem> getMaestroRetencionItem() {
		return maestroRetencionItem;
	}
	public void setMaestroRetencionItem(List<MaestroRetencionItem> maestroRetencionItem) {
		this.maestroRetencionItem = maestroRetencionItem;
	}
	
	
	/**
	 *
	 */
	public MaestroRetencionDTO error(Error error){
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
	    MaestroRetencionDTO maestroRetencionDTO = (MaestroRetencionDTO) o;
	    return Objects.equals(this.maestroRetencionItem, maestroRetencionDTO.maestroRetencionItem) &&
	    	   Objects.equals(this.error, maestroRetencionDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(maestroRetencionItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MaestroRetencionDTO {\n");
	    
	    sb.append("    maestroRetencionItem: ").append(toIndentedString(maestroRetencionItem)).append("\n");
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
