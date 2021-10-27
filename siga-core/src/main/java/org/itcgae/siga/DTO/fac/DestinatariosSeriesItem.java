package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class DestinatariosSeriesItem {
	
	private String idPersona;
	private String nombre;
	private String apellidos1;
	private String apellidos2;
	private String nifCif;
	private String movil;
	private String correoElectronico;
	private String domicilio;
	
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
	
	public String getNifCif() {
		return nifCif;
	}
	
	public void setNifCif(String nifCif) {
		this.nifCif = nifCif;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(apellidos1, apellidos2, correoElectronico, domicilio, idPersona, movil, nifCif, nombre);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DestinatariosSeriesItem other = (DestinatariosSeriesItem) obj;
		return Objects.equals(apellidos1, other.apellidos1) && Objects.equals(apellidos2, other.apellidos2)
				&& Objects.equals(correoElectronico, other.correoElectronico)
				&& Objects.equals(domicilio, other.domicilio) && Objects.equals(idPersona, other.idPersona)
				&& Objects.equals(movil, other.movil) && Objects.equals(nifCif, other.nifCif)
				&& Objects.equals(nombre, other.nombre);
	}
	
	@Override
	public String toString() {
		return "DestinatariosSeriesItem [idPersona=" + idPersona + ", nombre=" + nombre + ", apellidos1=" + apellidos1
				+ ", apellidos2=" + apellidos2 + ", nifCif=" + nifCif + ", movil=" + movil + ", correoElectronico="
				+ correoElectronico + ", domicilio=" + domicilio + "]";
	}
	
	
}
