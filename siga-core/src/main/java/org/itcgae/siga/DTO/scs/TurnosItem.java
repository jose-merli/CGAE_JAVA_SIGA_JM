package org.itcgae.siga.DTO.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnosItem {

	private String nombre;
	private Short abreviatura;
	private String area;
	private String antiguedad;
	private String antiguedadencola;
	private String estado;
	private String jurisdicciones;
	private String jurisdiccion;
	private Date fechabaja;
	private boolean historico;
	private String nletrados;
	private String idtipoturno;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Short getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(Short abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}
	public String getAntiguedadencola() {
		return antiguedadencola;
	}
	public void setAntiguedadencola(String antiguedadencola) {
		this.antiguedadencola = antiguedadencola;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	
	
}
