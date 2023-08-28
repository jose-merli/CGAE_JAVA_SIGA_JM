package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Hashtable;

import org.itcgae.siga.commons.constants.SigaConstants;

public class CargaMasivaDatosGuardiatem {
	
	private String nColegiado;
	private Short idInstitucion;
	private String nif;
	private String tipo;
	private String motivo;
	private Date fechaEfectiva;
	private Date fechaInicio;
	private Date fechaFinal;
	private String idPersona;
	private String idTurno;
	private String nombreTurno;
	private String idGuardia;
	private String nombreGuardia;
	private String grupo;
	private String orden;
	private String errores;
	private String posicion;
	
	
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}
	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
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
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

}
