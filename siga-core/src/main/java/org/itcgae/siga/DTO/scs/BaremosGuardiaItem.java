package org.itcgae.siga.DTO.scs;

import java.util.Date;

import lombok.Data;

@Data
public class BaremosGuardiaItem {
	String guardias;
	String nDias;
	String baremo;
	String dias;
	Double disponibilidad;
	Short numMinimoSimple;
	Short simpleOImporteIndividual;
	Short naPartir;
	Short maximo;
	String porDia;
	String idFacturaciones[];
	String idTurnos[];
   String idGuardias[];
   boolean historico;
   String precioHito;
   String idInstitucion;
   String idHito;
   String nomturno;
   String nomguardia;
   String idTurno;
   String idGuardia;
   String idhitoconfiguracion;
   String agrupar;
   String precioMin;
   String precioMax;
   Date fechaMod;
 
//	public String getGuardias() {
//		return guardias;
//	}
//	public void setGuardias(String guardias) {
//		this.guardias = guardias;
//	}
//	public String getnDias() {
//		return nDias;
//	}
//	public void setnDias(String nDias) {
//		this.nDias = nDias;
//	}
//	public String getBaremo() {
//		return baremo;
//	}
//	public void setBaremo(String baremo) {
//		this.baremo = baremo;
//	}
//	public String getDias() {
//		return dias;
//	}
//	public void setDias(String dias) {
//		this.dias = dias;
//	}
//	public Double getDisponibilidad() {
//		return disponibilidad;
//	}
//	public void setDisponibilidad(Double disponibilidad) {
//		this.disponibilidad = disponibilidad;
//	}
//	public Short getNumMinimoSimple() {
//		return numMinimoSimple;
//	}
//	public void setNumMinimoSimple(Short numMinimoSimple) {
//		this.numMinimoSimple = numMinimoSimple;
//	}
//	public Short getSimpleOImporteIndividual() {
//		return simpleOImporteIndividual;
//	}
//	public void setSimpleOImporteIndividual(Short simpleOImporteIndividual) {
//		this.simpleOImporteIndividual = simpleOImporteIndividual;
//	}
//	public Short getNumDoblar() {
//		return numDoblar;
//	}
//	public void setNumDoblar(Short numDoblar) {
//		this.numDoblar = numDoblar;
//	}
//	public Short getMaximo() {
//		return maximo;
//	}
//	public void setMaximo(Short maximo) {
//		this.maximo = maximo;
//	}
//	public String getPorDia() {
//		return porDia;
//	}
//	public void setPorDia(String porDia) {
//		this.porDia = porDia;
//	}
	
	
}
