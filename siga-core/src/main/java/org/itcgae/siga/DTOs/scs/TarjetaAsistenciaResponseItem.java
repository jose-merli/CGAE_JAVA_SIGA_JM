package org.itcgae.siga.DTOs.scs;

import java.util.List;
import java.util.Objects;

public class TarjetaAsistenciaResponseItem {
	
	private String anio;
	private String numero;
	private String anioNumero;
	private String asistido;
	private String idDelito;
	private String observaciones;
	private String ejgNumero;
	private String ejgAnio;
	private String ejgAnioNumero;
	private List<ActuacionAsistenciaItem> actuaciones;
	private String nombre, sexo, apellido1, apellido2, nif;
	private FiltroAsistenciaItem filtro;
	private String descripcionGuardia;
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
	 * @return the fechaGuardia
	 */
	public String getFechaGuardia() {
		return fechaGuardia;
	}
	/**
	 * @param fechaGuardia the fechaGuardia to set
	 */
	public void setFechaGuardia(String fechaGuardia) {
		this.fechaGuardia = fechaGuardia;
	}
	private String numeroColegiado;
	private String nombreColegiado;
	private String fechaGuardia;
	
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
	 * @return the anioNumero
	 */
	public String getAnioNumero() {
		return anioNumero;
	}
	/**
	 * @param anioNumero the anioNumero to set
	 */
	public void setAnioNumero(String anioNumero) {
		this.anioNumero = anioNumero;
	}
	/**
	 * @return the asistido
	 */
	public String getAsistido() {
		return asistido;
	}
	/**
	 * @param asistido the asistido to set
	 */
	public void setAsistido(String asistido) {
		this.asistido = asistido;
	}
	/**
	 * @return the ejgNumero
	 */
	public String getEjgNumero() {
		return ejgNumero;
	}
	/**
	 * @param ejgNumero the ejgNumero to set
	 */
	public void setEjgNumero(String ejgNumero) {
		this.ejgNumero = ejgNumero;
	}
	/**
	 * @return the ejgAnio
	 */
	public String getEjgAnio() {
		return ejgAnio;
	}
	/**
	 * @param ejgAnio the ejgAnio to set
	 */
	public void setEjgAnio(String ejgAnio) {
		this.ejgAnio = ejgAnio;
	}
	/**
	 * @return the ejgAnioNumero
	 */
	public String getEjgAnioNumero() {
		return ejgAnioNumero;
	}
	/**
	 * @param ejgAnioNumero the ejgAnioNumero to set
	 */
	public void setEjgAnioNumero(String ejgAnioNumero) {
		this.ejgAnioNumero = ejgAnioNumero;
	}

	/**
	 * @return the actuaciones
	 */
	public List<ActuacionAsistenciaItem> getActuaciones() {
		return actuaciones;
	}
	/**
	 * @param actuaciones the actuaciones to set
	 */
	public void setActuaciones(List<ActuacionAsistenciaItem> actuaciones) {
		this.actuaciones = actuaciones;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}
	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
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
	 * @return the filtro
	 */
	public FiltroAsistenciaItem getFiltro() {
		return filtro;
	}
	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(FiltroAsistenciaItem filtro) {
		this.filtro = filtro;
	}
	@Override
	public int hashCode() {
		return Objects.hash(actuaciones, anio, anioNumero, apellido1, apellido2, asistido,
				 ejgAnio, ejgAnioNumero, ejgNumero, nif, nombre, numero, sexo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaAsistenciaResponseItem other = (TarjetaAsistenciaResponseItem) obj;
		return Objects.equals(actuaciones, other.actuaciones) && Objects.equals(anio, other.anio)
				&& Objects.equals(anioNumero, other.anioNumero) && Objects.equals(apellido1, other.apellido1)
				&& Objects.equals(apellido2, other.apellido2) && Objects.equals(asistido, other.asistido)
				&& Objects.equals(ejgAnio, other.ejgAnio) && Objects.equals(ejgAnioNumero, other.ejgAnioNumero)
				&& Objects.equals(ejgNumero, other.ejgNumero)
				&& Objects.equals(nif, other.nif) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(numero, other.numero) && Objects.equals(sexo, other.sexo);
	}
	@Override
	public String toString() {
		return "TarjetaAsistenciaResponseItem [anio=" + anio + ", numero=" + numero + ", anioNumero=" + anioNumero
				+ ", asistido=" + asistido + ", ejgNumero="
				+ ejgNumero + ", ejgAnio=" + ejgAnio + ", ejgAnioNumero=" + ejgAnioNumero + ", actuaciones=" + actuaciones + ", nombre=" + nombre + ", sexo=" + sexo
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nif=" + nif + "]";
	}
	
	

}
