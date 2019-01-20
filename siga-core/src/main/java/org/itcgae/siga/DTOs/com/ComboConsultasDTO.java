package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;

public class ComboConsultasDTO {

	private List<ComboItem> consultasDatos = new ArrayList<ComboItem>();
	private List<ComboItem> consultasDestinatarios = new ArrayList<ComboItem>();
	private List<ComboItem> consultasCondicional= new ArrayList<ComboItem>();
	private List<ComboItem> consultasMultidoc = new ArrayList<ComboItem>();
	
	private Error error = null;	
	
	public List<ComboItem> getConsultasDatos() {
		return consultasDatos;
	}
	public void setConsultasDatos(List<ComboItem> consultasDatos) {
		this.consultasDatos = consultasDatos;
	}
	public List<ComboItem> getConsultasDestinatarios() {
		return consultasDestinatarios;
	}
	public void setConsultasDestinatarios(List<ComboItem> consultasDestinatarios) {
		this.consultasDestinatarios = consultasDestinatarios;
	}
	public List<ComboItem> getConsultasCondicional() {
		return consultasCondicional;
	}
	public void setConsultasCondicional(List<ComboItem> consultasCondicional) {
		this.consultasCondicional = consultasCondicional;
	}
	public List<ComboItem> getConsultasMultidoc() {
		return consultasMultidoc;
	}
	public void setConsultasMultidoc(List<ComboItem> consultasMultidoc) {
		this.consultasMultidoc = consultasMultidoc;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
