package org.itcgae.siga.DTOs.com;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class NuevaComunicacionItem {
	private String juzgado;
	private String asunto;
	private Date fechaEfecto;
	private String mensaje;
	private String numProcedimiento;
	private String nig;
	private String idModeloComunicacion;
	private String idPlantillaEnvios;
	private String idTipoMensaje;
	private List<MultipartFile> docs;
	
	public String getJuzgado() {
		return juzgado;
	}
	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Date getFechaEfecto() {
		return fechaEfecto;
	}
	public void setFechaEfecto(Date fechaEfecto) {
		this.fechaEfecto = fechaEfecto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNumProcedimiento() {
		return numProcedimiento;
	}
	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}
	public String getNig() {
		return nig;
	}
	public void setNig(String nig) {
		this.nig = nig;
	}
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public List<MultipartFile> getDocs() {
		return docs;
	}
	public void setDocs(List<MultipartFile> docs) {
		this.docs = docs;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public String getIdTipoMensaje() {
		return idTipoMensaje;
	}
	public void setIdTipoMensaje(String idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}
}
