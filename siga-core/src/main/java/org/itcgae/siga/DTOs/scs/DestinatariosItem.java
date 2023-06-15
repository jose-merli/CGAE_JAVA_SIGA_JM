package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DestinatariosItem {

	private String idinstitucion;
	private String iddestinatario;
	private String nombre;
	private Date fechamodificacion;
	private String usumodificacion;
	private String cuentacontable;
	private String orden;
	private Date fechabaja; 
	private boolean historico;
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getIddestinatario() {
		return iddestinatario;
	}
	public void setIddestinatario(String iddestinatario) {
		this.iddestinatario = iddestinatario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public String getCuentacontable() {
		return cuentacontable;
	}
	public void setCuentacontable(String cuentacontable) {
		this.cuentacontable = cuentacontable;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	
}
