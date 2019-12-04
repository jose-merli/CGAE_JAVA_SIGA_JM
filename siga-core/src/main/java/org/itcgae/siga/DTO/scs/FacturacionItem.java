package org.itcgae.siga.DTO.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacturacionItem {

	private String idInstitucion;
	private String abreviatura;
	private String idFacturacion;
	private String idConcepto;
	private Date fechaDesde;
	private Date fechaHasta;
	private String nombre;
	private String regularizacion;
	private String desEstado;
	private String idEstado;
	private Date fechaEstado;
	private String importeTotal;
	private String importePagado;
	private String idPartidaPresupuestaria;
	private String importePendiente;
	
	@JsonProperty("importePendiente")
	public String getImportePendiente() {
		return importePendiente;
	}

	public void setImportePendiente(String importePendiente) {
		this.importePendiente = importePendiente;
	}

	@JsonProperty("idPartidaPresupuestaria")
	public String getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}

	public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	@JsonProperty("abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}
	
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	@JsonProperty("idFacturacion")
	public String getIdFacturacion() {
		return idFacturacion;
	}
	
	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}
	
	@JsonProperty("idConcepto")
	public String getIdConcepto() {
		return idConcepto;
	}
	
	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
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
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("regularizacion")
	public String getRegularizacion() {
		return regularizacion;
	}
	
	public void setRegularizacion(String regularizacion) {
		this.regularizacion = regularizacion;
	}
	
	@JsonProperty("desEstado")
	public String getDesEstado() {
		return desEstado;
	}
	
	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}
	
	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}
	
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
	@JsonProperty("fechaEstado")
	public Date getFechaEstado() {
		return fechaEstado;
	}
	
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	@JsonProperty("importeTotal")
	public String getImporteTotal() {
		return importeTotal;
	}
	
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	@JsonProperty("importePagado")
	public String getImportePagado() {
		return importePagado;
	}
	
	public void setImportePagado(String importePagado) {
		this.importePagado = importePagado;
	}
	
	@Override
	public String toString() {
		return "FacturacionItem [idInstitucion=" + idInstitucion + ", abreviatura=" + abreviatura + ", idFacturacion="
				+ idFacturacion + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", nombre=" + nombre
				+ ", regularizacion=" + regularizacion + ", desEstado=" + desEstado + ", idEstado=" + idEstado
				+ ", fechaEstado=" + fechaEstado + ", importeTotal=" + importeTotal + ", importePagado=" + importePagado
				+ "]";
	}
}
