package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class ColegiadoDTO {

	private List<ColegiadoItem> colegiadoItem = new ArrayList<ColegiadoItem>();
	private Error error = null;
	
	/**
	 */
	public ColegiadoDTO ColegiadoItem(List<ColegiadoItem> colegiadoItem){
		this.colegiadoItem = colegiadoItem;
		return this;
	}
	
	
	public List<ColegiadoItem> getColegiadoItem() {
		return colegiadoItem;
	}
	public void setColegiadoItem(List<ColegiadoItem> colegiadoItem) {
		this.colegiadoItem = colegiadoItem;
	}
	
	
	/**
	 */
	public ColegiadoDTO error(Error error){
		this.error = error;
		return this;
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
	    ColegiadoDTO colegiadoDTO = (ColegiadoDTO) o;
	    return Objects.equals(this.colegiadoItem, colegiadoDTO.colegiadoItem) &&
	    		Objects.equals(this.error, colegiadoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(colegiadoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ColegiadoDTO {\n");
	    
	    sb.append("colegiadoItem: ").append(toIndentedString(colegiadoItem)).append("\n");
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
