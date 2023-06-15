package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ActuacionesJustificacionExpressDTO {

	private List<ActuacionesJustificacionExpressItem> actuacionesJustificacionExpressItem = new ArrayList<ActuacionesJustificacionExpressItem>();
	private Error error = null;
	
	/**
	 * 
	 * @param ActuacionesJustificacionExpressItem
	 * @return
	 */
	public ActuacionesJustificacionExpressDTO ActuacionesJustificacionExpressItem(List<ActuacionesJustificacionExpressItem> actuacionesJustificacionExpressItem){
		this.actuacionesJustificacionExpressItem = actuacionesJustificacionExpressItem;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ActuacionesJustificacionExpressItem> getActuacionesJustificacionExpressItem() {
		return actuacionesJustificacionExpressItem;
	}
	
	/**
	 * 
	 * @param ActuacionesJustificacionExpressItem
	 */
	public void setJustificacionExpressItem(List<ActuacionesJustificacionExpressItem> actuacionesJustificacionExpressItem) {
		this.actuacionesJustificacionExpressItem = actuacionesJustificacionExpressItem;
	}
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public ActuacionesJustificacionExpressDTO error(Error error){
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