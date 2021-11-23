package org.itcgae.siga.DTOs.adm;

import java.io.File;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FileDataDTO {
	
	private MultipartFile file;
	private String[] datos;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String[] getDatos() {
		return datos;
	}
	public void setDatos(String[] datos) {
		this.datos = datos;
	}
	
	

	
	
}
