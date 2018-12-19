package org.itcgae.siga.DTOs.com;

import java.util.Date;



public class EnviosMasivosItem {
	
	private Short idInstitucion;
	private Long idEnvio;
	private String descripcion;
	private Date fechaCreacion;
	private Short idPlantillasEnvio;
	private Short idEstado;
	private Short idTipoEnvio;
	private Short idPlantilla;
	private String nombrePlantilla;
	private Date fechaProgramada;
	private Date fechaBaja;
	private String asunto;
	private String cuerpo;
	private String estadoEnvio;
	private String tipoEnvio;
	private String idModeloComunicacion;
	private String idClaseComunicacion;
	
	
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Long getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Short getIdPlantillasEnvio() {
		return idPlantillasEnvio;
	}
	public void setIdPlantillasEnvio(Short idPlantillasEnvio) {
		this.idPlantillasEnvio = idPlantillasEnvio;
	}
	public Short getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Short idEstado) {
		this.idEstado = idEstado;
	}
	public Short getIdTipoEnvio() {
		return idTipoEnvio;
	}
	public void setIdTipoEnvio(Short idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	public Short getIdPlantilla() {
		return idPlantilla;
	}
	public void setIdPlantilla(Short idPlantilla) {
		this.idPlantilla = idPlantilla;
	}
	public Date getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getEstadoEnvio() {
		return estadoEnvio;
	}
	public void setEstadoEnvio(String estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getNombrePlantilla() {
		return nombrePlantilla;
	}
	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	
}