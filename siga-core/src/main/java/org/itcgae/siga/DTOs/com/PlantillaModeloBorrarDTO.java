package org.itcgae.siga.DTOs.com;

public class PlantillaModeloBorrarDTO {
	
	private String idModelo;
	private String idPlantillaEnvios;
	private String porDefecto;
	private String idInstitucion;
	private String idTipoEnvios;
	
	
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
	}
	public String getIdModelo() {
		return idModelo;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}

}
