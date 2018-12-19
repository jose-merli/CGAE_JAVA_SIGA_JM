package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ConsultaListadoModelosDTO {
	private List<ModelosComunicacionItem> listadoModelos = new ArrayList<ModelosComunicacionItem>();
	private String idConsulta;
	private Error error = null;
	
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public List<ModelosComunicacionItem> getListadoModelos() {
		return listadoModelos;
	}
	public void setListadoModelos(List<ModelosComunicacionItem> listadoModelos) {
		this.listadoModelos = listadoModelos;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
}
