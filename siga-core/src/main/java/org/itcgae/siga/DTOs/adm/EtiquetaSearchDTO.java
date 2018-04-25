package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class EtiquetaSearchDTO {

	private String descripcion;
	private String idiomaBusqueda;
	private String idiomaTraduccion;
	
	/**
	 */
	public EtiquetaSearchDTO descripcion(String descripcion){
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
	 */
	public EtiquetaSearchDTO idiomaBusqueda(String idiomaBusqueda){
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
	public EtiquetaSearchDTO idiomaTraduccion(String idiomaTraduccion){
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

	    EtiquetaSearchDTO etiquetaSearchDTO = (EtiquetaSearchDTO) o;
	    return Objects.equals(this.descripcion, etiquetaSearchDTO.descripcion) &&
	        Objects.equals(this.idiomaBusqueda, etiquetaSearchDTO.idiomaBusqueda) &&
	        Objects.equals(this.idiomaTraduccion, etiquetaSearchDTO.idiomaTraduccion);
	      
	}


	@Override
	public int hashCode() {
		 return Objects.hash(descripcion, idiomaBusqueda, idiomaTraduccion);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EtiquetaSearchDTO {\n");
	    
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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
