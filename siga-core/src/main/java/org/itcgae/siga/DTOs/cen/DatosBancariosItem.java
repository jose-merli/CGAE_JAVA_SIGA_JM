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
	private String ibanFormateado;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaModificacion;
	private String uso;
	private String bic;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFirmaServicios;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFirmaProductos;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaBaja;
	private String nifTitular;
	private String banco;
	private String[] tipoCuenta;
	private String cuentaContable;
	
	
	

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
	public DatosBancariosItem ibanFormateado(String ibanFormateado){
		this.ibanFormateado = ibanFormateado;
		return this;
	}
	
	@JsonProperty("ibanFormateado")
	public String getIbanFormateado() {
		return ibanFormateado;
	}
	public void setIbanFormateado(String ibanFormateado) {
		this.ibanFormateado = ibanFormateado;
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
	
	/**
	 */
	public DatosBancariosItem nifTitular(String nifTitular){
		this.nifTitular = nifTitular;
		return this;
	}
	
	@JsonProperty("nifTitular")
	public String getNifTitular() {
		return nifTitular;
	}
	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}
	
	/**
	 */
	public DatosBancariosItem banco(String banco){
		this.banco = banco;
		return this;
	}
	
	@JsonProperty("banco")
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	/**
	 */
	public DatosBancariosItem cuentaContable(String cuentaContable){
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
	public DatosBancariosItem tipoCuenta(String[] tipoCuenta){
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
	    DatosBancariosItem personaJuridicaSearchDTO = (DatosBancariosItem) o;
	    return Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, personaJuridicaSearchDTO.idInstitucion) &&
	    		Objects.equals(this.idCuenta, personaJuridicaSearchDTO.idCuenta) &&
	    		Objects.equals(this.titular, personaJuridicaSearchDTO.titular) &&
	    		Objects.equals(this.bic, personaJuridicaSearchDTO.bic) &&
	    		Objects.equals(this.uso, personaJuridicaSearchDTO.uso) &&
	    		Objects.equals(this.iban, personaJuridicaSearchDTO.iban) &&
	    		Objects.equals(this.ibanFormateado, personaJuridicaSearchDTO.ibanFormateado) &&
	    		Objects.equals(this.banco, personaJuridicaSearchDTO.banco) &&
	    		Objects.equals(this.tipoCuenta, personaJuridicaSearchDTO.tipoCuenta) &&
	    		Objects.equals(this.cuentaContable, personaJuridicaSearchDTO.cuentaContable) &&
	    		Objects.equals(this.nifTitular, personaJuridicaSearchDTO.nifTitular) &&
	    		Objects.equals(this.fechaBaja, personaJuridicaSearchDTO.fechaBaja) &&
	    		Objects.equals(this.fechaFirmaProductos, personaJuridicaSearchDTO.fechaFirmaProductos) &&
	    		Objects.equals(this.fechaFirmaServicios, personaJuridicaSearchDTO.fechaFirmaServicios);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,idCuenta,titular,iban,ibanFormateado,uso,bic,banco,tipoCuenta,nifTitular,cuentaContable,fechaFirmaProductos,fechaFirmaServicios,fechaBaja);
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
	    sb.append("    ibanFormateado: ").append(toIndentedString(ibanFormateado)).append("\n");
	    sb.append("    bic: ").append(toIndentedString(bic)).append("\n");
	    sb.append("    cuentaContable: ").append(toIndentedString(cuentaContable)).append("\n");
	    sb.append("    nifTitular: ").append(toIndentedString(nifTitular)).append("\n");
	    sb.append("    banco: ").append(toIndentedString(banco)).append("\n");
	    sb.append("    tipoCuenta: ").append(toIndentedString(tipoCuenta)).append("\n");
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


	public Date getFechaModificacion() {
		return fechaModificacion;
	}


	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	
}
