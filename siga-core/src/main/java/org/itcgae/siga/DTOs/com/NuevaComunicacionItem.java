package org.itcgae.siga.DTOs.com;

import java.io.File;
import java.util.Date;

public class NuevaComunicacionItem {
	private String juzgado;
	private String asunto;
	private Date fechaEfecto;
	private String mensaje;
	private String numProcedimiento;
	private String nig;
	private String idClaseComunicacion;
	private File[] docs;
	
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
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public File[] getDocs() {
		return docs;
	}
	public void setDocs(File[] docs) {
		this.docs = docs;
	}
}
