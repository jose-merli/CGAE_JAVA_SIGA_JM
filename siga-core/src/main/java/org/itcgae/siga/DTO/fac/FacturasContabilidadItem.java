package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class FacturasContabilidadItem {
	
	private String idfactura;
	private String numerofactura;
	private float impneto;
	private float impiva;
	private float iva;
	private int idpersona;
	private Date fechaemision;
	private String descripcion;
	private String ctaproductosservicio;
	private String ctaiva;
	private String confdeudor;
	private String confingresos;
	private String ctaingresos;
	private String ctaclientes;
	
	public String getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(String idfactura) {
		this.idfactura = idfactura;
	}
	public String getNumerofactura() {
		return numerofactura;
	}
	public void setNumerofactura(String numerofactura) {
		this.numerofactura = numerofactura;
	}
	public float getImpneto() {
		return impneto;
	}
	public void setImpneto(float impneto) {
		this.impneto = impneto;
	}
	public float getImpiva() {
		return impiva;
	}
	public void setImpiva(float impiva) {
		this.impiva = impiva;
	}
	public float getIva() {
		return iva;
	}
	public void setIva(float iva) {
		this.iva = iva;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public Date getFechaemision() {
		return fechaemision;
	}
	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCtaproductosservicio() {
		return ctaproductosservicio;
	}
	public void setCtaproductosservicio(String ctaproductosservicio) {
		this.ctaproductosservicio = ctaproductosservicio;
	}
	public String getCtaiva() {
		return ctaiva;
	}
	public void setCtaiva(String ctaiva) {
		this.ctaiva = ctaiva;
	}
	public String getConfdeudor() {
		return confdeudor;
	}
	public void setConfdeudor(String confdeudor) {
		this.confdeudor = confdeudor;
	}
	public String getConfingresos() {
		return confingresos;
	}
	public void setConfingresos(String confingresos) {
		this.confingresos = confingresos;
	}
	public String getCtaingresos() {
		return ctaingresos;
	}
	public void setCtaingresos(String ctaingresos) {
		this.ctaingresos = ctaingresos;
	}
	public String getCtaclientes() {
		return ctaclientes;
	}
	public void setCtaclientes(String ctaclientes) {
		this.ctaclientes = ctaclientes;
	}
	@Override
	public int hashCode() {
		return Objects.hash(confdeudor, confingresos, ctaclientes, ctaingresos, ctaiva, ctaproductosservicio,
				descripcion, fechaemision, idfactura, idpersona, impiva, impneto, iva, numerofactura);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturasContabilidadItem other = (FacturasContabilidadItem) obj;
		return Objects.equals(confdeudor, other.confdeudor) && Objects.equals(confingresos, other.confingresos)
				&& Objects.equals(ctaclientes, other.ctaclientes) && Objects.equals(ctaingresos, other.ctaingresos)
				&& Objects.equals(ctaiva, other.ctaiva)
				&& Objects.equals(ctaproductosservicio, other.ctaproductosservicio)
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(fechaemision, other.fechaemision)
				&& Objects.equals(idfactura, other.idfactura) && idpersona == other.idpersona
				&& Float.floatToIntBits(impiva) == Float.floatToIntBits(other.impiva)
				&& Float.floatToIntBits(impneto) == Float.floatToIntBits(other.impneto)
				&& Float.floatToIntBits(iva) == Float.floatToIntBits(other.iva)
				&& Objects.equals(numerofactura, other.numerofactura);
	}
	
	@Override
	public String toString() {
		return "FacturasContabilidadItem [idfactura=" + idfactura + ", numerofactura=" + numerofactura + ", impneto="
				+ impneto + ", impiva=" + impiva + ", iva=" + iva + ", idpersona=" + idpersona + ", fechaemision="
				+ fechaemision + ", descripcion=" + descripcion + ", ctaproductosservicio=" + ctaproductosservicio
				+ ", ctaiva=" + ctaiva + ", confdeudor=" + confdeudor + ", confingresos=" + confingresos
				+ ", ctaingresos=" + ctaingresos + ", ctaclientes=" + ctaclientes + "]";
	}

}
