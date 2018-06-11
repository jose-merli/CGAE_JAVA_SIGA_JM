package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MultiidiomaCatalogoUpdateDTO {

	private String idRecurso;
	private String descripcion;
	private String idLenguaje;
	private String nombreTabla;
	private String idInstitucion;
	private String local;
	
	
	


	/**
	**/
	public MultiidiomaCatalogoUpdateDTO idRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
		return this;
	}
	
	
	@JsonProperty("idRecurso")
	public String getIdRecurso() {
		return idRecurso;
	}
	
	
	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoUpdateDTO descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoUpdateDTO idLenguaje(String idLenguaje) {
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
	**/
	public MultiidiomaCatalogoUpdateDTO nombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
		return this;
	}
	
	
	@JsonProperty("nombreTabla")
	public String getNombreTabla() {
		return nombreTabla;
	}
	
	
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
	
	

	/**
	**/
	public MultiidiomaCatalogoUpdateDTO idInstitucion(String idInstitucion) {
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
	public MultiidiomaCatalogoUpdateDTO local(String local) {
		this.local = local;
		return this;
	}
	
	
	
	@JsonProperty("local")
	public String getLocal() {
		return local;
	}


	public void setLocal(String local) {
		this.local = local;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MultiidiomaCatalogoUpdateDTO multiidiomaCatalogoUpdateDTO = (MultiidiomaCatalogoUpdateDTO) o;
		return Objects.equals(this.idRecurso, multiidiomaCatalogoUpdateDTO.idRecurso) &&
        Objects.equals(this.descripcion, multiidiomaCatalogoUpdateDTO.descripcion) &&
        Objects.equals(this.idLenguaje, multiidiomaCatalogoUpdateDTO.idLenguaje) &&
        Objects.equals(this.nombreTabla, multiidiomaCatalogoUpdateDTO.nombreTabla) &&
        Objects.equals(this.idInstitucion, multiidiomaCatalogoUpdateDTO.idInstitucion) &&
		Objects.equals(this.local, multiidiomaCatalogoUpdateDTO.local);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idRecurso, descripcion, idLenguaje, nombreTabla, idInstitucion, local);
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MultiidiomaCatalogoUpdateDTO {\n");
    
		sb.append("    idRecurso: ").append(toIndentedString(idRecurso)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
		sb.append("    nombreTabla: ").append(toIndentedString(nombreTabla)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    local: ").append(toIndentedString(local)).append("\n");
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
