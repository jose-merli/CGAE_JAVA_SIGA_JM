package org.itcgae.siga.DTOs.com;

import java.util.List;

public class ModelosEnvioItem {
	
	private List<DatosEnvioDTO> listaDatosEnvio;	
	private Integer idPlantillaEnvio;	
	private Short idTipoEnvio;	
	private Long idModeloComunicacion;
	

	public List<DatosEnvioDTO> getListaDatosEnvio() {
		return listaDatosEnvio;
	}
	public void setListaDatosEnvio(List<DatosEnvioDTO> listaDatosEnvio) {
		this.listaDatosEnvio = listaDatosEnvio;
	}
	public Integer getIdPlantillaEnvio() {
		return idPlantillaEnvio;
	}
	public void setIdPlantillaEnvio(Integer idPlantillaEnvio) {
		this.idPlantillaEnvio = idPlantillaEnvio;
	}
	public Short getIdTipoEnvio() {
		return idTipoEnvio;
	}
	public void setIdTipoEnvio(Short idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	public Long getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(Long idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	
}
