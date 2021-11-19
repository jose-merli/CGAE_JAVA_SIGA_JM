package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class CargaMasivaComprasItem {
	
	private int idCargaMasiva;
	private int idInstitucion;
	private String nombreFichero;
	private String usuario;
	private int numRegistros;
	private Date fechaCarga;
	private Date fechaModificacion;
	private String tipoCarga;
	private Long idFichero;
	private Long idFicheroLog;
	private int numRegistrosErroneos;
	
	public int getIdCargaMasiva() {
		return idCargaMasiva;
	}

	public void setIdCargaMasiva(int idCargaMasiva) {
		this.idCargaMasiva = idCargaMasiva;
	}

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public int getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(int numRegistros) {
		this.numRegistros = numRegistros;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public int getNumRegistrosErroneos() {
		return numRegistrosErroneos;
	}

	public void setNumRegistrosErroneos(int numRegistrosErroneos) {
		this.numRegistrosErroneos = numRegistrosErroneos;
	}

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

}
