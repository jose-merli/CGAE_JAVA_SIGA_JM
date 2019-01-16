package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class PlantillasModeloDTO {

	private List<PlantillaModeloItem> plantillas  = new ArrayList<PlantillaModeloItem>();
	private Error error = null;
	
	
	public List<PlantillaModeloItem> getPlantillas() {
		return plantillas;
	}
	public Error getError() {
		return error;
	}
	public void setPlantillas(List<PlantillaModeloItem> plantillas) {
		this.plantillas = plantillas;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
}
