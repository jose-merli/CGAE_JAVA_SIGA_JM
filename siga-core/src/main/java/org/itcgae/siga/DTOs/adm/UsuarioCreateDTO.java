package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioCreateDTO {

	private String nombreApellidos;
	private String nif;
	private String rol;
	private String grupo;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaAlta;
	private String activo;
	private String idInstitucion;
	private String idLenguaje;
	
	
	/**
	 *
	 */
	public UsuarioCreateDTO nombreApellidos(String nombreApellidos){
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
	 *
	 */
	public UsuarioCreateDTO nif(String nif){
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
	 *
	 */
	public UsuarioCreateDTO rol(String rol){
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
	 *
	 */
	public UsuarioCreateDTO grupo(String grupo){
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
	 *
	 */
	public UsuarioCreateDTO fechaAlta(Date fechaAlta){
		this.fechaAlta = fechaAlta;
		return this;
	}
	
	
	@JsonProperty("fechaAlta")
	public Date getFechaAlta() {
		return fechaAlta;
	}
	
	
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	/**
	 *
	 */
	public UsuarioCreateDTO activo(String activo){
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
	
	
	/**
	 *
	 */
	public UsuarioCreateDTO idInstitucion(String idInstitucion){
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
	 *
	 */
	public UsuarioCreateDTO idLenguaje(String idLenguaje){
		this.idLenguaje = idLenguaje;
		return this;
	}
	
	
	@JsonProperty("idLenguaje")
	public String getIdLenguaje() {
		return idLenguaje;
	}



	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    UsuarioCreateDTO usuarioCreateDTO = (UsuarioCreateDTO) o;
	    return Objects.equals(this.nombreApellidos, usuarioCreateDTO.nombreApellidos) &&
	        Objects.equals(this.nif, usuarioCreateDTO.nif) &&
	        Objects.equals(this.rol, usuarioCreateDTO.rol) &&
	        Objects.equals(this.grupo, usuarioCreateDTO.grupo) &&
	        Objects.equals(this.fechaAlta, usuarioCreateDTO.fechaAlta) &&
	        Objects.equals(this.activo, usuarioCreateDTO.activo) &&
	        Objects.equals(this.idInstitucion, usuarioCreateDTO.idInstitucion) &&
	        Objects.equals(this.idLenguaje, usuarioCreateDTO.idLenguaje);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nombreApellidos, nif, rol, grupo, fechaAlta, activo, idInstitucion, idLenguaje);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioDTO {\n");
	    
	    sb.append("    nombreApellidos: ").append(toIndentedString(nombreApellidos)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    rol: ").append(toIndentedString(rol)).append("\n");
	    sb.append("    grupo: ").append(toIndentedString(grupo)).append("\n");
	    sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");
	    sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
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
