package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaDatosCurricularesItem {

	private String idInstitucion;
	private String idPersona;
	private String fechaInicio;
	private String fechaFin;
	private String descripcion;
	private String categoriaCurricular;
	private String tipoSubtipo;
	private String idCv;
	private String idTipoCv;
	private String idTipoCvSubtipo1;
	private String idTipoCvSubtipo2;
	private String creditos;
	
	

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

	
	
	public FichaDatosCurricularesItem fechaInicio(String fechaInicio){
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	@JsonProperty("fechaInicio")
	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	
	public FichaDatosCurricularesItem fechaFin(String fechaFin){
		this.fechaFin = fechaFin;
		return this;
	}
	
	@JsonProperty("fechaFin")
	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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
	    Objects.equals(this.fechaInicio, fichaDatosCurricularesItem.fechaInicio) &&
	    Objects.equals(this.fechaFin, fichaDatosCurricularesItem.fechaFin) &&
	    Objects.equals(this.descripcion, fichaDatosCurricularesItem.descripcion) &&
	    Objects.equals(this.categoriaCurricular, fichaDatosCurricularesItem.categoriaCurricular) &&
	    Objects.equals(this.tipoSubtipo, fichaDatosCurricularesItem.tipoSubtipo) &&
	    Objects.equals(this.idTipoCv, fichaDatosCurricularesItem.idTipoCv) &&
	    Objects.equals(this.idTipoCvSubtipo1, fichaDatosCurricularesItem.idTipoCvSubtipo1) &&
	    Objects.equals(this.idTipoCvSubtipo2, fichaDatosCurricularesItem.idTipoCvSubtipo2) &&
	    Objects.equals(this.idInstitucion, fichaDatosCurricularesItem.idInstitucion);

	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, fechaInicio, fechaFin, descripcion, categoriaCurricular, tipoSubtipo, idTipoCv, idTipoCvSubtipo1, idTipoCvSubtipo2, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaDatosCurricularesItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
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
	
}
