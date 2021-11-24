package org.itcgae.siga.DTOs.scs;

import java.util.Arrays;
import java.util.Date;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dani
 *
 */
public class DesignaItem {
	private int ano;
	private int anoProcedimiento;
	private String art27;
	private String codigo;
	private String defensaJuridica;
	private String delitos;
	private String[] estados;
	private String estado;
	private int factConvenio;
	private Date fechaAlta;
	private Date fechaAnulacion;
	private Date fechaEntradaInicio;
	private Date fechaEntradaFin;
	private Date fechaEstado;
	private Date fechaFin;
	private Date fechaJuicio;
	private Date fechaModificacion;
	private Date fechaOficioJuzgado;
	private Date fechaRecepcionColegio;
	private int idInstitucion;
	private int idInstitucion_juzg;
	private int idInstitucion_procur;
	private String[] idJuzgados;
	private int idJuzgado;
	private String nombreJuzgado;
	private String[] idJuzgadoActu;
	private String nombreJuzgadoActu;
	private int idPretension;
	private String[] idProcedimientos;
	private String idProcedimiento;
	private int idProcurador;
	private String[] idTipoDesignaColegios;
	private int idTipoDesignaColegio;
	private String[] idTurnos;
	private int idTurno;
	private String nig;
	private int numero;
	private String numProcedimiento;
	private String observaciones;
	private String procurador;
	private String resumenAsunto;
	private String sufijo;
	private int usuModificacion;
	private String nombreTurno;
	private String numColegiado;
	private String apellidosNombre;
	private String[] interesados;
	private String[] validadas;
	private String[] calidad;
	private String asunto;
	private String acreditacion;
	private String[] idAcreditacion;
	private String modulo;
	private String[] idModulos;
	private String idModulo;
	private Date fechaJustificacionDesde;
	private Date fechaJustificacionHasta;
	private String origen;
	private String idOrigen;
	private String nif;
	private String nombreInteresado;
	private String apellido1Interesado;
	private String apellido2Interesado;
	private String apellidosInteresado;
	private String idActuacionesV;
	private String idArt27;
	private String[] idCalidad;
	private String[] idProcedimientoActuaciones;
	private int idRol;
	private String[] rol;
	private String documentacionActuacion;
	private String[] idModuloActuaciones;
	private String descripcionTipoDesigna;
	private String expedientes;
	private String cliente;
	private String nombreProcedimiento;
	private String nombreColegiado;
	private String apellido1Colegiado;
	private String apellido2Colegiado;
	private String idPartidaPresupuestaria;
	private String nombrePartida;
	private String validada;
	private int existeDesignaJuzgadoProcedimiento;
	private String idPersona;  
	private Error error = null;
	private String institucioncolegiado;

