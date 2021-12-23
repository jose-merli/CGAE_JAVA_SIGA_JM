package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class FacAbonoItem {
	
	private Long idAdono;
	private String motivos;
	private Date fechaEmison;
	private Date fechaEmisionDesde;
	private Date fechaEmisionHasta;
	private char contabilizada;
	private Long idPersona;
	private int idCuenta;
	private String  idFactura;
	private Long idPagoJG;
	private String numeroAbono;
	private String observaciones;
	
	private int estado;
	private double importeTotalNeto;
	private double importeTotalIVA;
	private double importeTotal;
	private double importeTotalAbonadoEfectivo;
	private double importeTotalAbonadoBanco;
	private double importeTotalAbonado;
	private double importePendientePorAbonar;
	
	private int idPersonaDeudor;
	private int idCuentaDeudor;
	private int idPersonaOrigen;
	
	public Long getIdAdono() {
		return idAdono;
	}
	public void setIdAdono(Long idAdono) {
		this.idAdono = idAdono;
	}
	public String getMotivos() {
		return motivos;
	}
	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}
	public Date getFechaEmison() {
		return fechaEmison;
	}
	public void setFechaEmison(Date fechaEmison) {
		this.fechaEmison = fechaEmison;
	}
	public Date getFechaEmisionDesde() {
		return fechaEmisionDesde;
	}
	public void setFechaEmisionDesde(Date fechaEmisionDesde) {
		this.fechaEmisionDesde = fechaEmisionDesde;
	}
	public Date getFechaEmisionHasta() {
		return fechaEmisionHasta;
	}
	public void setFechaEmisionHasta(Date fechaEmisionHasta) {
		this.fechaEmisionHasta = fechaEmisionHasta;
	}
	public char getContabilizada() {
		return contabilizada;
	}
	public void setContabilizada(char contabilizada) {
		this.contabilizada = contabilizada;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
	public String getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}
	public Long getIdPagoJG() {
		return idPagoJG;
	}
	public void setIdPagoJG(Long idPagoJG) {
		this.idPagoJG = idPagoJG;
	}
	public String getNumeroAbono() {
		return numeroAbono;
	}
	public void setNumeroAbono(String numeroAbono) {
		this.numeroAbono = numeroAbono;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public double getImporteTotalNeto() {
		return importeTotalNeto;
	}
	public void setImporteTotalNeto(double importeTotalNeto) {
		this.importeTotalNeto = importeTotalNeto;
	}
	public double getImporteTotalIVA() {
		return importeTotalIVA;
	}
	public void setImporteTotalIVA(double importeTotalIVA) {
		this.importeTotalIVA = importeTotalIVA;
	}
	public double getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	public double getImporteTotalAbonadoEfectivo() {
		return importeTotalAbonadoEfectivo;
	}
	public void setImporteTotalAbonadoEfectivo(double importeTotalAbonadoEfectivo) {
		this.importeTotalAbonadoEfectivo = importeTotalAbonadoEfectivo;
	}
	public double getImporteTotalAbonadoBanco() {
		return importeTotalAbonadoBanco;
	}
	public void setImporteTotalAbonadoBanco(double importeTotalAbonadoBanco) {
		this.importeTotalAbonadoBanco = importeTotalAbonadoBanco;
	}
	public double getImporteTotalAbonado() {
		return importeTotalAbonado;
	}
	public void setImporteTotalAbonado(double importeTotalAbonado) {
		this.importeTotalAbonado = importeTotalAbonado;
	}
	public double getImportePendientePorAbonar() {
		return importePendientePorAbonar;
	}
	public void setImportePendientePorAbonar(double importePendientePorAbonar) {
		this.importePendientePorAbonar = importePendientePorAbonar;
	}
	public int getIdPersonaDeudor() {
		return idPersonaDeudor;
	}
	public void setIdPersonaDeudor(int idPersonaDeudor) {
		this.idPersonaDeudor = idPersonaDeudor;
	}
	public int getIdCuentaDeudor() {
		return idCuentaDeudor;
	}
	public void setIdCuentaDeudor(int idCuentaDeudor) {
		this.idCuentaDeudor = idCuentaDeudor;
	}
	public int getIdPersonaOrigen() {
		return idPersonaOrigen;
	}
	public void setIdPersonaOrigen(int idPersonaOrigen) {
		this.idPersonaOrigen = idPersonaOrigen;
	}
	
	
	
}
