package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;



public class StringDTO {

	private String valor;
	
	/**
	 */
	public StringDTO valor(String valor){
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
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    StringDTO stringDTO = (StringDTO) o;
	    return Objects.equals(this.valor, stringDTO.valor);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(valor);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class StringDTO {\n");
	    
	    sb.append("  valor: ").append(toIndentedString(valor)).append("\n");
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
