package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosSolicitudGratuitaDTO {

	private DatosPersonaMutualidadDTO datosPersona;
	private DatosDomicilioMutualidadDTO datosDireccion;
	
	@JsonProperty("datosPersona")
	public DatosPersonaMutualidadDTO getDatosPersona() {
		return datosPersona;
	}
	public void setDatosPersona(DatosPersonaMutualidadDTO datosPersona) {
		this.datosPersona = datosPersona;
	}
	
	@JsonProperty("datosDireccion")
	public DatosDomicilioMutualidadDTO getDatosDireccion() {
		return datosDireccion;
	}
	public void setDatosDireccion(DatosDomicilioMutualidadDTO datosDireccion) {
		this.datosDireccion = datosDireccion;
	}
	
	
}
