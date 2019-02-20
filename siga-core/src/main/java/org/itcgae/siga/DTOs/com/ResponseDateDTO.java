package org.itcgae.siga.DTOs.com;

import java.util.Date;

import org.itcgae.siga.DTOs.gen.Error;

public class ResponseDateDTO {
	
	private Date fecha;
	private Error error;	
	
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
