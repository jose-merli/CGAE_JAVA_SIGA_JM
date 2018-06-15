package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

public class AsociarPersonaDTO {
	private String idPersona;
	private String idPersonaAsociar;
	private String tipoPersona;
	private String idInstitucion;
	
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersonaAsociar() {
		return idPersonaAsociar;
	}
	public void setIdPersonaAsociar(String idPersonaDesasociar) {
		this.idPersonaAsociar = idPersonaDesasociar;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
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
	    		Objects.equals(this.idInstitucion, asociarPersonaDTO.idInstitucion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AsociarPersonaDTO {\n");
	    
	    sb.append("idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	  
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
