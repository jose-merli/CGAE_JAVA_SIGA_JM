package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosSolicitudGratuitaDTO {

	private DatosPersonaMutualidadDTO datosPersona;
	private DatosDomicilioMutualidadDTO datosDireccion;
	private DatosBancariosMutualidadDTO datosBancarios;
	private DatosPolizaMutualidadDTO datosPoliza;
	private DatosBeneficiarioMutualidadDTO datosBeneficiario;

	
	
	@JsonProperty("datosBeneficiario")
	public DatosBeneficiarioMutualidadDTO getDatosBeneficiario() {
		return datosBeneficiario;
	}
	public void setDatosBeneficiario(DatosBeneficiarioMutualidadDTO datosBeneficiario) {
		this.datosBeneficiario = datosBeneficiario;
	}
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
	public DatosBancariosMutualidadDTO getDatosBancarios() {
		return datosBancarios;
	}
	public void setDatosBancarios(DatosBancariosMutualidadDTO datosBancarios) {
		this.datosBancarios = datosBancarios;
	}
	public DatosPolizaMutualidadDTO getDatosPoliza() {
		return datosPoliza;
	}
	public void setDatosPoliza(DatosPolizaMutualidadDTO datosPoliza) {
		this.datosPoliza = datosPoliza;
	}
	
	
}
