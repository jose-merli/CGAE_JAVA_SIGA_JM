package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaIntegrantesCreateDTO {

	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nifcif;
	private String tipoIdentificacion;
	private Date fechaCargo;
	private String descripcionCargo;
	private String cargo;
	private String participacionSociedad;
	private String idComponente;
	private String idPersonaPadre;
	private String idPersonaIntegrante;
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO nombre(String nombre){
		this.nombre = nombre;
		return this;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO apellido1(String apellido1){
		this.apellido1 = apellido1;
		return this;
	}

	
	@JsonProperty("apellido1")
	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO apellido2(String apellido2){
		this.apellido2 = apellido2;
		return this;
	}
	
	
	@JsonProperty("apellido2")
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO nifcif(String nifcif){
		this.nifcif = nifcif;
		return this;
	}
	
	@JsonProperty("nifcif")
	public String getNifcif() {
		return nifcif;
	}

	public void setNifcif(String nifcif) {
		this.nifcif = nifcif;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO tipoIdentificacion(String tipoIdentificacion){
		this.tipoIdentificacion = tipoIdentificacion;
		return this;
	}
	
	
	@JsonProperty("tipoIdentificacion")
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	

	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO fechaCargo(Date fechaCargo){
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
	public TarjetaIntegrantesCreateDTO descripcionCargo(String descripcionCargo){
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
	public TarjetaIntegrantesCreateDTO cargo(String cargo){
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
	public TarjetaIntegrantesCreateDTO participacionSociedad(String participacionSociedad){
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
	public TarjetaIntegrantesCreateDTO idComponente(String idComponente){
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
	public TarjetaIntegrantesCreateDTO idPersonaPadre(String idPersonaPadre){
		this.idPersonaPadre = idPersonaPadre;
		return this;
	}
	
	@JsonProperty("idPersonaPadre")
	public String getIdPersonaPadre() {
		return idPersonaPadre;
	}

	public void setIdPersonaPadre(String idPersona) {
		this.idPersonaPadre = idPersona;
	}
	
	

	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO idPersonaIntegrante(String idPersonaIntegrante){
		this.idPersonaIntegrante = idPersonaIntegrante;
		return this;
	}
	
	
	@JsonProperty("idPersonaIntegrante")
	public String getIdPersonaIntegrante() {
		return idPersonaIntegrante;
	}

	public void setIdPersonaIntegrante(String idPersonaIntegrante) {
		this.idPersonaIntegrante = idPersonaIntegrante;
	}
	
	

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO = (TarjetaIntegrantesCreateDTO) o;
		return Objects.equals(this.fechaCargo, tarjetaIntegrantesCreateDTO.fechaCargo)
				&& Objects.equals(this.descripcionCargo, tarjetaIntegrantesCreateDTO.descripcionCargo)
				&& Objects.equals(this.cargo, tarjetaIntegrantesCreateDTO.cargo) 
				&& Objects.equals(this.participacionSociedad, tarjetaIntegrantesCreateDTO.participacionSociedad)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesCreateDTO.idComponente)
				&& Objects.equals(this.idPersonaPadre, tarjetaIntegrantesCreateDTO.idPersonaPadre) 
				&& Objects.equals(this.idPersonaIntegrante, tarjetaIntegrantesCreateDTO.idPersonaIntegrante)
				&& Objects.equals(this.nombre, tarjetaIntegrantesCreateDTO.nombre)
				&& Objects.equals(this.apellido1, tarjetaIntegrantesCreateDTO.apellido1)
				&& Objects.equals(this.apellido2, tarjetaIntegrantesCreateDTO.apellido2)
				&& Objects.equals(this.nifcif, tarjetaIntegrantesCreateDTO.nifcif)
				&& Objects.equals(this.tipoIdentificacion, tarjetaIntegrantesCreateDTO.tipoIdentificacion);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCargo, descripcionCargo, cargo, participacionSociedad, idComponente, idPersonaPadre, idPersonaIntegrante, nombre, apellido1, apellido2, nifcif, tipoIdentificacion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesCreateDTO {\n");

		sb.append("    fechaCargo: ").append(toIndentedString(fechaCargo)).append("\n");
		sb.append("    descripcionCargo: ").append(toIndentedString(descripcionCargo)).append("\n");
		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    participacionSociedad: ").append(toIndentedString(participacionSociedad)).append("\n");
		sb.append("    idComponente: ").append(toIndentedString(idComponente)).append("\n");
		sb.append("    idPersonaPadre: ").append(toIndentedString(idPersonaPadre)).append("\n"); 
		sb.append("    idPersonaIntegrante: ").append(toIndentedString(idPersonaIntegrante)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    apellido1: ").append(toIndentedString(apellido1)).append("\n");
		sb.append("    apellido2: ").append(toIndentedString(apellido2)).append("\n");
		sb.append("    nifcif: ").append(toIndentedString(nifcif)).append("\n");
		sb.append("    tipoIdentificacion: ").append(toIndentedString(tipoIdentificacion)).append("\n");
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
