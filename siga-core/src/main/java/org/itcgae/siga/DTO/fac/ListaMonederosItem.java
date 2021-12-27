package org.itcgae.siga.DTO.fac;

import java.math.BigDecimal;
import java.util.Date;

public class ListaMonederosItem {
	
	public Date fecha;
	public String nifCif;
	public String nombreCompleto;
	public String descripcion;
	public BigDecimal importeInicial;
	public BigDecimal importeRestante;
	public BigDecimal importeUsado;
    public String idPersona;
    public String idAnticipo;
    
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
	public BigDecimal getImporteInicial() {
		return importeInicial;
	}
	public void setImporteInicial(BigDecimal importeInicial) {
		this.importeInicial = importeInicial;
	}
	public BigDecimal getImporteRestante() {
		return importeRestante;
	}
	public void setImporteRestante(BigDecimal importeRestante) {
		this.importeRestante = importeRestante;
	}
	public BigDecimal getImporteUsado() {
		return importeUsado;
	}
	public void setImporteUsado(BigDecimal importeUsado) {
		this.importeUsado = importeUsado;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdAnticipo() {
		return idAnticipo;
	}
	public void setIdAnticipo(String idAnticipo) {
		this.idAnticipo = idAnticipo;
	}

}
