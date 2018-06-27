package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsociarPersonaDTO {
	private String idPersona;
	private String idPersonaAsociar;
	private String tipoPersona;
	private String idInstitucion;
	
	
	/**
	 *
	 */
	public AsociarPersonaDTO idPersona(String idPersona){
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
	public AsociarPersonaDTO idPersonaAsociar(String idPersonaAsociar){
		this.idPersonaAsociar = idPersonaAsociar;
		return this;
	}
	
	@JsonProperty("idPersonaAsociar")
	public String getIdPersonaAsociar() {
		return idPersonaAsociar;
	}
	public void setIdPersonaAsociar(String idPersonaDesasociar) {
		this.idPersonaAsociar = idPersonaDesasociar;
	}
	
	
	/**
	 *
	 */
	public AsociarPersonaDTO tipoPersona(String tipoPersona){
		this.tipoPersona = tipoPersona;
		return this;
	}

	
	@JsonProperty("tipoPersona")
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	/**
	 *
	 */
	public AsociarPersonaDTO idInstitucion(String idInstitucion){
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
	    AsociarPersonaDTO asociarPersonaDTO = (AsociarPersonaDTO) o;
	    return Objects.equals(this.idPersona, asociarPersonaDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, asociarPersonaDTO.idInstitucion) &&
	    		Objects.equals(this.idPersonaAsociar, asociarPersonaDTO.idPersonaAsociar) &&
	    		Objects.equals(this.tipoPersona, asociarPersonaDTO.tipoPersona);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion, idPersonaAsociar, tipoPersona);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AsociarPersonaDTO {\n");
	    
	    sb.append("idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("idPersonaAsociar: ").append(toIndentedString(idPersonaAsociar)).append("\n");
	    sb.append("tipoPersona: ").append(toIndentedString(tipoPersona)).append("\n");
	  
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
