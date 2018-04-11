package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.itcgae.siga.DTOs.gen.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioDTO {

	private List<UsuarioItem> usuarioItems = new ArrayList<UsuarioItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public UsuarioDTO usuarioItems(List<UsuarioItem> usuarioItem){
		this.usuarioItems = usuarioItem;
		return this;
	}
	
	
	@JsonProperty("usuarioItem")
	public List<UsuarioItem> getUsuarioItem() {
		return usuarioItems;
	}


	public void setUsuarioItem(List<UsuarioItem> usuarioItem) {
		this.usuarioItems = usuarioItem;
	}

	/**
	 *
	 */
	public UsuarioDTO error(Error error){
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
	    UsuarioDTO usuarioDTO = (UsuarioDTO) o;
	    return Objects.equals(this.usuarioItems, usuarioDTO.usuarioItems) &&
	        Objects.equals(this.error, usuarioDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(usuarioItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioDTO {\n");
	    
	    sb.append("    usuarioItems: ").append(toIndentedString(usuarioItems)).append("\n");
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
