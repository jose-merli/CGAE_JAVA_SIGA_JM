package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModifDatosCurricularesItem {

	private String categoriaCurricular;
    private String tipoCurricular;
    private String subtiposCurriculares;
    private String fechaDesde;
    private String fechaHasta;
    private String descripcion;
    
    @JsonProperty("motivo")
	public String getCategoriaCurricular() {
		return categoriaCurricular;
	}
	public void setCategoriaCurricular(String categoriaCurricular) {
		this.categoriaCurricular = categoriaCurricular;
	}
	
	@JsonProperty("motivo")
	public String getTipoCurricular() {
		return tipoCurricular;
	}
	public void setTipoCurricular(String tipoCurricular) {
		this.tipoCurricular = tipoCurricular;
	}
	
	@JsonProperty("motivo")
	public String getSubtiposCurriculares() {
		return subtiposCurriculares;
	}
	public void setSubtiposCurriculares(String subtiposCurriculares) {
		this.subtiposCurriculares = subtiposCurriculares;
	}
	
	@JsonProperty("motivo")
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	@JsonProperty("motivo")
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	@JsonProperty("motivo")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripción) {
		this.descripcion = descripción;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaCurricular == null) ? 0 : categoriaCurricular.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
		result = prime * result + ((subtiposCurriculares == null) ? 0 : subtiposCurriculares.hashCode());
		result = prime * result + ((tipoCurricular == null) ? 0 : tipoCurricular.hashCode());
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
		SolModifDatosCurricularesItem other = (SolModifDatosCurricularesItem) obj;
		if (categoriaCurricular == null) {
			if (other.categoriaCurricular != null)
				return false;
		} else if (!categoriaCurricular.equals(other.categoriaCurricular))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaDesde == null) {
			if (other.fechaDesde != null)
				return false;
		} else if (!fechaDesde.equals(other.fechaDesde))
			return false;
		if (fechaHasta == null) {
			if (other.fechaHasta != null)
				return false;
		} else if (!fechaHasta.equals(other.fechaHasta))
			return false;
		if (subtiposCurriculares == null) {
			if (other.subtiposCurriculares != null)
				return false;
		} else if (!subtiposCurriculares.equals(other.subtiposCurriculares))
			return false;
		if (tipoCurricular == null) {
			if (other.tipoCurricular != null)
				return false;
		} else if (!tipoCurricular.equals(other.tipoCurricular))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SolModifDatosCurricularesItem [categoriaCurricular=" + categoriaCurricular + ", tipoCurricular="
				+ tipoCurricular + ", subtiposCurriculares=" + subtiposCurriculares + ", fechaDesde=" + fechaDesde
				+ ", fechaHasta=" + fechaHasta + ", descripción=" + descripcion + "]";
	}
}
