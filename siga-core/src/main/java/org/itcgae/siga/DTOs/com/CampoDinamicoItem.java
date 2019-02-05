package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CampoDinamicoItem {
	
	private String campo;
	private String operacion;	
	private String valorDefecto;
	private boolean valorNulo;
	private String tipoDato;
	private String alias;
	private String ayuda;
	private String valor;
	
	private List<Map<String,Object>> valores = new ArrayList<Map<String,Object>>();

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getValorDefecto() {
		return valorDefecto;
	}

	public void setValorDefecto(String valorDefecto) {
		this.valorDefecto = valorDefecto;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAyuda() {
		return ayuda;
	}

	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}

	public boolean isValorNulo() {
		return valorNulo;
	}

	public void setValorNulo(boolean valorNulo) {
		this.valorNulo = valorNulo;
	}

	public List<Map<String,Object>> getValores() {
		return valores;
	}

	public void setValores(List<Map<String,Object>> valores) {
		this.valores = valores;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
