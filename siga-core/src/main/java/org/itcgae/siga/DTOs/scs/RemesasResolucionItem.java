package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesasResolucionItem {
	
	private int idRemesaResolucion;
	private int idTipoRemesa;
	private String numRemesaPrefijo;
	private String numRemesaSufijo;
	private String numRemesaNumero;
	private String nombreFichero;
	private String observaciones;
	private Date fechaCargaDesde;
	private Date fechaCargaHasta;
	private Date fechaResolucionDesde;
	private Date fechaResolucionHasta;
	private int idRemesa;
	private int log;
	
	
	//RESULTADO
	private Date fechaResolucion;
	private Date fechaCarga;
	
	//GETTERS
	
	
	@JsonProperty("idRemesaResolucion")
	public int getIdRemesaResolucion() {
		return idRemesaResolucion;
	}
	
	@JsonProperty("idTipoRemesa")
	public int getIdTipoRemesa() {
		return idTipoRemesa;
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
	
	@JsonProperty("fechaResolucion")
	public Date getFechaResolucion() {
		return fechaResolucion;
	}
	
	@JsonProperty("fechaCarga")
	public Date getFechaCarga() {
		return fechaCarga;
	}
	
	@JsonProperty("log")
	public int getLog() {
		return log;
	}
	
	@JsonProperty("numRemesaNumero")
	public String getNumRemesaNumero() {
		return numRemesaNumero;
	}

	//SETTERS
	public void setIdRemesaResolucion(int idRemesaResolucion) {
		this.idRemesaResolucion = idRemesaResolucion;
	}
	
	
	public void setIdTipoRemesa(int idTipoRemesa) {
		this.idTipoRemesa = idTipoRemesa;
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

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public void setLog(int log) {
		this.log = log;
	}

	public void setNumRemesaNumero(String numRemesaNumero) {
		this.numRemesaNumero = numRemesaNumero;
	}
	
	
	
	
	

}
