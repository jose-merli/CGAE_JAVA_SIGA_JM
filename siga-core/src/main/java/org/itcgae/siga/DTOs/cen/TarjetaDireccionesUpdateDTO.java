package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaDireccionesUpdateDTO {

	
	
	private String idDireccion;
	private String idPersona;
	private String[] idTipoDireccion;
	
	
	
	


	
	/**
	 *
	 */
	public TarjetaDireccionesUpdateDTO idDireccion(String idDireccion){
		this.idDireccion = idDireccion;
		return this;
	}
	
	@JsonProperty("idDireccion")
	public String getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}

	
	/**
	 *
	 */
	public TarjetaDireccionesUpdateDTO idPersona(String idPersona){
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
	public TarjetaDireccionesUpdateDTO idTipoDireccion(String[] idTipoDireccion){
		this.idTipoDireccion = idTipoDireccion;
		return this;
	}
	
	@JsonProperty("idTipoDireccion")
	public String[] getIdTipoDireccion() {
		return idTipoDireccion;
	}

	public void setIdTipoDireccion(String[] idTipoDireccion) {
		this.idTipoDireccion = idTipoDireccion;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TarjetaDireccionesUpdateDTO tarjetaDireccionesUpdateDTO = (TarjetaDireccionesUpdateDTO) o;
		return Objects.equals(this.idDireccion, tarjetaDireccionesUpdateDTO.idDireccion)
				&& Objects.equals(this.idPersona, tarjetaDireccionesUpdateDTO.idPersona)
		&& Objects.equals(this.idTipoDireccion, tarjetaDireccionesUpdateDTO.idTipoDireccion);

	}

	@Override
	public int hashCode() {
		return Objects.hash(idDireccion, idPersona, idTipoDireccion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaDireccionesUpdateDTO {\n");

		sb.append("    idDireccion: ").append(toIndentedString(idDireccion)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idTipoDireccion: ").append(toIndentedString(idTipoDireccion)).append("\n");
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
