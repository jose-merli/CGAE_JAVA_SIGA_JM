package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class EntidadLenguajeInstitucionDTO {

	private String idLenguaje;


	/**
	 *
	 */
	public EntidadLenguajeInstitucionDTO idLenguaje(String idLenguaje){
		this.idLenguaje = idLenguaje;
		return this;
	}
	
	@JsonProperty("idLenguaje")
	public String getIdLenguaje() {
		return idLenguaje;
	}

	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    EntidadLenguajeInstitucionDTO entidadLenguajeInstitucionDTO = (EntidadLenguajeInstitucionDTO) o;
	    return Objects.equals(this.idLenguaje, entidadLenguajeInstitucionDTO.idLenguaje);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idLenguaje);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EntidadLenguajeInstitucionDTO {\n");
	    sb.append("    etiquetaItem: ").append(toIndentedString(idLenguaje)).append("\n");
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
