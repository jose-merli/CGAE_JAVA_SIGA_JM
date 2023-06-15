package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class JustificacionExpressDTO {

	private List<JustificacionExpressItem> justificacionExpressItem = new ArrayList<JustificacionExpressItem>();
	private Error error = null;
	
	/**
	 * 
	 * @param JustificacionExpressItem
	 * @return
	 */
	public JustificacionExpressDTO JustificacionExpressItem(List<JustificacionExpressItem> JustificacionExpressItem){
		this.justificacionExpressItem = JustificacionExpressItem;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<JustificacionExpressItem> getJustificacionExpressItem() {
		return justificacionExpressItem;
	}
	
	/**
	 * 
	 * @param JustificacionExpressItem
	 */
	public void setJustificacionExpressItem(List<JustificacionExpressItem> JustificacionExpressItem) {
		this.justificacionExpressItem = JustificacionExpressItem;
	}
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public JustificacionExpressDTO error(Error error){
		this.error = error;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Error getError() {
		return error;
	}
	
	/**
	 * 
	 * @param error
	 */
	public void setError(Error error) {
		this.error = error;
	}	
}