package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.itcgae.siga.DTOs.com.ConsultasDTO;

public class PlantillaDocumentoDto {
	private String idioma;
	private Date fechaAsociacion;
	private String nombre;
	private String sufijo;
	private String formatoSalida;
	private List<ConsultasDTO> consultas = new ArrayList<ConsultasDTO>();
	
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSufijo() {
		return sufijo;
	}
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}
	public String getFormatoSalida() {
		return formatoSalida;
	}
	public void setFormatoSalida(String formatoSalida) {
		this.formatoSalida = formatoSalida;
	}
	public List<ConsultasDTO> getConsultas() {
		return consultas;
	}
	public void setConsultas(List<ConsultasDTO> consultas) {
		this.consultas = consultas;
	}
}
