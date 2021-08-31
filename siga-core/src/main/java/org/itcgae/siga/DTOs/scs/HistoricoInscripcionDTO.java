package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import org.itcgae.siga.DTOs.gen.Error;

public class HistoricoInscripcionDTO {
	private Date fecha;
	private String accion;
	private String observacion;
	private Error error = null;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
}
