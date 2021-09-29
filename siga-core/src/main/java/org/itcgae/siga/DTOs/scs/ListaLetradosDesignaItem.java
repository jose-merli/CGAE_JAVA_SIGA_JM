package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class ListaLetradosDesignaItem {
	
	private String nColegiado;
	private String apellidosNombre;
	private String motivoRenuncia;
	private String idPersona;
	private String observaciones;
	private String letradoTurno;
	private Date fechaDesignacion;
	private Date fechaSolRenuncia;
	private Date fechaEfecRenuncia;
	private String nif;
	
	
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getMotivoRenuncia() {
		return motivoRenuncia;
	}
	public void setMotivoRenuncia(String motivoRenuncia) {
		this.motivoRenuncia = motivoRenuncia;
	}
	public Date getFechaDesignacion() {
		return fechaDesignacion;
	}
	public void setFechaDesignacion(Date fechaDesignacion) {
		this.fechaDesignacion = fechaDesignacion;
	}
	public Date getFechaSolRenuncia() {
		return fechaSolRenuncia;
	}
	public void setFechaSolRenuncia(Date fechaSolRenuncia) {
		this.fechaSolRenuncia = fechaSolRenuncia;
	}
	public Date getFechaEfecRenuncia() {
		return fechaEfecRenuncia;
	}
	public void setFechaEfecRenuncia(Date fechaEfecRenuncia) {
		this.fechaEfecRenuncia = fechaEfecRenuncia;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getLetradoTurno() {
		return letradoTurno;
	}
	public void setLetradoTurno(String letradoTurno) {
		this.letradoTurno = letradoTurno;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	

}
