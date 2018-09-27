package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargaMasivaItem {


	private String nombreFichero;
	private String usuario;
	private String registros;
	private String fechaCarga;
	private String tipoCarga;
	

	/**
	 *
	 */
	public CargaMasivaItem nombreFichero(String nombreFichero){
		this.nombreFichero = nombreFichero;
		return this;
	}
	
	@JsonProperty("nombreFichero")
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem usuario(String usuario){
		this.usuario = usuario;
		return this;
	}
	
	@JsonProperty("usuario")
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
	/**
	 *
	 */
	public CargaMasivaItem registros(String registros){
		this.registros = registros;
		return this;
	}
	
	@JsonProperty("registros")
	public String getRegistros() {
		return registros;
	}
	public void setRegistros(String registros) {
		this.registros = registros;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem fechaCarga(String fechaCarga){
		this.fechaCarga = fechaCarga;
		return this;
	}

	@JsonProperty("fechaCarga")
	public String getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem numColegiado(String tipoCarga){
		this.tipoCarga = tipoCarga;
		return this;
	}
	
	@JsonProperty("tipoCarga")
	public String getTipoCarga() {
		return tipoCarga;
	}
	
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nombreFichero, usuario, registros, fechaCarga, tipoCarga);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesItem {\n");
	    
	    sb.append("    nombreFichero: ").append(toIndentedString(nombreFichero)).append("\n");
	    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
	    sb.append("    registros: ").append(toIndentedString(registros)).append("\n");
	    sb.append("    fechaCarga: ").append(toIndentedString(fechaCarga)).append("\n");
	    sb.append("    tipoCarga: ").append(toIndentedString(tipoCarga)).append("\n");
	    sb.append("}");
	    return sb.toString();
	}
	
	/**
	* Convert the given object to string with each line indented by 4 spaces
	* (except the first line).
	*/
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}



	
}
