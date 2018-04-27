package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class HistoricoUsuarioDTO {

	private List<HistoricoUsuarioItem> historicoUsuarioItem = new ArrayList<HistoricoUsuarioItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public HistoricoUsuarioDTO historicoUsuarioItem(List<HistoricoUsuarioItem> historicoUsuarioItem){
		this.historicoUsuarioItem = historicoUsuarioItem;
		return this;
	}
	
	
	@JsonProperty("historicoUsuarioItem")
	public List<HistoricoUsuarioItem> getHistoricoUsuarioItem() {
		return historicoUsuarioItem;
	}



	public void setHistoricoUsuarioItem(List<HistoricoUsuarioItem> historicoUsuarioItem) {
		this.historicoUsuarioItem = historicoUsuarioItem;
	}



	/**
	 *
	 */
	public HistoricoUsuarioDTO error(Error error){
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
	    HistoricoUsuarioDTO historicoUsuarioDTO = (HistoricoUsuarioDTO) o;
	    return Objects.equals(this.historicoUsuarioItem, historicoUsuarioDTO.historicoUsuarioItem) &&
	        Objects.equals(this.error, historicoUsuarioDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(historicoUsuarioItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class HistoricoUsuarioDTO {\n");
	    
	    sb.append("    etiquetaItem: ").append(toIndentedString(historicoUsuarioItem)).append("\n");
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
