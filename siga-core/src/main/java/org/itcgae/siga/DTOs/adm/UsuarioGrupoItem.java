package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.ComboItem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioGrupoItem {


	private String idGrupo = null;
	private String descripcionGrupo  = null;
	private ComboItem[] rolesAsignados = null;
	private ComboItem[] rolesNoAsignados = null;
	

	/**
	 */
	public UsuarioGrupoItem idGrupo(String idGrupo){
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
	public UsuarioGrupoItem descripcionGrupo(String descripcionGrupo){
		this.descripcionGrupo = descripcionGrupo;
		return this;
	}
	
	
	@JsonProperty("descripcionGrupo")
	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}
	
	
	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}
	
	
	/**
	 */
	public UsuarioGrupoItem rolesAsignados(ComboItem[] rolesAsignados){
		this.rolesAsignados = rolesAsignados;
		return this;
	}
	
	
	@JsonProperty("rolesAsignados")
	public ComboItem[] getRolesAsignados() {
		return rolesAsignados;
	}
	
	
	public void setRolesAsignados(ComboItem[] rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}
	
	/**
	 */
	public UsuarioGrupoItem rolesNoAsignados(ComboItem[] rolesNoAsignados){
		this.rolesNoAsignados = rolesNoAsignados;
		return this;
	}
	
	
	@JsonProperty("rolesNoAsignados")
	public ComboItem[] getRolesNoAsignados() {
		return rolesNoAsignados;
	}
	
	
	public void setRolesNoAsignados(ComboItem[] rolesNoAsignados) {
		this.rolesNoAsignados = rolesNoAsignados;
	}
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    UsuarioGrupoItem usuarioItem = (UsuarioGrupoItem) o;
	    return Objects.equals(this.idGrupo, usuarioItem.idGrupo) &&
	        Objects.equals(this.descripcionGrupo, usuarioItem.descripcionGrupo) &&
	        Objects.equals(this.rolesAsignados, usuarioItem.rolesAsignados) &&
	        Objects.equals(this.rolesNoAsignados, usuarioItem.rolesNoAsignados);
	    
	}


	@Override
	public int hashCode() {
		 return Objects.hash(idGrupo, descripcionGrupo, rolesAsignados, rolesNoAsignados);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioItem {\n");
	    
	    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
	    sb.append("    descripcionGrupo: ").append(toIndentedString(descripcionGrupo)).append("\n");
	    sb.append("    rolesAsignados: ").append(toIndentedString(rolesAsignados)).append("\n");
	    sb.append("    rolesNoAsignados: ").append(toIndentedString(rolesNoAsignados)).append("\n");

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
