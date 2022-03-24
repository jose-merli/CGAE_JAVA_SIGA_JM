package org.itcgae.siga.DTOs.scs;

public class GuardiaCalendarioItem {
	private String orden;
	private String turno;
	private String guardia;
	private String generado;
	private String idGuardia;
	private String idTurno;
	private String idCalendarioGuardia;
	private Boolean nuevo;
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getGuardia() {
		return guardia;
	}
	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	public String getGenerado() {
		return generado;
	}
	public void setGenerado(String generado) {
		this.generado = generado;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdCalendarioGuardia() {
		return idCalendarioGuardia;
	}
	public void setIdCalendarioGuardia(String idCalendarioGuardia) {
		this.idCalendarioGuardia = idCalendarioGuardia;
	}

	public Boolean getNuevo() {
		return nuevo;
	}

	public void setNuevo(Boolean nuevo) {
		this.nuevo = nuevo;
	}
}
