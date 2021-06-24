package org.itcgae.siga.DTOs.scs;

import java.util.Objects;

public class TarjetaAsistenciaItem {
	
	private String anio;
	private String numero;
	private String anioNumero;
	private String asistido;
	private String observaciones;
	private String idDelito;
	private String ejgNumero;
	private String ejgAnio;
	private String ejgAnioNumero;
	private String fchaActuacion;
	private String fchaJustificacion;
	private String lugar;
	private String numeroAsunto;
	private String nombre, sexo, apellido1, apellido2, nif, comisariaJuzgado;
	
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAnioNumero() {
		return anioNumero;
	}
	public void setAnioNumero(String anioNumero) {
		this.anioNumero = anioNumero;
	}
	public String getAsistido() {
		return asistido;
	}
	public void setAsistido(String asistido) {
		this.asistido = asistido;
	}
	public String getEjgNumero() {
		return ejgNumero;
	}
	public void setEjgNumero(String ejgNumero) {
		this.ejgNumero = ejgNumero;
	}
	public String getEjgAnio() {
		return ejgAnio;
	}
	public void setEjgAnio(String ejgAnio) {
		this.ejgAnio = ejgAnio;
	}
	public String getEjgAnioNumero() {
		return ejgAnioNumero;
	}
	public void setEjgAnioNumero(String ejgAnioNumero) {
		this.ejgAnioNumero = ejgAnioNumero;
	}
	public String getFchaActuacion() {
		return fchaActuacion;
	}
	public void setFchaActuacion(String fchaActuacion) {
		this.fchaActuacion = fchaActuacion;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getNumeroAsunto() {
		return numeroAsunto;
	}
	public void setNumeroAsunto(String numeroAsunto) {
		this.numeroAsunto = numeroAsunto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
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
	 * @return the idDelito
	 */
	public String getIdDelito() {
		return idDelito;
	}
	/**
	 * @param idDelito the idDelito to set
	 */
	public void setIdDelito(String idDelito) {
		this.idDelito = idDelito;
	}
	/**
	 * @return the fchaJustificacion
	 */
	public String getFchaJustificacion() {
		return fchaJustificacion;
	}
	/**
	 * @param fchaJustificacion the fchaJustificacion to set
	 */
	public void setFchaJustificacion(String fchaJustificacion) {
		this.fchaJustificacion = fchaJustificacion;
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
		return Objects.hash(anio, anioNumero, asistido, ejgAnio, ejgAnioNumero,
				ejgNumero, fchaActuacion, lugar, numero, numeroAsunto);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaAsistenciaItem other = (TarjetaAsistenciaItem) obj;
		return Objects.equals(anio, other.anio) && Objects.equals(anioNumero, other.anioNumero)
				&& Objects.equals(asistido, other.asistido)
				&& Objects.equals(ejgAnio, other.ejgAnio) && Objects.equals(ejgAnioNumero, other.ejgAnioNumero)
				&& Objects.equals(ejgNumero, other.ejgNumero) && Objects.equals(fchaActuacion, other.fchaActuacion)
				 && Objects.equals(lugar, other.lugar)
				&& Objects.equals(numero, other.numero) && Objects.equals(numeroAsunto, other.numeroAsunto);
	}
	
	@Override
	public String toString() {
		return "TarjetaAsistenciaItem [anio=" + anio + ", numero=" + numero + ", anioNumero=" + anioNumero
				+ ", asistido=" + asistido + ", ejgNumero="
				+ ejgNumero + ", ejgAnio=" + ejgAnio + ", ejgAnioNumero=" + ejgAnioNumero + ", fchaActuacion="
				+ fchaActuacion + ", lugar=" + lugar
				+ ", numeroAsunto=" + numeroAsunto + "]";
	}
	
	
	

}
