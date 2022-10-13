package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DocumentacionSojItem {
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaLimite; 
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaPresentacion;
	private String registroEntrada;
	private String registroSalida;
	private String documentacion;
	private String idDocumentacion;
	private boolean nuevo;
	private String anio;
	private String numero;
	private String idtiposoj;
	private boolean nuevoRegistro;
	private String usuModificacion;
	
	
	public String getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	public String getAnio() {
		return anio;
	}

	public String getNumero() {
		return numero;
	}

	public String getIdtiposoj() {
		return idtiposoj;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setIdtiposoj(String idtiposoj) {
		this.idtiposoj = idtiposoj;
	}

	public boolean getNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isNuevoRegistro() {
		return nuevoRegistro;
	}

	public void setNuevoRegistro(boolean nuevoRegistro) {
		this.nuevoRegistro = nuevoRegistro;
	}

	public String getIdDocumentacion() {
		return idDocumentacion;
	}

	public void setIdDocumentacion(String idDocumentacion) {
		this.idDocumentacion = idDocumentacion;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}
	
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public String getRegistroEntrada() {
		return registroEntrada;
	}
	public String getRegistroSalida() {
		return registroSalida;
	}
	public String getDocumentacion() {
		return documentacion;
	}
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public void setRegistroEntrada(String registroEntrada) {
		this.registroEntrada = registroEntrada;
	}
	public void setRegistroSalida(String registroSalida) {
		this.registroSalida = registroSalida;
	}
	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}
	
	

}
