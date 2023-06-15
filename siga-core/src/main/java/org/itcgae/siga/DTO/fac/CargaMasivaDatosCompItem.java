package org.itcgae.siga.DTO.fac;

import java.math.BigDecimal;
import java.util.Date;

public class CargaMasivaDatosCompItem {
	
	private String numColegiadoCliente;
	private String nifCifCliente;
	private Long idPersona;
	private String apellidosCliente;
	private String nombreCliente;
	private String nombreProducto;
	private String cantidadProducto;
	private BigDecimal valor;
	private Integer idTipoIva;
	private Date fechaCompra;
	private String codigoProducto;
	private String noFacturable;
	private Short idInstitucion;
	private String errores;
	
	private Long idProducto;
	private Long idProductoInstitucion;
	private Short idTipoProducto;
	
	public String getNumColegiadoCliente() {
		return numColegiadoCliente;
	}
	public void setNumColegiadoCliente(String numColegiadoCliente) {
		this.numColegiadoCliente = numColegiadoCliente;
	}
	public String getNifCifCliente() {
		return nifCifCliente;
	}
	public void setNifCifCliente(String nifCifCliente) {
		this.nifCifCliente = nifCifCliente;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public String getApellidosCliente() {
		return apellidosCliente;
	}
	public void setApellidosCliente(String apellidosCliente) {
		this.apellidosCliente = apellidosCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(String cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
//	public Date getFechaSolicitud() {
//		return fechaSolicitud;
//	}
//	public void setFechaSolicitud(Date fechaSolicitud) {
//		this.fechaSolicitud = fechaSolicitud;
//	}
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Long getIdProductoInstitucion() {
		return idProductoInstitucion;
	}
	public void setIdProductoInstitucion(Long idProductoInstitucion) {
		this.idProductoInstitucion = idProductoInstitucion;
	}
	public Short getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(Short idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getIdTipoIva() {
		return idTipoIva;
	}
	public void setIdTipoIva(Integer idTipoIva) {
		this.idTipoIva = idTipoIva;
	}
	public String getNoFacturable() {
		return noFacturable;
	}
	public void setNoFacturable(String noFacturable) {
		this.noFacturable = noFacturable;
	}

}
