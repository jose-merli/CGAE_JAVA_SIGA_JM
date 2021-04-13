package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class ActuacionDesignaDTO {

	private List<ActuacionDesignaItem> actuacionesDesignaItems = new ArrayList<ActuacionDesignaItem>();
	private Error error = null;

	/**
	 * @return the actuacionesDesignaItems
	 */
	public List<ActuacionDesignaItem> getActuacionesDesignaItems() {
		return actuacionesDesignaItems;
	}

	/**
	 * @param actuacionesDesignaItems the actuacionesDesignaItems to set
	 */
	public void setActuacionesDesignaItems(List<ActuacionDesignaItem> actuacionesDesignaItems) {
		this.actuacionesDesignaItems = actuacionesDesignaItems;
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
		return "ActuacionDesignaDTO [actuacionesDesignaItems=" + actuacionesDesignaItems + ", error=" + error + "]";
	}

}
