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
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosBancariosInsertDTO personaJuridicaSearchDTO = (DatosBancariosInsertDTO) o;
	    return  Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.iban, personaJuridicaSearchDTO.iban) &&
	    		Objects.equals(this.tipoCuenta, personaJuridicaSearchDTO.tipoCuenta) &&
	    		Objects.equals(this.titular, personaJuridicaSearchDTO.titular) &&
	    		Objects.equals(this.cuentaContable, personaJuridicaSearchDTO.cuentaContable) ;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, iban, idInstitucion,tipoCuenta,titular,cuentaContable);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosInsertDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    tipoCuenta: ").append(toIndentedString(tipoCuenta)).append("\n");
	    sb.append("    titular: ").append(toIndentedString(titular)).append("\n");
	    sb.append("    cuentaContable: ").append(toIndentedString(cuentaContable)).append("\n");

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
