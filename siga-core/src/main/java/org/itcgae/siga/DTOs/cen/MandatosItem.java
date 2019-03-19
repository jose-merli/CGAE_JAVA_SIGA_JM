package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MandatosItem {

	private String idPersona;
	private String idCuenta;
	private String idInstitucion;
	private String referenciaProducto;
	private String esquemaProducto;
	private String tipoPagoProducto;
	private String idMandatoProducto;
	private String referenciaServicio;
	private String esquemaServicio;
	private String tipoPagoServicio;
	private String idMandatoServicio;
	private String tipoIdProducto;
	private String tipoIdServicio;
	private String identifServicio;
	private String identifProducto;
	
	/**
	 *
	 */
	public MandatosItem idCuenta(String idCuenta){
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
	public MandatosItem idMandatoProducto(String idMandatoProducto){
		this.idMandatoProducto = idMandatoProducto;
		return this;
	}
	
	
	@JsonProperty("idMandatoProducto")
	public String getIdMandatoProducto() {
		return idMandatoProducto;
	}
	
	
	public void setIdMandato(String idMandatoProducto) {
		this.idMandatoProducto = idMandatoProducto;
	}
	
	/**
	 *
	 */
	public MandatosItem idMandatoServicio(String idMandatoServicio){
		this.idMandatoServicio = idMandatoServicio;
		return this;
	}
	
	
	@JsonProperty("idMandatoServicio")
	public String getIdMandatoServicio() {
		return idMandatoServicio;
	}
	
	
	public void setIdMandatoServicio(String idMandatoServicio) {
		this.idMandatoServicio = idMandatoServicio;
	}
	
	/**
	 */
	public MandatosItem idPersona(String idPersona){
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
	public MandatosItem referenciaProducto(String referenciaProducto){
		this.referenciaProducto = referenciaProducto;
		return this;
	}
	
	@JsonProperty("referenciaProducto")
	public String getReferenciaProducto() {
		return referenciaProducto;
	}
	public void setReferenciaProducto(String referenciaProducto) {
		this.referenciaProducto = referenciaProducto;
	}
	
	
	/**
	 */
	public MandatosItem esquemaProducto(String esquemaProducto){
		this.esquemaProducto = esquemaProducto;
		return this;
	}
	
	@JsonProperty("esquemaProducto")
	public String getEsquemaProducto() {
		return esquemaProducto;
	}
	public void setEsquemaProducto(String esquemaProducto) {
		this.esquemaProducto = esquemaProducto;
	}
	
	/**
	 */
	public MandatosItem tipoPagoProducto(String tipoPagoProducto){
		this.tipoPagoProducto = tipoPagoProducto;
		return this;
	}
	
	@JsonProperty("tipoPagoProducto")
	public String getTipoPagoProducto() {
		return tipoPagoProducto;
	}
	public void setTipoPagoProducto(String tipoPagoProducto) {
		this.tipoPagoProducto = tipoPagoProducto;
	}
	
	/**
	 */
	public MandatosItem referenciaServicio(String referenciaServicio){
		this.referenciaServicio = referenciaServicio;
		return this;
	}
	
	@JsonProperty("referenciaServicio")
	public String getReferenciaServicio() {
		return referenciaServicio;
	}
	public void setReferenciaServicio(String referenciaServicio) {
		this.referenciaServicio = referenciaServicio;
	}
	
	/**
	 */
	public MandatosItem esquemaServicio(String esquemaServicio){
		this.esquemaServicio = esquemaServicio;
		return this;
	}
	
	@JsonProperty("esquemaServicio")
	public String getEsquemaServicio() {
		return esquemaServicio;
	}
	public void setEsquemaServicio(String esquemaServicio) {
		this.esquemaServicio = esquemaServicio;
	}
	
	/**
	 */
	public MandatosItem tipoPagoServicio(String tipoPagoServicio){
		this.tipoPagoServicio = tipoPagoServicio;
		return this;
	}
	
	@JsonProperty("tipoPagoServicio")
	public String getTipoPagoServicio() {
		return tipoPagoServicio;
	}
	public void setTipoPagoServicio(String tipoPagoServicio) {
		this.tipoPagoServicio = tipoPagoServicio;
	}
	
	/**
	 */
	public MandatosItem idInstitucion(String idInstitucion){
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
	    MandatosItem personaJuridicaSearchDTO = (MandatosItem) o;
	    return  Objects.equals(this.idPersona, personaJuridicaSearchDTO.idPersona) &&
	    		Objects.equals(this.idInstitucion, personaJuridicaSearchDTO.idInstitucion) &&
	    		Objects.equals(this.idCuenta, personaJuridicaSearchDTO.idCuenta) &&
	    		Objects.equals(this.referenciaProducto, personaJuridicaSearchDTO.referenciaProducto) &&
	    		Objects.equals(this.esquemaProducto, personaJuridicaSearchDTO.esquemaProducto) &&
	    		Objects.equals(this.tipoPagoProducto, personaJuridicaSearchDTO.tipoPagoProducto) &&
	    		Objects.equals(this.idMandatoProducto, personaJuridicaSearchDTO.idMandatoProducto) &&
	    		Objects.equals(this.referenciaServicio, personaJuridicaSearchDTO.referenciaServicio) &&
	    		Objects.equals(this.tipoPagoServicio, personaJuridicaSearchDTO.tipoPagoServicio) &&
	    		Objects.equals(this.esquemaServicio, personaJuridicaSearchDTO.esquemaServicio) &&
	    		Objects.equals(this.idMandatoServicio, personaJuridicaSearchDTO.idMandatoServicio) 
	    		;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion,idCuenta,referenciaProducto,esquemaProducto,tipoPagoProducto,idMandatoProducto,referenciaServicio,tipoPagoServicio,esquemaServicio,idMandatoServicio);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MandatosItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idCuenta: ").append(toIndentedString(idCuenta)).append("\n");
	    sb.append("    referenciaProducto: ").append(toIndentedString(referenciaProducto)).append("\n");
	    sb.append("    esquemaProducto: ").append(toIndentedString(esquemaProducto)).append("\n");
	    sb.append("    tipoPagoProducto: ").append(toIndentedString(tipoPagoProducto)).append("\n");
	    sb.append("    idMandatoProducto: ").append(toIndentedString(idMandatoProducto)).append("\n");
	    sb.append("    referenciaServicio: ").append(toIndentedString(referenciaServicio)).append("\n");
	    sb.append("    tipoPagoServicio: ").append(toIndentedString(tipoPagoServicio)).append("\n");
	    sb.append("    esquemaServicio: ").append(toIndentedString(esquemaServicio)).append("\n");
	    sb.append("    idMandatoServicio ").append(toIndentedString(idMandatoServicio)).append("\n");

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


	public String getTipoIdProducto() {
		return tipoIdProducto;
	}


	public void setTipoIdProducto(String tipoIdProducto) {
		this.tipoIdProducto = tipoIdProducto;
	}


	public String getTipoIdServicio() {
		return tipoIdServicio;
	}


	public void setTipoIdServicio(String tipoIdServicio) {
		this.tipoIdServicio = tipoIdServicio;
	}


	public String getIdentifServicio() {
		return identifServicio;
	}


	public void setIdentifServicio(String identifServicio) {
		this.identifServicio = identifServicio;
	}


	public String getIdentifProducto() {
		return identifProducto;
	}


	public void setIdentifProducto(String identifProducto) {
		this.identifProducto = identifProducto;
	}
	
	
}
