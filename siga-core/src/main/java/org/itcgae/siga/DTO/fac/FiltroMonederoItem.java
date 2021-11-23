package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class FiltroMonederoItem {
	
	private String idPersonaColegiado;

    private Date fechaDesde;
    private Date fechaHasta;
	
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getIdPersonaColegiado() {
		return idPersonaColegiado;
	}
	public void setIdPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
	}

}
