package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ConsultasDTO {

	private List<ConsultaItem> consultaItem = new ArrayList<ConsultaItem>();
	private Error error = null;
	
	
	public List<ConsultaItem> getConsultaItem() {
		return consultaItem;
	}
	public void setConsultaItem(List<ConsultaItem> consultaItem) {
		this.consultaItem = consultaItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
