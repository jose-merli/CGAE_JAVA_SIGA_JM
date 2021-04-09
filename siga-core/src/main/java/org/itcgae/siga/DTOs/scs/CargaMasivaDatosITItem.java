package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class CargaMasivaDatosITItem {
	
	private String nombreTurno;
	private String nombreGuardia;
	private String nColegiado;
	private Date fechaEfectiva;
	private String tipo;
	private String grupo;
	private String orden;
	private Short idInstitucion;
	private String idTurno;
	private String idGuardia;
	private String idPersona;
	private String errores;
	
	
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getNombreTurno() {
		return nombreTurno;
	}
	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}
	public String getNombreGuardia() {
		return nombreGuardia;
	}
	public void setNombreGuardia(String nombreGuardia) {
		this.nombreGuardia = nombreGuardia;
	}	
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}
	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}
