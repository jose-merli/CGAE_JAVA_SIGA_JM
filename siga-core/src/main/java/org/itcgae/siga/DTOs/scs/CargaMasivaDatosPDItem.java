package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class CargaMasivaDatosPDItem {

	private String codigoDesignaAbogado;
	private String numEJG;
	private Long idPersona;
	private Long idProcurador;
	private String numColProcurador;
	private String numDesignaProcurador;
	private String nombreProcurador;
	private Date fechaDesignaProcurador;
	private String Observaciones;
	private Short idInstitucion;
	private String errores;
	
	private boolean existeDesigna;
	private int desigaAbogadoIdTurno;
	private Short desigaAbogadoAnio;
	private Long desigaAbogadoNumero;
	
	private int ejgIdTipo;
	private int ejgAnio;
	private Long ejgNumero;
		
	public Long getIdProcurador() {
		return idProcurador;
	}

	public void setIdProcurador(Long idProcurador) {
		this.idProcurador = idProcurador;
	}

	public boolean isExisteDesigna() {
		return existeDesigna;
	}

	public void setExisteDesigna(boolean existeDesigna) {
		this.existeDesigna = existeDesigna;
	}

	public int getEjgIdTipo() {
		return ejgIdTipo;
	}

	public void setEjgIdTipo(int ejgIdTipo) {
		this.ejgIdTipo = ejgIdTipo;
	}

	public int getEjgAnio() {
		return ejgAnio;
	}

	public void setEjgAnio(int ejgAnio) {
		this.ejgAnio = ejgAnio;
	}

	public Long getEjgNumero() {
		return ejgNumero;
	}

	public void setEjgNumero(Long ejgNumero) {
		this.ejgNumero = ejgNumero;
	}

	public int getDesigaAbogadoIdTurno() {
		return desigaAbogadoIdTurno;
	}

	public void setDesigaAbogadoIdTurno(int desigaAbogadoIdTurno) {
		this.desigaAbogadoIdTurno = desigaAbogadoIdTurno;
	}

	public Short getDesigaAbogadoAnio() {
		return desigaAbogadoAnio;
	}

	public void setDesigaAbogadoAnio(Short desigaAbogadoAnio) {
		this.desigaAbogadoAnio = desigaAbogadoAnio;
	}

	public Long getDesigaAbogadoNumero() {
		return desigaAbogadoNumero;
	}

	public void setDesigaAbogadoNumero(Long desigaAbogadoNumero) {
		this.desigaAbogadoNumero = desigaAbogadoNumero;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombreProcurador() {
		return nombreProcurador;
	}

	public void setNombreProcurador(String nombreProcurador) {
		this.nombreProcurador = nombreProcurador;
	}

	public String getCodigoDesignaAbogado() {
		return codigoDesignaAbogado;
	}
	
	public void setCodigoDesignaAbogado(String codigoDesignaAbogado) {
		this.codigoDesignaAbogado = codigoDesignaAbogado;
	}
	
	public String getNumEJG() {
		return numEJG;
	}
	
	public void setNumEJG(String numEJG) {
		this.numEJG = numEJG;
	}
	
	public String getNumColProcurador() {
		return numColProcurador;
	}
	
	public void setNumColProcurador(String numColProcurador) {
		this.numColProcurador = numColProcurador;
	}
	
	public String getNumDesignaProcurador() {
		return numDesignaProcurador;
	}
	
	public void setNumDesignaProcurador(String numDesignaProcurador) {
		this.numDesignaProcurador = numDesignaProcurador;
	}
	
	public Date getFechaDesignaProcurador() {
		return fechaDesignaProcurador;
	}
	
	public void setFechaDesignaProcurador(Date fechaDesignaProcurador) {
		this.fechaDesignaProcurador = fechaDesignaProcurador;
	}
	
	public String getObservaciones() {
		return Observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}
	
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	public String getErrores() {
		return errores;
	}
	
	public void setErrores(String errores) {
		this.errores = errores;
	}
	
}
