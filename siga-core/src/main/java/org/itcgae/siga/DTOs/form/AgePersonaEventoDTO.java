package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;


public class AgePersonaEventoDTO {

	private List<AgePersonaEventoItem> personaEventoItem = new ArrayList<AgePersonaEventoItem>();
	private Error error = null;
	
	
	public AgePersonaEventoDTO AgePersonaEventoItem(List<AgePersonaEventoItem> personaEventoItem){
		this.personaEventoItem = personaEventoItem;
		return this;
	}
	
	public List<AgePersonaEventoItem> getPersonaEventoItem() {
		return personaEventoItem;
	}

	public void setPersonaEventoItem(List<AgePersonaEventoItem> personaEventoItem) {
		this.personaEventoItem = personaEventoItem;
	}

	public AgePersonaEventoDTO error(Error error){
		this.error = error;
		return this;
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
		result = prime * result + ((personaEventoItem == null) ? 0 : personaEventoItem.hashCode());
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
		AgePersonaEventoDTO other = (AgePersonaEventoDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (personaEventoItem == null) {
			if (other.personaEventoItem != null)
				return false;
		} else if (!personaEventoItem.equals(other.personaEventoItem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AgePersonaEventoDTO [personaEventoItem=" + personaEventoItem + ", error=" + error + "]";
	}
}
