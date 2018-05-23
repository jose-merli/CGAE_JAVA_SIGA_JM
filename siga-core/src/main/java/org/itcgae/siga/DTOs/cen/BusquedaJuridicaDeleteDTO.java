package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaJuridicaDeleteDTO {

	private String [] idPersonaDelete;
	private String idInstitucion;
	
	/**
	 *
	 */
	public BusquedaJuridicaDeleteDTO fechaBaja(String [] idPersonaDelete){
		this.idPersonaDelete = idPersonaDelete;
		return this;
	}
	
	@JsonProperty("idPersonaDelete")
	public String [] getIdPersona() {
		return idPersonaDelete;
	}
	
	
	public void setIdPersona(String [] idPersonaDelete) {
		this.idPersonaDelete = idPersonaDelete;
	}
	
	
	/**
	 *
	 */
	public BusquedaJuridicaDeleteDTO idInstitucion(String idInstitucion){
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
	    BusquedaJuridicaDeleteDTO busquedaJuridicaDeleteDTO = (BusquedaJuridicaDeleteDTO) o;
	    return  Objects.equals(this.idPersonaDelete, busquedaJuridicaDeleteDTO.idPersonaDelete) &&
	    		Objects.equals(this.idInstitucion, busquedaJuridicaDeleteDTO.idInstitucion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersonaDelete, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaJuridicaDeleteDTO {\n");
	    
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idPersonaDelete: ").append(toIndentedString(idPersonaDelete)).append("\n");
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
