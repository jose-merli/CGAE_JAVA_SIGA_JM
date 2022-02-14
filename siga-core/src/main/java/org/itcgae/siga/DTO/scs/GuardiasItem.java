
package org.itcgae.siga.DTO.scs;

import java.util.Date;

public class GuardiasItem {

	private String idGuardia;
	private String idTurno;
	private String idOrdenacionColas;
	private String idPersonaUltimo;
	private String turno;
	private String nombre;
	private String descripcion;
	private String descripcionPago;
	private String descripcionFacturacion;
	private String area;
	private String materia;
	private String grupoZona;
	private String zona;
	private String obligatoriedad;
	private String duracion;
	private String validaJustificacion;
	private String partidaJudicial;
	private String jurisdiccion;
	private String grupoFacturacion;
	private String partidaPresupuestaria;
	private String tipoTurno;		
	private String tipoGuardia;
	private String tipoDia;
	private String tipoDiasGuardia;
	private String tipoDiasPeriodo;
	private String letradosGuardia;
	private String letradosIns;
	private String seleccionLaborables;
	private String seleccionFestivos;
	private String diasPeriodo;
	private String diasGuardia;
	private String diasSeparacionGuardias;
	private String envioCentralita;
	private String porGrupos;
	private Date fechabaja;
	private boolean historico;
	private String baremo;
	private String nDias;
	
	public String getIdPersonaUltimo() {
		return idPersonaUltimo;
	}
	public void setIdPersonaUltimo(String idPersonaUltimo) {
		this.idPersonaUltimo = idPersonaUltimo;
	}
	public String getTipoDiasGuardia() {
		return tipoDiasGuardia;
	}
	public void setTipoDiasGuardia(String tipoDiasGuardia) {
		this.tipoDiasGuardia = tipoDiasGuardia;
	}
	public String getTipoDiasPeriodo() {
		return tipoDiasPeriodo;
	}
	public void setTipoDiasPeriodo(String tipoDiasPeriodo) {
		this.tipoDiasPeriodo = tipoDiasPeriodo;
	}
	public String getDiasGuardia() {
		return diasGuardia;
	}
	public void setDiasGuardia(String diasGuardia) {
		this.diasGuardia = diasGuardia;
	}
	public String getDiasSeparacionGuardias() {
		return diasSeparacionGuardias;
	}
	public void setDiasSeparacionGuardias(String diasSeparacionGuardias) {
		this.diasSeparacionGuardias = diasSeparacionGuardias;
	}

