package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class CalendarioProgramadosDTO {
	
	private List<DatosCalendarioProgramadoItem> datos = new ArrayList<DatosCalendarioProgramadoItem>();
	private Error error = null;
	
	public List<DatosCalendarioProgramadoItem> getDatos() {
		return datos;
	}
	public void setDatos(List<DatosCalendarioProgramadoItem> datosCalendarioProgramadoItems) {
		this.datos = datosCalendarioProgramadoItems;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "CalendarioProgramadosDTO [datosCalendarioProgramadoItems=" + datos + ", error="
				+ error + "]";
	}
	
	
	

}
