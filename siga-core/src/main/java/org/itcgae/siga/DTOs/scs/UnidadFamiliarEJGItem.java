package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class UnidadFamiliarEJGItem {
	
    private String uf_idTipoejg;
    private String uf_anio;
    private String uf_numero;
    private String uf_idPersona;
    private String uf_solicitante;
    private String solicitantePpal;
    private String uf_enCalidad;
    private String pjg_nif;
    private String pjg_nombrecompleto;
    private String pjg_nombre;
    private String pjg_ape1;
    private String pjg_ape2;
    private String pjg_direccion;
    private String uf_idInstitucion;
//    private String nombrePrincipal;
//    private String apellido1Principal;
//    private String apellido2Principal;
    private String pd_descripcion;
    private String estado;
    private Date fechaSolicitud;
    private String fechaBaja;
    
    private Short circunsExcep;
    private Short incapacitado;
    
    private Short idParentesco;
    private Short idTipoGrupoLab;
    private Short idTipoIngreso;
    
    private String descrIngrAnuales;
    private String bienesInmu;
    private String bienesMu;
    private String otrosBienes;
    
    private BigDecimal impIngrAnuales;
    private BigDecimal impBienesInmu;
    private BigDecimal impBienesMu;
    private BigDecimal impOtrosBienes;
    
    private String observaciones;
    
    private String representante;
    private String direccionRepresentante;
    private String nifRepresentante;
    
	/**
	 **/
	public UnidadFamiliarEJGItem uf_idTipoejg(String uf_idTipoejg) {
		this.uf_idTipoejg = uf_idTipoejg;
		return this;
	}

	@JsonProperty("uf_idTipoejg")
	public String getUf_idTipoejg() {
		return uf_idTipoejg;
	}
	public void setUf_idTipoejg(String uf_idTipoejg) {
		this.uf_idTipoejg = uf_idTipoejg;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_anio(String uf_anio) {
		this.uf_anio = uf_anio;
		return this;
	}

	@JsonProperty("uf_anio")
	public String getUf_anio() {
		return uf_anio;
	}
	public void setUf_anio(String uf_anio) {
		this.uf_anio = uf_anio;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_numero(String uf_numero) {
		this.uf_numero = uf_numero;
		return this;
	}

	@JsonProperty("uf_numero")
	public String getUf_numero() {
		return uf_numero;
	}
	public void setUf_numero(String uf_numero) {
		this.uf_numero = uf_numero;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_idPersona(String uf_idPersona) {
		this.uf_idPersona = uf_idPersona;
		return this;
	}

	@JsonProperty("uf_idPersona")
	public String getUf_idPersona() {
		return uf_idPersona;
	}
	public void setUf_idPersona(String uf_idPersona) {
		this.uf_idPersona = uf_idPersona;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_solicitante(String uf_solicitante) {
		this.uf_solicitante = uf_solicitante;
		return this;
	}

	@JsonProperty("uf_solicitante")
	public String getUf_solicitante() {
		return uf_solicitante;
	}
	public void setUf_solicitante(String uf_solicitante) {
		this.uf_solicitante = uf_solicitante;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_enCalidad(String uf_enCalidad) {
		this.uf_enCalidad = uf_enCalidad;
		return this;
	}

	@JsonProperty("uf_enCalidad")
	public String getUf_enCalidad() {
		return uf_enCalidad;
	}
	public void setUf_enCalidad(String uf_enCalidad) {
		this.uf_enCalidad = uf_enCalidad;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_nif(String pjg_nif) {
		this.pjg_nif = pjg_nif;
		return this;
	}

	@JsonProperty("pjg_nif")
	public String getPjg_nif() {
		return pjg_nif;
	}
	public void setPjg_nif(String pjg_nif) {
		this.pjg_nif = pjg_nif;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_nombre(String pjg_nombre) {
		this.pjg_nombre = pjg_nombre;
		return this;
	}

	@JsonProperty("pjg_nombre")
	public String getPjg_nombre() {
		return pjg_nombre;
	}
	public void setPjg_nombre(String pjg_nombre) {
		this.pjg_nombre = pjg_nombre;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_ape1(String pjg_ape1) {
		this.pjg_ape1 = pjg_ape1;
		return this;
	}

	@JsonProperty("pjg_ape1")
	public String getPjg_ape1() {
		return pjg_ape1;
	}
	public void setPjg_ape1(String pjg_ape1) {
		this.pjg_ape1 = pjg_ape1;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_ape2(String pjg_ape2) {
		this.pjg_ape2 = pjg_ape2;
		return this;
	}

	@JsonProperty("pjg_ape2")
	public String getPjg_ape2() {
		return pjg_ape2;
	}
	public void setPjg_ape2(String pjg_ape2) {
		this.pjg_ape2 = pjg_ape2;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_direccion(String pjg_direccion) {
		this.pjg_direccion = pjg_direccion;
		return this;
	}

	@JsonProperty("pjg_direccion")
	public String getPjg_direccion() {
		return pjg_direccion;
	}
	public void setPjg_direccion(String pjg_direccion) {
		this.pjg_direccion = pjg_direccion;
	}

	/**
	 **/
	public UnidadFamiliarEJGItem pd_descripcion(String pd_descripcion) {
		this.pd_descripcion = pd_descripcion;
		return this;
	}

	@JsonProperty("pd_descripcion")
	public String getPd_descripcion() {
		return pd_descripcion;
	}
	public void setPd_descripcion(String pd_descripcion) {
		this.pd_descripcion = pd_descripcion;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem estado(String estado) {
		this.estado = estado;
		return this;
	}

	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem fechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
		return this;
	}

	@JsonProperty("fechaSolicitud")
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_nombrecompleto(String pjg_nombrecompleto) {
		this.pjg_nombrecompleto = pjg_nombrecompleto;
		return this;
	}

	@JsonProperty("pjg_nombrecompleto")
	public String getPjg_nombrecompleto() {
		return pjg_nombrecompleto;
	}

	public void setPjg_nombrecompleto(String pjg_nombrecompleto) {
		this.pjg_nombrecompleto = pjg_nombrecompleto;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem fechaBaja(String fechaBaja) {
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

	public Short getCircunsExcep() {
		return circunsExcep;
	}

	public void setCircunsExcep(Short circunsExcep) {
		this.circunsExcep = circunsExcep;
	}

	public Short getIncapacitado() {
		return incapacitado;
	}

	public void setIncapacitado(Short incapacitado) {
		this.incapacitado = incapacitado;
	}

	public Short getIdParentesco() {
		return idParentesco;
	}

	public void setIdParentesco(Short idParentesco) {
		this.idParentesco = idParentesco;
	}

	public Short getIdTipoGrupoLab() {
		return idTipoGrupoLab;
	}

	public void setIdTipoGrupoLab(Short idTipoGrupoLab) {
		this.idTipoGrupoLab = idTipoGrupoLab;
	}

	public Short getIdTipoIngreso() {
		return idTipoIngreso;
	}

	public void setIdTipoIngreso(Short idTipoIngreso) {
		this.idTipoIngreso = idTipoIngreso;
	}

	public String getDescrIngrAnuales() {
		return descrIngrAnuales;
	}

	public void setDescrIngrAnuales(String descrIngrAnuales) {
		this.descrIngrAnuales = descrIngrAnuales;
	}

	public String getBienesInmu() {
		return bienesInmu;
	}

	public void setBienesInmu(String bienesInmu) {
		this.bienesInmu = bienesInmu;
	}

	public String getBienesMu() {
		return bienesMu;
	}

	public void setBienesMu(String bienesMu) {
		this.bienesMu = bienesMu;
	}

	public String getOtrosBienes() {
		return otrosBienes;
	}

	public void setOtrosBienes(String otrosBienes) {
		this.otrosBienes = otrosBienes;
	}

	public BigDecimal getImpIngrAnuales() {
		return impIngrAnuales;
	}

	public void setImpIngrAnuales(BigDecimal impIngrAnuales) {
		this.impIngrAnuales = impIngrAnuales;
	}

	public BigDecimal getImpBienesInmu() {
		return impBienesInmu;
	}

	public void setImpBienesInmu(BigDecimal impBienesInmu) {
		this.impBienesInmu = impBienesInmu;
	}

	public BigDecimal getImpBienesMu() {
		return impBienesMu;
	}

	public void setImpBienesMu(BigDecimal impBienesMu) {
		this.impBienesMu = impBienesMu;
	}

	public BigDecimal getImpOtrosBienes() {
		return impOtrosBienes;
	}

	public void setImpOtrosBienes(BigDecimal impOtrosBienes) {
		this.impOtrosBienes = impOtrosBienes;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 **/
	public UnidadFamiliarEJGItem uf_idInstitucion(String uf_idInstitucion) {
		this.uf_idInstitucion = uf_idInstitucion;
		return this;
	}

	@JsonProperty("uf_idInstitucion")
	public String getUf_idInstitucion() {
		return uf_idInstitucion;
	}

	public void setUf_idInstitucion(String uf_idInstitucion) {
		this.uf_idInstitucion = uf_idInstitucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((fechaSolicitud == null) ? 0 : fechaSolicitud.hashCode());
		result = prime * result + ((pd_descripcion == null) ? 0 : pd_descripcion.hashCode());
		result = prime * result + ((pjg_ape1 == null) ? 0 : pjg_ape1.hashCode());
		result = prime * result + ((pjg_ape2 == null) ? 0 : pjg_ape2.hashCode());
		result = prime * result + ((pjg_direccion == null) ? 0 : pjg_direccion.hashCode());
		result = prime * result + ((pjg_nif == null) ? 0 : pjg_nif.hashCode());
		result = prime * result + ((pjg_nombre == null) ? 0 : pjg_nombre.hashCode());
		result = prime * result + ((pjg_nombrecompleto == null) ? 0 : pjg_nombrecompleto.hashCode());
		result = prime * result + ((uf_anio == null) ? 0 : uf_anio.hashCode());
		result = prime * result + ((uf_enCalidad == null) ? 0 : uf_enCalidad.hashCode());
		result = prime * result + ((uf_idPersona == null) ? 0 : uf_idPersona.hashCode());
		result = prime * result + ((uf_idTipoejg == null) ? 0 : uf_idTipoejg.hashCode());
		result = prime * result + ((uf_idInstitucion == null) ? 0 : uf_idInstitucion.hashCode());
		result = prime * result + ((uf_numero == null) ? 0 : uf_numero.hashCode());
		result = prime * result + ((uf_solicitante == null) ? 0 : uf_solicitante.hashCode());
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
		UnidadFamiliarEJGItem other = (UnidadFamiliarEJGItem) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (fechaSolicitud == null) {
			if (other.fechaSolicitud != null)
				return false;
		} else if (!fechaSolicitud.equals(other.fechaSolicitud))
			return false;
		if (pd_descripcion == null) {
			if (other.pd_descripcion != null)
				return false;
		} else if (!pd_descripcion.equals(other.pd_descripcion))
			return false;
		if (pjg_ape1 == null) {
			if (other.pjg_ape1 != null)
				return false;
		} else if (!pjg_ape1.equals(other.pjg_ape1))
			return false;
		if (pjg_ape2 == null) {
			if (other.pjg_ape2 != null)
				return false;
		} else if (!pjg_ape2.equals(other.pjg_ape2))
			return false;
		if (pjg_direccion == null) {
			if (other.pjg_direccion != null)
				return false;
		} else if (!pjg_direccion.equals(other.pjg_direccion))
			return false;
		if (pjg_nif == null) {
			if (other.pjg_nif != null)
				return false;
		} else if (!pjg_nif.equals(other.pjg_nif))
			return false;
		if (pjg_nombre == null) {
			if (other.pjg_nombre != null)
				return false;
		} else if (!pjg_nombre.equals(other.pjg_nombre))
			return false;
		if (pjg_nombrecompleto == null) {
			if (other.pjg_nombrecompleto != null)
				return false;
		} else if (!pjg_nombrecompleto.equals(other.pjg_nombrecompleto))
			return false;
		if (uf_anio == null) {
			if (other.uf_anio != null)
				return false;
		} else if (!uf_anio.equals(other.uf_anio))
			return false;
		if (uf_enCalidad == null) {
			if (other.uf_enCalidad != null)
				return false;
		} else if (!uf_enCalidad.equals(other.uf_enCalidad))
			return false;
		if (uf_idPersona == null) {
			if (other.uf_idPersona != null)
				return false;
		} else if (!uf_idPersona.equals(other.uf_idPersona))
			return false;
		if (uf_idTipoejg == null) {
			if (other.uf_idTipoejg != null)
				return false;
		} else if (!uf_idTipoejg.equals(other.uf_idTipoejg))
			return false;
		if (uf_idInstitucion == null) {
			if (other.uf_idInstitucion != null)
				return false;
		} else if (!uf_idInstitucion.equals(other.uf_idInstitucion))
			return false;
		if (uf_numero == null) {
			if (other.uf_numero != null)
				return false;
		} else if (!uf_numero.equals(other.uf_numero))
			return false;
		if (uf_solicitante == null) {
			if (other.uf_solicitante != null)
				return false;
		} else if (!uf_solicitante.equals(other.uf_solicitante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnidadFamiliarEJGItem [uf_idTipoejg=" + uf_idTipoejg + ", uf_anio=" + uf_anio + ", uf_numero="
				+ uf_numero + ", uf_idPersona=" + uf_idPersona + ", uf_solicitante=" + uf_solicitante
				+ ", uf_enCalidad=" + uf_enCalidad + ", pjg_nif=" + pjg_nif + ", pjg_nombrecompleto="
				+ pjg_nombrecompleto + ", pjg_nombre=" + pjg_nombre + ", pjg_ape1=" + pjg_ape1 + ", pjg_ape2="
				+ pjg_ape2 + ", pjg_direccion=" + pjg_direccion + ", uf_idinstitucion=" + uf_idInstitucion
				+ ", pd_descripcion=" + pd_descripcion + ", estado=" + estado + ", fechaSolicitud=" + fechaSolicitud
				+ ", fechaBaja=" + fechaBaja + "]";
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getDireccionRepresentante() {
		return direccionRepresentante;
	}

	public void setDireccionRepresentante(String direccionRepresentante) {
		this.direccionRepresentante = direccionRepresentante;
	}

	public String getNifRepresentante() {
		return nifRepresentante;
	}

	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	public String getSolicitantePpal() {
		return solicitantePpal;
	}

	public void setSolicitantePpal(String solicitantePpal) {
		this.solicitantePpal = solicitantePpal;
	}

	
    
	
    
}
