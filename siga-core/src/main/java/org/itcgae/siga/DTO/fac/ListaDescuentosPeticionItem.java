package org.itcgae.siga.DTO.fac;

import java.math.BigDecimal;
import java.util.Date;

public class ListaDescuentosPeticionItem {

	private String idPeticion;
	private String idLinea;
	private Short idAnticipo;
	private String tipo; //1 (anticipo / descuento) , 2 (monedero)
	private String desTipo;
	private String descripcion;
	private BigDecimal importe;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public String getDesTipo() {
		return desTipo;
	}
	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}
	public String getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Short getIdAnticipo() {
		return idAnticipo;
	}
	public void setIdAnticipo(Short idAnticipo) {
		this.idAnticipo = idAnticipo;
	}
	public String getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(String idLinea) {
		this.idLinea = idLinea;
	}
}
