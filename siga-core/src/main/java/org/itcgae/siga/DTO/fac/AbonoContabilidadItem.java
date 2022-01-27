package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class AbonoContabilidadItem {
	
	private int idabono;
	private String numeroabono;
	private int idpersona;
	private Date fecha;
	private float impneto;
	private float impiva;
	private float iva;
	private String descripcion;
	private String ctaproductoservicio;
	private String ctaiva;
	private String numerofactura;
	private String idpersona_1;
	private String devuelta;
	private String confdeudor;
	private String confingresos;
	private String ctaingresos;
	private String ctaclientes;
	private float imptotalpagadoporbanco;
	private float imptotalpagadoporcaja;
	private String bancos_codigo;
	
	public int getIdabono() {
		return idabono;
	}
	public void setIdabono(int idabono) {
		this.idabono = idabono;
	}
	public String getNumeroabono() {
		return numeroabono;
	}
	public String getBancos_codigo() {
		return bancos_codigo;
	}
	public float getImpneto() {
		return impneto;
	}
	public void setImpneto(float impneto) {
		this.impneto = impneto;
	}
	public void setBancos_codigo(String bancos_codigo) {
		this.bancos_codigo = bancos_codigo;
	}
	public void setNumeroabono(String numeroabono) {
		this.numeroabono = numeroabono;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCtaproductoservicio() {
		return ctaproductoservicio;
	}
	public void setCtaproductoservicio(String ctaproductoservicio) {
		this.ctaproductoservicio = ctaproductoservicio;
	}
	public String getCtaiva() {
		return ctaiva;
	}
	public void setCtaiva(String ctaiva) {
		this.ctaiva = ctaiva;
	}
	public String getNumerofactura() {
		return numerofactura;
	}
	public void setNumerofactura(String numerofactura) {
		this.numerofactura = numerofactura;
	}
	public String getIdpersona_1() {
		return idpersona_1;
	}
	public void setIdpersona_1(String idpersona_1) {
		this.idpersona_1 = idpersona_1;
	}
	public String getDevuelta() {
		return devuelta;
	}
	public void setDevuelta(String devuelta) {
		this.devuelta = devuelta;
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
	public float getImptotalpagadoporbanco() {
		return imptotalpagadoporbanco;
	}
	public void setImptotalpagadoporbanco(float imptotalpagadoporbanco) {
		this.imptotalpagadoporbanco = imptotalpagadoporbanco;
	}
	public float getImptotalpagadoporcaja() {
		return imptotalpagadoporcaja;
	}
	public void setImptotalpagadoporcaja(float imptotalpagadoporcaja) {
		this.imptotalpagadoporcaja = imptotalpagadoporcaja;
	}
	@Override
	public int hashCode() {
		return Objects.hash(bancos_codigo, confdeudor, confingresos, ctaclientes, ctaingresos, ctaiva,
				ctaproductoservicio, descripcion, devuelta, fecha, idabono, idpersona, idpersona_1, impiva, impneto,
				imptotalpagadoporbanco, imptotalpagadoporcaja, iva, numeroabono, numerofactura);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbonoContabilidadItem other = (AbonoContabilidadItem) obj;
		return Objects.equals(bancos_codigo, other.bancos_codigo) && Objects.equals(confdeudor, other.confdeudor)
				&& Objects.equals(confingresos, other.confingresos) && Objects.equals(ctaclientes, other.ctaclientes)
				&& Objects.equals(ctaingresos, other.ctaingresos) && Objects.equals(ctaiva, other.ctaiva)
				&& Objects.equals(ctaproductoservicio, other.ctaproductoservicio)
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(devuelta, other.devuelta)
				&& Objects.equals(fecha, other.fecha) && idabono == other.idabono && idpersona == other.idpersona
				&& Objects.equals(idpersona_1, other.idpersona_1)
				&& Float.floatToIntBits(impiva) == Float.floatToIntBits(other.impiva)
				&& Float.floatToIntBits(impneto) == Float.floatToIntBits(other.impneto)
				&& Float.floatToIntBits(imptotalpagadoporbanco) == Float.floatToIntBits(other.imptotalpagadoporbanco)
				&& Float.floatToIntBits(imptotalpagadoporcaja) == Float.floatToIntBits(other.imptotalpagadoporcaja)
				&& Float.floatToIntBits(iva) == Float.floatToIntBits(other.iva)
				&& Objects.equals(numeroabono, other.numeroabono) && Objects.equals(numerofactura, other.numerofactura);
	}
	
	@Override
	public String toString() {
		return "AbonoContabilidadItem [idabono=" + idabono + ", numeroabono=" + numeroabono + ", idpersona=" + idpersona
				+ ", fecha=" + fecha + ", impneto=" + impneto + ", impiva=" + impiva + ", iva=" + iva + ", descripcion="
				+ descripcion + ", ctaproductoservicio=" + ctaproductoservicio + ", ctaiva=" + ctaiva
				+ ", numerofactura=" + numerofactura + ", idpersona_1=" + idpersona_1 + ", devuelta=" + devuelta
				+ ", confdeudor=" + confdeudor + ", confingresos=" + confingresos + ", ctaingresos=" + ctaingresos
				+ ", ctaclientes=" + ctaclientes + ", imptotalpagadoporbanco=" + imptotalpagadoporbanco
				+ ", imptotalpagadoporcaja=" + imptotalpagadoporcaja + ", bancos_codigo=" + bancos_codigo + "]";
	}
	
}
