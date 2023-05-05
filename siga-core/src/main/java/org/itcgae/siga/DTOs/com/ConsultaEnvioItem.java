package org.itcgae.siga.DTOs.com;

public class ConsultaEnvioItem {
	
	private Long idConsulta;
	private Long idEnvio;
	private Short idInstitucion;
	private Long idObjetivo;
	private Short usuModificacion;
	private String consulta;
	private Long idPlantillaDoc;

	private Long idModeloComunicacion;
	private Long idInforme;
	
	private String sufijo;
	private String pathFichero;
	private String nombreFichero;
	private Short idInstitucionConsulta;
	
	private DestinatarioItem destinatario;
	
	public String getPathFichero() {
		return pathFichero;
	}
	public void setPathFichero(String pathFichero) {
		this.pathFichero = pathFichero;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Long getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(Long idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public Long getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(Long idInforme) {
		this.idInforme = idInforme;
	}
	public Long getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}
	public Long getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Long getIdObjetivo() {
		return idObjetivo;
	}
	public void setIdObjetivo(Long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}
	public Short getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(Short usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	public Long getIdPlantillaDoc() {
		return idPlantillaDoc;
	}
	public void setIdPlantillaDoc(Long idPlantillaDoc) {
		this.idPlantillaDoc = idPlantillaDoc;
	}
	public String getSufijo() {
		return sufijo;
	}
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}
	public Short getIdInstitucionConsulta() {
		return idInstitucionConsulta;
	}
	public void setIdInstitucionConsulta(Short idInstitucionConsulta) {
		this.idInstitucionConsulta = idInstitucionConsulta;
	}
	public DestinatarioItem getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(DestinatarioItem destinatario) {
		this.destinatario = destinatario;
	}
}
