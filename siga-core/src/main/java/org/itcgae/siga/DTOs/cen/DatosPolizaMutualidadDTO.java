package org.itcgae.siga.DTOs.cen;

import java.util.Calendar;

public class DatosPolizaMutualidadDTO {

	
	private int formaPago;
	private int idMutualista;
	private Calendar fEfecto;
	private String opcionesCobertura;
	private String textoOtros;
	private String cuotaMensual;
	private String capitalObjetivo;
	private int idCobertura;
	
	
	public int getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(int formaPago) {
		this.formaPago = formaPago;
	}
	public int getIdMutualista() {
		return idMutualista;
	}
	public void setIdMutualista(int idMutualista) {
		this.idMutualista = idMutualista;
	}
	public Calendar getfEfecto() {
		return fEfecto;
	}
	public void setfEfecto(Calendar fEfecto) {
		this.fEfecto = fEfecto;
	}
	public String getOpcionesCobertura() {
		return opcionesCobertura;
	}
	public void setOpcionesCobertura(String opcionesCobertura) {
		this.opcionesCobertura = opcionesCobertura;
	}
	public String getTextoOtros() {
		return textoOtros;
	}
	public void setTextoOtros(String textoOtros) {
		this.textoOtros = textoOtros;
	}
	public String getCuotaMensual() {
		return cuotaMensual;
	}
	public void setCuotaMensual(String cuotaMensual) {
		this.cuotaMensual = cuotaMensual;
	}
	public String getCapitalObjetivo() {
		return capitalObjetivo;
	}
	public void setCapitalObjetivo(String capitalObjetivo) {
		this.capitalObjetivo = capitalObjetivo;
	}
	public int getIdCobertura() {
		return idCobertura;
	}
	public void setIdCobertura(int idCobertura) {
		this.idCobertura = idCobertura;
	}
	
	
}
