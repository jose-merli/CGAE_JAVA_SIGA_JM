package org.itcgae.siga.DTOs.scs;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboColaOrdenadaItem {

	private String numero;
	private String orden;
	private String por_filas;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getPor_filas() {
		return por_filas;
	}
	public void setPor_filas(String por_filas) {
		this.por_filas = por_filas;
	}
	
	

}
