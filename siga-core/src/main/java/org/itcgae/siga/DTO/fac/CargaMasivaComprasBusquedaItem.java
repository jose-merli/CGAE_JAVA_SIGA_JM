package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class CargaMasivaComprasBusquedaItem {
	
	private String nombreFichero;
	private String usuario;
	private Short registrosCorrectos;
	private Date fechaCarga;
	private String tipoCarga;
	private Long idFichero;
	private Long idFicheroLog;
	private Short registrosErroneos;
	
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Short getRegistrosCorrectos() {
		return registrosCorrectos;
	}
	
	public void setRegistrosCorrectos(Short registrosCorrectos) {
		this.registrosCorrectos = registrosCorrectos;
	}
	
	public Date getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	public String getTipoCarga() {
		return tipoCarga;
	}
	
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	
	public Long getIdFichero() {
		return idFichero;
	}
	
	public void setIdFichero(Long idFichero) {
		this.idFichero = idFichero;
	}
	
	public Long getIdFicheroLog() {
		return idFicheroLog;
	}
	
	public void setIdFicheroLog(Long idFicheroLog) {
		this.idFicheroLog = idFicheroLog;
	}
	
	public Short getRegistrosErroneos() {
		return registrosErroneos;
	}
	
	public void setRegistrosErroneos(Short registrosErroneos) {
		this.registrosErroneos = registrosErroneos;
	}

}
