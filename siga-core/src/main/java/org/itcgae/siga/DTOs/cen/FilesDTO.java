package org.itcgae.siga.DTOs.cen;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilesDTO {

	private String extension;
	private String nameFile;
	private HttpServletResponse response;
	
	@JsonProperty("extension")
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	@JsonProperty("nameFile")
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
	@JsonProperty("response")
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
	
	
}
