package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BusquedaSancionesItem {

	private String[] idColegios;
	private String colegio;
	private String nombre;
	private String tipoSancion;
	private String estado;
	private String rehabilitado;
	private String refConsejo;
	private String firmeza;
	private String multa;
	private String refColegio;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaDesde;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaAcuerdo;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaFirmeza;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaRehabilitado;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaArchivada;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaHasta;
	private String texto;
	private String idPersona;
	private String idSancion;
	
	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	@JsonProperty("multa")
	public String getMulta() {
		return multa;
	}
	public void setMulta(String multa) {
		this.multa = multa;
	}
	
	@JsonProperty("idColegios")
	public String[] getidColegios() {
		return idColegios;
	}
	public void setidColegios(String[] idColegios) {
		this.idColegios = idColegios;
	}
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty("tipoSancion")
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@JsonProperty("refConsejo")
	public String getRefConsejo() {
		return refConsejo;
	}
	public void setRefConsejo(String refConsejo) {
		this.refConsejo = refConsejo;
	}
	@JsonProperty("refColegio")
	public String getRefColegio() {
		return refColegio;
	}
	public void setRefColegio(String refColegio) {
		this.refColegio = refColegio;
	}
	@JsonProperty("fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	@JsonProperty("fechaAcuerdo")
	public Date getFechaAcuerdo() {
		return fechaAcuerdo;
	}
	public void setFechaAcuerdo(Date fechaAcuerdo) {
		this.fechaAcuerdo = fechaAcuerdo;
	}
	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	@JsonProperty("fechaRehabilitado")
	public Date getFechaRehabilitado() {
		return fechaRehabilitado;
	}
	public void setFechaRehabilitado(Date fechaRehabilitado) {
		this.fechaRehabilitado = fechaRehabilitado;
	}
	@JsonProperty("fechaArchivada")
	public Date getFechaArchivada() {
		return fechaArchivada;
	}
	public void setFechaArchivada(Date fechaArchivada) {
		this.fechaArchivada = fechaArchivada;
	}
	@JsonProperty("rehabilitado")
	public String getRehabilitado() {
		return rehabilitado;
	}
	public void setRehabilitado(String rehabilitado) {
		this.rehabilitado = rehabilitado;
	}
	
	@JsonProperty("fechaFirmeza")
	public Date getFechaFirmeza() {
		return fechaFirmeza;
	}
	public void setFirmeza(Date fechaFirmeza) {
		this.fechaFirmeza = fechaFirmeza;
	}	
	
	@JsonProperty("firmeza")
	public String getFirmeza() {
		return firmeza;
	}
	public void setFirmeza(String firmeza) {
		this.firmeza = firmeza;
	}	
	
	@JsonProperty("texto")
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	@JsonProperty("idSancion")
	public String getIdSancion() {
		return idSancion;
	}
	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
	}
}
