package org.itcgae.siga.DTOs.com;

public class DatosDocumentoItem {
	
	private String fileName;
	private byte [] datos;
	private String pathDocumento;
	
	
	public String getPathDocumento() {
		return pathDocumento;
	}
	public void setPathDocumento(String pathDocumento) {
		this.pathDocumento = pathDocumento;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getDatos() {
		return datos;
	}
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}	

}
