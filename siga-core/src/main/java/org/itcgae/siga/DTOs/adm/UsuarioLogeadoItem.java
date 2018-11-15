package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioLogeadoItem {

	private Integer idUsuario = null;
	private String nombre = null;
	private String dni  = null;
	private String ultimaConex = null;
	private String institucion  = null;
	private String idioma  = null;
	private String perfiles  = null;
	private String rutaLogout  = null;
	

	public String getRutaLogout() {
		return rutaLogout;
	}


	public void setRutaLogout(String rutaLogout) {
		this.rutaLogout = rutaLogout;
	}


	public String getPerfiles() {
		return perfiles;
	}


	public void setPerfiles(String perfiles) {
		this.perfiles = perfiles;
	}


	/**
	 */
	public UsuarioLogeadoItem idUsuario(Integer idUsuario){
		this.idUsuario = idUsuario;
		return this;
	}
	
	
	@JsonProperty("idUsuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	

	/**
	 */
	public UsuarioLogeadoItem nombre(String nombre){
		this.nombre = nombre;
		return this;
	}
	
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 */
	public UsuarioLogeadoItem dni(String dni){
		this.dni = dni;
		return this;
	}
	
	
	@JsonProperty("dni")
	public String getDni() {
		return dni;
	}
	
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	/**
	 */
	public UsuarioLogeadoItem UltimaConex(String ultimaConex){
		this.ultimaConex = ultimaConex;
		return this;
	}
	
	
	@JsonProperty("ultimaConex")
	public String getUltimaConex() {
		return ultimaConex;
	}
	
	
	public void setUltimaConex(String ultimaConex) {
		this.ultimaConex = ultimaConex;
	}
	
	
	/**
	 */
	public UsuarioLogeadoItem institucion(String institucion){
		this.institucion = institucion;
		return this;
	}
	
	
	@JsonProperty("institucion")
	public String getInstitucion() {
		return institucion;
	}
	
	
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	
	/**
	 */
	public UsuarioLogeadoItem idioma(String idioma){
		this.idioma = idioma;
		return this;
	}
	
	
	@JsonProperty("idioma")
	public String getIdioma() {
		return idioma;
	}
	
	
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    UsuarioLogeadoItem usuarioItem = (UsuarioLogeadoItem) o;
	    return Objects.equals(this.idUsuario, usuarioItem.idUsuario) && 
	    	Objects.equals(this.nombre, usuarioItem.nombre) &&
	        Objects.equals(this.dni, usuarioItem.dni) &&
	        Objects.equals(this.ultimaConex, usuarioItem.ultimaConex) &&
	        Objects.equals(this.institucion, usuarioItem.institucion) &&
	        Objects.equals(this.idioma, usuarioItem.idioma);
	    
	}


	@Override
	public int hashCode() {
		 return Objects.hash(idUsuario,nombre, dni, ultimaConex, institucion, idioma);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioItem {\n");
	    
	    sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    dni: ").append(toIndentedString(dni)).append("\n");
	    sb.append("    UltimaConex: ").append(toIndentedString(ultimaConex)).append("\n");
	    sb.append("    institucion: ").append(toIndentedString(institucion)).append("\n");
	    sb.append("    idioma: ").append(toIndentedString(idioma)).append("\n");

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
