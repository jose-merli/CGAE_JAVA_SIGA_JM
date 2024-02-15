package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColegiadoJGItem {

	private String idInstitucion;
	private String nColegiado;
	private String idPersona;
	private String nifcif;
	private String nombre;

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("nColegiado")
	public String getnColegiado() {
		return nColegiado;
	}

	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}

	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@JsonProperty("nifcif")
	public String getNifcif() {
		return nifcif;
	}

	public void setNifcif(String nifcif) {
		this.nifcif = nifcif;
	}

	@Override
	public String toString() {
		return "ColegiadoJGItem [idInstitucion=" + idInstitucion + ", nColegiado=" + nColegiado + ", idPersona=" + idPersona + ", nifcif=" + nifcif + ", nombre=" + nombre + "]";
	}
}
