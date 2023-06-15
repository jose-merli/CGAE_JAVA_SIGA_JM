package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolucionEJGItem {
	Long idActa;
	Short annioActa;
	String idAnnioActa;
	Short idTiporatificacionEJG;
	Short idFundamentoJuridico;
	String ratificacionDictamen;
	Short idOrigencajg;
	Short anioCAJG;
	String numeroCAJG;
	Integer idPonente;
	String tipoResolucionCAJG;
	Date fechaPresentacionPonente;
	Date fechaResolucionCAJG;
    Date fechaRatificacion;
    Date fechaNotificacion;
	String refAuto;
	String turnadoRatificacion;
	String requiereNotificarProc;
	String notasCAJG;
	Short anio;
	Long numero;
	Short idInstitucion;
	Short idTipoEJG;
	String docResolucion;
	
	/**
	 **/
	public ResolucionEJGItem idActa(Long idActa) {
		this.idActa = idActa;
		return this;
	}

	@JsonProperty("idActa")
	public Long getIdActa() {
		return idActa;
	}
	public void setIdActa(Long idActa) {
		this.idActa = idActa;
	}
	/**
	 **/
	public ResolucionEJGItem annioActa(Short annioActa) {
		this.annioActa = annioActa;
		return this;
	}

	@JsonProperty("annioActa")
	public Short getAnnioActa() {
		return annioActa;
	}
	public void setAnnioActa(Short annioActa) {
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
	public ResolucionEJGItem idTiporatificacionEJG(Short idTiporatificacionEJG) {
		this.idTiporatificacionEJG = idTiporatificacionEJG;
		return this;
	}

	@JsonProperty("idTiporatificacionEJG")
	public Short getIdTiporatificacionEJG() {
		return idTiporatificacionEJG;
	}
	public void setIdTiporatificacionEJG(Short idTiporatificacionEJG) {
		this.idTiporatificacionEJG = idTiporatificacionEJG;
	}
	/**
	 **/
	public ResolucionEJGItem idFundamentoJuridico(Short idFundamentoJuridico) {
		this.idFundamentoJuridico = idFundamentoJuridico;
		return this;
	}

	@JsonProperty("idFundamentoJuridico")
	public Short getIdFundamentoJuridico() {
		return idFundamentoJuridico;
	}
	public void setIdFundamentoJuridico(Short idFundamentoJuridico) {
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
	public ResolucionEJGItem idOrigencajg(Short idOrigencajg) {
		this.idOrigencajg = idOrigencajg;
		return this;
	}

	@JsonProperty("idOrigencajg")
	public Short getIdOrigencajg() {
		return idOrigencajg;
	}
	public void setIdOrigencajg(Short idOrigencajg) {
		this.idOrigencajg = idOrigencajg;
	}
	/**
	 **/
	public ResolucionEJGItem anioCAJG(Short anioCAJG) {
		this.anioCAJG = anioCAJG;
		return this;
	}

	@JsonProperty("anioCAJG")
	public Short getAnioCAJG() {
		return anioCAJG;
	}
	public void setAnioCAJG(Short anioCAJG) {
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
	public ResolucionEJGItem idPonente(Integer idPonente) {
		this.idPonente = idPonente;
		return this;
	}

	@JsonProperty("idPonente")
	public Integer getIdPonente() {
		return idPonente;
	}
	public void setIdPonente(Integer idPonente) {
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
	@JsonProperty("tipoResolucionCAJG")
	public String getTipoResolucionCAJG() {
		return tipoResolucionCAJG;
	}

	public void setTipoResolucionCAJG(String tipoResolucionCAJG) {
		this.tipoResolucionCAJG = tipoResolucionCAJG;
	}
	
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

	@JsonProperty("anio")
	public Short getAnio() {
		return anio;
	}

	public void setAnio(Short anio) {
		this.anio = anio;
	}

	@JsonProperty("numero")
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	@JsonProperty("idInstitucion")
	public Short getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("idTipoEJG")
	public Short getIdTipoEJG() {
		return idTipoEJG;
	}

	public void setIdTipoEJG(Short idTipoEJG) {
		this.idTipoEJG = idTipoEJG;
	}
	
	@JsonProperty("docResolucion")
	public String getDocResolucion() {
		return docResolucion;
	}

	public void setDocResolucion(String docResolucion) {
		this.docResolucion = docResolucion;
	}
}
