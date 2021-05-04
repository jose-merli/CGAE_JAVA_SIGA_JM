package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class CambioLetradoItem {

	public String ano;
	public int idTurno; 
	public int numero;
	public String idPersonaSaliente;
	public String observaciones; 
	public String motivoRenuncia; 
	public String fechaDesignacionSaliente; 
	public Date fechaSolRenuncia;
    public Date fechaDesignacionEntrante; 
    public String idPersonaEntrante;
//    ano: "D2021/39"
//    	fechaDesignacionEntrante: Wed May 19 2021 00:00:00 GMT+0200 (hora de verano de Europa central) {}
//    	fechaDesignacionSaliente: "28/04/2021"
//    	fechaSolRenuncia: Tue May 04 2021 12:55:58 GMT+0200 (hora de verano de Europa central) {}
//    	idPersonaEntrante: undefined
//    	idPersonaSaliente: "3500000039"
//    	idTurno: 3931
//    	motivoRenuncia: "7"
//    	numero: 39
//    	observaciones: undefined
    
    public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getIdPersonaSaliente() {
		return idPersonaSaliente;
	}
	public void setIdPersonaSaliente(String idPersonaSaliente) {
		this.idPersonaSaliente = idPersonaSaliente;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getMotivoRenuncia() {
		return motivoRenuncia;
	}
	public void setMotivoRenuncia(String motivoRenuncia) {
		this.motivoRenuncia = motivoRenuncia;
	}
	public String getFechaDesignacionSaliente() {
		return fechaDesignacionSaliente;
	}
	public void setFechaDesignacionSaliente(String fechaDesignacionSaliente) {
		this.fechaDesignacionSaliente = fechaDesignacionSaliente;
	}
	public Date getFechaSolRenuncia() {
		return fechaSolRenuncia;
	}
	public void setFechaSolRenuncia(Date fechaSolRenuncia) {
		this.fechaSolRenuncia = fechaSolRenuncia;
	}
	public Date getFechaDesignacionEntrante() {
		return fechaDesignacionEntrante;
	}
	public void setFechaDesignacionEntrante(Date fechaDesignacionEntrante) {
		this.fechaDesignacionEntrante = fechaDesignacionEntrante;
	}
	public String getIdPersonaEntrante() {
		return idPersonaEntrante;
	}
	public void setIdPersonaEntrante(String idPersonaEntrante) {
		this.idPersonaEntrante = idPersonaEntrante;
	}
}
