package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoCurricularItem {

	private String idTipoCV;
	private String idTipoCvSubtipo1;
	private String tipoCategoriaCurricular;
	private String codigoExterno;
	private String descripcion;
	private String idInstitucion; 
	
	 @JsonFormat(pattern = "dd-MM-yyyy")
	  private Date fechaBaja = null;
	
	@JsonProperty("idTipoCV")
	public String getIdTipoCV() {
		return idTipoCV;
	}
	public void setIdTipoCV(String idTipoCV) {
		this.idTipoCV = idTipoCV;
	}
	@JsonProperty("idTipoCvSubtipo1")
	public String getIdTipoCvSubtipo1() {
		return idTipoCvSubtipo1;
	}
	public void setIdTipoCvSubtipo1(String idTipoCvSubtipo1) {
		this.idTipoCvSubtipo1 = idTipoCvSubtipo1;
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
	    TipoCurricularItem tipoCurricularItem = (TipoCurricularItem) o;
	    return Objects.equals(this.idTipoCV, tipoCurricularItem.idTipoCV) &&
	    		Objects.equals(this.idTipoCvSubtipo1, tipoCurricularItem.idTipoCvSubtipo1) &&
	    		Objects.equals(this.tipoCategoriaCurricular, tipoCurricularItem.tipoCategoriaCurricular) &&
	    		Objects.equals(this.codigoExterno, tipoCurricularItem.codigoExterno) &&
	    		Objects.equals(this.descripcion, tipoCurricularItem.descripcion) &&
	    		Objects.equals(this.fechaBaja, tipoCurricularItem.fechaBaja) &&
	    		Objects.equals(this.idInstitucion, tipoCurricularItem.idInstitucion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipoCategoriaCurricular, codigoExterno, descripcion, fechaBaja, idInstitucion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TipoCurricularItem {\n");
	    
	    sb.append("    idTipoCV: ").append(toIndentedString(idTipoCV)).append("\n");
	    sb.append("    idTipoCvSubtipo1: ").append(toIndentedString(idTipoCvSubtipo1)).append("\n");
	    sb.append("    tipoCategoriaCurricular: ").append(toIndentedString(tipoCategoriaCurricular)).append("\n");
	    sb.append("    codigoExterno: ").append(toIndentedString(codigoExterno)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
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
