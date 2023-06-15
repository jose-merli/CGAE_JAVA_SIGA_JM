package org.itcgae.siga.DTO.fac;

import java.util.List;
import org.itcgae.siga.DTOs.gen.Error;

public class ListaMovimientosMonederoDTO {
	
	private List<ListaMovimientosMonederoItem> listaMovimientosMonederoItems;
	
	private Error error;

	public List<ListaMovimientosMonederoItem> getListaMovimientosMonederoItem() {
		return listaMovimientosMonederoItems;
	}

	public void setListaMovimientosMonederoItem(List<ListaMovimientosMonederoItem> listaMovimientosMonederoItems) {
		this.listaMovimientosMonederoItems = listaMovimientosMonederoItems;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
