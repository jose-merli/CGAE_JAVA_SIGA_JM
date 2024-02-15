package org.itcgae.siga.DTOs.scs;

import java.util.List;
import java.util.Objects;

public class TarjetaAsistenciaItem2 {
	
	private String anio;
	private String numero;
	private String anioNumero;
	private String fechaAsistencia;
	private String fechaEstado;
	private String fechaCierre;
	private String fechaSolicitud;
	private String idTipoAsistenciaColegio;
	private String idLetradoGuardia;
	private String idSolicitudCentralita;
	private String estado;
	private String idGuardia;
	private String idTurno;
	private String asistido;
	private String observaciones;
	private List<String> idDelito;
	private String ejgNumero;
	private String ejgAnio;
	private String ejgAnioNumero;
	private String idTipoEjg;
	private String fchaActuacion;
	private String fchaJustificacion;
	private String lugar;
	private String numeroAsunto;
	private String nombre, sexo, apellido1, apellido2, nif, comisariaJuzgado;
	private String fechaNacimiento;

	public String getIdTipoEjg() {
		return idTipoEjg;
	}

	public void setIdTipoEjg(String idTipoEjg) {
		this.idTipoEjg = idTipoEjg;
	}

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


	public List<String> getIdDelito() {
		return idDelito;
	}

	public void setIdDelito(List<String> idDelito) {
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
	/**
	 * @return the fechaAsistencia
	 */
	public String getFechaAsistencia() {
		return fechaAsistencia;
	}
	/**
	 * @param fechaAsistencia the fechaAsistencia to set
	 */
	public void setFechaAsistencia(String fechaAsistencia) {
		this.fechaAsistencia = fechaAsistencia;
	}
	/**
	 * @return the fechaEstado
	 */
	public String getFechaEstado() {
		return fechaEstado;
	}
	/**
	 * @param fechaEstado the fechaEstado to set
	 */
	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	/**
	 * @return the fechaCierre
	 */
	public String getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	/**
	 * @return the idTipoAsistenciaColegio
	 */
	public String getIdTipoAsistenciaColegio() {
		return idTipoAsistenciaColegio;
	}
	/**
	 * @param idTipoAsistenciaColegio the idTipoAsistenciaColegio to set
	 */
	public void setIdTipoAsistenciaColegio(String idTipoAsistenciaColegio) {
		this.idTipoAsistenciaColegio = idTipoAsistenciaColegio;
	}
	/**
	 * @return the idLetradoGuardia
	 */
	public String getIdLetradoGuardia() {
		return idLetradoGuardia;
	}
	/**
	 * @param idLetradoGuardia the idLetradoGuardia to set
	 */
	public void setIdLetradoGuardia(String idLetradoGuardia) {
		this.idLetradoGuardia = idLetradoGuardia;
	}
	/**
	 * @return the idSolicitudCentralita
	 */
	public String getIdSolicitudCentralita() {
		return idSolicitudCentralita;
	}
	/**
	 * @param idSolicitudCentralita the idSolicitudCentralita to set
	 */
	public void setIdSolicitudCentralita(String idSolicitudCentralita) {
		this.idSolicitudCentralita = idSolicitudCentralita;
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
	
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
		TarjetaAsistenciaItem2 other = (TarjetaAsistenciaItem2) obj;
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
