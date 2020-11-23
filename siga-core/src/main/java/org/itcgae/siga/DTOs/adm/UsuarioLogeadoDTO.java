package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioLogeadoDTO {

	private List<UsuarioLogeadoItem> usuarioLogeadoItem = new ArrayList<UsuarioLogeadoItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public UsuarioLogeadoDTO usuarioLogeadoItem(List<UsuarioLogeadoItem> usuarioLogeadoItem){
		this.usuarioLogeadoItem = usuarioLogeadoItem;
		return this;
	}
	
	
	@JsonProperty("usuarioLogeadoItem")
	public List<UsuarioLogeadoItem> getUsuarioLogeadoItem() {
		return usuarioLogeadoItem;
	}


	public void setUsuarioLogeadoItem(List<UsuarioLogeadoItem> usuarioLogeadoItem) {
		this.usuarioLogeadoItem = usuarioLogeadoItem;
	}

	/**
	 *
	 */
	public UsuarioLogeadoDTO error(Error error){
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
	    UsuarioLogeadoDTO usuarioDTO = (UsuarioLogeadoDTO) o;
	    return Objects.equals(this.usuarioLogeadoItem, usuarioDTO.usuarioLogeadoItem) &&
	        Objects.equals(this.error, usuarioDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(usuarioLogeadoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioDTO {\n");
	    
	    sb.append("    usuarioLogeadoItem: ").append(toIndentedString(usuarioLogeadoItem)).append("\n");
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
