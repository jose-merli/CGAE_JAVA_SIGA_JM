package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class RevisionAutLetradoItem {

	private Long idPersona;
	private Date fechaProcesamiento;
	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}
	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}
}
