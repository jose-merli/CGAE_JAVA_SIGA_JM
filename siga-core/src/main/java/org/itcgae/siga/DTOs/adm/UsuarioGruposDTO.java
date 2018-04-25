package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.itcgae.siga.DTOs.gen.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioGruposDTO {

	private List<UsuarioGrupoItem> usuarioGrupoItems = new ArrayList<UsuarioGrupoItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public UsuarioGruposDTO usuarioItems(List<UsuarioGrupoItem> usuarioGrupoItems){
		this.usuarioGrupoItems = usuarioGrupoItems;
		return this;
	}
	
	
	@JsonProperty("usuarioGrupoItems")
	public List<UsuarioGrupoItem> getUsuarioGrupoItems() {
		return usuarioGrupoItems;
	}


	public void setUsuarioGrupoItem(List<UsuarioGrupoItem> usuarioGrupoItems) {
		this.usuarioGrupoItems = usuarioGrupoItems;
	}

	/**
	 *
	 */
	public UsuarioGruposDTO error(Error error){
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
	    UsuarioGruposDTO usuarioGruposDTO = (UsuarioGruposDTO) o;
	    return Objects.equals(this.usuarioGrupoItems, usuarioGruposDTO.usuarioGrupoItems) &&
	        Objects.equals(this.error, usuarioGruposDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(usuarioGrupoItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioGruposDTO {\n");
	    
	    sb.append("    usuarioGruposDTO: ").append(toIndentedString(usuarioGrupoItems)).append("\n");
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
