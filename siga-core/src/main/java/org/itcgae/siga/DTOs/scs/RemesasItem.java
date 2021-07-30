package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

public class RemesasItem {
	
	private int idRemesa;
	private int idInstitucion;
	private int prefijo;
	private int numero; 
	private int sufijo;
	private String descripcion;
	private Date fechaGeneracion;
	private Date fechaEnvio;
	private Date fechaRecepcion;
	private String estado;
	private int incidenciasEJG;
	private int totalEJG;
	
	public int getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}
	
	public int getidInstitucion() {
		return idInstitucion;
	}

	public void setidInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public int getPrefijo() {
		return prefijo;
	}
	
	public void setPrefijo(int prefijo) {
		this.prefijo = prefijo;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getSufijo() {
		return sufijo;
	}
	
	public void setSufijo(int sufijo) {
		this.sufijo = sufijo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public int getIncidenciasEJG() {
		return incidenciasEJG;
	}
	
	public void setIncidenciasEJG(int incidenciasEJG) {
		this.incidenciasEJG = incidenciasEJG;
	}
	
	public int getTotalEJG() {
		return totalEJG;
	}
	
	public void setTotalEJG(int totalEJG) {
		this.totalEJG = totalEJG;
	}
		
}