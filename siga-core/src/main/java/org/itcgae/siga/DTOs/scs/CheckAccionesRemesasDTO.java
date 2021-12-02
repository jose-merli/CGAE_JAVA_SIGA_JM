package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckAccionesRemesasDTO {

	private List<CheckAccionesRemesas> checkAccionesRemesas = new ArrayList<CheckAccionesRemesas>();
	private Error error = null;
	
	@JsonProperty("checkAccionesRemesas")
	public List<CheckAccionesRemesas> getCheckAccionesRemesas() {
		return checkAccionesRemesas;
	}

	public void setCheckAccionesRemesas(List<CheckAccionesRemesas> checkAccionesRemesas) {
		this.checkAccionesRemesas = checkAccionesRemesas;
	}
	
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
}
