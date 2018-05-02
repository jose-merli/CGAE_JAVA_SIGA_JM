package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import org.itcgae.siga.DTOs.gen.ComboCatalogoItem;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UsuarioGrupoEditDTO {


	private String idGrupo = null;
	private String descripcionGrupo  = null;
	private ComboCatalogoItem[] asignarRolDefecto = null;
	

	/**
	 */
	public UsuarioGrupoEditDTO idGrupo(String idGrupo){
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
	public UsuarioGrupoEditDTO descripcionGrupo(String descripcionGrupo){
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
	public UsuarioGrupoEditDTO asignarRolDefecto(ComboCatalogoItem[] asignarRolDefecto){
		this.asignarRolDefecto = asignarRolDefecto;
		return this;
	}
	
	
	@JsonProperty("asignarRolDefecto")
	public ComboCatalogoItem[] getAsignarRolDefecto() {
		return asignarRolDefecto;
	}
	
	
	public void setAsignarRolDefecto(ComboCatalogoItem[] asignarRolDefecto) {
		this.asignarRolDefecto = asignarRolDefecto;
	}

	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    UsuarioGrupoEditDTO usuarioItem = (UsuarioGrupoEditDTO) o;
	    return Objects.equals(this.idGrupo, usuarioItem.idGrupo) &&
	        Objects.equals(this.descripcionGrupo, usuarioItem.descripcionGrupo) &&
	        Objects.equals(this.asignarRolDefecto, usuarioItem.asignarRolDefecto);
    
	}


	@Override
	public int hashCode() {
		 return Objects.hash(idGrupo, descripcionGrupo, asignarRolDefecto);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class UsuarioGrupoEditDTO {\n");
	    
	    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
	    sb.append("    descripcionGrupo: ").append(toIndentedString(descripcionGrupo)).append("\n");
	    sb.append("    asignarRolDefecto: ").append(toIndentedString(asignarRolDefecto)).append("\n");

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
