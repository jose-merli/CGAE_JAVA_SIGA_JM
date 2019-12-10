package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartidasItem {

	private String idinstitucion;
	private String descripcion;
	private String nombrepartida;
	private Date fechamodificacion;
	private String usumodificacion;
	private String idpartidapresupuestaria;
	private String importepartida;
	private Date fechabaja; 
	private boolean historico;
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombrepartida() {
		return nombrepartida;
	}
	public void setNombrepartida(String nombrepartida) {
		this.nombrepartida = nombrepartida;
	}
	public String getIdpartidapresupuestaria() {
		return idpartidapresupuestaria;
	}
	public void setIdpartidapresupuestaria(String idpartidapresupuestaria) {
		this.idpartidapresupuestaria = idpartidapresupuestaria;
	}
	public String getImportepartida() {
		return importepartida;
	}
	public void setImportepartida(String importepartida) {
		this.importepartida = importepartida;
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
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	
	
	
	
	
	
}
