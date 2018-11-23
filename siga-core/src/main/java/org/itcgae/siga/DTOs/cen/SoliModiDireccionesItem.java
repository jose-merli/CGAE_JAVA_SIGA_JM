package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SoliModiDireccionesItem {
	private String idSolicitud;
	private String idDireccion;
	private String idPersona;
	private String motivo;
	private String nombre;
	private String fechaModificacion;
	private String estado;
	private String fechaAlta;
	private String codigoPostal;
	private String telefono;
	private String domicilio;
	private String movil;
	private String fax;
	private String correoElectronico;
	private String paginaWeb;
	private String pais;
	private String provincia;
	private String poblacion;
	private String poblacionExtranjera;
	
	public SoliModiDireccionesItem idSolicitud(String idSolicitud){
		this.idSolicitud = idSolicitud;
		return this;
	}
	
	@JsonProperty("idSolicitud")
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	public SoliModiDireccionesItem idDireccion(String idDireccion){
		this.idDireccion = idDireccion;
		return this;
	}
	
	@JsonProperty("idDireccion")
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	
	public SoliModiDireccionesItem idPersona(String idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	public SoliModiDireccionesItem motivo(String motivo){
		this.motivo = motivo;
		return this;
	}
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public SoliModiDireccionesItem nombre(String nombre){
		this.nombre = nombre;
		return this;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public SoliModiDireccionesItem fechaModificacion(String fechaModificacion){
		this.fechaModificacion = fechaModificacion;
		return this;
	}
	
	@JsonProperty("fechaModificacion")
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public SoliModiDireccionesItem estado(String estado){
		this.estado = estado;
		return this;
	}
	
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public SoliModiDireccionesItem fechaAlta(String fechaAlta){
		this.fechaAlta = fechaAlta;
		return this;
	}
	
	@JsonProperty("fechaAlta")
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public SoliModiDireccionesItem codigoPostal(String codigoPostal){
		this.codigoPostal = codigoPostal;
		return this;
	}
	
	@JsonProperty("codigoPostal")
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	public SoliModiDireccionesItem telefono(String telefono){
		this.telefono = telefono;
		return this;
	}
	
	@JsonProperty("telefono")
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public SoliModiDireccionesItem domicilio(String domicilio){
		this.domicilio = domicilio;
		return this;
	}
	
	@JsonProperty("domicilio")
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public SoliModiDireccionesItem movil(String movil){
		this.movil = movil;
		return this;
	}
	
	@JsonProperty("movil")
	public String getMovil() {
		return movil;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	public SoliModiDireccionesItem fax(String fax){
		this.fax = fax;
		return this;
	}
	
	@JsonProperty("fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public SoliModiDireccionesItem correoElectronico(String correoElectronico){
		this.correoElectronico = correoElectronico;
		return this;
	}
	
	@JsonProperty("correoElectronico")
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	public SoliModiDireccionesItem paginaWeb(String paginaWeb){
		this.paginaWeb = paginaWeb;
		return this;
	}
	
	@JsonProperty("paginaWeb")
	public String getPaginaWeb() {
		return paginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	
	public SoliModiDireccionesItem pais(String pais){
		this.pais = pais;
		return this;
	}
	
	@JsonProperty("pais")
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public SoliModiDireccionesItem provincia(String provincia){
		this.provincia = provincia;
		return this;
	}
	
	@JsonProperty("provincia")
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public SoliModiDireccionesItem poblacion(String poblacion){
		this.poblacion = poblacion;
		return this;
	}
	
	@JsonProperty("poblacion")
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	
	public SoliModiDireccionesItem poblacionExtranjera(String poblacionExtranjera){
		this.poblacionExtranjera = poblacionExtranjera;
		return this;
	}
	
	@JsonProperty("poblacionExtranjera")
	public String getPoblacionExtranjera() {
		return poblacionExtranjera;
	}
	public void setPoblacionExtranjera(String poblacionExtranjera) {
		this.poblacionExtranjera = poblacionExtranjera;
	}
	
	@Override
	public String toString() {
		return "SoliModiDireccionesItem [idSolicitud=" + idSolicitud + ", idDireccion=" + idDireccion + ", idPersona=" + idPersona + ", motivo=" + motivo + ", nombre=" + nombre
				+ ", fechaModificacion=" + fechaModificacion + ", estado=" + estado + ", fechaAlta=" + fechaAlta
				+ ", codigoPostal=" + codigoPostal + ", telefono=" + telefono
				+ ", domicilio=" + domicilio + ", movil=" + movil + ", fax=" + fax + ", correoElectronico="
				+ correoElectronico + ", paginaWeb=" + paginaWeb + ", pais=" + pais + ", provincia=" + provincia
				+ ", poblacion=" + poblacion + ", poblacionExtranjera=" + poblacionExtranjera + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSolicitud, idDireccion, idPersona, motivo, nombre, fechaModificacion, estado, fechaAlta, codigoPostal, telefono, domicilio,
			movil, fax, correoElectronico, paginaWeb, pais, provincia, poblacion, poblacionExtranjera);
	}
	
}
