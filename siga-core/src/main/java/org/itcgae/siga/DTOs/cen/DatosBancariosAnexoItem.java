package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosAnexoItem {

	private String idPersona;
	private String idCuenta;
	private String idAnexo;
	private String idMandato;
	private String descripcion;
	private String tipoMandato;
	private String tipo;
	private String fechaUso;
	private String firmaFecha;
	private String firmaLugar;
	private String idFicheroFirma;
	private String idInstitucion;
		

	/**
	 *
	 */
	public DatosBancariosAnexoItem idCuenta(String idCuenta){
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
	 */
	public DatosBancariosAnexoItem idPersona(String idPersona){
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
	public DatosBancariosAnexoItem idAnexo(String idAnexo){
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
	
	

	/**
	 */
	public DatosBancariosAnexoItem idMandato(String idMandato){
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
	 */
	public DatosBancariosAnexoItem descripcion(String descripcion){
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
	 */
	public DatosBancariosAnexoItem tipoMandato(String tipoMandato){
		this.tipoMandato = tipoMandato;
		return this;
	}
	
	@JsonProperty("tipoMandato")
	public String geTtipoMandato() {
		return tipoMandato;
	}
	public void setTipoMandato(String tipoMandato) {
		this.tipoMandato = tipoMandato;
	}
	
	/**
	 */
	public DatosBancariosAnexoItem tipo(String tipo){
		this.tipo = tipo;
		return this;
	}
	
	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}
	public void seTtipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 */
	public DatosBancariosAnexoItem fechaUso(String fechaUso){
		this.fechaUso = fechaUso;
		return this;
	}
	
	@JsonProperty("fechaUso")
	public String getfechaUso() {
		return fechaUso;
	}
	public void setfechaUso(String fechaUso) {
		this.fechaUso = fechaUso;
	}
	
	
	
	/**
	 */
	public DatosBancariosAnexoItem firmaFecha(String firmaFecha){
		this.firmaFecha = firmaFecha;
		return this;
	}
	
	@JsonProperty("firmaFecha")
	public String getFirmaFecha() {
		return firmaFecha;
	}
	public void setFirmaFecha(String firmaFecha) {
		this.firmaFecha = firmaFecha;
	}
	
	/**
	 */
	public DatosBancariosAnexoItem firmaLugar(String firmaLugar){
		this.firmaLugar = firmaLugar;
		return this;
	}
	
	@JsonProperty("firmaLugar")
	public String getFirmaLugar() {
		return firmaLugar;
	}
	public void setFirmaLugar(String firmaLugar) {
		this.firmaLugar = firmaLugar;
	}
	/**
	 *
	 */
	public DatosBancariosAnexoItem idFicheroFirma(String idFicheroFirma){
		this.idFicheroFirma = idFicheroFirma;
		return this;
	}
	
	
	@JsonProperty("idFicheroFirma")
	public String getIdFicheroFirma() {
		return idFicheroFirma;
	}
	
	
	public void setIdFicheroFirma(String idFicheroFirma) {
		this.idFicheroFirma = idFicheroFirma;
	}
	/**
	 *
	 */
	public DatosBancariosAnexoItem idInstitucion(String idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	
	@JsonProperty("idInstitucion")
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
	    DatosBancariosAnexoItem personaJuridicaSearchDTO = (DatosBancariosAnexoItem) o;
	    return Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idAnexo, personaJuridicaSearchDTO.idAnexo) &&
	    		Objects.equals(this.idCuenta, personaJuridicaSearchDTO.idCuenta) &&
	    		Objects.equals(this.idMandato, personaJuridicaSearchDTO.idMandato) &&
	    		Objects.equals(this.tipo, personaJuridicaSearchDTO.tipo) &&
	    		Objects.equals(this.fechaUso, personaJuridicaSearchDTO.fechaUso) &&
	    		Objects.equals(this.descripcion, personaJuridicaSearchDTO.descripcion) &&
	    		Objects.equals(this.tipoMandato, personaJuridicaSearchDTO.tipoMandato) &&
	    		Objects.equals(this.firmaLugar, personaJuridicaSearchDTO.firmaLugar) &&
	    		Objects.equals(this.firmaFecha, personaJuridicaSearchDTO.firmaFecha) &&
	    		Objects.equals(this.idFicheroFirma, personaJuridicaSearchDTO.idFicheroFirma) &&
	    		Objects.equals(this.idInstitucion, personaJuridicaSearchDTO.idInstitucion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idAnexo,idCuenta,idMandato,descripcion,tipoMandato,fechaUso,tipo,firmaLugar,
	    		firmaFecha, idFicheroFirma, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idAnexo: ").append(toIndentedString(idAnexo)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    fechaUso: ").append(toIndentedString(fechaUso)).append("\n");
	    sb.append("    idMandato: ").append(toIndentedString(idMandato)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
	    sb.append("    tipoMandato: ").append(toIndentedString(tipoMandato)).append("\n");
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    firmaFecha: ").append(toIndentedString(firmaFecha)).append("\n");
	    sb.append("    firmaLugar: ").append(toIndentedString(firmaLugar)).append("\n");
	    sb.append("    idFicheroFirma: ").append(toIndentedString(idFicheroFirma)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");



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
