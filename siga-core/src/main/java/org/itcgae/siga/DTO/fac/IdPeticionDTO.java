package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

public class IdPeticionDTO {
	private List<Integer> idpeticionUso = new ArrayList<Integer>();
	private List<Integer> idpeticionSolicitud = new ArrayList<Integer>();
	
	public List<Integer> getIdpeticionUso() {
		return idpeticionUso;
	}
	public void setIdpeticionUso(List<Integer> idpeticionUso) {
		this.idpeticionUso = idpeticionUso;
	}
	public List<Integer> getIdpeticionSolicitud() {
		return idpeticionSolicitud;
	}
	public void setIdpeticionSolicitud(List<Integer> idpeticionSolicitud) {
		this.idpeticionSolicitud = idpeticionSolicitud;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpeticionSolicitud == null) ? 0 : idpeticionSolicitud.hashCode());
		result = prime * result + ((idpeticionUso == null) ? 0 : idpeticionUso.hashCode());
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
		IdPeticionDTO other = (IdPeticionDTO) obj;
		if (idpeticionSolicitud == null) {
			if (other.idpeticionSolicitud != null)
				return false;
		} else if (!idpeticionSolicitud.equals(other.idpeticionSolicitud))
			return false;
		if (idpeticionUso == null) {
			if (other.idpeticionUso != null)
				return false;
		} else if (!idpeticionUso.equals(other.idpeticionUso))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "IdPeticionDTO [idpeticionUso=" + idpeticionUso + ", idpeticionSolicitud=" + idpeticionSolicitud + "]";
	}

}
