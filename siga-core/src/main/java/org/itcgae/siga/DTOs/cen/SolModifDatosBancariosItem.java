package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModifDatosBancariosItem {

	private String idPersona;
	private String idCuenta;
    private String abonoCargo;
    private String digitoControl;
    private String abonoJCS;
    private String iban;
    private String codigoSucursal;
    private String numeroCuenta;
    private String titular;
	
    public SolModifDatosBancariosItem idPersona(String idPersona){
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
	
	public SolModifDatosBancariosItem idCuenta(String idCuenta){
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
	
	public SolModifDatosBancariosItem abonoCargo(String abonoCargo){
		this.abonoCargo = abonoCargo;
		return this;
	}
	
	@JsonProperty("abonoCargo")
	public String getAbonoCargo() {
		return abonoCargo;
	}
	public void setAbonoCargo(String abonoCargo) {
		this.abonoCargo = abonoCargo;
	}
	
	public SolModifDatosBancariosItem digitoControl(String digitoControl){
		this.digitoControl = digitoControl;
		return this;
	}
	
	@JsonProperty("digitoControl")
	public String getDigitoControl() {
		return digitoControl;
	}
	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}
	
	public SolModifDatosBancariosItem abonoJCS(String abonoJCS){
		this.abonoJCS = abonoJCS;
		return this;
	}
	
	@JsonProperty("abonoJCS")
	public String getAbonoJCS() {
		return abonoJCS;
	}
	public void setAbonoJCS(String abonoJCS) {
		this.abonoJCS = abonoJCS;
	}
	
	public SolModifDatosBancariosItem iban(String iban){
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
	
	public SolModifDatosBancariosItem codigoSucursal(String codigoSucursal){
		this.codigoSucursal = codigoSucursal;
		return this;
	}
	
	@JsonProperty("codigoSucursal")
	public String getCodigoSucursal() {
		return codigoSucursal;
	}
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}
	
	public SolModifDatosBancariosItem numeroCuenta(String numeroCuenta){
		this.numeroCuenta = numeroCuenta;
		return this;
	}
	
	@JsonProperty("numeroCuenta")
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	public SolModifDatosBancariosItem titular(String titular){
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
	
	@Override
	public String toString() {
		return "SolModifDatosBancariosItem [idPersona=" + idPersona + ", idCuenta=" + idCuenta + ", abonoCargo=" + abonoCargo + ", digitoControl="
				+ digitoControl + ", abonoJCS=" + abonoJCS + ", iban=" + iban + ", codigoSucursal=" + codigoSucursal
				+ ", numeroCuenta=" + numeroCuenta + ", titular=" + titular + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abonoCargo == null) ? 0 : abonoCargo.hashCode());
		result = prime * result + ((abonoJCS == null) ? 0 : abonoJCS.hashCode());
		result = prime * result + ((codigoSucursal == null) ? 0 : codigoSucursal.hashCode());
		result = prime * result + ((digitoControl == null) ? 0 : digitoControl.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result + ((idCuenta == null) ? 0 : idCuenta.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((numeroCuenta == null) ? 0 : numeroCuenta.hashCode());
		result = prime * result + ((titular == null) ? 0 : titular.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolModifDatosBancariosItem other = (SolModifDatosBancariosItem) obj;
		if (abonoCargo == null) {
			if (other.abonoCargo != null)
				return false;
		} else if (!abonoCargo.equals(other.abonoCargo))
			return false;
		if (abonoJCS == null) {
			if (other.abonoJCS != null)
				return false;
		} else if (!abonoJCS.equals(other.abonoJCS))
			return false;
		if (codigoSucursal == null) {
			if (other.codigoSucursal != null)
				return false;
		} else if (!codigoSucursal.equals(other.codigoSucursal))
			return false;
		if (digitoControl == null) {
			if (other.digitoControl != null)
				return false;
		} else if (!digitoControl.equals(other.digitoControl))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (idCuenta == null) {
			if (other.idCuenta != null)
				return false;
		} else if (!idCuenta.equals(other.idCuenta))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (numeroCuenta == null) {
			if (other.numeroCuenta != null)
				return false;
		} else if (!numeroCuenta.equals(other.numeroCuenta))
			return false;
		if (titular == null) {
			if (other.titular != null)
				return false;
		} else if (!titular.equals(other.titular))
			return false;
		return true;
	}
}
