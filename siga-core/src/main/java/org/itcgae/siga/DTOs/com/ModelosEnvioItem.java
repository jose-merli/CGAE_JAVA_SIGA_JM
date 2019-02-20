package org.itcgae.siga.DTOs.com;

import java.util.List;

public class ModelosEnvioItem {
	
	private List<List<ConsultaEnvioItem>> listaConsultas;	
	private Integer idPlantillaEnvio;	
	private Short idTipoEnvio;	
	
	public List<List<ConsultaEnvioItem>> getListaConsultas() {
		return listaConsultas;
	}
	public void setListaConsultas(List<List<ConsultaEnvioItem>> listaConsultas) {
		this.listaConsultas = listaConsultas;
	}
	public Integer getIdPlantillaEnvio() {
		return idPlantillaEnvio;
	}
	public void setIdPlantillaEnvio(Integer idPlantillaEnvio) {
		this.idPlantillaEnvio = idPlantillaEnvio;
	}
	public Short getIdTipoEnvio() {
		return idTipoEnvio;
	}
	public void setIdTipoEnvio(Short idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	
}
