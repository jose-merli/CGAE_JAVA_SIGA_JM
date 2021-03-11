package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JustificacionExpressItem {

	private String nColegiado;
	private String nombre;
	private String apellidos;
	private String anioEJG;
	private String numEJG;
	private boolean muestraPendiente;
	private boolean restriccionesVisualizacion;
	private Date justificacionDesde;
	private Date justificacionHasta;
	private String estado;
	private String actuacionesValidadas;
	private String sinEJG;
	private String conEJGNoFavorables;
	private String ejgSinResolucion;
	private String resolucionPTECAJG;
	private Date designacionDesde;
	private Date designacionHasta;
	private String anioDesignacion;
	private String numDesignacion;
	
	/**
	 * @return the nColegiado
	 */
	@JsonProperty("nColegiado")
	public String getnColegiado() {
		return nColegiado;
	}
	
	/**
	 * @param nColegiado the nColegiado to set
	 */
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	
	/**
	 * @return the nombre
	 */
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the apellidos
	 */
	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}
	
	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * @return the anioEJG
	 */
	@JsonProperty("anioEJG")
	public String getAnioEJG() {
		return anioEJG;
	}
	
	/**
	 * @param anioEJG the anioEJG to set
	 */
	public void setAnioEJG(String anioEJG) {
		this.anioEJG = anioEJG;
	}
	
	/**
	 * @return the numEJG
	 */
	@JsonProperty("numEJG")
	public String getNumEJG() {
		return numEJG;
	}
	
	/**
	 * @param numEJG the numEJG to set
	 */
	public void setNumEJG(String numEJG) {
		this.numEJG = numEJG;
	}
	
	/**
	 * @return the muestraPendiente
	 */
	@JsonProperty("muestraPendiente")
	public boolean isMuestraPendiente() {
		return muestraPendiente;
	}
	
	/**
	 * @param muestraPendiente the muestraPendiente to set
	 */
	public void setMuestraPendiente(boolean muestraPendiente) {
		this.muestraPendiente = muestraPendiente;
	}
	
	/**
	 * @return the restriccionesVisualizacion
	 */
	@JsonProperty("restriccionesVisualizacion")
	public boolean isRestriccionesVisualizacion() {
		return restriccionesVisualizacion;
	}
	
	/**
	 * @param restriccionesVisualizacion the restriccionesVisualizacion to set
	 */
	public void setRestriccionesVisualizacion(boolean restriccionesVisualizacion) {
		this.restriccionesVisualizacion = restriccionesVisualizacion;
	}
	
	/**
	 * @return the justificacionDesde
	 */
	@JsonProperty("justificacionDesde")
	public Date getJustificacionDesde() {
		return justificacionDesde;
	}
	
	/**
	 * @param justificacionDesde the justificacionDesde to set
	 */
	public void setJustificacionDesde(Date justificacionDesde) {
		this.justificacionDesde = justificacionDesde;
	}
	
	/**
	 * @return the justificacionHasta
	 */
	@JsonProperty("justificacionHasta")
	public Date getJustificacionHasta() {
		return justificacionHasta;
	}
	
	/**
	 * @param justificacionHasta the justificacionHasta to set
	 */
	public void setJustificacionHasta(Date justificacionHasta) {
		this.justificacionHasta = justificacionHasta;
	}
	
	/**
	 * @return the estado
	 */
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the actuacionesValidadas
	 */
	@JsonProperty("actuacionesValidadas")
	public String getActuacionesValidadas() {
		return actuacionesValidadas;
	}
	
	/**
	 * @param actuacionesValidadas the actuacionesValidadas to set
	 */
	public void setActuacionesValidadas(String actuacionesValidadas) {
		this.actuacionesValidadas = actuacionesValidadas;
	}
	
	/**
	 * @return the sinEJG
	 */
	@JsonProperty("sinEJG")
	public String getSinEJG() {
		return sinEJG;
	}
	/**
	 * @param sinEJG the sinEJG to set
	 */
	public void setSinEJG(String sinEJG) {
		this.sinEJG = sinEJG;
	}
	
	/**
	 * @return the conEJGNoFavorables
	 */
	@JsonProperty("conEJGNoFavorables")
	public String getConEJGNoFavorables() {
		return conEJGNoFavorables;
	}
	
	/**
	 * @param conEJGNoFavorables the conEJGNoFavorables to set
	 */
	public void setConEJGNoFavorables(String conEJGNoFavorables) {
		this.conEJGNoFavorables = conEJGNoFavorables;
	}
	
	/**
	 * @return the ejgSinResolucion
	 */
	@JsonProperty("ejgSinResolucion")
	public String getEjgSinResolucion() {
		return ejgSinResolucion;
	}
	
	/**
	 * @param ejgSinResolucion the ejgSinResolucion to set
	 */
	public void setEjgSinResolucion(String ejgSinResolucion) {
		this.ejgSinResolucion = ejgSinResolucion;
	}
	
	/**
	 * @return the resolucionPTECAJG
	 */
	@JsonProperty("resolucionPTECAJG")
	public String getResolucionPTECAJG() {
		return resolucionPTECAJG;
	}
	/**
	 * @param resolucionPTECAJG the resolucionPTECAJG to set
	 */
	public void setResolucionPTECAJG(String resolucionPTECAJG) {
		this.resolucionPTECAJG = resolucionPTECAJG;
	}
	
	/**
	 * @return the designacionDesde
	 */
	@JsonProperty("designacionDesde")
	public Date getDesignacionDesde() {
		return designacionDesde;
	}
	
	/**
	 * @param designacionDesde the designacionDesde to set
	 */
	public void setDesignacionDesde(Date designacionDesde) {
		this.designacionDesde = designacionDesde;
	}
	
	/**
	 * @return the designacionHasta
	 */
	@JsonProperty("designacionHasta")
	public Date getDesignacionHasta() {
		return designacionHasta;
	}
	
	/**
	 * @param designacionHasta the designacionHasta to set
	 */
	public void setDesignacionHasta(Date designacionHasta) {
		this.designacionHasta = designacionHasta;
	}
	
	/**
	 * @return the anioDesignacion
	 */
	@JsonProperty("anioDesignacion")
	public String getAnioDesignacion() {
		return anioDesignacion;
	}
	
	/**
	 * @param anioDesignacion the anioDesignacion to set
	 */
	public void setAnioDesignacion(String anioDesignacion) {
		this.anioDesignacion = anioDesignacion;
	}
	
	/**
	 * @return the numDesignacion
	 */
	@JsonProperty("numDesignacion")
	public String getNumDesignacion() {
		return numDesignacion;
	}
	
	/**
	 * @param numDesignacion the numDesignacion to set
	 */
	public void setNumDesignacion(String numDesignacion) {
		this.numDesignacion = numDesignacion;
	}
}