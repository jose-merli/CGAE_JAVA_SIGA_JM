package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsistenciaEventoItem {

	private String idAsistenciaEvento;
	private Short idInstitucion;
	private String idEvento;
	private String idCurso;
	private String idInscripcion;
	private String asistencia;
	private String fechaBaja;
	private String usuModificacion;
	private Date fechaModificacion;
	private String idPersona; 
	private String nombrePersona;
	private String nif;
	private String errores;

	/**
	 **/
	public AsistenciaEventoItem idAsistenciaEvento(String idAsistenciaEvento) {
		this.idAsistenciaEvento = idAsistenciaEvento;
		return this;
	}

	@JsonProperty("idAsistenciaEvento")
	public String getIdAsistenciaEvento() {
		return idAsistenciaEvento;
	}

	public void setIdAsistenciaEvento(String idAsistenciaEvento) {
		this.idAsistenciaEvento = idAsistenciaEvento;
	}

	/**
	 * 
	 **/
	public AsistenciaEventoItem idInstitucion(Short idInstitucion) {
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
	public AsistenciaEventoItem idEvento(String idEvento) {
		this.idEvento = idEvento;
		return this;
	}

	@JsonProperty("idEvento")
	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	
	/**
	 * 
	 **/
	public AsistenciaEventoItem idCurso(String idCurso) {
		this.idCurso = idCurso;
		return this;
	}

	@JsonProperty("idCurso")
	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	/**
	 * 
	 **/
	public AsistenciaEventoItem idInscripcion(String idInscripcion) {
		this.idInscripcion = idInscripcion;
		return this;
	}

	@JsonProperty("idInscripcion")
	public String getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(String idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	/**
	 * 
	 **/
	public AsistenciaEventoItem asistencia(String asistencia) {
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

	/**
	 * 
	**/
	public AsistenciaEventoItem fechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	/**
	 * 
	**/
	public AsistenciaEventoItem usuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
		return this;
	}

	@JsonProperty("usuModificacion")
	public String getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	
	/**
	 * 
	**/
	public AsistenciaEventoItem fechaModificacion(Date fechaModificacion) {
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
	public AsistenciaEventoItem idPersona(String idPersona) {
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
	**/
	public AsistenciaEventoItem nombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
		return this;
	}

	@JsonProperty("nombrePersona")
	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	
	/**
	 **/
	public AsistenciaEventoItem nif(String nif) {
		this.nif = nif;
		return this;
	}

	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * 
	**/
	public AsistenciaEventoItem errores(String errores) {
		this.errores = errores;
		return this;
	}

	@JsonProperty("errores")
	public String getErrores() {
		return errores;
	}

	public void setErrores(String errores) {
		this.errores = errores;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AsistenciaEventoItem asistenciaEventoItem = (AsistenciaEventoItem) o;
		return Objects.equals(this.idAsistenciaEvento, asistenciaEventoItem.idAsistenciaEvento)
				&& Objects.equals(this.idInstitucion, asistenciaEventoItem.idInstitucion)
				&& Objects.equals(this.idInscripcion, asistenciaEventoItem.idInscripcion)
				&& Objects.equals(this.usuModificacion, asistenciaEventoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, asistenciaEventoItem.fechaModificacion)
				&& Objects.equals(this.fechaBaja, asistenciaEventoItem.fechaBaja)
				&& Objects.equals(this.asistencia, asistenciaEventoItem.asistencia)
				&& Objects.equals(this.idCurso, asistenciaEventoItem.idCurso)
				&& Objects.equals(this.idEvento, asistenciaEventoItem.idEvento)
				&& Objects.equals(this.idPersona, asistenciaEventoItem.idPersona)
				&& Objects.equals(this.nombrePersona, asistenciaEventoItem.nombrePersona)
				&& Objects.equals(this.nif, asistenciaEventoItem.nif)
				&& Objects.equals(this.errores, asistenciaEventoItem.errores);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAsistenciaEvento, idInstitucion, idInscripcion, usuModificacion, fechaModificacion, fechaBaja, asistencia, idEvento,
				idCurso, idPersona, nombrePersona, nif, errores);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AsistenciaEventoItem {\n");

		sb.append("    idAsistenciaEvento: ").append(toIndentedString(idAsistenciaEvento)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    idInscripcion: ").append(toIndentedString(idInscripcion)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    asistencia: ").append(toIndentedString(asistencia)).append("\n");
		sb.append("    idEvento: ").append(toIndentedString(idEvento)).append("\n");
		sb.append("    idCurso: ").append(toIndentedString(idCurso)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    nombrePersona: ").append(toIndentedString(nombrePersona)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    errores: ").append(toIndentedString(errores)).append("\n");
		
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
