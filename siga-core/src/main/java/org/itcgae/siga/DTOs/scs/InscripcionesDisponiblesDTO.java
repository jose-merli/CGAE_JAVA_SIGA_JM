package org.itcgae.siga.DTOs.scs;

import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class InscripcionesDisponiblesDTO {

	private List<GestionInscripcion> accion;
	private Error error = null;

	public List<GestionInscripcion> getAccion() {
		return accion;
	}

	public void setAccion(List<GestionInscripcion> accion) {
		this.accion = accion;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "InscripcionesDisponiblesDTO [accion=" + accion + ", error=" + error + "]";
	}
}
