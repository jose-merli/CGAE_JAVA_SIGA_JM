package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class PagoPorBancoItem {

	private int idfactura;
	private String numerofactura;
	private float importe;
	private String bancos_codigo;
	private int idpersona;
	private Date fechacreacion;
	private String confdeudor;
	private String ctaclientes;
	
	public int getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(int idfactura) {
		this.idfactura = idfactura;
	}
	public String getNumerofactura() {
		return numerofactura;
	}
	public void setNumerofactura(String numerofactura) {
		this.numerofactura = numerofactura;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public String getBancos_codigo() {
		return bancos_codigo;
	}
	public void setBancos_codigo(String bancos_codigo) {
		this.bancos_codigo = bancos_codigo;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public Date getFechacreacion() {
		return fechacreacion;
	}
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}
	public String getConfdeudor() {
		return confdeudor;
	}
	public void setConfdeudor(String confdeudor) {
		this.confdeudor = confdeudor;
	}
	public String getCtaclientes() {
		return ctaclientes;
	}
	public void setCtaclientes(String ctaclientes) {
		this.ctaclientes = ctaclientes;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bancos_codigo, confdeudor, ctaclientes, fechacreacion, idfactura, idpersona, importe,
				numerofactura);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoPorBancoItem other = (PagoPorBancoItem) obj;
		return Objects.equals(bancos_codigo, other.bancos_codigo) && Objects.equals(confdeudor, other.confdeudor)
				&& Objects.equals(ctaclientes, other.ctaclientes) && Objects.equals(fechacreacion, other.fechacreacion)
				&& idfactura == other.idfactura && idpersona == other.idpersona
				&& Float.floatToIntBits(importe) == Float.floatToIntBits(other.importe)
				&& Objects.equals(numerofactura, other.numerofactura);
	}
	
	@Override
	public String toString() {
		return "PagoPorBancoItem [idfactura=" + idfactura + ", numerofactura=" + numerofactura + ", importe=" + importe
				+ ", bancos_codigo=" + bancos_codigo + ", idpersona=" + idpersona + ", fechacreacion=" + fechacreacion
				+ ", confdeudor=" + confdeudor + ", ctaclientes=" + ctaclientes + ", getIdfactura()=" + getIdfactura()
				+ ", getNumerofactura()=" + getNumerofactura() + ", getImporte()=" + getImporte()
				+ ", getBancos_codigo()=" + getBancos_codigo() + ", getIdpersona()=" + getIdpersona()
				+ ", getFechacreacion()=" + getFechacreacion() + ", getConfdeudor()=" + getConfdeudor()
				+ ", getCtaclientes()=" + getCtaclientes() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
