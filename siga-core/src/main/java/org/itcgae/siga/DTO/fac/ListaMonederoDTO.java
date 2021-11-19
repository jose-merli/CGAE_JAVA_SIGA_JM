package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;


@Data
public class ListaMonederoDTO {
    private List<MonederoDTO> monederoItems = new ArrayList<>();
    private Error error = null;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public List<MonederoDTO> getMonederoItems() {
		return monederoItems;
	}
	public void setMonederoItems(List<MonederoDTO> monederoItems) {
		this.monederoItems = monederoItems;
	}
}
