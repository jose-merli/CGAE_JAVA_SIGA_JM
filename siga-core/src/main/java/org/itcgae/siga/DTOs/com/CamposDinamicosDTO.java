package org.itcgae.siga.DTOs.com;

import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class CamposDinamicosDTO {
	
	private List<CampoDinamicoItem> camposDinamicos;
	private Error error;	
	
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	public List<CampoDinamicoItem> getCamposDinamicos() {
		return camposDinamicos;
	}
	public void setCamposDinamicos(List<CampoDinamicoItem> camposDinamicos) {
		this.camposDinamicos = camposDinamicos;
	}

}
