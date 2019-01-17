package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class PlantillasDocumentosDTO {
	private List<PlantillaModeloDocumentoDTO> plantillasModeloDocumentos = new ArrayList<PlantillaModeloDocumentoDTO>();
	private Error error = null;	
	

	public List<PlantillaModeloDocumentoDTO> getPlantillasModeloDocumentos() {
		return plantillasModeloDocumentos;
	}
	public void setPlantillasModeloDocumentos(List<PlantillaModeloDocumentoDTO> plantillasModeloDocumentos) {
		this.plantillasModeloDocumentos = plantillasModeloDocumentos;
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
		result = prime * result + ((plantillasModeloDocumentos == null) ? 0 : plantillasModeloDocumentos.hashCode());
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
		if (plantillasModeloDocumentos == null) {
			if (other.plantillasModeloDocumentos != null)
				return false;
		} else if (!plantillasModeloDocumentos.equals(other.plantillasModeloDocumentos))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlantillasModeloDocumentos [plantillasModeloDocumentos=" + plantillasModeloDocumentos + ", error=" + error
				+ "]";
	}
}
