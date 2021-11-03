package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class ListaSuscripcionesItem {
	
	private Date fechaSolicitud;
	private String nSolicitud; //Equivaldria al idpeticion de la tabla pys_peticioncomprasuscripcion
	private String nIdentificacion; //de la persona asociada a la compra (cliente)
	private String nColegiado; //si el comprador es colegiado de ese colegio.
	private String apellidosNombre;// de la persona asociada a la compra.
	private String concepto;// nombre del producto solicitado. En caso de haber varios, mostrar el primero añadido y añadir puntos suspensivos. Se mostrará como un link.
	private String idFormaPago; // forma de pago utilizada
	private String importe; // valor aplicado durante la compra (importe total)
	private String idEstadoSolicitud; // ver estados en Ficha Compra/Suscripción > Tarjeta Solicitud.
	private Date fechaEfectiva; // fecha cuando se acepta la solicitud
	private Date fechaDenegada;
	private Date fechaSolicitadaAnulacion;
	private Date fechaAnulada;
	private String desFormaPago;
	private String precioPerio; 
	private Date fechaSuscripcion; 
	private Date fechaBaja;
	private String idEstadoFactura;
	
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getnSolicitud() {
		return nSolicitud;
	}
	public void setnSolicitud(String nSolicitud) {
		this.nSolicitud = nSolicitud;
	}
	public String getnIdentificacion() {
		return nIdentificacion;
	}
	public void setnIdentificacion(String nIdentificacion) {
		this.nIdentificacion = nIdentificacion;
	}
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}
	public void setIdEstadoSolicitud(String idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}
	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	public Date getFechaDenegada() {
		return fechaDenegada;
	}
	public void setFechaDenegada(Date fechaDenegada) {
		this.fechaDenegada = fechaDenegada;
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
	public String getDesFormaPago() {
		return desFormaPago;
	}
	public void setDesFormaPago(String desFormaPago) {
		this.desFormaPago = desFormaPago;
	}
	public String getPrecioPerio() {
		return precioPerio;
	}
	public void setPrecioPerio(String precioPerio) {
		this.precioPerio = precioPerio;
	}
	public Date getFechaSuscripcion() {
		return fechaSuscripcion;
	}
	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getIdEstadoFactura() {
		return idEstadoFactura;
	}
	public void setIdEstadoFactura(String idEstadoFactura) {
		this.idEstadoFactura = idEstadoFactura;
	}

}
