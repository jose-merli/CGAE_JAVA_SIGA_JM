package org.itcgae.siga.DTO.fac;

import java.util.Date;

import lombok.Data;

public class FacRegistroFichConta {
	
	private int idContabilidad;
	private int idContabilidadDesde;
	private int idContabilidadHasta;
	private Date fechaCreacion;
	private Date fechaCreacionDesde;
	private Date fechaCreacionHasta;
	private Date fechaExportacionDesde;
	private Date fechaExportacionHasta;
	private int numAsientos;
	private int numAsientosDesde;
	private int numAsientosHasta;
	private Date fechaModificacion;
	private String nombreFichero;
	private int estado;
	private String nombreEstado;
	private boolean nuevo;
	
	
	
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	public int getIdContabilidad() {
		return idContabilidad;
	}
	public void setIdContabilidad(int idContabilidad) {
		this.idContabilidad = idContabilidad;
	}
	public int getIdContabilidadDesde() {
		return idContabilidadDesde;
	}
	public void setIdContabilidadDesde(int idContabilidadDesde) {
		this.idContabilidadDesde = idContabilidadDesde;
	}
	public int getIdContabilidadHasta() {
		return idContabilidadHasta;
	}
	public void setIdContabilidadHasta(int idContabilidadHasta) {
		this.idContabilidadHasta = idContabilidadHasta;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaCreacionDesde() {
		return fechaCreacionDesde;
	}
	public void setFechaCreacionDesde(Date fechaCreacionDesde) {
		this.fechaCreacionDesde = fechaCreacionDesde;
	}
	public Date getFechaCreacionHasta() {
		return fechaCreacionHasta;
	}
	public void setFechaCreacionHasta(Date fechaCreacionHasta) {
		this.fechaCreacionHasta = fechaCreacionHasta;
	}
	public Date getFechaExportacionDesde() {
		return fechaExportacionDesde;
	}
	public void setFechaExportacionDesde(Date fechaExportacionDesde) {
		this.fechaExportacionDesde = fechaExportacionDesde;
	}
	public Date getFechaExportacionHasta() {
		return fechaExportacionHasta;
	}
	public void setFechaExportacionHasta(Date fechaExportacionHasta) {
		this.fechaExportacionHasta = fechaExportacionHasta;
	}
	public int getNumAsientos() {
		return numAsientos;
	}
	public void setNumAsientos(int numAsientos) {
		this.numAsientos = numAsientos;
	}
	public int getNumAsientosDesde() {
		return numAsientosDesde;
	}
	public void setNumAsientosDesde(int numAsientosDesde) {
		this.numAsientosDesde = numAsientosDesde;
	}
	public int getNumAsientosHasta() {
		return numAsientosHasta;
	}
	public void setNumAsientosHasta(int numAsientosHasta) {
		this.numAsientosHasta = numAsientosHasta;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public boolean isNuevo() {
		return nuevo;
	}
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	
	
}
