package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class PlantillasEnvioDTO {
	
	private List<PlantillaEnvioItem> plantillasItem = new ArrayList<PlantillaEnvioItem>();
	private Error error = new Error();
	
	
	public List<PlantillaEnvioItem> getPlantillasItem() {
		return plantillasItem;
	}
	public void setPlantillasItem(List<PlantillaEnvioItem> plantillasItem) {
		this.plantillasItem = plantillasItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	

}
