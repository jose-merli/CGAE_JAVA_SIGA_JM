package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaIntegrantesUpdateDTO {

	
	
	private Date fechaCargo;
	private String cargo; //  descripcionCargo: null --->cargo 
	private String idCargo;            //  cargo:  ---> idCargo
	private Double capitalSocial; //  participacionSociedad --> capitalSocial 
	private String idComponente;
	private String idPersona;
	private String flagSocio;
	private String idTipoColegio;
	private String idProvincia;
	private String colegio;
	private Date fechaBajaCargo;
	private String numColegiado;
	private String idPersonaComponente;
	
	
	
	
	public String getFlagSocio() {
		return flagSocio;
	}

	public void setFlagSocio(String flagSocio) {
		this.flagSocio = flagSocio;
	}

	

	
	
	

	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO fechaBajaCargo(Date fechaBajaCargo){
		this.fechaBajaCargo = fechaBajaCargo;
		return this;
	}
	
	@JsonProperty("fechaBajaCargo")
	public Date getFechaBajaCargo() {
		return fechaBajaCargo;
	}
	public void setFechaBajaCargo(Date fechaBajaCargo) {
		this.fechaBajaCargo = fechaBajaCargo;
	}
	
	
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
	public TarjetaIntegrantesUpdateDTO idCargo(String idCargo){
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
	public TarjetaIntegrantesUpdateDTO capitalSocial(Double capitalSocial){
		this.capitalSocial = capitalSocial;
		return this;
	}
	
	@JsonProperty("capitalSocial")
	public Double getCapitalSocial() {
		return capitalSocial;
	}
	
	
	public void setCapitalSocial(Double capitalSocial) {
		this.capitalSocial = capitalSocial;
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
	
	@JsonProperty("idTipoColegio")
	public String getIdTipoColegio() {
		return idTipoColegio;
	}

	public void setIdTipoColegio(String idTipoColegio) {
		this.idTipoColegio = idTipoColegio;
	}
	
	@JsonProperty("idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO numColegiado(String numColegiado){
		this.numColegiado = numColegiado;
		return this;
	}
	
	@JsonProperty("numColegiado")
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	/**
	 *
	 */
	public TarjetaIntegrantesUpdateDTO idPersonaComponente(String idPersonaComponente){
		this.idPersonaComponente = idPersonaComponente;
		return this;
	}
	
	@JsonProperty("idPersonaComponente")
	public String getIdPersonaComponente() {
		return idPersonaComponente;
	}
	public void setIdPersonaComponente(String idPersonaComponente) {
		this.idPersonaComponente = idPersonaComponente;
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
				&& Objects.equals(this.cargo, tarjetaIntegrantesUpdateDTO.cargo)
				&& Objects.equals(this.idCargo, tarjetaIntegrantesUpdateDTO.idCargo) 
				&& Objects.equals(this.capitalSocial, tarjetaIntegrantesUpdateDTO.capitalSocial)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesUpdateDTO.idComponente)
				&& Objects.equals(this.idPersona, tarjetaIntegrantesUpdateDTO.idPersona)
				&& Objects.equals(this.idTipoColegio, tarjetaIntegrantesUpdateDTO.idTipoColegio)
				&& Objects.equals(this.idProvincia, tarjetaIntegrantesUpdateDTO.idProvincia)
				&& Objects.equals(this.colegio, tarjetaIntegrantesUpdateDTO.colegio)
		&& Objects.equals(this.numColegiado, tarjetaIntegrantesUpdateDTO.numColegiado)
		&& Objects.equals(this.idPersonaComponente, tarjetaIntegrantesUpdateDTO.idPersonaComponente);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCargo, cargo, idCargo, capitalSocial, idComponente, idPersona, idTipoColegio, idProvincia, colegio, numColegiado, idPersonaComponente);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesUpdateDTO {\n");

		sb.append("    fechaCargo: ").append(toIndentedString(fechaCargo)).append("\n");
		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    idCargo: ").append(toIndentedString(idCargo)).append("\n");
		sb.append("    capitalSocial: ").append(toIndentedString(capitalSocial)).append("\n");
		sb.append("    idComponente: ").append(toIndentedString(idComponente)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idTipoColegio: ").append(toIndentedString(idTipoColegio)).append("\n");
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    colegio: ").append(toIndentedString(colegio)).append("\n");
		sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
		sb.append("    idPersonaComponente: ").append(toIndentedString(idPersonaComponente)).append("\n");
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
