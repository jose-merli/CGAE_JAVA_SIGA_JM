package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class ActuacionAsistenciaItem {

	
	private String fechaActuacion;
	private String lugar;
	private String numeroAsunto;
	private String fechaJustificacion;
	private String comisariaJuzgado;
	private String idActuacion;
	private String tipoActuacion;
	private String tipoActuacionDesc;
	private String validada;
	private String anulada;
	private String facturada;
	private String idFacturacion;
	private String facturacionDesc;
	private String costeDesc;
	private String idCoste;
	private String estado;
	private HistoricoActuacionAsistenciaItem ultimaModificacion;
	private String numDocumentos;

	public String getNumDocumentos() {
		return numDocumentos;
	}

	public void setNumDocumentos(String numDocumentos) {
		this.numDocumentos = numDocumentos;
	}

	public HistoricoActuacionAsistenciaItem getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(HistoricoActuacionAsistenciaItem ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdCoste() {
		return idCoste;
	}

	public void setIdCoste(String idCoste) {
		this.idCoste = idCoste;
	}

	public String getIdActuacion() {
		return idActuacion;
	}

	public void setIdActuacion(String idActuacion) {
		this.idActuacion = idActuacion;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getTipoActuacionDesc() {
		return tipoActuacionDesc;
	}

	public void setTipoActuacionDesc(String tipoActuacionDesc) {
		this.tipoActuacionDesc = tipoActuacionDesc;
	}

	public String getValidada() {
		return validada;
	}

	public void setValidada(String validada) {
		this.validada = validada;
	}

	public String getAnulada() {
		return anulada;
	}

	public void setAnulada(String anulada) {
		this.anulada = anulada;
	}

	public String getFacturada() {
		return facturada;
	}

	public void setFacturada(String facturada) {
		this.facturada = facturada;
	}

	public String getIdFacturacion() {
		return idFacturacion;
	}

	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}

	public String getFacturacionDesc() {
		return facturacionDesc;
	}

	public void setFacturacionDesc(String facturacionDesc) {
		this.facturacionDesc = facturacionDesc;
	}

	public String getCosteDesc() {
		return costeDesc;
	}

	public void setCosteDesc(String costeDesc) {
		this.costeDesc = costeDesc;
	}

	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getFechaActuacion() {
		return fechaActuacion;
	}
	public void setFechaActuacion(String fechaActuacion) {
		this.fechaActuacion = fechaActuacion;
	}
	public String getNumeroAsunto() {
		return numeroAsunto;
	}
	public void setNumeroAsunto(String numeroAsunto) {
		this.numeroAsunto = numeroAsunto;
	}
	/**
	 * @return the fchaJustificacion
	 */
	public String getFechaJustificacion() {
		return fechaJustificacion;
	}
	/**
	 * @param fchaJustificacion the fchaJustificacion to set
	 */
	public void setFechaJustificacion(String fchaJustificacion) {
		this.fechaJustificacion = fchaJustificacion;
	}
	/**
	 * @return the comisariaJuzgado
	 */
	public String getComisariaJuzgado() {
		return comisariaJuzgado;
	}
	/**
	 * @param comisariaJuzgado the comisariaJuzgado to set
	 */
	public void setComisariaJuzgado(String comisariaJuzgado) {
		this.comisariaJuzgado = comisariaJuzgado;
	}
	@Override
	public int hashCode() {
		return Objects.hash(fechaActuacion, lugar, numeroAsunto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActuacionAsistenciaItem other = (ActuacionAsistenciaItem) obj;
		return Objects.equals(fechaActuacion, other.fechaActuacion) && Objects.equals(lugar, other.lugar)
				&& Objects.equals(numeroAsunto, other.numeroAsunto);
	}
	@Override
	public String toString() {
		return "ActuacionAsistenciaItem [lugar=" + lugar + ", fechaActuacion=" + fechaActuacion + ", numeroAsunto="
				+ numeroAsunto + "]";
	}
	
	
	
}
