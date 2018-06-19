package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosDeleteDTO {

	private String idPersona;
	private String[] idCuenta;
	private String idInstitucion;
	
	
	/**
	 *
	 */
	public DatosBancariosDeleteDTO idCuenta(String[] idCuenta){
		this.idCuenta = idCuenta;
		return this;
	}
	
	@JsonProperty("idCuenta")
	public String[] getIdCuenta() {
		return idCuenta;
	}
	
	
	public void setIdCuenta(String[]  idCuenta) {
		this.idCuenta = idCuenta;
	}
	
	
	/**
	 *
	 */
	public DatosBancariosDeleteDTO idPersona(String idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	
	
	public void setIdPersona(String  idPersona) {
		this.idPersona = idPersona;
	}
	
	
	/**
	 *
	 */
	public DatosBancariosDeleteDTO idInstitucion(String idInstitucion){
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
	    DatosBancariosDeleteDTO busquedaJuridicaDeleteDTO = (DatosBancariosDeleteDTO) o;
	    return  Objects.equals(this.idPersona, busquedaJuridicaDeleteDTO.idPersona) &&
	    		Objects.equals(this.idCuenta, busquedaJuridicaDeleteDTO.idCuenta) &&
	    		Objects.equals(this.idInstitucion, busquedaJuridicaDeleteDTO.idInstitucion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,idCuenta);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosDeleteDTO {\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
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
