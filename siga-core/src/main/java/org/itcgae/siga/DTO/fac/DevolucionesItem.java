package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class DevolucionesItem {

	private String idfactura;
	private int idpersona;
	private float importe;
	private int iddisquetedevoluciones;
	private float gastosdevolucion;
	private String cargarcliente;
	private String bancos_codigo;
	private int idpersona_1;
	private Date fechageneracion;
	private String numerofactura;
	private String confdeudor;
	private String ctaclientes;
	
	public String getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(String idfactura) {
		this.idfactura = idfactura;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public int getIddisquetedevoluciones() {
		return iddisquetedevoluciones;
	}
	public void setIddisquetedevoluciones(int iddisquetedevoluciones) {
		this.iddisquetedevoluciones = iddisquetedevoluciones;
	}
	public float getGastosdevolucion() {
		return gastosdevolucion;
	}
	public void setGastosdevolucion(float gastosdevolucion) {
		this.gastosdevolucion = gastosdevolucion;
	}
	public String getCargarcliente() {
		return cargarcliente;
	}
	public void setCargarcliente(String cargarcliente) {
		this.cargarcliente = cargarcliente;
	}
	public String getBancos_codigo() {
		return bancos_codigo;
	}
	public void setBancos_codigo(String bancos_codigo) {
		this.bancos_codigo = bancos_codigo;
	}
	public int getIdpersona_1() {
		return idpersona_1;
	}
	public void setIdpersona_1(int idpersona_1) {
		this.idpersona_1 = idpersona_1;
	}
	public Date getFechageneracion() {
		return fechageneracion;
	}
	public void setFechageneracion(Date fechageneracion) {
		this.fechageneracion = fechageneracion;
	}
	public String getNumerofactura() {
		return numerofactura;
	}
	public void setNumerofactura(String numerofactura) {
		this.numerofactura = numerofactura;
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
		return Objects.hash(bancos_codigo, cargarcliente, confdeudor, ctaclientes, fechageneracion, gastosdevolucion,
				iddisquetedevoluciones, idfactura, idpersona, idpersona_1, importe, numerofactura);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevolucionesItem other = (DevolucionesItem) obj;
		return Objects.equals(bancos_codigo, other.bancos_codigo) && Objects.equals(cargarcliente, other.cargarcliente)
				&& Objects.equals(confdeudor, other.confdeudor) && Objects.equals(ctaclientes, other.ctaclientes)
				&& Objects.equals(fechageneracion, other.fechageneracion)
				&& Float.floatToIntBits(gastosdevolucion) == Float.floatToIntBits(other.gastosdevolucion)
				&& iddisquetedevoluciones == other.iddisquetedevoluciones && Objects.equals(idfactura, other.idfactura)
				&& idpersona == other.idpersona && idpersona_1 == other.idpersona_1
				&& Float.floatToIntBits(importe) == Float.floatToIntBits(other.importe)
				&& Objects.equals(numerofactura, other.numerofactura);
	}
	
	@Override
	public String toString() {
		return "DevolucionesItem [idfactura=" + idfactura + ", idpersona=" + idpersona + ", importe=" + importe
				+ ", iddisquetedevoluciones=" + iddisquetedevoluciones + ", gastosdevolucion=" + gastosdevolucion
				+ ", cargarcliente=" + cargarcliente + ", bancos_codigo=" + bancos_codigo + ", idpersona_1="
				+ idpersona_1 + ", fechageneracion=" + fechageneracion + ", numerofactura=" + numerofactura
				+ ", confdeudor=" + confdeudor + ", ctaclientes=" + ctaclientes + "]";
	}
	
}
