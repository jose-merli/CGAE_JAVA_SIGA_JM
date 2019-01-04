package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class CuotaYCapObjetivoDTO {

	private int sexo;
	private int cobertura;
	private Date fechaNacimiento;
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public int getCobertura() {
		return cobertura;
	}
	public void setCobertura(int cobertura) {
		this.cobertura = cobertura;
	}
	public int getSexo() {
		return sexo;
	}
	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
	
	
	
}
