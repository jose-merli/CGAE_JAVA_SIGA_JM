package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosDireccionesSearchDTO {

	private String idPersona;
	private String idInstitucion;
	private boolean historico;
	private String idTipo;

	
	
	/**
	 */
	public DatosDireccionesSearchDTO idPersona(String idPersona){
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
	public DatosDireccionesSearchDTO idInstitucion(String idInstitucion){
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
	public DatosDireccionesSearchDTO historico(boolean historico){
		this.historico = historico;
		return this;
	}
	
	
	@JsonProperty("historico")
	public boolean getHistorico() {
		return historico;
	}


	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosDireccionesSearchDTO datosDireccionesSearchDTO = (DatosDireccionesSearchDTO) o;
	    return  Objects.equals(this.idPersona, datosDireccionesSearchDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, datosDireccionesSearchDTO.idInstitucion) &&
	    		Objects.equals(this.historico, datosDireccionesSearchDTO.historico) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,historico);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesSearchDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    historico: ").append(toIndentedString(historico)).append("\n");

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

	/**
	 * @return the idTipo
	 */
	public String getIdTipo() {
		return idTipo;
	}

	/**
	 * @param idTipo the idTipo to set
	 */
	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
	
	
}
