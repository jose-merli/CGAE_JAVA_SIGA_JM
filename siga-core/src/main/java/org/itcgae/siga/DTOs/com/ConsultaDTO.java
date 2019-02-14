package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class ConsultaDTO {

	private ConsultaItem consultaItem = null;
	private Error error = null;	

	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public ConsultaItem getConsultaItem() {
		return consultaItem;
	}
	public void setConsultaItem(ConsultaItem consultaItem) {
		this.consultaItem = consultaItem;
	}
}
