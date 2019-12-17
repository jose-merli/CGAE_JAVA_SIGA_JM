package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosJusticiableItem {

	private String idInstitucion;
	private String asunto;
	private String turnoGuardia;
	private String letrado;
	private Date fechaModificacion;
	private Date fecha;
	private String interesado;
	private String datosInteres;
	private String anio;
	private String numero;
	private String clave;
	private String rol;
	private String nombre;
	private String apellidos;
	private String tipo;
	private String idTurno;
	private String idGuardia;
	private String idTipoSoj;
	private String idTipoDesigna;
	private String idEstadoDesigna;
	private String anioProcedimiento; 
	private String numProcedimiento;
	private String idJuzgado;
	private String nig;
	private String nif;
	private String idPersonaColegiado; 
	private String idTipoEjg;
	private String idTipoEjColegio;
	private String idEstadoPorEjg;
	private String idTipoAsistencia;
	private String numeroProcedimiento;
	private String numeroDiligencia;
	private String comisaria;
	private String juzgado;
	private Date fechaAperturaDesde;
	private Date fechaAperturaHasta;

	/**
	 **/
	public AsuntosJusticiableItem idInstitucion(String idInstitucion) {
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
	
	@JsonProperty("anioProcedimiento")
	public String getAnioProcedimiento() {
		return anioProcedimiento;
	}

	public void setAnioProcedimiento(String anioProcedimiento) {
		this.anioProcedimiento = anioProcedimiento;
	}
	@JsonProperty("numProcedimiento")
	public String getNumProcedimiento() {
		return numProcedimiento;
	}

	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}
	
	@JsonProperty("idJuzgado")
	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	@JsonProperty("anio")
	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	@JsonProperty("clave")
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@JsonProperty("idTurno")
	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	@JsonProperty("idGuardia")
	public String getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	@JsonProperty("idTipoSoj")
	public String getIdTipoSoj() {
		return idTipoSoj;
	}

	public void setIdTipoSoj(String idTipoSoj) {
		this.idTipoSoj = idTipoSoj;
	}
	@JsonProperty("idTipoDesigna")
	public String getIdTipoDesigna() {
		return idTipoDesigna;
	}

	public void setIdTipoDesigna(String idTipoDesigna) {
		this.idTipoDesigna = idTipoDesigna;
	}
	@JsonProperty("idEstadoDesigna")
	public String getIdEstadoDesigna() {
		return idEstadoDesigna;
	}

	public void setIdEstadoDesigna(String idEstadoDesigna) {
		this.idEstadoDesigna = idEstadoDesigna;
	}
	@JsonProperty("nig")
	public String getNig() {
		return nig;
	}

	public void setNig(String nig) {
		this.nig = nig;
	}
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
	@JsonProperty("idPersonaColegiado")
	public String getIdPersonaColegiado() {
		return idPersonaColegiado;
	}

	public void setIdPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
	}
	@JsonProperty("idTipoEjg")
	public String getIdTipoEjg() {
		return idTipoEjg;
	}

	public void setIdTipoEjg(String idTipoEjg) {
		this.idTipoEjg = idTipoEjg;
	}
	@JsonProperty("idTipoEjColegio")
	public String getIdTipoEjColegio() {
		return idTipoEjColegio;
	}

	public void setIdTipoEjColegio(String idTipoEjColegio) {
		this.idTipoEjColegio = idTipoEjColegio;
	}
	@JsonProperty("idEstadoPorEjg")
	public String getIdEstadoPorEjg() {
		return idEstadoPorEjg;
	}

	public void setIdEstadoPorEjg(String idEstadoPorEjg) {
		this.idEstadoPorEjg = idEstadoPorEjg;
	}
	@JsonProperty("idTipoAsistencia")
	public String getIdTipoAsistencia() {
		return idTipoAsistencia;
	}

	public void setIdTipoAsistencia(String idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
	}
	@JsonProperty("numeroProcedimiento")
	public String getNumeroProcedimiento() {
		return numeroProcedimiento;
	}

	public void setNumeroProcedimiento(String numeroProcedimiento) {
		this.numeroProcedimiento = numeroProcedimiento;
	}
	@JsonProperty("numeroDiligencia")
	public String getNumeroDiligencia() {
		return numeroDiligencia;
	}

	public void setNumeroDiligencia(String numeroDiligencia) {
		this.numeroDiligencia = numeroDiligencia;
	}
	@JsonProperty("comisaria")
	public String getComisaria() {
		return comisaria;
	}

	public void setComisaria(String comisaria) {
		this.comisaria = comisaria;
	}
	@JsonProperty("juzgado")
	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}
	@JsonProperty("fechaAperturaDesde")
	public Date getFechaAperturaDesde() {
		return fechaAperturaDesde;
	}

	public void setFechaAperturaDesde(Date fechaAperturaDesde) {
		this.fechaAperturaDesde = fechaAperturaDesde;
	}
	@JsonProperty("fechaAperturaHasta")
	public Date getFechaAperturaHasta() {
		return fechaAperturaHasta;
	}

	public void setFechaAperturaHasta(Date fechaAperturaHasta) {
		this.fechaAperturaHasta = fechaAperturaHasta;
	}

	/**
	 **/
	
	public AsuntosJusticiableItem asunto(String asunto) {
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
	public AsuntosJusticiableItem turnoGuardia(String turnoGuardia) {
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
	public AsuntosJusticiableItem letrado(String letrado) {
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
	public AsuntosJusticiableItem fechaModificacion(Date fechaModificacion) {
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
	public AsuntosJusticiableItem fecha(Date fecha) {
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
	public AsuntosJusticiableItem interesado(String interesado) {
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
	public AsuntosJusticiableItem rol(String rol) {
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
	public AsuntosJusticiableItem datosInteres(String datosInteres) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
		result = prime * result + ((datosInteres == null) ? 0 : datosInteres.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((interesado == null) ? 0 : interesado.hashCode());
		result = prime * result + ((letrado == null) ? 0 : letrado.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
		AsuntosJusticiableItem other = (AsuntosJusticiableItem) obj;
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
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
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
		return "AsuntosJusticiableItem [idInstitucion=" + idInstitucion + ", asunto="
				+ asunto + ", turnoGuardia=" + turnoGuardia + ", letrado=" + letrado + ", fechaModificacion="
				+ fechaModificacion + ", fecha=" + fecha + ", interesado=" + interesado + ", rol=" + rol
				+ ", datosInteres=" + datosInteres + "]";
	}

	
	

}
