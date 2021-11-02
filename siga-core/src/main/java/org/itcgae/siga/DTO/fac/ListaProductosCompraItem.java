package org.itcgae.siga.DTO.fac;

public class ListaProductosCompraItem {
	
	private String orden;
	private String descripcion;
	private String observaciones;
	private String cantidad;
	private String precioUnitario;
	private String iva;
	private String total;
	private String noFacturable;
	private String idtipoiva;
	private String valorIva;
	private String solicitarBaja;
	
	private int idproducto;
	private int idtipoproducto;
	private int idproductoinstitucion;
	
	private String idPeticion;
	private String idPersona;
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getIdtipoproducto() {
		return idtipoproducto;
	}
	public void setIdtipoproducto(int idtipoproducto) {
		this.idtipoproducto = idtipoproducto;
	}
	public int getIdproductoinstitucion() {
		return idproductoinstitucion;
	}
	public void setIdproductoinstitucion(int idproductoinstitucion) {
		this.idproductoinstitucion = idproductoinstitucion;
	}
	public String getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNoFacturable() {
		return noFacturable;
	}
	public void setNoFacturable(String noFacturable) {
		this.noFacturable = noFacturable;
	}
	public String getIdtipoiva() {
		return idtipoiva;
	}
	public void setIdtipoiva(String idtipoiva) {
		this.idtipoiva = idtipoiva;
	}
	public String getValorIva() {
		return valorIva;
	}
	public void setValorIva(String valorIva) {
		this.valorIva = valorIva;
	}
	public String getSolicitarBaja() {
		return solicitarBaja;
	}
	public void setSolicitarBaja(String solicitarBaja) {
		this.solicitarBaja = solicitarBaja;
	}
}
