package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaIntegrantesCreateDTO {

	
	private String nombre;
	private String apellidos1;
	private String apellidos2;
	private String nifCif;
	private String tipoIdentificacion;
	private Date fechaCargo;
	private String cargo;
	private String idCargo;
	private String capitalSocial;
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
	public TarjetaIntegrantesCreateDTO apellidos1(String apellidos1){
		this.apellidos1 = apellidos1;
		return this;
	}

	
	@JsonProperty("apellidos1")
	public String getApellidos1() {
		return apellidos1;
	}

	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}

	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO apellidos2(String apellidos2){
		this.apellidos2 = apellidos2;
		return this;
	}
	
	
	@JsonProperty("apellidos2")
	public String getApellidos2() {
		return apellidos2;
	}

	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	
	
	/**
	 *
	 */
	public TarjetaIntegrantesCreateDTO nifCif(String nifCif){
		this.nifCif = nifCif;
		return this;
	}
	
	@JsonProperty("nifCif")
	public String getNifCif() {
		return nifCif;
	}

	public void setNifCif(String nifCif) {
		this.nifCif = nifCif;
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
	public TarjetaIntegrantesCreateDTO capitalSocial(String capitalSocial){
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
				&& Objects.equals(this.capitalSocial, tarjetaIntegrantesCreateDTO.capitalSocial)
				&& Objects.equals(this.idComponente, tarjetaIntegrantesCreateDTO.idComponente)
				&& Objects.equals(this.idPersonaPadre, tarjetaIntegrantesCreateDTO.idPersonaPadre) 
				&& Objects.equals(this.idPersonaIntegrante, tarjetaIntegrantesCreateDTO.idPersonaIntegrante)
				&& Objects.equals(this.nombre, tarjetaIntegrantesCreateDTO.nombre)
				&& Objects.equals(this.apellidos1, tarjetaIntegrantesCreateDTO.apellidos1)
				&& Objects.equals(this.apellidos2, tarjetaIntegrantesCreateDTO.apellidos2)
				&& Objects.equals(this.nifCif, tarjetaIntegrantesCreateDTO.nifCif)
				&& Objects.equals(this.tipoIdentificacion, tarjetaIntegrantesCreateDTO.tipoIdentificacion) 
				&& Objects.equals(this.idInstitucionIntegrante, tarjetaIntegrantesCreateDTO.idInstitucionIntegrante) 
				&& Objects.equals(this.idTipoColegio, tarjetaIntegrantesCreateDTO.idTipoColegio) 
				&& Objects.equals(this.idProvincia, tarjetaIntegrantesCreateDTO.idProvincia)
				&& Objects.equals(this.numColegiado, tarjetaIntegrantesCreateDTO.numColegiado)
				&& Objects.equals(this.tipo, tarjetaIntegrantesCreateDTO.tipo);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCargo, cargo,idCargo, capitalSocial, idComponente, idPersonaPadre, idPersonaIntegrante, nombre, apellidos1, apellidos2, nifCif, tipoIdentificacion, idInstitucionIntegrante, idTipoColegio, idProvincia,
				numColegiado, tipo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TarjetaIntegrantesCreateDTO {\n");

		sb.append("    fechaCargo: ").append(toIndentedString(fechaCargo)).append("\n");
		sb.append("    cargo: ").append(toIndentedString(cargo)).append("\n");
		sb.append("    idCargo: ").append(toIndentedString(idCargo)).append("\n");
		sb.append("    capitalSocial: ").append(toIndentedString(capitalSocial)).append("\n");
		sb.append("    idComponente: ").append(toIndentedString(idComponente)).append("\n");
		sb.append("    idPersonaPadre: ").append(toIndentedString(idPersonaPadre)).append("\n"); 
		sb.append("    idPersonaIntegrante: ").append(toIndentedString(idPersonaIntegrante)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    apellidos1: ").append(toIndentedString(apellidos1)).append("\n");
		sb.append("    apellidos2: ").append(toIndentedString(apellidos2)).append("\n");
		sb.append("    nifCif: ").append(toIndentedString(nifCif)).append("\n");
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
