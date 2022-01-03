package org.itcgae.siga.DTO.fac;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class MonederoDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    private String nifCif;
    private String nombreCompleto;
    private String descripcion;
    private float importeInicial;
    private float importeRestante;
    private float importeUsado;
    private int idPersona;
    private int idAnticipo;
    
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNifCif() {
		return nifCif;
	}
	public void setNifCif(String nifCif) {
		this.nifCif = nifCif;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getImporteInicial() {
		return importeInicial;
	}
	public void setImporteInicial(float importeInicial) {
		this.importeInicial = importeInicial;
	}
	public float getImporteRestante() {
		return importeRestante;
	}
	public void setImporteRestante(float importeRestante) {
		this.importeRestante = importeRestante;
	}
	public float getImporteUsado() {
		return importeUsado;
	}
	public void setImporteUsado(float importeUsado) {
		this.importeUsado = importeUsado;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdAnticipo() {
		return idAnticipo;
	}
	public void setIdAnticipo(int idAnticipo) {
		this.idAnticipo = idAnticipo;
	}
}
