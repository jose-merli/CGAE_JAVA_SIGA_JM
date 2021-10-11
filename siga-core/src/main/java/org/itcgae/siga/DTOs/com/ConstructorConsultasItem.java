package org.itcgae.siga.DTOs.com;

import java.util.Objects;

public class ConstructorConsultasItem {
	
	private String conector; //AND (Y) U OR (O)
	private String campo;
	private String operador; //Igual a, distinto de
	private String valor;
	
	public String getConector() {
		return conector;
	}
	public void setConector(String conector) {
		this.conector = conector;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(campo, conector, operador, valor);
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
		return Objects.equals(campo, other.campo) && Objects.equals(conector, other.conector)
				&& Objects.equals(operador, other.operador) && Objects.equals(valor, other.valor);
	}
	
	@Override
	public String toString() {
		return "ConstructorConsultasItem [conector=" + conector + ", campo=" + campo + ", operador=" + operador
				+ ", valor=" + valor + "]";
	}
	
}
