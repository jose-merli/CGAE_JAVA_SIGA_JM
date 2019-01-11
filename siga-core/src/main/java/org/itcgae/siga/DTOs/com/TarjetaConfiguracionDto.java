package org.itcgae.siga.DTOs.com;

public class TarjetaConfiguracionDto {
	
	private String idEnvio;
	private String idPlantillasEnvio;
	private String idTipoEnvios;
	private String asunto;
	private String cuerpo;
	private String descripcion;
	private String nombre;
	
	

	public String getIdPlantillasEnvio() {
		return idPlantillasEnvio;
	}
	public void setIdPlantillasEnvio(String idPlantillasEnvio) {
		this.idPlantillasEnvio = idPlantillasEnvio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
}
