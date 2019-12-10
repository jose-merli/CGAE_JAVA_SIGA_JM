package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;

import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaItem;

public class TarjetaPesosDTO {
	private String idturno;
	private String idOrdenacionColas;
	private ArrayList<ComboColaOrdenadaItem> pesosSeleccionados;
	private ArrayList<Object> pesosExistentes;
	
	
	public String getIdOrdenacionColas() {
		return idOrdenacionColas;
	}
	public void setIdOrdenacionColas(String idOrdenacionColas) {
		this.idOrdenacionColas = idOrdenacionColas;
	}
	public ArrayList<ComboColaOrdenadaItem> getPesosSeleccionados() {
		return pesosSeleccionados;
	}
	public void setPesosSeleccionados(ArrayList<ComboColaOrdenadaItem> pesosSeleccionados) {
		this.pesosSeleccionados = pesosSeleccionados;
	}
	public ArrayList<Object> getPesosExistentes() {
		return pesosExistentes;
	}
	public void setPesosExistentes(ArrayList<Object> pesosExistentes) {
		this.pesosExistentes = pesosExistentes;
	}
	public String getIdturno() {
		return idturno;
	}
	public void setIdturno(String idturno) {
		this.idturno = idturno;
	}
	

}
