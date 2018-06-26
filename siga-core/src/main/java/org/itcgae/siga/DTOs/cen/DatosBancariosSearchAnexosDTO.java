package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosSearchAnexosDTO {

	private String idPersona;
	private String idCuenta;
	private String idInstitucion;
	private String idMandato;

	

	
	
	/**
	 */
	public DatosBancariosSearchAnexosDTO idPersona(String idPersona){
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
	public DatosBancariosSearchAnexosDTO idCuenta(String idCuenta){
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
	 */
	public DatosBancariosSearchAnexosDTO idInstitucion(String idInstitucion){
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
	 *
	 */
	public DatosBancariosSearchAnexosDTO idMandato(String idMandato){
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
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosBancariosSearchAnexosDTO datosBancariosSearchAnexosDTO = (DatosBancariosSearchAnexosDTO) o;
	    return  Objects.equals(this.idPersona, datosBancariosSearchAnexosDTO.idPersona) &&
	    		Objects.equals(this.idCuenta, datosBancariosSearchAnexosDTO.idCuenta) &&
	    		Objects.equals(this.idInstitucion, datosBancariosSearchAnexosDTO.idInstitucion) &&
	    		Objects.equals(this.idMandato, datosBancariosSearchAnexosDTO.idMandato) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idCuenta, idInstitucion,idMandato);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosSearchAnexosDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idMandato: ").append(toIndentedString(idMandato)).append("\n");

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
