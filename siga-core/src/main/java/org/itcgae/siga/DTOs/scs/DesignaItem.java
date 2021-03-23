package org.itcgae.siga.DTOs.scs;

import java.util.Arrays;
import java.util.Date;

public class DesignaItem {
	
	private int ano;
	private int anoProcedimiento;
	private String art27;
	private String codigo;
	private String defensaJuridica;
	private String delitos;
	private String estados;
	private int factConvenio;
	private Date fechaAlta;
	private Date fechaAnulacion;
	private Date fechaEntrada;
	private Date fechaEstado;
	private Date fechaFin;
	private Date fechaJuicio;
	private Date fechaModificacion;
	private Date fechaOficioJuzgado;
	private Date fechaRecepcionColegio;
	private int idInstitucion;
	private int idInstitucion_juzg;
	private int idInstitucion_procur;
	private int idJuzgado;
	private int idPretension;
	private String idProcedimiento;
	private int idProcurador;
	private int idTipoDesignaColegio;
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
	private int numColegiado;
	private String apellidosNombre;
	private String[] interesados;
	private String validadas;
	
	private String expedientes;
	private String cliente;
	
	
	public DesignaItem() {
		super();
		this.ano = 0;
		this.anoProcedimiento = 0;
		this.art27 = "";
		this.codigo = "";
		this.defensaJuridica = "";
		this.delitos = "";
		this.estados = "";
		this.factConvenio = 0;
		this.fechaAlta = new Date();
		this.fechaAnulacion = new Date();
		this.fechaEntrada = new Date();
		this.fechaEstado = new Date();
		this.fechaFin = new Date();
		this.fechaJuicio = new Date();
		this.fechaModificacion = new Date();
		this.fechaOficioJuzgado = new Date();
		this.fechaRecepcionColegio = new Date();
		this.idInstitucion = 0;
		this.idInstitucion_juzg = 0;
		this.idInstitucion_procur = 0;
		this.idJuzgado = 0;
		this.idPretension = 0;
		this.idProcedimiento = "";
		this.idProcurador = 0;
		this.idTipoDesignaColegio = 0;
		this.idTurno = 0;
		this.nig = "";
		this.numero = 0;
		this.numProcedimiento = "";
		this.observaciones = "";
		this.procurador = "";
		this.resumenAsunto = "";
		this.sufijo = "";
		this.usuModificacion = 0;
		this.nombreTurno = "";
		this.numColegiado = 0;
		this.apellidosNombre = "";
		this.interesados = null;
		this.validadas = "";
	}
	
	public DesignaItem(int ano, int anoProcedimiento, String art27, String codigo, String defensaJuridica,
			String delitos, String estados, int factConvenio, Date fechaAlta, Date fechaAnulacion, Date fechaEntrada,
			Date fechaEstado, Date fechaFin, Date fechaJuicio, Date fechaModificacion, Date fechaOficioJuzgado,
			Date fechaRecepcionColegio, int idInstitucion, int idInstitucion_juzg, int idInstitucion_procur,
			int idJuzgado, int idPretension, String idProcedimiento, int idProcurador, int idTipoDesignaColegio,
			int idTurno, String nig, int numero, String numProcedimiento, String observaciones, String procurador,
			String resumenAsunto, String sufijo, int usuModificacion, String nombreTurno, int numColegiado,
			String apellidosNombre, String[] interesados, String validadas) {
		super();
		this.ano = ano;
		this.anoProcedimiento = anoProcedimiento;
		this.art27 = art27;
		this.codigo = codigo;
		this.defensaJuridica = defensaJuridica;
		this.delitos = delitos;
		this.estados = estados;
		this.factConvenio = factConvenio;
		this.fechaAlta = fechaAlta;
		this.fechaAnulacion = fechaAnulacion;
		this.fechaEntrada = fechaEntrada;
		this.fechaEstado = fechaEstado;
		this.fechaFin = fechaFin;
		this.fechaJuicio = fechaJuicio;
		this.fechaModificacion = fechaModificacion;
		this.fechaOficioJuzgado = fechaOficioJuzgado;
		this.fechaRecepcionColegio = fechaRecepcionColegio;
		this.idInstitucion = idInstitucion;
		this.idInstitucion_juzg = idInstitucion_juzg;
		this.idInstitucion_procur = idInstitucion_procur;
		this.idJuzgado = idJuzgado;
		this.idPretension = idPretension;
		this.idProcedimiento = idProcedimiento;
		this.idProcurador = idProcurador;
		this.idTipoDesignaColegio = idTipoDesignaColegio;
		this.idTurno = idTurno;
		this.nig = nig;
		this.numero = numero;
		this.numProcedimiento = numProcedimiento;
		this.observaciones = observaciones;
		this.procurador = procurador;
		this.resumenAsunto = resumenAsunto;
		this.sufijo = sufijo;
		this.usuModificacion = usuModificacion;
		this.nombreTurno = nombreTurno;
		this.numColegiado = numColegiado;
		this.apellidosNombre = apellidosNombre;
		this.interesados = interesados;
		this.validadas = validadas;
	}

	public int getAno() {
		return ano;
	}


	public void setAno(int ano) {
		this.ano = ano;
	}


	public int getAnoProcedimiento() {
		return anoProcedimiento;
	}


	public void setAnoProcedimiento(int anoProcedimiento) {
		this.anoProcedimiento = anoProcedimiento;
	}


	public String getArt27() {
		return art27;
	}


