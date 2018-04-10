package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CreateResponseDTO {

	private String nombreApellidos;
	private String nif;
	private String rol;
	private String grupo;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaAlta;
	private String activo;
	
	
	@JsonProperty("nombreApellidos")
	public String getNombreApellidos() {
		return nombreApellidos;
	}
	
	
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}
	
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	
	@JsonProperty("rol")
	public String getRol() {
		return rol;
	}
	
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	@JsonProperty("grupo")
	public String getGrupo() {
		return grupo;
	}
	
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	@JsonProperty("fechaAlta")
	public Date getFechaAlta() {
		return fechaAlta;
	}
	
	
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	@JsonProperty("activo")
	public String getActivo() {
		return activo;
	}
	
	
	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	
}
