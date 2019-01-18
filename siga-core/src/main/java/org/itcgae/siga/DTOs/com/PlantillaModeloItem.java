package org.itcgae.siga.DTOs.com;

import java.util.Date;

public class PlantillaModeloItem {
	
	private String idPlantillaEnvios;
	private String nombrePlantilla;
	private String idTipoEnvios;
	private String tipoEnvio;
	private Date fechaBaja;
	private String porDefecto;
	private String idInstitucion;
	
	
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public String getNombrePlantilla() {
		return nombrePlantilla;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	
}
