package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaIntegrantesDeleteDTO {
	
	private String cargo; //  descripcionCargo: null --->cargo 
	private String idCargo;            //  cargo:  ---> idCargo
	private String capitalSocial; //  participacionSociedad --> capitalSocial 
	private String idComponente;
	private String idPersona;
	
	
	
	

	
	
	/**
	 *
	 */
	public TarjetaIntegrantesDeleteDTO cargo(String cargo){
		this.cargo = cargo;
		return this;
	}
	
	
	@JsonProperty("cargo")
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesDeleteDTO idCargo(String idCargo){
		this.idCargo = idCargo;
		return this;
	}
	
	@JsonProperty("idCargo")
	public String getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(String idCargo) {
		this.idCargo = idCargo;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesDeleteDTO capitalSocial(String capitalSocial){
		this.capitalSocial = capitalSocial;
		return this;
	}
	
	@JsonProperty("capitalSocial")
	public String getCapitalSocial() {
		return capitalSocial;
	}
	
	
	public void setCapitalSocial(String capitalSocial) {
		this.capitalSocial = capitalSocial;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesDeleteDTO idComponente(String idComponente){
		this.idComponente = idComponente;
		return this;
	}
	
	@JsonProperty("idComponente")
	public String getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	
	/**
	 *
	 */
	public TarjetaIntegrantesDeleteDTO idPersona(String idPersona){
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
	

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TarjetaIntegrantesDeleteDTO tarjetaIntegrantesDeleteDTO = (TarjetaIntegrantesDeleteDTO) o;
		return  Objects.equals(this.cargo, tarjetaIntegrantesDeleteDTO.cargo)
				&& Objects.equals(this.idCargo, tarjetaIntegrantesDeleteDTO.idCargo) 
				&& Objects.equals(this.capitalSocial, tarjetaIntegrantesDeleteDTO.capitalSocial)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesDeleteDTO.idComponente)
				&& Objects.equals(this.idPersona, tarjetaIntegrantesDeleteDTO.idPersona);

	}

	@Override
	public int hashCode() {
		return Objects.hash(cargo, idCargo, capitalSocial, idComponente, idPersona);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesDeleteDTO {\n");

		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    idCargo: ").append(toIndentedString(idCargo)).append("\n");
		sb.append("    capitalSocial: ").append(toIndentedString(capitalSocial)).append("\n");
		sb.append("    idComponente: ").append(toIndentedString(idComponente)).append("\n");
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
