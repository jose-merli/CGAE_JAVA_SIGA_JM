package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class PreciosCursoDTO {

	private List<PreciosCursoItem> preciosCursoItem = new ArrayList<PreciosCursoItem>();
	private Error error = null;
	
	/**
	 */
	public PreciosCursoDTO PreciosCursoItem(List<PreciosCursoItem> preciosCursoItem){
		this.preciosCursoItem = preciosCursoItem;
		return this;
	}
	
	public List<PreciosCursoItem> getPreciosCursoItem() {
		return preciosCursoItem;
	}
	public void setPreciosCursoItem(List<PreciosCursoItem> preciosCursoItem) {
		this.preciosCursoItem = preciosCursoItem;
	}
	
	
	/**
	 */
	public PreciosCursoDTO error(Error error){
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
	    PreciosCursoDTO CursoDTO = (PreciosCursoDTO) o;
	    return Objects.equals(this.preciosCursoItem, CursoDTO.preciosCursoItem) &&
	    		Objects.equals(this.error, CursoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(preciosCursoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class PreciosCursoDTO {\n");
	    
	    sb.append("PreciosCursoItem: ").append(toIndentedString(preciosCursoItem)).append("\n");
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
