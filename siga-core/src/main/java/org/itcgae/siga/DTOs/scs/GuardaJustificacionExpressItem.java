package org.itcgae.siga.DTOs.scs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuardaJustificacionExpressItem {

	private List<JustificacionExpressItem> listaItem;
	private JustificacionExpressItem filtro;
	
	/**
	 * @return the listaItem
	 */
	@JsonProperty("listaItem")
	public List<JustificacionExpressItem> getListaItem() {
		return listaItem;
	}
	/**
	 * @param listaItem the listaItem to set
	 */
	public void setListaItem(List<JustificacionExpressItem> listaItem) {
		this.listaItem = listaItem;
	}
	/**
	 * @return the filtro
	 */
	@JsonProperty("filtro")
	public JustificacionExpressItem getFiltro() {
		return filtro;
	}
	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(JustificacionExpressItem filtro) {
		this.filtro = filtro;
	}
}