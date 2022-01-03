package org.itcgae.siga.DTO.fac;

import org.apache.xpath.operations.Bool;
import org.itcgae.siga.DTOs.gen.ComboItem;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SerieFacturacionItem {

	private String idSerieFacturacion;
	private String observaciones;
	private Date fechaBaja;
	private String abreviatura;
	private String descripcion;
	private List<String> tiposIncluidos;

	private String idCuentaBancaria;
	private String cuentaBancaria;
	private String idSufijo;
	private String sufijo;

	private String idFormaPago;
	private Boolean formaPago;

	private Boolean generarPDF;
	private String idModeloFactura;
	private String idModeloRectificativa;

	private Boolean envioFacturas;
	private String idPlantillaMail;

	private Boolean traspasoFacturas;
	private String traspasoPlantilla;
	private String traspasoCodAuditoriaDef;

	private String confDeudor;
	private String ctaClientes;
	private String confIngresos;
	private String ctaIngresos;

	private List<String> idTiposProductos;
	private List<ComboItem> tiposProductos;
	private List<String> idTiposServicios;
	private List<ComboItem> tiposServicios;
	private List<String> idEtiquetas;
	private List<ComboItem> etiquetas;
	private List<String> idConsultasDestinatarios;
	private List<ComboItem> consultasDestinatarios;

	private String idContadorFacturas;
	private String idContadorFacturasRectificativas;

	private String idSerieFacturacionPrevia;
	private Boolean serieGenerica;

	public String getIdModeloRectificativa() {
		return idModeloRectificativa;
	}

	public void setIdModeloRectificativa(String idModeloRectificativa) {
		this.idModeloRectificativa = idModeloRectificativa;
	}

	public String getIdPlantillaMail() {
		return idPlantillaMail;
	}

	public void setIdPlantillaMail(String idPlantillaMail) {
		this.idPlantillaMail = idPlantillaMail;
	}

	public String getTraspasoPlantilla() {
		return traspasoPlantilla;
	}

	public void setTraspasoPlantilla(String traspasoPlantilla) {
		this.traspasoPlantilla = traspasoPlantilla;
	}

	public String getIdModeloFactura() {
		return idModeloFactura;
	}

	public void setIdModeloFactura(String idModeloFactura) {
		this.idModeloFactura = idModeloFactura;
	}

	public String getTraspasoCodAuditoriaDef() {
		return traspasoCodAuditoriaDef;
	}

	public void setTraspasoCodAuditoriaDef(String traspasoCodAuditoriaDef) {
		this.traspasoCodAuditoriaDef = traspasoCodAuditoriaDef;
	}

	public Boolean getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(Boolean formaPago) {
		this.formaPago = formaPago;
	}

	public String getConfDeudor() {
		return confDeudor;
	}

	public void setConfDeudor(String confDeudor) {
		this.confDeudor = confDeudor;
	}

	public String getCtaClientes() {
		return ctaClientes;
	}

	public void setCtaClientes(String ctaClientes) {
		this.ctaClientes = ctaClientes;
	}

	public String getConfIngresos() {
		return confIngresos;
	}

	public void setConfIngresos(String confIngresos) {
		this.confIngresos = confIngresos;
	}

	public String getCtaIngresos() {
		return ctaIngresos;
	}

	public void setCtaIngresos(String ctaIngresos) {
		this.ctaIngresos = ctaIngresos;
	}

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

	public List<String> getTiposIncluidos() {
		return tiposIncluidos;
	}

	public void setTiposIncluidos(List<String> tiposIncluidos) {
		this.tiposIncluidos = tiposIncluidos;
	}

	public String getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	public void setIdCuentaBancaria(String idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
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

	public String getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
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

	public List<String> getIdTiposProductos() {
		return idTiposProductos;
	}

	public void setIdTiposProductos(List<String> idTiposProductos) {
		this.idTiposProductos = idTiposProductos;
	}

	public List<ComboItem> getTiposProductos() {
		return tiposProductos;
	}

	public void setTiposProductos(List<ComboItem> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}

	public List<String> getIdTiposServicios() {
		return idTiposServicios;
	}

	public void setIdTiposServicios(List<String> idTiposServicios) {
		this.idTiposServicios = idTiposServicios;
	}

	public List<ComboItem> getTiposServicios() {
		return tiposServicios;
	}

	public void setTiposServicios(List<ComboItem> tiposServicios) {
		this.tiposServicios = tiposServicios;
	}

	public List<String> getIdEtiquetas() {
		return idEtiquetas;
	}

	public void setIdEtiquetas(List<String> idEtiquetas) {
		this.idEtiquetas = idEtiquetas;
	}

	public List<ComboItem> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<ComboItem> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public List<String> getIdConsultasDestinatarios() {
		return idConsultasDestinatarios;
	}

	public void setIdConsultasDestinatarios(List<String> idConsultasDestinatarios) {
		this.idConsultasDestinatarios = idConsultasDestinatarios;
	}

	public List<ComboItem> getConsultasDestinatarios() {
		return consultasDestinatarios;
	}

	public void setConsultasDestinatarios(List<ComboItem> consultasDestinatarios) {
		this.consultasDestinatarios = consultasDestinatarios;
	}

	public String getIdContadorFacturas() {
		return idContadorFacturas;
	}

	public void setIdContadorFacturas(String idContadorFacturas) {
		this.idContadorFacturas = idContadorFacturas;
	}

	public String getIdContadorFacturasRectificativas() {
		return idContadorFacturasRectificativas;
	}

	public void setIdContadorFacturasRectificativas(String idContadorFacturasRectificativas) {
		this.idContadorFacturasRectificativas = idContadorFacturasRectificativas;
	}

	public String getIdSerieFacturacionPrevia() {
		return idSerieFacturacionPrevia;
	}

	public void setIdSerieFacturacionPrevia(String idSerieFacturacionPrevia) {
		this.idSerieFacturacionPrevia = idSerieFacturacionPrevia;
	}

	public Boolean getSerieGenerica() {
		return serieGenerica;
	}

	public void setSerieGenerica(Boolean serieGenerica) {
		this.serieGenerica = serieGenerica;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SerieFacturacionItem that = (SerieFacturacionItem) o;
		return Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
				Objects.equals(observaciones, that.observaciones) &&
				Objects.equals(fechaBaja, that.fechaBaja) &&
				Objects.equals(abreviatura, that.abreviatura) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(tiposIncluidos, that.tiposIncluidos) &&
				Objects.equals(idCuentaBancaria, that.idCuentaBancaria) &&
				Objects.equals(cuentaBancaria, that.cuentaBancaria) &&
				Objects.equals(idSufijo, that.idSufijo) &&
				Objects.equals(sufijo, that.sufijo) &&
				Objects.equals(idFormaPago, that.idFormaPago) &&
				Objects.equals(formaPago, that.formaPago) &&
				Objects.equals(generarPDF, that.generarPDF) &&
				Objects.equals(idModeloFactura, that.idModeloFactura) &&
				Objects.equals(idModeloRectificativa, that.idModeloRectificativa) &&
				Objects.equals(envioFacturas, that.envioFacturas) &&
				Objects.equals(idPlantillaMail, that.idPlantillaMail) &&
				Objects.equals(traspasoFacturas, that.traspasoFacturas) &&
				Objects.equals(traspasoPlantilla, that.traspasoPlantilla) &&
				Objects.equals(traspasoCodAuditoriaDef, that.traspasoCodAuditoriaDef) &&
				Objects.equals(confDeudor, that.confDeudor) &&
				Objects.equals(ctaClientes, that.ctaClientes) &&
				Objects.equals(confIngresos, that.confIngresos) &&
				Objects.equals(ctaIngresos, that.ctaIngresos) &&
				Objects.equals(idTiposProductos, that.idTiposProductos) &&
				Objects.equals(tiposProductos, that.tiposProductos) &&
				Objects.equals(idTiposServicios, that.idTiposServicios) &&
				Objects.equals(tiposServicios, that.tiposServicios) &&
				Objects.equals(idEtiquetas, that.idEtiquetas) &&
				Objects.equals(etiquetas, that.etiquetas) &&
				Objects.equals(idConsultasDestinatarios, that.idConsultasDestinatarios) &&
				Objects.equals(consultasDestinatarios, that.consultasDestinatarios) &&
				Objects.equals(idContadorFacturas, that.idContadorFacturas) &&
				Objects.equals(idContadorFacturasRectificativas, that.idContadorFacturasRectificativas) &&
				Objects.equals(idSerieFacturacionPrevia, that.idSerieFacturacionPrevia) &&
				Objects.equals(serieGenerica, that.serieGenerica);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSerieFacturacion, observaciones, fechaBaja, abreviatura, descripcion, tiposIncluidos, idCuentaBancaria, cuentaBancaria, idSufijo, sufijo, idFormaPago, formaPago, generarPDF, idModeloFactura, idModeloRectificativa, envioFacturas, idPlantillaMail, traspasoFacturas, traspasoPlantilla, traspasoCodAuditoriaDef, confDeudor, ctaClientes, confIngresos, ctaIngresos, idTiposProductos, tiposProductos, idTiposServicios, tiposServicios, idEtiquetas, etiquetas, idConsultasDestinatarios, consultasDestinatarios, idContadorFacturas, idContadorFacturasRectificativas, idSerieFacturacionPrevia, serieGenerica);
	}

	@Override
	public String toString() {
		return "SerieFacturacionItem{" +
				"idSerieFacturacion='" + idSerieFacturacion + '\'' +
				", observaciones='" + observaciones + '\'' +
				", fechaBaja=" + fechaBaja +
				", abreviatura='" + abreviatura + '\'' +
				", descripcion='" + descripcion + '\'' +
				", tiposIncluidos=" + tiposIncluidos +
				", idCuentaBancaria='" + idCuentaBancaria + '\'' +
				", cuentaBancaria='" + cuentaBancaria + '\'' +
				", idSufijo='" + idSufijo + '\'' +
				", sufijo='" + sufijo + '\'' +
				", idFormaPago='" + idFormaPago + '\'' +
				", formaPago=" + formaPago +
				", generarPDF=" + generarPDF +
				", idModeloFactura='" + idModeloFactura + '\'' +
				", idModeloRectificativa='" + idModeloRectificativa + '\'' +
				", envioFacturas=" + envioFacturas +
				", idPlantillaMail='" + idPlantillaMail + '\'' +
				", traspasoFacturas=" + traspasoFacturas +
				", traspasoPlantilla='" + traspasoPlantilla + '\'' +
				", traspasoCodAuditoriaDef='" + traspasoCodAuditoriaDef + '\'' +
				", confDeudor='" + confDeudor + '\'' +
				", ctaClientes='" + ctaClientes + '\'' +
				", confIngresos='" + confIngresos + '\'' +
				", ctaIngresos='" + ctaIngresos + '\'' +
				", idTiposProductos=" + idTiposProductos +
				", tiposProductos=" + tiposProductos +
				", idTiposServicios=" + idTiposServicios +
				", tiposServicios=" + tiposServicios +
				", idEtiquetas=" + idEtiquetas +
				", etiquetas=" + etiquetas +
				", idConsultasDestinatarios=" + idConsultasDestinatarios +
				", consultasDestinatarios=" + consultasDestinatarios +
				", idContadorFacturas='" + idContadorFacturas + '\'' +
				", idContadorFacturasRectificativas='" + idContadorFacturasRectificativas + '\'' +
				", idSerieFacturacionPrevia='" + idSerieFacturacionPrevia + '\'' +
				", serieGenerica=" + serieGenerica +
				'}';
	}
}
