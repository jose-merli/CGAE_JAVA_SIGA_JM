package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosBancariosInsertDTO {

	private String idPersona;
	private String idInstitucion;
	private String titular;
	private String[] tipoCuenta;
	private String iban;
	private String cuentaContable;
	private Boolean revisionCuentas;
	private String idCuenta;
	private String motivo;
	
	

	

	/**
	 */
	public DatosBancariosInsertDTO idPersona(String idPersona){
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
	public DatosBancariosInsertDTO idCuenta(String idCuenta){
		this.idPersona = idCuenta;
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
	public DatosBancariosInsertDTO titular(String titular){
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
	public DatosBancariosInsertDTO idInstitucion(String idInstitucion){
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
	 *
	 */
	public DatosBancariosInsertDTO iban(String iban){
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
	public DatosBancariosInsertDTO cuentaContable(String cuentaContable){
		this.cuentaContable = cuentaContable;
		return this;
	}
	
	@JsonProperty("cuentaContable")
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	
	/**
	 */
	public DatosBancariosInsertDTO tipoCuenta(String[] tipoCuenta){
		this.tipoCuenta = tipoCuenta;
		return this;
	}
	
	@JsonProperty("tipoCuenta")
	public String[] getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String[] tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	
	/**
	 */
	public DatosBancariosInsertDTO revisionCuentas(Boolean revisionCuentas){
		this.revisionCuentas = revisionCuentas;
		return this;
	}
	
	@JsonProperty("revisionCuentas")
	public Boolean getRevisionCuentas() {
		return revisionCuentas;
	}
	public void setRevisionCuentas(Boolean revisionCuentas) {
		this.revisionCuentas = revisionCuentas;
	}
	
	
	/**
	 */
	public DatosBancariosInsertDTO motivo(String motivo){
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
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosBancariosInsertDTO datosBancariosInsertDTO = (DatosBancariosInsertDTO) o;
	    return  Objects.equals(this.idPersona, datosBancariosInsertDTO.idPersona) &&
	    		Objects.equals(this.idCuenta, datosBancariosInsertDTO.idCuenta) &&
	    		Objects.equals(this.iban, datosBancariosInsertDTO.iban) &&
	    		Objects.equals(this.tipoCuenta, datosBancariosInsertDTO.tipoCuenta) &&
	    		Objects.equals(this.titular, datosBancariosInsertDTO.titular) &&
	    		Objects.equals(this.revisionCuentas, datosBancariosInsertDTO.revisionCuentas) &&
	    		Objects.equals(this.cuentaContable, datosBancariosInsertDTO.cuentaContable) &&
	    		Objects.equals(this.motivo, datosBancariosInsertDTO.motivo) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idCuenta, iban, idInstitucion,tipoCuenta,titular,cuentaContable,revisionCuentas, motivo);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosInsertDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    tipoCuenta: ").append(toIndentedString(tipoCuenta)).append("\n");
	    sb.append("    titular: ").append(toIndentedString(titular)).append("\n");
	    sb.append("    cuentaContable: ").append(toIndentedString(cuentaContable)).append("\n");
	    sb.append("    revisionCuentas: ").append(toIndentedString(revisionCuentas)).append("\n");
	    sb.append("    motivo: ").append(toIndentedString(motivo)).append("\n");

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
