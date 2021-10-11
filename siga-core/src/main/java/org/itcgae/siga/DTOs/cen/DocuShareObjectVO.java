package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DocuShareObjectVO implements Comparable<DocuShareObjectVO>  {

	 

	private String id;
	private String title;
	private String description;
	private String tipo;
	private Date fechaModificacion;
	private long sizeKB;
	private String summary;
	private String originalFilename;
	private String idPersona;
	private String parent;
	private String idTipoEjg;
	private String anio;
	private String numero;


	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 */
	public DocuShareObjectVO title(String title) {
		this.title = title;
		return this;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	*
	*/
	public DocuShareObjectVO tipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

     public int compareTo(DocuShareObjectVO o) {
         if (fechaModificacion.getTime() < o.fechaModificacion.getTime()) {
             return 1;
         }
         if (fechaModificacion.getTime() > o.fechaModificacion.getTime()) {
             return -1;
         }
         return 0;
     }
	/**
	 *
	 */
	public DocuShareObjectVO fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	/**
	 *
	 */
	public DocuShareObjectVO sizeKB(Long sizeKB) {
		this.sizeKB = sizeKB;
		return this;
	}
	@JsonProperty("sizeKB")
	public long getSizeKB() {
		return sizeKB;
	}

	public void setSizeKB(long sizeKB) {
		this.sizeKB = sizeKB;
	}

	/**
	 *
	 */
	public DocuShareObjectVO description(String description) {
		this.description = description;
		return this;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 *
	 */
	public DocuShareObjectVO summary(String summary) {
		this.summary = summary;
		return this;
	}


	@JsonProperty("summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 *
	 */
	public DocuShareObjectVO originalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
		return this;
	}

	
	@JsonProperty("originalFilename")
	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	/**
	 *
	 */
	public DocuShareObjectVO idPersona(String idPersona) {
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
	
	/**
	 *
	 */
	public DocuShareObjectVO Parent(String parent) {
		this.parent = parent;
		return this;
	}

	@JsonProperty("parent")
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getIdTipoEjg() {
		return idTipoEjg;
	}

	public void setIdTipoEjg(String idTipoEjg) {
		this.idTipoEjg = idTipoEjg;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
