package org.itcgae.siga.DTOs.form;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsistenciaCursoItem {
	
	private String idAsistencia;
    private String nombre;
	private String asistencia;

	/**
	 **/
	public AsistenciaCursoItem idAsistencia(String idAsistencia) {
		this.idAsistencia = idAsistencia;
		return this;
	}

	@JsonProperty("idAsistencia")
	public String getIdAsistencia() {
		return idAsistencia;
	}

	public void setIdAsistencia(String idAsistencia) {
		this.idAsistencia = idAsistencia;
	}

	/**
	 * 
	 **/
	public AsistenciaCursoItem nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 **/
	public AsistenciaCursoItem asistencia(String asistencia) {
		this.asistencia = asistencia;
		return this;
	}

	@JsonProperty("asistencia")
	public String getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AsistenciaCursoItem asistenciaCursoItem = (AsistenciaCursoItem) o;
		return Objects.equals(this.idAsistencia, asistenciaCursoItem.idAsistencia)
				&& Objects.equals(this.nombre, asistenciaCursoItem.nombre)
				&& Objects.equals(this.asistencia, asistenciaCursoItem.asistencia);
	}


	@Override
	public int hashCode() {
		return Objects.hash(idAsistencia, nombre, asistencia);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AsistenciaCursoItem {\n");

		sb.append("    idAsistencia: ").append(toIndentedString(idAsistencia)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    asistencia: ").append(toIndentedString(asistencia)).append("\n");
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
