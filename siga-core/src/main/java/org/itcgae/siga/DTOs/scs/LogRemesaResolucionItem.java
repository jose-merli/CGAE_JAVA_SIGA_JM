package org.itcgae.siga.DTOs.scs;

public class LogRemesaResolucionItem {
	private String codigo;
	private String descripcion;
	private String parametrosError;
	private String numeroLinea;
	public String getCodigo() {
		return codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getParametrosError() {
		return parametrosError;
	}
	public String getNumeroLinea() {
		return numeroLinea;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setParametrosError(String parametrosError) {
		this.parametrosError = parametrosError;
	}
	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}
	
	
	
}
