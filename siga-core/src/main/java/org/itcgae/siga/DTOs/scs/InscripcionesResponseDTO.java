package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

public class InscripcionesResponseDTO {

	private List<BusquedaInscripcionItem> inscripcionesItem = new ArrayList<BusquedaInscripcionItem>();
	private Error error = null;
	  
	public List<BusquedaInscripcionItem> getInscripcionesItem() {
		return inscripcionesItem;
	}
	public void setInscripcionesItem(List<BusquedaInscripcionItem> inscripcionesItem) { //preguntar
		this.inscripcionesItem = inscripcionesItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
	
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((incompatibilidadesItem == null) ? 0 : incompatibilidadesItem.hashCode());
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
		IncompatibilidadesDTO other = (IncompatibilidadesDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (incompatibilidadesItem == null) {
			if (other.incompatibilidadesItem != null)
				return false;
		} else if (!incompatibilidadesItem.equals(other.incompatibilidadesItem))
			return false;
		return true;
	}*/