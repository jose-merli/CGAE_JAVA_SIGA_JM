package org.itcgae.siga.DTOs.com;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnviosMasivosItem {

	private Short idInstitucion;
	private Long idEnvio;
	private String descripcion;
	private Date fechaCreacion;
	private String idPlantillaEnvios;
	private Short idEstado;
	private String idTipoEnvios;
	private String idPlantilla;
	private String nombrePlantilla;
	private Date fechaProgramada;
	private Date fechaBaja;
	private String asunto;
	private String cuerpo;
	private String estadoEnvio;
	private String tipoEnvio;
	private String idModeloComunicacion;
	private String idClaseComunicacion;
	private String modeloComunicacion;
	private String claseComunicacion;
	private String csv;
	private String destinatario;
	private int numDestinatarios;

	public Short getIdInstitucion() {
		return idInstitucion;
	}

	public Long getIdEnvio() {
		return idEnvio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}

	public Short getIdEstado() {
		return idEstado;
	}

	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}

	public String getIdPlantilla() {
		return idPlantilla;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public Date getFechaProgramada() {
		return fechaProgramada;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public String getAsunto() {
		return asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public String getEstadoEnvio() {
		return estadoEnvio;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}

	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}

	public String getModeloComunicacion() {
		return modeloComunicacion;
	}

	public String getClaseComunicacion() {
		return claseComunicacion;
	}

	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}

	public void setIdEstado(Short idEstado) {
		this.idEstado = idEstado;
	}

	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}

	public void setIdPlantilla(String idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public void setEstadoEnvio(String estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}

	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}

	public void setModeloComunicacion(String modeloComunicacion) {
		this.modeloComunicacion = modeloComunicacion;
	}

	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public int getNumDestinatarios() {
		return numDestinatarios;
	}

	public void setNumDestinatarios(int numDestinatarios) {
		this.numDestinatarios = numDestinatarios;
	}

}