	 public DesignaItem error(Error error) {
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
	
	public String getApellido1Colegiado() {
		return apellido1Colegiado;
	}
	public void setApellido1Colegiado(String apellido1Colegiado) {
		this.apellido1Colegiado = apellido1Colegiado;
	}
	public String getApellido2Colegiado() {
		return apellido2Colegiado;
	}
	public void setApellido2Colegiado(String apellido2Colegiado) {
		this.apellido2Colegiado = apellido2Colegiado;
	}
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	public String getNombreProcedimiento() {
		return nombreProcedimiento;
	}
	public void setNombreProcedimiento(String nombreProcedimiento) {
		this.nombreProcedimiento = nombreProcedimiento;
	}
	public int getAno() {
		return ano;
	}
	/**
	 * @param ano the ano to set
	 */
	public void setAno(int ano) {
		this.ano = ano;
	}
	/**
	 * @return the anoProcedimiento
	 */
	public int getAnoProcedimiento() {
		return anoProcedimiento;
	}
	/**
	 * @param anoProcedimiento the anoProcedimiento to set
	 */
	public void setAnoProcedimiento(int anoProcedimiento) {
		this.anoProcedimiento = anoProcedimiento;
	}
	/**
	 * @return the art27
	 */
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the defensaJuridica
	 */
	public String getDefensaJuridica() {
		return defensaJuridica;
	}
	/**
	 * @param defensaJuridica the defensaJuridica to set
	 */
	public void setDefensaJuridica(String defensaJuridica) {
		this.defensaJuridica = defensaJuridica;
	}
	/**
	 * @return the delitos
	 */
	public String getDelitos() {
		return delitos;
	}
	/**
	 * @param delitos the delitos to set
	 */
	public void setDelitos(String delitos) {
		this.delitos = delitos;
	}
	/**
	 * @return the estados
	 */
	public String[] getEstados() {
		return estados;
	}
	/**
	 * @param estados the estados to set
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}
	/**
	 * @return the factConvenio
	 */
	public int getFactConvenio() {
		return factConvenio;
	}
	/**
	 * @param factConvenio the factConvenio to set
	 */
	public void setFactConvenio(int factConvenio) {
		this.factConvenio = factConvenio;
	}
	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return the fechaAnulacion
	 */
	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}
	/**
	 * @param fechaAnulacion the fechaAnulacion to set
	 */
	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	/**
	 * @return the fechaEntradaInicio
	 */
	public Date getFechaEntradaInicio() {
		return fechaEntradaInicio;
	}
	/**
	 * @param fechaEntradaInicio the fechaEntradaInicio to set
	 */
	public void setFechaEntradaInicio(Date fechaEntradaInicio) {
		this.fechaEntradaInicio = fechaEntradaInicio;
	}
	/**
	 * @return the fechaEntradaFin
	 */
	public Date getFechaEntradaFin() {
		return fechaEntradaFin;
	}
	/**
	 * @param fechaEntradaFin the fechaEntradaFin to set
	 */
	public void setFechaEntradaFin(Date fechaEntradaFin) {
		this.fechaEntradaFin = fechaEntradaFin;
	}
	/**
	 * @return the fechaEstado
	 */
	public Date getFechaEstado() {
		return fechaEstado;
	}
	/**
	 * @param fechaEstado the fechaEstado to set
	 */
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the fechaJuicio
	 */
	public Date getFechaJuicio() {
		return fechaJuicio;
	}
	/**
	 * @param fechaJuicio the fechaJuicio to set
	 */
	public void setFechaJuicio(Date fechaJuicio) {
		this.fechaJuicio = fechaJuicio;
	}
	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	/**
	 * @return the fechaOficioJuzgado
	 */
	public Date getFechaOficioJuzgado() {
		return fechaOficioJuzgado;
	}
	/**
	 * @param fechaOficioJuzgado the fechaOficioJuzgado to set
	 */
	public void setFechaOficioJuzgado(Date fechaOficioJuzgado) {
		this.fechaOficioJuzgado = fechaOficioJuzgado;
	}
	/**
	 * @return the fechaRecepcionColegio
	 */
	public Date getFechaRecepcionColegio() {
		return fechaRecepcionColegio;
	}
	/**
	 * @param fechaRecepcionColegio the fechaRecepcionColegio to set
	 */
	public void setFechaRecepcionColegio(Date fechaRecepcionColegio) {
		this.fechaRecepcionColegio = fechaRecepcionColegio;
	}
	/**
	 * @return the idInstitucion
	 */
	public int getIdInstitucion() {
		return idInstitucion;
	}
	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	/**
	 * @return the idInstitucion_juzg
	 */
	public int getIdInstitucion_juzg() {
		return idInstitucion_juzg;
	}
	/**
	 * @param idInstitucion_juzg the idInstitucion_juzg to set
	 */
	public void setIdInstitucion_juzg(int idInstitucion_juzg) {
		this.idInstitucion_juzg = idInstitucion_juzg;
	}
	/**
	 * @return the idInstitucion_procur
	 */
	public int getIdInstitucion_procur() {
		return idInstitucion_procur;
	}
	/**
	 * @param idInstitucion_procur the idInstitucion_procur to set
	 */
	public void setIdInstitucion_procur(int idInstitucion_procur) {
		this.idInstitucion_procur = idInstitucion_procur;
	}

