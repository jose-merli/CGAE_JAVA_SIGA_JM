package org.itcgae.siga.DTOs.scs;

import java.util.List;

public class DatosCalendarioProgramadoItem {
	
	private String idInstitucion;
	private String turno;
	private String guardia;
	private String idGuardia;
	private String idTurno;
	private String observaciones;
	private String fechaDesde;
	private String fechaHasta;
	private String fechaModificacion;
	private String fechaProgramacion;
	private String estado;
	private String generado;
	private String numGuardias;
	private String idCalG;
	private String listaGuardias;
	private String idCalendarioProgramado;
	private Boolean facturado;
	private Boolean asistenciasAsociadas;
	private String idCalendarioGuardia;
	List<GuardiaCalendarioItem> guardias;
	private String nombreLogProgramacion;
	private int contadorGenerados;
	
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getGenerado() {
		return generado;
	}
	public void setGenerado(String generado) {
		this.generado = generado;
	}
	public String getTurno() {
		return turno;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNumGuardias() {
		return numGuardias;
	}
	public void setNumGuardias(String numGuardias) {
		this.numGuardias = numGuardias;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getGuardia() {
		return guardia;
	}
	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	public String getIdCalG() {
		return idCalG;
	}
	public void setIdCalG(String idCalG) {
		this.idCalG = idCalG;
	}
	public String getListaGuardias() {
		return listaGuardias;
	}
	public void setListaGuardias(String listaGuardias) {
		this.listaGuardias = listaGuardias;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdCalendarioProgramado() {
		return idCalendarioProgramado;
	}
	public void setIdCalendarioProgramado(String idCalendarioProgramado) {
		this.idCalendarioProgramado = idCalendarioProgramado;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Boolean getFacturado() {
		return facturado;
	}
	public void setFacturado(Boolean facturado) {
		this.facturado = facturado;
	}
	public Boolean getAsistenciasAsociadas() {
		return asistenciasAsociadas;
	}
	public void setAsistenciasAsociadas(Boolean asistenciasAsociadas) {
		this.asistenciasAsociadas = asistenciasAsociadas;
	}
	public String getIdCalendarioGuardia() {
		return idCalendarioGuardia;
	}
	public void setIdCalendarioGuardia(String idCalendarioGuardia) {
		this.idCalendarioGuardia = idCalendarioGuardia;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public List<GuardiaCalendarioItem> getGuardias() {
		return guardias;
	}
	public void setGuardias(List<GuardiaCalendarioItem> guardias) {
		this.guardias = guardias;
	}
	public String getNombreLogProgramacion() {
		return nombreLogProgramacion;
	}
	public void setNombreLogProgramacion(String nombreLogProgramacion) {
		this.nombreLogProgramacion = nombreLogProgramacion;
	}
	public int getContadorGenerados() {
		return contadorGenerados;
	}
	public void setContadorGenerados(int contadorGenerados) {
		this.contadorGenerados = contadorGenerados;
	}
}