	public void setArt27(String art27) {
		this.art27 = art27;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDefensaJuridica() {
		return defensaJuridica;
	}


	public void setDefensaJuridica(String defensaJuridica) {
		this.defensaJuridica = defensaJuridica;
	}


	public String getDelitos() {
		return delitos;
	}


	public void setDelitos(String delitos) {
		this.delitos = delitos;
	}


	public String getEstados() {
		return estados;
	}


	public void setEstados(String estados) {
		this.estados = estados;
	}


	public int getFactConvenio() {
		return factConvenio;
	}


	public void setFactConvenio(int factConvenio) {
		this.factConvenio = factConvenio;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}


	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}


	public Date getFechaEstado() {
		return fechaEstado;
	}


	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public Date getFechaJuicio() {
		return fechaJuicio;
	}


	public void setFechaJuicio(Date fechaJuicio) {
		this.fechaJuicio = fechaJuicio;
	}


	public Date getFechaModificacion() {
		return fechaModificacion;
	}


	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}


	public Date getFechaOficioJuzgado() {
		return fechaOficioJuzgado;
	}


	public void setFechaOficioJuzgado(Date fechaOficioJuzgado) {
		this.fechaOficioJuzgado = fechaOficioJuzgado;
	}


	public Date getFechaRecepcionColegio() {
		return fechaRecepcionColegio;
	}


	public void setFechaRecepcionColegio(Date fechaRecepcionColegio) {
		this.fechaRecepcionColegio = fechaRecepcionColegio;
	}


	public int getIdInstitucion() {
		return idInstitucion;
	}


	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}


	public int getIdInstitucion_juzg() {
		return idInstitucion_juzg;
	}


	public void setIdInstitucion_juzg(int idInstitucion_juzg) {
		this.idInstitucion_juzg = idInstitucion_juzg;
	}


	public int getIdInstitucion_procur() {
		return idInstitucion_procur;
	}


	public void setIdInstitucion_procur(int idInstitucion_procur) {
		this.idInstitucion_procur = idInstitucion_procur;
	}


	public int getIdJuzgado() {
		return idJuzgado;
	}


	public void setIdJuzgado(int idJuzgado) {
		this.idJuzgado = idJuzgado;
	}


	public int getIdPretension() {
		return idPretension;
	}


	public void setIdPretension(int idPretension) {
		this.idPretension = idPretension;
	}


	public String getIdProcedimiento() {
		return idProcedimiento;
	}


	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}


	public int getIdProcurador() {
		return idProcurador;
	}


	public void setIdProcurador(int idProcurador) {
		this.idProcurador = idProcurador;
	}


	public int getIdTipoDesignaColegio() {
		return idTipoDesignaColegio;
	}


	public void setIdTipoDesignaColegio(int idTipoDesignaColegio) {
		this.idTipoDesignaColegio = idTipoDesignaColegio;
	}


	public int getIdTurno() {
		return idTurno;
	}


	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}


	public String getNig() {
		return nig;
	}


	public void setNig(String nig) {
		this.nig = nig;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getNumProcedimiento() {
		return numProcedimiento;
	}


	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public String getProcurador() {
		return procurador;
	}


	public void setProcurador(String procurador) {
		this.procurador = procurador;
	}


	public String getResumenAsunto() {
		return resumenAsunto;
	}


	public void setResumenAsunto(String resumenAsunto) {
		this.resumenAsunto = resumenAsunto;
	}


	public String getSufijo() {
		return sufijo;
	}


	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}


	public int getUsuModificacion() {
		return usuModificacion;
	}


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
	public int getNumColegiado() {
		return numColegiado;
	}

	/**
	 * @param numColegiado the numColegiado to set
	 */
	public void setNumColegiado(int numColegiado) {
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
	public String getValidadas() {
		return validadas;
	}

	/**
	 * @param validadas the validadas to set
	 */
	public void setValidadas(String validadas) {
		this.validadas = validadas;
	}

	@Override
	public String toString() {
		return "DesignaItem [ano=" + ano + ", anoProcedimiento=" + anoProcedimiento + ", art27=" + art27 + ", codigo="
				+ codigo + ", defensaJuridica=" + defensaJuridica + ", delitos=" + delitos + ", estados=" + estados
				+ ", factConvenio=" + factConvenio + ", fechaAlta=" + fechaAlta + ", fechaAnulacion=" + fechaAnulacion
				+ ", fechaEntrada=" + fechaEntrada + ", fechaEstado=" + fechaEstado + ", fechaFin=" + fechaFin
				+ ", fechaJuicio=" + fechaJuicio + ", fechaModificacion=" + fechaModificacion + ", fechaOficioJuzgado="
				+ fechaOficioJuzgado + ", fechaRecepcionColegio=" + fechaRecepcionColegio + ", idInstitucion="
				+ idInstitucion + ", idInstitucion_juzg=" + idInstitucion_juzg + ", idInstitucion_procur="
				+ idInstitucion_procur + ", idJuzgado=" + idJuzgado + ", idPretension=" + idPretension
				+ ", idProcedimiento=" + idProcedimiento + ", idProcurador=" + idProcurador + ", idTipoDesignaColegio="
				+ idTipoDesignaColegio + ", idTurno=" + idTurno + ", nig=" + nig + ", numero=" + numero
				+ ", numProcedimiento=" + numProcedimiento + ", observaciones=" + observaciones + ", procurador="
				+ procurador + ", resumenAsunto=" + resumenAsunto + ", sufijo=" + sufijo + ", usuModificacion="
				+ usuModificacion + ", nombreTurno=" + nombreTurno + ", numColegiado=" + numColegiado
				+ ", apellidosNombre=" + apellidosNombre + ", interesados=" + Arrays.toString(interesados)
				+ ", validadas=" + validadas + "]";
	}
}