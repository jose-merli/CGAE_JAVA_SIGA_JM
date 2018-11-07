package org.itcgae.siga.DTOs.form;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormadorCursoItem {
	
	private Long idCurso;
	private Long idPersona;
	private String idRol;
	private Short idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String idTipoCoste;
	private String nombreCompleto;
	private Double tarifa;
	private Double tutor;

	/**
	 **/
	public FormadorCursoItem idCurso(Long idCurso) {
		this.idCurso = idCurso;
		return this;
	}

	@JsonProperty("idCurso")
	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem idPersona(Long idPersona) {
		this.idPersona = idPersona;
		return this;
	}

	@JsonProperty("idPersona")
	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem idRol(String idRol) {
		this.idRol = idRol;
		return this;
	}

	@JsonProperty("idRol")
	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	
	/**
	 * 
	 **/
	public FormadorCursoItem idInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public Short getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}



	/**
	 * 
	 **/
	public FormadorCursoItem usuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
		return this;
	}

	@JsonProperty("usuModificacion")
	public Long getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem idTipoCoste(String idTipoCoste) {
		this.idTipoCoste = idTipoCoste;
		return this;
	}

	@JsonProperty("idTipoCoste")
	public String getIdTipoCoste() {
		return idTipoCoste;
	}

	public void setIdTipoCoste(String idTipoCoste) {
		this.idTipoCoste = idTipoCoste;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem nombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
		return this;
	}

	@JsonProperty("nombreCompleto")
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	/**
	 * 
	 **/
	public FormadorCursoItem tarifa(Double tarifa) {
		this.tarifa = tarifa;
		return this;
	}

	@JsonProperty("tarifa")
	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}
	
	/**
	 * 
	 **/
	public FormadorCursoItem tutor(Double tutor) {
		this.tutor = tutor;
		return this;
	}

	@JsonProperty("tutor")
	public Double getTutor() {
		return tutor;
	}

	public void setTutor(Double tutor) {
		this.tutor = tutor;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FormadorCursoItem formadorCursoItem = (FormadorCursoItem) o;
		return Objects.equals(this.idCurso, formadorCursoItem.idCurso)
				&& Objects.equals(this.idPersona, formadorCursoItem.idPersona)
				&& Objects.equals(this.idRol, formadorCursoItem.idRol)
				&& Objects.equals(this.idInstitucion, formadorCursoItem.idInstitucion)
				&& Objects.equals(this.usuModificacion, formadorCursoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, formadorCursoItem.fechaModificacion)
				&& Objects.equals(this.idTipoCoste, formadorCursoItem.idTipoCoste)
				&& Objects.equals(this.nombreCompleto, formadorCursoItem.nombreCompleto)
				&& Objects.equals(this.tarifa, formadorCursoItem.tarifa)
				&& Objects.equals(this.tutor, formadorCursoItem.tutor);
	}


	@Override
	public int hashCode() {
		return Objects.hash(idCurso, idPersona, idRol, idInstitucion, usuModificacion, fechaModificacion, idTipoCoste, nombreCompleto, tarifa, tutor);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class FormadorCursoItem {\n");

		sb.append("    idCurso: ").append(toIndentedString(idCurso)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idRol: ").append(toIndentedString(idRol)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    idTipoCoste: ").append(toIndentedString(idTipoCoste)).append("\n");
		sb.append("    nombreCompleto: ").append(toIndentedString(nombreCompleto)).append("\n");
		sb.append("    tarifa: ").append(toIndentedString(tarifa)).append("\n");
		sb.append("    tutor: ").append(toIndentedString(tutor)).append("\n");
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
