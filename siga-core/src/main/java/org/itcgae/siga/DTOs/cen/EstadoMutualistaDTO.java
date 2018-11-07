package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class EstadoMutualistaDTO {
	
	private String identificador;
	private Date fechaNacimiento;
	
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
