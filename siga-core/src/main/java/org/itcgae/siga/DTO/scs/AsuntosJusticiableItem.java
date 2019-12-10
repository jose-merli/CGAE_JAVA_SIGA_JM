package org.itcgae.siga.DTO.scs;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosJusticiableItem {

	private String idInstitucion;
	private Date anio;
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
	

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdEstadoDesigna() {
		return idEstadoDesigna;
	}

	public void setIdEstadoDesigna(String idEstadoDesigna) {
		this.idEstadoDesigna = idEstadoDesigna;
	}

	public String getNig() {
		return nig;
	}

	public void setNig(String nig) {
		this.nig = nig;
	}

	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	public String getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIdPersonaColegiado() {
		return idPersonaColegiado;
	}

	public void setIdPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
	}

	public String getIdTipoEjg() {
		return idTipoEjg;
	}

	public void setIdTipoEjg(String idTipoEjg) {
		this.idTipoEjg = idTipoEjg;
	}

	public String getIdTipoEjColegio() {
		return idTipoEjColegio;
	}

	public void setIdTipoEjColegio(String idTipoEjColegio) {
		this.idTipoEjColegio = idTipoEjColegio;
	}

	public String getIdEstadoPorEjg() {
		return idEstadoPorEjg;
	}

	public void setIdEstadoPorEjg(String idEstadoPorEjg) {
		this.idEstadoPorEjg = idEstadoPorEjg;
	}

	public String getIdTipoAsistencia() {
		return idTipoAsistencia;
	}

	public void setIdTipoAsistencia(String idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
	}

	public String getNumeroProcedimiento() {
		return numeroProcedimiento;
	}

	public void setNumeroProcedimiento(String numeroProcedimiento) {
		this.numeroProcedimiento = numeroProcedimiento;
	}

	public String getNumeroDiligencia() {
		return numeroDiligencia;
	}

	public void setNumeroDiligencia(String numeroDiligencia) {
		this.numeroDiligencia = numeroDiligencia;
	}

	public String getComisaria() {
		return comisaria;
	}

	public void setComisaria(String comisaria) {
		this.comisaria = comisaria;
	}

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getIdTipoSoj() {
		return idTipoSoj;
	}

	public void setIdTipoSoj(String idTipoSoj) {
		this.idTipoSoj = idTipoSoj;
	}

	public Date getFechaAperturaDesde() {
		return fechaAperturaDesde;
	}

	public void setFechaAperturaDesde(Date fechaAperturaDesde) {
		this.fechaAperturaDesde = fechaAperturaDesde;
	}

	public Date getFechaAperturaHasta() {
		return fechaAperturaHasta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setFechaAperturaHasta(Date fechaAperturaHasta) {
		this.fechaAperturaHasta = fechaAperturaHasta;
	}

	public AsuntosJusticiableItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String getidInstitucion() {
		return idInstitucion;
	}

	public void setidInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("anio")
	public Date getAnio() {
		return anio;
	}

	public void setAnio(Date anio) {
		this.anio = anio;
	}

	
	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getIdTipoDesigna() {
		return idTipoDesigna;
	}

	public void setIdTipoDesigna(String idTipoDesigna) {
		this.idTipoDesigna = idTipoDesigna;
	}

	@JsonProperty("clave")
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@JsonProperty("rol")
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
