package org.itcgae.siga.DTOs.scs;

public class ConceptoPagoItem {

	private String idInstitucion;
	private String idPagosjg;
	private String idFacturacion;
	private String idConcepto;
	private String desConcepto;
	private Double importeFacturado;
	private Double importePendiente;
	private Double porcentajePagado;
	private Double porcentajeApagar;
	private Double cantidadApagar;

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdPagosjg() {
		return idPagosjg;
	}

	public void setIdPagosjg(String idPagosjg) {
		this.idPagosjg = idPagosjg;
	}

	public String getIdFacturacion() {
		return idFacturacion;
	}

	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}

	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getDesConcepto() {
		return desConcepto;
	}

	public void setDesConcepto(String desConcepto) {
		this.desConcepto = desConcepto;
	}

	public Double getImporteFacturado() {
		return importeFacturado;
	}

	public void setImporteFacturado(Double importeFacturado) {
		this.importeFacturado = importeFacturado;
	}

	public Double getImportePendiente() {
		return importePendiente;
	}

	public void setImportePendiente(Double importePendiente) {
		this.importePendiente = importePendiente;
	}

	public Double getPorcentajePagado() {
		return porcentajePagado;
	}

	public void setPorcentajePagado(Double porcentajePagado) {
		this.porcentajePagado = porcentajePagado;
	}

	public Double getPorcentajeApagar() {
		return porcentajeApagar;
	}

	public void setPorcentajeApagar(Double porcentajeApagar) {
		this.porcentajeApagar = porcentajeApagar;
	}

	public Double getCantidadApagar() {
		return cantidadApagar;
	}

	public void setCantidadApagar(Double cantidadApagar) {
		this.cantidadApagar = cantidadApagar;
	}

	@Override
	public String toString() {
		return "ConceptoPagoItem [idInstitucion=" + idInstitucion + ", idPagosjg=" + idPagosjg + ", idFacturacion="
				+ idFacturacion + ", idConcepto=" + idConcepto + ", desConcepto=" + desConcepto + ", importeFacturado="
				+ importeFacturado + ", importePendiente=" + importePendiente + ", porcentajePagado=" + porcentajePagado
				+ ", porcentajeApagar=" + porcentajeApagar + ", cantidadApagar=" + cantidadApagar + "]";
	}

}
