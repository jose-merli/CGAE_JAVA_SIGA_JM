package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FichaCompraSuscripcionItem {

	// TARJETA CLIENTE
	private String idInstitucion;
	private String idPersona;
	private String nombre;
	private String apellidos;
	private String idtipoidentificacion;
	private String nif;

	// TARJETA SOLICITUD
	private String idPeticion;
	private String usuModificacion;
	private Date fechaSolicitud;
	private Date fechaAprobacion;
	private Date fechaDenegacion;
	private Date fechaAnulacion;
	
	//TARJETA FORMA DE PAGO
	private String idFormasPagoComunes;
	private Short idFormaPagoSeleccionada;
	private float totalNeto;
	private float totalIVA;
	private float impTotal;
	private float pendPago;
    private String cuentaBancSelecc;
	
	private String idEstadoPeticion;

    private ListaProductosItem[] productos;
	
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getIdtipoidentificacion() {
		return idtipoidentificacion;
	}
	public void setIdtipoidentificacion(String idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	public Date getFechaDenegacion() {
		return fechaDenegacion;
	}
	public void setFechaDenegacion(Date fechaDenegacion) {
		this.fechaDenegacion = fechaDenegacion;
	}
	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}
	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	public ListaProductosItem[] getProductos() {
		return productos;
	}
	public void setProductos(ListaProductosItem[] productos) {
		this.productos = productos;
	}
	public String getIdFormasPagoComunes() {
		return idFormasPagoComunes;
	}
	public void setIdFormasPagoComunes(String idFormasPagoComunes) {
		this.idFormasPagoComunes = idFormasPagoComunes;
	}
	public Short getIdFormaPagoSeleccionada() {
		return idFormaPagoSeleccionada;
	}
	public void setIdFormaPagoSeleccionada(Short idFormaPagoSeleccionada) {
		this.idFormaPagoSeleccionada = idFormaPagoSeleccionada;
	}
	public String getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	public String getIdEstadoPeticion() {
		return idEstadoPeticion;
	}
	public void setIdEstadoPeticion(String idEstadoPeticion) {
		this.idEstadoPeticion = idEstadoPeticion;
	}
	public String getCuentaBancSelecc() {
		return cuentaBancSelecc;
	}
	public void setCuentaBancSelecc(String cuentaBancSelecc) {
		this.cuentaBancSelecc = cuentaBancSelecc;
	}
	public float getTotalNeto() {
		return totalNeto;
	}
	public void setTotalNeto(float totalNeto) {
		this.totalNeto = totalNeto;
	}
	public float getTotalIVA() {
		return totalIVA;
	}
	public void setTotalIVA(float totalIVA) {
		this.totalIVA = totalIVA;
	}
	public float getImpTotal() {
		return impTotal;
	}
	public void setImpTotal(float impTotal) {
		this.impTotal = impTotal;
	}
	public float getPendPago() {
		return pendPago;
	}
	public void setPendPago(float pendPago) {
		this.pendPago = pendPago;
	}
	public String getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}

}
