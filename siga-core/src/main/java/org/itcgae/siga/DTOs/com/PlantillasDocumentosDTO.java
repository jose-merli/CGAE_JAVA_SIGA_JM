package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class PlantillasDocumentosDTO {
	private List<PlantillaDocumentoDTO> plantillasDocumentos = new ArrayList<PlantillaDocumentoDTO>();
	private Error error = null;	
	

	public List<PlantillaDocumentoDTO> getPlantillasDocumentos() {
		return plantillasDocumentos;
	}
	public void setPlantillasDocumentos(List<PlantillaDocumentoDTO> plantillasDocumentos) {
		this.plantillasDocumentos = plantillasDocumentos;
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
		result = prime * result + ((plantillasDocumentos == null) ? 0 : plantillasDocumentos.hashCode());
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
		PlantillasDocumentosDTO other = (PlantillasDocumentosDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (plantillasDocumentos == null) {
			if (other.plantillasDocumentos != null)
				return false;
		} else if (!plantillasDocumentos.equals(other.plantillasDocumentos))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlantillasDocumentos [plantillasDocumentos=" + plantillasDocumentos + ", error=" + error
				+ "]";
	}
}
