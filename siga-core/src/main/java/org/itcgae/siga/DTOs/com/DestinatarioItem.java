package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;

public class DestinatarioItem {
	
	private String idPersona;
	private String NIFCIF;
	private String apellidos1;
	private String apellidos2;
	private String nombre;
	private String correoElectronico;
	private String movil;
	private String domicilio;
	private String nombreCompleto;
	private ArrayList<ConsultaEnvioItem> listaConsultasEnvio;
	private String tratamiento;
	private String idioma;
	private String direccion;
	private String codigoPostal;
	private String idPais;
	private String idPoblacion;
	private String idProvincia;
	private String poblacionExtranjera;
	private CamposPlantillaEnvio camposEnvio;
	
	public DestinatarioItem(DestinatarioItem ori) {
		this.idPersona = ori.idPersona;
		this.NIFCIF = ori.NIFCIF;
		this.apellidos1 = ori.apellidos1;
		this.apellidos2 = ori.apellidos2;
		this.nombre =  ori.nombre;
		this.correoElectronico =  ori.correoElectronico;
		this.movil =  ori.movil;
		this.domicilio =  ori.domicilio;
		this.nombreCompleto =  ori.nombreCompleto;
		this.listaConsultasEnvio =  ori.listaConsultasEnvio;
		this.tratamiento =  ori.tratamiento;
		this.idioma =  ori.idioma;
		this.direccion =  ori.direccion;
		this.codigoPostal =  ori.codigoPostal;
		this.idPais =  ori.idPais;
		this.idPoblacion =  ori.idPoblacion;
		this.idProvincia =  ori.idProvincia;
		this.poblacionExtranjera =  ori.poblacionExtranjera;
		this.camposEnvio = ori.camposEnvio;
	}
	
	public DestinatarioItem() {
		// TODO Auto-generated constructor stub
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPoblacion() {
		return idPoblacion;
	}
	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public String getMovil() {
		return movil;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNIFCIF() {
		return NIFCIF;
	}
	public void setNIFCIF(String nIFCIF) {
		NIFCIF = nIFCIF;
	}
	public String getApellidos1() {
		return apellidos1;
	}
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}
	public String getApellidos2() {
		return apellidos2;
	}
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<ConsultaEnvioItem> getListaConsultasEnvio() {
		return listaConsultasEnvio;
	}
	public void setListaConsultasEnvio(ArrayList<ConsultaEnvioItem> listaConsultasEnvio) {
		this.listaConsultasEnvio = listaConsultasEnvio;
	}
	public String getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPoblacionExtranjera() {
		return poblacionExtranjera;
	}
	public void setPoblacionExtranjera(String poblacionExtranjera) {
		this.poblacionExtranjera = poblacionExtranjera;
	}


	public CamposPlantillaEnvio getCamposEnvio() {
		return camposEnvio;
	}

	public void setCamposEnvio(CamposPlantillaEnvio camposEnvio) {
		this.camposEnvio = camposEnvio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NIFCIF == null) ? 0 : NIFCIF.hashCode());
		result = prime * result + ((apellidos1 == null) ? 0 : apellidos1.hashCode());
		result = prime * result + ((apellidos2 == null) ? 0 : apellidos2.hashCode());
		result = prime * result + ((camposEnvio == null) ? 0 : camposEnvio.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((idPais == null) ? 0 : idPais.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idPoblacion == null) ? 0 : idPoblacion.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + ((listaConsultasEnvio == null) ? 0 : listaConsultasEnvio.hashCode());
		result = prime * result + ((movil == null) ? 0 : movil.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreCompleto == null) ? 0 : nombreCompleto.hashCode());
		result = prime * result + ((poblacionExtranjera == null) ? 0 : poblacionExtranjera.hashCode());
		result = prime * result + ((tratamiento == null) ? 0 : tratamiento.hashCode());
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
		DestinatarioItem other = (DestinatarioItem) obj;
		if (NIFCIF == null) {
			if (other.NIFCIF != null)
				return false;
		} else if (!NIFCIF.equals(other.NIFCIF))
			return false;
		if (apellidos1 == null) {
			if (other.apellidos1 != null)
				return false;
		} else if (!apellidos1.equals(other.apellidos1))
			return false;
		if (apellidos2 == null) {
			if (other.apellidos2 != null)
				return false;
		} else if (!apellidos2.equals(other.apellidos2))
			return false;
		if (camposEnvio == null) {
			if (other.camposEnvio != null)
				return false;
		} else if (!camposEnvio.equals(other.camposEnvio))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (idPais == null) {
			if (other.idPais != null)
				return false;
		} else if (!idPais.equals(other.idPais))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idPoblacion == null) {
			if (other.idPoblacion != null)
				return false;
		} else if (!idPoblacion.equals(other.idPoblacion))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (idioma == null) {
			if (other.idioma != null)
				return false;
		} else if (!idioma.equals(other.idioma))
			return false;
		if (listaConsultasEnvio == null) {
			if (other.listaConsultasEnvio != null)
				return false;
		} else if (!listaConsultasEnvio.equals(other.listaConsultasEnvio))
			return false;
		if (movil == null) {
			if (other.movil != null)
				return false;
		} else if (!movil.equals(other.movil))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreCompleto == null) {
			if (other.nombreCompleto != null)
				return false;
		} else if (!nombreCompleto.equals(other.nombreCompleto))
			return false;
		if (poblacionExtranjera == null) {
			if (other.poblacionExtranjera != null)
				return false;
		} else if (!poblacionExtranjera.equals(other.poblacionExtranjera))
			return false;
		if (tratamiento == null) {
			if (other.tratamiento != null)
				return false;
		} else if (!tratamiento.equals(other.tratamiento))
			return false;
		return true;
	}
	
	
}
