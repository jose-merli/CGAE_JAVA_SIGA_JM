package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MandatosDownloadDTO {

	private String idPersona;
	private String idCuenta;
	private String idAnexo;
	private String idMandato;
	
	
	/**
	 */
	public MandatosDownloadDTO idPersona(String idPersona){
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
	public MandatosDownloadDTO idCuenta(String idCuenta){
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
	public MandatosDownloadDTO idMandato(String idMandato){
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
	 *
	 */
	public MandatosDownloadDTO idAnexo(String idAnexo){
		this.idAnexo = idAnexo;
		return this;
	}
	
	
	@JsonProperty("idAnexo")
	public String getIdAnexo() {
		return idAnexo;
	}


	public void setIdAnexo(String idAnexo) {
		this.idAnexo = idAnexo;
	}
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    MandatosDownloadDTO mandatosDownloadDTO = (MandatosDownloadDTO) o;
	    return  Objects.equals(this.idPersona, mandatosDownloadDTO.idPersona) &&
	    		Objects.equals(this.idMandato, mandatosDownloadDTO.idMandato) &&
	    		Objects.equals(this.idAnexo, mandatosDownloadDTO.idAnexo) &&
	    		Objects.equals(this.idCuenta, mandatosDownloadDTO.idCuenta);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idMandato,idAnexo, idCuenta);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MandatosDownloadDTO {\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idMandato: ").append(toIndentedString(idMandato)).append("\n");
	    sb.append("    idAnexo: ").append(toIndentedString(idAnexo)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
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
