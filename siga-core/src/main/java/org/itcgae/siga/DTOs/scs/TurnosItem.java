package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnosItem {

	private String nombre;
	private String abreviatura;
	private String idarea;
	private String idmateria;
	private String idzona;
	private String idzubzona;
	private String area;
	private String materia;
	private String idturno;
	private String jurisdicciones;
	private String jurisdiccion;
	private String zona;
	private String subzona;
	private String idpartidapresupuestaria;
	private String grupofacturacion;
	private Date fechabaja;
	private boolean historico;
	private String nletrados;
	private String idtipoturno;
	private Date fechamodificacion;
	private String usumodificacion;
	private String validarjustificaciones;
	private String validarinscripciones;
	private String designadirecta;
	private String requisitos;
	private String idpersona_ultimo;
	private String descripcion;
	private String activarretriccionacredit;
	private String letradoasistencias;
	private String letradoactuaciones;
	private String codigoext;
	private String fechasolicitud_ultimo;
	private String idsubzona;
	private String idpersonaUltimo;
	private String fechasolicitudUltimo;
	private String visiblemovil;
	private String idgrupofacturacion;
	private String idjurisdiccion;
	private String idguardias;
	private String idordenacioncolas;
	private String orden;
	private String nombrepartidosjudiciales;
	
	
	public String getVisiblemovil() {
		return visiblemovil;
	}
	public void setVisiblemovil(String visiblemovil) {
		this.visiblemovil = visiblemovil;
	}
	public String getFechasolicitudUltimo() {
		return fechasolicitudUltimo;
	}
	public void setFechasolicitudUltimo(String fechasolicitudUltimo) {
		this.fechasolicitudUltimo = fechasolicitudUltimo;
	}
	public String getIdpersona_ultimo() {
		return idpersona_ultimo;
	}
	public void setIdpersona_ultimo(String idpersona_ultimo) {
		this.idpersona_ultimo = idpersona_ultimo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getActivarretriccionacredit() {
		return activarretriccionacredit;
	}
	public void setActivarretriccionacredit(String activarretriccionacredit) {
		this.activarretriccionacredit = activarretriccionacredit;
	}
	public String getLetradoasistencias() {
		return letradoasistencias;
	}
	public void setLetradoasistencias(String letradoasistencias) {
		this.letradoasistencias = letradoasistencias;
	}
	public String getLetradoactuaciones() {
		return letradoactuaciones;
	}
	public void setLetradoactuaciones(String letradoactuaciones) {
		this.letradoactuaciones = letradoactuaciones;
	}
	public String getCodigoext() {
		return codigoext;
	}
	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}
	public String getFechasolicitud_ultimo() {
		return fechasolicitud_ultimo;
	}
	public void setFechasolicitud_ultimo(String fechasolicitud_ultimo) {
		this.fechasolicitud_ultimo = fechasolicitud_ultimo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getJurisdicciones() {
		return jurisdicciones;
	}
	public void setJurisdicciones(String jurisdicciones) {
		this.jurisdicciones = jurisdicciones;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getNletrados() {
		return nletrados;
	}
	public void setNletrados(String nletrados) {
		this.nletrados = nletrados;
	}
	public String getIdtipoturno() {
		return idtipoturno;
	}
	public void setIdtipoturno(String idtipoturno) {
		this.idtipoturno = idtipoturno;
	}
	public String getIdturno() {
		return idturno;
	}
	public void setIdturno(String idturno) {
		this.idturno = idturno;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getSubzona() {
		return subzona;
	}
	public void setSubzona(String subzona) {
		this.subzona = subzona;
	}
	public String getGrupofacturacion() {
		return grupofacturacion;
	}
	public void setGrupofacturacion(String grupofacturacion) {
		this.grupofacturacion = grupofacturacion;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getIdarea() {
		return idarea;
	}
	public void setIdarea(String idarea) {
		this.idarea = idarea;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getIdmateria() {
		return idmateria;
	}
	public void setIdmateria(String idmateria) {
		this.idmateria = idmateria;
	}
	public String getIdzona() {
		return idzona;
	}
	public void setIdzona(String idzona) {
		this.idzona = idzona;
	}
	public String getIdzubzona() {
		return idzubzona;
	}
	public void setIdzubzona(String idzubzona) {
		this.idzubzona = idzubzona;
	}
	public String getIdpartidapresupuestaria() {
		return idpartidapresupuestaria;
	}
	public void setIdpartidapresupuestaria(String idpartidapresupuestaria) {
		this.idpartidapresupuestaria = idpartidapresupuestaria;
	}
	public String getValidarjustificaciones() {
		return validarjustificaciones;
	}
	public void setValidarjustificaciones(String validarjustificaciones) {
		this.validarjustificaciones = validarjustificaciones;
	}
	public String getValidarinscripciones() {
		return validarinscripciones;
	}
	public void setValidarinscripciones(String validarinscripciones) {
		this.validarinscripciones = validarinscripciones;
	}
	public String getDesignadirecta() {
		return designadirecta;
	}
	public void setDesignadirecta(String designadirecta) {
		this.designadirecta = designadirecta;
	}
	public String getRequisitos() {
		return requisitos;
	}
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}
	public String getIdsubzona() {
		return idsubzona;
	}
	public void setIdsubzona(String idsubzona) {
		this.idsubzona = idsubzona;
	}
	public String getIdpersonaUltimo() {
		return idpersonaUltimo;
	}
	public void setIdpersonaUltimo(String idpersonaUltimo) {
		this.idpersonaUltimo = idpersonaUltimo;
	}
	public String getIdgrupofacturacion() {
		return idgrupofacturacion;
	}
	public void setIdgrupofacturacion(String idgrupofacturacion) {
		this.idgrupofacturacion = idgrupofacturacion;
	}
	public String getIdjurisdiccion() {
		return idjurisdiccion;
	}
	public void setIdjurisdiccion(String idjurisdiccion) {
		this.idjurisdiccion = idjurisdiccion;
	}
	public String getIdguardias() {
		return idguardias;
	}
	public void setIdguardias(String idguardias) {
		this.idguardias = idguardias;
	}
	public String getIdordenacioncolas() {
		return idordenacioncolas;
	}
	public void setIdordenacioncolas(String idordenacioncolas) {
		this.idordenacioncolas = idordenacioncolas;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getNombrepartidosjudiciales() {
		return nombrepartidosjudiciales;
	}
	public void setNombrepartidosjudiciales(String nombrepartidosjudiciales) {
		this.nombrepartidosjudiciales = nombrepartidosjudiciales;
	}



	
	
}
