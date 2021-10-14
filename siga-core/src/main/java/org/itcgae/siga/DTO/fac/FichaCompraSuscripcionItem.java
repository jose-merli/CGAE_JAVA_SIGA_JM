package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.List;

public class FichaCompraSuscripcionItem {

	// TARJETA CLIENTE
	private String idInstitucion;
	private String idPersona;
	private String nombre;
	private String apellidos;
	private String idtipoidentificacion;
	private String nif;

	// TARJETA SOLICITUD
	private String nSolicitud;
	private String usuModificacion;
	private Date fechaPendiente;
	private Date fechaDenegada;
	private Date fechaAceptada;
	private Date fechaSolicitadaAnulacion;
	private Date fechaAnulada;
	
	//TARJETA FORMA DE PAGO
	private String idFormasPagoComunes;
	private String idFormaPagoSeleccionada;
	private String totalNeto;
	private String totalIVA;
	private String impTotal;
	private String pendPago;
    private String cuentaBancSelecc;
	
	private String idEstadoPeticion;

    private List<ListaProductosCompraItem> productos;
	
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
	public String getnSolicitud() {
		return nSolicitud;
	}
	public void setnSolicitud(String nSolicitud) {
		this.nSolicitud = nSolicitud;
	}
	public List<ListaProductosCompraItem> getProductos() {
		return productos;
	}
	public void setProductos(List<ListaProductosCompraItem> productos) {
		this.productos = productos;
	}
	public String getIdFormasPagoComunes() {
		return idFormasPagoComunes;
	}
	public void setIdFormasPagoComunes(String idFormasPagoComunes) {
		this.idFormasPagoComunes = idFormasPagoComunes;
	}
	public String getIdFormaPagoSeleccionada() {
		return idFormaPagoSeleccionada;
	}
	public void setIdFormaPagoSeleccionada(String idFormaPagoSeleccionada) {
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
	public String getTotalNeto() {
		return totalNeto;
	}
	public void setTotalNeto(String totalNeto) {
		this.totalNeto = totalNeto;
	}
	public String getTotalIVA() {
		return totalIVA;
	}
	public void setTotalIVA(String totalIVA) {
		this.totalIVA = totalIVA;
	}
	public String getImpTotal() {
		return impTotal;
	}
	public void setImpTotal(String impTotal) {
		this.impTotal = impTotal;
	}
	public String getPendPago() {
		return pendPago;
	}
	public void setPendPago(String pendPago) {
		this.pendPago = pendPago;
	}
	public Date getFechaPendiente() {
		return fechaPendiente;
	}
	public void setFechaPendiente(Date fechaPendiente) {
		this.fechaPendiente = fechaPendiente;
	}
	public Date getFechaDenegada() {
		return fechaDenegada;
	}
	public void setFechaDenegada(Date fechaDenegada) {
		this.fechaDenegada = fechaDenegada;
	}
	public Date getFechaAceptada() {
		return fechaAceptada;
	}
	public void setFechaAceptada(Date fechaAceptada) {
		this.fechaAceptada = fechaAceptada;
	}
	public Date getFechaSolicitadaAnulacion() {
		return fechaSolicitadaAnulacion;
	}
	public void setFechaSolicitadaAnulacion(Date fechaSolicitadaAnulacion) {
		this.fechaSolicitadaAnulacion = fechaSolicitadaAnulacion;
	}
	public Date getFechaAnulada() {
		return fechaAnulada;
	}
	public void setFechaAnulada(Date fechaAnulada) {
		this.fechaAnulada = fechaAnulada;
	}

}
