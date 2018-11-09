package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubtipoCurricularItem {
	private String idTipoCV;
	private String idTipoCvSubtipo2;
	private String tipoCategoriaCurricular;
	private String codigoExterno;
	private String descripcion;
	 @JsonFormat(pattern = "dd-MM-yyyy")
	  private Date fechaBaja = null;
	
	@JsonProperty("idTipoCV")
	public String getIdTipoCV() {
		return idTipoCV;
	}
	public void setIdTipoCV(String idTipoCV) {
		this.idTipoCV = idTipoCV;
	}
	@JsonProperty("idTipoCvSubtipo2")
	public String getIdTipoCvSubtipo2() {
		return idTipoCvSubtipo2;
	}
	public void setIdTipoCvSubtipo2(String idTipoCvSubtipo2) {
		this.idTipoCvSubtipo2 = idTipoCvSubtipo2;
	}
	@JsonProperty("tipoCategoriaCurricular")
	public String getTipoCategoriaCurricular() {
		return tipoCategoriaCurricular;
	}
	public void setTipoCategoriaCurricular(String tipoCategoriaCurricular) {
		this.tipoCategoriaCurricular = tipoCategoriaCurricular;
	}
	
	@JsonProperty("codigoExterno")
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	    SubtipoCurricularItem subtipoCurricularItem = (SubtipoCurricularItem) o;
	    return Objects.equals(this.idTipoCV, subtipoCurricularItem.idTipoCV) &&
	    		Objects.equals(this.idTipoCvSubtipo2, subtipoCurricularItem.idTipoCvSubtipo2) &&
	    		Objects.equals(this.tipoCategoriaCurricular, subtipoCurricularItem.tipoCategoriaCurricular) &&
	    		Objects.equals(this.codigoExterno, subtipoCurricularItem.codigoExterno) &&
	    		Objects.equals(this.descripcion, subtipoCurricularItem.descripcion) &&
	    		Objects.equals(this.fechaBaja, subtipoCurricularItem.fechaBaja);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipoCategoriaCurricular, codigoExterno, descripcion, fechaBaja);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SubtipoCurricularItem {\n");
	    
	    sb.append("    idTipoCV: ").append(toIndentedString(idTipoCV)).append("\n");
	    sb.append("    idTipoCvSubtipo2: ").append(toIndentedString(idTipoCvSubtipo2)).append("\n");
	    sb.append("    tipoCategoriaCurricular: ").append(toIndentedString(tipoCategoriaCurricular)).append("\n");
	    sb.append("    codigoExterno: ").append(toIndentedString(codigoExterno)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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
