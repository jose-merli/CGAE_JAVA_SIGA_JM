package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class HistoricoUsuarioUpdateDTO {

	private String idHistorico;
	private String idPersona;
	private String idInstitucion;
	private String motivo;
	
	
	/**
	 * 
	 */
	public HistoricoUsuarioUpdateDTO idHistorico(String idHistorico) {
		this.idHistorico =idHistorico;
		return this;
	}
	
	@JsonProperty("idHistorico")
	public String getIdHistorico() {
		return idHistorico;
	}
	
	
	public void setIdHistorico(String idHistorico) {
		this.idHistorico = idHistorico;
	}
	
	
	/**
	 * 
	 */
	public HistoricoUsuarioUpdateDTO idPersona(String idPersona) {
		this.idPersona =idPersona;
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
	public HistoricoUsuarioUpdateDTO idInstitucion(String idInstitucion) {
		this.idInstitucion =idInstitucion;
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
	public HistoricoUsuarioUpdateDTO motivo(String motivo) {
		this.motivo =motivo;
		return this;
	}
	
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}
	
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HistoricoUsuarioUpdateDTO historicoUsuarioUpdateDTO = (HistoricoUsuarioUpdateDTO) o;
		return Objects.equals(this.idHistorico, historicoUsuarioUpdateDTO.idHistorico) &&
        Objects.equals(this.idPersona, historicoUsuarioUpdateDTO.idPersona) &&
        Objects.equals(this.idInstitucion, historicoUsuarioUpdateDTO.idInstitucion) &&
        Objects.equals(this.motivo, historicoUsuarioUpdateDTO.motivo);
        
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idHistorico, idPersona, idInstitucion, motivo);
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class HistoricoUsuarioUpdateDTO {\n");
    
		sb.append("    idHistorico: ").append(toIndentedString(idHistorico)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    motivo: ").append(toIndentedString(motivo)).append("\n");
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
