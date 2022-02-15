
package org.itcgae.siga.DTO.scs;

import java.util.Date;
import java.util.Objects;

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
	private Short numMinimoSimple;
	private Short simpleOImporteIndividual;
	private Short naPartir;
	private Short maximo;
	private String porDia;

	
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

	public Short getNumMinimoSimple() {
		return numMinimoSimple;
	}

	public void setNumMinimoSimple(Short numMinimoSimple) {
		this.numMinimoSimple = numMinimoSimple;
	}

	public Short getSimpleOImporteIndividual() {
		return simpleOImporteIndividual;
	}

	public void setSimpleOImporteIndividual(Short simpleOImporteIndividual) {
		this.simpleOImporteIndividual = simpleOImporteIndividual;
	}

	public Short getNaPartir() {
		return naPartir;
	}

	public void setNaPartir(Short naPartir) {
		this.naPartir = naPartir;
	}

	public Short getMaximo() {
		return maximo;
	}

	public void setMaximo(Short maximo) {
		this.maximo = maximo;
	}

	public String getPorDia() {
		return porDia;
	}

	public void setPorDia(String porDia) {
		this.porDia = porDia;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GuardiasItem that = (GuardiasItem) o;
		return isHistorico() == that.isHistorico() &&
				Objects.equals(getIdGuardia(), that.getIdGuardia()) &&
				Objects.equals(getIdTurno(), that.getIdTurno()) &&
				Objects.equals(getIdOrdenacionColas(), that.getIdOrdenacionColas()) &&
				Objects.equals(getIdPersonaUltimo(), that.getIdPersonaUltimo()) &&
				Objects.equals(getTurno(), that.getTurno()) &&
				Objects.equals(getNombre(), that.getNombre()) &&
				Objects.equals(getDescripcion(), that.getDescripcion()) &&
				Objects.equals(getDescripcionPago(), that.getDescripcionPago()) &&
				Objects.equals(getDescripcionFacturacion(), that.getDescripcionFacturacion()) &&
				Objects.equals(getArea(), that.getArea()) &&
				Objects.equals(getMateria(), that.getMateria()) &&
				Objects.equals(getGrupoZona(), that.getGrupoZona()) &&
				Objects.equals(getZona(), that.getZona()) &&
				Objects.equals(getObligatoriedad(), that.getObligatoriedad()) &&
				Objects.equals(getDuracion(), that.getDuracion()) &&
				Objects.equals(getValidaJustificacion(), that.getValidaJustificacion()) &&
				Objects.equals(getPartidaJudicial(), that.getPartidaJudicial()) &&
				Objects.equals(getJurisdiccion(), that.getJurisdiccion()) &&
				Objects.equals(getGrupoFacturacion(), that.getGrupoFacturacion()) &&
				Objects.equals(getPartidaPresupuestaria(), that.getPartidaPresupuestaria()) &&
				Objects.equals(getTipoTurno(), that.getTipoTurno()) &&
				Objects.equals(getTipoGuardia(), that.getTipoGuardia()) &&
				Objects.equals(getTipoDia(), that.getTipoDia()) &&
				Objects.equals(getTipoDiasGuardia(), that.getTipoDiasGuardia()) &&
				Objects.equals(getTipoDiasPeriodo(), that.getTipoDiasPeriodo()) &&
				Objects.equals(getLetradosGuardia(), that.getLetradosGuardia()) &&
				Objects.equals(getLetradosIns(), that.getLetradosIns()) &&
				Objects.equals(seleccionLaborables, that.seleccionLaborables) &&
				Objects.equals(seleccionFestivos, that.seleccionFestivos) &&
				Objects.equals(getDiasPeriodo(), that.getDiasPeriodo()) &&
				Objects.equals(getDiasGuardia(), that.getDiasGuardia()) &&
				Objects.equals(getDiasSeparacionGuardias(), that.getDiasSeparacionGuardias()) &&
				Objects.equals(getEnvioCentralita(), that.getEnvioCentralita()) &&
				Objects.equals(getPorGrupos(), that.getPorGrupos()) &&
				Objects.equals(getFechabaja(), that.getFechabaja()) &&
				Objects.equals(getBaremo(), that.getBaremo()) &&
				Objects.equals(getnDias(), that.getnDias()) &&
				Objects.equals(getNumMinimoSimple(), that.getNumMinimoSimple()) &&
				Objects.equals(getSimpleOImporteIndividual(), that.getSimpleOImporteIndividual()) &&
				Objects.equals(getNaPartir(), that.getNaPartir()) &&
				Objects.equals(getMaximo(), that.getMaximo()) &&
				Objects.equals(getPorDia(), that.getPorDia());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdGuardia(), getIdTurno(), getIdOrdenacionColas(), getIdPersonaUltimo(), getTurno(), getNombre(), getDescripcion(), getDescripcionPago(), getDescripcionFacturacion(), getArea(), getMateria(), getGrupoZona(), getZona(), getObligatoriedad(), getDuracion(), getValidaJustificacion(), getPartidaJudicial(), getJurisdiccion(), getGrupoFacturacion(), getPartidaPresupuestaria(), getTipoTurno(), getTipoGuardia(), getTipoDia(), getTipoDiasGuardia(), getTipoDiasPeriodo(), getLetradosGuardia(), getLetradosIns(), seleccionLaborables, seleccionFestivos, getDiasPeriodo(), getDiasGuardia(), getDiasSeparacionGuardias(), getEnvioCentralita(), getPorGrupos(), getFechabaja(), isHistorico(), getBaremo(), getnDias(), getNumMinimoSimple(), getSimpleOImporteIndividual(), getNaPartir(), getMaximo(), getPorDia());
	}

	@Override
	public String toString() {
		return "GuardiasItem{" +
				"idGuardia='" + idGuardia + '\'' +
				", idTurno='" + idTurno + '\'' +
				", idOrdenacionColas='" + idOrdenacionColas + '\'' +
				", idPersonaUltimo='" + idPersonaUltimo + '\'' +
				", turno='" + turno + '\'' +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", descripcionPago='" + descripcionPago + '\'' +
				", descripcionFacturacion='" + descripcionFacturacion + '\'' +
				", area='" + area + '\'' +
				", materia='" + materia + '\'' +
				", grupoZona='" + grupoZona + '\'' +
				", zona='" + zona + '\'' +
				", obligatoriedad='" + obligatoriedad + '\'' +
				", duracion='" + duracion + '\'' +
				", validaJustificacion='" + validaJustificacion + '\'' +
				", partidaJudicial='" + partidaJudicial + '\'' +
				", jurisdiccion='" + jurisdiccion + '\'' +
				", grupoFacturacion='" + grupoFacturacion + '\'' +
				", partidaPresupuestaria='" + partidaPresupuestaria + '\'' +
				", tipoTurno='" + tipoTurno + '\'' +
				", tipoGuardia='" + tipoGuardia + '\'' +
				", tipoDia='" + tipoDia + '\'' +
				", tipoDiasGuardia='" + tipoDiasGuardia + '\'' +
				", tipoDiasPeriodo='" + tipoDiasPeriodo + '\'' +
				", letradosGuardia='" + letradosGuardia + '\'' +
				", letradosIns='" + letradosIns + '\'' +
				", seleccionLaborables='" + seleccionLaborables + '\'' +
				", seleccionFestivos='" + seleccionFestivos + '\'' +
				", diasPeriodo='" + diasPeriodo + '\'' +
				", diasGuardia='" + diasGuardia + '\'' +
				", diasSeparacionGuardias='" + diasSeparacionGuardias + '\'' +
				", envioCentralita='" + envioCentralita + '\'' +
				", porGrupos='" + porGrupos + '\'' +
				", fechabaja=" + fechabaja +
				", historico=" + historico +
				", baremo='" + baremo + '\'' +
				", nDias='" + nDias + '\'' +
				", numMinimoSimple=" + numMinimoSimple +
				", simpleOImporteIndividual=" + simpleOImporteIndividual +
				", naPartir=" + naPartir +
				", maximo=" + maximo +
				", porDia='" + porDia + '\'' +
				'}';
	}
}
