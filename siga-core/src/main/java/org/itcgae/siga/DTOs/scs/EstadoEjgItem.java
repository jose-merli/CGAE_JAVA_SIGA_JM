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
	
	private String idtipoejg;
	private String anio;
	private String numero;
	private String usumodificacion;
	private String idestadoporejg;
	private String propietariocomision;
	private Date fechabaja;
	private String idinstitucion;
	

	
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
	public String getIdtipoejg() {
		return idtipoejg;
	}

	public void setIdtipoejg(String idtipotejg) {
		this.idtipoejg = idtipotejg;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUsumodificacion() {
		return usumodificacion;
	}

	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	public String getIdestadoporejg() {
		return idestadoporejg;
	}

	public void setIdEstadoporEJG(String idestadoporejg) {
		this.idestadoporejg = idestadoporejg;
	}

	public String getPropietariocomision() {
		return propietariocomision;
	}

	public void setPropietariocomision(String propietariocomision) {
		this.propietariocomision = propietariocomision;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getIdinstitucion() {
		return idinstitucion;
	}

	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idEstadoejg == null) ? 0 : idEstadoejg.hashCode());
		result = prime * result + ((idtipoejg == null) ? 0 : idtipoejg.hashCode());
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((usumodificacion == null) ? 0 : usumodificacion.hashCode());
		result = prime * result + ((idestadoporejg == null) ? 0 : idestadoporejg.hashCode());
		result = prime * result + ((idtipoejg == null) ? 0 : idtipoejg.hashCode());
		result = prime * result + ((propietariocomision == null) ? 0 : propietariocomision.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((idinstitucion == null) ? 0 : idinstitucion.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
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
		if (idtipoejg == null) {
			if (other.idtipoejg != null)
				return false;
		} else if (!idtipoejg.equals(other.idtipoejg))
			return false;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equals(other.anio))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (usumodificacion == null) {
			if (other.usumodificacion != null)
				return false;
		} else if (!usumodificacion.equals(other.usumodificacion))
			return false;
		if (idestadoporejg == null) {
			if (other.idestadoporejg != null)
				return false;
		} else if (!idestadoporejg.equals(other.idestadoporejg))
			return false;
		if (propietariocomision == null) {
			if (other.propietariocomision != null)
				return false;
		} else if (!propietariocomision.equals(other.propietariocomision))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (idinstitucion == null) {
			if (other.idinstitucion != null)
				return false;
		} else if (!idinstitucion.equals(other.idinstitucion))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		return true;

	}

	@Override
	public String toString() {
		return "EstadoEjgItem [idEstadoejg=" + idEstadoejg + ", fechaModificacion=" + fechaModificacion
				+ ", fechaInicio=" + fechaInicio + ", descripcion=" + descripcion + ", idtipoejg=" + idtipoejg + 
				", anio=" + anio + ", numero=" + numero + ", usumodificacion=" + usumodificacion + 
				", idestadoporejg=" + idestadoporejg + " propietariocomision=" + propietariocomision + 
				", fechabaja=" + fechabaja +", idinstitucion=" + idinstitucion + ", observaciones=" + observaciones + "]";
	}


	
}
