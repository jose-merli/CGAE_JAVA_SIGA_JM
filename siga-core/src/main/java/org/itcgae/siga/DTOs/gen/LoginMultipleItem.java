package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginMultipleItem {
	
	private String idInstitucion;
	private String rol;
	private String perfil;
	
	
	
	/**
	 */
	public LoginMultipleItem idInstitucion(String idInstitucion){
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
	public LoginMultipleItem rol(String rol){
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
	public LoginMultipleItem perfil(String perfil){
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
	
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    LoginMultipleItem loginMultipleItem = (LoginMultipleItem) o;
	    return Objects.equals(this.idInstitucion, loginMultipleItem.idInstitucion) &&
	    	   Objects.equals(this.rol, loginMultipleItem.rol) &&
	    	   Objects.equals(this.perfil, loginMultipleItem.perfil) 
	    	   ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idInstitucion, rol, perfil);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class LoginMultipleItem {\n");
	    
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    rol: ").append(toIndentedString(rol)).append("\n");
	    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
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
