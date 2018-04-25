package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MultiidiomaCatalogoSearchDTO {

	private String nombreTabla;
	private String idiomaBusqueda;
	private String idiomaTraduccion;
	
	
	
	/**
	 */
	public MultiidiomaCatalogoSearchDTO nombreTabla(String nombreTabla){
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
	 */
	public MultiidiomaCatalogoSearchDTO idiomaBusqueda(String idiomaBusqueda){
		this.idiomaBusqueda = idiomaBusqueda;
		return this;
	}
	
	
	@JsonProperty("idiomaBusqueda")
	public String getIdiomaBusqueda() {
		return idiomaBusqueda;
	}
	
	
	public void setIdiomaBusqueda(String idiomaBusqueda) {
		this.idiomaBusqueda = idiomaBusqueda;
	}
	
	
	/**
	 */
	public MultiidiomaCatalogoSearchDTO idiomaTraduccion(String idiomaTraduccion){
		this.idiomaTraduccion = idiomaTraduccion;
		return this;
	}
	
	
	@JsonProperty("idiomaTraduccion")
	public String getIdiomaTraduccion() {
		return idiomaTraduccion;
	}
	
	
	public void setIdiomaTraduccion(String idiomaTraduccion) {
		this.idiomaTraduccion = idiomaTraduccion;
	}
	
		
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO = (MultiidiomaCatalogoSearchDTO) o;
	    return Objects.equals(this.nombreTabla, multiidiomaCatalogoSearchDTO.nombreTabla) &&
	        Objects.equals(this.idiomaBusqueda, multiidiomaCatalogoSearchDTO.idiomaBusqueda) &&
	        Objects.equals(this.idiomaTraduccion, multiidiomaCatalogoSearchDTO.idiomaTraduccion);
	      
	}


	@Override
	public int hashCode() {
		 return Objects.hash(nombreTabla, idiomaBusqueda, idiomaTraduccion);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MultiidiomaCatalogoSearchDTO {\n");
	    
	    sb.append("    nombreTabla: ").append(toIndentedString(nombreTabla)).append("\n");
	    sb.append("    idiomaBusqueda: ").append(toIndentedString(idiomaBusqueda)).append("\n");
	    sb.append("    idiomaTraduccion: ").append(toIndentedString(idiomaTraduccion)).append("\n");
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
