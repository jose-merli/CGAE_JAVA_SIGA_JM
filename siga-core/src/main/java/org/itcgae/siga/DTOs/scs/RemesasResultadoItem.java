package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemesasResultadoItem {

	private int idRemesaResultado;
	private String numRemesaPrefijo;
	private String numRemesaNumero;
	private String numRemesaSufijo;
	private String numRegistroPrefijo;
	private String numRegistroNumero;
	private String numRegistroSufijo;
	private String nombreFichero;
	private Date fechaRemesaDesde;
	private Date fechaRemesaHasta;
	private Date fechaCargaDesde;
	private Date fechaCargaHasta;

	/* Atributos para la respuesta */
	private String observacionesRemesaResultado;
	private Date fechaCargaRemesaResultado;
	private Date fechaResolucionRemesaResultado;
	private int idRemesa;
	private String numeroRemesa;
	private String prefijoRemesa;
	private String sufijoRemesa;
	private String descripcionRemesa;
	
	private String numRegistroRemesaCompleto;
	private String numRemesaCompleto;

	@JsonProperty("idRemesaResultado")
	public int getIdRemesaResultado() {
		return idRemesaResultado;
	}

	public void setIdRemesaResultado(int idRemesaResultado) {
		this.idRemesaResultado = idRemesaResultado;
	}

	@JsonProperty("numRemesaPrefijo")
	public String getNumRemesaPrefijo() {
		return numRemesaPrefijo;
	}

	public void setNumRemesaPrefijo(String numRemesaPrefijo) {
		this.numRemesaPrefijo = numRemesaPrefijo;
	}

	@JsonProperty("numRemesaNumero")
	public String getNumRemesaNumero() {
		return numRemesaNumero;
	}

	public void setNumRemesaNumero(String numRemesaNumero) {
		this.numRemesaNumero = numRemesaNumero;
	}

	@JsonProperty("numRemesaSufijo")
	public String getNumRemesaSufijo() {
		return numRemesaSufijo;
	}

	public void setNumRemesaSufijo(String numRemesaSufijo) {
		this.numRemesaSufijo = numRemesaSufijo;
	}

	@JsonProperty("numRegistroPrefijo")
	public String getNumRegistroPrefijo() {
		return numRegistroPrefijo;
	}

	public void setNumRegistroPrefijo(String numRegistroPrefijo) {
		this.numRegistroPrefijo = numRegistroPrefijo;
	}

	@JsonProperty("numRegistroNumero")
	public String getNumRegistroNumero() {
		return numRegistroNumero;
	}

	public void setNumRegistroNumero(String numRegistroNumero) {
		this.numRegistroNumero = numRegistroNumero;
	}

	@JsonProperty("numRegistroSufijo")
	public String getNumRegistroSufijo() {
		return numRegistroSufijo;
	}

	public void setNumRegistroSufijo(String numRegistroSufijo) {
		this.numRegistroSufijo = numRegistroSufijo;
	}

	@JsonProperty("nombreFichero")
	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	@JsonProperty("fechaRemesaDesde")
	public Date getFechaRemesaDesde() {
		return fechaRemesaDesde;
	}

	public void setFechaRemesaDesde(Date fechaRemesaDesde) {
		this.fechaRemesaDesde = fechaRemesaDesde;
	}

	@JsonProperty("fechaRemesaHasta")
	public Date getFechaRemesaHasta() {
		return fechaRemesaHasta;
	}

	public void setFechaRemesaHasta(Date fechaRemesaHasta) {
		this.fechaRemesaHasta = fechaRemesaHasta;
	}

	@JsonProperty("fechaCargaDesde")
	public Date getFechaCargaDesde() {
		return fechaCargaDesde;
	}

	public void setFechaCargaDesde(Date fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}

	@JsonProperty("fechaCargaHasta")
	public Date getFechaCargaHasta() {
		return fechaCargaHasta;
	}

	public void setFechaCargaHasta(Date fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

	@JsonProperty("observacionesRemesaResultado")
	public String getObservacionesRemesaResultado() {
		return observacionesRemesaResultado;
	}

	public void setObservacionesRemesaResultado(String observacionesRemesaResultado) {
		this.observacionesRemesaResultado = observacionesRemesaResultado;
	}

	@JsonProperty("fechaCargaRemesaResultado")
	public Date getFechaCargaRemesaResultado() {
		return fechaCargaRemesaResultado;
	}

	public void setFechaCargaRemesaResultado(Date fechaCargaRemesaResultado) {
		this.fechaCargaRemesaResultado = fechaCargaRemesaResultado;
	}

	@JsonProperty("fechaResolucionRemesaResultado")
	public Date getFechaResolucionRemesaResultado() {
		return fechaResolucionRemesaResultado;
	}

	public void setFechaResolucionRemesaResultado(Date fechaResolucionRemesaResultado) {
		this.fechaResolucionRemesaResultado = fechaResolucionRemesaResultado;
	}

	@JsonProperty("idRemesa")
	public int getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}

	@JsonProperty("numeroRemesa")
	public String getNumeroRemesa() {
		return numeroRemesa;
	}

	public void setNumeroRemesa(String numeroRemesa) {
		this.numeroRemesa = numeroRemesa;
	}

	@JsonProperty("prefijoRemesa")
	public String getPrefijoRemesa() {
		return prefijoRemesa;
	}

	public void setPrefijoRemesa(String prefijoRemesa) {
		this.prefijoRemesa = prefijoRemesa;
	}

	@JsonProperty("sufijoRemesa")
	public String getSufijoRemesa() {
		return sufijoRemesa;
	}

	public void setSufijoRemesa(String sufijoRemesa) {
		this.sufijoRemesa = sufijoRemesa;
	}

	@JsonProperty("descripcionRemesa")
	public String getDescripcionRemesa() {
		return descripcionRemesa;
	}

	public void setDescripcionRemesa(String descripcionRemesa) {
		this.descripcionRemesa = descripcionRemesa;
	}

	@JsonProperty("numRegistroRemesaCompleto")
	public String getNumRegistroRemesaCompleto() {
		return numRegistroRemesaCompleto;
	}

	public void setNumRegistroRemesaCompleto(String numRegistroRemesaCompleto) {
		this.numRegistroRemesaCompleto = numRegistroRemesaCompleto;
	}

	@JsonProperty("numRemesaCompleto")
	public String getNumRemesaCompleto() {
		return numRemesaCompleto;
	}

	public void setNumRemesaCompleto(String numRemesaCompleto) {
		this.numRemesaCompleto = numRemesaCompleto;
	}

}