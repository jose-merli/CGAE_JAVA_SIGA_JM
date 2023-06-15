package org.itcgae.siga.DTOs.com;

import java.util.Objects;

public class Criterio {
    public String operador;
    public boolean abrirParentesis;
    public boolean cerrarParentesis;
    public String filtro;
    public int orden;
    
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public boolean isAbrirParentesis() {
		return abrirParentesis;
	}
	public void setAbrirParentesis(boolean abrirParentesis) {
		this.abrirParentesis = abrirParentesis;
	}
	public boolean isCerrarParentesis() {
		return cerrarParentesis;
	}
	public void setCerrarParentesis(boolean cerrarParentesis) {
		this.cerrarParentesis = cerrarParentesis;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	@Override
	public int hashCode() {
		return Objects.hash(abrirParentesis, cerrarParentesis, filtro, operador, orden);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criterio other = (Criterio) obj;
		return abrirParentesis == other.abrirParentesis && cerrarParentesis == other.cerrarParentesis
				&& Objects.equals(filtro, other.filtro) && Objects.equals(operador, other.operador)
				&& orden == other.orden;
	}
	@Override
	public String toString() {
		return "Criterio [operador=" + operador + ", abrirParentesis=" + abrirParentesis + ", cerrarParentesis="
				+ cerrarParentesis + ", filtro=" + filtro + ", orden=" + orden + "]";
	}

	
    
}
