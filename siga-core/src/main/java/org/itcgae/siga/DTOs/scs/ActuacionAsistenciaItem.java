package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class ActuacionAsistenciaItem {

	
	private String fechaActuacion;
	private String lugar;
	private String numeroAsunto;
	private String fechaJustificacion;
	private String comisariaJuzgado;
	
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
