package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonaSearchDTO {

	private String idPersona;
	private String idInstitucion;
	private String idLenguaje;

	/**
	 *
	 */
	public PersonaSearchDTO idPersona(String idPersona){
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

	/**
	 *
	 */
	public PersonaSearchDTO idInstitucion(String idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	
	/**
	 *
	 */
	public PersonaSearchDTO idLenguaje(String idLenguaje){
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
		PersonaSearchDTO personaSearchDTO = (PersonaSearchDTO) o;
		return Objects.equals(this.idPersona, personaSearchDTO.idPersona)
				&& Objects.equals(this.idInstitucion, personaSearchDTO.idInstitucion)
				&& Objects.equals(this.idLenguaje, personaSearchDTO.idLenguaje);

	}

	@Override
	public int hashCode() {
		return Objects.hash(idPersona, idInstitucion, idLenguaje);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PersonaSearchDTO {\n");

		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
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
