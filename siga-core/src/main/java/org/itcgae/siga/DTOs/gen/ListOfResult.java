package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListOfResult {
	private String denominacion;
	private TipoDeFestivo tipoDeFestivo = new TipoDeFestivo();
	private Municipios municipios = new Municipios();
	private String municipio;
	private String fecha;

	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@JsonProperty("tipoDeFestivo")
	public TipoDeFestivo getTipoDeFestivo() {
		return tipoDeFestivo;
	}

	public void setTipoDeFestivo(TipoDeFestivo tipoDeFestivoObject) {
		tipoDeFestivo = tipoDeFestivoObject;
	}

	@JsonProperty("municipios")
	public Municipios getMunicipios() {
		return municipios;
	}

	public void setMunicipios(Municipios municipiosObject) {
		municipios = municipiosObject;
	}

	@JsonProperty("municipio")
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	@JsonProperty("fecha")
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
