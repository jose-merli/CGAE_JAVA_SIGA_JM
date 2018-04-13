package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ParametroDeleteDTO {

	private String modulo;
	private String parametro;
	private String valor;
	private String idInstitucion;
	
	
	
	/**
	 **/
	public ParametroDeleteDTO modulo(String modulo) {
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
	public ParametroDeleteDTO parametro(String parametro) {
		this.parametro = parametro;
		return this;
	}
	
	
	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}
	
	
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	
	/**
	 **/
	public ParametroDeleteDTO valor(String valor) {
		this.valor = valor;
		return this;
	}
	
	
	@JsonProperty("valor")
	public String getValor() {
		return valor;
	}
	
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	/**
	 **/
	public ParametroDeleteDTO idInstitucion(String idInstitucion) {
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
		ParametroDeleteDTO parametroDeleteDTO = (ParametroDeleteDTO) o;
		return Objects.equals(this.modulo, parametroDeleteDTO.modulo) &&
				Objects.equals(this.parametro, parametroDeleteDTO.parametro) &&
				 Objects.equals(this.valor, parametroDeleteDTO.valor) &&
				 Objects.equals(this.idInstitucion, parametroDeleteDTO.idInstitucion);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(modulo, parametro, valor, idInstitucion);
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroDeleteDTO {\n");
		sb.append("    modulo: ").append(toIndentedString(modulo)).append("\n");
		sb.append("    parametro: ").append(toIndentedString(parametro)).append("\n");
		sb.append("    valor: ").append(toIndentedString(valor)).append("\n");
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
