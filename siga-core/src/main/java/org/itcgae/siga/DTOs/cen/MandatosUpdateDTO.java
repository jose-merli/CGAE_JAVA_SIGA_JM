package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MandatosUpdateDTO {

	private String idPersona;
	private String idCuenta;
	private String idAnexo;
	private String idMandato;
	private String esquema;
	private String firmaLugar;
	private Date fechaUsoDate;
	private Date firmaFechaDate;
	private String descripcion;
//	private String identifProducto;
	private String identif;
//	private String tipoIdServicio;
	private String tipoId;
	private String referencia;
//	private String referenciaServicio;
	
	
	/**
	 */
	public MandatosUpdateDTO idPersona(String idPersona){
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
	public MandatosUpdateDTO idCuenta(String idCuenta){
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
	public MandatosUpdateDTO idMandato(String idMandato){
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
	public MandatosUpdateDTO idAnexo(String idAnexo){
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
	public MandatosUpdateDTO esquema(String esquema){
		this.esquema = esquema;
		return this;
	}
	
	@JsonProperty("esquema")
	public String getEsquema() {
		return esquema;
	}
	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}
	
	/**
	 */
	public MandatosUpdateDTO firmaLugar(String firmaLugar){
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
	 */
	public MandatosUpdateDTO fechaUso(Date fechaUso){
		this.fechaUsoDate = fechaUso;
		return this;
	}
	
	@JsonProperty("fechaUsoDate")
	public Date getFechaUso() {
		return fechaUsoDate;
	}
	public void setFechaUso(Date fechaUso) {
		this.fechaUsoDate = fechaUso;
	}
	
	/**
	 */
	public MandatosUpdateDTO firmafecha(Date firmaFecha){
		this.firmaFechaDate = firmaFecha;
		return this;
	}
	
	@JsonProperty("firmaFechaDate")
	public Date getFirmafecha() {
		return firmaFechaDate;
	}
	public void setFirmafecha(Date firmaFecha) {
		this.firmaFechaDate = firmaFecha;
	}
	
	/**
	 */
	public MandatosUpdateDTO descripcion(String descripcion){
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
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    MandatosUpdateDTO mandatosUpdateDTO = (MandatosUpdateDTO) o;
	    return  Objects.equals(this.idPersona, mandatosUpdateDTO.idPersona) &&
	    		Objects.equals(this.idMandato, mandatosUpdateDTO.idMandato) &&
	    		Objects.equals(this.idAnexo, mandatosUpdateDTO.idAnexo) &&
	    		Objects.equals(this.idCuenta, mandatosUpdateDTO.idCuenta) &&
	    		Objects.equals(this.esquema, mandatosUpdateDTO.esquema) &&
	    		Objects.equals(this.descripcion, mandatosUpdateDTO.descripcion) &&
	    		Objects.equals(this.firmaLugar, mandatosUpdateDTO.firmaLugar) &&
	    		Objects.equals(this.fechaUsoDate, mandatosUpdateDTO.fechaUsoDate) &&
	    		Objects.equals(this.firmaFechaDate, mandatosUpdateDTO.firmaFechaDate) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idMandato,idAnexo, idCuenta,esquema,descripcion,firmaLugar,fechaUsoDate,firmaFechaDate);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MandatosUpdateDTO {\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idMandato: ").append(toIndentedString(idMandato)).append("\n");
	    sb.append("    idAnexo: ").append(toIndentedString(idAnexo)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    esquema: ").append(toIndentedString(esquema)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
	    sb.append("    firmaLugar: ").append(toIndentedString(firmaLugar)).append("\n");
	    sb.append("    fechaUsoDate: ").append(toIndentedString(fechaUsoDate)).append("\n");
	    sb.append("    firmaFechaDate: ").append(toIndentedString(firmaFechaDate)).append("\n");
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

	public String getIdentif() {
		return identif;
	}

	public void setIdentif(String identif) {
		this.identif = identif;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
