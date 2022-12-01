package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class BajasTemporalesItem {

	public String idinstitucion;
	public String idpersona;
	public String tipo;
	public Date fechadesde;
	public Date fechahasta;
	public Date fechaalta;
	public Date fechasolicituddesde;
	public Date fechasolicitudhasta;
	public String descripcion;
	public String validado;
	public Date fechaestado;
	public String ncolegiado;
	public String nombre;
	public String apellidos1;
	public String apellidos2;
	public String eliminado;
	public Date fechabt;
	public boolean historico;
	private String usumodificacion;
	private Date fechamodificacion;
	
	public Date getFechasolicituddesde() {
		return fechasolicituddesde;
	}
	public void setFechasolicituddesde(Date fechasolicituddesde) {
		this.fechasolicituddesde = fechasolicituddesde;
	}
	public Date getFechasolicitudhasta() {
		return fechasolicitudhasta;
	}
	public void setFechasolicitudhasta(Date fechasolicitudhasta) {
		this.fechasolicitudhasta = fechasolicitudhasta;
	}
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(String idpersona) {
		this.idpersona = idpersona;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFechadesde() {
		return fechadesde;
	}
	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}
	public Date getFechahasta() {
		return fechahasta;
	}
	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
	}
	public Date getFechaalta() {
		return fechaalta;
	}
	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValidado() {
		return validado;
	}
	public void setValidado(String validado) {
		this.validado = validado;
	}
	public Date getFechaestado() {
		return fechaestado;
	}
	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}
	public String getNcolegiado() {
		return ncolegiado;
	}
	public void setNcolegiado(String ncolegiado) {
		this.ncolegiado = ncolegiado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos1() {
		return apellidos1;
	}
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}
	public String getApellidos2() {
		return apellidos2;
	}
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	public String getEliminado() {
		return eliminado;
	}
	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}
	public Date getFechabt() {
		return fechabt;
	}
	public void setFechabt(Date fechabt) {
		this.fechabt = fechabt;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	
}
