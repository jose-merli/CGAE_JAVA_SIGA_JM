package org.itcgae.siga.DTO.scs;

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



	
	
}
