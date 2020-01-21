package org.itcgae.siga.DTOs.com;

public class PlantillaDatosConsultaDTO {
	
	private String idPlantillaEnvios;
	private String idTipoEnvios;
	private String idConsulta;
	private String idInstitucion;
	private String idClaseComunicacion;


	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idClaseComunicacion == null) ? 0 : idClaseComunicacion.hashCode());
		result = prime * result + ((idConsulta == null) ? 0 : idConsulta.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPlantillaEnvios == null) ? 0 : idPlantillaEnvios.hashCode());
		result = prime * result + ((idTipoEnvios == null) ? 0 : idTipoEnvios.hashCode());
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
		PlantillaDatosConsultaDTO other = (PlantillaDatosConsultaDTO) obj;
		if (idClaseComunicacion == null) {
			if (other.idClaseComunicacion != null)
				return false;
		} else if (!idClaseComunicacion.equals(other.idClaseComunicacion))
			return false;
		if (idConsulta == null) {
			if (other.idConsulta != null)
				return false;
		} else if (!idConsulta.equals(other.idConsulta))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPlantillaEnvios == null) {
			if (other.idPlantillaEnvios != null)
				return false;
		} else if (!idPlantillaEnvios.equals(other.idPlantillaEnvios))
			return false;
		if (idTipoEnvios == null) {
			if (other.idTipoEnvios != null)
				return false;
		} else if (!idTipoEnvios.equals(other.idTipoEnvios))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlantillaDatosConsultaDTO [idPlantillaEnvios=" + idPlantillaEnvios + ", idTipoEnvios=" + idTipoEnvios
				+ ", idConsulta=" + idConsulta + ", idInstitucion=" + idInstitucion + ", idClaseComunicacion="
				+ idClaseComunicacion + "]";
	}
	
	

}
