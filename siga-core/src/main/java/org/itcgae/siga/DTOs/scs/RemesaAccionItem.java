package org.itcgae.siga.DTOs.scs;

public class RemesaAccionItem {
	
	private int idRemesa;
	private int accion;
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdRemesa() {
		return idRemesa;
	}
	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}
	public int getAccion() {
		return accion;
	}
	public void setAccion(int accion) {
		this.accion = accion;
	}
	
}
