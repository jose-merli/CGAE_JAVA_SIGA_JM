package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesasResolucionItem {
	
	private int idRemesaResolucion;
	private String numRemesaPrefijo;
	private String numRemesaSufijo;
	private String nombreFichero;
	private String observaciones;
	private Date fechaCargaDesde;
	private Date fechaCargaHasta;
	private Date fechaResolucionDesde;
	private Date fechaResolucionHasta;
	private int idRemesa;
	
	//GETTERS
	@JsonProperty("idRemesaResolucion")
	public int getIdRemesaResolucion() {
		return idRemesaResolucion;
	}
	
	@JsonProperty("numRemesaPrefijo")
	public String getNumRemesaPrefijo() {
		return numRemesaPrefijo;
	}
	
	@JsonProperty("numRemesaSufijo")
	public String getNumRemesaSufijo() {
		return numRemesaSufijo;
	}
	
	@JsonProperty("nombreFichero")
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}
	
	@JsonProperty("fechaCargaDesde")
	public Date getFechaCargaDesde() {
		return fechaCargaDesde;
	}
	
	@JsonProperty("fechaCargaHasta")
	public Date getFechaCargaHasta() {
		return fechaCargaHasta;
	}
	
	@JsonProperty("fechaResolucionDesde")
	public Date getFechaResolucionDesde() {
		return fechaResolucionDesde;
	}

	@JsonProperty("fechaResolucionHasta")
	public Date getFechaResolucionHasta() {
		return fechaResolucionHasta;
	}

	@JsonProperty("idRemesa")
	public int getIdRemesa() {
		return idRemesa;
	}
	
	//SETTERS
	public void setIdRemesaResolucion(int idRemesaResolucion) {
		this.idRemesaResolucion = idRemesaResolucion;
	}
	
	public void setNumRemesaPrefijo(String numRemesaPrefijo) {
		this.numRemesaPrefijo = numRemesaPrefijo;
	}
	
	public void setNumRemesaSufijo(String numRemesaSufijo) {
		this.numRemesaSufijo = numRemesaSufijo;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public void setFechaCargaDesde(Date fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}

	public void setFechaCargaHasta(Date fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

	public void setFechaResolucionDesde(Date fechaResolucionDesde) {
		this.fechaResolucionDesde = fechaResolucionDesde;
	}

	public void setFechaResolucionHasta(Date fechaResolucionHasta) {
		this.fechaResolucionHasta = fechaResolucionHasta;
	}

	public void setIdRemesa(int idRmesa) {
		this.idRemesa = idRmesa;
	}


	
	
	
	
	

}
