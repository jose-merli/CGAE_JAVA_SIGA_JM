package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class InscripcionGuardiaDTO {

	  private List<InscripcionGuardiaItem> inscripcionesItem = new ArrayList<InscripcionGuardiaItem>();
	  private Error error = null;
	  
	  
	  
	public List<InscripcionGuardiaItem> getInscripcionesItem() {
		return inscripcionesItem;
	}
	public void setInscripcionesItem(List<InscripcionGuardiaItem> inscripcionesItem) {
		this.inscripcionesItem = inscripcionesItem;
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
		result = prime * result + ((inscripcionesItem == null) ? 0 : inscripcionesItem.hashCode());
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
		InscripcionGuardiaDTO other = (InscripcionGuardiaDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (inscripcionesItem == null) {
			if (other.inscripcionesItem != null)
				return false;
		} else if (!inscripcionesItem.equals(other.inscripcionesItem))
			return false;
		return true;
	}

	
	
}
