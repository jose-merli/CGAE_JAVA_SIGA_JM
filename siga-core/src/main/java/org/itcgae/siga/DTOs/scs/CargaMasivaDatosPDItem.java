package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class CargaMasivaDatosPDItem {

	private String codigoDesignaAbogado;
	private String numEJG;
	private int numColProcurador;
	private int numDesignaProcurador;
	private Date fechaDesignaProcurador;
	private String Observaciones;
	private Short idInstitucion;
	private String errores;
	
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
	
	public int getNumColProcurador() {
		return numColProcurador;
	}
	
	public void setNumColProcurador(int numColProcurador) {
		this.numColProcurador = numColProcurador;
	}
	
	public int getNumDesignaProcurador() {
		return numDesignaProcurador;
	}
	
	public void setNumDesignaProcurador(int numDesignaProcurador) {
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
