package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocuShareObjectVO implements Comparable<DocuShareObjectVO>{

	public static enum DocuShareTipo {
		COLLECTION
		, DOCUMENT
	}
	
	private String id;
	private String title;	
	private String description;
	private DocuShareTipo tipo;
	private Date fechaModificacion;
	private long sizeKB;
	private String summary;
	private String originalFilename;
	
	public DocuShareObjectVO(DocuShareTipo tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DocuShareTipo getTipo() {
		return tipo;
	}

	public void setTipo(DocuShareTipo tipo) {
		this.tipo = tipo;
	}

	public int compareTo(DocuShareObjectVO other) {
		if (other != null && other.getTitle() != null && title != null) {
			return title.toUpperCase().compareTo(other.getTitle().toUpperCase());
		}
		return 0;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public long getSizeKB() {
		return sizeKB;
	}

	public void setSizeKB(long sizeKB) {
		this.sizeKB = sizeKB;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}



	
}
