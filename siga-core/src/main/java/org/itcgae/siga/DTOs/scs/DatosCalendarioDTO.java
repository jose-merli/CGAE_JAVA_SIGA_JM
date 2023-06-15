package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosCalendarioDTO {


	private List<DatosCalendarioItem> datosCalendatioItems = new ArrayList<DatosCalendarioItem>();
	private Error error = null;
	
	
	public List<DatosCalendarioItem> getDatosCalendatioItems() {
		return datosCalendatioItems;
	}
	public void setDatosCalendatioItems(List<DatosCalendarioItem> datosCalendatioItems) {
		this.datosCalendatioItems = datosCalendatioItems;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
	
}
