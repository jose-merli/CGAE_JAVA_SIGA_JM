package org.itcgae.siga.DTOs.scs;

public class ActuacionDesignaItem {

	private String fechaActuacion;
	private String numero;
	private String numeroAsunto;
	private String idTurno;
	private String anio;
	private boolean facturado;
	private String modulo;
	private String acreditacion;
	private String fechaJustificacion;
	private boolean validada;
	private boolean anulada;
	private String facturacion;

	/**
	 * @return the fechaActuacion
	 */
	public String getFechaActuacion() {
		return fechaActuacion;
	}

	/**
	 * @param fechaActuacion the fechaActuacion to set
	 */
	public void setFechaActuacion(String fechaActuacion) {
		this.fechaActuacion = fechaActuacion;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the numeroAsunto
	 */
	public String getNumeroAsunto() {
		return numeroAsunto;
	}

	/**
	 * @param numeroAsunto the numeroAsunto to set
	 */
	public void setNumeroAsunto(String numeroAsunto) {
		this.numeroAsunto = numeroAsunto;
	}

	/**
	 * @return the idTurno
	 */
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the facturado
	 */
	public boolean isFacturado() {
		return facturado;
	}

	/**
	 * @param facturado the facturado to set
	 */
	public void setFacturado(boolean facturado) {
		this.facturado = facturado;
	}

	/**
	 * @return the modulo
	 */
	public String getModulo() {
		return modulo;
	}

	/**
	 * @param modulo the modulo to set
	 */
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	/**
	 * @return the acreditacion
	 */
	public String getAcreditacion() {
		return acreditacion;
	}

	/**
	 * @param acreditacion the acreditacion to set
	 */
	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
	}

	/**
	 * @return the fechaJustificacion
	 */
	public String getFechaJustificacion() {
		return fechaJustificacion;
	}

	/**
	 * @param fechaJustificacion the fechaJustificacion to set
	 */
	public void setFechaJustificacion(String fechaJustificacion) {
		this.fechaJustificacion = fechaJustificacion;
	}

	/**
	 * @return the validada
	 */
	public boolean isValidada() {
		return validada;
	}

	/**
	 * @param validada the validada to set
	 */
	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	/**
	 * @return the anulada
	 */
	public boolean isAnulada() {
		return anulada;
	}

	/**
	 * @param anulada the anulada to set
	 */
	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}

	/**
	 * @return the facturacion
	 */
	public String getFacturacion() {
		return facturacion;
	}

	/**
	 * @param facturacion the facturacion to set
	 */
	public void setFacturacion(String facturacion) {
		this.facturacion = facturacion;
	}

	@Override
	public String toString() {
		return "ActuacionDesignaItem [fechaActuacion=" + fechaActuacion + ", numero=" + numero + ", numeroAsunto="
				+ numeroAsunto + ", idTurno=" + idTurno + ", anio=" + anio + ", facturado=" + facturado + ", modulo="
				+ modulo + ", acreditacion=" + acreditacion + ", fechaJustificacion=" + fechaJustificacion
				+ ", validada=" + validada + ", anulada=" + anulada + ", facturacion=" + facturacion + "]";
	}

}
