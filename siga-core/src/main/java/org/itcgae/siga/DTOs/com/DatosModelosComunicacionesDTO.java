package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosModelosComunicacionesDTO {
	
	List<ModelosComunicacionItem> modelosComunicacionItem = new ArrayList<ModelosComunicacionItem>();
	private Error error = null;
	
	
	public List<ModelosComunicacionItem> getModelosComunicacionItem() {
		return modelosComunicacionItem;
	}
	public void setModelosComunicacionItem(List<ModelosComunicacionItem> modelosComunicacionItem) {
		this.modelosComunicacionItem = modelosComunicacionItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((modelosComunicacionItem == null) ? 0 : modelosComunicacionItem.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosModelosComunicacionesDTO other = (DatosModelosComunicacionesDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (modelosComunicacionItem == null) {
			if (other.modelosComunicacionItem != null)
				return false;
		} else if (!modelosComunicacionItem.equals(other.modelosComunicacionItem))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BusquedaModelosComunicaciones [modelosComunicacionItem=" + modelosComunicacionItem + ", error=" + error
				+ "]";
	}
	
	

}
