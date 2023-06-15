package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class InscripcionDatosEntradaDTO {

	private String idEstado;
	private String idturno;
	private String idGuardia;
	private Object aFechaDe;
	private Object fechaDesde;
	private Object fechaHasta;
	private String nColegiado;
	private String nombre;
	private String apellidos;
	private String apellidos2;
	private String idPersona;
	private Object fechaActual;
	private String observaciones;

	public Object getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Object fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	

	public String getIdturno() {
		return idturno;
	}

	public void setIdturno(String idturno) {
		this.idturno = idturno;
	}

	public String getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}

	
	public Object getaFechaDe() {
		return aFechaDe;
	}

	public void setaFechaDe(Object aFechaDe) {
		this.aFechaDe = aFechaDe;
	}

	public Object getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Object fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Object getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Object fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getnColegiado() {
		return nColegiado;
	}

	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
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

	public String getApellidos2() {
		return apellidos2;
	}

	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

}