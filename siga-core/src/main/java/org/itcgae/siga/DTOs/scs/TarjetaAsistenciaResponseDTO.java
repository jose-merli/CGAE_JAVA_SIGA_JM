package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaAsistenciaResponseDTO {
	
	private List<TarjetaAsistenciaResponseItem> responseItems = new ArrayList<TarjetaAsistenciaResponseItem>();
	private Error error;
	/**
	 * @return the responseItems
	 */
	@JsonProperty("tarjetaAsistenciaItems")
	public List<TarjetaAsistenciaResponseItem> getResponseItems() {
		return responseItems;
	}
	/**
	 * @param responseItems the responseItems to set
	 */
	public void setResponseItems(List<TarjetaAsistenciaResponseItem> responseItems) {
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
