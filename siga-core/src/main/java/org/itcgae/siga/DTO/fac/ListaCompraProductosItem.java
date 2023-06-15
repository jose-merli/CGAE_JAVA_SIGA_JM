package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class ListaCompraProductosItem {

	private Date fechaSolicitud;
	private String idPersona;
	private String nSolicitud; //Equivaldria al idpeticion de la tabla pys_peticioncomprasuscripcion
	private String nIdentificacion; //de la persona asociada a la compra (cliente)
	private String nColegiado; //si el comprador es colegiado de ese colegio.
	private String apellidosNombre;// de la persona asociada a la compra.
	private String concepto;// nombre del producto solicitado. En caso de haber varios, mostrar el primero añadido y añadir puntos suspensivos. Se mostrará como un link.
	private String idFormaPago; // forma de pago utilizada
	private String importe; // valor aplicado durante la compra (importe total)
	private String idEstadoSolicitud; // ver estados en Ficha Compra/Suscripción > Tarjeta Solicitud.
	private Date fechaEfectiva; // fecha cuando se acepta la solicitud
	private String estadoFactura;
	private Date fechaDenegada;
	private Date fechaSolicitadaAnulacion;
	private Date fechaAnulada;
	private String desFormaPago;
	private String facturas;
	private String solicitarBaja;//En este caso representa si todos los servicios tienen el valor "solicitarBaja" a 1 o no. 
	//Se realiza una resta de los valores con el numero de columnas. Si no es 0, un colegiado no puede solicitar una anulación.
	
	//Se guardará información del primer producto para los datos necesarios del enlace en la tabla.
	private String idTipoProducto;
	private String idProducto;
	private String idProductoInstitucion;
	
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
	public String getEstadoFactura() {
		return estadoFactura;
	}
	public void setEstadoFactura(String estadoFactura) {
		this.estadoFactura = estadoFactura;
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
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getFacturas() {
		return facturas;
	}
	public void setFacturas(String facturas) {
		this.facturas = facturas;
	}
	public String getSolicitarBaja() {
		return solicitarBaja;
	}
	public void setSolicitarBaja(String solicitarBaja) {
		this.solicitarBaja = solicitarBaja;
	}
	public String getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(String idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getIdProductoInstitucion() {
		return idProductoInstitucion;
	}
	public void setIdProductoInstitucion(String idProductoInstitucion) {
		this.idProductoInstitucion = idProductoInstitucion;
	}
}
