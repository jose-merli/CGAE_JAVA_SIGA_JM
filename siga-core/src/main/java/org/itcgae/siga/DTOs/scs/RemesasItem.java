package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

public class RemesasItem {
	
	private int idRemesa;
	private int idInstitucion;
	private String prefijo;
	private String numero; 
	private String sufijo;
	private String nRegistro;
	private String descripcion;
	private Date fechaGeneracion;
	private Date fechaEnvio;
	private Date fechaRecepcion;
	private String estado;
	private Integer idEstado;
	private int incidenciasEJG;
	private int totalEJG;
	private String incidencias;
	private String comboIncidencia;
	private boolean informacionEconomica;
	
	public int getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}

	public String getPrefijo() {
		return prefijo;
	}
	
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getSufijo() {
		return sufijo;
	}
	
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

	public String getnRegistro() {
		return nRegistro;
	}

	public void setnRegistro(String nRegistro) {
		this.nRegistro = nRegistro;
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

	public String getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(String incidencias) {
		this.incidencias = incidencias;
	}

	public String getComboIncidencia() {
		return comboIncidencia;
	}

	public void setComboIncidencia(String comboIncidencia) {
		this.comboIncidencia = comboIncidencia;
	}

	public boolean isInformacionEconomica() {
		return informacionEconomica;
	}

	public void setInformacionEconomica(boolean informacionEconomica) {
		this.informacionEconomica = informacionEconomica;
	}

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	
	
	
}