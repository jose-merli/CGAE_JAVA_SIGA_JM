package org.itcgae.siga.DTOs.adm;

import java.util.Objects;
import org.itcgae.siga.DTOs.gen.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class HeaderLogoDTO {

	private byte[] imagen = null;
	private Error error;
	
	
	/**
	 *
	 */
	public HeaderLogoDTO imagen(byte[] imagen){
		this.imagen = imagen;
		return this;
	}
	
	
	@JsonProperty("imagen")
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	
	/**
	 *
	 */
	public HeaderLogoDTO error(Error error){
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
	    HeaderLogoDTO headerLogoDTO = (HeaderLogoDTO) o;
	    return Objects.equals(this.imagen, headerLogoDTO.imagen) &&
	        Objects.equals(this.error, headerLogoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(imagen, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class HeaderLogoDTO {\n");
	    
	    sb.append("    imagen: ").append(toIndentedString(imagen)).append("\n");
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