	public String[] getIdJuzgados() {
		return idJuzgados;
	}
	public void setIdJuzgados(String[] idJuzgados) {
		this.idJuzgados = idJuzgados;
	}
	public int getIdJuzgado() {
		return idJuzgado;
	}
	public void setIdJuzgado(int idJuzgado) {
		this.idJuzgado = idJuzgado;
	}
	/**
	 * @return the nombreJuzgado
	 */
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
	 * @return the idJuzgadoActu
	 */
	public String[] getIdJuzgadoActu() {
		return idJuzgadoActu;
	}
	/**
	 * @param idJuzgadoActu the idJuzgadoActu to set
	 */
	public void setIdJuzgadoActu(String[] idJuzgadoActu) {
		this.idJuzgadoActu = idJuzgadoActu;
	}
	/**
	 * @return the nombreJuzgadoActu
	 */
	public String getNombreJuzgadoActu() {
		return nombreJuzgadoActu;
	}
	/**
	 * @param nombreJuzgadoActu the nombreJuzgadoActu to set
	 */
	public void setNombreJuzgadoActu(String nombreJuzgadoActu) {
		this.nombreJuzgadoActu = nombreJuzgadoActu;
	}
	/**
	 * @return the idPretension
	 */
	public int getIdPretension() {
		return idPretension;
	}
	/**
	 * @param idPretension the idPretension to set
	 */
	public void setIdPretension(int idPretension) {
		this.idPretension = idPretension;
	}

