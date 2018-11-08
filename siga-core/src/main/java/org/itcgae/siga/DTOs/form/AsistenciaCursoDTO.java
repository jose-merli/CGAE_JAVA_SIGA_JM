package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class AsistenciaCursoDTO {
	private List<AsistenciaCursoItem> asistenciaCursoItem = new ArrayList<AsistenciaCursoItem>();
	private Error error = null;
	
	/**
	 */
	public AsistenciaCursoDTO AsistenciaCursoItem(List<AsistenciaCursoItem> asistenciaCursoItem){
		this.asistenciaCursoItem = asistenciaCursoItem;
		return this;
	}
	
	public List<AsistenciaCursoItem> getAsistenciaCursoItem() {
		return asistenciaCursoItem;
	}
	public void setAsistenciaCursoItem(List<AsistenciaCursoItem> asistenciaCursoItem) {
		this.asistenciaCursoItem = asistenciaCursoItem;
	}
	
	
	/**
	 */
	public AsistenciaCursoDTO error(Error error){
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
	    AsistenciaCursoDTO asistenciaCursoDTO = (AsistenciaCursoDTO) o;
	    return Objects.equals(this.asistenciaCursoItem, asistenciaCursoDTO.asistenciaCursoItem) &&
	    		Objects.equals(this.error, asistenciaCursoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(asistenciaCursoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AsistenciaCursoDTO {\n");
	    
	    sb.append("AsistenciaCursoItem: ").append(toIndentedString(asistenciaCursoItem)).append("\n");
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
