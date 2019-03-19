package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.Error;

public class ComboConsultaInstitucionDTO {

	private List<ComboItemConsulta> consultas = new ArrayList<ComboItemConsulta>();
	
	private Error error = null;	
	
	public List<ComboItemConsulta> getConsultas() {
		return consultas;
	}
	public void setConsultas(List<ComboItemConsulta> consultas) {
		this.consultas = consultas;
	}
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
