package org.itcgae.siga.DTOs.cen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ParametroColegioDTO {

	private String parametro;
	private Error error;
	
	
	
	/**
	 */
	public ParametroColegioDTO error(Error error){
		this.error = error;
		return this;
	}
	
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	/**
	 */
	public ParametroColegioDTO parametro(String parametro){
		this.parametro = parametro;
		return this;
	}
	
	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    ParametroColegioDTO parametroColegioDTO = (ParametroColegioDTO) o;
	    return Objects.equals(this.parametro, parametroColegioDTO.parametro) &&
	    		Objects.equals(this.error, parametroColegioDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(parametro, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ParametroColegioDTO {\n");
	    
	    sb.append("  parametro: ").append(toIndentedString(parametro)).append("\n");
	    sb.append("  error: ").append(toIndentedString(error)).append("\n");
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

	
	
	
	
	
	

