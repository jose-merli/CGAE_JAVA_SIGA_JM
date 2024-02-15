package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class FichasPlantillaDocumentoDTO {
	private List<FichaPlantillaDocumentoDTO> fichasPlantillaDocumento = new ArrayList<FichaPlantillaDocumentoDTO>();
	private Error error = null;	
	
	
	public List<FichaPlantillaDocumentoDTO> getFichasPlantillaDocumento() {
		return fichasPlantillaDocumento;
	}
	public void setFichasPlantillaDocumento(List<FichaPlantillaDocumentoDTO> fichasPlantillaDocumento) {
		this.fichasPlantillaDocumento = fichasPlantillaDocumento;
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
		result = prime * result + ((fichasPlantillaDocumento == null) ? 0 : fichasPlantillaDocumento.hashCode());
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
		FichasPlantillaDocumentoDTO other = (FichasPlantillaDocumentoDTO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (fichasPlantillaDocumento == null) {
			if (other.fichasPlantillaDocumento != null)
				return false;
		} else if (!fichasPlantillaDocumento.equals(other.fichasPlantillaDocumento))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FichasPlantillaDocumentosDTO [fichasPlantillaDocumento=" + fichasPlantillaDocumento + ", error=" + error
				+ "]";
	}
}