	public String getDiasPeriodo() {
		return diasPeriodo;
	}
	public void setDiasPeriodo(String diasPeriodo) {
		this.diasPeriodo = diasPeriodo;
	}
	public String getIdOrdenacionColas() {
		return idOrdenacionColas;
	}
	public void setIdOrdenacionColas(String idOrdenacionColas) {
		this.idOrdenacionColas = idOrdenacionColas;
	}
	public String getPorGrupos() {
		return porGrupos;
	}
	public void setPorGrupos(String porGrupos) {
		this.porGrupos = porGrupos;
	}
	public String getDescripcionPago() {
		return descripcionPago;
	}
	public void setDescripcionPago(String descripcionPago) {
		this.descripcionPago = descripcionPago;
	}
	public String getDescripcionFacturacion() {
		return descripcionFacturacion;
	}
	public void setDescripcionFacturacion(String descripcionFacturacion) {
		this.descripcionFacturacion = descripcionFacturacion;
	}
	public String getEnvioCentralita() {
		return envioCentralita;
	}
	public void setEnvioCentralita(String envioCentralita) {
		this.envioCentralita = envioCentralita;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getGrupoZona() {
		return grupoZona;
	}
	public void setGrupoZona(String grupoZona) {
		this.grupoZona = grupoZona;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getObligatoriedad() {
		return obligatoriedad;
	}
	public void setObligatoriedad(String obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getValidaJustificacion() {
		return validaJustificacion;
	}
	public void setValidaJustificacion(String validaJustificacion) {
		this.validaJustificacion = validaJustificacion;
	}
	public String getPartidaJudicial() {
		return partidaJudicial;
	}
	public void setPartidaJudicial(String partidaJudicial) {
		this.partidaJudicial = partidaJudicial;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public String getGrupoFacturacion() {
		return grupoFacturacion;
	}
	public void setGrupoFacturacion(String grupoFacturacion) {
		this.grupoFacturacion = grupoFacturacion;
	}
	public String getPartidaPresupuestaria() {
		return partidaPresupuestaria;
	}
	public void setPartidaPresupuestaria(String partidaPresupuestaria) {
		this.partidaPresupuestaria = partidaPresupuestaria;
	}
	public String getTipoTurno() {
		return tipoTurno;
	}
	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}
	public String getTipoGuardia() {
		return tipoGuardia;
	}
	public void setTipoGuardia(String tipoGuardia) {
		this.tipoGuardia = tipoGuardia;
	}
	public String getTipoDia() {
		return tipoDia;
	}
	public void setTipoDia(String tipoDia) {
		this.tipoDia = tipoDia;
	}
	public String getLetradosGuardia() {
		return letradosGuardia;
	}
	public void setLetradosGuardia(String letradosGuardia) {
		this.letradosGuardia = letradosGuardia;
	}
	public String getLetradosIns() {
		return letradosIns;
	}
	public void setLetradosIns(String letradosIns) {
		this.letradosIns = letradosIns;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((duracion == null) ? 0 : duracion.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((grupoFacturacion == null) ? 0 : grupoFacturacion.hashCode());
		result = prime * result + ((grupoZona == null) ? 0 : grupoZona.hashCode());
		result = prime * result + ((idGuardia == null) ? 0 : idGuardia.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + ((jurisdiccion == null) ? 0 : jurisdiccion.hashCode());
		result = prime * result + ((letradosGuardia == null) ? 0 : letradosGuardia.hashCode());
		result = prime * result + ((letradosIns == null) ? 0 : letradosIns.hashCode());
		result = prime * result + ((materia == null) ? 0 : materia.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((obligatoriedad == null) ? 0 : obligatoriedad.hashCode());
		result = prime * result + ((partidaJudicial == null) ? 0 : partidaJudicial.hashCode());
		result = prime * result + ((partidaPresupuestaria == null) ? 0 : partidaPresupuestaria.hashCode());
		result = prime * result + ((tipoDia == null) ? 0 : tipoDia.hashCode());
		result = prime * result + ((tipoGuardia == null) ? 0 : tipoGuardia.hashCode());
		result = prime * result + ((tipoTurno == null) ? 0 : tipoTurno.hashCode());
		result = prime * result + ((validaJustificacion == null) ? 0 : validaJustificacion.hashCode());
		result = prime * result + ((zona == null) ? 0 : zona.hashCode());
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
		GuardiasItem other = (GuardiasItem) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (duracion == null) {
			if (other.duracion != null)
				return false;
		} else if (!duracion.equals(other.duracion))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (grupoFacturacion == null) {
			if (other.grupoFacturacion != null)
				return false;
		} else if (!grupoFacturacion.equals(other.grupoFacturacion))
			return false;
		if (grupoZona == null) {
			if (other.grupoZona != null)
				return false;
		} else if (!grupoZona.equals(other.grupoZona))
			return false;
		if (idGuardia == null) {
			if (other.idGuardia != null)
				return false;
		} else if (!idGuardia.equals(other.idGuardia))
			return false;
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (jurisdiccion == null) {
			if (other.jurisdiccion != null)
				return false;
		} else if (!jurisdiccion.equals(other.jurisdiccion))
			return false;
		if (letradosGuardia == null) {
			if (other.letradosGuardia != null)
				return false;
		} else if (!letradosGuardia.equals(other.letradosGuardia))
			return false;
		if (letradosIns == null) {
			if (other.letradosIns != null)
				return false;
		} else if (!letradosIns.equals(other.letradosIns))
			return false;
		if (materia == null) {
			if (other.materia != null)
				return false;
		} else if (!materia.equals(other.materia))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (obligatoriedad == null) {
			if (other.obligatoriedad != null)
				return false;
		} else if (!obligatoriedad.equals(other.obligatoriedad))
			return false;
		if (partidaJudicial == null) {
			if (other.partidaJudicial != null)
				return false;
		} else if (!partidaJudicial.equals(other.partidaJudicial))
			return false;
		if (partidaPresupuestaria == null) {
			if (other.partidaPresupuestaria != null)
				return false;
		} else if (!partidaPresupuestaria.equals(other.partidaPresupuestaria))
			return false;
		if (tipoDia == null) {
			if (other.tipoDia != null)
				return false;
		} else if (!tipoDia.equals(other.tipoDia))
			return false;
		if (tipoGuardia == null) {
			if (other.tipoGuardia != null)
				return false;
		} else if (!tipoGuardia.equals(other.tipoGuardia))
			return false;
		if (tipoTurno == null) {
			if (other.tipoTurno != null)
				return false;
		} else if (!tipoTurno.equals(other.tipoTurno))
			return false;
		if (validaJustificacion == null) {
			if (other.validaJustificacion != null)
				return false;
		} else if (!validaJustificacion.equals(other.validaJustificacion))
			return false;
		if (zona == null) {
			if (other.zona != null)
				return false;
		} else if (!zona.equals(other.zona))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "GuardiasItem [idGuardia=" + idGuardia + ", idTurno=" + idTurno + ", nombre=" + nombre + ", area=" + area
				+ ", materia=" + materia + ", grupoZona=" + grupoZona + ", zona=" + zona + ", obligatoriedad="
				+ obligatoriedad + ", duracion=" + duracion + ", validaJustificacion=" + validaJustificacion
				+ ", partidaJudicial=" + partidaJudicial + ", jurisdiccion=" + jurisdiccion + ", grupoFacturacion="
				+ grupoFacturacion + ", partidaPresupuestaria=" + partidaPresupuestaria + ", tipoTurno=" + tipoTurno
				+ ", tipoGuardia=" + tipoGuardia + ", tipoDia=" + tipoDia + ", letradosGuardia=" + letradosGuardia
				+ ", letradosIns=" + letradosIns + ", fechabaja=" + fechabaja + "]";
	}
	public String getSeleccionLaborables() {
		return seleccionLaborables;
	}
	public void setSeleccionLaborables(String seleccionLaborables) {
		this.seleccionLaborables = seleccionLaborables;
	}
	public String getSeleccionFestivos() {
		return seleccionFestivos;
	}
	public void setSeleccionFestivos(String seleccionFestivos) {
		this.seleccionFestivos = seleccionFestivos;
	}
	public String getBaremo() {
		return baremo;
	}
	public void setBaremo(String baremo) {
		this.baremo = baremo;
	}
	public String getnDias() {
		return nDias;
	}
	public void setnDias(String nDias) {
		this.nDias = nDias;
	} 

	
	
}
