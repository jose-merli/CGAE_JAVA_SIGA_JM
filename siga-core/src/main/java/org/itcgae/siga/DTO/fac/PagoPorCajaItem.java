package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class PagoPorCajaItem {
	
	private String anticipo;
	private int idfactura;
	private String numerofactura;
	private String tarjeta;
	private String confdeufor;
	private String ctaclientes;
	private float importe;
	private String tipoapunte;
	private int idpersona;
	private Date fecha;
	
	public String getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}
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
	public String getConfdeufor() {
		return confdeufor;
	}
	public void setConfdeufor(String confdeufor) {
		this.confdeufor = confdeufor;
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
	public String getTipoapunte() {
		return tipoapunte;
	}
	public void setTipoapunte(String tipoapunte) {
		this.tipoapunte = tipoapunte;
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
		return Objects.hash(anticipo, confdeufor, ctaclientes, fecha, idfactura, idpersona, importe, numerofactura,
				tarjeta, tipoapunte);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoPorCajaItem other = (PagoPorCajaItem) obj;
		return Objects.equals(anticipo, other.anticipo) && Objects.equals(confdeufor, other.confdeufor)
				&& Objects.equals(ctaclientes, other.ctaclientes) && Objects.equals(fecha, other.fecha)
				&& idfactura == other.idfactura && idpersona == other.idpersona
				&& Float.floatToIntBits(importe) == Float.floatToIntBits(other.importe)
				&& Objects.equals(numerofactura, other.numerofactura) && Objects.equals(tarjeta, other.tarjeta)
				&& Objects.equals(tipoapunte, other.tipoapunte);
	}
	
	@Override
	public String toString() {
		return "PagoPorCajaItem [anticipo=" + anticipo + ", idfactura=" + idfactura + ", numerofactura=" + numerofactura
				+ ", tarjeta=" + tarjeta + ", confdeufor=" + confdeufor + ", ctaclientes=" + ctaclientes + ", importe="
				+ importe + ", tipoapunte=" + tipoapunte + ", idpersona=" + idpersona + ", fecha=" + fecha + "]";
	}
	
}
