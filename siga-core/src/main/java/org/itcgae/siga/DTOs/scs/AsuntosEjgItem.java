package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosEjgItem {

	private String idPersonajg;
	private String idInstitucion;
	private String asunto;
	private String estado;
	private String turnoGuardia;
	private String letrado;
	private Date fechaModificacion;
	private Date fecha;
	private String interesado;
	private String rol;
	private String datosInteres;
	private String anio;
	private String numero;
	private String idPersonaLetrado;
	private String expedienteInsostenibilidad;
	private String dictamen;
	private String fundamentoCalificacion;
	private String tipoResolucion;
	private String tipoFundamento;
	private String tipoResolucionAuto;
	private String tipoSentidoAuto;

	
	/**
	 **/
	public AsuntosEjgItem idPersonajg(String idPersonajg) {
		this.idPersonajg = idPersonajg;
		return this;
	}

	@JsonProperty("idpersonajg")
	public String getIdPersonajg() {
		return idPersonajg;
	}

	public void setIdPersonajg(String idPersonajg) {
		this.idPersonajg = idPersonajg;
	}
	
	/**
	 **/
	public AsuntosEjgItem idInstitucion(String idInstitucion) {
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
	public AsuntosEjgItem asunto(String asunto) {
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
	public AsuntosEjgItem estado(String estado) {
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
	public AsuntosEjgItem turnoGuardia(String turnoGuardia) {
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
	public AsuntosEjgItem letrado(String letrado) {
		this.letrado = letrado;
		return this;
	}

	@JsonProperty("letrado")
	public String getLetrado() {
		return letrado;
	}

	public void setLetrado(String letrado) {
		this.letrado = letrado;
	}

	/**
	 **/
	public AsuntosEjgItem fechaModificacion(Date fechaModificacion) {
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
	public AsuntosEjgItem fecha(Date fecha) {
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
	public AsuntosEjgItem interesado(String interesado) {
		this.interesado = interesado;
		return this;
	}

	@JsonProperty("interesado")
	public String getInteresado() {
		return interesado;
	}

	public void setInteresado(String interesado) {
		this.interesado = interesado;
	}

	/**
	 **/
	public AsuntosEjgItem rol(String rol) {
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
	 **/
	public AsuntosEjgItem datosInteres(String datosInteres) {
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
	public AsuntosEjgItem anio(String anio) {
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
	public AsuntosEjgItem numero(String numero) {
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
	public AsuntosEjgItem idPersonaLetrado(String idPersonaLetrado) {
		this.idPersonaLetrado = idPersonaLetrado;
		return this;
	}

	@JsonProperty("idPersonaLetrado")
	public String getIdPersonaLetrado() {
		return idPersonaLetrado;
	}

	public void setIdPersonaLetrado(String idPersonaLetrado) {
		this.idPersonaLetrado = idPersonaLetrado;
	}

	/**
	 **/
	public AsuntosEjgItem expedienteInsostenibilidad(String expedienteInsostenibilidad) {
		this.expedienteInsostenibilidad = expedienteInsostenibilidad;
		return this;
	}

	@JsonProperty("expedienteInsostenibilidad")
	public String getExpedienteInsostenibilidad() {
		return expedienteInsostenibilidad;
	}

	public void setExpedienteInsostenibilidad(String expedienteInsostenibilidad) {
		this.expedienteInsostenibilidad = expedienteInsostenibilidad;
	}

	/**
	 **/
	public AsuntosEjgItem dictamen(String dictamen) {
		this.dictamen = dictamen;
		return this;
	}

	@JsonProperty("dictamen")
	public String getDictamen() {
		return dictamen;
	}

	public void setDictamen(String dictamen) {
		this.dictamen = dictamen;
	}

	/**
	 **/
	public AsuntosEjgItem fundamentoCalificacion(String fundamentoCalificacion) {
		this.fundamentoCalificacion = fundamentoCalificacion;
		return this;
	}

	@JsonProperty("fundamentoCalificacion")
	public String getFundamentoCalificacion() {
		return fundamentoCalificacion;
	}

	public void setFundamentoCalificacion(String fundamentoCalificacion) {
		this.fundamentoCalificacion = fundamentoCalificacion;
	}

	/**
	 **/
	public AsuntosEjgItem tipoResolucion(String tipoResolucion) {
		this.tipoResolucion = tipoResolucion;
		return this;
	}

	@JsonProperty("tipoResolucion")
	public String getTipoResolucion() {
		return tipoResolucion;
	}

	public void setTipoResolucion(String tipoResolucion) {
		this.tipoResolucion = tipoResolucion;
	}

	/**
	 **/
	public AsuntosEjgItem tipoFundamento(String tipoFundamento) {
		this.tipoFundamento = tipoFundamento;
		return this;
	}

	@JsonProperty("tipoFundamento")
	public String getTipoFundamento() {
		return tipoFundamento;
	}

	public void setTipoFundamento(String tipoFundamento) {
		this.tipoFundamento = tipoFundamento;
	}

	/**
	 **/
	public AsuntosEjgItem tipoResolucionAuto(String tipoResolucionAuto) {
		this.tipoResolucionAuto = tipoResolucionAuto;
		return this;
	}

	@JsonProperty("tipoResolucionAuto")
	public String getTipoResolucionAuto() {
		return tipoResolucionAuto;
	}

	public void setTipoResolucionAuto(String tipoResolucionAuto) {
		this.tipoResolucionAuto = tipoResolucionAuto;
	}

	/**
	 **/
	public AsuntosEjgItem tipoSentidoAuto(String tipoSentidoAuto) {
		this.tipoSentidoAuto = tipoSentidoAuto;
		return this;
	}

	@JsonProperty("tipoSentidoAuto")
	public String getTipoSentidoAuto() {
		return tipoSentidoAuto;
	}

	public void setTipoSentidoAuto(String tipoSentidoAuto) {
		this.tipoSentidoAuto = tipoSentidoAuto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
		result = prime * result + ((datosInteres == null) ? 0 : datosInteres.hashCode());
		result = prime * result + ((dictamen == null) ? 0 : dictamen.hashCode());
		result = prime * result + ((expedienteInsostenibilidad == null) ? 0 : expedienteInsostenibilidad.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fundamentoCalificacion == null) ? 0 : fundamentoCalificacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersonaLetrado == null) ? 0 : idPersonaLetrado.hashCode());
		result = prime * result + ((idPersonajg == null) ? 0 : idPersonajg.hashCode());
		result = prime * result + ((interesado == null) ? 0 : interesado.hashCode());
		result = prime * result + ((letrado == null) ? 0 : letrado.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		result = prime * result + ((tipoFundamento == null) ? 0 : tipoFundamento.hashCode());
		result = prime * result + ((tipoResolucion == null) ? 0 : tipoResolucion.hashCode());
		result = prime * result + ((tipoResolucionAuto == null) ? 0 : tipoResolucionAuto.hashCode());
		result = prime * result + ((tipoSentidoAuto == null) ? 0 : tipoSentidoAuto.hashCode());
		result = prime * result + ((turnoGuardia == null) ? 0 : turnoGuardia.hashCode());
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
		AsuntosEjgItem other = (AsuntosEjgItem) obj;
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
		if (dictamen == null) {
			if (other.dictamen != null)
				return false;
		} else if (!dictamen.equals(other.dictamen))
			return false;
		if (expedienteInsostenibilidad == null) {
			if (other.expedienteInsostenibilidad != null)
				return false;
		} else if (!expedienteInsostenibilidad.equals(other.expedienteInsostenibilidad))
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
		if (fundamentoCalificacion == null) {
			if (other.fundamentoCalificacion != null)
				return false;
		} else if (!fundamentoCalificacion.equals(other.fundamentoCalificacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPersonaLetrado == null) {
			if (other.idPersonaLetrado != null)
				return false;
		} else if (!idPersonaLetrado.equals(other.idPersonaLetrado))
			return false;
		if (idPersonajg == null) {
			if (other.idPersonajg != null)
				return false;
		} else if (!idPersonajg.equals(other.idPersonajg))
			return false;
		if (interesado == null) {
			if (other.interesado != null)
				return false;
		} else if (!interesado.equals(other.interesado))
			return false;
		if (letrado == null) {
			if (other.letrado != null)
				return false;
		} else if (!letrado.equals(other.letrado))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (tipoFundamento == null) {
			if (other.tipoFundamento != null)
				return false;
		} else if (!tipoFundamento.equals(other.tipoFundamento))
			return false;
		if (tipoResolucion == null) {
			if (other.tipoResolucion != null)
				return false;
		} else if (!tipoResolucion.equals(other.tipoResolucion))
			return false;
		if (tipoResolucionAuto == null) {
			if (other.tipoResolucionAuto != null)
				return false;
		} else if (!tipoResolucionAuto.equals(other.tipoResolucionAuto))
			return false;
		if (tipoSentidoAuto == null) {
			if (other.tipoSentidoAuto != null)
				return false;
		} else if (!tipoSentidoAuto.equals(other.tipoSentidoAuto))
			return false;
		if (turnoGuardia == null) {
			if (other.turnoGuardia != null)
				return false;
		} else if (!turnoGuardia.equals(other.turnoGuardia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AsuntosEjgItem [idPersonajg=" + idPersonajg + ", idInstitucion=" + idInstitucion + ", asunto=" + asunto
				+ ", turnoGuardia=" + turnoGuardia + ", letrado=" + letrado + ", fechaModificacion=" + fechaModificacion
				+ ", fecha=" + fecha + ", interesado=" + interesado + ", rol=" + rol + ", datosInteres=" + datosInteres
				+ ", anio=" + anio + ", numero=" + numero + ", idPersonaLetrado=" + idPersonaLetrado
				+ ", expedienteInsostenibilidad=" + expedienteInsostenibilidad + ", dictamen=" + dictamen
				+ ", fundamentoCalificacion=" + fundamentoCalificacion + ", tipoResolucion=" + tipoResolucion
				+ ", tipoFundamento=" + tipoFundamento + ", tipoResolucionAuto=" + tipoResolucionAuto
				+ ", tipoSentidoAuto=" + tipoSentidoAuto + "]";
	}

	

	
	

}
