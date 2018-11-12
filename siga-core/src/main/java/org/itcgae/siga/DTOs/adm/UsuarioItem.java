package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioItem {

	private String nombreApellidos = null;
	private String nif  = null;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaAlta = null;
	private String activo  = null;
	private String roles  = null;
	private String codigoExterno = null;
	private String perfil = null;
	private String idInstitucion  = null;
	private String idLenguaje  = null;
	private String descLenguaje  = null;
	private String idUsuario = null;
	private String[] perfiles = null;
	private String nifRol = null;
	

	/**
	 */
	public UsuarioItem nombreApellidos(String nombreApellidos){
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
	 */
	public UsuarioItem nif(String nif){
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
	 */
	public UsuarioItem fechaAlta(Date fechaAlta){
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
	 */
	public UsuarioItem roles(String roles){
		this.roles = roles;
		return this;
	}
	
	
	@JsonProperty("roles")
	public String getRoles() {
		return roles;
	}
	
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
	/**
	 */
	public UsuarioItem codigoExterno(String codigoExterno){
		this.codigoExterno = codigoExterno;
		return this;
	}
	
	
	@JsonProperty("codigoExterno")
	public String getCodigoExterno() {
		return codigoExterno;
	}
	
	
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	
	
	/**
	 */
	public UsuarioItem perfil(String perfil){
		this.perfil = perfil;
		return this;
	}
	
	
	@JsonProperty("perfil")
	public String getPerfil() {
		return perfil;
	}
	
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	
	/**
	 */
	public UsuarioItem activo(String activo){
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
	 */
	public UsuarioItem idInstitucion(String idInstitucion){
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
	 */
	public UsuarioItem idUsuario(String idUsuario){
		this.idUsuario = idUsuario;
		return this;
	}
	
	
	@JsonProperty("idUsuario")
	public String getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 */
	public UsuarioItem idLenguaje(String idLenguaje){
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
	

	
	
	/**
	 */
	public UsuarioItem descLenguaje(String descLenguaje){
		this.descLenguaje = descLenguaje;
		return this;
	}
	
	
	@JsonProperty("descLenguaje")
	public String getDescLenguaje() {
		return descLenguaje;
	}
	
	public void setDescLenguaje(String descLenguaje) {
		this.descLenguaje = descLenguaje;
	}
	
	
	/**
	 */
	public UsuarioItem perfiles(String[] perfiles){
		this.perfiles = perfiles;
		return this;
	}
	
	
	@JsonProperty("perfiles")
	public String[] getperfiles() {
		return perfiles;
	}


	public void setPerfiles(String[] perfiles) {
		this.perfiles = perfiles;
	}
	
	
	/**
	 */
	public UsuarioItem nifRol(String nifRol){
		this.nifRol = nifRol;
		return this;
	}
	
	
	@JsonProperty("nifRol")
	public String getNifRol() {
		return nifRol;
	}
	
	
	public void setNifRol(String nifRol) {
		this.nifRol = nifRol;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    UsuarioItem usuarioItem = (UsuarioItem) o;
	    return Objects.equals(this.nombreApellidos, usuarioItem.nombreApellidos) &&
	        Objects.equals(this.nif, usuarioItem.nif) &&
	        Objects.equals(this.fechaAlta, usuarioItem.fechaAlta) &&
	        Objects.equals(this.roles, usuarioItem.roles) &&
	        Objects.equals(this.codigoExterno, usuarioItem.codigoExterno) &&
	        Objects.equals(this.perfil, usuarioItem.perfil) &&
	        Objects.equals(this.activo, usuarioItem.activo) &&
	        Objects.equals(this.idInstitucion, usuarioItem.idInstitucion) &&
	    	Objects.equals(this.idUsuario, usuarioItem.idUsuario)&&
	    	Objects.equals(this.perfiles, usuarioItem.perfiles)&&
	    	Objects.equals(this.nifRol, usuarioItem.nifRol) ;
	    
	}


	@Override
	public int hashCode() {
		 return Objects.hash(nombreApellidos, nif, fechaAlta, roles, codigoExterno, perfil, activo, idInstitucion, idUsuario,nifRol);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioItem {\n");
	    
	    sb.append("    nombreApellidos: ").append(toIndentedString(nombreApellidos)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");
	    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
	    sb.append("    codigoExterno: ").append(toIndentedString(codigoExterno)).append("\n");
	    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
	    sb.append("    grupo: ").append(toIndentedString(activo)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
	    sb.append("    perfiles: ").append(toIndentedString(perfiles)).append("\n");
	    sb.append("    nifRol: ").append(toIndentedString(nifRol)).append("\n");
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
