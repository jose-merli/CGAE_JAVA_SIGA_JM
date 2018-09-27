package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class CargaMasivaDatosCVItem {
	private String colegiadoNumero;
	private String personaNIF;
	private Date fechaInicio;
	private Date fechaFin;
	private Long creditos;
	private Date fechaVerificacion;
	private Short tipoCVCOD;
	private String subtipoCV1COD;
	private String subtipoCV2COD;
	private String personaNombre;
	private Long idPersona;
	private String tipoCVNombre;
	private Short idTipoCV;
	private String subTipoCV1Nombre;
	private Short idTipoCVSubtipo1;
	private String subtipoCV2Nombre;
	private Short idTipoCVSubtipo2;
	private String errores;
	private String descripcion;
	private Short subTipo1IdInstitucion;
	private Short subTipo2IdInstitucion;
	
	public String getColegiadoNumero() {
		return colegiadoNumero;
	}
	public void setColegiadoNumero(String colegiadoNumero) {
		this.colegiadoNumero = colegiadoNumero;
	}
	public String getPersonaNIF() {
		return personaNIF;
	}
	public void setPersonaNIF(String personaNIF) {
		this.personaNIF = personaNIF;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date c_fechaInicio) {
		this.fechaInicio = c_fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date c_fechaFin) {
		this.fechaFin = c_fechaFin;
	}
	public Long getCreditos() {
		return creditos;
	}
	public void setCreditos(Long c_creditos) {
		this.creditos = c_creditos;
	}
	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}
	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}
	public Short getTipoCVCOD() {
		return tipoCVCOD;
	}
	public void setTipoCVCOD(Short tipoCVCOD) {
		this.tipoCVCOD = tipoCVCOD;
	}
	public String getSubtipoCV1COD() {
		return subtipoCV1COD;
	}
	public void setSubtipoCV1COD(String subtipoCV1COD) {
		this.subtipoCV1COD = subtipoCV1COD;
	}
	public String getSubtipoCV2COD() {
		return subtipoCV2COD;
	}
	public void setSubtipoCV2COD(String subtipoCV2COD) {
		this.subtipoCV2COD = subtipoCV2COD;
	}
	public String getPersonaNombre() {
		return personaNombre;
	}
	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long c_idPersona) {
		this.idPersona = c_idPersona;
	}
	public String getTipoCVNombre() {
		return tipoCVNombre;
	}
	public void setTipoCVNombre(String tipoCVNombre) {
		this.tipoCVNombre = tipoCVNombre;
	}
	public Short getIdTipoCV() {
		return idTipoCV;
	}
	public void setIdTipoCV(Short c_idTipoCV) {
		this.idTipoCV = c_idTipoCV;
	}
	public String getSubTipoCV1Nombre() {
		return subTipoCV1Nombre;
	}
	public void setSubTipoCV1Nombre(String subTipoCV1Nombre) {
		this.subTipoCV1Nombre = subTipoCV1Nombre;
	}
	public Short getIdTipoCVSubtipo1() {
		return idTipoCVSubtipo1;
	}
	public void setIdTipoCVSubtipo1(Short c_idTipoCVSubtipo1) {
		this.idTipoCVSubtipo1 = c_idTipoCVSubtipo1;
	}
	public String getSubtipoCV2Nombre() {
		return subtipoCV2Nombre;
	}
	public void setSubtipoCV2Nombre(String subtipoCV2Nombre) {
		this.subtipoCV2Nombre = subtipoCV2Nombre;
	}
	public Short getIdTipoCVSubtipo2() {
		return idTipoCVSubtipo2;
	}
	public void setIdTipoCVSubtipo2(Short c_idTipoCVSubtipo2) {
		this.idTipoCVSubtipo2 = c_idTipoCVSubtipo2;
	}
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String c_descripcion) {
		this.descripcion = c_descripcion;
	}
	public Short getIdinstitucionSubt1() {
		return subTipo1IdInstitucion;
	}
	public Short getIdinstitucionSubt2() {
		return subTipo2IdInstitucion;
	}
	public void setIdinstitucionSubt1(Short subTipo1IdInstitucion) {
	   this.subTipo1IdInstitucion = subTipo1IdInstitucion;
	}
	public void setIdinstitucionSubt2(Short subTipo2IdInstitucion) {
		this.subTipo2IdInstitucion = subTipo2IdInstitucion;
	}
}
