package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosItem {

	private String idPersona;
	private String idCuenta;
	private String idInstitucion;
	private String titular;
	private String iban;
	private String uso;
	private String bic;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFirmaServicios;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFirmaProductos;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaBaja;
	
	
	

	/**
	 *
	 */
	public DatosBancariosItem idCuenta(String idCuenta){
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
	public DatosBancariosItem idPersona(String idPersona){
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
	public DatosBancariosItem idInstitucion(String idInstitucion){
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
	
	

	/**
	 */
	public DatosBancariosItem titular(String titular){
		this.titular = titular;
		return this;
	}
	
	@JsonProperty("titular")
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	/**
	 */
	public DatosBancariosItem iban(String iban){
		this.iban = iban;
		return this;
	}
	
	@JsonProperty("iban")
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	/**
	 */
	public DatosBancariosItem bic(String bic){
		this.bic = bic;
		return this;
	}
	
	@JsonProperty("bic")
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	/**
	 */
	public DatosBancariosItem uso(String uso){
		this.uso = uso;
		return this;
	}
	
	@JsonProperty("uso")
	public String getUso() {
		return uso;
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
	
	/**
	 */
	public DatosBancariosItem fechaFirmaServicios(Date fechaFirmaServicios){
		this.fechaFirmaServicios = fechaFirmaServicios;
		return this;
	}
	
	@JsonProperty("fechaFirmaServicios")
	public Date getFechaFirmaServicios() {
		return fechaFirmaServicios;
	}
	
	
	public void setFechaFirmaServicios(Date fechaFirmaServicios) {
		this.fechaFirmaServicios = fechaFirmaServicios;
	}
	
	
	/**
	 */
	public DatosBancariosItem fechaFirmaProductos(Date fechaFirmaProductos){
		this.fechaFirmaProductos = fechaFirmaProductos;
		return this;
	}
	
	@JsonProperty("fechaFirmaProductos")
	public Date getFechaFirmaProductos() {
		return fechaFirmaProductos;
	}
	
	
	public void setFechaFirmaProductos(Date fechaFirmaProductos) {
		this.fechaFirmaProductos = fechaFirmaProductos;
	}
	
	/**
	 */
	public DatosBancariosItem fechaBaja(Date fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}
	
	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}
	
	
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosBancariosItem personaJuridicaSearchDTO = (DatosBancariosItem) o;
	    return Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, personaJuridicaSearchDTO.idInstitucion) &&
	    		Objects.equals(this.idCuenta, personaJuridicaSearchDTO.idCuenta) &&
	    		Objects.equals(this.titular, personaJuridicaSearchDTO.titular) &&
	    		Objects.equals(this.bic, personaJuridicaSearchDTO.bic) &&
	    		Objects.equals(this.uso, personaJuridicaSearchDTO.uso) &&
	    		Objects.equals(this.iban, personaJuridicaSearchDTO.iban) &&
	    		Objects.equals(this.fechaBaja, personaJuridicaSearchDTO.fechaBaja) &&
	    		Objects.equals(this.fechaFirmaProductos, personaJuridicaSearchDTO.fechaFirmaProductos) &&
	    		Objects.equals(this.fechaFirmaServicios, personaJuridicaSearchDTO.fechaFirmaServicios);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,idCuenta,titular,iban,uso,bic,fechaFirmaProductos,fechaFirmaServicios,fechaBaja);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    uso: ").append(toIndentedString(uso)).append("\n");
	    sb.append("    titular: ").append(toIndentedString(titular)).append("\n");
	    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
	    sb.append("    bic: ").append(toIndentedString(bic)).append("\n");
	    sb.append("    fechaFirmaServicios: ").append(toIndentedString(fechaFirmaServicios)).append("\n");
	    sb.append("    fechaFirmaProductos: ").append(toIndentedString(fechaFirmaProductos)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");

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
