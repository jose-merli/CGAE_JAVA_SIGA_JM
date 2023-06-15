package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosAsistenciaItem {

	private String idInstitucion;
	private String asunto;
	private String estado;
	private String codigo;
	private String turnoGuardia;
	private String idTurno;
	private Date fechaModificacion;
	private Date fecha;
	private String anio;
	private String numero;
	private String idPersonaAsistido;
	private String datosInteres;
	private String idPersonaColegiado;
	
	/**
	 **/
	public AsuntosAsistenciaItem idInstitucion(String idInstitucion) {
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
	public AsuntosAsistenciaItem asunto(String asunto) {
		this.asunto = asunto;
		return this;
	}

	@JsonProperty("asunto")
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	
	/**
	 **/
	public AsuntosAsistenciaItem estado(String estado) {
		this.estado = estado;
		return this;
	}

	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 **/
	public AsuntosAsistenciaItem turnoGuardia(String turnoGuardia) {
		this.turnoGuardia = turnoGuardia;
		return this;
	}

	@JsonProperty("turnoGuardia")
	public String getTurnoGuardia() {
		return turnoGuardia;
	}

	public void setTurnoGuardia(String turnoGuardia) {
		this.turnoGuardia = turnoGuardia;
	}

	/**
	 **/
	public AsuntosAsistenciaItem idTurno(String idTurno) {
		this.idTurno = idTurno;
		return this;
	}

	@JsonProperty("idTurno")
	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	/**
	 **/
	public AsuntosAsistenciaItem fechaModificacion(Date fechaModificacion) {
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
	public AsuntosAsistenciaItem fecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}

	@JsonProperty("fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 **/
	public AsuntosAsistenciaItem datosInteres(String datosInteres) {
		this.datosInteres = datosInteres;
		return this;
	}

	@JsonProperty("datosInteres")
	public String getDatosInteres() {
		return datosInteres;
	}

	public void setDatosInteres(String datosInteres) {
		this.datosInteres = datosInteres;
	}
	
	
	/**
	 **/
	public AsuntosAsistenciaItem anio(String anio) {
		this.anio = anio;
		return this;
	}

	@JsonProperty("anio")
	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 **/
	public AsuntosAsistenciaItem numero(String numero) {
		this.numero = numero;
		return this;
	}

	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 **/
	public AsuntosAsistenciaItem codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	@JsonProperty("codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	/**
	 **/
	public AsuntosAsistenciaItem idPersonaAsistido(String idPersonaAsistido) {
		this.idPersonaAsistido = idPersonaAsistido;
		return this;
	}

	@JsonProperty("idPersonaAsistido")
	public String getIdPersonaAsistido() {
		return idPersonaAsistido;
	}

	public void setIdPersonaAsistido(String idPersonaAsistido) {
		this.idPersonaAsistido = idPersonaAsistido;
	}

	/**
	 **/
	public AsuntosAsistenciaItem idPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
		return this;
	}

	@JsonProperty("idPersonaColegiado")
	public String getIdPersonaColegiado() {
		return idPersonaColegiado;
	}

	public void setIdPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
		result = prime * result + ((datosInteres == null) ? 0 : datosInteres.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((turnoGuardia == null) ? 0 : turnoGuardia.hashCode());
		result = prime * result + ((idPersonaAsistido == null) ? 0 : idPersonaAsistido.hashCode());
		result = prime * result + ((idPersonaColegiado == null) ? 0 : idPersonaColegiado.hashCode());
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
		AsuntosAsistenciaItem other = (AsuntosAsistenciaItem) obj;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equals(other.anio))
			return false;
		if (asunto == null) {
			if (other.asunto != null)
				return false;
		} else if (!asunto.equals(other.asunto))
			return false;
		if (datosInteres == null) {
			if (other.datosInteres != null)
				return false;
		} else if (!datosInteres.equals(other.datosInteres))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
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
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (turnoGuardia == null) {
			if (other.turnoGuardia != null)
				return false;
		} else if (!turnoGuardia.equals(other.turnoGuardia))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idPersonaAsistido == null) {
			if (other.idPersonaAsistido != null)
				return false;
		} else if (!idPersonaAsistido.equals(other.idPersonaAsistido))
			return false;
		if (idPersonaColegiado == null) {
			if (other.idPersonaColegiado != null)
				return false;
		} else if (!idPersonaColegiado.equals(other.idPersonaColegiado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AsuntosAsistenciaItem [idInstitucion=" + idInstitucion + ", asunto=" + asunto + ", estado=" + estado
				+ ", codigo=" + codigo + ", turnoGuardia=" + turnoGuardia + ", idTurno=" + idTurno
				+ ", fechaModificacion=" + fechaModificacion + ", fecha=" + fecha + ", anio=" + anio + ", numero="
				+ numero + ", idPersonaAsistido=" + idPersonaAsistido + ", datosInteres=" + datosInteres
				+ ", idPersonaColegiado=" + idPersonaColegiado + "]";
	}

	

	

}
