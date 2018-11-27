package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoliModifFotoItem {
	private String idPersona;
	private String expFoto;

	public SoliModifFotoItem idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	public SoliModifFotoItem expFoto(String expFoto) {
		this.expFoto = expFoto;
		return this;
	}
	
	@JsonProperty("expFoto")
	public String getExpFoto() {
		return expFoto;
	}
	public void setExpFoto(String expFoto) {
		this.expFoto = expFoto;
	}

	@Override
	public String toString() {
		return "SoliModifFotoItem [idPersona=" + idPersona + ", expFoto=" + expFoto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expFoto == null) ? 0 : expFoto.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		return result;
	}
}
