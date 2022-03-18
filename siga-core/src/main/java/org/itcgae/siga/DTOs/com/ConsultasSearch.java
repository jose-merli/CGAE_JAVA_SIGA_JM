package org.itcgae.siga.DTOs.com;



import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultasSearch {

	private String nombre;
	private String descripcion;
	private String modulo;
	private String generica;
	private String objetivo;
	private String claseComunicacion;
	private String idModulo;
	private String idObjetivo;
	private String idClaseComunicacion;
	private String idInstitucion;
	private Boolean historico;
	private Boolean permisoejecucion;
	
	@JsonProperty("idModulo")
	public String getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}
	
	@JsonProperty("idObjetivo")
	public String getIdObjetivo() {
		return idObjetivo;
	}
	public void setIdObjetivo(String idObjetivo) {
		this.idObjetivo = idObjetivo;
	}
	
	@JsonProperty("idClaseComunicacion")
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@JsonProperty("modulo")
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	
	@JsonProperty("generica")
	public String getGenerica() {
		return generica;
	}
	public void setGenerica(String generica) {
		this.generica = generica;
	}
	
	@JsonProperty("objetivo")
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	@JsonProperty("claseComunicacion")
	public String getClaseComunicacion() {
		return claseComunicacion;
	}
	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Boolean getHistorico() {
		return historico;
	}
	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}
	public Boolean getPermisoejecucion() {
		return permisoejecucion;
	}
	public void setPermisoejecucion(Boolean permisoejecucion) {
		this.permisoejecucion = permisoejecucion;
	}
	
	
	
	
}
