package org.itcgae.siga.DTOs.gen;

public class FileInfoDTO {
	
	public String filePath;
	public String name;
	public String messageError;
	
	public String getMessageError() {
		return messageError;
	}
	
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
