package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class DatosIntegrantesWS {
	
	private String idSociedad;
	private String idInstitucionSociedad;
	private Date fechaModificacion;
	private String tipoIdentificacion;
	private String nifCif;
	private String nombre;
	private String apellidos1;
	private String apellidos2;
	private String personaJuridica;	
	private String profesionalAbogado;
	private String profesional;
	private String profesion;
	private String codigocolegio;
	private String descripcionColegio;
	private String numColegiado;
	private String socio;
	private String cargo;
	private String descripcionCargo;
	private Date fechaBajaCargo;
	private String fechaCargo;
	private String sociedad;
	private String idPersona;
	private String idInstitucionCliente;
	private String codigoColegioCliente;
	private String descripcionColegioCliente;
	
	public String getIdSociedad() {
		return idSociedad;
	}
	public void setIdSociedad(String idSociedad) {
		this.idSociedad = idSociedad;
	}
	public String getIdInstitucionSociedad() {
		return idInstitucionSociedad;
	}
	public void setIdInstitucionSociedad(String idInstitucionSociedad) {
		this.idInstitucionSociedad = idInstitucionSociedad;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNifCif() {
		return nifCif;
	}
	public void setNifCif(String nifCif) {
		this.nifCif = nifCif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getPersonaJuridica() {
		return personaJuridica;
	}
	public void setPersonaJuridica(String personaJuridica) {
		this.personaJuridica = personaJuridica;
	}
	public String getProfesionalAbogado() {
		return profesionalAbogado;
	}
	public void setProfesionalAbogado(String profesionalAbogado) {
		this.profesionalAbogado = profesionalAbogado;
	}
	public String getProfesional() {
		return profesional;
	}
	public void setProfesional(String profesional) {
		this.profesional = profesional;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	
	public String getSocio() {
		return socio;
	}
	public void setSocio(String socio) {
		this.socio = socio;
	}
	public String getCodigocolegio() {
		return codigocolegio;
	}
	public void setCodigocolegio(String codigocolegio) {
		this.codigocolegio = codigocolegio;
	}
	public String getDescripcionColegio() {
		return descripcionColegio;
	}
	public void setDescripcionColegio(String descripcionColegio) {
		this.descripcionColegio = descripcionColegio;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getDescripcionCargo() {
		return descripcionCargo;
	}
	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}
	public Date getFechaBajaCargo() {
		return fechaBajaCargo;
	}
	public void setFechaBajaCargo(Date fechaBajaCargo) {
		this.fechaBajaCargo = fechaBajaCargo;
	}
	public String getFechaCargo() {
		return fechaCargo;
	}
	public void setFechaCargo(String fechaCargo) {
		this.fechaCargo = fechaCargo;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdInstitucionCliente() {
		return idInstitucionCliente;
	}
	public void setIdInstitucionCliente(String idInstitucionCliente) {
		this.idInstitucionCliente = idInstitucionCliente;
	}
	public String getCodigoColegioCliente() {
		return codigoColegioCliente;
	}
	public void setCodigoColegioCliente(String codigoColegioCliente) {
		this.codigoColegioCliente = codigoColegioCliente;
	}
	public String getDescripcionColegioCliente() {
		return descripcionColegioCliente;
	}
	public void setDescripcionColegioCliente(String descripcionColegioCliente) {
		this.descripcionColegioCliente = descripcionColegioCliente;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos1 == null) ? 0 : apellidos1.hashCode());
		result = prime * result + ((apellidos2 == null) ? 0 : apellidos2.hashCode());
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((codigocolegio == null) ? 0 : codigocolegio.hashCode());
		result = prime * result + ((descripcionCargo == null) ? 0 : descripcionCargo.hashCode());
		result = prime * result + ((descripcionColegio == null) ? 0 : descripcionColegio.hashCode());
		result = prime * result + ((fechaBajaCargo == null) ? 0 : fechaBajaCargo.hashCode());
		result = prime * result + ((fechaCargo == null) ? 0 : fechaCargo.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idInstitucionSociedad == null) ? 0 : idInstitucionSociedad.hashCode());
		result = prime * result + ((idSociedad == null) ? 0 : idSociedad.hashCode());
		result = prime * result + ((nifCif == null) ? 0 : nifCif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((personaJuridica == null) ? 0 : personaJuridica.hashCode());
		result = prime * result + ((profesion == null) ? 0 : profesion.hashCode());
		result = prime * result + ((profesional == null) ? 0 : profesional.hashCode());
		result = prime * result + ((profesionalAbogado == null) ? 0 : profesionalAbogado.hashCode());
		result = prime * result + ((sociedad == null) ? 0 : sociedad.hashCode());
		result = prime * result + ((socio == null) ? 0 : socio.hashCode());
		result = prime * result + ((tipoIdentificacion == null) ? 0 : tipoIdentificacion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idInstitucionCliente == null) ? 0 : idInstitucionCliente.hashCode());
		result = prime * result + ((codigoColegioCliente == null) ? 0 : codigoColegioCliente.hashCode());
		result = prime * result + ((descripcionColegioCliente == null) ? 0 : descripcionColegioCliente.hashCode());
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
		DatosIntegrantesWS other = (DatosIntegrantesWS) obj;
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
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (codigocolegio == null) {
			if (other.codigocolegio != null)
				return false;
		} else if (!codigocolegio.equals(other.codigocolegio))
			return false;
		if (descripcionCargo == null) {
			if (other.descripcionCargo != null)
				return false;
		} else if (!descripcionCargo.equals(other.descripcionCargo))
			return false;
		if (descripcionColegio == null) {
			if (other.descripcionColegio != null)
				return false;
		} else if (!descripcionColegio.equals(other.descripcionColegio))
			return false;
		if (fechaBajaCargo == null) {
			if (other.fechaBajaCargo != null)
				return false;
		} else if (!fechaBajaCargo.equals(other.fechaBajaCargo))
			return false;
		if (fechaCargo == null) {
			if (other.fechaCargo != null)
				return false;
		} else if (!fechaCargo.equals(other.fechaCargo))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (idInstitucionSociedad == null) {
			if (other.idInstitucionSociedad != null)
				return false;
		} else if (!idInstitucionSociedad.equals(other.idInstitucionSociedad))
			return false;
		if (idSociedad == null) {
			if (other.idSociedad != null)
				return false;
		} else if (!idSociedad.equals(other.idSociedad))
			return false;
		if (nifCif == null) {
			if (other.nifCif != null)
				return false;
		} else if (!nifCif.equals(other.nifCif))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numColegiado == null) {
			if (other.numColegiado != null)
				return false;
		} else if (!numColegiado.equals(other.numColegiado))
			return false;
		if (personaJuridica == null) {
			if (other.personaJuridica != null)
				return false;
		} else if (!personaJuridica.equals(other.personaJuridica))
			return false;
		if (profesion == null) {
			if (other.profesion != null)
				return false;
		} else if (!profesion.equals(other.profesion))
			return false;
		if (profesional == null) {
			if (other.profesional != null)
				return false;
		} else if (!profesional.equals(other.profesional))
			return false;
		if (profesionalAbogado == null) {
			if (other.profesionalAbogado != null)
				return false;
		} else if (!profesionalAbogado.equals(other.profesionalAbogado))
			return false;
		if (sociedad == null) {
			if (other.sociedad != null)
				return false;
		} else if (!sociedad.equals(other.sociedad))
			return false;
		if (socio == null) {
			if (other.socio != null)
				return false;
		} else if (!socio.equals(other.socio))
			return false;
		if (tipoIdentificacion == null) {
			if (other.tipoIdentificacion != null)
				return false;
		} else if (!tipoIdentificacion.equals(other.tipoIdentificacion))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idInstitucionCliente == null) {
			if (other.idInstitucionCliente != null)
				return false;
		} else if (!idInstitucionCliente.equals(other.idInstitucionCliente))
			return false;
		if (codigoColegioCliente == null) {
			if (other.codigoColegioCliente != null)
				return false;
		} else if (!codigoColegioCliente.equals(other.codigoColegioCliente))
			return false;
		if (descripcionColegioCliente == null) {
			if (other.descripcionColegioCliente != null)
				return false;
		} else if (!descripcionColegioCliente.equals(other.descripcionColegioCliente))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DatosIntegrantesWS [idSociedad=" + idSociedad + ", idInstitucionSociedad=" + idInstitucionSociedad
				+ ", fechaModificacion=" + fechaModificacion + ", tipoIdentificacion=" + tipoIdentificacion
				+ ", nifCif=" + nifCif + ", nombre=" + nombre + ", apellidos1=" + apellidos1 + ", apellidos2="
				+ apellidos2 + ", personaJuridica=" + personaJuridica + ", profesionalAbogado=" + profesionalAbogado
				+ ", profesional=" + profesional + ", profesion=" + profesion + ", codigocolegio=" + codigocolegio
				+ ", descripcionColegio=" + descripcionColegio + ", numColegiado=" + numColegiado + ", socio=" + socio
				+ ", cargo=" + cargo + ", descripcionCargo=" + descripcionCargo + ", fechaBajaCargo=" + fechaBajaCargo
				+ ", fechaCargo=" + fechaCargo + ", sociedad=" + sociedad + ", idPersona=" + idPersona
				+ ", idInstitucionCliente=" + idInstitucionCliente + ", codigoColegioCliente=" + codigoColegioCliente
				+ ", descripcionColegioCliente=" + descripcionColegioCliente + "]";
	}
	
}
