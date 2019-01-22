package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaDatosCurricularesItem {

	private String idInstitucion;
	private String idPersona;
	private String fechaDesde;
	private String fechaHasta;
	private String fechaBaja;
	private String fechaMovimiento;
	private Date dateFechaInicio;
	private Date dateFechaFin;
//	private Date fechaBajaDate;
	private Date dateFechaMovimiento;
	private String descripcion;
	private String categoriaCurricular;
	private String tipoSubtipo;
	private String idCv;
	private String idTipoCv;
	private String idTipoCvSubtipo1;
	private String idTipoCvSubtipo2;
	private String creditos;
	private String motivo;
	private String certificado;
	private String isLetrado;
	
	/**
	 */
	

	public FichaDatosCurricularesItem certificado(String certificado){
		this.certificado = certificado;
		return this;
	}
	
	@JsonProperty("certificado")
	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
	
	/**
	 */
	

	public FichaDatosCurricularesItem dateFechaFin(Date dateFechaFin){
		this.dateFechaFin = dateFechaFin;
		return this;
	}
	
	@JsonProperty("dateFechaFin")
	public Date getFechaHastaDate() {
		return dateFechaFin;
	}

	public void setFechaHastaDate(Date dateFechaFin) {
		this.dateFechaFin = dateFechaFin;
	}
	
	/**
	 * 
	 * @param idPersona
	 * @return
	 */
	/**
	 */
	

	public FichaDatosCurricularesItem fechaMovimiento(Date dateFechaMovimiento){
		this.dateFechaMovimiento = dateFechaMovimiento;
		return this;
	}
	
	@JsonProperty("dateFechaMovimiento")
	public Date getFechaMovimientoDate() {
		return dateFechaMovimiento;
	}

	public void setFechaMovimientoDate(Date dateFechaMovimiento) {
		this.dateFechaMovimiento = dateFechaMovimiento;
	}
	
	/**
	 * 
	 * @param idPersona
	 * @return
	 */
	
	
	
	public FichaDatosCurricularesItem fechaBaja(String fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}
	
	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	/**
	 * 
	 * @param idPersona
	 * @return
	 */
	/**
	 */
	

	public FichaDatosCurricularesItem fechaMovimiento(String fechaMovimiento){
		this.fechaMovimiento = fechaMovimiento;
		return this;
	}
	
	@JsonProperty("fechaMovimiento")
	public String getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(String fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
	/**
	 * 
	 * @param idPersona
	 * @return
	 */
	
	
	/**
	 */
	

	public FichaDatosCurricularesItem idInstitucion(String idInstitucion){
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
	 * @param idPersona
	 * @return
	 */

	public FichaDatosCurricularesItem idPersona(String idPersona){
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
	 * @param fechaInicio
	 * @return
	 */
	
	public FichaDatosCurricularesItem creditos(String creditos){
		this.creditos = creditos;
		return this;
	}
	
	@JsonProperty("creditos")
	public String getCreditos() {
		return creditos;
	}

	public void setCredito(String creditos) {
		this.creditos = creditos;
	}
	
	public FichaDatosCurricularesItem fechaDesde(String fechaDesde){
		this.fechaDesde = fechaDesde;
		return this;
	}
	
	@JsonProperty("fechaDesde")
	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	
	public FichaDatosCurricularesItem fechaHasta(String fechaHasta){
		this.fechaHasta = fechaHasta;
		return this;
	}
	
	@JsonProperty("fechaHasta")
	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	
	
	
	
	
	
	
	
	
	
	

	
//	public FichaDatosCurricularesItem fechaDesdeDate(Date fechaDesdeDate){
//		this.fechaDesdeDate = fechaDesdeDate;
//		return this;
//	}
//	
//	@JsonProperty("fechaDesdeDate")
//	public Date getFechaDesdeDate() {
//		return fechaDesdeDate;
//	}
//
//	public void setFechaDesdeDate(Date fechaDesdeDate) {
//		this.fechaDesdeDate = fechaDesdeDate;
//	}
//
//	
	public FichaDatosCurricularesItem dateFechaInicio(Date dateFechaInicio){
		this.dateFechaInicio = dateFechaInicio;
		return this;
	}
	
	@JsonProperty("dateFechaInicio")
	public Date getFechaDesdeDate() {
		return dateFechaInicio;
	}

	public void setFechaDesdeDate(Date dateFechaInicio) {
		this.dateFechaInicio = dateFechaInicio;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public FichaDatosCurricularesItem descripcion(String descripcion){
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

	public FichaDatosCurricularesItem categoriaCurricular(String categoriaCurricular){
		this.categoriaCurricular = categoriaCurricular;
		return this;
	}
	
	@JsonProperty("categoriaCurricular")
	public String getCategoriaCurricular() {
		return categoriaCurricular;
	}

	public void setCategoriaCurricular(String categoriaCurricular) {
		this.categoriaCurricular = categoriaCurricular;
	}

	public FichaDatosCurricularesItem tipoSubtipo(String tipoSubtipo){
		this.tipoSubtipo = tipoSubtipo;
		return this;
	}
	
	@JsonProperty("tipoSubtipo")
	public String getTipoSubtipo() {
		return tipoSubtipo;
	}

	public void setTipoSubtipo(String tipoSubtipo) {
		this.tipoSubtipo = tipoSubtipo;
	}
	
	

	public FichaDatosCurricularesItem idCv(String idCv){
		this.idCv = idCv;
		return this;
	}
	
	@JsonProperty("idCv")
	public String getIdCv() {
		return idCv;
	}

	public void setIdCv(String idCv) {
		this.idCv = idCv;
	}
	

	public FichaDatosCurricularesItem idTipoCv(String idTipoCv){
		this.idTipoCv = idTipoCv;
		return this;
	}
	
	@JsonProperty("idTipoCv")
	public String getIdTipoCv() {
		return idTipoCv;
	}

	public void setIdTipoCv(String idTipoCv) {
		this.idTipoCv = idTipoCv;
	}

	public FichaDatosCurricularesItem idTipoCvSubtipo1(String idTipoCvSubtipo1){
		this.idTipoCvSubtipo1 = idTipoCvSubtipo1;
		return this;
	}
	
	@JsonProperty("idTipoCvSubtipo1")
	public String getIdTipoCvSubtipo1() {
		return idTipoCvSubtipo1;
	}

	public void setIdTipoCvSubtipo1(String idTipoCvSubtipo1) {
		this.idTipoCvSubtipo1 = idTipoCvSubtipo1;
	}

	public FichaDatosCurricularesItem idTipoCvSubtipo2(String idTipoCvSubtipo2){
		this.idTipoCvSubtipo2 = idTipoCvSubtipo2;
		return this;
	}
	
	@JsonProperty("idTipoCvSubtipo2")
	public String getIdTipoCvSubtipo2() {
		return idTipoCvSubtipo2;
	}

	public void setIdTipoCvSubtipo2(String idTipoCvSubtipo2) {
		this.idTipoCvSubtipo2 = idTipoCvSubtipo2;
	}


	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    FichaDatosCurricularesItem fichaDatosCurricularesItem = (FichaDatosCurricularesItem) o;
	    return  Objects.equals(this.idPersona, fichaDatosCurricularesItem.idPersona) &&
	    		Objects.equals(this.fechaBaja, fichaDatosCurricularesItem.fechaBaja) &&
	    Objects.equals(this.fechaDesde, fichaDatosCurricularesItem.fechaDesde) &&
	    Objects.equals(this.fechaHasta, fichaDatosCurricularesItem.fechaHasta) &&
	    Objects.equals(this.descripcion, fichaDatosCurricularesItem.descripcion) &&
	    Objects.equals(this.categoriaCurricular, fichaDatosCurricularesItem.categoriaCurricular) &&
	    Objects.equals(this.tipoSubtipo, fichaDatosCurricularesItem.tipoSubtipo) &&
	    Objects.equals(this.idTipoCv, fichaDatosCurricularesItem.idTipoCv) &&
	    Objects.equals(this.idTipoCvSubtipo1, fichaDatosCurricularesItem.idTipoCvSubtipo1) &&
	    Objects.equals(this.idTipoCvSubtipo2, fichaDatosCurricularesItem.idTipoCvSubtipo2) &&
	    Objects.equals(this.idInstitucion, fichaDatosCurricularesItem.idInstitucion)&&
	    Objects.equals(this.fechaMovimiento, fichaDatosCurricularesItem.fechaMovimiento)&&
	    Objects.equals(this.certificado, fichaDatosCurricularesItem.certificado);

	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, fechaBaja, fechaDesde, fechaHasta, descripcion, categoriaCurricular, tipoSubtipo, idTipoCv, idTipoCvSubtipo1, idTipoCvSubtipo2, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaDatosCurricularesItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    fechaDesde: ").append(toIndentedString(fechaDesde)).append("\n");
	    sb.append("    fechaHasta: ").append(toIndentedString(fechaHasta)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
	    sb.append("    categoriaCurricular: ").append(toIndentedString(categoriaCurricular)).append("\n");
	    sb.append("    tipoSubtipo: ").append(toIndentedString(tipoSubtipo)).append("\n");
	    sb.append("    idTipoCv: ").append(toIndentedString(idTipoCv)).append("\n");
	    sb.append("    idTipoCvSubtipo1: ").append(toIndentedString(idTipoCvSubtipo1)).append("\n");
	    sb.append("    idTipoCvSubtipo2: ").append(toIndentedString(idTipoCvSubtipo2)).append("\n");
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

	public String getIsLetrado() {
		return isLetrado;
	}

	public void setIsLetrado(String isLetrado) {
		this.isLetrado = isLetrado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
