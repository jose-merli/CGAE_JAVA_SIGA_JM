package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/*
 *    abreviaturaTipoDoc: string;
	  descripcionTipoDoc: string;
	  abreviaturaDoc: string;
	  descripcionDoc: string;
	  historico: boolean;
 */
public class DocumentacionEjgItem {

	private String abreviaturaTipoDoc;
	private String descripcionTipoDoc;
	private String abreviaturaDoc;
	private String descripcionDoc;
	private int idInstitucion;
	private String idTipoDocumento;
	private boolean historico;
	private Date fechabaja;
	private String codigodescripcion;
	private String codigoExt;
	private int indiceAbreviatura;
	private int indiceDesc;
	private String idDocumento;
	private String idCodigoDescripcion;


	/**
	 **/
	public DocumentacionEjgItem abreviaturaTipoDoc(String abreviaturaTipoDoc) {
		this.abreviaturaTipoDoc = abreviaturaTipoDoc;
		return this;
	}

	@JsonProperty("abreviaturaTipoDoc")
	public String getabreviaturaTipoDoc() {
		return abreviaturaTipoDoc;
	}

	public void setabreviaturaTipoDoc(String abreviaturaTipoDoc) {
		this.abreviaturaTipoDoc = abreviaturaTipoDoc;
	}
	/**
	 **/
	public DocumentacionEjgItem descripcionTipoDoc(String descripcionTipoDoc) {
		this.descripcionTipoDoc = descripcionTipoDoc;
		return this;
	}

	@JsonProperty("descripcionTipoDoc")
	public String getdescripcionTipoDoc() {
		return descripcionTipoDoc;
	}

	public void setdescripcionTipoDoc(String descripcionTipoDoc) {
		this.descripcionTipoDoc = descripcionTipoDoc;
	}
	/**
	 **/
	public DocumentacionEjgItem descripcionDoc(String descripcionDoc) {
		this.descripcionDoc = descripcionDoc;
		return this;
	}

	@JsonProperty("descripcionDoc")
	public String getdescripcionDoc() {
		return descripcionDoc;
	}

	public void setdescripcionDoc(String descripcionDoc) {
		this.descripcionDoc = descripcionDoc;
	}
	/**
	 **/
	public DocumentacionEjgItem abreviaturaDoc(String abreviaturaDoc) {
		this.abreviaturaDoc = abreviaturaDoc;
		return this;
	}

	@JsonProperty("abreviaturaDoc")
	public String getAbreviatura() {
		return abreviaturaDoc;
	}

	public void setAbreviatura(String abreviaturaDoc) {
		this.abreviaturaDoc = abreviaturaDoc;
	}
	/**
	 **/
	public DocumentacionEjgItem historico(boolean historico) {
		this.historico = historico;
		return this;
	}

	@JsonProperty("historico")
	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	/**
	 **/
	public DocumentacionEjgItem codigodescripcion(String codigodescripcion) {
		this.codigodescripcion = codigodescripcion;
		return this;
	}

	@JsonProperty("codigodescripcion")
	public String getCodigodescripcion() {
		return codigodescripcion;
	}

	public void setCodigodescripcion(String codigodescripcion) {
		this.codigodescripcion = codigodescripcion;
	}
	/**
	 **/
	public DocumentacionEjgItem codigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
		return this;
	}

	@JsonProperty("codigoExt")
	public String getCodigoExt() {
		return codigoExt;
	}

	public void setCodigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
	}
	/**
	 **/
	public DocumentacionEjgItem idTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
		return this;
	}

	@JsonProperty("idTipoDocumento")
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	/**
	 **/
	public DocumentacionEjgItem idDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
		return this;
	}

	@JsonProperty("idDocumento")
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	

	
	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public int getIndiceAbreviatura() {
		return indiceAbreviatura;
	}

	public void setIndiceAbreviatura(int indiceAbreviatura) {
		this.indiceAbreviatura = indiceAbreviatura;
	}

	public int getIndiceDesc() {
		return indiceDesc;
	}

	public void setIndiceDesc(int indiceDesc) {
		this.indiceDesc = indiceDesc;
	}

	public String getIdCodigoDescripcion() {
		return idCodigoDescripcion;
	}

	public void setIdCodigoDescripcion(String idCodigoDescripcion) {
		this.idCodigoDescripcion = idCodigoDescripcion;
	}
	

}
