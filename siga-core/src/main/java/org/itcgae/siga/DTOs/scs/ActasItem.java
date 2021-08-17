package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class ActasItem   {
  
	private String anio;
	private String acta;
	private String presidente;
	private String secretario;
	private Date fechaResolucion;
	private Date fechaReunion;
	
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getActa() {
		return acta;
	}
	public void setActa(String acta) {
		this.acta = acta;
	}
	public String getPresidente() {
		return presidente;
	}
	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}
	public String getSecretario() {
		return secretario;
	}
	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}
	public Date getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	public Date getFechaReunion() {
		return fechaReunion;
	}
	public void setFechaReunion(Date fechaReunion) {
		this.fechaReunion = fechaReunion;
	}
	
	
  
}

