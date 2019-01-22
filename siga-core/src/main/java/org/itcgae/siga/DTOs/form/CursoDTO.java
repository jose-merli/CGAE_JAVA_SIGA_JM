package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class CursoDTO {

	private List<CursoItem> cursoItem = new ArrayList<CursoItem>();
	private Error error = null;
	
	/**
	 */
	public CursoDTO CursoItem(List<CursoItem> cursoItem){
		this.cursoItem = cursoItem;
		return this;
	}
	
	public List<CursoItem> getCursoItem() {
		return cursoItem;
	}
	public void setCursoItem(List<CursoItem> cursoItem) {
		this.cursoItem = cursoItem;
	}
	
	
	/**
	 */
	public CursoDTO error(Error error){
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
	    CursoDTO CursoDTO = (CursoDTO) o;
	    return Objects.equals(this.cursoItem, CursoDTO.cursoItem) &&
	    		Objects.equals(this.error, CursoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(cursoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CursoDTO {\n");
	    
	    sb.append("CursoItem: ").append(toIndentedString(cursoItem)).append("\n");
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
