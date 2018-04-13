package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioDeleteDTO {

	private String nombreApellidos;
	private String nif;
	private String rol;
	private String grupo;
	private List<String> idUsuario = new ArrayList<String>();
	private String idInstitucion;
	private String activo;
	
	
	/**
	 **/
	public UsuarioDeleteDTO nombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
		return this;
	}
	
	
	@JsonProperty("nombreApellidos")
	public String getNombreApellidos() {
		return nombreApellidos;
	}
	
	
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO nif(String nif) {
		this.nif = nif;
		return this;
	}
	
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO rol(String rol) {
		this.rol = rol;
		return this;
	}
	
	
	@JsonProperty("rol")
	public String getRol() {
		return rol;
	}
	
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO grupo(String grupo) {
		this.grupo = grupo;
		return this;
	}
	
	
	@JsonProperty("grupo")
	public String getGrupo() {
		return grupo;
	}
	
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO idUsuario(List<String> idUsuario) {
		this.idUsuario = idUsuario;
		return this;
	}
	
	
	@JsonProperty("idUsuario")
	public List<String> getIdUsuario() {
		return idUsuario;
	}
	
	
	public void setIdUsuario(List<String> idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	
	/**
	 **/
	public UsuarioDeleteDTO activo(String activo) {
		this.activo = activo;
		return this;
	}
	
	
	@JsonProperty("activo")
	public String getActivo() {
		return activo;
	}


	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UsuarioDeleteDTO usuarioDeleteDTO = (UsuarioDeleteDTO) o;
		return Objects.equals(this.nombreApellidos, usuarioDeleteDTO.nombreApellidos) &&
        Objects.equals(this.nif, usuarioDeleteDTO.nif) &&
        Objects.equals(this.rol, usuarioDeleteDTO.rol) &&
        Objects.equals(this.grupo, usuarioDeleteDTO.grupo) &&
        Objects.equals(this.idUsuario, usuarioDeleteDTO.idUsuario) &&
        Objects.equals(this.idInstitucion, usuarioDeleteDTO.idInstitucion) &&
        Objects.equals(this.activo, usuarioDeleteDTO.activo);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(nombreApellidos, nif, rol, grupo, idUsuario, idInstitucion, activo);
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class UsuarioDeleteDTO {\n");
    
		sb.append("    nombreApellidos: ").append(toIndentedString(nombreApellidos)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    rol: ").append(toIndentedString(rol)).append("\n");
		sb.append("    grupo: ").append(toIndentedString(grupo)).append("\n");
		sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
		sb.append("}");
		return sb.toString();
	}
	

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
	
}
