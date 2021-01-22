package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.Error;

public class ComboConsultasDTO {

	private List<ComboItemConsulta> consultasDatos = new ArrayList<ComboItemConsulta>();
	private List<ComboItemConsulta> consultasDestinatarios = new ArrayList<ComboItemConsulta>();
	private List<ComboItemConsulta> consultasCondicional= new ArrayList<ComboItemConsulta>();
	private List<ComboItemConsulta> consultasMultidoc = new ArrayList<ComboItemConsulta>();
	
	private Error error = null;	
	
	public List<ComboItemConsulta> getConsultasDatos() {
		return consultasDatos;
	}
	public void setConsultasDatos(List<ComboItemConsulta> consultasDatos) {
		this.consultasDatos = consultasDatos;
	}
	public List<ComboItemConsulta> getConsultasDestinatarios() {
		return consultasDestinatarios;
	}
	public void setConsultasDestinatarios(List<ComboItemConsulta> consultasDestinatarios) {
		this.consultasDestinatarios = consultasDestinatarios;
	}
	public List<ComboItemConsulta> getConsultasCondicional() {
		return consultasCondicional;
	}
	public void setConsultasCondicional(List<ComboItemConsulta> consultasCondicional) {
		this.consultasCondicional = consultasCondicional;
	}
	public List<ComboItemConsulta> getConsultasMultidoc() {
		return consultasMultidoc;
	}
	public void setConsultasMultidoc(List<ComboItemConsulta> consultasMultidoc) {
		this.consultasMultidoc = consultasMultidoc;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
