package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolucionEJGItem {
	String idActa;
	String annioActa;
	String idAnnioActa;
	String idTiporatificacionEJG;
	String idFundamentoJuridico;
	String ratificacionDictamen;
	String idOrigencajg;
	String anioCAJG;
	String numeroCAJG;
	String idPonente;
	Date fechaPresentacionPonente;
	Date fechaResolucionCAJG;
    Date fechaRatificacion;
    Date fechaNotificacion;
	String refAuto;
	String turnadoRatificacion;
	String requiereNotificarProc;
	String notasCAJG;
	
	/**
	 **/
	public ResolucionEJGItem idActa(String idActa) {
		this.idActa = idActa;
		return this;
	}

	@JsonProperty("idActa")
	public String getIdActa() {
		return idActa;
	}
	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}
	/**
	 **/
	public ResolucionEJGItem annioActa(String annioActa) {
		this.annioActa = annioActa;
		return this;
	}

	@JsonProperty("annioActa")
	public String getAnnioActa() {
		return annioActa;
	}
	public void setAnnioActa(String annioActa) {
		this.annioActa = annioActa;
	}
	/**
	 **/
	public ResolucionEJGItem idAnnioActa(String idAnnioActa) {
		this.idAnnioActa = idAnnioActa;
		return this;
	}

	@JsonProperty("idAnnioActa")
	public String getIdAnnioActa() {
		return idAnnioActa;
	}
	public void setIdAnnioActa(String idAnnioActa) {
		this.idAnnioActa = idAnnioActa;
	}
	/**
	 **/
	public ResolucionEJGItem idTiporatificacionEJG(String idTiporatificacionEJG) {
		this.idTiporatificacionEJG = idTiporatificacionEJG;
		return this;
	}

	@JsonProperty("idTiporatificacionEJG")
	public String getIdTiporatificacionEJG() {
		return idTiporatificacionEJG;
	}
	public void setIdTiporatificacionEJG(String idTiporatificacionEJG) {
		this.idTiporatificacionEJG = idTiporatificacionEJG;
	}
	/**
	 **/
	public ResolucionEJGItem idFundamentoJuridico(String idFundamentoJuridico) {
		this.idFundamentoJuridico = idFundamentoJuridico;
		return this;
	}

	@JsonProperty("idFundamentoJuridico")
	public String getIdFundamentoJuridico() {
		return idFundamentoJuridico;
	}
	public void setIdFundamentoJuridico(String idFundamentoJuridico) {
		this.idFundamentoJuridico = idFundamentoJuridico;
	}
	/**
	 **/
	public ResolucionEJGItem ratificacionDictamen(String ratificacionDictamen) {
		this.ratificacionDictamen = ratificacionDictamen;
		return this;
	}

	@JsonProperty("ratificacionDictamen")
	public String getRatificacionDictamen() {
		return ratificacionDictamen;
	}
	public void setRatificacionDictamen(String ratificacionDictamen) {
		this.ratificacionDictamen = ratificacionDictamen;
	}
	/**
	 **/
	public ResolucionEJGItem idOrigencajg(String idOrigencajg) {
		this.idOrigencajg = idOrigencajg;
		return this;
	}

	@JsonProperty("idOrigencajg")
	public String getIdOrigencajg() {
		return idOrigencajg;
	}
	public void setIdOrigencajg(String idOrigencajg) {
		this.idOrigencajg = idOrigencajg;
	}
	/**
	 **/
	public ResolucionEJGItem anioCAJG(String anioCAJG) {
		this.anioCAJG = anioCAJG;
		return this;
	}

	@JsonProperty("anioCAJG")
	public String getAnioCAJG() {
		return anioCAJG;
	}
	public void setAnioCAJG(String anioCAJG) {
		this.anioCAJG = anioCAJG;
	}
	/**
	 **/
	public ResolucionEJGItem numeroCAJG(String numeroCAJG) {
		this.numeroCAJG = numeroCAJG;
		return this;
	}

	@JsonProperty("numeroCAJG")
	public String getNumeroCAJG() {
		return numeroCAJG;
	}
	public void setNumeroCAJG(String numeroCAJG) {
		this.numeroCAJG = numeroCAJG;
	}
	/**
	 **/
	public ResolucionEJGItem idPonente(String idPonente) {
		this.idPonente = idPonente;
		return this;
	}

	@JsonProperty("idPonente")
	public String getIdPonente() {
		return idPonente;
	}
	public void setIdPonente(String idPonente) {
		this.idPonente = idPonente;
	}
	/**
	 **/
	public ResolucionEJGItem fechaPresentacionPonente(Date fechaPresentacionPonente) {
		this.fechaPresentacionPonente = fechaPresentacionPonente;
		return this;
	}

	@JsonProperty("fechaPresentacionPonente")
	public Date getFechaPresentacionPonente() {
		return fechaPresentacionPonente;
	}
	public void setFechaPresentacionPonente(Date fechaPresentacionPonente) {
		this.fechaPresentacionPonente = fechaPresentacionPonente;
	}
	/**
	 **/
	public ResolucionEJGItem fechaResolucionCAJG(Date fechaResolucionCAJG) {
		this.fechaResolucionCAJG = fechaResolucionCAJG;
		return this;
	}

	@JsonProperty("fechaResolucionCAJG")
	public Date getFechaResolucionCAJG() {
		return fechaResolucionCAJG;
	}
	public void setFechaResolucionCAJG(Date fechaResolucionCAJG) {
		this.fechaResolucionCAJG = fechaResolucionCAJG;
	}
	/**
	 **/
	public ResolucionEJGItem fechaRatificacion(Date fechaRatificacion) {
		this.fechaRatificacion = fechaRatificacion;
		return this;
	}

	@JsonProperty("fechaRatificacion")
	public Date getFechaRatificacion() {
		return fechaRatificacion;
	}
	public void setFechaRatificacion(Date fechaRatificacion) {
		this.fechaRatificacion = fechaRatificacion;
	}
	/**
	 **/
	public ResolucionEJGItem fechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
		return this;
	}

	@JsonProperty("fechaNotificacion")
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	/**
	 **/
	public ResolucionEJGItem refAuto(String refAuto) {
		this.refAuto = refAuto;
		return this;
	}

	@JsonProperty("refAuto")
	public String getRefAuto() {
		return refAuto;
	}
	public void setRefAuto(String refAuto) {
		this.refAuto = refAuto;
	}
	/**
	 **/
	public ResolucionEJGItem turnadoRatificacion(String turnadoRatificacion) {
		this.turnadoRatificacion = turnadoRatificacion;
		return this;
	}

	@JsonProperty("turnadoRatificacion")
	public String getTurnadoRatificacion() {
		return turnadoRatificacion;
	}
	public void setTurnadoRatificacion(String turnadoRatificacion) {
		this.turnadoRatificacion = turnadoRatificacion;
	}
	/**
	 **/
	public ResolucionEJGItem requiereNotificarProc(String requiereNotificarProc) {
		this.requiereNotificarProc = requiereNotificarProc;
		return this;
	}

	@JsonProperty("requiereNotificarProc")
	public String getRequiereNotificarProc() {
		return requiereNotificarProc;
	}
	public void setRequiereNotificarProc(String requiereNotificarProc) {
		this.requiereNotificarProc = requiereNotificarProc;
	}
	/**
	 **/
	public ResolucionEJGItem notasCAJG(String notasCAJG) {
		this.notasCAJG = notasCAJG;
		return this;
	}

	@JsonProperty("notasCAJG")
	public String getNotasCAJG() {
		return notasCAJG;
	}
	public void setNotasCAJG(String notasCAJG) {
		this.notasCAJG = notasCAJG;
	}
	
	
}
