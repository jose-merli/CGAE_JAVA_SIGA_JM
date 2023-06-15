/**
 * 
 */
package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

/**
 * @author Pablo Jarana
 *
 */
public class PreAsistenciaItem {
	
	private String nAvisoCentralita, estado, idTurno, idGuardia, fechaLlamadaDsd, fechaLlamadaHasta, idComisaria, 
	idJuzgado, numeroColegiado, nombreColegiado, centroDetencion, descripcionGuardia, descripcionEstado, fechaLlamada,
	idTipoCentroDetencion, idSolicitud, fechaRecepcion;

	/**
	 * @return the fechaRecepcion
	 */
	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the nAvisoCentralita
	 */
	public String getnAvisoCentralita() {
		return nAvisoCentralita;
	}

	/**
	 * @param nAvisoCentralita the nAvisoCentralita to set
	 */
	public void setnAvisoCentralita(String nAvisoCentralita) {
		this.nAvisoCentralita = nAvisoCentralita;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
	 * @return the idGuardia
	 */
	public String getIdGuardia() {
		return idGuardia;
	}

	/**
	 * @param idGuardia the idGuardia to set
	 */
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}

	/**
	 * @return the fechaLlamadaDsd
	 */
	public String getFechaLlamadaDsd() {
		return fechaLlamadaDsd;
	}

	/**
	 * @param fechaLlamadaDsd the fechaLlamadaDsd to set
	 */
	public void setFechaLlamadaDsd(String fechaLlamadaDsd) {
		this.fechaLlamadaDsd = fechaLlamadaDsd;
	}

	/**
	 * @return the fechaLlamadaHasta
	 */
	public String getFechaLlamadaHasta() {
		return fechaLlamadaHasta;
	}

	/**
	 * @param fechaLlamadaHasta the fechaLlamadaHasta to set
	 */
	public void setFechaLlamadaHasta(String fechaLlamadaHasta) {
		this.fechaLlamadaHasta = fechaLlamadaHasta;
	}

	/**
	 * @return the idComisaria
	 */
	public String getIdComisaria() {
		return idComisaria;
	}

	/**
	 * @param idComisaria the idComisaria to set
	 */
	public void setIdComisaria(String idComisaria) {
		this.idComisaria = idComisaria;
	}

	/**
	 * @return the idJuzgado
	 */
	public String getIdJuzgado() {
		return idJuzgado;
	}

	/**
	 * @param idJuzgado the idJuzgado to set
	 */
	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	/**
	 * @return the numeroColegiado
	 */
	public String getNumeroColegiado() {
		return numeroColegiado;
	}

	/**
	 * @param numeroColegiado the numeroColegiado to set
	 */
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}

	/**
	 * @return the nombreColegiado
	 */
	public String getNombreColegiado() {
		return nombreColegiado;
	}

	/**
	 * @param nombreColegiado the nombreColegiado to set
	 */
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}

	/**
	 * @return the centroDetencion
	 */
	public String getCentroDetencion() {
		return centroDetencion;
	}

	/**
	 * @param centroDetencion the centroDetencion to set
	 */
	public void setCentroDetencion(String centroDetencion) {
		this.centroDetencion = centroDetencion;
	}

	/**
	 * @return the descripcionGuardia
	 */
	public String getDescripcionGuardia() {
		return descripcionGuardia;
	}

	/**
	 * @param descripcionGuardia the descripcionGuardia to set
	 */
	public void setDescripcionGuardia(String descripcionGuardia) {
		this.descripcionGuardia = descripcionGuardia;
	}

	/**
	 * @return the descripcionEstado
	 */
	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	/**
	 * @param descripcionEstado the descripcionEstado to set
	 */
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	/**
	 * @return the fechaLlamada
	 */
	public String getFechaLlamada() {
		return fechaLlamada;
	}

	/**
	 * @param fechaLlamada the fechaLlamada to set
	 */
	public void setFechaLlamada(String fechaLlamada) {
		this.fechaLlamada = fechaLlamada;
	}

	/**
	 * @return the idTipoCentroDetencion
	 */
	public String getIdTipoCentroDetencion() {
		return idTipoCentroDetencion;
	}

	/**
	 * @param idTipoCentroDetencion the idTipoCentroDetencion to set
	 */
	public void setIdTipoCentroDetencion(String idTipoCentroDetencion) {
		this.idTipoCentroDetencion = idTipoCentroDetencion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(centroDetencion, descripcionEstado, descripcionGuardia, estado, fechaLlamada,
				fechaLlamadaDsd, fechaLlamadaHasta, idComisaria, idGuardia, idJuzgado, idTurno, nAvisoCentralita,
				nombreColegiado, numeroColegiado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreAsistenciaItem other = (PreAsistenciaItem) obj;
		return Objects.equals(centroDetencion, other.centroDetencion)
				&& Objects.equals(descripcionEstado, other.descripcionEstado)
				&& Objects.equals(descripcionGuardia, other.descripcionGuardia) && Objects.equals(estado, other.estado)
				&& Objects.equals(fechaLlamada, other.fechaLlamada)
				&& Objects.equals(fechaLlamadaDsd, other.fechaLlamadaDsd)
				&& Objects.equals(fechaLlamadaHasta, other.fechaLlamadaHasta)
				&& Objects.equals(idComisaria, other.idComisaria) && Objects.equals(idGuardia, other.idGuardia)
				&& Objects.equals(idJuzgado, other.idJuzgado) && Objects.equals(idTurno, other.idTurno)
				&& Objects.equals(nAvisoCentralita, other.nAvisoCentralita)
				&& Objects.equals(nombreColegiado, other.nombreColegiado)
				&& Objects.equals(numeroColegiado, other.numeroColegiado);
	}

	@Override
	public String toString() {
		return "PreAsistenciaItem [nAvisoCentralita=" + nAvisoCentralita + ", estado=" + estado + ", idTurno=" + idTurno
				+ ", idGuardia=" + idGuardia + ", fechaLlamadaDsd=" + fechaLlamadaDsd + ", fechaLlamadaHasta="
				+ fechaLlamadaHasta + ", idComisaria=" + idComisaria + ", idJuzgado=" + idJuzgado + ", numeroColegiado="
				+ numeroColegiado + ", nombreColegiado=" + nombreColegiado + ", centroDetencion=" + centroDetencion
				+ ", descripcionGuardia=" + descripcionGuardia + ", descripcionEstado=" + descripcionEstado
				+ ", fechaLlamada=" + fechaLlamada + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	
	
	
	
	

}
