package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class GuardiaCalendarioItem {
	private String orden;
	private String turno;
	private String guardia;
	private String generado;
	private String idGuardia;
	private String idTurno;
	private String idCalendarioGuardia;
	private Boolean nuevo;
	private String estado;
	private String observaciones;
	private String fechaInicio;
	private String fechaFinal;
	private String idInstitucion;
	private String usuModificacion;
	private String fechaModificacion;
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getGuardia() {
		return guardia;
	}
	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	public String getGenerado() {
		return generado;
	}
	public void setGenerado(String generado) {
		this.generado = generado;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdCalendarioGuardia() {
		return idCalendarioGuardia;
	}
	public void setIdCalendarioGuardia(String idCalendarioGuardia) {
		this.idCalendarioGuardia = idCalendarioGuardia;
	}

	public Boolean getNuevo() {
		return nuevo;
	}

	public void setNuevo(Boolean nuevo) {
		this.nuevo = nuevo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
