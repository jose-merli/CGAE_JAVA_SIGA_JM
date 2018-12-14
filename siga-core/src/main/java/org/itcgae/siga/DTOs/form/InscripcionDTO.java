package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class InscripcionDTO {

	private List<InscripcionItem> inscripcionItem = new ArrayList<InscripcionItem>();
	private Error error = null;
	
	/**
	 */
	public InscripcionDTO InscripcionItem(List<InscripcionItem> inscripcionItem){
		this.inscripcionItem = inscripcionItem;
		return this;
	}
	
	public List<InscripcionItem> getInscripcionItem() {
		return inscripcionItem;
	}
	public void setInscripcionItem(List<InscripcionItem> inscripcionItem) {
		this.inscripcionItem = inscripcionItem;
	}
	
	
	/**
	 */
	public InscripcionDTO error(Error error){
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
	    InscripcionDTO InscripcionDTO = (InscripcionDTO) o;
	    return Objects.equals(this.inscripcionItem, InscripcionDTO.inscripcionItem) &&
	    		Objects.equals(this.error, InscripcionDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(inscripcionItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class InscripcionDTO {\n");
	    
	    sb.append("InscripcionItem: ").append(toIndentedString(inscripcionItem)).append("\n");
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
