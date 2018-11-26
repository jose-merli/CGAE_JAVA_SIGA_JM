package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModifDatosCurricularesItem {

	private String categoriaCurricular;
    private String tipoCurricular;
    private String subtiposCurriculares;
    private String fechaDesde;
    private String fechaHasta;
    private String idCv;
    private String idSolicitud;
    private String descripcion;
	private String idInstitucion;
	private String idPersona;
	private String fechaBaja;
	private String fechaMovimiento;
	private Date dateFechaInicio;
	private Date dateFechaFin;
	private Date dateFechaMovimiento;
	private String tipoSubtipo;
	private String idTipoCv;
	private String idTipoCvSubtipo1;
	private String idTipoCvSubtipo2;
	private String creditos;
	private String motivo;
	private String certificado;
	private String isLetrado;
    
    @JsonProperty("motivo")
	public String getCategoriaCurricular() {
		return categoriaCurricular;
	}
	public void setCategoriaCurricular(String categoriaCurricular) {
		this.categoriaCurricular = categoriaCurricular;
	}
	
    @JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
    @JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
    @JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
    @JsonProperty("fechaMovimiento")
	public String getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(String fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
    @JsonProperty("dateFechaInicio")
	public Date getDateFechaInicio() {
		return dateFechaInicio;
	}
	public void setDateFechaInicio(Date dateFechaInicio) {
		this.dateFechaInicio = dateFechaInicio;
	}
	
    @JsonProperty("dateFechaFin")
	public Date getDateFechaFin() {
		return dateFechaFin;
	}
	public void setDateFechaFin(Date dateFechaFin) {
		this.dateFechaFin = dateFechaFin;
	}
	
    @JsonProperty("dateFechaMovimiento")
	public Date getDateFechaMovimiento() {
		return dateFechaMovimiento;
	}
	public void setDateFechaMovimiento(Date dateFechaMovimiento) {
		this.dateFechaMovimiento = dateFechaMovimiento;
	}
	
    @JsonProperty("tipoSubtipo")
	public String getTipoSubtipo() {
		return tipoSubtipo;
	}
	public void setTipoSubtipo(String tipoSubtipo) {
		this.tipoSubtipo = tipoSubtipo;
	}
	
    @JsonProperty("idTipoCv")
	public String getIdTipoCv() {
		return idTipoCv;
	}
	public void setIdTipoCv(String idTipoCv) {
		this.idTipoCv = idTipoCv;
	}
	
    @JsonProperty("idTipoCvSubtipo1")
	public String getIdTipoCvSubtipo1() {
		return idTipoCvSubtipo1;
	}
	public void setIdTipoCvSubtipo1(String idTipoCvSubtipo1) {
		this.idTipoCvSubtipo1 = idTipoCvSubtipo1;
	}
	
    @JsonProperty("idTipoCvSubtipo2")
	public String getIdTipoCvSubtipo2() {
		return idTipoCvSubtipo2;
	}
	public void setIdTipoCvSubtipo2(String idTipoCvSubtipo2) {
		this.idTipoCvSubtipo2 = idTipoCvSubtipo2;
	}
	
    @JsonProperty("creditos")
	public String getCreditos() {
		return creditos;
	}
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	
    @JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
    @JsonProperty("certificado")
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
    @JsonProperty("isLetrado")
	public String getIsLetrado() {
		return isLetrado;
	}
	public void setIsLetrado(String isLetrado) {
		this.isLetrado = isLetrado;
	}
	
	@JsonProperty("idSolicitud")
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	@JsonProperty("motivo")
	public String getTipoCurricular() {
		return tipoCurricular;
	}
	public void setTipoCurricular(String tipoCurricular) {
		this.tipoCurricular = tipoCurricular;
	}
	
	@JsonProperty("idCv")
	public String getIdCv() {
		return idCv;
	}
	public void setIdCv(String idCv) {
		this.idCv = idCv;
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
