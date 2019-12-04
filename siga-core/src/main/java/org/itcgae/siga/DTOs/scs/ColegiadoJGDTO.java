package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class ColegiadoJGDTO {

	private List<ColegiadoJGItem> colegiadoJGItem = new ArrayList<ColegiadoJGItem>();
	private Error error = null;
	
	/**
	 */
	public ColegiadoJGDTO ColegiadoJGItem(List<ColegiadoJGItem> ColegiadoJGItem){
		this.colegiadoJGItem = ColegiadoJGItem;
		return this;
	}
	
	
	public List<ColegiadoJGItem> getColegiadoJGItem() {
		return colegiadoJGItem;
	}
	public void setColegiadoJGItem(List<ColegiadoJGItem> ColegiadoJGItem) {
		this.colegiadoJGItem = ColegiadoJGItem;
	}
	
	
	/**
	 */
	public ColegiadoJGDTO error(Error error){
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
	    ColegiadoJGDTO colegiadoJGDTO = (ColegiadoJGDTO) o;
	    return Objects.equals(this.colegiadoJGItem, colegiadoJGDTO.colegiadoJGItem) &&
	    		Objects.equals(this.error, colegiadoJGDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(colegiadoJGItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ColegiadoJGDTO {\n");
	    
	    sb.append("ColegiadoJGItem: ").append(toIndentedString(colegiadoJGItem)).append("\n");
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
