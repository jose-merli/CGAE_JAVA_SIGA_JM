package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogoComunicacionItem {
	
	private String idClaseComunicacion;
	private String idInstitucion;
	private Date fechaProgramada;
	private List<ModelosComunicacionItem> modelos = new ArrayList<ModelosComunicacionItem>();
	private List<List<String>> selectedDatos = new ArrayList<List<String>>();
	private List<ConsultaItem> consultas = new ArrayList<ConsultaItem>();
	
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public List<ModelosComunicacionItem> getModelos() {
		return modelos;
	}
	public void setModelos(List<ModelosComunicacionItem> modelos) {
		this.modelos = modelos;
	}
	public List<List<String>> getSelectedDatos() {
		return selectedDatos;
	}
	public void setSelectedDatos(List<List<String>> selectedDatos) {
		this.selectedDatos = selectedDatos;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public List<ConsultaItem> getConsultas() {
		return consultas;
	}
	public void setConsultas(List<ConsultaItem> consultas) {
		this.consultas = consultas;
	}
	public Date getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	
	
}
