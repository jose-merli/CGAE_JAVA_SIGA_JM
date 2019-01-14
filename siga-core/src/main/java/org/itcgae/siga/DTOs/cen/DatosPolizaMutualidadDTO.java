package org.itcgae.siga.DTOs.cen;

import java.util.Calendar;

public class DatosPolizaMutualidadDTO {

	
	private int formaPago;
	private int idMutualista;
	private Calendar fEfecto;
	private int opcionesCobertura;
	private String textoOtros;
	
	
	
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
	public int getOpcionesCobertura() {
		return opcionesCobertura;
	}
	public void setOpcionesCobertura(int opcionesCobertura) {
		this.opcionesCobertura = opcionesCobertura;
	}
	public String getTextoOtros() {
		return textoOtros;
	}
	public void setTextoOtros(String textoOtros) {
		this.textoOtros = textoOtros;
	}
	
	
}
