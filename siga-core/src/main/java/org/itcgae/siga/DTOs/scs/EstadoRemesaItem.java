package org.itcgae.siga.DTOs.scs;

public class EstadoRemesaItem {
	private int idRemesa;
	private int idInstitucion;
	private String estado;
	private String fechaModificacion;
	
	public int getIdRemesa() {
		return idRemesa;
	}
	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}
	public int getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
