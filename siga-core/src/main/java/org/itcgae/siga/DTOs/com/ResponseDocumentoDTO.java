package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ResponseDocumentoDTO {
	
	private String rutaDocumento;
	private String nombreDocumento;
	private String idEnvio;
	private String idPlantillaDocumento;
	private String idioma;
	private String idIdioma;
	private Object idDocumento;
	private Short idInstitucion;
	private Error error;
	
	
	public String getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getRutaDocumento() {
		return rutaDocumento;
	}
	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public String getIdPlantillaDocumento() {
		return idPlantillaDocumento;
	}
	public void setIdPlantillaDocumento(String idPlantillaDocumento) {
		this.idPlantillaDocumento = idPlantillaDocumento;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	public Object getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Object idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

}
