package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class HistoricoUsuarioItem {

	private String idPersona;
	private String idHistorico;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+1")
	private Date fechaEfectiva;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+1")
	private Date fechaEntrada;
	private String motivo;
	private String descripcion;
	private String idTipoCambio;
	private String descTipoCambio;
	private String nombre;
	private String apellidos1;
	private String apellidos2;
	private String descripcionUsuario;
	private String persona;
	
	
	

	/**
	 *
	 */
	public HistoricoUsuarioItem idPersona(String idPersona){
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
	public HistoricoUsuarioItem idHistorico(String idHistorico){
		this.idHistorico = idHistorico;
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
	public HistoricoUsuarioItem fechaEfectiva(Date fechaEfectiva){
		this.fechaEfectiva = fechaEfectiva;
		return this;
	}
	
	
	@JsonProperty("fechaEfectiva")
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}
	
	
	
	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem fechaEntrada(Date fechaEntrada){
		this.fechaEntrada = fechaEntrada;
		return this;
	}
	
	
	@JsonProperty("fechaEntrada")
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	
	
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem motivo(String motivo){
		this.motivo = motivo;
		return this;
	}
	
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}
	
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem descripcion(String descripcion){
		this.descripcion = descripcion;
		return this;
	}
	
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem idTipoCambio(String idTipoCambio){
		this.idTipoCambio = idTipoCambio;
		return this;
	}
	
	
	@JsonProperty("idTipoCambio")
	public String getIdTipoCambio() {
		return idTipoCambio;
	}
	
	
	public void setIdTipoCambio(String idTipoCambio) {
		this.idTipoCambio = idTipoCambio;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem descTipoCambio(String descTipoCambio){
		this.descTipoCambio = descTipoCambio;
		return this;
	}
	
	
	@JsonProperty("descTipoCambio")
	public String getDescTipoCambio() {
		return descTipoCambio;
	}
	
	
	public void setDescTipoCambio(String descTipoCambio) {
		this.descTipoCambio = descTipoCambio;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem nombre(String nombre){
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
	public HistoricoUsuarioItem apellidos1(String apellidos1){
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
	public HistoricoUsuarioItem apellidos2(String apellidos2){
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
	public HistoricoUsuarioItem descripcionUsuario(String descripcionUsuario){
		this.descripcionUsuario = descripcionUsuario;
		return this;
	}
	
	
	@JsonProperty("descripcionUsuario")
	public String getDescripcionUsuario() {
		return descripcionUsuario;
	}


	public void setDescripcionUsuario(String descripcionUsuario) {
		this.descripcionUsuario = descripcionUsuario;
	}
	
	
	/**
	 *
	 */
	public HistoricoUsuarioItem persona(String persona){
		this.persona = persona;
		return this;
	}
	
	
	@JsonProperty("persona")
	public String getPersona() {
		return persona;
	}


	public void setPersona(String persona) {
		this.persona = persona;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HistoricoUsuarioItem historicoUsuarioItem = (HistoricoUsuarioItem) o;
		return Objects.equals(this.idPersona, historicoUsuarioItem.idPersona) && 
				Objects.equals(this.idHistorico, historicoUsuarioItem.idHistorico) &&
				Objects.equals(this.fechaEfectiva, historicoUsuarioItem.fechaEfectiva) &&
				Objects.equals(this.fechaEntrada, historicoUsuarioItem.fechaEntrada) &&
				Objects.equals(this.motivo, historicoUsuarioItem.motivo) &&
				Objects.equals(this.descripcion, historicoUsuarioItem.descripcion) &&
				Objects.equals(this.idTipoCambio, historicoUsuarioItem.idTipoCambio) &&
				Objects.equals(this.descTipoCambio, historicoUsuarioItem.descTipoCambio) &&
				Objects.equals(this.nombre, historicoUsuarioItem.nombre) &&
				Objects.equals(this.apellidos1, historicoUsuarioItem.apellidos1) &&
				Objects.equals(this.apellidos2, historicoUsuarioItem.apellidos2) &&
				Objects.equals(this.descripcionUsuario, historicoUsuarioItem.descripcionUsuario) &&
				Objects.equals(this.persona, historicoUsuarioItem.persona);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPersona, idHistorico, fechaEfectiva, fechaEntrada, motivo, descripcion,idTipoCambio,descTipoCambio,nombre,apellidos1,apellidos2, descripcionUsuario, persona);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class HistoricoUsuarioItem {\n");

		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idHistorico: ").append(toIndentedString(idHistorico)).append("\n");
		sb.append("    fechaEfectiva: ").append(toIndentedString(fechaEfectiva)).append("\n");
		sb.append("    fechaEntrada: ").append(toIndentedString(fechaEntrada)).append("\n");
		sb.append("    motivo: ").append(toIndentedString(motivo)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    idTipoCambio: ").append(toIndentedString(idTipoCambio)).append("\n");
		sb.append("    descTipoCambio: ").append(toIndentedString(descTipoCambio)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    apellidos1: ").append(toIndentedString(apellidos1)).append("\n");
		sb.append("    apellidos2: ").append(toIndentedString(apellidos2)).append("\n");
		sb.append("    descripcionUsuario: ").append(toIndentedString(descripcionUsuario)).append("\n");
		sb.append("    usuario: ").append(toIndentedString(persona)).append("\n");
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
