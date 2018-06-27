package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaIntegrantesUpdateDTO {

	
	
	private Date fechaCargo;
	private String descripcionCargo;
	private String cargo;
	private String participacionSociedad;
	private String idComponente;
	private String idPersona;
	
	
	
	

	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO fechaCargo(Date fechaCargo){
		this.fechaCargo = fechaCargo;
		return this;
	}
	
	@JsonProperty("fechaCargo")
	public Date getFechaCargo() {
		return fechaCargo;
	}
	public void setFechaCargo(Date fechaCargo) {
		this.fechaCargo = fechaCargo;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO descripcionCargo(String descripcionCargo){
		this.descripcionCargo = descripcionCargo;
		return this;
	}
	
	
	@JsonProperty("descripcionCargo")
	public String getDescripcionCargo() {
		return descripcionCargo;
	}
	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO cargo(String cargo){
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
	public TarjetaIntegrantesUpdateDTO participacionSociedad(String participacionSociedad){
		this.participacionSociedad = participacionSociedad;
		return this;
	}
	
	@JsonProperty("participacionSociedad")
	public String getParticipacionSociedad() {
		return participacionSociedad;
	}
	
	
	public void setParticipacionSociedad(String participacionSociedad) {
		this.participacionSociedad = participacionSociedad;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO idComponente(String idComponente){
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
	public TarjetaIntegrantesUpdateDTO idPersona(String idPersona){
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
		TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO = (TarjetaIntegrantesUpdateDTO) o;
		return Objects.equals(this.fechaCargo, tarjetaIntegrantesUpdateDTO.fechaCargo)
				&& Objects.equals(this.descripcionCargo, tarjetaIntegrantesUpdateDTO.descripcionCargo)
				&& Objects.equals(this.cargo, tarjetaIntegrantesUpdateDTO.cargo) 
				&& Objects.equals(this.participacionSociedad, tarjetaIntegrantesUpdateDTO.participacionSociedad)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesUpdateDTO.idComponente)
				&& Objects.equals(this.idPersona, tarjetaIntegrantesUpdateDTO.idPersona);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCargo, descripcionCargo, cargo, participacionSociedad, idComponente, idPersona);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesUpdateDTO {\n");

		sb.append("    fechaCargo: ").append(toIndentedString(fechaCargo)).append("\n");
		sb.append("    descripcionCargo: ").append(toIndentedString(descripcionCargo)).append("\n");
		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    participacionSociedad: ").append(toIndentedString(participacionSociedad)).append("\n");
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
