package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ParametroRequestDTO {

	private String modulo;
	private String parametrosGenerales;
	private String idInstitucion;
	
	
	/**
	**/
	public ParametroRequestDTO modulo(String modulo) {
		this.modulo = modulo;
		return this;
	}
	
	
	@JsonProperty("modulo")
	public String getModulo() {
		return modulo;
	}
	
	
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	
	
	/**
	**/
	public ParametroRequestDTO parametrosGenerales(String parametrosGenerales) {
		this.parametrosGenerales = parametrosGenerales;
		return this;
	}
	
	@JsonProperty("parametrosGenerales")
	public String getParametrosGenerales() {
		return parametrosGenerales;
	}
	
	
	public void setParametrosGenerales(String parametrosGenerales) {
		this.parametrosGenerales = parametrosGenerales;
	}
	
	
	/**
	**/
	public ParametroRequestDTO idInstitucion(String idInstitucion) {
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
		ParametroRequestDTO parametroRequestDTO = (ParametroRequestDTO) o;
		return Objects.equals(this.modulo, parametroRequestDTO.modulo) &&
		        Objects.equals(this.parametrosGenerales, parametroRequestDTO.parametrosGenerales) &&
		        Objects.equals(this.idInstitucion, parametroRequestDTO.idInstitucion);
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(modulo, parametrosGenerales, idInstitucion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroRequestDTO {\n");

		sb.append("    modulo: ").append(toIndentedString(modulo)).append("\n");
		sb.append("    parametrosGenerales: ").append(toIndentedString(parametrosGenerales)).append("\n");
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
