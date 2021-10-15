package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SerieFacturacionItem {
	
	private String idSerieFacturacion;
	private String observaciones;
	private Date fechaBaja;
	private String idFormaPago;
	private String formaPago;
	private String abreviatura;
	private String descripcion;
	private String cuentaBancaria;
	private String idSufijo;
	private String sufijo;
	private List<String> tiposIncluidos;
	private Boolean generarPDF;
	private Boolean envioFacturas;
	private Boolean traspasoFacturas;
	
	private List<String> tiposProductos;
	private List<String> tiposServicios;
	private List<String> etiquetas;
	private List<String> consultasDestinatarios;
	private String contadorFacturas;
	private String contadorFacturasRectificativas;
	
	
	public String getIdSerieFacturacion() {
		return idSerieFacturacion;
	}
	public void setIdSerieFacturacion(String idSerieFacturacion) {
		this.idSerieFacturacion = idSerieFacturacion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}
	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
	public String getIdSufijo() {
		return idSufijo;
	}
	public void setIdSufijo(String idSufijo) {
		this.idSufijo = idSufijo;
	}
	public String getSufijo() {
		return sufijo;
	}
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}
	public List<String> getTiposIncluidos() {
		return tiposIncluidos;
	}
	public void setTiposIncluidos(List<String> tiposIncluidos) {
		this.tiposIncluidos = tiposIncluidos;
	}
	public Boolean getGenerarPDF() {
		return generarPDF;
	}
	public void setGenerarPDF(Boolean generarPDF) {
		this.generarPDF = generarPDF;
	}
	public Boolean getEnvioFacturas() {
		return envioFacturas;
	}
	public void setEnvioFacturas(Boolean envioFacturas) {
		this.envioFacturas = envioFacturas;
	}
	public Boolean getTraspasoFacturas() {
		return traspasoFacturas;
	}
	public void setTraspasoFacturas(Boolean traspasoFacturas) {
		this.traspasoFacturas = traspasoFacturas;
	}
	public List<String> getTiposProductos() {
		return tiposProductos;
	}
	public void setTiposProductos(List<String> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}
	public List<String> getTiposServicios() {
		return tiposServicios;
	}
	public void setTiposServicios(List<String> tiposServicios) {
		this.tiposServicios = tiposServicios;
	}
	public List<String> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<String> etiquetas) {
		this.etiquetas = etiquetas;
	}
	public List<String> getConsultasDestinatarios() {
		return consultasDestinatarios;
	}
	public void setConsultasDestinatarios(List<String> consultasDestinatarios) {
		this.consultasDestinatarios = consultasDestinatarios;
	}
	public String getContadorFacturas() {
		return contadorFacturas;
	}
	public void setContadorFacturas(String contadorFacturas) {
		this.contadorFacturas = contadorFacturas;
	}
	public String getContadorFacturasRectificativas() {
		return contadorFacturasRectificativas;
	}
	public void setContadorFacturasRectificativas(String contadorFacturasRectificativas) {
		this.contadorFacturasRectificativas = contadorFacturasRectificativas;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SerieFacturacionItem that = (SerieFacturacionItem) o;
		return Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
				Objects.equals(observaciones, that.observaciones) &&
				Objects.equals(fechaBaja, that.fechaBaja) &&
				Objects.equals(idFormaPago, that.idFormaPago) &&
				Objects.equals(formaPago, that.formaPago) &&
				Objects.equals(abreviatura, that.abreviatura) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(cuentaBancaria, that.cuentaBancaria) &&
				Objects.equals(idSufijo, that.idSufijo) &&
				Objects.equals(sufijo, that.sufijo) &&
				Objects.equals(tiposIncluidos, that.tiposIncluidos) &&
				Objects.equals(generarPDF, that.generarPDF) &&
				Objects.equals(envioFacturas, that.envioFacturas) &&
				Objects.equals(traspasoFacturas, that.traspasoFacturas) &&
				Objects.equals(tiposProductos, that.tiposProductos) &&
				Objects.equals(tiposServicios, that.tiposServicios) &&
				Objects.equals(etiquetas, that.etiquetas) &&
				Objects.equals(consultasDestinatarios, that.consultasDestinatarios) &&
				Objects.equals(contadorFacturas, that.contadorFacturas) &&
				Objects.equals(contadorFacturasRectificativas, that.contadorFacturasRectificativas);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSerieFacturacion, observaciones, fechaBaja, idFormaPago, formaPago, abreviatura, descripcion, cuentaBancaria, idSufijo, sufijo, tiposIncluidos, generarPDF, envioFacturas, traspasoFacturas, tiposProductos, tiposServicios, etiquetas, consultasDestinatarios, contadorFacturas, contadorFacturasRectificativas);
	}

	@Override
	public String toString() {
		return "SerieFacturacionItem{" +
				"idSerieFacturacion='" + idSerieFacturacion + '\'' +
				", observaciones='" + observaciones + '\'' +
				", fechaBaja=" + fechaBaja +
				", idFormaPago='" + idFormaPago + '\'' +
				", formaPago='" + formaPago + '\'' +
				", abreviatura='" + abreviatura + '\'' +
				", descripcion='" + descripcion + '\'' +
				", cuentaBancaria='" + cuentaBancaria + '\'' +
				", idSufijo='" + idSufijo + '\'' +
				", sufijo='" + sufijo + '\'' +
				", tiposIncluidos=" + tiposIncluidos +
				", generarPDF=" + generarPDF +
				", envioFacturas=" + envioFacturas +
				", traspasoFacturas=" + traspasoFacturas +
				", tiposProductos=" + tiposProductos +
				", tiposServicios=" + tiposServicios +
				", etiquetas=" + etiquetas +
				", consultasDestinatarios=" + consultasDestinatarios +
				", contadorFacturas='" + contadorFacturas + '\'' +
				", contadorFacturasRectificativas='" + contadorFacturasRectificativas + '\'' +
				'}';
	}
}
