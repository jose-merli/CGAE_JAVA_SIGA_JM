package org.itcgae.siga.DTOs.com;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnviosMasivosSearch {
	
	private String asunto;
	private Date fechaCreacion;
	private Date fechaProgramacion;
	private String tipoEnvio;
	private String estado;
	
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
	@JsonProperty("tipoEnvio")
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
