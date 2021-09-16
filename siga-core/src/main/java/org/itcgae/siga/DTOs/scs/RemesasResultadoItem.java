package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

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

	public int getIdRemesaResultado() {
		return idRemesaResultado;
	}

	public void setIdRemesaResultado(int idRemesaResultado) {
		this.idRemesaResultado = idRemesaResultado;
	}

	public String getNumRemesaPrefijo() {
		return numRemesaPrefijo;
	}

	public void setNumRemesaPrefijo(String numRemesaPrefijo) {
		this.numRemesaPrefijo = numRemesaPrefijo;
	}

	public String getNumRemesaNumero() {
		return numRemesaNumero;
	}

	public void setNumRemesaNumero(String numRemesaNumero) {
		this.numRemesaNumero = numRemesaNumero;
	}

	public String getNumRemesaSufijo() {
		return numRemesaSufijo;
	}

	public void setNumRemesaSufijo(String numRemesaSufijo) {
		this.numRemesaSufijo = numRemesaSufijo;
	}

	public String getNumRegistroPrefijo() {
		return numRegistroPrefijo;
	}

	public void setNumRegistroPrefijo(String numRegistroPrefijo) {
		this.numRegistroPrefijo = numRegistroPrefijo;
	}

	public String getNumRegistroNumero() {
		return numRegistroNumero;
	}

	public void setNumRegistroNumero(String numRegistroNumero) {
		this.numRegistroNumero = numRegistroNumero;
	}

	public String getNumRegistroSufijo() {
		return numRegistroSufijo;
	}

	public void setNumRegistroSufijo(String numRegistroSufijo) {
		this.numRegistroSufijo = numRegistroSufijo;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Date getFechaRemesaDesde() {
		return fechaRemesaDesde;
	}

	public void setFechaRemesaDesde(Date fechaRemesaDesde) {
		this.fechaRemesaDesde = fechaRemesaDesde;
	}

	public Date getFechaRemesaHasta() {
		return fechaRemesaHasta;
	}

	public void setFechaRemesaHasta(Date fechaRemesaHasta) {
		this.fechaRemesaHasta = fechaRemesaHasta;
	}

	public Date getFechaCargaDesde() {
		return fechaCargaDesde;
	}

	public void setFechaCargaDesde(Date fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}

	public Date getFechaCargaHasta() {
		return fechaCargaHasta;
	}

	public void setFechaCargaHasta(Date fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

	public String getObservacionesRemesaResultado() {
		return observacionesRemesaResultado;
	}

	public void setObservacionesRemesaResultado(String observacionesRemesaResultado) {
		this.observacionesRemesaResultado = observacionesRemesaResultado;
	}

	public Date getFechaCargaRemesaResultado() {
		return fechaCargaRemesaResultado;
	}

	public void setFechaCargaRemesaResultado(Date fechaCargaRemesaResultado) {
		this.fechaCargaRemesaResultado = fechaCargaRemesaResultado;
	}

	public Date getFechaResolucionRemesaResultado() {
		return fechaResolucionRemesaResultado;
	}

	public void setFechaResolucionRemesaResultado(Date fechaResolucionRemesaResultado) {
		this.fechaResolucionRemesaResultado = fechaResolucionRemesaResultado;
	}

	public int getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}

	public String getNumeroRemesa() {
		return numeroRemesa;
	}

	public void setNumeroRemesa(String numeroRemesa) {
		this.numeroRemesa = numeroRemesa;
	}

	public String getPrefijoRemesa() {
		return prefijoRemesa;
	}

	public void setPrefijoRemesa(String prefijoRemesa) {
		this.prefijoRemesa = prefijoRemesa;
	}

	public String getSufijoRemesa() {
		return sufijoRemesa;
	}

	public void setSufijoRemesa(String sufijoRemesa) {
		this.sufijoRemesa = sufijoRemesa;
	}

	public String getDescripcionRemesa() {
		return descripcionRemesa;
	}

	public void setDescripcionRemesa(String descripcionRemesa) {
		this.descripcionRemesa = descripcionRemesa;
	}

}