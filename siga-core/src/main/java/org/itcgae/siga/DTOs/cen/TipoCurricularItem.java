package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoCurricularItem {

	private String tipoCategoriaCurricular;
	private String codigoExterno;
	private String descripcion;
	
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
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    TipoCurricularItem tipoCurricularItem = (TipoCurricularItem) o;
	    return Objects.equals(this.tipoCategoriaCurricular, tipoCurricularItem.tipoCategoriaCurricular) &&
	    		Objects.equals(this.codigoExterno, tipoCurricularItem.codigoExterno) &&
	    		Objects.equals(this.descripcion, tipoCurricularItem.descripcion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipoCategoriaCurricular, codigoExterno, descripcion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TipoCurricularItem {\n");
	    
	    sb.append("    tipoCategoriaCurricular: ").append(toIndentedString(tipoCategoriaCurricular)).append("\n");
	    sb.append("    codigoExterno: ").append(toIndentedString(codigoExterno)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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
