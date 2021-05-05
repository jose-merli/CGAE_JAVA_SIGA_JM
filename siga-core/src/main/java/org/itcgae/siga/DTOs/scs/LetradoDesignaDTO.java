package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class LetradoDesignaDTO {

	private List<LetradoDesignaItem> listaLetradosDesignaItem = new ArrayList<LetradoDesignaItem>();
	private Error error = null;

	/**
	 * @return the listaLetradosDesignaItem
	 */
	public List<LetradoDesignaItem> getListaLetradosDesignaItem() {
		return listaLetradosDesignaItem;
	}

	/**
	 * @param listaLetradosDesignaItem the listaLetradosDesignaItem to set
	 */
	public void setListaLetradosDesignaItem(List<LetradoDesignaItem> listaLetradosDesignaItem) {
		this.listaLetradosDesignaItem = listaLetradosDesignaItem;
	}

	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "LetradoDesignaDTO [listaLetradosDesignaItem=" + listaLetradosDesignaItem + ", error=" + error + "]";
	}

}
