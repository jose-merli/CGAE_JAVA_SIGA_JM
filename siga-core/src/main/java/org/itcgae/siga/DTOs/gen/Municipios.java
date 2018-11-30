package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Municipios {
	private String codigo;
	private String denominacion;
	private float latitud;
	private float longitud;
	private Provincia provincia = new Provincia();

	@JsonProperty("codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@JsonProperty("latitud")
	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	@JsonProperty("longitud")
	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	@JsonProperty("provincia")
	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provinciaObject) {
		provincia = provinciaObject;
	}

}
