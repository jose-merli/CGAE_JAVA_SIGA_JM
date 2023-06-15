package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class ListaContrarioJusticiableItem {
	
	private String idInstitucion;
	private String idTurno;
	private String anio;
	private String numero;
	private String nif;
	private String abogado;
	
	private String apellidosnombre;
	private String idPersona;
	private String representante;
	private Date fechaBaja;
	private String idabogadocontrario;
	
	private String idprocurador;
	private String procurador;
	private String idInstitucionProc;
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getAbogado() {
		return abogado;
	}
	public void setAbogado(String abogado) {
		this.abogado = abogado;
	}
	public String getProcurador() {
		return procurador;
	}
	public void setProcurador(String procurador) {
		this.procurador = procurador;
	}
	public String getApellidosnombre() {
		return apellidosnombre;
	}
	public void setApellidosnombre(String apellidosnombre) {
		this.apellidosnombre = apellidosnombre;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getIdabogadocontrario() {
		return idabogadocontrario;
	}
	public void setIdabogadocontrario(String idabogadocontrario) {
		this.idabogadocontrario = idabogadocontrario;
	}
	public String getIdprocurador() {
		return idprocurador;
	}
	public void setIdprocurador(String idprocurador) {
		this.idprocurador = idprocurador;
	}
	public String getIdInstitucionProc() {
		return idInstitucionProc;
	}
	public void setIdInstitucionProc(String idInstitucionProc) {
		this.idInstitucionProc = idInstitucionProc;
	}
	
	
}
