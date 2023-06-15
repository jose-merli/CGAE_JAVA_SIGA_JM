package org.itcgae.siga.DTOs.scs;


public class CalendariosProgDatosEntradaItem {
	
	private String idTurno;
	private String idGuardia;
	private String idConjuntoGuardia;
	private String idCalendarioProgramado;
	private String estado;
	private String fechaCalendarioDesde;
	private String fechaCalendarioHasta;
	private String fechaProgramadaDesde;
	private String fechaProgramadaHasta;
	
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
	public String getIdConjuntoGuardia() {
		return idConjuntoGuardia;
	}
	public void setIdConjuntoGuardia(String idConjuntoGuardia) {
		this.idConjuntoGuardia = idConjuntoGuardia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaCalendarioDesde() {
		return fechaCalendarioDesde;
	}
	public void setFechaCalendarioDesde(String fechaCalendarioDesde) {
		this.fechaCalendarioDesde = fechaCalendarioDesde;
	}
	public String getFechaCalendarioHasta() {
		return fechaCalendarioHasta;
	}
	public void setFechaCalendarioHasta(String fechaCalendarioHasta) {
		this.fechaCalendarioHasta = fechaCalendarioHasta;
	}
	public String getFechaProgramadaDesde() {
		return fechaProgramadaDesde;
	}
	public void setFechaProgramadaDesde(String fechaProgramadaDesde) {
		this.fechaProgramadaDesde = fechaProgramadaDesde;
	}
	public String getFechaProgramadaHasta() {
		return fechaProgramadaHasta;
	}
	public void setFechaProgramadaHasta(String fechaProgramadaHasta) {
		this.fechaProgramadaHasta = fechaProgramadaHasta;
	}

	public String getIdCalendarioProgramado() {
		return idCalendarioProgramado;
	}

	public void setIdCalendarioProgramado(String idCalendarioProgramado) {
		this.idCalendarioProgramado = idCalendarioProgramado;
	}
}
