package org.itcgae.siga.DTOs.cen;

public class AsociarPersonaDTO {
	private String idPersona;
	private String idPersonaAsociar;
	private String tipoPersona;
	private String idInstitucion;
	
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersonaAsociar() {
		return idPersonaAsociar;
	}
	public void setIdPersonaAsociar(String idPersonaDesasociar) {
		this.idPersonaAsociar = idPersonaDesasociar;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
}
