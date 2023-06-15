package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpedienteEconomicoItem {
	private String idPeticion;
    private String justiciable;
    private String solicitadoPor;
    private Date f_solicitud;
    private Date f_recepcion;
	private String idEstado;
    private String estado;
    private String csv;

    
	/**
	 **/
	public ExpedienteEconomicoItem justiciable(String justiciable) {
		this.justiciable = justiciable;
		return this;
	}

	@JsonProperty("justiciable")
	public String getJusticiable() {
		return justiciable;
	}
	public void setJusticiable(String justiciable) {
		this.justiciable = justiciable;
	}
	/**
	 **/
	public ExpedienteEconomicoItem solicitadoPor(String solicitadoPor) {
		this.solicitadoPor = solicitadoPor;
		return this;
	}

	@JsonProperty("solicitadoPor")
	public String getSolicitadoPor() {
		return solicitadoPor;
	}
	public void setSolicitadoPor(String solicitadoPor) {
		this.solicitadoPor = solicitadoPor;
	}
	/**
	 **/
	public ExpedienteEconomicoItem f_solicitud(Date f_solicitud) {
		this.f_solicitud = f_solicitud;
		return this;
	}

	@JsonProperty("f_solicitud")
	public Date getF_solicitud() {
		return f_solicitud;
	}
	public void setF_solicitud(Date f_solicitud) {
		this.f_solicitud = f_solicitud;
	}
	/**
	 **/
	public ExpedienteEconomicoItem estado(String estado) {
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

	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 **/
	public ExpedienteEconomicoItem f_recepcion(Date f_recepcion) {
		this.f_recepcion = f_recepcion;
		return this;
	}

	@JsonProperty("f_recepcion")
	public Date getF_recepcion() {
		return f_recepcion;
	}
	public void setF_recepcion(Date f_recepcion) {
		this.f_recepcion = f_recepcion;
	}
	/**
	 **/
	public ExpedienteEconomicoItem csv(String csv) {
		this.csv = csv;
		return this;
	}

	@JsonProperty("csv")
	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}
}
