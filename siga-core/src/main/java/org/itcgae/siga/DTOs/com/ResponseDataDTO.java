package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class ResponseDataDTO {
	
	private String data;
	private Error error;	
	
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}	

}
