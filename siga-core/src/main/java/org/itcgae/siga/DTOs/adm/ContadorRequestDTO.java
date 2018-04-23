package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ContadorRequestDTO {

	private String idmodulo;
	private String idcontador;
	private String nombre;
	private String descripcion;
	private String idInstitucion;
	private String idLenguaje;
	
	
	/**
	**/
	public ContadorRequestDTO idmodulo(String idmodulo) {
		this.idmodulo = idmodulo;
		return this;
	}
	
	
	@JsonProperty("idmodulo")
	public String getIdModulo() {
		return idmodulo;
	}
	
	
	public void setIdModulo(String idmodulo) {
		this.idmodulo = idmodulo;
	}
	
	/**
	**/
	public ContadorRequestDTO idcontador(String idcontador) {
		this.idcontador = idcontador;
		return this;
	}
	
	
	@JsonProperty("idcontador")
	public String getIdContador() {
		return idcontador;
	}
	
	
	public void setIdContador(String idcontador) {
		this.idcontador = idcontador;
	}	

	
	/**
	**/
	public ContadorRequestDTO nombre(String nombre) {
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
	**/
	public ContadorRequestDTO descripcion(String descripcion) {
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
	public ContadorRequestDTO idInstitucion(String idInstitucion) {
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
	public ContadorRequestDTO idLenguaje(String idLenguaje) {
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
		ContadorRequestDTO parametroRequestDTO = (ContadorRequestDTO) o;
		return Objects.equals(this.idmodulo, parametroRequestDTO.idmodulo) &&
				Objects.equals(this.idcontador, parametroRequestDTO.idcontador) &&
				Objects.equals(this.nombre, parametroRequestDTO.nombre) &&
				Objects.equals(this.descripcion, parametroRequestDTO.descripcion) &&
				Objects.equals(this.idLenguaje, parametroRequestDTO.idLenguaje) &&
		        Objects.equals(this.idInstitucion, parametroRequestDTO.idInstitucion);
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(idmodulo,idcontador,nombre,descripcion,idLenguaje, idInstitucion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroRequestDTO {\n");

		sb.append("    idmodulo: ").append(toIndentedString(idmodulo)).append("\n");
		sb.append("    idcontador: ").append(toIndentedString(idcontador)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
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
