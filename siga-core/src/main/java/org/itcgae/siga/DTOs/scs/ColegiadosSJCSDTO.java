package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class ColegiadosSJCSDTO {

	private List<ColegiadosSJCSItem> colegiadosSJCSItem = new ArrayList<ColegiadosSJCSItem>();
	private Error error = null;
	
	/**
	 */
	public ColegiadosSJCSDTO ColegiadosSJCSItem(List<ColegiadosSJCSItem> ColegiadosSJCSItem){
		this.colegiadosSJCSItem = ColegiadosSJCSItem;
		return this;
	}
	
	
	public List<ColegiadosSJCSItem> getColegiadosSJCSItem() {
		return colegiadosSJCSItem;
	}
	public void setColegiadosSJCSItem(List<ColegiadosSJCSItem> ColegiadosSJCSItem) {
		this.colegiadosSJCSItem = ColegiadosSJCSItem;
	}
	
	
	/**
	 */
	public ColegiadosSJCSDTO error(Error error){
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
	    ColegiadosSJCSDTO colegiadoJGDTO = (ColegiadosSJCSDTO) o;
	    return Objects.equals(this.colegiadosSJCSItem, colegiadoJGDTO.colegiadosSJCSItem) &&
	    		Objects.equals(this.error, colegiadoJGDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(colegiadosSJCSItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ColegiadoJGDTO {\n");
	    
	    sb.append("ColegiadosSJCSItem: ").append(toIndentedString(colegiadosSJCSItem)).append("\n");
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
