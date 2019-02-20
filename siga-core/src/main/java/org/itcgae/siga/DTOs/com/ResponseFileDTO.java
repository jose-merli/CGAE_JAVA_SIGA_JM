package org.itcgae.siga.DTOs.com;

import java.io.File;

import org.itcgae.siga.DTOs.gen.Error;

public class ResponseFileDTO {
	
	private File file;
	private Error error;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}	

}
