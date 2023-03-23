package org.itcgae.siga.DTOs.scs;

public class DocumentoActDesignaItem {

	private String idDocumentacionasi;
	private String idTipoDocumento;
	private String nombreTipoDocumento;
	private String idFichero;
	private String idInstitucion;
	private String usuModificacion;
	private String fechaModificacion;
	private String fechaEntrada;
	private String anio;
	private String numero;
	private String idActuacion;
	private String observaciones;
	private String nombreFichero;
	private String asociado;
	private String extension;
	private String idPersona;
	private String idTurno;

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
	 * @return the idDocumentacionasi
	 */
	public String getIdDocumentacionasi() {
		return idDocumentacionasi;
	}

	/**
	 * @param idDocumentacionasi the idDocumentacionasi to set
	 */
	public void setIdDocumentacionasi(String idDocumentacionasi) {
		this.idDocumentacionasi = idDocumentacionasi;
	}

	/**
	 * @return the idTipoDocumento
	 */
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * @return the nombreTipoDocumento
	 */
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	/**
	 * @param nombreTipoDocumento the nombreTipoDocumento to set
	 */
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	/**
	 * @return the idFichero
	 */
	public String getIdFichero() {
		return idFichero;
	}

	/**
	 * @param idFichero the idFichero to set
	 */
	public void setIdFichero(String idFichero) {
		this.idFichero = idFichero;
	}

	/**
	 * @return the idInstitucion
	 */
	public String getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return the usuModificacion
	 */
	public String getUsuModificacion() {
		return usuModificacion;
	}

	/**
	 * @param usuModificacion the usuModificacion to set
	 */
	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	/**
	 * @return the fechaModificacion
	 */
	public String getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the fechaEntrada
	 */
	public String getFechaEntrada() {
		return fechaEntrada;
	}

	/**
	 * @param fechaEntrada the fechaEntrada to set
	 */
	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
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
	 * @return the idActuacion
	 */
	public String getIdActuacion() {
		return idActuacion;
	}

	/**
	 * @param idActuacion the idActuacion to set
	 */
	public void setIdActuacion(String idActuacion) {
		this.idActuacion = idActuacion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * @param nombreFichero the nombreFichero to set
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * @return the asociado
	 */
	public String getAsociado() {
		return asociado;
	}

	/**
	 * @param asociado the asociado to set
	 */
	public void setAsociado(String asociado) {
		this.asociado = asociado;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the idPersona
	 */
	public String getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@Override
	public String toString() {
		return "DocumentoActDesignaItem [idDocumentacionasi=" + idDocumentacionasi + ", idTipoDocumento="
				+ idTipoDocumento + ", nombreTipoDocumento=" + nombreTipoDocumento + ", idFichero=" + idFichero
				+ ", idInstitucion=" + idInstitucion + ", usuModificacion=" + usuModificacion + ", fechaModificacion="
				+ fechaModificacion + ", fechaEntrada=" + fechaEntrada + ", anio=" + anio + ", numero=" + numero
				+ ", idActuacion=" + idActuacion + ", observaciones=" + observaciones + ", nombreFichero="
				+ nombreFichero + ", asociado=" + asociado + ", extension=" + extension + ", idPersona=" + idPersona
				+ "]";
	}

}
