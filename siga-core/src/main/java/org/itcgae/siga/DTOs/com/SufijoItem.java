package org.itcgae.siga.DTOs.com;

public class SufijoItem {
	private String idSufijo;
	private String orden;
	private String nombreSufijo;
	
	public String getIdSufijo() {
		return idSufijo;
	}
	public void setIdSufijo(String idSufijo) {
		this.idSufijo = idSufijo;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getNombreSufijo() {
		return nombreSufijo;
	}
	public void setNombreSufijo(String nombreSufijo) {
		this.nombreSufijo = nombreSufijo;
	}
	public SufijoItem(String idSufijo, String orden, String nombreSufijo) {
		super();
		this.idSufijo = idSufijo;
		this.orden = orden;
		this.nombreSufijo = nombreSufijo;
	}
	public SufijoItem() {
		super();
	}
		
	
}
