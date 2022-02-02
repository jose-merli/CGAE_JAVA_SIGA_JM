package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class PagoPorTarjetaItem {
	
	private int idfactura;
	private	String numerofactura;
	private String tarjeta;
	private String confdeudor;
	private String ctaclientes;
	private float importe;
	private int idpersona;
	private Date fecha;
	
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
	public String getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
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
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(confdeudor, ctaclientes, fecha, idfactura, idpersona, importe, numerofactura, tarjeta);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoPorTarjetaItem other = (PagoPorTarjetaItem) obj;
		return Objects.equals(confdeudor, other.confdeudor) && Objects.equals(ctaclientes, other.ctaclientes)
				&& Objects.equals(fecha, other.fecha) && idfactura == other.idfactura && idpersona == other.idpersona
				&& Float.floatToIntBits(importe) == Float.floatToIntBits(other.importe)
				&& Objects.equals(numerofactura, other.numerofactura) && Objects.equals(tarjeta, other.tarjeta);
	}
	
	@Override
	public String toString() {
		return "PagoPorTarjetaItem [idfactura=" + idfactura + ", numerofactura=" + numerofactura + ", tarjeta="
				+ tarjeta + ", confdeudor=" + confdeudor + ", ctaclientes=" + ctaclientes + ", importe=" + importe
				+ ", idpersona=" + idpersona + ", fecha=" + fecha + "]";
	}
	
}