	/**
	 * @return the idProcurador
	 */
	public int getIdProcurador() {
		return idProcurador;
	}
	public String[] getIdProcedimientos() {
		return idProcedimientos;
	}
	public void setIdProcedimientos(String[] idProcedimientos) {
		this.idProcedimientos = idProcedimientos;
	}
	public String getIdProcedimiento() {
		return idProcedimiento;
	}
	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
	/**
	 * @param idProcurador the idProcurador to set
	 */
	public void setIdProcurador(int idProcurador) {
		this.idProcurador = idProcurador;
	}
	/**
	 * @return the idTipoDesignaColegios
	 */
	public String[] getIdTipoDesignaColegios() {
		return idTipoDesignaColegios;
	}
	/**
	 * @param idTipoDesignaColegios the idTipoDesignaColegios to set
	 */
	public void setIdTipoDesignaColegios(String[] idTipoDesignaColegios) {
		this.idTipoDesignaColegios = idTipoDesignaColegios;
	}
	/**
	 * @return the idTipoDesignaColegio
	 */
	public int getIdTipoDesignaColegio() {
		return idTipoDesignaColegio;
	}
	/**
	 * @param idTipoDesignaColegio the idTipoDesignaColegio to set
	 */
	public void setIdTipoDesignaColegio(int idTipoDesignaColegio) {
		this.idTipoDesignaColegio = idTipoDesignaColegio;
	}
	public String[] getIdTurnos() {
		return idTurnos;
	}
	public void setIdTurnos(String[] idTurnos) {
		this.idTurnos = idTurnos;
	}
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	/**
	 * @return the nig
	 */
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
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	/**
	 * @return the numProcedimiento
	 */
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
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the procurador
	 */
	public String getProcurador() {
		return procurador;
	}
	/**
	 * @param procurador the procurador to set
	 */
	public void setProcurador(String procurador) {
		this.procurador = procurador;
	}
	/**
	 * @return the resumenAsunto
	 */
	public String getResumenAsunto() {
		return resumenAsunto;
	}
	/**
	 * @param resumenAsunto the resumenAsunto to set
	 */
	public void setResumenAsunto(String resumenAsunto) {
		this.resumenAsunto = resumenAsunto;
	}
	/**
	 * @return the sufijo
	 */
	public String getSufijo() {
		return sufijo;
	}
	/**
	 * @param sufijo the sufijo to set
	 */
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}
	/**
	 * @return the usuModificacion
	 */
	public int getUsuModificacion() {
		return usuModificacion;
	}
	/**
	 * @param usuModificacion the usuModificacion to set
	 */
	public void setUsuModificacion(int usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	/**
	 * @return the nombreTurno
	 */
	public String getNombreTurno() {
		return nombreTurno;
	}
	/**
	 * @param nombreTurno the nombreTurno to set
	 */
	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}
	/**
	 * @return the numColegiado
	 */
	public String getNumColegiado() {
		return numColegiado;
	}
	/**
	 * @param numColegiado the numColegiado to set
	 */
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	/**
	 * @return the apellidosNombre
	 */
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	/**
	 * @param apellidosNombre the apellidosNombre to set
	 */
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	/**
	 * @return the interesados
	 */
	public String[] getInteresados() {
		return interesados;
	}
	/**
	 * @param interesados the interesados to set
	 */
	public void setInteresados(String[] interesados) {
		this.interesados = interesados;
	}
	/**
	 * @return the validadas
	 */
	public String[] getValidadas() {
		return validadas;
	}
	/**
	 * @param validadas the validadas to set
	 */
	public void setValidadas(String[] validadas) {
		this.validadas = validadas;
	}
	/**
	 * @return the calidad
	 */
	public String[] getCalidad() {
		return calidad;
	}
	/**
	 * @param calidad the calidad to set
	 */
	public void setCalidad(String[] calidad) {
		this.calidad = calidad;
	}
	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return the acreditacion
	 */
	public String getAcreditacion() {
		return acreditacion;
	}
	/**
	 * @param acreditacion the acreditacion to set
	 */
	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
	}
	/**
	 * @return the idAcreditacion
	 */
	public String[] getIdAcreditacion() {
		return idAcreditacion;
	}
	/**
	 * @param idAcreditacion the idAcreditacion to set
	 */
	public void setIdAcreditacion(String[] idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}
	/**
	 * @return the modulo
	 */
	public String getModulo() {
		return modulo;
	}
	/**
	 * @param modulo the modulo to set
	 */
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	/**
	 * @return the idModulo
	 */
	public String[] getIdModulos() {
		return idModulos;
	}
	/**
	 * @param
	 */
	public void setIdModulos(String[] idModulos) {
		this.idModulos = idModulos;
	}
	/**
	 * @return the fechaJustificacionDesde
	 */
	public Date getFechaJustificacionDesde() {
		return fechaJustificacionDesde;
	}
	/**
	 * @param fechaJustificacionDesde the fechaJustificacionDesde to set
	 */
	public void setFechaJustificacionDesde(Date fechaJustificacionDesde) {
		this.fechaJustificacionDesde = fechaJustificacionDesde;
	}
	/**
	 * @return the fechaJustificacionHasta
	 */
	public Date getFechaJustificacionHasta() {
		return fechaJustificacionHasta;
	}
	/**
	 * @param fechaJustificacionHasta the fechaJustificacionHasta to set
	 */
	public void setFechaJustificacionHasta(Date fechaJustificacionHasta) {
		this.fechaJustificacionHasta = fechaJustificacionHasta;
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getIdOrigen() {
		return idOrigen;
	}
	public void setIdOrigen(String idOrigen) {
		this.idOrigen = idOrigen;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return the nombreInteresado
	 */
	public String getNombreInteresado() {
		return nombreInteresado;
	}
	/**
	 * @param nombreInteresado the nombreInteresado to set
	 */
	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}
	/**
	 * @return the apellidosInteresado
	 */
	public String getApellidosInteresado() {
		return apellidosInteresado;
	}
	/**
	 * @param apellidosInteresado the apellidosInteresado to set
	 */
	public void setApellidosInteresado(String apellidosInteresado) {
		this.apellidosInteresado = apellidosInteresado;
	}

	public String getIdActuacionesV() {
		return idActuacionesV;
	}
	public void setIdActuacionesV(String idActuacionesV) {
		this.idActuacionesV = idActuacionesV;
	}
	/**
	 * @return the idArt27
	 */
	public String getIdArt27() {
		return idArt27;
	}
	/**
	 * @param idArt27 the idArt27 to set
	 */
	public void setIdArt27(String idArt27) {
		this.idArt27 = idArt27;
	}
	/**
	 * @return the idCalidad
	 */
	public String[] getIdCalidad() {
		return idCalidad;
	}
	/**
	 * @param idCalidad the idCalidad to set
	 */
	public void setIdCalidad(String[] idCalidad) {
		this.idCalidad = idCalidad;
	}
	/**
	 * @return the idProcedimientoActuaciones
	 */
	public String[] getIdProcedimientoActuaciones() {
		return idProcedimientoActuaciones;
	}
	/**
	 * @param idProcedimientoActuaciones the idProcedimientoActuaciones to set
	 */
	public void setIdProcedimientoActuaciones(String[] idProcedimientoActuaciones) {
		this.idProcedimientoActuaciones = idProcedimientoActuaciones;
	}
	/**
	 * @return the idRol
	 */
	public int getIdRol() {
		return idRol;
	}
	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String[] getRol() {
		return rol;
	}
	public void setRol(String[] rol) {
		this.rol = rol;
	}
	public String getDocumentacionActuacion() {
		return documentacionActuacion;
	}
	public void setDocumentacionActuacion(String documentacionActuacion) {
		this.documentacionActuacion = documentacionActuacion;
	}
	public String[] getIdModuloActuaciones() {
		return idModuloActuaciones;
	}
	public void setIdModuloActuaciones(String[] idModuloActuaciones) {
		this.idModuloActuaciones = idModuloActuaciones;
	}
	public String getExpedientes() {
		return expedientes;
	}
	public void setExpedientes(String expedientes) {
		this.expedientes = expedientes;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public String getDescripcionTipoDesigna() {
		return descripcionTipoDesigna;
	}
	public void setDescripcionTipoDesigna(String descripcionTipoDesigna) {
		this.descripcionTipoDesigna = descripcionTipoDesigna;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getApellido1Interesado() {
		return apellido1Interesado;
	}
	public void setApellido1Interesado(String apellido1Interesado) {
		this.apellido1Interesado = apellido1Interesado;
	}
	
	public String getApellido2Interesado() {
		return apellido2Interesado;
	}
	public void setApellido2Interesado(String apellido2Interesado) {
		this.apellido2Interesado = apellido2Interesado;
	}
	
	public String getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}
	
	public String getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}
	public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}
	
	public String getNombrePartida() {
		return nombrePartida;
	}
	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}
	
	public String getValidada() {
		return validada;
	}
	public void setValidada(String validada) {
		this.validada = validada;
	}
	
	public int getExisteDesignaJuzgadoProcedimiento() {
		return existeDesignaJuzgadoProcedimiento;
	}
	public void setExisteDesignaJuzgadoProcedimiento(int existeDesignaJuzgadoProcedimiento) {
		this.existeDesignaJuzgadoProcedimiento = existeDesignaJuzgadoProcedimiento;
	}

	public String getInstitucioncolegiado() {
		return institucioncolegiado;
	}
	public void setInstitucioncolegiado(String institucioncolegiado) {
		this.institucioncolegiado = institucioncolegiado;
	}
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	@Override
	public String toString() {
		return "DesignaItem [ano=" + ano + ", anoProcedimiento=" + anoProcedimiento + ", art27=" + art27 + ", codigo="
				+ codigo + ", defensaJuridica=" + defensaJuridica + ", delitos=" + delitos + ", estados="
				+ Arrays.toString(estados) + ", estado=" + estado + ", factConvenio=" + factConvenio + ", fechaAlta="
				+ fechaAlta + ", fechaAnulacion=" + fechaAnulacion + ", fechaEntradaInicio=" + fechaEntradaInicio
				+ ", fechaEntradaFin=" + fechaEntradaFin + ", fechaEstado=" + fechaEstado + ", fechaFin=" + fechaFin
				+ ", fechaJuicio=" + fechaJuicio + ", fechaModificacion=" + fechaModificacion + ", fechaOficioJuzgado="
				+ fechaOficioJuzgado + ", fechaRecepcionColegio=" + fechaRecepcionColegio + ", idInstitucion="
				+ idInstitucion + ", idInstitucion_juzg=" + idInstitucion_juzg + ", idInstitucion_procur="
				+ idInstitucion_procur + ", idJuzgados=" + Arrays.toString(idJuzgados) + ", idJuzgado=" + idJuzgado
				+ ", nombreJuzgado=" + nombreJuzgado + ", idJuzgadoActu=" + Arrays.toString(idJuzgadoActu)
				+ ", nombreJuzgadoActu=" + nombreJuzgadoActu + ", idPretension=" + idPretension + ", idProcedimientos="
				+ Arrays.toString(idProcedimientos) + ", idProcedimiento=" + idProcedimiento + ", idProcurador="
				+ idProcurador + ", idTipoDesignaColegios=" + Arrays.toString(idTipoDesignaColegios)
				+ ", idTipoDesignaColegio=" + idTipoDesignaColegio + ", idTurnos=" + Arrays.toString(idTurnos)
				+ ", idTurno=" + idTurno + ", nig=" + nig + ", numero=" + numero + ", numProcedimiento="
				+ numProcedimiento + ", observaciones=" + observaciones + ", procurador=" + procurador
				+ ", resumenAsunto=" + resumenAsunto + ", sufijo=" + sufijo + ", usuModificacion=" + usuModificacion
				+ ", nombreTurno=" + nombreTurno + ", numColegiado=" + numColegiado + ", apellidosNombre="
				+ apellidosNombre + ", interesados=" + Arrays.toString(interesados) + ", validadas="
				+ Arrays.toString(validadas) + ", calidad=" + Arrays.toString(calidad) + ", asunto=" + asunto
				+ ", acreditacion=" + acreditacion + ", idAcreditacion=" + Arrays.toString(idAcreditacion) + ", modulo="
				+ modulo + ", idModulo=" + Arrays.toString(idModulos) + ", fechaJustificacionDesde="
				+ fechaJustificacionDesde + ", fechaJustificacionHasta=" + fechaJustificacionHasta + ", origen="
				+ origen + ", idOrigen=" + idOrigen + ", nif=" + nif + ", nombreInteresado=" + nombreInteresado
				+ ", apellidosInteresado=" + apellidosInteresado + ", idActuacionesV=" + idActuacionesV + ", idArt27="
				+ idArt27 + ", idCalidad=" + Arrays.toString(idCalidad) + ", idProcedimientoActuaciones="
				+ Arrays.toString(idProcedimientoActuaciones) + ", idRol=" + idRol + ", rol=" + Arrays.toString(rol)
				+ ", documentacionActuacion=" + documentacionActuacion + ", idModuloActuaciones="
				+ Arrays.toString(idModuloActuaciones) + ", descripcionTipoDesigna=" + descripcionTipoDesigna
				+ ", expedientes=" + expedientes + ", cliente=" + cliente + ", nombreProcedimiento="
				+ nombreProcedimiento + ", nombreColegiado=" + nombreColegiado + ", apellido1Colegiado="
				+ apellido1Colegiado + ", apellido2Colegiado=" + apellido2Colegiado + "]";
	}


	
}