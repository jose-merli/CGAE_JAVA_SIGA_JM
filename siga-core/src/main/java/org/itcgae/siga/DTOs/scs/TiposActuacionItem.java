package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TiposActuacionItem {

	private String idinstitucion;
	private Date fechamodificacion;
	private String usumodificacion;
	private String importemaximo;
	private String importe;
	private Date fechabaja; 
	private String idtipoactuacion;
	private String idtipoasistenciacolegio;
	private String descripciontipoactuacion;
	private String descripciontipoasistencia;
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
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getIdtipoasistenciacolegio() {
		return idtipoasistenciacolegio;
	}
	public void setIdtipoasistenciacolegio(String idtipoasistenciacolegio) {
		this.idtipoasistenciacolegio = idtipoasistenciacolegio;
	}
	
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getIdtipoasistencia() {
		return idtipoasistencia;
	}
	public void setIdtipoasistencia(String idtipoasistencia) {
		this.idtipoasistencia = idtipoasistencia;
	}
	public String getDescripciontipoasistencia() {
		return descripciontipoasistencia;
	}
	public void setDescripciontipoasistencia(String descripciontipoasistencia) {
		this.descripciontipoasistencia = descripciontipoasistencia;
	}
	public String getDescripciontipoactuacion() {
		return descripciontipoactuacion;
	}
	public void setDescripciontipoactuacion(String descripciontipoactuacion) {
		this.descripciontipoactuacion = descripciontipoactuacion;
	}
	public String getIdtipoactuacion() {
		return idtipoactuacion;
	}
	public void setIdtipoactuacion(String idtipoactuacion) {
		this.idtipoactuacion = idtipoactuacion;
	}
	
}
