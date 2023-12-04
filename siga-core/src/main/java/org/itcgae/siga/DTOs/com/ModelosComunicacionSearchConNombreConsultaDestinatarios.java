package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ModelosComunicacionSearchConNombreConsultaDestinatarios {

	List<ModelosComunicacionesItemConNombreConsultaDestinatarios> modelosComunicacionesItemConNombreConsultaDestinatarios = new ArrayList<ModelosComunicacionesItemConNombreConsultaDestinatarios>();
	Error error = null;
	
	public List<ModelosComunicacionesItemConNombreConsultaDestinatarios> getModelosComunicacionItems() {
		return modelosComunicacionesItemConNombreConsultaDestinatarios;
	}
	public Error getError() {
		return error;
	}
	public void setModelosComunicacionItems(List<ModelosComunicacionesItemConNombreConsultaDestinatarios> modelosComunicacionItems) {
		this.modelosComunicacionesItemConNombreConsultaDestinatarios = modelosComunicacionItems;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
}
