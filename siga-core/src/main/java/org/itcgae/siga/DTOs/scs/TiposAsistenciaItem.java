package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TiposAsistenciaItem {

	private String idinstitucion;
	private Date fechamodificacion;
	private String usumodificacion;
	private String importemaximo;
	private String importe;
	private Date fechaBaja; 
	private String idtipoasistenciacolegio;
	private String visiblemovil;
	private String pordefecto;
	private String tipoasistencia;
	private String tiposguardia;
	private String idtiposguardia;
	private String idtipoasistencia;
	private boolean historico;
	
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public String getImportemaximo() {
		return importemaximo;
	}
	public void setImportemaximo(String importemaximo) {
		this.importemaximo = importemaximo;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public Date getFechabaja() {
		return fechaBaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechaBaja = fechabaja;
	}
	public String getIdtipoasistenciacolegio() {
		return idtipoasistenciacolegio;
	}
	public void setIdtipoasistenciacolegio(String idtipoasistenciacolegio) {
		this.idtipoasistenciacolegio = idtipoasistenciacolegio;
	}
	public String getVisiblemovil() {
		return visiblemovil;
	}
	public void setVisiblemovil(String visiblemovil) {
		this.visiblemovil = visiblemovil;
	}
	public String getPordefecto() {
		return pordefecto;
	}
	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getTiposguardia() {
		return tiposguardia;
	}
	public void setTiposguardia(String tiposguardia) {
		this.tiposguardia = tiposguardia;
	}
	public String getIdtiposguardia() {
		return idtiposguardia;
	}
	public void setIdtiposguardias(String idtiposguardia) {
		this.idtiposguardia = idtiposguardia;
	}
	public String getTipoasistencia() {
		return tipoasistencia;
	}
	public void setTipoasistencia(String tipoasistencia) {
		this.tipoasistencia = tipoasistencia;
	}
	public String getIdtipoasistencia() {
		return idtipoasistencia;
	}
	public void setIdtipoasistencia(String idtipoasistencia) {
		this.idtipoasistencia = idtipoasistencia;
	}

	

}
