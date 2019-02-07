package org.itcgae.siga.DTOs.com;

public class PlantillaModeloBorrarDTO {
	
	private String idModeloComunicacion;
	private String idPlantillaEnvios;
	private String porDefecto;
	private String idInstitucion;
	private String idTipoEnvios;
	private String idAntiguaPlantillaEnvios;
	private String idAntiguaTipoEnvios;
	
	
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public String getIdAntiguaPlantillaEnvios() {
		return idAntiguaPlantillaEnvios;
	}
	public String getIdAntiguaTipoEnvios() {
		return idAntiguaTipoEnvios;
	}
	public void setIdAntiguaPlantillaEnvios(String idAntiguaPlantillaEnvios) {
		this.idAntiguaPlantillaEnvios = idAntiguaPlantillaEnvios;
	}
	public void setIdAntiguaTipoEnvios(String idAntiguaTipoEnvios) {
		this.idAntiguaTipoEnvios = idAntiguaTipoEnvios;
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
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
}
