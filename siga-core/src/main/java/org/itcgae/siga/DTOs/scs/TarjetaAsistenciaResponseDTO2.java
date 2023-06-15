package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaAsistenciaResponseDTO2 {
	
	private List<TarjetaAsistenciaResponse2Item> responseItems = new ArrayList<TarjetaAsistenciaResponse2Item>();
	private Error error;
	/**
	 * @return the responseItems
	 */
	@JsonProperty("tarjetaAsistenciaItems2")
	public List<TarjetaAsistenciaResponse2Item> getResponseItems() {
		return responseItems;
	}
	/**
	 * @param responseItems the responseItems to set
	 */
	public void setResponseItems(List<TarjetaAsistenciaResponse2Item> responseItems) {
		this.responseItems = responseItems;
	}
	/**
	 * @return the error
	 */
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}
	
	

}
