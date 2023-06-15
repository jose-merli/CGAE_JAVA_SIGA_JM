package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class DestinatariosSeriesItem {
	
	private String idPersona;
	private String idSerieFacturacion;
	private String idInstitucion;
	private String numeroInstitucion;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nif;
	private String movil;
	private String correoElectronico;
	private String domicilio;

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getMovil() {
		return movil;
	}
	
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getIdSerieFacturacion() {
		return idSerieFacturacion;
	}

	public void setIdSerieFacturacion(String idSerieFacturacion) {
		this.idSerieFacturacion = idSerieFacturacion;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNumeroInstitucion() {
		return numeroInstitucion;
	}

	public void setNumeroInstitucion(String numeroInstitucion) {
		this.numeroInstitucion = numeroInstitucion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DestinatariosSeriesItem that = (DestinatariosSeriesItem) o;
		return Objects.equals(idPersona, that.idPersona) &&
				Objects.equals(idSerieFacturacion, that.idSerieFacturacion) &&
				Objects.equals(idInstitucion, that.idInstitucion) &&
				Objects.equals(numeroInstitucion, that.numeroInstitucion) &&
				Objects.equals(nombre, that.nombre) &&
				Objects.equals(apellido1, that.apellido1) &&
				Objects.equals(apellido2, that.apellido2) &&
				Objects.equals(nif, that.nif) &&
				Objects.equals(movil, that.movil) &&
				Objects.equals(correoElectronico, that.correoElectronico) &&
				Objects.equals(domicilio, that.domicilio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPersona, idSerieFacturacion, idInstitucion, numeroInstitucion, nombre, apellido1, apellido2, nif, movil, correoElectronico, domicilio);
	}

	@Override
	public String toString() {
		return "DestinatariosSeriesItem{" +
				"idPersona='" + idPersona + '\'' +
				", idSerieFacturacion='" + idSerieFacturacion + '\'' +
				", idInstitucion='" + idInstitucion + '\'' +
				", numeroInstitucion='" + numeroInstitucion + '\'' +
				", nombre='" + nombre + '\'' +
				", apellido1='" + apellido1 + '\'' +
				", apellido2='" + apellido2 + '\'' +
				", nif='" + nif + '\'' +
				", movil='" + movil + '\'' +
				", correoElectronico='" + correoElectronico + '\'' +
				", domicilio='" + domicilio + '\'' +
				'}';
	}
}
