package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioUpdateDTO {

	private String nombreApellidos;
	private String nif;
	private String rol;
	private String[] grupo;
	private String idGrupo;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaAlta;
	private String codigoExterno;
	private String activo;
	private String idUsuario;
	private String idInstitucion;
	
	
	/**
	 */
	public UsuarioUpdateDTO nombreApellidos(String nombreApellidos){
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
	public UsuarioUpdateDTO nif(String nif){
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
	public UsuarioUpdateDTO rol(String rol){
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
	 */
	public UsuarioUpdateDTO grupo(String[] grupo){
		this.grupo = grupo;
		return this;
	}
	
	
	@JsonProperty("grupo")
	public String[] getGrupo() {
		return grupo;
	}
	
	
	public void setGrupo(String[] grupo) {
		this.grupo = grupo;
	}
	
	
	/**
	 */
	public UsuarioUpdateDTO idGrupo(String idGrupo){
		this.idGrupo = idGrupo;
		return this;
	}
	
	
	@JsonProperty("idGrupo")
	public String getIdGrupo() {
		return idGrupo;
	}


	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	
	/**
	 */
	public UsuarioUpdateDTO fechaAlta(Date fechaAlta){
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
	public UsuarioUpdateDTO codigoExterno(String codigoExterno){
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
	public UsuarioUpdateDTO activo(String activo){
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
	public UsuarioUpdateDTO idUsuario(String idUsuario){
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
	public UsuarioUpdateDTO idInstitucion(String idInstitucion){
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

	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    UsuarioUpdateDTO usuarioUpdateDTO = (UsuarioUpdateDTO) o;
	    return Objects.equals(this.nombreApellidos, usuarioUpdateDTO.nombreApellidos) &&
	    		Objects.equals(this.nif, usuarioUpdateDTO.nif) &&
	    		Objects.equals(this.rol, usuarioUpdateDTO.rol) &&
	    		Objects.equals(this.grupo, usuarioUpdateDTO.grupo) &&
	    		Objects.equals(this.grupo, usuarioUpdateDTO.idGrupo) &&
	    		Objects.equals(this.fechaAlta, usuarioUpdateDTO.fechaAlta) &&
	    		Objects.equals(this.codigoExterno, usuarioUpdateDTO.codigoExterno) &&
	    		Objects.equals(this.activo, usuarioUpdateDTO.activo) &&
	    		Objects.equals(this.idUsuario, usuarioUpdateDTO.idUsuario) &&
	    		Objects.equals(this.idInstitucion, usuarioUpdateDTO.idInstitucion);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(nombreApellidos, nif, rol, grupo, idGrupo, fechaAlta, codigoExterno, activo, idUsuario, idInstitucion);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioUpdateDTO {\n");
		sb.append("    nombreApellidos: ").append(toIndentedString(nombreApellidos)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    rol: ").append(toIndentedString(rol)).append("\n");
		sb.append("    grupo: ").append(toIndentedString(grupo)).append("\n");
		sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
		sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");
		sb.append("    codigoExterno: ").append(toIndentedString(codigoExterno)).append("\n");
		sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
		sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
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
