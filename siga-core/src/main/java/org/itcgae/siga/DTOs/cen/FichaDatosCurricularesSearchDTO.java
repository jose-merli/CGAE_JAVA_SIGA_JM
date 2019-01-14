package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FichaDatosCurricularesSearchDTO {

	private String idPersona;
	private boolean historico;
	

	/**
	 */
	public FichaDatosCurricularesSearchDTO idPersona(String idPersona){
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
	 *
	 */
	public FichaDatosCurricularesSearchDTO historico(boolean historico){
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
	    FichaDatosCurricularesSearchDTO datosBancariosInsertDTO = (FichaDatosCurricularesSearchDTO) o;
	    return  Objects.equals(this.idPersona, datosBancariosInsertDTO.idPersona) &&
	    		Objects.equals(this.historico, datosBancariosInsertDTO.historico);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, historico);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosInsertDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
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
