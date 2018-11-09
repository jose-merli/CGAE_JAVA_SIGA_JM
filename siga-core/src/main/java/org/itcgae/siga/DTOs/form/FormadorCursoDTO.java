package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class FormadorCursoDTO {

	private List<FormadorCursoItem> formadoresCursoItem = new ArrayList<FormadorCursoItem>();
	private Error error = null;
	
	/**
	 */
	public FormadorCursoDTO formadoresCursoItem(List<FormadorCursoItem> formadoresCursoItem){
		this.formadoresCursoItem = formadoresCursoItem;
		return this;
	}
	
	public List<FormadorCursoItem> getFormadoresCursoItem() {
		return formadoresCursoItem;
	}
	public void setFormadoresCursoItem(List<FormadorCursoItem> formadoresCursoItem) {
		this.formadoresCursoItem = formadoresCursoItem;
	}
	
	
	/**
	 */
	public FormadorCursoDTO error(Error error){
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
	    FormadorCursoDTO formadorCursoDTO = (FormadorCursoDTO) o;
	    return Objects.equals(this.formadoresCursoItem, formadorCursoDTO.formadoresCursoItem) &&
	    		Objects.equals(this.error, formadorCursoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(formadoresCursoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FormadorCursoDTO {\n");
	    
	    sb.append("formadorCursoItem: ").append(toIndentedString(formadoresCursoItem)).append("\n");
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
