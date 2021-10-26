package org.itcgae.siga.DTOs.com;

import java.util.Objects;

public class ConstructorConsultasItem {
	
	private int orden; 
	private String conector; //AND (Y) U OR (O), columna OPERADOR en con_criterioconsulta
	private String abrirparentesis;
	private String campo; //columna NOMBREENCONSULTA en con_campoconsulta
	private String operador; //Igual a, distinto de (se encuentran en con_operacionconsulta)
	private String simbolo;
	private String valor;
	private String cerrarparentesis;
	
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getConector() {
		return conector;
	}
	public void setConector(String conector) {
		this.conector = conector;
	}
	public String getAbrirparentesis() {
		return abrirparentesis;
	}
	public void setAbrirparentesis(String abrirparentesis) {
		this.abrirparentesis = abrirparentesis;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getCerrarparentesis() {
		return cerrarparentesis;
	}
	public void setCerrarparentesis(String cerrarparentesis) {
		this.cerrarparentesis = cerrarparentesis;
	}
	@Override
	public int hashCode() {
		return Objects.hash(abrirparentesis, campo, cerrarparentesis, conector, operador, orden, simbolo, valor);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructorConsultasItem other = (ConstructorConsultasItem) obj;
		return Objects.equals(abrirparentesis, other.abrirparentesis) && Objects.equals(campo, other.campo)
				&& Objects.equals(cerrarparentesis, other.cerrarparentesis) && Objects.equals(conector, other.conector)
				&& Objects.equals(operador, other.operador) && orden == other.orden
				&& Objects.equals(simbolo, other.simbolo) && Objects.equals(valor, other.valor);
	}
	
	@Override
	public String toString() {
		return "ConstructorConsultasItem [orden=" + orden + ", conector=" + conector + ", abrirparentesis="
				+ abrirparentesis + ", campo=" + campo + ", operador=" + operador + ", simbolo=" + simbolo + ", valor="
				+ valor + ", cerrarparentesis=" + cerrarparentesis + "]";
	}
	
}
