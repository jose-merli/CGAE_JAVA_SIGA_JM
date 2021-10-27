package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class DestinatariosSeriesItem {
	
	private String idPersona;
	private String nombre;
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
		return Objects.hash(correoElectronico, domicilio, idPersona, movil, nifCif, nombre);
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
		return Objects.equals(correoElectronico, other.correoElectronico) && Objects.equals(domicilio, other.domicilio)
				&& Objects.equals(idPersona, other.idPersona) && Objects.equals(movil, other.movil)
				&& Objects.equals(nifCif, other.nifCif) && Objects.equals(nombre, other.nombre);
	}
	
	@Override
	public String toString() {
		return "DestinatariosSeriesItem [idPersona=" + idPersona + ", nombre=" + nombre + ", nifCif=" + nifCif
				+ ", movil=" + movil + ", correoElectronico=" + correoElectronico + ", domicilio=" + domicilio + "]";
	}

}
