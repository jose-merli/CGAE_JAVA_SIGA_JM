package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ModelosComunicacionSearch {

	List<ModelosComunicacionItem> modelosComunicacionItems = new ArrayList<ModelosComunicacionItem>();
	Error error = null;
	
	public List<ModelosComunicacionItem> getModelosComunicacionItems() {
		return modelosComunicacionItems;
	}
	public Error getError() {
		return error;
	}
	public void setModelosComunicacionItems(List<ModelosComunicacionItem> modelosComunicacionItems) {
		this.modelosComunicacionItems = modelosComunicacionItems;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
}
