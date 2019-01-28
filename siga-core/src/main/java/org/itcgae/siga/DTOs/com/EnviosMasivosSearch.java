package org.itcgae.siga.DTOs.com;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnviosMasivosSearch {
	
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaProgramacion;
	private String idTipoEnvios;
	private String idEstado;
	private String idClaseComunicacion;
	

	@JsonProperty("fechaCreacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}
	@JsonProperty("fechaProgramacion")
	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	@JsonProperty("idTipoEnvios")
	public String getidTipoEnvios() {
		return idTipoEnvios;
	}
	public void setidTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	@JsonProperty("idEstado")
	public String getidEstado() {
		return idEstado;
	}
	public void setidEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
	@JsonProperty("idClaseComunicacion")
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	
	

}
