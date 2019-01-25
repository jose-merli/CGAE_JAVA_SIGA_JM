package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

public class DialogoComunicacionItem {
	
	private String idClaseComunicacion;
	private List<ModelosComunicacionItem> modelos = new ArrayList<ModelosComunicacionItem>();
	private List<List<String>> selectedDatos = new ArrayList<List<String>>();
	
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
	
	
}
