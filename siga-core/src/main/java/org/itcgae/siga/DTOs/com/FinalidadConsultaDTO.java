package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class FinalidadConsultaDTO {
	
	private String finalidad;
	private String objetivo;
	
	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	private Error error;
	
	public String getFinalidad() {
		return finalidad;
	}

	public void setFinalidad(String finalidad) {
		this.finalidad = finalidad;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	
	

}
