package org.itcgae.siga.DTOs.form;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormadorCursoItem {
	
	private String idFormador;
	private Long idCurso;
	private Long idPersona;
	private String idRol;
	private String rol;
	private Short idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String idTipoCoste;
	private String tipoCoste;
	private String nombre;
	private String nombreCompleto;
	private String apellidos;
	private Double tarifa;
	private Short tutor;
	private String nif;
	private String tipoIdentificacion;
	private Date fechaBaja;

	/**
	 **/
	public FormadorCursoItem idFormador(String idFormador) {
		this.idFormador = idFormador;
		return this;
	}

	@JsonProperty("idFormador")
	public String getIdFormador() {
		return idFormador;
	}

	public void setIdFormador(String idFormador) {
		this.idFormador = idFormador;
	}
	
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
	public FormadorCursoItem rol(String rol) {
		this.rol = rol;
		return this;
	}

	@JsonProperty("rol")
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
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
	public FormadorCursoItem tipoCoste(String tipoCoste) {
		this.tipoCoste = tipoCoste;
		return this;
	}

	@JsonProperty("tipoCoste")
	public String getTipoCoste() {
		return tipoCoste;
	}

	public void setTipoCoste(String tipoCoste) {
		this.tipoCoste = tipoCoste;
	}

	/**
	 * 
	 **/
	public FormadorCursoItem nombre(String nombre) {
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
	public FormadorCursoItem apellidos(String apellidos) {
		this.apellidos = apellidos;
		return this;
	}

	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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
	public FormadorCursoItem tutor(Short tutor) {
		this.tutor = tutor;
		return this;
	}

	@JsonProperty("tutor")
	public Short getTutor() {
		return tutor;
	}

	public void setTutor(Short tutor) {
		this.tutor = tutor;
	}
	
	/**
	 * 
	 **/
	public FormadorCursoItem nif(String nif) {
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
	public FormadorCursoItem tipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
		return this;
	}

	@JsonProperty("tipoIdentificacion")
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	/**
	 * 
	 **/
	public FormadorCursoItem fechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
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
		return  Objects.equals(this.idFormador, formadorCursoItem.idFormador)
				&& Objects.equals(this.idCurso, formadorCursoItem.idCurso)
				&& Objects.equals(this.idPersona, formadorCursoItem.idPersona)
				&& Objects.equals(this.idRol, formadorCursoItem.idRol)
				&& Objects.equals(this.rol, formadorCursoItem.rol)
				&& Objects.equals(this.idInstitucion, formadorCursoItem.idInstitucion)
				&& Objects.equals(this.usuModificacion, formadorCursoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, formadorCursoItem.fechaModificacion)
				&& Objects.equals(this.idTipoCoste, formadorCursoItem.idTipoCoste)
				&& Objects.equals(this.tipoCoste, formadorCursoItem.tipoCoste)
				&& Objects.equals(this.nombre, formadorCursoItem.nombre)
				&& Objects.equals(this.apellidos, formadorCursoItem.apellidos)
				&& Objects.equals(this.tarifa, formadorCursoItem.tarifa)
				&& Objects.equals(this.tutor, formadorCursoItem.tutor)
				&& Objects.equals(this.nif, formadorCursoItem.nif)
				&& Objects.equals(this.tipoIdentificacion, formadorCursoItem.tipoIdentificacion)
				&& Objects.equals(this.fechaBaja, formadorCursoItem.fechaBaja);
	}


	@Override
	public int hashCode() {
		return Objects.hash(idFormador, idCurso, idPersona, idRol, rol, idInstitucion, usuModificacion, fechaModificacion, idTipoCoste, 
				tipoCoste, nombre, apellidos, tarifa, tutor, nif, tipoIdentificacion, fechaBaja);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class FormadorCursoItem {\n");

		sb.append("    idFormador: ").append(toIndentedString(idFormador)).append("\n");
		sb.append("    idCurso: ").append(toIndentedString(idCurso)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idRol: ").append(toIndentedString(idRol)).append("\n");
		sb.append("    rol: ").append(toIndentedString(rol)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    idTipoCoste: ").append(toIndentedString(idTipoCoste)).append("\n");
		sb.append("    tipoCoste: ").append(toIndentedString(tipoCoste)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    apellidos: ").append(toIndentedString(apellidos)).append("\n");
		sb.append("    tarifa: ").append(toIndentedString(tarifa)).append("\n");
		sb.append("    tutor: ").append(toIndentedString(tutor)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    tipoIdentificacion: ").append(toIndentedString(tipoIdentificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
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
