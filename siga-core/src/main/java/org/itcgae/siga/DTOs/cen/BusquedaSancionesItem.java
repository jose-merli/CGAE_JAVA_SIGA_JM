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
	private String refConsejo;
	private String refColegio;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaDesde;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaHasta;
	private String rehabilitado;
	private String firmeza;
	
	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
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
	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	@JsonProperty("rehabilitado")
	public String getRehabilitado() {
		return rehabilitado;
	}
	public void setRehabilitado(String rehabilitado) {
		this.rehabilitado = rehabilitado;
	}
	@JsonProperty("firmeza")
	public String getFirmeza() {
		return firmeza;
	}
	public void setFirmeza(String firmeza) {
		this.firmeza = firmeza;
	}	
}
