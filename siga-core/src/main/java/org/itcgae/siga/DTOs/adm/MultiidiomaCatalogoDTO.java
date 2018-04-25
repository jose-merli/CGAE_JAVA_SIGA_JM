package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MultiidiomaCatalogoDTO {

	private List<MultiidiomaCatalogoItem> multiidiomaCatalogoItem = new ArrayList<MultiidiomaCatalogoItem>();
	private Error error = null;
	
	
	/**
	**/
	public MultiidiomaCatalogoDTO etiquetaItem(List<MultiidiomaCatalogoItem> multiidiomaCatalogoItem) {
		this.multiidiomaCatalogoItem = multiidiomaCatalogoItem;
		return this;
	}
	
	
	@JsonProperty("multiidiomaCatalogoItem")
	public List<MultiidiomaCatalogoItem> getMultiidiomaCatalogoItem() {
		return multiidiomaCatalogoItem;
	}
	
	
	public void setMultiidiomaCatalogoItem(List<MultiidiomaCatalogoItem> multiidiomaCatalogoItem) {
		this.multiidiomaCatalogoItem = multiidiomaCatalogoItem;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoDTO error(Error error) {
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
	    MultiidiomaCatalogoDTO multiidiomaCatalogoDTO = (MultiidiomaCatalogoDTO) o;
	    return Objects.equals(this.multiidiomaCatalogoItem, multiidiomaCatalogoDTO.multiidiomaCatalogoItem) &&
	        Objects.equals(this.error, multiidiomaCatalogoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(multiidiomaCatalogoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MultiidiomaCatalogoDTO {\n");
	    
	    sb.append("    multiidiomaCatalogoItem: ").append(toIndentedString(multiidiomaCatalogoItem)).append("\n");
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
