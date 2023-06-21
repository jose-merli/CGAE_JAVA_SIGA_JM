package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class DocumentoPlantillaItem {
	
	private String rutaDocumento;
	private String nombreDocumento;
	private String idPlantillaDocumento;
	private String idioma;
	private String idIdioma;
	private String idInforme;
	private String idModeloComunicacion;
	private String idClaseComunicacion;
	private Error error;
	
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
	public String getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((idClaseComunicacion == null) ? 0 : idClaseComunicacion.hashCode());
		result = prime * result + ((idIdioma == null) ? 0 : idIdioma.hashCode());
		result = prime * result + ((idInforme == null) ? 0 : idInforme.hashCode());
		result = prime * result + ((idModeloComunicacion == null) ? 0 : idModeloComunicacion.hashCode());
		result = prime * result + ((idPlantillaDocumento == null) ? 0 : idPlantillaDocumento.hashCode());
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + ((nombreDocumento == null) ? 0 : nombreDocumento.hashCode());
		result = prime * result + ((rutaDocumento == null) ? 0 : rutaDocumento.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoPlantillaItem other = (DocumentoPlantillaItem) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (idClaseComunicacion == null) {
			if (other.idClaseComunicacion != null)
				return false;
		} else if (!idClaseComunicacion.equals(other.idClaseComunicacion))
			return false;
		if (idIdioma == null) {
			if (other.idIdioma != null)
				return false;
		} else if (!idIdioma.equals(other.idIdioma))
			return false;
		if (idInforme == null) {
			if (other.idInforme != null)
				return false;
		} else if (!idInforme.equals(other.idInforme))
			return false;
		if (idModeloComunicacion == null) {
			if (other.idModeloComunicacion != null)
				return false;
		} else if (!idModeloComunicacion.equals(other.idModeloComunicacion))
			return false;
		if (idPlantillaDocumento == null) {
			if (other.idPlantillaDocumento != null)
				return false;
		} else if (!idPlantillaDocumento.equals(other.idPlantillaDocumento))
			return false;
		if (idioma == null) {
			if (other.idioma != null)
				return false;
		} else if (!idioma.equals(other.idioma))
			return false;
		if (nombreDocumento == null) {
			if (other.nombreDocumento != null)
				return false;
		} else if (!nombreDocumento.equals(other.nombreDocumento))
			return false;
		if (rutaDocumento == null) {
			if (other.rutaDocumento != null)
				return false;
		} else if (!rutaDocumento.equals(other.rutaDocumento))
			return false;
		return true;
	}
	
	
	

}
