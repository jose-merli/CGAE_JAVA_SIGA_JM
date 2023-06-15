package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ListDTO {

	private List<String> lista = new ArrayList<String>();
	private Error error = null;

	/**
	 * @return the lista
	 */
	public List<String> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<String> lista) {
		this.lista = lista;
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
		return "ListDTO [lista=" + lista + ", error=" + error + "]";
	}

}
