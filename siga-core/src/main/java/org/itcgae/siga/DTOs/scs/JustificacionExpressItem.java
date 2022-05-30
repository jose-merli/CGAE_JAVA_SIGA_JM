package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.itcgae.siga.DTOs.gen.Error;

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
	private String codigoDesignacion;
	private String ejgs;
	private String cliente;
	private String art27;
	private String nig;
	private String idJuzgado;
	private String nombreJuzgado;
	private String categoriaJuzgado;
	private int idInstitucionJuzgado;
	private int anioProcedimiento;
	private String numProcedimiento;
	private Date fechaJustificacion;
	private Date fechaActuacion;
	private Date fechaDesignacion;
	private String resolucionDesignacion;
	private String idInstitucion;
	private String idTurno;
	private String idPersona;
	private String idProcedimiento;
	private String procedimiento;
	private String validarjustificaciones;
	private String letradoActuaciones;
	private Map<String, String> expedientes;
	private List<ActuacionesJustificacionExpressItem> actuaciones;	 
	private String categoriaProcedimiento;
	private Error error = null;

	public String getCategoriaJuzgado() {
		return categoriaJuzgado;
	}

	public void setCategoriaJuzgado(String categoriaJuzgado) {
		this.categoriaJuzgado = categoriaJuzgado;
	}

	public String getLetradoActuaciones() {
		return letradoActuaciones;
	}

	public void setLetradoActuaciones(String letradoActuaciones) {
		this.letradoActuaciones = letradoActuaciones;
	}

	/**
	   * 
	   **/
	  public JustificacionExpressItem error(Error error) {
	    this.error = error;
	    return this;
	  }
	  
	  @JsonProperty("error")
	  public Error getError() {
	    return error;
	  }
	  
	  public void setError(Error error) {
	    this.error = error;
	  }
	  
	
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

	/**
	 * @return the codigoDesignacion
	 */
	@JsonProperty("codigoDesignacion")
	public String getCodigoDesignacion() {
		return codigoDesignacion;
	}

	/**
	 * @param codigoDesignacion the codigoDesignacion to set
	 */
	public void setCodigoDesignacion(String codigoDesignacion) {
		this.codigoDesignacion = codigoDesignacion;
	}

	/**
	 * @return the ejgs
	 */
	@JsonProperty("ejgs")
	public String getEjgs() {
		return ejgs;
	}

	/**
	 * @param ejgs the ejgs to set
	 */
	public void setEjgs(String ejgs) {
		this.ejgs = ejgs;
	}

	/**
	 * @return the cliente
	 */
	@JsonProperty("cliente")
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		if(cliente == null ) {
			this.cliente = " ";
		}else {
			this.cliente = cliente;
		}
	}

	/**
	 * @return the art27
	 */
	@JsonProperty("art27")
	public String getArt27() {
		return art27;
	}

	/**
	 * @param art27 the art27 to set
	 */
	public void setArt27(String art27) {
		this.art27 = art27;
	}

	/**
	 * @return the nig
	 */
	@JsonProperty("nig")
	public String getNig() {
		return nig;
	}

	/**
	 * @param nig the nig to set
	 */
	public void setNig(String nig) {
		this.nig = nig;
	}

	/**
	 * @return the idJuzgado
	 */
	@JsonProperty("idJuzgado")
	public String getIdJuzgado() {
		return idJuzgado;
	}

	/**
	 * @param idJuzgado the idJuzgado to set
	 */
	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	/**
	 * @return the nombreJuzgado
	 */
	@JsonProperty("nombreJuzgado")
	public String getNombreJuzgado() {
		return nombreJuzgado;
	}

	/**
	 * @param nombreJuzgado the nombreJuzgado to set
	 */
	public void setNombreJuzgado(String nombreJuzgado) {
		this.nombreJuzgado = nombreJuzgado;
	}

	/**
	 * @return the idInstitucionJuzgado
	 */
	@JsonProperty("idInstitucionJuzgado")
	public int getIdInstitucionJuzgado() {
		return idInstitucionJuzgado;
	}

	/**
	 * @param idInstitucionJuzgado the idInstitucionJuzgado to set
	 */
	public void setIdInstitucionJuzgado(int idInstitucionJuzgado) {
		this.idInstitucionJuzgado = idInstitucionJuzgado;
	}

	/**
	 * @return the anioProcedimiento
	 */
	@JsonProperty("anioProcedimiento")
	public int getAnioProcedimiento() {
		return anioProcedimiento;
	}

	/**
	 * @param anioProcedimiento the anioProcedimiento to set
	 */
	public void setAnioProcedimiento(int anioProcedimiento) {
		this.anioProcedimiento = anioProcedimiento;
	}

	/**
	 * @return the numProcedimiento
	 */
	@JsonProperty("numProcedimiento")
	public String getNumProcedimiento() {
		return numProcedimiento;
	}

	/**
	 * @param numProcedimiento the numProcedimiento to set
	 */
	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}

	/**
	 * @return the fechaJustificacion
	 */
	@JsonProperty("fechaJustificacion")
	public Date getFechaJustificacion() {
		return fechaJustificacion;
	}

	/**
	 * @param fechaJustificacion the fechaJustificacion to set
	 */
	public void setFechaJustificacion(Date fechaJustificacion) {
		this.fechaJustificacion = fechaJustificacion;
	}

	/**
	 * @return the fechaActuacion
	 */
	@JsonProperty("fechaActuacion")
	public Date getFechaActuacion() {
		return fechaActuacion;
	}

	/**
	 * @param fechaActuacion the fechaActuacion to set
	 */
	public void setFechaActuacion(Date fechaActuacion) {
		this.fechaActuacion = fechaActuacion;
	}

	/**
	 * @return the fechaDesignacion
	 */
	@JsonProperty("fechaDesignacion")
	public Date getFechaDesignacion() {
		return fechaDesignacion;
	}

	/**
	 * @param fechaDesignacion the fechaDesignacion to set
	 */
	public void setFechaDesignacion(Date fechaDesignacion) {
		this.fechaDesignacion = fechaDesignacion;
	}

	/**
	 * @return the resolucionDesignacion
	 */
	@JsonProperty("resolucionDesignacion")
	public String getResolucionDesignacion() {
		return resolucionDesignacion;
	}

	/**
	 * @param resolucionDesignacion the resolucionDesignacion to set
	 */
	public void setResolucionDesignacion(String resolucionDesignacion) {
		this.resolucionDesignacion = resolucionDesignacion;
	}

	/**
	 * @return the idInstitucion
	 */
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return the idTurno
	 */
	@JsonProperty("idTurno")
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	/**
	 * @return the idPersona
	 */
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idProcedimiento
	 */
	@JsonProperty("idProcedimiento")
	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	/**
	 * @param idProcedimiento the idProcedimiento to set
	 */
	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	/**
	 * @return the expedientes
	 */
	@JsonProperty("expedientes")
	public Map<String, String> getExpedientes() {
		return expedientes;
	}

	/**
	 * @param expedientes the expedientes to set
	 */
	public void setExpedientes(Map<String, String> expedientes) {
		this.expedientes = expedientes;
	}

	/**
	 * @return the actuaciones
	 */
	@JsonProperty("actuaciones")
	public List<ActuacionesJustificacionExpressItem> getActuaciones() {
		return actuaciones;
	}

	/**
	 * @param actuaciones the actuaciones to set
	 */
	public void setActuaciones(List<ActuacionesJustificacionExpressItem> actuaciones) {
		this.actuaciones = actuaciones;
	}

	/**
	 * @return the procedimiento
	 */
	@JsonProperty("procedimiento")
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento the procedimiento to set
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getValidarjustificaciones() {
		return validarjustificaciones;
	}

	public void setValidarjustificaciones(String validarjustificaciones) {
		this.validarjustificaciones = validarjustificaciones;
	}
	public String getCategoriaProcedimiento() {
		return categoriaProcedimiento;
	}

	public void setCategoriaProcedimiento(String categoriaProcedimiento) {
		this.categoriaProcedimiento = categoriaProcedimiento;
	}
}