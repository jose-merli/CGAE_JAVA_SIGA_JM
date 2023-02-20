
package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class GuardiasItem {

	private String idGuardia;
	private String idTurno;
	private String idGuardiaPrincipal;
	private String idTurnoPrincipal;
	private String idGuardiaVinculada;
	private String idTurnoVinculada;
	private String idOrdenacionColas;
	private String idPersonaUltimo;
	private String idGrupoUltimo;
	private String idTipoGuardia;
	private String ordenacionManual;
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
	private Boolean envioCentralita;
	private String porGrupos;
	private String filtros;
	private String rotarComponentes;
	private String incompatibilidades;
	private boolean requeridaValidacion;
	private Date fechabaja;
	private Date fechaalta;
	private Date fechadesde;
	private Date fechahasta;
	private Date fechasustitucion;
	private boolean historico;
	private String separarGuardia;
	private String numColegiado;
	private String validada;
	private String ordenGrupo;
	private String grupoGuardiaColegiado;
	private String idPersona;
	private Date fechaValidacion;
	private String estadoGuardia;
	private String idCalendarioGuardias;
	private String facturado;
	private Integer idFacturacion;
	private String observacionesAnulacion;
	private String comensustitucion;
	private String letradosustituido;
	private String sustituto;
	private String idListaGuardia;
	private String orden;
	private Short idInstitucion;
	//Aux para GuardiasColegiado
	private String fechaIntro;
	private String idCalendarioProgramado;
	
	
	public String getIdListaGuardia() {
		return idListaGuardia;
	}

	public void setIdListaGuardia(String idListaGuardia) {
		this.idListaGuardia = idListaGuardia;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getSepararGuardia() {
		return separarGuardia;
	}

	public void setSepararGuardia(String separarGuardia) {
		this.separarGuardia = separarGuardia;
	}

	public boolean isRequeridaValidacion() {
		return requeridaValidacion;
	}

	public void setRequeridaValidacion(boolean requeridaValidacion) {
		this.requeridaValidacion = requeridaValidacion;
	}

	public String getIncompatibilidades() {
		return incompatibilidades;
	}

	public void setIncompatibilidades(String incompatibilidades) {
		this.incompatibilidades = incompatibilidades;
	}

	public String getOrdenacionManual() {
		return ordenacionManual;
	}

	public void setOrdenacionManual(String ordenacionManual) {
		this.ordenacionManual = ordenacionManual;
	}

	public String getIdGrupoUltimo() {
		return idGrupoUltimo;
	}

	public void setIdGrupoUltimo(String idGrupoUltimo) {
		this.idGrupoUltimo = idGrupoUltimo;
	}

	public String getRotarComponentes() {
		return rotarComponentes;
	}

	public void setRotarComponentes(String rotarComponentes) {
		this.rotarComponentes = rotarComponentes;
	}

	public String getFiltros() {
		return filtros;
	}

	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}

	public String getIdGuardiaPrincipal() {
		return idGuardiaPrincipal;
	}

	public void setIdGuardiaPrincipal(String idGuardiaPrincipal) {
		this.idGuardiaPrincipal = idGuardiaPrincipal;
	}

	public String getIdTurnoPrincipal() {
		return idTurnoPrincipal;
	}

	public void setIdTurnoPrincipal(String idTurnoPrincipal) {
		this.idTurnoPrincipal = idTurnoPrincipal;
	}

	public String getIdGuardiaVinculada() {
		return idGuardiaVinculada;
	}

	public void setIdGuardiaVinculada(String idGuardiaVinculada) {
		this.idGuardiaVinculada = idGuardiaVinculada;
	}

	public String getIdTurnoVinculada() {
		return idTurnoVinculada;
	}

	public void setIdTurnoVinculada(String idTurnoVinculada) {
		this.idTurnoVinculada = idTurnoVinculada;
	}

	public String getIdTipoGuardia() {
		return idTipoGuardia;
	}

	public void setIdTipoGuardia(String idTipoGuardia) {
		this.idTipoGuardia = idTipoGuardia;
	}

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

	public Boolean getEnvioCentralita() {
		return envioCentralita;
	}

	public void setEnvioCentralita(Boolean envioCentralita) {
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

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}
	
	public String getValidada() {
		return validada;
	}

	public void setValidada(String validada) {
		this.validada = validada;
	}

	public Date getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}
	public Date getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
	}
	public String getOrdenGrupo() {
		return ordenGrupo;
	}

	public void setOrdenGrupo(String ordenGrupo) {
		this.ordenGrupo = ordenGrupo;
	}
	public String getGrupoGuardiaColegiado() {
		return grupoGuardiaColegiado;
	}

	public void setGrupoGuardiaColegiado(String grupoGuardiaColegiado) {
		this.grupoGuardiaColegiado = grupoGuardiaColegiado;
	}
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}
	public String getEstadoGuardia() {
		return estadoGuardia;
	}

	public void setEstadoGuardia(String estadoGuardia) {
		this.estadoGuardia = estadoGuardia;
	}
	public String getIdCalendarioGuardias() {
		return idCalendarioGuardias;
	}

	public void setIdCalendarioGuardias(String idCalendarioGuardias) {
		this.idCalendarioGuardias = idCalendarioGuardias;
	}
	public String getFacturado() {
		return facturado;
	}

	public void setFacturado(String facturado) {
		this.facturado = facturado;
	}
	public Integer getIdFacturacion() {
		return idFacturacion;
	}

	public void setIdFacturacion(Integer idFacturacion) {
		this.idFacturacion = idFacturacion;
	}
	public String getObservacionesAnulacion() {
		return observacionesAnulacion;
	}

	public void setObservacionesAnulacion(String observacionesAnulacion) {
		this.observacionesAnulacion = observacionesAnulacion;
	}
	public Date getFechasustitucion() {
		return fechasustitucion;
	}

	public void setFechasustitucion(Date fechasustitucion) {
		this.fechasustitucion = fechasustitucion;
	}
	public String getComensustitucion() {
		return comensustitucion;
	}

	public void setComensustitucion(String comensustitucion) {
		this.comensustitucion = comensustitucion;
	}



	public String getLetradosustituido() {
		return letradosustituido;
	}

	public void setLetradosustituido(String letradosustituido) {
		this.letradosustituido = letradosustituido;
	}

	public String getSustituto() {
		return sustituto;
	}

	public void setSustituto(String sustituto) {
		this.sustituto = sustituto;
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
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((fechaalta == null) ? 0 : fechaalta.hashCode());
		result = prime * result + ((validada == null) ? 0 : validada.hashCode());
		result = prime * result + ((fechadesde == null) ? 0 : fechadesde.hashCode());
		result = prime * result + ((fechahasta == null) ? 0 : fechahasta.hashCode());
		result = prime * result + ((ordenGrupo == null) ? 0 : ordenGrupo.hashCode());
		result = prime * result + ((grupoGuardiaColegiado == null) ? 0 : grupoGuardiaColegiado.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((fechaValidacion == null) ? 0 : fechaValidacion.hashCode());
		result = prime * result + ((estadoGuardia == null) ? 0 : estadoGuardia.hashCode());
		result = prime * result + ((idCalendarioGuardias == null) ? 0 : idCalendarioGuardias.hashCode());
		result = prime * result + ((facturado == null) ? 0 : facturado.hashCode());
		result = prime * result + ((idFacturacion == null) ? 0 : idFacturacion.hashCode());
		result = prime * result + ((observacionesAnulacion == null) ? 0 : observacionesAnulacion.hashCode());
		result = prime * result + ((fechasustitucion == null) ? 0 : fechasustitucion.hashCode());
		result = prime * result + ((comensustitucion == null) ? 0 : comensustitucion.hashCode());
		result = prime * result + ((letradosustituido == null) ? 0 : letradosustituido.hashCode());
		result = prime * result + ((sustituto == null) ? 0 : sustituto.hashCode());
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
		if (numColegiado == null) {
			if (other.numColegiado != null)
				return false;
		} else if (!numColegiado.equals(other.numColegiado))
			return false;
		if (fechaalta == null) {
			if (other.fechaalta != null)
				return false;
		} else if (!fechaalta.equals(other.fechaalta))
			return false;
		if (validada == null) {
			if (other.validada != null)
				return false;
		} else if (!validada.equals(other.validada))
			return false;
		if (ordenGrupo == null) {
			if (other.ordenGrupo != null)
				return false;
		} else if (!ordenGrupo.equals(other.ordenGrupo))
			return false;
		if (fechadesde == null) {
			if (other.fechadesde != null)
				return false;
		} else if (!fechadesde.equals(other.fechadesde))
			return false;
		if (fechahasta == null) {
			if (other.fechahasta != null)
				return false;
		} else if (!fechahasta.equals(other.fechahasta))
			return false;
		if (grupoGuardiaColegiado == null) {
			if (other.grupoGuardiaColegiado != null)
				return false;
		} else if (!grupoGuardiaColegiado.equals(other.grupoGuardiaColegiado))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (fechaValidacion == null) {
			if (other.fechaValidacion != null)
				return false;
		} else if (!fechaValidacion.equals(other.fechaValidacion))
			return false;
		if (estadoGuardia == null) {
			if (other.estadoGuardia != null)
				return false;
		} else if (!estadoGuardia.equals(other.estadoGuardia))
			return false;
		if (idCalendarioGuardias == null) {
			if (other.idCalendarioGuardias != null)
				return false;
		} else if (!idCalendarioGuardias.equals(other.idCalendarioGuardias))
			return false;
		if (facturado == null) {
			if (other.facturado != null)
				return false;
		} else if (!facturado.equals(other.facturado))
			return false;
		if (idFacturacion == null) {
			if (other.idFacturacion != null)
				return false;
		} else if (!idFacturacion.equals(other.idFacturacion))
			return false;
		if (observacionesAnulacion == null) {
			if (other.observacionesAnulacion != null)
				return false;
		} else if (!observacionesAnulacion.equals(other.observacionesAnulacion))
			return false;
		if (fechasustitucion == null) {
			if (other.fechasustitucion != null)
				return false;
		} else if (!fechasustitucion.equals(other.fechasustitucion))
			return false;
		if (comensustitucion == null) {
			if (other.comensustitucion != null)
				return false;
		} else if (!comensustitucion.equals(other.comensustitucion))
			return false;
		if (letradosustituido == null) {
			if (other.letradosustituido != null)
				return false;
		} else if (!letradosustituido.equals(other.letradosustituido))
			return false;
		if (sustituto == null) {
			if (other.sustituto != null)
				return false;
		} else if (!sustituto.equals(other.sustituto))
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
				+ ", letradosIns=" + letradosIns + ", fechabaja=" + fechabaja + ", numColegiado=" + numColegiado + ", fechaalta=" + fechaalta +
				", validada=" + validada + ", ordenGrupo=" + ordenGrupo +", fechadesde=" + fechadesde +", fechahasta=" + fechahasta +
				", grupoGuardiaColegiado=" + grupoGuardiaColegiado + ", idPersona=" + idPersona + ", fechaValidacion=" + fechaValidacion +
				", estadoGuardia=" + estadoGuardia + ", idCalendarioGuardias=" + idCalendarioGuardias +", facturado=" + facturado +
				", idFacturacion =" + idFacturacion + ", observacionesAnulacion =" + observacionesAnulacion +", fechasustitucion =" + fechasustitucion +
				", comensustitucion =" + comensustitucion + ", letradosustituido =" + letradosustituido + ", sustituto =" + sustituto +"]";
	}

	public String getFechaIntro() {
		return fechaIntro;
	}

	public void setFechaIntro(String fechaIntro) {
		this.fechaIntro = fechaIntro;
	}

	public String getIdCalendarioProgramado() {
		return idCalendarioProgramado;
	}

	public void setIdCalendarioProgramado(String idCalendarioProgramado) {
		this.idCalendarioProgramado = idCalendarioProgramado;
	}

	public Short getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}





}
