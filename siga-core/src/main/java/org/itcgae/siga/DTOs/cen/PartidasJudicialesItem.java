package org.itcgae.siga.DTOs.cen;

public class PartidasJudicialesItem {

	private String idinstitucion;
	private String idpartido;
	private String nombre;
	private boolean historico;
	
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getIdpartido() {
		return idpartido;
	}
	public void setIdpartido(String idpartido) {
		this.idpartido = idpartido;
	}


	public boolean isHistorico() {
		return historico;
	}
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
