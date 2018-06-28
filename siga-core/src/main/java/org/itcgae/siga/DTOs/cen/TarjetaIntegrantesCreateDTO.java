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
	private String cargo;
	private String idCargo;
	private String participacionSociedad;
	private String idComponente;
	private String idPersonaPadre;
	private String idPersonaIntegrante;
	private String idInstitucionIntegrante;
	private String idTipoColegio;
	private String idProvincia;
	private String numColegiado;
	private String tipo;
	
	
	
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
	public TarjetaIntegrantesCreateDTO idCargo(String idCargo){
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
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO idInstitucionIntegrante(String idInstitucionIntegrante){
		this.idInstitucionIntegrante = idInstitucionIntegrante;
		return this;
	}
	
	
	public String getIdInstitucionIntegrante() {
		return idInstitucionIntegrante;
	}

	public void setIdInstitucionIntegrante(String idInstitucionIntegrante) {
		this.idInstitucionIntegrante = idInstitucionIntegrante;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO idTipoColegio(String idTipoColegio){
		this.idTipoColegio = idTipoColegio;
		return this;
	}
	
	@JsonProperty("idTipoColegio")
	public String getIdTipoColegio() {
		return idTipoColegio;
	}

	public void setIdTipoColegio(String idTipoColegio) {
		this.idTipoColegio = idTipoColegio;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO idProvincia(String idProvincia){
		this.idProvincia = idProvincia;
		return this;
	}
	
	@JsonProperty("idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO numColegiado(String numColegiado){
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
	public TarjetaIntegrantesCreateDTO tipo(String tipo){
		this.tipo = tipo;
		return this;
	}
	
	
	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
				&& Objects.equals(this.cargo, tarjetaIntegrantesCreateDTO.cargo)
				&& Objects.equals(this.idCargo, tarjetaIntegrantesCreateDTO.idCargo) 
				&& Objects.equals(this.participacionSociedad, tarjetaIntegrantesCreateDTO.participacionSociedad)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesCreateDTO.idComponente)
				&& Objects.equals(this.idPersonaPadre, tarjetaIntegrantesCreateDTO.idPersonaPadre) 
				&& Objects.equals(this.idPersonaIntegrante, tarjetaIntegrantesCreateDTO.idPersonaIntegrante)
				&& Objects.equals(this.nombre, tarjetaIntegrantesCreateDTO.nombre)
				&& Objects.equals(this.apellido1, tarjetaIntegrantesCreateDTO.apellido1)
				&& Objects.equals(this.apellido2, tarjetaIntegrantesCreateDTO.apellido2)
				&& Objects.equals(this.nifcif, tarjetaIntegrantesCreateDTO.nifcif)
				&& Objects.equals(this.tipoIdentificacion, tarjetaIntegrantesCreateDTO.tipoIdentificacion) 
				&& Objects.equals(this.idInstitucionIntegrante, tarjetaIntegrantesCreateDTO.idInstitucionIntegrante) 
				&& Objects.equals(this.idTipoColegio, tarjetaIntegrantesCreateDTO.idTipoColegio) 
				&& Objects.equals(this.idProvincia, tarjetaIntegrantesCreateDTO.idProvincia)
				&& Objects.equals(this.numColegiado, tarjetaIntegrantesCreateDTO.numColegiado)
				&& Objects.equals(this.tipo, tarjetaIntegrantesCreateDTO.tipo);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCargo, cargo,idCargo, participacionSociedad, idComponente, idPersonaPadre, idPersonaIntegrante, nombre, apellido1, apellido2, nifcif, tipoIdentificacion, idInstitucionIntegrante, idTipoColegio, idProvincia,
				numColegiado, tipo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesCreateDTO {\n");

		sb.append("    fechaCargo: ").append(toIndentedString(fechaCargo)).append("\n");
		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    idCargo: ").append(toIndentedString(idCargo)).append("\n");
		sb.append("    participacionSociedad: ").append(toIndentedString(participacionSociedad)).append("\n");
		sb.append("    idComponente: ").append(toIndentedString(idComponente)).append("\n");
		sb.append("    idPersonaPadre: ").append(toIndentedString(idPersonaPadre)).append("\n"); 
		sb.append("    idPersonaIntegrante: ").append(toIndentedString(idPersonaIntegrante)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    apellido1: ").append(toIndentedString(apellido1)).append("\n");
		sb.append("    apellido2: ").append(toIndentedString(apellido2)).append("\n");
		sb.append("    nifcif: ").append(toIndentedString(nifcif)).append("\n");
		sb.append("    tipoIdentificacion: ").append(toIndentedString(tipoIdentificacion)).append("\n");
		sb.append("    idInstitucionIntegrante: ").append(toIndentedString(idInstitucionIntegrante)).append("\n");
		sb.append("    idTipoColegio: ").append(toIndentedString(idTipoColegio)).append("\n"); 
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
		sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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
