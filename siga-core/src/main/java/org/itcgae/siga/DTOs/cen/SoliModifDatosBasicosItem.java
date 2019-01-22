package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoliModifDatosBasicosItem {

	private String idPersona;
	private String idioma;

	public SoliModifDatosBasicosItem idPersona(String idPersona) {
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
	
	public SoliModifDatosBasicosItem idioma(String idioma) {
		this.idioma = idioma;
		return this;
	}
	
	@JsonProperty("idioma")
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	@Override
	public String toString() {
		return "SoliModifDatosBasicosItem [idPersona=" + idPersona + ", idioma=" + idioma + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		return result;
	}
}
