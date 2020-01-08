package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadoEjgItem {

	private String idEstadoejg;
	private Date fechaModificacion;
	private Date fechaInicio;
	private String descripcion;
	private String observaciones;
	private String automatico;
	private String propietario;
	private String user;

	
	/**
	 **/
	public EstadoEjgItem idEstadoejg(String idEstadoejg) {
		this.idEstadoejg = idEstadoejg;
		return this;
	}

	@JsonProperty("idEstadoejg")
	public String getIdEstadoejg() {
		return idEstadoejg;
	}


	public void setIdEstadoejg(String idEstadoejg) {
		this.idEstadoejg = idEstadoejg;
	}


	/**
	 **/
	public EstadoEjgItem fechaModificacion(Date fechaModificacion) {
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
	 **/
	public EstadoEjgItem fechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}

	@JsonProperty("fechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 **/
	public EstadoEjgItem descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 **/
	public EstadoEjgItem observaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}

	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 **/
	public EstadoEjgItem automatico(String automatico) {
		this.automatico = automatico;
		return this;
	}

	@JsonProperty("automatico")
	public String getAutomatico() {
		return automatico;
	}

	public void setAutomatico(String automatico) {
		this.automatico = automatico;
	}
	/**
	 **/
	public EstadoEjgItem propietario(String propietario) {
		this.propietario = propietario;
		return this;
	}

	@JsonProperty("propietario")
	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	/**
	 **/
	public EstadoEjgItem user(String user) {
		this.user = user;
		return this;
	}

	@JsonProperty("user")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idEstadoejg == null) ? 0 : idEstadoejg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoEjgItem other = (EstadoEjgItem) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (idEstadoejg == null) {
			if (other.idEstadoejg != null)
				return false;
		} else if (!idEstadoejg.equals(other.idEstadoejg))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstadoEjgItem [idEstadoejg=" + idEstadoejg + ", fechaModificacion=" + fechaModificacion
				+ ", fechaInicio=" + fechaInicio + ", descripcion=" + descripcion + "]";
	}
}
