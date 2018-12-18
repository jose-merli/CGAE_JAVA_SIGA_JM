package org.itcgae.siga.DTOs.com;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnviosMasivosSearch {
	
	private String asunto;
	private Date fechaCreacion;
	private Date fechaProgramacion;
	private String idTipoEnvio;
	private String idEstado;
	private String idClaseComunicacion;
	
	@JsonProperty("asunto")
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	@JsonProperty("fechaCreacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	@JsonProperty("fechaCreacion")
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}
	@JsonProperty("fechaProgramacion")
	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	@JsonProperty("idTipoEnvio")
	public String getidTipoEnvio() {
		return idTipoEnvio;
	}
	public void setidTipoEnvio(String idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
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
