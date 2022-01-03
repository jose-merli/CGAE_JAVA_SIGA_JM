package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class ListaServiciosSuscripcionItem {
	
	private String orden;
	private String descripcion;
	private String observaciones;
	private String cantidad;
	private String idPrecioServicio;
	private String precioServicioValor;
	private String precioServicioDesc;
	private String idPeriodicidad;
	private String periodicidadValor;
	private String periodicidadDesc;
	private String iva;
	private String total;
	private String noFacturable;
	private String idtipoiva;
	private String valorIva;
	private String solicitarBaja;
	private String automatico;
	private Date fechaAlta;
	private Date fechaBaja;
	
	private int idServicio;
	private int idTipoServicios;
	private int idServiciosInstitucion;
	
	private String idPeticion;
	private String idPersona;
	private String idComboPrecio;
	
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
	public int getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	public String getIdPrecioServicio() {
		return idPrecioServicio;
	}
	public void setIdPrecioServicio(String idPrecioServicio) {
		this.idPrecioServicio = idPrecioServicio;
	}
	public String getPrecioServicioValor() {
		return precioServicioValor;
	}
	public void setPrecioServicioValor(String precioServicioValor) {
		this.precioServicioValor = precioServicioValor;
	}
	public String getIdPeriodicidad() {
		return idPeriodicidad;
	}
	public void setIdPeriodicidad(String idPeriodicidad) {
		this.idPeriodicidad = idPeriodicidad;
	}
	public String getPeriodicidadValor() {
		return periodicidadValor;
	}
	public void setPeriodicidadValor(String periodicidadValor) {
		this.periodicidadValor = periodicidadValor;
	}
	public String getPeriodicidadDesc() {
		return periodicidadDesc;
	}
	public void setPeriodicidadDesc(String periodicidadDesc) {
		this.periodicidadDesc = periodicidadDesc;
	}
	public String getAutomatico() {
		return automatico;
	}
	public void setAutomatico(String automatico) {
		this.automatico = automatico;
	}
	public int getIdTipoServicios() {
		return idTipoServicios;
	}
	public void setIdTipoServicios(int idTipoServicios) {
		this.idTipoServicios = idTipoServicios;
	}
	public int getIdServiciosInstitucion() {
		return idServiciosInstitucion;
	}
	public void setIdServiciosInstitucion(int idServiciosInstitucion) {
		this.idServiciosInstitucion = idServiciosInstitucion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getPrecioServicioDesc() {
		return precioServicioDesc;
	}
	public void setPrecioServicioDesc(String precioServicioDesc) {
		this.precioServicioDesc = precioServicioDesc;
	}
	public String getIdComboPrecio() {
		return idComboPrecio;
	}
	public void setIdComboPrecio(String idComboPrecio) {
		this.idComboPrecio = idComboPrecio;
	}

}
