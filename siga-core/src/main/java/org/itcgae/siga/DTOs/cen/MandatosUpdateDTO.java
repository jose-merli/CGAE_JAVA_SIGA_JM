package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MandatosUpdateDTO {

	private String idPersona;
	private String idCuenta;

	private String idMandato;
	private String esquema;
	

	
	
	/**
	 */
	public MandatosUpdateDTO idPersona(String idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 */
	public MandatosUpdateDTO idCuenta(String idCuenta){
		this.idCuenta = idCuenta;
		return this;
	}
	
	@JsonProperty("idCuenta")
	public String getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}
	
	

	
	
	/**
	 *
	 */
	public MandatosUpdateDTO idMandato(String idMandato){
		this.idMandato = idMandato;
		return this;
	}
	
	
	@JsonProperty("idMandato")
	public String getIdMandato() {
		return idMandato;
	}


	public void setIdMandato(String idMandato) {
		this.idMandato = idMandato;
	}
	
	/**
	 */
	public MandatosUpdateDTO esquema(String esquema){
		this.esquema = esquema;
		return this;
	}
	
	@JsonProperty("esquema")
	public String getEsquema() {
		return esquema;
	}
	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    MandatosUpdateDTO personaJuridicaSearchDTO = (MandatosUpdateDTO) o;
	    return  Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idMandato, personaJuridicaSearchDTO.idMandato) &&
	    		Objects.equals(this.idCuenta, personaJuridicaSearchDTO.idCuenta) &&
	    		Objects.equals(this.esquema, personaJuridicaSearchDTO.esquema) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idMandato, idCuenta,esquema);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MandatosUpdateDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idMandato: ").append(toIndentedString(idMandato)).append("\n");

	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    esquema: ").append(toIndentedString(esquema)).append("\n");

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
