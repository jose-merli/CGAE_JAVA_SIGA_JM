package org.itcgae.siga.db.entities;

public class PCAJGAlcActIncidencia {
	private String campo;
	private String mensaje;
	private Short idTipoIncidencia;
	private String nivel;
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Short getIdTipoIncidencia() {
		return idTipoIncidencia;
	}
	public void setIdTipoIncidencia(Short idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
}