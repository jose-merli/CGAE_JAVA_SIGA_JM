package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PersonaJuridicaFotoDTO {

	private String idPersona;
	
	
	/**
	 *
	 */
	public PersonaJuridicaFotoDTO idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}
	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    PersonaJuridicaFotoDTO personaJuridicaFotoDTO = (PersonaJuridicaFotoDTO) o;
	    return Objects.equals(this.idPersona, personaJuridicaFotoDTO.idPersona);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class PersonaJuridicaFotoDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
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
