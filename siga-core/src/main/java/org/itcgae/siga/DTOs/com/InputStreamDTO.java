package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.core.io.InputStreamResource;

public class InputStreamDTO {
	
	private InputStreamResource inputStream;
	private Error error = null;	
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public InputStreamResource getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStreamResource inputStream) {
		this.inputStream = inputStream;
	}	
	

}
