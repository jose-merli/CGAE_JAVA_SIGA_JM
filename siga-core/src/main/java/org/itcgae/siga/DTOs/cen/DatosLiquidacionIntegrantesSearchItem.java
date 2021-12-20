package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

public class DatosLiquidacionIntegrantesSearchItem {

	
	private String idPersona;
	private String idInstitucion;
	private String idComponente;
	private String idHistorico;
	private String sociedad;
	private Date fechaInicio;
	private Date fechaFin;
	private String observaciones;
	private Date fechaModificacion;
	private int usuModificacion;
	private boolean anterior;
	
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdComponente() {
		return idComponente;
	}
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}
	public String getIdHistorico() {
		return idHistorico;
	}
	public void setIdHistorico(String idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public boolean isAnterior() {
		return anterior;
	}
	public void setAnterior(boolean anterior) {
		this.anterior = anterior;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public int getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(int usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	
	
}
