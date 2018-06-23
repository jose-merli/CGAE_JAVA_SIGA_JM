package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosIntegrantesSearchDTO {

	private String idPersona;
	private String idInstitucion;
	private boolean historico;
	

	
	
	/**
	 */
	public DatosIntegrantesSearchDTO idPersona(String idPersona){
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
	public DatosIntegrantesSearchDTO idInstitucion(String idInstitucion){
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
	public DatosIntegrantesSearchDTO historico(boolean historico){
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
	    DatosIntegrantesSearchDTO personaJuridicaSearchDTO = (DatosIntegrantesSearchDTO) o;
	    return  Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, personaJuridicaSearchDTO.idInstitucion) &&
	    		Objects.equals(this.historico, personaJuridicaSearchDTO.historico) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,historico);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosSearchDTO {\n");
	    
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
	
	
}
