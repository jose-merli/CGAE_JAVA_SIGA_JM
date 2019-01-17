package org.itcgae.siga.DTOs.com;

import java.util.Date;

public class ConsultaPlantillaDTO {
	private String idInstitucion;
	private String idConsulta;
	private String idPlantillaConsulta;
	
	private Date fechaModificacion;
	private String usuModificacion;
	
	private String idClaseComunicacion;
	private String idModeloComunicacion;
	
	private String idPlantillaDocumento;
	
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getIdPlantillaConsulta() {
		return idPlantillaConsulta;
	}
	public void setIdPlantillaConsulta(String idPlantillaConsulta) {
		this.idPlantillaConsulta = idPlantillaConsulta;
	}
	public String getIdPlantillaDocumento() {
		return idPlantillaDocumento;
	}
	public void setIdPlantillaDocumento(String idPlantillaDocumento) {
		this.idPlantillaDocumento = idPlantillaDocumento;
	}

	

	
}
