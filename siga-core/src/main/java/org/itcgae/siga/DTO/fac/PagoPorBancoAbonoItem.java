package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class PagoPorBancoAbonoItem {
	
	private int idabono;
	private String numeroabono;
	private int iddisqueteabono;
	private float importe;
	private String bancos_codigo;
	private int idpersona;
	private Date fecha;
	private int estado;
	private String numerofactura;
	private String confingresos;
	private String ctaingresos;
	private String confdeudor;
	private String ctaclientes;
	private String ctaproductoservicio;
	private String idfactura;
	
	public int getIdabono() {
		return idabono;
	}
	public void setIdabono(int idabono) {
		this.idabono = idabono;
	}
	public String getNumeroabono() {
		return numeroabono;
	}
	public void setNumeroabono(String numeroabono) {
		this.numeroabono = numeroabono;
	}
	public int getIddisqueteabono() {
		return iddisqueteabono;
	}
	public void setIddisqueteabono(int iddisqueteabono) {
		this.iddisqueteabono = iddisqueteabono;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getNumerofactura() {
		return numerofactura;
	}
	public void setNumerofactura(String numerofactura) {
		this.numerofactura = numerofactura;
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
	public String getCtaproductoservicio() {
		return ctaproductoservicio;
	}
	public void setCtaproductoservicio(String ctaproductoservicio) {
		this.ctaproductoservicio = ctaproductoservicio;
	}
	public String getIdfactura() {
		return idfactura;
	}
	public void setIdfactura(String idfactura) {
		this.idfactura = idfactura;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bancos_codigo, confdeudor, confingresos, ctaclientes, ctaingresos, ctaproductoservicio,
				estado, fecha, idabono, iddisqueteabono, idfactura, idpersona, importe, numeroabono, numerofactura);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagoPorBancoAbonoItem other = (PagoPorBancoAbonoItem) obj;
		return Objects.equals(bancos_codigo, other.bancos_codigo) && Objects.equals(confdeudor, other.confdeudor)
				&& Objects.equals(confingresos, other.confingresos) && Objects.equals(ctaclientes, other.ctaclientes)
				&& Objects.equals(ctaingresos, other.ctaingresos)
				&& Objects.equals(ctaproductoservicio, other.ctaproductoservicio) && estado == other.estado
				&& Objects.equals(fecha, other.fecha) && idabono == other.idabono
				&& iddisqueteabono == other.iddisqueteabono && Objects.equals(idfactura, other.idfactura)
				&& idpersona == other.idpersona && Float.floatToIntBits(importe) == Float.floatToIntBits(other.importe)
				&& Objects.equals(numeroabono, other.numeroabono) && Objects.equals(numerofactura, other.numerofactura);
	}
	
	@Override
	public String toString() {
		return "PagoPorCajaAbonoItem [idabono=" + idabono + ", numeroabono=" + numeroabono + ", iddisqueteabono="
				+ iddisqueteabono + ", importe=" + importe + ", bancos_codigo=" + bancos_codigo + ", idpersona="
				+ idpersona + ", fecha=" + fecha + ", estado=" + estado + ", numerofactura=" + numerofactura
				+ ", confingresos=" + confingresos + ", ctaingresos=" + ctaingresos + ", confdeudor=" + confdeudor
				+ ", ctaclientes=" + ctaclientes + ", ctaproductoservicio=" + ctaproductoservicio + ", idfactura="
				+ idfactura + "]";
	}

}
