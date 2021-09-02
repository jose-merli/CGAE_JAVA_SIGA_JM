package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class ActasItem {

	private String anio;
	private String acta;
	private String idPresidente;
	private String idSecretario;
	private String idActa;
	private String miembros;
	private String pendientes;
	private String numeroActa;
	private String observaciones;
	private String sufijo;
	private String nombreSecretario;
	private String nombrePresidente;
	private Date fechaResolucion;
	private Date fechaReunion;
	private Date horaInicio;
	private Date horaFin;

	public String getNombreSecretario() {
		return nombreSecretario;
	}

	public void setNombreSecretario(String nombreSecretario) {
		this.nombreSecretario = nombreSecretario;
	}

	public String getNombrePresidente() {
		return nombrePresidente;
	}

	public void setNombrePresidente(String nombrePresidente) {
		this.nombrePresidente = nombrePresidente;
	}

	public String getIdPresidente() {
		return idPresidente;
	}

	public void setIdPresidente(String idPresidente) {
		this.idPresidente = idPresidente;
	}

	public String getIdSecretario() {
		return idSecretario;
	}

	public void setIdSecretario(String idSecretario) {
		this.idSecretario = idSecretario;
	}

	public String getIdActa() {
		return idActa;
	}

	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}

	public String getMiembros() {
		return miembros;
	}

	public void setMiembros(String miembros) {
		this.miembros = miembros;
	}

	public String getPendientes() {
		return pendientes;
	}

	public void setPendientes(String pendientes) {
		this.pendientes = pendientes;
	}

	public String getNumeroActa() {
		return numeroActa;
	}

	public void setNumeroActa(String numeroActa) {
		this.numeroActa = numeroActa;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getSufijo() {
		return sufijo;
	}

	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getActa() {
		return acta;
	}

	public void setActa(String acta) {
		this.acta = acta;
	}

	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public Date getFechaReunion() {
		return fechaReunion;
	}

	public void setFechaReunion(Date fechaReunion) {
		this.fechaReunion = fechaReunion;
	}

}
