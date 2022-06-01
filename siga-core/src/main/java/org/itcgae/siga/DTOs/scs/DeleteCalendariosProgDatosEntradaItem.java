package org.itcgae.siga.DTOs.scs;


public class DeleteCalendariosProgDatosEntradaItem {
	
	private String idTurno;
	private String idGuardia;
	private String idCalendarioProgramado;
	private String idInstitucion;
	private String fechaDesde;

	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdCalendarioProgramado() {
		return idCalendarioProgramado;
	}
	public void setIdCalendarioProgramado(String idCalendarioProgramado) {
		this.idCalendarioProgramado = idCalendarioProgramado;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
}
