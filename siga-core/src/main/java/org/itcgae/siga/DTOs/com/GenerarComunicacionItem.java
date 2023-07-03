package org.itcgae.siga.DTOs.com;

import java.util.Date;
import java.util.List;

public class GenerarComunicacionItem {
	
	private List<DatosDocumentoItem> listaDocumentos;
	private List<ModelosEnvioItem> listaModelosEnvio;
	private Date fechaProgramada;
	private String descripcionDefecto;
	
	public List<DatosDocumentoItem> getListaDocumentos() {
		return listaDocumentos;
	}
	public void setListaDocumentos(List<DatosDocumentoItem> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}
	public Date getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	public List<ModelosEnvioItem> getListaModelosEnvio() {
		return listaModelosEnvio;
	}
	public void setListaModelosEnvio(List<ModelosEnvioItem> listaModelosEnvio) {
		this.listaModelosEnvio = listaModelosEnvio;
	}
	public String getDescripcionDefecto() {
		return descripcionDefecto;
	}
	public void setDescripcionDefecto(String descripcionDefecto) {
		this.descripcionDefecto = descripcionDefecto;
	}

}
