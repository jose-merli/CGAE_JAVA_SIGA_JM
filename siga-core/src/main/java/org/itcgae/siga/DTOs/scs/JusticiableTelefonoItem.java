package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableTelefonoItem {

	private String idPersona;
	private String idInstitucion;
	private String idTelefono;
	private String nombreTelefono;
	private String numeroTelefono;
	private Date fechaModificacion;
	private String preferenteSms;
	

	/**
	 **/
	public JusticiableTelefonoItem idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}

	@JsonProperty("idpersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 **/
	public JusticiableTelefonoItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idinstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 **/
	public JusticiableTelefonoItem idTelefonok(String idTelefono) {
		this.idTelefono = idTelefono;
		return this;
	}

	@JsonProperty("idTelefono")
	public String getIdTelefono() {
		return idTelefono;
	}

	public void setIdTelefono(String idTelefono) {
		this.idTelefono = idTelefono;
	}

	/**
	 **/
	public JusticiableTelefonoItem nombreTelefono(String nombreTelefono) {
		this.nombreTelefono = nombreTelefono;
		return this;
	}

	@JsonProperty("nombreTelefono")
	public String getNombreTelefono() {
		return nombreTelefono;
	}

	public void setNombreTelefono(String nombreTelefono) {
		this.nombreTelefono = nombreTelefono;
	}

	/**
	 **/
	public JusticiableTelefonoItem numeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
		return this;
	}

	@JsonProperty("numeroTelefono")
	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	/**
	 **/
	public JusticiableTelefonoItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechamodificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 **/
	public JusticiableTelefonoItem preferenteSms(String preferenteSms) {
		this.preferenteSms = preferenteSms;
		return this;
	}

	@JsonProperty("preferenteSms")
	public String getPreferenteSms() {
		return preferenteSms;
	}

	public void setPreferenteSms(String preferenteSms) {
		this.preferenteSms = preferenteSms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idTelefono == null) ? 0 : idTelefono.hashCode());
		result = prime * result + ((nombreTelefono == null) ? 0 : nombreTelefono.hashCode());
		result = prime * result + ((numeroTelefono == null) ? 0 : numeroTelefono.hashCode());
		result = prime * result + ((preferenteSms == null) ? 0 : preferenteSms.hashCode());
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
		JusticiableTelefonoItem other = (JusticiableTelefonoItem) obj;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idTelefono == null) {
			if (other.idTelefono != null)
				return false;
		} else if (!idTelefono.equals(other.idTelefono))
			return false;
		if (nombreTelefono == null) {
			if (other.nombreTelefono != null)
				return false;
		} else if (!nombreTelefono.equals(other.nombreTelefono))
			return false;
		if (numeroTelefono == null) {
			if (other.numeroTelefono != null)
				return false;
		} else if (!numeroTelefono.equals(other.numeroTelefono))
			return false;
		if (preferenteSms != other.preferenteSms)
			return false;
		return true;
	}

}
