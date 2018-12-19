package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;
public class ConsultaListadoPlantillasDTO {
	private List<PlantillaEnvioItem> listadoPlantillas = new ArrayList<PlantillaEnvioItem>();
	private Error error = null;
	private String idConsulta;
	
	
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public List<PlantillaEnvioItem> getListadoPlantillas() {
		return listadoPlantillas;
	}
	public void setListadoPlantillas(List<PlantillaEnvioItem> listadoPlantillas) {
		this.listadoPlantillas = listadoPlantillas;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
}
