package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DesasociarPersonaDTO {

	private String idPersona;
	private String idPersonaDesasociar;
	private String tipoPersona;
	private String idInstitucion;

	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@JsonProperty("idPersonaDesasociar")
	public String getIdPersonaDesasociar() {
		return idPersonaDesasociar;
	}

	public void setIdPersonaDesasociar(String idPersonaDesasociar) {
		this.idPersonaDesasociar = idPersonaDesasociar;
	}

	@JsonProperty("tipoPersona")
	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DesasociarPersonaDTO desasociarPersonaDTO = (DesasociarPersonaDTO) o;
		return Objects.equals(this.idPersona, desasociarPersonaDTO.idPersona)
				&& Objects.equals(this.idPersonaDesasociar, desasociarPersonaDTO.idPersonaDesasociar)
				&& Objects.equals(this.tipoPersona, desasociarPersonaDTO.tipoPersona)
				&& Objects.equals(this.idInstitucion, desasociarPersonaDTO.idInstitucion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPersona, idInstitucion, idPersonaDesasociar, tipoPersona);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DesasociarPersonaDTO {\n");

		sb.append("idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("idPersonaDesasociar: ").append(toIndentedString(idPersonaDesasociar)).append("\n");
		sb.append("tipoPersona: ").append(toIndentedString(tipoPersona)).append("\n");
		sb.append("idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");

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
