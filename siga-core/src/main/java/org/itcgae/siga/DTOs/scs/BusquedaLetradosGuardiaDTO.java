package org.itcgae.siga.DTOs.scs;

public class BusquedaLetradosGuardiaDTO {

	private String idTurno;
	private String idGuardia;

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

	@Override
	public String toString() {
		return "BusquedaLetradosGuardiaDTO [idTurno=" + idTurno + ", idGuardia=" + idGuardia + "]";
	}

}
