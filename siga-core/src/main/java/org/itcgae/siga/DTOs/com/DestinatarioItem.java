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
	private ArrayList<ConsultaEnvioItem> listaConsultasEnvio;
	
	
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
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
}
