package org.itcgae.siga.DTOs.cen;


import java.util.Date;

public class PersonaDTO {
	
	private String parentesco;
	private String sexo;
	private String nombre;
	private String apellido;
	private String tipoIdentificacion;
	private String identificacion;
	private Date fechaNacimiento;
	
	


	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	

}
